package gov.nih.nci.po.webservices.service.simple.soap;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.test.TestUtils;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.webservices.service.simple.AbstractBaseTest;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonRoleStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonRoleStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonRoleResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRoleByIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRoleByIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRolesByPersonIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRolesByPersonIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonsByCtepIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonsByCtepIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.PersonService;
import gov.nih.nci.po.webservices.service.simple.soap.person.PersonService_Service;
import gov.nih.nci.po.webservices.service.simple.soap.person.RoleType;
import gov.nih.nci.po.webservices.service.simple.soap.person.SearchPersonsRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.SearchPersonsResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonRoleResponse;
import gov.nih.nci.po.webservices.service.utils.AuthUtils;
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

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is an Integration test class for PersonService(SOAP).
 * 
 * @author Rohit Gupta
 * 
 */
public class PersonServiceTest extends AbstractBaseTest {

    private PersonService personService = null;

    private static final String SPECIAL_CHARS_STR = TestUtils
            .getUTF8TestString();

    @Before
    public void setUp() throws Exception {

        super.setUp();

        // get PersonService
        QName serviceName = new QName(
                "http://soap.simple.service.webservices.po.nci.nih.gov/person/",
                "PersonService");
        URL url = new URL(TstProperties.getPersonServiceURL());

        AuthUtils.addBasicAuthSupport(
                        TstProperties.getWebserviceUsername(),
                        TstProperties.getWebservicePassword()
                );

        PersonService_Service service = new PersonService_Service(url,
                serviceName);

        AuthUtils.removeBasicAuthSupport();

        personService = service.getPersonServicePort();

        final Binding binding = ((BindingProvider) personService).getBinding();
        AuthUtils.addWsSecurityUTSupport(binding, TstProperties.getWebserviceUsername(), TstProperties.getWebservicePassword());

    }

    @Test
    public void testCreatePersonWithoutAuthentication() {
        AuthUtils.addWsSecurityUTSupport(((BindingProvider) personService).getBinding(), "bogusUser", "bogusPassword");
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        try {
            personService.createPerson(cpRequest);
            fail();
        } catch (WebServiceException wse) {
            assertEquals("The server sent HTTP status code 401: Unauthorized", wse.getMessage().trim());
        }
    }

    /**
     * Testcase for PersonService-createPerson
     */
    @Test
    public void testCreatePerson() {
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        Person retPerson = cpResponse.getPerson();
        Assert.assertNotNull(retPerson);
        Assert.assertNotNull(retPerson.getId());
        checkPersonDetails(person, retPerson, false);
    }

    /**
     * Testcase for PersonService-createPerson-Person is NULL
     */
    @Test
    public void testCreatePersonNullPerson() {
        String excepMessage = "";
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(null);
        try {
            personService.createPerson(cpRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{person}' is expected"));
    }

    /**
     * Testcase for PersonService-createPerson-database ID is present
     */
    @Test
    public void testCreatePersonIdPresent() {
        String excepMessage = null;
        person.setId(999999888888777777l);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        try {
            personService.createPerson(cpRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("IllegalArgumentException: id must be null on calls to create"));

        // try to get the person by ID - it shouldn't be found
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(999999888888777777l);
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);
        Assert.assertNull(gpResponse.getPerson());
    }

    /**
     * Testcase for PersonService-createPerson-CTEP ID is present
     */
    @Test
    public void testCreatePersonCtepIdPresent() {
        String excepMessage = null;
        person.setCtepId("123456789");
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        try {
            personService.createPerson(cpRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Person couldn't be created as CTEP ID 123456789 is passed in the request."));

        // try to get the person by ID - it shouldn't be found
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(123456789);
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);
        Assert.assertNull(gpResponse.getPerson());
    }
    
    /**
     * Testcase for PersonService-createPerson-Address not present
     */
    @Test
    public void testCreatePersonAddressNotPresentInRequest() {
        String excepMessage = null;
        person.setAddress(null);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        try {
            personService.createPerson(cpRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("gov.nih.nci.po.webservices.types\":address}' is expected"));
    }

    /**
     * Testcase for PersonService-createPerson-PhoneNumber is invalid
     */
    @Test
    public void testCreatePersonPhoneInvalid() {
        String excepMessage = null;
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        person.getContact().set(1, phoneContact); // set invalid phone number
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        try {
            personService.createPerson(cpRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Person couldn't be created as data is invalid. The invalid elements are: phone"));
    }

    /**
     * Testcase for PersonService-updatePerson
     */
    @Test
    public void testUpdatePerson() {
        // create a person first
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        Person createdPerson = cpResponse.getPerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");
        createdPerson.setFirstName("updated first name");
        createdPerson.setLastName("updated last name");
        createdPerson.setSuffix("IX");
        createdPerson.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdPerson.setAddress(getJaxbAddressList().get(1));

        // clear the existing contacts & set new one
        createdPerson.getContact().clear();
        createdPerson.getContact().addAll(getJaxbUpdatedContactList()); // updated

        // now update the created person
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        upRequest.setPerson(createdPerson);
        UpdatePersonResponse upResponse = personService.updatePerson(upRequest);
        Person retUpdatedPerson = upResponse.getPerson();

        checkPersonDetails(createdPerson, retUpdatedPerson, true);
    }

    /**
     * Testcase for PersonService-updatePerson-IdNotPresentInRequest
     */
    @Test
    public void testUpdatePersonForIdNotPresentInRequest() {
        String excepMessage = null;
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        person.setId(null); // Id not present in the request
        upRequest.setPerson(person);
        try {
            personService.updatePerson(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The Person couldn't be updated"));
        Assert.assertTrue(excepMessage.contains("personId is null"));
    }

    /**
     * Testcase for PersonService-updatePerson-CtepId Present In Request
     */
    @Test
    public void testUpdatePersonForCtepIdPresentInRequest() {
        String excepMessage = null;
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        person.setId(9999999999999L);
        person.setCtepId("123456789");
        upRequest.setPerson(person);
        try {
            personService.updatePerson(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage.contains("couldn't be updated as CTEP ID 123456789 is passed in the request."));
    }
    
    /**
     * Testcase for PersonService-updatePerson-Person is NULL
     */
    @Test
    public void testUpdatePersonForNullPerson() {
        String excepMessage = null;
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        upRequest.setPerson(null);
        try {
            personService.updatePerson(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{person}' is expected"));
    }

    /**
     * Testcase for PersonService-updatePerson-Address is NULL
     */
    @Test
    public void testUpdatePersonForNullAddress() {
        // create a person first
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        Person createdPerson = cpResponse.getPerson();

        // now set person's address to NULL and update
        String excepMessage = null;
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        createdPerson.setAddress(null); // address is set to null
        upRequest.setPerson(createdPerson);
        try {
            personService.updatePerson(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        // check for the exception message
        Assert.assertTrue(excepMessage.contains("address}' is expected"));

        // now get the same person and check that address is still there
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(createdPerson.getId());
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);
        Assert.assertNotNull(gpResponse.getPerson().getAddress());
    }

    /**
     * Testcase for PersonService-updatePerson-Invalid Phone#
     */
    @Test
    public void testUpdatePersonForInvalidPhone() {
        // create a person first
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        Person createdPerson = cpResponse.getPerson();

        // now set invalid phone and try to update
        for (Contact contact : createdPerson.getContact()) {
            // set invalid phone number
            if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                contact.setValue("703@35@234");
            }
        }

        String excepMessage = null;
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        upRequest.setPerson(createdPerson); // try to update
        try {
            personService.updatePerson(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        // check for the exception message
        Assert.assertTrue(excepMessage
                .contains("validation failed for: gov.nih.nci.po.data.bo.PhoneNumber"));

        // now get the same person and check that old(valid) phone# is present
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(createdPerson.getId());
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);
        for (Contact contact : gpResponse.getPerson().getContact()) {
            // set invalid phone number
            if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals("571-456-1245", contact.getValue());
            }
        }
    }

    /**
     * Testcase for PersonService-updatePerson-PersonNotFoundInDB
     */
    @Test
    public void testUpdatePersonForPersonNotFoundInDB() {
        String excepMessage = null;
        UpdatePersonRequest upRequest = new UpdatePersonRequest();
        person.setId(999999888888777777l); // Id not present in the DB
        upRequest.setPerson(person);
        try {
            personService.updatePerson(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("ObjectNotFoundException: No row with the given identifier exists"));
    }

    /**
     * Testcase for PersonService-changePersonStatus
     */
    @Test
    public void testChangePersonStatus() {
        Person createdPerson = createActivePerson();// status ACTIVE

        // change the status of the person to INACTIVE
        ChangePersonStatusRequest request = new ChangePersonStatusRequest();
        request.setPersonID(createdPerson.getId());
        request.setStatus(EntityStatus.INACTIVE);
        ChangePersonStatusResponse response = personService
                .changePersonStatus(request);

        Assert.assertTrue(response.getPerson().getStatus().value()
                .equals(EntityStatus.INACTIVE.value()));

        // check Person Status is the DB
        checkPersonStatusInDB(response.getPerson().getId(),
                EntityStatus.INACTIVE.value());
    }

    /**
     * Testcase for PersonService-changePersonStatus-invalid transition
     */
    @Test
    public void testChangePersonStatusInvalidTransition() {
        // create ACTIVE person
        Person createdPerson = createActivePerson();

        // try to change status to PENDING
        ChangePersonStatusRequest request = new ChangePersonStatusRequest();
        request.setPersonID(createdPerson.getId());
        request.setStatus(EntityStatus.PENDING);
        String excepMessage = "";
        try {
            personService.changePersonStatus(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("Illegal curation transition from ACTIVE to PENDING"));

        // check Person Status in the DB (it shouldn't change)
        checkPersonStatusInDB(createdPerson.getId(),
                EntityStatus.ACTIVE.value());
    }

    /**
     * Testcase for PersonService-changePersonStatus-personId not present in DB
     */
    @Test
    public void testChangePersonStatusForPersonIdNotFoundInDB() {
        ChangePersonStatusRequest request = new ChangePersonStatusRequest();
        // Id not present in DB
        request.setPersonID(999999888888777777l);
        request.setStatus(EntityStatus.ACTIVE);
        String excepMessage = "";
        try {
            personService.changePersonStatus(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("Couldn't update the Person Status for personID"));
    }

    /**
     * Testcase for PersonService-getPerson
     */
    @Test
    public void testGetPerson() {
        // create an ACTIVE person
        Person createdPerson = createActivePerson();

        // now get the same person
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(createdPerson.getId());
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);

        // check the person details
        checkPersonDetails(createdPerson, gpResponse.getPerson(), false);
    }

    /**
     * Testcase for PersonService-getPerson- name having special chars
     */
    @Test
    public void testGetPersonHavingSpecialChars() {
        // create an person having special chars in name
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        person.setFirstName(SPECIAL_CHARS_STR);
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        Person createdPerson = cpResponse.getPerson();

        // now get the same person
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(createdPerson.getId());
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);

        // check the person details
        checkPersonDetails(createdPerson, gpResponse.getPerson(), false);
    }

    /**
     * Testcase for PersonService-getPerson-PersonNotFound in DB
     */
    @Test
    public void testGetPersonForPersonNotFoundInDB() {
        GetPersonRequest gpRequest = new GetPersonRequest();
        gpRequest.setPersonID(999999888888777777l); // Id not present
        GetPersonResponse gpResponse = personService.getPerson(gpRequest);

        Assert.assertNull(gpResponse.getPerson());
    }

    /**
     * Testcase for PersonService-getPersonsByCtepId
     */
    @Test
    public void testGetPersonsByCtepId() {
        String randomCtepId = RandomStringUtils.random(11, true, true);        

        // create few person
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse resp = personService.createPerson(cpRequest);
        createIdentifiedPerson(resp.getPerson().getId(), randomCtepId);

        cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        resp = personService.createPerson(cpRequest);
        createIdentifiedPerson(resp.getPerson().getId(), randomCtepId);

        cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        resp = personService.createPerson(cpRequest);
        createIdentifiedPerson(resp.getPerson().getId(), randomCtepId);

        GetPersonsByCtepIdRequest request = new GetPersonsByCtepIdRequest();
        request.setCtepID(randomCtepId);
        GetPersonsByCtepIdResponse response = personService.getPersonsByCtepId(request);
        List<Person> personList = response.getPersonList();
        Assert.assertTrue(personList.size() >= 3);

        for (Person person : personList) {
            Assert.assertEquals(randomCtepId, person.getCtepId());
        }
    }

    /**
     * Testcase for PersonService-getPersonsByCtepId- Not found in DB
     */
    @Test
    public void testGetPersonsByCtepIdForNotFoundInDB() {
        String randomCtepId = RandomStringUtils.random(76, true, true);

        GetPersonsByCtepIdRequest request = new GetPersonsByCtepIdRequest();
        request.setCtepID(randomCtepId);
        GetPersonsByCtepIdResponse response = personService
                .getPersonsByCtepId(request);
        List<Person> personList = response.getPersonList();

        Assert.assertTrue(personList.size() == 0);
    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName
     */
    @Test
    public void testSearchPersonsByFirstName() {
        String randomFirstName = RandomStringUtils.random(30, true, true);
        String randomLastName = RandomStringUtils.random(35, true, true);
        // create few persons
        person.setFirstName(randomFirstName); // for LIKE search
        person.setLastName(randomLastName);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.setFirstName("Rohit_" + randomFirstName); // for LIKE search
        person.setLastName(randomLastName);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by First Name
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setFirstName(randomFirstName);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 2);
        for (PersonSearchResult psr : psrList) {
            Assert.assertTrue(psr.getFirstName().contains(randomFirstName)); // LIKE
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName having special
     * chars
     */
    @Test
    public void testSearchPersonsByFirstNameHavingSpecialChars() {
        // create few persons
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        person.setFirstName(SPECIAL_CHARS_STR);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.setFirstName("Rohit_" + SPECIAL_CHARS_STR); // for LIKE search
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by First Name with special chars
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setFirstName(SPECIAL_CHARS_STR);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        // rerun might have more records
        Assert.assertTrue(psrList.size() >= 2);
        for (PersonSearchResult psr : psrList) {
            Assert.assertTrue(psr.getFirstName().contains(SPECIAL_CHARS_STR)); // LIKE
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by LastName/City
     */
    @Test
    public void testSearchPersonsByLastNameAndCity() {
        String randomFirstName = RandomStringUtils.random(30, true, true);
        String randomLastName = RandomStringUtils.random(36, true, true);
        // create few persons
        person.setLastName("xyz_" + randomLastName);
        person.setFirstName(randomFirstName);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.setLastName("abc_" + randomLastName);
        person.setFirstName(randomFirstName);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by LastName & City
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setLastName(randomLastName);
        psCriteria.setCity("Herndon");
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 2);
        for (PersonSearchResult psr : psrList) {
            // LIKE search using last name
            Assert.assertTrue(psr.getLastName().contains(randomLastName));
            Assert.assertTrue(psr.getCity().contains("Herndon")); // LIKE search
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by LastName having special
     * chars
     */
    @Test
    public void testSearchPersonsByLastNameHavingSpecialChars() {
        // create few persons
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        person.setLastName("xyz_" + SPECIAL_CHARS_STR);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.setLastName("abc_" + SPECIAL_CHARS_STR);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by LastName
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setLastName(SPECIAL_CHARS_STR);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        // rerun might have more results
        Assert.assertTrue(psrList.size() >= 2);
        for (PersonSearchResult psr : psrList) {
            // LIKE search using last name
            Assert.assertTrue(psr.getLastName().contains(SPECIAL_CHARS_STR));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Email
     */
    @Test
    public void testSearchPersonsByEmail() {
        String randomEmail = RandomStringUtils.random(15, true, true);
        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue(randomEmail + "@mayoclinic.org");

        // create few persons
        person.getContact().set(0, emailContact);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.setFirstName("Rohit Kumar");
        // this person also have random generated email
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by Email
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setEmail(randomEmail);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 2);
        for (PersonSearchResult psr : psrList) {
            Assert.assertTrue(psr.getEmailAddresses().contains(randomEmail)); // LIKE
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by CtepId
     */
    @Test
    public void testSearchPersonsByCtepId() {        
        String randomCtepId = RandomStringUtils.random(11, true, true);        

        // create few person
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse resp = personService.createPerson(cpRequest);
        createIdentifiedPerson(resp.getPerson().getId(), randomCtepId);

        cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        resp = personService.createPerson(cpRequest);
        createIdentifiedPerson(resp.getPerson().getId(), randomCtepId);

        // search by CtepId
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setCtepID(randomCtepId);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 2);
        // LIKE search
        for (PersonSearchResult psr : psrList) {
            Assert.assertTrue(psr.getCtepID().contains(randomCtepId));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by PO database Id
     */
    @Test
    public void testSearchPersonsByPOId() {
        // create a person
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        long poId = cpResponse.getPerson().getId();

        // search by PO database Id
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setId(poId);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 1); // EXACT search
        Assert.assertTrue(poId == psrList.get(0).getId());
    }

    /**
     * Testcase for PersonService-searchPersons- by Status
     */
    @Test
    public void testSearchPersonsByStatus() {
        // create a person
        person.setStatus(EntityStatus.ACTIVE);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        long createdPersonId = cpResponse.getPerson().getId();

        // search by Status
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setStatusCode(EntityStatus.ACTIVE);
        psCriteria.setLimit(500);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() >= 1);

        boolean createdPersonPresent = false;
        for (PersonSearchResult psr : psrList) {
            Assert.assertTrue(EntityStatus.ACTIVE.value().equalsIgnoreCase(
                    psr.getStatusCode().value()));

            // also check that the collection contain just created Person
            if (psr.getId() == createdPersonId) {
                createdPersonPresent = true;
            }
        }

        if (!createdPersonPresent) {
            Assert.fail("The returned collection doesn't contain newly created person.");
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Country
     */
    @Test
    public void testSearchPersonsByCountry() {
        // create few persons
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 2nd person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 3rd person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 4th person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 5th person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by Country
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setCountryCode(CountryISO31661Alpha3Code.USA);
        psCriteria.setLimit(4);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 4); // as the limit is 4
        for (PersonSearchResult psr : psrList) {
            Assert.assertTrue(psr.getCountryCode().name()
                    .equalsIgnoreCase("USA"));// EXACT search
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by AddressLine1
     */
    @Test
    public void testSearchPersonsByAddressLine1() {
        String randomLine1 = RandomStringUtils.random(50, true, true);
        // create few persons
        person.getAddress().setLine1(randomLine1);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.getAddress().setLine1(randomLine1);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by AddressLine1
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setLine1(randomLine1);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 2);
        for (PersonSearchResult psr : psrList) {
            // LIKE search
            Assert.assertTrue(psr.getLine1().contains(randomLine1));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by ZipCode
     */
    @Test
    public void testSearchPersonsByZipCode() {
        String randomPostalCode = RandomStringUtils.random(10, false, true);
        // create few persons
        person.getAddress().setPostalcode(randomPostalCode);
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create another person
        person.getAddress().setPostalcode(randomPostalCode);
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by ZipCode
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setPostalcode(randomPostalCode);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 2);
        for (PersonSearchResult psr : psrList) {
            // LIKE search
            Assert.assertTrue(psr.getPostalcode().contains(randomPostalCode));
        }
    }

    /**
     * Testcase for PersonService-searchPersons-for Offset & Limit
     */
    @Test
    public void testSearchPersonsForOffsetAndLimit() {
        String randomLastName = RandomStringUtils.random(30, true, true);
        // all the below created person will have same last name
        person.setLastName(randomLastName);

        // create 1st person
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        person.setFirstName("0000000000000000000000000");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 2nd person
        person.setFirstName("1111111111111111111111111");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 3rd person
        person.setFirstName("2222222222222222222222222");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 4th person
        person.setFirstName("3333333333333333333333333");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 5th person
        person.setFirstName("44444444444444444444444444");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 6th person
        person.setFirstName("5555555555555555555555555555");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 7th person
        person.setFirstName("66666666666666666666666666666");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 8th person
        person.setFirstName("77777777777777777777777777777");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 9th person
        person.setFirstName("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 10th person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 11th person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 12th person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // create 13th person
        cpRequest.setPerson(person);
        personService.createPerson(cpRequest);

        // search by LastName with Offset & Limit
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        psCriteria.setLastName(randomLastName);
        psCriteria.setOffset(2);
        psCriteria.setLimit(3);
        spRequest.setPersonSearchCriteria(psCriteria);
        SearchPersonsResponse spResponse = personService
                .searchPersons(spRequest);
        List<PersonSearchResult> psrList = spResponse
                .getPersonSearchResultList();
        Assert.assertNotNull(psrList);
        Assert.assertTrue(psrList.size() == 3); // as the limit is 3
        for (PersonSearchResult psr : psrList) {
            // check that serach was find using LastName
            Assert.assertTrue(psr.getLastName()
                    .equalsIgnoreCase(randomLastName));
        }
        for (int i = 0; i < psrList.size(); i++) {
            PersonSearchResult psr = psrList.get(i);
            if (i == 0) {
                Assert.assertTrue(psr.getFirstName().equalsIgnoreCase(
                        "2222222222222222222222222"));
            } else if (i == 1) {
                Assert.assertTrue(psr.getFirstName().equalsIgnoreCase(
                        "3333333333333333333333333"));
            } else if (i == 2) {
                Assert.assertTrue(psr.getFirstName().equalsIgnoreCase(
                        "44444444444444444444444444"));
            }
        }

    }

    /**
     * Testcase for PersonService-searchPerson-Search Criteria is empty
     */
    @Test
    public void testSearchPersonCriteriaEmpty() {
        String excepMessage = null;
        SearchPersonsRequest spRequest = new SearchPersonsRequest();
        // criteria is empty
        PersonSearchCriteria psCriteria = new PersonSearchCriteria();
        spRequest.setPersonSearchCriteria(psCriteria);
        try {
            personService.searchPersons(spRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("OneCriterionRequiredException: At least one criterion must be provided"));
    }

    /**
     * Testcase for PersonService-createPersonRole-HCP
     */
    @Test
    public void testCreatePersonRoleHCP() {

        HealthCareProvider hcp = getHealthCareProviderObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(hcp);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);

        PersonRole perRole = response.getPersonRole();
        Assert.assertTrue(perRole instanceof HealthCareProvider);
        Assert.assertNotNull(perRole.getId());

        // check for the address details
        checkPersonRoleAddressDetails(hcp, perRole);

        // check for the contact details
        checkPersonRoleContactDetails(hcp, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-CRS
     */
    @Test
    public void testCreatePersonRoleCRS() {
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(crs);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);

        PersonRole perRole = response.getPersonRole();
        Assert.assertTrue(perRole instanceof ClinicalResearchStaff);
        Assert.assertNotNull(perRole.getId());

        // check for the address details
        checkPersonRoleAddressDetails(crs, perRole);

        // check for the contact details
        checkPersonRoleContactDetails(crs, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-OC
     */
    @Test
    public void testCreatePersonRoleOC() {
        OrganizationalContact oc = getOrganizationalContactObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(oc);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);

        PersonRole perRole = response.getPersonRole();
        Assert.assertTrue(perRole instanceof OrganizationalContact);
        Assert.assertNotNull(perRole.getId());
        // check for the address details
        checkPersonRoleAddressDetails(oc, perRole);

        // check for the contact details
        checkPersonRoleContactDetails(oc, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-Null PersonRole
     */
    @Test
    public void testCreatePersonRoleForNullPerRole() {
        String excepMessage = "";
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(null); // PersonRole is Null
        try {
            personService.createPersonRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{personRole}' is expected"));
    }

    /**
     * Testcase for PersonService-createPersonRole-DB Id Present.
     */
    @Test
    public void testCreatePersonRoleForIdPresentInRequest() {
        String excepMessage = "";
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        HealthCareProvider hcp = getHealthCareProviderObj();
        hcp.setId(11123l); // Id is present
        request.setPersonRole(hcp); // PersonRole is Null
        try {
            personService.createPersonRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The PersonRole couldn't be created as personRoleId is present in the request"));
    }

    /**
     * Testcase for PersonService-updatePersonRole-HCP
     */
    @Test
    public void testUpdatePersonRoleHCP() {
        // create HCP first
        HealthCareProvider hcp = getHealthCareProviderObj(); // ACTIVE
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(hcp);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);

        PersonRole perRole = response.getPersonRole();

        // now update the HCP details
        UpdatePersonRoleRequest upRequest = new UpdatePersonRoleRequest();
        // update the status
        perRole.setStatus(EntityStatus.NULLIFIED);
        // update the address
        perRole.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        perRole.getContact().clear(); // clear existing
        perRole.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setPersonRole(perRole);
        UpdatePersonRoleResponse upResponse = personService
                .updatePersonRole(upRequest);
        // check for update Status
        checkHCPStatusInDB(upResponse.getPersonRole().getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(perRole, upResponse.getPersonRole());
        // check for the updated contact details
        checkPersonRoleContactDetails(perRole, upResponse.getPersonRole(),
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-OC
     */
    @Test
    public void testUpdatePersonRoleOC() {
        // create OC first
        OrganizationalContact oc = getOrganizationalContactObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(oc);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);

        PersonRole perRole = response.getPersonRole();
        Assert.assertTrue(perRole instanceof OrganizationalContact);

        // now update the OC details
        UpdatePersonRoleRequest upRequest = new UpdatePersonRoleRequest();
        // update the address
        perRole.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        perRole.getContact().clear(); // clear existing
        perRole.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setPersonRole(perRole);
        UpdatePersonRoleResponse upResponse = personService
                .updatePersonRole(upRequest);
        // check for the updated address details
        checkPersonRoleAddressDetails(perRole, upResponse.getPersonRole());
        // check for the updated contact details
        checkPersonRoleContactDetails(perRole, upResponse.getPersonRole(),
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-CRS
     */
    @Test
    public void testUpdatePersonRoleCRS() {
        // create CRS first
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(crs);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);

        PersonRole perRole = response.getPersonRole();
        Assert.assertTrue(perRole instanceof ClinicalResearchStaff);

        // now update the CRS details
        UpdatePersonRoleRequest upRequest = new UpdatePersonRoleRequest();
        // update the address
        perRole.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        perRole.getContact().clear(); // clear existing
        perRole.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setPersonRole(perRole);
        UpdatePersonRoleResponse upResponse = personService
                .updatePersonRole(upRequest);
        // check for the updated address details
        checkPersonRoleAddressDetails(perRole, upResponse.getPersonRole());
        // check for the updated contact details
        checkPersonRoleContactDetails(perRole, upResponse.getPersonRole(),
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-null PersonRole
     */
    @Test
    public void testUpdatePersonRoleForNullPersonRole() {
        String excepMessage = null;
        UpdatePersonRoleRequest upRequest = new UpdatePersonRoleRequest();
        upRequest.setPersonRole(null);
        try {
            personService.updatePersonRole(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{personRole}' is expected"));
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole ID not present in
     * the request
     */
    @Test
    public void testUpdatePersonRoleForRoleIdNotPresentInRequest() {
        String excepMessage = null;
        UpdatePersonRoleRequest upRequest = new UpdatePersonRoleRequest();
        HealthCareProvider hcp = getHealthCareProviderObj(); // DB id not
                                                             // present
        upRequest.setPersonRole(hcp);
        try {
            personService.updatePersonRole(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The PersonRole couldn't be updated as either personRole or personRoleId is null"));
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId
     */
    @Test
    public void testGetPersonRolesByPersonId() {
        // create a person
        Person person = createActivePerson();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();

        // create a role-HCP for that person
        HealthCareProvider hcp = getHealthCareProviderObj();
        hcp.setPersonId(person.getId());
        request.setPersonRole(hcp);
        personService.createPersonRole(request);

        // create a role-OC for that person
        OrganizationalContact oc = getOrganizationalContactObj();
        oc.setPersonId(person.getId());
        request.setPersonRole(oc);
        personService.createPersonRole(request);

        // create a role-CRS for that person
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        crs.setPersonId(person.getId());
        request.setPersonRole(crs);
        personService.createPersonRole(request);

        // get the Roles by the PersonId.
        GetPersonRolesByPersonIdRequest req = new GetPersonRolesByPersonIdRequest();
        req.setPersonID(person.getId());
        GetPersonRolesByPersonIdResponse res = personService
                .getPersonRolesByPersonId(req);

        List<PersonRole> perRoleList = res.getPersonRoleList();
        Assert.assertTrue(perRoleList.size() == 3);

        for (PersonRole perRole : perRoleList) {
            // check that Ids are same
            Assert.assertTrue(person.getId() == perRole.getPersonId());
        }
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId-personId not found in
     * DB.
     */
    @Test
    public void testGetPersonRolesByPersonIdNotFoundInDB() {
        String excepMessage = "";
        // get the Roles by the PersonId.
        GetPersonRolesByPersonIdRequest req = new GetPersonRolesByPersonIdRequest();
        req.setPersonID(999999888888777777l); // Id not present in DB
        try {
            personService.getPersonRolesByPersonId(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The PersonRole couldn't be fetched"));
    }

    /**
     * Testcase for PersonService-getPersonRoleById-HCP
     */
    @Test
    public void testGetPersonRoleByIdHCP() {
        // create a role-HCP
        HealthCareProvider hcp = getHealthCareProviderObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(hcp);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();

        // get the Role by the DB Id.
        GetPersonRoleByIdRequest req = new GetPersonRoleByIdRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.HEALTH_CARE_PROVIDER);
        GetPersonRoleByIdResponse res = personService.getPersonRoleById(req);

        Assert.assertTrue(res.getPersonRole() instanceof HealthCareProvider);
        Assert.assertTrue(perRole.getId().longValue() == res.getPersonRole()
                .getId().longValue());
        Assert.assertTrue(perRole.getPersonId() == res.getPersonRole()
                .getPersonId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-OC
     */
    @Test
    public void testGetPersonRoleByIdOC() {
        // create a role-OC
        OrganizationalContact oc = getOrganizationalContactObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(oc);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();

        // get the Roles by the DB Id.
        GetPersonRoleByIdRequest req = new GetPersonRoleByIdRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.ORGANIZATIONAL_CONTACT);
        GetPersonRoleByIdResponse res = personService.getPersonRoleById(req);

        Assert.assertTrue(res.getPersonRole() instanceof OrganizationalContact);
        Assert.assertTrue(perRole.getId().longValue() == res.getPersonRole()
                .getId().longValue());
        Assert.assertTrue(perRole.getPersonId() == res.getPersonRole()
                .getPersonId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-CRS
     */
    @Test
    public void testGetPersonRoleByIdCRS() {
        // create a role-CRS
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(crs);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();

        // get the Roles by the DB Id.
        GetPersonRoleByIdRequest req = new GetPersonRoleByIdRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.CLINICAL_RESEARCH_STAFF);
        GetPersonRoleByIdResponse res = personService.getPersonRoleById(req);

        Assert.assertTrue(res.getPersonRole() instanceof ClinicalResearchStaff);
        Assert.assertTrue(perRole.getId().longValue() == res.getPersonRole()
                .getId().longValue());
        Assert.assertTrue(perRole.getPersonId() == res.getPersonRole()
                .getPersonId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-HCP Not found in DB
     */
    @Test
    public void testGetPersonRoleByIdHCPNotFoundInDB() {
        // get the Roles by the DB Id.
        GetPersonRoleByIdRequest req = new GetPersonRoleByIdRequest();
        req.setPersonRoleID(999999888888777777l); // Id not present in DB
        req.setRoleType(RoleType.HEALTH_CARE_PROVIDER);
        GetPersonRoleByIdResponse res = personService.getPersonRoleById(req);

        Assert.assertNull(res.getPersonRole());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-CRS
     */
    @Test
    public void testGetPersonRoleByIdForNullRoleType() {
        // create a role-CRS
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(crs);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();

        // get the Roles by the DB Id, but Roletype is NULL
        GetPersonRoleByIdRequest req = new GetPersonRoleByIdRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(null);
        String excepMessage = null;
        try {
            personService.getPersonRoleById(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{roleType}' is expected"));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-HCP
     */
    @Test
    public void testChangePersonRoleStatusHCP() {
        // create HCP first
        HealthCareProvider hcp = getHealthCareProviderObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(hcp);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();
        assertEquals(EntityStatus.ACTIVE.value(), perRole.getStatus().value());

        // now change the status to InActive
        ChangePersonRoleStatusRequest req = new ChangePersonRoleStatusRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.HEALTH_CARE_PROVIDER);
        req.setStatus(EntityStatus.NULLIFIED);
        ChangePersonRoleStatusResponse res = personService
                .changePersonRoleStatus(req);

        assertEquals(EntityStatus.NULLIFIED.value(),  res.getPersonRole().getStatus().value());
        checkHCPStatusInDB(res.getPersonRole().getId(), EntityStatus.NULLIFIED.value());
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-OC
     */
    @Test
    public void testChangePersonRoleStatusOC() {
        // create OC first
        OrganizationalContact oc = getOrganizationalContactObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(oc);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();
        assertEquals(EntityStatus.ACTIVE.value(), perRole.getStatus().value());

        // now change the status to InActive
        ChangePersonRoleStatusRequest req = new ChangePersonRoleStatusRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.ORGANIZATIONAL_CONTACT);
        req.setStatus(EntityStatus.NULLIFIED);
        ChangePersonRoleStatusResponse res = personService
                .changePersonRoleStatus(req);

        Assert.assertEquals(EntityStatus.NULLIFIED.value(),  res.getPersonRole().getStatus().value());
        checkOrgContactStatusInDB(res.getPersonRole().getId(), EntityStatus.NULLIFIED.value());
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-CRS
     */
    @Test
    public void testChangePersonRoleStatusCRS() {
        // create a role-CRS
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(crs);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();
        assertEquals(EntityStatus.ACTIVE.value(), perRole.getStatus().value());

        // now change the status to InActive
        ChangePersonRoleStatusRequest req = new ChangePersonRoleStatusRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.CLINICAL_RESEARCH_STAFF);
        req.setStatus(EntityStatus.NULLIFIED);
        ChangePersonRoleStatusResponse res = personService
                .changePersonRoleStatus(req);

        Assert.assertEquals(EntityStatus.NULLIFIED.value(),  res.getPersonRole().getStatus().value());
        checkCRSStatusInDB(res.getPersonRole().getId(), EntityStatus.NULLIFIED.value());
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-CRS - not found in DB
     */
    @Test
    public void testChangePersonRoleStatusForNotFoundInDB() {
        String excepMessage = "";
        ChangePersonRoleStatusRequest req = new ChangePersonRoleStatusRequest();
        req.setPersonRoleID(999999888888777777l); // Id not present in the DB
        req.setRoleType(RoleType.CLINICAL_RESEARCH_STAFF);
        req.setStatus(EntityStatus.INACTIVE);
        try {
            personService.changePersonRoleStatus(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Couldn't update the PersonRole Status for personRoleID 999999888888777777 as it is not found in the DB"));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-null Roletype
     */
    @Test
    public void testChangePersonRoleStatusForNullRoleType() {
        // create OC first
        OrganizationalContact oc = getOrganizationalContactObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(oc);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();
        Assert.assertEquals(EntityStatus.ACTIVE.value(), perRole.getStatus().value());

        // now update the status but RoleType is null
        String excepMessage = "";
        ChangePersonRoleStatusRequest req = new ChangePersonRoleStatusRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(null);
        req.setStatus(EntityStatus.INACTIVE);
        try {
            personService.changePersonRoleStatus(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{roleType}' is expected"));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-HCP - invalid
     * transition
     */
    @Test
    public void testChangePersonRoleStatusHCPForInvalidTransition() {
        String excepMessage = null;
        // create HCP first
        HealthCareProvider hcp = getHealthCareProviderObj();
        CreatePersonRoleRequest request = new CreatePersonRoleRequest();
        request.setPersonRole(hcp);
        CreatePersonRoleResponse response = personService
                .createPersonRole(request);
        PersonRole perRole = response.getPersonRole();
        assertEquals(EntityStatus.ACTIVE.value(), perRole.getStatus().value());

        // now change the status to Pending
        ChangePersonRoleStatusRequest req = new ChangePersonRoleStatusRequest();
        req.setPersonRoleID(perRole.getId());
        req.setRoleType(RoleType.HEALTH_CARE_PROVIDER);
        req.setStatus(EntityStatus.PENDING); // invalid transition
        try {
            personService.changePersonRoleStatus(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Illegal curation transition from ACTIVE to PENDING"));
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    private Person createActivePerson() {
        // create a person first
        CreatePersonRequest cpRequest = new CreatePersonRequest();
        person.setStatus(EntityStatus.ACTIVE);
        cpRequest.setPerson(person);
        CreatePersonResponse cpResponse = personService.createPerson(cpRequest);
        return cpResponse.getPerson();
    }

    private HealthCareProvider getHealthCareProviderObj() {
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setLicense("license text");
        Person per = createActivePerson();
        hcp.setPersonId(per.getId());
        hcp.setOrganizationId(1l);
        hcp.setStatus(EntityStatus.ACTIVE);
        hcp.getAddress().add(getJaxbAddressList().get(0));
        hcp.getContact().addAll(getJaxbContactList());
        return hcp;
    }

    private ClinicalResearchStaff getClinicalResearchStaffObj() {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        Person per = createActivePerson();
        crs.setPersonId(per.getId());
        crs.setOrganizationId(1l);
        crs.setStatus(EntityStatus.ACTIVE);
        crs.getAddress().add(getJaxbAddressList().get(0));
        crs.getContact().addAll(getJaxbContactList());
        return crs;
    }

    private OrganizationalContact getOrganizationalContactObj() {
        OrganizationalContact oc = new OrganizationalContact();
        Person per = createActivePerson();
        oc.setPersonId(per.getId());
        oc.setOrganizationId(1l);
        oc.setStatus(EntityStatus.ACTIVE);
        oc.setType(OrganizationalContactType.IRB);
        oc.getAddress().add(getJaxbAddressList().get(0));
        oc.getContact().addAll(getJaxbContactList());
        return oc;
    }

}
