package gov.nih.nci.po.service;

import gov.nih.nci.services.BusinessServiceBean;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
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
    
    /**
     * Init the test.
     * 
     * @throws NamingException on error.
     */
    @Before
    public void init() throws NamingException {
        if (busService == null) {
            busService = (BusinessServiceBean) EjbTestHelper.getBusinessService();
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
    }
    
}
