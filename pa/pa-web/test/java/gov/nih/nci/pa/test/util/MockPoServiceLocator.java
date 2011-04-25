/**
 *
 */
package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.service.MockPoClinicalResearchStaffCorrelationService;
import gov.nih.nci.service.MockPoHealthCareFacilityCorrelationService;
import gov.nih.nci.service.MockPoHealthCareProviderCorrelationService;
import gov.nih.nci.service.MockPoOrganizationEntityService;
import gov.nih.nci.service.MockPoOrganizationalContactCorrelationService;
import gov.nih.nci.service.MockPoOversightCommitteeCorrelationService;
import gov.nih.nci.service.MockPoPersonEntityService;
import gov.nih.nci.service.MockPoResearchOrganizationCorrelationService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockPoServiceLocator implements PoServiceLocator {

    private final OrganizationEntityServiceRemote orgEntityServiceRemote = new MockPoOrganizationEntityService();
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
       return orgEntityServiceRemote;
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

    /**
     * {@inheritDoc}
     */
    public FamilyServiceRemote getFamilyService() {
        return null;
    }
}
