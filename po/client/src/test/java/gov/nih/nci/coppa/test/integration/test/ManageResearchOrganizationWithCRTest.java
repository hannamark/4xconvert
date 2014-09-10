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

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import org.apache.commons.dbutils.DbUtils;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * @author mshestopalov
 * @author Rohit Gupta
 *
 */
public class ManageResearchOrganizationWithCRTest extends AbstractManageOrgRolesWithCRTest {
    public String ORG_FOR_TEST = "org_for_test";
    public String AFFILIATE_ORG_FOR_IO = "affiliate_org_for_io";

    public void testCreateActiveOrganizationWithPendingRO() throws Exception {
        loginAsCurator();

        // create ACTIVE org we are using now.
        createOrganization("ACTIVE", ORG_FOR_TEST, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        String activeOrgId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", activeOrgId.trim());

        // Goto Manage RO Page
        accessManageResearchOrganizationScreen();
        clickAndWait("add_button_ro");


        assertTrue(selenium.isTextPresent("Research Organization Role Information"));
        assertFalse(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' shouldn't be present
        // ensure the player is ACTIVE
        assertEquals("ACTIVE", selenium.getText("wwctrl_organization.statusCode"));

        selenium.select("curateRoleForm.role.status", "label=PENDING");
        selenium.type("curateRoleForm.role.name", "original RO name");
        selenium.select("curateRoleForm_role_typeCode", "label=Cancer Center");
        waitForElementById("curateRoleForm.role._selectFundingMechanism", 30);
        selenium.select("curateRoleForm.role._selectFundingMechanism", "label=P30 - Center Core Grants");

        //add Contact Information
        waitForTelecomFormsToLoad();
        checkContactInformation();

        addUSPostalAddress();
        waitForTelecomFormsToLoad();
        addContactInformation();
        
        waitForAliasFormsToLoad();
        verifyAlias();
        inputAlias();

        clickAndWaitSaveButton();

        assertTrue(selenium.isTextPresent("exact:Research Organization was successfully created!"));
        String roId = selenium.getTable("ro_row.1.0");
        assertNotEquals("null", roId.trim());
        selenium.click("link=" + getSortFieldTestColumnName());
        pause(1000);
        roId = selenium.getTable("ro_row.1.0");
        assertNotEquals("null", roId.trim());

        clickAndWait("return_to_button");
        assertTrue(selenium.isTextPresent("exact:Basic Identifying Information"));
        // save everything
        clickAndWaitSaveButton();


        activateRole(Long.parseLong(roId));

        // Step2:: OrgRole not overridden, other curator (who didn't create the OrgRole) logs in.
        logoutUser();// 'curator' logout 
        loginAsJohnDoe(); // some other curator   
        openAndWait("/po-web/protected/roles/organizational/ResearchOrganization/start.action?organization=" + activeOrgId);
        clickAndWait("edit_researchOrganization_id_" + roId.trim());
        assertTrue(selenium.isTextPresent("Not Overridden"));
        clickAndWait("ro_override_button"); // click on Override button
        assertFalse(selenium.isTextPresent("Not Overridden")); 
        assertTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the OrgRole
        assertFalse(selenium.isTextPresent("Override"));
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");
        clickAndWaitSaveButton();
        logoutUser(); //'John Doe' logs out      
        loginAsCurator();
        
        // Add a CR
        updateRemoteRoOrg(roId.trim());
        
        // Scenario -- Different curator access a OrgRole-CR after clicking 'Override' button
        // Goto Manage RO Page.... should see CR
        openAndWait("/po-web/protected/roles/organizational/ResearchOrganization/start.action?organization=" + activeOrgId);
        clickAndWait("edit_researchOrganization_id_" + roId.trim());
        assertTrue(selenium.isTextPresent("exact:Edit Research Organization - Comparison"));        
        assertEquals("ACTIVE", selenium.getText("wwctrl_organization.statusCode")); // status
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present
        assertFalse(selenium.isTextPresent("Not Overridden")); 
        assertTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the OrgRole
        assertFalse(selenium.isTextPresent("Copy")); // copy button not present as role is overrode by different curator
        assertTrue(selenium.isTextPresent("Override")); //Override button present as role is overrode by different curator       
        clickAndWait("ro_override_button"); // click on Override button
        
        openAndWait("/po-web/protected/roles/organizational/ResearchOrganization/start.action?organization=" + activeOrgId);
        clickAndWait("edit_researchOrganization_id_" + roId.trim());

        // verify that the type code required indicator is not present. Verifies PO-1155 via UI.
        verifyPresenceOfRequiredIndicator(false, "curateRoleForm_role_typeCode");
        verifyPresenceOfRequiredIndicator(false, "curateCrForm_cr_typeCode_description");

        assertEquals("original RO name", selenium.getValue("curateRoleForm.role.name").trim());
        assertEquals("1", selenium.getValue("curateRoleForm_role_typeCode").trim());
        assertEquals("367", selenium.getValue("curateRoleForm.role._selectFundingMechanism").trim());

        // copy over new name
        selenium.click("copy_curateCrForm_role_name");
        waitForElementById("curateRoleForm.role.name", 5);
        assertEquals("new RO name", selenium.getValue("curateRoleForm.role.name").trim());

        // copy over new type code
        selenium.click("copy_curateCrForm_role_typeCode");
        waitForElementById("curateRoleForm_role_typeCode", 5);
        waitForElementById("curateRoleForm.role._selectFundingMechanism", 5);
        assertEquals("6", selenium.getValue("curateRoleForm_role_typeCode").trim());

        // copy over new funding Mechanism
        selenium.click("copy_curateCrForm_role_fundingMechanism");
        assertEquals("307", selenium.getValue("id=curateRoleForm.role._selectFundingMechanism"));

        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Research Organization was successfully updated!".trim()));
        
        logoutUser(); //logs out current user      
        loginAsCurator();
        
        // Add another CR
        updateRemoteRoOrg(roId.trim());
        // Scenario -- OrgRole creator access a OrgRole-CR without clicking 'Override' button
        openAndWait("/po-web/protected/roles/organizational/ResearchOrganization/start.action?organization=" + activeOrgId);
        clickAndWait("edit_researchOrganization_id_" + roId.trim());
        assertTrue(selenium.isTextPresent("Copy")); // copy button present as role is overrode by this user
        // copy over new name
        selenium.click("copy_curateCrForm_role_name");
        waitForElementById("curateRoleForm.role.name", 5);
        assertEquals("new RO name", selenium.getValue("curateRoleForm.role.name").trim());
        
    }

    private void activateRole(long id) {
        Connection connection = DataGeneratorUtil.getJDBCConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("update researchorganization set status='ACTIVE' where id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
        }
    }

    private void checkContactInformation() throws Exception {
        // Check contact information functionality - add/remove, eror messages etc.
        checkPostalAddress();
        checkEmail(0);
        checkUrl();
        checkPhone(0);
        checkFax(0);
        checkTty();
    }

    private void updateRemoteRoOrg(String ext) throws EntityValidationException, NamingException, URISyntaxException,
            NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.4.5");
        id.setIdentifierName("NCI Research Organization identifier");
        ResearchOrganizationDTO dto = RemoteServiceHelper.getResearchOrganizationCorrelationService().getCorrelation(id);

        dto.setName(StringConverter.convertToEnOn("new RO name"));
        Cd type = new Cd();
        type.setCode("NWK");
        dto.setTypeCode(type);

        Cd fm = new Cd();
        fm.setCode("B09");
        dto.setFundingMechanism(fm);

        RemoteServiceHelper.getResearchOrganizationCorrelationService().updateCorrelation(dto);
    }

    @Override
    protected String getSortFieldTestColumnName() {
        return "Research Organization Type";
    }
}
