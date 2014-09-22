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

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Abstract base class for selenium tests.
 * 
 * @author Abraham J. Evans-EL <aevanse@5amsolutions.com>
 */
@Ignore
public abstract class AbstractRegistrySeleniumTest extends
        AbstractPaSeleniumTest {

    protected static final FastDateFormat MONTH_DAY_YEAR_FMT = FastDateFormat
            .getInstance("MM/dd/yyyy");
    
    private static final String PROTOCOL_DOCUMENT = "ProtocolDoc.doc";
    private static final String IRB_DOCUMENT = "IrbDoc.doc";
    private static final String SITES_DOCUMENT = "Sites.doc";
    private static final String CONSENT_DOCUMENT = "Consent.doc";
    private static final String OTHER_DOCUMENT = "Other.doc";
    
    private static boolean firstRun = true;

    protected String today = MONTH_DAY_YEAR_FMT.format(new Date());
    protected String tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), 1));
    protected String oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils
            .addYears(new Date(), 1));

    @Override
    protected void logoutUser() {
        openAndWait("/registry/login/logout.action");
    }

    @Override
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

    protected void registerTrial(String trialName, String leadOrgTrialId)
            throws URISyntaxException, SQLException {
        registerTrial(trialName, leadOrgTrialId,
                RandomStringUtils.randomNumeric(10));
    }
    
    protected void registerTrial(final String rand) throws URISyntaxException, SQLException {
        registerTrial("An Open-Label Study of Ruxolitinib "+rand, "LEAD"+rand, rand);
    }

    protected void registerTrial(String trialName, String leadOrgTrialId,
            final String rand) throws URISyntaxException, SQLException {
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        registerTrialWithoutDeletingExistingOne(trialName, leadOrgTrialId, rand);
    }

    /**
     * @param trialName
     * @param leadOrgTrialId
     * @throws URISyntaxException
     */
    protected void registerTrialWithoutDeletingExistingOne(String trialName,
            String leadOrgTrialId, final String rand) throws URISyntaxException {
        populateRegisterNationalTrialScreen(trialName, leadOrgTrialId, rand);      
        reviewAndSubmit();
       
    }

    /**
     * 
     */
    protected void reviewAndSubmit() {
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("reviewTrialForm", 60);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
    }

    /**
     * @param trialName
     * @param leadOrgTrialId
     * @param rand
     * @throws URISyntaxException
     */
    protected void populateRegisterNationalTrialScreen(String trialName,
            String leadOrgTrialId, final String rand) throws URISyntaxException {
        today = MONTH_DAY_YEAR_FMT.format(new Date());
        tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(
                new Date(), 1));
        
        // Select register trial and choose trial type
        hoverLink("Register Trial");       
        clickAndWait("link=National");
        waitForElementById("trialDTO.leadOrgTrialIdentifier", 30);
        
        hideTopMenu();
        
        selenium.click("id=xmlRequiredtrue");
        hover(By.xpath("//i[preceding-sibling::input[@id='xmlRequiredfalse']]"));
        assertTrue(selenium.isTextPresent("Indicate whether you need an XML file "
                + "to submit/update your trial to ClinicalTrials.gov"));
        
        selenium.type("trialDTO.leadOrgTrialIdentifier", leadOrgTrialId);
        
        
        selenium.type("trialDTO.nctIdentifier", "NCT"+rand);
        final String nctID = "OTHER"+rand;
        selenium.type("otherIdentifierOrg", nctID);
        clickAndWaitAjax("id=otherIdbtnid");
        waitForElementById("otherIdentifierdiv", 30);
        assertTrue(selenium.isTextPresent(nctID));
        
        selenium.type("trialDTO.officialTitle", trialName);
        selenium.select("trialDTO.phaseCode", "label=0");
        selenium.click("id=trialDTO.trialType.Interventional");
        selenium.select("trialDTO.primaryPurposeCode", "label=Treatment");
        selenium.select("trialDTO.secondaryPurposes", "label=Ancillary");
        selenium.select("trialDTO.accrualDiseaseCodeSystem", "label=ICD10");

        // Select Lead Organization
        moveElementIntoView(By.id("trialDTO.leadOrganizationNameField"));
        hover(By.id("trialDTO.leadOrganizationNameField"));
        clickAndWaitAjax("link=National Cancer Institute Division of Cancer Prevention (Your Affiliation)");

        // Select Principal Investigator
        driver.switchTo().defaultContent();
        clickAndWaitAjax("xpath=//div[@id='loadPersField']//button");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", 30);   
        selenium.type("firstName", "John");
        selenium.type("lastName", "Doe");
        clickAndWaitAjax("id=search_person_btn");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();

        // Select Sponsor
        driver.switchTo().defaultContent();
        hover(By.id("trialDTO.sponsorNameField"));
        clickAndWaitAjax("link=Cancer Therapy Evaluation Program");
        
        selenium.select("trialDTO.responsiblePartyType", "label=Sponsor");

        // Select Funding Sponsor
        hover(By.id("trialDTO.summaryFourOrgName"));
        clickAndWaitAjax("xpath=//table[@id='dropdown-sum4Organization']//a[text()='National Cancer Institute']");
        selenium.type("trialDTO.programCodeText", "PG"+rand);
        
        // Grants
        moveElementIntoView(By.id("nciGrantfalse")); 
        selenium.click("nciGrantfalse");
        selenium.select("fundingMechanismCode", "label=B09");
        selenium.select("nihInstitutionCode", "label=AA");
        selenium.type("serialNumber", rand);
        selenium.select("nciDivisionProgramCode", "label=CCR");
        selenium.click("grantbtnid");
        waitForElementById("grantdiv", 5);

        // Trial Status Information
        driver.switchTo().defaultContent();
        selenium.select("trialDTO_statusCode", "label=In Review");
        selenium.type("trialDTO_statusDate", today);
        selenium.type("trialDTO_startDate", tommorrow);
        selenium.click("trialDTO_startDateTypeAnticipated");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", oneYearFromToday);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", oneYearFromToday);
        
        // IND/IDE
        moveElementIntoView(By.id("group3"));
        selenium.select("group3", "label=IND");
        selenium.type("id=indidenumber", rand);        
        selenium.click("id=SubCat");
        selenium.select("id=SubCat", "label=CDER");                
        selenium.select("holderType", "label=NIH");
        selenium.select("programcodenihselectedvalue", "label=NEI-National Eye Institute");
        moveElementIntoView(By.id("group4"));  
        driver.findElement(By.id("group4")).sendKeys(" ");
        //selenium.click("id=group4");
        selenium.select("expanded_status", "label=Available");
        selenium.click("exemptIndicator");
        moveElementIntoView(By.id("addbtn"));
        selenium.click("addbtn");
        waitForElementById("indidediv", 5);

        // Regulator Information
        moveElementIntoView(By.id("countries")); 
        selenium.select("countries", "label=United States");
        pause(1000);
        moveElementIntoView(By.id("trialDTO.selectedRegAuth"));  
        selenium.select("trialDTO.selectedRegAuth", "label=Food and Drug Administration");
        
        moveElementIntoView(By.id("trialDTO.fdaRegulatoryInformationIndicatorYes")); 
        selenium.click("trialDTO.fdaRegulatoryInformationIndicatorYes");
        selenium.click("trialDTO.section801IndicatorYes");
        selenium.click("trialDTO.delayedPostingIndicatorYes");
        selenium.click("trialDTO.dataMonitoringCommitteeAppointedIndicatorYes");
      

        // Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);
        selenium.type("participatingSites", (new File(ClassLoader.getSystemResource(
                SITES_DOCUMENT).toURI()).toString()));
        selenium.type("informedConsentDocument", (new File(ClassLoader.getSystemResource(
                CONSENT_DOCUMENT).toURI()).toString()));
        selenium.type("submitTrial_otherDocument_0", (new File(ClassLoader.getSystemResource(
                OTHER_DOCUMENT).toURI()).toString()));
    }

    protected void hideTopMenu() {
        ((JavascriptExecutor) driver).executeScript("$('nav').hide();");
        
    }

    protected void registerDraftTrial(String trialName, String leadOrgTrialId)
            throws URISyntaxException, SQLException {
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        today = MONTH_DAY_YEAR_FMT.format(new Date());
        tommorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(new Date(), 1));
        oneYearFromToday = MONTH_DAY_YEAR_FMT.format(DateUtils.addYears(
                new Date(), 1));

        // Select register trial and choose trial type
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

        // Select Lead Organization
        clickAndWaitAjax("link=Look Up Org");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        selenium.type("orgNameSearch", "Division");
        waitForElementById("search_organization_btn", 15);
        if (firstRun) {
            // compiling the popup jsp the first time screws up selenium, so
            // give it some time
            System.out.println("Waiting on first run - org");
            pause(2000);
        }
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 30);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        // Select Principal Investigator
        driver.switchTo().defaultContent();
        clickAndWaitAjax("link=Look Up Person");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", 30);
        if (firstRun) {
            // compiling the popup jsp the first time screws up selenium, so
            // give it some time
            System.out.println("Waiting on first run - pers");
            pause(2000);
        }
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/a");
        waitForPageToLoad();

        // Select Sponsor
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

        // Select Funding Sponsor
        clickAndWaitAjax("//div[@id='loadSummary4FundingSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("search_organization_btn", 15);
        selenium.type("orgNameSearch", "Division");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();

        // Trial Status Information
        driver.switchTo().defaultContent();
        selenium.select("trialDTO_statusCode", "label=In Review");
        selenium.type("trialDTO_statusDate", today);
        selenium.type("trialDTO_startDate", tommorrow);
        selenium.click("trialDTO_startDateTypeAnticipated");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", oneYearFromToday);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", oneYearFromToday);

        // Regulator Information
        selenium.select("countries", "label=United States");
        selenium.click("//option[@value='1026']");
        selenium.select("trialDTO.fdaRegulatoryInformationIndicator",
                "label=Yes");
        selenium.select("trialDTO.section801Indicator", "label=Yes");
        selenium.select("trialDTO.delayedPostingIndicator", "label=Yes");
        selenium.select("trialDTO.dataMonitoringCommitteeAppointedIndicator",
                "label=Yes");

        // Grants
        selenium.click("nciGrantfalse");

        // Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
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

    protected void changeRegUserAffiliation(String loginName, int orgPoId,
            String orgName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update registry_user set affiliate_org='"
                + orgName
                + "', affiliated_org_id="
                + orgPoId
                + " where csm_user_id=(select user_id from csm_user where csm_user.login_name like '%"
                + loginName + "%')";
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

}
