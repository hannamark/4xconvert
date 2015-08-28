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



import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 * Selenium test for manage terms feature
 * 
 * @author Gopalakrishnan Unnikrishnan
 */
public class ResultsDashboardTest extends AbstractPaSeleniumTest {
    
    List<TrialInfo> testTrials;
    
    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
        logoutUser();
        deactivateAllTrials();
        registerTestTrials();
        loginAsResultsAbstractor();
        clickAndWait("link=Results Reporting");
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking Dashboard"));
    }
    
    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }
    
    
    
    /**
     * Test the results reporting home page
     */
    @Test       
    public void testResultDashboardHomePage(){
        assertTrue(selenium.isTextPresent("Section 801 Indicator:"));
        assertTrue(selenium.isTextPresent("Primary Completion Date:"));
        assertTrue(selenium.isTextPresent("From:"));
        assertTrue(selenium.isTextPresent("To:"));
        assertTrue(selenium.isTextPresent("Type:"));
        assertTrue(selenium.isTextPresent("Search"));
        assertTrue(selenium.isTextPresent("Reset"));
        assertTrue(selenium.isTextPresent("Search Results"));
        assertTrue(selenium.isTextPresent("3 items found, displaying all items.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
        assertTrue(selenium.isTextPresent("Results Reporting Progress"));
        assertTrue(selenium.isTextPresent("Add/Update Designee or PIO Contact"));
        assertTrue(selenium.isTextPresent("View/Upload Trial Comparison Documents"));
        assertTrue(selenium.isTextPresent("Results Cover Sheet"));
        assertTrue(selenium.isTextPresent("XML Upload Errors & Actions Taken"));
        assertTrue(selenium.isTextPresent("Enter Trial ID:"));
    }
    
    @Test       
    public void testOnlyOneOfsection801CheckboxIsChecked(){
        selenium.click("id=section801IndicatorYes");
        assertFalse(driver.findElement(By.id("section801IndicatorNo")).isSelected());
        selenium.click("id=section801IndicatorNo");
        assertFalse(driver.findElement(By.id("section801IndicatorYes")).isSelected());
        selenium.click("id=section801IndicatorNo");
        assertFalse(driver.findElement(By.id("section801IndicatorNo")).isSelected());
        assertFalse(driver.findElement(By.id("section801IndicatorYes")).isSelected());
    }
    
    @Test       
    public void testResultsSearchNoFilter(){
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("Search Results"));
        assertTrue(selenium.isTextPresent("3 items found, displaying all items.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }
    
    @Test       
    public void testResultsSearchWithDateFilter(){
        selenium.type("id=pcdFrom", "01/01/2015");
        selenium.type("id=pcdTo", "12/31/2015");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("2 items found, displaying all items."));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }
    
    @Test       
    public void testResultsSearchWithDateTypeFilter(){
        selenium.type("id=pcdFrom", "01/01/2015");
        selenium.type("id=pcdTo", "12/31/2015");
        final Select selectBox = new Select(driver.findElement(By.id("pcdType")));
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");        
        java.util.Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        
        selectBox.selectByValue("Anticipated");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found.1"));
        
        assertTrue(selenium.isTextPresent("ClinicalTrials.gov Import " + reportDate));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }    
    
    @Test       
    public void testResultsSearchBySection801Yes(){
        selenium.click("id=section801IndicatorYes");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("2 items found, displaying all items.1"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(1).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }        
    
    @Test       
    public void testResultsSearchBySection801No(){
        selenium.click("id=section801IndicatorNo");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found."));
        assertFalse(selenium.isTextPresent(testTrials.get(0).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(1).nciID));
        assertTrue(selenium.isTextPresent(testTrials.get(2).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(3).nciID));
        assertFalse(selenium.isTextPresent(testTrials.get(4).nciID));
    }
    
    @Test       
    public void testUpdateDate(){
        long trialId = testTrials.get(0).id;
        selenium.type("id=pcdSentToPIODate_"+trialId, "01/01/2015");
        selenium.type("id=pcdConfirmedDate_"+testTrials.get(0).id, "01/02/2015");
        selenium.type("id=desgneeNotifiedDate_"+trialId, "01/03/2015");
        selenium.type("id=reportingInProcessDate_"+trialId, "01/04/2015");
        selenium.type("id=threeMonthReminderDate_"+trialId, "01/05/2015");
        selenium.type("id=fiveMonthReminderDate_"+trialId, "01/06/2015");
        selenium.type("id=sevenMonthEscalationtoPIODate_"+trialId, "01/07/2015");
        selenium.type("id=resultsSentToPIODate_"+trialId, "01/08/2015");
        selenium.type("id=resultsApprovedByPIODate_"+trialId, "01/09/2015");
        selenium.type("id=prsReleaseDate_"+trialId, "01/10/2015");
        selenium.type("id=qaCommentsReturnedDate_"+trialId, "01/11/2015");
        selenium.type("id=trialPublishedDate_"+trialId, "01/12/2015");
        driver.findElement(By.id("trialPublishedDate_"+trialId)).sendKeys("\t");
        pause(1000);
        clickAndWait("link=Search");
        pause(1000);
        assertEquals("01/01/2015",driver.findElement(By.id("pcdSentToPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/02/2015",driver.findElement(By.id("pcdConfirmedDate_"+trialId)).getAttribute("value"));
        assertEquals("01/03/2015",driver.findElement(By.id("desgneeNotifiedDate_"+trialId)).getAttribute("value"));
        assertEquals("01/04/2015",driver.findElement(By.id("reportingInProcessDate_"+trialId)).getAttribute("value"));
        assertEquals("01/05/2015",driver.findElement(By.id("threeMonthReminderDate_"+trialId)).getAttribute("value"));
        assertEquals("01/06/2015",driver.findElement(By.id("fiveMonthReminderDate_"+trialId)).getAttribute("value"));
        assertEquals("01/07/2015",driver.findElement(By.id("sevenMonthEscalationtoPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/08/2015",driver.findElement(By.id("resultsSentToPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/09/2015",driver.findElement(By.id("resultsApprovedByPIODate_"+trialId)).getAttribute("value"));
        assertEquals("01/10/2015",driver.findElement(By.id("prsReleaseDate_"+trialId)).getAttribute("value"));
        assertEquals("01/11/2015",driver.findElement(By.id("qaCommentsReturnedDate_"+trialId)).getAttribute("value"));
        assertEquals("01/12/2015",driver.findElement(By.id("trialPublishedDate_"+trialId)).getAttribute("value"));
    } 
    
    @Test
    public void testPiechart(){
        assertTrue(selenium.isTextPresent("Results Reporting Progress"));
        assertTrue(selenium.isTextPresent("In Process"));
        assertTrue(selenium.isTextPresent("Completed"));
        assertTrue(selenium.isTextPresent("Not Started"));
        assertTrue(selenium.isTextPresent("Issues"));
        assertNotNull(driver.findElement(By.id("resultsChart")));
    }

    public void testSearchTrialComparison(){
        selenium.type("id=trialCompDocsTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("trialCompDocsTrialSearch");
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking - View/Upload Trial Comparison Documents"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchResultsCoverSheet(){
        selenium.type("id=coverSheetTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("coverSheetTrialSearch");
        assertTrue(selenium.isTextPresent("Results Reporting & Tracking & Cover Sheet"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchUploadErrors(){
        selenium.type("id=uploadErrorsTrialId", testTrials.get(0).nciID);
        clickSearchForResultsReportingData("uploadErrorsTrialSearch");
        pause(1000);
        assertTrue(selenium.isTextPresent("Summary Of XML Upload Errors & Actions Taken"));
        assertTrue(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    public void testSearchUploadErrorsNoTrialId(){
        clickSearchForResultsReportingData("uploadErrorsTrialSearch");
        assertTrue(selenium.isTextPresent("Summary Of XML Upload Errors & Actions Taken"));
        assertFalse(selenium.isTextPresent(testTrials.get(0).nciID+": "+testTrials.get(0).title));
    }
    
    private void clickSearchForResultsReportingData(String searchBtnId){
        clickAndWaitAjax("//*[@id='"+searchBtnId+"']");
    }
    
    private void registerTestTrials()
            throws SQLException {
        testTrials = new ArrayList<AbstractPaSeleniumTest.TrialInfo>();
        TrialInfo trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_RESPONSE");
        setPCD(trial, "2015-01-01", ActualAnticipatedTypeCode.ANTICIPATED);
        addDocument(trial, "COMPARISON", "Protocol.doc");
        setSeciont801Indicator(trial, true);
        testTrials.add(trial);
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "ABSTRACTION_VERIFIED_NORESPONSE");
        setPCD(trial, "2015-12-31", ActualAnticipatedTypeCode.ACTUAL);
        setSeciont801Indicator(trial, true);
        testTrials.add(trial);
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        addDWS(trial, "VERIFICATION_PENDING");
        setPCD(trial, "2016-01-01", ActualAnticipatedTypeCode.ACTUAL);
        setSeciont801Indicator(trial, false);
        testTrials.add(trial);        
        
        trial = createSubmittedTrial();
        addSponsor(trial, "National Cancer Institute");
        testTrials.add(trial);
        
        trial = createSubmittedTrial();
        addSponsor(trial, "Cancer Therapy Evaluation Program");
        addDWS(trial, "VERIFICATION_PENDING");
        testTrials.add(trial);
    }
    
}
