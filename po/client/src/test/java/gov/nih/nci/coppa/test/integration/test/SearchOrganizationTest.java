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
 * SearchOrganizationTest -
 *   Adds an organization and performs a Name, Contact, Address and Id search for the added
 *     organization.
 *   Additionally this will test - No rows returned and nothing selected tests
 * @author Bill Mason
 *
 */
public class SearchOrganizationTest extends OrganizationWebTest {


    public void testSearchOrganization(){

        loginAsCurator();
        openCreateOrganization();
        // verify the presence of required indicator in create screen.
        //verifyRequiredIndicators(true);
        addOrganization();
        openSearchOrganization();
        verifySearchForm();
        searchByName();        
        searchByAddress();
        searchByPoId();
        noRowsReturnedTest();
        nothingSelectedTest();
        
        searchByOrgAlias();
      }
    
    
    public void testSearchOrganizationUsingCtepId(){
        
        // Step1:: Create an Organization & its Identified Org (Ctep Id)
        String AFFILIATE_ORG_FOR_IO = "affiliate_org_for_io_search_by_CtepId";
        String ORG_FOR_TEST = "org_for_search_by_CtepId";
        String ctepId = "12346789";

        loginAsCurator();
        // create an ACTIVE ORG for later
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_IO, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        String ioAffOrgId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", ioAffOrgId.trim());

        // create ACTIVE org we are using now.
        createOrganization("ACTIVE", ORG_FOR_TEST, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", "703-111-1234", "http://www.example.com");
        String activeOrgId = selenium.getText("wwctrl_organization.id");
        assertNotEquals("null", activeOrgId.trim());

        // Goto Manage IO Page
        accessManageIdentifiedOrganizationScreen();
        // add IO
        clickAndWait("add_button_io");
        assertTrue(selenium.isTextPresent("Identified Organization Role Information"));
        // ensure the player is ACTIVE
        assertEquals("ACTIVE", selenium.getText("wwctrl_organization.statusCode"));
        // select a ACTIVE Scoper
        selectOrganizationScoper(ioAffOrgId.trim(), AFFILIATE_ORG_FOR_IO);
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");
        selenium.type("curateRoleForm.role.assignedIdentifier.extension", ctepId);
        selenium.type("curateRoleForm.role.assignedIdentifier.root", "2.16.840.1.113883.3.26.6.2");
        selenium.select("curateRoleForm.role.assignedIdentifier.displayable", "label=TRUE");
        selenium.type("curateRoleForm.role.assignedIdentifier.identifierName", "Cancer Therapy Evaluation Program Organization Identifier");
        selenium.select("curateRoleForm.role.assignedIdentifier.reliability", "label=VRF");
        selenium.select("curateRoleForm.role.assignedIdentifier.scope", "label=OBJ");
        clickAndWaitSaveButton();
        assertTrue(selenium.isTextPresent("exact:Identified Organization was successfully created!"));
        
        // Step2:: Searching using the CTEP ID
        openSearchOrganization();
        selenium.type("searchOrganizationForm_criteria_ctepID", ctepId);
        clickAndWait("submitSearchOrganizationForm");
        int secondColumn = 1;
        int row = getRow(ORG_FOR_TEST, secondColumn);
        if (row == -1) {
            fail("Did not find organization for CTEP ID " + ctepId);
        } else {
            setPoId(row);
            assertEquals(poId, selenium.getTable("row." + row + ".0"));
            assertEquals(ORG_FOR_TEST, selenium.getTable("row." + row + ".1"));
            assertEquals(ctepId, selenium.getTable("row." + row + ".5"));            
            assertEquals("Curate", selenium.getTable("row." + row + ".10"));            
            clear();            
        }
        
      }

    private void verifyRequiredIndicators(boolean expectedValue) {
        verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm.organization.postalAddress.country");
        verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_organization_postalAddress_streetAddressLine");
        verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_organization_postalAddress_cityOrMunicipality");
        verifyPresenceOfRequiredIndicator(expectedValue, "organization.postalAddress.stateOrProvince");
        verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_organization_postalAddress_postalCode");
    }

    private void nothingSelectedTest() {
        clear();
        clickAndWait("submitSearchOrganizationForm");
        assertTrue("At least one criterion message is missing", selenium.isTextPresent("At least one criterion must be provided"));
        assertTrue("Nothing found message is missing", selenium.isTextPresent("Nothing found to display"));
        assertTrue("No items found message is missing", selenium.isTextPresent("No items found"));
    }

    private void noRowsReturnedTest() {
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine", "jf yfnfufigkmi");
        clickAndWait("submitSearchOrganizationForm");
        assertTrue("Nothing found message is missing", selenium.isTextPresent("Nothing found to display"));
        assertTrue("No items found message is missing", selenium.isTextPresent("No items found"));
    }

    private void searchByPoId() {
        selenium.type("searchOrganizationForm_criteria_id", poId);
        selenium.select("searchOrganizationForm_criteria_statusCode", "label=PENDING");
        clickAndWait("submitSearchOrganizationForm");
        verify();
    }

    private void searchByAddress() {
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine", "400 First Street");
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_deliveryAddressLine", orgName);
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_cityOrMunicipality", "Atlanta");
        selenium.type("criteria.organization.postalAddress.stateOrProvince", "GA");
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_postalCode", "30345");
        selenium.select("searchOrganizationForm.criteria.organization.postalAddress.country", "label=United States");
        clickAndWait("submitSearchOrganizationForm");
        verify();
    }


    private void verifySearchForm() {
        assertTrue("PoId field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_id"));
        assertTrue("Status code field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_statusCode"));
        assertTrue("Organization name field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_name"));
        assertTrue("Family name field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_familyName"));
        assertTrue("Address 1 field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine"));
        assertTrue("Address 2 field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_deliveryAddressLine"));
        assertTrue("City field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_cityOrMunicipality"));
        assertTrue("State field is missing",selenium.isElementPresent("criteria.organization.postalAddress.stateOrProvince"));
        assertTrue("Postal code field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_postalCode"));
        assertTrue("Country field is missing",selenium.isElementPresent("searchOrganizationForm.criteria.organization.postalAddress.country"));

        // verify the absence of required indicators.
        verifyPresenceOfRequiredIndicator(false, "searchOrganizationForm.criteria.organization.postalAddress.country");
        verifyPresenceOfRequiredIndicator(false, "searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine");
        verifyPresenceOfRequiredIndicator(false, "searchOrganizationForm_criteria_organization_postalAddress_cityOrMunicipality");
        verifyPresenceOfRequiredIndicator(false, "criteria.organization.postalAddress.stateOrProvince");
        verifyPresenceOfRequiredIndicator(false, "searchOrganizationForm_criteria_organization_postalAddress_postalCode");
    }
}
