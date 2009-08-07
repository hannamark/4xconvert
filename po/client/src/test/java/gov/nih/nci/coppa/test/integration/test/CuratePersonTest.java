package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.remoteapi.RemoteApiUtils;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CuratePersonTest extends AbstractPoWebTest {
    private static final int DEFAULT_TEXT_COL_LENGTH = 50;

    private final Map<Ii, PersonDTO> catalogPersons = new HashMap<Ii, PersonDTO>();
    private PersonEntityServiceRemote personService;

    protected PersonEntityServiceRemote getPersonService() {
        return personService;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        if (personService == null) {
            personService = RemoteServiceHelper.getPersonEntityService();
        }
    }

    public void testCurateNewPerson() throws Exception {
        /* create a new person via remote API. */
        String firstName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String lastName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Z', 10);
        Ii id = remoteCreateAndCatalog(create(firstName, lastName));

        loginAsCurator();

        openEntityInboxPerson();

        // click on item to curate
        clickAndWaitButton("person_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        assertEquals(firstName, selenium.getValue("curateEntityForm_person_firstName"));
        assertEquals(lastName, selenium.getValue("curateEntityForm_person_lastName"));

        verifyPostalAddress(ENTITYTYPE.person);
        verifyTelecom();

        savePersonAsActive(id);
    }

    public void testCuratePersonWithCRs() throws Exception {
        /* create a new person via remote API. */
        String firstName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String lastName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Z', 10);
        Ii id = remoteCreateAndCatalog(create(firstName, lastName));

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
        clickAndWaitButton("person_id_" + id.getExtension());
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

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getPersonService().getPerson(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
        }
    }

    public void testCurateNewPersonThenCurateAfterRemoteUpdateToNullifyWithDuplicateId() throws Exception {
        /* create an org to serve as a duplicate */
        Ii dupId = createNewPersonThenCurateAsActive();
        Ii id = curateNewPersonThenCurateAfterRemoteUpdate();

        selenium.select("curateEntityForm.person.statusCode", "label=NULLIFIED");
        /* wait for in-browser js to execute via select's onchange event */
        Thread.sleep(1000);
        selenium.isVisible("//div[@id='duplicateOfDiv']");
        clickAndWaitButton("select_duplicate");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicatePersonForm_criteria_email", DEFAULT_EMAIL);

        /* search for dups */
        selenium.click("//a[@id='submitDuplicatePersonForm']/span/span");
        /* wait for results to load */
        waitForElementById("mark_as_dup_" + dupId.getExtension(), 30);
        /* select record to use as duplicate */
        clickAndWaitButton("mark_as_dup_" + dupId.getExtension());

        selenium.selectFrame("relative=parent");

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

    private Ii createNewPersonThenCurateAsActive() throws EntityValidationException, URISyntaxException {
        /* create a new person via remote API. */
        String firstName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String lastName = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Z', 10);
        Ii id = remoteCreateAndCatalog(create(firstName, lastName));

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        openEntityInboxPerson();

        // click on item to curate
        selenium.click("//a[@id='person_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();
        assertEquals(firstName, selenium.getValue("curateEntityForm_person_firstName"));
        assertEquals(lastName, selenium.getValue("curateEntityForm_person_lastName"));

        verifyPostalAddress(ENTITYTYPE.person);

        verifyTelecom();

        savePersonAsActive(id);
        return id;
    }

    private Ii curateNewPersonThenCurateAfterRemoteUpdate() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException {
        Ii id = createNewPersonThenCurateAsActive();

        PersonDTO proposedState = remoteGetPerson(id);
        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        proposedState.setName(RemoteApiUtils.convertToEnPn(newFirstName, null, newLastName, null, null));
        remoteUpdate(proposedState);

        openEntityInboxPerson();

        // click on item to curate
        selenium.click("//a[@id='person_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();

        // method exits on certain page
        verifyEquals("PO: Persons and Organizations - Person Details - Comparison", selenium.getTitle());

        return id;
    }

    private void openEntityInboxPerson() {
        selenium.open("/po-web/protected/curate/search/listPersons.action");
        selenium.click("id=EntityInboxPerson");
        waitForPageToLoad();
    }

    private void saveAsInactive(Ii id) {
        selenium.select("curateEntityForm.person.statusCode", "label=INACTIVE");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        selenium.getConfirmation();
        verifyEquals("PO: Persons and Organizations - Entity Inbox - Person", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='person_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullified(Ii id) {
        selenium.select("curateEntityForm.person.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        selenium.getConfirmation();
        verifyEquals("PO: Persons and Organizations - Entity Inbox - Person", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='person_id_" + id.getExtension() + "']/span/span"));
    }

    private Ii remoteCreateAndCatalog(PersonDTO org) throws EntityValidationException {
        Ii id = getPersonService().createPerson(org);
        org.setIdentifier(id);
        catalogPersons.put(id, org);
        return id;
    }

    private PersonDTO create(String firstName, String lastName) throws URISyntaxException {
        return create(firstName, null, lastName, null, null, RemoteApiUtils.createAd("123 abc ave.", null, "mycity", "VA", "12345", "USA"));
    }

    private PersonDTO create(String firstName, String middleName, String lastName, String prefix, String suffix, Ad postalAddress) throws URISyntaxException {
        PersonDTO person = new PersonDTO();
        person.setName(RemoteApiUtils.convertToEnPn(firstName, middleName, lastName, prefix, suffix));

        person.setPostalAddress(postalAddress);
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        person.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        person.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        person.getTelecomAddress().getItem().add(url);
        return person;
    }

    private PersonDTO remoteGetPerson(Ii id) throws NullifiedEntityException {
        return getPersonService().getPerson(id);
    }

    private void remoteUpdate(PersonDTO proposedState) throws EntityValidationException {
        getPersonService().updatePerson(proposedState);
    }
}
