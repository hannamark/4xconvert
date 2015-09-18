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
package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
/**
 * Selenium test case for planned markers. This test assumes that the register trial
 * test in registry has already been run.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PlannedMarkerTest extends AbstractPaSeleniumTest {

    private static final int CADSR_SEARCH_WAIT_TIME = 90;

    /**
     * Tests add/edit/deleting planned markers.
     *
     * @throws Exception on error
     */
    @Test
    public void testPlannedMarkers() throws Exception {
        logoutUser();
        TrialInfo trial = createSubmittedTrial();
        loginAsAdminAbstractor();
        searchSelectAndAcceptTrial(trial.title, true, false);
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        checkOutTrialAsScientificAbstractor();
        logoutUser();
        loginAsScientificAbstractor();
        searchAndSelectTrial(trial.title);
        clickAndWait("link=Markers");
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");
        verifyAttributes();
        clickAndWait("link=Cancel");
        assertTrue(selenium.isTextPresent("Nothing found to display."));
        clickAndWait("link=Add");
        //Test Validation and Creation
        clickAndWait("link=Save");
        verifyValidationAndCreation();
        //Verify values
        verifyCreatedValues();
        //Test editing
        verifyEditing(trial.id);
        clickAndWait("link=Select All");
        assertTrue(selenium.isElementPresent("link=Deselect All"));
        //Test deletion
        verifyDeletion();
        // save and retain markers
        //save and retain attributes
        //cadsr button click
        verifycaDSRView();
        // cadsr search
        verifyCaDSRSearch();
        // verify pending marker creation
        verifyCreatePendingMarker();
        

    }
    
    private void verifyAttributes() {
        assertTrue(selenium.isElementPresent("id=name"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-6"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-7"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-8"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-9"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.evaluationType-10"));
        assertFalse(selenium.isVisible("id=evaluationTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-6"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-7"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-8"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-9"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-10"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-11"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-12"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-13"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-14"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-15"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayType-16"));
        assertFalse(selenium.isVisible("id=assayTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=assayUse"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.assayPurpose-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-1"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-2"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-3"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-4"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-5"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-6"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-7"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-8"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-9"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-10"));
        assertTrue(selenium.isElementPresent("id=plannedMarker.tissueSpecimenType-11"));
        assertTrue(selenium.isElementPresent("id=specimenTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=status"));
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        assertTrue(selenium.isElementPresent("link=Save & Retain Marker Name"));
        assertTrue(selenium.isElementPresent("link=Save & Retain Attributes"));
    }
    
    private void verifyValidationAndCreation() {
       assertTrue(selenium.isTextPresent("Name is required"));
       assertTrue(selenium.isTextPresent("Evaluation Type is required."));
       assertTrue(selenium.isTextPresent("Assay Type is required."));
       assertTrue(selenium.isTextPresent("Biomarker Use is required."));
       assertTrue(selenium.isTextPresent("Biomarker Purpose is required."));
       assertTrue(selenium.isTextPresent("Specimen Type is required."));

       selenium.check("id=plannedMarker.evaluationType-10");
       assertTrue(selenium.isVisible("id=evaluationTypeOtherText"));
          
       selenium.check("id=plannedMarker.assayType-16");
       assertTrue(selenium.isVisible("id=assayTypeOtherText"));

       selenium.check("id=plannedMarker.tissueSpecimenType-11");
       assertTrue(selenium.isVisible("id=specimenTypeOtherText"));
          
       clickAndWait("link=Save");
       assertTrue(selenium.isTextPresent("Name is required"));
       assertTrue(selenium.isTextPresent("Evaluation Type Other Text is required."));
       assertTrue(selenium.isTextPresent("Assay Type Other Text is required."));
       assertTrue(selenium.isTextPresent("Biomarker Use is required."));
       assertTrue(selenium.isTextPresent("Biomarker Purpose is required."));
       assertTrue(selenium.isTextPresent("Specimen Type Other Text is required."));

       selenium.type("id=name", "Marker #1");
       selenium.check("id=plannedMarker.evaluationType-1");
       selenium.type("id=evaluationTypeOtherText","EvaluationTypeOtherText");
       selenium.check("id=plannedMarker.assayType-1");
       selenium.type("id=assayTypeOtherText","AssayTypeOtherText");
       selenium.select("id=assayUse","label=Integral");
       selenium.check("id=plannedMarker.assayPurpose-1");
       selenium.check("id=plannedMarker.tissueSpecimenType-1");
       selenium.type("id=specimenTypeOtherText","SpecimenTypeOtherText");
       clickAndWait("link=Save");
       assertTrue(selenium.isTextPresent("Message. Record Created."));
       assertTrue(selenium.isTextPresent("One item found."));
    }
    
    private void verifyCreatedValues() {
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"), "Marker #1");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "Level/Quantity, Other : EvaluationTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "PCR, Other : AssayTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Serum, Other : SpecimenTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input"));
    }
    
    private void verifyEditing(Long id) {
        clickAndWait("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a");
        assertTrue(selenium.isTextPresent("Edit Marker"));
        selenium.type("id=name", "Marker #1 Edit" + id);
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. Record Updated."));
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"), "Marker #1 Edit" + id);
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "Level/Quantity, Other : EvaluationTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "PCR, Other : AssayTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Serum, Other : SpecimenTypeOtherText");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input"));
    }
    private void verifyDeletion() {
        selenium.check("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]"));
        
        ((JavascriptExecutor) driver).executeScript("handleMultiDelete('', 'plannedMarkerdelete.action');");
        waitForPageToLoad();
//        clickAndWait("link=Delete");
//        pause(1000);
        assertTrue(selenium.isTextPresent("Message. Record(s) Deleted."));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]"));
    }
    private void verifycaDSRView() {
        clickAndWait("link=Add");
        assertTrue(selenium.isElementPresent("link=caDSR"));
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        assertTrue(selenium.isElementPresent("id=searchBothTerms"));
        assertTrue(selenium.isElementPresent("id=caseTypetrue"));
        assertFalse(selenium.isChecked("id=caseTypetrue"));
        assertTrue(selenium.isElementPresent("id=highlightRequiredtrue"));
        assertTrue(selenium.isTextPresent("Marker Search in caDSR"));
        assertTrue(selenium.isElementPresent("link=Search"));
        assertTrue(selenium.isElementPresent("link=Reset"));
        assertTrue(selenium.isElementPresent("link=Create Biomarker Request"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        assertTrue(selenium.isElementPresent("id=searchName"));
        assertTrue(selenium.isElementPresent("id=searchPublicId"));
        assertTrue(selenium.isTextPresent("Nothing found to display."));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
    }
    private void verifyCaDSRSearch() {
        assertTrue(selenium.isElementPresent("link=caDSR"));
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[1]")
                .trim(), "alpha"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        assertTrue(selenium.isElementPresent("link=caDSR"));
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 30);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        selenium.select("id=searchBothTerms","label=Synonym");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[3]")
                .trim(), "alpha"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        selenium.check("id=caseTypetrue");
        selenium.check("id=highlightRequiredtrue");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.contains(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[1]")
                .trim(), "alpha"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        clickAndWait("link=Reset");
        assertEquals(selenium.getText("searchName"), "");
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        selenium.type("id=searchName", "alpha");
        selenium.select("id=searchBothTerms","label=Primary Term");
        clickAndWait("link=Search");
        waitForElementById("row", CADSR_SEARCH_WAIT_TIME);
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText(
                "xpath=//form//table[@class='data']//tr[1]//td[1]")
                .trim(), "alpha"));
        clickAndWait("link=Select");
        pause(3000);
        driver.switchTo().defaultContent();
        selenium.check("id=plannedMarker.evaluationType-1");
        selenium.check("id=plannedMarker.assayType-1");
        selenium.select("id=assayUse","label=Integral");
        selenium.check("id=plannedMarker.assayPurpose-1");
        selenium.check("id=plannedMarker.tissueSpecimenType-1");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. Record Created."));
        assertTrue(selenium.isTextPresent("One item found."));
        assertTrue(StringUtils.containsIgnoreCase(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"),"alpha"));
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "Level/Quantity");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "PCR");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Serum");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]"), "Active");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[9]//input"));
        
    }
    
    private void verifyCreatePendingMarker() {
        clickAndWait("link=Add");
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        clickAndWait("link=Create Biomarker Request");
        assertTrue(selenium.isTextPresent("Create Permissible Value Request"));
        assertTrue(selenium.isElementPresent("id=toEmail"));
        assertTrue(selenium.isElementPresent("id=fromEmail"));
        assertTrue(selenium.isElementPresent("id=name"));
        assertTrue(selenium.isElementPresent("id=foundInHugo"));
        assertTrue(selenium.isElementPresent("id=message"));
        assertTrue(selenium.isElementPresent("link=Send Email"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        driver.switchTo().defaultContent();
        selenium.click("id=popCloseBox");
        
        clickAndWait("link=caDSR");
        waitForElementById("popupFrame", 15);
        selenium.selectFrame("popupFrame");
        clickAndWait("link=Create Biomarker Request");
        assertTrue(selenium.isTextPresent("Create Permissible Value Request"));
        selenium.type("id=fromEmail", "reshma.kgoanti@semanticbits.com");
        selenium.type("id=name", "TestPendingMarker");
        selenium.type("id=message", "TestPendingMarker message");
        clickAndWait("link=Send Email");
        pause(3000);
        driver.switchTo().defaultContent();
        selenium.click("id=plannedMarker.evaluationType-1");
        selenium.click("id=plannedMarker.assayType-1");
        selenium.select("id=assayUse","label=Integral");
        selenium.click("id=plannedMarker.assayPurpose-1");
        selenium.click("id=plannedMarker.tissueSpecimenType-1");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. Record Created."));
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[1]"),"TestPendingMarker");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[2]"), "Level/Quantity");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[3]"), "PCR");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[4]"), "Integral");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[5]"), "Eligibility Criterion");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[6]"), "Serum");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[7]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[8]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[2]//td[9]//input"));
    }
}
