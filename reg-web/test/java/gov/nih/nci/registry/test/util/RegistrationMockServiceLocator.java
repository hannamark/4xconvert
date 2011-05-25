package gov.nih.nci.registry.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.registry.service.MockLookUpTableService;
import gov.nih.nci.registry.service.MockOrganizationCorrelationService;
import gov.nih.nci.registry.service.MockPAOrganizationService;
import gov.nih.nci.registry.service.MockProtocolQueryService;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.service.MockStudyProtocolService;
import gov.nih.nci.registry.service.MockStudyProtocolStageService;
import gov.nih.nci.registry.service.MockStudyResourcingService;
import gov.nih.nci.registry.service.MockStudySiteAccrualStatusService;
import gov.nih.nci.registry.service.MockStudySiteService;
import gov.nih.nci.registry.service.MockTrialRegistrationService;
import gov.nih.nci.security.cgmm.constants.CGMMConstants;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class RegistrationMockServiceLocator implements ServiceLocator {

    private final StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();
    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final LookUpTableServiceRemote lookUpTableService = new MockLookUpTableService();
    private final TrialRegistrationServiceLocal  trialRegistrationService = new MockTrialRegistrationService();
    private final StudySiteServiceLocal studySiteService = new MockStudySiteService();
    private final StudyResourcingServiceLocal studyResourcingService = new MockStudyResourcingService();
    private final PAOrganizationServiceRemote paOrganizationService = new MockPAOrganizationService();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final RegistryUserServiceLocal registryUserService = new MockRegistryUserService();
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new MockStudySiteAccrualStatusService();
    private final StudyProtocolStageServiceLocal studyProtocolStageService = new MockStudyProtocolStageService();

    /**
     * {@inheritDoc}
     */
    public DocumentServiceLocal getDocumentService() {
        List<DocumentDTO> results = new ArrayList<DocumentDTO>();

        DocumentDTO dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("Protocol Document"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        results.add(dto);
        dto = new DocumentDTO();
        dto.setFileName(StConverter.convertToSt("fileName"));
        dto.setTypeCode(CdConverter.convertStringToCd("IRB Approval Document"));
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        results.add(dto);

        DocumentServiceLocal svc = mock(DocumentServiceLocal.class);
        try {
            when(svc.get(any(Ii.class))).thenReturn(dto);
            when(svc.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpTableService;
    }

    /**
     * {@inheritDoc}
     */
    public MailManagerServiceLocal getMailManagerService() {
        return mock(MailManagerServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return organizationCorrelationService;
    }

    /**
     * {@inheritDoc}
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return paOrganizationService;
    }

    /**
     * {@inheritDoc}
     */
    public PAPersonServiceRemote getPAPersonService() {
        return mock(PAPersonServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        RegulatoryInformationServiceRemote svc = mock(RegulatoryInformationServiceRemote.class);
        try {
            when(svc.getDistinctCountryNames()).thenReturn(new ArrayList<CountryRegAuthorityDTO>());
            when(svc.getRegulatoryAuthorityNameId(any(Long.class))).thenReturn(new ArrayList<RegulatoryAuthOrgDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudyContactServiceLocal getStudyContactService() {
        return mock(StudyContactServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setIndldeTypeCode(CdConverter.convertStringToCd("IND"));
        dto.setIndldeNumber(StConverter.convertToSt("Ind no"));
        dto.setGrantorCode(CdConverter.convertStringToCd("CDER"));
        dto.setHolderTypeCode(CdConverter.convertStringToCd("Investigator"));
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setExpandedAccessStatusCode(CdConverter.convertStringToCd("expandedAccessType"));
        dto.setNciDivProgHolderCode(CdConverter.convertStringToCd("programCode"));
        dto.setNihInstHolderCode(CdConverter.convertStringToCd(""));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));

        StudyIndldeServiceLocal svc = mock(StudyIndldeServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(dto));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        StudyOverallStatusServiceLocal svc = mock(StudyOverallStatusServiceLocal.class);
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setStatusCode(CdConverter.convertStringToCd("Active"));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/20/2008")));
        dto.setReasonText(null);
        try {
            when(svc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteContactServiceLocal getStudySiteContactService() {
        StudySiteContactDTO dto = new StudySiteContactDTO();
        dto.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
        dto.setOrganizationalContactIi(IiConverter.convertToPoOrganizationalContactIi("1"));
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        TelPhone phone = new TelPhone();
        phone.setValue(URI.create("tel:phone"));
        telSet.add(phone);
        TelEmail email = new TelEmail();
        email.setValue(URI.create("mailto:email"));
        telSet.add(email);
        telecomAddresses.setItem(telSet);
        dto.setTelecomAddresses(telecomAddresses);

        StudySiteContactServiceLocal svc = mock(StudySiteContactServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(Arrays.asList(dto));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * {@inheritDoc}
     */
    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        StudyRegulatoryAuthorityServiceLocal svc = mock(StudyRegulatoryAuthorityServiceLocal.class);
        try {
            when(svc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(new StudyRegulatoryAuthorityDTO());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingServiceLocal getStudyResoucringService() {
        return studyResourcingService;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * {@inheritDoc}
     */
    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return trialRegistrationService;
    }

    /**
     * {@inheritDoc}
     */
    public AbstractionCompletionServiceRemote getAbstractionCompletionService() {
        return mock(AbstractionCompletionServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public ArmServiceLocal getArmService() {
        return mock(ArmServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() {
        return mock(CTGovXmlGeneratorServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        return mock(PDQDiseaseAlternameServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
        return mock(PDQDiseaseParentServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQDiseaseServiceLocal getDiseaseService() {
        return mock(PDQDiseaseServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return mock(DocumentWorkflowStatusServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        return mock(InterventionAlternateNameServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public InterventionServiceLocal getInterventionService() {
        return mock(InterventionServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return mock(PAHealthCareProviderRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return mock(PlannedActivityServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        return mock(PlannedSubstanceAdministrationServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * {@inheritDoc}
     */
    public StratumGroupServiceLocal getStratumGroupService() {
        return mock(StratumGroupServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        return mock(StudyCheckoutServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return mock(StudyDiseaseServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyInboxServiceLocal getStudyInboxService() {
        return mock(StudyInboxServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return mock(StudyMilestoneServicelocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return mock(StudyMilestoneTasksServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        return mock(StudyObjectiveServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyOnholdServiceLocal getStudyOnholdService() {
        return mock(StudyOnholdServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return mock(StudyRecruitmentStatusServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return mock(StudyRelationshipServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return mock(StudySiteAccrualAccessServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        return mock(StudySiteOverallStatusServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return mock(TSRReportGeneratorServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return studyProtocolStageService;
    }

    /**
     * {@inheritDoc}
     */
    public GridAccountServiceRemote getGridAccountService() {
        GridAccountServiceRemote svc = mock(GridAccountServiceRemote.class);
        try {
            when(svc.createGridAccount(any(RegistryUser.class), any(String.class), any(String.class))).thenReturn("Success");
        } catch (PAException e) {
            //Unreachable
        }
        when(svc.doesGridAccountExist(any(String.class))).thenReturn(false);
        when(svc.isValidGridPassword(any(String.class))).thenReturn(true);
        when(svc.getIdentityProviders()).thenReturn(new HashMap<String, String>());

        Map<String, String> results = new HashMap<String, String>();
        results.put(CGMMConstants.CGMM_EMAIL_ID, "test@test.com");
        results.put(CGMMConstants.CGMM_FIRST_NAME, "firstName");
        results.put(CGMMConstants.CGMM_LAST_NAME, "lastName");
        when(svc.authenticateUser(any(String.class), any(String.class), any(String.class))).thenReturn(results);
        when(svc.getFullyQualifiedUsername(any(String.class), any(String.class),
                any(String.class))).thenAnswer(new Answer<String>() {
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        String username = (String) args[0];
                        return "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=" + username;
                    }
                });
        return svc;
    }

    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return mock(ProprietaryTrialManagementServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return mock(ParticipatingSiteServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        return mock(StudyOutcomeMeasureServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return mock(PDQXmlGeneratorServiceRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        return mock(PDQUpdateGeneratorTaskServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return mock(PDQTrialAbstractionServiceBeanRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        return mock(PDQTrialRegistrationServiceBeanRemote.class);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));

        PlannedMarkerServiceLocal svc = mock(PlannedMarkerServiceLocal.class);
        try {
            when(svc.getPlannedMarker(any(Ii.class))).thenReturn(dto);
            when(svc.get(any(Ii.class))).thenReturn(dto);
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<PlannedMarkerDTO>());
            when(svc.copy(any(Ii.class), any(Ii.class))).thenReturn(new HashMap<Ii, Ii>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public AuditTrailServiceLocal getAuditTrailService() {
        return mock(AuditTrailServiceLocal.class);
    }

}
