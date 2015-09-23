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

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;

public class CuratePersonTest extends AbstractCurateTest {
    private static final int DEFAULT_TEXT_COL_LENGTH = 50;
    

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testCurateNewPerson() throws Exception {
        /* create a new person via remote API. */
        String firstName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String lastName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Z', 10);
        Ii id = remoteCreateAndCatalog(createPerson(firstName, lastName));

        loginAsCurator();

        openEntityInboxPerson();

        // click on item to curate
        clickLinkInTable("person_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present

        String tempName = firstName.concat(firstName);
        selenium.type("curateEntityForm_person_firstName", tempName);
        assertEquals(tempName, selenium.getValue("curateEntityForm_person_firstName"));
        clickAndWait("reset_button");
        waitForTelecomFormsToLoad();

        // verify the presence of required indicator in create screen.
        verifyRequiredIndicators(true);

        assertEquals(firstName, selenium.getValue("curateEntityForm_person_firstName"));
        assertEquals(lastName, selenium.getValue("curateEntityForm_person_lastName"));

        verifyPostalAddress(ENTITYTYPE.person);
        verifyTelecom();

        savePersonAsActive(id);
    }

    private void verifyRequiredIndicators(boolean expectedValue) {
        //verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm.person.postalAddress.country");
        //verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_person_postalAddress_streetAddressLine");
        //verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_person_postalAddress_cityOrMunicipality");
        verifyPresenceOfRequiredIndicator(expectedValue, "person.postalAddress.stateOrProvince");
        //verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_person_postalAddress_postalCode");
    }

    public void testCuratePersonWithCRs() throws Exception {
        
        /* create a new person via remote API. */
        String firstName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String lastName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Z', 10);
        Ii id = remoteCreateAndCatalog(createPerson(firstName, lastName));

        PersonDTO orgDTO = getPersonService().getPerson(id);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        orgDTO.getTelecomAddress().getItem().add(email);
        getPersonService().updatePerson(orgDTO);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI(TelPhone.SCHEME_TEL + ":123-456-7890"));
        orgDTO.getTelecomAddress().getItem().add(phone);
        getPersonService().updatePerson(orgDTO);
        TelPhone fax = new TelPhone();
        fax.setValue(new URI(TelPhone.SCHEME_X_TEXT_FAX + ":234-567-8901"));
        orgDTO.getTelecomAddress().getItem().add(fax);
        getPersonService().updatePerson(orgDTO);
        TelPhone tty = new TelPhone();
        tty.setValue(new URI(TelPhone.SCHEME_X_TEXT_TEL + ":345-678-9012"));
        orgDTO.getTelecomAddress().getItem().add(tty);
        getPersonService().updatePerson(orgDTO);

        loginAsCurator();

        openEntityInboxPerson();

        // click on item to curate
        clickLinkInTable("person_id_" + id.getExtension());
        assertEquals(firstName, selenium.getValue("curateEntityForm_person_firstName"));
        assertEquals(lastName, selenium.getValue("curateEntityForm_person_lastName"));

        verifyPostalAddress(ENTITYTYPE.person);

        verifyTelecom();

        savePersonAsActive(id);
    }

    public void testCurateNewPersonThenCurateAfterRemoteUpdateToActive() throws Exception {
        
        Ii id = curateNewPersonThenCurateAfterRemoteUpdate();

        savePersonAsActive(id);
    }

    public void testCurateNewPersonThenCurateAfterRemoteUpdateToNullify() throws Exception {
        
        Ii id = curateNewPersonThenCurateAfterRemoteUpdate();

        selenium.select("curateEntityForm.person.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        // We are staying on the same page because of validation error.
        assertEquals("PO: Persons and Organizations - Person Details - Comparison", selenium.getTitle());
        
    }

    public void testCurateNewPersonThenCurateAfterRemoteUpdateToNullifyWithDuplicateId() throws Exception {
        
        /* create an org to serve as a duplicate */
        Ii dupId = createNewPersonThenCurateAsActive();
        Ii id = curateNewPersonThenCurateAfterRemoteUpdate();

        selenium.select("curateEntityForm.person.statusCode", "label=NULLIFIED");
        /* wait for in-browser js to execute via select's onchange event */
        pause(1000);
        selenium.isVisible("//div[@id='duplicateOfDiv']");
        clickAndWait("select_duplicate");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicatePersonForm_criteria_email", DEFAULT_EMAIL);

        /* search for dups */
        selenium.click("//a[@id='submitDuplicatePersonForm']/span/span");
        /* select record to use as duplicate */
        pause(2000);
        waitForElementById("selector_person_back_to_search_form_bottom", 30);
        waitForElementById("duplicatePersonSearchResults", 30);
        clickLinkInTable("mark_as_dup_" + dupId.getExtension());

        driver.switchTo().defaultContent();

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getPersonService().getPerson(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
            assertEquals(dupId.getExtension(), nullifiedEntities.values().iterator().next().getExtension());
        }
    }

    public void testCurateNewPersonThenCurateAfterRemoteUpdateToInactive() throws Exception {
        
        Ii id = curateNewPersonThenCurateAfterRemoteUpdate();

        saveAsInactive(id);
    }

    private Ii createNewPersonThenCurateAsActive() throws EntityValidationException, URISyntaxException, CurationException {
        /* create a new person via remote API. */
        String firstName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String lastName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Z', 10);
        Ii id = remoteCreateAndCatalog(createPerson(firstName, lastName));

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        openEntityInboxPerson();

        // click on item to curate
        clickLinkInTable("person_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        assertEquals(firstName, selenium.getValue("curateEntityForm_person_firstName"));
        assertEquals(lastName, selenium.getValue("curateEntityForm_person_lastName"));

        verifyPostalAddress(ENTITYTYPE.person);

        verifyTelecom();

        savePersonAsActive(id);
        return id;
    }

    private Ii curateNewPersonThenCurateAfterRemoteUpdate() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        Ii id = createNewPersonThenCurateAsActive();

        PersonDTO proposedState = remoteGetPerson(id);
        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        proposedState.setName(TestConvertHelper.convertToEnPn(newFirstName, null, newLastName, null, null));
        remoteUpdate(proposedState);

        openEntityInboxPerson();

        // click on item to curate
        clickLinkInTable("person_id_" + id.getExtension());
        waitForTelecomFormsToLoad();

        // method exits on certain page
        assertEquals("PO: Persons and Organizations - Person Details - Comparison", selenium.getTitle());

        return id;
    }

    private void openEntityInboxPerson() {
        selenium.open("/po-web/protected/curate/search/listPersons.action");
        clickAndWait("EntityInboxPerson");
    }

    private void saveAsInactive(Ii id) {
        selenium.select("curateEntityForm.person.statusCode", "label=INACTIVE");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();      
        assertEquals("PO: Persons and Organizations - Person Details", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='person_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullified(Ii id) {
        selenium.select("curateEntityForm.person.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();       
        assertEquals("PO: Persons and Organizations - Person Details", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='person_id_" + id.getExtension() + "']/span/span"));
    }
   

    private PersonDTO remoteGetPerson(Ii id) throws NullifiedEntityException {
        return getPersonService().getPerson(id);
    }

    private void remoteUpdate(PersonDTO proposedState) throws EntityValidationException {
        getPersonService().updatePerson(proposedState);
    }
}
