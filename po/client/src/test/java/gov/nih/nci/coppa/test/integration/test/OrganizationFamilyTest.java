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

/**
 * @author ludetc
 *
 */
public class OrganizationFamilyTest extends AbstractPoWebTest {

    private static String FAMILY_NAME = "myTestFamily" + new Date().getTime();
    private static String FUNCTIONAL_TYPE = "CONTRACTUAL";
    private final SimpleDateFormat dateFormat =
        new SimpleDateFormat("MM/d/yyyy");

   
    @SuppressWarnings("deprecation")
    public void testList(){
        loginAsCurator();
        openOrganizationFamilyList();
        assertEquals("Family ID", selenium.getTable("row.0.0"));
        assertEquals("Family Name", selenium.getTable("row.0.1"));
        assertEquals("Organization Family Members", selenium.getTable("row.0.2"));
        assertEquals("Status", selenium.getTable("row.0.3"));
        assertEquals("Action", selenium.getTable("row.0.4"));
        assertTrue(selenium.isElementPresent("createNewFamily"));

        createNewFamily();

        assertTrue(selenium.isElementPresent("return_to_button"));
        clickAndWait("return_to_button");

        selenium.isTextPresent(FAMILY_NAME);
        String famId = getFamilyId();
        assertNotNull(famId);
    }

    /**
     * 
     */
    private void createNewFamily() {
        FAMILY_NAME = "myTestFamily" + new Date().getTime();
        createFamily(FAMILY_NAME);
    }
   
    public void testOrgSearchByFamily(){
        loginAsCurator();
        createNewFamily();
        addOrgMember("National Cancer Institute");
        searchForCreatedFamily("National Cancer Institute");
        addOrgMember("ClinicalTrials");
        searchForCreatedFamily("ClinicalTrials");
        removeMember();
        removeMember();
    } 

    public void testFamilyOrgPerspective(){
        loginAsCurator();
        createNewFamily();
        createNewFamily();
        addOrgMember("ClinicalTrials");
        openSearchOrganization();
        selenium.type("searchOrganizationForm_criteria_name", "National");
        clickAndWait("submitSearchOrganizationForm");
        clickAndWait(getLinkStartingWith("org_id_"));
        assertTrue(selenium.isTextPresent("Manage Family(s)"));
        accessFamilyScreen();
        addFamilyRelationship();
        checkManageFamilyScreenOrgPerspective();
        clickAndWait("//table[@id='row']/tbody/tr[" + getRowThatContainsText(FAMILY_NAME, 1) + "]/td[5]/ul/li[1]/a");
        checkOrgRelationshipScreenOrgPerspective();
        clickAndWait("return_to_button");
        clickAndWait("return_to_button");
        assertTrue(selenium.isTextPresent("Organization Details"));
        removeMemberOrgPerspective();
        removeMember();
    }

    private void removeMemberOrgPerspective() {
        searchForCreatedFamily("National");
        String orgId = selenium.getTable("row." + getRowThatContainsText("National", 1) + ".0");
        clickAndWait("org_id_" + orgId);
        accessFamilyScreen();
        selenium.chooseOkOnNextConfirmation();
        clickAndWait(getLinkStartingWith("fam_org_relationship_remove_id_"));       
        assertTrue(selenium.isTextPresent("Organization Family Relationship was successfully removed."));
        assertTrue(selenium.isTextPresent("Organization Information"));
    }

    private void checkManageFamilyScreenOrgPerspective() {
        assertTrue(selenium.isTextPresent("Organization Details"));
        assertNotNull(getFamilyId());
        assertTrue(selenium.isElementPresent("link=Add"));
        assertTrue(selenium.isElementPresent("link=Edit"));
        assertTrue(selenium.isElementPresent("link=Remove"));
    }

    private void addFamilyRelationship() {
        addFamilyToOrg();
        assertTrue(selenium.isTextPresent("Organization Family Relationship was successfully created."));
        clickAndWait("return_to_button");
        addFamilyToOrg();
        assertTrue(selenium.isTextPresent("An active family organization relationship already exists for this organization."));
        clickAndWait("return_to_button");
    }

    private void addFamilyToOrg() {
        clickAndWait("link=Add");
        pause(2000);
        clickAndWait("link=Search");
        pause(3000);
        selenium.selectFrame("popupFrame");
        String familyId = getFamilyId();
        assertTrue(selenium.isElementPresent("select_family_" + familyId));
        clickAndWait("select_family_" + familyId);
        pause(1000);
        driver.switchTo().defaultContent();
        selenium.select("familyOrgRelationship.functionalType", FUNCTIONAL_TYPE);
        clickAndWait("save_button");
        pause(2000);
    }

    @SuppressWarnings("deprecation")
    private void checkOrgRelationshipScreenOrgPerspective() {
        assertTrue(selenium.isTextPresent(FAMILY_NAME));
        assertTrue(selenium.isTextPresent("Family Organization Relationship"));
        assertEquals(FUNCTIONAL_TYPE, selenium.getSelectedValue("name=familyOrgRelationship.functionalType"));
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Return to Family Information"));
        assertTrue(selenium.isTextPresent("Hierarchical Relationship to other Organizations within this Family"));
        assertTrue(selenium.getTable("row.0.0").contains("Organization Relationship to"));
        assertTrue(selenium.getTable("row.0.1").equals("Start Date"));
        assertTrue(selenium.getTable("row.1.0").contains("has no relationship (change)"));
        assertTrue(StringUtils.isBlank(selenium.getTable("row.1.1")));
    }
   
    public void testRemoveFamily(){
        loginAsCurator();
        createNewFamily();
        openOrganizationFamilyList();
        String famId = getFamilyId();
        assertNotNull(famId);
        clickAndWait("edit_family_id_" + famId);
        selenium.select("name=family.statusCode", "NULLIFIED");
        enterDate("name=family.endDate", dateFormat.format(new Date()));
        selenium.chooseOkOnNextConfirmation();
        clickAndWait("save_button");        
        assertTrue(selenium.isTextPresent("Family " + FAMILY_NAME +" was successfully nullified."));
    }

    private void searchForCreatedFamily(String orgName) {
        openSearchOrganization();
        selenium.type("searchOrganizationForm_criteria_name", orgName);
        selenium.type("searchOrganizationForm_criteria_familyName", FAMILY_NAME);
        clickAndWait("submitSearchOrganizationForm");
        verify(orgName);
    }

    private void verify(String orgName) {
        int row = getRowThatContainsText(orgName, 1);
        if (row == -1) {
            fail("Did not find " + FAMILY_NAME + " in search results");
        }
    }

    private void createFamily(String familyName) {
        openOrganizationFamilyCreate();
        selenium.type("familyEntityForm_family_name", familyName);
        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent(familyName + " was successfully created"));
    }

    private void addOrgMember(String orgSearchName) {
        openOrganizationFamilyList();
        String famId = getFamilyId();
        assertNotNull(famId);

        clickAndWait("edit_family_id_" + famId);

        clickAndWait("add_family_member_id_" + famId);
        clickAndWait("link=Search");
        selenium.selectFrame("popupFrame");
        waitForElementById("duplicateOrganizationForm_criteria_name", 10);
        selenium.type("duplicateOrganizationForm_criteria_name", orgSearchName);
        clickAndWait("submitDuplicateOrganizationForm");

        clickAndWait(getLinkStartingWith("mark_as_dup"));

        driver.switchTo().defaultContent();

        clickAndWait("save_button");

        assertTrue(selenium.isTextPresent("Organization Family Relationship was successfully created."));

        selenium.select("familyOrgRelationship.functionalType", FUNCTIONAL_TYPE);
        enterDate("familyOrgRelationship.endDate", "01/01/2050");

        selenium.chooseOkOnNextConfirmation();
        clickAndWait("save_button");        
        assertTrue(selenium.isTextPresent("End Date must not be in the future"));

        enterDate("familyOrgRelationship.endDate", "01/01/2009");
        selenium.chooseOkOnNextConfirmation();
        clickAndWait("save_button");        
        assertTrue(selenium.isTextPresent("must be before"));

        enterDate("familyOrgRelationship.endDate", "");

        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Organization Family Relationship was successfully updated."));
    }

    private void removeMember() {
        openOrganizationFamilyList();
        String famId = getFamilyId();
        assertNotNull(famId);
        selenium.chooseOkOnNextConfirmation();
        clickAndWait("edit_family_id_" + famId);
        clickAndWait(getLinkStartingWith("fam_org_relationship_remove_id_"));
        assertTrue(selenium.isTextPresent("Organization Family Relationship was successfully removed."));
        assertTrue(selenium.isTextPresent("Family Details"));
        
    }

    private String getFamilyId() {
        int row = getRow(FAMILY_NAME, 1);
        return selenium.getTable("row." + row + ".0");
    }

    private String getLinkStartingWith(String key) {
        String[] links = selenium.getAllLinks();
        for (String link : links) {
            if (link.startsWith(key)) {
                return(link);
            }
        }
        return null;
    }
}
