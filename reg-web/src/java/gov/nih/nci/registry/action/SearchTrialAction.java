package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.InterventionalStudyProtocolWebDTO;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.dto.StudyParticipationWebDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 *
 * @author Bala Nair
 *
 */
@SuppressWarnings({ "PMD" })
@Validation
public class SearchTrialAction extends ActionSupport {
    
    private List<StudyProtocolQueryDTO> records = null;
    
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    
    private Long studyProtocolId = null;


    /**  
     * @return res
     */ 
    public String execute() {
        return SUCCESS;
    }
    

    /**  
     * @return res
     */     
    public String showCriteria() {
        return "criteria";
    }
    
    /**  
     * @return res
     */
    public String query()  {
        try { 
            records = new ArrayList<StudyProtocolQueryDTO>();
            records = RegistryServiceLocator.getProtocolQueryService().getStudyProtocolByCriteria(criteria);       
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * 
     * @return records
     */
    public List<StudyProtocolQueryDTO>  getRecords() {
        return records;
    }

    /**
     * 
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }
    
    /**
     * 
     * @param criteria StudyProtocolQueryCriteria
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**  
     * @return res
     */
    public String view() {
        try {            
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().
                                removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId); 
                        
            InterventionalStudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().
                                                             getInterventionalStudyProtocol(studyProtocolIi);
            InterventionalStudyProtocolWebDTO trialWebDTO =
                                                          new InterventionalStudyProtocolWebDTO(protocolDTO);
            // put an entry in the session and store InterventionalStudyProtocolDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                                    Constants.TRIAL_SUMMARY, trialWebDTO);            
            
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().
                                removeAttribute(Constants.TRIAL_FUNDING_LIST);
            // query the study grants
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService().
                                                 getstudyResourceByStudyProtocol(studyProtocolIi);
            List <TrialFundingWebDTO> trialFundingList;
            if (!(isoList.isEmpty())) { 
                trialFundingList = new ArrayList<TrialFundingWebDTO>();                
                for (StudyResourcingDTO dto : isoList) {
                    trialFundingList.add(new TrialFundingWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.TRIAL_FUNDING_LIST, trialFundingList.get(0));
            }
            
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().
                                removeAttribute(Constants.TRIAL_OVERALL_STATUS);
            
            List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.
                                                        getStudyOverallStatusService().
                                                            getByStudyProtocol(studyProtocolIi);
            List <StudyOverallStatusWebDTO> overallStatusList;
            if (!(overallStatusISOList.isEmpty())) { 
                overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();                
                for (StudyOverallStatusDTO dto : overallStatusISOList) {
                    overallStatusList.add(new StudyOverallStatusWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.TRIAL_OVERALL_STATUS, overallStatusList.get(0));
            }
            
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().
                                removeAttribute(Constants.STUDY_PARTICIPATION);
            
            // query the study participation sites
            List<StudyParticipationDTO> studyParticipationISOList = 
                                       RegistryServiceLocator.getStudyParticipationService().
                                               getByStudyProtocol(studyProtocolIi);
            
            List <StudyParticipationWebDTO> studyParticipationList;
            if (!(studyParticipationISOList.isEmpty())) { 
                studyParticipationList = new ArrayList<StudyParticipationWebDTO>();                
                for (StudyParticipationDTO dto : studyParticipationISOList) {
                    studyParticipationList.add(new StudyParticipationWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.STUDY_PARTICIPATION, studyParticipationList.get(0));
                
            }
            
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().
                                removeAttribute(Constants.PROTOCOL_DOCUMENT);
            ServletActionContext.getRequest().getSession().
                                removeAttribute(Constants.IRB_APPROVAL);
            
            // query the trial documents
            List<DocumentDTO> documentISOList = 
                                       RegistryServiceLocator.getDocumentService().
                                               getDocumentsByStudyProtocol(studyProtocolIi);
            List <TrialDocumentWebDTO> documentList;
            if (!(documentISOList.isEmpty())) { 
                documentList = new ArrayList<TrialDocumentWebDTO>();                
                for (DocumentDTO dto : documentISOList) {
                    documentList.add(new TrialDocumentWebDTO(dto));
                }
                
                for (TrialDocumentWebDTO webdto : documentList) {
                    if (webdto.getTypeCode().equalsIgnoreCase(
                            DocumentTypeCode.Protocol_Document.getCode())) {
                        // put an entry in the session and store Protocol Document
                        ServletActionContext.getRequest().getSession().setAttribute(
                                                Constants.PROTOCOL_DOCUMENT, webdto);
                        
                    }
                    if (webdto.getTypeCode().equalsIgnoreCase(
                            DocumentTypeCode.IRB_Approval_Document.getCode())) {
                        // put an entry in the session and store IRB Approval Document
                        ServletActionContext.getRequest().getSession().setAttribute(
                                                Constants.IRB_APPROVAL, webdto);                        
                    }                    
                }
                
            }
 
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }        
    }


}