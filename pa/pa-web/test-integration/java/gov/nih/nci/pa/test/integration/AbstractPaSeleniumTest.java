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

import gov.nih.nci.coppa.test.integration.AbstractSeleneseTestCase;
import gov.nih.nci.pa.test.integration.util.TestProperties;

import org.junit.Ignore;

/**
 * Abstract base class for selenium tests.
 *
 * @author Abraham J. Evans-EL <aevanse@5amsolutions.com>
 */
@Ignore
public abstract class AbstractPaSeleniumTest extends AbstractSeleneseTestCase {

    @Override
    public void setUp() throws Exception {
        super.setSeleniumPort(Integer.toString(TestProperties.getSeleniumServerPort()));
        super.setServerHostname(TestProperties.getServerHostname());
        super.setServerPort(TestProperties.getServerPort());
        super.setBrowser(TestProperties.getSeleniumBrowser());
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        logoutUser();
        super.tearDown();
    }

    private void logoutUser() {
        openAndWait("/pa/login/logout.action");
    }

    protected void login(String username, String password) {
        selenium.open("/pa");
        verifyLoginPage();
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        clickAndWait("id=loginLink");
        assertTrue(selenium.isElementPresent("link=Logout"));
        verifyDisclaimerPage();
    }

    private void verifyLoginPage() {
        assertTrue(selenium.isTextPresent("Login"));
        assertTrue(selenium.isTextPresent("CONTACT US"));
        assertTrue(selenium.isTextPresent("PRIVACY NOTICE"));
        assertTrue(selenium.isTextPresent("DISCLAIMER"));
        assertTrue(selenium.isTextPresent("ACCESSIBILITY"));
        assertTrue(selenium.isTextPresent("SUPPORT"));
        clickAndWait("link=Login");
    }

    protected void verifyDisclaimerPage() {
        assertTrue(selenium.isElementPresent("id=acceptDisclaimer"));
        assertTrue(selenium.isElementPresent("id=rejectDisclaimer"));
    }

    protected void disclaimer(boolean accept) {
        verifyDisclaimerPage();
        if (accept) {
            clickAndWait("id=acceptDisclaimer");
            verifyHomePage();
        } else {
            clickAndWait("id=rejectDisclaimer");
            verifyLoginPage();
        }

    }

    protected void verifyHomePage() {
        assertTrue(selenium.isElementPresent("link=Logout"));
        assertTrue(selenium.isElementPresent("id=trialSearchMenuOption"));
        assertTrue(selenium.isElementPresent("id=inboxProcessingMenuOption"));
        assertTrue(selenium.isElementPresent("id=logoutMenuOption"));
    }

    /**
     * Verifies that the trial search page has been loaded.
     */
    protected void verifyTrialSearchPage() {
        assertTrue(selenium.isElementPresent("id=officialTitle"));
        assertTrue(selenium.isElementPresent("id=leadOrganizationId"));
        assertTrue(selenium.isElementPresent("id=identifierType"));
        assertTrue(selenium.isElementPresent("id=identifier"));
        assertTrue(selenium.isElementPresent("id=principalInvestigatorId"));
        assertTrue(selenium.isElementPresent("id=primaryPurpose"));
        assertTrue(selenium.isElementPresent("id=phaseCode"));
        assertTrue(selenium.isElementPresent("id=studyStatusCode"));
        assertTrue(selenium.isElementPresent("id=documentWorkflowStatusCode"));
        assertTrue(selenium.isElementPresent("id=studyMilestone"));
        assertTrue(selenium.isElementPresent("id=searchOnHold"));
        assertTrue(selenium.isElementPresent("id=studyLockedBy"));
        assertTrue(selenium.isElementPresent("id=submissionType"));
        assertTrue(selenium.isElementPresent("id=trialCategory"));
        assertTrue(selenium.isElementPresent("link=Search"));
        assertTrue(selenium.isElementPresent("link=Reset"));
    }

    /**
     * Verifies that a trial has been selected from the search results.
     * @param nciTrialIdentifier the NCI trial identifier i.e. NCI-2010-00001
     */
    protected void verifyTrialSelected(String nciTrialIdentifier) {
        assertTrue(selenium.isTextPresent(nciTrialIdentifier));
        assertTrue(selenium.isTextPresent("Trial Overview"));
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        assertTrue(selenium.isElementPresent("link=Trial History"));
        assertTrue(selenium.isElementPresent("link=Trial Milestones"));
        assertTrue(selenium.isElementPresent("link=On-hold Information"));
        assertTrue(selenium.isElementPresent("link=Manage Accrual Access"));
        assertTrue(selenium.isElementPresent("link=View TSR"));
        assertTrue(selenium.isElementPresent("link=Assign Ownership"));
        assertTrue(selenium.isTextPresent("Validation"));
        assertTrue(selenium.isElementPresent("link=Trial Related Documents"));
        assertTrue(selenium.isElementPresent("link=Trial Status"));
        assertTrue(selenium.isElementPresent("link=Trial Funding"));
        assertTrue(selenium.isElementPresent("link=Trial IND/IDE"));
        assertTrue(selenium.isElementPresent("link=Regulatory Information"));
        assertTrue(selenium.isElementPresent("link=Trial Validation"));
    }

    /**
     * Checks a trial out. Assumes that the trial has already been selected.
     */
    protected void checkOutTrial() {
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertTrue(selenium.isElementPresent("link=Check Out"));
        assertFalse(selenium.isElementPresent("link=Check In"));
        clickAndWait("link=Check Out");
        selenium.getConfirmation();
        assertTrue(selenium.isElementPresent("link=Check In"));
        assertFalse(selenium.isElementPresent("link=Check Out"));
    }
    
    /**
     * Checks a trial out. Assumes that the trial has already been selected.
     */
    protected void checkInTrial() {
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        clickAndWait("link=Trial Identification");
        assertFalse(selenium.isElementPresent("link=Check Out"));
        assertTrue(selenium.isElementPresent("link=Check In"));
        clickAndWait("link=Check In");
        selenium.getConfirmation();
        assertTrue(selenium.isElementPresent("link=Check Out"));
        assertFalse(selenium.isElementPresent("link=Check In"));
    }

    /**
     * Accepts a trial. Assumes that the trial has already been checked out.
     */
    protected void acceptTrial() {
        clickAndWait("link=Trial Validation");
        assertTrue(selenium.isElementPresent("link=Save"));
        assertTrue(selenium.isElementPresent("link=Accept"));
        assertTrue(selenium.isElementPresent("link=Reject"));
        clickAndWait("link=Accept");
    }

    /**
     * Verifies that the trial has been accepted.
     */
    protected void verifyTrialAccepted() {
        assertTrue(selenium.isTextPresent("Trial Overview"));
        assertTrue(selenium.isElementPresent("link=Trial Identification"));
        assertTrue(selenium.isElementPresent("link=Trial History"));
        assertTrue(selenium.isElementPresent("link=Trial Milestones"));
        assertTrue(selenium.isElementPresent("link=On-hold Information"));
        assertTrue(selenium.isElementPresent("link=Manage Accrual Access"));
        assertTrue(selenium.isElementPresent("link=View TSR"));
        assertTrue(selenium.isElementPresent("link=Assign Ownership"));

        assertTrue(selenium.isTextPresent("Administrative Data"));
        assertTrue(selenium.isElementPresent("link=General Trial Details"));
        assertTrue(selenium.isElementPresent("link=NCI Specific Information"));
        assertTrue(selenium.isElementPresent("link=Regulatory Information"));
        assertTrue(selenium.isElementPresent("link=Human Subject Safety"));
        assertTrue(selenium.isElementPresent("link=Trial IND/IDE"));
        assertTrue(selenium.isElementPresent("link=Trial Status"));
        assertTrue(selenium.isElementPresent("link=Trial Funding"));
        assertTrue(selenium.isElementPresent("link=Participating Sites"));
        assertTrue(selenium.isElementPresent("link=Collaborators"));
        assertTrue(selenium.isElementPresent("link=Trial Related Documents"));

        assertTrue(selenium.isTextPresent("Scientific Data"));
        assertTrue(selenium.isElementPresent("link=Trial Description"));
        assertTrue(selenium.isElementPresent("link=Design Details"));
        assertTrue(selenium.isElementPresent("link=Outcome Measures"));
        assertTrue(selenium.isElementPresent("link=Eligibility Criteria"));
        assertTrue(selenium.isElementPresent("link=Disease/Condition"));
        assertTrue(selenium.isElementPresent("link=Markers"));
        assertTrue(selenium.isElementPresent("link=Interventions"));
        assertTrue(selenium.isElementPresent("link=Arms"));
        assertTrue(selenium.isElementPresent("link=Sub-groups"));

        assertTrue(selenium.isTextPresent("Completion"));
        assertTrue(selenium.isElementPresent("link=Abstraction Validation"));
    }


    public void loginAsAbstractor() {
        login("abstractor-ci", "Coppa#12345");
        disclaimer(false);
        login("abstractor-ci", "Coppa#12345");
        disclaimer(true);
    }

    protected boolean isLoggedIn() {
        return selenium.isElementPresent("link=Logout") && !selenium.isElementPresent("link=Login");
    }

    protected void openAndWait(String url) {
        selenium.open(url);
        waitForPageToLoad();
    }
}
