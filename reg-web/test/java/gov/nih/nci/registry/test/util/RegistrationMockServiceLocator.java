package gov.nih.nci.registry.test.util;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.TrialRegistrationServiceRemote;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
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
import gov.nih.nci.registry.service.MockStudyContactService;
import gov.nih.nci.registry.service.MockStudyIndldeService;
import gov.nih.nci.registry.service.MockStudyOverallStatusService;
import gov.nih.nci.registry.service.MockStudyParticipationContactService;
import gov.nih.nci.registry.service.MockStudyParticipationService;
import gov.nih.nci.registry.service.MockStudyProtocolService;
import gov.nih.nci.registry.service.MockStudyResourcingService;
import gov.nih.nci.registry.service.MockTrialRegistrationService;
import gov.nih.nci.registry.util.ServiceLocator;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

public class RegistrationMockServiceLocator implements ServiceLocator {
    
    private final StudyProtocolServiceRemote studyProtocolService = new MockStudyProtocolService();
    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final DocumentServiceRemote documentService = new MockDocumentService(); 
    private final StudyOverallStatusServiceRemote studyOverallStatusService = new MockStudyOverallStatusService();
    private final LookUpTableServiceRemote lookUpTableService = new MockLookUpTableService();
    private final TrialRegistrationServiceRemote  trialRegistrationService = new MockTrialRegistrationService();
    private final MailManagerServiceRemote mailManagerService = new MockMailManagerService();
    private final StudyIndldeServiceRemote  studyIndldeService = new MockStudyIndldeService();
    private final StudyParticipationServiceRemote studyParticipationService = new MockStudyParticipationService();
    private final StudyResourcingServiceRemote studyResourcingService = new MockStudyResourcingService();
    private final PAOrganizationServiceRemote paOrganizationService = new MockPAOrganizationService();
    private final OrganizationEntityServiceRemote organizationEntityService = new MockOrganizationEntityService();
    private final IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationService = new MockIdentifiedOrganizationCorrelationService();
    private final PersonEntityServiceRemote personEntityService = new MockPersonEntityService();
    private final IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationService = new MockIdentifiedPersonCorrelationService();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final OrganizationalContactCorrelationServiceRemote orgContactCorrelationService = new MockOrganizationalContactCorrelationService();
    private final StudyContactServiceRemote studyContactService = new MockStudyContactService();
    private final StudyParticipationContactServiceRemote studyParticipationContactService = new MockStudyParticipationContactService();
    private final RegistryUserServiceRemote registryUserService = new MockRegistryUserService();
    public DocumentServiceRemote getDocumentService() {
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

    public MailManagerServiceRemote getMailManagerService() throws PAException {
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
        // TODO Auto-generated method stub
        return null;
    }

    public StudyContactServiceRemote getStudyContactService() {
        return studyContactService;
    }

    public StudyIndldeServiceRemote getStudyIndldeService() {
        return studyIndldeService;
    }

    public StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    public StudyParticipationContactServiceRemote getStudyParticipationContactService()
            throws PAException {
        return studyParticipationContactService;
    }

    public StudyParticipationServiceRemote getStudyParticipationService() {
        return studyParticipationService;
    }

    public StudyProtocolServiceRemote getStudyProtocolService() {
        return studyProtocolService;
    }

    public StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyResourcingServiceRemote getStudyResoucringService() {
        return studyResourcingService;
    }

    public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    public TrialRegistrationServiceRemote getTrialRegistrationService()
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
