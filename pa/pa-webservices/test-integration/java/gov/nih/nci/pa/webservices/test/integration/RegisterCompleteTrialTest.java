package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.INDIDE;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.ResponsiblePartyType;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
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

    @Test
    public void testImportNonCtGov() throws Exception {
        registerAndVerify("/integration_register_complete_nonCtGov_success.xml");
    }

    @Test
    public void testImportNonInterventional() throws Exception {
        registerAndVerify("/integration_register_complete_nonInt_success.xml");
    }

    @Test
    public void testImportWithNewOrgsAndPersons() throws Exception {
        registerAndVerify("/integration_register_complete_new_orgs_persons_success.xml");
    }

    @Test
    public void testImportWithOrgsAndPersonsIdentifiedByCtepID()
            throws Exception {
        registerAndVerify("/integration_register_complete_orgs_persons_ctepid.xml");
    }

    @Test
    public void testImportResponsiblePartyPI() throws Exception {
        registerAndVerify("/integration_register_complete_resp_party_pi.xml");
    }

    @Test
    public void testImportResponsiblePartySI() throws Exception {
        registerAndVerify("/integration_register_complete_resp_party_si.xml");
    }

    @Test
    public void testImportSchemaValidation() throws Exception {
        verifyFailureToRegister(
                "/integration_register_complete_schema_violation.xml", 400);
    }

    @Test
    public void testImportMinimalData() throws Exception {
        registerAndVerify("/integration_register_complete_minimal_dataset.xml");
    }

    private void verifyFailureToRegister(String file, int code)
            throws JAXBException, SAXException, UnsupportedEncodingException,
            ClientProtocolException, IOException {
        HttpResponse response = submitRegistrationAndReturnResponse(file);
        assertEquals(code, getReponseCode(response));
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

        if (indide.getNihInstitution() != null) {
            assertTrue(selenium.getText(
                    "xpath=//table[@id='row']//tr[" + i + "]//td[5]")
                    .startsWith(indide.getNihInstitution().value()));
        }
        if (indide.getNciDivisionProgramCode() != null) {
            assertEquals(
                    indide.getNciDivisionProgramCode().value(),
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
        HttpResponse response = submitRegistrationAndReturnResponse(file);

        HttpEntity resEntity = response.getEntity();
        TrialRegistrationConfirmation conf = unmarshalTrialRegistrationConfirmation(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertTrue(StringUtils.isNotBlank(conf.getNciTrialID()));
        assertNotNull(conf.getPaTrialID());
        assertEquals(getTrialIdByLeadOrgID("REST00001").longValue(),
                conf.getPaTrialID());

        LOG.info(ToStringBuilder.reflectionToString(conf));

        return conf;

    }

    /**
     * @param file
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClientProtocolException
     */
    private HttpResponse submitRegistrationAndReturnResponse(String file)
            throws UnsupportedEncodingException, IOException,
            ClientProtocolException {
        String url = baseURL + "/registration/complete";

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        req.addHeader("Content-Type", APPLICATION_XML);
        StringEntity orgEntity = new StringEntity(IOUtils.toString(getClass()
                .getResourceAsStream(file), "UTF-8"));
        req.setEntity(orgEntity);

        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        return response;
    }

}
