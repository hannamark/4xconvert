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

import java.net.URISyntaxException;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Tests trial search in Registry, as well as search-related functionality.
 * 
 * @author dkrylov
 */
@SuppressWarnings("deprecation")
public class AddUpdateSiteTest extends AbstractRegistrySeleniumTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testPO8268_SingleSiteFromFamily() throws URISyntaxException,
            SQLException {
        TrialInfo info = createAndSelectTrial();

        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId);

        findInMyTrials();
        invokeUpdateMySite();

        // Since there is only one site from the family on this trial at this
        // point, we should have gone straight
        // to updating it.
        assertEquals("National Cancer Institute Division of Cancer Prevention",
                selenium.getValue("organizationName"));
        driver.findElement(By.xpath("//button[normalize-space(text())='Save']"))
                .click();
        driver.switchTo().defaultContent();
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("Your site information has been updated."));

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPO8268_AllSitesFromFamilyAndNotAffiliatedWithCancerCenter()
            throws URISyntaxException, SQLException {
        TrialInfo info = createAndSelectTrial();

        addSiteToTrial(info, "DCP");
        addSiteToTrial(info, "CTEP");
        addSiteToTrial(info, "NCI");

        findInMyTrials();
        invokeUpdateMySite();

        // We must be presented with an option to update one of the two
        // non-cancer center sites.
        assertWeAreOnSiteSelectionScreen("National Cancer Institute Division of Cancer Prevention");

        String[] options = selenium.getSelectOptions("pickedSiteOrgPoId");
        assertEquals(2, options.length);
        assertTrue(ArrayUtils.contains(options,
                "Cancer Therapy Evaluation Program"));
        assertTrue(ArrayUtils.contains(options,
                "National Cancer Institute Division of Cancer Prevention"));

        pickAndUpdateCTEP();

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPO8268_AllSitesFromFamilyAndAffiliatedWithCancerCenter()
            throws URISyntaxException, SQLException {

        changeUserAffiliation("submitter-ci@example.com", "4",
                "National Cancer Institute");

        TrialInfo info = createAndSelectTrial();

        addSiteToTrial(info, "DCP");
        addSiteToTrial(info, "CTEP");
        addSiteToTrial(info, "NCI");

        findInMyTrials();
        invokeUpdateMySite();

        // We must be presented with an option to update one of the 3
        // non-cancer center sites.
        assertWeAreOnSiteSelectionScreen("National Cancer Institute");

        String[] options = selenium.getSelectOptions("pickedSiteOrgPoId");
        assertEquals(3, options.length);
        assertTrue(ArrayUtils.contains(options,
                "Cancer Therapy Evaluation Program"));
        assertTrue(ArrayUtils.contains(options, "National Cancer Institute"));
        assertTrue(ArrayUtils.contains(options,
                "National Cancer Institute Division of Cancer Prevention"));

        pickAndUpdateCTEP();

    }

    @Override
    public void tearDown() throws Exception {
        changeUserAffiliation("submitter-ci@example.com", "3",
                "National Cancer Institute Division of Cancer Prevention");
        super.tearDown();
    }

    private void changeUserAffiliation(String email, String poID, String orgName)
            throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "UPDATE registry_user SET affiliated_org_id=" + poID
                + ", affiliate_org='" + orgName + "' WHERE email_address='"
                + email + "'";
        runner.update(connection, sql);

    }

    /**
     * 
     */
    private void pickAndUpdateCTEP() {
        selenium.select("pickedSiteOrgPoId",
                "label=Cancer Therapy Evaluation Program");
        clickAndWait("pickSiteBtn");
        assertEquals("Cancer Therapy Evaluation Program",
                selenium.getValue("organizationName"));
        selenium.type("programCode", "CTEP_PGCODE");
        driver.findElement(By.xpath("//button[normalize-space(text())='Save']"))
                .click();
        driver.switchTo().defaultContent();
        waitForPageToLoad();
        assertTrue(selenium
                .isTextPresent("Your site information has been updated."));
        // Make sure the right site got updated.
        assertEquals("Cancer Therapy Evaluation Program",
                selenium.getText("//table[@id='row']/tbody/tr[2]/td[1]"));
        assertEquals("CTEP_PGCODE",
                selenium.getText("//table[@id='row']/tbody/tr[2]/td[4]"));
    }

    /**
     * 
     */
    private void assertWeAreOnSiteSelectionScreen(String orgName) {
        assertTrue(selenium
                .isTextPresent("Based on the fact that your organization belongs to a family, you can update more than one "
                        + "site on this trial. Please select the site you would like to update below:"));
        assertEquals(orgName, selenium.getSelectedLabel("pickedSiteOrgPoId"));
    }

    /**
     * 
     */
    private void findInMyTrials() {
        loginAsSubmitter();
        handleDisclaimer(true);
        accessTrialSearchScreen();
        selenium.click("runSearchBtn");
        clickAndWait("link=My Trials");
        waitForElementById("row", 10);
    }

    /**
     * @return
     * @throws SQLException
     */
    private TrialInfo createAndSelectTrial() throws SQLException {
        deactivateAllTrials();
        TrialInfo info = createAcceptedTrial(true);
        login("/pa", "ctrpsubstractor", "Coppa#12345");
        disclaimer(true);
        searchAndSelectTrial(info.title);
        return info;
    }

    /**
     * 
     */
    private void invokeUpdateMySite() {
        final By selectActionBtn = By
                .xpath("//table[@id='row']/tbody/tr[1]/td[10]//button[normalize-space(text())='Select Action']");
        moveElementIntoView(selectActionBtn);
        driver.findElement(selectActionBtn).click();
        driver.findElement(
                By.xpath("//li/a[normalize-space(text())='Update My Site']"))
                .click();
        selenium.selectFrame("popupFrame");
    }

    /**
     * @param info
     * @param siteCtepId
     */
    public void addSiteToTrial(TrialInfo info, String siteCtepId) {
        clickAndWait("link=Participating Sites");
        clickAndWait("link=Add");
        clickAndWaitAjax("link=Look Up");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("orgCtepIdSearch", 15);
        selenium.type("orgCtepIdSearch", siteCtepId);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        selenium.click("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        driver.switchTo().defaultContent();
        selenium.type("siteLocalTrialIdentifier", info.uuid);
        selenium.select("recStatus", "In Review");
        selenium.type("id=recStatusDate", today);
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));

        selenium.click("link=Investigators");
        clickAndWaitAjax("link=Add");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        waitForElementById("poOrganizations", 15);
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        clickAndWaitAjax("//table[@id='row']/tbody/tr[1]/td[9]/a");
        waitForPageToLoad();
        pause(2000);
        driver.switchTo().defaultContent();
        assertTrue(selenium.isTextPresent("One item found"));
    }

    private void makeIndustrial(TrialInfo info) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "UPDATE study_protocol SET proprietary_trial_indicator=true WHERE identifier="
                + info.id;
        runner.update(connection, sql);
    }

}
