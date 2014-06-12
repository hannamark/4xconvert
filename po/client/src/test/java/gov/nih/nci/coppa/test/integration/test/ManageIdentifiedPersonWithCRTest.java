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

import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URISyntaxException;

import javax.naming.NamingException;

/**
 * @author mshestopalov
 *
 */
public class ManageIdentifiedPersonWithCRTest extends AbstractManageOrgRolesWithCRTest {
    public String ORG_FOR_TEST = "org_for_test";
    public String AFFILIATE_ORG_FOR_PERSON_IP_OLD = "org_for_person_ip_original";
    public String AFFILIATE_ORG_FOR_PERSON_IP_NEW = "org_for_person_ip_new";

    public void testCreateActivePersonWithActiveIP() throws Exception {
        loginAsCurator();

        // create an ACTIVE ORG for later original affiliation
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON_IP_OLD, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        String activeOrgId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", activeOrgId.trim());

        // create an ACTIVE ORG for later new affiliation
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON_IP_NEW, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        String activeNewOrgId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", activeOrgId.trim());

        // create the ACTIVE Person
        openCreatePerson();
        createPerson("ACTIVE", "Dr", "po-1178", "L", "po-1178", "III", getAddress(), "sample@example.com",
                "703-111-2345", "http://www.example.com", "703-111-1234");
        String personIdExt = selenium.getText("wwctrl_person.id");
        Ii personId = new Ii();
        personId.setExtension(personIdExt);
        savePersonAsActive(personId);
        // open Person Curate page
        openAndWait("po-web/protected/person/curate/start.action?person.id=" + personIdExt);

        // Goto Manage IP Page
        accessManageIdentifiedPersonScreen();
        // add IP
        clickAndWait("add_button_io");
        assertTrue(selenium.isTextPresent("Other Person Identifier Role Information"));
        // ensure the player is ACTIVE
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));

        // select a ACTIVE Scoper
        selectOrganizationScoper(activeOrgId.trim(), AFFILIATE_ORG_FOR_PERSON_IP_OLD);
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");

        selenium.type("curateRoleForm.role.assignedIdentifier.extension", "1234");
        selenium.type("curateRoleForm.role.assignedIdentifier.root", "1.2.3.4");

        // Test the Reliability Dropdown value changes.
        verifyReliabilityChange();

        selenium.select("curateRoleForm.role.assignedIdentifier.displayable", "label=TRUE");
        selenium.type("curateRoleForm.role.assignedIdentifier.identifierName", "identifierNameValue");
        selenium.select("curateRoleForm.role.assignedIdentifier.scope", "label=BUSN");

        clickAndWaitSaveButton();

        assertTrue(selenium.isTextPresent("exact:Other Person Identifier was successfully created!"));
        String ocId = selenium.getTable("ip_row.1.0");
        assertNotEquals("null", ocId.trim());
        selenium.click("link=" + getSortFieldTestColumnName());
        pause(1000);
        ocId = selenium.getTable("ip_row.1.0");
        assertNotEquals("null", ocId.trim());

        clickAndWait("return_to_button");
        assertTrue(selenium.isTextPresent("exact:Basic Identifying Information"));
        // save everything
        clickAndWaitSaveButton();

        updateRemoteOcOrg(ocId.trim(), activeNewOrgId);

        // Goto Manage IP Page.... should see CR
        openAndWait("/po-web/protected/roles/person/IdentifiedPerson/start.action?person=" + personIdExt);
        clickAndWait("edit_identifiedPerson_id_" + ocId.trim());
        assertTrue(selenium.isTextPresent("exact:Edit Other Person Identifier - Comparison"));
        // status
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present

        // old values
        assertEquals(AFFILIATE_ORG_FOR_PERSON_IP_OLD + " (" + activeOrgId.trim() + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id").trim());

        // copy over new affiliated org and check new value
        selenium.click("copy_curateCrForm_role_typeCode");
        waitForElementById("wwctrl_curateRoleForm_role_scoper_id", 5);
        assertEquals(AFFILIATE_ORG_FOR_PERSON_IP_NEW + " (" + activeNewOrgId.trim() + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id").trim());

        // old values
        assertEquals("1234", selenium.getValue("curateRoleForm.role.assignedIdentifier.extension").trim());
        assertEquals("1.2.3.4", selenium.getValue("curateRoleForm.role.assignedIdentifier.root").trim());
        assertEquals("true", selenium.getValue("curateRoleForm.role.assignedIdentifier.displayable").trim());
        assertEquals("identifierNameValue", selenium.getValue("curateRoleForm.role.assignedIdentifier.identifierName").trim());
        assertEquals("BUSN", selenium.getValue("curateRoleForm.role.assignedIdentifier.scope").trim());
        assertEquals("VRF", selenium.getValue("curateRoleForm.role.assignedIdentifier.reliability").trim());

        // copy over new ext
        selenium.click("copy_curateCrForm_role_assignedIdentifier");
        assertTrue(selenium.getConfirmation().matches("^Changing the reliability is usually not recommended\\. Are you sure you want to continue[\\s\\S]$"));
        // new values
        waitForElementById("curateRoleForm.role.assignedIdentifier.extension", 5);
        waitForElementById("curateRoleForm.role.assignedIdentifier.root", 5);
        waitForElementById("curateRoleForm.role.assignedIdentifier.displayable", 5);
        waitForElementById("curateRoleForm.role.assignedIdentifier.identifierName", 5);
        waitForElementById("curateRoleForm.role.assignedIdentifier.scope", 5);
        assertEquals("9999", selenium.getValue("curateRoleForm.role.assignedIdentifier.extension").trim());
        assertEquals("9.9.9.9", selenium.getValue("curateRoleForm.role.assignedIdentifier.root").trim());
        assertEquals("false", selenium.getValue("curateRoleForm.role.assignedIdentifier.displayable").trim());
        assertEquals("newIdentifierNameValue", selenium.getValue("curateRoleForm.role.assignedIdentifier.identifierName").trim());
        assertEquals("VER", selenium.getValue("curateRoleForm.role.assignedIdentifier.scope").trim());
        assertEquals("UNV", selenium.getValue("curateRoleForm.role.assignedIdentifier.reliability").trim());

        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Other Person Identifier was successfully updated!".trim()));

        updateRemoteOcOrgWithISSAssignedReliability(ocId.trim(), activeNewOrgId);

        // Goto Manage IP Page.... should see CR with ISS reliability
        openAndWait("/po-web/protected/roles/person/IdentifiedPerson/start.action?person=" + personIdExt);
        clickAndWait("edit_identifiedPerson_id_" + ocId.trim());
        assertTrue(selenium.isTextPresent("exact:Edit Other Person Identifier - Comparison"));

        assertEquals("ISS", selenium.getText("wwctrl_curateCrForm.role.assignedIdentifier.reliability").trim());
        selenium.click("copy_curateCrForm_role_assignedIdentifier");
        assertTrue(selenium.getAlert().matches("^ISS not found!$"));
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Other Person Identifier was successfully updated!".trim()));
    }

    private void updateRemoteOcOrg(String ext, String affOrgId) throws EntityValidationException, NamingException, URISyntaxException,
    NullifiedEntityException, NullifiedRoleException {
        helperUpdateRemoteOcOrg(ext, affOrgId, IdentifierReliability.UNV);
    }

    private void updateRemoteOcOrgWithISSAssignedReliability(String ext, String affOrgId) throws EntityValidationException, NamingException, URISyntaxException,
    NullifiedEntityException, NullifiedRoleException {
        helperUpdateRemoteOcOrg(ext, affOrgId, IdentifierReliability.ISS);
    }

    private void helperUpdateRemoteOcOrg(String ext, String affOrgId, IdentifierReliability rel) throws EntityValidationException, NamingException, URISyntaxException,
    NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.4.7");
        id.setIdentifierName("Identified person identifier");
        IdentifiedPersonDTO dto = RemoteServiceHelper.getIdentifiedPersonCorrelationServiceRemote().getCorrelation(id);
        Ii assignedIdentifier = new Ii();
        assignedIdentifier.setExtension("9999");
        assignedIdentifier.setRoot("9.9.9.9");
        assignedIdentifier.setDisplayable(Boolean.FALSE);
        assignedIdentifier.setScope(IdentifierScope.VER);
        assignedIdentifier.setReliability(rel);
        assignedIdentifier.setIdentifierName("newIdentifierNameValue");
        dto.setAssignedId(assignedIdentifier);
        if (affOrgId != null) {
            Ii aff = new Ii();
            aff.setExtension(affOrgId);
            aff.setRoot("2.16.840.1.113883.3.26.4.2");
            aff.setIdentifierName("NCI organization entity identifier");
            dto.setScoperIdentifier(aff);
        }

        RemoteServiceHelper.getIdentifiedPersonCorrelationServiceRemote().updateCorrelation(dto);
    }

    @Override
    protected String getSortFieldTestColumnName() {
        return "Affiliated Organization Name";
    }
}
