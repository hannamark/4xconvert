/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.registry.test.integration;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.selenium.SeleniumException;

/**
 * Tests the trial registration process.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class RegisterTrialTest extends AbstractRegistrySeleniumTest {

  
    /**
     * Tests registering a trial.
     * @throws Exception on error
     */
    @Test
    public void testRegisterTrial() throws Exception {        
        loginAndAcceptDisclaimer();
        // register a trial
        String rand = RandomStringUtils.randomNumeric(10);
        registerTrial(rand);       
        final String nciID = getLastNciId();
        assertTrue("No success message found",
                   selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier "+nciID));
        verifyRegisterTrialConfirmaionPage(rand, nciID);
        
        /**
        int nciId1 = getSeqNumFromNciId(getNciIdViaSearch(TRIAL_NAME));
        // try to register a trial with the same lead org trial ID and fail
        registerTrialWithoutDeletingExistingOne(TRIAL_NAME, LEAD_ORG_TRIAL_ID);
        waitForPageToLoad();
        assertFalse("A success message was found", selenium.isElementPresent("css=div.confirm_msg"));
        assertFalse("A success message was found",
                    selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier"));
        assertTrue("No error message found", selenium.isElementPresent("css=div.error_msg"));
        assertTrue("No error message found",
                   selenium
                       .isTextPresent("Error Message: A trial exists in the system with the same Lead Organization Trial Identifier for the selected Lead Organization"));

        // try to register a trial with the a new lead org trial ID (and title) and succeed
        registerTrial(TRIAL_NAME2, LEAD_ORG_TRIAL_ID2);
        waitForPageToLoad();
        assertTrue("No success message found", selenium.isElementPresent("css=div.confirm_msg"));
        assertTrue("No success message found",
                   selenium.isTextPresent("The trial has been successfully submitted and assigned the NCI Identifier"));
        int nciId2 = getSeqNumFromNciId(getNciIdViaSearch(TRIAL_NAME2));
        assertEquals(nciId2, nciId1 + 1);
        
        **/
    }

    /**
     * Tests saving a draft trial.
     * @param nciID 
     * @throws Exception on error
     */
    
    /*public void testSaveDraftTrial() throws Exception {
        loginAndAcceptDisclaimer();
        // register a trial
        registerDraftTrial("Test Trial Draft created by Selenium.", LEAD_ORG_TRIAL_ID);
        waitForPageToLoad();
        assertTrue("No success message found",
                   selenium.isTextPresent("The trial draft has been successfully saved and assigned the Identifier"));
    }*/
    
    private void verifyRegisterTrialConfirmaionPage(String rand, String nciID) {       
        assertEquals(nciID, getTrialConfValue("NCI Trial Identifier:"));
        assertEquals("LEAD"+rand, getTrialConfValue("Lead Organization Trial Identifier:"));
        assertEquals("NCT"+rand, getTrialConfValue("ClinicalTrials.gov Identifier:"));
        assertTrue(selenium.isElementPresent("//li[normalize-space(text())='OTHER"+rand+"']"));
        assertEquals("An Open-Label Study of Ruxolitinib "+rand, getTrialConfValue("Title:"));
        assertEquals("0", getTrialConfValue("Phase:"));
        assertEquals("Interventional", getTrialConfValue("Trial Type:"));
        assertEquals("Treatment", getTrialConfValue("Primary Purpose:"));
        assertEquals("Ancillary", getTrialConfValue("Secondary Purpose:"));
        assertEquals("ICD10", getTrialConfValue("Accrual Disease Terminology:"));
        assertEquals("National Cancer Institute Division of Cancer Prevention", getTrialConfValue("Lead Organization:"));
        assertEquals("Doe, John", getTrialConfValue("Principal Investigator:"));
        assertEquals("Cancer Therapy Evaluation Program", getTrialConfValue("Sponsor:"));
        assertEquals("Sponsor", getTrialConfValue("Responsible Party:"));
        assertEquals("National", getTrialConfValue("Summary 4 Funding Sponsor Type:"));
        assertEquals("National Cancer Institute", getTrialConfValue("Summary 4 Funding Sponsor/Source:"));
        assertEquals("PG"+rand, getTrialConfValue("Program code:"));
        assertEquals("In Review", getTrialConfValue("Current Trial Status:"));
        assertEquals("", getTrialConfValue("Why the Study Stopped:"));
        assertEquals(today, getTrialConfValue("Current Trial Status Date:"));
        assertEquals(tommorrow+" Anticipated", getTrialConfValue("Trial Start Date:"));
        assertEquals(oneYearFromToday+" Anticipated", getTrialConfValue("Primary Completion Date:"));
        assertEquals(oneYearFromToday+" Anticipated", getTrialConfValue("Completion Date:"));
        
        assertEquals("IND", selenium
                .getText("//div[@id='indideDiv']/table/tbody/tr/td[1]"));
        
    }

    private String getTrialConfValue(String labeltxt) {
        try {
            return selenium
                    .getText("//div[preceding-sibling::label[normalize-space(text())='"
                            + labeltxt + "']]");

        } catch (SeleniumException e) {
            return selenium
                    .getText("//div[preceding-sibling::label/strong[normalize-space(text())='"
                            + labeltxt + "']/..]");
        }
    }

    /**
     * Tests Lookup of an organization with apostrophe.
     * @throws Exception on error
     */
    @Ignore("Assumes po is up and running. Needs to be fixed.")
    /*public void lookupOrganization() throws Exception {
        loginAndAcceptDisclaimer();
        clickAndWaitAjax("registerTrialMenuOption");
        selenium.selectFrame("popupFrame");
        waitForElementById("summaryFourFundingCategoryCode", 60);
        selenium.select("summaryFourFundingCategoryCode", "label=National");
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();
        clickAndWaitAjax("link=Look Up Org");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        clickAndWaitAjax("link=Add Org");
        String orgName = "PO-2098'test organization";
        selenium.type("orgName", orgName);
        selenium.type("orgAddress", "2115 E Jefferson St");
        selenium.type("orgCity", "Rockville");
        selenium.select("orgStateSelect", "label=MD");
        selenium.type("orgZip", "20852");
        selenium.type("orgEmail", "po2098@example.com");
        clickAndWaitAjax("link=Save");
        clickAndWaitAjax("link=Cancel");
        clickAndWaitAjax("link=Close");
        selenium.waitForPageToLoad("30000");

        driver.switchTo().defaultContent();
        clickAndWaitAjax("link=Look Up Org");
        selenium.selectFrame("popupFrame");
        selenium.type("orgNameSearch", "'");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        assertTrue("Wrong search results returned", selenium.isTextPresent("One item found"));
        assertTrue("Wrong search results returned", selenium.isTextPresent(orgName));
        clickAndWait("//table[@id='row']/tbody/tr[1]/td[7]/a/span/span");
        selenium.selectWindow(null);
        assertEquals("Wrong Principal investigator", orgName, selenium.getValue("name=trialDTO.leadOrganizationName"));
    }*/

    /**
     * Tests Lookup of a person with apostrophe.
     * @throws Exception on error
     */
    /*
    public void lookupPerson() throws Exception {
        loginAndAcceptDisclaimer();
        clickAndWaitAjax("registerTrialMenuOption");
        selenium.selectFrame("popupFrame");
        waitForElementById("summaryFourFundingCategoryCode", 60);
        selenium.select("summaryFourFundingCategoryCode", "label=National");
        clickAndWaitAjax("link=Submit");
        waitForPageToLoad();
        clickAndWaitAjax("link=Look Up Person");
        waitForElementById("popupFrame", 60);
        selenium.selectFrame("popupFrame");
        clickAndWaitAjax("link=Add Person");
        selenium.type("poOrganizations_firstName", "Michael");
        selenium.type("poOrganizations_lastName", "O'Grady");
        selenium.type("poOrganizations_streetAddress", "2115 E Jefferson St");
        selenium.type("poOrganizations_city", "Rockville");
        selenium.select("poOrganizations_orgStateSelect", "label=MD");
        selenium.type("poOrganizations_zip", "20852");
        selenium.type("poOrganizations_email", "mogrady@example.com");
        clickAndWaitAjax("link=Save");
        clickAndWaitAjax("link=Cancel");
        clickAndWaitAjax("link=Close");
        selenium.waitForPageToLoad("30000");
        driver.switchTo().defaultContent();
        clickAndWaitAjax("link=Look Up Person");
        selenium.selectFrame("popupFrame");
        selenium.type("lastName", "'");
        clickAndWaitAjax("link=Search");
        waitForElementById("row", 15);
        assertTrue("Wrong search results returned", selenium.isTextPresent("One item found"));
        assertTrue("Wrong search results returned", selenium.isTextPresent("O'Grady"));
        clickAndWait("//table[@id='row']/tbody/tr[1]/td[6]/a/span/span");
        selenium.selectWindow(null);
        assertEquals("Wrong Principal investigator", "O'Grady,Michael", selenium.getValue("name=trialDTO.piName"));
    }*/

    private String getNciIdViaSearch(String trialName) {

        clickAndWait("searchTrialsMenuOption");
        waitForElementById("searchMyTrialsBtn", 5);
        waitForElementById("searchAllTrialsBtn", 5);

        selenium.type("id=officialTitle", trialName);
        clickAndWait("id=searchAllTrialsBtn");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]"));
        String nciId = selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]");
        assertTrue(nciId.contains("NCI"));
        assertEquals(14, nciId.length());
        return nciId;
    }

 

}
