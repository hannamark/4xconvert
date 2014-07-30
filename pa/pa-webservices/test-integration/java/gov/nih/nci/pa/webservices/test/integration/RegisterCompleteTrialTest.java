package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.webservices.types.AccrualDiseaseTerminology;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.ExpandedAccessType;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.GrantorCode;
import gov.nih.nci.pa.webservices.types.HolderType;
import gov.nih.nci.pa.webservices.types.INDIDE;
import gov.nih.nci.pa.webservices.types.NciDivisionProgramCode;
import gov.nih.nci.pa.webservices.types.NihInstitutionCode;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.PrimaryPurpose;
import gov.nih.nci.pa.webservices.types.ResponsiblePartyType;
import gov.nih.nci.pa.webservices.types.SecondaryPurpose;
import gov.nih.nci.pa.webservices.types.StudyModelCode;
import gov.nih.nci.pa.webservices.types.TimePerspectiveCode;
import gov.nih.nci.pa.webservices.types.TrialCategory;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.pa.webservices.types.TrialStatusCode;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Denis G. Krylov
 * 
 */
public class RegisterCompleteTrialTest extends AbstractRestServiceTest {

    public void setUp() throws Exception {
        super.setUp("/registration/complete");

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
        TrialRegistrationConfirmation conf = registerTrialFromFile(file);
        verifyRegistration(conf, reg);

    }

    private void verifyRegistration(TrialRegistrationConfirmation conf,
            CompleteTrialRegistration reg) throws JAXBException, SAXException,
            SQLException {

        logInFindAndAcceptTrial(conf);

        verifyTrialIdentification(reg, conf);
        verifyDiseaseTerm(reg, conf);
        verifyOwnership(reg, conf);
        verifyGeneralTrialDetails(reg, conf);
        verifyNCISpecificInformation(reg, conf);
        verifyRegulatoryInfo(reg, conf);
        verifyIndIde(reg, conf);
        verifyTrialStatus(reg, conf);
        verifyGrant(reg, conf);
        verifyTrialDocuments(reg, conf);
        verifyTrialDesign(reg, conf);

    }

    private void verifyTrialDesign(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        if (reg.getInterventionalDesign() != null) {
            verifyInterventialDesign(reg, conf);
        } else {
            verifyNonInterventialDesign(reg, conf);
        }
    }

    private void verifyNonInterventialDesign(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Design Details");
        assertEquals("Non-Interventional",
                selenium.getSelectedLabel("id=webDTO.studyType"));
        assertEquals(reg.getNonInterventionalDesign().getTrialType().value(),
                selenium.getSelectedLabel("id=webDTO.studySubtypeCode"));
        assertEquals(reg.getPrimaryPurpose().value(),
                selenium.getSelectedLabel("id=webDTO.primaryPurposeCode"));
        assertEquals(StringUtils.defaultString(reg
                .getPrimaryPurposeOtherDescription()),
                selenium.getValue("id=webDTO.primaryPurposeOtherText"));
        assertEquals(reg.getPhase(),
                selenium.getSelectedLabel("id=webDTO.phaseCode"));
        assertEquals(reg.getNonInterventionalDesign().getStudyModelCode()
                .value(), selenium.getSelectedLabel("id=webDTO.studyModelCode"));
        assertEquals(StringUtils.defaultString(reg.getNonInterventionalDesign()
                .getStudyModelCodeOtherDescription()),
                selenium.getValue("id=webDTO.studyModelOtherText"));
        assertEquals(reg.getNonInterventionalDesign().getTimePerspectiveCode()
                .value(),
                selenium.getSelectedLabel("id=webDTO.timePerspectiveCode"));
        assertEquals(StringUtils.defaultString(reg.getNonInterventionalDesign()
                .getTimePerspectiveCodeOtherDescription()),
                selenium.getValue("id=webDTO.timePerspectiveOtherText"));
    }

    private void verifyInterventialDesign(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Design Details");
        assertEquals("Interventional", selenium.getSelectedLabel("id=study"));
        assertEquals(reg.getPrimaryPurpose().value(),
                selenium.getSelectedLabel("id=webDTO.primaryPurposeCode"));
        assertEquals(StringUtils.defaultString(reg
                .getPrimaryPurposeOtherDescription()),
                selenium.getValue("id=otherText"));
        assertEquals(
                reg.getInterventionalDesign().getSecondaryPurpose() != null ? reg
                        .getInterventionalDesign().getSecondaryPurpose()
                        .value()
                        : "",
                selenium.getSelectedLabel("id=webDTO.secondaryPurposes"));
        assertEquals(StringUtils.defaultString(reg.getInterventionalDesign()
                .getSecondaryPurposeOtherDescription()),
                selenium.getValue("id=secondaryOtherText"));
        assertEquals(reg.getPhase(),
                selenium.getSelectedLabel("id=webDTO.phaseCode"));

    }

    private void verifyTrialDocuments(CompleteTrialRegistration reg,
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

    private void verifyDocument(TrialDocument doc, String type) {
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

    private void verifyGrant(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Funding");
        if (reg.getGrant().isEmpty()) {
            assertTrue(selenium
                    .isTextPresent("No funding records exist on the trial"));
        } else {
            final Grant grant = reg.getGrant().get(0);
            assertTrue(selenium
                    .isChecked(reg.isFundedByNciGrant() ? "id=nciGranttrue"
                            : "id=nciGrantfalse"));
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

    private void verifyTrialStatus(CompleteTrialRegistration reg,
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

    private void verifyIndIde(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial IND/IDE");
        if (!reg.getInd().isEmpty())
            verifyIndIdeEntry("IND", reg.getInd().get(0), 1);
        if (!reg.getIde().isEmpty())
            verifyIndIdeEntry("IDE", reg.getIde().get(0), 2);

        if (reg.getInd().isEmpty() && reg.getIde().isEmpty()) {
            assertTrue(selenium
                    .isTextPresent("No IND/IDE records exist on the trial."));
        }

    }

    private void verifyIndIdeEntry(String type, INDIDE indide, int i) {
        assertEquals(
                type,
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[1]"));
        assertEquals(
                indide.getNumber(),
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[2]"));
        assertEquals(
                indide.getGrantor().value(),
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[3]"));
        assertEquals(
                indide.getHolderType().value(),
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[4]"));

        if (indide.getNihInstitution() != null
                && indide.getHolderType() == HolderType.NIH) {
            assertEquals(
                    NihInstituteCode.valueOf(indide.getNihInstitution().name())
                            .getCode(),
                    selenium.getText("xpath=//table[@id='row']//tr[" + i
                            + "]//td[5]"));
        } else {
            assertTrue(selenium.getText(
                    "xpath=//table[@id='row']//tr[" + i + "]//td[5]")
                    .equals(""));
        }
        if (indide.getNciDivisionProgramCode() != null
                && indide.getHolderType() == HolderType.NCI) {
            assertEquals(
                    indide.getNciDivisionProgramCode().value(),
                    selenium.getText("xpath=//table[@id='row']//tr[" + i
                            + "]//td[6]"));
        } else {
            assertEquals(
                    "",
                    selenium.getText("xpath=//table[@id='row']//tr[" + i
                            + "]//td[6]"));
        }
        assertEquals(
                indide.isExpandedAccess() ? "Yes" : "No",
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[7]"));
        assertEquals(
                indide.getExpandedAccessType().value(),
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[8]"));
        assertEquals(
                indide.isExempt() ? "Yes" : "No",
                selenium.getText("xpath=//table[@id='row']//tr[" + i
                        + "]//td[9]"));
    }

    private void verifyRegulatoryInfo(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Regulatory Information");
        if (reg.isClinicalTrialsDotGovXmlRequired()) {
            assertEquals("United States",
                    selenium.getSelectedLabel("id=countries"));
            assertEquals(reg.getRegulatoryInformation().getAuthorityName(),
                    selenium.getSelectedLabel("id=auths"));
            assertEquals(
                    reg.getRegulatoryInformation().isFdaRegulated() ? "Yes"
                            : "No", selenium.getSelectedLabel("id=fdaindid"));
            assertEquals(reg.getRegulatoryInformation().isSection801() ? "Yes"
                    : "No", selenium.getSelectedLabel("id=sec801id"));
            assertEquals(
                    reg.getRegulatoryInformation().isDelayedPosting() ? "Yes"
                            : "No",
                    selenium.getSelectedLabel("id=delpostindid"));
            assertEquals(reg.getRegulatoryInformation()
                    .isDataMonitoringCommitteeAppointed() ? "Yes" : "No",
                    selenium.getSelectedLabel("id=datamonid"));
        } else {
            assertEquals("", selenium.getValue("id=countries"));
            assertEquals("", selenium.getValue("id=auths"));
            assertEquals("", selenium.getValue("id=fdaindid"));
            assertEquals("", selenium.getValue("id=datamonid"));
        }
    }

    private void verifyNCISpecificInformation(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) throws SQLException {
        clickAndWait("link=NCI Specific Information");
        assertEquals(reg.getCategory().value(),
                selenium.getSelectedValue("id=summary4TypeCode"));
        verifyOrganizationPoId(
                reg.getSummary4FundingSponsor().get(0),
                selenium.getValue("id=nciSpecificInformationWebDTO.summary4Sponsors[0].orgId"));
        assertEquals(StringUtils.defaultString(reg.getProgramCode()),
                selenium.getValue("id=summary4ProgramCode"));

    }

    private void verifyGeneralTrialDetails(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) throws SQLException {
        clickAndWait("link=General Trial Details");
        verifyOrganizationPoId(reg.getLeadOrganization(),
                selenium.getValue("id=gtdDTO.leadOrganizationIdentifier"));
        verifyPersonPoId(reg.getPi(),
                selenium.getValue("id=gtdDTO.piIdentifier"));

        if (reg.isClinicalTrialsDotGovXmlRequired()) {
            verifyOrganizationPoId(reg.getSponsor(),
                    selenium.getValue("id=sponsorIdentifier"));
            verifyResponsibleParty(reg);
        }

    }

    /**
     * @param reg
     * @throws SQLException
     */
    private void verifyResponsibleParty(CompleteTrialRegistration reg)
            throws SQLException {
        assertEquals(reg.getResponsibleParty().getType().value(),
                selenium.getSelectedLabel("id=gtdDTO.responsiblePartyType"));
        if (reg.getResponsibleParty().getType()
                .equals(ResponsiblePartyType.PRINCIPAL_INVESTIGATOR)) {
            verifyPersonPoId(reg.getPi(),
                    selenium.getValue("id=gtdDTO.responsiblePersonIdentifier"));
            assertEquals(reg.getResponsibleParty().getInvestigatorTitle(),
                    selenium.getValue("id=gtdDTO.responsiblePersonTitle"));
            verifyOrganizationPoId(
                    reg.getResponsibleParty().getInvestigatorAffiliation(),
                    selenium.getValue("id=gtdDTO.responsiblePersonAffiliationOrgId"));
        } else if (reg.getResponsibleParty().getType()
                .equals(ResponsiblePartyType.SPONSOR_INVESTIGATOR)) {
            verifyPersonPoId(reg.getResponsibleParty().getInvestigator(),
                    selenium.getValue("id=gtdDTO.responsiblePersonIdentifier"));
            assertEquals(reg.getResponsibleParty().getInvestigatorTitle(),
                    selenium.getValue("id=gtdDTO.responsiblePersonTitle"));
            verifyOrganizationPoId(
                    reg.getSponsor(),
                    selenium.getValue("id=gtdDTO.responsiblePersonAffiliationOrgId"));
        }
    }

    private void verifyPersonPoId(Person p, String poIdOnScreen)
            throws SQLException {
        if (p.getExistingPerson() != null) {
            if (p.getExistingPerson().getPoID() != null) {
                assertEquals(p.getExistingPerson().getPoID().toString(),
                        poIdOnScreen);
            } else {
                assertEquals(determinePoIdOfPersonByCtepId(p
                        .getExistingPerson().getCtepID()), poIdOnScreen);
            }
        } else {
            assertEquals(determinePoIdOfNewlyCreatedPerson(p.getNewPerson()),
                    poIdOnScreen);
        }
    }

    private String determinePoIdOfNewlyCreatedPerson(
            gov.nih.nci.po.webservices.types.Person p) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (String) runner.query(connection,
                "select o.assigned_identifier from person o "
                        + " where o.first_name='" + p.getFirstName()
                        + "' and o.last_name='" + p.getLastName()
                        + "' order by identifier desc limit 1",
                new ArrayHandler())[0];
    }

    private void verifyOrganizationPoId(Organization org, String poIdOnScreen)
            throws SQLException {
        if (org.getExistingOrganization() != null) {
            if (org.getExistingOrganization().getPoID() != null) {
                assertEquals(
                        org.getExistingOrganization().getPoID().toString(),
                        poIdOnScreen);
            } else {
                assertEquals(determinePoIdOfOrgByCtepId(org
                        .getExistingOrganization().getCtepID()), poIdOnScreen);
            }
        } else {
            assertEquals(
                    determinePoIdOfNewlyCreatedOrg(org.getNewOrganization()),
                    poIdOnScreen);
        }
    }

    private String determinePoIdOfOrgByCtepId(String ctepID) {
        OrganizationSearchCriteriaDTO c = new OrganizationSearchCriteriaDTO();
        c.setCtepId(ctepID);
        try {
            return new MockOrganizationEntityService().search(c, null).get(0)
                    .getIdentifier().getExtension();
        } catch (TooManyResultsException e) {
            throw new RuntimeException(e);

        }
    }

    private String determinePoIdOfPersonByCtepId(String ctepID) {
        PersonSearchCriteriaDTO c = new PersonSearchCriteriaDTO();
        c.setCtepId(ctepID);
        try {
            return new MockPersonEntityService().search(c, null).get(0)
                    .getIdentifier().getExtension();
        } catch (TooManyResultsException e) {
            throw new RuntimeException(e);

        }
    }

    private String determinePoIdOfNewlyCreatedOrg(
            gov.nih.nci.po.webservices.types.Organization org)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (String) runner.query(connection,
                "select o.assigned_identifier from organization o "
                        + " where o.name='" + org.getName()
                        + "' order by identifier desc limit 1",
                new ArrayHandler())[0];
    }

    private void verifyOwnership(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Assign Ownership");
        if (!reg.getTrialOwner().isEmpty()) {
            assertEquals(
                    reg.getTrialOwner().get(0),
                    selenium.getText("xpath=//table[@id='row']//tr[1]//td[2]/a"));
        } else {
            assertTrue(selenium.isTextPresent("Nothing found to display."));
        }

    }

    private void verifyDiseaseTerm(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Manage Accrual Access");
        assertEquals(reg.getAccrualDiseaseTerminology().value(),
                selenium.getValue("id=accrualDiseaseTerminology"));
    }

    private void verifyTrialIdentification(CompleteTrialRegistration reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Identification");

        assertTrue(selenium.getText("id=displaySubmitterLink").contains(
                "submitter-ci"));
        assertTrue(selenium
                .isElementPresent("link=National Cancer Institute Division of Cancer Prevention"));

        assertEquals(reg.getInterventionalDesign() != null ? "Interventional"
                : "Non-interventional",
                getTrialIdentificationTableCellValue("Trial Type"));
        assertEquals(conf.getNciTrialID(),
                getTrialIdentificationTableCellValue("NCI Trial Identifier"));
        assertEquals(
                reg.isClinicalTrialsDotGovXmlRequired() ? "Yes" : "No",
                getTrialIdentificationTableCellValue("ClinicalTrials.gov XML required?"));
        assertEquals(
                reg.getLeadOrgTrialID(),
                getTrialIdentificationTableCellValue("Lead Organization Trial ID"));
        if (reg.getClinicalTrialsDotGovTrialID() != null) {
            assertEquals(
                    reg.getClinicalTrialsDotGovTrialID(),
                    getTrialIdentificationTableCellValue("ClinicalTrials.gov Identifier"));
        }
        if (!reg.getOtherTrialID().isEmpty()) {
            assertEquals(reg.getOtherTrialID().get(0),
                    getTrialIdentificationTableCellValue("Other Identifier"));
        }
        assertEquals("No",
                getTrialIdentificationTableCellValue("Abbreviated Trial?"));
        assertEquals(reg.getTitle(),
                getTrialIdentificationTableCellValue("Official Title"));
        assertEquals("REST Service",
                getTrialIdentificationTableCellValue("Submission Source"));

    }

   
}
