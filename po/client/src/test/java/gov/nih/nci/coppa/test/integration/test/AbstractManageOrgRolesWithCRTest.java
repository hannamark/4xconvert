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

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;


/**
 * @author kkanchinadam
 *
 */
public abstract class AbstractManageOrgRolesWithCRTest extends AbstractPoWebTest {

    private String organizationId;
    private String organizationalRoleId;

    private String orgRoleTitleText;
    private String orgRoleLinkText;
    private String orgRoleCreateMessage;
    private String orgRoleUpdateMessage;
    private String orgRoleName;
    private String orgRoleSearchResultsMessage;
    private String orgRoleSearchResultsRowNumber = "hcf_row.1.0";
    private String organizationStatus;

    protected abstract String getSortFieldTestColumnName();

    protected void createActiveOrganization() {
        organizationStatus = "ACTIVE";
        createActiveOrPendingOrganization();
    }

    protected void createPendingOrganization() {
        organizationStatus = "PENDING";
        createActiveOrPendingOrganization();
    }

    private void createActiveOrPendingOrganization() {
        createOrganization(organizationStatus, "ORGANIZATION - PENDING", getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        organizationId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", organizationId);
    }

    protected void checkPostalAddress() {
        // add postal address, check remove functionality, add again.
        addUSPostalAddress();
        removePostalAddress();
    }

    protected void accessOrgRoleScreen() {
        // Goto Manage Org Role Screen
        clickAndWait(orgRoleLinkText);       
    }

    protected void openOrganizationCuratePage() {
        openAndWait("po-web/protected/organization/curate/start.action?organization.id=" + organizationId);
    }

    protected void addUSPostalAddress() {
        addPostalAddressUsingPopup("Address One", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        driver.switchTo().defaultContent();
        assertEquals("Address One", selenium.getText("wwctrl_address.streetAddressLine1"));
        assertEquals("suite xyz", selenium.getText("wwctrl_address.deliveryAddressLine1"));
        assertEquals("phoenix", selenium.getText("wwctrl_address.cityOrMunicipality1"));
        assertEquals("AZ", selenium.getText("wwctrl_address.stateOrProvince1"));
        assertEquals("67890", selenium.getText("wwctrl_address.postalCode1"));
        assertEquals("United States", selenium.getText("wwctrl_address.country1"));
    }

    protected void addNonUSPostalAddress() {
        addPostalAddressUsingPopup("Non US Address", "suite xyz", "City One", "State One", "67890", "Thailand", 1);
        driver.switchTo().defaultContent();
        assertEquals("Non US Address", selenium.getText("wwctrl_address.streetAddressLine1"));
        assertEquals("suite xyz", selenium.getText("wwctrl_address.deliveryAddressLine1"));
        assertEquals("City One", selenium.getText("wwctrl_address.cityOrMunicipality1"));
        assertEquals("State One", selenium.getText("wwctrl_address.stateOrProvince1"));
        assertEquals("67890", selenium.getText("wwctrl_address.postalCode1"));
        assertEquals("Thailand", selenium.getText("wwctrl_address.country1"));
    }

    protected void removePostalAddress() {        
        waitForElementToBecomeAvailable(By.id("address-remove-0"), 10);
        clickAndWaitAjax("address-remove-0");
        waitForElementById("add_address", 10);
    }

    protected void addContactInformation() {
        inputContactInfoForUSAndCan("abc@example.com", new String[] {"123", "456", "7890"}, new String[] {"234", "567",
                "8901"}, new String[] {"345", "678", "9012"}, "http://www.example.com");
    }

    protected void checkEmail() throws Exception {
       checkEmail(1);
    }
    
    protected void checkEmail(int entryID) throws Exception {
        checkEmailOrUrl("email-add",
                "exact:Email Address must be set",
                "emailEntry_value",
                "Some email id",
                "exact:Email Address is not a well-formed email address",
                "example1@example.com",
                "email-entry-"+entryID,
                "email-remove-"+entryID);
    }

    protected void checkUrl() throws Exception {
        checkEmailOrUrl("url-add",
                "exact:URL must be set",
                "urlEntry_value",
                "Some invalid url value",
                "exact:URL is not well formed",
                "http://www.example.com",
                "url-entry-0",
                "url-remove-0");
    }

    protected void checkPhone() {
        checkPhone(1);
    }
    
    protected void checkPhone(int entryID) {
        checkTelType("phone", "phone-add", "123-456-7890",
                "phone-entry-"+entryID, "123-456-7890", "phone-remove-"+entryID);
    }

    protected void checkFax() {
        checkFax(1);
    }
    
    protected void checkFax(int entryID) {
        checkTelType("fax", "fax-add", "234-567-8901",
                "fax-entry-"+entryID, "234-567-8901", "fax-remove-"+entryID);
    }

    protected void checkTty() {
        checkTelType("tty", "tty-add", "345-678-9012",
                "tty-entry-0", "345-678-9012", "tty-remove-0");
    }


    @SuppressWarnings("deprecation")
    private void checkTelType(String type, String anchorAddId, String value, String entryId, String entryText, String anchorRemoveId) {
        /*
         * Commenting out the following code, since isAlertPresent() always returns false, and
         * getAlert() always returns the exception message: There were no alerts.
        // Check for alerts.
        clickAnchor(anchorAddId); // no value currently present in the input id.
        assertTrue(selenium.isAlertPresent());
        assertEquals("The entire " + type + " number must be provided.", selenium.getAlert());
        */

        // Add a non US Postal address, check tel format
        addNonUSPostalAddress();
        inputNonUSFormatTel(value, type);
        waitForElementById(entryId, 10);
        assertEquals(entryText + " | Remove", selenium.getText("id=" + entryId));
        clickAndWaitAjax(anchorRemoveId);
        removePostalAddress();

        // Add a US postal address, check tel format, also check Tel entry/removal.
        addUSPostalAddress();
        inputNonUSFormatTel(value, type);
        waitForElementById(entryId, 10);
        assertEquals(entryText + " | Remove", selenium.getText("id=" + entryId));
        clickAndWaitAjax(anchorRemoveId);
        removePostalAddress();
    }

    private void checkEmailOrUrl(String anchorAddId, String blankValueErrorMsg, String inputId, String invalidValue,
            String incorrectFormatMsg, String validValue, String entryId, String anchorRemoveId) throws Exception {
        clickAndWaitAjax(anchorAddId);
        assertTrue(selenium.isTextPresent(blankValueErrorMsg));

        selenium.type(inputId, invalidValue);
        clickAndWaitAjax(anchorAddId);

        assertTrue(selenium.isTextPresent(incorrectFormatMsg));
        selenium.type(inputId, validValue);
        clickAndWaitAjax(anchorAddId);
        assertEquals(validValue + " | Remove", selenium.getText("id=" + entryId));

        clickAndWaitAjax(anchorRemoveId);
    }

    protected void saveOrganizationalRole() {
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:" + orgRoleCreateMessage));
        if (StringUtils.isNotEmpty(orgRoleName)) { // Some org roles do not have a name - eg: oversight committee.
            assertTrue(selenium.isTextPresent("exact:" + orgRoleName));
        }
        assertTrue(selenium.isTextPresent("exact:" + orgRoleSearchResultsMessage));

        organizationalRoleId = selenium.getTable(orgRoleSearchResultsRowNumber);
        assertNotEquals("null", getOrganizationalRoleId());
        assertNotNull(getOrganizationalRoleId());
        selenium.click("link=" + getSortFieldTestColumnName());
        pause(1000);
        assertNotEquals("null", getOrganizationalRoleId());
        assertNotNull(getOrganizationalRoleId());

        clickAndWait("return_to_button");
        assertTrue(selenium.isTextPresent("exact:Basic Identifying Information"));
        // save everything
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Organization was successfully curated!"));
    }

    protected void updateOrganizationalRole() {
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:" + orgRoleUpdateMessage));
        if (StringUtils.isNotEmpty(orgRoleName)) { // Some org roles do not have a name - eg: oversight committee.
            assertTrue(selenium.isTextPresent("exact:" + orgRoleName + " Updated"));
        }
        assertTrue(selenium.isTextPresent("exact:" +  orgRoleSearchResultsMessage));
    }

    @SuppressWarnings("deprecation")
    protected void checkOrgRoleContactInfomation() {
        //Pause to allow for section to load completely.
        waitForElementById("postalAddresses.div", 15);        
        waitForElementById("wwctrl_address.country1", 15);
        
        // Check the address info.
        assertEquals("Address One", StringUtils.trim(selenium.getText("wwctrl_address.streetAddressLine1")));
        assertEquals("suite xyz", StringUtils.trim(selenium.getText("wwctrl_address.deliveryAddressLine1")));
        assertEquals("phoenix", StringUtils.trim(selenium.getText("wwctrl_address.cityOrMunicipality1")));
        assertEquals("AZ", StringUtils.trim(selenium.getText("wwctrl_address.stateOrProvince1")));
        assertEquals("67890", StringUtils.trim(selenium.getText("wwctrl_address.postalCode1")));
        assertEquals("United States", StringUtils.trim(selenium.getText("wwctrl_address.country1")));

        // email, phone, fax, tty, url
        waitForElementById("email-remove-0", 5);
        assertEquals("abc@example.com | Remove", StringUtils.trim(selenium.getText("id=email-entry-0")));

        waitForElementById("phone-remove-0", 5);
        assertEquals("123-456-7890 | Remove", StringUtils.trim(selenium.getText("id=phone-entry-0")));

        waitForElementById("fax-remove-0", 5);
        assertEquals("234-567-8901 | Remove", StringUtils.trim(selenium.getText("id=fax-entry-0")));

        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", StringUtils.trim(selenium.getText("id=tty-entry-0")));

        waitForElementById("url-remove-0", 5);
        assertEquals("http://www.example.com | Remove", StringUtils.trim(selenium.getText("id=url-entry-0")));
    }

    protected void copyCRInfo(String crValueId, String expectedValue, String entryId) {
        // Copy the value over.
        selenium.click(crValueId);
        pause(1000);
        assertEquals(expectedValue + " | Remove", selenium.getText("id=" + entryId));

        // Copy value over a second time, ensure error message is present.
        selenium.click(crValueId);
        pause(1000);
        assertTrue(selenium.isTextPresent("exact:Already added"));
    }

    /**
     * @return the organizationalRoleId
     */
    protected String getOrganizationalRoleId() {
        return organizationalRoleId;
    }

    /**
     * @param orgRoleTitleText the orgRoleTitleText to set
     */
    protected void setOrgRoleTitleText(String orgRoleTitleText) {
        this.orgRoleTitleText = orgRoleTitleText;
    }

    /**
     * @param orgRoleCreateMessage the orgRoleCreateMessage to set
     */
    protected void setOrgRoleCreateMessage(String orgRoleCreateMessage) {
        this.orgRoleCreateMessage = orgRoleCreateMessage;
    }

    /**
     * @param orgRoleName the orgRoleName to set
     */
    protected void setOrgRoleName(String orgRoleName) {
        this.orgRoleName = orgRoleName;
    }

    /**
     * @param orgRoleLinkText the orgRoleLinkText to set
     */
    protected void setOrgRoleLinkText(String orgRoleLinkText) {
        this.orgRoleLinkText = orgRoleLinkText;
    }

    /**
     * @param orgRoleUpdateMessage the orgRoleUpdateMessage to set
     */
    protected void setOrgRoleUpdateMessage(String orgRoleUpdateMessage) {
        this.orgRoleUpdateMessage = orgRoleUpdateMessage;
    }

    /**
     * @param orgRoleSearchResultsMessage the orgRoleSearchResultsMessage to set
     */
    protected void setOrgRoleSearchResultsMessage(String orgRoleSearchResultsMessage) {
        this.orgRoleSearchResultsMessage = orgRoleSearchResultsMessage;
    }

    /**
     * @param orgRoleSearchResultsRowNumber the orgRoleSearchResultsRowNumber to set
     */
    protected void setOrgRoleSearchResultsRowNumber(String orgRoleSearchResultsRowNumber) {
        this.orgRoleSearchResultsRowNumber = orgRoleSearchResultsRowNumber;
    }

    /**
     * @param organizationStatus the organizationStatus to set
     */
    public void setOrganizationStatus(String organizationStatus) {
        this.organizationStatus = organizationStatus;
    }
}
