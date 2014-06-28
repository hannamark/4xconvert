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

public class OrganizationWebTest extends AbstractPoWebTest {
    protected String orgName = "orgName" + Long.toString(System.currentTimeMillis());
    protected String poId = null;

    protected void setPoId(int row) {
        if (poId == null) {
            poId = selenium.getText("//table[@id='row']/tbody/tr[" + row + "]/td[1]");
        }
    }

    protected void verify() {
        verify(true);
    }

    protected void verify(boolean clear) {
        int secondColumn = 1;
        int row = getRow(orgName, secondColumn);
        if (row == -1) {
            fail("Did not find " + orgName + " in search results");
        } else {
            setPoId(row);
            assertEquals(poId, selenium.getTable("row." + row + ".0"));
            assertEquals(orgName, selenium.getTable("row." + row + ".1"));
            assertEquals("", selenium.getTable("row." + row + ".2"));
            assertEquals("PENDING", selenium.getTable("row." + row + ".9"));
            assertEquals("", selenium.getTable("row." + row + ".6"));
            assertEquals("Curate", selenium.getTable("row." + row + ".10"));
            if (clear) {
                clear();
            }
        }
    }

    protected void searchByName(boolean clear) {
        selenium.type("searchOrganizationForm_criteria_name", orgName);
        clickAndWait("submitSearchOrganizationForm");
        verify(clear);
    }

    protected void searchByName() {
        searchByName(true);
    }
    
    protected void searchByOrgAlias() {               
        // search by alias name
        selenium.type("searchOrganizationForm_criteria_name", ALIAS_STRING);
        clickAndWait("submitSearchOrganizationForm");
       
        int secondColumn = 1;
        int row = getRow(orgName, secondColumn);
        if (row == -1) {
            fail("Did not find organization for alias " + ALIAS_STRING);
        } else {
            setPoId(row);
            assertEquals(poId, selenium.getTable("row." + row + ".0"));
            assertEquals(orgName, selenium.getTable("row." + row + ".1"));
            assertEquals("PENDING", selenium.getTable("row." + row + ".9"));            
            assertEquals("Curate", selenium.getTable("row." + row + ".10"));            
            clear();            
        }
    }

    protected void addOrganization() {
        selenium.select("curateEntityForm.organization.statusCode", "label=PENDING");
        selenium.type("curateEntityForm_organization_name", orgName);
        selenium.type("curateEntityForm_organization_postalAddress_streetAddressLine", "400 First Street");
        selenium.type("curateEntityForm_organization_postalAddress_deliveryAddressLine", orgName);
        selenium.type("curateEntityForm_organization_postalAddress_postalCode", "30345");
        selenium.select("curateEntityForm.organization.postalAddress.country", "label=United States");
        waitForElementById("organization.postalAddress.stateOrProvince", 10);
        selenium.type("curateEntityForm_organization_postalAddress_cityOrMunicipality", "Atlanta");
        selenium.select("organization.postalAddress.stateOrProvince", "label=GA (GEORGIA)");
        
        inputAlias(); // add Alias

        selenium.type("emailEntry_value", "emailAddress@example.com");
        selenium.click("email-add");
        waitForElementById("email-entry-0", 5);

        selenium.type("phoneEntry_value", "899-090-0987");
        selenium.click("phone-add");
        waitForElementById("phone-entry-0", 5);

        selenium.type("faxEntry_value", "123-987-0908");
        selenium.click("fax-add");
        waitForElementById("fax-entry-0", 5);

        selenium.type("ttyEntry_value", "800-834-7654");
        selenium.click("tty-add");
        waitForElementById("tty-entry-0", 5);

        selenium.type("urlEntry_value", "http://sample.url.org");
        selenium.click("url-add");
        waitForElementById("url-entry-0", 5);

        clickAndWaitSaveButton();
        assertTrue("Organization was successfully created!",
                selenium.isTextPresent("Organization was successfully created"));
    }

    protected void clear() {
        openSearchOrganization();
    }
}
