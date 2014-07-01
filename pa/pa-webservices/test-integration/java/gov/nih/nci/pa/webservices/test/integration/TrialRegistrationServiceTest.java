package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class TrialRegistrationServiceTest extends AbstractPaSeleniumTest {

    @SuppressWarnings("deprecation")
    private DefaultHttpClient httpClient = null;

    private String baseURL = "";

    private static final String APPLICATION_XML = "application/xml";

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp();
        httpClient = new DefaultHttpClient();

        AuthScope authScope = new AuthScope(TestProperties.getServerHostname(),
                TestProperties.getServerPort());
        Credentials credentials = new UsernamePasswordCredentials(
                "submitter-ci", "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";

    }

    @Test
    public void testImportAbbreviatedSuccess() throws Exception {
        importAndVerify("NCT01721876");
    }

    private void importAndVerify(String nctID) throws SQLException,
            ClientProtocolException, ParseException, IOException, JAXBException {
        loginAsSuperAbstractor();

        deactivateTrialByNctId(nctID);
        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("Nothing found to display"));

        importByNctIdViaService(nctID);

        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        assertTrue(selenium.getText("id=displaySubmitterLink").contains(
                "ClinicalTrials.gov Import"));
        assertTrue(selenium.getText("id=td_CTGOV_value").contains(nctID));

        clickAndWait("id=ctGovImportLogMenuOption");
        selenium.type("id=nctIdentifier", nctID);
        selenium.click("link=Display Log");
        assertTrue(selenium.isTextPresent("One item found."));
        assertEquals(nctID,
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[2]/a")
                        .trim());
        assertEquals("Success",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[7]")
                        .trim());
        assertEquals("New Trial",
                selenium.getText("xpath=//table[@id='row']//tr[1]//td[4]/a")
                        .trim());

    }

    private void importByNctIdViaService(String nctID)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException {
        String url = baseURL + "/registration/abbreviated/" + nctID;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);

        HttpEntity resEntity = response.getEntity();
        TrialRegistrationConfirmation conf = unmarshalTrialRegistrationConfirmation(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertTrue(StringUtils.isNotBlank(conf.getNciTrialID()));
        assertNotNull(conf.getPaTrialID());

    }

    private TrialRegistrationConfirmation unmarshalTrialRegistrationConfirmation(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(TrialRegistrationConfirmation.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        JAXBElement<TrialRegistrationConfirmation> jaxbEle = (JAXBElement<TrialRegistrationConfirmation>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgXMLStr)),
                        TrialRegistrationConfirmation.class);
        return jaxbEle.getValue();
    }

    protected int getReponseCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    @SuppressWarnings("deprecation")
    protected String getResponseContentType(HttpResponse response) {
        return EntityUtils.getContentMimeType(response.getEntity());
    }

}
