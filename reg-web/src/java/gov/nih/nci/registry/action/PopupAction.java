package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.PersonDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class PopupAction extends ActionSupport implements Preparable {
    private List countryList = new ArrayList();
    private List<SearchOrgResultDisplay> orgs = new ArrayList<SearchOrgResultDisplay>();
    private List<PersonDTO> persons = new ArrayList<PersonDTO>();
    private OrgSearchCriteria orgSearchCriteria = new OrgSearchCriteria();
    private OrgSearchCriteria createOrg = new OrgSearchCriteria();
    private PersonDTO personDTO = null;
    private static final String PERS_CREATE_RESPONSE = "create_pers_response";

    /**
     * @throws Exception on error
     */
    public void prepare() throws Exception {
        populateCountryList();
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuppersons() {
        persons = null;
        return "persons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            orgs = null;
            populateCountryList();
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
        return populateOrgs(false);
    }

    /**
     * @throws PAException on error
     * @return result
     */
    public String displayPersonsList() throws PAException {
        try {
            String firstName = ServletActionContext.getRequest().getParameter("firstName");
            String lastName = ServletActionContext.getRequest().getParameter("lastName");
            String email = ServletActionContext.getRequest().getParameter("email");
            String ctep = ServletActionContext.getRequest().getParameter("ctepId");
            if ((firstName != null) && (firstName.equals("")) && (lastName != null) && (lastName.equals(""))
                    && (email.equals("")) && ctep != null && !(ctep.length() > 0)) {
                String message = "Please enter at least one search criteria";
                persons = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return SUCCESS;
            }
            gov.nih.nci.services.person.PersonDTO p = new gov.nih.nci.services.person.PersonDTO();
            //
            if (email != null && email.length() > 0) {
                DSet<Tel> list = new DSet<Tel>();
                list.setItem(new HashSet<Tel>());
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                list.getItem().add(telemail);
                p.setTelecomAddress(list);
            }
            //
            List<gov.nih.nci.services.person.PersonDTO> poPersonList = 
                                                        new ArrayList<gov.nih.nci.services.person.PersonDTO>();
            if (ctep != null && ctep.length() > 0) {
                IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
                identifiedPersonDTO.setIdentifier(IiConverter.converToIdentifiedEntityIi(ctep));
                List<IdentifiedPersonDTO> retResultList = 
                                  RegistryServiceLocator.getIdentifiedPersonEntityService().search(identifiedPersonDTO);
                p.setIdentifier(retResultList.get(0).getPlayerIdentifier());
            } else {
                p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
            }
            poPersonList = RegistryServiceLocator.getPoPersonEntityService().search(p);
            for (gov.nih.nci.services.person.PersonDTO poPersonDTO : poPersonList) {
                persons.add(EnPnConverter.convertToPaPersonDTO(poPersonDTO));
            }
        } catch (URISyntaxException e) {
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return populateOrgs(true);
    }

    private String populateOrgs(boolean pagination) {
        try {
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            String ctepId = ServletActionContext.getRequest().getParameter("ctepid");
            if (orgName.equals("") && countryName.equals("") && cityName.equals("") && zipCode.equals("")
                    && ctepId != null && !(ctepId.length() > 0)) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return pagination ? "orgs" : SUCCESS;
            }
            if (countryName.equals("") && ctepId != null && !(ctepId.length() > 0)) {
                String message = "Please select a country";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return pagination ? "orgs" : SUCCESS;
            }
            if (pagination) {
                orgSearchCriteria.setOrgName(orgName);
                orgSearchCriteria.setOrgCity(cityName);
                orgSearchCriteria.setOrgCountry(countryName);
                orgSearchCriteria.setOrgZip(zipCode);
            }
            OrganizationDTO criteria = new OrganizationDTO();
            if (ctepId != null && ctepId.length() > 0) {
                IdentifiedOrganizationDTO identifiedOrganizationDTO = new IdentifiedOrganizationDTO();
                identifiedOrganizationDTO.setAssignedId(IiConverter.converToIdentifiedEntityIi(ctepId));
                List<IdentifiedOrganizationDTO> identifiedOrgs = RegistryServiceLocator
                        .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
                criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
            } else {
                criteria.setName(EnOnConverter.convertToEnOn(orgName));
                criteria
                        .setPostalAddress(AddressConverterUtil.create(null, null, cityName, null, 
                                                                                            zipCode, countryName));
            }
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria);
            convertPoOrganizationDTO(callConvert);
            return pagination ? "orgs" : SUCCESS;
        } catch (Exception e) {
            orgs = null;
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            return pagination ? "orgs" : SUCCESS;
        }
    }

    private String getCountryNameUsingCode(String code) {
        for (int i = 0; i < countryList.size(); i++) {
            gov.nih.nci.pa.domain.Country country = (gov.nih.nci.pa.domain.Country) countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }

    private void populateCountryList() throws PAException {
        countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = RegistryServiceLocator.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @throws PAException on error
     * @return result
     */
    public String createOrganization() throws PAException {
        OrganizationDTO orgDto = new OrganizationDTO();
        String orgName = ServletActionContext.getRequest().getParameter("orgName");
        if (orgName != null && !PAUtil.isNotEmpty(orgName)) {
            addActionError("Organization is a required field");
        }
        String orgStAddress = ServletActionContext.getRequest().getParameter("orgStAddress");
        if (orgStAddress != null && !PAUtil.isNotEmpty(orgStAddress)) {
            addActionError("Street address is a required field");
        }
        String countryName = ServletActionContext.getRequest().getParameter("countryName");
        if (countryName != null && countryName.equals("aaa")) {
            addActionError("Country is a required field");
        }
        String cityName = ServletActionContext.getRequest().getParameter("cityName");
        if (cityName != null && !PAUtil.isNotEmpty(cityName)) {
            addActionError("City is a required field");
        }
        String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
        if (zipCode != null && !PAUtil.isNotEmpty(zipCode)) {
            addActionError("Zip is a required field");
        }
        String stateName = ServletActionContext.getRequest().getParameter("stateName");
        if (stateName != null && !PAUtil.isNotEmpty(stateName)) {
            addActionError("State is a required field");
        }
        String email = ServletActionContext.getRequest().getParameter("email");
        if (email != null && !PAUtil.isNotEmpty(email)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String phoneNumer = ServletActionContext.getRequest().getParameter("phoneNumber");
        String faxNumber = ServletActionContext.getRequest().getParameter("fax");
        String ttyNumber = ServletActionContext.getRequest().getParameter("tty");
        String url = ServletActionContext.getRequest().getParameter("url");
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = getActionErrors().iterator();
            while (i.hasNext()) {
                sb.append(" - " + i.next().toString());
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", sb.toString());
            return "create_org_response";
        }
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, cityName, stateName, zipCode,
                countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        try {
            if (phoneNumer != null && phoneNumer.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phoneNumer, null));
                telco.getItem().add(t);
            }
            if (faxNumber != null && faxNumber.length() > 0) {
                Tel fax = new Tel();
                fax.setValue(new URI("x-text-fax", faxNumber, null));
                telco.getItem().add(fax);
            }
            if (ttyNumber != null && ttyNumber.length() > 0) {
                Tel tty = new Tel();
                tty.setValue(new URI("x-text-tel", ttyNumber, null));
                telco.getItem().add(tty);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                telco.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            telco.getItem().add(telemail);
            orgDto.setTelecomAddress(telco);
            Ii id = RegistryServiceLocator.getPoOrganizationEntityService().createOrganization(orgDto);
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert.add(RegistryServiceLocator.getPoOrganizationEntityService().getOrganization(id));
            convertPoOrganizationDTO(callConvert);
        } catch (NullifiedEntityException e) {
            handleError(e.getMessage());
        } catch (URISyntaxException e) {
            handleError(e.getMessage());
        } catch (EntityValidationException e) {
            handleError(e.getMessage());
        } catch (PAException e) {
            handleError(e.getMessage());
        }
        return "create_org_response";
    }

    private String handleError(String message) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return "create_org_response";
    }

    /**
     * 
     * @return String rest
     */
    public String createPerson() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        if (firstName != null && !PAUtil.isNotEmpty(firstName)) {
            addActionError("First Name is a required field");
        }
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (lastName != null && !PAUtil.isNotEmpty(lastName)) {
            addActionError("Last Name is a required field");
        }
        String email = ServletActionContext.getRequest().getParameter("email");
        if (email != null && !PAUtil.isNotEmpty(email)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String streetAddr = ServletActionContext.getRequest().getParameter("streetAddr");
        if (streetAddr != null && !PAUtil.isNotEmpty(streetAddr)) {
            addActionError("Street address is a required field");
        }
        String city = ServletActionContext.getRequest().getParameter("city");
        if (city != null && !PAUtil.isNotEmpty(city)) {
            addActionError("City is a required field");
        }
        String zip = ServletActionContext.getRequest().getParameter("zip");
        if (zip != null && !PAUtil.isNotEmpty(zip)) {
            addActionError("Zip is a required field");
        }
        String country = ServletActionContext.getRequest().getParameter("country");
        if (country != null && country.equals("aaa")) {
            addActionError("Country is a required field");
        }
        
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = getActionErrors().iterator();
            while (i.hasNext()) {
                sb.append(" - " + i.next().toString());
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", sb.toString());
            return PERS_CREATE_RESPONSE;
        }
        
        String preFix = ServletActionContext.getRequest().getParameter("preFix");
        String midName = ServletActionContext.getRequest().getParameter("midName");
        String state = ServletActionContext.getRequest().getParameter("state");
        String phone = ServletActionContext.getRequest().getParameter("phone");
        String tty = ServletActionContext.getRequest().getParameter("tty");
        String fax = ServletActionContext.getRequest().getParameter("fax");
        String url = ServletActionContext.getRequest().getParameter("url");
        String suffix = ServletActionContext.getRequest().getParameter("suffix");
        //
        gov.nih.nci.services.person.PersonDTO dto = new gov.nih.nci.services.person.PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        // if middel name exists stick it in here!
        if (midName != null && PAUtil.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.FAM);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);
        if (preFix != null && PAUtil.isNotEmpty(preFix)) {
            Enxp partPfx = new Enxp(EntityNamePartType.PFX);
            partPfx.setValue(preFix);
            dto.getName().getPart().add(partPfx);
        }
        if (suffix != null && PAUtil.isNotEmpty(suffix)) {
            Enxp partSfx = new Enxp(EntityNamePartType.SFX);
            partSfx.setValue(suffix);
            dto.getName().getPart().add(partSfx);
        }
       // dto.getName().getPart().add(part);
        DSet<Tel> list = new DSet<Tel>();
        list.setItem(new HashSet<Tel>());
        try {
            if (phone != null && phone.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phone, null));
                list.getItem().add(t);
            }
            if (fax != null && fax.length() > 0) {
                Tel faxTel = new Tel();
                faxTel.setValue(new URI("x-text-fax", fax, null));
                list.getItem().add(faxTel);
            }
            if (tty != null && tty.length() > 0) {
                Tel ttyTel = new Tel();
                ttyTel.setValue(new URI("x-text-tel", tty, null));
                list.getItem().add(ttyTel);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                list.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            list.getItem().add(telemail);
            dto.setTelecomAddress(list);
            dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
            Ii id = RegistryServiceLocator.getPoPersonEntityService().createPerson(dto);
            persons.add(EnPnConverter.convertToPaPersonDTO(RegistryServiceLocator.getPoPersonEntityService().getPerson(
                    id)));
        } catch (NullifiedEntityException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (PAException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (URISyntaxException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        }
        return PERS_CREATE_RESPONSE;
    }

    private String handleExceptions(String message, String returnString) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return returnString;
    }

    private void convertPoOrganizationDTO(List<OrganizationDTO> poOrgDtos) {
        SearchOrgResultDisplay displayElement = null;
        for (int i = 0; i < poOrgDtos.size(); i++) {
            displayElement = new SearchOrgResultDisplay();
            displayElement.setId(poOrgDtos.get(i).getIdentifier().getExtension().toString());
            displayElement.setName(poOrgDtos.get(i).getName().getPart().get(0).getValue());
            //
            int partSize = poOrgDtos.get(i).getPostalAddress().getPart().size();
            AddressPartType type = null;
            for (int k = 0; k < partSize; k++) {
                type = poOrgDtos.get(i).getPostalAddress().getPart().get(k).getType();
                if (type.name().equals("CNT")) {
                    displayElement.setCountry(getCountryNameUsingCode(poOrgDtos.get(i).getPostalAddress().getPart()
                            .get(k).getCode()));
                }
                if (type.name().equals("ZIP")) {
                    displayElement.setZip(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
                if (type.name().equals("CTY")) {
                    displayElement.setCity(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
                if (type.name().equals("STA")) {
                    displayElement.setState(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
            }
            orgs.add(displayElement);
        }
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the persons
     */
    public List<PersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }

    /**
     * @return the orgSearchCriteria
     */
    public OrgSearchCriteria getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @param orgSearchCriteria the orgSearchCriteria to set
     */
    public void setOrgSearchCriteria(OrgSearchCriteria orgSearchCriteria) {
        this.orgSearchCriteria = orgSearchCriteria;
    }

    /**
     * @return the createOrg
     */
    public OrgSearchCriteria getCreateOrg() {
        return createOrg;
    }

    /**
     * @param createOrg the createOrg to set
     */
    public void setCreateOrg(OrgSearchCriteria createOrg) {
        this.createOrg = createOrg;
    }

    /**
     * @return the personDTO
     */
    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    /**
     * @param personDTO the personDTO to set
     */
    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    /**
     * @return the orgs
     */
    public List<SearchOrgResultDisplay> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<SearchOrgResultDisplay> orgs) {
        this.orgs = orgs;
    }
}
