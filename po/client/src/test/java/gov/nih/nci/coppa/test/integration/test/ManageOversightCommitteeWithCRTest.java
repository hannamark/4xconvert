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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URISyntaxException;

import javax.naming.NamingException;

/**
 * @author kkanchinadam
 * @author Rohit Gupta
 *
 */
public class ManageOversightCommitteeWithCRTest extends AbstractManageOrgRolesWithCRTest {
    private final String TYPE_IRB = "Institutional Review Board (IRB)";
    private final String TYPE_EC = "Ethics Committee";
    private final String TYPE_REB = "Research Ethics Board";
    private final String ROLE_STATUS_ACTIVE = "ACTIVE";
    private final String ROLE_STATUS_PENDING = "PENDING";

    public void testOversightCommittee() throws Exception {
        // Setup
        setOrgRoleTitleText("Oversight Committee Information");
        setOrgRoleLinkText("link=Oversight Committee (0)");
        setOrgRoleCreateMessage("Oversight Committee was successfully created!");
        setOrgRoleUpdateMessage("Oversight Committee was successfully updated!");
        setOrgRoleSearchResultsRowNumber("oc_row.1.0");

        // Create a new organization.
        createActiveOrganization();

        // Check Required fields for Oversight Committee.
        checkRequiredFields();

        // Create
        setOrgRoleSearchResultsMessage("One item found");
        createOversightCommittee(TYPE_IRB, ROLE_STATUS_ACTIVE);
        setOrgRoleLinkText("link=Oversight Committee (1)");
        createDuplicateOversightCommittee(TYPE_IRB, ROLE_STATUS_ACTIVE);

        setOrgRoleSearchResultsMessage("2 items found, displaying all items");
        setOrgRoleSearchResultsRowNumber("oc_row.2.0");
        createOversightCommittee(TYPE_EC, ROLE_STATUS_PENDING);
        setOrgRoleLinkText("link=Oversight Committee (2) P");
        createDuplicateOversightCommittee(TYPE_EC, ROLE_STATUS_PENDING);

        // Test update.
        openOrgRoleScreen(false);
        waitForElementById("curateRoleForm_role_typeCode", 10);
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present
        assertTrue(selenium.isTextPresent("PO Curator"));
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_overriddenBy']")); // 'overriddenBy' div should be present
        assertTrue(selenium.isTextPresent("Not Overridden"));
        assertEquals(TYPE_EC, selenium.getSelectedLabel("id=curateRoleForm_role_typeCode"));
        logoutUser();// 'curator' logout 
        loginAsJohnDoe(); // some other curator 
        openOrgRoleScreen(false);
        waitForPageToLoad();
        clickAndWait("overcomm_override_button"); // click on Override button
        assertFalse(selenium.isTextPresent("Not Overridden")); 
        assertTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the OrgRole
        assertFalse(selenium.isTextPresent("Override"));
        // Update Type to TYPE_REB, and save.
        selenium.select("curateRoleForm_role_typeCode", TYPE_REB);
        updateOrganizationalRole();

        logoutUser();
        loginAsCurator();
        openOrgRoleScreen(false);
        waitForPageToLoad();
        // Ensure that you can now add TYPE_EC again, since the previous one was updated to TYPE_REB.
        setOrgRoleSearchResultsMessage("3 items found, displaying all items");
        setOrgRoleSearchResultsRowNumber("oc_row.3.0");
        createOversightCommittee(TYPE_EC, ROLE_STATUS_PENDING);
        setOrgRoleLinkText("link=Oversight Committee (3) P");
        createDuplicateOversightCommittee(TYPE_EC, ROLE_STATUS_PENDING);

        // Check the duplicate error message when attempting to add TYPE_REB again.
        createDuplicateOversightCommittee(TYPE_REB, ROLE_STATUS_PENDING);

        // Create another organization with status = PENDING. Create PENDING OC.
        // Attempt to create ACTIVE OC. Check Error Message.
        // Then test the CR.
        createPendingOrganization();
        setOrgRoleSearchResultsMessage("One item found");
        setOrgRoleSearchResultsRowNumber("oc_row.1.0");
        setOrgRoleLinkText("link=Oversight Committee (0)");
        createOversightCommittee(TYPE_IRB, ROLE_STATUS_PENDING);

        setOrgRoleLinkText("link=Oversight Committee (1) P");
        openOrgRoleScreen(true);
        selenium.select("curateRoleForm_role_typeCode", TYPE_EC);
        selenium.select("curateRoleForm.role.status", ROLE_STATUS_ACTIVE);
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Role status not compatible with associated entity's status"));
        // Test the CR
        checkCR();        
    }

    private void openOrgRoleScreen(boolean addMode) {
        openOrganizationCuratePage();
        accessOrgRoleScreen();
        if (addMode) {
            clickAndWait("add_button_oc");
        } else {
            clickAndWait("id=edit_oversightCommittee_id_" + getOrganizationalRoleId());
        }
    }

    private void checkRequiredFields() {
        openOrgRoleScreen(true);

        this.waitForElementById("curateRoleForm_role_typeCode", 10);
        this.waitForElementById("curateRoleForm.role.status", 10);
        // Attempt to save
        selenium.select("curateRoleForm_role_typeCode", "label=--Select a Type--");
        selenium.select("curateRoleForm.role.status", "label=--Select a Role Status--");
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Oversight Committee Type must be set"));
        assertTrue(selenium.isTextPresent("exact:Role Status must be set"));
    }

    private void createOversightCommittee(String oversightCommitteeType, String roleStatus)  {
        openOrgRoleScreen(true);
        assertFalse(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' shouldn't be present
        selenium.select("curateRoleForm_role_typeCode", oversightCommitteeType);
        selenium.select("curateRoleForm.role.status", roleStatus);
        saveOrganizationalRole();
    }

    private void createDuplicateOversightCommittee(String oversightCommitteeType, String roleStatus) {
        // Attempt saving the same type again. Check for error message.
        openOrgRoleScreen(true);
        selenium.select("curateRoleForm_role_typeCode", oversightCommitteeType);
        selenium.select("curateRoleForm.role.status", roleStatus);
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Organization already has an Oversight Committee of this type"));
    }

    private void checkCR() throws Exception {
        //create a cr remotely        
        updateRemoteOversightCommittee(getOrganizationalRoleId());        

        // scenario 1: Curator access a OrgRole-CR without clicking 'Override' button as curator created this OC
        //curate org
        openOrganizationCuratePage();
        // Go to the Manage OC Screen
        accessOrgRoleScreen();

        clickAndWait("edit_oversightCommittee_id_" + getOrganizationalRoleId());
        assertTrue(selenium.isTextPresent("exact:Edit Oversight Committee - Comparison"));

        // Check Status
        assertEquals("PENDING", selenium.getText("wwctrl_organization.statusCode"));

        // Confirm that current type = IRB.
        assertEquals(TYPE_IRB, selenium.getSelectedLabel("id=curateRoleForm_role_typeCode"));
        //Confirm change request type = Research Ethics Board.
        assertEquals(TYPE_REB, selenium.getText("wwctrl_curateCrForm_cr_typeCode_code").trim());
        assertTrue(selenium.isTextPresent("Copy")); // copy button present 
        // Copy over the new Type Code.
        selenium.click("copy_curateCrForm_role_typeCode");
        assertEquals(TYPE_REB, selenium.getSelectedLabel("id=curateRoleForm_role_typeCode"));

        // update org role and check for success message.
        updateOrganizationalRole();
        logoutUser();
        
        
        // Scenario 2: Curator access a OrgRole-CR after clicking 'Override' button 
        //create another CR remotely
        updateRemoteOversightCommittee(getOrganizationalRoleId());        
        loginAsJohnDoe();
        openOrgRoleScreen(false);
        assertFalse(selenium.isTextPresent("Copy")); // copy button not present as role is overrode by different curator
        assertTrue(selenium.isTextPresent("Override")); //Override button present as role is overrode by different curator 
        clickAndWait("overcomm_override_button"); // click on Override button
        assertTrue(selenium.isTextPresent("Copy")); // copy button present
        // Copy over the new Type Code.
        selenium.click("copy_curateCrForm_role_typeCode");
        assertEquals(TYPE_REB, selenium.getSelectedLabel("id=curateRoleForm_role_typeCode"));
        updateOrganizationalRole();
        logoutUser();        
    }

    private void updateRemoteOversightCommittee(String ext) throws EntityValidationException, NamingException, URISyntaxException,
            NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.4.4");
        id.setIdentifierName("NCI oversight committee identifier");

        OversightCommitteeDTO dto = RemoteServiceHelper.getOversightCommitteeCorrelationService().getCorrelation(id);

        dto.getTypeCode().setCode(TYPE_REB);
        RemoteServiceHelper.getOversightCommitteeCorrelationService().updateCorrelation(dto);
    }

    @Override
    protected String getSortFieldTestColumnName() {
        return "Oversight Committee Type";
    }
}
