package gov.nih.nci.registry.action;


import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

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

/**
 * 
 * @author Vrushali
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength",
    "PMD.TooManyFields", "unchecked" })
public class AmendmentTrialAction extends ManageFileAction implements ServletResponseAware {
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(AmendmentTrialAction.class);
    private TrialDTO trialDTO;
    private String trialAction = null;
    private String studyProtocolId = null;
    private static String sessionTrialDTO = "trialDTO";
    private final TrialUtil trialUtil = new TrialUtil();
    
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
                ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
                setPageFrom("amendTrial");
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
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    public String review() {
        try {
            clearErrorsAndMessages();
            enforceBusinessRules();
            List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                TrialValidator.addSessionAttributes(trialDTO);
                trialUtil.populateRegulatoryList(trialDTO);
            return ERROR;
            }
            if (hasActionErrors()) {
                TrialValidator.addSessionAttributes(trialDTO);
                trialUtil.populateRegulatoryList(trialDTO);
                return ERROR;
            }            
            populateList(docDTOList);            
           trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
           trialDTO.setDocDtos(docDTOList);
           // get the document and put in list add the IndIde,FundingList 
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
           if (trialDTO.getXmlRequired()) {
              trialUtil.setOversgtInfo(trialDTO);
           }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
        LOG.info("Calling the review page...");
        return "review";    
    }
    
    /**
     * 
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        trialUtil.populateRegulatoryList(trialDTO);
        TrialValidator.addSessionAttributes(trialDTO);
        setDocumentsInSession(trialDTO);
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
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser()));
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            // updated only if the ctGovXmlRequired is true
            OrganizationDTO sponsorOrgDTO = null;
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            }
            StudySiteDTO leadOrgSiteIdDTO = util.convertToleadOrgSiteIdDTO(trialDTO);

            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studySiteContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO, null);
            Ii responsiblePartyContactIi = null;
            // updated only if the ctGovXmlRequired is true 
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
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
            }    
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            
            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, null));
            studyIdentifierDTOs.add(util.convertToCTEPStudySiteDTO(trialDTO, null));
            studyIdentifierDTOs.add(util.convertToDCPStudySiteDTO(trialDTO, null));
            // updated only if the ctGovXmlRequired is true 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = null;
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                studyRegAuthDTO = util.getStudyRegAuth(null, trialDTO);
            }
            amendId = PaRegistry.getTrialRegistrationService().
            amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs, documentDTOs, 
                    leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO, leadOrgSiteIdDTO, 
                    studyIdentifierDTOs, studyContactDTO, studySiteContactDTO, summary4orgDTO, 
                    summary4studyResourcingDTO, responsiblePartyContactIi, studyRegAuthDTO, 
                    BlConverter.convertToBl(Boolean.FALSE));  
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", amendId.getExtension());
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", amendId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            TrialValidator.addSessionAttributes(trialDTO);
            trialUtil.populateRegulatoryList(trialDTO);
            setDocumentsInSession(trialDTO);
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
        validateDocuments();
        //Only allow completing amendment submission of the pre-IRB approved study is the 
        //current trial status 'In-Review' is replaced with 'Approved'.
        if (PAUtil.isNotEmpty(trialDTO.getStatusCode()) && trialDTO.getStatusCode().equalsIgnoreCase("In Review")) {
            addActionError("To Amend Submission of pre-IRB approved study replace " 
              + " current trial status 'In-Review' with 'Approved'");
        }
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