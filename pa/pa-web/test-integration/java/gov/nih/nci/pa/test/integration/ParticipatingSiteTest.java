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

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

/**
 * Tests creation/editing/deleting of participating sites.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class ParticipatingSiteTest extends AbstractPaSeleniumTest {
    private String today = MONTH_DAY_YEAR_FMT.format(new Date());

    /**
     * Creates participating sites.
     * 
     * @throws SQLException
     */
    @Test
    public void testAccrualDatesSavedProperly_PO_8774() throws SQLException {
        TrialInfo info = createAndSelectTrial();

        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId, "Withdrawn");
        Number siteID = findParticipatingSite(info,
                "National Cancer Institute Division of Cancer Prevention");

        QueryRunner runner = new QueryRunner();
        String sql = "update study_site set accrual_date_range_low=now(), accrual_date_range_high=now() where identifier="
                + siteID;
        runner.update(connection, sql);

        // Reset Accrual dates.
        clickAndWait("link=Participating Sites");
        clickAndWait("//table[@id='row']/tbody/tr/td[7]/a");
        s.type("participatingOrganizationsedit_dateOpenedForAccrual", "");
        s.type("participatingOrganizationsedit_dateClosedForAccrual", "");
        s.click("link=Save");
        waitForElementToBecomeAvailable(By.className("confirm_msg"), 10);
        assertTrue(selenium.isTextPresent("Message. Record Updated."));

        // Ensure they are showing blanks.
        clickAndWait("link=Participating Sites");
        clickAndWait("//table[@id='row']/tbody/tr/td[7]/a");
        assertEquals(
                "",
                s.getValue("participatingOrganizationsedit_dateOpenedForAccrual"));
        assertEquals(
                "",
                s.getValue("participatingOrganizationsedit_dateClosedForAccrual"));

    }
    
    /**
     * Creates participating sites with double quotes
     * 
     * @throws SQLException
     */
    @Test
    public void testAddParticiptingSiteWithDoubleQuotes() throws SQLException {
        TrialInfo info = createAndSelectTrial();
        addSiteToTrialWithName(info, "double", "In Review");
        
        clickAndWait("link=Participating Sites");
        
        assertTrue(selenium.isTextPresent("Double \" quotes\""));
      
    }
    
    /**
     * Creates participating sites with double quotes
     * 
     * @throws SQLException
     */
    @Test
    public void testAddParticiptingSiteWithSingleQuotes() throws SQLException {
        TrialInfo info = createAndSelectTrial();
        addSiteToTrialWithName(info, "single", "In Review");
        
        clickAndWait("link=Participating Sites");
        
        assertTrue(selenium.isTextPresent("single \' quotes\'"));
      
    }
    
    /**
     * Creates participating sites with new program code
     * 
     * @throws SQLException
     */
    @Test
    public void testAddingPSWithProgramCode() throws SQLException {
        
        TrialInfo info = createAndSelectTrial();

        String siteCtepId = "DCP";
        addSiteToTrial(info, siteCtepId, "Withdrawn");
        Number siteID = findParticipatingSite(info,
                "National Cancer Institute Division of Cancer Prevention");

        // Reset Accrual dates.
        clickAndWait("link=Participating Sites");
        clickAndWait("//table[@id='row']/tbody/tr/td[7]/a");
        s.type("participatingOrganizationsedit_dateOpenedForAccrual", "");
        s.type("participatingOrganizationsedit_dateClosedForAccrual", "");
        addNewPrgCd("programCodes", "PC-CD-3", "PC-NM-3", false);
        //checking already present condition
        addNewPrgCd("programCodes", "PC-CD-3", "PC-NM-3", true);
        assertOptionSelected("PC-NM-3");
        
        s.click("link=Save");
        waitForElementToBecomeAvailable(By.className("confirm_msg"), 10);
        assertTrue(selenium.isTextPresent("Message. Record Updated."));

        // Ensure they are showing blanks.
        clickAndWait("link=Participating Sites");
        clickAndWait("//table[@id='row']/tbody/tr/td[7]/a");
        assertEquals(
                "",
                s.getValue("participatingOrganizationsedit_dateOpenedForAccrual"));
        assertEquals(
                "",
                s.getValue("participatingOrganizationsedit_dateClosedForAccrual"));
        
        QueryRunner runner = new QueryRunner();
        String sql = "delete from org_family_program_code "
                + "where org_family_po_id='1' and program_name='PC-NM-3' and program_code='PC-CD-3'";
        runner.update(connection, sql);
    }
    
    @SuppressWarnings("deprecation")
    private void addNewPrgCd(String id, String newCd,
            String newValue, boolean shouldOptionBePresent) {
        useSelect2ToPickAnOption(id, "Add New", "Add New ...");
        
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        
        boolean elementPresent = s.isElementPresent("//input[@id='poOrgFamilyName']");
        assertTrue(elementPresent);
        selenium.type("orgFamProgramCodeDto.programName", newValue);
        selenium.type("orgFamProgramCodeDto.programCode", newCd);
        selenium.click("xpath=//div[@class='actionsrow']//span[normalize-space(text())='Save']");
        waitForPageToLoad();
        if (shouldOptionBePresent) {
            waitForElementToBecomeAvailable(By.className("error_msg"), 10);
            assertTrue(selenium.isTextPresent("Entered program code and program name combo "
                    + "already exists for, National Cancer Institute."));
        } else {
            waitForElementToBecomeAvailable(By.className("confirm_msg"), 10);
            assertTrue(selenium.isTextPresent("Message. Record Created."));
        }
        clickAndWait("xpath=//div[@class='actionsrow']//span[normalize-space(text())='Close']");
        driver.switchTo().defaultContent();
    }
    
    @SuppressWarnings("deprecation")
    private void useSelect2ToPickAnOption(String id, String sendKeys,
            String option) {
        WebElement sitesBox = driver.findElement(By
                .xpath("//span[preceding-sibling::select[@id='" + id
                        + "']]//input[@type='search']"));
        sitesBox.click();
        assertTrue(s.isElementPresent("select2-" + id + "-results"));
        sitesBox.sendKeys(sendKeys);

        By xpath = null;
        try {
            xpath = By.xpath("//li[@role='treeitem' and text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 3);
        } catch (TimeoutException e) {
            xpath = By.xpath("//li[@role='treeitem']//b[text()='" + option
                    + "']");
            waitForElementToBecomeAvailable(xpath, 15);
        }

        driver.findElement(xpath).click();
    }

    /**
     * @param option
     */
    @SuppressWarnings("deprecation")
    private void assertOptionSelected(String option) {
        assertTrue(s.isElementPresent(getXPathForSelectedOption(option)));
    }

    /**
     * @param option
     * @return
     */
    private String getXPathForSelectedOption(String option) {
        return "//li[@class='select2-selection__choice' and @title='" + option
                + "']";
    }
}
