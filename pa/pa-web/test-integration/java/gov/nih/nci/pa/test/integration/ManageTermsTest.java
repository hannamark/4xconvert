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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * Selenium test for manage terms feature
 * 
 * @author Gopalakrishnan Unnikrishnan
 */
public class ManageTermsTest extends AbstractPaSeleniumTest {
    
    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
        logoutUser();
        
        loginAsSuperAbstractor();
        clickAndWait("link=Manage NCIt Terms");
        assertTrue(selenium.isTextPresent("Manage NCIt Disease/Condition or Intervention Terms"));
    }
    
    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }
    
    /**
     * Test the manage terms home page contents
     */
    @Test       
    public void testMenuItems(){
        assertTrue(selenium.isTextPresent("Manage NCIt Intervention Terms"));
        assertTrue(selenium.isTextPresent("Manage NCIt Disease Terms"));
        assertTrue(selenium.isElementPresent("link=Enter Term Information"));
        assertTrue(selenium.isElementPresent("link=Import/Sync Term with NCIt"));
        assertTrue(selenium.isElementPresent("link=EVS New Term Request Form"));
        
    }
    
    /**
     * test create intervention
     */
    @Test
    public void testEnterInterventionTerm(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//span[@class='btn_img']"))).perform();
        clickAndWait("xpath=//a[@href='manageTermscreateIntervention.action']");
        assertTrue(selenium.isTextPresent("Enter New Intervention Details"));
        
        // Test for validation errors
        clickAndWait("link=Save");
        
        assertTrue(selenium.isTextPresent("Message. Please correct the errors listed below and resubmit."));
        assertTrue(selenium.isTextPresent("NCI Identifier must be entered"));
        assertTrue(selenium.isTextPresent("Preferred Name must be entered"));
        
        // Test save successful with mandatory fields
        selenium.type("id=ntTermIdentifier", "CTEST123");
        selenium.type("id=pdqTermIdentifier", "CDRTEST123");
        selenium.type("id=name", "Preferred Name");
        
        clickAndWait("link=Save");
        
        assertTrue(selenium.isTextPresent("Message. New intervention CTEST123 added successfully."));
    }

    /**
     * test create intervention
     */
    @Test
    public void testImportInterventionTerm(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//span[@class='btn_img']"))).perform();
        clickAndWait("xpath=//a[@href='manageTermssearchIntervention.action?searchStart=true']");
        assertTrue(selenium.isTextPresent("Import New Intervention From NCIt"));
        
        // Test for validation errors
        clickAndWait("link=Look Up");
        
        assertTrue(selenium.isTextPresent("Message. Enter valid NCIt Identifier."));
        
        // Test import non existing term
        selenium.type("id=ntTermIdentifier", "CTEST123");
        clickAndWait("link=Look Up");
        assertTrue(selenium.isTextPresent("Message. No intervention with NCIt code 'CTEST123' found in NCI Thesaurus, try a different code."));
        
        // Test valid import
        selenium.type("id=ntTermIdentifier", "C800");
        clickAndWait("link=Look Up");
        assertTrue(selenium.isTextPresent("Import New Intervention From NCIt"));
        assertEquals("C800",selenium.getValue("id=ntTermIdentifier"));
        assertEquals("Radon",selenium.getValue("id=name"));
        assertEquals("",selenium.getValue("id=pdqTermIdentifier"));
        assertEquals("Rn",selenium.getValue("id=intervAltNames"));
        assertEquals("",selenium.getValue("id=typeCode"));
        assertEquals("",selenium.getValue("id=ctTypeCode"));
        
        selenium.type("id=pdqTermIdentifier", "CDR123");    
        clickAndWait("link=Import");
        
        assertTrue(selenium.isTextPresent("Message. New intervention C800 added successfully."));
        
        //Test sync        
        action.moveToElement(driver.findElement(By.xpath("//span[@class='btn_img']"))).perform();
        clickAndWait("xpath=//a[@href='manageTermssearchIntervention.action?searchStart=true']");
        assertTrue(selenium.isTextPresent("Import New Intervention From NCIt"));
        
        selenium.type("id=ntTermIdentifier", "C800");
        clickAndWait("link=Look Up");
        assertTrue(selenium.isTextPresent("Synchronize Existing Intervention Term With NCIt"));
        assertTrue(selenium.isTextPresent("Message. Intervention with NCIt code 'C800' already present in CTRP, compare the values below and click 'Sync Term' to update the CTRP term with values from NCIt."));
        assertTrue(selenium.isTextPresent("Value in CTRP"));
        assertTrue(selenium.isTextPresent("Value in NCIt"));
        clickAndWait("link=Sync Term");
        
        assertTrue(selenium.isTextPresent("Message. Intervention C800 synchronized with NCI thesaurus."));        
    }
    
    
    /**
     * test create disease
     */
    @Test
    public void testEnterDiseaseTerm(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(By.xpath("//span[@class='btn_img']")).get(1)).perform();
        clickAndWait("xpath=//a[@href='manageTermscreateDisease.action']");
        assertTrue(selenium.isTextPresent("Enter New Disease/Condition Details"));
        
        // Test for validation errors
        clickAndWait("link=Save");
        
        assertTrue(selenium.isTextPresent("Message. Please correct the errors listed below and resubmit."));
        assertTrue(selenium.isTextPresent("NCI Identifier must be entered"));
        assertTrue(selenium.isTextPresent("Preferred Name must be entered"));
        assertTrue(selenium.isTextPresent("Menu Display Name must be entered"));
        
        // Test save successful with mandatory fields
        selenium.type("id=ntTermIdentifier", "CTEST123");
        selenium.type("id=code", "CDRTEST123");
        selenium.type("id=preferredName", "Preferred Name");
        selenium.type("id=menuDisplayName", "Menu display name");
        
        selenium.type("id=altName", "synonym1");
        clickAndWait("link=Add");
        
        Select sel = new Select(driver.findElement(By.id("alterNames")));
        assertEquals("synonym1", sel.getOptions().get(0).getText());

        clickAndWait("link=Save");
        assertTrue(selenium.isTextPresent("Message. New Disease CTEST123 added successfully."));
    }

    /**
     * test create intervention
     */
    @Test
    public void testImportDiseaseTerm(){
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(By.xpath("//span[@class='btn_img']")).get(1)).perform();
        clickAndWait("xpath=//a[@href='manageTermssearchDisease.action?searchStart=true']");
        assertTrue(selenium.isTextPresent("Import New Disease/Condiion From NCIt"));
        
        // Test for validation errors
        clickAndWait("link=Look Up");
        
        assertTrue(selenium.isTextPresent("Message. Enter valid NCIt Identifier."));
        
        // Test import non existing term
        selenium.type("id=ntTermIdentifier", "CTEST123");
        clickAndWait("link=Look Up");
        assertTrue(selenium.isTextPresent("Message. No disease with NCIt code 'CTEST123' found in NCI Thesaurus, try a different code."));
        
        // Test valid import
        selenium.type("id=ntTermIdentifier", "C4877");
        clickAndWait("link=Look Up");
        assertTrue(selenium.isTextPresent("Import New Disease/Condition From NCIt"));
        assertEquals("C4877",selenium.getValue("id=ntTermIdentifier"));
        assertEquals("Rectosigmoid Neoplasm",selenium.getValue("id=preferredName"));
        assertEquals("",selenium.getValue("id=code"));
        Select select = new Select(driver.findElement(By.id("alterNames")));
        List<WebElement> altNames = select.getOptions();
        assertEquals(7, altNames.size());
        assertEquals("C2956: Colorectal Neoplasm",selenium.getValue("id=parentTerms"));
        assertEquals("C7420: Malignant Rectosigmoid Neoplasm",selenium.getValue("id=childTerms"));
        
        selenium.type("id=code", "CDR123");    
        clickAndWait("link=Import");
        
        assertTrue(selenium.isTextPresent("Message. The CTRP system imported the disease C4877 successfully. It also imported the following parent/child terms:C2956, C7420."));
        
        //Test sync        
        action.moveToElement(driver.findElements(By.xpath("//span[@class='btn_img']")).get(1)).perform();
        clickAndWait("xpath=//a[@href='manageTermssearchDisease.action?searchStart=true']");
               
        selenium.type("id=ntTermIdentifier", "C4877");
        clickAndWait("link=Look Up");
        assertTrue(selenium.isTextPresent("Synchronize Existing Disease Term With NCIt"));
        assertTrue(selenium.isTextPresent("Message. Disease with NCIt code 'C4877' already present in CTRP, compare the values below and click 'Sync Term' to update the CTRP term with values from NCIt."));
        assertTrue(selenium.isTextPresent("Value in CTRP"));
        assertTrue(selenium.isTextPresent("Value in NCIt"));
        clickAndWait("link=Sync Term");
        
        assertTrue(selenium.isTextPresent("Disease/Condition with NCIt code 'C4877' synchronized from NCIt."));        
    }
        
}
