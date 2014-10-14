package gov.nih.nci.accrual.accweb.integration;

import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.test.integration.AbstractRestServiceTest;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

@SuppressWarnings("deprecation")
public class BatchUploadTest extends AbstractRestServiceTest {

    public static final int PORT = 51234;

    /**
     * @throws java.lang.Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp("/sites/1");
        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";
        new QueryRunner().update(connection, "update pa_properties set value='"
                + PORT + "' where name='smtp.port'");
    }

    @Test
    public void testNullifiedOrgHandlingPO8108() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        String batchFileStr = IOUtils
                .toString(
                        getClass().getResourceAsStream("/PO_8108_by_poid.txt"))
                .replaceAll("@nciid@", rConf.getNciTrialID())
                .replaceAll("@poid@", "6");
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        FileUtils.writeStringToFile(batchFile, batchFileStr);

        SimpleSmtpServer server = SimpleSmtpServer.start(PORT);
        submitBatchFile(batchFile);
        pause(10000);
        server.stop();
        verifyAccrualCollectionRecord("6");
        verifyEmail(server, "6");
    }

    @Test
    public void testNullifiedOrgHandlingByCtepIDPO8108() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);

        String batchFileStr = IOUtils
                .toString(
                        getClass().getResourceAsStream("/PO_8108_by_poid.txt"))
                .replaceAll("@nciid@", rConf.getNciTrialID())
                .replaceAll("@poid@", "CTGOVDUPE");
        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        FileUtils.writeStringToFile(batchFile, batchFileStr);

        SimpleSmtpServer server = SimpleSmtpServer.start(PORT);
        submitBatchFile(batchFile);
        pause(10000);
        server.stop();
        verifyAccrualCollectionRecord("CTGOVDUPE");
        verifyEmail(server, "CTGOVDUPE");
    }

    @SuppressWarnings("rawtypes")
    private void verifyEmail(SimpleSmtpServer server, String orgID) {
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        verifyErrorMessage(email.getBody(), orgID);

    }

    private void verifyAccrualCollectionRecord(String orgID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String error = (String) runner
                .query(connection,
                        "select results from accrual_collections order by identifier desc limit 1",
                        new ArrayHandler())[0];
        verifyErrorMessage(error, orgID);
    }

    private void verifyErrorMessage(String msg, String orgID) {
        assertTrue(msg
                .contains("The Registering Institution Code must be a valid PO or CTEP ID. Code: "
                        + orgID + "*"));
        assertTrue(msg
                .contains("*This organization's record has been nullified and merged with another organization."));
        assertTrue(msg.contains("The new organization is:"));
        assertTrue(msg.contains("Name: ClinicalTrials.gov"));
        assertTrue(msg.contains("PO ID: 1"));
        assertTrue(msg.contains("CTEP ID: N/A"));
    }

    @SuppressWarnings("deprecation")
    private void submitBatchFile(File batchFile) {
        login();
        clickAndWait("link=Batch Upload");
        String path = batchFile.toString();
        selenium.type("upload", path);
        clickAndWait("xpath=//button[normalize-space(text())='Submit']");
    }

    @SuppressWarnings("deprecation")
    private void login() {
        openAndWait("/accrual");
        selenium.type("j_username", "submitter-ci");
        selenium.type("j_password", "Coppa#12345");
        selenium.select("id=authenticationServiceURL", "label=Training");
        clickAndWait("xpath=//i[@class='fa-arrow-circle-right']");
        clickAndWait("id=acceptDisclaimer");

    }

}
