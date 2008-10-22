/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.PAResearchOrganizationDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAResearchOrganizationServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the collaborating organizations.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings("PMD")
public class CollaboratorsAction extends ActionSupport 
        implements Preparable {

    private static final long serialVersionUID = 123412653L;
    private static final int AD_CITY_IDX = 1;
    private static final int AD_COUNTRY_IDX = 3;
    private static final int AD_ZIP_IDX = 2;
    private static final String ACT_FACILITY_SAVE = "facilitySave";
    private static final String ACT_EDIT = "edit";
    private static final String ACT_DELETE = "delete";
    private static final String ACT_CREATE = "create";

    private StudyParticipationServiceRemote sPartService;
    private PAResearchOrganizationServiceRemote paroService;
    private PAOrganizationServiceRemote paoService;
    private List<CountryRegAuthorityDTO> countryRegDTO;
    private Ii spIi;
    private List<OrganizationWebDTO> organizationList = null; 
    private OrganizationDTO selectedOrgDTO = null;
    private Organization editOrg;
    private static final String DISPLAYJSP = "displayJsp";
    private Long cbValue;
    private String functionalCode;
    private boolean newParticipation;


    /** 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        sPartService = PaRegistry.getStudyParticipationService();
        paroService = PaRegistry.getPAResearchOrganizationService();
        paoService = PaRegistry.getPAOrganizationService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
        .getRequest().getSession()
        .getAttribute(Constants.TRIAL_SUMMARY);

        spIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return Action result.
     * @throws Exception exception.
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return SUCCESS;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String create() throws Exception {
        setNewParticipation(true);
        return ACT_CREATE;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String facilitySave() throws Exception {
        clearErrorsAndMessages();
        
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) 
                ServletActionContext.getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("You must select an organization before adding.");
            return SUCCESS;
        }
        String poOrgId = tab.getFacilityOrganization().getIdentifier();

        Organization org = new Organization();
        org.setIdentifier(poOrgId);
        Organization paOrg = paoService.getOrganizationByIndetifers(org);

        if (paOrg == null) {
            org.setCity(tab.getFacilityOrganization().getCity());
            org.setCountryName(tab.getFacilityOrganization().getCountryName());
            org.setName(tab.getFacilityOrganization().getName());
            org.setPostalCode(tab.getFacilityOrganization().getPostalCode());
            paOrg = paoService.createOrganization(org);
        }

        PAResearchOrganizationDTO roDto = null;
        List<PAResearchOrganizationDTO> roList = paroService.getByOrganization(paOrg.getId());
        
        if (!roList.isEmpty()) {
            roDto = roList.get(0);
        } else {
            roDto = new PAResearchOrganizationDTO();
            roDto.setOrganizationId(paOrg.getId());
            roDto = paroService.create(roDto);
        }

        StudyParticipationDTO sp = new StudyParticipationDTO();
        sp.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.getByCode(functionalCode)));
        sp.setHealthcareFacilityIi(null);
        sp.setResearchOrganizationIi(IiConverter.convertToIi(roDto.getId()));
        sp.setIi(null);
        sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP Identifier"));
        sp.setStudyProtocolIi(spIi);
        sp = sPartService.create(sp);
        
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        return ACT_FACILITY_SAVE;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String facilityUpdate() throws Exception {
        clearErrorsAndMessages();

        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("System error getting participating orgainzation data from session.");
            return SUCCESS;
        }
        StudyParticipationDTO sp = sPartService.get(IiConverter.convertToIi(tab.getStudyParticipationId()));
        sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.getByCode(functionalCode)));
        sp = sPartService.update(sp);
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return ACT_FACILITY_SAVE;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        StudyParticipationDTO spDto = sPartService.get(IiConverter.convertToIi(cbValue));
        PAResearchOrganizationDTO roDto = paroService.get(IiConverter.convertToLong(spDto.getResearchOrganizationIi()));
        Organization org = new Organization();
        org.setId(roDto.getOrganizationId());
        editOrg = paoService.getOrganizationByIndetifers(org);
        setFunctionalCode(spDto.getFunctionalCode().getCode());
        
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(null);
        tab.setResearchOrganization(org);
        tab.setPoHealthCareFacilityIi(null);
        tab.setPoOrganizationIi(null);
        tab.setNewParticipation(false);

        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return ACT_EDIT;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        clearErrorsAndMessages();
        
        sPartService.delete(IiConverter.convertToIi(cbValue));
        
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        loadForm();
        return ACT_DELETE;
    }

    private void loadForm() throws Exception {
        ArrayList<StudyParticipationDTO> criteriaList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipationFunctionalCode cd : StudyParticipationFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudyParticipationDTO searchCode = new StudyParticipationDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        organizationList = new ArrayList<OrganizationWebDTO>();
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIi, criteriaList);
        for (StudyParticipationDTO sp : spList) {
            PAResearchOrganizationDTO ro = paroService.get(IiConverter.convertToLong(sp.getResearchOrganizationIi()));
            Organization orgBo = new Organization();
            orgBo.setId(ro.getOrganizationId());
            orgBo = paoService.getOrganizationByIndetifers(orgBo);
            
            OrganizationWebDTO orgWebDTO = new OrganizationWebDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIi()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            orgWebDTO.setFunctionalRole(sp.getFunctionalCode().getCode());
            organizationList.add(orgWebDTO);
        }

    }

    /**
     * @return the organizationList
     * @throws Exception on error.
     */    
    public String displayOrg() throws Exception {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));        
        selectedOrgDTO = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0);

        // store selection
        Organization org = new Organization();
        org.setCity(selectedOrgDTO.getPostalAddress().getPart().get(AD_CITY_IDX).getValue());
        org.setCountryName(selectedOrgDTO.getPostalAddress().getPart().get(AD_COUNTRY_IDX).getCode());
        org.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        org.setName(selectedOrgDTO.getName().getPart().get(0).getValue());
        org.setPostalCode(selectedOrgDTO.getPostalAddress().getPart().get(AD_ZIP_IDX).getValue());
        
        ParticipatingOrganizationsTabWebDTO tab =  new ParticipatingOrganizationsTabWebDTO();
        tab.setPoOrganizationIi(selectedOrgDTO.getIdentifier());
        tab.setFacilityOrganization(org);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return DISPLAYJSP;
    }

    /**
     * @return the organizationList
     */
    public List<OrganizationWebDTO> getOrganizationList() {
        return organizationList;
    }

    /**
     * @param organizationList the organizationList to set
     */
    public void setOrganizationList(List<OrganizationWebDTO> organizationList) {
        this.organizationList = organizationList;
    }
    
    /**
     * @return result
     * @throws Exception on error.
     */    
    public String nodecorlookup() throws Exception {
        countryRegDTO = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
        return "lookup";
    }

    /**
     * @return the countryRegDTO
     */
    public List<CountryRegAuthorityDTO> getCountryRegDTO() {
        return countryRegDTO;
    }

    /**
     * @param countryRegDTO the countryRegDTO to set
     */
    public void setCountryRegDTO(List<CountryRegAuthorityDTO> countryRegDTO) {
        this.countryRegDTO = countryRegDTO;
    }

    /**
     * @return the selectedOrgDTO
     */
    public OrganizationDTO getSelectedOrgDTO() {
        return selectedOrgDTO;
    }

    /**
     * @param selectedOrgDTO the selectedOrgDTO to set
     */
    public void setSelectedOrgDTO(OrganizationDTO selectedOrgDTO) {
        this.selectedOrgDTO = selectedOrgDTO;
    }
    /**
     * @return the cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue the cbValue to set
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return the newParticipation
     */
    public boolean isNewParticipation() {
        return newParticipation;
    }

    /**
     * @param newParticipation the newParticipation to set
     */
    public void setNewParticipation(boolean newParticipation) {
        this.newParticipation = newParticipation;
    }

    /**
     * @return the editOrg
     */
    public Organization getEditOrg() {
        return editOrg;
    }

    /**
     * @param editOrg the editOrg to set
     */
    public void setEditOrg(Organization editOrg) {
        this.editOrg = editOrg;
    }

    /**
     * @return the functionalCode
     */
    public String getFunctionalCode() {
        return functionalCode;
    }

    /**
     * @param functionalCode the functionalCode to set
     */
    public void setFunctionalCode(String functionalCode) {
        this.functionalCode = functionalCode;
    }

}
