package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.pa.enums.StudyStatusCode;
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
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
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
    public void testPrimaryCompletionDateAsNAForDcpTrials() throws Exception {
        String uuid = RandomStringUtils.randomAlphabetic(12);
        System.out.println(uuid);
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile("/integration_register_complete_success.xml");
        reg.setLeadOrgTrialID(uuid);
        reg.setDcpIdentifier(uuid);

        // N/A date type cannot be used for Trial Start Date or Completion Date.
        reg.getTrialStartDate().setType("N/A");
        HttpResponse response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(500, "Start date cannot have type of 'N/A'",
                response);
        reg.getTrialStartDate().setType("Actual");

        reg.getCompletionDate().setType("N/A");
        response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(500,
                "Completion date cannot have type of 'N/A'", response);
        reg.getCompletionDate().setType("Anticipated");

        // If PCD is specified, it cannot be N/A.
        reg.getPrimaryCompletionDate().getValue().setType("N/A");
        response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(400,
                "When the Primary Completion Date Type is set to 'N/A', "
                        + "the Primary Completion Date must be null. ",
                response);

        // Only DCP trials can have N/A PCD.
        reg.setDcpIdentifier(null);
        reg.getPrimaryCompletionDate().getValue().setValue(null);
        reg.getPrimaryCompletionDate().getValue().setType("N/A");
        reg.getPrimaryCompletionDate().setNil(true);
        response = submitRegistrationAndReturnResponse(reg);
        verifyResponseHasFailure(
                400,
                "Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'",
                response);

        // Finally, register a DCP trial with N/A PCD.
        reg.setDcpIdentifier(uuid);
        reg.getPrimaryCompletionDate().getValue().setValue(null);
        reg.getPrimaryCompletionDate().getValue().setType("N/A");
        reg.getPrimaryCompletionDate().setNil(true);
        TrialRegistrationConfirmation conf = registerTrialFromJAXBElement(reg);
        logInFindAndAcceptTrial(conf);
        verifyTrialStatus(reg, conf);

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
        registerAndVerify("/integration_register_complete_success_no_dcp.xml");
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
    public void testInvalidRegistrationTransactionRollback() throws Exception {
        final QueryRunner runner = new QueryRunner();
        runner.update(connection, "update pa_properties set name = '_trial.register.body' where name = 'trial.register.body'");
        for (int i =0; i <=3; i++) {
            int countBefore = ((Number) runner.query(connection,
                    "select count(*) from study_protocol", new ArrayHandler())[0])
                    .intValue();
            verifyFailureToRegister("/integration_register_complete_invalid_startdate_dataset.xml",  500,
                    "PA_PROPERTIES does not have entry for  trial.register.body");
            int countAfter = ((Number) runner.query(connection,
                    "select count(*) from study_protocol", new ArrayHandler())[0])
                    .intValue();
            assertEquals( i
                    + ") Trial registration ran non-transactionally and left junk in the database!!!",
                    countBefore, countAfter);
        }

        runner.update(connection, "update pa_properties set name = 'trial.register.body' where name = '_trial.register.body'");
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
                reg.getPrimaryCompletionDate()
                        .getValue()
                        .setValue(
                                DatatypeFactory.newInstance()
                                        .newXMLGregorianCalendar(
                                                new GregorianCalendar()));
                reg.getPrimaryCompletionDate().getValue().setType("Actual");
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
        verifyResponseHasFailure(code, expectedErrMsg, response);
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
                String subject = email.getHeaderValue("Subject");
                System.out.println(body);
                switch (i) {
                case 0:
                    verifyCreateBodyDSPWarning(body);
                    verifyCreateSubjectDSPWarning(subject);
                    break;
                case 1:
                    verifyCreateBodyDSPCTRO(body);
                    verifyCreateSubjectDSPCTRO(subject);
                    break;
                default:
                    break;
                }
            }
        }
    }
    
    /**
     * @param subject
     */
    private void verifyCreateSubjectDSPWarning(final String subject) {
        assertFalse(subject
                .contains("<table>"));    	
        assertTrue(subject.contains("NCI CTRP: Trial RECORD CREATED for"));        
    }

    /**
     * @param body
     */
    private void verifyCreateBodyDSPWarning(final String body) {
        assertTrue(body
                .contains("WARNING:</b> The trial submitted has a Delayed Posting Indicator value of \"Yes\""));
    }
    
    /**
     * @param subject
     */
    private void verifyCreateSubjectDSPCTRO(final String subject) {
        assertFalse(subject
                .contains("<table>"));        
        assertTrue(subject
                .contains("Delayed Posting Indicator set to \"Yes\""));       
    }

    /**
     * @param body
     */
    private void verifyCreateBodyDSPCTRO(final String body) {
        assertTrue(body
                .contains("Dear CTRO Staff,</p><p>The trial below was submitted with the value for the Delayed Posting Indicator set to \"Yes\":"));
    }

}
