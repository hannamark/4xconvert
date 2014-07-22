package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisterCompleteTrialTest extends AbstractRestServiceTest {

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp();
        insertOtherSecondaryPurpose();
    }

    private void insertOtherSecondaryPurpose() throws SQLException {
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "INSERT INTO secondary_purpose (name) VALUES ('Other')";
            runner.update(connection, sql);
        } catch (SQLException e) {
            LOG.warning("Oops; 'Other' most likely is in secondary_purpose already; ignoring...");
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInvalidCredentials() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials(
                "nonexistentuserfortesting", "nonexistentuserfortesting");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + "/registration/complete";

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    @Test
    public void testValidCredentialsButNoRole() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials("curator",
                "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + "/registration/complete";

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    @Test
    public void testImportSuccess() throws Exception {
        registerAndVerify("/integration_register_complete_success.xml");
    }

    private void registerAndVerify(String file) throws SQLException,
            ClientProtocolException, ParseException, IOException,
            JAXBException, SAXException {

        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(file);
        deactivateTrialByNctId(reg.getClinicalTrialsDotGovTrialID());
        TrialRegistrationConfirmation conf = registerTrialFromFile(file);
        verifyRegistration(conf, reg);

    }

    private void verifyRegistration(TrialRegistrationConfirmation conf,
            CompleteTrialRegistration reg) throws JAXBException, SAXException {

        loginAsSuperAbstractor();

        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", conf.getNciTrialID());
        selenium.select("id=identifierType", "NCI");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        acceptTrial();
        verifyTrialAccepted();

        verifyTrialIdentification(reg, conf);

    }

    private void verifyTrialIdentification(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Identification");
        
        assertTrue(selenium.getText("id=displaySubmitterLink").contains(
                "submitter-ci"));
        assertTrue(selenium.isElementPresent("link=National Cancer Institute Division of Cancer Prevention"));

        assertEquals("Interventional",
                getTrialIdentificationTableCellValue("Trial Type"));
        assertEquals(conf.getNciTrialID(),
                getTrialIdentificationTableCellValue("NCI Trial Identifier"));
        assertEquals(
                reg.isClinicalTrialsDotGovXmlRequired() ? "Yes" : "No",
                getTrialIdentificationTableCellValue("ClinicalTrials.gov XML required?"));
        assertEquals(
                reg.getLeadOrgTrialID(),
                getTrialIdentificationTableCellValue("Lead Organization Trial ID"));
        assertEquals(
                reg.getClinicalTrialsDotGovTrialID(),
                getTrialIdentificationTableCellValue("ClinicalTrials.gov Identifier"));
        assertEquals(reg.getOtherTrialID().get(0),
                getTrialIdentificationTableCellValue("Other Identifier"));
        assertEquals("No",
                getTrialIdentificationTableCellValue("Abbreviated Trial?"));
        assertEquals(reg.getTitle(),
                getTrialIdentificationTableCellValue("Official Title"));
        assertEquals("REST Service",
                getTrialIdentificationTableCellValue("Submission Source"));

    }

    private String getTrialIdentificationTableCellValue(String label) {
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

    @SuppressWarnings("unchecked")
    private CompleteTrialRegistration readCompleteTrialRegistrationFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialRegistration o = ((JAXBElement<CompleteTrialRegistration>) u
                .unmarshal(url)).getValue();
        return o;
    }

    @SuppressWarnings("unchecked")
    private TrialRegistrationConfirmation registerTrialFromFile(String file)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        String url = baseURL + "/registration/complete";

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        req.addHeader("Content-Type", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(IOUtils.toString(getClass()
                .getResourceAsStream(file), "UTF-8"));
        req.setEntity(orgEntity);

        HttpResponse response = httpClient.execute(req);

        HttpEntity resEntity = response.getEntity();
        TrialRegistrationConfirmation conf = unmarshalTrialRegistrationConfirmation(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertTrue(StringUtils.isNotBlank(conf.getNciTrialID()));
        assertNotNull(conf.getPaTrialID());
        assertEquals(getTrialIdByNct("NCT000000000001").longValue(),
                conf.getPaTrialID());

        LOG.info(ToStringBuilder.reflectionToString(conf));

        return conf;

    }

}
