package gov.nih.nci.po.webservices.service.simple.rest;

import gov.nih.nci.coppa.test.TestUtils;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.webservices.service.simple.AbstractBaseTest;
import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareProvider;
import gov.nih.nci.po.webservices.types.OrganizationalContact;
import gov.nih.nci.po.webservices.types.OrganizationalContactType;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonList;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.PersonRoleList;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import gov.nih.nci.po.webservices.types.PersonSearchResultList;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This is an Integration test class for PersonService(REST).
 * 
 * @author Rohit Gupta
 * 
 */
public class PersonRESTServiceTest extends AbstractBaseTest {

    DefaultHttpClient httpClient = null;

    private static final QName PERSON_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "person");
    private final static QName PER_ROLE_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "personRole");

    String psUrl = null;
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TXT_PLAIN = "text/plain";

    private static final String SPECIAL_CHARS_STR = TestUtils
            .getUTF8TestString();

    @Before
    public void setUp() throws Exception {

        super.setUp();

        httpClient = new DefaultHttpClient();

        AuthScope authScope = new AuthScope(
                TstProperties.getServerHostname(),
                TstProperties.getServerPort()
        );

        Credentials credentials = new UsernamePasswordCredentials(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        httpClient.getCredentialsProvider().setCredentials(authScope, credentials);

        psUrl = TstProperties.getPersonRESTServiceURL();
    }

    @Test
    public void testCreatePersonWithoutAuthentication() throws Exception {
        AuthScope authScope = new AuthScope(
                TstProperties.getServerHostname(),
                TstProperties.getServerPort()
        );
        Credentials credentials = new UsernamePasswordCredentials("bogusUser","bogusPassword");

        httpClient.getCredentialsProvider().setCredentials(authScope, credentials);

        StringWriter writer = marshalPerson(person);
        String url = psUrl + "/person";

        // Define a postRequest request
        HttpPost postRequest = new HttpPost(url);

        // Set the API media type in http content-type header
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);

        // Set the request post body
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);

        // Send the request
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, getReponseCode(response));

    }

    /**
     * Testcase for PersonService-createPerson
     */
    @Test
    public void testCreatePerson() throws Exception {
        StringWriter writer = marshalPerson(person);
        String url = psUrl + "/person";

        // Define a postRequest request
        HttpPost postRequest = new HttpPost(url);

        // Set the API media type in http content-type header
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);

        // Set the request post body
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);

        // Send the request
        HttpResponse response = httpClient.execute(postRequest);

        // check the response code
        assertEquals(201, getReponseCode(response));

        // check the return content-type
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        // Now pull back the response object
        HttpEntity resEntity = response.getEntity();
        Person retPerson = unmarshalPerson(resEntity);

        // check for returned person
        checkPersonDetails(person, retPerson, false);
    }

    /**
     * Testcase for PersonService-createPerson- JSON format
     */
    @Test
    public void testCreatePerson_JSON() throws Exception {

        String url = psUrl + "/person";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");

        Person retPerson = mapper.readValue(perJSONStr, Person.class);

        // check for returned person
        checkPersonDetails(person, retPerson, false);
    }

    /**
     * Testcase for PersonService-createPerson-Person is NULL
     */
    @Test
    public void testCreatePersonNullPerson() throws Exception {
        String url = psUrl + "/person";
        StringWriter writer = marshalPerson(null);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));        
    }

    /**
     * Testcase for PersonService-createPerson-Person is NULL - JSON Format
     */
    @Test
    public void testCreatePersonNullPerson_JSON() throws Exception {
        String url = psUrl + "/person";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(null)); // person is null
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The Person couldn't be created as person is null"));
    }

    /**
     * Testcase for PersonService-createPerson-database ID is present
     */
    @Test
    public void testCreatePersonIdPresent() throws Exception {
        String url = psUrl + "/person";
        person.setId(999999888888777777l);
        StringWriter writer = marshalPerson(person);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "id must be null on calls to create"));
    }

    /**
     * Testcase for PersonService-createPerson-database ID is present - JSON
     * Format
     */
    @Test
    public void testCreatePersonIdPresent_JSON() throws Exception {
        String url = psUrl + "/person";
        person.setId(999999888888777777l);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "id must be null on calls to create"));
    }

    /**
     * Testcase for PersonService-createPerson-CTEP ID is present
     */
    @Test
    public void testCreatePersonCtepIdPresent() throws Exception {
        String url = psUrl + "/person";
        person.setCtepId("123456789");
        StringWriter writer = marshalPerson(person);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("Person couldn't be created as CTEP ID 123456789 is passed in the request."));
    }

    /**
     * Testcase for PersonService-createPerson-CTEP ID is present - JSON
     * Format
     */
    @Test
    public void testCreatePersonCtepIdPresent_JSON() throws Exception {
        String url = psUrl + "/person";
        person.setCtepId("123456789");

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("Person couldn't be created as CTEP ID 123456789 is passed in the request."));
    }
    
    /**
     * Testcase for PersonService-createPerson-Address not present
     */
    @Test
    public void testCreatePersonAddressNotPresentInRequest() throws Exception {
        String url = psUrl + "/person";
        person.setAddress(null);
        StringWriter writer = marshalPerson(person);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));        
    }

    /**
     * Testcase for PersonService-createPerson-Address not present - JSON Format
     */
    @Test
    public void testCreatePersonAddressNotPresentInRequest_JSON()
            throws Exception {
        String url = psUrl + "/person";
        person.setAddress(null);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Person couldn't be created as data is invalid. The invalid elements are: postalAddress"));
    }

    /**
     * Testcase for PersonService-createPerson-PhoneNumber is invalid
     */
    @Test
    public void testCreatePersonPhoneInvalid() throws Exception {
        String url = psUrl + "/person";
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        person.getContact().set(1, phoneContact); // set invalid phone number
        StringWriter writer = marshalPerson(person);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Person couldn't be created as data is invalid. The invalid elements are: phone[0].value"));
    }

    /**
     * Testcase for PersonService-createPerson-PhoneNumber is invalid - JSON
     * Format
     */
    @Test
    public void testCreatePersonPhoneInvalid_JSON() throws Exception {
        String url = psUrl + "/person";
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        person.getContact().set(1, phoneContact); // set invalid phone number

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Person couldn't be created as data is invalid. The invalid elements are: phone[0].value"));
    }

    /**
     * Testcase for PersonService-updatePerson
     */
    @Test
    public void testUpdatePerson() throws Exception {

        // create a person first
        Person createdPerson = createActivePerson();

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

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        StringWriter writer = marshalPerson(createdPerson);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        Person retPerson = unmarshalPerson(resEntity);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        checkPersonDetails(createdPerson, retPerson, true);
    }

    /**
     * Testcase for PersonService-updatePerson
     */
    @Test
    public void testUpdatePerson_JSON() throws Exception {

        // create a person first
        Person createdPerson = createActivePerson();

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

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(createdPerson));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        Person retPerson = mapper.readValue(perJSONStr, Person.class);
        checkPersonDetails(createdPerson, retPerson, true);
    }

    /**
     * Testcase for PersonService-updatePerson-IdNotPresentInRequest
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForIdNotPresentInRequestAndURL()
            throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");

        // now update the created person (HttpPut) -- don't set "Id"
        String url = psUrl + "/person";
        createdPerson.setId(null);
        StringWriter writer = marshalPerson(createdPerson);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(405, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-updatePerson-IdNotPresentInRequest - JSON
     * Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForIdNotPresentInRequestAndURL_JSON()
            throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");

        // now update the created person (HttpPut) -- don't set "Id"
        String url = psUrl + "/person";
        createdPerson.setId(null);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(createdPerson));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(405, getReponseCode(response));
    }
    
    /**
     * Testcase for PersonService-updatePerson-CtepId Present In Request
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForCtepIdPresentInRequest()
            throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        createdPerson.setCtepId("123456789");
        StringWriter writer = marshalPerson(createdPerson);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("couldn't be updated as CTEP ID 123456789 is passed in the request."));
    }

    /**
     * Testcase for PersonService-updatePerson-CtepId Present In Request - JSON
     * Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForCtepIdPresentInRequest_JSON()
            throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        createdPerson.setCtepId("123456789");
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(createdPerson));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("couldn't be updated as CTEP ID 123456789 is passed in the request."));
    }

    /**
     * Testcase for PersonService-updatePerson-Person is NULL
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForNullPerson() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        StringWriter writer = marshalPerson(null);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(400, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-updatePerson-Person is NULL - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForNullPerson_JSON() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now change some attributes of the newly created person
        createdPerson.setPrefix("Ms.");

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(null));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-updatePerson-Address is NULL
     */
    @Test
    public void testUpdatePersonForNullAddress() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now set person's address to NULL
        createdPerson.setAddress(null);

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        StringWriter writer = marshalPerson(createdPerson);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));        
    }

    /**
     * Testcase for PersonService-updatePerson-Address is NULL - JSON Format
     */
    @Test
    public void testUpdatePersonForNullAddress_JSON() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now set person's address to NULL
        createdPerson.setAddress(null);

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(createdPerson));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "PropertyValueException: not-null property references a null or transient value: gov.nih.nci.po.data.bo.Person.postalAddress"));
    }

    /**
     * Testcase for PersonService-updatePerson-Invalid Phone#
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForInvalidPhone() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now set invalid phone and try to update
        for (Contact contact : createdPerson.getContact()) {
            // set invalid phone number
            if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                contact.setValue("703@35@234");
            }
        }

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        StringWriter writer = marshalPerson(createdPerson);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "InvalidStateException: validation failed for: gov.nih.nci.po.data.bo.PhoneNumber"));
    }

    /**
     * Testcase for PersonService-updatePerson-Invalid Phone# - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForInvalidPhone_JSON() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        // now set invalid phone and try to update
        for (Contact contact : createdPerson.getContact()) {
            // set invalid phone number
            if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                contact.setValue("703@35@234");
            }
        }

        // now update the created person (HttpPut)
        String url = psUrl + "/person/" + createdPerson.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(createdPerson));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "InvalidStateException: validation failed for: gov.nih.nci.po.data.bo.PhoneNumber"));
    }

    /**
     * Testcase for PersonService-updatePerson-PersonNotFoundInDB
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForPersonNotFoundInDB() throws Exception {
        person.setId(999999888888777777l); // Id not present in the DB

        String url = psUrl + "/person/" + "999999888888777777";
        // now update the created person (HttpPut)
        StringWriter writer = marshalPerson(person);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-updatePerson-PersonNotFoundInDB - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdatePersonForPersonNotFoundInDB_JSON() throws Exception {
        person.setId(999999888888777777l); // Id not present in the DB

        String url = psUrl + "/person/" + "999999888888777777";
        // now update the created person (HttpPut)
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-changePersonStatus
     * 
     * @throws Exception
     */
    @Test
    public void testChangePersonStatus() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        String url = psUrl + "/person/" + createdPerson.getId() + "/status";

        // now update the status of the created person (HttpPut)
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("INACTIVE");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertEquals("INACTIVE", getResponseMessage(response));
    }

    /**
     * Testcase for PersonService-changePersonStatus-invalid transition
     * 
     * @throws Exception
     */
    @Test
    public void testChangePersonStatusInvalidTransition() throws Exception {
        // create an ACTIVE person first
        Person createdPerson = createActivePerson();

        String url = psUrl + "/person/" + createdPerson.getId() + "/status";

        // now update the status of the created person (HttpPut)
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("PENDING");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Illegal curation transition from ACTIVE to PENDING"));
    }

    /**
     * Testcase for PersonService-changePersonStatus-personId not present in DB
     */
    @Test
    public void testChangePersonStatusForPersonIdNotFoundInDB()
            throws Exception {
        String url = psUrl + "/person/" + "999999888888777777" + "/status";
        // now update the status of the person (HttpPut)
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("PENDING");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPerson
     * 
     * @throws Exception
     */
    @Test
    public void testGetPerson() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        String url = psUrl + "/person/" + createdPerson.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        Person retPerson = unmarshalPerson(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        checkPersonDetails(createdPerson, retPerson, false);
    }

    /**
     * Testcase for PersonService-getPerson - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testGetPerson_JSON() throws Exception {
        // create a person first
        Person createdPerson = createActivePerson();

        String url = psUrl + "/person/" + createdPerson.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        Person retPerson = mapper.readValue(perJSONStr, Person.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        checkPersonDetails(createdPerson, retPerson, false);
    }

    /**
     * Testcase for PersonService-getPerson- name having special chars
     */
    @Test
    public void testGetPersonHavingSpecialChars() throws Exception {
        // create a person having special chars in first name
        person.setFirstName(SPECIAL_CHARS_STR);
        StringWriter writer = marshalPerson(person);
        String cpUrl = psUrl + "/person";
        HttpPost postRequest = new HttpPost(cpUrl);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);

        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString(), "utf-8");
        postRequest.setEntity(personEntity);
        HttpResponse crtresponse = httpClient.execute(postRequest);
        Person createdPerson = unmarshalPerson(crtresponse.getEntity());

        String url = psUrl + "/person/" + createdPerson.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        Person retPerson = unmarshalPerson(response.getEntity());

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        checkPersonDetails(createdPerson, retPerson, false);
    }

    /**
     * Testcase for PersonService-getPerson- name having special chars - JSON
     * Format
     */
    @Test
    public void testGetPersonHavingSpecialChars_JSON() throws Exception {
        // create a person having special chars in first name
        person.setFirstName(SPECIAL_CHARS_STR);
        String cpUrl = psUrl + "/person";
        HttpPost postRequest = new HttpPost(cpUrl);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity personEntity = new StringEntity(
                mapper.writeValueAsString(person));
        postRequest.setEntity(personEntity);
        HttpResponse crtresponse = httpClient.execute(postRequest);
        HttpEntity resEntity = crtresponse.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        Person createdPerson = mapper.readValue(perJSONStr, Person.class);

        // now get the person
        String url = psUrl + "/person/" + createdPerson.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Person retPerson = mapper.readValue(str, Person.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        checkPersonDetails(createdPerson, retPerson, false);
    }

    /**
     * Testcase for PersonService-getPerson-PersonNotFound in DB
     */
    @Test
    public void testGetPersonForPersonNotFoundInDB() throws Exception {
        String url = psUrl + "/person/" + "999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPerson-PersonNotFound in DB - JSON Format
     */
    @Test
    public void testGetPersonForPersonNotFoundInDB_JSON() throws Exception {
        String url = psUrl + "/person/" + "999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPersonsByCtepId
     */
    @Test
    public void testGetPersonsByCtepId() throws Exception {
        String randomCtepId = RandomStringUtils.random(10, false, true);

        // create few person
        Person person1 = createActivePerson();

        Person person2 = createActivePerson();

        Person person3 = createActivePerson();
        
        // create their CTEP ID
        createIdentifiedPerson(person1.getId(), randomCtepId);
        createIdentifiedPerson(person2.getId(), randomCtepId);
        createIdentifiedPerson(person3.getId(), randomCtepId);

        // get person by ctepId
        String url = psUrl + "/person/ctep/" + randomCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonList retPerList = unmarshalPersonList(response.getEntity());
        assertTrue(retPerList.getPerson().size() == 3);
        for (Person person : retPerList.getPerson()) {
            assertEquals(randomCtepId, person.getCtepId());
        }
    }

    /**
     * Testcase for PersonService-getPersonsByCtepId - JSON Format
     */
    @Test
    public void testGetPersonsByCtepId_JSON() throws Exception {
        String randomCtepId = RandomStringUtils.random(10, false, true);

        // create few person
        Person person1 = createActivePerson();

        Person person2 = createActivePerson();

        Person person3 = createActivePerson();
        
        // create their CTEP ID
        createIdentifiedPerson(person1.getId(), randomCtepId);
        createIdentifiedPerson(person2.getId(), randomCtepId);
        createIdentifiedPerson(person3.getId(), randomCtepId);

        // get person by ctepId
        String url = psUrl + "/person/ctep/" + randomCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonList retPerList = mapper.readValue(perJSONStr, PersonList.class);

        assertTrue(retPerList.getPerson().size() == 3);
        for (Person person : retPerList.getPerson()) {
            assertEquals(randomCtepId, person.getCtepId());
        }
    }

    /**
     * Testcase for PersonService-getPersonsByCtepId- Not found in DB
     */
    @Test
    public void testGetPersonsByCtepIdForNotFoundInDB() throws Exception {
        String randomCtepId = RandomStringUtils.random(76, true, true);

        // get person by ctepId
        String url = psUrl + "/person/ctep/" + randomCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPersonsByCtepId- Not found in DB - JSON
     * Format
     */
    @Test
    public void testGetPersonsByCtepIdForNotFoundInDB_JSON() throws Exception {
        String randomCtepId = RandomStringUtils.random(76, true, true);

        // get person by ctepId
        String url = psUrl + "/person/ctep/" + randomCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName
     */
    @Test
    public void testSearchPersonsByFirstName() throws Exception {
        String randomFirstName = RandomStringUtils.random(40, true, true);

        // create a person
        person.setFirstName(randomFirstName);
        createActivePerson();

        // create another person for LIKE search
        person.setFirstName("Rohit_" + randomFirstName);
        createActivePerson();

        // create another person for LIKE search
        person.setFirstName(randomFirstName + " abc");
        createActivePerson();

        String url = psUrl + "/persons?firstName=" + randomFirstName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 3);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getFirstName().contains(randomFirstName));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName - JSON Format
     */
    @Test
    public void testSearchPersonsByFirstName_JSON() throws Exception {
        String randomFirstName = RandomStringUtils.random(40, true, true);

        // create a person
        person.setFirstName(randomFirstName);
        createActivePerson();

        // create another person for LIKE search
        person.setFirstName("Rohit_" + randomFirstName);
        createActivePerson();

        // create another person for LIKE search
        person.setFirstName(randomFirstName + " abc");
        createActivePerson();

        String url = psUrl + "/persons?firstName=" + randomFirstName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 3);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getFirstName().contains(randomFirstName));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName -- no matching
     * record found.
     */
    @Test
    public void testSearchPersonsByFirstNameNoMatchingRecordFound()
            throws Exception {
        String randomFirstName = RandomStringUtils.random(70, true, true);

        String url = psUrl + "/persons?firstName=" + randomFirstName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 0);

    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName -- no matching
     * record found -JSON Format.
     */
    @Test
    public void testSearchPersonsByFirstNameNoMatchingRecordFound_JSON()
            throws Exception {
        String randomFirstName = RandomStringUtils.random(70, true, true);

        String url = psUrl + "/persons?firstName=" + randomFirstName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 0);
    }

    /**
     * Testcase for PersonService-searchPersons- by FirstName having special
     * chars
     */
    @Test
    public void testSearchPersonsByFirstNameHavingSpecialChars()
            throws Exception {

        // create a person having special chars in first name
        person.setFirstName(SPECIAL_CHARS_STR);
        createActivePerson();

        // create another person
        createActivePerson();

        // create another person
        createActivePerson();

        String encodedFName = URLEncoder.encode(SPECIAL_CHARS_STR, "UTF-8");
        String url = psUrl + "/persons?firstName=" + encodedFName;

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() >= 3);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getFirstName().contains(SPECIAL_CHARS_STR));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by LastName/City
     */
    @Test
    public void testSearchPersonsByLastNameAndCity() throws Exception {
        String randomFirstName = RandomStringUtils.random(30, true, true);
        String randomLastName = RandomStringUtils.random(46, true, true);

        // create few persons
        person.setLastName("xyz_" + randomLastName);
        person.setFirstName(randomFirstName);
        createActivePerson();

        // create another person
        person.setLastName("abc_" + randomLastName);
        person.setFirstName(randomFirstName);
        createActivePerson();

        String url = psUrl + "/persons?lastName=" + randomLastName
                + "&city=Herndon";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getLastName().contains(randomLastName));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by LastName/City - JSON Format
     */
    @Test
    public void testSearchPersonsByLastNameAndCity_JSON() throws Exception {
        String randomFirstName = RandomStringUtils.random(30, true, true);
        String randomLastName = RandomStringUtils.random(46, true, true);

        // create few persons
        person.setLastName("xyz_" + randomLastName);
        person.setFirstName(randomFirstName);
        createActivePerson();

        // create another person
        person.setLastName("abc_" + randomLastName);
        person.setFirstName(randomFirstName);
        createActivePerson();

        String url = psUrl + "/persons?lastName=" + randomLastName
                + "&city=Herndon";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getLastName().contains(randomLastName));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Email
     */
    @Test
    public void testSearchPersonsByEmail() throws Exception {
        String randomEmail = RandomStringUtils.random(25, true, true);
        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue(randomEmail + "@mayoclinic.org");

        // create few persons
        person.getContact().set(0, emailContact);
        createActivePerson();

        createActivePerson();

        String url = psUrl + "/persons?email=" + randomEmail;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getEmailAddresses().contains(randomEmail));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Email - JSON Format
     */
    @Test
    public void testSearchPersonsByEmail_JSON() throws Exception {
        String randomEmail = RandomStringUtils.random(25, true, true);
        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue(randomEmail + "@mayoclinic.org");

        // create few persons
        person.getContact().set(0, emailContact);
        createActivePerson();

        createActivePerson();

        String url = psUrl + "/persons?email=" + randomEmail;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getEmailAddresses().contains(randomEmail));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by CtepId
     */
    @Test
    public void testSearchPersonsByCtepId() throws Exception {
        String randomCtepId = RandomStringUtils.random(10, false, true);
        // create few persons
        Person person1 = createActivePerson();

        Person person2 = createActivePerson();
        
        // create their CTEP ID
        createIdentifiedPerson(person1.getId(), randomCtepId);
        createIdentifiedPerson(person2.getId(), randomCtepId);        

        String url = psUrl + "/persons?ctepID=" + randomCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response.getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getCtepID().contains(randomCtepId));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by CtepId - JSON format
     */
    @Test
    public void testSearchPersonsByCtepId_JSON() throws Exception {
        String randomCtepId = RandomStringUtils.random(11, false, true);
        // create few persons
        Person person1 = createActivePerson();

        Person person2 = createActivePerson();
        
        // create their CTEP ID
        createIdentifiedPerson(person1.getId(), randomCtepId);
        createIdentifiedPerson(person2.getId(), randomCtepId);

        String url = psUrl + "/persons?ctepID=" + randomCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getCtepID().contains(randomCtepId));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by PO database Id
     */
    @Test
    public void testSearchPersonsByPOId() throws Exception {
        Person retPerson = createActivePerson();
        long poId = retPerson.getId();

        // search by PO database Id
        String url = psUrl + "/persons?id=" + poId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 1);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(poId == psr.getId());
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by PO database Id - JSON Format
     */
    @Test
    public void testSearchPersonsByPOId_JSON() throws Exception {
        Person retPerson = createActivePerson();
        long poId = retPerson.getId();

        // search by PO database Id
        String url = psUrl + "/persons?id=" + poId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 1);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(poId == psr.getId());
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Status
     */
    @Test
    public void testSearchPersonsByStatus() throws Exception {
        createActivePerson();

        // search by Status
        String url = psUrl + "/persons?statusCode=" + "ACTIVE" + "&limit=500";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() >= 1);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(EntityStatus.ACTIVE.value().equalsIgnoreCase(
                    psr.getStatusCode().value()));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Status - JSON Format
     */
    @Test
    public void testSearchPersonsByStatus_JSON() throws Exception {
        createActivePerson();

        // search by Status
        String url = psUrl + "/persons?statusCode=" + "ACTIVE" + "&limit=500";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() >= 1);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(EntityStatus.ACTIVE.value().equalsIgnoreCase(
                    psr.getStatusCode().value()));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Country
     */
    @Test
    public void testSearchPersonsByCountry() throws Exception {

        person.getAddress().setCountry(CountryISO31661Alpha3Code.FRA);

        // create few persons
        createActivePerson();

        createActivePerson();

        createActivePerson();

        createActivePerson();

        createActivePerson();

        // search by Country
        String url = psUrl + "/persons?countryCode=FRA";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() >= 5);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getCountryCode().name().equalsIgnoreCase("FRA"));// EXACT
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by Country - JSON Format
     */
    @Test
    public void testSearchPersonsByCountry_JSON() throws Exception {

        person.getAddress().setCountry(CountryISO31661Alpha3Code.FRA);

        // create few persons
        createActivePerson();

        createActivePerson();

        createActivePerson();

        createActivePerson();

        createActivePerson();

        // search by Country
        String url = psUrl + "/persons?countryCode=FRA";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() >= 5);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getCountryCode().name().equalsIgnoreCase("FRA"));// EXACT
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by AddressLine1
     */
    @Test
    public void testSearchPersonsByAddressLine1() throws Exception {
        String randomLine1 = RandomStringUtils.random(70, true, true);
        person.getAddress().setLine1(randomLine1);
        createActivePerson();

        // create another person
        person.getAddress().setLine1(randomLine1);
        createActivePerson();

        // search by AddressLine1
        String url = psUrl + "/persons?line1=" + randomLine1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getLine1().contains(randomLine1));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by AddressLine1 - JSON Format
     */
    @Test
    public void testSearchPersonsByAddressLine1_JSON() throws Exception {
        String randomLine1 = RandomStringUtils.random(70, true, true);
        person.getAddress().setLine1(randomLine1);
        createActivePerson();

        // create another person
        person.getAddress().setLine1(randomLine1);
        createActivePerson();

        // search by AddressLine1
        String url = psUrl + "/persons?line1=" + randomLine1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getLine1().contains(randomLine1));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by ZipCode
     */
    @Test
    public void testSearchPersonsByZipCode() throws Exception {
        String randomPostalCode = RandomStringUtils.random(10, false, true);
        // create few persons
        person.getAddress().setPostalcode(randomPostalCode);
        createActivePerson();

        // create another person
        person.getAddress().setPostalcode(randomPostalCode);
        createActivePerson();

        // search by ZipCode
        String url = psUrl + "/persons?postalcode=" + randomPostalCode;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getPostalcode().contains(randomPostalCode));
        }
    }

    /**
     * Testcase for PersonService-searchPersons- by ZipCode - JSON Format
     */
    @Test
    public void testSearchPersonsByZipCode_JSON() throws Exception {
        String randomPostalCode = RandomStringUtils.random(10, false, true);
        // create few persons
        person.getAddress().setPostalcode(randomPostalCode);
        createActivePerson();

        // create another person
        person.getAddress().setPostalcode(randomPostalCode);
        createActivePerson();

        // search by ZipCode
        String url = psUrl + "/persons?postalcode=" + randomPostalCode;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 2);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            assertTrue(psr.getPostalcode().contains(randomPostalCode));
        }
    }

    /**
     * Testcase for PersonService-searchPersons-for Offset & Limit
     */
    @Test
    public void testSearchPersonsForOffsetAndLimit() throws Exception {
        String randomLastName = RandomStringUtils.random(50, true, true);
        // all the below created person will have same last name
        person.setLastName(randomLastName);

        // create 1st person
        person.setFirstName("0000000000000000000000000");
        createActivePerson();

        // create 2nd person
        person.setFirstName("1111111111111111111111111");
        createActivePerson();

        // create 3rd person
        person.setFirstName("2222222222222222222222222");
        createActivePerson();

        // create 4th person
        person.setFirstName("3333333333333333333333333");
        createActivePerson();

        // create 5th person
        person.setFirstName("44444444444444444444444444");
        createActivePerson();

        // create 6th person
        person.setFirstName("5555555555555555555555555555");
        createActivePerson();

        // create 7th person
        person.setFirstName("66666666666666666666666666666");
        createActivePerson();

        String url = psUrl + "/persons?lastName=" + randomLastName
                + "&offset=2&limit=3";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonSearchResultList psrList = unmarshalPersonSearchResultList(response
                .getEntity());
        assertTrue(psrList.getPersonSearchResult().size() == 3);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            // check that serach was find using LastName
            assertTrue(psr.getLastName()
                    .equalsIgnoreCase(randomLastName));
        }
        for (int i = 0; i < psrList.getPersonSearchResult().size(); i++) {
            PersonSearchResult psr = psrList.getPersonSearchResult().get(i);
            if (i == 0) {
                assertTrue(psr.getFirstName().equalsIgnoreCase("2222222222222222222222222"));
            } else if (i == 1) {
                assertTrue(psr.getFirstName().equalsIgnoreCase("3333333333333333333333333"));
            } else if (i == 2) {
               assertTrue(psr.getFirstName().equalsIgnoreCase("44444444444444444444444444"));
            }
        }
    }

    /**
     * Testcase for PersonService-searchPersons-for Offset & Limit - JSON Format
     */
    @Test
    public void testSearchPersonsForOffsetAndLimit_JSON() throws Exception {
        String randomLastName = RandomStringUtils.random(50, true, true);
        // all the below created person will have same last name
        person.setLastName(randomLastName);

        // create 1st person
        person.setFirstName("0000000000000000000000000");
        createActivePerson();

        // create 2nd person
        person.setFirstName("1111111111111111111111111");
        createActivePerson();

        // create 3rd person
        person.setFirstName("2222222222222222222222222");
        createActivePerson();

        // create 4th person
        person.setFirstName("3333333333333333333333333");
        createActivePerson();

        // create 5th person
        person.setFirstName("44444444444444444444444444");
        createActivePerson();

        // create 6th person
        person.setFirstName("5555555555555555555555555555");
        createActivePerson();

        // create 7th person
        person.setFirstName("66666666666666666666666666666");
        createActivePerson();

        String url = psUrl + "/persons?lastName=" + randomLastName
                + "&offset=2&limit=3";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonSearchResultList psrList = mapper.readValue(perJSONStr,
                PersonSearchResultList.class);
        assertTrue(psrList.getPersonSearchResult().size() == 3);
        for (PersonSearchResult psr : psrList.getPersonSearchResult()) {
            // check that serach was find using LastName
            assertTrue(psr.getLastName()
                    .equalsIgnoreCase(randomLastName));
        }
        for (int i = 0; i < psrList.getPersonSearchResult().size(); i++) {
            PersonSearchResult psr = psrList.getPersonSearchResult().get(i);
            if (i == 0) {
                assertTrue(psr.getFirstName().equalsIgnoreCase("2222222222222222222222222"));
            } else if (i == 1) {
                assertTrue(psr.getFirstName().equalsIgnoreCase("3333333333333333333333333"));
            } else if (i == 2) {
                assertTrue(psr.getFirstName().equalsIgnoreCase("44444444444444444444444444"));
            }
        }
    }

    /**
     * Testcase for PersonService-searchPerson-Search Criteria is empty
     */
    @Test
    public void testSearchPersonCriteriaEmpty() throws Exception {
        String url = psUrl + "/persons";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "OneCriterionRequiredException: At least one criterion must be provided"));
    }

    /**
     * Testcase for PersonService-searchPerson-Search Criteria is empty - JSON
     * Format
     */
    @Test
    public void testSearchPersonCriteriaEmpty_JSON() throws Exception {
        String url = psUrl + "/persons";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "OneCriterionRequiredException: At least one criterion must be provided"));
    }

    /**
     * Testcase for PersonService-createPersonRole-HCP
     */
    @Test
    public void testCreatePersonRoleHCP() throws Exception {
        HealthCareProvider hcp = getHealthCareProviderObj();
        String url = psUrl + "/role";
        StringWriter writer = marshalPersonRole(hcp);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonRole perRole = unmarshalPersonRole(response.getEntity());
        assertTrue(perRole instanceof HealthCareProvider);
        assertNotNull(perRole.getId());
        checkPersonRoleAddressDetails(hcp, perRole);
        checkPersonRoleContactDetails(hcp, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-HCP - JSON Format
     */
    @Test
    public void testCreatePersonRoleHCP_JSON() throws Exception {
        HealthCareProvider hcp = getHealthCareProviderObj();
        String url = psUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(hcp));
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");

        PersonRole perRole = mapper.readValue(perJSONStr, PersonRole.class);
        assertTrue(perRole instanceof HealthCareProvider);
        assertNotNull(perRole.getId());
        checkPersonRoleAddressDetails(hcp, perRole);
        checkPersonRoleContactDetails(hcp, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-CRS
     */
    @Test
    public void testCreatePersonRoleCRS() throws Exception {
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        String url = psUrl + "/role";
        StringWriter writer = marshalPersonRole(crs);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonRole perRole = unmarshalPersonRole(response.getEntity());
        assertTrue(perRole instanceof ClinicalResearchStaff);
        assertNotNull(perRole.getId());
        checkPersonRoleAddressDetails(crs, perRole);
        checkPersonRoleContactDetails(crs, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-CRS - JSON Format
     */
    @Test
    public void testCreatePersonRoleCRS_JSON() throws Exception {
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        String url = psUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(crs));
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");

        PersonRole perRole = mapper.readValue(perJSONStr, PersonRole.class);
        assertTrue(perRole instanceof ClinicalResearchStaff);
        assertNotNull(perRole.getId());
        checkPersonRoleAddressDetails(crs, perRole);
        checkPersonRoleContactDetails(crs, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-OC
     */
    @Test
    public void testCreatePersonRoleOC() throws Exception {
        OrganizationalContact oc = getOrganizationalContactObj();
        String url = psUrl + "/role";
        StringWriter writer = marshalPersonRole(oc);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        PersonRole perRole = unmarshalPersonRole(response.getEntity());
        assertTrue(perRole instanceof OrganizationalContact);
        assertNotNull(perRole.getId());
        checkPersonRoleAddressDetails(oc, perRole);
        checkPersonRoleContactDetails(oc, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-OC - JSON Format
     */
    @Test
    public void testCreatePersonRoleOC_JSON() throws Exception {
        OrganizationalContact oc = getOrganizationalContactObj();
        String url = psUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(oc));
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");

        PersonRole perRole = mapper.readValue(perJSONStr, PersonRole.class);
        assertTrue(perRole instanceof OrganizationalContact);
        assertNotNull(perRole.getId());
        checkPersonRoleAddressDetails(oc, perRole);
        checkPersonRoleContactDetails(oc, perRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for PersonService-createPersonRole-Null PersonRole
     */
    @Test
    public void testCreatePersonRoleForNullPerRole() throws Exception {
        String url = psUrl + "/role";
        StringWriter writer = marshalPersonRole(null);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));        
    }

    /**
     * Testcase for PersonService-createPersonRole-Null PersonRole - JSON Format
     */
    @Test
    public void testCreatePersonRoleForNullPerRole_JSON() throws Exception {
        String url = psUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(null));
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The PersonRole couldn't be created as personRole is null"));
    }

    /**
     * Testcase for PersonService-createPersonRole-DB Id Present.
     */
    @Test
    public void testCreatePersonRoleForIdPresentInRequest() throws Exception {
        HealthCareProvider hcp = getHealthCareProviderObj();
        hcp.setId(11111111111l);
        String url = psUrl + "/role";
        StringWriter writer = marshalPersonRole(hcp);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "The PersonRole couldn't be created as personRoleId is present in the request"));
    }

    /**
     * Testcase for PersonService-createPersonRole-DB Id Present-JSON Format
     */
    @Test
    public void testCreatePersonRoleForIdPresentInRequest_JSON()
            throws Exception {
        HealthCareProvider hcp = getHealthCareProviderObj();
        hcp.setId(11111111111l);
        String url = psUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(hcp));
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "The PersonRole couldn't be created as personRoleId is present in the request"));
    }

    /**
     * Testcase for PersonService-updatePersonRole-HCP
     */
    @Test
    public void testUpdatePersonRoleHCP() throws Exception {
        // create HCP first
        HealthCareProvider hcp = (HealthCareProvider) createPerRole(getHealthCareProviderObj());// ACTIVE

        // now update the HCP details
        // update the status
        hcp.setStatus(EntityStatus.NULLIFIED);
        // update the address
        hcp.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        hcp.getContact().clear(); // clear existing
        hcp.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = psUrl + "/role/HealthCareProvider/" + hcp.getId();
        StringWriter writer = marshalPersonRole(hcp);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        PersonRole retPerRol = unmarshalPersonRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(retPerRol instanceof HealthCareProvider);

        // check for update Status
        checkHCPStatusInDB(retPerRol.getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(hcp, retPerRol);
        // check for the updated contact details
        checkPersonRoleContactDetails(hcp, retPerRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-HCP-JSON Format
     */
    @Test
    public void testUpdatePersonRoleHCP_JSON() throws Exception {
        // create HCP first
        HealthCareProvider hcp = (HealthCareProvider) createPerRole(getHealthCareProviderObj());// ACTIVE

        // now update the HCP details
        // update the status
        hcp.setStatus(EntityStatus.NULLIFIED);
        // update the address
        hcp.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        hcp.getContact().clear(); // clear existing
        hcp.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = psUrl + "/role/HealthCareProvider/" + hcp.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(hcp));
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        PersonRole retPerRol = mapper.readValue(perJSONStr, PersonRole.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(retPerRol instanceof HealthCareProvider);

        // check for update Status
        checkHCPStatusInDB(retPerRol.getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(hcp, retPerRol);
        // check for the updated contact details
        checkPersonRoleContactDetails(hcp, retPerRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-OC
     */
    @Test
    public void testUpdatePersonRoleOC() throws Exception {
        // create OrgContact first
        OrganizationalContact oc = (OrganizationalContact) createPerRole(getOrganizationalContactObj());// ACTIVE

        // now update the OrgContact details
        // update the status
        oc.setStatus(EntityStatus.NULLIFIED);
        // update the address
        oc.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        oc.getContact().clear(); // clear existing
        oc.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = psUrl + "/role/OrganizationalContact/" + oc.getId();
        StringWriter writer = marshalPersonRole(oc);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        PersonRole retPerRol = unmarshalPersonRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(retPerRol instanceof OrganizationalContact);

        // check for update Status
        checkOrgContactStatusInDB(retPerRol.getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(oc, retPerRol);
        // check for the updated contact details
        checkPersonRoleContactDetails(oc, retPerRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-OC-JSON Format
     */
    @Test
    public void testUpdatePersonRoleOC_JSON() throws Exception {
        // create OC first
        OrganizationalContact oc = (OrganizationalContact) createPerRole(getOrganizationalContactObj());// ACTIVE

        // now update the OrgContact details
        // update the status
        oc.setStatus(EntityStatus.NULLIFIED);
        // update the address
        oc.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        oc.getContact().clear(); // clear existing
        oc.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = psUrl + "/role/OrganizationalContact/" + oc.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(oc));
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        PersonRole retPerRol = mapper.readValue(perJSONStr, PersonRole.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(retPerRol instanceof OrganizationalContact);

        // check for update Status
        checkOrgContactStatusInDB(retPerRol.getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(oc, retPerRol);
        // check for the updated contact details
        checkPersonRoleContactDetails(oc, retPerRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-CRS
     */
    @Test
    public void testUpdatePersonRoleCRS() throws Exception {
        // create OrgContact first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());// ACTIVE

        // now update the OrgContact details
        // update the status
        crs.setStatus(EntityStatus.NULLIFIED);
        // update the address
        crs.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        crs.getContact().clear(); // clear existing
        crs.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId();
        StringWriter writer = marshalPersonRole(crs);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        PersonRole retPerRol = unmarshalPersonRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(retPerRol instanceof ClinicalResearchStaff);

        // check for update Status
        checkCRSStatusInDB(retPerRol.getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(crs, retPerRol);
        // check for the updated contact details
        checkPersonRoleContactDetails(crs, retPerRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-CRS-JSON Format
     */
    @Test
    public void testUpdatePersonRoleCRS_JSON() throws Exception {
        // create OC first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());// ACTIVE

        // now update the OrgContact details
        // update the status
        crs.setStatus(EntityStatus.NULLIFIED);
        // update the address
        crs.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        crs.getContact().clear(); // clear existing
        crs.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(crs));
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        PersonRole retPerRol = mapper.readValue(perJSONStr, PersonRole.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(retPerRol instanceof ClinicalResearchStaff);

        // check for update Status
        checkCRSStatusInDB(retPerRol.getId(), "NULLIFIED");
        // check for the updated address details
        checkPersonRoleAddressDetails(crs, retPerRol);
        // check for the updated contact details
        checkPersonRoleContactDetails(crs, retPerRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole not present in the
     * DB.
     */
    @Test
    public void testUpdatePersonRoleForPersonRoleNotFound() throws Exception {
        // create CRS first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());
        crs.setId(999999888888777777l);
        String url = psUrl + "/role/ClinicalResearchStaff/"
                + "999999888888777777";

        StringWriter writer = marshalPersonRole(crs);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "PersonRole is not found in the database"));
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole not present in the
     * DB - JSON Format
     */
    @Test
    public void testUpdatePersonRoleForPersonRoleNotFound_JSON()
            throws Exception {

        // create CRS first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());
        crs.setId(999999888888777777l);

        String url = psUrl + "/role/ClinicalResearchStaff/"
                + "999999888888777777";
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(crs));
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "PersonRole is not found in the database"));
    }

    /**
     * Testcase for PersonService-updatePersonRole-null PersonRole
     */
    @Test
    public void testUpdatePersonRoleForNullPersonRole() throws Exception {
        // create OrgContact first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());

        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId();
        StringWriter writer = marshalPersonRole(null);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(400, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-updatePersonRole-null PersonRole- JSON Format
     */
    @Test
    public void testUpdatePersonRoleForNullPersonRole_JSON() throws Exception {

        // create CRS first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());

        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(null));
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole ID not present in
     * the request
     */
    @Test
    public void testUpdatePersonRoleForRoleIdNotPresentInRequest()
            throws Exception {
        // create OrgContact first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());// ACTIVE

        crs.setId(null); // "Id" is null

        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId();

        StringWriter writer = marshalPersonRole(crs);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-updatePersonRole-PersonRole ID not present in
     * the request - JSON Format
     */
    @Test
    public void testUpdatePersonRoleForRoleIdNotPresentInRequest_JSON()
            throws Exception {

        // create CRS first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());
        crs.setId(null); // "Id" is null
        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity perRolEntity = new StringEntity(
                mapper.writeValueAsString(crs));
        putReq.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId
     */
    @Test
    public void testGetPersonRolesByPersonId() throws Exception {

        // create a person
        Person person = createActivePerson();

        // create a role-HCP for that person
        HealthCareProvider hcp = getHealthCareProviderObj();
        hcp.setPersonId(person.getId());
        createPerRole(hcp);

        // create a role-CRS for that person
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        crs.setPersonId(person.getId());
        createPerRole(crs);

        // create a role-OC for that person
        OrganizationalContact oc = getOrganizationalContactObj();
        oc.setPersonId(person.getId());
        createPerRole(oc);

        // get the Roles by the PersonId.
        String url = psUrl + "/person/" + person.getId() + "/roles";

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        PersonRoleList retPerRolList = unmarshalPerRoleList(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(retPerRolList.getPersonRole().size() == 3);
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId - JSON Format
     */
    @Test
    public void testGetPersonRolesByPersonId_JSON() throws Exception {

        // create a person
        Person person = createActivePerson();

        // create a role-HCP for that person
        HealthCareProvider hcp = getHealthCareProviderObj();
        hcp.setPersonId(person.getId());
        createPerRole(hcp);

        // create a role-CRS for that person
        ClinicalResearchStaff crs = getClinicalResearchStaffObj();
        crs.setPersonId(person.getId());
        createPerRole(crs);

        // create a role-OC for that person
        OrganizationalContact oc = getOrganizationalContactObj();
        oc.setPersonId(person.getId());
        createPerRole(oc);

        // get the Roles by the PersonId.
        String url = psUrl + "/person/" + person.getId() + "/roles";

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PersonRoleList retPerRolList = mapper.readValue(perRolJSONStr,
                PersonRoleList.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(retPerRolList.getPersonRole().size() == 3);
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId - Person found but
     * not having any roles.
     */
    @Test
    public void testGetPersonRolesByPersonIdFoundButNoRoleExist()
            throws Exception {

        // create a person
        Person person = createActivePerson();

        // person is NOT having any roles

        // get the Roles by the PersonId.
        String url = psUrl + "/person/" + person.getId() + "/roles";

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        PersonRoleList retPerRolList = unmarshalPerRoleList(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(retPerRolList.getPersonRole().size() == 0);
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId - Person found but
     * not having any roles- JSON Format
     */
    @Test
    public void testGetPersonRolesByPersonIdFoundButNoRoleExist_JSON()
            throws Exception {

        // create a person
        Person person = createActivePerson();

        // person is NOT having any roles

        // get the Roles by the PersonId.
        String url = psUrl + "/person/" + person.getId() + "/roles";

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");

        ObjectMapper mapper = new ObjectMapper();
        PersonRoleList retPerRolList = mapper.readValue(perJSONStr,
                PersonRoleList.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(retPerRolList.getPersonRole().size() == 0);
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId-personId not found in
     * DB.
     */
    @Test
    public void testGetPersonRolesByPersonIdNotFoundInDB() throws Exception {
        // get the Roles by the PersonId.
        String url = psUrl + "/person/" + "999999888888777777" + "/roles";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPersonRolesByPersonId-personId not found in
     * DB.
     */
    @Test
    public void testGetPersonRolesByPersonIdNotFoundInDB_JSON()
            throws Exception {

        // get the Roles by the PersonId.
        String url = psUrl + "/person/" + "999999888888777777" + "/roles";

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Person is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPersonRoleById-HCP
     */
    @Test
    public void testGetPersonRoleByIdHCP() throws Exception {
        // create a role-HCP
        HealthCareProvider hcp = (HealthCareProvider) createPerRole(getHealthCareProviderObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/HealthCareProvider/" + hcp.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();

        PersonRole retPerRol = unmarshalPersonRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals(hcp.getId(), retPerRol.getId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-HCP - JSON Format
     */
    @Test
    public void testGetPersonRoleByIdHCP_JSON() throws Exception {
        // create a role-HCP
        HealthCareProvider hcp = (HealthCareProvider) createPerRole(getHealthCareProviderObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/HealthCareProvider/" + hcp.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        HealthCareProvider retPerRol = mapper.readValue(perRolJSONStr,
                HealthCareProvider.class);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertEquals(hcp.getId(), retPerRol.getId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-OC
     */
    @Test
    public void testGetPersonRoleByIdOC() throws Exception {
        // create a role-OC
        OrganizationalContact oc = (OrganizationalContact) createPerRole(getOrganizationalContactObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/OrganizationalContact/" + oc.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();

        PersonRole retPerRol = unmarshalPersonRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals(oc.getId(), retPerRol.getId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-OC - JSON Format
     */
    @Test
    public void testGetPersonRoleByIdOC_JSON() throws Exception {
        // create a role-OC
        OrganizationalContact oc = (OrganizationalContact) createPerRole(getOrganizationalContactObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/OrganizationalContact/" + oc.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationalContact retPerRol = mapper.readValue(perRolJSONStr,
                OrganizationalContact.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertEquals(oc.getId(), retPerRol.getId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-CRS
     */
    @Test
    public void testGetPersonRoleByIdCRS() throws Exception {
        // create a role-CRS
        ClinicalResearchStaff oc = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/ClinicalResearchStaff/" + oc.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();

        PersonRole retPerRol = unmarshalPersonRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals(oc.getId(), retPerRol.getId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-CRS - JSON Format
     */
    @Test
    public void testGetPersonRoleByIdCRS_JSON() throws Exception {
        // create a role-CRS
        ClinicalResearchStaff oc = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/ClinicalResearchStaff/" + oc.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        ClinicalResearchStaff retPerRol = mapper.readValue(perRolJSONStr,
                ClinicalResearchStaff.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertEquals(oc.getId(), retPerRol.getId());
    }

    /**
     * Testcase for PersonService-getPersonRoleById-HCP Not found in DB
     */
    @Test
    public void testGetPersonRoleByIdHCPNotFoundInDB() throws Exception {
        // get the Role by the DB Id.
        String url = psUrl + "/role/HealthCareProvider/999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "PersonRole is not found in the database"));
    }

    /**
     * Testcase for PersonService-getPersonRoleById-HCP Not found in DB - JSON
     * Format
     */
    @Test
    public void testGetPersonRoleByIdHCPNotFoundInDB_JSON() throws Exception {

        // get the Role by the DB Id.
        String url = psUrl + "/role/HealthCareProvider/999999888888777777";

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "PersonRole is not found in the database"));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-HCP
     */
    @Test
    public void testChangePersonRoleStatusHCP() throws Exception {
        // create HCP first
        HealthCareProvider hcp = (HealthCareProvider) createPerRole(getHealthCareProviderObj());


        // get the Role by the DB Id.
        String url = psUrl + "/role/HealthCareProvider/" + hcp.getId()
                + "/status";

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("NULLIFIED");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertEquals("NULLIFIED", getResponseMessage(response));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-OC
     */
    @Test
    public void testChangePersonRoleStatusOC() throws Exception {
        // create OC first
        OrganizationalContact oc = (OrganizationalContact) createPerRole(getOrganizationalContactObj());


        // get the Role by the DB Id.
        String url = psUrl + "/role/OrganizationalContact/" + oc.getId()
                + "/status";

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("NULLIFIED");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertEquals("NULLIFIED", getResponseMessage(response));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-CRS
     */
    @Test
    public void testChangePersonRoleStatusCRS() throws Exception {
        // create CRS first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());


        // get the Role by the DB Id.
        String url = psUrl + "/role/ClinicalResearchStaff/" + crs.getId()
                + "/status";

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("NULLIFIED");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertEquals("NULLIFIED", getResponseMessage(response));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-CRS - not found in DB
     */
    @Test
    public void testChangePersonRoleStatusForNotFoundInDB() throws Exception {
        // create CRS first
        ClinicalResearchStaff crs = (ClinicalResearchStaff) createPerRole(getClinicalResearchStaffObj());

        String url = psUrl
                + "/role/ClinicalResearchStaff/999999888888777777/status";

        // change the status to InActive
        crs.setStatus(EntityStatus.INACTIVE);

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("INACTIVE");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "PersonRole is not found in the database"));
    }

    /**
     * Testcase for PersonService-changePersonRoleStatus-HCP - invalid
     * transition
     */
    @Test
    public void testChangePersonRoleStatusHCPForInvalidTransition()
            throws Exception {
        // create HCP first with ACTIVE status
        HealthCareProvider hcp = (HealthCareProvider) createPerRole(getHealthCareProviderObj());

        // get the Role by the DB Id.
        String url = psUrl + "/role/HealthCareProvider/" + hcp.getId()
                + "/status";

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        putReq.addHeader("Accept", TXT_PLAIN);
        StringEntity personEntity = new StringEntity("PENDING");
        putReq.setEntity(personEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Illegal curation transition from ACTIVE to PENDING"));
    }

    private Person createActivePerson() throws Exception {
        person.setStatus(EntityStatus.ACTIVE);
        StringWriter writer = marshalPerson(person);
        String url = psUrl + "/person";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity personEntity = new StringEntity(writer.getBuffer()
                .toString(), "utf-8");
        postRequest.setEntity(personEntity);
        HttpResponse response = httpClient.execute(postRequest);
        HttpEntity resEntity = response.getEntity();
        Person retPerson = unmarshalPerson(resEntity);
        return retPerson;
    }

    private PersonRole createPerRole(PersonRole perRole) throws Exception {
        String url = psUrl + "/role";
        StringWriter writer = marshalPersonRole(perRole);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity perRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(perRolEntity);
        HttpResponse response = httpClient.execute(postRequest);
        PersonRole retPerRol = unmarshalPersonRole(response.getEntity());
        return retPerRol;
    }

    private StringWriter marshalPerson(Person person) throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(new JAXBElement<Person>(PERSON_QNAME,
                Person.class, null, person), writer);

        return writer;
    }

    private Person unmarshalPerson(HttpEntity httpEntity) throws JAXBException,
            ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String personXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<Person> jaxbEle = (JAXBElement<Person>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(personXMLStr)),
                        Person.class);
        return jaxbEle.getValue();
    }

    private PersonList unmarshalPersonList(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PersonList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String personListXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<PersonList> jaxbEle = (JAXBElement<PersonList>) jaxbUnmarshaller
                .unmarshal(
                        new StreamSource(new StringReader(personListXMLStr)),
                        PersonList.class);
        return jaxbEle.getValue();
    }

    private PersonSearchResultList unmarshalPersonSearchResultList(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(PersonSearchResultList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String personXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<PersonSearchResultList> jaxbEle = (JAXBElement<PersonSearchResultList>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(personXMLStr)),
                        PersonSearchResultList.class);
        return jaxbEle.getValue();
    }

    private StringWriter marshalPersonRole(PersonRole perRole)
            throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(PersonRole.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(new JAXBElement<PersonRole>(PER_ROLE_QNAME,
                PersonRole.class, null, perRole), writer);
        return writer;
    }

    private PersonRole unmarshalPersonRole(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PersonRole.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String perRoleXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<PersonRole> jaxbEle = (JAXBElement<PersonRole>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(perRoleXMLStr)),
                        PersonRole.class);
        return jaxbEle.getValue();
    }

    private PersonRoleList unmarshalPerRoleList(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PersonRoleList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String perRoleListXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<PersonRoleList> jaxbEle = (JAXBElement<PersonRoleList>) jaxbUnmarshaller
                .unmarshal(
                        new StreamSource(new StringReader(perRoleListXMLStr)),
                        PersonRoleList.class);
        return jaxbEle.getValue();
    }

    private HealthCareProvider getHealthCareProviderObj() throws Exception {
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

    private ClinicalResearchStaff getClinicalResearchStaffObj()
            throws Exception {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        Person per = createActivePerson();
        crs.setPersonId(per.getId());
        crs.setOrganizationId(1l);
        crs.setStatus(EntityStatus.ACTIVE);
        crs.getAddress().add(getJaxbAddressList().get(0));
        crs.getContact().addAll(getJaxbContactList());
        return crs;
    }

    private OrganizationalContact getOrganizationalContactObj()
            throws Exception {
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

    @After
    public void tearDown() {
        httpClient.getConnectionManager().shutdown();
    }
}
