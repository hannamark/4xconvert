package gov.nih.nci.registry.action;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.InterventionalStudyProtocolWebDTO;
import gov.nih.nci.registry.util.Constants;

/**
 * 
 * @author Bala Nair
 * 
 */
@Validation
public class SubmitTrialAction extends ActionSupport {
    private static final String VIEW_TRIAL = "view";
    private static final Logger LOG  = Logger.getLogger(SubmitTrialAction.class);
    private List<TrialFundingWebDTO> trialFundingList;
    private TrialFundingWebDTO trialFundingWebDTO = new TrialFundingWebDTO();
    private Long cbValue;
    private String page;
    /**
     * create protocol.
     * @return String
     */
    public String create() {
        try {
            InterventionalStudyProtocolDTO protocolDTO = new InterventionalStudyProtocolDTO();
            String phaseCode = ServletActionContext.getRequest().getParameter("trialPhase");
            String officialTitle = ServletActionContext.getRequest().getParameter("trialTitle");
            String startDate = ServletActionContext.getRequest().getParameter("startDate");
            String completionDate = ServletActionContext.getRequest().getParameter("completionDate");
            String startDateType = ServletActionContext.getRequest().getParameter("startDateType");
            String completionDateType = ServletActionContext.getRequest().getParameter("completionDateType");
            
            //create Study Protocol
            protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(phaseCode)));
            protocolDTO.setOfficialTitle(StConverter.convertToSt(officialTitle));
            protocolDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(startDate)));
            protocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(
                                    PAUtil.dateStringToTimestamp(completionDate)));
            protocolDTO.setStartDateTypeCode(CdConverter.convertToCd(
                                    ActualAnticipatedTypeCode.getByCode(startDateType)));
            protocolDTO.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(completionDateType)));
            Ii studyProtocolIi = RegistryServiceLocator.getStudyProtocolService()
                                    .createInterventionalStudyProtocol(protocolDTO);
            LOG.info("Trial is registered with ID: " + IiConverter.convertToString(studyProtocolIi));
            
            ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.STUDY_PROTOCOL_II, IiConverter.convertToString(studyProtocolIi));
            // create Study Participation record
            //StudyParticipationDTO studyPartcipationDTO = new StudyParticipationDTO();
            //studyPartcipationDTO.setStudyProtocolIi(studyProtocolIi);
            //studyPartcipationDTO.setLocalStudyProtocolIdentifier(
            //                                            StConverter.convertToSt(leadOrgIdentifier));
            //RegistryServiceLocator.getStudyParticipationService().create(studyPartcipationDTO);
            
            //create study overall status
            createStudyStatus(studyProtocolIi);
            
            // create the Study Grants
            createStudyResource(studyProtocolIi);
            query();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Exception occured while submitting trial: " + e);
        }
        return VIEW_TRIAL;
    }

    /**
     * @param studyProtocolIi
     */
    private void createStudyResource(Ii studyProtocolIi) {
        // create the Study Grants
        try {
            String fundingMechanism = ServletActionContext.getRequest().getParameter("fundingMechanism");
            String nihInstituteCode = ServletActionContext.getRequest().getParameter("instituteCode");
            String serialNumber = ServletActionContext.getRequest().getParameter("serialNumber");
            String divPrgCode = ServletActionContext.getRequest().getParameter("divPrgCode");
            
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            
    
            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
            studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(fundingMechanism));
            studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                                                            MonitorCode.getByCode(divPrgCode)));
            studyResoureDTO.setNihInstitutionCode(CdConverter.convertStringToCd(nihInstituteCode));
            studyResoureDTO.setSerialNumber(IntConverter.convertToInt(serialNumber));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            RegistryServiceLocator.getStudyResourcingService().createStudyResourcing(studyResoureDTO);
        } catch (PAException pae) {
            LOG.error("Exception occured while creating study resource: " + pae);
        }
    }

    /**
     * @param studyProtocolIi
     */
    private void createStudyStatus(Ii studyProtocolIi)  {
        //create study overall status
        try {

            String trialStatus = ServletActionContext.getRequest().getParameter("trialStatus");
            String statusDate = ServletActionContext.getRequest().getParameter("statusDate");
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
            overallStatusDTO.setStudyProtocolIi(studyProtocolIi);
            overallStatusDTO.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialStatus)));
            overallStatusDTO.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(statusDate)));
            RegistryServiceLocator.getStudyOverallStatusService().create(overallStatusDTO);
        } catch (PAException pae) {
            LOG.error("Exception occured while creating study overall status: " + pae);
        }
    }
    
    /**
     * query the created protocol and all related associations.
     * @return String
     */
    public String query() {
        try {
            InterventionalStudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().
                                                             getInterventionalStudyProtocol(IiConverter.convertToIi(
                                                               (String) ServletActionContext.getRequest().getSession().
                                                               
                                                               getAttribute(Constants.STUDY_PROTOCOL_II)));
            InterventionalStudyProtocolWebDTO protocolWebDTO =
                                                          new InterventionalStudyProtocolWebDTO(protocolDTO);
            // put an entry in the session and store InterventionalStudyProtocolDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                                    Constants.TRIAL_SUMMARY, protocolWebDTO);
            
            // query the study grants
            Ii studyProtocolIi = IiConverter.convertToIi(
                                                (String) ServletActionContext.getRequest().getSession().
                                                      getAttribute(Constants.STUDY_PROTOCOL_II));  
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService().
                                                 getstudyResourceByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) { 
                LOG.info("Trial funding list not empty");
                trialFundingList = new ArrayList<TrialFundingWebDTO>();                
                for (StudyResourcingDTO dto : isoList) {
                    trialFundingList.add(new TrialFundingWebDTO(dto));
                    LOG.info("Trial funding added to the list");
                }
            }
            
            // put an entry in the session and store TrialFunding list 
            ServletActionContext.getRequest().getSession().setAttribute(
                                    Constants.TRIAL_FUNDING_LIST, trialFundingList);

            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Exception occured while querying trial " + e);
        }
        return VIEW_TRIAL;
    }
    
    
    
    /**
     * @return trialFundingList
     */
    public List<TrialFundingWebDTO> getTrialFundingList() {
        return trialFundingList;
    }

    /**
     * @param trialFundingList trialFundingList
     */
    public void setTrialFundingList(List<TrialFundingWebDTO> trialFundingList) {
        this.trialFundingList = trialFundingList;
    }
    
    /**
     * @return trialFundingWebDTO
     */
    public TrialFundingWebDTO getTrialFundingWebDTO() {
        return trialFundingWebDTO;
    }

    /**
     * @param trialFundingWebDTO trialFundingWebDTO
     */
    public void setTrialFundingWebDTO(TrialFundingWebDTO trialFundingWebDTO) {
        this.trialFundingWebDTO = trialFundingWebDTO;
    }
    
    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }
}
