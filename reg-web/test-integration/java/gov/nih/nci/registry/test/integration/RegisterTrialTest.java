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
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * Tests the trial registration process.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class RegisterTrialTest extends AbstractRegistrySeleniumTest {
    private static final String PROTOCOL_DOCUMENT = "ProtocolDoc.doc";
    private static final String IRB_DOCUMENT = "IrbDoc.doc";

    /**
     * Tests registering a trial.
     * @throws Exception on error
     */
    @Test
    public void testRegisterTrial() throws Exception {
        String today = monthDayYearFormat.format(new Date());
        String tommorrow = monthDayYearFormat.format(DateUtils.addDays(new Date(), 1));
        String oneYearFromToday = monthDayYearFormat.format(DateUtils.addYears(new Date(), 1));

        loginAsAbstractor();
        isLoggedIn();
        disclaimer(true);

        //Select register trial and choose trial type
        clickAndWaitAjax("registerTrialMenuOption");
        selenium.selectFrame("popupFrame");
        waitForElementById("summaryFourFundingCategoryCode", 60);
        selenium.select("summaryFourFundingCategoryCode", "label=Institutional");
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();

        selenium.selectFrame("relative=up");
        waitForElementById("submitTrial_trialDTO_leadOrgTrialIdentifier", 15);
        selenium.type("submitTrial_trialDTO_leadOrgTrialIdentifier", "LEAD-ORG");
        selenium.type("submitTrial_trialDTO_officialTitle", "Test Trial created by Selenium.");
        selenium.select("trialDTO.phaseCode", "label=0");
        selenium.select("trialDTO.primaryPurposeCode", "label=Treatment");

        //Select Lead Organization
        clickAndWaitAjax("link=Look Up Org");
        selenium.selectFrame("popupFrame");
        waitForPageToLoad();
        waitForElementById("poOrganizations", 15);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[7]/a");
        waitForPageToLoad();

        //Select Principal Investigator
        selenium.selectFrame("relative=up");
        clickAndWaitAjax("link=Look Up Person");
        selenium.selectFrame("popupFrame");
        waitForPageToLoad();
        waitForElementById("poOrganizations", 15);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[6]/a");
        waitForPageToLoad();

        //Select Sponsor
        selenium.selectFrame("relative=up");
        clickAndWaitAjax("//div[@id='loadSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("poOrganizations", 15);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[7]/a");
        waitForPageToLoad();

        selenium.selectFrame("relative=up");
        selenium.type("trialDTO.contactEmail", "selenium@example.com");
        selenium.type("trialDTO.contactPhone", "123-456-7890");

        //Select Funding Sponsor
        clickAndWaitAjax("//div[@id='loadSummary4FundingSponsorField']/table/tbody/tr/td[2]/ul/li/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("poOrganizations", 15);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[7]/a");
        waitForPageToLoad();

        //Trial Status Information
        selenium.selectFrame("relative=up");
        selenium.select("submitTrial_trialDTO_statusCode", "label=In Review");
        selenium.type("submitTrial_trialDTO_statusDate", today);
        selenium.type("submitTrial_trialDTO_startDate", tommorrow);
        selenium.click("submitTrial_trialDTO_startDateTypeAnticipated");
        selenium.click("submitTrial_trialDTO_completionDateTypeAnticipated");
        selenium.type("submitTrial_trialDTO_completionDate", oneYearFromToday);

        //Regulator Information
        selenium.select("countries", "label=United States");
        selenium.click("//option[@value='1026']");
        selenium.select("trialDTO.fdaRegulatoryInformationIndicator", "label=Yes");
        selenium.select("trialDTO.section801Indicator", "label=Yes");
        selenium.select("trialDTO.delayedPostingIndicator", "label=Yes");
        selenium.select("trialDTO.dataMonitoringCommitteeAppointedIndicator", "label=Yes");

        //Add Protocol and IRB Document
        String protocolDocPath = (new File(ClassLoader.getSystemResource(PROTOCOL_DOCUMENT).toURI()).toString());
        String irbDocPath = (new File(ClassLoader.getSystemResource(IRB_DOCUMENT).toURI()).toString());
        selenium.type("submitTrial_protocolDoc", protocolDocPath);
        selenium.type("submitTrial_irbApproval", irbDocPath);
        pause(5000);
        clickAndWaitAjax("link=Review Trial");
        waitForElementById("reviewTrialForm", 15);
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();

        //Verify that we end up back on the search trials page.
        selenium.isElementPresent("link=Search My Trials");
        selenium.isElementPresent("link=Search All Trials");
        selenium.isElementPresent("link=Reset");
        selenium.isElementPresent("link=Search Saved Drafts");
    }
}
