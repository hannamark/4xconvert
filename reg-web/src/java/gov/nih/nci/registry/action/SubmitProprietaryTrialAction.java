/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength",
    "PMD.TooManyMethods"  })
public class SubmitProprietaryTrialAction extends ActionSupport implements
        ServletResponseAware {
    private static final String SESSION_TRIAL_DTO = "trialDTO";
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(SubmitProprietaryTrialAction.class);
    private ProprietaryTrialDTO trialDTO;
    private File protocolDoc;
    private String protocolDocFileName;
    private File otherDocument;
    private String otherDocumentFileName;
    private String trialAction = "submit";
    private String selectedTrialType = "no";
    private final TrialUtil  util = new TrialUtil();
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
     * @return String st
     */
    public String selectTypeOfTrial() {
        String strDesclaimer = (String) ServletActionContext.getRequest().getSession().getAttribute("disclaimer");
        if (strDesclaimer == null || !strDesclaimer.equals("accept")) {
            return "show_Disclaimer_Page";
        }
        return SUCCESS;
    }
    /**
     * 
     * @return st
     */
    public String execute() {
        TrialValidator.removeSessionAttributes();
        trialDTO = new ProprietaryTrialDTO();
        trialDTO.setPropritaryTrialIndicator(CommonsConstant.YES);
        trialDTO.setTrialType("Interventional");
     return SUCCESS;   
    }
    /**
     * 
     * @return st
     */
    public String review() {
        clearErrorsAndMessages();
        enforceBusinessRules();
        if (hasFieldErrors()) {
            ServletActionContext.getRequest().setAttribute(
                    "failureMessage" , "The form has errors and could not be submitted, "
                    + "please check the fields highlighted below");
        return ERROR;
        }
        if (hasActionErrors()) {
            return ERROR;
        }
        try {
            trialDTO.setDocDtos(addDocDTOToList());
        } catch (IOException e) {
                addActionError(e.getMessage());
                return ERROR;
        }
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.INDIDE_LIST);
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.GRANT_LIST);
        ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_DTO, trialDTO);
        return "review";
    }

    private void enforceBusinessRules() {
        InvalidValue[] invalidValues = null;
        ClassValidator<ProprietaryTrialDTO> classValidator = new ClassValidator(trialDTO.getClass());
        invalidValues = classValidator.getInvalidValues(trialDTO);
        for (int i = 0; i < invalidValues.length; i++) {
            if (PAUtil.isNotEmpty(trialDTO.getNctIdentifier())) {
                if (!invalidValues[i].getPropertyName().equalsIgnoreCase("phaseCode")
                    && !invalidValues[i].getPropertyName().equalsIgnoreCase("primaryPurposeCode")) {
                //if the nct Number is present ignore the phase code and primary purpose codes
                addFieldError("trialDTO." + invalidValues[i].getPropertyName(), 
                            getText(invalidValues[i].getMessage().trim()));
                }
            } else {
                addFieldError("trialDTO." + invalidValues[i].getPropertyName(), 
                        getText(invalidValues[i].getMessage().trim()));
            }
        }
            
        if (PAUtil.isEmpty(trialDTO.getNctIdentifier()) && PAUtil.isEmpty(protocolDocFileName)) {
            addFieldError("trialDTO.nctIdentifier", "Provide either NCT Number or Protocol Trial Template.\n");
            addFieldError("trialDTO.protocolDocFileName", "Provide either NCT Number or Protocol Trial Template.\n");
        }
        TrialValidator validator = new TrialValidator();
        Map<String, String> errMap = new HashMap<String, String>();
        if (PAUtil.isNotEmpty(protocolDocFileName)) {
            errMap = validator.validateDcoument(protocolDocFileName, protocolDoc, "trialDTO.protocolDocFileName"
                    , "");
            addErrors(errMap);
        }
        if (PAUtil.isNotEmpty(otherDocumentFileName)) {
            errMap = new HashMap<String, String>();
            errMap = validator.validateDcoument(otherDocumentFileName, otherDocument, "trialDTO.otherDocumentFileName"
                    , "");
            addErrors(errMap);
        }
        PAServiceUtils paServiceUtils = new PAServiceUtils();
        StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(trialDTO);
        StudySiteDTO studySiteDTO = getSubmittingStudySiteDTO();
        String errMsg = paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO);
        if (PAUtil.isNotEmpty(errMsg)) {
            addActionError(errMsg);    
        }
        
    }

    /**
     * @return the trialDTO
     */
    public ProprietaryTrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(ProprietaryTrialDTO trialDTO) {
        this.trialDTO = trialDTO;
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
     * @return the otherDocument
     */
    public File getOtherDocument() {
        return otherDocument;
    }

    /**
     * @param otherDocument the otherDocument to set
     */
    public void setOtherDocument(File otherDocument) {
        this.otherDocument = otherDocument;
    }

    /**
     * @return the otherDocumentFileName
     */
    public String getOtherDocumentFileName() {
        return otherDocumentFileName;
    }

    /**
     * @param otherDocumentFileName the otherDocumentFileName to set
     */
    public void setOtherDocumentFileName(String otherDocumentFileName) {
        this.otherDocumentFileName = otherDocumentFileName;
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
     * 
     * @return st
     */
    public String edit() {
        trialDTO  = (ProprietaryTrialDTO) ServletActionContext.getRequest().
            getSession().getAttribute(SESSION_TRIAL_DTO);
        return "edit";
    }
    /**
     * 
     * @return s
     */
    public String create() {
        trialDTO  = (ProprietaryTrialDTO) ServletActionContext.getRequest().getSession().
            getAttribute(SESSION_TRIAL_DTO);
        if (trialDTO == null) {
            return ERROR;
        }
        try {
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            StudyProtocolDTO studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext.
                    getRequest().getRemoteUser()));
            StudySiteAccrualStatusDTO siteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(trialDTO);
            
            OrganizationDTO leadOrganizationDTO = util.convertToLeadOrgDTO(trialDTO);
            OrganizationDTO summary4organizationDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudySiteDTO leadOrganizationTrialIdentifierDTO = util.convertToleadOrgSiteIdDTO(trialDTO);
                    
            StudySiteDTO nctIdentifierDTO = util.convertToNCTStudySiteDTO(trialDTO, null);
            StudySiteDTO siteDTO = getSubmittingStudySiteDTO();
            StudyResourcingDTO studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO, null);
            
            PersonDTO siteInvestigatorDTO = new PersonDTO();
            siteInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi(trialDTO.getSitePiIdentifier()));
            OrganizationDTO studySiteOrgDTO = new OrganizationDTO();
            studySiteOrgDTO.setIdentifier(IiConverter.convertToPoOrganizationIi(
                    trialDTO.getSiteOrganizationIdentifier()));
            
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            
            Ii studyProtocolIi = PaRegistry.getTrialRegistrationService().
            createProprietaryInterventionalStudyProtocol(studyProtocolDTO, siteAccrualStatusDTO,  
                    documentDTOs, leadOrganizationDTO, siteInvestigatorDTO,
                    leadOrganizationTrialIdentifierDTO, studySiteOrgDTO, siteDTO, 
                    nctIdentifierDTO, summary4organizationDTO, studyResourcingDTO, 
                    BlConverter.convertToBl(Boolean.FALSE));
            StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().setAttribute(SESSION_TRIAL_DTO, trialDTO);
            ServletActionContext.getRequest().setAttribute("protocolId", 
                    protocolDTO.getAssignedIdentifier().getExtension());
            if (PAUtil.isNotEmpty(trialDTO.getStudyProtocolId())) {
                PaRegistry.getStudyProtocolStageService().delete(IiConverter.convertToIi(
                        trialDTO.getStudyProtocolId()));
             }

        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
            return ERROR;
        }
        return "review";
    }

    /**
     * @return
     */
    private StudySiteDTO getSubmittingStudySiteDTO() {
        StudySiteDTO siteDTO = new StudySiteDTO();
        siteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getLocalSiteIdentifier()));
        siteDTO.setProgramCodeText(StConverter.convertToSt(trialDTO.getSiteProgramCodeText()));
        if (PAUtil.isNotEmpty(trialDTO.getDateOpenedforAccrual()) 
                && PAUtil.isNotEmpty(trialDTO.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(trialDTO.getDateOpenedforAccrual(),
                    trialDTO.getDateClosedforAccrual()));
        }
        if (PAUtil.isNotEmpty(trialDTO.getDateOpenedforAccrual()) 
                && PAUtil.isEmpty(trialDTO.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(trialDTO.getDateOpenedforAccrual(),
                    null));
        }
        return siteDTO;
    }
    /**
     * 
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        return "redirect_to_search";
    }
    private List<TrialDocumentWebDTO> addDocDTOToList() throws IOException {
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        if (PAUtil.isNotEmpty(protocolDocFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(), 
                    protocolDocFileName, protocolDoc));
        }
        if (PAUtil.isNotEmpty(otherDocumentFileName)) {
             docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.OTHER.getCode(), 
                        otherDocumentFileName, otherDocument));  
         }
        return docDTOList;
    }
    
    private StudySiteAccrualStatusDTO convertToStudySiteAccrualStatusDTO(ProprietaryTrialDTO trialDto) {
        StudySiteAccrualStatusDTO isoDto = new StudySiteAccrualStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(trialDto
                .getSiteStatusCode())));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto
                .getSiteStatusDate())));
        return isoDto;
    }

    /**
     * @return the selectedTrialType
     */
    public String getSelectedTrialType() {
        return selectedTrialType;
    }

    /**
     * @param selectedTrialType the selectedTrialType to set
     */
    public void setSelectedTrialType(String selectedTrialType) {
        this.selectedTrialType = selectedTrialType;
    }
    /**
     * 
     * @return str
     */
    public String partialSave() {
        try {
            trialDTO = (ProprietaryTrialDTO) util.saveDraft(trialDTO);
            ServletActionContext.getRequest().setAttribute("protocolId", trialDTO.getStudyProtocolId());
            ServletActionContext.getRequest().setAttribute("partialSubmission", "submit");
            ServletActionContext.getRequest().setAttribute("trialDTO", trialDTO);
        } catch (PAException e) {
            LOG.error(e.getLocalizedMessage());
            addActionError(e.getMessage());
            return ERROR;
        }
        return "review";
    }
    /**
     * 
     * @return str
     */
    public String complete() {
        TrialValidator.removeSessionAttributes();
        String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (PAUtil.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        trialDTO = new ProprietaryTrialDTO();
        try {
            trialDTO =  (ProprietaryTrialDTO) util.getTrialDTOForPartiallySumbissionById(pId);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    trialDTO.getIndIdeDtos());
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    trialDTO.getFundingDtos());
        } catch (PAException e) {
            addActionError(e.getMessage());
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
       return SUCCESS; 
    }
}
