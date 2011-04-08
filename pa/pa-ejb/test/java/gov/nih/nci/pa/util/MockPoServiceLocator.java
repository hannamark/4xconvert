package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.MockPoClinicalResearchStaffCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareFacilityCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareProviderCorrelationService;
import gov.nih.nci.pa.service.MockPoOrganizationEntityService;
import gov.nih.nci.pa.service.MockPoOrganizationalContactCorrelationService;
import gov.nih.nci.pa.service.MockPoOversightCommitteeCorrelationService;
import gov.nih.nci.pa.service.MockPoPersonEntityService;
import gov.nih.nci.pa.service.MockPoResearchOrganizationCorrelationService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

public class MockPoServiceLocator implements PoServiceLocator {

    private final OrganizationEntityServiceRemote organizationEntityServiceRemote = new MockPoOrganizationEntityService();
    private final HealthCareFacilityCorrelationServiceRemote hcfService = new MockPoHealthCareFacilityCorrelationService();
    private final ResearchOrganizationCorrelationServiceRemote roService = new MockPoResearchOrganizationCorrelationService();
    private final OversightCommitteeCorrelationServiceRemote ocService = new MockPoOversightCommitteeCorrelationService();
    private final PersonEntityServiceRemote personEntityService = new MockPoPersonEntityService();
    private final ClinicalResearchStaffCorrelationServiceRemote crsService = new MockPoClinicalResearchStaffCorrelationService();
    private final HealthCareProviderCorrelationServiceRemote hcpService = new MockPoHealthCareProviderCorrelationService();
    private final OrganizationalContactCorrelationServiceRemote orgContact = new MockPoOrganizationalContactCorrelationService();

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
       return organizationEntityServiceRemote;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return hcfService;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        return roService;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        return ocService;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        return crsService;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        return hcpService;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        return orgContact;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        return personEntityService;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        return null;
    }
}
