package gov.nih.nci.registry.test.util;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.registry.service.MockDocumentService;
import gov.nih.nci.registry.service.MockIdentifiedOrganizationCorrelationService;
import gov.nih.nci.registry.service.MockIdentifiedPersonCorrelationService;
import gov.nih.nci.registry.service.MockLookUpTableService;
import gov.nih.nci.registry.service.MockMailManagerService;
import gov.nih.nci.registry.service.MockOrganizationCorrelationService;
import gov.nih.nci.registry.service.MockOrganizationEntityService;
import gov.nih.nci.registry.service.MockOrganizationalContactCorrelationService;
import gov.nih.nci.registry.service.MockPAOrganizationService;
import gov.nih.nci.registry.service.MockPersonEntityService;
import gov.nih.nci.registry.service.MockProtocolQueryService;
import gov.nih.nci.registry.service.MockRegistryUserService;
import gov.nih.nci.registry.service.MockRegulatoryInformationService;
import gov.nih.nci.registry.service.MockStudyContactService;
import gov.nih.nci.registry.service.MockStudyIndldeService;
import gov.nih.nci.registry.service.MockStudyOverallStatusService;
import gov.nih.nci.registry.service.MockStudyProtocolService;
import gov.nih.nci.registry.service.MockStudyRegulatoryAuthorityService;
import gov.nih.nci.registry.service.MockStudyResourcingService;
import gov.nih.nci.registry.service.MockStudySiteAccrualStatusService;
import gov.nih.nci.registry.service.MockStudySiteContactService;
import gov.nih.nci.registry.service.MockStudySiteService;
import gov.nih.nci.registry.service.MockTrialRegistrationService;
import gov.nih.nci.registry.util.ServiceLocator;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

public class RegistrationMockServiceLocator implements ServiceLocator {
    
    private final StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();
    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final DocumentServiceLocal documentService = new MockDocumentService(); 
    private final StudyOverallStatusServiceLocal studyOverallStatusService = new MockStudyOverallStatusService();
    private final LookUpTableServiceRemote lookUpTableService = new MockLookUpTableService();
    private final TrialRegistrationServiceLocal  trialRegistrationService = new MockTrialRegistrationService();
    private final MailManagerServiceLocal mailManagerService = new MockMailManagerService();
    private final StudyIndldeServiceLocal  studyIndldeService = new MockStudyIndldeService();
    private final StudySiteServiceLocal studySiteService = new MockStudySiteService();
    private final StudyResourcingServiceLocal studyResourcingService = new MockStudyResourcingService();
    private final PAOrganizationServiceRemote paOrganizationService = new MockPAOrganizationService();
    private final OrganizationEntityServiceRemote organizationEntityService = new MockOrganizationEntityService();
    private final IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationService = new MockIdentifiedOrganizationCorrelationService();
    private final PersonEntityServiceRemote personEntityService = new MockPersonEntityService();
    private final IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationService = new MockIdentifiedPersonCorrelationService();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final OrganizationalContactCorrelationServiceRemote orgContactCorrelationService = new MockOrganizationalContactCorrelationService();
    private final StudyContactServiceLocal studyContactService = new MockStudyContactService();
    private final StudySiteContactServiceLocal studySiteContactService = new MockStudySiteContactService();
    private final RegistryUserServiceRemote registryUserService = new MockRegistryUserService();
    private final RegulatoryInformationServiceRemote regulatoryInfoService = new MockRegulatoryInformationService();
    private final StudyRegulatoryAuthorityServiceLocal studyRegulatorAuthService = new MockStudyRegulatoryAuthorityService();
    private final StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new MockStudySiteAccrualStatusService();    
    public DocumentServiceLocal getDocumentService() {
        return documentService;
    }

    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService()
            throws PAException {
        return identifiedOrganizationCorrelationService;
    }

    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService()
            throws PAException {
        return identifiedPersonCorrelationService;
    }

    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpTableService;
    }

    public MailManagerServiceLocal getMailManagerService() throws PAException {
        return mailManagerService;
    }

    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService()
            throws PAException {
        return organizationCorrelationService;
    }

    public PAOrganizationServiceRemote getPAOrganizationService() {
        return paOrganizationService;
    }

    public PAPersonServiceRemote getPAPersonService() {
        // TODO Auto-generated method stub
        return null;
    }

    public OrganizationEntityServiceRemote getPoOrganizationEntityService()
            throws PAException {
        return organizationEntityService;
    }

    public OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        return orgContactCorrelationService;
    }

    public PersonEntityServiceRemote getPoPersonEntityService()
            throws PAException {
        return personEntityService;
    }

    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    public RegistryUserServiceRemote getRegistryUserService() {

        return registryUserService;
    }

    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return regulatoryInfoService;
    }

    public StudyContactServiceLocal getStudyContactService() {
        return studyContactService;
    }

    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndldeService;
    }

    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    public StudySiteContactServiceLocal getStudySiteContactService()
            throws PAException {
        return studySiteContactService;
    }

    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return studyRegulatorAuthService;
    }

    public StudyResourcingServiceLocal getStudyResoucringService() {
        return studyResourcingService;
    }

    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    public TrialRegistrationServiceLocal getTrialRegistrationService()
            throws PAException {
        return trialRegistrationService;
    }

    public Organization getPAOrganizationByIndetifers(Long paIdentifer,
            String poIdentifer) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Organization getPAOrganizationByPAHealthCareFacilityId(
            Long paHealthCareFacilityId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Organization getPAOrganizationByPAOversightCommitteeId(
            Long paOversightCommitteeId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Organization getPAOrganizationByPAResearchOrganizationId(
            Long paResearchOrganizationId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public OrganizationalContact getPAOrganizationalContact(
            OrganizationalContact oc) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Person getPAPersonByIndetifers(Long paIdentifer, String poIdentifer)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Person getPAPersonByPAClinicalResearchStaffId(
            Long paClinicalResearchStaffId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Person getPAPersonByPAOrganizationalContactId(
            Long paOrganizationalContactId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    
    }
