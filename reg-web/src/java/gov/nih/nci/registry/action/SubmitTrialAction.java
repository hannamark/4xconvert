package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.dto.InterventionalStudyProtocolWebDTO;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.dto.StudyParticipationWebDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.mail.MailManager;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Bala Nair
 * @author Harsha
 * 
 */
@Validation
@SuppressWarnings("PMD")
public class SubmitTrialAction extends ActionSupport implements ServletResponseAware {
    private static final String VIEW_TRIAL = "view";
    private static final Logger LOG = Logger.getLogger(SubmitTrialAction.class);
    private InterventionalStudyProtocolWebDTO protocolWebDTO = new InterventionalStudyProtocolWebDTO();
    private StudyParticipationWebDTO participationWebDTO = new StudyParticipationWebDTO();
    private TrialFundingWebDTO trialFundingWebDTO = new TrialFundingWebDTO();
    private StudyOverallStatusWebDTO overallStatusWebDTO = new StudyOverallStatusWebDTO();
    private TrialDocumentWebDTO trialDocumentWebDTO = new TrialDocumentWebDTO();
    private Long cbValue;
    private String page;
    private File protocolDoc;
    private String protocolDocFileName;
    private File irbApproval;
    private String irbApprovalFileName;
    private Long id = null;
    private HttpServletResponse servletResponse;
    private static final int MAXF = 1024;
    private String trialAction = "submit";
    /**
     * Adding new members for PO integration and additional Use cases.
     */
    // Collection for holding the ideInd information
    private ArrayList<IndIdeHolder> ideInd = new ArrayList<IndIdeHolder>();
    // Lead Org/Pers
    private OrganizationDTO selectedLeadOrg = null;
    private PersonDTO selectedLeadPrincipalInvestigator = null;
    // Sponsor / Resp Party
    private OrganizationDTO selectedSponsor = null;
    private PersonDTO responsiblePartyContact = null;
    // Summary 4 Sponsor
    private OrganizationDTO selectedSummary4Sponsor = null;
    // Collection for holding the grant information
    private ArrayList<GrantHolder> grants = new ArrayList<GrantHolder>();
    private String trialType = null;
    private File participatingSites = null;
    private String participatingSitesFileName = null;
    private File informedConsentDocument = null;
    private String informedConsentDocumentFileName = null;
    private File otherDocument = null;
    private String otherDocumentFileName = null;
    private String respparty = null;
    private String contactEmail = null;
    private String contactPhone = null;
    private String trialPurpose = null;
    private String summary4FundingCategory = null;

    /**
     * 
     * @return res
     */
    public String execute() {
        return SUCCESS;
    }

    /**
     * create protocol.
     * 
     * @return String
     */
    public String create() {
        StudyProtocolDTO protocolDTO;
        try {
            clearErrorsAndMessages();
            // validate the form elements
            validateForm();
            if (hasFieldErrors()) {
                resetValuesFromSession();
                return ERROR;
            }
            Ii studyProtocolIi = null;
            // before creating the protocol check for duplicate
            // using the Lead Org Trial Identifier and Lead Org Identifier
            Organization paOrg = new Organization();
            paOrg.setIdentifier(IiConverter.convertToString(selectedLeadOrg.getIdentifier()));
            paOrg = RegistryServiceLocator.getPAOrganizationService().getOrganizationByIndetifers(paOrg);
            
            if (paOrg != null && paOrg.getId() != null) {            
                StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
                criteria.setLeadOrganizationTrialIdentifier(
                                    participationWebDTO.getLocalProtocolIdentifier());
                criteria.setLeadOrganizationId(paOrg.getId());
                
                List<StudyProtocolQueryDTO> records = RegistryServiceLocator.
                                    getProtocolQueryService().getStudyProtocolByCriteria(criteria);
                if (records != null && records.size() > 0) {
                    addActionError("Duplicate Trial Submission: A trial exists in the system "
                                        + " for the Lead Organization selected and entered Trial Identifier");
                    ServletActionContext.getRequest().setAttribute(
                                    "failureMessage" , "Duplicate Trial Submission: A trial exists in the system "
                                      + " for the Lead Organization selected and entered Trial Identifier");
                    return ERROR;
                }
            }            
            
            if (trialType.equals("Observational")) {
                studyProtocolIi = RegistryServiceLocator.getStudyProtocolService().createObservationalStudyProtocol(
                        (ObservationalStudyProtocolDTO) createProtocolDTO(trialType));
            } else {
                studyProtocolIi = RegistryServiceLocator.getStudyProtocolService().createInterventionalStudyProtocol(
                        (InterventionalStudyProtocolDTO) createProtocolDTO(trialType));
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
            LOG.info("Trial is registered with ID: " + IiConverter.convertToString(studyProtocolIi));
            ServletActionContext.getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,
                    IiConverter.convertToString(studyProtocolIi));
            // create study overall status
            createStudyStatus(studyProtocolIi);
            // create IND/IDE information *Multiple - times*
            createIndIdeIndicators(studyProtocolIi);
            // create the Study Grants *Multiple - times*
            createStudyResources(studyProtocolIi);
            if (PAUtil.isNotEmpty(protocolDocFileName)) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.Protocol_Document.getCode(), protocolDocFileName,
                        protocolDoc);
            }
            if (PAUtil.isNotEmpty(irbApprovalFileName)) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.IRB_Approval_Document.getCode(), irbApprovalFileName,
                        irbApproval);
            }
            
            if (PAUtil.isNotEmpty(informedConsentDocumentFileName)) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.Informed_Consent_Document.getCode(),
                        informedConsentDocumentFileName, informedConsentDocument);
            }
            
            if (PAUtil.isNotEmpty(participatingSitesFileName)) {
                uploadDocument(studyProtocolIi,
                        DocumentTypeCode.Participating_sites.getCode(),
                        participatingSitesFileName, participatingSites);
            }
            
            if (PAUtil.isNotEmpty(otherDocumentFileName)) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.Other.getCode(), otherDocumentFileName,
                        otherDocument);  
            }
            // ----------- Begin calling the PO related
            selectedSummary4Sponsor = (OrganizationDTO) ServletActionContext.getRequest().getSession().getAttribute(
                    "PoSummary4Sponsor");
            if (selectedSummary4Sponsor != null) {
                new PARelationServiceBean().createSummary4ReportedSource(selectedSummary4Sponsor.getIdentifier()
                        .getExtension(), SummaryFourFundingCategoryCode.getByCode(summary4FundingCategory), IiConverter
                        .convertToLong(studyProtocolIi));
            }
            selectedLeadOrg = (OrganizationDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute("PoLeadOrg");
            if (selectedLeadOrg != null) {
                new PARelationServiceBean().createLeadOrganizationRelations(selectedLeadOrg.getIdentifier()
                        .getExtension(), IiConverter.convertToLong(studyProtocolIi), participationWebDTO
                        .getLocalProtocolIdentifier());
            }
            selectedLeadPrincipalInvestigator = (PersonDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute("PoLeadPI");
            if (selectedLeadPrincipalInvestigator != null) {
                new PARelationServiceBean().createPrincipalInvestigatorRelations(selectedLeadOrg.getIdentifier()
                        .getExtension(), selectedLeadPrincipalInvestigator.getIdentifier().getExtension(), IiConverter
                        .convertToLong(studyProtocolIi), StudyTypeCode.getByCode(trialType));
            }
            selectedSponsor = (OrganizationDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute("PoSponsor");
            if (selectedSponsor != null) {
                if (respparty.equals("pi")) {
                    new PARelationServiceBean().createPIAsResponsiblePartyRelations(selectedSponsor.getIdentifier()
                            .getExtension(), selectedLeadPrincipalInvestigator.getIdentifier().getExtension(),
                            IiConverter.convertToLong(studyProtocolIi), contactEmail, contactPhone);
                } else {
                    responsiblePartyContact = (PersonDTO) ServletActionContext.getRequest().getSession().getAttribute(
                            "PoResponsibleContact");
                    new PARelationServiceBean().createSponsorAsPrimaryContactRelations(selectedSponsor.getIdentifier()
                            .getExtension(), responsiblePartyContact.getIdentifier().getExtension(), IiConverter
                            .convertToLong(studyProtocolIi), contactEmail, contactPhone);
                }
            }
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", studyProtocolIi.getExtension());
            // after creating the study protocol, query the protocol for viewing
            protocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            final MailManager mailManager = new MailManager();
            mailManager.sendNotificationMail(ServletActionContext.getRequest().getRemoteUser(), // remote
                    // user
                    protocolDTO.getAssignedIdentifier().getExtension(), // generated
                    // identifier
                    participationWebDTO.getLocalProtocolIdentifier() // lead
                    // org
                    // trial
                    // identifier
                    );
            ServletActionContext.getRequest().getSession().removeAttribute("INDIDE_LIST");
            ServletActionContext.getRequest().getSession().removeAttribute("GRANT_LIST");
            ServletActionContext.getRequest().getSession().removeAttribute("PoLeadOrg");
            ServletActionContext.getRequest().getSession().removeAttribute("PoLeadPI");
            ServletActionContext.getRequest().getSession().removeAttribute("PoSponsor");
            ServletActionContext.getRequest().getSession().removeAttribute("PoResponsibleContact");
            ServletActionContext.getRequest().getSession().removeAttribute("PoSummary4Sponsor");
        } catch (Exception e) {
            if (e != null && e.getMessage() != null) {
                addActionError(e.getMessage());
            } else {
                addActionError("Please try again");
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Exception occured while submitting trial: " + e);
            return ERROR;
        }
        return "redirect_to_search";
    }

    /**
     * Keeps the user looking at the spinning wheel until a SUCCESS/EXCEPTION
     * occurs.
     * 
     * @return res
     */
    public String showWaitDialog() {
        return "show_ok_create";
    }

    private StudyProtocolDTO createProtocolDTO(String type) {
        StudyProtocolDTO protocolDTO = null;
        if (type.equals("Observational")) {
            protocolDTO = new ObservationalStudyProtocolDTO();
        } else {
            protocolDTO = new InterventionalStudyProtocolDTO();
        }
        protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(protocolWebDTO.getTrialPhase())));
        protocolDTO.setOfficialTitle(StConverter.convertToSt(protocolWebDTO.getTrialTitle()));
        protocolDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(protocolWebDTO.getStartDate())));
        protocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(protocolWebDTO
                .getCompletionDate())));
        protocolDTO.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(protocolWebDTO
                .getStartDateType())));
        protocolDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode
                .getByCode(protocolWebDTO.getCompletionDateType())));
        protocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.getByCode(trialPurpose)));
        return protocolDTO;
    }

    private void createStudyStatus(Ii studyProtocolIi) {
        // create study overall status
        try {
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
            // overallStatusDTO.setIi(IiConverter.convertToIi((Long) null));
            overallStatusDTO.setStudyProtocolIi(studyProtocolIi);
            overallStatusDTO.setStatusCode(CdConverter.convertToCd(TrialStatusCode.getByCode(overallStatusWebDTO
                    .getStatusCode())));
            overallStatusDTO.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(overallStatusWebDTO
                    .getStatusDate())));
            RegistryServiceLocator.getStudyOverallStatusService().create(overallStatusDTO);
        } catch (PAException pae) {
            // pae.printStackTrace();
            LOG.error("Exception occured while creating study overall status: " + pae);
        }
    }

    private void uploadDocument(Ii studyProtocolIi, String docTypeCode, String fileName, File file) {
        try {
            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setStudyProtocolIi(studyProtocolIi);
            docDTO.setTypeCode(CdConverter.convertStringToCd(docTypeCode));
            docDTO.setFileName(StConverter.convertToSt(fileName));
            // docDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser())));
            docDTO.setText(EdConverter.convertToEd(readInputStream(new FileInputStream(file))));
            RegistryServiceLocator.getDocumentService().create(docDTO);
        } catch (PAException pae) {
            // pae.printStackTrace();
            LOG.error("Exception occured while uploading a '" + fileName + "' Exception is" + pae);
        } catch (IOException ioe) {
            // ioe.printStackTrace();
            LOG.error("Exception occured reading '" + fileName + "' Exception is" + ioe);
        }
    }

    private void createIndIdeIndicators(Ii studyProtocolIi) throws PAException {
        ArrayList<IndIdeHolder> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "INDIDE_LIST");
        if (sessionList == null) {
            return;
        }
        if (!(sessionList.size() > 0)) {
            return;
        }
        StudyIndldeDTO indldeDTO = null;
        for (IndIdeHolder holder : sessionList) {
            indldeDTO = new StudyIndldeDTO();
            indldeDTO.setStudyProtocolIi(studyProtocolIi);
            // indldeDTO.setStatusDateRange(statusDateRange)sDateRange(statusDateRange)
            indldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(holder.getIndIde()));
            indldeDTO.setIndldeNumber(StConverter.convertToSt(holder.getNumber()));
            indldeDTO.setGrantorCode(CdConverter.convertStringToCd(holder.getGrantor()));
            indldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(holder.getHolderType()));
            if (holder.getHolderType().equalsIgnoreCase("NIH")) {
                indldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(holder.getProgramCode()));
            }
            if (holder.getHolderType().equalsIgnoreCase("NCI")) {
                indldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(holder.getProgramCode()));
            }
            indldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(holder.getExpandedAccess())));
            indldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(holder.getExpandedAccessType()));
            RegistryServiceLocator.getStudyIndldeService().create(indldeDTO);
        }
    }

    private void createStudyResources(Ii studyProtocolIi) {
        try {
            ArrayList<GrantHolder> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
                    .getAttribute("GRANT_LIST");
            if (sessionList == null) {
                return;
            }
            if (!(sessionList.size() > 0)) {
                return;
            }
            StudyResourcingDTO studyResoureDTO = null;
            for (GrantHolder holder : sessionList) {
                studyResoureDTO = new StudyResourcingDTO();
                studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
                studyResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
                studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(holder.getFundingMechanism()));
                studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(MonitorCode.getByCode(holder
                        .getNciDivisionProgramCode())));
                studyResoureDTO.setNihInstitutionCode(CdConverter.convertStringToCd(holder.getInstituteCode()));
                studyResoureDTO.setSerialNumber(IntConverter.convertToInt(holder.getSerialNumber()));
                // studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest()
                // .getRemoteUser())));
                RegistryServiceLocator.getStudyResourcingService().createStudyResourcing(studyResoureDTO);
            }
        } catch (PAException pae) {
            // pae.printStackTrace();
            LOG.error("Exception occured while creating study participation: " + pae);
        }
    }

    /**
     * query the created protocol and all related associations.
     * 
     * @return res
     */
    public String query() {
        try {
            Ii studyProtocolIi = IiConverter.convertToIi((String) ServletActionContext.getRequest().getSession()
                    .getAttribute(Constants.STUDY_PROTOCOL_II));
            StudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService()
                    .getInterventionalStudyProtocol(studyProtocolIi);
            InterventionalStudyProtocolWebDTO trialWebDTO = new InterventionalStudyProtocolWebDTO(protocolDTO);
            // put an entry in the session and store
            // InterventionalStudyProtocolDTO
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, trialWebDTO);
            // query the study grants
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                    .getstudyResourceByStudyProtocol(studyProtocolIi);
            List<TrialFundingWebDTO> trialFundingList;
            if (!(isoList.isEmpty())) {
                trialFundingList = new ArrayList<TrialFundingWebDTO>();
                for (StudyResourcingDTO dto : isoList) {
                    trialFundingList.add(new TrialFundingWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_FUNDING_LIST, trialFundingList.get(0));
            }
            List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.getStudyOverallStatusService()
                    .getByStudyProtocol(studyProtocolIi);
            List<StudyOverallStatusWebDTO> overallStatusList;
            if (!(overallStatusISOList.isEmpty())) {
                overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();
                for (StudyOverallStatusDTO dto : overallStatusISOList) {
                    overallStatusList.add(new StudyOverallStatusWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest()
                        .setAttribute(Constants.TRIAL_OVERALL_STATUS, overallStatusList.get(0));
            }
            // query the study participation sites
            List<StudyParticipationDTO> studyParticipationISOList = RegistryServiceLocator
                    .getStudyParticipationService().getByStudyProtocol(studyProtocolIi);
            List<StudyParticipationWebDTO> studyParticipationList;
            if (!(studyParticipationISOList.isEmpty())) {
                studyParticipationList = new ArrayList<StudyParticipationWebDTO>();
                for (StudyParticipationDTO dto : studyParticipationISOList) {
                    studyParticipationList.add(new StudyParticipationWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_PARTICIPATION,
                        studyParticipationList.get(0));
            }
            // query the trial documents
            List<DocumentDTO> documentISOList = RegistryServiceLocator.getDocumentService()
                    .getDocumentsByStudyProtocol(studyProtocolIi);
            List<TrialDocumentWebDTO> documentList;
            if (!(documentISOList.isEmpty())) {
                documentList = new ArrayList<TrialDocumentWebDTO>();
                for (DocumentDTO dto : documentISOList) {
                    documentList.add(new TrialDocumentWebDTO(dto));
                }
                for (TrialDocumentWebDTO webdto : documentList) {
                    if (webdto.getTypeCode().equalsIgnoreCase(DocumentTypeCode.Protocol_Document.getCode())) {
                        // put an entry in the session and store Protocol
                        // Document
                        ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, webdto);
                    }
                    if (webdto.getTypeCode().equalsIgnoreCase(DocumentTypeCode.IRB_Approval_Document.getCode())) {
                        // put an entry in the session and store IRB Approval
                        // Document
                        ServletActionContext.getRequest().setAttribute(Constants.IRB_APPROVAL, webdto);
                    }
                }
            }
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
        } catch (Exception e) {
            // e.printStackTrace();
            LOG.error("Exception occured while querying trial " + e);
        }
        return VIEW_TRIAL;
    }

    /**
     * 
     * @return result
     */
    // public String displayOrg() {
    public String displayLeadOrganization() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (orgId.equals("undefined")) {
            return "display_org";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        try {
            selectedLeadOrg = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria).get(0);
            ServletActionContext.getRequest().getSession().setAttribute("PoLeadOrg", selectedLeadOrg);
        } catch (Exception e) {
            return "display_lead_org";
        }
        return "display_lead_org";
    }

    /**
     * 
     * @return result
     */
    // public String displayPerson() {
    public String displayLeadPrincipalInvestigator() {
        String persId = ServletActionContext.getRequest().getParameter("persId");
        try {
            selectedLeadPrincipalInvestigator = RegistryServiceLocator.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            ServletActionContext.getRequest().getSession().setAttribute("PoLeadPI", selectedLeadPrincipalInvestigator);
        } catch (Exception e) {
            return "display_lead_prinicipal_inv";
        }
        return "display_lead_prinicipal_inv";
    }

    /**
     * 
     * @return result
     */
    // public String displayOrg() {
    public String displaySelectedSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (orgId.equals("undefined")) {
            return "display_selected_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        try {
            selectedSponsor = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria).get(0);
            ServletActionContext.getRequest().getSession().setAttribute("PoSponsor", selectedSponsor);
        } catch (Exception e) {
            return "display_selected_sponsor";
        }
        return "display_selected_sponsor";
    }

    /**
     * 
     * @return result
     */
    // public String displayOrg() {
    public String displaySummary4FundingSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (orgId.equals("undefined")) {
            return "display_summary4funding_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        try {
            selectedSummary4Sponsor = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria).get(0);
            ServletActionContext.getRequest().getSession().setAttribute("PoSummary4Sponsor", selectedSummary4Sponsor);
        } catch (Exception e) {
            return "display_summary4funding_sponsor";
        }
        return "display_summary4funding_sponsor";
    }

    /**
     * @return result
     */
    public String viewDoc() {
        LOG.info("Entering viewProtocolDoc");
        try {
            DocumentDTO docDTO = RegistryServiceLocator.getDocumentService().get(IiConverter.convertToIi(id));
            InterventionalStudyProtocolWebDTO spDTO = (InterventionalStudyProtocolWebDTO) ServletActionContext
                    .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
            StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
            sb.append(File.separator).append(spDTO.getNciAccessionNumber()).append(File.separator).append(
                    docDTO.getIdentifier().getExtension()).append('-').append(docDTO.getFileName().getValue());
            File downloadFile = new File(sb.toString());
            servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/x-unknown");
            FileInputStream fileToDownload = new FileInputStream(downloadFile);
            servletResponse.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
            servletResponse.setContentLength(fileToDownload.available());
            int data;
            ServletOutputStream out = servletResponse.getOutputStream();
            while ((data = fileToDownload.read()) != -1) {
                out.write(data);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException err) {
            LOG.error("TrialDocumentAction failed with FileNotFoundException: " + err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            query();
            return ERROR;
        } catch (Exception e) {
            // e.printStackTrace();
            LOG.error("Exception occured while retrieving document " + e);
        }
        return NONE;
    }

    /**
     * validate the submit trial form elements.
     */
    private void validateForm() {
        if (PAUtil.isEmpty(participationWebDTO.getLocalProtocolIdentifier())) {
            addFieldError("participationWebDTO.localProtocolIdentifier",
                    getText("error.submit.localProtocolIdentifier"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getTrialTitle())) {
            addFieldError("protocolWebDTO.trialTitle", getText("error.submit.trialTitle"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getTrialPhase())) {
            addFieldError("protocolWebDTO.trialPhase", getText("error.submit.trialPhase"));
        }
        if (PAUtil.isEmpty(overallStatusWebDTO.getStatusCode())) {
            addFieldError("overallStatusWebDTO.statusCode", getText("error.submit.statusCode"));
        }
        if (PAUtil.isEmpty(trialType)) {
            addFieldError("trialType", getText("error.submit.trialType"));
        }
        if (PAUtil.isEmpty(trialPurpose)) {
            addFieldError("trialPurpose", getText("error.submit.trialPurpose"));
        }
        if (PAUtil.isEmpty(overallStatusWebDTO.getStatusDate())) {
            addFieldError("overallStatusWebDTO.statusDate", getText("error.submit.statusDate"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getStartDate())) {
            addFieldError("protocolWebDTO.startDate", getText("error.submit.startDate"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getStartDateType())) {
            addFieldError("protocolWebDTO.startDateType", getText("error.submit.dateType"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getCompletionDate())) {
            addFieldError("protocolWebDTO.completionDate", getText("error.submit.completionDate"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getCompletionDateType())) {
            addFieldError("protocolWebDTO.completionDateType", getText("error.submit.dateType"));
        }
        if (PAUtil.isEmpty(protocolDocFileName)) {
            addFieldError("trialDocumentWebDTO.protocolDocFileName", getText("error.submit.protocolDocument"));
        }
        if (PAUtil.isEmpty(irbApprovalFileName)) {
            addFieldError("trialDocumentWebDTO.irbApprovalFileName", getText("error.submit.irbApproval"));
        }
        if (PAUtil.isEmpty(contactEmail)) {
            addFieldError("contactEmail", getText("error.submit.contactEmail"));
        }
        if (PAUtil.isEmpty(contactPhone)) {
            addFieldError("contactPhone", getText("error.submit.contactPhone"));
        }
        // check if the contact e-mail address is valid
        if (PAUtil.isNotEmpty(contactEmail)) {
            if (!isValidEmailAddress(contactEmail)) {
                addFieldError("contactEmail",
                        getText("error.submit.invalidContactEmailAddress"));                
            }
            
        }


        // LeadOrgNotSelected;check the session;
        selectedLeadOrg = (OrganizationDTO) ServletActionContext.getRequest().getSession().getAttribute("PoLeadOrg");
        if (selectedLeadOrg == null) {
            addFieldError("LeadOrgNotSelected", getText("error.submit.leadOrganization"));
        }
        selectedLeadPrincipalInvestigator = (PersonDTO) ServletActionContext.getRequest().getSession().getAttribute(
                "PoLeadPI");
        if (selectedLeadPrincipalInvestigator == null) {
            addFieldError("LeadPINotSelected", getText("error.submit.leadPrincipalInvestigator"));
        }
        selectedSponsor = (OrganizationDTO) ServletActionContext.getRequest().getSession().getAttribute("PoSponsor");
        if (selectedSponsor == null) {
            addFieldError("SponsorNotSelected", getText("error.submit.sponsor"));
        }
        if (!(respparty.equals("pi"))) {
            responsiblePartyContact = (PersonDTO) ServletActionContext.getRequest().getSession().getAttribute(
                    "PoResponsibleContact");
            if (responsiblePartyContact == null) {
                addFieldError("ResponsiblePartyNotSelected", getText("error.submit.sponsorResponsibelParty"));
            }
        }
        // validate trial status and dates
        validateTrialDates();

    }
    
    /**
     * validate the submit trial dates.
     */
    private void validateTrialDates() {
        // Constraint/Rule: 18 Current Trial Status Date must be current or past.
        if (PAUtil.isNotEmpty(overallStatusWebDTO.getStatusDate())) {
            Timestamp statusDate = PAUtil.dateStringToTimestamp(overallStatusWebDTO.getStatusDate());
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(statusDate)) {
                addFieldError("overallStatusWebDTO.statusDate", 
                        getText("error.submit.invalidStatusDate"));                
            }
        }        
        // Constraint/Rule:  21 If Current Trial Status is ‘Active’, Trial Start Date must be the same as 
        // Current Trial Status Date and have ‘actual’ type.
        if (PAUtil.isNotEmpty(overallStatusWebDTO.getStatusCode())
                && PAUtil.isNotEmpty(overallStatusWebDTO.getStatusDate())
                        && PAUtil.isNotEmpty(protocolWebDTO.getStartDate())
                         && PAUtil.isNotEmpty(protocolWebDTO.getStartDateType())) {
          if (TrialStatusCode.ACTIVE.getCode().equals(
                          overallStatusWebDTO.getStatusCode())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(overallStatusWebDTO.getStatusDate());
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(protocolWebDTO.getStartDate());
              if (!statusDate.equals(trialStartDate) 
                              || !protocolWebDTO.getStartDateType().equals(
                                  ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  addFieldError("protocolWebDTO.startDate", getText("error.submit.invalidStartDate"));
              }                
          }            
        }        
        // Constraint/Rule: 22 If Current Trial Status is ‘Approved’, Trial Start Date must have ‘anticipated’ type. 
        //  Trial Start Date must have ‘actual’ type for any other Current Trial Status value besides ‘Approved’. 
        if (PAUtil.isNotEmpty(overallStatusWebDTO.getStatusCode())
                         && PAUtil.isNotEmpty(protocolWebDTO.getStartDateType())) {
          if (TrialStatusCode.APPROVED.getCode().equals(
                          overallStatusWebDTO.getStatusCode())) {
              if (!protocolWebDTO.getStartDateType().equals(
                              ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  addFieldError("protocolWebDTO.startDateType", 
                          getText("error.submit.invalidStartDateTypeApproved"));
              }                
          } else {
              if (!protocolWebDTO.getStartDateType().equals(
                       ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                          addFieldError("protocolWebDTO.startDateType", 
                                  getText("error.submit.invalidStartDateTypeOther"));
              }              
          }
        }        
        // Constraint/Rule: 23 If Current Trial Status is ‘Completed’, Primary Completion Date must be the 
        // same as Current Trial Status Date and have ‘actual’ type.
        if (PAUtil.isNotEmpty(overallStatusWebDTO.getStatusCode())
                && PAUtil.isNotEmpty(overallStatusWebDTO.getStatusDate())
                        && PAUtil.isNotEmpty(protocolWebDTO.getCompletionDate())
                         && PAUtil.isNotEmpty(protocolWebDTO.getCompletionDateType())) {
          if (TrialStatusCode.COMPLETE.getCode().equals(
                          overallStatusWebDTO.getStatusCode())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(overallStatusWebDTO.getStatusDate());
              Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(protocolWebDTO.getCompletionDate());
              if (!statusDate.equals(trialCompletionDate) 
                      ||  !protocolWebDTO.getCompletionDateType().equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                              addFieldError("protocolWebDTO.completionDate", 
                                  getText("error.submit.invalidCompletionDate"));
              }                
          }            
        }        
        // Constraint/Rule: 24 If Current Trial Status is ‘Completed’ or ‘Administratively Completed’, 
        // Primary Completion Date must have ‘actual’ type. Primary Completion Date must have ‘anticipated’ type 
        // for any other Current Trial Status value besides ‘Completed’ or ‘Administratively Completed’. 
        if (PAUtil.isNotEmpty(overallStatusWebDTO.getStatusCode())
                && PAUtil.isNotEmpty(protocolWebDTO.getCompletionDateType())) {
          if (TrialStatusCode.COMPLETE.getCode().equals(overallStatusWebDTO.getStatusCode()) 
                  || TrialStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().
                          equals(overallStatusWebDTO.getStatusCode())) {
              if (!protocolWebDTO.getCompletionDateType().equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                              addFieldError("protocolWebDTO.completionDateType", 
                                     getText("error.submit.invalidCompletionDateType"));
              }
             
          } else {
              
              if (PAUtil.isNotEmpty(protocolWebDTO.getCompletionDateType())
                      && !protocolWebDTO.getCompletionDateType().equals(
                              ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                                  addFieldError("protocolWebDTO.completionDateType", 
                                          getText("error.submit.invalidCompletionDateTypeOther"));                  
              }
              
          }          
        }        
        // Constraint/Rule:25 Trial Start Date must be same/smaller than Primary Completion Date. 
        if (PAUtil.isNotEmpty(protocolWebDTO.getStartDate())
                        && PAUtil.isNotEmpty(protocolWebDTO.getCompletionDate())) {
            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(protocolWebDTO.getStartDate());
            Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(protocolWebDTO.getCompletionDate());
            if (trialCompletionDate.before(trialStartDate)) {
                addFieldError("protocolWebDTO.startDate", 
                        getText("error.submit.invalidTrialDates"));                
            }
        }        
        // Constraint/Rule: 19 Trial Start Date must be current/past if ‘actual’ trial start date type 
        // is selected and must be future if ‘anticipated’ trial start date type is selected.
        if (PAUtil.isNotEmpty(protocolWebDTO.getStartDate())
                         && PAUtil.isNotEmpty(protocolWebDTO.getStartDateType())) {
            if (protocolWebDTO.getStartDateType().equals(
                    ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                Timestamp trialStartDate = PAUtil.dateStringToTimestamp(protocolWebDTO.getStartDate());
                Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.before(trialStartDate)) {
                    addFieldError("protocolWebDTO.startDate", 
                            getText("error.submit.invalidActualStartDate"));                
                }
                
            } else if (protocolWebDTO.getStartDateType().equals(
                        ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(
                                    protocolWebDTO.getStartDate());
                            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                            if (currentTimeStamp.after(trialStartDate)) {
                                addFieldError("protocolWebDTO.startDate", 
                                        getText("error.submit.invalidAnticipatedStartDate"));                
                            }
            }          
        }        
        // Constraint/Rule: 20 Primary Completion Date must be current/past if ‘actual’ 
        // primary completion date type is selected and must be future if ‘anticipated’ 
        // trial primary completion date type is selected.
        if (PAUtil.isNotEmpty(protocolWebDTO.getCompletionDate())
                         && PAUtil.isNotEmpty(protocolWebDTO.getCompletionDateType())) {
            if (protocolWebDTO.getCompletionDateType().equals(
                    ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                        Timestamp completionDate = PAUtil.dateStringToTimestamp(
                                protocolWebDTO.getCompletionDate());
                        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                        if (currentTimeStamp.before(completionDate)) {
                            addFieldError("protocolWebDTO.completionDate", 
                                    getText("error.submit.invalidActualCompletionDate"));                
                        }
                
            } else if (protocolWebDTO.getCompletionDateType().equals(
                    ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                        Timestamp completionDate = PAUtil.dateStringToTimestamp(
                                protocolWebDTO.getCompletionDate());
                        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                        if (currentTimeStamp.after(completionDate)) {
                            addFieldError("protocolWebDTO.completionDate", 
                                    getText("error.submit.invalidAnticipatedCompletionDate"));                
                        }
            }          
        }        
    }
    
    private  boolean isValidEmailAddress(String emailAddress)  {
        boolean isvalidEmailAddr = false;
        Pattern email = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");

        Matcher fit = email.matcher(emailAddress);
        if (fit.matches()) {
            isvalidEmailAddr = true;
        } 
        return isvalidEmailAddr;
    }

    /**
     * @return result
     */
    public String input() {
        return INPUT;
    }

    /** Read an input stream in its entirety into a byte array. */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        int bufSize = MAXF * MAXF;
        byte[] content;
        List<byte[]> parts = new LinkedList();
        InputStream in = new BufferedInputStream(inputStream);
        byte[] readBuffer = new byte[bufSize];
        byte[] part = null;
        int bytesRead = 0;
        // read everyting into a list of byte arrays
        while ((bytesRead = in.read(readBuffer, 0, bufSize)) != -1) {
            part = new byte[bytesRead];
            System.arraycopy(readBuffer, 0, part, 0, bytesRead);
            parts.add(part);
        }
        // calculate the total size
        int totalSize = 0;
        for (byte[] partBuffer : parts) {
            totalSize += partBuffer.length;
        }
        // allocate the array
        content = new byte[totalSize];
        int offset = 0;
        for (byte[] partBuffer : parts) {
            System.arraycopy(partBuffer, 0, content, offset, partBuffer.length);
            offset += partBuffer.length;
        }
        return content;
    }

    private void resetValuesFromSession() {
        ArrayList<IndIdeHolder> sessionList1 = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "INDIDE_LIST");
        if (sessionList1 != null && sessionList1.size() > 0) {
            for (int i = 0; i < sessionList1.size(); i++) {
                ideInd.add(sessionList1.get(i));
            }
        }
        ArrayList<GrantHolder> sessionList2 = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "GRANT_LIST");
        if (sessionList2 != null && sessionList2.size() > 0) {
            for (int i = 0; i < sessionList2.size(); i++) {
                grants.add(sessionList2.get(i));
            }
        }
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
        IndIdeHolder indIdeHolder = new IndIdeHolder();
        indIdeHolder.setExpandedAccess(expandedAccess.equals("") ? "-" : expandedAccess);
        indIdeHolder.setExpandedAccessType(expandedAccessType.equals("") ? "-" : expandedAccessType);
        indIdeHolder.setGrantor(grantor.equals("") ? "-" : grantor);
        indIdeHolder.setHolderType(holderType.equals("") ? "-" : holderType);
        indIdeHolder.setNumber(number.equals("") ? "-" : number);
        indIdeHolder.setProgramCode(programCode.equals("") ? "-" : programCode);
        indIdeHolder.setIndIde(indIde.equals("") ? "-" : indIde);
        indIdeHolder.setRowId(UUID.randomUUID().toString());
        ArrayList<IndIdeHolder> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "INDIDE_LIST");
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                ideInd.add(sessionList.get(i));
            }
            ideInd.add(indIdeHolder);
            sessionList.add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute("INDIDE_LIST", sessionList);
        } else {
            ideInd.add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute("INDIDE_LIST", ideInd);
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
        ArrayList<IndIdeHolder> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "INDIDE_LIST");
        IndIdeHolder holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (IndIdeHolder) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        ideInd.clear();
        for (int i = 0; i < sessionList.size(); i++) {
            ideInd.add(sessionList.get(i));
        }
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
        GrantHolder grantHolder = new GrantHolder();
        grantHolder.setFundingMechanism(fundingMechanismCode);
        grantHolder.setInstituteCode(nihInstitutionCode);
        grantHolder.setSerialNumber(serialNumber);
        grantHolder.setNciDivisionProgramCode(nciDivisionProgramCode);
        grantHolder.setRowId(UUID.randomUUID().toString());
        ArrayList<GrantHolder> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "GRANT_LIST");
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                grants.add(sessionList.get(i));
            }
            grants.add(grantHolder);
            sessionList.add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute("GRANT_LIST", sessionList);
        } else {
            grants.add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute("GRANT_LIST", grants);
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
        ArrayList<GrantHolder> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().getAttribute(
                "GRANT_LIST");
        GrantHolder holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (GrantHolder) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        grants.clear();
        for (int i = 0; i < sessionList.size(); i++) {
            grants.add(sessionList.get(i));
        }
        // ideIndList.setList(ideInd);
        return "display_grants";
    }

    /**
     * 
     * @return res
     */
    public String createOrganizationContacts() {
        boolean contactExists = false;
        try {
            String orgId = ServletActionContext.getRequest().getParameter("orgId");
            String persId = ServletActionContext.getRequest().getParameter("persId");
            OrganizationalContactDTO dto = new OrganizationalContactDTO();
//            dto.setOrganizationIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(orgId));
//            dto.getOrganizationIdentifier().setRoot("UID.for.nci.entity.organization");
//            dto.getOrganizationIdentifier().setIdentifierName("NCI organization entity identifier");
            dto.setScoperIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(orgId));
            dto.getScoperIdentifier().setRoot("UID.for.nci.entity.organization");
            dto.getScoperIdentifier().setIdentifierName("NCI organization entity identifier");
            // Use these two values and check if the contact already exists, if
            // they do then this means that the user selected from the list and
            // did not create a new user
            List<OrganizationalContactDTO> list = RegistryServiceLocator.getPoOrganizationalContactCorrelationService()
                    .search(dto);
            //             
            for (OrganizationalContactDTO contactDTO : list) {
//                String persIdfromOrgContact = contactDTO.getPersonIdentifier().getExtension();
                String persIdfromOrgContact = contactDTO.getPlayerIdentifier().getExtension();
                if (persIdfromOrgContact.equals(persId)) {
                    contactExists = true;
                }
            }
            if (!contactExists) {
//                dto.setPersonIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(persId));
//                dto.getPersonIdentifier().setRoot("UID.for.nci.entity.person");
//                dto.getPersonIdentifier().setIdentifierName("NCI person entity identifier");
                dto.setPlayerIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(persId));
                dto.getPlayerIdentifier().setRoot("UID.for.nci.entity.person");
                dto.getPlayerIdentifier().setIdentifierName("NCI person entity identifier");
                dto.setPrimaryIndicator(RemoteApiUtil.convertToBl(Boolean.TRUE));
                RegistryServiceLocator.getPoOrganizationalContactCorrelationService().createCorrelation(dto);
            }
            Ii personIi = gov.nih.nci.pa.iso.util.IiConverter.convertToIi(persId);
            personIi.setRoot("UID.for.nci.entity.organization");
            responsiblePartyContact = RegistryServiceLocator.getPoPersonEntityService().getPerson(personIi);
            ServletActionContext.getRequest().getSession()
                    .setAttribute("PoResponsibleContact", responsiblePartyContact);
        } catch (NullifiedEntityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EntityValidationException e) {
            // TODO Auto-generated catch block
        } catch (PAException e) {
            // TODO Auto-generated catch block
//        } catch (NullifiedRoleException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
        return "display_responsible_contact";
    }

    /**
     * 
     * @return res
     */
    public String addGrants() {
        return null;
    }

    /**
     * @return protocolWebDTO
     */
    public InterventionalStudyProtocolWebDTO getProtocolWebDTO() {
        return protocolWebDTO;
    }

    /**
     * @param protocolWebDTO protocolWebDTO
     */
    public void setProtocolWebDTO(InterventionalStudyProtocolWebDTO protocolWebDTO) {
        this.protocolWebDTO = protocolWebDTO;
    }

    /**
     * @return the overallStatusWebDTO
     */
    public StudyOverallStatusWebDTO getOverallStatusWebDTO() {
        return overallStatusWebDTO;
    }

    /**
     * @param overallStatusWebDTO the overallStatusWebDTO to set
     */
    public void setOverallStatusWebDTO(StudyOverallStatusWebDTO overallStatusWebDTO) {
        this.overallStatusWebDTO = overallStatusWebDTO;
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
     * @return the trialDocumentWebDTO
     */
    public TrialDocumentWebDTO getTrialDocumentWebDTO() {
        return trialDocumentWebDTO;
    }

    /**
     * @param trialDocumentWebDTO the trialDocumentWebDTO to set
     */
    public void setTrialDocumentWebDTO(TrialDocumentWebDTO trialDocumentWebDTO) {
        this.trialDocumentWebDTO = trialDocumentWebDTO;
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
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     * @return protocolDoc
     */
    public File getProtocolDoc() {
        return protocolDoc;
    }

    /**
     * @param protocolDoc protocolDoc
     */
    public void setProtocolDoc(File protocolDoc) {
        this.protocolDoc = protocolDoc;
    }

    /**
     * @return protocolDocFileName
     */
    public String getProtocolDocFileName() {
        return protocolDocFileName;
    }

    /**
     * @param protocolDocFileName protocolDocFileName
     */
    public void setProtocolDocFileName(String protocolDocFileName) {
        this.protocolDocFileName = protocolDocFileName;
    }

    /**
     * @return irbApproval
     */
    public File getIrbApproval() {
        return irbApproval;
    }

    /**
     * @param irbApproval irbApproval
     */
    public void setIrbApproval(File irbApproval) {
        this.irbApproval = irbApproval;
    }

    /**
     * @return irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
        return irbApprovalFileName;
    }

    /**
     * @param irbApprovalFileName irbApprovalFileName
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalFileName = irbApprovalFileName;
    }

    /**
     * @return the ideInd
     */
    public ArrayList<IndIdeHolder> getIdeInd() {
        return ideInd;
    }

    /**
     * @param ideInd the ideInd to set
     */
    public void setIdeInd(ArrayList<IndIdeHolder> ideInd) {
        this.ideInd = ideInd;
    }

    /**
     * @return the selectedLeadOrg
     */
    public OrganizationDTO getSelectedLeadOrg() {
        return selectedLeadOrg;
    }

    /**
     * @param selectedLeadOrg the selectedLeadOrg to set
     */
    public void setSelectedLeadOrg(OrganizationDTO selectedLeadOrg) {
        this.selectedLeadOrg = selectedLeadOrg;
    }

    /**
     * @return the selectedLeadPrincipalInvestigator
     */
    public PersonDTO getSelectedLeadPrincipalInvestigator() {
        return selectedLeadPrincipalInvestigator;
    }

    /**
     * @param selectedLeadPrincipalInvestigator the
     *            selectedLeadPrincipalInvestigator to set
     */
    public void setSelectedLeadPrincipalInvestigator(PersonDTO selectedLeadPrincipalInvestigator) {
        this.selectedLeadPrincipalInvestigator = selectedLeadPrincipalInvestigator;
    }

    /**
     * @return the selectedSponsor
     */
    public OrganizationDTO getSelectedSponsor() {
        return selectedSponsor;
    }

    /**
     * @param selectedSponsor the selectedSponsor to set
     */
    public void setSelectedSponsor(OrganizationDTO selectedSponsor) {
        this.selectedSponsor = selectedSponsor;
    }

    /**
     * @return the responsiblePartyContact
     */
    public PersonDTO getResponsiblePartyContact() {
        return responsiblePartyContact;
    }

    /**
     * @param responsiblePartyContact the responsiblePartyContact to set
     */
    public void setResponsiblePartyContact(PersonDTO responsiblePartyContact) {
        this.responsiblePartyContact = responsiblePartyContact;
    }

    /**
     * @return the selectedSummary4Sponsor
     */
    public OrganizationDTO getSelectedSummary4Sponsor() {
        return selectedSummary4Sponsor;
    }

    /**
     * @param selectedSummary4Sponsor the selectedSummary4Sponsor to set
     */
    public void setSelectedSummary4Sponsor(OrganizationDTO selectedSummary4Sponsor) {
        this.selectedSummary4Sponsor = selectedSummary4Sponsor;
    }

    /**
     * @return the grants
     */
    public ArrayList<GrantHolder> getGrants() {
        return grants;
    }

    /**
     * @param grants the grants to set
     */
    public void setGrants(ArrayList<GrantHolder> grants) {
        this.grants = grants;
    }

    /**
     * @return the trialType
     */
    public String getTrialType() {
        return trialType;
    }

    /**
     * @param trialType the trialType to set
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
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
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocumentFileName;
    }

    /**
     * @param informedConsentDocumentFileName the
     *            informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(String informedConsentDocumentFileName) {
        this.informedConsentDocumentFileName = informedConsentDocumentFileName;
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
     * @return the respparty
     */
    public String getRespparty() {
        return respparty;
    }

    /**
     * @param respparty the respparty to set
     */
    public void setRespparty(String respparty) {
        this.respparty = respparty;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return the trialPurpose
     */
    public String getTrialPurpose() {
        return trialPurpose;
    }

    /**
     * @param trialPurpose the trialPurpose to set
     */
    public void setTrialPurpose(String trialPurpose) {
        this.trialPurpose = trialPurpose;
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
     * @return the summary4FundingCategory
     */
    public String getSummary4FundingCategory() {
        return summary4FundingCategory;
    }

    /**
     * @param summary4FundingCategory the summary4FundingCategory to set
     */
    public void setSummary4FundingCategory(String summary4FundingCategory) {
        this.summary4FundingCategory = summary4FundingCategory;
    }

    /**
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }

}
