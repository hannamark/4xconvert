/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;
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

    private StudyProtocolServiceRemote sProService;
    private StudyParticipationServiceRemote sPartService;
    private StudySiteAccrualStatusServiceRemote ssasService;
    private PAHealthCareFacilityServiceRemote pahfService;
    private PAOrganizationServiceRemote paoService;
    private List<CountryRegAuthorityDTO> countryRegDTO;
    private Ii spIi;
    private List<OrganizationWebDTO> organizationList = null; 
    private OrganizationDTO selectedOrgDTO = null;
    private static final int THREE = 3;    
    private static final String DISPLAYJSP = "displayJsp";

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
        clearErrorsAndMessages();
        enforceBusinessRules();
        if (hasActionErrors()) {
            return Action.SUCCESS;
        }
        if (!hasActionErrors()) {
//            addActionError("Create succeeded.");
        }
        loadForm();
        return Action.SUCCESS;
    }


    /**  
     * @return result
     * @throws Exception exception
     */
    public String createTest() throws Exception {
        clearErrorsAndMessages();
        enforceBusinessRules();
        
        StudyParticipationDTO sp = new StudyParticipationDTO();
        sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        sp.setHealthcareFacilityIi(IiConverter.convertToIi(1L));
        sp.setIi(IiConverter.convertToIi((Long) null));
        sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP Identifier"));
        sp.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        sp.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2001")));
        sp.setStudyProtocolIi(spIi);
        sp = sPartService.createStudyParticipation(sp);
        
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIi(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ENROLLING_BY_INVITATION));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("10/1/2008")));
        ssas.setStudyParticipationIi(sp.getIi());
        ssas = ssasService.createStudySiteAccrualStatus(ssas);
        
        addActionError("createTest() completed");
        loadForm();
        return Action.SUCCESS;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String updateTest() throws Exception {
        clearErrorsAndMessages();
        enforceBusinessRules();
        
        List<StudyParticipationDTO> spList = sPartService.getStudyParticipationByStudyProtocol(spIi);
        if (!spList.isEmpty()) {
           StudyParticipationDTO sp = spList.get(spList.size() - 1);
           StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
           ssas.setIi(IiConverter.convertToIi((Long) null));
           ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.WITHDRAWN));
           ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("9/9/2009")));
           ssas.setStudyParticipationIi(sp.getIi());
           ssas = ssasService.createStudySiteAccrualStatus(ssas);
        }
        
        addActionError("updateTest() completed");
        loadForm();
        return Action.SUCCESS;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String deleteTest() throws Exception {
        clearErrorsAndMessages();
        
        List<StudyParticipationDTO> spList = sPartService.getStudyParticipationByStudyProtocol(spIi);
        if (spList.size() > 1) {
           StudyParticipationDTO sp = spList.get(spList.size() - 1);
           sPartService.deleteStudyParticipation(sp.getIi());
        }
        
        addActionError("deleteTest() completed");
        loadForm();
        return Action.SUCCESS;
    }

    private void loadForm() throws Exception {
        organizationList = new ArrayList<OrganizationWebDTO>();
        List<StudyParticipationDTO> spList = sPartService.getStudyParticipationByStudyProtocol(spIi);
        for (StudyParticipationDTO sp : spList) {
            List<StudySiteAccrualStatusDTO> ssasList = 
                ssasService.getCurrentStudySiteAccrualStatusByStudyParticipation(sp.getIi());
            HealthCareFacilityDTO hf = pahfService.getHealthCareFacility(sp.getHealthcareFacilityIi());
            Organization orgBo = new Organization();
            orgBo.setId(IiConverter.convertToLong(hf.getOrganizationIi()));
            orgBo = paoService.getOrganizationByIndetifers(orgBo);
            
            OrganizationWebDTO orgWebDTO = new OrganizationWebDTO();
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

}
