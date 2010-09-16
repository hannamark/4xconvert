package gov.nih.nci.po.service;

import gov.nih.nci.services.BusinessServiceRemote;
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

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BusinessServiceBeanTest extends AbstractBeanTest {

    protected BusinessServiceRemote busService;
    protected OrganizationEntityServiceRemote orgService;
    protected PersonEntityServiceRemote personService;
    protected ResearchOrganizationCorrelationServiceRemote researchOrgService;
    protected OversightCommitteeCorrelationServiceRemote oversightComService;
    protected ClinicalResearchStaffCorrelationServiceRemote crsService;
    protected HealthCareProviderCorrelationServiceRemote hcpService;
    protected IdentifiedPersonCorrelationServiceRemote idpService;
    protected IdentifiedOrganizationCorrelationServiceRemote idoService;
    protected HealthCareFacilityCorrelationServiceRemote hcfService;
    protected OrganizationalContactCorrelationServiceRemote ocService;

    @Test
    public void testGetByIdWithCorrelations() throws Exception {
        BusinessServiceTestHelper.testGetByIdWithCorrelations(orgService, personService, busService,
                crsService, researchOrgService, oversightComService, true);
    }

    @Test
    public void testOrgRoleCorrelationsGetById() throws Exception {
        BusinessServiceTestHelper.helpTestOrgRoleCorrelationsGetById(
                researchOrgService, busService, orgService);
    }


    @Test
    public void testPersonRoleCorrelationsGetById() throws Exception {
        BusinessServiceTestHelper.helpTestPersonRoleCorrelationsGetById(
                crsService, busService, orgService, personService);
    }

    @Test
    public void testSearchCorrelationWithEntities() throws Exception {
        BusinessServiceTestHelper.testSearchCorrelationsWithEntities(personService, orgService, busService,
                crsService, hcpService, idpService, idoService,
                hcfService, researchOrgService, ocService, oversightComService);
    }

    @Test
    public void testSearchEntitiesWithCorrelations() throws Exception {
        BusinessServiceTestHelper.testSearchEntitiesWithCorrelations
            (personService, orgService, busService,
                crsService, hcpService, idpService, idoService,
                hcfService, researchOrgService, ocService, oversightComService);
    }

    /**
     * Init the test.
     *
     * @throws NamingException on error.
     */
    @Before
    public void init() throws NamingException {
        if (busService == null) {
            busService = EjbTestHelper.getBusinessService();
        }
        if (orgService == null) {
            orgService = EjbTestHelper.getOrganizationEntityServiceBeanAsRemote();
        }
        if (personService == null) {
            personService = EjbTestHelper.getPersonEntityServiceBeanAsRemote();
        }
        if (researchOrgService == null) {
            researchOrgService = EjbTestHelper.getResearchOrganizationCorrelationServiceRemote();
        }
        if (oversightComService == null) {
            oversightComService = EjbTestHelper.getOversightCommitteeCorrelationServiceRemote();
        }
        if (crsService == null) {
            crsService = EjbTestHelper.getClinicalResearchStaffCorrelationServiceRemote();
        }
        if (hcpService == null) {
            hcpService = EjbTestHelper.getHealthCareProviderCorrelationServiceRemote();
        }
        if (idpService == null) {
            idpService = EjbTestHelper.getIdentifiedPersonServiceBeanAsRemote();
        }
        if (idoService == null) {
            idoService = EjbTestHelper.getIdentifiedOrganizationServiceBeanAsRemote();
        }
        if (hcfService == null) {
            hcfService = EjbTestHelper.getHealthCareFacilityCorrelationServiceRemote();
        }
        if (ocService == null) {
            ocService = EjbTestHelper.getOrganizationalContactCorrelationServiceRemote();
        }

    }

    /**
     * cleanup after test is complete.
     *
     * @throws NamingException on error.
     */
    @After
    public void cleanup() throws NamingException {
        busService = null;
        orgService = null;
        oversightComService = null;
        researchOrgService = null;
        personService = null;
        crsService = null;
        idpService = null;
        idoService = null;
        hcfService = null;
        ocService = null;
    }

}
