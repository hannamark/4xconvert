/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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
package gov.nih.nci.coppa.test.integration.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;

/**
 * Selenium test for managing organization relationships
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class OrganizationRelationshipTest extends AbstractPoWebTest {

    public void testOrganizationRelationships() throws Exception {
        loginAsCurator();
        openOrganizationFamilyCreate();
        selenium.type("familyEntityForm_family_name", "Test Family");
        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Test Family was successfully created"));

        String famId = selenium.getValue("id=familyEntityForm_family_id");

        // Add organizations to this family
        addFamilyMember(famId, "ClinicalTrials.gov");
        addFamilyMember(famId, "Cancer Therapy Evaluation Program");
        addFamilyMember(famId, "Division of Cancer Control and Population Sciences");
        addFamilyMember(famId, "National Cancer Institute");

        // Verify that you can browse through all the orgs for the family
        navigateToFamilyOrgRelationship(1, "Cancer Therapy Evaluation Program");
        clickAndWait("return_to_button");
        navigateToFamilyOrgRelationship(2, "ClinicalTrials.gov");
        clickAndWait("return_to_button");
        navigateToFamilyOrgRelationship(3, "Division of Cancer Control and Population Sciences");
        clickAndWait("return_to_button");
        navigateToFamilyOrgRelationship(4, "National Cancer Institute");
        clickAndWait("return_to_button");

        // Set ClinicalTrials.gov as a parent of Cancer Therapy Evaluation Program, and verify that CTEP is now a child
        // of ct.gov
        navigateToFamilyOrgRelationship(2, "ClinicalTrials.gov");
        addRelationship(1, "PARENT");
        clickAndWait("return_to_button");
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[5]//ul//li[1]/a");
        validateRelatedOrg(1, "CHILD");

        // Change ct.gov to be a division of CTEP and verify that CTEP is a subdivision of ct.gov
        clickAndWait("xpath=//table[@id='row']//tr[2]//td[5]//ul//li[1]/a");
        updateRelationship(1, "DIVISION");
        clickAndWait("return_to_button");
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[5]//ul//li[1]/a");
        validateRelatedOrg(1, "SUBDIVISION");

        // Remove the relationship from ct.gov
        clickAndWait("xpath=//table[@id='row']//tr[2]//td[5]//ul//li[1]/a");        
        removeRelationship(1, true);

        // browse through all the organizations again
        navigateToFamilyOrgRelationship(1, "Cancer Therapy Evaluation Program");
        clickAndWait("return_to_button");
        navigateToFamilyOrgRelationship(2, "ClinicalTrials.gov");
        clickAndWait("return_to_button");
        navigateToFamilyOrgRelationship(3, "Division of Cancer Control and Population Sciences");
        clickAndWait("return_to_button");
        navigateToFamilyOrgRelationship(4, "National Cancer Institute");
        clickAndWait("return_to_button");

        //Nullify family to remove from view as not to interfere with other tests
        selenium.select("familyEntityForm.family.statusCode", "NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWait("save_button");        
    }

    private void addFamilyMember(String famId, String organizationName) {
        clickAndWait("add_family_member_id_" + famId);
        pause(2000);
        clickAndWait("link=Search");
        selenium.selectFrame("popupFrame");
        waitForElementById("duplicateOrganizationForm_criteria_name", 10);
        selenium.type("duplicateOrganizationForm_criteria_name", organizationName);
        clickAndWait("submitDuplicateOrganizationForm");
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[6]/ul/li/a");

        driver.switchTo().defaultContent();
        selenium.select("familyOrgRelationship.functionalType", "ORGANIZATIONAL");
        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Organization Family Relationship was successfully created."));
        clickAndWait("return_to_button");
    }

    private void navigateToFamilyOrgRelationship(int row, String orgName) {
        clickAndWait("xpath=//table[@id='row']//tr[" + row+ "]//td[5]//ul//li[1]/a");

        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]/a"));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]/a[2]"));
        assertTrue(StringUtils.isBlank(selenium.getText("xpath=//table[@id='row']//tr[1]/td[2]")));

        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[2]"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[2]//td[1]/a"));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='row']//tr[2]//td[1]/a[2]"));
        assertTrue(StringUtils.isBlank(selenium.getText("xpath=//table[@id='row']//tr[2]/td[2]")));

        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[3]"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[3]//td[1]/a"));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='row']//tr[3]//td[1]/a[2]"));
        assertTrue(StringUtils.isBlank(selenium.getText("xpath=//table[@id='row']//tr[3]/td[2]")));

        assertFalse(selenium.isElementPresent("xpath=//table[@id='row']//tr[4]"));

        assertTrue(selenium.isTextPresent("Organization Relationship to " + orgName));
    }

    private void addRelationship(int row, String relationshipName) {
        clickAndWait("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a");
        selenium.selectFrame("popupFrame");
        waitForElementById("organizationRelationshipForm", 10);
        assertTrue(selenium.isTextPresent("Change Hierarchical Relationship"));
        assertTrue(selenium.isTextPresent("New Relationship"));
        assertFalse(selenium.isTextPresent("Old Relationship"));
        assertTrue(selenium.isElementPresent("add_relationship_button"));
        assertTrue(selenium.isElementPresent("cancel_button"));

        selenium.select("newOrgRelationship.hierarchicalType", relationshipName);
        selenium.click("add_relationship_button");

        driver.switchTo().defaultContent();
        pause(2000);
        waitForElementById("row", 10);

        String info = selenium.getText("xpath=//table[@id='row']//tr[" + row + "]//td[1]");
        assertTrue(StringUtils.contains(info, relationshipName));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a[2]"));
        assertFalse(StringUtils.isBlank(selenium.getText("xpath=//table[@id='row']//tr[" + row + "]/td[2]")));
        assertTrue(selenium.isTextPresent("Organization relationship successfully changed."));
    }

    private void updateRelationship(int row, String relationshipName) throws Exception {
        clickAndWait("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a");
        driver.switchTo().frame(driver.findElement(By.id("popupFrame")));
        waitForElementById("organizationRelationshipForm", 10);
        assertTrue(selenium.isTextPresent("Change Hierarchical Relationship"));
        assertTrue(selenium.isTextPresent("New Relationship"));
        assertTrue(selenium.isTextPresent("Old Relationship"));
        assertTrue(selenium.isElementPresent("add_relationship_button"));
        assertTrue(selenium.isElementPresent("cancel_button"));

        enterDate("orgRelationship.endDate", "");
        selenium.click("add_relationship_button");
        waitForElementById("organizationRelationshipForm", 10);
        assertTrue(selenium.isTextPresent("End Date is required"));
        selenium.select("newOrgRelationship.hierarchicalType", relationshipName);

        String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        enterDate("orgRelationship.endDate", today);
        enterDate("newOrgRelationship.startDate", today);
        driver.findElement(By.tagName("h2")).click();
        selenium.click("add_relationship_button");
        pause(10000);
        
        driver.switchTo().defaultContent();        
        waitForElementById("row", 20);

        String info = selenium.getText("xpath=//table[@id='row']//tr[" + row + "]//td[1]");
        assertTrue(StringUtils.contains(info, relationshipName));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a[2]"));
        assertFalse(StringUtils.isBlank(selenium.getText("xpath=//table[@id='row']//tr[" + row + "]/td[2]")));
        assertTrue(selenium.isTextPresent("Organization relationship successfully changed."));
    }

    private void removeRelationship(int row, boolean accept) {
        if (accept) {
            selenium.chooseOkOnNextConfirmation();            
            clickAndWait("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a[2]");
            pause(10000);
            waitForElementById("row", 10);
            assertTrue(selenium.isTextPresent("Organization relationship successfully removed."));
            clickAndWait("return_to_button");
        } else {
            selenium.chooseCancelOnNextConfirmation();
            selenium.click("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a[2]");
            pause(10000);
            assertFalse(selenium.isTextPresent("Organization relationship successfully removed."));
        }
    }

    private void validateRelatedOrg(int row, String relationshipName) {
        String info = selenium.getText("xpath=//table[@id='row']//tr[" + row + "]//td[1]");
        assertTrue(StringUtils.contains(info, relationshipName));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[" + row + "]//td[1]/a[2]"));
        assertFalse(StringUtils.isBlank(selenium.getText("xpath=//table[@id='row']//tr[" + row + "]/td[2]")));
        clickAndWait("return_to_button");
    }

}
