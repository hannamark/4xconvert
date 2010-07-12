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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength",
    "PMD.TooManyMethods", "unchecked"  })
public class SubmitProprietaryTrialAction extends ManageFileAction implements
        ServletResponseAware {
    private static final String SESSION_TRIAL_DTO = "trialDTO";
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(SubmitProprietaryTrialAction.class);
    private ProprietaryTrialDTO trialDTO;
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
    @Override
    public String execute() {
        TrialValidator.removeSessionAttributes();
        trialDTO = new ProprietaryTrialDTO();
        trialDTO.setPropritaryTrialIndicator(CommonsConstant.YES);
        trialDTO.setTrialType("Interventional");
        setPageFrom("proprietaryTrial");
     return SUCCESS;
    }
    /**
     *
     * @return st
     */
    public String review() {
        clearErrorsAndMessages();
        enforceBusinessRules();
        try {
            List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                return ERROR;
            }
            if (hasActionErrors()) {
                return ERROR;
            }
            populateList(docDTOList);

            trialDTO.setDocDtos(docDTOList);
        } catch (IOException e) {
            addActionError(e.getMessage());
            return ERROR;
        }

        ServletActionContext.getRequest().getSession().removeAttribute(Constants.INDIDE_LIST);
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.GRANT_LIST);
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(DocumentTypeCode.OTHER.getShortName());
        ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_DTO, trialDTO);
        return "review";
    }

    private void enforceBusinessRules() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        InvalidValue[] invalidValues = null;
        ClassValidator<ProprietaryTrialDTO> classValidator = new ClassValidator(trialDTO.getClass());
        invalidValues = classValidator.getInvalidValues(trialDTO);
        for (int i = 0; i < invalidValues.length; i++) {
            if (StringUtils.isNotEmpty(trialDTO.getNctIdentifier())) {
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

        if (StringUtils.isEmpty(trialDTO.getNctIdentifier())
                && StringUtils.isEmpty(getProtocolDocFileName())
                && session.getAttribute(DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName()) == null) {
            addFieldError("trialDTO.nctIdentifier", "Provide either NCT Number or Protocol Trial Template.\n");
            addFieldError("trialDTO.protocolDocFileName", "Provide either NCT Number or Protocol Trial Template.\n");
        }
        if (!StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && StringUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            addFieldError("summary4FundingSponsor", "Select the Summary 4 Funding Sponsor");
        }
        if (StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && !StringUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            addFieldError("trialDTO.summaryFourFundingCategoryCode", "Select the Summary 4 Funding Sponsor Type");
        }
        TrialValidator validator = new TrialValidator();
        Map<String, String> errMap = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(getProtocolDocFileName())
                && session.getAttribute(DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName()) == null) {
            errMap = validator.validateDocument(getProtocolDocFileName(), getProtocolDoc(),
                                                "trialDTO.protocolDocFileName", "");
            addErrors(errMap);
        }
        if (StringUtils.isNotEmpty(getOtherDocumentFileName())
                && session.getAttribute(DocumentTypeCode.OTHER.getShortName()) == null) {
            errMap = new HashMap<String, String>();
            errMap = validator.validateDocument(getOtherDocumentFileName(), getOtherDocument(),
                                                "trialDTO.otherDocumentFileName", "");
            addErrors(errMap);
        }
        PAServiceUtils paServiceUtils = new PAServiceUtils();
        StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(trialDTO);
        StudySiteDTO studySiteDTO = getSubmittingStudySiteDTO();
        String errMsg = paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO);
        if (StringUtils.isNotEmpty(errMsg)) {
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
     *
     * @return st
     */
    public String edit() {
        trialDTO  = (ProprietaryTrialDTO) ServletActionContext.getRequest().
            getSession().getAttribute(SESSION_TRIAL_DTO);
        setDocumentsInSession();
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
                    PAUtil.getAssignedIdentifier(protocolDTO).getExtension());
            if (StringUtils.isNotEmpty(trialDTO.getStudyProtocolId())) {
                PaRegistry.getStudyProtocolStageService().delete(IiConverter.convertToIi(
                        trialDTO.getStudyProtocolId()));
             }

        } catch (PAException e) {
            LOG.error(e);
            setDocumentsInSession();
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
        if (StringUtils.isNotEmpty(trialDTO.getDateOpenedforAccrual())
                && StringUtils.isNotEmpty(trialDTO.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(trialDTO.getDateOpenedforAccrual(),
                    trialDTO.getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(trialDTO.getDateOpenedforAccrual())
                && StringUtils.isEmpty(trialDTO.getDateClosedforAccrual())) {
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

    private void setDocumentsInSession() {
        if (trialDTO != null && trialDTO.getDocDtos() != null && !trialDTO.getDocDtos().isEmpty()) {
            for (TrialDocumentWebDTO webDto : trialDTO.getDocDtos()) {
                ServletActionContext.getRequest().getSession().setAttribute(
                        DocumentTypeCode.getByCode(webDto.getTypeCode()).getShortName(), webDto);
            }
        }
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
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (StringUtils.isEmpty(pId)) {
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
            setPageFrom("proprietaryTrial");
        } catch (PAException e) {
            addActionError(e.getMessage());
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
       return SUCCESS;
    }
}
