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

import java.util.UUID;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests the trial registration process.
 * 
 * @author Denis G. Krylov
 */
public class RegisterAbbreviatedTrialTest extends AbstractRegistrySeleniumTest {

    private final String LEAD_ORG_TRIAL_ID = UUID.randomUUID().toString();
    private final String UUID_2 = UUID.randomUUID().toString();

    /**
     * Tests registering a trial.
     * 
     * @throws Exception
     *             on error
     */
    @Test
    public void testRegisterTrial() throws Exception {
        loginAndAcceptDisclaimer();
        openAndWait("/registry/protected/submitProprietaryTrial.action?sum4FundingCatCode=Industrial");
        populateBaseData();
        selenium.click("id=reviewTrialBtn");
        waitForPageToLoad();
        selenium.click("id=submitTrialBtn");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier"));

    }

    @Test
    public void testPO7459_CorrectErrorMessageSpacing() throws Exception {
        loginAndAcceptDisclaimer();
        openAndWait("/registry/protected/submitProprietaryTrial.action?sum4FundingCatCode=Industrial");
        populateBaseData();

        selenium.select("id=trialDTO.siteStatusCode",
                "Administratively Complete");
        selenium.type("id=trialDTO.siteStatusDate", "03/03/2014");

        selenium.click("id=reviewTrialBtn");
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("Date Opened for Accrual must be a valid date for Administratively Complete. "
                        + "Date Closed for Accrual must be a valid date for Administratively Complete."));

    }

    /**
     * 
     */
    private void populateBaseData() {
        selenium.click("id=trialDTO.leadOrganizationNameField");
        selenium.click("//table[@id='dropdown-leadOrganization']/tbody/tr[2]/td[3]/a");
        selenium.type("id=trialDTO.leadOrgTrialIdentifier", LEAD_ORG_TRIAL_ID);
        selenium.click("id=trialDTO.siteOrganizationNameField");
        selenium.click("//table[@id='dropdown-siteOrganization']/tbody/tr[2]/td[3]/a");
        selenium.type("id=trialDTO.localSiteIdentifier", LEAD_ORG_TRIAL_ID);
        selenium.type("id=trialDTO.officialTitle", LEAD_ORG_TRIAL_ID);

        checkInterventionalNonInterventionalFields_PO7457();

        selenium.click("id=trialDTO.trialType.Interventional");
        selenium.select("id=trialDTO.primaryPurposeCode", "Treatment");
        selenium.select("id=trialDTO.phaseCode", "0");

        // Site Principal Investigator
        clickAndWaitAjax("id=lookup4loadSitePersonBtn");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        waitForElementById("search_person_btn", 30);
        clickAndWaitAjax("id=search_person_btn");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[8]/button");
        waitForPageToLoad();
        driver.switchTo().defaultContent();

        selenium.click("id=trialDTO.summaryFourOrgName");
        selenium.click("//table[@id='dropdown-sum4Organization']/tbody/tr[2]/td[3]/a");
        selenium.select("id=trialDTO.siteStatusCode", "In Review");
        selenium.type("id=trialDTO.siteStatusDate", "06/02/2014");
    }

    private void checkInterventionalNonInterventionalFields_PO7457() {

        selenium.click("id=trialDTO.trialType.Noninterventional");
        assertTrue(selenium.isElementPresent("id=trialDTO.studySubtypeCode"));
        assertTrue(selenium.isElementPresent("id=trialDTO.studyModelCode"));
        assertTrue(selenium.isElementPresent("id=trialDTO.timePerspectiveCode"));
        assertTrue(selenium.isElementPresent("id=trialDTO.studyModelCode"));
        assertTrue(selenium.isElementPresent("id=trialDTO.studyModelCode"));
        assertFalse(driver.findElement(By.id("trialDTO.secondaryPurposes"))
                .isDisplayed());

        selenium.click("id=trialDTO.trialType.Interventional");
        assertFalse(driver.findElement(By.id("trialDTO.studySubtypeCode"))
                .isDisplayed());
        assertFalse(driver.findElement(By.id("trialDTO.studyModelCode"))
                .isDisplayed());
        assertFalse(driver.findElement(By.id("trialDTO.timePerspectiveCode"))
                .isDisplayed());
        assertFalse(driver.findElement(By.id("trialDTO.studyModelCode"))
                .isDisplayed());
        assertFalse(driver.findElement(By.id("trialDTO.studyModelCode"))
                .isDisplayed());
        assertTrue(selenium.isElementPresent("id=trialDTO.secondaryPurposes"));

    }

}