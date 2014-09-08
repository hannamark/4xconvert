package gov.nih.nci.po.webservices.service.simple;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bo.ClinicalResearchStaffBoService;
import gov.nih.nci.po.webservices.service.bo.HealthCareProviderBoService;
import gov.nih.nci.po.webservices.service.bo.IdentifiedPersonBoService;
import gov.nih.nci.po.webservices.service.bo.OrganizationalContactBoService;
import gov.nih.nci.po.webservices.service.bo.PersonBoService;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareProvider;
import gov.nih.nci.po.webservices.types.OrganizationalContact;
import gov.nih.nci.po.webservices.types.OrganizationalContactType;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * This is the test class for PersonServiceImpl.
 * 
 * @author Rohit Gupta
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PoServiceUtil.class)
public class PersonServiceTest extends AbstractEndpointTest {

    private gov.nih.nci.po.webservices.types.Person person;

    private gov.nih.nci.po.webservices.types.PersonSearchCriteria psCriteria;

    private Organization ctep;
    private PersonServiceImpl perService;
    private ClinicalResearchStaffBoService clinicalResearchStaffBoService;
    private HealthCareProviderBoService healthCareProviderBoService;
    private IdentifiedPersonBoService identifiedPersonBoService;
    private OrganizationalContactBoService organizationalContactBoService;
    private PersonBoService personBoService;


    @Before
    public void setUp() {
        setupServiceLocator();
        when(serviceLocator.getCountryService().getCountryByAlpha3("USA")).thenReturn(ModelUtils.getDefaultCountry());

        initBoService();

        ctep = ModelUtils.getBasicOrganization();
        ctep.setId(1L);
        mockStatic(PoServiceUtil.class);
        PowerMockito.when(PoServiceUtil.getCtepOrganization()).thenReturn(ctep);

        // setting up gov.nih.nci.po.webservices.types.Person
        person = new Person();
        person.setPrefix("Mr.");
        person.setFirstName("John");
        person.setMiddleName("L");
        person.setLastName("Doe");
        person.setSuffix("Sr");
        person.setStatus(EntityStatus.PENDING);

        person.setAddress(getJaxbAddressList().get(0));        
        person.getContact().addAll(getJaxbContactList());

        psCriteria = new PersonSearchCriteria();
        psCriteria.setFirstName("Rohit");
        psCriteria.setOffset(0);
        psCriteria.setLimit(5);

        perService = new PersonServiceImpl();
        perService.setPersonBoService(personBoService);
        perService.setClinicalResearchStaffBoService(clinicalResearchStaffBoService);
        perService.setHealthCareProviderBoService(healthCareProviderBoService);
        perService.setIdentifiedPersonBoService(identifiedPersonBoService);
        perService.setOrganizationalContactBoService(organizationalContactBoService);
    }

    private void initBoService() {
        personBoService = mock(PersonBoService.class);
        clinicalResearchStaffBoService = mock(ClinicalResearchStaffBoService.class);
        healthCareProviderBoService = mock(HealthCareProviderBoService.class);
        identifiedPersonBoService = mock(IdentifiedPersonBoService.class);
        organizationalContactBoService = mock(OrganizationalContactBoService.class);

    }

    /**
     * Testcase for PersonService-createPerson
     */
    @Test
    public void testCreatePerson() throws JMSException, EntityValidationException {
        when(personBoService.create(any(gov.nih.nci.po.data.bo.Person.class))).thenReturn(1L);

        when(personBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.Person>() {
            @Override
            public gov.nih.nci.po.data.bo.Person answer(InvocationOnMock invocation) throws Throwable {
                gov.nih.nci.po.data.bo.Person result = new gov.nih.nci.po.data.bo.Person();
                result.setId(1L);
                result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.PENDING);
                return result;
            }
        });

        Person retPerson = perService.createPerson(person);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
    }

    /**
     * Testcase for PersonService-createPerson-Person is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullPerson() {
        
        perService.createPerson(null);
    }

    /**
     * Testcase for PersonService-createPerson-EntityValidationExceptionScenario
     * 
     * @throws JMSException
     * @throws EntityValidationException
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonEntityValidationExceptionScenario()
            throws EntityValidationException, JMSException {
        when(personBoService.create(isA(gov.nih.nci.po.data.bo.Person.class)))
                .thenThrow(
                        new EntityValidationException(
                                "EntityValidationException Occured while creating the person.",
                                null));

        
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        person.getContact().set(1, phoneContact); // set invalid phone number
        perService.createPerson(person);

    }

    /**
     * Testcase for PersonService-createPerson-Exception scenario
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonForExceptionScenario()
            throws EntityValidationException, JMSException {
        when(personBoService
                        .create(isA(gov.nih.nci.po.data.bo.Person.class)))
                .thenThrow(
                        new ServiceException("Exception Occured while creating the person.", null));
        perService.createPerson(person);
    }
    
    /**
     * Testcase for PersonService-createPerson-Exception scenario
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonHavingCTEPID()
            throws EntityValidationException, JMSException {
        person.setCtepId("VA202");
        perService.createPerson(person);
    }


    /**
     * Testcase for PersonService-updatePerson
     */
    @Test
    public void testUpdatePerson() throws JMSException, EntityValidationException {
        
        person.setId(1l);

        gov.nih.nci.po.data.bo.Person currentPersonInstance = ModelUtils.getBasicPerson();
        currentPersonInstance.setId(1L);

        when(personBoService.getById(1L)).thenReturn(currentPersonInstance);


        Person retPerson = perService.updatePerson(person);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
        verify(personBoService).curate(any(gov.nih.nci.po.data.bo.Person.class));
    }

    /**
     * Testcase for PersonService-updatePerson-Person is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullPerson() {
        
        perService.updatePerson(null);
    }

    /**
     * Testcase for PersonService-updatePerson-PersonId is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonForNullDBId() {
        
        person.setId(null);
        perService.updatePerson(person);
    }

    /**
     * Testcase for PersonService-updatePerson-Exception Scenario
     * 
     * @throws JMSException
     * @throws EntityValidationException 
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonForExceptionScenario() throws JMSException, EntityValidationException {

         doThrow(
                new ServiceException(
                        "Exception Occured while updating the Person.")).when(
                personBoService).curate(isA(gov.nih.nci.po.data.bo.Person.class));

        Person per = new Person();
        per.setId(5l);
        per.setStatus(EntityStatus.ACTIVE);
        
        perService.updatePerson(per);
    }

    /**
     * Testcase for PersonService-updatePerson-Exception Scenario
     * 
     * @throws JMSException
     * @throws EntityValidationException 
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonHavingCTEPID() throws JMSException, EntityValidationException {

        Person per = new Person();
        per.setId(5l);
        per.setStatus(EntityStatus.ACTIVE);
        per.setCtepId("VA212");
        
        perService.updatePerson(per);
    }

    /**
     * Testcase for PersonService-changePersonStatus
     */
    @Test
    public void testChangePersonStatus() throws JMSException, EntityValidationException {

        when(personBoService.getById(1L))
                .thenAnswer(new Answer<gov.nih.nci.po.data.bo.Person>() {
                    @Override
                    public gov.nih.nci.po.data.bo.Person answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.Person result = new gov.nih.nci.po.data.bo.Person();
                        result.setId(1L);
                        result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.ACTIVE);
                        return result;
                    }
                });

        Person retPerson = perService.changePersonStatus(1l,
                EntityStatus.ACTIVE);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
        Assert.assertEquals(EntityStatus.ACTIVE, retPerson.getStatus());
        verify(personBoService).curate(any(gov.nih.nci.po.data.bo.Person.class));
    }

    /**
     * Testcase for PersonService-changePersonStatus-PersonNotFoundInDB
     */
    @Test(expected = ServiceException.class)
    public void testChangePersonStatusForPersonNotFoundInDB() {
        
        perService.changePersonStatus(1002l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for PersonService-getPerson
     */
    @Test
    public void testGetPerson() {

        when(personBoService.getById(1L))
                .thenAnswer(new Answer<gov.nih.nci.po.data.bo.Person>() {
                    @Override
                    public gov.nih.nci.po.data.bo.Person answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.Person result = new gov.nih.nci.po.data.bo.Person();
                        result.setId(1L);
                        result.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.ACTIVE);
                        return result;
                    }
                });

        Person retPerson = perService.getPerson(1l);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
    }

    /**
     * Testcase for PersonService-getPersons
     */
    @Test
    public void testGetPersonsByCtepId() {
        final List<gov.nih.nci.po.data.bo.IdentifiedPerson> hits = new ArrayList<IdentifiedPerson>();
        for (int i=0; i<2; i++) {
            gov.nih.nci.po.data.bo.Person personInstance = ModelUtils.getBasicPerson();

            gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();

            gov.nih.nci.po.data.bo.IdentifiedPerson identifiedPerson = new gov.nih.nci.po.data.bo.IdentifiedPerson();
            identifiedPerson.setPlayer(personInstance);
            identifiedPerson.setScoper(organization);

            hits.add(identifiedPerson);
        }

        when(identifiedPersonBoService.search(any(SearchCriteria.class))).thenReturn(hits);

        Collection<Person> personList = perService.getPersonsByCtepId("12345");
        Assert.assertNotNull(personList);
        Assert.assertEquals(2, personList.size());

        // Call it again & Mock will return 'null' -- added for code coverage
        hits.clear();
        personList = perService.getPersonsByCtepId("12345");
        Assert.assertEquals(new ArrayList<Person>(), personList);
    }

    /**
     * Testcase for PersonService-searchPersons
     */
    @Test
    public void testSearchPersons() {

        List<PersonSearchDTO> hits = new ArrayList<PersonSearchDTO>();
        for (int i=0; i<2; i++) {
            PersonSearchDTO dto = new PersonSearchDTO();
            dto.setId(Long.valueOf(i));
            dto.setStatusCode(EntityStatus.ACTIVE.value());
            hits.add(dto);
        }


        when(personBoService.search(any(gov.nih.nci.po.service.PersonSearchCriteria.class), any(PageSortParams.class)))
                .thenReturn(hits);

        List<PersonSearchResult> psrList = perService.searchPersons(psCriteria);
        Assert.assertNotNull(psrList);
        Assert.assertEquals(2, psrList.size());
    }

    /**
     * Testcase for PersonService-searchPersons-Criteria is Null
     */
    @Test(expected = ServiceException.class)
    public void testSearchPersonsForNullCriteria() {
        
        perService.searchPersons(null);
    }

    /**
     * Testcase for PersonService-searchPersons-Criteria is Empty(Nothing
     * specified in search criteria)
     */
    @Test(expected = ServiceException.class)
    public void testSearchPersonsForEmptyCriteria() {
        
        perService.searchPersons(new PersonSearchCriteria());
    }

    /**
     * Testcase for PersonService-searchPersons-No matching person found
     */
    @Test
    public void testSearchPersonsForNoPersonFound() {

        when(personBoService.search(any(gov.nih.nci.po.service.PersonSearchCriteria.class), any(PageSortParams.class)))
                .thenReturn(Collections.EMPTY_LIST);
        List<PersonSearchResult> psrList = perService.searchPersons(psCriteria);
        Assert.assertTrue(psrList.size() == 0);
    }

    /**
     * Testcase for PersonService-createPersonRole-PersonRole is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullPersonRole() {
        
        perService.createPersonRole(null);
    }

    /**
     * Testcase for PersonService-createPersonRole-PersonRoleId is
     * Present(shouldn't be during creation)
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonRoleIdPresent() {
        
        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(1l);
        perService.createPersonRole(hcp);
    }

    /**
     * Testcase for PersonService-createPersonRole-HealthCareProvider
     */
    @Test
    public void testCreatePersonRoleHealthCareProvider() {
        HealthCareProvider healthCareProvider = getHealthCareProvider();

        final gov.nih.nci.po.data.bo.Person personInstance = ModelUtils.getBasicPerson();
        personInstance.setId(healthCareProvider.getPersonId());

        final gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(healthCareProvider.getOrganizationId());

        when(personBoService.getById(healthCareProvider.getPersonId()))
                .thenReturn(personInstance);

        when(serviceLocator.getOrganizationService().getById(healthCareProvider.getOrganizationId()))
                .thenReturn(organization);

        when(healthCareProviderBoService.getById(anyLong()))
                .thenAnswer( new Answer<gov.nih.nci.po.data.bo.HealthCareProvider>() {
                    @Override
                    public gov.nih.nci.po.data.bo.HealthCareProvider answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.HealthCareProvider result = new gov.nih.nci.po.data.bo.HealthCareProvider();
                        result.setPlayer(personInstance);
                        result.setScoper(organization);
                        result.setStatus(RoleStatus.PENDING);
                        result.setId((Long) invocation.getArguments()[0]);
                        return result;
                    }
                });

        PersonRole perRole = perService
                .createPersonRole(healthCareProvider);

        Assert.assertTrue(perRole instanceof HealthCareProvider);
        Assert.assertFalse(perRole instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-createPersonRole-ClinicalResearchStaff
     */
    @Test
    public void testCreatePersonRoleClinicalResearchStaff() {

        ClinicalResearchStaff clinicalResearchStaff = getClinicalResearchStaff();

        final gov.nih.nci.po.data.bo.Person personInstance = ModelUtils.getBasicPerson();
        personInstance.setId(clinicalResearchStaff.getPersonId());

        final gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(clinicalResearchStaff.getOrganizationId());

        when(personBoService.getById(clinicalResearchStaff.getPersonId()))
                .thenReturn(personInstance);

        when(serviceLocator.getOrganizationService().getById(clinicalResearchStaff.getOrganizationId()))
                .thenReturn(organization);

        when(clinicalResearchStaffBoService.getById(anyLong()))
                .thenAnswer( new Answer<gov.nih.nci.po.data.bo.ClinicalResearchStaff>() {
                    @Override
                    public gov.nih.nci.po.data.bo.ClinicalResearchStaff answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.ClinicalResearchStaff result = new gov.nih.nci.po.data.bo.ClinicalResearchStaff();
                        result.setPlayer(personInstance);
                        result.setScoper(organization);
                        result.setStatus(RoleStatus.PENDING);
                        result.setId((Long) invocation.getArguments()[0]);
                        return result;
                    }
                });

        PersonRole perRole = perService
                .createPersonRole(clinicalResearchStaff);
        Assert.assertTrue(perRole instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-createPersonRole-OrganizationalContact
     */
    @Test
    public void testCreatePersonRoleOrganizationalContact() {

        OrganizationalContact organizationalContact = getOrganizationalContact();

        final gov.nih.nci.po.data.bo.Person personInstance = ModelUtils.getBasicPerson();
        personInstance.setId(organizationalContact.getPersonId());

        final gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(organizationalContact.getOrganizationId());

        when(personBoService.getById(organizationalContact.getPersonId()))
                .thenReturn(personInstance);

        when(serviceLocator.getOrganizationService().getById(organizationalContact.getOrganizationId()))
                .thenReturn(organization);

        when(organizationalContactBoService.getById(anyLong()))
                .thenAnswer( new Answer<gov.nih.nci.po.data.bo.OrganizationalContact>() {
                    @Override
                    public gov.nih.nci.po.data.bo.OrganizationalContact answer(InvocationOnMock invocation) throws Throwable {
                        gov.nih.nci.po.data.bo.OrganizationalContact result = new gov.nih.nci.po.data.bo.OrganizationalContact();
                        result.setPlayer(personInstance);
                        result.setScoper(organization);
                        result.setStatus(RoleStatus.PENDING);
                        result.setId((Long) invocation.getArguments()[0]);
                        result.setType(new gov.nih.nci.po.data.bo.OrganizationalContactType(
                                OrganizationalContactType.RESPONSIBLE_PARTY.value()
                        ));
                        return result;
                    }
                });

        PersonRole perRole = perService
                .createPersonRole(organizationalContact);
        Assert.assertTrue(perRole instanceof OrganizationalContact);
    }

    /**
     * Testcase for PersonService-createPersonRole-ExceptionScenario
     * 
     * @throws JMSException
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonRoleExceptionScenario() throws JMSException {

        doThrow(
                new ServiceException(
                        "Exception Occured while creating Person Role."))
                .when(
                    healthCareProviderBoService).curate(
                        isA(gov.nih.nci.po.data.bo.HealthCareProvider.class)
                );


        perService.createPersonRole(getHealthCareProvider());
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullPersonRole() {

        perService.updatePersonRole(null);
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRoleId is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonRoleForNullDBId() {
        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(null);

        perService.updatePersonRole(hcp);
    }

    /**
     * Testcase for PersonService-updatePersonRole-HealthCareProvider
     */
    @Test
    public void testUpdatePersonRoleHealthCareProvider() {

        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(1l);

        final gov.nih.nci.po.data.bo.Person player = ModelUtils.getBasicPerson();
        player.setId(hcp.getPersonId());

        final Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(hcp.getOrganizationId());

        when(personBoService.getById(hcp.getPersonId())).thenReturn(player);

        when(healthCareProviderBoService.getById(1L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.HealthCareProvider>() {
            @Override
            public gov.nih.nci.po.data.bo.HealthCareProvider answer(InvocationOnMock invocation) throws Throwable {


                gov.nih.nci.po.data.bo.HealthCareProvider result = new gov.nih.nci.po.data.bo.HealthCareProvider();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                result.setScoper(organization);
                return result;
            }
        });


        PersonRole perRole = perService.updatePersonRole(hcp);
        Assert.assertNotNull(perRole);
    }

    /**
     * Testcase for PersonService-updatePersonRole-ClinicalResearchStaff
     */
    @Test
    public void testUpdatePersonRoleClinicalResearchStaff() {

        ClinicalResearchStaff crs = getClinicalResearchStaff();
        crs.setId(123l);


        final gov.nih.nci.po.data.bo.Person player = ModelUtils.getBasicPerson();
        player.setId(crs.getPersonId());

        final Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(crs.getOrganizationId());

        when(personBoService.getById(crs.getPersonId())).thenReturn(player);

        when(clinicalResearchStaffBoService.getById(123L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.ClinicalResearchStaff>() {
            @Override
            public gov.nih.nci.po.data.bo.ClinicalResearchStaff answer(InvocationOnMock invocation) throws Throwable {


                gov.nih.nci.po.data.bo.ClinicalResearchStaff result = new gov.nih.nci.po.data.bo.ClinicalResearchStaff();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                result.setScoper(organization);
                return result;
            }
        });


        PersonRole perRole = perService.updatePersonRole(crs);
        Assert.assertNotNull(perRole);
        Assert.assertTrue(perRole instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-updatePersonRole-OrganizationalContact
     */
    @Test
    public void testUpdatePersonRoleOrganizationalContact() {

        final OrganizationalContact oc = getOrganizationalContact();
        oc.setId(5639l);

        final gov.nih.nci.po.data.bo.Person player = ModelUtils.getBasicPerson();
        player.setId(oc.getPersonId());

        final Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(oc.getOrganizationId());

        when(personBoService.getById(oc.getPersonId())).thenReturn(player);

        when(organizationalContactBoService.getById(5639L)).thenAnswer(new Answer<gov.nih.nci.po.data.bo.OrganizationalContact>() {
            @Override
            public gov.nih.nci.po.data.bo.OrganizationalContact answer(InvocationOnMock invocation) throws Throwable {


                gov.nih.nci.po.data.bo.OrganizationalContact result = new gov.nih.nci.po.data.bo.OrganizationalContact();
                result.setId(1L);
                result.setStatus(RoleStatus.PENDING);
                result.setPlayer(player);
                result.setScoper(organization);
                result.setType(
                        new gov.nih.nci.po.data.bo.OrganizationalContactType(
                                oc.getType().value()
                        )
                );
                return result;
            }
        });

        PersonRole perRole = perService.updatePersonRole(oc);
        Assert.assertTrue(perRole instanceof OrganizationalContact);
    }

    /**
     * Testcase for PersonService-updatePersonRole-ExceptionScenario
     *
     * @throws JMSException
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonRoleExceptionScenario() throws JMSException {
        HealthCareProviderServiceLocal hcplocal = mock(HealthCareProviderServiceLocal.class);
        when(serviceLocator.getHealthCareProviderService())
                .thenReturn(hcplocal);
        doThrow(
                new ServiceException(
                        "Exception Occured while updating Person Role.")).when(
                hcplocal).curate(
                isA(gov.nih.nci.po.data.bo.HealthCareProvider.class));


        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(4546l);
        perService.updatePersonRole(hcp);
    }

    /**
     * Testcase for PersonService-getPersonRoles
     */
    @Test
    public void testGetPersonRolesByPersonId() {

        //mock role services
        gov.nih.nci.po.data.bo.Person personInstance = ModelUtils.getBasicPerson();
        personInstance.setId(1L);

        when(personBoService.getById(1L)).thenReturn(personInstance);

        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(2L);

        when(serviceLocator.getOrganizationService().getById(2L)).thenReturn(organization);

        gov.nih.nci.po.data.bo.OrganizationalContact organizationalContact = ModelUtils.getBasicOrganizationalContact(
                new gov.nih.nci.po.data.bo.OrganizationalContactType(
                        OrganizationalContactType.RESPONSIBLE_PARTY.value()
                )
        );
        organizationalContact.setScoper(organization);
        organizationalContact.setPlayer(personInstance);
        personInstance.getOrganizationalContacts().add(organizationalContact);

        gov.nih.nci.po.data.bo.HealthCareProvider healthCareProvider = ModelUtils.getBasicHealthCareProvider();
        healthCareProvider.setScoper(organization);
        healthCareProvider.setPlayer(personInstance);
        personInstance.getHealthCareProviders().add(healthCareProvider);

        Collection<PersonRole> perRoleList = perService.getPersonRolesByPersonId(1l);
        assertEquals(2, perRoleList.size());
    }

    /**
     * Testcase for PersonService-getPersonRoles-PersonNotFound in DB
     */
    @Test(expected = ServiceException.class)
    public void testGetPersonRolesPersonNotFoundInDB() {

        perService.getPersonRolesByPersonId(1002l);
    }

    /**
     * Testcase for PersonService-getPersonRole-HealthCareProvider
     */
    @Test
    public void testGetHCPPersonRole() {
        gov.nih.nci.po.data.bo.HealthCareProvider instance = ModelUtils.getBasicHealthCareProvider();
        instance.getScoper().setId(2L);
        instance.getPlayer().setId(3L);

        when(healthCareProviderBoService.getById(1L))
                .thenReturn(instance);

        HealthCareProvider hcp = perService.getPersonRoleById(
                HealthCareProvider.class, 1l);
        Assert.assertNotNull(hcp);
        Assert.assertTrue(hcp instanceof HealthCareProvider);
    }

    /**
     * Testcase for PersonService-getPersonRole-ClinicalResearchStaff
     */
    @Test
    public void testGetCRSPersonRole() {

        gov.nih.nci.po.data.bo.ClinicalResearchStaff instance = ModelUtils.getBasicClinicalResearchStaff();
        instance.getScoper().setId(2L);
        instance.getPlayer().setId(3L);

        when(clinicalResearchStaffBoService.getById(1L))
                .thenReturn(instance);

        ClinicalResearchStaff crs = perService.getPersonRoleById(
                ClinicalResearchStaff.class, 1l);
        Assert.assertNotNull(crs);
        Assert.assertTrue(crs instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-getPersonRole-OrganizationalContact
     */
    @Test
    public void testGetOrgContactPersonRole() {


        gov.nih.nci.po.data.bo.OrganizationalContact instance = ModelUtils.getBasicOrganizationalContact(
                new gov.nih.nci.po.data.bo.OrganizationalContactType(
                        OrganizationalContactType.RESPONSIBLE_PARTY.value()
                )
        );
        instance.getScoper().setId(2L);
        instance.getPlayer().setId(3L);

        when(organizationalContactBoService.getById(1L))
                .thenReturn(instance);

        OrganizationalContact oc = perService.getPersonRoleById(
                OrganizationalContact.class, 1l);
        Assert.assertNotNull(oc);
        Assert.assertTrue(oc instanceof OrganizationalContact);
    }

    /**
     * Testcase for PersonService-getPersonRole-PersonRole is Null
     */
    @Test(expected = ServiceException.class)
    public void testGetNullPersonRole() {

        PersonRole hcp = perService.getPersonRoleById(null, 1l);
        Assert.assertNotNull(hcp);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-HealthCareProvider
     */
    @Test
    public void testChangeHCPPersonRoleStatus() {
        gov.nih.nci.po.data.bo.HealthCareProvider instance = ModelUtils.getBasicHealthCareProvider();
        instance.getScoper().setId(2L);
        instance.getPlayer().setId(3L);

        when(healthCareProviderBoService.getById(1L))
                .thenReturn(instance);

        HealthCareProvider hcp = perService.changePersonRoleStatus(
                HealthCareProvider.class, 1l, EntityStatus.ACTIVE);
        Assert.assertNotNull(hcp);
        Assert.assertEquals(EntityStatus.ACTIVE, hcp.getStatus());
        Assert.assertTrue(hcp instanceof HealthCareProvider);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-ClinicalResearchStaff
     */
    @Test
    public void testChangeCRSPersonRoleStatus() {
        gov.nih.nci.po.data.bo.ClinicalResearchStaff instance = ModelUtils.getBasicClinicalResearchStaff();
        instance.getScoper().setId(2L);
        instance.getPlayer().setId(3L);

        when(clinicalResearchStaffBoService.getById(1L))
                .thenReturn(instance);

        ClinicalResearchStaff crs = perService.changePersonRoleStatus(
                ClinicalResearchStaff.class, 1l, EntityStatus.ACTIVE);
        Assert.assertNotNull(crs);
        Assert.assertEquals(EntityStatus.ACTIVE, crs.getStatus());
        Assert.assertTrue(crs instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-OrganizationalContact
     */
    @Test
    public void testChangeOrgConPersonRoleStatus() {

        gov.nih.nci.po.data.bo.OrganizationalContact instance = ModelUtils.getBasicOrganizationalContact(
                new gov.nih.nci.po.data.bo.OrganizationalContactType(
                        OrganizationalContactType.RESPONSIBLE_PARTY.value()
                )
        );
        instance.getScoper().setId(2L);
        instance.getPlayer().setId(3L);

        when(organizationalContactBoService.getById(1L))
                .thenReturn(instance);

        OrganizationalContact oc = perService.changePersonRoleStatus(
                OrganizationalContact.class, 1l, EntityStatus.ACTIVE);
        Assert.assertNotNull(oc);
        Assert.assertEquals(EntityStatus.ACTIVE, oc.getStatus());
        Assert.assertTrue(oc instanceof OrganizationalContact);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-PersonRole is Null
     */
    @Test(expected = ServiceException.class)
    public void testChangePersonRoleStatusForNullPersonRole() {
        
        perService.changePersonRoleStatus(null, 1l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-PersonRole not found in
     * the Database
     */
    @Test(expected = ServiceException.class)
    public void testChangePersonRoleStatusForPersonRoleNotFoundInDB() {
        
        perService.changePersonRoleStatus(HealthCareProvider.class, 54672l,
                EntityStatus.ACTIVE);
    }

    private HealthCareProvider getHealthCareProvider() {
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setLicense("license text");
        hcp.setPersonId(89767l);
        hcp.setOrganizationId(98054l);
        hcp.setStatus(EntityStatus.ACTIVE);
        hcp.getAddress().add(getJaxbAddressList().get(0));
        hcp.getContact().addAll(getJaxbContactList());
        return hcp;
    }

    private ClinicalResearchStaff getClinicalResearchStaff() {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setPersonId(10034l);
        crs.setOrganizationId(23415l);
        crs.setStatus(EntityStatus.ACTIVE);
        crs.getAddress().add(getJaxbAddressList().get(0));
        crs.getContact().addAll(getJaxbContactList());
        return crs;
    }

    private OrganizationalContact getOrganizationalContact() {
        OrganizationalContact oc = new OrganizationalContact();
        oc.setPersonId(1342l);
        oc.setOrganizationId(1567l);
        oc.setStatus(EntityStatus.ACTIVE);
        oc.setType(OrganizationalContactType.IRB);
        oc.getAddress().add(getJaxbAddressList().get(0));
        oc.getContact().addAll(getJaxbContactList());
        return oc;
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
}
