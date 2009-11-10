/**
 * 
 */
package gov.nih.nci.pa.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyRelationshipTypeCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.NPathComplexity" , "PMD.ExcessiveParameterList" ,
    "PMD.ExcessiveClassLength" , "PMD.TooManyMethods" , "PMD.ExcessiveMethodLength" , "PMD.TooManyFields" })
@Interceptors(HibernateSessionInterceptor.class)
public class TrialRegistrationBeanLocal implements TrialRegistrationServiceLocal { 

    @EJB
     StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
     StudyRelationshipServiceLocal studyRelationshipService = null;
    @EJB
     StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB
     StudyIndldeServiceLocal studyIndldeService  = null;
    @EJB
     StudyResourcingServiceLocal studyResourcingService = null;
    @EJB
     StudyMilestoneServicelocal studyMilestoneService = null;
    @EJB
     DocumentServiceLocal documentService = null;
    @EJB
     StudyDiseaseServiceLocal studyDiseaseService = null;
    @EJB
     ArmServiceLocal armService = null;
    @EJB
     PlannedActivityServiceLocal plannedActivityService = null;
    @EJB
     StratumGroupServiceLocal subGroupsService = null;
    @EJB
     StudySiteServiceLocal studySiteService = null;
    @EJB
     StudySiteContactServiceLocal studySiteContactService = null;
    @EJB
     StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
    @EJB
     StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = null;
    @EJB
     StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = null;
    @EJB
     OrganizationCorrelationServiceRemote ocsr = null;
    @EJB 
     StudyContactServiceRemote studyContactService = null;
    @EJB
     TSRReportGeneratorServiceRemote tsrReportService = null;
    @EJB
     DocumentWorkflowStatusServiceLocal docWrkFlowStatusService = null;
    @EJB
     RegulatoryInformationServiceRemote regulatoryInfoBean = null;
    @EJB
     StudyRecruitmentStatusServiceLocal studyRecruitmentStatusServiceLocal = null;
    @EJB
     StudyObjectiveServiceLocal studyObjectiveService = null;
    @EJB
     StudySiteOverallStatusServiceLocal studySiteOverallStatusService = null;
    @EJB
     AbstractionCompletionServiceRemote abstractionCompletionService = null;
    @EJB
     StudyInboxServiceLocal studyInboxServiceLocal = null;
    @EJB
     MailManagerServiceLocal mailManagerSerivceLocal = null;

   private static final String CREATE = "Create";
   private static final String AMENDMENT = "Amendment";
   private static final String UPDAT = "Update";
   private static final String REJECTION = "Reject";
   private static PAServiceUtils paServiceUtils = new PAServiceUtils();

   private static final String PROTOCOL_ID_NULL = "Study Protocol Identifer is null";
   private static final String NO_PROTOCOL_FOUND = "No Study Protocol found for = ";
   private static final String EMAIL_NOT_NULL = "Email cannot be null, ";
   private static final String PHONE_NOT_NULL = "Phone cannot be null, ";
   private SessionContext ejbContext;
   private static final String VALIDATION_EXCEPTION = "Validation Exception ";

  @Resource
  void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
  }


 /**
  * An action plan and execution of a pre-clinical or clinical study including
  * all activities to test a particular hypothesis that is the basis of the study
  * regarding the effectiveness of a particular treatment, drug, device,
  * procedure, or care plan. This includes prevention, observational,
  * therapeutic, and other types of studies that involve subjects.:{@link URL}. 
  * <ul>
  * <li>
  * </ul>
  *
  * @param studyProtocolDTO StudyProtocolDTO
  * @param overallStatusDTO OverallStatusDTO
  * @param studyIndldeDTOs list of Study Ind/ides
  * @param studyResourcingDTOs list of nih grants
  * @param documentDTOs list of documents
  * @param leadOrganizationDTO lead organization
  * @param principalInvestigatorDTO Principal Investigator
  * @param sponsorOrganizationDTO Sponsor Organization
  * @param leadOrganizationSiteIdentifierDTO local protocol identifier
  * @param nctIdentifierSiteIdentifierDTO nct Identifier
  * @param studyContactDTO phone and email info when Pi is responsible
  * @param studySiteContactDTO phone and email info when sponsor is responsible
  * @param summary4organizationDTO summary 4 organization code
  * @param summary4studyResourcingDTO summary 4 category code
  * @param responsiblePartyContactIi id of the person when sponsor is responsible
  * @param isBatchMode to identify if  batch is caller 
  * @return ii of Study Protocol
  * @throws PAException on error
  */

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public Ii createInterventionalStudyProtocol(
        StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO ,
        List<StudyIndldeDTO> studyIndldeDTOs ,
        List<StudyResourcingDTO> studyResourcingDTOs ,
        List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrganizationDTO ,
        PersonDTO principalInvestigatorDTO ,
        OrganizationDTO sponsorOrganizationDTO ,
        StudySiteDTO leadOrganizationSiteIdentifierDTO ,
        StudySiteDTO nctIdentifierSiteIdentifierDTO ,
        StudyContactDTO studyContactDTO ,
        StudySiteContactDTO studySiteContactDTO ,
        OrganizationDTO summary4organizationDTO ,
        StudyResourcingDTO summary4studyResourcingDTO ,
        Ii responsiblePartyContactIi ,
        Bl isBatchMode)
 throws PAException {

    Ii studyProtocolIi = null;
    try {
        if (PAUtil.isListNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
                studyResourcingDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
                studyResourcingDTO.setOrganizationIdentifier(null);
                studyResourcingDTO.setTypeCode(null);
            }
        }
    studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
    studyProtocolIi = createStudyProtocolObjs(
            studyProtocolDTO ,
            overallStatusDTO ,
            studyIndldeDTOs ,
            studyResourcingDTOs ,
            documentDTOs ,
            leadOrganizationDTO ,
            principalInvestigatorDTO ,
            sponsorOrganizationDTO ,
            leadOrganizationSiteIdentifierDTO ,
            nctIdentifierSiteIdentifierDTO ,
            studyContactDTO ,
            studySiteContactDTO ,
            summary4organizationDTO ,
            summary4studyResourcingDTO ,
            responsiblePartyContactIi, CREATE, isBatchMode);
    } catch (Exception e) {
        ejbContext.setRollbackOnly();
        throw new PAException(e);
    }
    return studyProtocolIi;


 }

 /**
  * An action plan and execution of a pre-clinical for amending an existing protocols.
  * <ul>
  * <li>
  * </ul>
  *
  * @param studyProtocolDTO StudyProtocolDTO
  * @param overallStatusDTO OverallStatusDTO
  * @param studyIndldeDTOs list of Study Ind/ides
  * @param studyResourcingDTOs list of nih grants
  * @param documentDTOs list of documents
  * @param leadOrganizationDTO Pead organization
  * @param principalInvestigatorDTO Principal Investigator
  * @param sponsorOrganizationDTO Sponsort Organization
  * @param leadOrganizationSiteIdentifierDTO local protocol identifier
  * @param nctIdentifierSiteIdentifierDTO nct Identifier
  * @param studyContactDTO phone and email info when Pi is responsible
  * @param studySiteContactDTO phone and email info when sponsor is responsible
  * @param summary4organizationDTO summary 4 organization code
  * @param summary4studyResourcingDTO summary 4 category code
  * @param responsiblePartyContactIi id of the person when sponsor is responsible
  * @param isBatchMode to identify if  batch is caller
  * @return ii of Study Protocol
  * @throws PAException on error
  */

 @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
 public Ii amend(
        StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO ,
        List<StudyIndldeDTO> studyIndldeDTOs ,
        List<StudyResourcingDTO> studyResourcingDTOs ,
        List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrganizationDTO ,
        PersonDTO principalInvestigatorDTO ,
        OrganizationDTO sponsorOrganizationDTO ,
        StudySiteDTO leadOrganizationSiteIdentifierDTO ,
        StudySiteDTO nctIdentifierSiteIdentifierDTO ,
        StudyContactDTO studyContactDTO ,
        StudySiteContactDTO studySiteContactDTO ,
        OrganizationDTO summary4organizationDTO ,
        StudyResourcingDTO summary4studyResourcingDTO ,
        Ii responsiblePartyContactIi ,
        Bl isBatchMode)
 throws PAException {
    
    try {      
        validateStudyExist(studyProtocolDTO, AMENDMENT);
        updateStudyProtocolObjs(
            getStudyProtocolForCreateOrAmend(studyProtocolDTO, AMENDMENT),
            overallStatusDTO ,
            leadOrganizationSiteIdentifierDTO ,
            nctIdentifierSiteIdentifierDTO,
            studyIndldeDTOs ,
            studyResourcingDTOs ,
            documentDTOs ,
            leadOrganizationDTO ,
            principalInvestigatorDTO,
            sponsorOrganizationDTO,
            studyContactDTO ,
            studySiteContactDTO,
            summary4organizationDTO ,
            summary4studyResourcingDTO ,
            responsiblePartyContactIi , 
            null, 
            null, 
            null,
            null , AMENDMENT, isBatchMode);
    } catch (Exception e) {
        ejbContext.setRollbackOnly();
        throw new PAException(e);
    }            
    
    return studyProtocolDTO.getIdentifier();

 }

 /**
  * An action plan and execution of a pre-clinical for Updating an existing protocols.
  * <ul>
  * <li>
  * </ul>
  *
  * @param studyProtocolDTO StudyProtocolDTO
  * @param overallStatusDTO OverallStatusDTO
  * @param nctIdentifierSiteIdentifierDTO StudysiteDTO
  * @param studyIndldeDTOs list of Study Ind/ides
  * @param studyResourcingDTOs list of nih grants
  * @param documentDTOs IRB document
  * @param studyContactDTO phone and email info when Pi is responsible
  * @param studyParticipationContactDTO StudySiteContactDTO 
  * @param summary4organizationDTO summary 4 organization code
  * @param summary4studyResourcingDTO summary 4 category code
  * @param responsiblePartyContactIi id of the person when sponsor is responsible
  * @param studyRegAuthDTO updated studyRegAuthDTO
  * @param collaboratorDTOs list of updated collaborators
  * @param studySiteAccrualStatusDTOs list of updated participating sites 
  * @param studySiteDTOs  list of StudySite DTOs with updated program code
  * @param isBatchMode to identify if  batch is caller
  * @throws PAException on error
  */
  @SuppressWarnings({"PMD.ExcessiveMethodLength" })
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void update(
    StudyProtocolDTO studyProtocolDTO ,
    StudyOverallStatusDTO overallStatusDTO ,
    StudySiteDTO nctIdentifierSiteIdentifierDTO,
    List<StudyIndldeDTO> studyIndldeDTOs ,
    List<StudyResourcingDTO> studyResourcingDTOs ,
    List<DocumentDTO> documentDTOs ,
    StudyContactDTO studyContactDTO ,
    StudySiteContactDTO studyParticipationContactDTO, 
    OrganizationDTO summary4organizationDTO ,
    StudyResourcingDTO summary4studyResourcingDTO ,
    Ii responsiblePartyContactIi, 
    StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
    List<StudySiteDTO> collaboratorDTOs, 
    List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
    List<StudySiteDTO> studySiteDTOs ,
    Bl isBatchMode) throws PAException {
   
    try {
        validateStudyExist(studyProtocolDTO, UPDAT);
        //get
        CorrelationUtils cUtils = new CorrelationUtils();
        OrganizationDTO leadOrgDTO = new OrganizationDTO();
        StudySiteDTO studySiteDto = PAUtil.getFirstObj(paServiceUtils.getStudySite(studyProtocolDTO.getIdentifier(),
                StudySiteFunctionalCode.LEAD_ORGANIZATION, true)); 
        leadOrgDTO.setIdentifier(IiConverter.convertToIi(cUtils.getPAOrganizationByIi(
                studySiteDto.getResearchOrganizationIi()).getIdentifier()));

        OrganizationDTO sponsorOrgDTO = new OrganizationDTO();
        studySiteDto = PAUtil.getFirstObj(paServiceUtils.getStudySite(studyProtocolDTO.getIdentifier(),
                StudySiteFunctionalCode.SPONSOR, true)); 
        sponsorOrgDTO.setIdentifier(IiConverter.convertToIi(cUtils.getPAOrganizationByIi(
                studySiteDto.getResearchOrganizationIi()).getIdentifier()));
        
        PersonDTO principalInvestigatorDTO = new PersonDTO();
        StudyContactDTO studyContactDto = PAUtil.getFirstObj(paServiceUtils.getStudyContact(
                studyProtocolDTO.getIdentifier(), StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true));
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToIi(
                cUtils.getPAPersonByIi(studyContactDto.getClinicalResearchStaffIi()).getIdentifier()));
        
        InterventionalStudyProtocolDTO dbStudyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(
                studyProtocolDTO.getIdentifier());
        dbStudyProtocolDTO.setPrimaryPurposeCode(studyProtocolDTO.getPrimaryPurposeCode());
        dbStudyProtocolDTO.setPrimaryPurposeOtherText(studyProtocolDTO.getPrimaryPurposeOtherText());
        dbStudyProtocolDTO.setStartDate(studyProtocolDTO.getStartDate());
        dbStudyProtocolDTO.setStartDateTypeCode(studyProtocolDTO.getStartDateTypeCode());
        dbStudyProtocolDTO.setPrimaryCompletionDate(studyProtocolDTO.getPrimaryCompletionDate());
        dbStudyProtocolDTO.setPrimaryCompletionDateTypeCode(studyProtocolDTO.getPrimaryCompletionDateTypeCode());
        dbStudyProtocolDTO.setFdaRegulatedIndicator(studyProtocolDTO.getFdaRegulatedIndicator());
        dbStudyProtocolDTO.setSection801Indicator(studyProtocolDTO.getSection801Indicator());
        dbStudyProtocolDTO.setDelayedpostingIndicator(studyProtocolDTO.getDelayedpostingIndicator());
        dbStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(
                studyProtocolDTO.getDataMonitoringCommitteeAppointedIndicator());
        dbStudyProtocolDTO.setProgramCodeText(studyProtocolDTO.getProgramCodeText());
        updateStudyProtocolObjs(
                dbStudyProtocolDTO ,
                overallStatusDTO ,
                null ,
                nctIdentifierSiteIdentifierDTO,
                studyIndldeDTOs ,
                studyResourcingDTOs ,
                documentDTOs ,
                leadOrgDTO ,
                principalInvestigatorDTO,
                sponsorOrgDTO,
                studyContactDTO ,
                studyParticipationContactDTO,
                summary4organizationDTO ,
                summary4studyResourcingDTO ,
                responsiblePartyContactIi , 
                studyRegAuthDTO, 
                collaboratorDTOs, 
                studySiteAccrualStatusDTOs,
                studySiteDTOs, UPDAT, isBatchMode);

       } catch (Exception e) {
        ejbContext.setRollbackOnly();
        throw new PAException(e);
    }
 }

  private void validateStudyExist(StudyProtocolDTO studyProtocolDTO,
        String operation) throws PAException {
    if (AMENDMENT.equalsIgnoreCase(operation) || UPDAT.equalsIgnoreCase(operation)) {
        //make sure Trial Exist 
        InterventionalStudyProtocolDTO dto = studyProtocolService.getInterventionalStudyProtocol(
                studyProtocolDTO.getIdentifier());
        if (dto == null) {
            throw new PAException("No Trial found for given Trial Identifier.\n");
        }
        if (!PAUtil.isBlNull(dto.getProprietaryTrialIndicator()) 
                && dto.getProprietaryTrialIndicator().getValue().booleanValue()) {
            throw new PAException(operation + " to Proprietary trial is not supported. ");
        }
    }
 }


 /**
  * 
  * @param studyProtocolDTO studyProtocolDTO
  * @param studySiteAccrualStatusDTO studySiteAccrualStatusDTO
  * @param documentDTOs documentDTOs
  * @param leadOrganizationDTO leadOrganizationDTO
  * @param studySiteInvestigatorDTO studySiteInvestigatorDTO
  * @param leadOrganizationStudySiteDTO leadOrganizationStudySiteDTO
  * @param studySiteOrganizationDTO studySiteOrganizationDTO
  * @param studySiteDTO studySiteDTO
  * @param nctIdentifierDTO nctIdentifierDTO
  * @param summary4OrganizationDTO summary4OrganizationDTO
  * @param summary4StudyResourcingDTO summary4StudyResourcingDTO
  * @param isBatchMode to identify if  batch is caller
  * @return Ii s
  * @throws PAException e
  */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  @SuppressWarnings({"PMD.PreserveStackTrace" })
  public Ii createProprietaryInterventionalStudyProtocol(
        StudyProtocolDTO studyProtocolDTO,
        StudySiteAccrualStatusDTO studySiteAccrualStatusDTO ,
        List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrganizationDTO ,
        PersonDTO studySiteInvestigatorDTO ,
        StudySiteDTO leadOrganizationStudySiteDTO ,
        OrganizationDTO studySiteOrganizationDTO ,
        StudySiteDTO studySiteDTO ,
        StudySiteDTO nctIdentifierDTO,
        OrganizationDTO summary4OrganizationDTO ,
        StudyResourcingDTO summary4StudyResourcingDTO ,
        Bl isBatchMode)
 throws PAException {
    Ii studyProtocolIi = null;
    StudyTypeCode studyTypeCode = null;
    //validate method needs to be here
    StringBuffer errorMsg = new StringBuffer();
    errorMsg.append(enforceBusinessRulesForProprietary(studyProtocolDTO,
            studySiteAccrualStatusDTO ,
            documentDTOs ,
            leadOrganizationDTO ,
            studySiteInvestigatorDTO ,
            leadOrganizationStudySiteDTO ,
            studySiteOrganizationDTO ,
            studySiteDTO ,
            nctIdentifierDTO,
            summary4OrganizationDTO ,
            summary4StudyResourcingDTO));
    
    if (errorMsg.length() > 0) {
        throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
    }
    List<PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
    listOfDTOToCreateInPO.add(leadOrganizationDTO);
    listOfDTOToCreateInPO.add(studySiteOrganizationDTO);
    listOfDTOToCreateInPO.add(studySiteInvestigatorDTO);
    listOfDTOToCreateInPO.add(summary4OrganizationDTO);
    paServiceUtils.createPoObject(listOfDTOToCreateInPO);
    studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));
    studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
    try {
        if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
            studyProtocolIi =  studyProtocolService.createInterventionalStudyProtocol(
                        (InterventionalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(
                                studyProtocolDTO, CREATE));
            studyTypeCode = StudyTypeCode.INTERVENTIONAL;
        } else {
            studyProtocolIi =  studyProtocolService.createObservationalStudyProtocol(
                    (ObservationalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
            studyTypeCode = StudyTypeCode.OBSERVATIONAL;
        }
        paServiceUtils.createMilestone(studyProtocolIi, MilestoneCode.SUBMISSION_RECEIVED, null);
        paServiceUtils.createOrUpdate(documentDTOs, IiConverter.convertToDocumentIi(null), studyProtocolIi);
        paServiceUtils.manageSummaryFour(studyProtocolIi , summary4OrganizationDTO , summary4StudyResourcingDTO);
      
        paServiceUtils.manageLeadOrganization(studyProtocolIi , leadOrganizationDTO ,
                leadOrganizationStudySiteDTO.getLocalStudyProtocolIdentifier());
        paServiceUtils.manageNCTIdentifier(studyProtocolIi, nctIdentifierDTO);

        //create StudySite
        Ii studySiteIi = createStudySite(studyProtocolIi, studySiteOrganizationDTO, studySiteDTO);
        studySiteAccrualStatusDTO.setStudySiteIi(studySiteIi);
        studySiteAccrualStatusService.createStudySiteAccrualStatus(studySiteAccrualStatusDTO);
        //set PI
        createStudySiteContact(studySiteIi, studyProtocolIi, studySiteOrganizationDTO,
                studySiteInvestigatorDTO, studyTypeCode);
        //
        sendMail(CREATE, isBatchMode, studyProtocolIi);            
    } catch (Exception e) {
        ejbContext.setRollbackOnly();
        throw new PAException(e.getMessage());
    }
    return studyProtocolIi;
 }

 /**
  * Reject a protocol and rollback all the changes.
  * @param studyProtocolIi study protocol identifier 
  * @param rejectionReason rejectionReason
  * @throws PAException on error
  */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
  public void reject(Ii studyProtocolIi, St rejectionReason) throws PAException {
    try {
        StudyProtocolDTO studyProtocolDto = studyProtocolService.getInterventionalStudyProtocol(studyProtocolIi);
        validate(studyProtocolDto, null , REJECTION, null, null, null, null, null, null, null, null);
        //Original trial Rejection
        if (studyProtocolDto.getSubmissionNumber().getValue().intValue() == 1) {
            StudyMilestoneDTO smDto = new StudyMilestoneDTO();
            smDto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
            smDto.setStudyProtocolIdentifier(studyProtocolIi);
            smDto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_REJECTED));
            smDto.setCommentText(rejectionReason);
            studyMilestoneService.create(smDto);                
        } else {
        Ii targetSpIi = studyProtocolIi;
        Ii sourceSpIi = null;
        // search the StudyProtocol to get the latest accepted protocol.
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
        StudyProtocolDTO studyToSearch = new StudyProtocolDTO();
        studyToSearch.setAssignedIdentifier(studyProtocolDto.getAssignedIdentifier());
        studyToSearch.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
        
        List<StudyProtocolDTO> spList = studyProtocolService.search(studyToSearch, limit);
        if (PAUtil.isListNotEmpty(spList)) {
          Collections.sort(spList, new Comparator<StudyProtocolDTO>() {
            public int compare(StudyProtocolDTO o1, StudyProtocolDTO o2) {
                 return o1.getSubmissionNumber().getValue()
                             .compareTo(o2.getSubmissionNumber().getValue());
             }
  
         });
          sourceSpIi = spList.get(spList.size() - 1).getIdentifier(); 
        }
        if (PAUtil.isListEmpty(spList)) {
         throw new PAException("Discrepancies occured while Rejecting the Amended Protocol");
        }
        if (sourceSpIi == null) {
         throw new PAException("Discrepancies occured while Rejecting the Amended Protocol");
        }
        if (targetSpIi == null) {
            throw new PAException("Study Relationship not found for the Amended Protocol");
           
        }
        InterventionalStudyProtocolDTO sourceSpDto = 
            studyProtocolService.getInterventionalStudyProtocol(sourceSpIi);
        // overwrite with the target
        sourceSpDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
        //studyProtocolService.updateStudyProtocol(sourceSpDto);
        Session session = HibernateUtil.getCurrentSession();
        InterventionalStudyProtocol source = 
            InterventionalStudyProtocolConverter.convertFromDTOToDomain(sourceSpDto);
        Long id = IiConverter.convertToLong(targetSpIi);
        InterventionalStudyProtocol target = 
            (InterventionalStudyProtocol) session.load(InterventionalStudyProtocol.class, id);
        source.setId(target.getId());
        target = source;
        session.merge(target);
        Ii sourceIi = null;
        List<StudyContactDTO> studyContactDtos = 
            paServiceUtils.getStudyContact(sourceSpIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
        StudyContactDTO scSourceDTO = null;
        if (PAUtil.getFirstObj(studyContactDtos) != null) {
            scSourceDTO = PAUtil.getFirstObj(studyContactDtos);
        } else {
            throw new PAException("Source Study Principal Investigator is not available");
        }        
        sourceIi = scSourceDTO.getIdentifier();
        studyContactDtos = 
            paServiceUtils.getStudyContact(targetSpIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
        StudyContactDTO scTargetDTO = null;
        if (PAUtil.getFirstObj(studyContactDtos) != null) {
            scTargetDTO = PAUtil.getFirstObj(studyContactDtos);
        } else {
            throw new PAException("Target Study Principal Investigator is not available");
        }
        
        // replace target with source
        scSourceDTO.setIdentifier(scTargetDTO.getIdentifier());
        scSourceDTO.setStudyProtocolIdentifier(targetSpIi);
        studyContactService.delete(sourceIi);
        studyContactService.update(scSourceDTO);

        List<StudySiteDTO> studySiteDtos = 
            paServiceUtils.getStudySite(sourceSpIi, StudySiteFunctionalCode.LEAD_ORGANIZATION, true);
        StudySiteDTO ssSourceDTO = null;
        if (PAUtil.getFirstObj(studySiteDtos) != null) {
            ssSourceDTO = PAUtil.getFirstObj(studySiteDtos);
        } else {
            throw new PAException("Source Lead Organization is not available");
        }
        sourceIi = ssSourceDTO.getIdentifier();
        studySiteDtos = paServiceUtils.getStudySite(targetSpIi, StudySiteFunctionalCode.LEAD_ORGANIZATION, true);
        StudySiteDTO ssTargetDTO = null;
        if (PAUtil.getFirstObj(studySiteDtos) != null) {
            ssTargetDTO = PAUtil.getFirstObj(studySiteDtos);
        } else {
            throw new PAException("Target Lead Organization is not available");
        }
        ssSourceDTO.setIdentifier(ssTargetDTO.getIdentifier());
        ssSourceDTO.setStudyProtocolIdentifier(targetSpIi);
        studySiteService.delete(sourceIi);
        studySiteService.update(ssSourceDTO);
        //sponsor
        List<StudySiteDTO> studySiteSponsorDtos = 
            paServiceUtils.getStudySite(sourceSpIi, StudySiteFunctionalCode.SPONSOR, true);
        StudySiteDTO ssSponsorSourceDTO = null;
        if (PAUtil.getFirstObj(studySiteSponsorDtos) != null) {
            ssSponsorSourceDTO = PAUtil.getFirstObj(studySiteSponsorDtos);
        } else {
            throw new PAException("Source Lead Organization is not available");
        }
        sourceIi = ssSponsorSourceDTO.getIdentifier();
        studySiteSponsorDtos = paServiceUtils.getStudySite(targetSpIi, StudySiteFunctionalCode.SPONSOR, true);
        StudySiteDTO ssSponsorTargetDTO = null;
        if (PAUtil.getFirstObj(studySiteSponsorDtos) != null) {
            ssSponsorTargetDTO = PAUtil.getFirstObj(studySiteSponsorDtos);
        } else {
            throw new PAException("Target Sponsor is not available");
        }
        ssSponsorSourceDTO.setIdentifier(ssSponsorTargetDTO.getIdentifier());
        ssSponsorSourceDTO.setStudyProtocolIdentifier(targetSpIi);
        studySiteService.delete(sourceIi);
        studySiteService.update(ssSponsorSourceDTO);
        
        paServiceUtils.executeSql(deleteAndReplace(sourceSpIi , targetSpIi));
      }  
    } catch (Exception e) {
        ejbContext.setRollbackOnly();
        throw new PAException(e);
    }
    
 }


 @SuppressWarnings({"PMD.ExcessiveMethodLength" })
 private void updateStudyProtocolObjs(
      StudyProtocolDTO studyProtocolDTO ,
      StudyOverallStatusDTO overallStatusDTO ,
      StudySiteDTO leadOrganizationSiteIdentifierDTO ,
      StudySiteDTO nctIdentifierSiteIdentifierDTO ,
      List<StudyIndldeDTO> studyIndldeDTOs ,
      List<StudyResourcingDTO> studyResourcingDTOs ,
      List<DocumentDTO> documentDTOs ,
      OrganizationDTO leadOrganizationDTO ,
      PersonDTO principalInvestigatorDTO,
      OrganizationDTO sponsorOrganizationDTO,
      StudyContactDTO studyContactDTO ,
      StudySiteContactDTO studySiteContactDTO, 
      OrganizationDTO summary4organizationDTO ,
      StudyResourcingDTO summary4studyResourcingDTO ,
      Ii responsiblePartyContactIi, 
      StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
      List<StudySiteDTO> collaborators, 
      List<StudySiteAccrualStatusDTO> participatingSites,
      List<StudySiteDTO> pgCdUpdatedList , String operation,
      Bl isBatchMode) throws PAException {
    
    Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
    Ii toStudyProtocolIi = null;
    validate(studyProtocolDTO, overallStatusDTO , operation, studyResourcingDTOs, documentDTOs,
            leadOrganizationDTO, sponsorOrganizationDTO, summary4organizationDTO,  principalInvestigatorDTO
            , responsiblePartyContactIi, studyIndldeDTOs);
    
    enforceBusinessRulesForUpdate(studyProtocolDTO ,
                                 overallStatusDTO ,
                                 studyContactDTO ,
                                 studySiteContactDTO ,
                                 studyIndldeDTOs ,
                                 studyResourcingDTOs ,
                                 studyRegAuthDTO, 
                                 participatingSites , operation ,
                                 collaborators);
  
   //update studyProtocol
    if (AMENDMENT.equalsIgnoreCase(operation)) {
        toStudyProtocolIi = paServiceUtils.copy(studyProtocolDTO.getIdentifier());
        String strTSR = tsrReportService.generateTSRHtml(studyProtocolDTO.getIdentifier());
        DocumentDTO docDto = new DocumentDTO();
        docDto.setStudyProtocolIdentifier(toStudyProtocolIi);
        docDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
        docDto.setText(EdConverter.convertToEd(strTSR.getBytes()));
        docDto.setFileName(StConverter.convertToSt("TSR.html"));
        documentService.create(docDto);
        // reset milestones
        String sql = null;
        sql = "Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = "  + studyProtocolIi.getExtension();
        paServiceUtils.executeSql(sql);
        sql = "Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = "  
            + studyProtocolIi.getExtension();
        paServiceUtils.executeSql(sql);
        sql = "Delete from STUDY_ONHOLD WHERE STUDY_PROTOCOL_IDENTIFIER  = "  + studyProtocolIi.getExtension();
        paServiceUtils.executeSql(sql);
        studyProtocolDTO.setAmendmentReasonCode(null);
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt(
                paServiceUtils.generateSubmissionNumber(studyProtocolDTO.getAssignedIdentifier().getExtension())));
        studyProtocolDTO.setStatusDate(TsConverter.convertToTs(null));
       
    }
    if (UPDAT.equalsIgnoreCase(operation)) {
        studyProtocolDTO.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
    }
    studyProtocolService.updateStudyProtocol(studyProtocolDTO);
    studyProtocolIi = studyProtocolDTO.getIdentifier();
    paServiceUtils.manageNCTIdentifier(studyProtocolIi, nctIdentifierSiteIdentifierDTO);
    paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null) , studyProtocolIi);
    paServiceUtils.createOrUpdate(studyResourcingDTOs, 
            IiConverter.convertToStudyResourcingIi(null) , studyProtocolIi);
    
    if (UPDAT.equalsIgnoreCase(operation)) {
        List<StudySiteDTO> collaboratorDTOs  = new ArrayList<StudySiteDTO>();
        if (PAUtil.isListNotEmpty(collaborators)) {
            for (StudySiteDTO collaborator : collaborators) {
                StudySiteDTO dbCollaborator = studySiteService.get(collaborator.getIdentifier());
                dbCollaborator.setFunctionalCode(collaborator.getFunctionalCode());
                collaboratorDTOs.add(dbCollaborator);
            }
        }
        paServiceUtils.createOrUpdate(collaboratorDTOs, IiConverter.convertToStudySiteIi(null) , studyProtocolIi);
        updateParticipatingSites(participatingSites);
        paServiceUtils.createOrUpdate(pgCdUpdatedList, IiConverter.convertToStudySiteIi(null) , studyProtocolIi);
        if (studyRegAuthDTO != null) {
            List<StudyRegulatoryAuthorityDTO> sraDto = new ArrayList<StudyRegulatoryAuthorityDTO>();
            sraDto.add(studyRegAuthDTO);
            paServiceUtils.createOrUpdate(sraDto, 
                    IiConverter.convertToStudyRegulatoryAuthorityIi(null) , studyProtocolIi);

        }
        
    }
  
    paServiceUtils.removeResponsibleParty(studyProtocolDTO.getIdentifier());
    if (AMENDMENT.equalsIgnoreCase(operation)) {
        paServiceUtils.manageSponsor(studyProtocolIi , sponsorOrganizationDTO);
    }
    paServiceUtils.createResponsibleParty(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO, 
            sponsorOrganizationDTO, responsiblePartyContactIi, studyContactDTO, studySiteContactDTO);
    
   // update summary4
   paServiceUtils.manageSummaryFour(studyProtocolIi , summary4organizationDTO , summary4studyResourcingDTO);
  if (AMENDMENT.equalsIgnoreCase(operation)) {
      St localStudyProtocolIdentifier = null;
      if (leadOrganizationSiteIdentifierDTO != null) {
          localStudyProtocolIdentifier = leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier();
      }
      paServiceUtils.manageLeadOrganization(studyProtocolIi, leadOrganizationDTO, localStudyProtocolIdentifier);
      paServiceUtils.managePrincipalInvestigator(studyProtocolIi , leadOrganizationDTO , 
              principalInvestigatorDTO , StudyTypeCode.INTERVENTIONAL);
      overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
      createStudyRelationship(studyProtocolIi , toStudyProtocolIi , studyProtocolDTO);
      paServiceUtils.createMilestone(studyProtocolIi, MilestoneCode.SUBMISSION_RECEIVED, null);
  } 
  studyOverallStatusService.create(overallStatusDTO);
  paServiceUtils.createOrUpdate(documentDTOs , IiConverter.convertToDocumentIi(null) , 
          studyProtocolDTO.getIdentifier());
  if (UPDAT.equalsIgnoreCase(operation)) {
      createInboxProcessingComments(documentDTOs, studyProtocolDTO);
  }
  //do not send the mail when its batch mode 
  sendMail(operation, isBatchMode, studyProtocolIi);
 }


 /**
  * @param operation
  * @param isBatchMode
  * @param studyProtocolIi
  * @throws PAException
  */
  private void sendMail(String operation, Bl isBatchMode, Ii studyProtocolIi)
        throws PAException {
    if (PAUtil.isBlNull(isBatchMode) || !BlConverter.covertToBool(isBatchMode)) {
          if (AMENDMENT.equalsIgnoreCase(operation)) {
              mailManagerSerivceLocal.sendAmendNotificationMail(studyProtocolIi);
          } else if (UPDAT.equalsIgnoreCase(operation)) {
              mailManagerSerivceLocal.sendUpdateNotificationMail(studyProtocolIi);
          } else if (CREATE.equalsIgnoreCase(operation)) {
             mailManagerSerivceLocal.sendNotificationMail(studyProtocolIi);
          }
      }
 }

 private Ii createStudyProtocolObjs(
        StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO ,
        List<StudyIndldeDTO> studyIndldeDTOs ,
        List<StudyResourcingDTO> studyResourcingDTOs ,
        List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrganizationDTO ,
        PersonDTO principalInvestigatorDTO ,
        OrganizationDTO sponsorOrganizationDTO ,
        StudySiteDTO leadOrganizationSiteIdentifierDTO ,
        StudySiteDTO nctIdentifierDTO ,
        StudyContactDTO studyContactDTO ,
        StudySiteContactDTO studySiteContactDTO ,
        OrganizationDTO summary4organizationDTO ,
        StudyResourcingDTO summary4studyResourcingDTO ,
        Ii responsiblePartyContactIi , String operation, Bl isBatchMode)
 throws PAException {
    validate(studyProtocolDTO, overallStatusDTO, operation, studyResourcingDTOs, documentDTOs,
            leadOrganizationDTO, sponsorOrganizationDTO, summary4organizationDTO, principalInvestigatorDTO
            , responsiblePartyContactIi, studyIndldeDTOs);
    enforceBusinessRules(
            studyProtocolDTO,
            overallStatusDTO,
            documentDTOs,
            leadOrganizationDTO,
            principalInvestigatorDTO,
            sponsorOrganizationDTO,
            studyContactDTO,
            studySiteContactDTO,
            leadOrganizationSiteIdentifierDTO);
    List <PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
    listOfDTOToCreateInPO.add(leadOrganizationDTO);
    listOfDTOToCreateInPO.add(sponsorOrganizationDTO);
    listOfDTOToCreateInPO.add(principalInvestigatorDTO);
    listOfDTOToCreateInPO.add(summary4organizationDTO);
    paServiceUtils.createPoObject(listOfDTOToCreateInPO);
    
    Ii studyProtocolIi = null;
    StudyTypeCode studyTypeCode = null;
    if (!BlConverter.covertToBool(studyProtocolDTO.getFdaRegulatedIndicator()) 
            && (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty())) {
        studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));            
        
        // size of ind/ide > 0 
    }
    studyProtocolDTO.setIdentifier(null);
    studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));
    if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
        studyProtocolIi =  studyProtocolService.createInterventionalStudyProtocol(
                    (InterventionalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
        studyTypeCode = StudyTypeCode.INTERVENTIONAL;
    } else {
        studyProtocolIi =  studyProtocolService.createObservationalStudyProtocol(
                (ObservationalStudyProtocolDTO) getStudyProtocolForCreateOrAmend(studyProtocolDTO, CREATE));
        studyTypeCode = StudyTypeCode.OBSERVATIONAL;
    }
    paServiceUtils.createMilestone(studyProtocolIi, MilestoneCode.SUBMISSION_RECEIVED, null);
    overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
    studyOverallStatusService.create(overallStatusDTO);
    paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null), studyProtocolIi);    
    paServiceUtils.createOrUpdate(studyResourcingDTOs, 
            IiConverter.convertToStudyResourcingIi(null), studyProtocolIi);    
    paServiceUtils.createOrUpdate(documentDTOs, IiConverter.convertToDocumentIi(null), studyProtocolIi);
    paServiceUtils.manageSummaryFour(studyProtocolIi , summary4organizationDTO , summary4studyResourcingDTO);
    St localStudyProtocolIdentifier = null;
    if (leadOrganizationSiteIdentifierDTO != null) {
        localStudyProtocolIdentifier = leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier();
    }
    paServiceUtils.manageLeadOrganization(studyProtocolIi , leadOrganizationDTO , localStudyProtocolIdentifier);
    paServiceUtils.managePrincipalInvestigator(studyProtocolIi , leadOrganizationDTO , 
            principalInvestigatorDTO , studyTypeCode);
    createSponsor(studyProtocolIi , sponsorOrganizationDTO);
    paServiceUtils.createResponsibleParty(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO, 
            sponsorOrganizationDTO, responsiblePartyContactIi, studyContactDTO, studySiteContactDTO);
    paServiceUtils.manageNCTIdentifier(studyProtocolIi, nctIdentifierDTO);
    sendMail(operation, isBatchMode, studyProtocolIi);
    return studyProtocolIi;
 }


 /**
  * @param studyProtocolDTO
  * @throws PAException e 
  */
  private StudyProtocolDTO getStudyProtocolForCreateOrAmend(StudyProtocolDTO studyProtocolDTO , String operation) 
  throws PAException {
    StudyProtocolDTO createStudyProtocolDTO =  null;
    if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
        createStudyProtocolDTO  = new InterventionalStudyProtocolDTO();
    } else if (studyProtocolDTO instanceof ObservationalStudyProtocolDTO) {
        createStudyProtocolDTO  = new ObservationalStudyProtocolDTO();   
    }
    if (AMENDMENT.equalsIgnoreCase(operation)) {
        createStudyProtocolDTO =  studyProtocolService.getInterventionalStudyProtocol(
                studyProtocolDTO.getIdentifier());
        createStudyProtocolDTO.setAmendmentDate(studyProtocolDTO.getAmendmentDate());
        createStudyProtocolDTO.setAmendmentNumber(studyProtocolDTO.getAmendmentNumber());
    } else {
        createStudyProtocolDTO.setSubmissionNumber(studyProtocolDTO.getSubmissionNumber());
        createStudyProtocolDTO.setIdentifier(null);
    }
    createStudyProtocolDTO.setOfficialTitle(studyProtocolDTO.getOfficialTitle());
    createStudyProtocolDTO.setPhaseCode(studyProtocolDTO.getPhaseCode());
    createStudyProtocolDTO.setPhaseOtherText(studyProtocolDTO.getPhaseOtherText());
    createStudyProtocolDTO.setPrimaryPurposeCode(studyProtocolDTO.getPrimaryPurposeCode());
    createStudyProtocolDTO.setPrimaryPurposeOtherText(studyProtocolDTO.getPrimaryPurposeOtherText());
    createStudyProtocolDTO.setStartDate(studyProtocolDTO.getStartDate());
    createStudyProtocolDTO.setStartDateTypeCode(studyProtocolDTO.getStartDateTypeCode());
    createStudyProtocolDTO.setPrimaryCompletionDate(studyProtocolDTO.getPrimaryCompletionDate());
    createStudyProtocolDTO.setPrimaryCompletionDateTypeCode(studyProtocolDTO.getPrimaryCompletionDateTypeCode());
    createStudyProtocolDTO.setStudyProtocolType(studyProtocolDTO.getStudyProtocolType());
    createStudyProtocolDTO.setProgramCodeText(studyProtocolDTO.getProgramCodeText());
    createStudyProtocolDTO.setFdaRegulatedIndicator(studyProtocolDTO.getFdaRegulatedIndicator());
    createStudyProtocolDTO.setSection801Indicator(studyProtocolDTO.getSection801Indicator());
    createStudyProtocolDTO.setDelayedpostingIndicator(studyProtocolDTO.getDelayedpostingIndicator());
    createStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(
            studyProtocolDTO.getDataMonitoringCommitteeAppointedIndicator());
    
    
    
    createStudyProtocolDTO.setProprietaryTrialIndicator(studyProtocolDTO.getProprietaryTrialIndicator());
    createStudyProtocolDTO.setUserLastCreated(studyProtocolDTO.getUserLastCreated());
    return createStudyProtocolDTO;
 }

 private void createStudyRelationship(Ii fromStudyProtocolIi , Ii toStudyProtocolIi , StudyProtocolDTO spDto)
 throws PAException {
    StudyRelationshipDTO srDto = new StudyRelationshipDTO();
    srDto.setSequenceNumber(spDto.getSubmissionNumber());
    srDto.setSourceStudyProtocolIdentifier(toStudyProtocolIi);
    srDto.setTargetStudyProtocolIdentifier(fromStudyProtocolIi);
    srDto.setTypeCode(CdConverter.convertToCd(StudyRelationshipTypeCode.MOD));
    studyRelationshipService.create(srDto);
 }

 private void createSponsor(Ii studyProtocolIi , OrganizationDTO sponsorOrganizationDto) throws PAException {
    String orgPoIdentifier = sponsorOrganizationDto.getIdentifier().getExtension();
    if (orgPoIdentifier == null) {
        throw new PAException("Organization Identifer is null");
    }
    if (studyProtocolIi == null) {
        throw new PAException(PROTOCOL_ID_NULL);
    }
    StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
    if (spDTO == null) {
        throw new PAException(NO_PROTOCOL_FOUND + studyProtocolIi.getExtension());
    }
    Long roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
    StudySiteDTO studyPartDTO = new StudySiteDTO();
    studyPartDTO.setFunctionalCode(CdConverter
            .convertStringToCd(StudySiteFunctionalCode.SPONSOR.getCode()));
    studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
    studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
    studyPartDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
    studyPartDTO = studySiteService.create(studyPartDTO);
 }

 @SuppressWarnings({ "PMD" })
 private void enforceBusinessRules(
        StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO ,
        List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrganizationDTO ,
        PersonDTO principalInvestigatorDTO ,
        OrganizationDTO sponsorOrganizationDTO ,
        StudyContactDTO studyContactDTO ,
        StudySiteContactDTO studySiteContactDTO ,
        StudySiteDTO leadOrganizationSiteIdentifierDTO)
 throws PAException {
    StringBuffer sb = new StringBuffer();
    // validate of null objects
    sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
    sb.append(overallStatusDTO == null ? "Study OverallStatus DTO cannot be null , " : "");
    sb.append(documentDTOs == null ? "Document DTO's cannot be null , " : "");
    sb.append(leadOrganizationDTO == null ? "Lead Organization DTO cannot be null , " : "");
    sb.append(principalInvestigatorDTO == null ? "Principal Investigator DTO cannot be null , " : "");
    sb.append(sponsorOrganizationDTO == null ? "Sponsor Organization DTO cannot be null , " : "");
    if (studyContactDTO != null && studySiteContactDTO != null) {
        sb.append("Either StudyContactDTO or studySiteContactDTO should be null ,");
    }
    if (studyContactDTO == null && studySiteContactDTO == null) {
        sb.append("Either StudyContactDTO or studySiteContactDTO should not be null ,");
    }
    // validates for attributes
    sb.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle()) ? "Official Title cannot be null , " : "");
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())
            ? "Trial Start Date Type cannot be null , " : "");
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())
            ? "Primary Completion Date Type cannot be null , " : "");
    sb.append(PAUtil.isTsNull(studyProtocolDTO.getStartDate()) ? "Trial Start Date cannot be null , " : "");
    sb.append(PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
            ? "Primary Completion Datecannot be null , " : "");
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
    if (leadOrganizationSiteIdentifierDTO != null) {
        sb.append(PAUtil.isStNull(leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier())
                ? "Local StudyProtocol Identifier cannot be null , " : "");
    }
    sb.append(PAUtil.isIiNull(leadOrganizationDTO.getIdentifier()) 
            ? "Lead Organization Identifier cannot be null , " : "");
    sb.append(PAUtil.isIiNull(principalInvestigatorDTO.getIdentifier())
            ? "Principal Investigator  Identifier cannot be null , " : "");
    sb.append(PAUtil.isIiNull(sponsorOrganizationDTO.getIdentifier())
            ? "Sponsor Organization  Identifier cannot be null , " : "");
    if (studyContactDTO != null) {
        sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.EMAIL) == null
                ? EMAIL_NOT_NULL : "");
        sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.PHONE) == null
                ? PHONE_NOT_NULL : "");
    }
    if (studySiteContactDTO != null) {
        sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                PAConstants.EMAIL) == null ? EMAIL_NOT_NULL : "");
        sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                PAConstants.PHONE) == null  ? PHONE_NOT_NULL : "");

    }
    if (overallStatusDTO != null) {
        sb.append(PAUtil.isCdNull(overallStatusDTO.getStatusCode())
                ? "Current Trial Status Code cannot be null , " : "");
        sb.append(PAUtil.isTsNull(overallStatusDTO.getStatusDate())
                ? "Current Trial Status Date cannot be null , " : "");
    }
    if (sb.length() > 0) {
        throw new PAException(VALIDATION_EXCEPTION + sb.toString());
    }

 }
 /**
  * 
  * @param studyProtocolDTO protocolDto
  * @param overallStatusDTO statusDto
  * @param isAmend amend
  * @param studyResourcingDTO studyResourcingDTO
  * @param documentDTOs list of Documents 
  * @return 
  * @throws PAException e
  */
  private void validate(StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO , String operation ,
        List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrganizationDTO, OrganizationDTO sponsorOrganizationDTO,
        OrganizationDTO summary4organizationDTO , PersonDTO piPersonDTO ,
        Ii responsiblePartyContactIi, List<StudyIndldeDTO> studyIndldeDTOs) throws PAException {
    StringBuffer errorMsg = new StringBuffer();
    //is user valid
    String loginName = "";
    if (!PAUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
        loginName = studyProtocolDTO.getUserLastCreated().getValue();
        CSMUserService userService = new CSMUserService();
        User user = userService.getCSMUser(loginName);
        if (user == null) {
           errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
        }
    } else {
        errorMsg.append("Submitter is required.");
    }
    if (REJECTION.equalsIgnoreCase(operation)) {
        DocumentWorkflowStatusDTO dws = docWrkFlowStatusService.getCurrentByStudyProtocol(
                studyProtocolDTO.getIdentifier());
        if (!DocumentWorkflowStatusCode.SUBMITTED.getCode().equals(dws.getStatusCode().getCode())
            && !DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED.getCode().equals(dws.getStatusCode().getCode())) {
            errorMsg.append("Only Trials with SUBMITTED can be REJECTED");
        }
    } else {
        try {
        studyOverallStatusService.validate(overallStatusDTO, studyProtocolDTO);
        } catch (PAException e) {
            errorMsg.append(e.getMessage());
        }
        if (PAUtil.isListNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
                try {
                studyResourcingDTO.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
                studyResourcingService.validate(studyResourcingDTO);
                } catch (PAException e) {
                    errorMsg.append(e.getMessage());
                }
            }
        }
        if (PAUtil.isListNotEmpty(studyIndldeDTOs)) {
            for (StudyIndldeDTO indIdeDto : studyIndldeDTOs) {
                try {
                indIdeDto.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
                studyIndldeService.validate(indIdeDto);
                } catch (PAException e) {
                    errorMsg.append(e.getMessage());
                }
            }
        }
    }
    if (AMENDMENT.equalsIgnoreCase(operation) || UPDAT.equalsIgnoreCase(operation)) {
        DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
                studyProtocolDTO.getIdentifier());
        String dwfs = isoDocWrkStatus.getStatusCode().getCode();
        String userCreated = StConverter.convertToString(
                studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier()).getUserLastCreated());
        if (!userCreated.equalsIgnoreCase(StConverter.convertToString(studyProtocolDTO.getUserLastCreated()))) {
           errorMsg.append(operation).append("to Trial can be submitted by the submitter of the original Trial.\n");
        }
        StudyOverallStatusDTO statusDTO = studyOverallStatusService.getCurrentByStudyProtocol(
                studyProtocolDTO.getIdentifier());
        if ((AMENDMENT.equalsIgnoreCase(operation))
                && (!(dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode())
                    || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())))) {
                errorMsg.append("Trial with processing status Abstraction Verified Response or " 
                        + " Abstraction Verified No Response can be Amended.\n");
        }
        if ((UPDAT.equalsIgnoreCase(operation)) 
            && (dwfs.equals(DocumentWorkflowStatusCode.SUBMITTED.getCode())
                    || dwfs.equals(DocumentWorkflowStatusCode.REJECTED.getCode()))) {
                errorMsg.append("Only Trials with processing status Accepted or Abstracted or  " 
                        + " Abstraction Verified No Response or  " 
                        + " Abstraction Verified No Response can be Updated.");
        }
        if (statusDTO.getStatusCode().getCode().equals(StudyStatusCode.DISAPPROVED.getCode())
             || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode())
             || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.WITHDRAWN.getCode())
             || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.COMPLETE.getCode())) {
            errorMsg.append(operation).append(" to a Trial with Current Trial Status as Disapproved or" 
                    + " Withdrawn or Complete or Administratively Complete is not allowed.\n");
        }
    }
    if (CREATE.equalsIgnoreCase(operation) || AMENDMENT.equalsIgnoreCase(operation)) {
        if (!paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)) {
            errorMsg.append("Protocol Document is required.\n");
        }
        if (!paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT)) {
            errorMsg.append("IRB Approval Document is required.\n");
        }
        errorMsg.append(validatePoObjects(leadOrganizationDTO, "Lead Organization "));
        errorMsg.append(validatePoObjects(sponsorOrganizationDTO, "Sponsor Organization "));
        errorMsg.append(validatePoObjects(summary4organizationDTO, "Summary 4 Organization "));
        errorMsg.append(validatePoObjects(piPersonDTO, "Prinicipal Investigator "));
    }
    if (UPDAT.equalsIgnoreCase(operation) && documentDTOs != null && !documentDTOs.isEmpty()) {
         for (DocumentDTO docDto : documentDTOs) {
             if (PAUtil.isIiNotNull(docDto.getIdentifier()) 
                && !paServiceUtils.isIiExistInPA(IiConverter.convertToDocumentIi(
                        Long.valueOf(docDto.getIdentifier().getExtension())))) {
              errorMsg.append("Document id " + docDto.getIdentifier().getExtension() + " does not exits.");
              
             }
         }
         errorMsg.append(validatePoObjects(summary4organizationDTO, "Summary 4 Organization "));
    }
    if (AMENDMENT.equalsIgnoreCase(operation)
            && !paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.CHANGE_MEMO_DOCUMENT)) {
            errorMsg.append("Change Memo Document is required.\n");
    }
    if (PAUtil.isIiNotNull(responsiblePartyContactIi) 
            && !paServiceUtils.isIiExistInPO(responsiblePartyContactIi)) {
        errorMsg.append("Error getting Responsible Party Contact from PO for id = " 
                + responsiblePartyContactIi.getExtension() + ".  ");
    }
    if (errorMsg.length() > 0) {
            throw new PAException(VALIDATION_EXCEPTION + errorMsg);
    }
 }


 /**
  * @param leadOrganizationDTO
  * @param fieldName fieldName
  * @param errorMsg
  */
 
  private <TYPE extends PoDto> String validatePoObjects(TYPE poDTO , String fieldName) {
    String strNewLine = ".\n";
    StringBuffer errorMsg = new StringBuffer();

    if (poDTO instanceof OrganizationDTO && PAUtil.isIiNotNull(((OrganizationDTO) poDTO).getIdentifier())) {
        if (!paServiceUtils.isIiExistInPO(((OrganizationDTO) poDTO).getIdentifier())) {
            errorMsg.append("Error getting ").append(fieldName).append(" from PO for id = ") 
                  .append(((OrganizationDTO) poDTO).getIdentifier().getExtension()).append(strNewLine);
        }
    } else if (poDTO instanceof PersonDTO && PAUtil.isIiNotNull(((PersonDTO) poDTO).getIdentifier())) {
        if (!paServiceUtils.isIiExistInPO(((PersonDTO) poDTO).getIdentifier())) {
            errorMsg.append("Error getting ").append(fieldName).append(" from PO for id = ") 
                  .append(((PersonDTO) poDTO).getIdentifier().getExtension()).append(strNewLine);
        }
    } else {
            String poValidErr = paServiceUtils.isDTOValidInPO(poDTO);
            if (PAUtil.isNotEmpty(poValidErr)) {
                errorMsg.append("Error validating ").append(fieldName).append(poValidErr);
            }
    }
    return errorMsg.toString();
 }

 /**
  * Enforce business rules for update.
  * 
  * @param studyProtocolDTO the study protocol dto
  * @param overallStatusDTO the overall status dto
  * @param documentDTOs the document dt os
  * @param leadOrganizationDTO the lead organization dto
  * @param principalInvestigatorDTO the principal investigator dto
  * @param sponsorOrganizationDTO the sponsor organization dto
  * @param studyContactDTO the study contact dto
  * @param studySiteContactDTO the study site contact dto
  * @param studyIndldeDTOs the study indlde dt os
  * @param studyResourcingDTOs the study resourcing dt os
  * @param studyRegAuthDTO the study reg auth dto
  * @param collaborators the collaborators
  * @param participatingSites the participating sites
  * @param collaborators 
  * 
  * @throws PAException the PA exception
  */
  private void enforceBusinessRulesForUpdate(
        StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO ,
        StudyContactDTO studyContactDTO ,
        StudySiteContactDTO studySiteContactDTO ,
        List<StudyIndldeDTO> studyIndldeDTOs ,
        List<StudyResourcingDTO> studyResourcingDTOs ,
        StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
        List<StudySiteAccrualStatusDTO> participatingSites , String operation,
        List<StudySiteDTO> collaborators) throws PAException {
    StringBuffer sb = new StringBuffer();
    // validate of null objects
    sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
    sb.append(overallStatusDTO == null ? "Study OverallStatus DTO cannot be null , " : "");
    if (studyContactDTO != null && studySiteContactDTO != null) {
        sb.append("Either StudyContactDTO or studySiteContactDTO should be null ,");
    }
    if (studyContactDTO == null && studySiteContactDTO == null) {
        sb.append("Either StudyContactDTO or studySiteContactDTO should not be null ,");
    }
    // validates for attributes
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())
            ? "Trial Start Date Type cannot be null , " : "");
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())
            ? "Primary Completion Date Type cannot be null , " : "");
    sb.append(PAUtil.isTsNull(studyProtocolDTO.getStartDate()) ? "Trial Start Date cannot be null , " : "");
    sb.append(PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
            ? "Primary Completion Datecannot be null , " : "");
    sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
    if (studyContactDTO != null) {
        sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.EMAIL) == null
                ? EMAIL_NOT_NULL : "");
        sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.PHONE) == null
                ? PHONE_NOT_NULL : "");
    }
    if (studySiteContactDTO != null) {
        sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                PAConstants.EMAIL) == null ? EMAIL_NOT_NULL : "");
        sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                PAConstants.PHONE) == null  ? PHONE_NOT_NULL : "");

    }
    if (overallStatusDTO != null) {
        sb.append(PAUtil.isCdNull(overallStatusDTO.getStatusCode())
                ? "Current Trial Status cannot be null , " : "");
        sb.append(PAUtil.isTsNull(overallStatusDTO.getStatusDate())
                ? "Current Trial Status Date cannot be null , " : "");
    }
    if (UPDAT.equalsIgnoreCase(operation) && collaborators != null 
            && !collaborators.isEmpty()) {
        for (StudySiteDTO collaborator : collaborators) {
            if (PAUtil.isIiNotNull(collaborator.getIdentifier())
                && !paServiceUtils.isIiExistInPA(IiConverter.convertToStudySiteIi(
                        Long.valueOf(collaborator.getIdentifier().getExtension())))) {
                sb.append("Collaborator Id " + collaborator.getIdentifier().getExtension()
                        + " does not exist.");
            }
        }
    }
    if (sb.length() > 0) {
        throw new PAException(VALIDATION_EXCEPTION + sb.toString());
    }
    paServiceUtils.enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);
    paServiceUtils.enforceNoDuplicateGrants(studyResourcingDTOs);
    if (UPDAT.equalsIgnoreCase(operation)) {
        enforceRegulatoryInfo(studyProtocolDTO, studyRegAuthDTO , studyIndldeDTOs);
        enforceRecruitmentStatus(studyProtocolDTO, participatingSites);
        
    }
 }

 private void enforceRegulatoryInfo(StudyProtocolDTO studyProtocolDTO, 
        StudyRegulatoryAuthorityDTO studyRegAuthDTO , List<StudyIndldeDTO> studyIndldeDTOs) throws PAException {
    StringBuffer errMsg = new StringBuffer();
    if (studyRegAuthDTO == null) {
        errMsg.append("Regulatory Information fields must be Entered.\n");
    }
    DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
          studyProtocolDTO.getIdentifier());
    List<PlannedActivityDTO> paList = 
        plannedActivityService.getByStudyProtocol(studyProtocolDTO.getIdentifier());      
    paServiceUtils.enforceRegulatoryInfo(studyProtocolDTO, 
                            studyRegAuthDTO, 
                            studyIndldeDTOs, 
                            isoDocWrkStatus, 
                            paList, 
                            regulatoryInfoBean);         
 }   

 private void enforceRecruitmentStatus(StudyProtocolDTO studyProtocolDTO,  
    List<StudySiteAccrualStatusDTO> participatingSites) throws PAException {
    if (participatingSites != null && !participatingSites.isEmpty()) { 
          StudyRecruitmentStatusDTO recruitmentStatusDto = 
          studyRecruitmentStatusServiceLocal.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
          paServiceUtils.enforceRecruitmentStatus(studyProtocolDTO, participatingSites, recruitmentStatusDto);
    } 
 }

 private void updateParticipatingSites(
        List<StudySiteAccrualStatusDTO> participatingSites)
        throws PAException {
    if (participatingSites != null) {
        for (StudySiteAccrualStatusDTO sdto : participatingSites) {
            studySiteAccrualStatusService.createStudySiteAccrualStatus(sdto);
        }
    }
 }

 @SuppressWarnings({"PMD.AvoidDuplicateLiterals" })
 private List<String> deleteAndReplace(Ii sourceIi, Ii targetIi) {
    String sqlUpd = targetIi.getExtension() + " WHERE STUDY_PROTOCOL_IDENTIFIER = " + sourceIi.getExtension();
    List<String> sqls = new ArrayList<String>();
    String targetId = targetIi.getExtension();
    sqls.add("Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("UPDATE STUDY_MILESTONE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    sqls.add("Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("UPDATE DOCUMENT_WORKFLOW_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension() 
            + " and TYPE_CODE = '" + DocumentTypeCode.TSR.getCode() + "'");
    sqls.add("UPDATE DOCUMENT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    sqls.add("UPDATE STUDY_ONHOLD SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    sqls.add("Delete from STUDY_OVERALL_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("UPDATE STUDY_OVERALL_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    sqls.add("Delete from STUDY_RECRUITMENT_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("UPDATE STUDY_RECRUITMENT_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    
    sqls.add("Delete from STUDY_INDLDE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("UPDATE STUDY_INDLDE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

    sqls.add("Delete from STUDY_RESOURCING WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
    sqls.add("UPDATE STUDY_RESOURCING SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
    
    sqls.add("DELETE FROM STUDY_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId 
            + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
    sqls.add("UPDATE STUDY_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd 
            + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
    sqls.add("DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND FUNCTIONAL_CODE IN " 
        + "('RESPONSIBLE_PARTY_SPONSOR')");
    sqls.add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND FUNCTIONAL_CODE IN " 
            + "('RESPONSIBLE_PARTY_SPONSOR')");
    sqls.add("DELETE FROM STUDY_SITE_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND ROLE_CODE IN " 
        + "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
    sqls.add("UPDATE STUDY_SITE_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND ROLE_CODE IN " 
           +  "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
    //nct reject
    sqls.add("DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND FUNCTIONAL_CODE IN " 
            + "('IDENTIFIER_ASSIGNER')");
    sqls.add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND FUNCTIONAL_CODE IN " 
                + "('IDENTIFIER_ASSIGNER')");
    sqls.add("Delete from STUDY_RELATIONSHIP WHERE TARGET_STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension());
    sqls.add("Delete from STUDY_PROTOCOL WHERE IDENTIFIER  = " + sourceIi.getExtension());
    return sqls;
 }

 private Ii createStudySite(Ii studyProtocolIi,
        OrganizationDTO studySiteDTO , StudySiteDTO siteDTO) throws PAException {
    
    Long paHealthCareFacilityId = ocsr.createHealthCareFacilityCorrelations(
            studySiteDTO.getIdentifier().getExtension());
    StudySiteDTO sp = new StudySiteDTO();
    sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
    sp.setHealthcareFacilityIi(IiConverter.convertToIi(paHealthCareFacilityId));
    sp.setIdentifier(null);
    sp.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
    sp.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
    sp.setStudyProtocolIdentifier(studyProtocolIi);
    sp.setProgramCodeText(siteDTO.getProgramCodeText());
    sp.setLocalStudyProtocolIdentifier(siteDTO.getLocalStudyProtocolIdentifier());
    sp.setAccrualDateRange(siteDTO.getAccrualDateRange());
    sp = studySiteService.create(sp);
    return sp.getIdentifier();
 }

 private Ii createStudySiteContact(Ii studySiteIi , Ii studyProtocolIi, OrganizationDTO siteDto, PersonDTO piDto,
        StudyTypeCode studyTypeCode) throws PAException {
     Ii studySiteContactIi = null;
     String orgPoIdentifier = siteDto.getIdentifier().getExtension();
     String perIdentifier = piDto.getIdentifier().getExtension();
     StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
     studySiteContactDTO.setStudySiteIi(studySiteIi);
     Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean().
        createClinicalResearchStaffCorrelations(orgPoIdentifier, perIdentifier);
     Long healthCareProviderIi = null;
     if (studyTypeCode.equals(StudyTypeCode.INTERVENTIONAL)) {
        healthCareProviderIi = new HealthCareProviderCorrelationBean().createHealthCareProviderCorrelationBeans(
            orgPoIdentifier, perIdentifier);
    }
     studySiteContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
    if (healthCareProviderIi != null) {
        studySiteContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));
    }
    studySiteContactDTO.setRoleCode(CdConverter.convertToCd(
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR));
    
    studySiteContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
    studySiteContactDTO.setStatusCode(CdConverter.convertStringToCd(
            FunctionalRoleStatusCode.PENDING.getCode()));
    
    
    studySiteContactService.create(studySiteContactDTO);
    return studySiteContactIi;
 }

 @SuppressWarnings({"PMD" })
 private void createInboxProcessingComments(List<DocumentDTO> documentDTOs, StudyProtocolDTO studyProtocolDTO) 
 throws PAException {
  StudyInboxDTO studyInboxDTO = new StudyInboxDTO();
  studyInboxDTO.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
  studyInboxDTO.setInboxDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
  boolean docUpdated = false;
  boolean docParticipatingUpdated = false;
  boolean trialUpdateForReview = false;
  StringBuffer stringBuffer = new StringBuffer();
  DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
           studyProtocolDTO.getIdentifier());
  if (documentDTOs != null && !documentDTOs.isEmpty()) {
    for (DocumentDTO doc : documentDTOs) {
      if (DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode().equals(
              CdConverter.convertCdToString(doc.getTypeCode()))) {
           docUpdated = true;
      }
      if (DocumentTypeCode.PARTICIPATING_SITES.getCode().equals(
              CdConverter.convertCdToString(doc.getTypeCode()))) {
          docParticipatingUpdated = true;
      }
    }
   }
   if (docUpdated) {
      stringBuffer.append("IRB Document was updated<br>");
      studyInboxDTO.setComments(StConverter.convertToSt(stringBuffer.toString()));
      trialUpdateForReview = true;
   }
   if (docParticipatingUpdated) {
       stringBuffer.append("Participating Document was updated<br>");
       studyInboxDTO.setComments(StConverter.convertToSt(stringBuffer.toString()));
       trialUpdateForReview = true;
   }
   if (PAUtil.isAbstractedAndAbove(isoDocWrkStatus.getStatusCode())) {
       List<AbstractionCompletionDTO> errorList =
           abstractionCompletionService.validateAbstractionCompletion(studyProtocolDTO.getIdentifier());
       if (!errorList.isEmpty()) {
         stringBuffer.append("<b>Type :</b>  <b>Description :</b> <b>Comments :</b><br>");
         for (AbstractionCompletionDTO abDTO : errorList) {
           stringBuffer.append(abDTO.getErrorType()).append(":").append(abDTO.getErrorDescription())
                      .append(":").append(abDTO.getComment()).append("<br>");
        }
        studyInboxDTO.setComments(StConverter.convertToSt(stringBuffer.toString()));
        trialUpdateForReview = true;
       } 
   }
   if (trialUpdateForReview) {
    //create 
    studyInboxServiceLocal.create(studyInboxDTO);
   } 
 }   
 private String enforceBusinessRulesForProprietary(StudyProtocolDTO studyProtocolDTO,
      StudySiteAccrualStatusDTO studySiteAccrualStatusDTO ,
      List<DocumentDTO> documentDTOs ,
      OrganizationDTO leadOrganizationDTO ,
      PersonDTO studySiteInvestigatorDTO ,
      StudySiteDTO leadOrganizationStudySiteDTO ,
      OrganizationDTO studySiteOrganizationDTO ,
      StudySiteDTO studySiteDTO ,
      StudySiteDTO nctIdentifierDTO,
      OrganizationDTO summary4OrganizationDTO ,
      StudyResourcingDTO summary4StudyResourcingDTO) throws PAException {
  StringBuffer errorMsg = new StringBuffer();
  errorMsg.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
  errorMsg.append(studySiteAccrualStatusDTO == null ? "Study Site Accrual Status DTO cannot be null , " : "");

  errorMsg.append(leadOrganizationDTO == null ? "Lead Organization DTO cannot be null , " : "");
  errorMsg.append(studySiteInvestigatorDTO == null ? "Principal Investigator DTO cannot be null , " : "");
  errorMsg.append(studySiteOrganizationDTO == null ? "Study Site Organization DTO cannot be null , " : "");
  
  errorMsg.append(leadOrganizationStudySiteDTO == null ? "Lead Organization Study SiteDTO cannot be null , " : "");
  errorMsg.append(studySiteDTO == null ? "Study Site DTO cannot be null , " : "");
  
  String loginName = "";
  if (!PAUtil.isStNull(studyProtocolDTO.getUserLastCreated())) {
      loginName = studyProtocolDTO.getUserLastCreated().getValue();
     CSMUserService userService = new CSMUserService();
     User user = userService.getCSMUser(loginName);
     if (user == null) {
         errorMsg.append("Submitter " + loginName + " does not exist. Please do self register in CTRP.");
     }
  } else {
      errorMsg.append("Submitter is required.");
  }
  try {
      //duplicate check only for NCT
      if (!PAUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
          nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
          String poOrgId = PaRegistry.getOrganizationCorrelationService().getCtGovPOIdentifier();
          long roId = PaRegistry.getOrganizationCorrelationService().createResearchOrganizationCorrelations(
                  poOrgId);
          nctIdentifierDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
          studySiteService.validate(nctIdentifierDTO);
      }
  } catch (PAException e) {
      errorMsg.append(e.getMessage());
  }

  // validates for attributes
  errorMsg.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle()) ? "Official Title cannot be null , " : "");
  if (nctIdentifierDTO != null) { 
      if (PAUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
          errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
          errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode()) 
                  ? "Purpose cannot be null , " : "");
          if (PAUtil.isListEmpty(documentDTOs) 
                  || !paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)) {
              errorMsg.append("Proprietary template document is mandatory if NCT number is not provided");
          }
      }
  } else {
      errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
      errorMsg.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryPurposeCode()) ? "Purpose cannot be null , " : "");
  }
  if (studySiteDTO != null) {
      errorMsg.append(PAUtil.isStNull(studySiteDTO.getLocalStudyProtocolIdentifier())
              ? "Submitting Organization Local Trial Identifier cannot be null, " : "");
  } 
  if (leadOrganizationStudySiteDTO != null) {
      errorMsg.append(PAUtil.isStNull(leadOrganizationStudySiteDTO.getLocalStudyProtocolIdentifier())
              ? "Lead Organization Trial Identifier cannot be null, " : "");
  } 
  errorMsg.append(paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO));
  if (summary4StudyResourcingDTO != null && !PAUtil.isCdNull(summary4StudyResourcingDTO.getTypeCode()) 
          && null == SummaryFourFundingCategoryCode.getByCode(
              CdConverter.convertCdToString(summary4StudyResourcingDTO.getTypeCode()))) {
       errorMsg.append(CdConverter.convertCdToString(summary4StudyResourcingDTO.getTypeCode()))
       .append(" is not valid Summary Four Funding Category Code");   
  }
  errorMsg.append(validatePoObjects(leadOrganizationDTO, "Lead Organization "));
  errorMsg.append(validatePoObjects(studySiteOrganizationDTO, "Study Site Organization "));
  errorMsg.append(validatePoObjects(summary4OrganizationDTO, "Summary 4 Organization "));
  errorMsg.append(validatePoObjects(studySiteInvestigatorDTO, "Study Site Investigator "));
  
  return errorMsg.toString();
 }

}
