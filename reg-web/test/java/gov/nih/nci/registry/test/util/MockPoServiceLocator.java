/**
 *
 */
package gov.nih.nci.registry.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.registry.service.MockIdentifiedOrganizationCorrelationService;
import gov.nih.nci.registry.service.MockIdentifiedPersonCorrelationService;
import gov.nih.nci.registry.service.MockOrganizationEntityService;
import gov.nih.nci.registry.service.MockOrganizationalContactCorrelationService;
import gov.nih.nci.registry.service.MockPersonEntityService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockPoServiceLocator implements PoServiceLocator {


    private final OrganizationEntityServiceRemote orgEntityServiceRemote = new MockOrganizationEntityService();
    private final HealthCareFacilityCorrelationServiceRemote hcfService = null;
    private final ResearchOrganizationCorrelationServiceRemote roService = null;
    private final OversightCommitteeCorrelationServiceRemote ocService = null;
    private final PersonEntityServiceRemote personEntityService = new MockPersonEntityService();
    private final ClinicalResearchStaffCorrelationServiceRemote crsService = null;
    private final HealthCareProviderCorrelationServiceRemote hcpService = null;
    private final OrganizationalContactCorrelationServiceRemote orgContact = new MockOrganizationalContactCorrelationService();
    private final IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationService = new MockIdentifiedOrganizationCorrelationService();
    private final IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationService = new MockIdentifiedPersonCorrelationService();

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        return orgEntityServiceRemote;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        return hcfService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService()  {
        return roService ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        return ocService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        return crsService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        return hcpService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        return orgContact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonEntityServiceRemote getPersonEntityService() {
        return personEntityService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        return identifiedOrganizationCorrelationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        return identifiedPersonCorrelationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public FamilyServiceRemote getFamilyService() {
        FamilyServiceRemote svc = mock(FamilyServiceRemote.class);
        Map<Ii, FamilyDTO> results = new HashMap<Ii, FamilyDTO>();
        when(svc.getFamilies(any(Set.class))).thenReturn(results);
        return svc;
    }
}

