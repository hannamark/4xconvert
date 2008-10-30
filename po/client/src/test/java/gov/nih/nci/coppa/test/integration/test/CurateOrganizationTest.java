package gov.nih.nci.coppa.test.integration.test;

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
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CurateOrganizationTest extends AbstractPoWebTest {
    private static final String DEFAULT_URL = "http://default.example.com";

    private static final String DEFAULT_EMAIL = "default@example.com";

    private static final int DEFAULT_TEXT_COL_LENGTH = 100;

    private final Map<Ii, OrganizationDTO> catalogOrgs = new HashMap<Ii, OrganizationDTO>();
    private OrganizationEntityServiceRemote orgService;

    protected OrganizationEntityServiceRemote getOrgService() {
        return orgService;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        if (orgService == null) {
            orgService = RemoteServiceHelper.getOrganizationEntityService();
        }
    }

    public void testCurateNewOrg() throws Exception {
        /* create a new org via remote API. */
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String abbrv = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'X', 10);
        String desc = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'W', 10);
        Ii id = remoteCreateAndCatalog(create(name, abbrv, desc));

        loginAsCurator();

        openEntityInboxOrganization();

        // click on item to curate
        clickAndWaitButton("org_id_" + id.getExtension());
        assertEquals(name, selenium.getValue("curateOrgForm_organization_name"));
        assertEquals(abbrv, selenium.getValue("curateOrgForm_organization_abbreviatedName"));
        assertEquals(desc, selenium.getValue("curateOrgForm_organization_description"));

        verifyPostalAddress();

        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();

        saveAsActive(id);
    }

    public void testCurateOrgWithCRs() throws Exception {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String abbrv = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'X', 10);
        String desc = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'W', 10);
        Ii id = remoteCreateAndCatalog(create(name, abbrv, desc));

        OrganizationDTO orgDTO = getOrgService().getOrganization(id);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        orgDTO.getTelecomAddress().getItem().add(email);
        getOrgService().updateOrganization(orgDTO);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI(TelPhone.SCHEME_TEL + ":123-456-7890"));
        orgDTO.getTelecomAddress().getItem().add(phone);
        getOrgService().updateOrganization(orgDTO);
        TelPhone fax = new TelPhone();
        fax.setValue(new URI(TelPhone.SCHEME_X_TEXT_FAX + ":234-567-8901"));
        orgDTO.getTelecomAddress().getItem().add(fax);
        getOrgService().updateOrganization(orgDTO);
        TelPhone tty = new TelPhone();
        tty.setValue(new URI(TelPhone.SCHEME_X_TEXT_TEL + ":345-678-9012"));
        orgDTO.getTelecomAddress().getItem().add(tty);
        getOrgService().updateOrganization(orgDTO);

        loginAsCurator();

        openEntityInboxOrganization();

        // click on item to curate
        clickAndWaitButton("org_id_" + id.getExtension());
        assertEquals(name, selenium.getValue("curateOrgForm_organization_name"));
        assertEquals(abbrv, selenium.getValue("curateOrgForm_organization_abbreviatedName"));
        assertEquals(desc, selenium.getValue("curateOrgForm_organization_description"));

        verifyPostalAddress();

        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();

        saveAsActive(id);
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToActive() throws Exception {
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        saveAsActive(id);
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullify() throws Exception {
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getOrgService().getOrganization(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
        }
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullifyWithDuplicateId() throws Exception {
        /* create an org to serve as a duplicate */
        Ii dupId = createNewOrgThenCurateAsActive();
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        selenium.select("curateOrgForm_organization_statusCode", "label=NULLIFIED");
        /* wait for in-browser js to execute via select's onchange event */
        Thread.sleep(1000);
        selenium.isVisible("//div[@id='duplicateOfDiv']");
        clickAndWaitButton("select_duplicate");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicateOrganizationForm_criteria_organization_id", dupId.getExtension());

        /* search for dups */
        selenium.click("//a[@id='submitDuplicateOrganizationForm']/span/span");
        /* wait for results to load */
        waitForElementById("mark_as_dup_" + dupId.getExtension(), 30);
        /* select record to use at duplicate */
        clickAndWaitButton("mark_as_dup_" + dupId.getExtension());

        selenium.selectFrame("relative=parent");

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getOrgService().getOrganization(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
        }
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToInactive() throws Exception {
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        saveAsInactive(id);
    }

    private Ii createNewOrgThenCurateAsActive() throws EntityValidationException, URISyntaxException {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        String abbrv = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'X', 10);
        String desc = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'W', 10);
        Ii id = remoteCreateAndCatalog(create(name, abbrv, desc));

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        openEntityInboxOrganization();

        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        assertEquals(name, selenium.getValue("curateOrgForm_organization_name"));
        assertEquals(abbrv, selenium.getValue("curateOrgForm_organization_abbreviatedName"));
        assertEquals(desc, selenium.getValue("curateOrgForm_organization_description"));

        verifyPostalAddress();

        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();

        saveAsActive(id);
        return id;
    }

    private Ii curateNewOrgThenCurateAfterRemoteUpdate() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException {
        Ii id = createNewOrgThenCurateAsActive();

        OrganizationDTO proposedState = remoteGetOrganization(id);
        String newCrDescription = "a realistic description";
        proposedState.setDescription(RemoteApiUtils.convertToSt(newCrDescription));
        remoteUpdate(proposedState);

        openEntityInboxOrganization();

        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();

        String crDescription = selenium.getText("wwctrl_curateOrgCrForm_cr_description");
        assertEquals(crDescription, crDescription);
        selenium.type("curateOrgForm_organization_description", crDescription);

        // method exits on certain page
        verifyEquals("PO: Persons and Organizations - Organization Details - Comparison", selenium.getTitle());

        return id;
    }

    private void openEntityInboxOrganization() {
        selenium.open("/po-web/protected/curate/search/listOrgs.action");
        selenium.click("id=EntityInboxOrganization");
        waitForPageToLoad();
    }

    private void saveAsActive(Ii id) {
        selenium.select("curateOrgForm_organization_statusCode", "label=ACTIVE");
        clickAndWaitSaveButton();
        verifyEquals("PO: Persons and Organizations - Entity Inbox - Organization", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsInactive(Ii id) {
        selenium.select("curateOrgForm_organization_statusCode", "label=INACTIVE");
        clickAndWaitSaveButton();
        verifyEquals("PO: Persons and Organizations - Entity Inbox - Organization", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullified(Ii id) {
        selenium.select("curateOrgForm_organization_statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        verifyEquals("PO: Persons and Organizations - Entity Inbox - Organization", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void verifyPostalAddress() {
        assertEquals("123 abc ave.", selenium.getValue("curateOrgForm_organization_postalAddress_streetAddressLine"));
        assertEquals("", selenium.getValue("curateOrgForm_organization_postalAddress_deliveryAddressLine"));
        assertEquals("mycity", selenium.getValue("curateOrgForm_organization_postalAddress_cityOrMunicipality"));
        assertEquals("", selenium.getValue("curateOrgForm_organization_postalAddress_stateOrProvince"));
        assertEquals("12345", selenium.getValue("curateOrgForm_organization_postalAddress_postalCode"));
    }

    private void verifyEmail() {
        waitForElementById("email-remove-0", 5);
        assertEquals(DEFAULT_EMAIL + " | Remove", selenium.getText("email-entry-0"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address must be set"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc@example.com");
        selenium.click("email-add");
        waitForElementById("email-remove-1", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("email-entry-1"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc.com");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address is not a well-formed email address"));
    }

    private void verifyPhone() {
        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone must be set"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "123-456-7890");
        selenium.click("phone-add");
        waitForElementById("phone-remove-0", 5);
        assertEquals("123-456-7890 | Remove", selenium.getText("phone-entry-0"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "1234567890123456789012345678901");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone length must be between 0 and 30"));
    }

    private void verifyFax() {
        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax must be set"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "234-567-8901");
        selenium.click("fax-add");
        waitForElementById("fax-remove-0", 5);
        assertEquals("234-567-8901 | Remove", selenium.getText("fax-entry-0"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "1234567890123456789012345678901");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax length must be between 0 and 30"));
    }

    private void verifyTty() {
        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY must be set"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "345-678-9012");
        selenium.click("tty-add");
        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", selenium.getText("tty-entry-0"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "1234567890123456789012345678901");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY length must be between 0 and 30"));
    }

    private void verifyUrl() {
        waitForElementById("url-remove-0", 5);
        assertEquals(DEFAULT_URL + " | Remove | Visit...", selenium.getText("url-entry-0"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL must be set"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "http://www.example.com");
        selenium.click("url-add");
        waitForElementById("url-remove-1", 5);
        assertEquals("http://www.example.com | Remove | Visit...", selenium.getText("url-entry-1"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "abc.com");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL is not well formed"));
    }

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException {
        Ii id = getOrgService().createOrganization(org);
        org.setIdentifier(id);
        catalogOrgs.put(id, org);
        return id;
    }

    private OrganizationDTO create(String name, String abbrv, String desc) throws URISyntaxException {
        OrganizationDTO org = new OrganizationDTO();
        org.setName(RemoteApiUtils.convertToEnOn(name));
        org.setAbbreviatedName(RemoteApiUtils.convertToEnOn(abbrv));
        org.setDescription(RemoteApiUtils.convertToSt(desc));
        org.setPostalAddress(RemoteApiUtils.createAd("123 abc ave.", null, "mycity", null, "12345", "USA"));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        org.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        org.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        org.getTelecomAddress().getItem().add(url);
        return org;
    }

    private OrganizationDTO remoteGetOrganization(Ii id) throws NullifiedEntityException {
        return getOrgService().getOrganization(id);
    }

    private void remoteUpdate(OrganizationDTO proposedState) throws EntityValidationException {
        getOrgService().updateOrganization(proposedState);
    }
}
