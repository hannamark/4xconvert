/**
 * 
 */
package gov.nih.nci.pa.webservices.test.integration;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.HolderType;
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
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.xml.sax.SAXException;

/**
 * @author dkrylov
 * 
 */
public abstract class AbstractRestServiceTest extends AbstractPaSeleniumTest {
    @SuppressWarnings("deprecation")
    protected DefaultHttpClient httpClient = null;
    protected AuthScope authScope;

    protected String baseURL = "";
    protected String serviceURL = "";

    protected static final String APPLICATION_XML = "application/xml";

    @SuppressWarnings("deprecation")
    public void setUp(String serviceURL) throws Exception {
        super.setUp();

        this.serviceURL = serviceURL;

        insertSecondaryPurposes();

        httpClient = new DefaultHttpClient();

        authScope = new AuthScope(TestProperties.getServerHostname(),
                TestProperties.getServerPort());
        Credentials credentials = new UsernamePasswordCredentials(
                "submitter-ci", "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";

    }

    @SuppressWarnings("deprecation")
    public void testInvalidCredentials() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials(
                "nonexistentuserfortesting", "nonexistentuserfortesting");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + serviceURL;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    @SuppressWarnings("deprecation")
    public void testValidCredentialsButNoRole() throws Exception {
        Credentials credentials = new UsernamePasswordCredentials("curator",
                "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);
        String url = baseURL + serviceURL;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        HttpResponse response = httpClient.execute(req);
        assertEquals(401, getReponseCode(response));
        assertNull(response.getFirstHeader("Set-Cookie"));

    }

    private void insertSecondaryPurposes() throws SQLException {
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "INSERT INTO secondary_purpose (name) VALUES ('Other')";
            runner.update(connection, sql);
        } catch (SQLException e) {
            LOG.warning("Oops; 'Other' most likely is in secondary_purpose already; ignoring...");
        }
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "INSERT INTO secondary_purpose (name) VALUES ('Ancillary-Correlative')";
            runner.update(connection, sql);
        } catch (SQLException e) {
            LOG.warning("Oops; 'Ancillary-Correlative' most likely is in secondary_purpose already; ignoring...");
        }
    }

    protected int getReponseCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    @SuppressWarnings("deprecation")
    protected String getResponseContentType(HttpResponse response) {
        return EntityUtils.getContentMimeType(response.getEntity());
    }

    protected TrialRegistrationConfirmation unmarshalTrialRegistrationConfirmation(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(TrialRegistrationConfirmation.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        LOG.info(orgXMLStr);
        JAXBElement<TrialRegistrationConfirmation> jaxbEle = (JAXBElement<TrialRegistrationConfirmation>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgXMLStr)),
                        TrialRegistrationConfirmation.class);
        return jaxbEle.getValue();
    }

    @SuppressWarnings("unchecked")
    protected CompleteTrialRegistration readCompleteTrialRegistrationFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialRegistration o = ((JAXBElement<CompleteTrialRegistration>) u
                .unmarshal(url)).getValue();
        return o;
    }

    @SuppressWarnings("unchecked")
    protected CompleteTrialUpdate readCompleteTrialUpdateFromFile(String string)
            throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialUpdate o = ((JAXBElement<CompleteTrialUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }

    @SuppressWarnings("unchecked")
    protected TrialRegistrationConfirmation registerTrialFromFile(String file)
            throws ClientProtocolException, IOException, ParseException,
            JAXBException, SQLException {
        HttpResponse response = submitRegistrationAndReturnResponse(file);
        return processTrialRegistrationResponseAndDoBasicVerification(response);

    }

    @SuppressWarnings("unchecked")
    protected TrialRegistrationConfirmation registerTrialFromJAXBElement(
            CompleteTrialRegistration o) throws ClientProtocolException,
            IOException, ParseException, JAXBException, SQLException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller m = jc.createMarshaller();
        StringWriter out = new StringWriter();
        m.marshal(new JAXBElement<CompleteTrialRegistration>(
                new QName("gov.nih.nci.pa.webservices.types",
                        "CompleteTrialRegistration"),
                CompleteTrialRegistration.class, o), out);

        StringEntity entity = new StringEntity(out.toString());
        HttpResponse response = submitRegistrationAndReturnResponse(entity);
        return processTrialRegistrationResponseAndDoBasicVerification(response);

    }

    protected HttpResponse submitRegistrationAndReturnResponse(String file)
            throws UnsupportedEncodingException, IOException,
            ClientProtocolException {
        StringEntity orgEntity = new StringEntity(IOUtils.toString(getClass()
                .getResourceAsStream(file), "UTF-8"));
        return submitRegistrationAndReturnResponse(orgEntity);
    }

    /**
     * @param file
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ClientProtocolException
     */
    private HttpResponse submitRegistrationAndReturnResponse(
            StringEntity orgEntity) throws UnsupportedEncodingException,
            IOException, ClientProtocolException {
        return submitEntityAndReturnResponse(orgEntity,
                "/registration/complete");

    }

    protected HttpResponse submitEntityAndReturnResponse(
            StringEntity orgEntity, String serviceURL)
            throws UnsupportedEncodingException, IOException,
            ClientProtocolException {
        String url = baseURL + serviceURL;

        HttpPost req = new HttpPost(url);
        req.addHeader("Accept", APPLICATION_XML);
        req.addHeader("Content-Type", APPLICATION_XML);
        req.setEntity(orgEntity);

        HttpResponse response = httpClient.execute(req);
        LOG.info("Response code: " + getReponseCode(response));
        return response;
    }

    /**
     * @param response
     * @return
     * @throws JAXBException
     * @throws ParseException
     * @throws IOException
     * @throws SQLException
     */
    protected TrialRegistrationConfirmation processTrialRegistrationResponseAndDoBasicVerification(
            HttpResponse response) throws JAXBException, ParseException,
            IOException, SQLException {

        return processTrialRegistrationResponseAndDoBasicVerification(response,
                "UPCC 34890534");
    }

    protected TrialRegistrationConfirmation processTrialRegistrationResponseAndDoBasicVerification(
            HttpResponse response, String trialLeadOrgID) throws JAXBException,
            ParseException, IOException, SQLException {
        HttpEntity resEntity = response.getEntity();
        TrialRegistrationConfirmation conf = unmarshalTrialRegistrationConfirmation(resEntity);

        assertEquals(200, getReponseCode(response));
        assertEquals(APPLICATION_XML, getResponseContentType(response));
        assertEquals("", response.getFirstHeader("Set-Cookie").getValue());
        assertTrue(StringUtils.isNotBlank(conf.getNciTrialID()));
        assertNotNull(conf.getPaTrialID());
        assertEquals(getTrialIdByLeadOrgID(trialLeadOrgID).longValue(),
                conf.getPaTrialID());

        LOG.info(ToStringBuilder.reflectionToString(conf));

        return conf;
    }

    /**
     * @param conf
     */
    protected void logInFindAndAcceptTrial(TrialRegistrationConfirmation conf) {
        logoutUser();
        loginAsSuperAbstractor();

        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", conf.getNciTrialID());
        selenium.select("id=identifierType", "NCI");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        acceptTrial();
        verifyTrialAccepted();
    }

    protected String getTrialIdentificationTableCellValue(String label) {
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

    protected void verifyRegistration(TrialRegistrationConfirmation conf,
            BaseTrialInformation reg) throws JAXBException, SAXException,
            SQLException {

        logInFindAndAcceptTrial(conf);

        verifyTrialIdentification(reg, conf);
        verifyDiseaseTerm(reg, conf);
        if (reg instanceof CompleteTrialRegistration) {
            verifyOwnership(((CompleteTrialRegistration) reg), conf);
        }

        verifyGeneralTrialDetails(reg, conf);
        verifyNCISpecificInformation(reg, conf);
        verifyRegulatoryInfo(reg, conf);
        verifyIndIde(reg, conf);
        verifyTrialStatus(reg, conf);
        verifyGrant(reg, conf);
        verifyTrialDocuments(reg, conf);
        verifyTrialDesign(reg, conf);

    }

    protected void verifyTrialIdentification(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) throws JAXBException,
            SAXException {
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

        verifyOtherIdentifiers(reg);

        assertEquals("No",
                getTrialIdentificationTableCellValue("Abbreviated Trial?"));
        assertEquals(reg.getTitle(),
                getTrialIdentificationTableCellValue("Official Title"));
        assertEquals("REST Service",
                getTrialIdentificationTableCellValue("Submission Source"));

    }

    /**
     * @param reg
     * @throws SAXException
     * @throws JAXBException
     */
    protected void verifyOtherIdentifiers(BaseTrialInformation reg)
            throws JAXBException, SAXException {
        if (!reg.getOtherTrialID().isEmpty()) {
            List<String> otherIDs = Arrays
                    .asList(getTrialIdentificationTableCellValue(
                            "Other Identifier").split(",\\s"));
            LOG.info("Other IDs on the screen: " + otherIDs);

            for (String expected : reg.getOtherTrialID()) {
                assertTrue(otherIDs.contains(expected));
            }

        }
    }

    protected void verifyOwnership(CompleteTrialRegistration reg,
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

    protected void verifyDiseaseTerm(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Manage Accrual Access");
        assertEquals(reg.getAccrualDiseaseTerminology().value(),
                selenium.getValue("id=accrualDiseaseTerminology"));
    }

    protected void verifyTrialDesign(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) {
        if (reg.getInterventionalDesign() != null) {
            verifyInterventialDesign(reg, conf);
        } else {
            verifyNonInterventialDesign(reg, conf);
        }
    }

    private void verifyNonInterventialDesign(BaseTrialInformation reg,
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
        verifyPhaseAndPilot(reg);
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

    private void verifyInterventialDesign(BaseTrialInformation reg,
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

        verifyPhaseAndPilot(reg);

    }

    /**
     * @param reg
     */
    protected void verifyPhaseAndPilot(BaseTrialInformation reg) {
        assertEquals(reg.getPhase(),
                selenium.getSelectedLabel("id=webDTO.phaseCode"));

        if ("NA".equals(reg.getPhase())) {
            if (reg.isPilot()) {
                assertEquals(
                        "Yes",
                        selenium.getSelectedLabel("xpath=//select[@name='webDTO.phaseAdditionalQualifierCode']"));
            } else {
                assertEquals(
                        "",
                        selenium.getSelectedLabel("xpath=//select[@name='webDTO.phaseAdditionalQualifierCode']"));
            }
        }
    }

    protected void verifyTrialDocuments(BaseTrialInformation reg,
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

    private void verifyGrant(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial Funding");
        if (reg.getGrant().isEmpty()) {
            assertTrue(selenium
                    .isTextPresent("No funding records exist on the trial"));
        } else {
            assertTrue(selenium
                    .isChecked(reg.isFundedByNciGrant() ? "id=nciGranttrue"
                            : "id=nciGrantfalse"));
            outer: for (Grant grant : reg.getGrant()) {
                for (int i = 1; i < 20; i++) {
                    if (grant.getSerialNumber().equals(
                            selenium.getText("xpath=//table[@id='row']//tr["
                                    + i + "]//td[3]"))) {
                        assertEquals(
                                grant.getFundingMechanism(),
                                selenium.getText("xpath=//table[@id='row']//tr["
                                        + i + "]//td[1]"));
                        assertEquals(
                                grant.getNihInstitutionCode(),
                                selenium.getText("xpath=//table[@id='row']//tr["
                                        + i + "]//td[2]"));
                        assertEquals(
                                grant.getNciDivisionProgramCode().value(),
                                selenium.getText("xpath=//table[@id='row']//tr["
                                        + i + "]//td[4]"));
                        continue outer;
                    }
                }
                Assert.fail();
            }

        }
    }

    protected void verifyTrialStatus(BaseTrialInformation reg,
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

    protected void verifyIndIde(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) {
        clickAndWait("link=Trial IND/IDE");

        for (INDIDE ind : reg.getInd()) {
            verifyIndIdeEntry("IND", ind);
        }
        for (INDIDE ide : reg.getIde()) {
            verifyIndIdeEntry("IDE", ide);
        }

        if (reg.getInd().isEmpty() && reg.getIde().isEmpty()) {
            assertTrue(selenium
                    .isTextPresent("No IND/IDE records exist on the trial."));
        }

    }

    private void verifyIndIdeEntry(String type, INDIDE indide) {

        for (int i = 1; i < 20; i++) {
            if (selenium.getText(
                    "xpath=//table[@id='row']//tr[" + i + "]//td[2]").equals(
                    indide.getNumber())) {
                assertEquals(
                        type,
                        selenium.getText("xpath=//table[@id='row']//tr[" + i
                                + "]//td[1]"));
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
                            NihInstituteCode.valueOf(
                                    indide.getNihInstitution().name())
                                    .getCode(),
                            selenium.getText("xpath=//table[@id='row']//tr["
                                    + i + "]//td[5]"));
                } else {
                    assertTrue(selenium.getText(
                            "xpath=//table[@id='row']//tr[" + i + "]//td[5]")
                            .equals(""));
                }
                if (indide.getNciDivisionProgramCode() != null
                        && indide.getHolderType() == HolderType.NCI) {
                    assertEquals(
                            indide.getNciDivisionProgramCode().value(),
                            selenium.getText("xpath=//table[@id='row']//tr["
                                    + i + "]//td[6]"));
                } else {
                    assertEquals(
                            "",
                            selenium.getText("xpath=//table[@id='row']//tr["
                                    + i + "]//td[6]"));
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

                return;
            }
        }
        Assert.fail();

    }

    private void verifyRegulatoryInfo(BaseTrialInformation reg,
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

    protected void verifyNCISpecificInformation(BaseTrialInformation reg,
            TrialRegistrationConfirmation conf) throws SQLException {
        clickAndWait("link=NCI Specific Information");

        if (reg instanceof CompleteTrialRegistration) {
            assertEquals(((CompleteTrialRegistration) reg).getCategory()
                    .value(), selenium.getSelectedValue("id=summary4TypeCode"));
        }

        verifyOrganizationPoId(
                reg.getSummary4FundingSponsor().get(0),
                selenium.getValue("id=nciSpecificInformationWebDTO.summary4Sponsors[0].orgId"));
        assertEquals(StringUtils.defaultString(reg.getProgramCode()),
                selenium.getValue("id=summary4ProgramCode"));

    }

    private void verifyGeneralTrialDetails(BaseTrialInformation reg,
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
        } else {
            verifyNoCtGovFieldsOnGTD(reg);
        }

    }

    private void verifyNoCtGovFieldsOnGTD(BaseTrialInformation reg) {
        assertFalse(selenium.isVisible("id=gtdDTO.sponsorName"));
        assertFalse(selenium.isVisible("id=gtdDTO.responsiblePartyType"));

    }

    /**
     * @param reg
     * @throws SQLException
     */
    private void verifyResponsibleParty(BaseTrialInformation reg)
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

}
