package gov.nih.nci.registry.action;


import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Vrushali
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "ExcessiveClassLength", "PMD.TooManyFields" })
public class AmendmentTrialAction extends ActionSupport implements ServletResponseAware {
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(AmendmentTrialAction.class);
    private TrialDTO trialDTO;
    private File protocolDoc;
    private String protocolDocFileName;
    private File irbApproval;
    private String irbApprovalFileName;
    private File participatingSites = null;
    private String participatingSitesFileName = null;
    private File informedConsentDocument = null;
    private String informedConsentDocumentFileName = null;
    private File protocolHighlightDocument = null;
    private String protocolHighlightDocumentFileName = null;
    private File changeMemoDoc;
    private String changeMemoDocFileName = null;
    private String trialAction = null;
    private String studyProtocolId = null;
    private static String sessionTrialDTO = "trialDTO";


    /**
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }
    /**
     * 
     * @return res
     */
    public String view() {
        //clear the session
        TrialValidator.removeSessionAttributes();
        try {
                String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
                if (studyProtocolId == null) {
                    studyProtocolId = pId;
                }
                Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
                TrialUtil util = new TrialUtil();
                trialDTO = new TrialDTO();
                util.getTrialDTOFromDb(studyProtocolIi, trialDTO);
                TrialValidator.addSessionAttributes(trialDTO);

            LOG.info("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }
    /**
     * @return the trialDTO
     */
    public TrialDTO getTrialDTO() {
        return trialDTO;
    }
    /**
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(TrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }
    /**
     * Clears the session variables and redirect to search.
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        return "redirect_to_search";
    }
    /**
     * 
     * @return s
     */

    public String review() {
        try {
            
            clearErrorsAndMessages();
            enforceBusinessRules();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                TrialValidator.addSessionAttributes(trialDTO);
            return ERROR;
            }
            if (hasActionErrors()) {
                TrialValidator.addSessionAttributes(trialDTO);
                return ERROR;
            }
         
            List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
           trialDTO.setDocDtos(docDTOList);
           //get the document and put in list
           //add the IndIde,FundingList
           List<TrialIndIdeDTO> indList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest()
           .getSession().getAttribute(Constants.INDIDE_LIST);
           if (indList != null) {
               trialDTO.setIndIdeDtos(indList);
           }
           
           List<TrialFundingWebDTO> grantList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
           .getSession().getAttribute(Constants.GRANT_LIST);
           if (grantList != null) {
               trialDTO.setFundingDtos(grantList);
           }

        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
        LOG.info("Calling the review page...");
        return "review";    
    }

    /**
     * @return
     * @throws IOException
     */
    private List<TrialDocumentWebDTO> addDocDTOToList() throws IOException {
        TrialUtil util = new TrialUtil();
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        if (PAUtil.isNotEmpty(protocolDocFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(), 
                    protocolDocFileName, protocolDoc));
        }
        if (PAUtil.isNotEmpty(irbApprovalFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(), 
                        irbApprovalFileName, irbApproval));
        }
        if (PAUtil.isNotEmpty(changeMemoDocFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getCode(), 
                       changeMemoDocFileName, changeMemoDoc));  
        }
        if (PAUtil.isNotEmpty(informedConsentDocumentFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode(),
                        informedConsentDocumentFileName, informedConsentDocument));
        }
        if (PAUtil.isNotEmpty(participatingSitesFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PARTICIPATING_SITES.getCode(),
                        participatingSitesFileName, participatingSites));
         }
         if (PAUtil.isNotEmpty(protocolHighlightDocumentFileName)) {
             docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT.getCode(), 
                     protocolHighlightDocumentFileName, protocolHighlightDocument));  
         }
        return docDTOList;
    }
    /**
     * 
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        TrialValidator.addSessionAttributes(trialDTO);
        return "edit";
    }
    /**
     * 
     * @return s
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    public String amend() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        if (trialDTO == null) {
           return ERROR; 
        }
        TrialUtil util = new TrialUtil();
        Ii amendId = null;
        try {
            StudyProtocolDTO studyProtocolDTO = util.convertToStudyProtocolDTOForAmendment(trialDTO);
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            OrganizationDTO sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            StudySiteDTO leadOrgSiteIdDTO = util.convertToStudySiteDTO(trialDTO);
            StudySiteDTO nctIdentifierSiteIdDTO = util.convertToNCTStudySiteDTO(trialDTO);
            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studySiteContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
            Ii responsiblePartyContactIi = null;
            if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                studyContactDTO = util.convertToStudyContactDTO(trialDTO);
            } else {
                studySiteContactDTO = util.convertToStudySiteContactDTO(trialDTO);
                if (trialDTO.getResponsiblePersonName() != null && !trialDTO.getResponsiblePersonName().equals("")) {
                  responsiblePartyContactIi = IiConverter.convertToPoPersonIi(
                              trialDTO.getResponsiblePersonIdentifier());
                }
                if (trialDTO.getResponsibleGenericContactName() != null 
                          && !trialDTO.getResponsibleGenericContactName().equals("")) {
                    responsiblePartyContactIi = IiConverter.
                        convertToPoOrganizationalContactIi(trialDTO.getResponsiblePersonIdentifier());
                }
            }
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            amendId = RegistryServiceLocator.getTrialRegistrationService().
            amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs, documentDTOs, 
                    leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO, leadOrgSiteIdDTO, 
                    nctIdentifierSiteIdDTO, studyContactDTO, studySiteContactDTO, summary4orgDTO, 
                    summary4studyResourcingDTO, responsiblePartyContactIi);  
            TrialValidator.removeSessionAttributes();
            //send mail
            RegistryServiceLocator.getMailManagerService().sendAmendNotificationMail(amendId);
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", amendId.getExtension());
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", amendId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            TrialValidator.addSessionAttributes(trialDTO);
            return ERROR;
        }
        setTrialAction("amend");
     return "redirect_to_search";   
    }
    /**
     * validate the submit trial form elements.
     * @throws PAException 
     */
    private void enforceBusinessRules() throws PAException {
        if (PAUtil.isEmpty(trialDTO.getAmendmentDate())) {
            addFieldError("trialDTO.amendmentDate",
                    getText("error.submit.amendmentDate"));
        }
        if (!RegistryUtil.isValidDate(trialDTO.getAmendmentDate())) {
                    addFieldError("trialDTO.amendmentDate", 
                            getText("error.submit.invalidDate"));
        } else {
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(PAUtil.dateStringToTimestamp(trialDTO.getAmendmentDate()))) {
                addFieldError("trialDTO.amendmentDate", 
                    getText("error.submit.invalidAmendDate"));                
            }
        }
        TrialValidator validator = new TrialValidator();
        Map<String, String> err = new HashMap<String, String>();
        err = validator.validateTrialDTO(trialDTO);
        addErrors(err);
        // validate trial status and dates specific for amendment
        if (PAUtil.isNotEmpty(trialDTO.getStatusCode())
                && RegistryUtil.isValidDate(trialDTO.getStatusDate())
                && RegistryUtil.isValidDate(trialDTO.getCompletionDate())
                && RegistryUtil.isValidDate(trialDTO.getStartDate())
                && validator.isTrialStatusOrDateChanged(trialDTO)) {
            Collection<String> errDate = validator.enforceBusinessRulesForDates(trialDTO);
            if (!errDate.isEmpty()) {
                for (String msg : errDate) {
                    addActionError(msg);
                }
            }
        }
        //validate the docs
        validateAmendmentDocuments();
        //Only allow completing amendment submission of the pre-IRB approved study is the 
        //current trial status 'In-Review' is replaced with 'Approved'.
        if (trialDTO.getStatusCode().equalsIgnoreCase("In Review")) {
            addActionError("To Amend Submission of pre-IRB approved study replace " 
              + " current trial status 'In-Review' with 'Approved'");
        }
    }
    /**
     * @param err
     */
    private void addErrors(Map<String, String> err) {
        if (!err.isEmpty()) {
            for (String msg : err.keySet()) {
                addFieldError(msg, err.get(msg));
            }
        }
    }
    /**
     * @return the protocolDoc
     */
    public File getProtocolDoc() {
        return protocolDoc;
    }
    /**
     * @param protocolDoc the protocolDoc to set
     */
    public void setProtocolDoc(File protocolDoc) {
        this.protocolDoc = protocolDoc;
    }
    /**
     * @return the protocolDocFileName
     */
    public String getProtocolDocFileName() {
        return protocolDocFileName;
    }
    /**
     * @param protocolDocFileName the protocolDocFileName to set
     */
    public void setProtocolDocFileName(String protocolDocFileName) {
        this.protocolDocFileName = protocolDocFileName;
    }
    /**
     * @return the irbApproval
     */
    public File getIrbApproval() {
        return irbApproval;
    }
    /**
     * @param irbApproval the irbApproval to set
     */
    public void setIrbApproval(File irbApproval) {
        this.irbApproval = irbApproval;
    }
    /**
     * @return the irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
        return irbApprovalFileName;
    }
    /**
     * @param irbApprovalFileName the irbApprovalFileName to set
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalFileName = irbApprovalFileName;
    }
    /**
     * @return the participatingSites
     */
    public File getParticipatingSites() {
        return participatingSites;
    }
    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(File participatingSites) {
        this.participatingSites = participatingSites;
    }
    /**
     * @return the participatingSitesFileName
     */
    public String getParticipatingSitesFileName() {
        return participatingSitesFileName;
    }
    /**
     * @param participatingSitesFileName the participatingSitesFileName to set
     */
    public void setParticipatingSitesFileName(String participatingSitesFileName) {
        this.participatingSitesFileName = participatingSitesFileName;
    }
    /**
     * @return the informedConsentDocument
     */
    public File getInformedConsentDocument() {
        return informedConsentDocument;
    }
    /**
     * @param informedConsentDocument the informedConsentDocument to set
     */
    public void setInformedConsentDocument(File informedConsentDocument) {
        this.informedConsentDocument = informedConsentDocument;
    }
    /**
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocumentFileName;
    }
    /**
     * @param informedConsentDocumentFileName the informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(
            String informedConsentDocumentFileName) {
        this.informedConsentDocumentFileName = informedConsentDocumentFileName;
    }

    /**
     * @return the protocolHighlightDocument
     */
    public File getProtocolHighlightDocument() {
        return protocolHighlightDocument;
    }

    /**
     * @param protocolHighlightDocument the protocolHighlightDocument to set
     */
    public void setProtocolHighlightDocument(File protocolHighlightDocument) {
        this.protocolHighlightDocument = protocolHighlightDocument;
    }

    /**
     * @return the protocolHighlightDocumentFileName
     */
    public String getProtocolHighlightDocumentFileName() {
        return protocolHighlightDocumentFileName;
    }

    /**
     * @param protocolHighlightDocumentFileName the protocolHighlightDocumentFileName to set
     */
    public void setProtocolHighlightDocumentFileName(
            String protocolHighlightDocumentFileName) {
        this.protocolHighlightDocumentFileName = protocolHighlightDocumentFileName;
    }

    /**
     * @return the changeMemoDoc
     */
    public File getChangeMemoDoc() {
        return changeMemoDoc;
    }

    /**
     * @param changeMemoDoc the changeMemoDoc to set
     */
    public void setChangeMemoDoc(File changeMemoDoc) {
        this.changeMemoDoc = changeMemoDoc;
    }
    /**
     * @return the changeMemoDocFileName
     */
    public String getChangeMemoDocFileName() {
        return changeMemoDocFileName;
    }
    /**
     * @param changeMemoDocFileName the changeMemoDocFileName to set
     */
    public void setChangeMemoDocFileName(String changeMemoDocFileName) {
        this.changeMemoDocFileName = changeMemoDocFileName;
    }
    private void validateAmendmentDocuments() {
        TrialValidator  validator = new TrialValidator();
        Map<String, String> err = new HashMap<String, String>();
        err = validator.validateDcoument(protocolDocFileName, protocolDoc, "trialDTO.protocolDocFileName",
                "error.submit.protocolDocument");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(irbApprovalFileName, irbApproval, "trialDTO.irbApprovalFileName",
                "error.submit.irbApproval");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(participatingSitesFileName, participatingSites,
                "trialDTO.participatingSitesFileName", "");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(informedConsentDocumentFileName, informedConsentDocument, 
                "trialDTO.informedConsentDocumentFileName", "");
        //protocol Highlighted doc
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(protocolHighlightDocumentFileName, protocolHighlightDocument,
                "trialDTO.protocolHighlightDocumentFileName", "");
        addErrors(err);
        err = new HashMap<String, String>();
        err = validator.validateDcoument(changeMemoDocFileName, changeMemoDoc, "trialDTO.changeMemoDocFileName",
                "error.submit.changeMemo");
        addErrors(err);
    }
    /**
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }
    /**
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }
    /**
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }
    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

}