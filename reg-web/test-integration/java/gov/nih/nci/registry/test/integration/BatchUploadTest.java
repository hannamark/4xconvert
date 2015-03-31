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

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import com.dumbster.smtp.SmtpMessage;

/**
 * @author dkrylov
 */
public class BatchUploadTest extends AbstractRegistrySeleniumTest {

    @SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    public void testRegisterTrial() throws Exception {
        registerNewTrialAndVerify();

    }

    @SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    public void testUpdateTrial() throws Exception {
        String leadOrgTrialId = "FKTESTING_23";
        registerNewTrialAndVerify();

        changeNciId(getLastNciId(), "NCI-2015-99999");
        deleteTrialDocuments(acceptTrialByNciId("NCI-2015-99999",
                leadOrgTrialId));
        logoutUser();

        loginAndAcceptDisclaimer();
        accessBatchUploadScreen();

        final String trialDataFileName = "batch_update.xls";
        restartEmailServer();
        submitBatchFile(trialDataFileName);

        Number trialID = waitForTrialToRegister(leadOrgTrialId, 60);
        waitForEmailToArriveAndStopServer();
        verifyEmailSentByBatchProcessing();

        TrialInfo trial = new TrialInfo();
        trial.id = trialID.longValue();
        assertEquals("CLOSED_TO_ACCRUAL",
                getCurrentTrialStatus(trial).statusCode);
        assertTrue(DateUtils.isSameDay(getCurrentTrialStatus(trial).statusDate,
                DateUtils.parseDate("06/04/13", new String[] { "MM/dd/yy" })));

    }

    /**
     * @throws SQLException
     * @throws URISyntaxException
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    private void registerNewTrialAndVerify() throws SQLException,
            URISyntaxException, ParseException {
        loginAndAcceptDisclaimer();

        String leadOrgTrialId = "FKTESTING_23";
        deactivateTrialByLeadOrgId(leadOrgTrialId);
        accessBatchUploadScreen();

        restartEmailServer();

        final String trialDataFileName = "batch_new_registration.xls";
        submitBatchFile(trialDataFileName);

        Number trialID = waitForTrialToRegister(leadOrgTrialId, 60);
        assertNotNull(trialID);

        waitForEmailToArriveAndStopServer();

        TrialInfo trial = new TrialInfo();
        trial.id = trialID.longValue();
        assertEquals("APPROVED", getCurrentTrialStatus(trial).statusCode);
        assertTrue(DateUtils.isSameDay(getCurrentTrialStatus(trial).statusDate,
                DateUtils.parseDate("06/03/13", new String[] { "MM/dd/yy" })));

        verifyEmailSentByBatchProcessing();
    }

    /**
     * @param server
     * @throws SQLException
     */
    @SuppressWarnings("rawtypes")
    private void verifyEmailSentByBatchProcessing() throws SQLException {
        assertEquals(1, server.getReceivedEmailSize());
        Iterator emailIter = server.getReceivedEmail();
        SmtpMessage email = (SmtpMessage) emailIter.next();
        final String body = email.getBody();
        System.out.println(body);
        verifyBody(body);

        // Ensure same info was written into email log table.
        pause(3000);
        QueryRunner runner = new QueryRunner();
        String loggedBody = (String) runner
                .query(connection,
                        "SELECT body FROM email_log INNER JOIN email_attachment ON email_log.identifier=email_attachment.email_id "
                                + "WHERE outcome='SUCCESS' "
                                + "ORDER BY date_sent desc LIMIT 1",
                        new ArrayHandler())[0];
        verifyBody(loggedBody);
    }

    /**
     * @param body
     */
    private void verifyBody(final String body) {
        assertTrue(body.replaceAll("(\\r|\\n)+", "").contains(
                "<td><b>Number of trials submitted: </b></td><td>1</td>"));
        assertTrue(body
                .replaceAll("(\\r|\\n)+", "")
                .contains(
                        "<td><b>Number of trials registered successfully: </b></td><td>1</td>"));
    }

    /**
     * @param server
     */
    private void waitForEmailToArriveAndStopServer() {
        // wait for email to arrive
        long stamp = System.currentTimeMillis();
        while (server.getReceivedEmailSize() < 1
                && System.currentTimeMillis() - stamp < 10 * 1000) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        server.stop();
    }

    /**
     * @param trialDataFileName
     * @throws URISyntaxException
     */
    @SuppressWarnings("deprecation")
    private void submitBatchFile(final String trialDataFileName)
            throws URISyntaxException {
        selenium.type("orgName",
                "National Cancer Institute Division of Cancer Prevention");
        selenium.type("trialData",
                (new File(ClassLoader.getSystemResource(trialDataFileName)
                        .toURI()).toString()));
        selenium.type("docZip",
                (new File(ClassLoader.getSystemResource("batch.zip").toURI())
                        .toString()));

        selenium.click("xpath=//button[normalize-space(text())='Upload Trials']");
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("Batch Trial Upload Confirmation"));
        driver.switchTo().defaultContent();
        selenium.click("popCloseBox");
    }

    /**
     * 
     */
    private void accessBatchUploadScreen() {
        hoverLink("Register Trial");
        waitForElementToBecomeVisible(
                By.xpath("//button[normalize-space(text())='Batch Upload']"), 5);
        clickAndWait("xpath=//button[normalize-space(text())='Batch Upload']");
        waitForElementToBecomeVisible(By.id("popupFrame"), 15);
        selenium.selectFrame("id=popupFrame");
        waitForElementToBecomeVisible(
                By.xpath("//button[normalize-space(text())='Upload Trials']"),
                5);
    }

}
