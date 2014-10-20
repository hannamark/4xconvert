package gov.nih.nci.accrual.accweb.integration;

import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.test.integration.AbstractRestServiceTest;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.xml.sax.SAXException;

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
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

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
        verifyAccrualCollectionRecordHasNullifiedOrgErrorMessage("6");
        verifyEmailContainsNullifiedOrgError(server, "6");
    }

    @Test
    public void testNullifiedOrgHandlingByCtepIDPO8108() throws Exception {
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

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
        verifyAccrualCollectionRecordHasNullifiedOrgErrorMessage("CTGOVDUPE");
        verifyEmailContainsNullifiedOrgError(server, "CTGOVDUPE");
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingPO8106() throws Exception {
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        writeValidBatchFileButWithDuplicatePatients(rConf, batchFile);

        SimpleSmtpServer server = SimpleSmtpServer.start(PORT);
        submitBatchFile(batchFile);
        pause(10000);
        server.stop();
        
        String error = getLatestAccrualCollectionMessage();
        verifyErrorMessageContainsDupePatientInfo(error);
        
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        error = email.getBody();
        assertTrue(error.contains("CTRP processed your file successfully"));
        assertTrue(error.contains("Number of Subjects Registered: </b> 1"));
        verifyErrorMessageContainsDupePatientInfo(error);
       
        verifySu002IsOnTrial(rConf);

    }

    /**
     * @param error
     */
    private void verifyErrorMessageContainsDupePatientInfo(String error) {
        assertTrue(error
                .contains("The following lines contain duplicate subject data and were not processed into the system. "
                        + "Please remove the duplicate lines and resubmit the file"));
        assertTrue(error
                .contains("Line 2, Site ID: 3, Subject ID: SU001"));
        assertTrue(error
                .contains("Line 3, Site ID: 3, Subject ID: SU001"));
    }

    /**
     * @param rConf
     * @param batchFile
     * @throws IOException
     */
    private void writeValidBatchFileButWithDuplicatePatients(
            TrialRegistrationConfirmation rConf, File batchFile)
            throws IOException {
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                        "COLLECTIONS,"+rConf.getNciTrialID()+",,,,,,,,,",
                        "PATIENTS,"+rConf.getNciTrialID()+",SU001,77058,,193106,Female,Not Hispanic or Latino,,20110513,, 3 ,,,,,,,,,,B46.9,,",
                        "PATIENTS, "+rConf.getNciTrialID()+", SU001 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",
                        "PATIENTS,"+rConf.getNciTrialID()+",SU002,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",                                
                        "", 
                        "PATIENT_RACES,"+rConf.getNciTrialID()+",SU001,White",
                        "PATIENT_RACES,"+rConf.getNciTrialID()+",SU002,Asian" }));
    }

    /**
     * @return
     * @throws JAXBException
     * @throws SAXException
     * @throws SQLException
     * @throws ClientProtocolException
     * @throws ParseException
     * @throws IOException
     * @throws NumberFormatException
     */
    private TrialRegistrationConfirmation prepareTrialForAccrualSubmission()
            throws JAXBException, SAXException, SQLException,
            ClientProtocolException, ParseException, IOException,
            NumberFormatException {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        ParticipatingSite upd = readParticipatingSiteFromFile("/integration_ps_accruing_add.xml");
        HttpResponse response = addSite("pa", rConf.getPaTrialID() + "", upd);
        assertEquals(200, getReponseCode(response));
        long siteID = Long.parseLong(EntityUtils.toString(response.getEntity(),
                "utf-8"));
        grantAccrualAccess("submitter-ci", siteID);
        return rConf;
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingZipUploadPO8106() throws Exception {
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        File zip = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".zip");
        File batchFile1 = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString() + ".txt");
        File batchFile2 = new File(SystemUtils.JAVA_IO_TMPDIR, UUID
                .randomUUID().toString() + ".txt");
        writeValidBatchFileButWithDuplicatePatients(rConf, batchFile1);
        writeInvalidBatchFileWithDuplicatePatients(rConf, batchFile2);
        
        FileOutputStream fos = new FileOutputStream(zip);
        ZipOutputStream zos = new ZipOutputStream(fos);        
        addToZipFile(batchFile1, zos);
        addToZipFile(batchFile2, zos);        
        zos.close();
        fos.close();
        
        SimpleSmtpServer server = SimpleSmtpServer.start(PORT);
        submitBatchFile(zip);
        pause(10000);
        server.stop();
        
        assertEquals(2, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email1 = (SmtpMessage) emailIter.next();
        String msg1 = email1.getBody();        
        SmtpMessage email2 = (SmtpMessage) emailIter.next();
        String msg2 = email2.getBody();
        
        String success = msg1;
        String error = msg2;
        if (msg2.contains("CTRP processed your file successfully")) {
            success = msg2;
            error = msg1;
        }
        
        assertTrue(success.contains("CTRP processed your file successfully"));
        assertTrue(success.contains("Number of Subjects Registered: </b> 1"));
        verifyErrorMessageContainsDupePatientInfo(success);
        
        assertTrue(error
                .contains("Unfortunately, the submission failed due to the following errors"));
        assertTrue(error
                .contains("Patient race code is not valid for patient ID SU002 at line 7"));
        verifyErrorMessageContainsDupePatientInfo(error);
        verifySu002IsOnTrial(rConf);

    }
    
   
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testDuplicateSubjectHandlingWithErrorsPO8106() throws Exception {
        TrialRegistrationConfirmation rConf = prepareTrialForAccrualSubmission();

        File batchFile = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString() + ".txt");
        writeInvalidBatchFileWithDuplicatePatients(rConf, batchFile);

        SimpleSmtpServer server = SimpleSmtpServer.start(PORT);
        submitBatchFile(batchFile);
        pause(10000);
        server.stop();
        
        String error = getLatestAccrualCollectionMessage();
        verifyErrorMessageContainsDupePatientInfo(error);
        
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        error = email.getBody();
        assertTrue(error
                .contains("Unfortunately, the submission failed due to the following errors"));
        assertTrue(error
                .contains("Patient race code is not valid for patient ID SU002 at line 7"));
        verifyErrorMessageContainsDupePatientInfo(error);

        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        assertFalse(selenium.isElementPresent("xpath=//a[text()='SU001']"));
        assertFalse(selenium.isElementPresent("xpath=//a[text()='SU002']"));

    }

    /**
     * @param rConf
     * @param batchFile
     * @throws IOException
     */
    private void writeInvalidBatchFileWithDuplicatePatients(
            TrialRegistrationConfirmation rConf, File batchFile)
            throws IOException {
        FileUtils
                .writeLines(
                        batchFile,
                        "UTF-8",
                        Arrays.asList(new String[] {
                        "COLLECTIONS,"+rConf.getNciTrialID()+",,,,,,,,,",
                        "PATIENTS,"+rConf.getNciTrialID()+",SU001,77058,,193106,Female,Not Hispanic or Latino,,20110513,, 3 ,,,,,,,,,,B46.9,,",
                        "PATIENTS, "+rConf.getNciTrialID()+", SU001 ,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",
                        "PATIENTS,"+rConf.getNciTrialID()+",SU002,33908,,195306,Female,Not Hispanic or Latino,,20110930,,3,,,,,,,,,,B46.9,,",                                
                        "", 
                        "PATIENT_RACES,"+rConf.getNciTrialID()+",SU001,Black",
                        "PATIENT_RACES,"+rConf.getNciTrialID()+",SU002,Black" }));
    }

    /**
     * @param rConf
     */
    private void verifySu002IsOnTrial(TrialRegistrationConfirmation rConf) {
        clickAndWait("link=Trial Search");
        clickAndWait("link=" + rConf.getNciTrialID());
        moveElementIntoView(By.xpath("//a[text()='SU002']"));
        clickAndWait("link=SU002");
        clickAndWait("xpath=//i[@class='fa-pencil']");
        assertEquals("SU002", selenium.getValue("id=identifier"));
    }

    @SuppressWarnings("rawtypes")
    private void verifyEmailContainsNullifiedOrgError(SimpleSmtpServer server,
            String orgID) {
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        verifyNullifiedOrgErrorMessage(email.getBody(), orgID);

    }

    private void verifyAccrualCollectionRecordHasNullifiedOrgErrorMessage(
            String orgID) throws SQLException {
        String error = getLatestAccrualCollectionMessage();
        verifyNullifiedOrgErrorMessage(error, orgID);
    }

    /**
     * @return
     * @throws SQLException
     */
    private String getLatestAccrualCollectionMessage() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String error = (String) runner
                .query(connection,
                        "select results from accrual_collections order by identifier desc limit 1",
                        new ArrayHandler())[0];
        return error;
    }

    private void verifyNullifiedOrgErrorMessage(String msg, String orgID) {
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
    
    private static void addToZipFile(File file, ZipOutputStream zos)
            throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

}
