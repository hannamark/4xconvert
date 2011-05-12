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

import org.junit.Test;

/**
 * Selenium test case for planned markers. This test assumes that the register trial
 * test in registry has already been run.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PlannedMarkerTest extends AbstractPaSeleniumTest {

    /**
     * Tests add/edit/deleting planned markers.
     *
     * @throws Exception on error
     */
    @Test
    public void testPlannedMarkers() throws Exception {
        loginAsScientificAbstractor();
        verifyTrialSearchPage();
        selenium.type("id=officialTitle", "Test Trial created by Selenium.");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        assertTrue(selenium.isElementPresent("id=row"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]"));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='row']//tr[2]"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='row']//tr[1]//td[1]/a"));
        String nciTrialId = selenium.getText("xpath=//table[@id='row']//tr[1]//td[1]/a");
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");
        verifyTrialSelected(nciTrialId);
        checkOutTrial();
        acceptTrial();
        verifyTrialAccepted();

        clickAndWait("link=Markers");
        assertTrue(selenium.isElementPresent("link=Add"));
        clickAndWait("link=Add");
        assertTrue(selenium.isElementPresent("id=name"));
        assertTrue(selenium.isElementPresent("id=assayType"));
        assertFalse(selenium.isVisible("id=assayTypeOtherText"));
        assertTrue(selenium.isElementPresent("id=assayUse"));
        assertTrue(selenium.isElementPresent("id=assayPurpose"));
        assertFalse(selenium.isVisible("id=assayPurposeOtherText"));
        assertTrue(selenium.isElementPresent("id=name"));
        assertTrue(selenium.isElementPresent("id=tissueSpecimenType"));
        assertTrue(selenium.isElementPresent("id=tissueCollectionMethod"));
        assertTrue(selenium.isElementPresent("id=status"));
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Cancel"));
        clickAndWait("link=Cancel");
        assertTrue(selenium.isTextPresent("Nothing found to display."));
        clickAndWait("link=Add");

        //Test Validation and Creation
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Name is required"));
        assertTrue(selenium.isTextPresent("Assay Type is required"));
        assertTrue(selenium.isTextPresent("Assay Use is required"));
        assertTrue(selenium.isTextPresent("Assay Purpose is required"));
        assertTrue(selenium.isTextPresent("Tissue Specimen Type is required"));
        assertTrue(selenium.isTextPresent("Tissue Collection Method is required"));

        selenium.select("id=assayType", "label=Other");
        assertTrue(selenium.isVisible("id=assayTypeOtherText"));

        selenium.select("id=assayPurpose", "label=Other");
        assertTrue(selenium.isVisible("id=assayPurposeOtherText"));
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Name is required"));
        assertTrue(selenium.isTextPresent("Assay Type Other Text is required"));
        assertTrue(selenium.isTextPresent("Assay Use is required"));
        assertTrue(selenium.isTextPresent("Assay Purpose Other Text is required"));
        assertTrue(selenium.isTextPresent("Tissue Specimen Type is required"));
        assertTrue(selenium.isTextPresent("Tissue Collection Method is required"));

        selenium.type("id=name", "Marker #1");
        selenium.select("id=assayType", "label=RT-PCR");
        selenium.select("id=assayUse", "label=Research");
        selenium.select("id=assayPurpose", "label=Treatment Assignment");
        selenium.select("id=tissueSpecimenType", "label=Serum");
        selenium.select("id=tissueCollectionMethod", "label=Voluntary");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Created"));
        assertTrue(selenium.isTextPresent("One item found."));

        //Verify values
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"), "Marker #1");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "RT-PCR");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "Research");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Treatment Assignment");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Voluntary");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));

        //Test prevention of duplicate creation
        clickAndWait("link=Add");
        selenium.type("id=name", "Marker #1");
        selenium.select("id=assayType", "label=RT-PCR");
        selenium.select("id=assayUse", "label=Research");
        selenium.select("id=assayPurpose", "label=Treatment Assignment");
        selenium.select("id=tissueSpecimenType", "label=Serum");
        selenium.select("id=tissueCollectionMethod", "label=Voluntary");
        clickAndWait("link=Save");
        assertFalse(selenium.isTextPresent("Record Created"));
        assertTrue(selenium.isTextPresent("Duplicate Planned Markers are not allowed."));
        clickAndWait("link=Cancel");
        assertTrue(selenium.isTextPresent("One item found."));

        //Test editing
        clickAndWait("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]//a");
        assertTrue(selenium.isTextPresent("Edit Marker"));
        selenium.type("id=name", "Marker #1 Edit");
        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Record Updated"));

        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[1]"), "Marker #1 Edit");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[2]"), "RT-PCR");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[3]"), "Research");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[4]"), "Treatment Assignment");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[5]"), "Voluntary");
        assertEquals(selenium.getText("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[6]"), "Pending");
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[7]//a"));
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a"));

        //Test deletion
        selenium.chooseCancelOnNextConfirmation();
        selenium.click("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a");
        selenium.getConfirmation();
        assertTrue(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]"));

        clickAndWait("xpath=//table[@id='plannedMarkerTable']//tr[1]//td[8]//a");
        selenium.getConfirmation();
        assertTrue(selenium.isTextPresent("Record Deleted"));
        assertFalse(selenium.isElementPresent("xpath=//table[@id='plannedMarkerTable']//tr[1]"));
    }
}
