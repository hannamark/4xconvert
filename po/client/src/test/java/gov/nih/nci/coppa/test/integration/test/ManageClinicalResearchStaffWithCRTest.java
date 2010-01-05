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

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URISyntaxException;

import javax.naming.NamingException;
public class ManageClinicalResearchStaffWithCRTest extends AbstractManageOrgRolesWithCRTest {

    private static final String ORG_WITH_APOS = "Org'With'Apos";
    public String AFFILIATE_ORG_FOR_PERSON_CRS_OLD = "org_for_person_crs_original";
    public String AFFILIATE_ORG_FOR_PERSON_CRS_NEW = "org_for_person_crs_new";

    public void testCreateActivePersonWithPendingCRS() throws Exception {
        loginAsCurator();
        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", ORG_WITH_APOS, getAddress(), "test1@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String activeOrgId =  selenium.getText("wwctrl_organization.id");
        activeOrgId.trim();
        assertNotEquals("null", activeOrgId);

        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON_CRS_OLD, getAddress(), "test2@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String affiliateOldOrgId =  selenium.getText("wwctrl_organization.id");
        affiliateOldOrgId.trim();
        assertNotEquals("null", affiliateOldOrgId);

        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON_CRS_NEW, getAddress(), "test3@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String affiliateNewOrgId =  selenium.getText("wwctrl_organization.id");
        affiliateNewOrgId.trim();
        assertNotEquals("null", affiliateNewOrgId);

        //create a PENDING ORG for later
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        //create the ACTIVE Person
        openCreatePerson();
        createPerson();
        String personIdExt = selenium.getText("wwctrl_person.id");
        Ii personId = new Ii();
        personId.setExtension(personIdExt);
        savePersonAsActive(personId);

        //open Person Curate page
        openAndWait("po-web/protected/person/curate/start.action?person.id=" + personIdExt);

        //Goto Manage CRS Page
        accessManageClinicalResearchStaffScreen();
        //add CRS
        clickAndWait("add_button");
        assertTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        //ensure the player is ACTIVE
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");

        //verify validation messages
        clickAndWait("save_button");
        assertEquals("ACTIVE", selenium.getSelectedLabel("curateRoleForm.role.status"));

        assertTrue(selenium.isTextPresent("Affiliated Organization ID must be set"));
        assertTrue(selenium.isTextPresent("Phone number is required for this status."));
        assertFalse(selenium.isTextPresent("Role status not compatible with associated entity's status."));

        //select a PENDING Scoper
        selectOrganizationScoper(orgId.getExtension(), ORG_WITH_APOS);
        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        assertTrue(selenium.isTextPresent("Phone number is required for this status."));
        assertFalse(selenium.isTextPresent("Affiliated Organization ID must be set"));

        //select a ACTIVE Scoper
        selectOrganizationScoper(affiliateOldOrgId, AFFILIATE_ORG_FOR_PERSON_CRS_OLD);
        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Phone number is required for this status."));
        assertFalse(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        assertFalse(selenium.isTextPresent("Affiliated Organization ID must be set"));

        //add Contact Information
        waitForTelecomFormsToLoad();
        checkContactInformation();

        addUSPostalAddress();
        waitForTelecomFormsToLoad();
        addContactInformation();

        //save CRS
        clickAndWait("save_button");
        assertTrue("PO: Persons and Organizations - Manage Clinical Research Staff(s)".equals(selenium.getTitle()));
        assertTrue(selenium.isTextPresent("Clinical Research Staff was successfully created!"));

        String ocId = selenium.getTable("row.1.0");
        assertNotEquals("null", ocId.trim());
        selenium.click("link=" + getSortFieldTestColumnName());
        ocId = selenium.getTable("row.1.0");
        assertNotEquals("null", ocId.trim());

        clickAndWait("return_to_button");
        assertTrue(selenium.isTextPresent("exact:Basic Identifying Information"));
        // save everything
        clickAndWait("save_button");

        updateRemoteCrsOrg(ocId, affiliateNewOrgId);

        // Goto Manage CRS Page.... should see CR
        openAndWait("/po-web/protected/roles/person/ClinicalResearchStaff/start.action?person=" + personIdExt);
        clickAndWait("edit_clinicalResearchStaff_id_" + ocId.trim());
        assertTrue(selenium.isTextPresent("exact:Edit Clinical Research Staff - Comparison"));
        // status
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));

        // old values
        assertEquals(AFFILIATE_ORG_FOR_PERSON_CRS_OLD + " (" + affiliateOldOrgId.trim() + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id").trim());

        // copy over new affiliated org and check new value
        selenium.click("copy_curateCrForm_role_scoper");
        waitForElementById("wwctrl_curateRoleForm_role_scoper_id", 5);
        assertEquals(AFFILIATE_ORG_FOR_PERSON_CRS_NEW + " (" + affiliateNewOrgId.trim() + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id").trim());

        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("exact:Clinical Research Staff was successfully updated!".trim()));

    }

    private void checkContactInformation() throws Exception {
        // Check contact information functionality - add/remove, eror messages etc.
        checkPostalAddress();
        checkEmail();
        checkUrl();
        checkPhone();
        checkFax();
        checkTty();
    }

    private void updateRemoteCrsOrg(String ext, String affOrgId) throws EntityValidationException, NamingException, URISyntaxException,
    NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.4.1");
        id.setIdentifierName("NCI clinical research staff identifier");
        ClinicalResearchStaffDTO dto = RemoteServiceHelper.getClinicalResearchStaffCorrelationService().getCorrelation(id);
        Cd roleStatus = new Cd();
        roleStatus.setCode("active");
        dto.setStatus(roleStatus);
        Ii aff = new Ii();
        aff.setExtension(affOrgId);
        aff.setRoot("2.16.840.1.113883.3.26.4.2");
        aff.setIdentifierName("NCI organization entity identifier");
        dto.setScoperIdentifier(aff);

        RemoteServiceHelper.getClinicalResearchStaffCorrelationService().updateCorrelation(dto);
    }

    public void createPendingPersonWithPendingCRS() throws Exception {
        loginAsCurator();
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        openCreatePerson();
        createPerson();

        accessManageClinicalResearchStaffScreen();
        clickAndWait("add_button");
        assertTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        assertEquals("PENDING", selenium.getText("wwctrl_person.statusCode"));

        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Affiliated Organization ID must be set"));

        selenium.select("curateRoleForm.role.status", "label=ACTIVE");
        clickAndWait("save_button");
        assertTrue(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        selenium.select("curateRoleForm.role.status", "label=PENDING");

        selectOrganizationScoper(orgId.getExtension(), ORG_WITH_APOS);

        addPostalAddressUsingPopup("456 jik ", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        addPostalAddressUsingPopup("123 abc", "suite def", "mycity", "AK", "12345", "United Kingdom", 2);
        selenium.selectFrame("relative=parent");
        waitForTelecomFormsToLoad();
        inputContactInfo("abc@example.com", "1234567890", "2345678901", "3456789012", "http://www.example.com");

        clickAndWait("save_button");
    }

    public void createPersonWithClinicalResearchStaffTest() throws Exception {
        loginAsCurator();
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        openCreatePerson();
        createPerson();
        accessManageClinicalResearchStaffScreen();
        addClinicalResearchStaffAffiliateOrg(orgId.getExtension());
    }

    private void addClinicalResearchStaffAffiliateOrg(String orgId) throws InterruptedException {
        clickAndWait("add_button");
        assertTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        selectOrganizationScoper(orgId, ORG_WITH_APOS);
    }

    @Override
    protected String getSortFieldTestColumnName() {
        return "Affiliated Organization Name";
    }

}
