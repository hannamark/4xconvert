/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.PAHealthCareFacilityDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the participating organizations.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD")
public class ParticipatingOrganizationsAction extends ActionSupport 
        implements Preparable {
    private static final long serialVersionUID = 123412653L;
    private static final int AD_CITY_IDX = 1;
    private static final int AD_COUNTRY_IDX = 3;
    private static final int AD_ZIP_IDX = 2;
    private static final String ACT_FACILITY_SAVE = "facilitySave";
    private static final String ACT_EDIT = "edit";
    private static final String ACT_DELETE = "delete";

    private StudyProtocolServiceRemote sProService;
    private StudyParticipationServiceRemote sPartService;
    private StudySiteAccrualStatusServiceRemote ssasService;
    private PAHealthCareFacilityServiceRemote pahfService;
    private PAOrganizationServiceRemote paoService;
    private HealthCareFacilityCorrelationServiceRemote pohfService;
    private List<CountryRegAuthorityDTO> countryRegDTO;
    private Ii spIi;
    private List<OrganizationWebDTO> organizationList = null; 
    private OrganizationDTO selectedOrgDTO = null;
    private Organization editOrg;
    private static final int THREE = 3;    
    private static final String DISPLAYJSP = "displayJsp";
    private Long cbValue;
    private String recStatus;
    private String recStatusDate;
    private boolean newParticipation;


    /** 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        sProService = PaRegistry.getStudyProtocolService();
        sPartService = PaRegistry.getStudyParticipationService();
        ssasService = PaRegistry.getStudySiteAccrualStatusService();
        pahfService = PaRegistry.getPAHealthCareFacilityService();
        paoService = PaRegistry.getPAOrganizationService();
        pohfService = PaRegistry.getHealthCareFacilityCorrelationService();

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
        loadForm();
        setNewParticipation(true);
        return Action.SUCCESS;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String facilitySave() throws Exception {
        clearErrorsAndMessages();
        enforceBusinessRules();
        
        // 1) get the po identifer from input field ( user selected from pop-up)
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) 
                ServletActionContext.getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("You must select an organization before adding.");
            return SUCCESS;
        }
        String poOrgId = tab.getFacilityOrganization().getIdentifier();

        // 2) using the poIdentifer call po.getHealthcareFacility(poOrganizationid) (this 
        //
        //  if (poHealthcareFacilityIi == null) {
        //      poHealthcareFacilityIi = pa.regisitry.poCreateHealthcareFacility(poISOHealthcareFacilityDTO) 
        //
        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setPlayerIdentifier(tab.getPoOrganizationIi());
        List<HealthCareFacilityDTO> poHfList = pohfService.search(hcf);
        if (poHfList.isEmpty()) {
            tab.setPoHealthCareFacilityIi(pohfService.createCorrelation(hcf));
         }

        // 3) at this point we have both poOrganizationIi & poHealthcareFacilityIi
        // 4) check if PA has organization and healthcarefacitly (local copy)
        // 5) paOrganizationIi = PAOrganizationServiceRemote.getOrganizationByIndetifers(poOrganizationIi)
        //      (this method is implemented )
        Organization org = new Organization();
        org.setIdentifier(poOrgId);
        Organization paOrg = paoService.getOrganizationByIndetifers(org);

        // 6) if (paOrganizationIi == null ) {
        //      create an organization in pa
        //      paOrganizationIi = PAOrganizationServiceRemote.createOrganization(Organization) 
        //          (this method is implemented)
        //    }
        if (paOrg == null) {
            org.setCity(tab.getFacilityOrganization().getCity());
            org.setCountryName(tab.getFacilityOrganization().getCountryName());
            org.setName(tab.getFacilityOrganization().getName());
            org.setPostalCode(tab.getFacilityOrganization().getPostalCode());
            paOrg = paoService.createOrganization(org);
        }

        // 7) paHealthcareFacilityIi = PAHealthcareFacilityServiceRemote.getHealthcareFacility(poHealthcareFacilityIi)
        // 8) if (paHealthcareFacilityIi == null ) {
        //      create an HealthcareFacilityIi in pa
        //      paHealthcareFacilityIi = PAHealthcareFacilityServiceRemote.createHealthcareFacility(HealthcareFacility) 
        //          (this method is NOT impemented)
        //    }
        PAHealthCareFacilityDTO hfDto = null;
        List<PAHealthCareFacilityDTO> hfList = pahfService.getByOrganization(IiConverter.convertToIi(poOrgId));
        if (hfList.size() > 0) {
            hfDto = hfList.get(0);
        } else {
            hfDto = new PAHealthCareFacilityDTO();
            hfDto.setIdentifier(StConverter.convertToSt("1234"));
            hfDto.setOrganizationIi(IiConverter.convertToIi(paOrg.getId()));
            hfDto = pahfService.create(hfDto);
        }

        // 9) now you have paOrganizationIi & paHealthcareFacilityIi and you can 
        //      create Participations and Studyprotocol   
        StudyParticipationDTO sp = new StudyParticipationDTO();
        sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        sp.setHealthcareFacilityIi(hfDto.getIi());
        sp.setIi(IiConverter.convertToIi((Long) null));
        sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP Identifier"));
        sp.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        sp.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2001")));
        sp.setStudyProtocolIi(spIi);
        sp = sPartService.create(sp);
        tab.setStudyParticipationId(IiConverter.convertToLong(sp.getIi()));
        
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIi(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(getRecStatus())));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(getRecStatusDate())));
        ssas.setStudyParticipationIi(sp.getIi());
        ssas = ssasService.createStudySiteAccrualStatus(ssas);
        
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
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
        enforceBusinessRules();
        
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) 
                ServletActionContext.getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("System error getting participating orgainzation data from session.");
            return SUCCESS;
        }
        List<StudySiteAccrualStatusDTO> currentStatus = ssasService
                .getCurrentStudySiteAccrualStatusByStudyParticipation(
                IiConverter.convertToIi(tab.getStudyParticipationId()));
        if (currentStatus.isEmpty()
            || !currentStatus.get(0).getStatusCode().getCode().equals(getRecStatus())
            || !currentStatus.get(0).getStatusDate().equals(PAUtil.dateStringToTimestamp(getRecStatusDate()))) {
              StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
              
              ssas.setIi(IiConverter.convertToIi((Long) null));
              ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(getRecStatus())));
              ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(getRecStatusDate())));
              ssas.setStudyParticipationIi(IiConverter.convertToIi(tab.getStudyParticipationId()));
              ssas = ssasService.createStudySiteAccrualStatus(ssas);
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        return ACT_FACILITY_SAVE;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        StudyParticipationDTO spDto = sPartService.get(IiConverter.convertToIi(cbValue));
        PAHealthCareFacilityDTO hfDto = pahfService.get(spDto.getHealthcareFacilityIi());
        Organization org = new Organization();
        org.setId(IiConverter.convertToLong(hfDto.getOrganizationIi()));
        editOrg = paoService.getOrganizationByIndetifers(org);
        List<StudySiteAccrualStatusDTO> statusList = 
                ssasService.getCurrentStudySiteAccrualStatusByStudyParticipation(spDto.getIi()); 
        if (!statusList.isEmpty()) {
            this.setRecStatus(statusList.get(0).getStatusCode().getCode());
            this.setRecStatusDate(TsConverter.convertToTimestamp(statusList.get(0).getStatusDate()).toString());
        }
        setNewParticipation(false);
        
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(org);
        tab.setPoHealthCareFacilityIi(null);
        tab.setPoOrganizationIi(null);
        tab.setNewParticipation(false);

        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return this.ACT_EDIT;
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
        return this.ACT_DELETE;
    }

    private void loadForm() throws Exception {
        organizationList = new ArrayList<OrganizationWebDTO>();
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIi);
        for (StudyParticipationDTO sp : spList) {
            List<StudySiteAccrualStatusDTO> ssasList = 
                ssasService.getCurrentStudySiteAccrualStatusByStudyParticipation(sp.getIi());
            PAHealthCareFacilityDTO hf = pahfService.get(sp.getHealthcareFacilityIi());
            Organization orgBo = new Organization();
            orgBo.setId(IiConverter.convertToLong(hf.getOrganizationIi()));
            orgBo = paoService.getOrganizationByIndetifers(orgBo);
            
            OrganizationWebDTO orgWebDTO = new OrganizationWebDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIi()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            if (ssasList.isEmpty()) {
                orgWebDTO.setRecruitmentStatus("unknown");
                orgWebDTO.setRecruitmentStatusDate("unknown");
            } else {
              orgWebDTO.setRecruitmentStatus(CdConverter.convertCdToString(ssasList.get(0).getStatusCode()));
              orgWebDTO.setRecruitmentStatusDate(
                      PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                              ssasList.get(0).getStatusDate()).toString()));
            }
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
     * This method is used to enforce the business rules which are form specific or
     * based on an interaction between services.
     */
    private void enforceBusinessRules() {
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
     * @return the recStatus
     */
    public String getRecStatus() {
        return recStatus;
    }

    /**
     * @param recStatus the recStatus to set
     */
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    /**
     * @return the recStatusDate
     */
    public String getRecStatusDate() {
        return recStatusDate;
    }

    /**
     * @param recStatusDate the recStatusDate to set
     */
    public void setRecStatusDate(String recStatusDate) {
        this.recStatusDate = PAUtil.normalizeDateString(recStatusDate);
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

}
