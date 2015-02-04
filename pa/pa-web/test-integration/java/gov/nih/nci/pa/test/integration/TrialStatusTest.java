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
package gov.nih.nci.pa.test.integration;



import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests for trial status transitions.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class TrialStatusTest extends AbstractPaSeleniumTest {
    private String tomorrow = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), 1));
    private String today = MONTH_DAY_YEAR_FMT.format(new Date());
    private String yesterday = MONTH_DAY_YEAR_FMT.format(DateUtils.addDays(
            new Date(), -1));

    /**
     * Tests the standard trial transitions from In Review to Complete, with
     * stops at Approved, Active, Closed to Accrual & Closed to Accrual and
     * Intervention.
     * 
     * @throws SQLException
     */
    @Test
    public void testStandardTrialStatusTransitions() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Approved", false, false, null);
        changeStatus("Active", true, false, null);
        changeStatus("Closed to Accrual", false, false, null);
        changeStatus("Closed to Accrual and Intervention", false, false, null);
        changeStatus("Complete", false, true, null);
    }

    /**
     * Tests going from In Review to Active to Complete.
     * 
     * @throws SQLException
     */
    @Test
    public void testSkippedCompleteStatus() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Active", true, false, null);
        changeStatus("Complete", false, true, null);
    }

    /**
     * Tests going from In Review to Active to Administratively Complete.
     * 
     * @throws SQLException
     */
    @Test
    public void testSkippedAdministrativelyCompleteStatus() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);

        clickAndWait("link=Trial Status");
        changeStatus("Active", true, false, null);
        changeStatus("Administratively Complete", false, true,
                "Administratively Complete Reason.");
    }

    @SuppressWarnings("deprecation")
    private void changeStatus(String statusName, boolean startDateActual,
            boolean completionDateActual, String statusReason) {
        selenium.select("id=currentTrialStatus", "label=" + statusName);

        if (startDateActual) {
            selenium.type("id=startDate", yesterday);
            selenium.click("id=startDateTypeActual");
        }

        if (completionDateActual) {
            selenium.click("id=primaryCompletionDateTypeActual");
            selenium.type("id=primaryCompletionDate", today);
        }

        if (StringUtils.isNotEmpty(statusReason)) {
            selenium.type("id=statusReason", statusReason);
        }
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
    }

    @Test
    public void testStandardTrialStatusTransitionsForScientificAbs()
            throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsScientificAbstractor();
        searchSelectAndAcceptTrial(trial.title, false, true);
        clickAndWait("link=Trial Status");
        changeStatus("Approved", false, false, null);
        changeStatus("Active", true, false, null);
        changeStatus("Closed to Accrual", false, false, null);
        changeStatus("Closed to Accrual and Intervention", false, false, null);
        changeStatus("Complete", false, true, null);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTrialHistoryForScientificAbs() throws SQLException {
        TrialInfo trial = createSubmittedTrial();

        loginAsScientificAbstractor();
        searchSelectAndAcceptTrial(trial.title, false, true);
        clickAndWait("link=Trial Status");
        clickAndWait("link=History");
        assertTrue(selenium.isTextPresent("Status History"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testTransitionValidationService() throws SQLException {
        TrialInfo trial = createAcceptedTrial();
        loginAsAdminAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsAdminAbstractor();
        clickAndWait("link=Trial Status");

        // Merely entering a comment should not affect the status history.
        selenium.type("additionalComments", "Just a comment");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        clickAndWait("link=Trial Status");
        assertEquals("Just a comment", selenium.getValue("additionalComments"));
        List<TrialStatus> hist = getTrialStatusHistory(trial);
        assertEquals(1, hist.size());
        assertEquals("APPROVED", hist.get(0).statusCode);
        assertEquals("Just a comment", hist.get(0).comments);

        // Should be unable to move to a status with a future date
        TrialStatus statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", tomorrow);
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("Current Trial Status Date cannot be in the future."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Should be unable to move to a status with a past date
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", yesterday);
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("New current status date should be bigger/same as old date."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Active status cannot precede trial start
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", "04/15/2013");
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("If Current Trial Status is Active, Trial Start Date must be Actual and same as or smaller than Current Trial Status Date."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Complete status past trial end date.
        clickAndWait("link=Trial Status");
        statusBefore = getCurrentTrialStatus(trial);
        selenium.type("statusDate", "04/17/2018");
        selenium.select("id=currentTrialStatus", "label=Complete");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("If Current Trial Status is Completed, Primary Completion Date must be Actual."));
        assertTrue(selenium
                .isTextPresent("Current Trial Status Date cannot be in the future."));
        assertEquals(statusBefore, getCurrentTrialStatus(trial));

        // Move to Active: no errors.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus", "label=Active");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(driver.findElements(By.className("error_msg")).isEmpty());
        assertEquals("ACTIVE", getCurrentTrialStatus(trial).statusCode);

        // Move to Closed to Accrual and Intervention: a warning due to missing
        // optional status.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus",
                "label=Closed to Accrual and Intervention");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved"));
        assertEquals("CLOSED_TO_ACCRUAL_AND_INTERVENTION",
                getCurrentTrialStatus(trial).statusCode);

        // Move to Withdrawn produces an error: bad transition.
        clickAndWait("link=Trial Status");
        selenium.select("id=currentTrialStatus", "label=Withdrawn");
        clickAndWait("link=Save");
        assertTrue(selenium
                .isTextPresent("A reason must be entered when the study status is set to Withdrawn."));
        selenium.type("statusReason", "Just testing.");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));
        assertTrue(selenium
                .isTextPresent("Status Transition Errors and Warnings were found. Please use the History button to review and make corrections. Trial record cannot be checked-in until all Status Transition Errors have been resolved."));
        assertEquals("WITHDRAWN", getCurrentTrialStatus(trial).statusCode);

    }

}
