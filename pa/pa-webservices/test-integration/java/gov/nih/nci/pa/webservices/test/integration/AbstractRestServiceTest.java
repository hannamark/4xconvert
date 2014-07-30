/**
 * 
 */
package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

/**
 * @author dkrylov
 * 
 */
public abstract class AbstractRestServiceTest extends AbstractPaSeleniumTest {
    @SuppressWarnings("deprecation")
    protected DefaultHttpClient httpClient = null;
    protected AuthScope authScope;

    protected String baseURL = "";
    protected String serviceURL = "";

    protected static final String APPLICATION_XML = "application/xml";

    @SuppressWarnings("deprecation")
    public void setUp(String serviceURL) throws Exception {
        super.setUp();

        this.serviceURL = serviceURL;

        insertSecondaryPurposes();

        httpClient = new DefaultHttpClient();

        authScope = new AuthScope(TestProperties.getServerHostname(),
                TestProperties.getServerPort());
        Credentials credentials = new UsernamePasswordCredentials(
                "submitter-ci", "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";

    }

    @SuppressWarnings("deprecation")
    public void testInvalidCredentials() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials(
                "nonexistentuserfortesting", "nonexistentuserfortesting");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + serviceURL;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    @SuppressWarnings("deprecation")
    public void testValidCredentialsButNoRole() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials("curator",
                "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + serviceURL;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    private void insertSecondaryPurposes() throws SQLException {
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "INSERT INTO secondary_purpose (name) VALUES ('Other')";
            runner.update(connection, sql);
        } catch (SQLException e) {
            LOG.warning("Oops; 'Other' most likely is in secondary_purpose already; ignoring...");
        }
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "INSERT INTO secondary_purpose (name) VALUES ('Ancillary-Correlative')";
            runner.update(connection, sql);
        } catch (SQLException e) {
            LOG.warning("Oops; 'Ancillary-Correlative' most likely is in secondary_purpose already; ignoring...");
        }
    }

    protected int getReponseCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    @SuppressWarnings("deprecation")
    protected String getResponseContentType(HttpResponse response) {
        return EntityUtils.getContentMimeType(response.getEntity());
    }

    protected TrialRegistrationConfirmation unmarshalTrialRegistrationConfirmation(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(TrialRegistrationConfirmation.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        LOG.info(orgXMLStr);
        JAXBElement<TrialRegistrationConfirmation> jaxbEle = (JAXBElement<TrialRegistrationConfirmation>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgXMLStr)),
                        TrialRegistrationConfirmation.class);
        return jaxbEle.getValue();
    }

    @SuppressWarnings("unchecked")
    protected CompleteTrialRegistration readCompleteTrialRegistrationFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialRegistration o = ((JAXBElement<CompleteTrialRegistration>) u
                .unmarshal(url)).getValue();
        return o;
    }

    @SuppressWarnings("unchecked")
    protected CompleteTrialUpdate readCompleteTrialUpdateFromFile(String string)
            throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialUpdate o = ((JAXBElement<CompleteTrialUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }

    @SuppressWarnings("unchecked")
    protected TrialRegistrationConfirmation registerTrialFromFile(String file)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        HttpResponse response = submitRegistrationAndReturnResponse(file);
        return processTrialRegistrationResponseAndDoBasicVerification(response);

    }

    @SuppressWarnings("unchecked")
    protected TrialRegistrationConfirmation registerTrialFromJAXBElement(
            CompleteTrialRegistration o) throws ClientProtocolException,
            IOException, ParseException, JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<CompleteTrialRegistration>(
                new QName("gov.nih.nci.pa.webservices.types",
                        "CompleteTrialRegistration"),
                CompleteTrialRegistration.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = submitRegistrationAndReturnResponse(entity);
        return processTrialRegistrationResponseAndDoBasicVerification(response);

    }

    protected HttpResponse submitRegistrationAndReturnResponse(String file)
            throws UnsupportedEncodingException, IOException,
            ClientProtocolException {
        StringEntity orgEntity = new StringEntity(IOUtils.toString(getClass()
                .getResourceAsStream(file), "UTF-8"));
        return submitRegistrationAndReturnResponse(orgEntity);
    }

    /**
     * @param file
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClientProtocolException
     */
    private HttpResponse submitRegistrationAndReturnResponse(
            StringEntity orgEntity) throws UnsupportedEncodingException,
            IOException, ClientProtocolException {
        return submitEntityAndReturnResponse(orgEntity,
                "/registration/complete");

    }

    protected HttpResponse submitEntityAndReturnResponse(
            StringEntity orgEntity, String serviceURL)
            throws UnsupportedEncodingException, IOException,
            ClientProtocolException {
        String url = baseURL + serviceURL;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(orgEntity);

        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        return response;
    }

    /**
     * @param response
     * @return
     * @throws JAXBException
     * @throws ParseException
     * @throws IOException
     * @throws SQLException
     */
    protected TrialRegistrationConfirmation processTrialRegistrationResponseAndDoBasicVerification(
            HttpResponse response) throws JAXBException, ParseException,
            IOException, SQLException {
        HttpEntity resEntity = response.getEntity();
        TrialRegistrationConfirmation conf = unmarshalTrialRegistrationConfirmation(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertTrue(StringUtils.isNotBlank(conf.getNciTrialID()));
        assertNotNull(conf.getPaTrialID());
        assertEquals(getTrialIdByLeadOrgID("UPCC 34890534").longValue(),
                conf.getPaTrialID());

        LOG.info(ToStringBuilder.reflectionToString(conf));

        return conf;
    }

    /**
     * @param conf
     */
    protected void logInFindAndAcceptTrial(TrialRegistrationConfirmation conf) {
        logoutUser();
        loginAsSuperAbstractor();

        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", conf.getNciTrialID());
        selenium.select("id=identifierType", "NCI");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        acceptTrial();
        verifyTrialAccepted();
    }
    
    protected String getTrialIdentificationTableCellValue(String label) {
        for (int i = 1; i < 20; i++) {
            String text = selenium
                    .getText("xpath=//form//table[@class='form']//tr[" + i
                            + "]//td[1]");
            if (label.equalsIgnoreCase(StringUtils.trim(text))) {
                return selenium
                        .getText("xpath=//form//table[@class='form']//tr[" + i
                                + "]//td[2]");
            }
        }
        return null;
    }


}
