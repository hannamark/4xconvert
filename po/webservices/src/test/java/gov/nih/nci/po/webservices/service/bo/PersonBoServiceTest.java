package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PersonSearchCriteria;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;
import gov.nih.nci.security.exceptions.CSException;
import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */

public class PersonBoServiceTest extends AbstractEndpointTest{


    private PersonBoService service;

    @Before
    public void setup() throws CSException {
        setupServiceLocator();

        service = new PersonBoService();

        mockSecurity();
    }

    @After
    public void teardown() {

    }

    @Test
    public void testCreate() throws JMSException, EntityValidationException {
        Person person = ModelUtils.getBasicPerson();

        when(serviceLocator.getPersonService().create(any(Person.class))).thenReturn(1L);

        long id = service.create(person);

        assertEquals(person.getCreatedBy().getLoginName(), UsernameHolder.getUser());
        assertEquals(1L, id);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateWithSecurityException() throws JMSException, EntityValidationException {
        Person person = ModelUtils.getBasicPerson();

        when(userProvisioningManager.getUser(anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new CSException();
            }
        });

        service.create(person);
    }

    @Test
    public void testCreateWithCtepId() throws JMSException, EntityValidationException {
        Person person = ModelUtils.getBasicPerson();

        when(serviceLocator.getPersonService().create(any(Person.class), anyString())).thenReturn(1L);

        long id = service.create(person, "12345");

        assertEquals(person.getCreatedBy().getLoginName(), UsernameHolder.getUser());
        assertEquals(1L, id);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateWithCtepIdWithSecurityException() throws JMSException, EntityValidationException {
        Person person = ModelUtils.getBasicPerson();

        when(userProvisioningManager.getUser(anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new CSException();
            }
        });

        service.create(person, "12345");
    }

    //test update own
    @Test
    public void testUpdateOwn() throws JMSException, EntityValidationException {

        Person currentPerson = ModelUtils.getBasicPerson();
        currentPerson.setId(1L);
        currentPerson.setCreatedBy(me);

        Person updatedPerson = ModelUtils.getBasicPerson();
        updatedPerson.setId(1L);
        updatedPerson.setLastName("JustMarried");

        //mock call to getById
        when(serviceLocator.getPersonService().getById(1L)).thenReturn(currentPerson);

        service.curate(updatedPerson);

        //verify person updated directly
        verify(serviceLocator.getPersonService()).curate(any(Person.class));
        verify(serviceLocator.getPersonCRService(), never()).create(any(PersonCR.class));


    }

    @Test(expected = ObjectNotFoundException.class)
    public void testUpdateNonexistingInstance() throws JMSException, EntityValidationException {


        Person updatedPerson = ModelUtils.getBasicPerson();
        updatedPerson.setId(1L);
        updatedPerson.setLastName("JustMarried");

        //mock call to getById
        PersonServiceLocal personServiceLocal = serviceLocator.getPersonService();
        when(personServiceLocal.getById(1L)).thenReturn(null);

        doThrow(new ObjectNotFoundException(updatedPerson, "ObjectNotFoundException")).when(personServiceLocal).curate(any(Person.class));

        service.curate(updatedPerson);

    }



    @Test
    public void testUpdateOwnWithBlankCtepId() throws EntityValidationException, JMSException {
        Person currentPerson = ModelUtils.getBasicPerson();
        currentPerson.setId(1L);
        currentPerson.setCreatedBy(me);

        gov.nih.nci.po.data.bo.IdentifiedPerson currentIdentifiedPerson = new gov.nih.nci.po.data.bo.IdentifiedPerson();
        currentIdentifiedPerson.setPlayer(currentPerson);
        currentIdentifiedPerson.setAssignedIdentifier(new Ii());

        Person updatedPerson = ModelUtils.getBasicPerson();
        updatedPerson.setId(1L);
        updatedPerson.setLastName("JustMarried");
        //mock call to getById
        when(serviceLocator.getPersonService().getById(1L)).thenReturn(currentPerson);


        service.curate(updatedPerson, "");

        //verify person updated via a CR
        verify(serviceLocator.getPersonService()).curate(any(Person.class), anyString());
        verify(serviceLocator.getPersonCRService(), never()).create(any(PersonCR.class));

        //verify identified person CR created
        verify(serviceLocator.getIdentifiedPersonService(), never()).curate(any(gov.nih.nci.po.data.bo.IdentifiedPerson.class));
        verify(serviceLocator.getIdentifiedPersonCRService(), never()).create(any(IdentifiedPersonCR.class));
    }



    @Test
    public void testUpdateOwnWithCtepId() throws JMSException, EntityValidationException {

        Person currentPerson = ModelUtils.getBasicPerson();
        currentPerson.setId(1L);
        currentPerson.setCreatedBy(me);

        gov.nih.nci.po.data.bo.IdentifiedPerson currentIdentifiedPerson = new gov.nih.nci.po.data.bo.IdentifiedPerson();
        currentIdentifiedPerson.setPlayer(currentPerson);
        currentIdentifiedPerson.setAssignedIdentifier(new Ii());

        List<gov.nih.nci.po.data.bo.IdentifiedPerson> identifiedPersons = new ArrayList<gov.nih.nci.po.data.bo.IdentifiedPerson>();
        identifiedPersons.add(currentIdentifiedPerson);

        Person updatedPerson = ModelUtils.getBasicPerson();
        updatedPerson.setId(1L);
        updatedPerson.setLastName("JustMarried");
        //mock call to getById
        when(serviceLocator.getPersonService().getById(1L)).thenReturn(currentPerson);
        when(serviceLocator.getIdentifiedPersonService().search(any(SearchCriteria.class))).thenReturn(identifiedPersons);


        service.curate(updatedPerson, "newCtepId");

        //verify person updated via a CR
        verify(serviceLocator.getPersonService()).curate(any(Person.class), eq("newCtepId"));
        verify(serviceLocator.getPersonCRService(), never()).create(any(PersonCR.class));

    }



    @Test(expected = ObjectNotFoundException.class)
    public void testUpdateNonexistingWithCtepId() throws JMSException, EntityValidationException {


        Person updatedPerson = ModelUtils.getBasicPerson();
        updatedPerson.setId(1L);
        updatedPerson.setLastName("JustMarried");
        //mock call to getById
        PersonServiceLocal personServiceLocal = serviceLocator.getPersonService();
        when(personServiceLocal.getById(1L)).thenReturn(null);

        doThrow(new ObjectNotFoundException(updatedPerson, "ObjectNotFoundException"))
                .when(personServiceLocal)
                .curate(any(Person.class), anyString());

        service.curate(updatedPerson, "12345");

    }

    //getbyid
    @Test
    public void testGetById() {
        Person person = ModelUtils.getBasicPerson();
        person.setId(1L);

        when(serviceLocator.getPersonService().getById(1L))
                .thenReturn(person);

        Person retrieved = service.getById(1L);

        assertEquals(person, retrieved);
    }

    //validate
    @Test
    public void testValidate() {
        Person person = ModelUtils.getBasicPerson();

        Map<String, String[]> errorMap = new HashMap<String, String[]>();
        errorMap.put("foo", new String[]{"bar", "baz"});

        when(serviceLocator.getPersonService().validate(any(Person.class)))
                .thenReturn(errorMap);


        Map<String, String[]> retrieved = service.validate(person);

        assertEquals(errorMap, retrieved);
    }



    //search - PersonSearchCriteria and sort params
    @Test
    public void testSearch() {
        PersonSearchCriteria searchCriteria = new PersonSearchCriteria();
        PageSortParams<PersonSearchDTO> pageSortParams = mock(PageSortParams.class);

        service.search(searchCriteria, pageSortParams);

        verify(serviceLocator.getPersonService()).search(searchCriteria, pageSortParams);
    }

    //search - SearchCriteria
    @Test
    public void testSearchWithCriteria() {
        SearchCriteria<Person> searchCriteria = mock(SearchCriteria.class);

        service.search(searchCriteria);

        verify(serviceLocator.getPersonService()).search(searchCriteria);
    }

    //search - SearchCriteria, PageSortParams
    @Test
    public void testSearchWithCriteriaAndSortParams() {
        SearchCriteria<Person> searchCriteria = mock(SearchCriteria.class);
        PageSortParams<Person> sortParams = mock(PageSortParams.class);
        service.search(searchCriteria, sortParams);

        verify(serviceLocator.getPersonService()).search(searchCriteria, sortParams);
    }



    //getInboxPersons
    @Test
    public void testGetInboxPersons() {
        when(serviceLocator.getPersonService().getInboxPersons(any(PageSortParams.class)))
                .thenAnswer(new Answer<List<PersonSearchDTO>>() {
                    @Override
                    public List<PersonSearchDTO> answer(InvocationOnMock invocation) throws Throwable {
                        List<PersonSearchDTO> result = new ArrayList<PersonSearchDTO>();
                        result.add(new PersonSearchDTO());
                        result.add(new PersonSearchDTO());
                        result.add(new PersonSearchDTO());
                        return result;
                    }
                });

        PageSortParams<PersonSearchDTO> pageSortParams = mock(PageSortParams.class);

        List<PersonSearchDTO> retrieved = service.getInboxPersons(pageSortParams);

        assertEquals(3, retrieved.size());

    }

    //countInboxPersons
    @Test
    public void testCountInboxPersons() {
        when(serviceLocator.getPersonService().countInboxPersons())
                .thenReturn(3);

        int retrived = service.countInboxPersons();

        assertEquals(3, retrived);
    }

    //count - PageSortParams
    @Test
    public void testCountWithSearchCriteria() {
        when(serviceLocator.getPersonService().count(any(SearchCriteria.class)))
                .thenReturn(3);

        SearchCriteria<Person> searchCriteria = mock(SearchCriteria.class);
        int retrieved = service.count(searchCriteria);

        assertEquals(3, retrieved);
    }

    //count - PersonSearchCriteria
    @Test
    public void testCountWithPersonSearchCriteria() {
        when(serviceLocator.getPersonService().count(any(PersonSearchCriteria.class)))
                .thenReturn(3);

        PersonSearchCriteria searchCriteria = new PersonSearchCriteria();
        int retrieved = service.count(searchCriteria);

        assertEquals(3, retrieved);
    }
}
