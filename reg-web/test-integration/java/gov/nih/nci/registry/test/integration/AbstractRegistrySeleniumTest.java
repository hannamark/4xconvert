/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.registry.test.integration;

import static gov.nih.nci.registry.integration.TestProperties.TEST_DB_DRIVER;
import static gov.nih.nci.registry.integration.TestProperties.TEST_DB_PASSWORD;
import static gov.nih.nci.registry.integration.TestProperties.TEST_DB_URL;
import static gov.nih.nci.registry.integration.TestProperties.TEST_DB_USER;
import gov.nih.nci.registry.integration.TestProperties;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Abstract base class for selenium tests.
 *
 * @author Abraham J. Evans-EL <aevanse@5amsolutions.com>
 */
@Ignore
public abstract class AbstractRegistrySeleniumTest extends AbstractSelenese2TestCase {
    
    private static final String PHANTOM_JS_DRIVER = "org.openqa.selenium.phantomjs.PhantomJSDriver";
    protected static final FastDateFormat MONTH_DAY_YEAR_FMT = FastDateFormat.getInstance("MM/dd/yyyy");
    private static final String PROTOCOL_DOCUMENT = "ProtocolDoc.doc";
    private static final String IRB_DOCUMENT = "IrbDoc.doc";
    private static boolean firstRun = true;
    
    private Connection connection;
    
    private static Logger LOG = Logger.getLogger(AbstractRegistrySeleniumTest.class.getName());
    
    protected String today = MONTH_DAY_YEAR_FMT.format(new Date());
    protected String tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
    protected String oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(new Date(), 1));

    @Override
    public void setUp() throws Exception {        
        super.setServerHostname(TestProperties.getServerHostname());
        super.setServerPort(TestProperties.getServerPort());
        super.setDriverClass(TestProperties.getDriverClass());
        //super.setDriverClass(PHANTOM_JS_DRIVER);
        System.setProperty("phantomjs.binary.path", TestProperties.getPhantomJsPath());
        super.setUp();
        selenium.setSpeed(TestProperties.getSeleniumCommandDelay());
        
        openDbConnection();
    }

    private void openDbConnection() {
        try {
            DbUtils.loadDriver(TestProperties.getProperty(TEST_DB_DRIVER));
            this.connection = DriverManager.getConnection(TestProperties.getProperty(TEST_DB_URL),
                    TestProperties.getProperty(TEST_DB_USER), TestProperties.getProperty(TEST_DB_PASSWORD));
            LOG.info("Successfully connected to the database at "+TestProperties.getProperty(TEST_DB_URL));
        } catch (Exception e) {
            LOG.severe("Unable to open a JDBC connection to the database: tests may fail!");
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void tearDown() throws Exception {
        takeScreenShot();
        logoutUser();
        closeBrowser();
        closeDbConnection();
        super.tearDown();
    }

    private void takeScreenShot() {
        try {           
            final String screenShotFileName = getClass().getSimpleName()
                    + "_ScreenShot_"
                    + new Timestamp(System.currentTimeMillis()).toString()
                            .replaceAll("\\D+", "_") + ".png";
            File destFile = new File(SystemUtils.JAVA_IO_TMPDIR,
                    screenShotFileName);

            File scrFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, destFile);
            System.out.println("Saved screen shot: "
                    + destFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    private void closeBrowser() {
        driver.quit();
    }

    private void closeDbConnection() {
       DbUtils.closeQuietly(connection);        
    }

    protected void logoutUser() {
        openAndWait("/registry/login/logout.action");
    }

    protected void login(String username, String password) {
        openAndWait("/registry");
        verifyLoginPage();
        clickAndWait("link=Sign In");
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        selenium.select("id=authenticationServiceURL", "label=Training");
        clickAndWait("id=loginButton");
        verifyDisclaimerPage();
    }

    public void loginAsAbstractor() {
        login("abstractor-ci", "Coppa#12345");
    }

    public void loginAsSubmitter() {
        login("submitter-ci", "Coppa#12345");
    }

    public void verifyDisclaimer() {
        loginAsAbstractor();
        handleDisclaimer(false);
        loginAsAbstractor();
        handleDisclaimer(true);
    }

    public void loginAndAcceptDisclaimer() {
        loginAsAbstractor();
        handleDisclaimer(true);
    }

    protected void handleDisclaimer(boolean accept) {
        verifyDisclaimerPage();
        if (accept) {
            clickAndWait("id=acceptDisclaimer");
            verifySearchPage();
        } else {
            clickAndWait("id=rejectDisclaimer");
            verifyLoginPage();
        }

    }

    protected void verifySearchPage() {
        assertTrue(selenium.isTextPresent("Search Clinical Trials"));
        assertTrue(selenium.isElementPresent("id=resetSearchBtn"));        
        assertTrue(selenium.isElementPresent("id=runSearchBtn"));       
    }

    protected void verifyLoginPage() {
        assertTrue(selenium.isTextPresent("Sign In"));
        assertTrue(selenium.isTextPresent("Contact Us"));
        assertTrue(selenium.isTextPresent("Policies"));      
    }

    protected void verifyDisclaimerPage() {
        assertTrue(selenium.isElementPresent("id=acceptDisclaimer"));
        assertTrue(selenium.isElementPresent("id=rejectDisclaimer"));
    }

    protected boolean isLoggedIn() {
        return !selenium.isElementPresent("link=Sign In");
    }

    protected void openAndWait(String url) {
        selenium.open(url);
        waitForPageToLoad();
    }

    protected void registerTrial(String trialName, String leadOrgTrialId) throws URISyntaxException, SQLException {
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        registerTrialWithoutDeletingExistingOne(trialName, leadOrgTrialId);
    }

    /**
     * @param trialName
     * @param leadOrgTrialId
     * @throws URISyntaxException
     */
    protected void registerTrialWithoutDeletingExistingOne(String trialName,
            String leadOrgTrialId) throws URISyntaxException {
        today = MONTH_DAY_YEAR_FMT.format(new Date());
        tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(new Date(), 1));

        //Select register trial and choose trial type
        clickAndWaitAjax("registerTrialMenuOption");
        selenium.selectFrame("popupFrame");
        waitForElementById("summaryFourFundingCategoryCode", 60);
        selenium.select("summaryFourFundingCategoryCode", "label=Institutional");
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();

        driver.switchTo().defaultContent();
        waitForElementById("trialDTO.leadOrgTrialIdentifier", 30);
        selenium.type("trialDTO.leadOrgTrialIdentifier", leadOrgTrialId);
        selenium.type("trialDTO.officialTitle", trialName);
        selenium.select("trialDTO.phaseCode", "label=0");
        selenium.select("trialDTO.primaryPurposeCode", "label=Treatment");

        //Select Lead Organization
        clickAndWaitAjax("link=Look Up Org");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_organization_btn", 30);
        if (firstRun) {
            // compiling the popup jsp the first time screws up selenium, so give it some time
            System.out.println("Waiting on first run - org");
            pause(2000);
        }
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 30);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        //Select Principal Investigator
        driver.switchTo().defaultContent();
        clickAndWaitAjax("link=Look Up Person");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", 30);
        if (firstRun) {
            // compiling the popup jsp the first time screws up selenium, so give it some time
            System.out.println("Waiting on first run - pers");
            pause(2000);
        }
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/a");
        waitForPageToLoad();

        //Select Sponsor
        driver.switchTo().defaultContent();
        clickAndWaitAjax("//div[@id='loadSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("search_organization_btn", 15);
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        driver.switchTo().defaultContent();
        selenium.select("trialDTO.responsiblePartyType", "label=Sponsor");

        //Select Funding Sponsor
        clickAndWaitAjax("//div[@id='loadSummary4FundingSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("search_organization_btn", 15);
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        //Trial Status Information
        driver.switchTo().defaultContent();
        selenium.select("trialDTO_statusCode", "label=In Review");
        selenium.type("trialDTO_statusDate", today);
        selenium.type("trialDTO_startDate", tommorrow);
        selenium.click("trialDTO_startDateTypeAnticipated");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", oneYearFromToday);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", oneYearFromToday);

        //Regulator Information
        selenium.select("countries", "label=United States");
        selenium.click("//option[@value='1026']");
        selenium.select("trialDTO.fdaRegulatoryInformationIndicator", "label=Yes");
        selenium.select("trialDTO.section801Indicator", "label=Yes");
        selenium.select("trialDTO.delayedPostingIndicator", "label=Yes");
        selenium.select("trialDTO.dataMonitoringCommitteeAppointedIndicator", "label=Yes");
        
        // Grants
        selenium.click("nciGrantfalse");

        //Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);

        pause(1000);
        clickAndWaitAjax("link=Review Trial");
        waitForElementById("reviewTrialForm", 60);
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();
        pause(2000);
        firstRun = false;
    }

    protected void registerDraftTrial(String trialName, String leadOrgTrialId) throws URISyntaxException, SQLException {
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        today = MONTH_DAY_YEAR_FMT.format(new Date());
        tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(new Date(), 1));

        //Select register trial and choose trial type
        clickAndWaitAjax("registerTrialMenuOption");
        selenium.selectFrame("popupFrame");
        waitForElementById("summaryFourFundingCategoryCode", 60);
        selenium.select("summaryFourFundingCategoryCode", "label=Institutional");
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();

        driver.switchTo().defaultContent();
        waitForElementById("trialDTO.leadOrgTrialIdentifier", 15);
        selenium.type("trialDTO.leadOrgTrialIdentifier", leadOrgTrialId);
        selenium.type("trialDTO.officialTitle", trialName);
        selenium.select("trialDTO.phaseCode", "label=0");
        selenium.select("trialDTO.primaryPurposeCode", "label=Treatment");

        //Select Lead Organization
        clickAndWaitAjax("link=Look Up Org");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        selenium.type("orgNameSearch", "Division");
        waitForElementById("search_organization_btn", 15);
        if (firstRun) {
            // compiling the popup jsp the first time screws up selenium, so give it some time
            System.out.println("Waiting on first run - org");
            pause(2000);
        }
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 30);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        //Select Principal Investigator
        driver.switchTo().defaultContent();
        clickAndWaitAjax("link=Look Up Person");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", 30);
        if (firstRun) {
            // compiling the popup jsp the first time screws up selenium, so give it some time
            System.out.println("Waiting on first run - pers");
            pause(2000);
        }
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/a");
        waitForPageToLoad();

        //Select Sponsor
        driver.switchTo().defaultContent();
        clickAndWaitAjax("//div[@id='loadSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("search_organization_btn", 15);
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        driver.switchTo().defaultContent();
        selenium.select("trialDTO.responsiblePartyType", "label=Sponsor");
        
        
        //Select Funding Sponsor
        clickAndWaitAjax("//div[@id='loadSummary4FundingSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("search_organization_btn", 15);
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        //Trial Status Information
        driver.switchTo().defaultContent();
        selenium.select("trialDTO_statusCode", "label=In Review");
        selenium.type("trialDTO_statusDate", today);
        selenium.type("trialDTO_startDate", tommorrow);
        selenium.click("trialDTO_startDateTypeAnticipated");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", oneYearFromToday);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", oneYearFromToday);

        //Regulator Information
        selenium.select("countries", "label=United States");
        selenium.click("//option[@value='1026']");
        selenium.select("trialDTO.fdaRegulatoryInformationIndicator", "label=Yes");
        selenium.select("trialDTO.section801Indicator", "label=Yes");
        selenium.select("trialDTO.delayedPostingIndicator", "label=Yes");
        selenium.select("trialDTO.dataMonitoringCommitteeAppointedIndicator", "label=Yes");
        
        // Grants
        selenium.click("nciGrantfalse");

        //Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);
        
        
        pause(5000);
        clickAndWaitAjax("link=Save as Draft");
        pause(5000);
        firstRun = false;
    }
    
    protected void hoverLink(String linkText) {       
        By by = By.linkText(linkText);
        hover(by);     
    }
    
    protected void hover(By by) {
        Actions action = new Actions(driver);
        WebElement elem = driver.findElement(by);
        action.moveToElement(elem);
        action.perform();        
    }

    
    public final void deactivateTrialByLeadOrgId(String leadOrgID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(
                connection,
                "update study_protocol sp set status_code='INACTIVE' where exists "
                        + "(select local_sp_indentifier from study_site ss where ss.study_protocol_identifier=sp.identifier "
                        + "and ss.functional_code='LEAD_ORGANIZATION' and ss.local_sp_indentifier='"
                        + StringEscapeUtils.escapeSql(leadOrgID) + "')");
        LOG.info("De-activated trial with Lead Org ID of "+leadOrgID);
    }
    
    protected TrialInfo createSubmittedTrial(boolean isAbbreviated) throws SQLException {
        TrialInfo info = new TrialInfo();
        info.uuid = UUID.randomUUID().toString();
        info.title = "Title " + info.uuid;

        QueryRunner runner = new QueryRunner();

        final Object[] regUserResults = runner.query(connection,
                "select identifier, csm_user_id from registry_user limit 1",
                new ArrayHandler());
        info.registryUserID = (Long) regUserResults[0];
        info.csmUserID = (Long) regUserResults[1];

        String protocolInsertSQL = "INSERT INTO study_protocol "
                + "(identifier,accr_rept_meth_code,acronym,accept_healthy_volunteers_indicator,data_monty_comty_apptn_indicator,"
                + "delayed_posting_indicator,expd_access_indidicator,fda_regulated_indicator,review_brd_approval_req_indicator,"
                + "keyword_text,official_title,max_target_accrual_num,phase_code,phase_other_text,pri_compl_date,pri_compl_date_type_code,"
                + "start_date,start_date_type_code,primary_purpose_code,primary_purpose_other_text,public_description,"
                + "public_tittle,section801_indicator,record_verification_date,scientific_description,study_protocol_type,allocation_code,"
                + "blinding_role_code_subject,blinding_role_code_caregiver,blinding_role_code_investigator,blinding_role_code_outcome,"
                + "blinding_schema_code,design_configuration_code,number_of_intervention_groups,study_classification_code,bio_specimen_description,"
                + "bio_specimen_retention_code,sampling_method_code,number_of_groups,study_model_code,study_model_other_text,time_perspective_code,"
                + "time_perspective_other_text,study_population_description,date_last_created,date_last_updated,status_code,status_date,"
                + "amendment_number,amendment_date,amendment_reason_code,submission_number,parent_protocol_identifier,program_code_text,"
                + "min_target_accrual_num,proprietary_trial_indicator,ctgov_xml_required_indicator,user_last_created_id,user_last_updated_id,"
                + "phase_additional_qualifier_code,primary_purpose_additional_qualifier_code,completion_date,completion_date_type_code,"
                + "study_subtype_code,comments,processing_priority,assigned_user_id,ctro_override,secondary_purpose_other_text,nci_grant,"
                + "consortia_trial_category,final_accrual_num,study_source) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE'))"
                + ",'ABBREVIATED','Accr',false,false,false,false,"
                + "false,false,'stage I prostate cancer, stage IIB prostate cancer, stage IIA prostate cancer, stage III prostate cancer'"
                + ",'"
                + info.title
                + "'"
                + ",null,'II',null,{ts '2018-04-16 12:18:50.572'},'ANTICIPATED',{ts '2013-04-16 12:18:50.572'},'ACTUAL',"
                + "'TREATMENT',null,'Public Description "
                + info.uuid
                + "','"
                + info.title
                + "',"
                + "false,null,'Scientific Description','InterventionalStudyProtocol','RANDOMIZED_CONTROLLED_TRIAL',"
                + "null,null,null,null,'OPEN','PARALLEL',1,'EFFICACY',null,null,null,1,null,null,null,null,null,"
                + "{ts '2014-04-16 12:18:50.572'},null,'ACTIVE',{ts '2013-04-16 12:18:50.572'},null,"
                + "null,null,1,null,'" + info.uuid + "',60,"+isAbbreviated+",false,"
                + info.csmUserID + ",null,null,null,"
                + "{ts '2018-04-16 12:18:50.572'},'ANTICIPATED',null,null,2,"
                + info.csmUserID + ",false,null,false,null,null,'OTHER');";
        runner.update(connection, protocolInsertSQL);
        info.id = (Long) runner
                .query(connection,
                        "select identifier from study_protocol order by identifier desc limit 1",
                        new ArrayHandler())[0];
        assignNciId(info);
        addDWS(info, "SUBMITTED");
        addMilestone(info, "SUBMISSION_RECEIVED");       
        addLeadOrg(info, "ClinicalTrials.gov");
        addPI(info, "1");
        addSOS(info, "APPROVED");

        LOG.info("Registered a new trial: " + info);
        return info;
        
    }
    
    protected TrialInfo createSubmittedTrial() throws SQLException {
        return createSubmittedTrial(false);
    }
    
    protected TrialInfo createAcceptedTrial(boolean isAbbreviated)
            throws SQLException {
        TrialInfo info = createSubmittedTrial(isAbbreviated);
        addDWS(info, "ACCEPTED");
        addMilestone(info, "SUBMISSION_ACCEPTED");
        return info;
    }

    
    protected TrialInfo createAcceptedTrial() throws SQLException {
        return createAcceptedTrial(false);
    }

    private void addSOS(TrialInfo info, String code) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_overall_status (identifier,comment_text,status_code,status_date,"
                + "study_protocol_identifier,date_last_created,date_last_updated,user_last_created_id,"
                + "user_last_updated_id,system_created) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),null,'"
                + code
                + "',"
                + today()
                + " ,"
                + info.id
                + ","
                + "null,null,null,null,false)";
        runner.update(connection, sql);
    }

    private String today() {
        return String.format("{ts '%s'}", new Timestamp(System.currentTimeMillis()).toString());       
    }

    private void addMilestone(TrialInfo info, String code) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_milestone (identifier,comment_text,milestone_code,milestone_date,"
                + "study_protocol_identifier,date_last_created,date_last_updated,user_last_created_id,user_last_updated_id,"
                + "rejection_reason_code) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),null,'"
                + code
                + "',"
                + ""
                + today()
                + ","
                + info.id
                + ","
                + ""
                + today()
                + ","
                + today()
                + ","
                + info.csmUserID
                + ","
                + info.csmUserID + ",null)";
        runner.update(connection, sql);
    }

    private void addPI(TrialInfo info, String poPersonID) throws SQLException {
        Number paPersonID = findOrCreatePersonByPoId(poPersonID);
        Number crsID = findOrCreateCrs(paPersonID, "ClinicalTrials.gov");
        Number hcpID = findOrCreateHcp(paPersonID, "ClinicalTrials.gov");

        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_contact"
                + "(identifier,role_code,primary_indicator,address_line,delivery_address_line,city,state,postal_code,"
                + "country_identifier,telephone,email,healthcare_provider_identifier,clinical_research_staff_identifier,"
                + "study_protocol_identifier,status_code,status_date_range_low,date_last_created,date_last_updated,"
                + "status_date_range_high,organizational_contact_identifier,user_last_created_id,user_last_updated_id,"
                + "title) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'STUDY_PRINCIPAL_INVESTIGATOR',null,null,null,null,null,null,null,null"
                + ",null,"
                + hcpID
                + ","
                + crsID
                + ","
                + info.id
                + ",'PENDING',"
                + today()
                + ",null,null,null,null,"
                + info.csmUserID + "," + info.csmUserID + ",null)";
        runner.update(connection, sql);
        

    }
    
    private Number findOrCreateHcp(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select crs.identifier from healthcare_provider crs "
                + "inner join organization o on crs.organization_identifier=o.identifier"
                + "  where person_identifier="
                + paPersonID
                + " and o.name='"
                + orgName + "' limit 1";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number hcpID = results != null ? (Number) results[0] : null;
        if (hcpID == null) {
            createHcp(paPersonID, orgName);
            hcpID = (Number) runner.query(connection, sql, new ArrayHandler())[0];
        }
        return hcpID;
    }

    private Number findOrCreateCrs(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select crs.identifier from clinical_research_staff crs "
                + "inner join organization o on crs.organization_identifier=o.identifier"
                + "  where person_identifier="
                + paPersonID
                + " and o.name='"
                + orgName + "' limit 1";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number crsID = results != null ? (Number) results[0] : null;
        if (crsID == null) {
            createCrs(paPersonID, orgName);
            crsID = (Number) runner.query(connection, sql, new ArrayHandler())[0];
        }
        return crsID;
    }
    
    private void createHcp(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO healthcare_provider (identifier,assigned_identifier,person_identifier,organization_identifier,"
                + "status_code) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) ,'1' ,"
                + paPersonID + " ," + getOrgIdByName(orgName) + " ,'PENDING')";
        runner.update(connection, sql);
    }

    private void createCrs(Number paPersonID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO clinical_research_staff (identifier,assigned_identifier,person_identifier,organization_identifier,"
                + "status_code) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) ,'1' ,"
                + paPersonID + " ," + getOrgIdByName(orgName) + " ,'PENDING')";
        runner.update(connection, sql);
    }

    private Number getOrgIdByName(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner.query(connection,
                "select o.identifier from organization o " + "where o.name='"
                        + orgName + "' limit 1", new ArrayHandler())[0];
    }

    private Number findOrCreatePersonByPoId(String poPersonID)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "select identifier from person where assigned_identifier='"
                + poPersonID + "'";
        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number paID = results != null ? (Number) results[0] : null;
        if (paID == null) {
            createPerson(poPersonID, "John", "G", "Doe", "Mr.", "III");
            paID = (Number) runner.query(connection, sql,
                    new ArrayHandler())[0];
        }
        return paID;
    }

    private void createPerson(String poPersonID, String firstName,
            String middleName, String lastName, String prefix, String suffix)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO person (identifier,assigned_identifier,first_name,middle_name,last_name,"
                + "status_code,date_last_created,date_last_updated,user_last_created_id,user_last_updated_id) "
                + "VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'"
                + poPersonID
                + "','"
                + firstName
                + "','"
                + middleName
                + "','"
                + lastName + "','PENDING',null," + "null,null,null)";
        runner.update(connection, sql);

    }

    private void addLeadOrg(TrialInfo info, String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        info.leadOrgID = info.uuid;
        String sql = "INSERT INTO study_site (identifier,functional_code,local_sp_indentifier,"
                + "review_board_approval_number,review_board_approval_date,review_board_approval_status_code,"
                + "target_accrual_number,study_protocol_identifier,healthcare_facility_identifier,"
                + "research_organization_identifier,oversight_committee_identifier,"
                + "status_code,status_date_range_low,date_last_created,date_last_updated,status_date_range_high,"
                + "review_board_organizational_affiliation,program_code_text,accrual_date_range_low,accrual_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES "
                + "((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'LEAD_ORGANIZATION','"
                + info.leadOrgID
                + "',null,null,null,null,"
                + info.id
                + " "
                + ",null,"
                + getResearchOrgId(orgName)
                + ",null,'PENDING',{ts '2014-04-16 14:56:08.559'},{ts '2014-04-16 14:56:08.559'},"
                + "{ts '2014-04-16 14:56:08.559'},null,null,null,"
                + "null,null," + info.csmUserID + "," + info.csmUserID + ")";
        runner.update(connection, sql);
    }

    private Number getResearchOrgId(String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return (Number) runner
                .query(connection,
                        "select ro.identifier from research_organization ro inner join organization o " +
                        "on o.identifier=ro.organization_identifier and o.name='"
                                + orgName + "' limit 1", new ArrayHandler())[0];
        
    }

    private void addDWS(TrialInfo info, String status) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO document_workflow_status (identifier,status_code,comment_text,status_date_range_low,"
                + "study_protocol_identifier,date_last_created,date_last_updated,status_date_range_high,"
                + "user_last_created_id,user_last_updated_id) VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')),'"
                + status
                + "',null,"
                + "{ts '2014-04-16 14:20:22.361'},"
                + info.id
                + ",{ts '2014-04-16 14:20:22.361'},"
                + "{ts '2014-04-16 14:20:22.361'},{ts '2014-04-16 14:20:22.361'},"
                + info.csmUserID + "," + info.csmUserID + ")";
        runner.update(connection, sql);
    }

    private void assignNciId(TrialInfo info) throws SQLException {
        info.nciID = "NCI-2014-" + RandomStringUtils.randomNumeric(8);
        QueryRunner runner = new QueryRunner();
        String sql = "INSERT INTO study_otheridentifiers "
                + "(study_protocol_id,null_flavor,displayable,extension,identifier_name,reliability,root,scope) "
                + "VALUES ("
                + info.id
                + ",null,null,'"
                + info.nciID
                + "','NCI study protocol entity identifier',null,'2.16.840.1.113883.3.26.4.3',null)";
        runner.update(connection, sql);
    }
    
    protected void deactivateTrialByNctId(String nctID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set status_code='INACTIVE' where exists"
                + " (select * from study_site ss where ss.study_protocol_identifier=study_protocol.identifier and ss.local_sp_indentifier='"
                + nctID + "')";
        runner.update(connection, sql);
    }
    
    protected void deactivateAllTrials() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update study_protocol set status_code='INACTIVE'";
        runner.update(connection, sql);
    }
    
    protected Number findParticipatingSite(TrialInfo trial, String orgName,
            String localID) throws SQLException {
        QueryRunner runner = new QueryRunner();
        final String sql = "SELECT ss.identifier FROM "
                + "("
                + "   ("
                + "      study_site ss"
                + "      JOIN healthcare_facility ro ON"
                + "      ("
                + "         (ro.identifier = ss.healthcare_facility_identifier)"
                + "      )"
                + "   )"
                + "   JOIN organization org ON ((org.identifier = ro.organization_identifier))"
                + ")" + "WHERE ss.local_sp_indentifier='" + localID
                + "' AND org.name='" + orgName
                + "' AND ((ss.functional_code)::text = 'TREATING_SITE'::text)";

        final Object[] results = runner.query(connection, sql,
                new ArrayHandler());
        Number siteID = results != null ? (Number) results[0] : null;
        return siteID;
    }

    public static final class TrialInfo {

        public String uuid;
        public String title;
        public Long id;
        public String leadOrgID;
        public String nciID;
        public Long registryUserID;
        public Long csmUserID;
        
        @Override
        public String toString() {         
            return ToStringBuilder.reflectionToString(this);
        }

    }


}
