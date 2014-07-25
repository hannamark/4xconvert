package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OrganizationBoServiceTest extends AbstractEndpointTest{

    private OrganizationBoService service;

    @Before
    public void setup() throws CSException {
        setupServiceLocator();

        when(serviceLocator.getCountryService().getCountryByAlpha3("USA"))
                .thenReturn(ModelUtils.getDefaultCountry());

        service = new OrganizationBoService();

        mockSecurity();
    }


    @Test
    public void testCreate() throws JMSException, EntityValidationException {
        Organization organization = ModelUtils.getBasicOrganization();

        when(serviceLocator.getOrganizationService().create(any(Organization.class))).thenReturn(1L);

        long id = service.create(organization);

        assertEquals(organization.getCreatedBy().getLoginName(), UsernameHolder.getUser());
        assertEquals(1L, id);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateWithSecurityException() throws JMSException, EntityValidationException {
        Organization organization = ModelUtils.getBasicOrganization();

        when(userProvisioningManager.getUser(anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new CSException();
            }
        });

        service.create(organization);
    }


    //test update own
    @Test
    public void testUpdateOwn() throws JMSException, EntityValidationException {

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(me);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);

        service.curate(updatedOrganization);

        //verify organization updated directly
        verify(serviceLocator.getOrganizationService()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));


    }

    @Test
    public void testUpdateOwnWithNoChanges() throws JMSException, EntityValidationException {

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(me);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);


        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);
        when(serviceLocator.getCountryService().getCountryByAlpha3("USA")).thenReturn(ModelUtils.getDefaultCountry());
        service.curate(updatedOrganization);

        //verify no updates made
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));


    }

    //test update another organization's
    @Test
    public void testUpdateSomeoneElses() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(otherUser);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);

        service.curate(updatedOrganization);

        //verify organization updated via a CR
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService()).create(any(OrganizationCR.class));

    }

    @Test
    public void testUpdateSomeoneElsesWithNoChanges() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(otherUser);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);

        service.curate(updatedOrganization);

        //verify organization updated via a CR
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));

    }

    @Test
    public void testUpdateWithoutCreatedBy() throws JMSException, EntityValidationException {
        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(null);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);

        service.curate(updatedOrganization);

        //verify organization updated via a CR
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService()).create(any(OrganizationCR.class));

    }


    @Test(expected = RuntimeException.class)
    public void testUpdateSomeoneElsesWithEntityValidationException() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(otherUser);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);
        when(serviceLocator.getOrganizationCRService().create(any(OrganizationCR.class)))
            .thenThrow(new EntityValidationException(Collections.EMPTY_MAP));
        service.curate(updatedOrganization);

    }


    //getbyid
    @Test
    public void testGetById() {
        Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(1L);

        when(serviceLocator.getOrganizationService().getById(1L))
            .thenReturn(organization);

        Organization retrieved = service.getById(1L);

        assertEquals(organization, retrieved);
    }

    //validate
    @Test
    public void testValidate() {
        Organization organization = ModelUtils.getBasicOrganization();

        Map<String, String[]> errorMap = new HashMap<String, String[]>();
        errorMap.put("foo", new String[]{"bar", "baz"});

        when(serviceLocator.getOrganizationService().validate(any(Organization.class)))
            .thenReturn(errorMap);


        Map<String, String[]> retrieved = service.validate(organization);

        assertEquals(errorMap, retrieved);
    }

    //getassociatedPlayedRoles
    @Test
    public void testGetAssociatedPlayedRoles() {
        Organization organization = ModelUtils.getBasicOrganization();
        service.getAssociatedPlayedRoles(organization);

        verify(serviceLocator.getOrganizationService()).getAssociatedPlayedRoles(organization);
    }

    //getAssociatedscopedroles
    @Test
    public void testGetScopedPlayedRoles() {
        Organization organization = ModelUtils.getBasicOrganization();
        service.getAssociatedScopedRoles(organization);

        verify(serviceLocator.getOrganizationService()).getAssociatedScopedRoles(organization);
    }
    //search - OrganizationSearchCriteria
    @Test
    public void testSearch() {
        OrganizationSearchCriteria searchCriteria = new OrganizationSearchCriteria();
        PageSortParams<OrganizationSearchDTO> pageSortParams = mock(PageSortParams.class);

        service.search(searchCriteria, pageSortParams);

        verify(serviceLocator.getOrganizationService()).search(searchCriteria, pageSortParams);
    }

    //search - searchCriteria
    @Test
    public void testSearchWithCriteria() {
        SearchCriteria<Organization> searchCriteria = mock(SearchCriteria.class);

        service.search(searchCriteria);

        verify(serviceLocator.getOrganizationService()).search(searchCriteria);
    }

    //search - searchCriteria, PageSortParams
    @Test
    public void testSearchWithCriteriaAndSortParams() {
        SearchCriteria<Organization> searchCriteria = mock(SearchCriteria.class);
        PageSortParams<Organization> sortParams = mock(PageSortParams.class);
        service.search(searchCriteria, sortParams);

        verify(serviceLocator.getOrganizationService()).search(searchCriteria, sortParams);
    }

    //getInboxOrgs
    @Test
    public void testGetInboxOrgs() {
        when(serviceLocator.getOrganizationService().getInboxOrgs(any(PageSortParams.class)))
                .thenAnswer( new Answer<List<OrganizationSearchDTO>>() {
                    @Override
                    public List<OrganizationSearchDTO> answer(InvocationOnMock invocation) throws Throwable {
                        List<OrganizationSearchDTO> result = new ArrayList<OrganizationSearchDTO>();
                        result.add(new OrganizationSearchDTO());
                        result.add(new OrganizationSearchDTO());
                        result.add(new OrganizationSearchDTO());
                        return result;
                    }
                });

        PageSortParams<OrganizationSearchDTO> pageSortParams = mock(PageSortParams.class);

        List<OrganizationSearchDTO> retrieved = service.getInboxOrgs(pageSortParams);

        assertEquals(3, retrieved.size());

    }

    //countInboxOrgs
    @Test
    public void testCountInboxOrgs() {
        when(serviceLocator.getOrganizationService().countInboxOrgs())
                .thenReturn(3L);

        long retrived = service.countInboxOrgs();

        assertEquals(3L, retrived);
    }

    //removeChangeRequest
    @Test
    public void testRemoveChangeRequest() {
        OrganizationCR cr = new OrganizationCR();

        service.removeChangeRequest(cr);

        verify(serviceLocator.getOrganizationService()).removeChangeRequest(cr);
    }

    //count -SearchCriteria
    @Test
    public void testCountWithSearchCriteria() {
        when(serviceLocator.getOrganizationService().count(any(SearchCriteria.class)))
                .thenReturn(3);

        SearchCriteria<Organization> searchCriteria = mock(SearchCriteria.class);
        int retrieved = service.count(searchCriteria);

        assertEquals(3, retrieved);
    }

    //count - OrganizationSearchCriteria
    @Test
    public void testCountWithOrganizationSearchCriteria() {
        when(serviceLocator.getOrganizationService().count(any(OrganizationSearchCriteria.class)))
                .thenReturn(3L);

        OrganizationSearchCriteria searchCriteria = new OrganizationSearchCriteria();
        long retrieved = service.count(searchCriteria);

        assertEquals(3L, retrieved);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testUpdateNonexistingInstance() throws JMSException, EntityValidationException {


        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        OrganizationServiceLocal organizationServiceLocal = serviceLocator.getOrganizationService();
        when(organizationServiceLocal.getById(1L)).thenReturn(null);

        doThrow(new ObjectNotFoundException(updatedOrganization, "ObjectNotFoundException"))
                .when(organizationServiceLocal).curate(any(Organization.class));

        service.curate(updatedOrganization);

    }

    @Test(expected = ServiceException.class)
    public void testUpdateWithErrors() throws JMSException {


        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        OrganizationServiceLocal organizationServiceLocal = serviceLocator.getOrganizationService();
        when(organizationServiceLocal.getById(1L)).thenReturn(null);

        Map<String, String[]> errorMap = new HashMap<String, String[]>();
        errorMap.put("foo", new String[]{"bar", "baz"});

        when(organizationServiceLocal.validate(any(Organization.class))).thenReturn(errorMap);

        service.curate(updatedOrganization);

    }


    @Test
    public void testAlias() throws JMSException, EntityValidationException {
        gov.nih.nci.po.data.bo.Organization currentInstance = ModelUtils.getBasicOrganization();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(me);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        gov.nih.nci.po.data.bo.Organization updatedInstance = ModelUtils.getBasicOrganization();
        updatedInstance.setId(2L);
        updatedInstance.setName("delta");

        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(currentInstance);

        service.curate(updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(2, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        assertEquals("delta", updatedInstance.getAlias().get(1).getValue());
        verify(serviceLocator.getOrganizationService()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));

    }

    @Test
    public void testUpdateSomeoneElsesAlias() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        gov.nih.nci.po.data.bo.Organization currentInstance = ModelUtils.getBasicOrganization();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(otherUser);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        gov.nih.nci.po.data.bo.Organization updatedInstance = ModelUtils.getBasicOrganization();
        updatedInstance.setId(2L);
        updatedInstance.setName("delta");

        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(currentInstance);

        service.curate(updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(2, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        assertEquals("delta", updatedInstance.getAlias().get(1).getValue());
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService()).create(any(OrganizationCR.class));

    }

    @Test
    public void testDuplicateAlias() throws JMSException, EntityValidationException {
        gov.nih.nci.po.data.bo.Organization currentInstance = ModelUtils.getBasicOrganization();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(me);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        gov.nih.nci.po.data.bo.Organization updatedInstance = ModelUtils.getBasicOrganization();
        updatedInstance.setId(2L);
        updatedInstance.setName("alpha");

        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(currentInstance);

        service.curate(updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(1, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
//        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
//        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));
    }

    @Test
    public void testDuplicateAliasOnSomeoneElsesOrganization() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        gov.nih.nci.po.data.bo.Organization currentInstance = ModelUtils.getBasicOrganization();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(otherUser);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        gov.nih.nci.po.data.bo.Organization updatedInstance = ModelUtils.getBasicOrganization();
        updatedInstance.setId(2L);
        updatedInstance.setName("alpha");

        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(currentInstance);

        service.curate(updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(1, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
//        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));
    }

    @Test
    public void testUpdateOwnOverridden() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setCreatedBy(me);
        currentOrganization.setOverriddenBy(otherUser);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);
        updatedOrganization.setName("Just Reorganized");

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);

        service.curate(updatedOrganization);

        //verify organization updated directly
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService()).create(any(OrganizationCR.class));


    }

    @Test
    public void testUpdateOwnOverriddenWithNoChanges() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        Organization currentOrganization = ModelUtils.getBasicOrganization();
        currentOrganization.setId(1L);
        currentOrganization.setOverriddenBy(otherUser);
        currentOrganization.setCreatedBy(me);

        Organization updatedOrganization = ModelUtils.getBasicOrganization();
        updatedOrganization.setId(1L);

        //mock call to getById
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(currentOrganization);

        service.curate(updatedOrganization);

        //verify organization updated directly
        verify(serviceLocator.getOrganizationService(), never()).curate(any(Organization.class));
        verify(serviceLocator.getOrganizationCRService(), never()).create(any(OrganizationCR.class));



    }
}
