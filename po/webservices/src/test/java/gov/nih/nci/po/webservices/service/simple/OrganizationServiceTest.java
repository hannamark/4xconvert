package gov.nih.nci.po.webservices.service.simple;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.webservices.convert.simple.AbstractConverterTest;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.OversightCommitteeType;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.types.ResearchOrganizationType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is the test class for OrganizationServiceImpl.
 * 
 * @author Rohit Gupta
 * 
 */
public class OrganizationServiceTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.Organization org;
    private gov.nih.nci.po.webservices.types.OrganizationSearchCriteria osCriteria;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.Organization
        org = new Organization();
        org.setName("Mayo Clinic");
        org.setStatus(EntityStatus.PENDING);
        org.setAddress(getJaxbAddressList().get(0));
        org.getContact().addAll(getJaxbContactList());

        super.setUpMockObjects();

        osCriteria = new OrganizationSearchCriteria();
        osCriteria.setOrganizationName("Mayo");
        osCriteria.setOffset(0);
        osCriteria.setLimit(4);
    }

    /**
     * Testcase for OrganizationService-createOrganization
     */
    @Test
    public void testcreateOrganization() {
        OrganizationService orgService = new OrganizationServiceImpl();
        Organization retOrg = orgService.createOrganization(org);
        Assert.assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-createOrganization-Organization is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullOrganization() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.createOrganization(null);
    }

    /**
     * Testcase for OrganizationService-createOrganization-
     * EntityValidationExceptionScenario
     * 
     * @throws JMSException
     * @throws EntityValidationException
     */
    @Test(expected = ServiceException.class)
    public void testcreateOrganizationEntityValidationExceptionScenario()
            throws EntityValidationException, JMSException {
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(orgSerLocal);
        when(orgSerLocal.create(isA(gov.nih.nci.po.data.bo.Organization.class)))
                .thenThrow(
                        new EntityValidationException(
                                "EntityValidationException Occured while creating the organization.",
                                null));

        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.createOrganization(org);
    }

    /**
     * Testcase for OrganizationService-createOrganization-Exception scenario
     */
    @Test(expected = ServiceException.class)
    public void testcreateOrganizationForExceptionScenario()
            throws EntityValidationException, JMSException {
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(orgSerLocal);
        when(orgSerLocal.create(isA(gov.nih.nci.po.data.bo.Organization.class)))
                .thenThrow(
                        new ServiceException(
                                "Exception Occured while creating the organization.",
                                null));
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.createOrganization(org);
    }

    /**
     * Testcase for OrganizationService-updateOrganization
     */
    @Test
    public void testupdateOrganization() {
        OrganizationService orgService = new OrganizationServiceImpl();
        org.setId(1l);
        Organization retOrg = orgService.updateOrganization(org);
        Assert.assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-updateOrganization-- Organization is
     * null
     */
    @Test(expected = ServiceException.class)
    public void testupdateNullOrganization() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.updateOrganization(null);
    }

    /**
     * Testcase for OrganizationService-updateOrganization-- Organization DBId
     * is null
     */
    @Test(expected = ServiceException.class)
    public void testupdateOrganizationForNullDBId() {
        OrganizationService orgService = new OrganizationServiceImpl();
        org.setId(null);
        orgService.updateOrganization(org);
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Exception Scenario
     * 
     * @throws JMSException
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrganizationForExceptionScenario()
            throws JMSException {
        org.setId(1l);
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(orgSerLocal);
        doThrow(
                new ServiceException(
                        "Exception Occured while updating the organization."))
                .when(orgSerLocal).curate(
                        isA(gov.nih.nci.po.data.bo.Organization.class));
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.updateOrganization(org);
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus
     */
    @Test
    public void testChangeOrganizationStatus() {
        OrganizationService orgService = new OrganizationServiceImpl();
        Organization retOrg = orgService.changeOrganizationStatus(1l,
                EntityStatus.ACTIVE);
        Assert.assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
        Assert.assertEquals(EntityStatus.ACTIVE, retOrg.getStatus());
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationStatus-OrganizationNotFoundInDB
     */
    @Test(expected = ServiceException.class)
    public void testChangeOrganizationStatusForOrgNotFoundInDB() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.changeOrganizationStatus(1002l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for OrganizationService-getOrganization
     */
    @Test
    public void testGetOrganization() {
        OrganizationService orgService = new OrganizationServiceImpl();
        Organization retOrg = orgService.getOrganization(1l);
        Assert.assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-searchOrganizations
     */
    @Test
    public void testSearchOrganizations() {
        OrganizationService orgService = new OrganizationServiceImpl();
        List<OrganizationSearchResult> osrList = orgService
                .searchOrganizations(osCriteria);
        Assert.assertNotNull(osrList);
        Assert.assertTrue(osrList.size() > 0);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-Criteria is Null
     */
    @Test(expected = ServiceException.class)
    public void testSearchOrganizationsForNullCriteria() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.searchOrganizations(null);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-Criteria is
     * Empty(Nothing specified in search criteria)
     */
    @Test(expected = ServiceException.class)
    public void testSearchOrganizationsForEmptyCriteria() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.searchOrganizations(new OrganizationSearchCriteria());
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-No matching
     * organization found
     */
    @Test
    public void testSearchOrganizationsForNoOrganizationFound() {
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(orgSerLocal);
        when(
                orgSerLocal
                        .search(isA(gov.nih.nci.po.service.OrganizationSearchCriteria.class),
                                isA(PageSortParams.class))).thenReturn(
                new ArrayList<OrganizationSearchDTO>());

        OrganizationService orgService = new OrganizationServiceImpl();
        List<OrganizationSearchResult> osrList = orgService
                .searchOrganizations(osCriteria);
        Assert.assertNotNull(osrList);
        Assert.assertTrue(osrList.size() == 0);
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrganizationRole
     * is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullOrganizationRole() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.createOrganizationRole(null);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-OrganizationRoleId is
     * Present(shouldn't be during creation)
     */
    @Test(expected = ServiceException.class)
    public void testCreateOrganizationRoleIdPresent() {
        OrganizationService orgService = new OrganizationServiceImpl();
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(1l);
        orgService.createOrganizationRole(hcf);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-HealthCareFacility
     */
    @Test
    public void testCreateOrgRoleHealthCareProvider() {
        OrganizationService orgService = new OrganizationServiceImpl();
        OrganizationRole orgRole = orgService
                .createOrganizationRole(getHealthCareFacility());
        Assert.assertTrue(orgRole instanceof HealthCareFacility);
        Assert.assertFalse(orgRole instanceof ResearchOrganization);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-OversightCommittee
     */
    @Test
    public void testCreateOrgRoleOversightCommittee() {
        OrganizationService orgService = new OrganizationServiceImpl();
        OrganizationRole orgRole = orgService
                .createOrganizationRole(getOversightCommittee());
        Assert.assertTrue(orgRole instanceof OversightCommittee);
        Assert.assertFalse(orgRole instanceof ResearchOrganization);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-ResearchOrganization
     */
    // @Test
    public void testCreateOrgRoleResearchOrganization() {
        // TODO:: Debug as why this testcase is failing -- Mocking issue
        OrganizationService orgService = new OrganizationServiceImpl();
        OrganizationRole orgRole = orgService
                .createOrganizationRole(getResearchOrganization());
        Assert.assertTrue(orgRole instanceof ResearchOrganization);
        Assert.assertFalse(orgRole instanceof HealthCareFacility);
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-Exception
     * Scenario
     */
    @Test(expected = ServiceException.class)
    public void testCreateOrgRoleExceptionScenario() throws JMSException {
        HealthCareFacilityServiceLocal hcflocal = mock(HealthCareFacilityServiceLocal.class);
        when(serviceLocator.getHealthCareFacilityService())
                .thenReturn(hcflocal);
        doThrow(
                new ServiceException(
                        "Exception Occured while creating Organization Role."))
                .when(hcflocal).curate(
                        isA(gov.nih.nci.po.data.bo.HealthCareFacility.class));

        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.createOrganizationRole(getHealthCareFacility());
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrganizationRole
     * is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullOrganizationRole() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.updateOrganizationRole(null);
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-OrganizationRoleId is NOT
     * Present
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrganizationRoleForNullDBId() {
        OrganizationService orgService = new OrganizationServiceImpl();
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(null);
        orgService.updateOrganizationRole(hcf);
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-HealthCareFacility
     */
    @Test
    public void testUpdateOrgRoleHealthCareProvider() {
        OrganizationService orgService = new OrganizationServiceImpl();
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(1l);
        OrganizationRole orgRole = orgService.updateOrganizationRole(hcf);
        Assert.assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof HealthCareFacility);
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-OversightCommittee
     */
    @Test
    public void testUpdateOrgRoleOversightCommittee() {
        OrganizationService orgService = new OrganizationServiceImpl();
        OversightCommittee oc = getOversightCommittee();
        oc.setId(1l);
        OrganizationRole orgRole = orgService.updateOrganizationRole(oc);
        Assert.assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof OversightCommittee);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-ResearchOrganization
     */
    // @Test
    public void testUpdateOrgRoleResearchOrganization() {
        OrganizationService orgService = new OrganizationServiceImpl();
        // TODO:: Debug as why this testcase is failing -- Mocking issue
        ResearchOrganization ro = getResearchOrganization();
        ro.setId(1l);
        OrganizationRole orgRole = orgService.updateOrganizationRole(ro);
        Assert.assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof ResearchOrganization);
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-Exception
     * Scenario
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrgRoleExceptionScenario() throws JMSException {
        HealthCareFacilityServiceLocal hcflocal = mock(HealthCareFacilityServiceLocal.class);
        when(serviceLocator.getHealthCareFacilityService())
                .thenReturn(hcflocal);
        doThrow(
                new ServiceException(
                        "Exception Occured while updating Organization Role."))
                .when(hcflocal).curate(
                        isA(gov.nih.nci.po.data.bo.HealthCareFacility.class),
                        isA(String.class));

        OrganizationService orgService = new OrganizationServiceImpl();
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(1l);
        orgService.updateOrganizationRole(hcf);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId
     */
    @Test
    public void testGetOrganizationRolesByOrgId() {
        OrganizationService orgService = new OrganizationServiceImpl();
        List<OrganizationRole> orgRoleList = orgService
                .getOrganizationRolesByOrgId(1l);
        Assert.assertNotNull(orgRoleList);
        Assert.assertTrue(orgRoleList.size() >= 3);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId
     */
    @Test(expected = ServiceException.class)
    public void testGetOrganizationRolesOrgNotFoundInDB() {
        OrganizationService orgService = new OrganizationServiceImpl();
        List<OrganizationRole> orgRoleList = orgService
                .getOrganizationRolesByOrgId(1002l);
        Assert.assertNotNull(orgRoleList);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByCtepId
     */
    @Test
    public void testGetOrgRoleByCtepId() {
        OrganizationService orgService = new OrganizationServiceImpl();
        List<OrganizationRole> orgRoleList = orgService
                .getOrganizationRolesByCtepId("1234566");
        Assert.assertNotNull(orgRoleList);
        Assert.assertTrue(orgRoleList.size() >= 2);
        for (OrganizationRole orgRole : orgRoleList) {
            Assert.assertTrue((orgRole instanceof HealthCareFacility)
                    || (orgRole instanceof ResearchOrganization));
            Assert.assertFalse(orgRole instanceof OversightCommittee);
        }
    }

    /**
     * Testcase for
     * OrganizationService-getOrganizationRoleById-HealthCareFacility
     */
    @Test
    public void testGetHCFOrgRole() {
        OrganizationService orgService = new OrganizationServiceImpl();
        HealthCareFacility hcf = orgService.getOrganizationRoleById(
                HealthCareFacility.class, 1l);
        Assert.assertNotNull(hcf);
        Assert.assertTrue(hcf instanceof HealthCareFacility);
    }

    /**
     * Testcase for
     * OrganizationService-getOrganizationRoleById-OversightCommittee
     */
    @Test
    public void testGetOverCommOrgRole() {
        OrganizationService orgService = new OrganizationServiceImpl();
        OversightCommittee oc = orgService.getOrganizationRoleById(
                OversightCommittee.class, 1l);
        Assert.assertNotNull(oc);
        Assert.assertTrue(oc instanceof OversightCommittee);
    }

    /**
     * Testcase for
     * OrganizationService-getOrganizationRoleById-ResearchOrganization
     */
    @Test
    public void testGetROOrgRole() {
        OrganizationService orgService = new OrganizationServiceImpl();
        ResearchOrganization ro = orgService.getOrganizationRoleById(
                ResearchOrganization.class, 1l);
        Assert.assertNotNull(ro);
        Assert.assertTrue(ro instanceof ResearchOrganization);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-OrganizationRole
     * is Null
     */
    @Test(expected = ServiceException.class)
    public void testGetNullOrganizationRole() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.getOrganizationRoleById(null, 1l);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-HealthCareFacility
     */
    @Test
    public void testChangeHCFOrgRoleStatus() {
        OrganizationService orgService = new OrganizationServiceImpl();
        HealthCareFacility hcf = orgService.changeOrganizationRoleStatus(
                HealthCareFacility.class, 1l, EntityStatus.ACTIVE);
        Assert.assertNotNull(hcf);
        Assert.assertEquals(EntityStatus.ACTIVE, hcf.getStatus());
        Assert.assertTrue(hcf instanceof HealthCareFacility);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-ResearchOrganization
     */
    @Test
    public void testChangeROOrgRoleStatus() {
        OrganizationService orgService = new OrganizationServiceImpl();
        ResearchOrganization ro = orgService.changeOrganizationRoleStatus(
                ResearchOrganization.class, 1l, EntityStatus.ACTIVE);
        Assert.assertNotNull(ro);
        Assert.assertEquals(EntityStatus.ACTIVE, ro.getStatus());
        Assert.assertTrue(ro instanceof ResearchOrganization);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-OversightCommittee
     */
    @Test
    public void testChangeOCOrgRoleStatus() {
        OrganizationService orgService = new OrganizationServiceImpl();
        OversightCommittee oc = orgService.changeOrganizationRoleStatus(
                OversightCommittee.class, 1l, EntityStatus.ACTIVE);
        Assert.assertNotNull(oc);
        Assert.assertEquals(EntityStatus.ACTIVE, oc.getStatus());
        Assert.assertTrue(oc instanceof OversightCommittee);
    }

    /**
     * Testcase for OrganizationService-changeOrganizationRoleStatus-Exception
     * Scenario
     */
    @Test(expected = ServiceException.class)
    public void testChangeOrgRoleStatusExceptionScenario() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.changeOrganizationRoleStatus(null, 1l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-HealthCareFacility not
     * found in the DB
     */
    @Test(expected = ServiceException.class)
    public void testChangeHCFOrgRoleStatusOrgRoleNotExist() {
        OrganizationService orgService = new OrganizationServiceImpl();
        orgService.changeOrganizationRoleStatus(HealthCareFacility.class,
                543210l, EntityStatus.ACTIVE);
    }

    private HealthCareFacility getHealthCareFacility() {
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setCtepId("1234567");
        hcf.setName("Mayo HCF");
        hcf.setOrganizationId(1l);
        hcf.setStatus(EntityStatus.ACTIVE);
        hcf.getAddress().add(getJaxbAddressList().get(0));
        hcf.getContact().addAll(getJaxbContactList());
        return hcf;
    }

    private OversightCommittee getOversightCommittee() {
        OversightCommittee oc = new OversightCommittee();
        oc.setType(OversightCommitteeType.ETHICS_COMMITTEE);
        oc.setOrganizationId(1l);
        oc.setStatus(EntityStatus.ACTIVE);
        oc.getAddress().add(getJaxbAddressList().get(0));
        oc.getContact().addAll(getJaxbContactList());
        return oc;
    }

    private ResearchOrganization getResearchOrganization() {
        ResearchOrganization ro = new ResearchOrganization();
        ro.setCtepId("1221234");
        ro.setName("Mayo RO");
        ro.setOrganizationId(1l);
        ro.setType(ResearchOrganizationType.NCP);
        ro.setStatus(EntityStatus.ACTIVE);
        ro.getAddress().add(getJaxbAddressList().get(0));
        ro.getContact().addAll(getJaxbContactList());
        return ro;
    }
}
