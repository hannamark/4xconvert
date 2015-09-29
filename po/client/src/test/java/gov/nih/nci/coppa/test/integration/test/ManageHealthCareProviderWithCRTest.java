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

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import javax.naming.NamingException;

/**
 * @author mshestopalov
 *
 */
public class ManageHealthCareProviderWithCRTest extends AbstractPoWebTest {
    public String AFFILIATE_ORG_FOR_PERSON = "affiliate_org_for_person";
    public String AFFILIATE_ORG_FOR_HCP = "affiliate_org_for_hcp";

    @SuppressWarnings("deprecation")
    public void testCreateActivePersonWithPendingHCP() throws Exception {
        loginAsCurator();
        // create an ACTIVE ORG for later
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        String activeOrgId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", activeOrgId.trim());

        // create the ACTIVE Person
        openCreatePerson();
        createPerson("ACTIVE", "Dr", "po-1177", "L", "po-1177", "III", getAddress(), "sample@example.com",
                "703-111-2345", "http://www.example.com", "703-111-1234");
        String personIdExt = selenium.getText("wwctrl_person.id");
        Ii personId = new Ii();
        personId.setExtension(personIdExt);
        savePersonAsActive(personId);
        // open Person Curate page
        openAndWait("po-web/protected/person/curate/start.action?person.id=" + personIdExt);
        
        clickAndWait("link=HCP (0)");
        clickAndWait("add_button_ro");
        assertTrue(selenium.isTextPresent("Health Care Provider Role Information"));
        // ensure the player is ACTIVE
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));
        
        waitForTelecomFormsToLoad();
        
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");
        
        assertTrue(selenium.isElementPresent("id=onload_phone_number_required"));
        selenium.type("curateRoleForm.role.certificateLicenseText", "CR Original");
        clickAndWaitSaveButton();
        // assert validation messages
        assertEquals("ACTIVE", selenium.getSelectedLabel("curateRoleForm.role.status"));
        
        // select a ACTIVE Scoper
        selectOrganizationScoper(activeOrgId.trim(), AFFILIATE_ORG_FOR_PERSON);
        
        addPostalAddressUsingPopup("456 jik", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        driver.switchTo().defaultContent();
      
        // add Contact Information
        inputContactInfoForUSAndCan("abc@example.com", new String[] {"123", "456", "7890"}, new String[] {"234", "567",
                "8901"}, new String[] {"345", "678", "9012"}, "http://www.example.com");
        // save HCP
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Health Care Provider was successfully created!"));
        String hcpId = selenium.getTable("hcp_row.1.0");
        assertNotEquals("null", hcpId.trim());
        selenium.click("link=" + getSortFieldTestColumnName());
        pause(500);
        hcpId = selenium.getTable("hcp_row.1.0");
        assertNotEquals("null", hcpId.trim());

        clickAndWait("return_to_button");
        assertTrue(selenium.isTextPresent("exact:Basic Identifying Information"));
        // save everything
        clickAndWaitSaveButton();
        // assertTrue(selenium.isTextPresent("exact:Person was successfully curated!"));

        updateRemoteHcpOrg(hcpId.trim());

        // Goto Manage HCP Page.... should see CR
        openAndWait("/po-web/protected/roles/person/HealthCareProvider/start.action?person=" + personIdExt);
        clickAndWait("edit_healthCareProvider_id_" + hcpId.trim());
        assertTrue(selenium.isTextPresent("exact:Edit Health Care Provider - Comparison"));
        // status
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode").trim());
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present
        // scoper
        assertEquals(AFFILIATE_ORG_FOR_PERSON + " (" + activeOrgId.trim() + ")", selenium
                .getText("wwctrl_curateRoleForm_role_scoper_id").trim());
        // cert
        assertEquals("CR Original", selenium.getValue("curateRoleForm.role.certificateLicenseText").trim());
        // address
        assertEquals("456 jik", selenium.getText("wwctrl_address.streetAddressLine1").trim());
        assertEquals("suite xyz", selenium.getText("wwctrl_address.deliveryAddressLine1").trim());
        assertEquals("phoenix", selenium.getText("wwctrl_address.cityOrMunicipality1").trim());
        assertEquals("AZ", selenium.getText("wwctrl_address.stateOrProvince1").trim());
        assertEquals("67890", selenium.getText("wwctrl_address.postalCode1").trim());
        assertEquals("United States", selenium.getText("wwctrl_address.country1").trim());

        // email, phone, fax, tty, url
        waitForElementById("email-remove-1", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("id=email-entry-1").trim());

        waitForElementById("phone-remove-1", 5);
        assertEquals("123-456-7890 | Remove", selenium.getText("id=phone-entry-1").trim());

        waitForElementById("fax-remove-1", 5);
        assertEquals("234-567-8901 | Remove", selenium.getText("id=fax-entry-1").trim());

        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", selenium.getText("id=tty-entry-0").trim());

        waitForElementById("url-remove-0", 5);
        assertEquals("http://www.example.com | Remove", selenium.getText("id=url-entry-0").trim());

        // new cert
        assertEquals("CR cert license change",
                selenium.getText("wwctrl_curateCrForm_cr_certificateLicenseText").trim());
        // copy over new cert license
        selenium.click("copy_curateCrForm_role_certificateLicenseText");
        waitForElementById("curateRoleForm.role.certificateLicenseText", 5);
        assertEquals("CR cert license change", selenium.getValue("curateRoleForm.role.certificateLicenseText").trim());

        selenium.click("copy_emailEntry_value0"); 
        waitForElementById("email-remove-2", 5);
        assertEquals("cr@example.com | Remove", selenium.getText("id=email-entry-2").trim());
         
        selenium.click("copy_phoneEntry_value0"); 
        waitForElementById("phone-remove-2", 5);
        assertEquals("112-233-4455 | Remove", selenium.getText("id=phone-entry-2").trim());
         
        selenium.click("copy_faxEntry_value0"); 
        waitForElementById("fax-remove-2", 5);
        assertEquals("112-233-4455 | Remove", selenium.getText("id=fax-entry-2").trim());
         
        selenium.click("copy_ttyEntry_value0"); 
        waitForElementById("tty-remove-1", 5);
        assertEquals("112-233-4455 | Remove", selenium.getText("id=tty-entry-1").trim());
         
        selenium.click("copy_urlEntry_value0"); 
        waitForElementById("url-remove-1", 5);
        assertEquals("http://www.cr.com | Remove", selenium.getText("id=url-entry-1").trim());
         
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Health Care Provider was successfully updated!".trim()));
    }

    private void updateRemoteHcpOrg(String ext) throws EntityValidationException, NamingException, URISyntaxException,
            NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.4.2");
        id.setIdentifierName("NCI health care provider identifier");
        HealthCareProviderDTO dto = RemoteServiceHelper.getHealthCareProviderCorrelationService().getCorrelation(id);
        dto.setCertificateLicenseText(TestConvertHelper.convertToSt("CR cert license change"));

        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);

        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:112-233-4455"));
        dto.getTelecomAddress().getItem().add(phone);

        TelPhone fax = new TelPhone();
        fax.setValue(new URI("x-text-fax:112-233-4455"));
        dto.getTelecomAddress().getItem().add(fax);

        TelPhone tty = new TelPhone();
        tty.setValue(new URI("x-text-tel:112-233-4455"));
        dto.getTelecomAddress().getItem().add(tty);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:cr@example.com"));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://www.cr.com"));
        dto.getTelecomAddress().getItem().add(url);

        RemoteServiceHelper.getHealthCareProviderCorrelationService().updateCorrelation(dto);
    }
    
    protected String getSortFieldTestColumnName() {
        return "Affiliated Organization Name";
    }
}
