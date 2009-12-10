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

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import javax.naming.NamingException;

/**
 * @author kkanchinadam
 *
 */
public class ManageHealthCareFacilityWithCRTest extends AbstractManageOrgRolesWithCRTest {
    public void testHealthCareFacility() throws Exception {
        createOrganization("ACTIVE", "Organization 1 Name", getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
    }

    private void createHCF() throws Exception {
        //curate org
        openOrganizationCuratePage();
        // Go to the Manage HCF Screen
        accessOrgRoleScreen();

        clickAndWait("add_button");
        // check Status
        checkStatus();
        // Check contact information functionality - add/remove, eror messages etc.
        checkContactInformation();

        // Save the HCF
        selenium.type("curateRoleForm.role.name", "Facility 1");
        selenium.select("curateRoleForm.role.status", "label=PENDING");
        addUSPostalAddress();
        waitForTelecomFormsToLoad();
        addContactInformation();
        saveOrganizationalRole();
    }

    private void updateHCF() throws Exception {
        //curate org
        openOrganizationCuratePage();
        // Go to the Manage HCF Screen
        accessOrgRoleScreen();

        clickAnchor("edit_healthCareFacility_id_" + getOrganizationalRoleId());
        // check Status
        checkStatus();

        // Check contact information functionality - add/remove, eror messages etc. in Update mode.
        // But first, remove the existing contact info.
        removeAddressAndContactInformationFromRole();
        checkContactInformation();

        // Update the HCF
        selenium.type("curateRoleForm.role.name", "Facility 1 Updated");
        selenium.select("curateRoleForm.role.status", "label=PENDING");
        addUSPostalAddress();
        waitForTelecomFormsToLoad();
        addContactInformation();
        updateOrganizationalRole();
    }

    private void removeAddressAndContactInformationFromRole() {
        removePostalAddress();
        clickAnchor("email-remove-0");
        clickAnchor("url-remove-0");
        clickAnchor("phone-remove-0");
        clickAnchor("fax-remove-0");
        clickAnchor("tty-remove-0");
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

    private void checkStatus() {
        this.waitForElementById("curateRoleForm.role.status", 10);
        // Attempt to save with no status
        selenium.select("curateRoleForm.role.status", "label=--Select a Role Status--");
        clickAndWaitButton("save_button");
        assertTrue(selenium.isTextPresent("exact:Role Status must be set"));
    }

    private void checkCR() throws Exception {
        //create a cr remotely
        updateRemoteHealthCareFacility(getOrganizationalRoleId());

        //curate org
        openOrganizationCuratePage();
        // Go to the Manage HCF Screen
        accessOrgRoleScreen();

        clickAnchor("edit_healthCareFacility_id_" + getOrganizationalRoleId());
        waitForPageToLoad();
        assertTrue(selenium.isTextPresent("exact:Edit Health Care Facility - Comparison"));

        // Check Status
        assertEquals("ACTIVE", selenium.getText("wwctrl_organization.statusCode"));

        // Check address, email, phone, fax, tty and url info.
        checkOrgRoleContactInfomation();
        // Copy CR EMail
        copyCRInfo("copy_emailEntry_value0", "cr@example.com", "email-entry-1");

        // update org role and check for success message.
        updateOrganizationalRole();
    }

    private void updateRemoteHealthCareFacility(String ext) throws EntityValidationException, NamingException, URISyntaxException,
            NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.5.3");
        id.setIdentifierName("NCI health care facility identifier");
        HealthCareFacilityDTO dto = RemoteServiceHelper.getHealthCareFacilityCorrelationService().getCorrelation(id);

        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);

        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:1122334455"));
        dto.getTelecomAddress().getItem().add(phone);

        TelPhone fax = new TelPhone();
        fax.setValue(new URI("x-text-fax:1122334455"));
        dto.getTelecomAddress().getItem().add(fax);

        TelPhone tty = new TelPhone();
        tty.setValue(new URI("x-text-tel:1122334455"));
        dto.getTelecomAddress().getItem().add(tty);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:cr@example.com"));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://www.example.com"));
        dto.getTelecomAddress().getItem().add(url);

        RemoteServiceHelper.getHealthCareFacilityCorrelationService().updateCorrelation(dto);
    }
}
