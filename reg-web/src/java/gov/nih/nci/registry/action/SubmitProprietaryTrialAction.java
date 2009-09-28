/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
    private ProprietaryTrialDTO trialDTO;
    private File protocolDoc;
    private String protocolDocFileName;
    private File otherDocument;
    private String otherDocumentFileName;
    private String trialAction = "submit";
    private String selectedTrialType = "no";
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
            addSessionAttributes(trialDTO);
        return ERROR;
        }
        if (hasActionErrors()) {
            addSessionAttributes(trialDTO);
            return ERROR;
        }
        try {
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
            trialDTO.setDocDtos(addDocDTOToList());
        } catch (IOException e) {
                addSessionAttributes(trialDTO);
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
            addFieldError("trialDTO." + invalidValues[i].getPropertyName(), 
                            getText(invalidValues[i].getMessage().trim()));
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
        String err = "error.submit.invalidDate";      // validate date and its format
        if (!RegistryUtil.isValidDate(trialDTO.getSiteStatusDate())) {
            addFieldError("trialDTO.siteStatusDate", getText(err));
        } else if (PAUtil.isDateCurrentOrPast(trialDTO.getSiteStatusDate())) {
                addFieldError("trialDTO.siteStatusDate", getText("error.submit.invalidStatusDate"));
        }
        if (PAUtil.isNotEmpty(trialDTO.getDateOpenedforAccrual())) {
                if (!RegistryUtil.isValidDate(trialDTO.getDateOpenedforAccrual())) {
                    addFieldError("trialDTO.dateOpenedforAccrual", getText(err));
                } else if (PAUtil.isDateCurrentOrPast(trialDTO.getDateOpenedforAccrual())) {
                    addFieldError("trialDTO.dateOpenedforAccrual", 
                       getText("error.proprietary.submit.dateOpenedAccrual"));
                }
        }
        if (PAUtil.isNotEmpty(trialDTO.getDateClosedforAccrual())) {
                if (!RegistryUtil.isValidDate(trialDTO.getDateClosedforAccrual())) {
                    addFieldError("trialDTO.dateClosedforAccrual", getText(err));
                } else if (PAUtil.isDateCurrentOrPast(trialDTO.getDateClosedforAccrual())) {
                    addFieldError("trialDTO.dateClosedforAccrual", 
                       getText("error.proprietary.submit.dateClosedAccrual"));
                }
        }
        if (PAUtil.isNotEmpty(trialDTO.getDateClosedforAccrual())  
            && PAUtil.isEmpty(trialDTO.getDateOpenedforAccrual())) {
                addFieldError("trialDTO.dateOpenedforAccrual", 
                        getText("error.proprietary.submit.dateOpenReq"));
        }
        if (PAUtil.isNotEmpty(trialDTO.getDateOpenedforAccrual())
                && PAUtil.isNotEmpty(trialDTO.getDateClosedforAccrual())
                && RegistryUtil.isValidDate(trialDTO.getDateOpenedforAccrual())
                && RegistryUtil.isValidDate(trialDTO.getDateClosedforAccrual())) {
            Timestamp dateOpenedDateStamp = PAUtil.dateStringToTimestamp(trialDTO.getDateOpenedforAccrual());
            Timestamp dateClosedDateStamp = PAUtil.dateStringToTimestamp(trialDTO.getDateClosedforAccrual());
            if (dateClosedDateStamp.before(dateOpenedDateStamp)) {
                addFieldError("trialDTO.dateClosedforAccrual", 
                        getText("error.proprietary.submit.dateClosedAccrualBigger"));                
            }
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
        addSessionAttributes(trialDTO);
        return "edit";
    }
    /**
     * 
     * @return s
     */
    public String create() {
        trialDTO  = (ProprietaryTrialDTO) ServletActionContext.getRequest().getSession().
            getAttribute(SESSION_TRIAL_DTO);
        TrialUtil util = new TrialUtil();
        try {
            StudyProtocolDTO studyProtocolDTO = convertToInterventionalStudyProtocolDTO(trialDTO);
            StudySiteOverallStatusDTO siteOverallStatusDTO = convertToStudySiteOverallStatusDTO(trialDTO);
            
            OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
            leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi(
                    trialDTO.getLeadOrganizationIdentifier()));
            OrganizationDTO summary4organizationDTO = null;
            if (PAUtil.isNotEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
                summary4organizationDTO = new OrganizationDTO();
                summary4organizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi(
                    trialDTO.getSummaryFourOrgIdentifier()));
            }
            StudySiteDTO leadOrganizationTrialIdentifierDTO = new StudySiteDTO();
            leadOrganizationTrialIdentifierDTO.setLocalStudyProtocolIdentifier(
                    StConverter.convertToSt(trialDTO.getLeadOrgTrialIdentifier()));
                    
            StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
            nctIdentifierDTO.setLocalStudyProtocolIdentifier(
                    StConverter.convertToSt(trialDTO.getNctIdentifier()));
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
            StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
            studyResourcingDTO.setTypeCode(
                    CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode()));
            PersonDTO siteInvestigatorDTO = new PersonDTO();
            siteInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi(trialDTO.getSitePiIdentifier()));
            OrganizationDTO studySiteOrgDTO = new OrganizationDTO();
            studySiteOrgDTO.setIdentifier(IiConverter.convertToPoOrganizationIi(
                    trialDTO.getSiteOrganizationIdentifier()));
            
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            
            Ii studyProtocolIi = RegistryServiceLocator.getTrialRegistrationService().
            createProprietaryInterventionalStudyProtocol(studyProtocolDTO, siteOverallStatusDTO, studyIndldeDTOs, 
                    studyResourcingDTOs, documentDTOs, leadOrganizationDTO, siteInvestigatorDTO,
                    leadOrganizationTrialIdentifierDTO, studySiteOrgDTO, siteDTO, 
                    nctIdentifierDTO, summary4organizationDTO, studyResourcingDTO);
          //send a notification mail
            RegistryServiceLocator.getMailManagerService().sendNotificationMail(studyProtocolIi);  
            StudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().setAttribute(SESSION_TRIAL_DTO, trialDTO);
            ServletActionContext.getRequest().setAttribute("protocolId", 
                    protocolDTO.getAssignedIdentifier().getExtension());
         
        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
            addSessionAttributes(trialDTO);
            return ERROR;
        }
        return "review";
    }
    /**
     * 
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        return "redirect_to_search";
    }
    private void addSessionAttributes(ProprietaryTrialDTO tDTO) {
        if (tDTO == null) {
            return;
        }
        if (tDTO.getIndIdeDtos() != null && !tDTO.getIndIdeDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    tDTO.getIndIdeDtos());
        }
        if (tDTO.getFundingDtos() != null && !tDTO.getFundingDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    tDTO.getFundingDtos());
        }
    }
    private List<TrialDocumentWebDTO> addDocDTOToList() throws IOException {
        TrialUtil util = new TrialUtil();
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
    private InterventionalStudyProtocolDTO convertToInterventionalStudyProtocolDTO(BaseTrialDTO trialDto) {
        InterventionalStudyProtocolDTO isoDto =  new InterventionalStudyProtocolDTO();
        isoDto.setOfficialTitle(StConverter.convertToSt(trialDto.getOfficialTitle()));
        isoDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDto.getPhaseCode())));
        isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(
                PrimaryPurposeCode.getByCode(trialDto.getPrimaryPurposeCode())));
        isoDto.setStudyProtocolType(StConverter.convertToSt(trialDto.getTrialType()));
        isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
        return isoDto;
    }
    
    private StudySiteOverallStatusDTO convertToStudySiteOverallStatusDTO(ProprietaryTrialDTO trialDto) {
        StudySiteOverallStatusDTO isoDto = new StudySiteOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDto
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
    
}
