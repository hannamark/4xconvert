package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

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
        enableCtroOverride(rConf);

        CompleteTrialAmendment upd = readCompleteTrialAmendmentFromFile("/integration_amend_complete.xml");
        HttpResponse response = amendTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);

        logInFindAndAcceptTrial(uConf);
        clickAndWait("link=NCI Specific Information");
        assertTrue(selenium.isChecked("ctroOverride"));

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
        HttpResponse response = putEntityAndReturnResponse(entity,
                serviceURL + "/" + idType + "/" + trialID);
        return response;

    }

    @SuppressWarnings("unchecked")
    protected HttpResponse amendTrialFromFile(String idType, String trialID,
            String file) throws ClientProtocolException, IOException,
            ParseException, JAXBException, SQLException {
        StringEntity entity = new StringEntity(IOUtils.toString(getClass()
                .getResourceAsStream(file)));
        HttpResponse response = putEntityAndReturnResponse(entity,
                serviceURL + "/" + idType + "/" + trialID);
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
