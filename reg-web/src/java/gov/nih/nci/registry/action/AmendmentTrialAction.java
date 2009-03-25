package gov.nih.nci.registry.action;


import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDTO;
import gov.nih.nci.pa.dto.TrialDocumentDTO;
import gov.nih.nci.pa.dto.TrialFundingDTO;
import gov.nih.nci.pa.dto.TrialIndIdeDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TrialUtil;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Vrushali
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" , "PMD" })
public class AmendmentTrialAction extends ActionSupport implements ServletResponseAware {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(AmendmentTrialAction.class);
    private TrialDTO trialDTO = new TrialDTO();
    private TrialFundingDTO trialFundingDTO = new TrialFundingDTO();
    private File protocolDoc;
    private String protocolDocFileName;
    private File irbApproval;
    private String irbApprovalFileName;
    private File participatingSites = null;
    private String participatingSitesFileName = null;
    private File informedConsentDocument = null;
    private String informedConsentDocumentFileName = null;
    private File otherDocument = null;
    private String otherDocumentFileName = null;
    private File changeMemoDoc;
    private String changeMemoDocFileName = null;
    private static String actualString = "Actual";
    private static String anticipatedString = "Anticipated";

    /**
     * @return the trialFundingDTO
     */
    public TrialFundingDTO getTrialFundingDTO() {
        return trialFundingDTO;
    }

    /**
     * @param trialFundingDTO the trialFundingDTO to set
     */
    public void setTrialFundingDTO(TrialFundingDTO trialFundingDTO) {
        this.trialFundingDTO = trialFundingDTO;
    }

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
        String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
        //clear the session
        removeSessionAttributes();
        try {
            Ii studyProtocolIi = IiConverter.convertToIi(pId);
            populateDTO(studyProtocolIi);
            trialDTO.setIdentifier(pId);
            addSessionAttributes(trialDTO);
            LOG.info("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }
    private void populateDTO(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        StudyProtocolQueryDTO spqDto = RegistryServiceLocator.getProtocolQueryService().
                getTrialSummaryByStudyProtocolId(
                    Long.valueOf(studyProtocolIi.getExtension()));
        CorrelationUtils cUtils = new CorrelationUtils();
        TrialUtil util = new TrialUtil();
        util.copy(spDTO, trialDTO);
        util.copy(spqDto, trialDTO);
        util.copyLO(cUtils.getPAOrganizationByIndetifers(spqDto.getLeadOrganizationId(), null), trialDTO);
        util.copyPI(cUtils.getPAPersonByIndetifers(spqDto.getPiId(), null), trialDTO);
        util.copyResponsibleParty(studyProtocolIi, trialDTO);
        util.copySponsor(studyProtocolIi, trialDTO);
        util.copyNctNummber(studyProtocolIi, trialDTO);
        util.copySummaryFour(RegistryServiceLocator.getStudyResourcingService().
                    getsummary4ReportedResource(studyProtocolIi), trialDTO);
        //Copy IND's
        List<StudyIndldeDTO> studyIndldeDTOList = RegistryServiceLocator.getStudyIndldeService()
            .getByStudyProtocol(studyProtocolIi);
        if (!(studyIndldeDTOList.isEmpty())) {
             util.copyINDIDEList(studyIndldeDTOList, trialDTO);
        }
        // query the study grants
        List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                    .getstudyResourceByStudyProtocol(studyProtocolIi);
        if (!(isoList.isEmpty())) {
            util.copyGrantList(isoList, trialDTO);
        }
        //copy the reason for trial
        StudyOverallStatusDTO sosDto = null;
        List<StudyOverallStatusDTO> sosList = RegistryServiceLocator.getStudyOverallStatusService()
                    .getCurrentByStudyProtocol(studyProtocolIi);
        if (!sosList.isEmpty()) {
            sosDto = sosList.get(0);
        }
        if (sosDto != null) {
            trialDTO.setReason(StConverter.convertToString(sosDto.getReasonText()));
        } else {
            trialDTO.setReason(null);
        }
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
     * Sets the ind ide information in the collection.
     * 
     * @return result
     */
    public String addIdeIndIndicator() {
        String number = ServletActionContext.getRequest().getParameter("number");
        String grantor = ServletActionContext.getRequest().getParameter("grantor");
        String programCode = ServletActionContext.getRequest().getParameter("programcode");
        String expandedAccess = ServletActionContext.getRequest().getParameter("expandedaccess");
        String expandedAccessType = ServletActionContext.getRequest().getParameter("expandedaccesstype");
        String holderType = ServletActionContext.getRequest().getParameter("holdertype");
        String indIde = ServletActionContext.getRequest().getParameter("indIde");
        // TODO DO A NOT NULL CHECK HERE -Harsha
        if (number.equals("") && grantor.equals("") && programCode.equals("") && expandedAccess.equals("No")
                && expandedAccessType.equals("") && holderType.equals("") && indIde.equals("undefined")) {
            return SUCCESS;
        }
        TrialIndIdeDTO indIdeHolder = new TrialIndIdeDTO();
        indIdeHolder.setExpandedAccess(expandedAccess.equals("") ? "-" : expandedAccess);
        indIdeHolder.setExpandedAccessType(expandedAccessType.equals("") ? "-" : expandedAccessType);
        indIdeHolder.setGrantor(grantor.equals("") ? "-" : grantor);
        indIdeHolder.setHolderType(holderType.equals("") ? "-" : holderType);
        indIdeHolder.setNumber(number.equals("") ? "-" : number);
        indIdeHolder.setProgramCode(programCode.equals("") ? "-" : programCode);
        indIdeHolder.setIndIde(indIde.equals("") ? "-" : indIde);
        indIdeHolder.setRowId(UUID.randomUUID().toString());
        ArrayList<TrialIndIdeDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                trialDTO.getIndDtos().add(sessionList.get(i));
            }
            trialDTO.getIndDtos().add(indIdeHolder);
            sessionList.add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, sessionList);
        } else {
            trialDTO.getIndDtos().add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, trialDTO.getIndDtos());
        }
        // ideIndList.setList(ideInd);
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String deleteIndIde() {
        String rowid = ServletActionContext.getRequest().getParameter("uuid");
        ArrayList<TrialIndIdeDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
        TrialIndIdeDTO holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (TrialIndIdeDTO) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        trialDTO.getIndDtos().clear();
        for (int i = 0; i < sessionList.size(); i++) {
            trialDTO.getIndDtos().add(sessionList.get(i));
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, trialDTO.getIndDtos());
        // ideIndList.setList(ideInd);
        return "display_ideind";
    }

    /**
     * 
     * @return result
     */
    public String addGrant() {
        String fundingMechanismCode = ServletActionContext.getRequest().getParameter("fundingMechanismCode");
        String nihInstitutionCode = ServletActionContext.getRequest().getParameter("nihInstitutionCode");
        String serialNumber = ServletActionContext.getRequest().getParameter("serialNumber");
        String nciDivisionProgramCode = ServletActionContext.getRequest().getParameter("nciDivisionProgramCode");
        // TODO DO A NOT NULL CHECK HERE -Harsha
        TrialFundingDTO grantHolder = new TrialFundingDTO();
        grantHolder.setFundingMechanism(fundingMechanismCode);
        grantHolder.setInstituteCode(nihInstitutionCode);
        grantHolder.setSerialNumber(serialNumber);
        grantHolder.setNciDivisionProgramCode(nciDivisionProgramCode);
        grantHolder.setRowId(UUID.randomUUID().toString());
        ArrayList<TrialFundingDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.GRANT_LIST);
        LOG.info("trialDTO.getFundingDtos()" + trialDTO.getFundingDtos().isEmpty());
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                trialDTO.getFundingDtos().add(sessionList.get(i));
            }
            trialDTO.getFundingDtos().add(grantHolder);
            sessionList.add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, sessionList);
        } else {
            trialDTO.getFundingDtos().add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, 
                    trialDTO.getFundingDtos());
        }
        // ideIndList.setList(ideInd);
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String deleteGrant() {
        String rowid = ServletActionContext.getRequest().getParameter("uuid");
        ArrayList<TrialFundingDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.GRANT_LIST);
        TrialFundingDTO holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (TrialFundingDTO) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        trialDTO.getFundingDtos().clear();
        for (int i = 0; i < sessionList.size(); i++) {
            trialDTO.getFundingDtos().add(sessionList.get(i));
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, trialDTO.getFundingDtos());
        // ideIndList.setList(ideInd);
        return "display_grants";
    }
    /**
     * 
     */
    private void removeSessionAttributes() {
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
        ServletActionContext.getRequest().getSession().removeAttribute("grantList");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadOrg");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadPI");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("Sponsorselected");
        ServletActionContext.getRequest().getSession().removeAttribute("PoResponsibleContact");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSummary4Sponsor");
    }
    /**
     * Clears the session variables and redirect to search.
     * @return s
     */
    public String cancel() {
        removeSessionAttributes();
        return "redirect_to_search";
    }
    /**
     * clears the session variables and shows the review page.
     * @return s
     */
    public String review() {
        try {
            enforceBusinessRules();
            if (hasFieldErrors()) {
            return ERROR;
            }
            if (hasActionErrors()) {
                return ERROR;
            }
            TrialUtil util = new TrialUtil();
            List<TrialDocumentDTO> docDTOList = new ArrayList<TrialDocumentDTO>();
            if (PAUtil.isNotEmpty(protocolDocFileName)) {
                docDTOList.add(util.convertToDocumentList(DocumentTypeCode.Protocol_Document.getCode(), 
                        protocolDocFileName, protocolDoc));
            }
            if (PAUtil.isNotEmpty(irbApprovalFileName)) {
                docDTOList.add(util.convertToDocumentList(DocumentTypeCode.IRB_Approval_Document.getCode(), 
                            irbApprovalFileName, irbApproval));
            }
            if (PAUtil.isNotEmpty(changeMemoDocFileName)) {
                docDTOList.add(util.convertToDocumentList(DocumentTypeCode.Other.getCode(), 
                           changeMemoDocFileName, changeMemoDoc));  
            }
            if (PAUtil.isNotEmpty(informedConsentDocumentFileName)) {
                docDTOList.add(util.convertToDocumentList(DocumentTypeCode.Informed_Consent_Document.getCode(),
                            informedConsentDocumentFileName, informedConsentDocument));
            }
            if (PAUtil.isNotEmpty(participatingSitesFileName)) {
                docDTOList.add(util.convertToDocumentList(DocumentTypeCode.Participating_sites.getCode(),
                            participatingSitesFileName, participatingSites));
             }
             if (PAUtil.isNotEmpty(otherDocumentFileName)) {
                 docDTOList.add(util.convertToDocumentList(DocumentTypeCode.Other.getCode(), 
                            otherDocumentFileName, otherDocument));  
             }
             trialDTO.setDocDtos(docDTOList);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            return ERROR;
        }
        //get the document and put in list
        //add the IndIde,FundingList,protocolDocument
           ArrayList<TrialIndIdeDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
        .getAttribute(Constants.INDIDE_LIST);
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                trialDTO.getIndDtos().add(sessionList.get(i));
            }
        }
        ArrayList<TrialFundingDTO> grantList = (ArrayList) ServletActionContext.getRequest().getSession()
        .getAttribute(Constants.GRANT_LIST);
        if (sessionList != null) {
            for (int i = 0; i < grantList.size(); i++) {
                trialDTO.getFundingDtos().add(grantList.get(i));
            }
        }
        removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute("trialDTO", trialDTO);
        LOG.info("Calling the review page...");
        return "review";    
    }
    /**
     * 
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute("trialDTO");
        addSessionAttributes(trialDTO);
        return "edit";
    }
    /**
     * 
     * @return s
     */
    public String amend() {
     return SUCCESS;   
    }
    private void addSessionAttributes(TrialDTO tDTO) {
        if (!tDTO.getIndDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    tDTO.getIndDtos());
        }
        if (!tDTO.getFundingDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    tDTO.getFundingDtos());
        }
        List<DocumentDTO> documentISOList;
        try {
            documentISOList = RegistryServiceLocator.getDocumentService()
            .getDocumentsByStudyProtocol(IiConverter.convertToIi(tDTO.getIdentifier()));
            if (!(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
        } catch (PAException e) {
            LOG.error("exception in edit" + e.getMessage());
        }

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
        if (PAUtil.isEmpty(trialDTO.getLocalProtocolIdentifier())) {
            addFieldError("trialDTO.localProtocolIdentifier",
                    getText("error.submit.localProtocolIdentifier"));
        }
        if (PAUtil.isEmpty(trialDTO.getOfficialTitle())) {
            addFieldError("trialDTO.officialTitle", getText("error.submit.trialTitle"));
        } else if (PAUtil.isNotEmpty(trialDTO.getOfficialTitle())) {            
            if (trialDTO.getOfficialTitle().trim().length() > Constants.TRIAL_TITLE_MAX_LENGTH) {
                addFieldError("trialDTO.officialTitle", getText("error.submit.trialTitleLength"));
            }            
        }
        if (PAUtil.isEmpty(trialDTO.getPhaseCode())) {
            addFieldError("trialDTO.phaseCode", getText("error.submit.trialPhase"));
        }
        if (PAUtil.isEmpty(trialDTO.getStatusCode())) {
            addFieldError("trialDTO.statusCode", getText("error.submit.statusCode"));
        }
        if (PAUtil.isEmpty(trialDTO.getTrialType())) {
            addFieldError("trialDTO.trialType", getText("error.submit.trialType"));
        }
        if (PAUtil.isEmpty(trialDTO.getPrimaryPurposeCode())) {
            addFieldError("trialDTO.primaryPurposeCode", getText("error.submit.trialPurpose"));
        }
        if (PAUtil.isEmpty(trialDTO.getStatusDate())) {
            addFieldError("trialDTO.statusDate", getText("error.submit.statusDate"));
        }
        if (PAUtil.isEmpty(trialDTO.getStartDate())) {
            addFieldError("trialDTO.startDate", getText("error.submit.startDate"));
        }
        if (PAUtil.isEmpty(trialDTO.getStartDateType())) {
            addFieldError("trialDTO.startDateType", getText("error.submit.dateType"));
        }
        if (PAUtil.isEmpty(trialDTO.getCompletionDate())) {
            addFieldError("trialDTO.completionDate", getText("error.submit.completionDate"));
        }
        if (PAUtil.isEmpty(trialDTO.getCompletionDateType())) {
            addFieldError("trialDTO.completionDateType", getText("error.submit.dateType"));
        }
        if (PAUtil.isEmpty(trialDTO.getContactEmail())) {
            addFieldError("trialDTO.contactEmail", getText("error.submit.contactEmail"));
        }
        if (PAUtil.isEmpty(trialDTO.getContactPhone())) {
            addFieldError("trialDTO.contactPhone", getText("error.submit.contactPhone"));
        }
        // LeadOrgNotSelected;check the session;
        if (trialDTO.getLeadOrganizationIdentifier() == null) {
            addFieldError("LeadOrgNotSelected", getText("error.submit.leadOrganization"));
        }
        if (trialDTO.getPiIdentifier() == null) {
            addFieldError("LeadPINotSelected", getText("error.submit.leadPrincipalInvestigator"));
        }
        if (trialDTO.getSponsorIdentifier() == null) {
            addFieldError("SponsorNotSelected", getText("error.submit.sponsor"));
        }
        if (!(trialDTO.getResponsiblePartyType().equals("pi"))) {
            if (trialDTO.getResponsiblePersonIdentifier() == null) {
                addFieldError("ResponsiblePartyNotSelected", getText("error.submit.sponsorResponsibelParty"));
            }
        }
        if (PAUtil.isNotEmpty(trialDTO.getPhaseCode())) {
            if (PhaseCode.OTHER.getCode().equals(trialDTO.getPhaseCode())
                && PAUtil.isEmpty(trialDTO.getPhaseOtherText())) {
                addFieldError("trialDTO.phaseOtherText", 
                        getText("error.submit.otherPhaseText"));
            }            
        }
        if (PAUtil.isNotEmpty(trialDTO.getPhaseCode())) {
            if (PrimaryPurposeCode.OTHER.getCode().equals(trialDTO.getPrimaryPurposeCode())
                && PAUtil.isEmpty(trialDTO.getPrimaryPurposeOtherText())) {
                addFieldError("trialDTO.primaryPurposeOtherText", 
                        getText("error.submit.otherPurposeText"));
            }            
        }
        // check if the contact email address is valid
        validateEmailAddress(trialDTO.getContactEmail(), "trialDTO.contactEmail");
        // check if the contact phone number is valid
        validatePhoneNumber(trialDTO.getContactPhone(), "trialDTO.contactPhone");
        // validate the date formats
        validateDateFormat();
        // validate trial status reason
        validateTrialStatusReason();
        // validate trial status and dates
        enforceBusinessRulesForDates();
        //validate the docs
        validateDocuments();
    }
    
    /**
     * validate Phone number.
     */
    private void validatePhoneNumber(String contactPhone, String fieldName) {
        // check if the contact phone number is valid
        if (PAUtil.isNotEmpty(contactPhone)) {
            if (!RegistryUtil.isValidPhoneNumber(contactPhone)) {
                addFieldError(fieldName,
                        getText("error.register.invalidPhoneNumber"));               
            }            
        }
        
    }
    /**
     * validate Email Address.
     */
    private void validateEmailAddress(String contactEmail , String fieldName) {
        // check if the contact e-mail address is valid
        if (PAUtil.isNotEmpty(contactEmail)) {
            if (!RegistryUtil.isValidEmailAddress(contactEmail)) {
                addFieldError(fieldName,
                        getText("error.submit.invalidContactEmailAddress"));               
            }            
        }        
    }

    /**
     * validate trial status reason.
     */
    private void validateTrialStatusReason() {
        if (PAUtil.isNotEmpty(trialDTO.getStatusCode())) {
            // check if reason entered for these trial statuses
            if (TrialStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().
                    equals(trialDTO.getStatusCode())
              || TrialStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode().
                    equals(trialDTO.getStatusCode())
              || TrialStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode().
                    equals(trialDTO.getStatusCode())) {                
                    if (PAUtil.isEmpty(trialDTO.getReason())) {
                        addFieldError("trialDTO.reason", 
                                getText("error.submit.trialStatusReason")); 
                        
                    }                
            } else {
                if (PAUtil.isNotEmpty(trialDTO.getReason())) {
                    trialDTO.setReason(null);
                }                 
            }
        }
        
    }

    /**
     * validate the date format.
     */
    private void validateDateFormat() {
        if (PAUtil.isNotEmpty(trialDTO.getStatusDate())
                && !RegistryUtil.isValidDate(trialDTO.getStatusDate())) {
                    addFieldError("trialDTO.statusDate", 
                            getText("error.submit.invalidDate"));
        }
        if (PAUtil.isNotEmpty(trialDTO.getStartDate())
                && !RegistryUtil.isValidDate(trialDTO.getStartDate())) {
                    addFieldError("trialDTO.startDate", 
                            getText("error.submit.invalidDate"));
        }
        if (PAUtil.isNotEmpty(trialDTO.getCompletionDate())
                && !RegistryUtil.isValidDate(trialDTO.getCompletionDate())) {
                    addFieldError("trialDTO.completionDate", 
                            getText("error.submit.invalidDate"));
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
    private void validateDocuments() {
        if (PAUtil.isEmpty(protocolDocFileName)) {
            addFieldError("trialDTO.protocolDocFileName", getText("error.submit.protocolDocument"));
        }
        if (PAUtil.isNotEmpty(protocolDocFileName)) {
            if (!protocolDoc.exists()) {
                addFieldError("trialDTO.protocolDocFileName", 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(protocolDocFileName)) {
                addFieldError("trialDTO.protocolDocFileName", 
                        getText("error.submit.invalidFileType"));                
            }
        }
        if (PAUtil.isEmpty(irbApprovalFileName)) {
            addFieldError("trialDTO.irbApprovalFileName", getText("error.submit.irbApproval"));
        }
        if (PAUtil.isNotEmpty(irbApprovalFileName)) {
            if (!irbApproval.exists()) {
                addFieldError("trialDTO.irbApprovalFileName", 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(irbApprovalFileName)) {
                addFieldError("trialDTO.irbApprovalFileName", 
                        getText("error.submit.invalidFileType"));                
            }
        }        
        if (PAUtil.isNotEmpty(participatingSitesFileName)) {
            if (!participatingSites.exists()) {
                addFieldError("trialDTO.participatingSitesFileName", 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(participatingSitesFileName)) {
                addFieldError("trialDTO.participatingSitesFileName", 
                        getText("error.submit.invalidFileType"));                
            }
        }        
        if (PAUtil.isNotEmpty(informedConsentDocumentFileName)) {
            if (!informedConsentDocument.exists()) {
                addFieldError("trialDTO.informedConsentDocumentFileName", 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(informedConsentDocumentFileName)) {
                addFieldError("trialDTO.informedConsentDocumentFileName", 
                        getText("error.submit.invalidFileType"));                
            }
        }     
        //protocol Highlighted doc
        if (PAUtil.isNotEmpty(otherDocumentFileName)) {
            if (!otherDocument.exists()) {
                addFieldError("trialDTO.otherDocumentFileName", 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(otherDocumentFileName)) {
                addFieldError("trialDTO.otherDocumentFileName", 
                        getText("error.submit.invalidFileType"));                
            }
        }
        if (PAUtil.isEmpty(changeMemoDocFileName)) {
            addFieldError("trialDTO.changeMemoDocFileName", getText("error.submit.changeMemo"));
        }

        if (PAUtil.isNotEmpty(changeMemoDocFileName)) {
            if (!changeMemoDoc.exists()) {
                addFieldError("trialDTO.changeMemoDocFileName", 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(changeMemoDocFileName)) {
                addFieldError("trialDTO.changeMemoDocFileName", 
                        getText("error.submit.invalidFileType"));                
            }
        }

    }
    private boolean enforceBusinessRulesForDates() throws PAException {
        StudyStatusCode newCode = StudyStatusCode.getByCode(trialDTO.getStatusCode());
        
        Timestamp statusTimestamp = PAUtil.dateStringToTimestamp(trialDTO.getStatusDate());
        Timestamp startTimestamp = PAUtil.dateStringToTimestamp(trialDTO.getStartDate());
        Timestamp completionTimestamp = PAUtil.dateStringToTimestamp(trialDTO.getCompletionDate());
        
        StudyStatusCode oldCode = null;
        Timestamp oldDate = null;
        String oldReason = null;
        List<StudyOverallStatusDTO> sosList = RegistryServiceLocator.getStudyOverallStatusService()
        .getCurrentByStudyProtocol(IiConverter.convertToIi(trialDTO.getIdentifier()));
        if (!sosList.isEmpty()) {
            oldCode = StudyStatusCode.getByCode(CdConverter.convertCdToString(sosList.get(0).getStatusCode()));
            oldDate = TsConverter.convertToTimestamp(sosList.get(0).getStatusDate());
            oldReason = StConverter.convertToString(sosList.get(0).getReasonText());
        }

        
        boolean codeChanged = (newCode == null) ? (oldCode != null) : !newCode.equals(oldCode);
        boolean dateChanged = (oldDate == null) ? (statusTimestamp != null) : !oldDate.equals(statusTimestamp);
        boolean reasonChanged = (oldReason == null) 
                ? (trialDTO.getReason() != null) : !oldReason.equals(trialDTO.getReason());
        if (!codeChanged && !dateChanged && !reasonChanged) {
            return false;
        }

        // enforce status transition rules (this must be first check per Tracker 17366
        if ((oldCode != null) && !oldCode.canTransitionTo(newCode)) {
            addActionError("Illegal study status transition from '" + oldCode.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }

        // enforce date rules related to status transitions
        if ((trialDTO.getStartDateType() != null) && (trialDTO.getCompletionDateType() != null)) {
            if (StudyStatusCode.APPROVED.equals(oldCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
                if (!startTimestamp.equals(statusTimestamp)) {
                    addActionError("When transitioning from 'Approved' to 'Active' the trial start "
                            + "date must be the same as the status date.");
                }
                if (!trialDTO.getStartDateType().equals(actualString)) {
                    addActionError("When transitioning from 'Approved' to 'Active' "
                            + "the trial start date must be 'Actual'.");
                }
            }
            if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                    && trialDTO.getStartDateType().equals(anticipatedString)) {
                addActionError("Trial start date can be 'Anticipated' only if the status is "
                            + "'Approved' or 'Withdrawn'.");
            }
            if (StudyStatusCode.APPROVED.equals(oldCode) && StudyStatusCode.WITHDRAWN.equals(newCode)
                    && trialDTO.getStartDateType().equals(actualString)) {
                addActionError("Trial Start date type should be ‘Anticipated’ and Trial Start date "
                            + "should be future date if Trial Status is changed from ‘Approved’ to ‘Withdrawn’.  ");
            }
            if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
                if (trialDTO.getCompletionDateType().equals(anticipatedString)) {
                    addActionError("Primary Completion Date cannot be 'Anticipated' when "
                            + "Current Trial Status is '" + newCode.getCode() + "'.");
                }
                if (!statusTimestamp.equals(completionTimestamp)) {
                    addActionError("Current Trial Status Date and Primary Completion Date must be the same when "
                            + "Current Trial Status is '" + newCode.getCode() + "'.");
                }
            } else {
                if (!trialDTO.getCompletionDateType().equals(anticipatedString)) {
                    addActionError("Trial completion date must be 'Anticipated' when the status is "
                            + "not 'Complete' or 'Administratively Complete'.");
                }
            }
        }
        return true;
    }

}