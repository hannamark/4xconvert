package gov.nih.nci.po.webservices.service.simple.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.TestUtils;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.webservices.service.simple.AbstractOrganizationServiceTest;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.FundingMechanism;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationRoleList;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.types.OrganizationSearchResultList;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.OversightCommitteeType;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.types.ResearchOrganizationType;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
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
import org.hibernate.SQLQuery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is an Integration test class for OrganizationService(REST).
 * 
 * @author Rohit Gupta
 * 
 */

public class OrganizationRESTServiceTest extends
        AbstractOrganizationServiceTest {

    DefaultHttpClient httpClient = null;

    private static final QName ORG_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "organization");
    private static final QName ORG_ROLE_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "organizationRole");

    String osUrl = null;
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

        osUrl = TstProperties.getOrgRESTServiceURL();
    }

    @Test
    public void testCreateOrganizationWithoutAuthentication() throws Exception {
        AuthScope authScope = new AuthScope(
                TstProperties.getServerHostname(),
                TstProperties.getServerPort()
        );
        Credentials credentials = new UsernamePasswordCredentials("bogusUser","bogusPassword");

        httpClient.getCredentialsProvider().setCredentials(authScope, credentials);

        StringWriter writer = marshalOrganization(org);
        String url = osUrl + "/organization";

        // Define a postRequest request
        HttpPost postRequest = new HttpPost(url);

        // Set the API media type in http content-type header
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);

        // Set the request post body
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);

        // Send the request
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, getReponseCode(response));

    }

    /**
     * Testcase for OrganizationService-createOrganization
     */
    @Test
    public void testCreateOrganization() throws Exception {        
        StringWriter writer = marshalOrganization(org);
        String url = osUrl + "/organization";

        // Define a postRequest request
        HttpPost postRequest = new HttpPost(url);

        // Set the API media type in http content-type header
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);

        // Set the request post body
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);

        // Send the request
        HttpResponse response = httpClient.execute(postRequest);

        // check the response code
        assertEquals(201, getReponseCode(response));

        // check the return content-type
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        // Now pull back the response object
        HttpEntity resEntity = response.getEntity();
        Organization retOrg = unmarshalOrganization(resEntity);

        // check for returned organization
        Assert.assertNotNull(retOrg);
        Assert.assertNotNull(retOrg.getId());
        checkOrganizationDetails(org.getName(), org, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");        
    }

    /**
     * Testcase for OrganizationService-createOrganization-JSON Format
     */
    @Test
    public void testCreateOrganization_JSON() throws Exception {        
        String url = osUrl + "/organization";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String orgJSONStr = EntityUtils.toString(resEntity, "utf-8");

        Organization retOrg = mapper.readValue(orgJSONStr, Organization.class);

        // check for returned organization
        Assert.assertNotNull(retOrg);
        Assert.assertNotNull(retOrg.getId());
        checkOrganizationDetails(org.getName(), org, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");
    }
    
    /**
     * Testcase for OrganizationService-createOrganization - ACTIVE Status
     */
    @Test
    public void testCreateActiveOrganization() throws Exception {        
        org.setStatus(EntityStatus.ACTIVE);
        StringWriter writer = marshalOrganization(org);
        String url = osUrl + "/organization";

        // Define a postRequest request
        HttpPost postRequest = new HttpPost(url);

        // Set the API media type in http content-type header
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);

        // Set the request post body
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);

        // Send the request
        HttpResponse response = httpClient.execute(postRequest);

        // check the response code
        assertEquals(201, getReponseCode(response));

        // check the return content-type
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        // Now pull back the response object
        HttpEntity resEntity = response.getEntity();
        Organization retOrg = unmarshalOrganization(resEntity);

        // check for returned organization
        Assert.assertNotNull(retOrg);
        Assert.assertNotNull(retOrg.getId());
        checkOrganizationDetails(org.getName(), org, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");        
    }

    /**
     * Testcase for OrganizationService-createOrganization-Organization is Null
     */
    @Test
    public void testCreateNullOrganization() throws Exception {
        String url = osUrl + "/organization";
        StringWriter writer = marshalOrganization(null);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
    }

    /**
     * Testcase for OrganizationService-createOrganization-Organization is
     * Null-JSON Format
     */
    @Test
    public void testCreateNullOrganization_JSON() throws Exception {
        String url = osUrl + "/organization";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(null)); // Org is null
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The Organization couldn't be created as organization is null"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-database ID is
     * present
     */
    @Test
    public void testCreateOrganizationIdPresent() throws Exception {
        String url = osUrl + "/organization";
        org.setId(999999888888777777l);
        StringWriter writer = marshalOrganization(org);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "id must be null on calls to create"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-database ID is
     * present - JSON Format
     */
    @Test
    public void testCreateOrganizationIdPresent_JSON() throws Exception {
        org.setId(999999888888777777l); // Id is set/present
        String url = osUrl + "/organization";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "id must be null on calls to create"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-CTEP ID is
     * present
     */
    @Test
    public void testCreateOrganizationCtepIdPresent() throws Exception {
        String url = osUrl + "/organization";
        org.setCtepId("VA212");
        StringWriter writer = marshalOrganization(org);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("Organization couldn't be created as CTEP ID VA212 is passed in the request"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-CTEP ID is
     * present - JSON Format
     */
    @Test
    public void testCreateOrganizationCtepIdPresent_JSON() throws Exception {
        org.setCtepId("VA212");
        String url = osUrl + "/organization";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("Organization couldn't be created as CTEP ID VA212 is passed in the request"));
    }
    /**
     * Testcase for OrganizationService-createOrganization-Address not present
     */
    @Test
    public void testCreateOrganizationAddressNotPresent() throws Exception {
        String url = osUrl + "/organization";
        org.setAddress(null);
        StringWriter writer = marshalOrganization(org);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
    }

    /**
     * Testcase for OrganizationService-createOrganization-Address not present -
     * JSON Format
     */
    @Test
    public void testCreateOrganizationAddressNotPresent_JSON() throws Exception {
        org.setAddress(null); // address is null
        String url = osUrl + "/organization";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Organization couldn't be created as data is invalid. The invalid elements are: postalAddress"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-PhoneNumber is
     * invalid
     */
    @Test
    public void testCreateOrganizationPhoneInvalid() throws Exception {
        String url = osUrl + "/organization";
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        org.getContact().set(1, phoneContact); // set invalid phone number
        StringWriter writer = marshalOrganization(org);

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Organization couldn't be created as data is invalid. The invalid elements are: phone[0].value"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-PhoneNumber is
     * invalid - JSON Format
     */
    @Test
    public void testCreateOrganizationPhoneInvalid_JSON() throws Exception {
        String url = osUrl + "/organization";
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        org.getContact().set(1, phoneContact); // set invalid phone number

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Organization couldn't be created as data is invalid. The invalid elements are: phone[0].value"));
    }

    /**
     * Testcase for OrganizationService-updateOrganization
     */
    @Test
    public void testUpdateOrganization() throws Exception {

        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo");
        createdOrg.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdOrg.setAddress(getJaxbAddressList().get(1));
        // clear the existing contacts & set new one
        createdOrg.getContact().clear();
        createdOrg.getContact().addAll(getJaxbUpdatedContactList()); // updated      

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        Organization retUpOrg = unmarshalOrganization(resEntity);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        checkOrganizationDetails(org.getName(), createdOrg, retUpOrg,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganization - JSON Format
     */
    @Test
    public void testUpdateOrganization_JSON() throws Exception {

        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo");
        createdOrg.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdOrg.setAddress(getJaxbAddressList().get(1));
        // clear the existing contacts & set new one
        createdOrg.getContact().clear();
        createdOrg.getContact().addAll(getJaxbUpdatedContactList()); // updated

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        Organization retUpOrg = mapper
                .readValue(orgJSONStr, Organization.class);

        checkOrganizationDetails(org.getName(), createdOrg, retUpOrg,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganization - aliases
     */
    @Test
    public void testUpdateOrganizationAliases() throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change name of the organization
        createdOrg.setName("My Mayo 1111"); // will be added in Alias
        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse putRes = httpClient.execute(putReq);
        HttpEntity resEntity = putRes.getEntity();
        EntityUtils.consume(resEntity);
        // check for Alias
        url = osUrl + "/organizations?id=" + createdOrg.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);
        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(getResponse
                .getEntity());
        checkOrgAliases("My Mayo 1111", osrList.getOrganizationSearchResult()
                .get(0)); // check alias

        // now again change name of the organization
        createdOrg.setName("My Mayo 2222"); // will be added in Alias
        // now update the organization
        url = osUrl + "/organization/" + createdOrg.getId();
        writer = marshalOrganization(createdOrg);
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        putRes = httpClient.execute(putReq);
        resEntity = putRes.getEntity();
        EntityUtils.consume(resEntity);
        // check for Alias
        url = osUrl + "/organizations?id=" + createdOrg.getId();
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        getResponse = httpClient.execute(getReq);
        osrList = unmarshalOrganizationSearchResultList(getResponse.getEntity());
        Assert.assertTrue(osrList.getOrganizationSearchResult().get(0)
                .getAlias().size() == 2);
        checkOrgAliases("My Mayo 2222", osrList.getOrganizationSearchResult()
                .get(0)); // check alias

        // now pass the same name of the organization
        createdOrg.setName(org.getName()); // nothing added to alias
        // now update the organization
        url = osUrl + "/organization/" + createdOrg.getId();
        writer = marshalOrganization(createdOrg);
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        putRes = httpClient.execute(putReq);
        resEntity = putRes.getEntity();
        EntityUtils.consume(resEntity);
        // check for Alias
        url = osUrl + "/organizations?id=" + createdOrg.getId();
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        getResponse = httpClient.execute(getReq);
        osrList = unmarshalOrganizationSearchResultList(getResponse.getEntity());
        Assert.assertTrue(osrList.getOrganizationSearchResult().get(0)
                .getOrganizationName().equalsIgnoreCase(org.getName()));
        Assert.assertTrue(osrList.getOrganizationSearchResult().get(0)
                .getAlias().size() == 2);

    }

    /**
     * Testcase for OrganizationService-updateOrganization - aliases - JSON
     * Format
     */
    @Test
    public void testUpdateOrganizationAliases_JSON() throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change name of the organization
        createdOrg.setName("My Mayo 1111"); // will be added in Alias
        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse putRes = httpClient.execute(putReq);
        HttpEntity resEntity = putRes.getEntity();
        EntityUtils.consume(resEntity);
        // check for Alias
        url = osUrl + "/organizations?id=" + createdOrg.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);
        resEntity = getResponse.getEntity();
        String osrJSONStr = EntityUtils.toString(resEntity, "utf-8");
        OrganizationSearchResultList osrList = mapper.readValue(osrJSONStr,
                OrganizationSearchResultList.class);
        checkOrgAliases("My Mayo 1111", osrList.getOrganizationSearchResult()
                .get(0)); // check alias

        // now again change name of the organization
        createdOrg.setName("My Mayo 2222"); // will be added in Alias
        // now update the organization
        url = osUrl + "/organization/" + createdOrg.getId();
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        mapper = new ObjectMapper();
        orgEntity = new StringEntity(mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        putRes = httpClient.execute(putReq);
        resEntity = putRes.getEntity();
        EntityUtils.consume(resEntity);
        // check for Alias
        url = osUrl + "/organizations?id=" + createdOrg.getId();
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        getResponse = httpClient.execute(getReq);

        osrJSONStr = EntityUtils.toString(getResponse.getEntity(), "utf-8");
        osrList = mapper.readValue(osrJSONStr,
                OrganizationSearchResultList.class);
        Assert.assertTrue(osrList.getOrganizationSearchResult().get(0)
                .getAlias().size() == 2);
        checkOrgAliases("My Mayo 2222", osrList.getOrganizationSearchResult()
                .get(0)); // check alias

        // now pass the same name of the organization
        createdOrg.setName(org.getName()); // nothing added to alias
        // now update the organization
        url = osUrl + "/organization/" + createdOrg.getId();
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);
        mapper = new ObjectMapper();
        orgEntity = new StringEntity(mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        putRes = httpClient.execute(putReq);
        resEntity = putRes.getEntity();
        EntityUtils.consume(resEntity);
        // check for Alias
        url = osUrl + "/organizations?id=" + createdOrg.getId();
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        getResponse = httpClient.execute(getReq);
        osrJSONStr = EntityUtils.toString(getResponse.getEntity(), "utf-8");
        osrList = mapper.readValue(osrJSONStr,
                OrganizationSearchResultList.class);
        Assert.assertTrue(osrList.getOrganizationSearchResult().get(0)
                .getOrganizationName().equalsIgnoreCase(org.getName()));
        Assert.assertTrue(osrList.getOrganizationSearchResult().get(0)
                .getAlias().size() == 2);

    }
    
    /**
     * Testcase for OrganizationService-updateOrganization- create a Change Request
     */
    @Test
    public void testUpdateOrganization_create_ChangeRequest() throws Exception {

        // create a organization first
        Organization createdOrg = createActiveOrganization();
        
        // set Overridden by CTRPQATester1
        updateOverriddenBy(Organization.class, createdOrg.getId());

        // now change some attributes of the newly created organization
        String orgUpName ="My Mayo 123";
        createdOrg.setName(orgUpName);
        createdOrg.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdOrg.setAddress(getJaxbAddressList().get(1));
        // clear the existing contacts & set new one
        createdOrg.getContact().clear();
        createdOrg.getContact().addAll(getJaxbUpdatedContactList()); // updated       

        // now update the created organization, it should create a Change Request
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);
        EntityUtils.consume(response.getEntity());
        checkOrganizationCRDetails(orgUpName, createdOrg, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganization -create a Change Request- JSON Format
     */
    @Test
    public void testUpdateOrganization_create_ChangeRequest_JSON() throws Exception {

        // create a organization first
        Organization createdOrg = createActiveOrganization();
        
        // set Overridden by CTRPQATester1
        updateOverriddenBy(Organization.class, createdOrg.getId());

        // now change some attributes of the newly created organization
        String orgUpName ="My Mayo 123";
        createdOrg.setName(orgUpName);
        createdOrg.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdOrg.setAddress(getJaxbAddressList().get(1));
        // clear the existing contacts & set new one
        createdOrg.getContact().clear();
        createdOrg.getContact().addAll(getJaxbUpdatedContactList()); // updated

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);
        EntityUtils.consume(response.getEntity());

        checkOrganizationCRDetails(orgUpName, createdOrg, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganization-IdNotPresentInRequest
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForIdNotPresentInRequestAndURL()
            throws Exception {
        // create a Organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("updated name");

        // now update the created organization (HttpPut) -- don't set "Id"
        String url = osUrl + "/organization";
        createdOrg.setId(null);
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(405, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-IdNotPresentInRequest
     * - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForIdNotPresentInRequestAndURL_JSON()
            throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo");

        // now update the created organization (HttpPut) -- don't set "Id"
        String url = osUrl + "/organization";
        createdOrg.setId(null);

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(405, getReponseCode(response));
    }
    
    /**
     * Testcase for OrganizationService-updateOrganization-CTEP Id Present In Request
     * 
     * @throws Exception
     */
    
    @Test
    public void testUpdateOrganizationCtepIdPresent()
            throws Exception {
        // create a Organization first
        Organization createdOrg = createActiveOrganization();
        createdOrg.setName("updated name");
        createdOrg.setCtepId("MD212");

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("Organization couldn't be updated as CTEP ID MD212 is passed in the request"));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-CTEP Id Present In Request
     * - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationCtepIdPresent_JSON()
            throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo");
        createdOrg.setCtepId("MD212");

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains("Organization couldn't be updated as CTEP ID MD212 is passed in the request"));
    }
    

    /**
     * Testcase for OrganizationService-updateOrganization-organization is NULL
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForNullOrganization() throws Exception {
        // create a Organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("updated name");

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(null);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(400, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-organization is NULL
     * - JSON format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForNullOrganization_JSON()
            throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo");

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(null)); // Org is null
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Address is NULL
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForNullAddress() throws Exception {
        // create a Organization first
        Organization createdOrg = createActiveOrganization();

        // now set Org's address to NULL
        createdOrg.setAddress(null);

        // now update the created Organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Address is NULL -
     * JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForNullAddress_JSON() throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        // address is updated - set null
        createdOrg.setAddress(null);

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains("gov.nih.nci.po.service.EntityValidationException The invalid elements are: postalAddress "));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Invalid Phone#
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForInvalidPhone() throws Exception {
        // create a Organization first
        Organization createdOrg = createActiveOrganization();

        // now set invalid phone and try to update
        for (Contact contact : createdOrg.getContact()) {
            // set invalid phone number
            if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                contact.setValue("703@35@234");
            }
        }

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();
        StringWriter writer = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "gov.nih.nci.po.service.EntityValidationException The invalid elements are: phone[0].value Phone number 703@35@234 "));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-Invalid Phone# - JSON
     * Format
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOrganizationForInvalidPhone_JSON() throws Exception {
        // create a Organization first
        Organization createdOrg = createActiveOrganization();

        // now set invalid phone and try to update
        for (Contact contact : createdOrg.getContact()) {
            // set invalid phone number
            if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                contact.setValue("703@35@234");
            }
        }

        // now update the created organization (HttpPut)
        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(createdOrg));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "gov.nih.nci.po.service.EntityValidationException The invalid elements are: phone[0].value Phone number 703@35@234 "));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-OrgNotFoundInDB
     */
    @Test
    public void testUpdateOrganizationForOrgNotFoundInDB() throws Exception {
        org.setId(999999888888777777l); // Id not present in the DB

        String url = osUrl + "/organization/" + "999999888888777777";

        StringWriter writer = marshalOrganization(org);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-OrgNotFoundInDB -
     * JSON Format
     */
    @Test
    public void testUpdateOrganizationForOrgNotFoundInDB_JSON()
            throws Exception {
        org.setId(999999888888777777l); // Id not present in the DB

        String url = osUrl + "/organization/" + "999999888888777777";

        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_JSON);
        putReq.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus
     */
    @Test
    public void testChangeOrganizationStatus() throws Exception {
        // create a organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organization/" + createdOrg.getId() + "/status";

        // now update the status of the created organization (HttpPut)
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("INACTIVE");
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertEquals("INACTIVE", getResponseMessage(response));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-OrgNotFoundInDB
     */
    @Test
    public void testChangeOrganizationStatusForOrgNotFoundInDB()
            throws Exception {
        String url = osUrl + "/organization/" + "999999888888777777"
                + "/status";
        // now update the status of the organization (HttpPut)
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("PENDING");
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationStatus-InvalidStatusTransition
     */
    @Test
    public void testChangeOrganizationStatusForInvalidTransition()
            throws Exception {
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organization/" + createdOrg.getId() + "/status";

        // now try to update the status to PENDING
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("PENDING");
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Illegal curation transition from ACTIVE to PENDING"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-InvalidStatus
     */
    @Test
    public void testChangeOrganizationStatusForInvalidStatus() throws Exception {
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organization/" + createdOrg.getId() + "/status";

        // now try to update the status to some invalid status
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("INVALID_STATUS_VALUE");
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains("Invalid status found"));
    }

    /**
     * Testcase for OrganizationService-getOrganization
     */
    @Test
    public void testGetOrganization() throws Exception {
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        Organization retOrg = unmarshalOrganization(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals(org.getName(), retOrg.getName());
        assertEquals(EntityStatus.ACTIVE.value(), retOrg.getStatus().value());
        assertEquals(createdOrg.getId(), retOrg.getId());
    }

    /**
     * Testcase for OrganizationService-getOrganization - JSON Format
     */
    @Test
    public void testGetOrganization_JSON() throws Exception {
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organization/" + createdOrg.getId();

        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        ObjectMapper mapper = new ObjectMapper();
        HttpEntity resEntity = response.getEntity();
        String orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        Organization retOrg = mapper.readValue(orgJSONStr, Organization.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertEquals(org.getName(), retOrg.getName());
        assertEquals(EntityStatus.ACTIVE.value(), retOrg.getStatus().value());
        assertEquals(createdOrg.getId(), retOrg.getId());
    }

    /**
     * Testcase for OrganizationService-getOrganization
     */
    @Test
    public void testGetOrganizationForOrgNotFound() throws Exception {
        String url = osUrl + "/organization/" + "999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-getOrganization - JSON Format
     */
    @Test
    public void testGetOrganizationForOrgNotFound_JSON() throws Exception {
        String url = osUrl + "/organization/" + "999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-createOrganization
     */
    @Test
    public void testGetOrganizationHavingSpecialChars() throws Exception {
        org.setName(SPECIAL_CHARS_STR);
        StringWriter writer = marshalOrganization(org);
        String url = osUrl + "/organization";

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(
                writer.getBuffer().toString(), "utf-8");
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);
        Organization createdOrg = unmarshalOrganization(response.getEntity());

        String getUrl = osUrl + "/organization/" + createdOrg.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);
        Organization retOrg = unmarshalOrganization(getResponse.getEntity());
        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_XML, getResponseContentType(getResponse));
        checkOrganizationDetails(createdOrg.getName(), createdOrg, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganization - JSON Format
     */
    @Test
    public void testGetOrganizationHavingSpecialChars_JSON() throws Exception {
        org.setName(SPECIAL_CHARS_STR);
        String url = osUrl + "/organization";

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgEntity = new StringEntity(
                mapper.writeValueAsString(org));
        postRequest.setEntity(orgEntity);
        HttpResponse crtresponse = httpClient.execute(postRequest);
        String orgJSONStr = EntityUtils.toString(crtresponse.getEntity(),
                "utf-8");
        Organization createdOrg = mapper.readValue(orgJSONStr,
                Organization.class);

        String getUrl = osUrl + "/organization/" + createdOrg.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);
        orgJSONStr = EntityUtils.toString(getResponse.getEntity(), "utf-8");
        Organization retOrg = mapper.readValue(orgJSONStr, Organization.class);
        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_JSON, getResponseContentType(getResponse));
        checkOrganizationDetails(createdOrg.getName(), createdOrg, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Name
     */
    @Test
    public void testSearchOrganizationsByOrgName() throws Exception {
        String randomOrgName = RandomStringUtils.random(60, true, true);
        org.setName(randomOrgName);

        // create few organizations
        createActiveOrganization();

        org.setName(randomOrgName + "abc"); // for LIKE search
        createActiveOrganization();

        org.setName("xyz" + randomOrgName); // for LIKE search
        createActiveOrganization();

        String url = osUrl + "/organizations?organizationName=" + randomOrgName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 3);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getOrganizationName().contains(randomOrgName));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Name - JSON
     * Format
     */
    @Test
    public void testSearchOrganizationsByOrgName_JSON() throws Exception {
        String randomOrgName = RandomStringUtils.random(80, true, true);
        org.setName(randomOrgName);

        // create few organizations
        createActiveOrganization();

        org.setName(randomOrgName + "abc"); // for LIKE search
        createActiveOrganization();

        org.setName("xyz" + randomOrgName); // for LIKE search
        createActiveOrganization();

        String url = osUrl + "/organizations?organizationName=" + randomOrgName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 3);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getOrganizationName().contains(randomOrgName));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Alias
     */
    @Test
    public void testSearchOrganizationsByOrgAlias() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // now create Aliases for this organization
        createOrgAliasesData(createdOrg.getId());

        // search by OrganizationID ( & check that Alias is present in result)
        String url = osUrl + "/organizations?id=" + createdOrg.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);
        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            // check that fetch Orgs have aliases
            assertTrue(osr.getId() == createdOrg.getId());
            assertTrue(osr.getAlias().get(0).getValue()
                    .equals("test org alias 1"));
        }

        // search by Organization Alias (flag is true)
        url = osUrl
                + "/organizations?organizationName=alias&searchAliases=true";
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(getReq);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        osrList = unmarshalOrganizationSearchResultList(response.getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() >= 1);
        boolean isOrgPresent = false;
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            if (osr.getId() == createdOrg.getId()) {
                isOrgPresent = true;
            }
        }
        if (isOrgPresent == false) {
            Assert.fail("Organization is not fetched using Alias.");
        }

        // search by Organization Alias (flag is false -- no result found)
        url = osUrl
                + "/organizations?organizationName=alias&searchAliases=false";
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(getReq);
        osrList = unmarshalOrganizationSearchResultList(response.getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 0);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Alias - JSON
     * Format
     */
    @Test
    public void testSearchOrganizationsByOrgAlias_JSON() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // now create Aliases for this organization
        createOrgAliasesData(createdOrg.getId());

        // search by OrganizationID ( & check that Alias is present in result)
        String url = osUrl + "/organizations?id=" + createdOrg.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);
        HttpEntity resEntity = response.getEntity();
        String orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(orgJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            // check that fetch Orgs have aliases
            assertTrue(osr.getId() == createdOrg.getId());
            assertTrue(osr.getAlias().get(0).getValue()
                    .equals("test org alias 1"));
        }

        // search by Organization Alias (flag is true)
        url = osUrl
                + "/organizations?organizationName=alias&searchAliases=true";
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        response = httpClient.execute(getReq);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        resEntity = response.getEntity();
        orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        mapper = new ObjectMapper();
        osrList = mapper.readValue(orgJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() >= 1);
        boolean isOrgPresent = false;
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            if (osr.getId() == createdOrg.getId()) {
                isOrgPresent = true;
            }
        }
        if (isOrgPresent == false) {
            Assert.fail("Organization is not fetched using Alias.");
        }

        // search by Organization Alias (flag is false -- no result found)
        url = osUrl
                + "/organizations?organizationName=alias&searchAliases=false";
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        response = httpClient.execute(getReq);
        resEntity = response.getEntity();
        orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        mapper = new ObjectMapper();
        osrList = mapper.readValue(orgJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 0);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Name -- no
     * matching record found.
     */
    @Test
    public void testSearchOrganizationsByNameNoMatchingRecordFound()
            throws Exception {
        String randomOrgName = RandomStringUtils.random(70, true, true);

        String url = osUrl + "/organizations?organizationName=" + randomOrgName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 0);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Name -- no
     * matching record found - JSON Format
     */
    @Test
    public void testSearchOrganizationsByNameNoMatchingRecordFound_JSON()
            throws Exception {
        String randomOrgName = RandomStringUtils.random(78, true, true);

        String url = osUrl + "/organizations?organizationName=" + randomOrgName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 0);

    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by FamilyName
     */
    @Test
    public void testSearchOrganizationsByFamilyName() throws Exception {
        String encodedFName = URLEncoder.encode("Cancer Center", "UTF-8");

        String url = osUrl + "/organizations?familyName=" + encodedFName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());

        if (CollectionUtils.isNotEmpty(osrList.getOrganizationSearchResult())) {
            for (OrganizationSearchResult osr : osrList
                    .getOrganizationSearchResult()) {
                assertTrue(osr.getFamilyName().contains("Cancer Center"));
            }
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by FamilyName -
     * JSON Format
     */
    @Test
    public void testSearchOrganizationsByFamilyName_JSON() throws Exception {
        String encodedFName = URLEncoder.encode("Cancer Center", "UTF-8");

        String url = osUrl + "/organizations?familyName=" + encodedFName;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);

        if (CollectionUtils.isNotEmpty(osrList.getOrganizationSearchResult())) {
            for (OrganizationSearchResult osr : osrList
                    .getOrganizationSearchResult()) {
                assertTrue(osr.getFamilyName().contains("Cancer Center"));
            }
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by PO database Id
     */
    @Test
    public void testSearchOrganizationsByPOId() throws Exception {
        String randomName = RandomStringUtils.random(59, true, true);
        org.setName(randomName);
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organizations?id=" + createdOrg.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertTrue(osr.getOrganizationName().equals(randomName));
        }
    }
    
    /**
     * Testcase for OrganizationService-searchOrganizations- by PO database Id -
     * JSON Format
     */
    @Test
    public void testSearchOrganizationsByPOId_JSON() throws Exception {
        String randomName = RandomStringUtils.random(58, true, true);
        org.setName(randomName);
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();

        String url = osUrl + "/organizations?id=" + createdOrg.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertTrue(osr.getOrganizationName().equals(randomName));
        }
    }
    
    /**
     * Testcase for OrganizationService-searchOrganizations- by CTEP Id
     */
    @Test
    public void testSearchOrganizationsByCTEPId() throws Exception {        
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();
                
        // create HCF and search the Org by that CTEP ID
        String hcfCtepId = RandomStringUtils.random(10, true, true);
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgRolEntity);        
        HttpResponse postRes = httpClient.execute(postRequest);
        OrganizationRole orgRole = unmarshalOrganizationRole(postRes.getEntity());
        
        // now set the CTEP ID for this HCF
        createHcfCtepId(orgRole.getId(), hcfCtepId);

        // search the Org using HCF CTEP ID
        url = osUrl + "/organizations?ctepID=" + hcfCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertEquals(osr.getHcfCtepID(), hcfCtepId);
        }
        
        
        // create RO with CETP ID and search the Org by that CTEP ID
        String roCtepId = RandomStringUtils.random(10, true, true);
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());
        url = osUrl + "/role";
        writer = marshalOrganizationRole(ro);
        postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        orgRolEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgRolEntity);        
        postRes = httpClient.execute(postRequest);
        orgRole = unmarshalOrganizationRole(postRes.getEntity());
        
        // now set the CTEP ID for this RO
        createROCtepId(orgRole.getId(), roCtepId);

        // search the Org using RO CTEP ID
        url = osUrl + "/organizations?ctepID=" + roCtepId;
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(getReq);
        osrList = unmarshalOrganizationSearchResultList(response.getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList.getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertEquals(osr.getRoCtepID(), roCtepId);
        }
        
        
        // create IdentifiedOrganization with CETP ID and search the Org by that CTEP ID
        String ioCtepId = RandomStringUtils.random(10, true, true);
        createIdentifiedOrganization(createdOrg.getId(), ioCtepId);
        
        // search the Org using IO CTEP ID
        url = osUrl + "/organizations?ctepID=" + ioCtepId;
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        response = httpClient.execute(getReq);
        osrList = unmarshalOrganizationSearchResultList(response.getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList.getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertEquals(osr.getOrgCtepId(), ioCtepId);
        }
    }
    
    /**
     * Testcase for OrganizationService-searchOrganizations- by CTEP ID -
     * JSON Format
     */
    @Test
    public void testSearchOrganizationsByCTEPId_JSON() throws Exception {
        // create an ACTIVE organization first
        Organization createdOrg = createActiveOrganization();
        
        // create HCF and search the Org by that CTEP ID
        String hcfCtepId = RandomStringUtils.random(10, true, true);
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(hcf));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest); 
        String rolJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");
        OrganizationRole orgRole = mapper.readValue(rolJSONStr,OrganizationRole.class);

        // now set the CTEP ID for this HCF
        createHcfCtepId(orgRole.getId(), hcfCtepId);

        // search the Org using HCF CTEP ID
        url = osUrl + "/organizations?ctepID=" + hcfCtepId;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        response = httpClient.execute(getReq);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(orgJSONStr,OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList.getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertEquals(osr.getHcfCtepID(), hcfCtepId);
        }
        
        // create RO and search the Org by that CTEP ID
        String roCtepId = RandomStringUtils.random(10, true, true);
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());
        url = osUrl + "/role";
        postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        mapper = new ObjectMapper();
        orgRolEntity = new StringEntity(mapper.writeValueAsString(ro));
        postRequest.setEntity(orgRolEntity);
        response = httpClient.execute(postRequest); 
        rolJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");
        orgRole = mapper.readValue(rolJSONStr,OrganizationRole.class);

        // now set the CTEP ID for this RO
        createROCtepId(orgRole.getId(), roCtepId);

        // search the Org using RO CTEP ID
        url = osUrl + "/organizations?ctepID=" + roCtepId;
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        response = httpClient.execute(getReq);
        resEntity = response.getEntity();
        orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        mapper = new ObjectMapper();
        osrList = mapper.readValue(orgJSONStr,OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList.getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertEquals(osr.getRoCtepID(), roCtepId);
        }
        
        
        // create IdentifiedOrganization with CETP ID and search the Org by that CTEP ID
        String ioCtepId = RandomStringUtils.random(56, true, true);
        createIdentifiedOrganization(createdOrg.getId(), ioCtepId);
        
        // search the Org using IO CTEP ID
        url = osUrl + "/organizations?ctepID=" + ioCtepId;
        getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        response = httpClient.execute(getReq);
        resEntity = response.getEntity();
        orgJSONStr = EntityUtils.toString(resEntity, "utf-8");
        mapper = new ObjectMapper();
        osrList = mapper.readValue(orgJSONStr,OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 1);

        for (OrganizationSearchResult osr : osrList.getOrganizationSearchResult()) {
            // check that the Organization is same as what we are expecting
            assertTrue(osr.getId() == createdOrg.getId());
            assertEquals(osr.getOrgCtepId(), ioCtepId);
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Status
     */
    @Test
    public void testSearchOrganizationsByStatus() throws Exception {
        createActiveOrganization();

        String getUrl = osUrl + "/organizations?statusCode=" + "ACTIVE";
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);
        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(getResponse
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() >= 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getStatusCode().value().equals("ACTIVE"));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Status - JSON
     * Format
     */
    @Test
    public void testSearchOrganizationsByStatus_JSON() throws Exception {
        createActiveOrganization();

        String getUrl = osUrl + "/organizations?statusCode=" + "ACTIVE";
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);
        HttpEntity resEntity = getResponse.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() >= 1);

        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getStatusCode().value().equals("ACTIVE"));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Country
     */
    @Test
    public void testSearchOrganizationsByCountry() throws Exception {
        // create few organizations with country as Japan
        org.getAddress().setCountry(CountryISO31661Alpha3Code.JPN);

        createActiveOrganization();

        createActiveOrganization();

        createActiveOrganization();

        createActiveOrganization();

        // now search by Country as Japan and limit the record to 2
        String url = osUrl + "/organizations?limit=2&countryCode=" + "JPN";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 2);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getCountryCode().value()
                    .equals(CountryISO31661Alpha3Code.JPN.value()));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Country - JSON
     * Format
     */
    @Test
    public void testSearchOrganizationsByCountry_JSON() throws Exception {
        // create few organizations with country as HTI
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HTI);

        createActiveOrganization();

        createActiveOrganization();

        createActiveOrganization();

        createActiveOrganization();

        // now search by Country as HTI and limit the record to 2
        String url = osUrl + "/organizations?limit=2&countryCode=" + "HTI";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 2);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getCountryCode().value()
                    .equals(CountryISO31661Alpha3Code.HTI.value()));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by AddressLine1
     */
    @Test
    public void testSearchOrganizationsByAddressLine1() throws Exception {
        String randomLine1 = RandomStringUtils.random(67, true, true);
        org.getAddress().setLine1(randomLine1);

        // create few organizations with this address line
        createActiveOrganization();

        org.getAddress().setLine1(randomLine1 + "asfcsdf"); // LIKE search
        createActiveOrganization();

        org.getAddress().setLine1("sdfsdf" + randomLine1); // LIKE search
        createActiveOrganization();

        // now search by address-line1
        String url = osUrl + "/organizations?line1=" + randomLine1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 3);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getLine1().contains(randomLine1));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by AddressLine1 -
     * JSON Format
     */
    @Test
    public void testSearchOrganizationsByAddressLine1_JSON() throws Exception {
        String randomLine1 = RandomStringUtils.random(68, true, true);
        org.getAddress().setLine1(randomLine1);

        // create few organizations with this address line
        createActiveOrganization();

        org.getAddress().setLine1(randomLine1 + "asfcsdf"); // LIKE search
        createActiveOrganization();

        org.getAddress().setLine1("sdfsdf" + randomLine1); // LIKE search
        createActiveOrganization();

        // now search by address-line1
        String url = osUrl + "/organizations?line1=" + randomLine1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 3);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getLine1().contains(randomLine1));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by AddressLine1 -
     * having special characters
     */
     @Test
    public void testSearchOrganizationsByAddressLine1HavingSpecialChars()
            throws Exception {
       
        org.getAddress().setLine1(SPECIAL_CHARS_STR);
        createActiveOrganization();
        createActiveOrganization();
        createActiveOrganization();

        // now search by address-line1
        // String encodedLine1 = URLEncoder.encode(SPECIAL_CHARS_STR, "UTF-8");
        String url = osUrl + "/organizations?line1=" + SPECIAL_CHARS_STR;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() >= 3);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getLine1().contains(SPECIAL_CHARS_STR));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by ZipCode
     */
    @Test
    public void testSearchOrganizationsByZipCode() throws Exception {
        String randomPostalCode = RandomStringUtils.random(10, false, true);
        org.getAddress().setPostalcode(randomPostalCode);
        createActiveOrganization();
        createActiveOrganization();
        createActiveOrganization();

        // now search by ZipCode
        String url = osUrl + "/organizations?postalcode=" + randomPostalCode;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() >= 3);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getPostalcode().contains(randomPostalCode));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by ZipCode - JSON
     * Format
     */
    @Test
    public void testSearchOrganizationsByZipCode_JSON() throws Exception {
        String randomPostalCode = RandomStringUtils.random(10, false, true);
        org.getAddress().setPostalcode(randomPostalCode);
        createActiveOrganization();
        createActiveOrganization();
        createActiveOrganization();

        // now search by ZipCode
        String url = osUrl + "/organizations?postalcode=" + randomPostalCode;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() >= 3);
        for (OrganizationSearchResult osr : osrList
                .getOrganizationSearchResult()) {
            assertTrue(osr.getPostalcode().contains(randomPostalCode));
        }
    }

    /**
     * Testcase OrganizationService-searchOrganizations-for Offset & Limit
     */
    @Test
    public void testSearchOrganizationsForOffsetAndLimit() throws Exception {
        // create few organizations with country as HongKong
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HKG);
        org.getAddress().setLine1("line1111111");
        org.setName("00000000000000000000000000000000000000");
        createActiveOrganization();

        // create 2nd organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HKG);
        org.setName("11111111111111111111111111111111111111");
        createActiveOrganization();

        // create 3rd organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HKG);
        org.setName("22222222222222222222222222222222222222");
        createActiveOrganization();

        // create 4th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HKG);
        org.setName("33333333333333333333333333333333333333");
        createActiveOrganization();

        // create 5th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HKG);
        org.setName("44444444444444444444444444444444444444");
        createActiveOrganization();

        // create 6th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.HKG);
        org.setName("55555555555555555555555555555555555555");
        createActiveOrganization();

        // search by Country with Offset & Limit
        // now search by ZipCode
        String url = osUrl
                + "/organizations?offset=2&limit=3&line1=line1111111&countryCode="
                + "HKG";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        OrganizationSearchResultList osrList = unmarshalOrganizationSearchResultList(response
                .getEntity());
        assertTrue(osrList.getOrganizationSearchResult().size() == 3);
        for (int i = 0; i < osrList.getOrganizationSearchResult().size(); i++) {
            OrganizationSearchResult osr = osrList
                    .getOrganizationSearchResult().get(i);
            if (i == 0) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "22222222222222222222222222222222222222"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.HKG.value()));
            } else if (i == 1) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "33333333333333333333333333333333333333"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.HKG.value()));
            } else if (i == 2) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "44444444444444444444444444444444444444"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.HKG.value()));
            }
        }
    }

    /**
     * Testcase OrganizationService-searchOrganizations-for Offset & Limit -
     * JSON Format
     */
    @Test
    public void testSearchOrganizationsForOffsetAndLimit_JSON()
            throws Exception {
        // create few organizations with country as CRI
        org.getAddress().setCountry(CountryISO31661Alpha3Code.CRI);
        org.getAddress().setLine1("line1111111");
        org.setName("00000000000000000000000000000000000000");
        createActiveOrganization();

        // create 2nd organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.CRI);
        org.setName("11111111111111111111111111111111111111");
        createActiveOrganization();

        // create 3rd organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.CRI);
        org.setName("22222222222222222222222222222222222222");
        createActiveOrganization();

        // create 4th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.CRI);
        org.setName("33333333333333333333333333333333333333");
        createActiveOrganization();

        // create 5th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.CRI);
        org.setName("44444444444444444444444444444444444444");
        createActiveOrganization();

        // create 6th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.CRI);
        org.setName("55555555555555555555555555555555555555");
        createActiveOrganization();

        // search by Country with Offset & Limit
        // now search by ZipCode
        String url = osUrl
                + "/organizations?offset=2&limit=3&line1=line1111111&countryCode="
                + "CRI";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        HttpEntity resEntity = response.getEntity();
        String perJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationSearchResultList osrList = mapper.readValue(perJSONStr,
                OrganizationSearchResultList.class);
        assertTrue(osrList.getOrganizationSearchResult().size() == 3);
        for (int i = 0; i < osrList.getOrganizationSearchResult().size(); i++) {
            OrganizationSearchResult osr = osrList
                    .getOrganizationSearchResult().get(i);
            if (i == 0) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "22222222222222222222222222222222222222"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.CRI.value()));
            } else if (i == 1) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "33333333333333333333333333333333333333"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.CRI.value()));
            } else if (i == 2) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "44444444444444444444444444444444444444"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.CRI.value()));
            }
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-Search Criteria is
     * empty
     */
    @Test
    public void testSearchOrganizationCriteriaEmpty() throws Exception {
        String url = osUrl + "/organizations";
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
     * Testcase for OrganizationService-searchOrganizations-Search Criteria is
     * empty - JSON Format
     */
    @Test
    public void testSearchOrganizationCriteriaEmpty_JSON() throws Exception {
        String url = osUrl + "/organizations";
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
     * Testcase for OrganizationService-createOrganizationRole-HCF
     */
    @Test
    public void testCreateOrganizationRoleHCF() throws Exception {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationRole orgRole = unmarshalOrganizationRole(response
                .getEntity());
        assertTrue(orgRole instanceof HealthCareFacility);
        assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.PENDING, orgRole.getStatus());
        // check the details in DB for HCF
        checkOrgRoleAddressDetails(hcf, orgRole);
        checkOrgRoleContactDetails(hcf, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-HCF - JSON Format
     */
    @Test
    public void testCreateOrganizationRoleHCF_JSON() throws Exception {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(hcf));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        String perJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");

        OrganizationRole orgRole = mapper.readValue(perJSONStr,
                OrganizationRole.class);
        assertTrue(orgRole instanceof HealthCareFacility);
        assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.PENDING, orgRole.getStatus());
        // check the details in DB for HCF
        checkOrgRoleAddressDetails(hcf, orgRole);
        checkOrgRoleContactDetails(hcf, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }
    
    

    /**
     * Testcase for OrganizationService-createOrganizationRole-OverComm
     */
    @Test
    public void testCreateOrganizationRoleOC() throws Exception {
        OversightCommittee oc = getOversightCommitteeObj();
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(oc);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        OrganizationRole orgRole = unmarshalOrganizationRole(response
                .getEntity());
        assertTrue(orgRole instanceof OversightCommittee);
        assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.ACTIVE, orgRole.getStatus());
        // check the details in DB for OC
        checkOrgRoleAddressDetails(oc, orgRole);
        checkOrgRoleContactDetails(oc, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OverComm - JSON
     * Format
     */
    @Test
    public void testCreateOrganizationRoleOC_JSON() throws Exception {
        OversightCommittee oc = getOversightCommitteeObj();
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(oc));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        String perJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");

        OrganizationRole orgRole = mapper.readValue(perJSONStr,
                OrganizationRole.class);
        assertTrue(orgRole instanceof OversightCommittee);
        assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.ACTIVE, orgRole.getStatus());
        // check the details in DB for OC
        checkOrgRoleAddressDetails(oc, orgRole);
        checkOrgRoleContactDetails(oc, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-RO
     */
    @Test
    public void testCreateOrganizationRoleRO() throws Exception {
        ResearchOrganization ro = getResearchOrganizationObj();
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(ro);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        ResearchOrganization orgRole = (ResearchOrganization) unmarshalOrganizationRole(response.getEntity());
        assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.PENDING, orgRole.getStatus());
        assertEquals("NWK", orgRole.getType().name());
        assertEquals(ro.getFundingMechanism().value(), orgRole.getFundingMechanism().value());
        // check the details in DB for OC
        checkOrgRoleAddressDetails(ro, orgRole);
        checkOrgRoleContactDetails(ro, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-RO - JSON Format
     */
    @Test
    public void testCreateOrganizationRoleRO_JSON() throws Exception {
        ResearchOrganization ro = getResearchOrganizationObj();
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(201, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        String perJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");

        ResearchOrganization orgRole = (ResearchOrganization) mapper.readValue(perJSONStr,OrganizationRole.class);
        assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.PENDING, orgRole.getStatus());
        assertEquals("NWK", orgRole.getType().name());
        assertEquals(ro.getFundingMechanism().value(), orgRole.getFundingMechanism().value());
        // check the details in DB for OC
        checkOrgRoleAddressDetails(ro, orgRole);
        checkOrgRoleContactDetails(ro, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole null
     */
    @Test
    public void testCreateOrganizationRoleForNullOrgRole() throws Exception {
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(null);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole null -
     * JSON Format
     */
    @Test
    public void testCreateOrganizationRoleForNullOrgRole_JSON()
            throws Exception {
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(null));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "The OrganizationRole couldn't be created as organizationRole is null"));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole ID
     * present in the request
     */
    @Test
    public void testCreateOrganizationRoleForRoleIdPresent() throws Exception {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setId(12345l);

        StringWriter writer = marshalOrganizationRole(hcf);
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);
        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "organizationRoleId is present in the request"));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole ID
     * present in the request - JSON Format
     */
    @Test
    public void testCreateOrganizationRoleForRoleIdPresent_JSON()
            throws Exception {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setId(12345l);
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(hcf));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "organizationRoleId is present in the request"));
    }

    
    /**
     * Testcase for OrganizationService-createOrganizationRole-HCF-CTEP ID
     * present in the request
     */
    @Test
    public void testCreateOrganizationRoleHCFForCtepIdPresent() throws Exception {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setCtepId("VA212");

        StringWriter writer = marshalOrganizationRole(hcf);
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);
        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be created as CTEP ID VA212 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-HCF-CTEP ID
     * present in the request - JSON Format
     */
    @Test
    public void testCreateOrganizationRoleHCFForCtepIdPresent_JSON()
            throws Exception {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setCtepId("VA212");
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(hcf));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be created as CTEP ID VA212 is passed in the request."));
    }
    
    /**
     * Testcase for OrganizationService-createOrganizationRole-RO-CTEP ID
     * present in the request
     */
    @Test
    public void testCreateOrganizationRoleROForCtepIdPresent() throws Exception {
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setCtepId("VA212");

        StringWriter writer = marshalOrganizationRole(ro);
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(writer.getBuffer().toString());
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);
        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be created as CTEP ID VA212 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-RO-CTEP ID
     * present in the request - JSON Format
     */
    @Test
    public void testCreateOrganizationRoleROForCtepIdPresent_JSON()
            throws Exception {
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setCtepId("VA212");
        String url = osUrl + "/role";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_JSON);
        postRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be created as CTEP ID VA212 is passed in the request."));
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF
     */
    @Test
    public void testUpdateOrganizationRoleHCF() throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());
        activateRole(hcf);

        // now update the HCF details
        hcf.setName("Mayo HCF 111"); // added to alias, name not change
        // update the status
        hcf.setStatus(EntityStatus.NULLIFIED);
        // update the address
        hcf.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        hcf.getContact().clear(); // clear existing
        hcf.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        HealthCareFacility retHCF = (HealthCareFacility) unmarshalOrganizationRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        // check for the updated address details in DB
        checkOrgRoleAddressDetails(hcf, retHCF);
        // check for the updated contact details
        checkOrgRoleContactDetails(hcf, retHCF,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias
    }

    private void activateRole(OrganizationRole role) throws SQLException {
        role.setStatus(EntityStatus.ACTIVE);

        String sql = String.format(
                "update %s set status='ACTIVE' where id=?",
                role.getClass().getSimpleName().toLowerCase()
        );

        if (conn == null) {
            conn = DataGeneratorUtil.getJDBCConnection();
        }

        PreparedStatement statement = null;

        try {
            statement = DataGeneratorUtil.getJDBCConnection().prepareStatement(sql);
            statement.setLong(1, role.getId());
            statement.executeUpdate();
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF - JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleHCF_JSON() throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());
        activateRole(hcf);

        // now update the HCF details
        hcf.setName("Mayo HCF 111"); // added to alias, name not change
        // update the status
        hcf.setStatus(EntityStatus.NULLIFIED);
        // update the address
        hcf.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        hcf.getContact().clear(); // clear existing
        hcf.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(hcf));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        String orgRolJSONStr = EntityUtils.toString(response.getEntity(),
                "utf-8");
        HealthCareFacility retHCF = (HealthCareFacility) mapper.readValue(
                orgRolJSONStr, OrganizationRole.class);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        // check for the updated address details in DB
        checkOrgRoleAddressDetails(hcf, retHCF);
        // check for the updated contact details
        checkOrgRoleContactDetails(hcf, retHCF,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OC
     */
    @Test
    public void testUpdateOrganizationRoleOC() throws Exception {
        // create OC first
        OversightCommittee oc = (OversightCommittee) createOrgRole(getOversightCommitteeObj());

        // now update the OC details
        // update the status
        oc.setStatus(EntityStatus.NULLIFIED);
        // update the address
        oc.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        oc.getContact().clear(); // clear existing
        oc.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = osUrl + "/role/OversightCommittee/" + oc.getId();
        StringWriter writer = marshalOrganizationRole(oc);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        OrganizationRole retOrgRol = unmarshalOrganizationRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        // check for the updated address details in DB
        checkOrgRoleAddressDetails(oc, retOrgRol);
        // check for the updated contact details
        checkOrgRoleContactDetails(oc, retOrgRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OC- JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleOC_JSON() throws Exception {
        // create OC first
        OversightCommittee oc = (OversightCommittee) createOrgRole(getOversightCommitteeObj());

        // now update the OC details
        // update the status
        oc.setStatus(EntityStatus.NULLIFIED);
        // update the address
        oc.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        oc.getContact().clear(); // clear existing
        oc.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = osUrl + "/role/OversightCommittee/" + oc.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(oc));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        String orgRolJSONStr = EntityUtils.toString(response.getEntity(),
                "utf-8");
        OrganizationRole retOrgRol = mapper.readValue(orgRolJSONStr,
                OrganizationRole.class);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        // check for the updated address details in DB
        checkOrgRoleAddressDetails(oc, retOrgRol);
        // check for the updated contact details
        checkOrgRoleContactDetails(oc, retOrgRol,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO
     */
    @Test
    public void testUpdateOrganizationRoleRO() throws Exception {
        // create OC first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        activateRole(ro);

        // now update the RO details
        ro.setName("Mayo RO 111"); // added to alias, name not change
        ro.setStatus(EntityStatus.NULLIFIED); // update the status
        // update the address
        ro.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        ro.getContact().clear(); // clear existing
        ro.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        ro.setType(ResearchOrganizationType.RSB);
        ro.setFundingMechanism(FundingMechanism.U_10);

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        StringWriter writer = marshalOrganizationRole(ro);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        ResearchOrganization retRO = (ResearchOrganization) unmarshalOrganizationRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        // check for the updated address details in DB
        checkOrgRoleAddressDetails(ro, retRO);
        // check for the updated contact details
        checkOrgRoleContactDetails(ro, retRO,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias
        assertEquals(ResearchOrganizationType.RSB.value(), retRO.getType().value());
        assertEquals("U10", retRO.getFundingMechanism().value());
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO - JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleRO_JSON() throws Exception {
        // create OC first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        activateRole(ro);
        // now update the OC details
        ro.setName("Mayo RO 111"); // added to alias, name not change
        // update the status
        ro.setStatus(EntityStatus.NULLIFIED);
        // update the address
        ro.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        ro.getContact().clear(); // clear existing
        ro.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        ro.setType(ResearchOrganizationType.RSB);
        ro.setFundingMechanism(FundingMechanism.U_10);

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        String orgRolJSONStr = EntityUtils.toString(response.getEntity(),
                "utf-8");
        ResearchOrganization retRO = (ResearchOrganization) mapper.readValue(
                orgRolJSONStr, OrganizationRole.class);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        // check for the updated address details in DB
        checkOrgRoleAddressDetails(ro, retRO);
        // check for the updated contact details
        checkOrgRoleContactDetails(ro, retRO,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias
        assertEquals(ResearchOrganizationType.RSB.value(), retRO.getType().value());
        assertEquals("U10", retRO.getFundingMechanism().value());
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole ID not
     * present in the request
     */
    @Test
    public void testUpdateOrganizationRoleForRoleIdNotPresent()
            throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());

        ro.setId(null); // "Id" is null

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();

        StringWriter writer = marshalOrganizationRole(ro);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole ID not
     * present in the request - JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleForRoleIdNotPresent_JSON()
            throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());

        ro.setId(null); // "Id" is null

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();

        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        assertEquals(404, getReponseCode(response));
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF-CTEP ID present in the request
     */
    @Test
    public void testUpdateOrganizationRoleHCFForCTEPIdPresent()
            throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());
        hcf.setCtepId("VA222");

        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be updated as CTEP ID VA222 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF-CTEP ID present in the request-JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleHCFForCTEPIdPresent_JSON()
            throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());
        hcf.setCtepId("VA222");

        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();

        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(mapper.writeValueAsString(hcf));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be updated as CTEP ID VA222 is passed in the request."));
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO-CTEP ID present in the request
     */
    @Test
    public void testUpdateOrganizationRoleROForCTEPIdPresent()
            throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        ro.setCtepId("VA222");

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();

        StringWriter writer = marshalOrganizationRole(ro);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be updated as CTEP ID VA222 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-CTEP ID present in the request-JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleROForCTEPIdPresent_JSON()
            throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        ro.setCtepId("VA222");

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();

        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        assertEquals(500, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "The OrganizationRole couldn't be updated as CTEP ID VA222 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrgRole null
     */
    @Test
    public void testUpdateOrganizationRoleForNullOrgRole() throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();

        StringWriter writer = marshalOrganizationRole(null); // OrgRole is NULL
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(400, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrgRole null -
     * JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleForNullOrgRole_JSON()
            throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();

        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(null)); // OrgRole is null
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        assertEquals(500, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrgRole NOT found
     * in DB
     */
    @Test
    public void testUpdateOrganizationRoleForOrgRoleNotFound() throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        ro.setId(999999888888777777l);

        String url = osUrl + "/role/ResearchOrganization/"
                + "999999888888777777";

        StringWriter writer = marshalOrganizationRole(ro);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrgRole NOT found
     * in DB - JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleForOrgRoleNotFound_JSON()
            throws Exception {
        // create RO first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        ro.setId(999999888888777777l);

        String url = osUrl + "/role/ResearchOrganization/"
                + "999999888888777777";

        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        assertEquals(404, getReponseCode(response));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-Aliases-HCF
     */
    @Test
    public void testUpdateOrganizationRoleAliasesHCF() throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());

        // now update the HCF name
        hcf.setName("Mayo HCF 111"); // added to alias, name not change
        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);
        HttpEntity resEntity = response.getEntity();
        HealthCareFacility retHCF = (HealthCareFacility) unmarshalOrganizationRole(resEntity);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias

        // now again change name of the HCF
        hcf.setName("Mayo HCF 222"); // added to alias, name not change
        url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        writer = marshalOrganizationRole(hcf);
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        orgRolEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgRolEntity);
        response = httpClient.execute(putReq);
        resEntity = response.getEntity();
        retHCF = (HealthCareFacility) unmarshalOrganizationRole(resEntity);
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias
        checkOrgRoleAliases("Mayo HCF 222", hcf); // check for alias

        // now again update HCF but sending same name
        hcf.setName("Mayo HCF"); // added to alias, name not change
        url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        writer = marshalOrganizationRole(hcf);
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        orgRolEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgRolEntity);
        response = httpClient.execute(putReq);
        resEntity = response.getEntity();
        retHCF = (HealthCareFacility) unmarshalOrganizationRole(resEntity);
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias
        checkOrgRoleAliases("Mayo HCF 222", hcf); // check for alias
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-Aliases-HCF -
     * JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleAliasesHCF_JSON() throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());

        // now update the HCF name
        hcf.setName("Mayo HCF 111"); // added to alias, name not change
        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(hcf));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);
        String orgRolJSONStr = EntityUtils.toString(response.getEntity(),
                "utf-8");
        HealthCareFacility retHCF = (HealthCareFacility) mapper.readValue(
                orgRolJSONStr, OrganizationRole.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias

        // now again change name of the HCF
        hcf.setName("Mayo HCF 222"); // added to alias, name not change
        url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);
        mapper = new ObjectMapper();
        orgRolEntity = new StringEntity(mapper.writeValueAsString(hcf));
        putRequest.setEntity(orgRolEntity);
        response = httpClient.execute(putRequest);
        orgRolJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");
        retHCF = (HealthCareFacility) mapper.readValue(orgRolJSONStr,
                OrganizationRole.class);
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias
        checkOrgRoleAliases("Mayo HCF 222", hcf); // check for alias

        // now again update HCF but sending same name
        hcf.setName("Mayo HCF"); // added to alias, name not change
        url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);
        mapper = new ObjectMapper();
        orgRolEntity = new StringEntity(mapper.writeValueAsString(hcf));
        putRequest.setEntity(orgRolEntity);
        response = httpClient.execute(putRequest);
        orgRolJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");
        retHCF = (HealthCareFacility) mapper.readValue(orgRolJSONStr,
                OrganizationRole.class);
        Assert.assertEquals("Mayo HCF", retHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", hcf); // check for alias
        checkOrgRoleAliases("Mayo HCF 222", hcf); // check for alias
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-create a Change Request-HCF
     */
    @Test
    public void testUpdateOrganizationRoleHCF_create_ChangeRequest() throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());
        activateRole(hcf);

        // set Overridden by CTRPQATester1
        updateOverriddenBy(HealthCareFacility.class, hcf.getId());
        
        // now update the HCF details
        String rolUpName ="Mayo HCF 111";
        hcf.setName(rolUpName); // CR should have this name
        // update the status
        hcf.setStatus(EntityStatus.INACTIVE);
        // update the address
        hcf.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        hcf.getContact().clear(); // clear existing
        hcf.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);        
        EntityUtils.consume(response.getEntity());
        
        checkHCFChangeRequestDetails(rolUpName, hcf, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org"); 
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF - create a Change Request- JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleHCF_create_ChangeRequest_JSON() throws Exception {
        // create HCF first
        HealthCareFacility hcf = (HealthCareFacility) createOrgRole(getHealthCareFacilityObj());
        activateRole(hcf);

        // set Overridden by CTRPQATester1
        updateOverriddenBy(HealthCareFacility.class, hcf.getId());

        // now update the HCF details
        String rolUpName ="Mayo HCF 111";
        hcf.setName(rolUpName); //  CR should have this name
        // update the status
        hcf.setStatus(EntityStatus.INACTIVE);
        // update the address
        hcf.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        hcf.getContact().clear(); // clear existing
        hcf.getContact().addAll(getJaxbUpdatedContactList());// UPDATED

        String url = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(mapper.writeValueAsString(hcf));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);
        EntityUtils.consume(response.getEntity());        
        
        checkHCFChangeRequestDetails(rolUpName, hcf, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org"); 
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-Aliases-RO
     */
    @Test
    public void testUpdateOrganizationRoleAliasesRO() throws Exception {
        // create OC first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());

        // now update the RO name
        ro.setName("Mayo RO 111"); // added to alias, name not change
        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        StringWriter writer = marshalOrganizationRole(ro);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);
        HttpEntity resEntity = response.getEntity();
        ResearchOrganization retRO = (ResearchOrganization) unmarshalOrganizationRole(resEntity);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias

        // now again change name of the RO
        ro.setName("Mayo RO 222"); // added to alias, name not change
        url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        writer = marshalOrganizationRole(ro);
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        orgRolEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgRolEntity);
        response = httpClient.execute(putReq);
        resEntity = response.getEntity();
        retRO = (ResearchOrganization) unmarshalOrganizationRole(resEntity);
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias
        checkOrgRoleAliases("Mayo RO 222", ro); // check for alias

        // now again update RO but sending same name
        ro.setName("Mayo RO"); // added to alias, name not change
        url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        writer = marshalOrganizationRole(ro);
        putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        orgRolEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgRolEntity);
        response = httpClient.execute(putReq);
        resEntity = response.getEntity();
        retRO = (ResearchOrganization) unmarshalOrganizationRole(resEntity);
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias
        checkOrgRoleAliases("Mayo RO 222", ro); // check for alias
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-Aliases-RO - JSON
     * Format
     */
    @Test
    public void testUpdateOrganizationRoleAliasesRO_JSON() throws Exception {
        // create OC first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());

        // now update the RO name
        ro.setName("Mayo RO 111"); // added to alias, name not change
        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        String orgRolJSONStr = EntityUtils.toString(response.getEntity(),
                "utf-8");
        ResearchOrganization retRO = (ResearchOrganization) mapper.readValue(
                orgRolJSONStr, OrganizationRole.class);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias

        // now again change name of the RO
        ro.setName("Mayo RO 222"); // added to alias, name not change
        url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);
        mapper = new ObjectMapper();
        orgRolEntity = new StringEntity(mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        response = httpClient.execute(putRequest);

        orgRolJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");
        retRO = (ResearchOrganization) mapper.readValue(orgRolJSONStr,
                OrganizationRole.class);
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias
        checkOrgRoleAliases("Mayo RO 222", ro); // check for alias

        // now again update RO but sending same name
        ro.setName("Mayo RO"); // added to alias, name not change
        url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);
        mapper = new ObjectMapper();
        orgRolEntity = new StringEntity(mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        response = httpClient.execute(putRequest);

        orgRolJSONStr = EntityUtils.toString(response.getEntity(), "utf-8");
        retRO = (ResearchOrganization) mapper.readValue(orgRolJSONStr,
                OrganizationRole.class);
        Assert.assertEquals("Mayo RO", retRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", ro); // check for alias
        checkOrgRoleAliases("Mayo RO 222", ro); // check for alias
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-create a Change Request-RO
     */
    @Test
    public void testUpdateOrganizationRoleRO_create_ChangeRequest() throws Exception {
        // create OC first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        activateRole(ro);
        // set Overridden by CTRPQATester1
        updateOverriddenBy(ResearchOrganization.class, ro.getId());

        // now update the RO details
        String rolUpName ="Mayo RO 111";
        ro.setName(rolUpName); // CR should have this name
        ro.setStatus(EntityStatus.INACTIVE); // update the status
        // update the address
        ro.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        ro.getContact().clear(); // clear existing
        ro.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        ro.setType(ResearchOrganizationType.RSB);
        ro.setFundingMechanism(FundingMechanism.U_10);

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        StringWriter writer = marshalOrganizationRole(ro);
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer().toString());
        putReq.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putReq);

        HttpEntity resEntity = response.getEntity();
        ResearchOrganization retRO = (ResearchOrganization) unmarshalOrganizationRole(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        assertEquals(ResearchOrganizationType.RSB.value(), retRO.getType().value());
        assertEquals("U10", retRO.getFundingMechanism().value());        
        checkROChangeRequestDetails(rolUpName, ro, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org"); 
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO -create a Change Request- JSON Format
     */
    @Test
    public void testUpdateOrganizationRoleRO_create_ChangeRequest_JSON() throws Exception {
        // create OC first
        ResearchOrganization ro = (ResearchOrganization) createOrgRole(getResearchOrganizationObj());
        activateRole(ro);

        // set Overridden by CTRPQATester1
        updateOverriddenBy(ResearchOrganization.class, ro.getId());

        // now update the OC details
        String rolUpName ="Mayo RO 111";
        ro.setName(rolUpName); // CR should have this name
        // update the status
        ro.setStatus(EntityStatus.INACTIVE);
        // update the address
        ro.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        ro.getContact().clear(); // clear existing
        ro.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        ro.setType(ResearchOrganizationType.RSB);
        ro.setFundingMechanism(FundingMechanism.U_10);

        String url = osUrl + "/role/ResearchOrganization/" + ro.getId();
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("content-type", APPLICATION_JSON);
        putRequest.addHeader("Accept", APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        StringEntity orgRolEntity = new StringEntity(
                mapper.writeValueAsString(ro));
        putRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(putRequest);

        String orgRolJSONStr = EntityUtils.toString(response.getEntity(),"utf-8");
        ResearchOrganization retRO = (ResearchOrganization) mapper.readValue(
                orgRolJSONStr, OrganizationRole.class);
        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertEquals(ResearchOrganizationType.RSB.value(), retRO.getType().value());
        assertEquals("U10", retRO.getFundingMechanism().value());
        checkROChangeRequestDetails(rolUpName, ro, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId
     */
    @Test
    public void testGetOrganizationRolesByOrgId() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create HCF for that Org
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        createOrgRole(hcf);

        // create OC for that Org
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(createdOrg.getId());
        createOrgRole(oc);

        // create RO for that Org
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());
        createOrgRole(ro);

        // now get the roles for this organization
        String getUrl = osUrl + "/organization/" + createdOrg.getId()
                + "/roles";

        HttpGet getReq = new HttpGet(getUrl);
        HttpResponse getResponse = httpClient.execute(getReq);
        OrganizationRoleList orgRoleList = unmarshalOrgRoleList(getResponse
                .getEntity());
        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_XML, getResponseContentType(getResponse));
        assertTrue(orgRoleList.getOrganizationRole().size() == 3);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId - JSON
     * Format
     */
    @Test
    public void testGetOrganizationRolesByOrgId_JSON() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create HCF for that Org
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        createOrgRole(hcf);

        // create OC for that Org
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(createdOrg.getId());
        createOrgRole(oc);

        // create RO for that Org
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());
        createOrgRole(ro);

        // now get the roles for this organization
        String getUrl = osUrl + "/organization/" + createdOrg.getId()
                + "/roles";

        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);
        HttpEntity resEntity = getResponse.getEntity();
        String orgRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OrganizationRoleList orgRoleList = mapper.readValue(orgRolJSONStr,
                OrganizationRoleList.class);
        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_JSON, getResponseContentType(getResponse));
        assertTrue(orgRoleList.getOrganizationRole().size() == 3);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId -- for Org
     * not found in DB
     */
    @Test
    public void testGetOrganizationRolesByOrgIdForOrgNotFoundInDB()
            throws Exception {
        String getUrl = osUrl + "/organization/" + "999999888888777777"
                + "/roles";

        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);
        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId -- for Org
     * not found in DB - JSON Format
     */
    @Test
    public void testGetOrganizationRolesByOrgIdForOrgNotFoundInDB_JSON()
            throws Exception {
        String getUrl = osUrl + "/organization/" + "999999888888777777"
                + "/roles";

        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);
        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Organization is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-HCF
     */
    @Test
    public void testGetOrganizationRoleByIdHCF() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create HCF for that Org
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        hcf = (HealthCareFacility) createOrgRole(hcf);

        // now get the role using Id
        String getUrl = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);
        HealthCareFacility retOrgRole = (HealthCareFacility) unmarshalOrganizationRole(getResponse
                .getEntity());

        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_XML, getResponseContentType(getResponse));
        assertEquals(hcf.getName(), retOrgRole.getName());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-HCF - JSON
     * Format
     */
    @Test
    public void testGetOrganizationRoleByIdHCF_JSON() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create HCF for that Org
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        hcf = (HealthCareFacility) createOrgRole(hcf);

        // now get the role using Id
        String getUrl = osUrl + "/role/HealthCareFacility/" + hcf.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);

        HttpEntity resEntity = getResponse.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        HealthCareFacility retOrgRole = mapper.readValue(perRolJSONStr,
                HealthCareFacility.class);

        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_JSON, getResponseContentType(getResponse));
        assertEquals(hcf.getName(), retOrgRole.getName());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-OC
     */
    @Test
    public void testGetOrganizationRoleByIdOC() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create OC for that Org
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(createdOrg.getId());
        oc = (OversightCommittee) createOrgRole(oc);

        // now get the role using Id
        String getUrl = osUrl + "/role/OversightCommittee/" + oc.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);
        OversightCommittee retOrgRole = (OversightCommittee) unmarshalOrganizationRole(getResponse
                .getEntity());

        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_XML, getResponseContentType(getResponse));
        assertTrue(oc.getId().longValue() == retOrgRole.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-OC - JSON Format
     */
    @Test
    public void testGetOrganizationRoleByIdOC_JSON() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create OC for that Org
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(createdOrg.getId());
        oc = (OversightCommittee) createOrgRole(oc);

        // now get the role using Id
        String getUrl = osUrl + "/role/OversightCommittee/" + oc.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);

        HttpEntity resEntity = getResponse.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        OversightCommittee retOrgRole = mapper.readValue(perRolJSONStr,
                OversightCommittee.class);

        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_JSON, getResponseContentType(getResponse));
        assertTrue(oc.getId().longValue() == retOrgRole.getId().longValue());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-RO
     */
    @Test
    public void testGetOrganizationRoleByIdRO() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create OC for that Org
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());
        ro = (ResearchOrganization) createOrgRole(ro);

        // now get the role using Id
        String getUrl = osUrl + "/role/ResearchOrganization/" + ro.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);
        ResearchOrganization retOrgRole = (ResearchOrganization) unmarshalOrganizationRole(getResponse
                .getEntity());

        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_XML, getResponseContentType(getResponse));
        assertEquals(ro.getName(), retOrgRole.getName());
        assertEquals(ro.getType().value(), retOrgRole.getType().value());
        assertEquals(ro.getFundingMechanism().value(), retOrgRole.getFundingMechanism().value());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-RO - JSON Format
     */
    @Test
    public void testGetOrganizationRoleByIdRO_JSON() throws Exception {
        // create an organization
        Organization createdOrg = createActiveOrganization();

        // create OC for that Org
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());
        ro = (ResearchOrganization) createOrgRole(ro);

        // now get the role using Id
        String getUrl = osUrl + "/role/ResearchOrganization/" + ro.getId();
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);

        HttpEntity resEntity = getResponse.getEntity();
        String perRolJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        ResearchOrganization retOrgRole = mapper.readValue(perRolJSONStr,
                ResearchOrganization.class);

        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_JSON, getResponseContentType(getResponse));
        assertEquals(ro.getName(), retOrgRole.getName());
        assertEquals(ro.getType().value(), retOrgRole.getType().value());
        assertEquals(ro.getFundingMechanism().value(), retOrgRole.getFundingMechanism().value());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-HCF
     */
    @Test
    public void testGetOrganizationRoleByIdForRoleNotFoundInDB()
            throws Exception {
        String getUrl = osUrl + "/role/HealthCareFacility/"
                + "999999888888777777";
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(getResponse));
        assertEquals(TXT_PLAIN, getResponseContentType(getResponse));
        assertTrue(getResponseMessage(getResponse).contains(
                "OrganizationRole is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-HCF-JSON Format
     */
    @Test
    public void testGetOrganizationRoleByIdForRoleNotFoundInDB_JSON()
            throws Exception {
        String getUrl = osUrl + "/role/HealthCareFacility/"
                + "999999888888777777";
        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse getResponse = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(getResponse));
        assertEquals(TXT_PLAIN, getResponseContentType(getResponse));
        assertTrue(getResponseMessage(getResponse).contains(
                "OrganizationRole is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByCtepId
     */
    @Test
    public void testGetOrganizationRolesByCtepId() throws Exception {

        Organization organization = createActiveOrganization();
        String randomCtepId = RandomStringUtils.random(10, true, true);

        // create a role-HCF 
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());
        hcf = (HealthCareFacility) createOrgRole(hcf);
        // now set the CTEP ID for this HCF
        createHcfCtepId(hcf.getId(), randomCtepId);

        // create a role-RO 
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());
        ro = (ResearchOrganization) createOrgRole(ro);
        // now set the CTEP ID for this RO
        createROCtepId(ro.getId(), randomCtepId);

        // get the Roles by the CtepId.
        String getUrl = osUrl + "/organization/ctep/" + randomCtepId;

        HttpGet getReq = new HttpGet(getUrl);
        HttpResponse getResponse = httpClient.execute(getReq);
        OrganizationRoleList orgRoleList = unmarshalOrgRoleList(getResponse
                .getEntity());
        assertEquals(200, getReponseCode(getResponse));
        assertEquals(APPLICATION_XML, getResponseContentType(getResponse));
        assertTrue(orgRoleList.getOrganizationRole().size() == 2);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByCtepId
     */
    @Test
    public void testGetOrganizationRolesByCtepIdForNotCtepIdNotFoundInDB()
            throws Exception {
        String randomCtepId = RandomStringUtils.random(78, true, true);

        String getUrl = osUrl + "/organization/ctep/" + randomCtepId;

        HttpGet getReq = new HttpGet(getUrl);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse getResponse = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(getResponse));
        assertEquals(TXT_PLAIN, getResponseContentType(getResponse));
        assertTrue(getResponseMessage(getResponse).contains(
                "OrganizationRole is not found in the database"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-HCF
     */
    @Test
    public void testChangeOrganizationRoleStatusHCF() throws Exception {
        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(hcf);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);
        OrganizationRole orgRole = unmarshalOrganizationRole(response
                .getEntity());

        // now change the status
        url = osUrl + "/role/HealthCareFacility/" + orgRole.getId() + "/status";
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("NULLIFIED");
        putReq.setEntity(orgEntity);
        HttpResponse putResponse = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(putResponse));
        assertEquals(TXT_PLAIN, getResponseContentType(putResponse));
        assertEquals("NULLIFIED", getResponseMessage(putResponse));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-OC
     */
    @Test
    public void testChangeOrganizationRoleStatusOC() throws Exception {
        // create a OC first
        OversightCommittee oc = getOversightCommitteeObj();
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(oc);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);
        OrganizationRole orgRole = unmarshalOrganizationRole(response
                .getEntity());

        // now change the status
        url = osUrl + "/role/OversightCommittee/" + orgRole.getId() + "/status";
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("NULLIFIED");
        putReq.setEntity(orgEntity);
        HttpResponse putResponse = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(putResponse));
        assertEquals(TXT_PLAIN, getResponseContentType(putResponse));
        assertEquals("NULLIFIED", getResponseMessage(putResponse));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-RO
     */
    @Test
    public void testChangeOrganizationRoleStatusRO() throws Exception {
        // create a OC first
        ResearchOrganization ro = getResearchOrganizationObj();
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(ro);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);
        OrganizationRole orgRole = unmarshalOrganizationRole(response
                .getEntity());

        // now change the status
        url = osUrl + "/role/ResearchOrganization/" + orgRole.getId()
                + "/status";
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("NULLIFIED");
        putReq.setEntity(orgEntity);
        HttpResponse putResponse = httpClient.execute(putReq);

        assertEquals(200, getReponseCode(putResponse));
        assertEquals(TXT_PLAIN, getResponseContentType(putResponse));
        assertEquals("NULLIFIED", getResponseMessage(putResponse));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-Role not found
     * in DB
     */
    @Test
    public void testChangeOrganizationRoleStatusForNotFoundInDB()
            throws Exception {
        String url = osUrl + "/role/HealthCareFacility/" + "999999888888777777"
                + "/status";
        HttpPut putReq = new HttpPut(url);
        putReq.addHeader("content-type", TXT_PLAIN);
        StringEntity orgEntity = new StringEntity("INACTIVE");
        putReq.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(putReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "OrganizationRole is not found in the database"));
    }

    @After
    public void tearDown() {
        httpClient.getConnectionManager().shutdown();
    }

    private Organization createActiveOrganization() throws Exception {
        StringWriter writer = marshalOrganization(org);
        String url = osUrl + "/organization";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(
                writer.getBuffer().toString(), "utf-8");
        postRequest.setEntity(orgEntity);
        HttpResponse response = httpClient.execute(postRequest);

        Organization createdOrg = unmarshalOrganization(response.getEntity());

        // change Org status to ACTIVE
        createdOrg.setStatus(EntityStatus.ACTIVE);
        String upUrl = osUrl + "/organization/" + createdOrg.getId();
        StringWriter upWriter = marshalOrganization(createdOrg);
        HttpPut putReq = new HttpPut(upUrl);
        putReq.addHeader("content-type", APPLICATION_XML);
        putReq.addHeader("Accept", APPLICATION_XML);
        StringEntity upOrgEntity = new StringEntity(upWriter.getBuffer()
                .toString());
        putReq.setEntity(upOrgEntity);
        HttpResponse upResponse = httpClient.execute(putReq);

        HttpEntity resEntity = upResponse.getEntity();
        Organization retUpOrg = unmarshalOrganization(resEntity);
        return retUpOrg;
    }

    private OrganizationRole createOrgRole(OrganizationRole orgRole)
            throws Exception {
        String url = osUrl + "/role";
        StringWriter writer = marshalOrganizationRole(orgRole);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", APPLICATION_XML);
        postRequest.addHeader("Accept", APPLICATION_XML);
        StringEntity orgRolEntity = new StringEntity(writer.getBuffer()
                .toString());
        postRequest.setEntity(orgRolEntity);
        HttpResponse response = httpClient.execute(postRequest);
        OrganizationRole retOrgRol = unmarshalOrganizationRole(response
                .getEntity());
        return retOrgRol;
    }

    private StringWriter marshalOrganization(Organization organization)
            throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(Organization.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(new JAXBElement<Organization>(ORG_QNAME,
                Organization.class, null, organization), writer);

        return writer;
    }

    private Organization unmarshalOrganization(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Organization.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<Organization> jaxbEle = (JAXBElement<Organization>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgXMLStr)),
                        Organization.class);
        return jaxbEle.getValue();
    }

    private OrganizationSearchResultList unmarshalOrganizationSearchResultList(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(OrganizationSearchResultList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String osrlXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<OrganizationSearchResultList> jaxbEle = (JAXBElement<OrganizationSearchResultList>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(osrlXMLStr)),
                        OrganizationSearchResultList.class);
        return jaxbEle.getValue();
    }

    private StringWriter marshalOrganizationRole(OrganizationRole orgRole)
            throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jaxbContext = JAXBContext
                .newInstance(OrganizationRole.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(new JAXBElement<OrganizationRole>(
                ORG_ROLE_QNAME, OrganizationRole.class, null, orgRole), writer);
        return writer;
    }

    private OrganizationRole unmarshalOrganizationRole(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(OrganizationRole.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgRoleXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<OrganizationRole> jaxbEle = (JAXBElement<OrganizationRole>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgRoleXMLStr)),
                        OrganizationRole.class);
        return jaxbEle.getValue();
    }

    private OrganizationRoleList unmarshalOrgRoleList(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(OrganizationRoleList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgRolListXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<OrganizationRoleList> jaxbEle = (JAXBElement<OrganizationRoleList>) jaxbUnmarshaller
                .unmarshal(
                        new StreamSource(new StringReader(orgRolListXMLStr)),
                        OrganizationRoleList.class);
        return jaxbEle.getValue();
    }

    private HealthCareFacility getHealthCareFacilityObj() throws Exception {
        HealthCareFacility hcf = new HealthCareFacility();        
        hcf.setName("Mayo HCF");
        hcf.setOrganizationId(createActiveOrganization().getId());
        hcf.setStatus(EntityStatus.PENDING);
        hcf.getAddress().add(getJaxbAddressList().get(0));
        hcf.getContact().addAll(getJaxbContactList());
        return hcf;
    }

    private OversightCommittee getOversightCommitteeObj() throws Exception {
        OversightCommittee oc = new OversightCommittee();
        oc.setType(OversightCommitteeType.ETHICS_COMMITTEE);
        oc.setOrganizationId(createActiveOrganization().getId());
        oc.setStatus(EntityStatus.ACTIVE);
        oc.getAddress().add(getJaxbAddressList().get(0));
        oc.getContact().addAll(getJaxbContactList());
        return oc;
    }

    private ResearchOrganization getResearchOrganizationObj() throws Exception {
        ResearchOrganization ro = new ResearchOrganization();        
        ro.setName("Mayo RO");
        ro.setOrganizationId(createActiveOrganization().getId());
        ro.setType(ResearchOrganizationType.NWK);
        ro.setFundingMechanism(FundingMechanism.G_11);
        ro.setStatus(EntityStatus.PENDING);
        ro.getAddress().add(getJaxbAddressList().get(0));
        ro.getContact().addAll(getJaxbContactList());
        return ro;
    }

}
