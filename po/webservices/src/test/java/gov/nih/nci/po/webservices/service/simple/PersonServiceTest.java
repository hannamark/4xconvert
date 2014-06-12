package gov.nih.nci.po.webservices.service.simple;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.webservices.convert.simple.AbstractConverterTest;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
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

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is the test class for PersonServiceImpl.
 * 
 * @author Rohit Gupta
 * 
 */
public class PersonServiceTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.Person person;

    private gov.nih.nci.po.webservices.types.PersonSearchCriteria psCriteria;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.Person
        person = new Person();
        person.setPrefix("Mr.");
        person.setFirstName("John");
        person.setMiddleName("L");
        person.setLastName("Doe");
        person.setSuffix("Sr");
        person.setStatus(EntityStatus.PENDING);

        person.setAddress(getJaxbAddressList().get(0));
        person.setCtepId("25879");
        person.getContact().addAll(getJaxbContactList());

        super.setUpMockObjects();

        psCriteria = new PersonSearchCriteria();
        psCriteria.setFirstName("Rohit");
        psCriteria.setOffset(0);
        psCriteria.setLimit(5);
    }

    /**
     * Testcase for PersonService-createPerson
     */
    @Test
    public void testCreatePerson() {
        PersonService perService = new PersonServiceImpl();
        Person retPerson = perService.createPerson(person);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
    }

    /**
     * Testcase for PersonService-createPerson-Person is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullPerson() {
        PersonService perService = new PersonServiceImpl();
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
        PersonServiceLocal personServiceLocal = mock(PersonServiceLocal.class);
        when(serviceLocator.getPersonService()).thenReturn(personServiceLocal);
        when(
                personServiceLocal
                        .create(isA(gov.nih.nci.po.data.bo.Person.class),isA(String.class)))
                .thenThrow(
                        new EntityValidationException(
                                "EntityValidationException Occured while creating the person.",
                                null));

        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
        PersonServiceLocal personServiceLocal = mock(PersonServiceLocal.class);
        when(serviceLocator.getPersonService()).thenReturn(personServiceLocal);
        when(
                personServiceLocal
                        .create(isA(gov.nih.nci.po.data.bo.Person.class),isA(String.class)))
                .thenThrow(
                        new ServiceException(
                                "Exception Occured while creating the person.",
                                null));
        perService.createPerson(person);
    }

    /**
     * Testcase for PersonService-createPerson-CtepOrganizationNotFound scenario
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonForCtepOrganizationNotFound()
            throws EntityValidationException, JMSException {

        // Mock setup for getting Organization
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(orgSerLocal);
        when(
                orgSerLocal.search(isA(OrganizationSearchCriteria.class),
                        isA(PageSortParams.class))).thenReturn(null);

        PersonService perService = new PersonServiceImpl();
        perService.createPerson(person);
    }

    /**
     * Testcase for PersonService-updatePerson
     */
    @Test
    public void testUpdatePerson() {
        PersonService perService = new PersonServiceImpl();
        person.setId(1l);
        Person retPerson = perService.updatePerson(person);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
    }

    /**
     * Testcase for PersonService-updatePerson-Person is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullPerson() {
        PersonService perService = new PersonServiceImpl();
        perService.updatePerson(null);
    }

    /**
     * Testcase for PersonService-updatePerson-PersonId is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonForNullDBId() {
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
        PersonServiceLocal personServiceLocal = mock(PersonServiceLocal.class);
        when(serviceLocator.getPersonService()).thenReturn(personServiceLocal);
        doThrow(
                new ServiceException(
                        "Exception Occured while updating the Person.")).when(
                personServiceLocal).curate(
                isA(gov.nih.nci.po.data.bo.Person.class),isA(String.class));
        Person per = new Person();
        per.setId(5l);
        per.setStatus(EntityStatus.ACTIVE);
        per.setCtepId("12345");
        perService.updatePerson(per);
    }

    /**
     * Testcase for PersonService-updatePerson-CtepOrganizationNotFound scenario
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonForCtepOrganizationNotFound()
            throws EntityValidationException, JMSException {

        // Mock setup for getting Organization
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(orgSerLocal);
        when(
                orgSerLocal.search(isA(OrganizationSearchCriteria.class),
                        isA(PageSortParams.class))).thenReturn(null);

        PersonService perService = new PersonServiceImpl();
        perService.updatePerson(person);
    }

    /**
     * Testcase for PersonService-changePersonStatus
     */
    @Test
    public void testChangePersonStatus() {
        PersonService perService = new PersonServiceImpl();
        Person retPerson = perService.changePersonStatus(1l,
                EntityStatus.ACTIVE);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
        Assert.assertEquals(EntityStatus.ACTIVE, retPerson.getStatus());
    }

    /**
     * Testcase for PersonService-changePersonStatus-PersonNotFoundInDB
     */
    @Test(expected = ServiceException.class)
    public void testChangePersonStatusForPersonNotFoundInDB() {
        PersonService perService = new PersonServiceImpl();
        perService.changePersonStatus(1002l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for PersonService-getPerson
     */
    @Test
    public void testGetPerson() {
        PersonService perService = new PersonServiceImpl();
        Person retPerson = perService.getPerson(1l);
        Assert.assertNotNull(retPerson);
        Assert.assertEquals(1l, retPerson.getId().longValue());
    }

    /**
     * Testcase for PersonService-getPersons
     */
    @Test
    public void testGetPersonsByCtepId() {
        PersonService perService = new PersonServiceImpl();
        Collection<Person> personList = perService.getPersonsByCtepId("12345");
        Assert.assertNotNull(personList);
        Assert.assertTrue(personList.size() > 0);

        // Call it again & Mock will return 'null' -- added for code coverage
        personList = perService.getPersonsByCtepId("12345");
        Assert.assertEquals(new ArrayList<Person>(), personList);
    }

    /**
     * Testcase for PersonService-searchPersons
     */
    @Test
    public void testSearchPersons() {
        PersonService perService = new PersonServiceImpl();
        List<PersonSearchResult> psrList = perService.searchPersons(psCriteria);
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() > 0);
    }

    /**
     * Testcase for PersonService-searchPersons-Criteria is Null
     */
    @Test(expected = ServiceException.class)
    public void testSearchPersonsForNullCriteria() {
        PersonService perService = new PersonServiceImpl();
        perService.searchPersons(null);
    }

    /**
     * Testcase for PersonService-searchPersons-Criteria is Empty(Nothing
     * specified in search criteria)
     */
    @Test(expected = ServiceException.class)
    public void testSearchPersonsForEmptyCriteria() {
        PersonService perService = new PersonServiceImpl();
        perService.searchPersons(new PersonSearchCriteria());
    }

    /**
     * Testcase for PersonService-searchPersons-No matching person found
     */
    @Test
    public void testSearchPersonsForNoPersonFound() {
        PersonService perService = new PersonServiceImpl();
        List<PersonSearchResult> psrList = perService.searchPersons(psCriteria);
        Assert.assertTrue(psrList.size() > 0);

        // Call it again & Mock will return 'null' -- added for code coverage
        psrList = perService.searchPersons(psCriteria);
        Assert.assertTrue(psrList.size() == 0);
    }

    /**
     * Testcase for PersonService-createPersonRole-PersonRole is Null
     */
    @Test(expected = ServiceException.class)
    public void testCreateNullPersonRole() {
        PersonService perService = new PersonServiceImpl();
        perService.createPersonRole(null);
    }

    /**
     * Testcase for PersonService-createPersonRole-PersonRoleId is
     * Present(shouldn't be during creation)
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonRoleIdPresent() {
        PersonService perService = new PersonServiceImpl();
        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(1l);
        perService.createPersonRole(hcp);
    }

    /**
     * Testcase for PersonService-createPersonRole-HealthCareProvider
     */
    @Test
    public void testCreatePersonRoleHealthCareProvider() {
        PersonService perService = new PersonServiceImpl();
        PersonRole perRole = perService
                .createPersonRole(getHealthCareProvider());
        Assert.assertTrue(perRole instanceof HealthCareProvider);
        Assert.assertFalse(perRole instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-createPersonRole-ClinicalResearchStaff
     */
    @Test
    public void testCreatePersonRoleClinicalResearchStaff() {
        PersonService perService = new PersonServiceImpl();
        PersonRole perRole = perService
                .createPersonRole(getClinicalResearchStaff());
        Assert.assertTrue(perRole instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-createPersonRole-OrganizationalContact
     */
    @Test
    public void testCreatePersonRoleOrganizationalContact() {
        PersonService perService = new PersonServiceImpl();
        PersonRole perRole = perService
                .createPersonRole(getOrganizationalContact());
        Assert.assertTrue(perRole instanceof OrganizationalContact);
    }

    /**
     * Testcase for PersonService-createPersonRole-ExceptionScenario
     * 
     * @throws JMSException
     */
    @Test(expected = ServiceException.class)
    public void testCreatePersonRoleExceptionScenario() throws JMSException {
        HealthCareProviderServiceLocal hcplocal = mock(HealthCareProviderServiceLocal.class);
        when(serviceLocator.getHealthCareProviderService())
                .thenReturn(hcplocal);
        doThrow(
                new ServiceException(
                        "Exception Occured while creating Person Role.")).when(
                hcplocal).curate(
                isA(gov.nih.nci.po.data.bo.HealthCareProvider.class));

        PersonService perService = new PersonServiceImpl();
        perService.createPersonRole(getHealthCareProvider());
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdateNullPersonRole() {
        PersonService perService = new PersonServiceImpl();
        perService.updatePersonRole(null);
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRoleId is Null
     */
    @Test(expected = ServiceException.class)
    public void testUpdatePersonRoleForNullDBId() {
        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(null);
        PersonService perService = new PersonServiceImpl();
        perService.updatePersonRole(hcp);
    }

    /**
     * Testcase for PersonService-updatePersonRole-HealthCareProvider
     */
    @Test
    public void testUpdatePersonRoleHealthCareProvider() {
        PersonService perService = new PersonServiceImpl();
        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(1l);
        PersonRole perRole = perService.updatePersonRole(hcp);
        Assert.assertNotNull(perRole);
    }

    /**
     * Testcase for PersonService-updatePersonRole-ClinicalResearchStaff
     */
    @Test
    public void testUpdatePersonRoleClinicalResearchStaff() {
        PersonService perService = new PersonServiceImpl();
        ClinicalResearchStaff crs = getClinicalResearchStaff();
        crs.setId(123l);
        PersonRole perRole = perService.updatePersonRole(crs);
        Assert.assertNotNull(perRole);
        Assert.assertTrue(perRole instanceof ClinicalResearchStaff);
    }

    /**
     * Testcase for PersonService-updatePersonRole-OrganizationalContact
     */
    @Test
    public void testUpdatePersonRoleOrganizationalContact() {
        PersonService perService = new PersonServiceImpl();
        OrganizationalContact oc = getOrganizationalContact();
        oc.setId(5639l);
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

        PersonService perService = new PersonServiceImpl();
        HealthCareProvider hcp = getHealthCareProvider();
        hcp.setId(4546l);
        perService.updatePersonRole(hcp);
    }

    /**
     * Testcase for PersonService-getPersonRoles
     */
    @Test
    public void testGetPersonRolesByPersonId() {
        PersonService perService = new PersonServiceImpl();
        Collection<PersonRole> perRoleList = perService.getPersonRolesByPersonId(1l);
        Assert.assertNotNull(perRoleList);
    }

    /**
     * Testcase for PersonService-getPersonRoles-PersonNotFound in DB
     */
    @Test(expected = ServiceException.class)
    public void testGetPersonRolesPersonNotFoundInDB() {
        PersonService perService = new PersonServiceImpl();
        perService.getPersonRolesByPersonId(1002l);
    }

    /**
     * Testcase for PersonService-getPersonRole-HealthCareProvider
     */
    @Test
    public void testGetHCPPersonRole() {
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
        PersonRole hcp = perService.getPersonRoleById(null, 1l);
        Assert.assertNotNull(hcp);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-HealthCareProvider
     */
    @Test
    public void testChangeHCPPersonRoleStatus() {
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
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
        PersonService perService = new PersonServiceImpl();
        perService.changePersonRoleStatus(null, 1l, EntityStatus.ACTIVE);
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-PersonRole not found in
     * the Database
     */
    @Test(expected = ServiceException.class)
    public void testChangePersonRoleStatusForPersonRoleNotFoundInDB() {
        PersonService perService = new PersonServiceImpl();
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
}
