/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
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
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
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

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import com.dumbster.smtp.SmtpMessage;

/**
 * Searches and Amends trial in Registry.
 * 
 * @author gundalar
 */
@SuppressWarnings("deprecation")
public class AmendTrialTest extends AbstractRegistrySeleniumTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testAmendCompleteTrialAndCloseSitesPO_8323() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        // Prepare amendable trial.
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        final String nciID = info.nciID;
        addDWS(info,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        logoutUser();

        // Add 3 sites, one is already closed.
        selectTrialInPA(info);
        addSiteToTrial(info, "DCP", "In Review", true);
        addSiteToTrial(info, "CTEP", "Active" , true);
        addSiteToTrial(info, "NCI", "Withdrawn" , true);
        logoutPA();

        // Amend
        loginAndAcceptDisclaimer();
        searchForTrialByNciID(nciID);
        selectAction("Amend");

        // Close trial.
        deleteStatus(2);
        deleteStatus(1);
        addStatus(date(-3), "In Review", "");
        addStatus(date(-2), "Approved", "");
        addStatus(date(-1), "Active", "");
        addStatus(date(0), "Closed to Accrual", "");

        populateAmendmentNumber();
        populateTrialDates();
        populateDocuments();
        assertFalse(driver.findElement(
                By.id("trialDTO.delayedPostingIndicatorNo")).isEnabled());
        assertTrue(driver.findElement(
                By.id("trialDTO.delayedPostingIndicatorNo")).isSelected());
        // Try to submit and verify the dialog (see JIRA).
        submitTrialAndVerifyOpenSitesDialog(new String[] { "In Review",
                "Active" }, "Review Trial" , true);

        // Verify amendment went through.
        waitForElementById("reviewTrialForm", 10);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The amendment to trial with the NCI Identifier "
                        + nciID + " was successfully submitted."));

        // Do backend checks; ensure sites are closed with the same status.
        assertEquals(
                "CLOSED_TO_ACCRUAL",
                getTrialStatusHistory(info).get(
                        getTrialStatusHistory(info).size() - 1).statusCode);
        verifySiteIsNowClosed(info,
                "National Cancer Institute Division of Cancer Prevention",
                "Closed to Accrual");
        verifySiteIsNowClosed(info, "Cancer Therapy Evaluation Program",
                "Closed to Accrual");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAmendTrial() throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialInfo trialInfo = createTrial();
        amendTrial(trialInfo);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testServerSideErrorDuringAmendAndTransactionRollback()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        QueryRunner runner = new QueryRunner();
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        final String nciID = info.nciID;
        addDWS(info,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        searchForTrialByNciID(nciID);
        selectAction("Amend");
        populateAmendTrialPage(info);
        try {
            runner.update(connection,
                    "update pa_properties set name = '_trial.amend.body' "
                            + " where name = 'trial.amend.body'");
            for (int i = 1; i <= 3; i++) {

                int countBefore = ((Number) runner.query(connection,
                        "select count(*) from study_protocol",
                        new ArrayHandler())[0]).intValue();

                clickAndWait("xpath=//button[text()='Review Trial']");
                waitForElementById("reviewTrialForm", 10);
                clickAndWait("xpath=//button[text()='Submit']");
                waitForPageToLoad();
                assertFalse(selenium
                        .isTextPresent("The amendment to trial with the NCI Identifier "
                                + nciID + " was successfully submitted."));

                assertTrue(selenium
                        .isTextPresent("PA_PROPERTIES does not have entry for trial.amend.body"));

                int countAfter = ((Number) runner.query(connection,
                        "select count(*) from study_protocol",
                        new ArrayHandler())[0]).intValue();

                assertEquals(
                        i
                                + ") Test ran non transactionally and left junk!! in database",
                        countBefore, countAfter);

            }
        } finally {
            runner.update(connection,
                    "update pa_properties set name = 'trial.amend.body' "
                            + " where name = '_trial.amend.body'");
        }
    }

    @SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    public void testLateRejectionMilestoneOnAmendedTrial_PO_8835()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        TrialInfo info = amendTrialAndAddMilestones();

        // Late reject entire trial.
        restartEmailServer();
        String action = "Reject Entire Trial";
        lateReject(action);
        assertTrue(s
                .isElementPresent("xpath=//input[@value='Late Rejection Date']"));
        assertTrue(s.isTextPresent("Processing Status: Rejected"));
        assertTrue(s.isTextPresent("Amendment Number: 1"));
        assertFalse(s.isTextPresent("Administrative Data"));
        assertFalse(s.isTextPresent("Scientific Data"));
        assertFalse(s.isTextPresent("Completion"));

        waitForEmailsToArrive(1);
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        String subject = email.getHeaderValues("Subject")[0];
        String to = email.getHeaderValues("To")[0];
        String body = email.getBody().replaceAll("\\s+", " ")
                .replaceAll(">\\s+", ">");
        assertEquals("NCI CTRP: Trial REGISTRATION REJECTED for " + info.nciID
                + ",", subject);
        assertEquals(
                "<hr><p><b>Title: </b>An Open-Label Study of Ruxolitinib "
                        + info.rand
                        + "</p><table border=\"0\"><tr><td><b>Lead Organization Trial ID:</b></td><td>LEAD"
                        + info.rand
                        + "</td></tr><tr><td><b>Lead Organization:</b></td><td>National Cancer Institute Division of Cancer Prevention</td></tr><tr><td><b>CTRP-assigned Lead Organization ID:</b></td><td>3</td></tr><tr><td><b>NCI Trial ID:</b></td><td>"
                        + info.nciID
                        + "</td></tr><tr><td><b>NCT ID:</b></td><td>NCT"
                        + info.rand
                        + "</td></tr><tr><td><b>Other IDs:</b></td><td>OTHER"
                        + info.rand
                        + "</td></tr><tr><td><b>Submission Date:</b></td><td>"
                        + today
                        + "</td></tr></table><hr><p>Date: "
                        + today
                        + "</p><p>Dear Abstractor User,</p><p>The Clinical Trials Reporting Office (CTRO) staff cannot register the trial identified above in the NCI Clinical Trials Reporting Program (CTRP) for the following reason(s):<br><i>Rejecting entire trial..</i></p><p><b>NEXT STEPS:</b><br>If you feel that this trial has been rejected in error, please contact us at ncictro@mail.nih.gov at your earliest convenience to resolve the issue.</p><p>If you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov.</p><p>Thank you for participating in the NCI Clinical Trials Reporting Program.</p>",
                body);

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testLateRejectionMilestoneRevertingAmendmentOnlyPO_8835()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        amendTrialAndAddMilestones();

        // Late reject entire trial.
        String action = "Reject This Amendment Only";
        lateReject(action);

        // We should have gone to the original submission now.
        assertFalse(s
                .isElementPresent("xpath=//input[@value='Late Rejection Date']"));
        assertTrue(s
                .isTextPresent("Processing Status: Abstraction Verified No Response"));
        assertFalse(s.isTextPresent("Amendment Number: 1"));
        assertTrue(s.isTextPresent("Administrative Data"));
        assertTrue(s.isTextPresent("Scientific Data"));
        assertTrue(s.isTextPresent("Completion"));
        assertEquals("1", s.getValue("submissionNumber"));

        // Late Rejection milestone should have recorded into the rejected
        // amendment.
        s.select("submissionNumber", "2");
        waitForPageToLoad();
        assertTrue(s
                .isElementPresent("xpath=//input[@value='Late Rejection Date']"));

    }


    @SuppressWarnings("deprecation")
    @Test
    //PO-9304 - should change the last submitter and last submitter org
    public void testTransferOfOwnershipFollowedByAmend()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        //When I login as Abstractor and create a trial & logout
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo trialInfo = registerAndAcceptTrial(rand);
        final String nciID = trialInfo.nciID;
        logoutUser();

        //Then trial should be available in search
        loginAsSuperAbstractor();
        String trialID = searchAndSelectTrial(trialInfo.title);
        assertEquals(nciID, trialID);

        //And go to ownership page
        clickLinkAndWait("Assign Ownership");

        //Then I see the last submitter as 'abstractor-ci'
        assertEquals("abstractor-ci", getUIRowValue("Last Submitter:"));

        //And I see the last submitter organization as ''
        assertEquals("National Cancer Institute Division of Cancer Prevention", getUIRowValue("Last Submitter Organization:"));
        logoutPA();

        //Now create a new ctepAbstractor and transfer trial ownership
        String ctepAbstractor = "u" + rand;
        Number csmUserId = createCSMUser(ctepAbstractor);
        createRegistryUserForCTEP(csmUserId);
        assignUserToGroup(ctepAbstractor, "Submitter");
        assignUserToGroup(ctepAbstractor, "RegAdmin");
        assignUserToGroup(ctepAbstractor, "ScientificAbstractor");
        assignUserToGroup(ctepAbstractor, "AdminAbstractor");
        assignUserToGroup(ctepAbstractor, "SuAbstractor");
        assignTrialOwner(ctepAbstractor, trialInfo.id);

        //When I Login as ctepAbstractor
        login(ctepAbstractor, "pass");
        handleDisclaimer(true);

        //then I must see the trial
        searchForTrialByNciID(nciID);
        assertTrue(selenium.isTextPresent("OTHER" + rand));

        //When I amend trial & logout from registry
        amendTrial(trialInfo);

        logoutUser();

        //When I login to PA as ctepAbstractor
        loginPA(ctepAbstractor, "pass");
        disclaimer(true);
        String trialID2 = searchAndSelectTrial(trialInfo.title);
        assertEquals(nciID, trialID2);

        //And go to ownership page
        clickLinkAndWait("Assign Ownership");

        //Then I see the last submitter as ctepAbstractor
        assertEquals(ctepAbstractor, getUIRowValue("Last Submitter:"));
        //and the organization as CTEP
        assertEquals("Cancer Therapy Evaluation Program", getUIRowValue("Last Submitter Organization:"));
        logoutPA();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testLateRejectionDialogMustNotShowUpForOriginalSubmission_PO_8835()
            throws Exception {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);

        loginAsSuperAbstractor();
        searchAndSelectTrial(info.title);
        addMilestones();

        s.select("milestone", "Late Rejection Date");
        s.type("milestoneCommentsTA", "Rejecting entire trial.");
        clickAndWait("addMilestoneBtn");
        assertFalse(s.isVisible("late-reject-dialog"));
        assertTrue(s
                .isElementPresent("xpath=//input[@value='Late Rejection Date']"));
        assertTrue(s.isTextPresent("Processing Status: Rejected"));
        assertFalse(s.isTextPresent("Administrative Data"));
        assertFalse(s.isTextPresent("Scientific Data"));
        assertFalse(s.isTextPresent("Completion"));

    }

    /**
     * @param action
     */
    private void lateReject(String action) {
        s.select("milestone", "Late Rejection Date");
        s.type("milestoneCommentsTA", "Rejecting entire trial.");
        s.click("addMilestoneBtn");
        waitForElementToBecomeVisible(By.id("late-reject-dialog"), 5);
        assertEquals("Late Rejection",s.getText("class=ui-dialog-title"));
        assertEquals(
                "Please indicate an action you want to take on this trial:", s
                        .getText("late-reject-dialog").trim());

        clickAndWait("xpath=//button/span[text()='" + action + "']");
    }

    /**
     * @throws URISyntaxException
     * @throws SQLException
     */
    private TrialInfo amendTrialAndAddMilestones() throws URISyntaxException,
            SQLException {
        TrialInfo trialInfo = createTrial();
        amendTrial(trialInfo);
        logoutPA();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trialInfo.title);
        acceptTrial();

        addMilestones();

        return trialInfo;
    }

    /**
     * 
     */
    private void addMilestones() {
        clickAndWait("link=Trial Milestones");
        addMilestone("Administrative Processing Start Date");
        addMilestone("Administrative Processing Completed Date");
        addMilestone("Ready for Administrative QC Date");
        addMilestone("Administrative QC Start Date");
        addMilestone("Administrative QC Completed Date");
        addMilestone("Scientific Processing Start Date");
        addMilestone("Scientific Processing Completed Date");
        addMilestone("Ready for Scientific QC Date");
        addMilestone("Scientific QC Start Date");
        addMilestone("Scientific QC Completed Date");
    }

    private void addMilestone(String milestone) {
        s.select("milestone", milestone);
        s.type("milestoneCommentsTA", "Just testing something...");
        clickAndWait("id=addMilestoneBtn");
        assertTrue(s.isElementPresent("xpath=//input[@value='" + milestone
                + "']"));
    }

    /**
     * Will create a new trial
     * @return
     * @throws URISyntaxException
     * @throws SQLException
     */
    private TrialInfo createTrial() throws URISyntaxException, SQLException {
        loginAndAcceptDisclaimer();
        String rand = RandomStringUtils.randomNumeric(10);
        TrialInfo info = registerAndAcceptTrial(rand);
        return info;
    }

    /**
     * @throws URISyntaxException
     * @throws SQLException
     */
    private TrialInfo amendTrial(TrialInfo info) throws URISyntaxException, SQLException {

        final String nciID = info.nciID;
        addDWS(info,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());

        searchForTrialByNciID(nciID);
        selectAction("Amend");
        populateAmendTrialPage(info);
        clickAndWait("xpath=//button[text()='Review Trial']");
        waitForElementById("reviewTrialForm", 10);
        clickAndWait("xpath=//button[text()='Submit']");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The amendment to trial with the NCI Identifier "
                        + nciID + " was successfully submitted."));

        // Trial Status
        verifyTrialStatus(nciID, "Active");

        // Make sure deleted statuses are there as well.
        List<TrialStatus> hist = getDeletedTrialStatuses(info);
        assertEquals(2, hist.size());

        assertTrue(DateUtils.isSameDay(hist.get(0).statusDate, yesterdayDate));
        assertEquals("IN_REVIEW", hist.get(0).statusCode);
        assertEquals("Wrong status", hist.get(0).comments);

        assertTrue(DateUtils.isSameDay(hist.get(1).statusDate, new Date()));
        assertEquals("APPROVED", hist.get(1).statusCode);
        assertEquals("Wrong status", hist.get(1).comments);

        return info;
    }

    private void populateAmendTrialPage(TrialInfo info)
            throws URISyntaxException {
        populateAmendmentNumber();

        deleteStatus(2);
        deleteStatus(1);
        populateStatusHistory();
        editStatus(2, today, "Active", "Changed to Active.");

        populateTrialDates();

        populateDocuments();
    }

    /**
     * 
     */
    protected void populateAmendmentNumber() {
        s.type("trialDTO.localAmendmentNumber", "1");
        s.click("xpath=//span[@class='add-on btn-default' and preceding-sibling::input[@id='trialDTO.amendmentDate']]");
        clickOnFirstVisible(By.xpath("//td[@class='day active']"));
        clickOnFirstVisible(By
                .xpath("//div[@class='datepicker']/button[@class='close']"));
    }

    /**
     * @throws URISyntaxException
     */
    protected void populateDocuments() throws URISyntaxException {
        // Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(
                PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(
                IRB_DOCUMENT).toURI()).toString());
        selenium.type("protocolDoc", protocolDocPath);
        selenium.type("irbApproval", irbDocPath);
        selenium.type("protocolHighlightDocument", protocolDocPath);
    }

    /**
     * 
     */
    private void populateTrialDates() {
        // Start/End Dates.
        selenium.type("trialDTO_startDate", today);
        selenium.click("trialDTO_startDateTypeActual");
        selenium.click("trialDTO_primaryCompletionDateTypeAnticipated");
        selenium.type("trialDTO_primaryCompletionDate", tommorrow);
        selenium.click("trialDTO_completionDateTypeAnticipated");
        selenium.type("trialDTO_completionDate", tommorrow);
        assertFalse(s
                .isElementPresent("xpath=//input[@type='radio' and @value='N/A']"));
    }

    @Test
    public void testLeadOrgSelectionFixed() throws SQLException,
            URISyntaxException {
        if (isPhantomJS() && SystemUtils.IS_OS_LINUX) {
            // PhantomJS keeps crashing on Linux CI box. No idea why at the
            // moment.
            return;
        }

        TrialInfo info = createAcceptedTrial(false);
        final String nciID = getLastNciId();
        acceptTrialByNciIdWithGivenDWS(nciID, info.leadOrgID,
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .toString());
        assignTrialOwner("abstractor-ci", info.id);

        loginAndAcceptDisclaimer();

        String rand = info.leadOrgID;
        runSearchAndVerifySingleTrialResult("officialTitle", rand, info);
        invokeAmendTrial();
        changeAndVerifyLeadOrganization();
    }


    private void invokeAmendTrial() {
        final By selectActionBtn = By
                .xpath("//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']");
        moveElementIntoView(selectActionBtn);
        driver.findElement(selectActionBtn).click();
        driver.findElement(By.xpath("//li/a[normalize-space(text())='Amend']"))
                .click();

        assertEquals("1",
                selenium.getValue("trialDTO.leadOrganizationIdentifier"));
    }

    private void changeAndVerifyLeadOrganization() {

        final By leadOrgElement = By.id("trialDTO.leadOrganizationNameField");
        moveElementIntoView(leadOrgElement);
        driver.findElement(leadOrgElement).click();
        driver.findElement(
                By.xpath("//tr/td/a[normalize-space(text())='Search...']"))
                .click();
        selenium.selectFrame("popupFrame");

        final By poIDElement = By.id("orgPOIdSearch");
        moveElementIntoView(poIDElement);
        selenium.type("orgPOIdSearch", "2");
        clickAndWaitAjax("search_organization_btn");
        assertTrue(selenium.isTextPresent("One item found.1"));

        moveElementIntoView(By.xpath("//table[@id='row']/tbody/tr/td/button"));
        selenium.click("//table[@id='row']/tbody/tr/td/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        assertEquals("2",
                selenium.getValue("trialDTO.leadOrganizationIdentifier"));
    }

}
