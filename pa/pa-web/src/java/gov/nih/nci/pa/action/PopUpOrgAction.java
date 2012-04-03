package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Mar 27, 2012
 */
public class PopUpOrgAction extends AbstractPopUpPoAction {
    private static final long serialVersionUID = -6795733516099098037L;
    private static final Set<String> US_OR_CANADA = new HashSet<String>(Arrays.asList("USA", "CAN"));
    private static final String AUSTRALIA = "AUS";
    private static final Set<Integer> AUSTRALIA_STATE_LENGTHS = new HashSet<Integer>(Arrays.asList(2, 3));

    private List<PaOrganizationDTO> orgs = new ArrayList<PaOrganizationDTO>();
    private final PaOrganizationDTO orgSearchCriteria = new PaOrganizationDTO();
    private String ctepId;
    private String familyName;
    private String fax;
    private String orgName;
    private String orgStAddress;
    private String tty;
    private String url;

    /**
     *
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            getCountriesList();
            orgs.clear();
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return "orgs";
    }

    /**
     *
     * @return result
     */
    public String displayOrgList() {
        return processDisplayOrganizations(SUCCESS);
    }

    /**
     *
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return processDisplayOrganizations("orgs");
    }

    /**
     * @return result
     */
    public String createOrganization() {
        validateRequiredField(getOrgName(), "Organization");
        validateRequiredField(getOrgStAddress(), "Street address");
        validateRequiredField(getCountryName(), "Country");
        validateRequiredField(getCityName(), "City");
        validateRequiredField(getZipCode(), "Zip");
        validateRequiredField(getEmail(), "Email");
        if (StringUtils.isNotBlank(getUrl()) && !PAUtil.isCompleteURL(getUrl())) {
            addActionError("Please provide a full URL that includes protocol and host, e.g. http://cancer.gov/");
        }
        countrySpecificValidations();
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            for (String actionErr : getActionErrors()) {
                sb.append(" - ").append(actionErr);
            }
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, sb.toString());
            return "create_org_response";
        }
        String result = null;
        try {
            result = updatePo();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return result;
    }

    private void validateRequiredField(String value, String type) {
        if (StringUtils.isEmpty(value)) {
            addActionError(type + " is a required field");
        } else if ("Email".equals(type) && !PAUtil.isValidEmail(value)) {
            addActionError("Email address is invalid");
        }
    }

    private void countrySpecificValidations() {
        Integer stateLen = StringUtils.length(getStateName());
        if (US_OR_CANADA.contains(getCountryName())) {
            if (stateLen != 2) {
                addActionError("2-letter State/Province Code required for USA/Canada");
            } else {
                // PO Service requires upper case state codes for US and Canada
                setStateName(getStateName().toUpperCase());
            }
            validateUsCanadaPhoneNumber(getTelephone(), "phone");
            validateUsCanadaPhoneNumber(fax, "fax");
            validateUsCanadaPhoneNumber(tty, "TTY");
        }
        if (StringUtils.equals(AUSTRALIA, getCountryName()) && !AUSTRALIA_STATE_LENGTHS.contains(stateLen)) {
            addActionError("2/3-letter State/Province Code required for Australia");
        }
    }

    private void validateUsCanadaPhoneNumber(String number, String type) {
        String badPhoneMsg = "Valid USA/Canada %s numbers must match ###-###-####x#*, e.g. "
                + "555-555-5555 or 555-555-5555x123";
        if (StringUtils.isNotBlank(number) && !PAUtil.isUsOrCanadaPhoneNumber(number)) {
            addActionError(String.format(badPhoneMsg, type));
        }
    }

    private String updatePo() throws URISyntaxException, EntityValidationException, CurationException,
            NullifiedEntityException {
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(EnOnConverter.convertToEnOn(getOrgName()));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, getCityName(), getStateName(),
                getZipCode(), getCountryName()));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        if (StringUtils.isNotBlank(getTelephone())) {
            Tel t = new Tel();
            t.setValue(new URI("tel", getTelephone(), null));
            telco.getItem().add(t);
        }
        if (StringUtils.isNotBlank(fax)) {
            Tel f = new Tel();
            f.setValue(new URI("x-text-fax", fax, null));
            telco.getItem().add(f);
        }
        if (StringUtils.isNotBlank(tty)) {
            Tel tt = new Tel();
            tt.setValue(new URI("x-text-tel", tty, null));
            telco.getItem().add(tt);
        }
        if (StringUtils.isNotBlank(url)) {
            TelUrl telurl = new TelUrl();
            telurl.setValue(new URI(url));
            telco.getItem().add(telurl);
        }
        TelEmail telemail = new TelEmail();
        telemail.setValue(new URI("mailto:" + getEmail()));
        telco.getItem().add(telemail);
        orgDto.setTelecomAddress(telco);
        Ii id = PoRegistry.getOrganizationEntityService().createOrganization(orgDto);
        OrganizationDTO newOrg = PoRegistry.getOrganizationEntityService().getOrganization(id);
        convertPoOrganizationDTO(newOrg, null);
        return "create_org_response";
    }

    @SuppressWarnings("unchecked")
    private String processDisplayOrganizations(String retvalue) {
        try {
            getCountriesList();
            orgSearchCriteria.setName(orgName);
            orgSearchCriteria.setFamilyName(familyName);
            orgSearchCriteria.setCity(getCityName());
            orgSearchCriteria.setCountry(getCountryName());
            orgSearchCriteria.setZip(getZipCode());
            orgSearchCriteria.setState(getStateName());
            orgSearchCriteria.setCtepId(getCtepId());
            List<OrganizationDTO> orgList = PADomainUtils.orgSearchByNameAddressCtepId(orgSearchCriteria);
            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            for (OrganizationDTO dto : orgList) {
                if (CollectionUtils.isNotEmpty(dto.getFamilyOrganizationRelationships().getItem())) {
                    famOrgRelIiList.addAll(dto.getFamilyOrganizationRelationships().getItem());
                }
            }
            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
            for (OrganizationDTO dto : orgList) {
                PaOrganizationDTO paDTO = PADomainUtils.convertPoOrganizationDTO(dto, getCountryList());
                paDTO.setFamilies(getFamilies(dto.getFamilyOrganizationRelationships(), familyMap));
                orgs.add(paDTO);
            }
            return retvalue;
        } catch (Exception e) {
            return ERROR;
        }
    }

    @SuppressWarnings("unchecked")
    private void convertPoOrganizationDTO(OrganizationDTO poOrg, Map<Ii, FamilyDTO> familyMap) {
        Map<String, String> addrs = AddressConverterUtil.convertToAddressBo(poOrg.getPostalAddress());
        PaOrganizationDTO paOrg = new PaOrganizationDTO();
        paOrg.setCountry(addrs.get(AdxpCnt.class.getName()));
        paOrg.setZip(addrs.get(AdxpZip.class.getName()));
        paOrg.setCity(addrs.get(AdxpCty.class.getName()));
        paOrg.setState(addrs.get(AdxpSta.class.getName()));
        paOrg.setId(poOrg.getIdentifier().getExtension().toString());
        paOrg.setName(poOrg.getName().getPart().get(0).getValue());
        if (MapUtils.isNotEmpty(familyMap)) {
            paOrg.setFamilies(getFamilies(poOrg.getFamilyOrganizationRelationships(), familyMap));
        }
        orgs.clear();
        orgs.add(paOrg);
    }

    private Map<Long, String> getFamilies(DSet<Ii> familyOrganizationRelationships, Map<Ii, FamilyDTO> familyMap) {
        Map<Long, String> retMap = new HashMap<Long, String>();
        Set<Ii> famOrgIis = familyOrganizationRelationships.getItem();
        for (Ii ii : famOrgIis) {
            FamilyDTO dto = familyMap.get(ii);
            retMap.put(IiConverter.convertToLong(dto.getIdentifier()),
                    EnOnConverter.convertEnOnToString(dto.getName()));
        }
        return retMap;
    }

    /**
     * @return the orgs
     */
    public List<PaOrganizationDTO> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<PaOrganizationDTO> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the ctepId
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     * @param ctepId the ctepId to set
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgStAddress
     */
    public String getOrgStAddress() {
        return orgStAddress;
    }

    /**
     * @param orgStAddress the orgStAddress to set
     */
    public void setOrgStAddress(String orgStAddress) {
        this.orgStAddress = orgStAddress;
    }

    /**
     * @return the tty
     */
    public String getTty() {
        return tty;
    }

    /**
     * @param tty the tty to set
     */
    public void setTty(String tty) {
        this.tty = tty;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}