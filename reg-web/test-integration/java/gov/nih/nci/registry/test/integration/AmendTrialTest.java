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
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

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
        addSiteToTrial(info, "DCP", "In Review");
        addSiteToTrial(info, "CTEP", "Active");
        addSiteToTrial(info, "NCI", "Closed to Accrual");
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

        // Try to submit and verify the dialog (see JIRA).
        submitTrialAndVerifyOpenSitesDialog(new String[] { "In Review",
                "Active" }, "Review Trial");

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
    private void populateDocuments() throws URISyntaxException {
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

    /**
     * @param fieldID
     * @param value
     * @param info
     */
    protected void runSearchAndVerifySingleTrialResult(String fieldID,
            String value, TrialInfo info) {

        accessTrialSearchScreen();
        selenium.type(fieldID, value);

        selenium.click("runSearchBtn");
        clickAndWait("link=All Trials");
        waitForElementById("row", 10);
        verifySingleTrialSearchResult(info);

    }

    /**
     * @param info
     */
    protected void verifySingleTrialSearchResult(TrialInfo info) {
        assertEquals(
                info.nciID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[1]/a"));
        assertEquals(info.title,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[2]"));
        assertEquals("Approved",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[3]"));
        assertEquals("ClinicalTrials.gov",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[4]"));
        assertEquals(info.leadOrgID,
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[5]"));
        assertEquals("Doe, John",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[6]"));
        assertEquals(
                "View",
                selenium.getText("xpath=//table[@id='row']/tbody/tr[1]/td[9]/a"));
        assertTrue(selenium
                .isElementPresent("xpath=//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']"));
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
