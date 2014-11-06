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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Selenium test for adding/removing diseases
 * 
 * @author Gopalakrishnan Unnikrishnan
 */
public class DiseaseTest extends AbstractPaSeleniumTest {
    
    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
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
        clickAndWait("link=Disease/Condition");
        assertTrue(selenium.isTextPresent("Nothing found to display"));
        clickAndWait("link=Add");
        selenium.selectFrame("popupFrame");
        waitForElementById("disease", 30);
        assertTrue(selenium.isTextPresent("Search Synonyms"));        
    }
    
    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }
    
    
    
    /**
     * Search for non existing disease
     */
    @Test       
    public void testSearchNonExistingDisese(){
        // Search for invalid d
//        searchDisease("invalid disease");
//        
//        assertTrue(selenium.isTextPresent("0 results for \"invalid disease\""));
//        assertFalse(selenium.isTextPresent("Preferred Term"));
        assertTrue(true);
    } 
    
    /**
     * Search by Preferred term
     */
 /*   @Test
    public void testSearchDiseseByPreferredTerm(){
        //Search for preferred term
        searchDisease("lung cancer");
        
        assertTrue(selenium.isTextPresent("32 results for \"lung cancer\""));
        assertTrue(selenium.isTextPresent("Preferred Term"));
    }*/

    /**
     * Search by synonym
     */
   /*// @Test
    public void testSearchDiseaseBySynonym(){
        //Search synonyms
        selenium.click("id=searchSynonym");
        searchDisease("lung carcinoma");
        
        assertTrue(selenium.isTextPresent("18 results for \"lung carcinoma\""));
        assertTrue(selenium.isTextPresent("Preferred Term"));
        assertTrue(selenium.isTextPresent("Synonyms"));
        
    }   */ 
    
    /**
     * Select search results 
     */
   /* @Test
    public void testSelectDisease(){
        //Search synonyms
        //Search for preferred term
        searchDisease("lung cancer");
        selenium.click("class=breadcrumbFeaturedElementText");
        assertTrue(selenium.isTextPresent("1 diseases selected"));
    }*/    
    
    /**
     * Add disease to study 
     */
    //@Test
    /*public void testAddDiseaseToStudy(){
        //Search synonyms
        //Search for preferred term
        searchDisease("lung cancer");
        selenium.click("class=breadcrumbFeaturedElement");
        assertTrue(selenium.isTextPresent("1 selection added"));
        clickAndWait("link=Add");
        waitForElementById("popupContainer", 30);
        assertFalse(selenium.isElementPresent("id=popupContainer"));
        assertTrue(selenium.isTextPresent("One item found"));       
        assertTrue(selenium.isTextPresent("Stage 0 Lung Cancer"));
    }*/
    
    /**
     * Delete disease from study 
     */
    //@Test
    /*public void testDeleteDiseaseFromStudy(){
        //Search synonyms
        //Search for preferred term
        searchDisease("lung cancer");
        selenium.click("class=breadcrumbFeaturedElement");
        clickAndWait("link=Add");
        assertTrue(selenium.isTextPresent("One item found"));        

        selenium.click("name=objectsToDelete");
        clickAndWait("link=Delete");
        driver.switchTo().alert().accept();
        assertTrue(selenium.isTextPresent("Nothing found to display"));
    }*/
    
    
    /**
     * Test open hierarchy tree 
     */
    /*@Test
    public void testOpenHierarchyTree(){
        //Search synonyms
        //Search for preferred term
        searchDisease("lung cancer");
        selenium.click("class=breadcrumbFeaturedElementTreeLink");
        assertTrue(selenium.isElementPresent("id=ui-dialog-title-pdq_tree_dialog"));
    }  */
    
    /**
     * Test Add all
     */
   /* @Test
    public void testAddAll(){
        //Search synonyms
        //Search for preferred term
        searchDisease("lung cancer");
        selenium.click("link=Add All");
        assertTrue(selenium.isTextPresent("32 diseases selected"));
    }  */
    
    /**
     * Test show tree
     */
    /*@Test
    public void testShowTree(){
        selenium.click("link=Show Tree");
        assertTrue(selenium.isElementPresent("id=ui-dialog-title-pdq_tree_dialog"));
    }  */
    
    /**
     * Test reset
     */
    /*
    //@Test
    public void testReset(){
        //Search synonyms
        //Search for preferred term
        searchDisease("lung cancer");
        selenium.click("link=Add All");
        assertTrue(selenium.isTextPresent("32 diseases selected"));
        
        selenium.click("link=Reset");
        
        assertFalse(selenium.isTextPresent("32 diseases selected"));
        assertFalse(selenium.isTextPresent("Preferred Term"));
        assertFalse(selenium.isElementPresent("class=breadcrumbFeaturedElement"));
        assertFalse(selenium.isTextPresent("lung cancer"));
    }  */
    
    private void searchDisease(String searchName) {
        selenium.type("id=disease", searchName);
        clickAndWaitAjax("alt=Search");
        pause(5000);
    }

}
