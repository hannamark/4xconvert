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

/**
 * Verifies PO-1440. Tests for US/Canada and non-US/non-Canada Phone/Fax/TTY formatting.
 */
public class ContactFormattingTest extends AbstractPoWebTest {

    public void testCreateOrganizationContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCreateMode(ENTITYTYPE.organization);
    }

    public void testCreatePersonContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCreateMode(ENTITYTYPE.person);
    }

    public void testCurateOrganizationContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCurateMode(ENTITYTYPE.organization);
    }

    public void testCuratePersonContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCurateMode(ENTITYTYPE.person);
    }

    private void checkUsOrCanadaFormatsInCreateMode(ENTITYTYPE entityType) throws InterruptedException {
        if (entityType.equals(ENTITYTYPE.organization)) {
            openCreateOrganization();
        } else {
            openCreatePerson();
        }

        String countryLocatorId = "curateEntityForm." + entityType.name() + ".postalAddress.country";
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", countryLocatorId);
        checkPhoneFaxTtyFormats("fax", countryLocatorId);
        checkPhoneFaxTtyFormats("tty", countryLocatorId);
    }

    private void checkUsOrCanadaFormatsInCurateMode(ENTITYTYPE entityType) throws InterruptedException {
        if (entityType.equals(ENTITYTYPE.organization)) {
            createOrganization("ACTIVE", "ORGANIZATION - PENDING", getAddress(), "sample@example.com", "703-111-2345",
                    "703-111-1234", "703-111-1234", "http://www.example.com");
        } else {
            openCreatePerson();
            createPerson("ACTIVE", "po-1440", "po-1440", "1440", "po-1440", "III", getAddress(), "sample@example.com",
                    "703-111-2345", "http://www.example.com", "703-111-1234");
        }

        String entityId = selenium.getText("wwctrl_" + entityType.name() + ".id");
        assertNotEquals("null", entityId);

        String urlAccessPersonOrOrgScreen = "po-web/protected/" + entityType.name() + "/curate/start.action?" + entityType.name() + ".id=" + entityId;
        openAndWait(urlAccessPersonOrOrgScreen);

        String countryLocatorId = "curateEntityForm." + entityType.name() + ".postalAddress.country";
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", countryLocatorId);
        checkPhoneFaxTtyFormats("fax", countryLocatorId);
        checkPhoneFaxTtyFormats("tty", countryLocatorId);

        // Check the formatting on org/person roles.
        if (entityType.equals(ENTITYTYPE.organization)) {
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Research Organization(s)", "Research Organization Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Health Care Facility", "Health Care Facility Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Organizational Contact(s)", "Organizational Contact Information");
        } else {
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Clinical Research Staff(s)", "Clinical Research Staff Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Health Care Provider(s)", "Health Care Provider Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Organizational Contact(s)", "Organizational Contact Information");
        }
    }

    private void checkPersonOrgRoleContactFormatting(ENTITYTYPE entityType, String entityId, String roleLinkText, String roleTitleText) throws InterruptedException {
        openAndWait("po-web/protected/" + entityType.name() + "/curate/start.action?" + entityType.name() + ".id=" + entityId);
        clickAndWait(roleLinkText);
        assertTrue(selenium.isTextPresent(roleTitleText));

        clickAndWait("add_button");
        waitForTelecomFormsToLoad();

        // By default, US/Canada formatting is off.
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", false);
        checkPhoneFaxTtyFormats("fax", false);
        checkPhoneFaxTtyFormats("tty", false);

        // add us address. should display us/canada format.
        addPostalAddressUsingPopup("Address One", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        selenium.selectFrame("relative=parent");
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", true);
        checkPhoneFaxTtyFormats("fax", true);
        checkPhoneFaxTtyFormats("tty", true);

        // add non-us/canada address. should display non-US formatted phone/fax/tty.
        addPostalAddressUsingPopup("Non US Address", "suite xyz", "City One", "State One", "67890", "Thailand", 1);
        selenium.selectFrame("relative=parent");
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", false);
        checkPhoneFaxTtyFormats("fax", false);
        checkPhoneFaxTtyFormats("tty", false);
    }

    private void checkPhoneFaxTtyFormats(String type, boolean usOrCanadaFormatExpected) throws InterruptedException {
        String usFormatDivId = "id=us_format_" + type;
        String noFormatDivId = "id=no_format_" + type;

        if (usOrCanadaFormatExpected) {
            checkDivVisibility(usFormatDivId, noFormatDivId);
        } else {
            checkDivVisibility(noFormatDivId, usFormatDivId);
        }
    }

    private void checkPhoneFaxTtyFormats(String type, String countryLocatorId) throws InterruptedException {
        String usFormatDivId = "id=us_format_" + type;
        String noFormatDivId = "id=no_format_" + type;

        selectCountry(countryLocatorId, "label=United States");
        checkDivVisibility(usFormatDivId, noFormatDivId);

        selectCountry(countryLocatorId, "label=Barbados");
        checkDivVisibility(noFormatDivId, usFormatDivId);

        selectCountry(countryLocatorId, "label=Canada");
        checkDivVisibility(usFormatDivId, noFormatDivId);

        selectCountry(countryLocatorId, "label=Taiwan");
        checkDivVisibility(noFormatDivId, usFormatDivId);
    }

    private void selectCountry(String countryLocatorId, String countryLabelText) throws InterruptedException {
        selenium.select(countryLocatorId, countryLabelText);
        Thread.sleep(100);
    }

    private void checkDivVisibility(String visibleDivId, String invisibleDivId) throws InterruptedException {
        Thread.sleep(100);
        assertTrue(selenium.isVisible(visibleDivId));
        assertFalse(selenium.isVisible(invisibleDivId));
    }
}
