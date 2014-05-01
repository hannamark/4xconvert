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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Ignore;

/**
 * Abstract base class for selenium tests.
 *
 * @author Abraham J. Evans-EL <aevanse@5amsolutions.com>
 */
@Ignore
public abstract class AbstractRegistrySeleniumTest extends AbstractSelenese2TestCase {
    protected static final FastDateFormat MONTH_DAY_YEAR_FMT = FastDateFormat.getInstance("MM/dd/yyyy");
    private static final String PROTOCOL_DOCUMENT = "ProtocolDoc.doc";
    private static final String IRB_DOCUMENT = "IrbDoc.doc";
    private static boolean firstRun = true;
    
    private Connection connection;
    
    private static Logger LOG = Logger.getLogger(AbstractRegistrySeleniumTest.class.getName());

    @Override
    public void setUp() throws Exception {        
        super.setServerHostname(TestProperties.getServerHostname());
        super.setServerPort(TestProperties.getServerPort());
        super.setDriverClass(TestProperties.getDriverClass());
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
        logoutUser();
        closeBrowser();
        closeDbConnection();
        super.tearDown();
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
        String today = MONTH_DAY_YEAR_FMT.format(new Date());
        String tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        String oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(new Date(), 1));

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
        String today = MONTH_DAY_YEAR_FMT.format(new Date());
        String tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        String oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(new Date(), 1));

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

}
