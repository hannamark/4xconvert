package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author Denis G. Krylov
 * 
 */
public class AmendCompleteTrialTest extends AbstractRestServiceTest {

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp("/trials/complete");
        deactivateAllTrials();
    }

    @Test
    public void testInvalidCredentials() throws Exception {
        super.testInvalidCredentials();
    }

    @Test
    public void testValidCredentialsButNoRole() throws Exception {
        super.testValidCredentialsButNoRole();
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testAmendAndCloseSitesPO_8323() throws Exception {
        final String file = "/integration_register_complete_success.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(file);
        TrialRegistrationConfirmation rConf = register(file);

        assignTrialOwner("ctrpsubstractor", rConf.getPaTrialID());
        addDummyCtepDcpToTrial();
        prepareTrialForAmendment(rConf);

        // Add 3 sites, one is already closed.
        TrialInfo info = new TrialInfo();
        info.nciID = rConf.getNciTrialID();
        info.id = rConf.getPaTrialID();
        info.title = reg.getTitle();
        info.leadOrgID = reg.getLeadOrgTrialID();
        info.uuid = info.leadOrgID;
        logoutPA();
        selectTrialInPA(info);
        addSiteToTrial(info, "DCP", "In Review");
        addSiteToTrial(info, "CTEP", "Active");
        addSiteToTrial(info, "NCI", "Withdrawn");

        // Amend
        restartEmailServer();
        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete_trial_closed.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);

        // Do backend checks; ensure sites are closed with the same status.
        verifySiteIsNowClosed(info,
                "National Cancer Institute Division of Cancer Prevention",
                "Closed to Accrual");
        verifySiteIsNowClosed(info, "Cancer Therapy Evaluation Program",
                "Closed to Accrual");
        List<TrialStatus> hist = getTrialStatusHistory(info);
        assertEquals("CLOSED_TO_ACCRUAL", hist.get(1).statusCode);
        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, upd
                .getTrialStatusDate().toGregorianCalendar().getTime()));

        // Verify email.
        waitForEmailsToArrive(4);
        verifySiteClosedEmail(findEmailByRecipient("submitter-ci@example.com"),
                "submitter-ci@example.com", "Submitter CI", info);
        verifySiteClosedEmail(
                findEmailByRecipient("ctrpsubstractor-ci@example.com"),
                "ctrpsubstractor-ci@example.com", "ctrpsubstractor CI", info);

    }

    protected void verifySiteClosedEmail(SmtpMessage email, String recipient,
            String recipientName, TrialInfo info) throws SQLException {
        String subject = email.getHeaderValues("Subject")[0];
        String to = email.getHeaderValues("To")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");
        assertEquals(recipient, to);
        assertEquals("NCI CTRP: SITE STATUS CHANGED ON TRIAL " + info.nciID
                + " AS A RESULT", subject);
        assertEquals(

                "<hr><table border=\"0\"><tr><td><b>Trial Title:</b></td><td>A Phase I/II Study Of Brentuximab Vedotin"
                        + " In Combination With Multi-Agent Chemotherapy Amended</td></tr><tr><td><b>Lead Organization:"
                        + "</b></td><td>Mayo Clinic in Arizona</td></tr><tr><td><b>Previous Trial Status:</b></td><td>"
                        + "In Review</td></tr><tr><td><b>New Trial Status:</b></td><td>Closed to Accrual</td></tr></table>"
                        + "<hr><p>Date: "
                        + today
                        + "</p><p>Dear "
                        + recipientName
                        + ",</p><p>The Status on the above trial has been changed"
                        + " and as a result the following Open Participating Sites have been closed:</p><table border=\"0\">"
                        + "<tr>"
                        + "<td align=\"right\">Site Name:</td>"
                        + "<td align=\"left\">Cancer Therapy Evaluation Program</td></tr><tr><td align=\"right\">"
                        + "Previous Site Status:</td><td align=\"left\">Active</td></tr><tr><td align=\"right\">"
                        + "New Site Status:</td><td align=\"left\">Closed to Accrual</td></tr><tr><td align=\"right\">"
                        + "Missing Status(es) or Errors:</td><td align=\"left\">Interim status [APPROVED] is missing. "
                        + "Interim status [IN REVIEW] is missing. Statuses [ACTIVE] and [CLOSED TO ACCRUAL] can not "
                        + "have the same date</td>"
                        + "</tr>"
                        + "<tr><td colspan=\"2\">&nbsp;</td></tr>"
                        + "<tr>"
                        + "<td align=\"right\">Site Name:</td><td align=\"left\">National Cancer Institute Division of Cancer Prevention</td>"
                        + "</tr><tr><td align=\"right\">Previous Site Status:</td><td align=\"left\">In Review</td></tr><tr>"
                        + "<td align=\"right\">New Site Status:</td><td align=\"left\">Closed to Accrual</td></tr><tr>"
                        + "<td align=\"right\">Missing Status(es) or Errors:</td><td align=\"left\">"
                        + "Interim status [ACTIVE] is missing. Interim status [APPROVED] is missing. "
                        + "Statuses [IN REVIEW] and [CLOSED TO ACCRUAL] can not have the same date</td>"
                        + "</tr>"
                        + "<tr><td colspan=\"2\">&nbsp;</td></tr>"
                        + "</table><p><b>NEXT STEPS:"
                        + "</b><br>Please login to the Clinical Trials Reporting Program Registry application and provide"
                        + " any missing site status information listed above.</p><p>If you have any questions or concerns "
                        + "regarding this change, please contact the Clinical Trials Reporting Office (CTRO) staff at ncictro@mail.nih.gov."
                        + "</p><p>Thank you for ensuring accurate Trial and Participating Site Statuses and Dates in the Clinical"
                        + " Trials Reporting Program.</p>".replaceAll("\\s+",
                                " ").replaceAll(">\\s+", ">"), body);

    }

    @Test
    public void testAmend() throws Exception {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");

        addDummyCtepDcpToTrial();

        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        // Other IDs, Grants, INDs & IDEs must merge
        upd.getGrant().addAll(reg.getGrant());
        upd.getInd().addAll(reg.getInd());
        upd.getIde().addAll(reg.getIde());
        upd.getOtherTrialID().addAll(reg.getOtherTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendDoesNotResetCtroOverride() throws Exception {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");

        addDummyCtepDcpToTrial();

        prepareTrialForAmendment(rConf);
        enableCtroOverride(rConf.getPaTrialID());

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete_ctro_visible.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);

        logInFindAndAcceptTrial(uConf);
        clickAndWait("link=NCI Specific Information");
        assertTrue(selenium.isChecked("id=ctroOverridefalse"));

    }

    /**
     * @throws InterruptedException
     */
    private void addDummyCtepDcpToTrial() throws InterruptedException {
        clickAndWait("link=General Trial Details");
        selenium.select("id=otherIdentifierType", "label=" + "CTEP Identifier");
        selenium.type("id=otherIdentifierOrg", "CTEP239048234");
        clickAndWait("id=otherIdbtnid");
        Thread.sleep(2000L);
        selenium.select("id=otherIdentifierType", "label=" + "DCP Identifier");
        selenium.type("id=otherIdentifierOrg", "DCP239048234");
        clickAndWait("id=otherIdbtnid");
    }

    @Test
    public void testAmendNonCtGov() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        addDummyCtepDcpToTrial();
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_non_ct_gov.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendVerifyCtGovSectionsIgnoredWhenIndicatorIsFalse()
            throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_non_ct_gov_but_with_data.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        upd.setSponsor(null);
        upd.setResponsibleParty(null);
        upd.setRegulatoryInformation(null);
        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendAndChangeResponsiblePartyType() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_responsible_party_si.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendAndChangeLeadOrgID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_change_lead_org_id.xml");
        deactivateTrialByLeadOrgId(upd.getLeadOrgTrialID());

        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(
                response, upd.getLeadOrgTrialID());
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendNonInterventional() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_nonInt_success.xml");
        addDummyCtepDcpToTrial();
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_non_int.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testUnableToChangeDesignType() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_non_int.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("An amendment cannot change a trial from Interventional to Non-Interventional or vice versa"));

    }

    @Test
    public void testAmendmentsByOwnersOnly() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);
        removeOwners(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("Amendment to the trial can only be submitted by either an owner of the trial "
                        + "or a lead organization admin"));

    }

    @Test
    public void testAmendmentsProhibitedForAbbreviatedTrials() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);
        makeAbbreviated(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("Amendment to Proprietary trial is not supported"));

    }

    @Test
    public void testAmendByNciId() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("nci",
                rConf.getNciTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendByCtepId() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");
        clickAndWait("link=General Trial Details");
        selenium.select("id=otherIdentifierType", "label=" + "CTEP Identifier");
        selenium.type("id=otherIdentifierOrg", "CTEP00000000000001");
        clickAndWait("id=otherIdbtnid");
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("ctep",
                "CTEP00000000000001", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyAmendment(upd, uConf);

    }

    @Test
    public void testAmendOnTopOfMinimalDataset() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        addDummyCtepDcpToTrial();
        prepareTrialForAmendment(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

    }

    private void verifyAmendment(CompleteTrialAmendment upd,
            TrialRegistrationConfirmation uConf) throws JAXBException,
            SAXException, SQLException {
        super.verifyRegistration(uConf, upd);
    }

    @Override
    protected void verifyTrialIdentification(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) throws JAXBException,
            SAXException {
        super.verifyTrialIdentification(reg, conf);

        CompleteTrialAmendment am = (CompleteTrialAmendment) reg;

        assertEquals(
                am.getAmendmentNumber(),
                selenium.getText("xpath=//div[@class='summarybox']//div[@class='float33_first']/div[3]/span[2]"));
        assertEquals(
                DateFormatUtils.format(am.getAmendmentDate()
                        .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getText("xpath=//div[@class='summarybox']/div[3]/div[4]/span[2]"));

        assertEquals(am.getCtepIdentifier(),
                getTrialIdentificationTableCellValue("CTEP Identifier"));
        assertEquals(am.getDcpIdentifier(),
                getTrialIdentificationTableCellValue("DCP Identifier"));

    }

    @Override
    protected void verifyTrialDocuments(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) {

        super.verifyTrialDocuments(reg, conf);

        CompleteTrialAmendment am = (CompleteTrialAmendment) reg;
        verifyDocument(am.getChangeMemoDocument(), "Change Memo Document");
        verifyDocument(am.getProtocolHighlightDocument(),
                "Protocol Highlighted Document");
    }

    @Test
    public void testSchemaValidation() throws Exception {
        HttpResponse response = amendTrialFromFile("pa", "1",
                "/integration_amend_schema_violation.xml");
        assertEquals(400, getReponseCode(response));

    }

    @SuppressWarnings("unchecked")
    protected HttpResponse amendTrialFromJAXBElement(String idType,
            String trialID, CompleteTrialAmendment o)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<CompleteTrialAmendment>(new QName(
                "gov.nih.nci.pa.webservices.types", "CompleteTrialAmendment"),
                CompleteTrialAmendment.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = putEntityAndReturnResponse(entity, serviceURL
                + "/" + idType + "/" + trialID);
        return response;

    }

    @SuppressWarnings("unchecked")
    protected HttpResponse amendTrialFromFile(String idType, String trialID,
            String file) throws ClientProtocolException, IOException,
            ParseException, JAXBException, SQLException {
        StringEntity entity = new StringEntity(IOUtils.toString(getClass()
                .getResourceAsStream(file)));
        HttpResponse response = putEntityAndReturnResponse(entity, serviceURL
                + "/" + idType + "/" + trialID);
        return response;

    }

    @SuppressWarnings("unchecked")
    protected CompleteTrialAmendment readCompleteTrialAmendmentFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialAmendment o = ((JAXBElement<CompleteTrialAmendment>) u
                .unmarshal(url)).getValue();
        return o;
    }

    private void prepareTrialForAmendment(TrialRegistrationConfirmation rConf)
            throws SQLException {
        TrialInfo ti = new TrialInfo();
        pickUsers(ti);
        ti.id = rConf.getPaTrialID();

        for (MilestoneCode code : MilestoneCode.ADMIN_SEQ) {
            addMilestone(ti, code.name());
        }
        for (MilestoneCode code : MilestoneCode.SCIENTIFIC_SEQ) {
            addMilestone(ti, code.name());
        }
        for (MilestoneCode code : MilestoneCode.TRS_AND_ABOVE) {
            addMilestone(ti, code.name());
        }
        addDWS(ti, DocumentWorkflowStatusCode.ABSTRACTED.name());
        addDWS(ti, DocumentWorkflowStatusCode.VERIFICATION_PENDING.name());
        addDWS(ti,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.name());

    }
}
