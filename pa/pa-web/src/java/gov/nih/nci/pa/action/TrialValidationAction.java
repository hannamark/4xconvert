package gov.nih.nci.pa.action;
      
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
//import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.dto.ContactWebDTO;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolWebDTO;
import gov.nih.nci.pa.dto.StudyParticipationWebDTO;
import gov.nih.nci.pa.dto.StudyResourcingWebDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
      
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
      
/**
* @author Hong Gao
*
*/
public class TrialValidationAction extends ActionSupport {

          private static final String QUERY_RESULT = "query";
          private static final Logger LOG = Logger.getLogger(TrialValidationAction.class);
          private StudyProtocolWebDTO studyProtocolWebDTO = new StudyProtocolWebDTO();
          private StudyParticipationWebDTO participationWebDTO = new StudyParticipationWebDTO();
          private StudyResourcingWebDTO  studyResourcingWebDTO = new StudyResourcingWebDTO();
          private OrganizationWebDTO organizationWebDTO = new OrganizationWebDTO();
          private ContactWebDTO contactWebDTO = new ContactWebDTO();
          private Long studyProtocolId;
          private String validationAction = "update";

//          leadOrganization;
//          principalInvestigator;
//          sponsorResParty;
        
          /**
           * @return res
           */
          public String query() {
              try {
                  Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
                  ServletActionContext.getRequest().getSession().setAttribute(
                          Constants.STUDY_PROTOCOL_II, IiConverter.convertToString(studyProtocolIi));
                  StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
                  ServletActionContext.getRequest().getSession().setAttribute("validateprotocolDTO", protocolDTO);
                  studyProtocolWebDTO = new StudyProtocolWebDTO(protocolDTO);
                  // query the study participation sites
                  List<StudyParticipationDTO> studyParticipationISOList = PaRegistry.getStudyParticipationService()
                          .getByStudyProtocol(studyProtocolIi);
                  ServletActionContext.getRequest().getSession().setAttribute("validatestudyPartiDTO", 
                          studyParticipationISOList.get(0)); 
                  if (!(studyParticipationISOList.isEmpty())) {
                      participationWebDTO = new StudyParticipationWebDTO(studyParticipationISOList.get(0));
                  }   
                  //getsummary4ReportedResource
                  StudyResourcingDTO resourcingDTO = PaRegistry.getStudyResourcingService()
                          .getsummary4ReportedResource(studyProtocolIi);
                  // get the organzation name
                  if (resourcingDTO != null) {
                      Organization o = new CorrelationUtils().
                          getPAOrganizationByIndetifers(Long.valueOf(resourcingDTO.
                                  getOrganizationIdentifier().getExtension()), null);
                      ServletActionContext.getRequest().setAttribute("summaryFourSponsorName", o.getName());  
                 
                  organizationWebDTO.setId(o.getIdentifier());
                  organizationWebDTO.setName(o.getName().toString());
                  }
                  ServletActionContext.getRequest().getSession().setAttribute("validateresourcingDTO", resourcingDTO);
                  studyResourcingWebDTO = new StudyResourcingWebDTO(resourcingDTO);
                  
              } catch (Exception e) {
                  // e.printStackTrace();
                  LOG.error("Exception occured while querying trial " + e);
              }
              return QUERY_RESULT;
          }
          
          /**
           * @return res
           */
          public String update() {
              try {
                  Ii studyProtocolIi = IiConverter.convertToIi((String) ServletActionContext.getRequest().getSession()
                          .getAttribute(Constants.STUDY_PROTOCOL_II));
                  if (studyProtocolIi == null) {
                      String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
                      studyProtocolIi = IiConverter.convertToIi(pId);
                  }
                  StudyProtocolDTO protocolDTO =  (StudyProtocolDTO) ServletActionContext.getRequest().getSession()
                      .getAttribute("validateprotocolDTO"); 
                  StudyParticipationDTO studyParticipationDTO = (StudyParticipationDTO) ServletActionContext
                      .getRequest().getSession().getAttribute("validatestudyPartiDTO"); 
                  StudyResourcingDTO resourcingDTO = (StudyResourcingDTO) ServletActionContext
                      .getRequest().getSession().getAttribute("validateresourcingDTO");
                  
                  protocolDTO.setOfficialTitle(StConverter.convertToSt(studyProtocolWebDTO.getTrialTitle()));
                  protocolDTO.setPhaseCode(CdConverter.convertStringToCd(studyProtocolWebDTO.getTrialPhase()));
                  protocolDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(studyProtocolWebDTO
                          .getPrimaryPurposeCode()));
                  protocolDTO.setStudyProtocolType(StConverter.convertToSt(studyProtocolWebDTO.getStudyProtocolType()));
                  
                  studyParticipationDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(participationWebDTO
                          .getLocalProtocolIdentifier()));
                  
                  resourcingDTO.setTypeCode(CdConverter.convertStringToCd(studyResourcingWebDTO
                          .getSummaryFourFundingCategoryCode()));
                  PaRegistry.getStudyProtocolService().updateStudyProtocol(protocolDTO);
                  PaRegistry.getStudyParticipationService().update(studyParticipationDTO);
                  PaRegistry.getStudyResourcingService().updateStudyResourcing(resourcingDTO);
                  //updateDocumentWorkFlowStatus 
                  String actionName = studyProtocolWebDTO.getActionName();
                  if (PAUtil.isNotEmpty(actionName)) {
                      updateDocumentWorkFlowStatus();
                  }
                 
                  query();
                  ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
                  ServletActionContext.getRequest().getSession().removeAttribute("validateprotocolDTO");
                  ServletActionContext.getRequest().getSession().removeAttribute("validatestudyPartiDTO");
                  ServletActionContext.getRequest().getSession().removeAttribute("validateresourcingDTO");
              } catch (PAException e) {
                  addActionError(e.getLocalizedMessage());
                  return ERROR;
              }
              if (PAUtil.isEmpty(studyProtocolWebDTO.getActionName())) {
                  return QUERY_RESULT;
              } else {
                  return "redirect_to_search";
              }
          }
          
          /**
           * updateDocumentWorkFlowStatus.
           */
          public void updateDocumentWorkFlowStatus() {
              try {
                  Ii studyProtocolIi = IiConverter.convertToIi((String) ServletActionContext.getRequest().getSession()
                          .getAttribute(Constants.STUDY_PROTOCOL_II));
                  if (studyProtocolIi == null) {
                      String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
                      studyProtocolIi = IiConverter.convertToIi(pId);
                  }
                  List<DocumentWorkflowStatusDTO> docWfsDTOList = PaRegistry.getDocumentWorkflowStatusService()
                      .getByStudyProtocol(studyProtocolIi);
                  DocumentWorkflowStatusDTO documentWorkflowStatusDTO = null;
                  if (!(docWfsDTOList.isEmpty())) {
                      documentWorkflowStatusDTO = docWfsDTOList.get(0);
                  }  
                  String actionName = studyProtocolWebDTO.getActionName();
                  if (!PAUtil.isEmpty(actionName) && actionName.equalsIgnoreCase("Accept")) {
                      documentWorkflowStatusDTO.setStatusCode(CdConverter.convertToCd(
                              DocumentWorkflowStatusCode.ACCEPTED));
                      documentWorkflowStatusDTO.setStatusDateRange(TsConverter.convertToTs(
                              new Timestamp(new Date().getTime())));
                  } else {
                      documentWorkflowStatusDTO.setStatusCode(CdConverter.convertToCd(
                              DocumentWorkflowStatusCode.REJECTED));
                      documentWorkflowStatusDTO.setStatusDateRange(TsConverter.convertToTs(
                              new Timestamp(new Date().getTime())));
                  } 
                  PaRegistry.getDocumentWorkflowStatusService().update(documentWorkflowStatusDTO);  
              } catch (PAException e) {
                  addActionError(e.getLocalizedMessage());
              }
          }
          
          
        /**
         * @return the participationWebDTO
         */
        public StudyParticipationWebDTO getParticipationWebDTO() {
            return participationWebDTO;
        }


        /**
         * @param participationWebDTO the participationWebDTO to set
         */
        public void setParticipationWebDTO(StudyParticipationWebDTO participationWebDTO) {
            this.participationWebDTO = participationWebDTO;
        }

        
        /**
         * @return the studyProtocolId
         */
        public Long getStudyProtocolId() {
            return studyProtocolId;
        }


        /**
         * @param studyProtocolId the studyProtocolId to set
         */
        public void setStudyProtocolId(Long studyProtocolId) {
            this.studyProtocolId = studyProtocolId;
        }


        /**
         * @return the studyProtocolWebDTO
         */
        public StudyProtocolWebDTO getStudyProtocolWebDTO() {
            return studyProtocolWebDTO;
        }


        /**
         * @param studyProtocolWebDTO the studyProtocolWebDTO to set
         */
        public void setStudyProtocolWebDTO(StudyProtocolWebDTO studyProtocolWebDTO) {
            this.studyProtocolWebDTO = studyProtocolWebDTO;
        }


        /**
         * @return the studyResourcingWebDTO
         */
        public StudyResourcingWebDTO getStudyResourcingWebDTO() {
            return studyResourcingWebDTO;
        }


        /**
         * @param studyResourcingWebDTO the studyResourcingWebDTO to set
         */
        public void setStudyResourcingWebDTO(StudyResourcingWebDTO studyResourcingWebDTO) {
            this.studyResourcingWebDTO = studyResourcingWebDTO;
        }
        
        /**
         * @return the validationAction
         */
        public String getValidationAction() {
            return validationAction;
        }

        /**
         * @param validationAction the validationAction to set
         */
        public void setValidationAction(String validationAction) {
            this.validationAction = validationAction;
        }

        /**
         * @return the organizationWebDTO
         */
        public OrganizationWebDTO getOrganizationWebDTO() {
            return organizationWebDTO;
        }

        /**
         * @param organizationWebDTO the organizationWebDTO to set
         */
        public void setOrganizationWebDTO(OrganizationWebDTO organizationWebDTO) {
            this.organizationWebDTO = organizationWebDTO;
        }

        /**
         * @return the contactWebDTO
         */
        public ContactWebDTO getContactWebDTO() {
            return contactWebDTO;
        }

        /**
         * @param contactWebDTO the contactWebDTO to set
         */
        public void setContactWebDTO(ContactWebDTO contactWebDTO) {
            this.contactWebDTO = contactWebDTO;
        }

      }
