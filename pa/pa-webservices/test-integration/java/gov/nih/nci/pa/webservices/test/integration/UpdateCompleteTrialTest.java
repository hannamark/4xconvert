package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class UpdateCompleteTrialTest extends AbstractRestServiceTest {

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
    public void testUpdate() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyUpdate(upd, uConf);

    }
    
    @Test
    public void testUpdateDoesNotResetCtroOverride() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        enableCtroOverride(rConf.getPaTrialID());
        
        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        clickAndWait("link=NCI Specific Information");
        assertFalse(selenium.isTextPresent("Send XML to ClinicalTrials.gov?:"));

    }

    @Test
    public void testUpdateByNciID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete.xml");
        HttpResponse response = updateTrialFromJAXBElement("nci",
                rConf.getNciTrialID(), upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyUpdate(upd, uConf);

    }

    @Test
    public void testUpdateByCtepID() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        clickAndWait("link=General Trial Details");
        selenium.select("id=otherIdentifierType", "label=" + "CTEP Identifier");
        selenium.type("id=otherIdentifierOrg", "CTEP00001");
        clickAndWait("id=otherIdbtnid");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete.xml");
        HttpResponse response = updateTrialFromJAXBElement("ctep", "CTEP00001",
                upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        verifyUpdate(upd, uConf);

    }

    @Test
    public void testUpdateExistingNctIdStays() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        clickAndWait("link=General Trial Details");
        selenium.select("id=otherIdentifierType", "label="
                + "ClinicalTrials.gov Identifier");
        selenium.type("id=otherIdentifierOrg", "NCT23489057389475");
        clickAndWait("id=otherIdbtnid");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        clickAndWait("link=Trial Identification");
        assertEquals(
                "NCT23489057389475",
                getTrialIdentificationTableCellValue("ClinicalTrials.gov Identifier"));

        clickAndWait("link=Trial History");
        clickAndWait("id=updatesId");
        assertFalse(selenium
                .isTextPresent("ClinicalTrials.gov Identifier was added"));

    }

    @Test
    public void testUpdateOtherDocumentsDoNotGetReplaced() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete_other_docs.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        clickAndWait("link=Trial Related Documents");
        assertTrue(selenium.isTextPresent(upd.getOtherDocument().get(0)
                .getFilename()));
        assertTrue(selenium.isTextPresent("other.pdf"));

    }

    @Test
    public void testUpdateOtherIdentifiersGetMerged() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_success.xml");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete_other_ids.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        clickAndWait("link=Trial Identification");
        assertEquals("818280234, ADDED01",
                getTrialIdentificationTableCellValue("Other Identifier"));

    }

    @Test
    public void testSchemaValidation() throws Exception {
        HttpResponse response = updateTrialFromFile("pa", "1",
                "/integration_update_complete_schema_violation.xml");
        assertEquals(400, getReponseCode(response));

    }

    @Test
    public void testUpdateBizValidation() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete_biz_validation.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody.contains("Validation Exception"));
    }

    @Test
    public void testUpdateRestrictedToCompleteTrials() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        makeAbbreviated(rConf);

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete_no_changes.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("Update to Proprietary trial is not supported"));
    }

    @Test
    public void testUpdateRestrictedToOwners() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");
        removeOwners(rConf);

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete_no_changes.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        assertEquals(400, getReponseCode(response));
        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        assertTrue(respBody
                .contains("Update to the trial can only be submitted by either "
                        + "an owner of the trial or a lead organization admin"));
    }

    @Test
    public void testUpdateNoChanges() throws Exception {
        TrialRegistrationConfirmation rConf = register("/integration_register_complete_minimal_dataset.xml");

        CompleteTrialUpdate upd = readCompleteTrialUpdateFromFile("/integration_update_complete_no_changes.xml");
        HttpResponse response = updateTrialFromJAXBElement("pa",
                rConf.getPaTrialID() + "", upd);
        TrialRegistrationConfirmation uConf = processTrialRegistrationResponseAndDoBasicVerification(response);
        assertEquals(rConf.getPaTrialID(), uConf.getPaTrialID());
        assertEquals(rConf.getNciTrialID(), uConf.getNciTrialID());

        clickAndWait("link=Trial History");
        clickAndWait("id=updatesId");
        assertTrue(selenium.isTextPresent("Nothing found to display"));
    }

    private void verifyUpdate(CompleteTrialUpdate upd,
            TrialRegistrationConfirmation conf) {
        verifyTrialIdentification(upd, conf);
        verifyTrialHistory(upd, conf);
        verifyDiseaseTerm(upd, conf);
        verifyTrialStatus(upd, conf);
        verifyGrant(upd, conf);
        verifyTrialDocuments(upd, conf);
    }

    private void verifyTrialDocuments(CompleteTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Related Documents");
        verifyDocument(reg.getProtocolDocument(), "Protocol Document");
        verifyDocument(reg.getIrbApprovalDocument(), "IRB Approval Document");
        verifyDocument(reg.getParticipatingSitesDocument(),
                "Participating sites");
        verifyDocument(reg.getInformedConsentDocument(),
                "Informed Consent Document");
        verifyDocument(reg.getOtherDocument().isEmpty() ? null : reg
                .getOtherDocument().get(0), "Other");

    }

    protected void verifyDocument(TrialDocument doc, String type) {
        if (doc != null) {
            String file = doc.getFilename();
            for (int i = 1; i < 20; i++) {
                String text = selenium
                        .getText("xpath=//form//table[@id='row']//tr[" + i
                                + "]//td[2]");
                if (type.equalsIgnoreCase(StringUtils.trim(text))) {
                    assertEquals(
                            file,
                            selenium.getText("xpath=//form//table[@id='row']//tr["
                                    + i + "]//td[1]"));
                    break;
                }
            }
        }
    }

    private void verifyGrant(CompleteTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Funding");
        if (reg.getGrant().isEmpty()) {
            assertTrue(selenium
                    .isTextPresent("No funding records exist on the trial"));
        } else {
            final Grant grant = reg.getGrant().get(0);
            assertEquals(grant.getFundingMechanism(),
                    selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]"));
            assertEquals(grant.getNihInstitutionCode(),
                    selenium.getText("xpath=//table[@id='row']//tr[1]//td[2]"));
            assertEquals(grant.getSerialNumber(),
                    selenium.getText("xpath=//table[@id='row']//tr[1]//td[3]"));
            assertEquals(grant.getNciDivisionProgramCode().value(),
                    selenium.getText("xpath=//table[@id='row']//tr[1]//td[4]"));
        }
    }

    private void verifyTrialStatus(CompleteTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Status");
        assertEquals(reg.getTrialStatus().value(),
                selenium.getSelectedValue("id=currentTrialStatus"));
        assertEquals(DateFormatUtils.format(reg.getTrialStatusDate()
                .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=statusDate"));
        assertEquals(StringUtils.defaultString(reg.getWhyStopped()),
                selenium.getValue("id=statusReason"));

        assertEquals(
                DateFormatUtils.format(reg.getTrialStartDate().getValue()
                        .toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=startDate"));
        if (reg.getTrialStartDate().getType().equals("Actual")) {
            assertTrue(selenium.isChecked("id=startDateTypeActual"));
        } else {
            assertTrue(selenium.isChecked("id=startDateTypeAnticipated"));
        }

        assertEquals(
                DateFormatUtils.format(reg.getPrimaryCompletionDate()
                        .getValue().toGregorianCalendar(), "MM/dd/yyyy"),
                selenium.getValue("id=primaryCompletionDate"));
        if (reg.getPrimaryCompletionDate().getType().equals("Actual")) {
            assertTrue(selenium.isChecked("id=primaryCompletionDateTypeActual"));
        } else {
            assertTrue(selenium
                    .isChecked("id=primaryCompletionDateTypeAnticipated"));
        }

        if (reg.getCompletionDate() != null) {
            assertEquals(
                    DateFormatUtils.format(reg.getCompletionDate().getValue()
                            .toGregorianCalendar(), "MM/dd/yyyy"),
                    selenium.getValue("id=completionDate"));
            if (reg.getCompletionDate().getType().equals("Actual")) {
                assertTrue(selenium.isChecked("id=completionDateTypeActual"));
            } else {
                assertTrue(selenium
                        .isChecked("id=completionDateTypeAnticipated"));
            }
        } else {
            assertEquals("", selenium.getValue("id=completionDate"));
            assertFalse(selenium.isChecked("id=completionDateTypeActual"));
            assertFalse(selenium.isChecked("id=completionDateTypeAnticipated"));
        }

    }

    private void verifyDiseaseTerm(CompleteTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Manage Accrual Access");
        assertEquals(reg.getAccrualDiseaseTerminology().value(),
                selenium.getValue("id=accrualDiseaseTerminology"));
    }

    private void verifyTrialHistory(CompleteTrialUpdate upd,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial History");
        clickAndWait("id=updatesId");
        assertTrue(selenium.isTextPresent("One item found"));
        assertTrue(selenium.isTextPresent("Trial Start Date was updated"));
        assertTrue(selenium
                .isTextPresent("Trial Primary Completion Date was updated"));
        assertTrue(selenium.isTextPresent("Trial Completion Date was updated"));
        assertTrue(selenium
                .isTextPresent("Accrual Disease Terminology was updated"));
        assertTrue(selenium.isTextPresent("Grant information was updated"));
        assertTrue(selenium
                .isTextPresent("Trial Status and/or Trial Status Date were updated"));
        assertTrue(selenium
                .isTextPresent("ClinicalTrials.gov Identifier was added"));
        assertTrue(selenium
                .isTextPresent("Protocol Document document was updated"));
        assertTrue(selenium
                .isTextPresent("IRB Approval Document document was updated"));
        assertTrue(selenium
                .isTextPresent("Participating sites document was updated"));
        assertTrue(selenium
                .isTextPresent("Informed Consent Document document was updated"));
        assertTrue(selenium.isTextPresent("Other document was updated"));
        clickAndWaitAjax("link=Acknowledge");
    }

    private void verifyTrialIdentification(CompleteTrialUpdate reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Identification");

        if (reg.getClinicalTrialsDotGovTrialID() != null) {
            assertEquals(
                    reg.getClinicalTrialsDotGovTrialID(),
                    getTrialIdentificationTableCellValue("ClinicalTrials.gov Identifier"));
        }
        if (!reg.getOtherTrialID().isEmpty()) {
            assertEquals(reg.getOtherTrialID().get(0),
                    getTrialIdentificationTableCellValue("Other Identifier"));
        }

    }

    @SuppressWarnings("unchecked")
    protected HttpResponse updateTrialFromJAXBElement(String idType,
            String trialID, CompleteTrialUpdate o)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<CompleteTrialUpdate>(new QName(
                "gov.nih.nci.pa.webservices.types", "CompleteTrialUpdate"),
                CompleteTrialUpdate.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = submitEntityAndReturnResponse(entity,
                serviceURL + "/" + idType + "/" + trialID);
        return response;

    }

}
