package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.AbstractIdentifiedEntity;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractRoleBoServiceTest<TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>> extends AbstractEndpointTest{

    protected AbstractRoleBoService<TYPE, CR_TYPE> service;

    @Before
    public void setup() throws CSException {
        setupServiceLocator();
        initServiceUnderTest();
        mockSecurity();

        when(serviceLocator.getCountryService().getCountryByAlpha3("USA"))
                .thenReturn(ModelUtils.getDefaultCountry());
    }



    protected abstract void initServiceUnderTest();
    protected abstract TYPE getBasicModel();
    protected abstract GenericStructrualRoleServiceLocal<TYPE> getEjbService();
    protected abstract GenericStructrualRoleCRServiceLocal<CR_TYPE> getCrService();

    //create
    @Test
    public void testCreate() throws JMSException, EntityValidationException {
        TYPE instance = getBasicModel();
        instance.setStatus(RoleStatus.PENDING);
        when(getEjbService().create(instance)).thenReturn(1L);

        long id = service.create(instance);

        verify(getEjbService()).create((TYPE) any());
        assertEquals(1L, id);
        assertEquals(UsernameHolder.getUser(), getCreatedBy(instance).getLoginName());
    }




    //create with entity validation exception
    @Test(expected = EntityValidationException.class)
    public void testCreateWithEntityValidationException() throws JMSException, EntityValidationException {
        TYPE instance = getBasicModel();
        instance.setStatus(RoleStatus.PENDING);
        when(getEjbService().create(instance)).thenThrow(new EntityValidationException(Collections.EMPTY_MAP));

        long id = service.create(instance);

    }

    //create with jms exception
    @Test(expected = JMSException.class)
    public void testCreateWithJMSException() throws JMSException, EntityValidationException {
        TYPE type = getBasicModel();
        type.setStatus(RoleStatus.PENDING);
        when(getEjbService().create(type)).thenThrow(new JMSException(""));

        service.create(type);
    }

    //getById
    @Test
    public void testGetById() {
        when(getEjbService().getById(1L)).thenReturn(getBasicModel());

        TYPE retrieved = service.getById(1L);

        assertNotNull(retrieved);
        verify(getEjbService()).getById(1l);
    }


    //getByIds
    @Test
    public void testGetByIds() {
        Long[] ids = new Long[]{1L,2L};

        when(getEjbService().getByIds(ids))
                .thenAnswer(new Answer<List<TYPE>>() {
                    @Override
                    public List<TYPE> answer(InvocationOnMock invocation) throws Throwable {
                        List<TYPE> results = new ArrayList<TYPE>();

                        for (Long id : (Long[])invocation.getArguments()[0]) {
                            TYPE item = getBasicModel();
                            results.add(item);
                        }

                        return results;
                    }
                });

        List<TYPE> items = getEjbService().getByIds(ids);

        assertEquals(2, items.size());
        verify(getEjbService()).getByIds(ids);
    }

    //getByPlayerIds
    @Test
    public void testGetByPlayerIds() {
        Long[] ids = new Long[]{1L,2L};

        when(getEjbService().getByPlayerIds(ids))
                .thenAnswer(new Answer<List<TYPE>>() {
                    @Override
                    public List<TYPE> answer(InvocationOnMock invocation) throws Throwable {
                        List<TYPE> results = new ArrayList<TYPE>();

                        for (Long id : (Long[])invocation.getArguments()[0]) {
                            TYPE item = getBasicModel();
                            results.add(item);
                        }

                        return results;
                    }
                });

        List<TYPE> items = getEjbService().getByPlayerIds(ids);

        assertEquals(2, items.size());
        verify(getEjbService()).getByPlayerIds(ids);
    }

    //curate
    @Test
    public void testCurateOwn() throws JMSException, EntityValidationException {
        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(me);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        when(getEjbService().getById(1L))
            .thenReturn(currentInstance);


        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                .thenReturn(organization);
        }

        service.curate(updatedInstance);

        verify(getEjbService()).curate(updatedInstance);
        verify(getCrService(), never()).create((CR_TYPE)any());

        assertEquals(((AbstractRole) updatedInstance).getCreatedBy(), me);
    }




    @Test
    public void testCurateSomeoneElses() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(otherUser);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        when(getEjbService().getById(1L))
                .thenReturn(currentInstance);

        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);

        if (currentInstance instanceof PersonRole) {
            verify(getEjbService()).curate(updatedInstance);
            verify(getCrService(), never()).create((CR_TYPE) notNull());
        } else {
            verify(getEjbService(), never()).curate(updatedInstance);
            verify(getCrService()).create((CR_TYPE) notNull());
        }

        assertEquals(((AbstractRole)updatedInstance).getCreatedBy().getLoginName(), otherUser.getLoginName());
    }

    @Test(expected = RuntimeException.class)
    public void testCurateSomeoneElsesWithEntityValidationException() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(otherUser);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        //Hack to work around PersonRole exclusion
        if (currentInstance instanceof PersonRole) {
            throw new RuntimeException();
        }

        when(getEjbService().getById(1L))
                .thenReturn(currentInstance);

        when(getCrService().create((CR_TYPE) any()))
                .thenThrow(new EntityValidationException(Collections.EMPTY_MAP));

        service.curate(updatedInstance);
    }

    @Test
    public void testCurateWithNullCreatedBy() throws JMSException, EntityValidationException {
        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(null);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        when(getEjbService().getById(1L))
                .thenReturn(currentInstance);

        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);

        if (currentInstance instanceof PersonRole) {
            verify(getEjbService()).curate(updatedInstance);
            verify(getCrService(), never()).create((CR_TYPE) notNull());
        } else {
            verify(getEjbService(), never()).curate(updatedInstance);
            verify(getCrService()).create((CR_TYPE) notNull());
        }
        assertEquals(((AbstractRole)updatedInstance).getCreatedBy(), null);
    }



    //curate with JMSException
    @Test(expected = JMSException.class)
    public void testCurateWithException() throws JMSException {
        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(me);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        GenericStructrualRoleServiceLocal<TYPE> ejbService = getEjbService();

        doReturn(currentInstance).when(ejbService).getById(1L);

        doThrow(new JMSException(""))
                .when(ejbService).curate(updatedInstance);

        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);
    }


    //curate with JMSException
    @Test(expected = RuntimeException.class)
    public void testCurateSomeoneElsesWithException() throws JMSException, EntityValidationException {
        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(null);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        //this is a hack to exclude the test from being applied to PersonRoles
        if (currentInstance instanceof PersonRole) {
            throw new RuntimeException();
        }

        GenericStructrualRoleServiceLocal<TYPE> ejbService = getEjbService();

        doReturn(currentInstance).when(ejbService).getById(1L);

        when(getCrService().create((CR_TYPE) any())).thenThrow(new EntityValidationException(Collections.EMPTY_MAP));

        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);
    }

    @Test
    public void testCurateWithNoChanges() throws JMSException, EntityValidationException {
        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(me);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(me);

        when(getEjbService().getById(1L))
                .thenReturn(currentInstance);

        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);

        verify(getEjbService(), never()).curate(updatedInstance);
        verify(getCrService(), never()).create((CR_TYPE) notNull());
    }

    //validate
    @Test
    public void testValidate() {

        Map<String, String[]> errors = new HashMap<String, String[]>();
        errors.put("foo", new String[]{"bar"});

        when(getEjbService().validate((TYPE) any())).thenReturn(errors);

        Map<String, String[]> result = service.validate(getBasicModel());

        assertEquals(errors, result);
    }

    //search - SearchCriteria
    @Test
    public void testSearch() {
        SearchCriteria<TYPE> searchCriteria = mock(SearchCriteria.class);
        service.search(searchCriteria);

        verify(getEjbService()).search(searchCriteria);
    }

    //search - SearchCriteria, PageSortParams
    @Test
    public void testSearchWithPageSortParams() {
        SearchCriteria<TYPE> searchCriteria = mock(SearchCriteria.class);
        PageSortParams pageSortParams = mock(PageSortParams.class);
        service.search(searchCriteria, pageSortParams);

        verify(getEjbService()).search(searchCriteria, pageSortParams);
    }

    //count - SearchCriteria
    @Test
    public void testCount() {
        SearchCriteria<TYPE> searchCriteria = mock(SearchCriteria.class);

        when(getEjbService().count(searchCriteria)).thenReturn(5);

        int hits = service.count(searchCriteria);

        assertEquals(5, hits);
    }


    private User getCreatedBy(TYPE instance) {
        return ((AbstractRole) instance).getCreatedBy();
    }



}
