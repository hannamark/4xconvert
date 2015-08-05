package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.StudySourceInterceptor;
import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.webservices.types.AccrualDiseaseTerminology;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ExpandedAccessType;
import gov.nih.nci.pa.webservices.types.GrantorCode;
import gov.nih.nci.pa.webservices.types.HolderType;
import gov.nih.nci.pa.webservices.types.NciDivisionProgramCode;
import gov.nih.nci.pa.webservices.types.NihInstitutionCode;
import gov.nih.nci.pa.webservices.types.PrimaryPurpose;
import gov.nih.nci.pa.webservices.types.SecondaryPurpose;
import gov.nih.nci.pa.webservices.types.StudyModelCode;
import gov.nih.nci.pa.webservices.types.TimePerspectiveCode;
import gov.nih.nci.pa.webservices.types.TrialCategory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.pa.webservices.types.TrialStatusCode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.xml.sax.SAXException;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisterCompleteTrialTest extends AbstractRestServiceTest {

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
    public void testRegisterSuccess() throws Exception {
        registerAndVerify("/integration_register_complete_success.xml");
    }

    @Test
    public void testRegisterNonCtGovIgnoreSections() throws Exception {
        registerAndVerify("/integration_register_complete_non_ctgov_but_with_data.xml");
    }

    @Test
    public void testRegisterNonCtGov() throws Exception {
        registerAndVerify("/integration_register_complete_nonCtGov_success.xml");
    }

    @Test
    public void testRegisterNonInterventional() throws Exception {
        registerAndVerify("/integration_register_complete_nonInt_success.xml");
    }

    @Test
    public void testRegisterWithNewOrgsAndPersons() throws Exception {
        registerAndVerify("/integration_register_complete_new_orgs_persons_success.xml");
    }

    @Test
    public void testRegisterWithOrgsAndPersonsIdentifiedByCtepID()
            throws Exception {
        registerAndVerify("/integration_register_complete_orgs_persons_ctepid.xml");
    }

    @Test
    public void testRegisterResponsiblePartyPI() throws Exception {
        registerAndVerify("/integration_register_complete_resp_party_pi.xml");
    }

    @Test
    public void testRegisterResponsiblePartySI() throws Exception {
        registerAndVerify("/integration_register_complete_resp_party_si.xml");
    }

    @Test
    public void testRegisterSchemaValidation() throws Exception {
        verifyFailureToRegister(
                "/integration_register_complete_schema_violation.xml", 400,
                "cvc-enumeration-valid");
    }

    @Test
    public void testRegisterBusinessValidation() throws Exception {
        verifyFailureToRegister(
                "/integration_register_complete_validation_error.xml", 400,
                "Validation Exception");
    }

    @Test
    public void testRegisterMinimalData() throws Exception {
        registerAndVerify("/integration_register_complete_minimal_dataset.xml");
    }

    @Test
    public void testRegisterFundedByNciGrant() throws Exception {
        registerAndVerify("/integration_register_complete_nci_grant.xml");
    }

    @Test
    public void testRegisterRotateEnumValues() throws Exception {

        for (TrialCategory v : TrialCategory.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.setCategory(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyNCISpecificInformation(reg, conf);
        }

        for (PrimaryPurpose v : PrimaryPurpose.values()) {
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            reg.setPrimaryPurpose(v);
            reg.setPrimaryPurposeOtherDescription(v != PrimaryPurpose.OTHER ? null
                    : "PrimaryPurpose.OTHER");
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyTrialDesign(reg, conf);
        }

        for (SecondaryPurpose v : SecondaryPurpose.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.getInterventionalDesign().setSecondaryPurpose(v);
            reg.getInterventionalDesign().setSecondaryPurposeOtherDescription(
                    v != SecondaryPurpose.OTHER ? null
                            : "SecondaryPurpose.OTHER");
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyTrialDesign(reg, conf);
        }

        for (AccrualDiseaseTerminology v : AccrualDiseaseTerminology.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.setAccrualDiseaseTerminology(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyDiseaseTerm(reg, conf);
        }

        for (StudyModelCode v : StudyModelCode.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_nonInt_success.xml");
            reg.getNonInterventionalDesign().setStudyModelCode(v);
            reg.getNonInterventionalDesign().setStudyModelCodeOtherDescription(
                    v != StudyModelCode.OTHER ? null : "StudyModelCode.OTHER");
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyTrialDesign(reg, conf);
        }

        for (TimePerspectiveCode v : TimePerspectiveCode.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_nonInt_success.xml");
            reg.getNonInterventionalDesign().setTimePerspectiveCode(v);
            reg.getNonInterventionalDesign()
                    .setTimePerspectiveCodeOtherDescription(
                            v != TimePerspectiveCode.OTHER ? null
                                    : "TimePerspectiveCode.OTHER");
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyTrialDesign(reg, conf);
        }

        for (TrialStatusCode v : TrialStatusCode.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.setTrialStatus(v);
            if (StudyStatusCode.getByCode(v.value()).requiresReasonText()) {
                reg.setWhyStopped("WhyStopped");
            }
            if (v.value().contains("Complete")) {
                reg.getPrimaryCompletionDate().setValue(
                        DatatypeFactory.newInstance().newXMLGregorianCalendar(
                                new GregorianCalendar()));
                reg.getPrimaryCompletionDate().setType("Actual");
                reg.getCompletionDate().setValue(
                        DatatypeFactory.newInstance().newXMLGregorianCalendar(
                                new GregorianCalendar()));
                reg.getCompletionDate().setType("Actual");
            }
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyTrialStatus(reg, conf);
        }

        for (HolderType v : HolderType.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            if (v == HolderType.NIH || v == HolderType.NCI) {
                continue;
            }
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.getInd().get(0).setHolderType(v);
            reg.getIde().get(0).setHolderType(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyIndIde(reg, conf);
        }

        for (GrantorCode v : new GrantorCode[] { GrantorCode.CBER,
                GrantorCode.CDER }) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.getInd().get(0).setGrantor(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyIndIde(reg, conf);
        }

        for (NihInstitutionCode v : NihInstitutionCode.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.getInd().get(0).setNihInstitution(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyIndIde(reg, conf);
        }

        for (NciDivisionProgramCode v : NciDivisionProgramCode.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.getIde().get(0).setNciDivisionProgramCode(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyIndIde(reg, conf);
        }

        for (ExpandedAccessType v : ExpandedAccessType.values()) {
            LOG.info("Rotating " + v.getClass() + ": " + v.name());
            CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
            reg.getInd().get(0).setExpandedAccessType(v);
            deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
            TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
            logInFindAndAcceptTrial(conf);
            verifyIndIde(reg, conf);
        }
    }

    @Test
    public void testRegistrationWithoutTrialOwnerElement_PO9091()
            throws Exception {
        registerAndVerify("/integration_register_complete_no_owner.xml");
    }

    private void verifyFailureToRegister(String file, int code,
            String expectedErrMsg) throws JAXBException, SAXException,
            UnsupportedEncodingException, ClientProtocolException, IOException {
        HttpResponse response = submitRegistrationAndReturnResponse(file);
        assertEquals(code, getReponseCode(response));

        String respBody = EntityUtils.toString(response.getEntity(), "utf-8");
        LOG.info(respBody);
        assertTrue(respBody.contains(expectedErrMsg));
    }

    private void registerAndVerify(String file) throws SQLException,
            ClientProtocolException, ParseException, IOException,
            JAXBException, SAXException {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(file);
        deactivateTrialByLeadOrgId(reg.getLeadOrgTrialID());
        restartEmailServer();
        TrialRegistrationConfirmation conf = registerTrialFromFile(file);
        verifyRegistration(conf, reg);
        TrialInfo trial = new TrialInfo();
        trial.id = conf.getPaTrialID();
        if (reg.isClinicalTrialsDotGovXmlRequired()) {
            assertEquals("false",
                    getTrialField(trial, "DELAYED_POSTING_INDICATOR")
                            .toString());
            assertEquals(3, server.getReceivedEmailSize());
            Iterator<SmtpMessage> emailIter = server.getReceivedEmail();
            for (int i = 0; emailIter.hasNext(); i++) {
                SmtpMessage email = (SmtpMessage) emailIter.next();
                String body = email.getBody();
                System.out.println(body);
                switch (i) {
                case 0:
                    verifyCreateBodyDSPWarning(body);
                    break;
                case 1:
                    verifyCreateBodyDSPCTRO(body);
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * @param body
     */
    private void verifyCreateBodyDSPWarning(final String body) {
        assertTrue(body
                .contains("WARNING:</b> The trial submitted has a Delayed Posting Indicator value of \"Yes\""));
    }

    /**
     * @param body
     */
    private void verifyCreateBodyDSPCTRO(final String body) {
        assertTrue(body
                .contains("Dear CTRO Staff,</p><p>The trial below was submitted with the value for the Delayed Posting Indicator set to \"Yes\":"));
    }

}
