package gov.nih.nci.po.webservices.service.simple.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.webservices.service.simple.AbstractFamilyServiceTest;
import gov.nih.nci.po.webservices.service.utils.AuthUtils;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyList;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationshipList;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This is an Integration test class for FamilyService(REST).
 *
 * @author Rohit Gupta
 */

public class FamilyRESTServiceTest extends AbstractFamilyServiceTest {

    private static DefaultHttpClient httpClient = null;

    private static String fsUrl = null;
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TXT_PLAIN = "text/plain";


    @BeforeClass
    public static void setUpClass() throws Exception {
        AuthUtils.addBasicAuthSupport(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        setUpFamilyServiceData();

        AuthUtils.removeBasicAuthSupport();
    }

    @Before
    public void setUp() throws Exception {
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

        fsUrl = TstProperties.getFamilyRESTServiceURL();
    }

    @Test
    public void searchFamiliesByNameWithoutAuthentication() throws Exception {
        AuthScope authScope = new AuthScope(
                TstProperties.getServerHostname(),
                TstProperties.getServerPort()
        );
        Credentials credentials = new UsernamePasswordCredentials("bogusUser","bogusPassword");

        httpClient.getCredentialsProvider().setCredentials(authScope, credentials);

        String url = fsUrl + "/families?name=Arizona";
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(HttpStatus.SC_UNAUTHORIZED, getReponseCode(response));

    }

    /**
     * Testcase for FamilyService-searchFamilies - by name
     */
    @Test
    public void searchFamiliesByName() throws Exception {
        String url = fsUrl + "/families?name=Arizona";
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = httpClient.execute(getReq);
        getReq.addHeader("Accept", APPLICATION_XML);
        FamilyList familyList = unmarshalFamilyList(response.getEntity());

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(familyList.getFamily().size() >= 2);
        for (Family family : familyList.getFamily()) {
            assertTrue(family.getName().contains("Arizona"));
        }
    }

    /**
     * Testcase for FamilyService-searchFamilies - by name - JSON Format
     */
    @Test
    public void searchFamiliesByName_JSON() throws Exception {
        String url = fsUrl + "/families?name=Arizona";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String famJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        FamilyList familyList = mapper.readValue(famJSONStr, FamilyList.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(familyList.getFamily().size() >= 2);
        for (Family family : familyList.getFamily()) {
            assertTrue(family.getName().contains("Arizona"));
        }
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName-no matching record found
     */
    @Test
    public void testSearchFamiliesByNameForNoRecordFound() throws Exception {
        String url = fsUrl + "/families?name=Cancerrrrrrrrrrrrrrrrrrrrr";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));

        FamilyList familyList = unmarshalFamilyList(response.getEntity());
        assertTrue(familyList.getFamily().size() == 0);
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName-no matching record found
     * - JSON Format
     */
    @Test
    public void testSearchFamiliesByNameForNoRecordFound_JSON()
            throws Exception {
        String url = fsUrl + "/families?name=Cancerrrrrrrrrrrrrrrrrrrrr";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));

        HttpEntity resEntity = response.getEntity();
        String famJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        FamilyList familyList = mapper.readValue(famJSONStr, FamilyList.class);
        assertTrue(familyList.getFamily().size() == 0);
    }

    /**
     * Testcase for FamilyService-searchFamilies - by OrgId
     */
    @Test
    public void testSearchFamiliesByOrgId() throws Exception {
        String url = fsUrl + "/families?organizationId=" + org1.getId();
        HttpGet getReq = new HttpGet(url);
        HttpResponse response = httpClient.execute(getReq);
        FamilyList familyList = unmarshalFamilyList(response.getEntity());

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        // 2 Family records created in setup()
        assertTrue(familyList.getFamily().size() == 2);

        for (Family fam : familyList.getFamily()) {
            List<FamilyMember> fmList = fam.getMember();
            boolean isOrgFound = false;
            for (FamilyMember fm : fmList) {
                if (org1.getId().longValue() == fm.getOrganizationId()) {
                    isOrgFound = true;
                }
            }
            if (!isOrgFound) {
               fail("FamilyMember doesn't contain the OrgId:"
                        + org1.getId());
            }
        }
    }

    /**
     * Testcase for FamilyService-searchFamilies - by OrgId - JSON Format
     */
    @Test
    public void testSearchFamiliesByOrgId_JSON() throws Exception {
        String url = fsUrl + "/families?organizationId=" + org1.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String famJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        FamilyList familyList = mapper.readValue(famJSONStr, FamilyList.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        // 2 Family records created in setup()
        assertTrue(familyList.getFamily().size() == 2);

        for (Family fam : familyList.getFamily()) {
            List<FamilyMember> fmList = fam.getMember();
            boolean isOrgFound = false;
            for (FamilyMember fm : fmList) {
                if (org1.getId().longValue() == fm.getOrganizationId()) {
                    isOrgFound = true;
                }
            }
            if (!isOrgFound) {
                fail("FamilyMember doesn't contain the OrgId:"
                        + org1.getId());
            }
        }
    }

    /**
     * Testcase for FamilyService-searchFamilies - by OrgId
     */
    @Test
    public void testSearchFamiliesByOrgIdForOrgNotFound() throws Exception {
        String url = fsUrl + "/families?organizationId=" + "999999999999999999";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Family search couldn't be performed as Organization for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-searchFamilies - by OrgId - JSON Format
     */
    @Test
    public void testSearchFamiliesByOrgIdForOrgNotFound_JSON() throws Exception {
        String url = fsUrl + "/families?organizationId=" + "999999999999999999";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Family search couldn't be performed as Organization for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-searchFamilies - by name & OrgId
     */
    @Test
    public void searchFamiliesByNameAndOrgId() throws Exception {
        // URL have both FamilyName & OrgId
        String url = fsUrl + "/families?name=Arizona&organizationId="
                + org1.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);
        FamilyList familyList = unmarshalFamilyList(response.getEntity());

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(familyList.getFamily().size() == 1);
        for (Family family : familyList.getFamily()) {
            assertTrue(family.getName().contains("Arizona"));
        }
    }

    /**
     * Testcase for FamilyService-searchFamilies - by name & OrgId - JSON Format
     */
    @Test
    public void searchFamiliesByNameAndOrgId_JSON() throws Exception {
        // URL have both FamilyName & OrgId
        String url = fsUrl + "/families?name=Arizona&organizationId="
                + org1.getId();
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String famJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        FamilyList familyList = mapper.readValue(famJSONStr, FamilyList.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(familyList.getFamily().size() == 1);
        for (Family family : familyList.getFamily()) {
            assertTrue(family.getName().contains("Arizona"));
        }
    }

    /**
     * Testcase for FamilyService-searchFamilies - by name & OrgId - but Org not
     * found in DB.
     */
    @Test
    public void searchFamiliesByNameAndOrgIdForOrgNotFound() throws Exception {
        // URL have both FamilyName & OrgId (OrgId not found in DB)
        String url = fsUrl
                + "/families?name=Arizona&organizationId=999999999999999999";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Family search couldn't be performed as Organization for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-searchFamilies - by name & OrgId - but Org not
     * found in DB - JSON Format
     */
    @Test
    public void searchFamiliesByNameAndOrgIdForOrgNotFound_JSON()
            throws Exception {
        // URL have both FamilyName & OrgId (OrgId not found in DB)
        String url = fsUrl
                + "/families?name=Arizona&organizationId=999999999999999999";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Family search couldn't be performed as Organization for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-searchFamilies-search criteria not specified
     */
    @Test
    public void testSearchFamiliesForCriteriaNotSpecified() throws Exception {
        String url = fsUrl + "/families"; // no search criteria
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Search Criteria (name or/and organizationId) is not specified in the URL"));
    }

    /**
     * Testcase for FamilyService-searchFamilies-search criteria not specified -
     * JSON Format
     */
    @Test
    public void testSearchFamiliesForCriteriaNotSpecified_JSON()
            throws Exception {
        String url = fsUrl + "/families"; // no search criteria
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "Search Criteria (name or/and organizationId) is not specified in the URL"));
    }

    /**
     * Testcase for FamilyService-getFamily
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamily() throws Exception {

        String url = fsUrl + "/family/" + familyId1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        Family retFamily = unmarshalFamily(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(retFamily.getName().contains("Arizona"));
    }

    /**
     * Testcase for FamilyService-getFamily - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamily_JSON() throws Exception {

        String url = fsUrl + "/family/" + familyId1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String famJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        Family retFamily = mapper.readValue(famJSONStr, Family.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(retFamily.getName().contains("Arizona"));
    }

    /**
     * Testcase for FamilyService-getFamily -Family Not Found in DB
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamilyNotFound() throws Exception {

        String url = fsUrl + "/family/999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Family is not found in the database"));
    }

    /**
     * Testcase for FamilyService-getFamily -Family Not Found in DB - JSON
     * Format
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamilyNotFound_JSON() throws Exception {

        String url = fsUrl + "/family/999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Family is not found in the database"));
    }

    /**
     * Testcase for FamilyService-getFamilyMember
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamilyMember() throws Exception {

        String url = fsUrl + "/familymember/" + famOrgRelId1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        FamilyMember retFamMem = unmarshalFamilyMember(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(familyId1 == retFamMem.getFamilyId());
        assertTrue("ORGANIZATIONAL".equalsIgnoreCase(retFamMem.getType().value()));
    }

    /**
     * Testcase for FamilyService-getFamilyMember-JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamilyMember_JSON() throws Exception {

        String url = fsUrl + "/familymember/" + famOrgRelId1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String famMemJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        FamilyMember retFamMem = mapper.readValue(famMemJSONStr,FamilyMember.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(familyId1 == retFamMem.getFamilyId());
        assertTrue("ORGANIZATIONAL".equalsIgnoreCase(retFamMem.getType().value()));
    }

    /**
     * Testcase for FamilyService-getFamilyMember -FamilyMember Not Found in DB
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamilyMemberNotFound() throws Exception {

        String url = fsUrl + "/familymember/999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Family Member is not found in the database"));
    }

    /**
     * Testcase for FamilyService-getFamilyMember -FamilyMember Not Found in DB
     * - JSON Format
     * 
     * @throws Exception
     */
    @Test
    public void testGetFamilyMemberNotFound_JSON() throws Exception {

        String url = fsUrl + "/familymember/999999888888777777";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Family Member is not found in the database"));
    }
    
    
    
    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyId() throws Exception {
        String url = fsUrl + "/familyMemberRelationships?familyId="
                + familyId1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        FamilyMemberRelationshipList fmrList = unmarshalFamilyMemberRelationshipList(response
                .getEntity());

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(fmrList.getFamilyMemberRelationship().size() >= 2);
        for (FamilyMemberRelationship fmr : fmrList
                .getFamilyMemberRelationship()) {
            assertEquals(familyId1, fmr.getFamilyId());
        }
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId - JSON
     * Format
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyId_JSON()
            throws Exception {
        String url = fsUrl + "/familyMemberRelationships?familyId="
                + familyId1;
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        HttpEntity resEntity = response.getEntity();
        String famJSONStr = EntityUtils.toString(resEntity, "utf-8");
        ObjectMapper mapper = new ObjectMapper();
        FamilyMemberRelationshipList fmrList = mapper.readValue(famJSONStr,
                FamilyMemberRelationshipList.class);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_JSON, getResponseContentType(response));
        assertTrue(fmrList.getFamilyMemberRelationship().size() >= 2);
        for (FamilyMemberRelationship fmr : fmrList
                .getFamilyMemberRelationship()) {
            assertEquals(familyId1, fmr.getFamilyId());
        }
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId -family
     * not found
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyIdForFamilyNotFound()
            throws Exception {
        String url = fsUrl + "/familyMemberRelationships?familyId="
                + "999999999999999999";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "FamilyMemberRelationship couldn't be fetched as Family for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId -family
     * not found - JSON Format
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyIdForFamilyNotFound_JSON()
            throws Exception {
        String url = fsUrl + "/familyMemberRelationships?familyId="
                + "999999999999999999";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(404, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response)
                .contains(
                        "FamilyMemberRelationship couldn't be fetched as Family for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-GetFamilyMemberRelationshipsByFamilyId-search
     * criteria not specified
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyIdForCriteriaNotSpecified()
            throws Exception {
        String url = fsUrl + "/familyMemberRelationships";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Search Criteria (familyId) is not specified in the URL"));
    }

    /**
     * Testcase for FamilyService-GetFamilyMemberRelationshipsByFamilyId-search
     * criteria not specified - JSON Format
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyIdForCriteriaNotSpecified_JSON()
            throws Exception {
        String url = fsUrl + "/familyMemberRelationships";
        HttpGet getReq = new HttpGet(url);
        getReq.addHeader("Accept", APPLICATION_JSON);
        HttpResponse response = httpClient.execute(getReq);

        assertEquals(400, getReponseCode(response));
        assertEquals(TXT_PLAIN, getResponseContentType(response));
        assertTrue(getResponseMessage(response).contains(
                "Search Criteria (familyId) is not specified in the URL"));
    }

    @After
    public void tearDown() {
        httpClient.getConnectionManager().shutdown();
    }

    private FamilyList unmarshalFamilyList(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(FamilyList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String familyListXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<FamilyList> jaxbEle = (JAXBElement<FamilyList>) jaxbUnmarshaller
                .unmarshal(
                        new StreamSource(new StringReader(familyListXMLStr)),
                        FamilyList.class);
        return jaxbEle.getValue();
    }

    private FamilyMemberRelationshipList unmarshalFamilyMemberRelationshipList(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(FamilyMemberRelationshipList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String fmrListXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<FamilyMemberRelationshipList> jaxbEle = (JAXBElement<FamilyMemberRelationshipList>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(fmrListXMLStr)),
                        FamilyMemberRelationshipList.class);
        return jaxbEle.getValue();
    }
    
    private Family unmarshalFamily(HttpEntity httpEntity) throws JAXBException,
            ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Family.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String familyXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<Family> jaxbEle = (JAXBElement<Family>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(familyXMLStr)),
                        Family.class);
        return jaxbEle.getValue();
    }
        
    private FamilyMember unmarshalFamilyMember(HttpEntity httpEntity)
            throws JAXBException, ParseException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(FamilyMember.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String famMemXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<FamilyMember> jaxbEle = (JAXBElement<FamilyMember>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(famMemXMLStr)),
                        FamilyMember.class);
        return jaxbEle.getValue();
    }
}
