package gov.nih.nci.po.webservices.service.simple;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bo.HealthCareFacilityBoService;
import gov.nih.nci.po.webservices.service.bo.OrganizationBoService;
import gov.nih.nci.po.webservices.service.bo.OversightCommitteeBoService;
import gov.nih.nci.po.webservices.service.bo.ResearchOrganizationBoService;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jms.JMSException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * This is the test class for OrganizationServiceImpl.
 * 
 * @author Rohit Gupta
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PoServiceUtil.class)
public class OrganizationServiceTest extends AbstractEndpointTest {

    private gov.nih.nci.po.webservices.types.Organization org;
    private gov.nih.nci.po.data.bo.Organization ctep;
    private gov.nih.nci.po.webservices.types.OrganizationSearchCriteria osCriteria;
    private OrganizationServiceImpl orgService;
    private OrganizationBoService organizationBoService;
    private HealthCareFacilityBoService healthCareFacilityBoService;
    private OversightCommitteeBoService oversightCommitteeBoService;
    private ResearchOrganizationBoService researchOrganizationBoService;


    @Before
    public void setUp() {
        setupServiceLocator();
        when(serviceLocator.getCountryService().getCountryByAlpha3("USA")).thenReturn(ModelUtils.getDefaultCountry());

        initBoServices();
        
        ctep = ModelUtils.getBasicOrganization();
        ctep.setId(1L);
        mockStatic(PoServiceUtil.class);

        // setting up gov.nih.nci.po.webservices.types.Organization
        org = new Organization();
        org.setName("Mayo Clinic");
        org.setStatus(EntityStatus.PENDING);
        org.setAddress(getJaxbAddressList().get(0));
        org.getContact().addAll(getJaxbContactList());

        osCriteria = new OrganizationSearchCriteria();
        osCriteria.setOrganizationName("Mayo");
        osCriteria.setOffset(0);
        osCriteria.setLimit(4);

        orgService = new OrganizationServiceImpl();
        orgService.setHealthCareFacilityBoService(healthCareFacilityBoService);
        orgService.setOrganizationBoService(organizationBoService);
        orgService.setOversightCommitteeBoService(oversightCommitteeBoService);
        orgService.setResearchOrganizationBoService(researchOrganizationBoService);

    }

    private void initBoServices() {
        organizationBoService = mock(OrganizationBoService.class);
        healthCareFacilityBoService = mock(HealthCareFacilityBoService.class);
        oversightCommitteeBoService = mock(OversightCommitteeBoService.class);
        researchOrganizationBoService = mock(ResearchOrganizationBoService.class);
    }

    /**
     * Testcase for OrganizationService-createOrganization
     */
    @Test
    public void testCreateOrganization() throws JMSException, EntityValidationException {

        when(organizationBoService.create(any(gov.nih.nci.po.data.bo.Organization.class), anyString()))
                .thenReturn(1L);

        when(organizationBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.Organization>() {
            @Override
            public gov.nih.nci.po.data.bo.Organization answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization result = new gov.nih.nci.po.data.bo.Organization();

                result.setId(1L);
                result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.PENDING);
                return result;
            }
        });

        Organization retOrg = orgService.createOrganization(org);
        assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-createOrganization-Organization is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullOrganization() {
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
        when(organizationBoService.create(isA(gov.nih.nci.po.data.bo.Organization.class), anyString()))
                .thenThrow(
                        new EntityValidationException(
                                "EntityValidationException Occured while creating the organization.",
                                null));

        orgService.createOrganization(org);
    }

    /**
     * Testcase for OrganizationService-createOrganization-Exception scenario
     */
    @Test(expected = ServiceException.class)
    public void testcreateOrganizationForExceptionScenario()
            throws EntityValidationException, JMSException {
        when(organizationBoService.create(isA(gov.nih.nci.po.data.bo.Organization.class), anyString()))
                .thenThrow(
                        new ServiceException(
                                "Exception Occured while creating the organization.",
                                null));
        
        orgService.createOrganization(org);
    }

    /**
     * Testcase for OrganizationService-createOrganization-Exception scenario- CTEP ID
     */
    @Test(expected = ServiceException.class)
    public void testCreateOrganizationHavingCTEPID()
            throws EntityValidationException, JMSException { 
        org.setCtepId("VA212");
        orgService.createOrganization(org);
    }
    /**
     * Testcase for OrganizationService-updateOrganization
     */
    @Test
    public void testUpdateOrganization() {
        
        org.setId(1L);

        when(organizationBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.Organization>() {
            @Override
            public gov.nih.nci.po.data.bo.Organization answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization result = new gov.nih.nci.po.data.bo.Organization();

                result.setId(1L);
                result.setName(org.getName());
                result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.PENDING);
                return result;
            }
        });

        Organization retOrg = orgService.updateOrganization(org);
        assertNotNull(retOrg);
        Assert.assertEquals(1L, retOrg.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-updateOrganization-- Organization is
     * null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullOrganization() {
        
        orgService.updateOrganization(null);
    }

    /**
     * Testcase for OrganizationService-updateOrganization-- Organization DBId
     * is null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrganizationForNullDBId() {
        
        org.setId(null);
        orgService.updateOrganization(org);
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Exception Scenario
     * 
     * @throws JMSException
     * @throws EntityValidationException 
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrganizationForExceptionScenario()
            throws JMSException, EntityValidationException {
        org.setId(1l);

        gov.nih.nci.po.data.bo.Organization instance = ModelUtils.getBasicOrganization();
        instance.setId(1L);

        doThrow(new RuntimeException())
                .when(organizationBoService).curate(any(gov.nih.nci.po.data.bo.Organization.class), anyString());

        when(organizationBoService.getById(1L)).thenReturn(instance);

        orgService.updateOrganization(org);
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Exception Scenario-CTEP ID
     * 
     * @throws JMSException
     * @throws EntityValidationException 
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrganizationHavingCTEPID()
            throws JMSException, EntityValidationException {
        org.setId(1l);
        org.setCtepId("VA212");        

        orgService.updateOrganization(org);
    }
    /**
     * Testcase for OrganizationService-changeOrganizationStatus
     */
    @Test
    public void testChangeOrganizationStatus() {

        when(organizationBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.Organization>() {
            @Override
            public gov.nih.nci.po.data.bo.Organization answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization result = new gov.nih.nci.po.data.bo.Organization();

                result.setId(1L);
                result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.PENDING);
                return result;
            }
        });

        Organization retOrg = orgService.changeOrganizationStatus(1l,
                EntityStatus.ACTIVE);
        assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
    }

    @Test(expected = RuntimeException.class)
    public void testChangeOrganizationStatusWithException() throws JMSException {

        when(organizationBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.Organization>() {
            @Override
            public gov.nih.nci.po.data.bo.Organization answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization result = new gov.nih.nci.po.data.bo.Organization();

                result.setId(1L);
                result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.PENDING);
                return result;
            }
        });

        doThrow(new RuntimeException())
                .when(organizationBoService).curate(any(gov.nih.nci.po.data.bo.Organization.class));

        Organization retOrg = orgService.changeOrganizationStatus(1l,
                EntityStatus.ACTIVE);
        assertNotNull(retOrg);
        Assert.assertEquals(1l, retOrg.getId().longValue());
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationStatus-OrganizationNotFoundInDB
     */
    @Test(expected = ServiceException.class)
    public void testChangeOrganizationStatusForOrgNotFoundInDB() {
        
        orgService.changeOrganizationStatus(1002l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for OrganizationService-getOrganization
     */
    @Test
    public void testGetOrganization() {
        
        Organization retOrg = orgService.getOrganization(1l);
        verify(organizationBoService).getById(1l);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations
     */
    @Test
    public void testSearchOrganizations() {

        when(organizationBoService.search(any(gov.nih.nci.po.service.OrganizationSearchCriteria.class), any(PageSortParams.class)))
                .thenAnswer(new Answer<List<OrganizationSearchDTO>>() {
                    @Override
                    public List<OrganizationSearchDTO> answer(InvocationOnMock invocation) throws Throwable {
                        List<OrganizationSearchDTO> results = new ArrayList<OrganizationSearchDTO>();
                        OrganizationSearchDTO dto = new OrganizationSearchDTO();
                        dto.setId(1L);
                        dto.setStatusCode("ACTIVE");
                        results.add(dto);
                        return results;
                    }
                });

        List<OrganizationSearchResult> osrList = orgService
                .searchOrganizations(osCriteria);
        verify(organizationBoService)
                .search(isA(gov.nih.nci.po.service.OrganizationSearchCriteria.class), isA(PageSortParams.class));
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-Criteria is Null
     */
    @Test(expected = ServiceException.class)
    public void testSearchOrganizationsForNullCriteria() {
        
        orgService.searchOrganizations(null);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-Criteria is
     * Empty(Nothing specified in search criteria)
     */
    @Test(expected = ServiceException.class)
    public void testSearchOrganizationsForEmptyCriteria() {
        
        orgService.searchOrganizations(new OrganizationSearchCriteria());
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-No matching
     * organization found
     */
    @Test
    public void testSearchOrganizationsForNoOrganizationFound() {
        when(
                organizationBoService
                        .search(isA(gov.nih.nci.po.service.OrganizationSearchCriteria.class),
                                isA(PageSortParams.class))).thenReturn(
                new ArrayList<OrganizationSearchDTO>());

        
        List<OrganizationSearchResult> osrList = orgService
                .searchOrganizations(osCriteria);
        assertNotNull(osrList);
        Assert.assertTrue(osrList.size() == 0);
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrganizationRole
     * is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullOrganizationRole() {
        
        orgService.createOrganizationRole(null);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-OrganizationRoleId is
     * Present(shouldn't be during creation)
     */
    @Test(expected = ServiceException.class)
    public void testCreateOrganizationRoleIdPresent() {
        
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(1l);
        orgService.createOrganizationRole(hcf);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-HealthCareFacility
     */
    @Test
    public void testCreateOrgRoleHealthCareFacility() throws JMSException, EntityValidationException {
        HealthCareFacility healthCareFacility = getHealthCareFacility();

        when(healthCareFacilityBoService.create(any(gov.nih.nci.po.data.bo.HealthCareFacility.class)))
                .thenReturn(1L);

        when(healthCareFacilityBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.HealthCareFacility>() {
            @Override
            public gov.nih.nci.po.data.bo.HealthCareFacility answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization player = new gov.nih.nci.po.data.bo.Organization();
                player.setId(2L);

                gov.nih.nci.po.data.bo.HealthCareFacility result = new gov.nih.nci.po.data.bo.HealthCareFacility();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                return result;
            }
        });

        OrganizationRole orgRole = orgService
                .createOrganizationRole(healthCareFacility);
       verify(healthCareFacilityBoService).create(any(gov.nih.nci.po.data.bo.HealthCareFacility.class));
    }
    
    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-HealthCareFacility- HCF having CTEP ID
     */
    @Test(expected = ServiceException.class)
    public void testCreateOrgRoleHealthCareFacilityHavingCTEPID() throws JMSException, EntityValidationException {
        HealthCareFacility healthCareFacility = getHealthCareFacility();
        healthCareFacility.setCtepId("VA212");

        orgService.createOrganizationRole(healthCareFacility);
    }

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-OversightCommittee
     */
    @Test
    public void testCreateOrgRoleOversightCommittee() throws JMSException, EntityValidationException {


        OversightCommittee oversightCommittee = getOversightCommittee();

        when(oversightCommitteeBoService.create(any(gov.nih.nci.po.data.bo.OversightCommittee.class)))
                .thenReturn(1L);

        when(oversightCommitteeBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.OversightCommittee>() {
            @Override
            public gov.nih.nci.po.data.bo.OversightCommittee answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization player = new gov.nih.nci.po.data.bo.Organization();
                player.setId(2L);

                gov.nih.nci.po.data.bo.OversightCommitteeType type
                        = new gov.nih.nci.po.data.bo.OversightCommitteeType(OversightCommitteeType.RESEARCH_ETHICS_BOARD.value());

                gov.nih.nci.po.data.bo.OversightCommittee result = new gov.nih.nci.po.data.bo.OversightCommittee();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                result.setTypeCode(type);
                return result;
            }
        });


        OrganizationRole orgRole = orgService
                .createOrganizationRole(oversightCommittee);

        verify(oversightCommitteeBoService).create(any(gov.nih.nci.po.data.bo.OversightCommittee.class));
    }
    

    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-ResearchOrganization
     */
    @Test
    public void testCreateOrgRoleResearchOrganization() throws JMSException, EntityValidationException {
        
        ResearchOrganization researchOrganization = getResearchOrganization();

        when(researchOrganizationBoService.create(any(gov.nih.nci.po.data.bo.ResearchOrganization.class)))
                .thenReturn(1L);

        when(researchOrganizationBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.ResearchOrganization>() {
            @Override
            public gov.nih.nci.po.data.bo.ResearchOrganization answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Organization player = new gov.nih.nci.po.data.bo.Organization();
                player.setId(2L);

                gov.nih.nci.po.data.bo.ResearchOrganization result = new gov.nih.nci.po.data.bo.ResearchOrganization();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                return result;
            }
        });

        OrganizationRole orgRole = orgService.createOrganizationRole(researchOrganization);
       verify(researchOrganizationBoService).create(any(gov.nih.nci.po.data.bo.ResearchOrganization.class));
   }
    
    /**
     * Testcase for
     * OrganizationService-createOrganizationRole-ResearchOrganization-RO having CTEP ID
     */
    @Test(expected=ServiceException.class)
    public void testCreateOrgRoleResearchOrganizationHavingCTEPID() throws JMSException, EntityValidationException {
        
        ResearchOrganization researchOrganization = getResearchOrganization();

        researchOrganization.setCtepId("VA212");

        orgService.createOrganizationRole(researchOrganization);
   }

    /**
     * Testcase for OrganizationService-createOrganizationRole-Exception
     * Scenario
     */
    @Test(expected = ServiceException.class)
    public void testCreateOrgRoleExceptionScenario() throws JMSException {

        doThrow(
                new ServiceException(
                        "Exception Occured while creating Organization Role."))
                .when(healthCareFacilityBoService).curate(
                        isA(gov.nih.nci.po.data.bo.HealthCareFacility.class));

        
        orgService.createOrganizationRole(getHealthCareFacility());
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrganizationRole
     * is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullOrganizationRole() {
        
        orgService.updateOrganizationRole(null);
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-OrganizationRoleId is NOT
     * Present
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrganizationRoleForNullDBId() {
        
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(null);
        orgService.updateOrganizationRole(hcf);
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-HealthCareFacility
     */
    @Test
    public void testUpdateOrgRoleHealthCareFacility() {
        
        final HealthCareFacility healthCareFacility = getHealthCareFacility();
        healthCareFacility.setId(1l);
        healthCareFacility.setOrganizationId(2L);

        final gov.nih.nci.po.data.bo.Organization player = ModelUtils.getBasicOrganization();
        player.setId(2L);

        when(healthCareFacilityBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.HealthCareFacility>() {
            @Override
            public gov.nih.nci.po.data.bo.HealthCareFacility answer(InvocationOnMock invocation) throws Throwable {


                gov.nih.nci.po.data.bo.HealthCareFacility result = new gov.nih.nci.po.data.bo.HealthCareFacility();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                result.setName(healthCareFacility.getName());
                return result;
            }
        });


        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(player);


        OrganizationRole orgRole = orgService.updateOrganizationRole(healthCareFacility);
        assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof HealthCareFacility);
    }
    
    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-HealthCareFacility
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrgRoleHealthCareFacilityHavingCTEPID() {
        
        final HealthCareFacility healthCareFacility = getHealthCareFacility();
        healthCareFacility.setId(1l);
        healthCareFacility.setOrganizationId(2L);
        healthCareFacility.setCtepId("VA212");
        
        orgService.updateOrganizationRole(healthCareFacility);
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-OversightCommittee
     */
    @Test
    public void testUpdateOrgRoleOversightCommittee() {
        
        OversightCommittee oc = getOversightCommittee();
        oc.setId(1l);
        oc.setOrganizationId(2L);

        final gov.nih.nci.po.data.bo.Organization player = new gov.nih.nci.po.data.bo.Organization();
        player.setId(2L);

        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(player);

        when(oversightCommitteeBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.OversightCommittee>() {
            @Override
            public gov.nih.nci.po.data.bo.OversightCommittee answer(InvocationOnMock invocation) throws Throwable {


                gov.nih.nci.po.data.bo.OversightCommitteeType type
                        = new gov.nih.nci.po.data.bo.OversightCommitteeType(OversightCommitteeType.RESEARCH_ETHICS_BOARD.value());

                gov.nih.nci.po.data.bo.OversightCommittee result = new gov.nih.nci.po.data.bo.OversightCommittee();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                result.setTypeCode(type);

                return result;
            }
        });


        when(serviceLocator.getGenericCodeValueService().getByCode(isA(Class.class), anyString()))
                .thenAnswer(new Answer<gov.nih.nci.po.data.bo.OversightCommitteeType>() {

                    @Override
                    public gov.nih.nci.po.data.bo.OversightCommitteeType answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.OversightCommitteeType result
                                = new gov.nih.nci.po.data.bo.OversightCommitteeType((String) invocation.getArguments()[1]);

                        return result;
                    }
                });

        OrganizationRole orgRole = orgService.updateOrganizationRole(oc);
        assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof OversightCommittee);
    }

   
    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-ResearchOrganization
     */
     @Test
    public void testUpdateOrgRoleResearchOrganization() {
                  
         ResearchOrganization ro = getResearchOrganization();
         ro.setId(1l);
         ro.setOrganizationId(2L);
         ro.setName("test ro");

         final gov.nih.nci.po.data.bo.Organization player = new gov.nih.nci.po.data.bo.Organization();
         player.setId(2L);

         when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(player);

         when(researchOrganizationBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.ResearchOrganization>() {
             @Override
             public gov.nih.nci.po.data.bo.ResearchOrganization answer(InvocationOnMock invocation) throws Throwable {               
                 gov.nih.nci.po.data.bo.ResearchOrganization result = new gov.nih.nci.po.data.bo.ResearchOrganization();
                 result.setId(1L);
                 result.setStatus(RoleStatus.PENDING);
                 result.setPlayer(player);    
                 result.setName("test ro");
                 return result;
             }
         });

         OrganizationRole orgRole = orgService.updateOrganizationRole(ro);
         assertNotNull(orgRole);
         Assert.assertTrue(orgRole instanceof ResearchOrganization);
    }

     /**
      * Testcase for
      * OrganizationService-updateOrganizationRole-ResearchOrganization
      */
      @Test(expected = ServiceException.class)
     public void testUpdateOrgRoleResearchOrganizationHavingCTEPID() {
                   
          ResearchOrganization ro = getResearchOrganization();
          ro.setId(1l);
          ro.setOrganizationId(2L);
          ro.setName("test ro");
          ro.setCtepId("VA212");
          
          orgService.updateOrganizationRole(ro);
     }
      
    /**
     * Testcase for OrganizationService-createOrganizationRole-Exception
     * Scenario
     */
    @Test(expected = ServiceException.class)
    public void testUpdateOrgRoleExceptionScenario() throws JMSException {
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(1l);

        gov.nih.nci.po.data.bo.HealthCareFacility instance = ModelUtils.getBasicHealthCareFacility();
        when(healthCareFacilityBoService.getById(1L)).thenReturn(instance);

        HealthCareFacilityServiceLocal healthCareFacilityServiceLocal = healthCareFacilityBoService;
        doThrow(
                new ServiceException(
                        "Exception Occured while updating Organization Role."))
                .when(healthCareFacilityServiceLocal).curate(
                        isA(gov.nih.nci.po.data.bo.HealthCareFacility.class),
                        isA(String.class));



        orgService.updateOrganizationRole(hcf);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId
     */
    @Test
    public void testGetOrganizationRolesByOrgId() {
        final gov.nih.nci.po.data.bo.Organization organization = new gov.nih.nci.po.data.bo.Organization();
        organization.setId(1L);

        when(organizationBoService.getById(1L))
                .thenAnswer(new Answer< gov.nih.nci.po.data.bo.Organization>() {
                    @Override
                    public gov.nih.nci.po.data.bo.Organization answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.Organization result = new  gov.nih.nci.po.data.bo.Organization();

                        gov.nih.nci.po.data.bo.ResearchOrganization researchOrganization
                                = new gov.nih.nci.po.data.bo.ResearchOrganization();
                        researchOrganization.setPlayer(organization);
                        researchOrganization.setStatus(RoleStatus.ACTIVE);

                        gov.nih.nci.po.data.bo.HealthCareFacility healthCareFacility
                                = new gov.nih.nci.po.data.bo.HealthCareFacility();
                        healthCareFacility.setPlayer(organization);
                        healthCareFacility.setStatus(RoleStatus.ACTIVE);

                        gov.nih.nci.po.data.bo.OversightCommittee oversightCommittee = new gov.nih.nci.po.data.bo.OversightCommittee();
                        oversightCommittee.setPlayer(organization);
                        oversightCommittee.setStatus(RoleStatus.ACTIVE);
                        oversightCommittee.setTypeCode(
                                new gov.nih.nci.po.data.bo.OversightCommitteeType(OversightCommitteeType.ETHICS_COMMITTEE.value())
                        );

                        result.getResearchOrganizations().add(researchOrganization);
                        result.getHealthCareFacilities().add(healthCareFacility);
                        result.getOversightCommittees().add(oversightCommittee);
                        return result;
                    }
                });

        List<OrganizationRole> orgRoleList = orgService
                .getOrganizationRolesByOrgId(1l);
        assertEquals(3, orgRoleList.size());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId
     */
    @Test(expected = ServiceException.class)
    public void testGetOrganizationRolesOrgNotFoundInDB() {
        
        List<OrganizationRole> orgRoleList = orgService
                .getOrganizationRolesByOrgId(1002l);
        assertNotNull(orgRoleList);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByCtepId
     */
    @Test
    public void testGetOrgRoleByCtepId() {

        final gov.nih.nci.iso21090.Ii ctepId = new gov.nih.nci.iso21090.Ii();
        ctepId.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
        ctepId.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
        ctepId.setExtension("1234566");

        final gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        when(healthCareFacilityBoService.search(isA(SearchCriteria.class)))
            .thenAnswer(new Answer<List<gov.nih.nci.po.data.bo.HealthCareFacility>>() {
                @Override
                public List<gov.nih.nci.po.data.bo.HealthCareFacility> answer(InvocationOnMock invocation) throws Throwable {
                    List<gov.nih.nci.po.data.bo.HealthCareFacility> result = new ArrayList<gov.nih.nci.po.data.bo.HealthCareFacility>();

                    //create an instance with assigned ctep id 1234566
                    gov.nih.nci.po.data.bo.HealthCareFacility instance = new gov.nih.nci.po.data.bo.HealthCareFacility();
                    instance.getOtherIdentifiers().add(ctepId);
                    instance.setPlayer(organization);
                    instance.setStatus(RoleStatus.ACTIVE);

                    result.add(instance);

                    return result;
                }
            });

        when(researchOrganizationBoService.search(isA(SearchCriteria.class)))
                .thenAnswer(new Answer<List<gov.nih.nci.po.data.bo.ResearchOrganization>>() {
                    @Override
                    public List<gov.nih.nci.po.data.bo.ResearchOrganization> answer(InvocationOnMock invocation) throws Throwable {
                        List<gov.nih.nci.po.data.bo.ResearchOrganization> result = new ArrayList<gov.nih.nci.po.data.bo.ResearchOrganization>();

                        //create an instance with assigned ctep id 1234566
                        gov.nih.nci.po.data.bo.ResearchOrganization instance = new gov.nih.nci.po.data.bo.ResearchOrganization();
                        instance.getOtherIdentifiers().add(ctepId);
                        instance.setPlayer(organization);
                        instance.setStatus(RoleStatus.ACTIVE);

                        result.add(instance);

                        return result;
                    }
                });

        when(organizationBoService.getById(2L)).thenReturn(organization);

        List<OrganizationRole> orgRoleList = orgService
                .getOrganizationRolesByCtepId("1234566");

        assertNotNull(orgRoleList);
        assertEquals(2, orgRoleList.size());

        verify(oversightCommitteeBoService, never()).search(isA(SearchCriteria.class));
    }

    /**
     * Testcase for
     * OrganizationService-getOrganizationRoleById-HealthCareFacility
     */
    @Test
    public void testGetHCFOrgRole() {

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        gov.nih.nci.po.data.bo.HealthCareFacility instance = ModelUtils.getBasicHealthCareFacility();
        instance.setPlayer(organization);

        when(organizationBoService.getById(2L)).thenReturn(organization);
        when(healthCareFacilityBoService.getById(1L)).thenReturn(instance);

        HealthCareFacility hcf = orgService.getOrganizationRoleById(
                HealthCareFacility.class, 1l);
        assertNotNull(hcf);
        Assert.assertTrue(hcf instanceof HealthCareFacility);
    }

    /**
     * Testcase for
     * OrganizationService-getOrganizationRoleById-OversightCommittee
     */
    @Test
    public void testGetOverCommOrgRole() {

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        gov.nih.nci.po.data.bo.OversightCommitteeType type = new gov.nih.nci.po.data.bo.OversightCommitteeType(
                OversightCommitteeType.ETHICS_COMMITTEE.value()
        );
        gov.nih.nci.po.data.bo.OversightCommittee instance = ModelUtils.getBasicOversightCommittee(type);
        instance.setPlayer(organization);

        when(organizationBoService.getById(2L)).thenReturn(organization);
        when(oversightCommitteeBoService.getById(1L)).thenReturn(instance);

        OversightCommittee oc = orgService.getOrganizationRoleById(
                OversightCommittee.class, 1l);
        assertNotNull(oc);
        Assert.assertTrue(oc instanceof OversightCommittee);
    }

    /**
     * Testcase for
     * OrganizationService-getOrganizationRoleById-ResearchOrganization
     */
    @Test
    public void testGetROOrgRole() {

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        gov.nih.nci.po.data.bo.ResearchOrganizationType type = new gov.nih.nci.po.data.bo.ResearchOrganizationType(
                ResearchOrganizationType.NCTN.value(), ""
        );
        gov.nih.nci.po.data.bo.ResearchOrganization instance = ModelUtils.getBasicResearchOrganization(type);
        instance.setPlayer(organization);

        when(organizationBoService.getById(2L)).thenReturn(organization);
        when(researchOrganizationBoService.getById(1L)).thenReturn(instance);

        ResearchOrganization ro = orgService.getOrganizationRoleById(
                ResearchOrganization.class, 1l);
        assertNotNull(ro);
        Assert.assertTrue(ro instanceof ResearchOrganization);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-OrganizationRole
     * is Null
     */
    @Test(expected = ServiceException.class)
    public void testGetNullOrganizationRole() {
        
        orgService.getOrganizationRoleById(null, 1l);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-HealthCareFacility
     */
    @Test
    public void testChangeHCFOrgRoleStatus() throws JMSException {

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        gov.nih.nci.po.data.bo.HealthCareFacility healthCareFacility = ModelUtils.getBasicHealthCareFacility();
        healthCareFacility.setStatus(RoleStatus.PENDING);
        healthCareFacility.setPlayer(organization);

        when(organizationBoService.getById(2L)).thenReturn(organization);

        when(healthCareFacilityBoService.getById(1L))
                .thenReturn(healthCareFacility);

        HealthCareFacility hcf = orgService.changeOrganizationRoleStatus(
                HealthCareFacility.class, 1l, EntityStatus.ACTIVE);

        Assert.assertEquals(EntityStatus.ACTIVE, hcf.getStatus());
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-ResearchOrganization
     */
    @Test
    public void testChangeROOrgRoleStatus() {

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        gov.nih.nci.po.data.bo.ResearchOrganizationType type = new gov.nih.nci.po.data.bo.ResearchOrganizationType(
                ResearchOrganizationType.NCTN.value(), ""
        );
        gov.nih.nci.po.data.bo.ResearchOrganization researchOrganization = ModelUtils.getBasicResearchOrganization(type);
        researchOrganization.setStatus(RoleStatus.PENDING);
        researchOrganization.setPlayer(organization);

        when(organizationBoService.getById(2L)).thenReturn(organization);

        when(researchOrganizationBoService.getById(1L))
                .thenReturn(researchOrganization);


        ResearchOrganization ro = orgService.changeOrganizationRoleStatus(
                ResearchOrganization.class, 1l, EntityStatus.ACTIVE);
        assertNotNull(ro);
        Assert.assertEquals(EntityStatus.ACTIVE, ro.getStatus());
        Assert.assertTrue(ro instanceof ResearchOrganization);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-OversightCommittee
     */
    @Test
    public void testChangeOCOrgRoleStatus() {

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        gov.nih.nci.po.data.bo.OversightCommitteeType type = new gov.nih.nci.po.data.bo.OversightCommitteeType(
                OversightCommitteeType.ETHICS_COMMITTEE.value()
        );
        gov.nih.nci.po.data.bo.OversightCommittee oversightCommittee = ModelUtils.getBasicOversightCommittee(type);
        oversightCommittee.setStatus(RoleStatus.PENDING);
        oversightCommittee.setPlayer(organization);

        when(organizationBoService.getById(2L)).thenReturn(organization);

        when(oversightCommitteeBoService.getById(1L))
                .thenReturn(oversightCommittee);

        OversightCommittee oc = orgService.changeOrganizationRoleStatus(
                OversightCommittee.class, 1l, EntityStatus.ACTIVE);
        assertNotNull(oc);
        Assert.assertEquals(EntityStatus.ACTIVE, oc.getStatus());
        Assert.assertTrue(oc instanceof OversightCommittee);
    }

    /**
     * Testcase for OrganizationService-changeOrganizationRoleStatus-Exception
     * Scenario
     */
    @Test(expected = ServiceException.class)
    public void testChangeOrgRoleStatusExceptionScenario() throws JMSException {
        HealthCareFacility hcf = getHealthCareFacility();
        hcf.setId(1l);

        gov.nih.nci.po.data.bo.HealthCareFacility instance = ModelUtils.getBasicHealthCareFacility();
        when(healthCareFacilityBoService.getById(1L)).thenReturn(instance);

        HealthCareFacilityServiceLocal healthCareFacilityServiceLocal = healthCareFacilityBoService;
        doThrow(
                new ServiceException(
                        "Exception Occured while updating Organization Role."))
                .when(healthCareFacilityServiceLocal).curate(
                isA(gov.nih.nci.po.data.bo.HealthCareFacility.class),
                isA(String.class));

        orgService.changeOrganizationRoleStatus(HealthCareFacility.class, 1l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationRoleStatus-HealthCareFacility not
     * found in the DB
     */
    @Test(expected = ServiceException.class)
    public void testChangeHCFOrgRoleStatusOrgRoleNotExist() {
        
        orgService.changeOrganizationRoleStatus(HealthCareFacility.class,
                543210l, EntityStatus.ACTIVE);
    }




    private HealthCareFacility getHealthCareFacility() {
        HealthCareFacility hcf = new HealthCareFacility();
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
        ro.setName("Mayo RO");
        ro.setOrganizationId(1l);
        ro.setType(ResearchOrganizationType.NCP);
        ro.setStatus(EntityStatus.ACTIVE);
        ro.getAddress().add(getJaxbAddressList().get(0));
        ro.getContact().addAll(getJaxbContactList());
        return ro;
    }

    protected List<gov.nih.nci.po.webservices.types.Address> getJaxbAddressList() {
        List<gov.nih.nci.po.webservices.types.Address> addressList = new ArrayList<Address>();
        gov.nih.nci.po.webservices.types.Address address1 = new Address();
        address1.setLine1("13621 Leagcy Circle");
        address1.setLine2("Apt G");
        address1.setCity("Herndon");
        address1.setStateOrProvince("VA");
        address1.setCountry(CountryISO31661Alpha3Code.USA);
        address1.setPostalcode("20171");

        gov.nih.nci.po.webservices.types.Address address2 = new Address();
        address2.setLine1("200 1st St");
        address2.setLine2("SW # W4");
        address2.setCity("Rochester");
        address2.setStateOrProvince("MN");
        address2.setCountry(CountryISO31661Alpha3Code.USA);
        address2.setPostalcode("55901");

        addressList.add(address1);
        addressList.add(address2);

        return addressList;
    }

    protected List<gov.nih.nci.po.webservices.types.Contact> getJaxbContactList() {
        List<gov.nih.nci.po.webservices.types.Contact> contactList = new ArrayList<Contact>();

        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue("my.email@mayoclinic.org");

        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("571-456-1245");

        Contact faxContact = new Contact();
        faxContact.setType(ContactType.FAX);
        faxContact.setValue("571-456-1245");

        Contact ttyContact = new Contact();
        ttyContact.setType(ContactType.TTY);
        ttyContact.setValue("571-123-1123");

        Contact urlContact = new Contact();
        urlContact.setType(ContactType.URL);
        urlContact.setValue("http://www.mayoclinic.org");

        contactList.add(emailContact);
        contactList.add(phoneContact);
        contactList.add(faxContact);
        contactList.add(ttyContact);
        contactList.add(urlContact);
        return contactList;
    }

    /**
     * This method is used to populate different Contact in BO Object.
     *
     */
    protected void populateBOContacts(Contactable contactableBo) {
        contactableBo.getEmail().add(new Email("my.test@nci.gov"));
        contactableBo.getPhone().add(new PhoneNumber("571-563-0987"));
        contactableBo.getFax().add(new PhoneNumber("571-576-0912"));
        contactableBo.getTty().add(new PhoneNumber("571-123-4567"));
        contactableBo.getUrl().add(new URL("http://nih.gov"));
    }

    protected XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            System.err.println(ex);
        }
        return xmlCalendar;
    }

    private List<OrganizationSearchDTO> getOrgSearchDtoList() {
        List<OrganizationSearchDTO> orgSearchDtoList = new ArrayList<OrganizationSearchDTO>();
        OrganizationSearchDTO dto = new OrganizationSearchDTO();
        dto.setId(1l);
        dto.setName("Cancer Therapy Evaluation Program");
        dto.setStatusCode("ACTIVE");
        dto.setStatusDate(new Date());
        orgSearchDtoList.add(dto);
        return orgSearchDtoList;
    }

    private List<FamilyOrganizationRelationship> getFamilyOrganizationRelationshipList() {
        List<FamilyOrganizationRelationship> forList = new ArrayList<FamilyOrganizationRelationship>();

        gov.nih.nci.po.data.bo.Organization organization = new gov.nih.nci.po.data.bo.Organization();
        organization.setId(123l);
        organization.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.ACTIVE);

        FamilyOrganizationRelationship for0 = new FamilyOrganizationRelationship();
        for0.setStartDate(new Date());
        for0.setFunctionalType(FamilyFunctionalType.AFFILIATION);
        for0.setId(12345l);
        for0.setOrganization(organization);

        FamilyOrganizationRelationship for1 = new FamilyOrganizationRelationship();
        for1.setStartDate(new Date());
        for1.setFunctionalType(FamilyFunctionalType.CONTRACTUAL);
        for1.setId(123456l);
        for1.setOrganization(organization);

        forList.add(for0);
        forList.add(for1);
        return forList;
    }



}
