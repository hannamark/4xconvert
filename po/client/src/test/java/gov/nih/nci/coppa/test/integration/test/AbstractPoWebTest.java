package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class AbstractPoWebTest extends AbstractSeleneseTestCase {
    protected static final String DEFAULT_URL = "http://default.example.com";

    protected static final String DEFAULT_EMAIL = "default@example.com";
    private static int PAGE_SIZE = 20;
    private static final Logger LOG = Logger.getLogger(AbstractPoWebTest.class);

    protected static final String SELECT_A_COUNTRY = "--Select a Country--";

    protected static final String SELECT_A_STATUS = "--Select a Status--";

    protected static final String COUNTRY_MUST_BE_SET = "Country must be set";

    protected static final String STATUS_MUST_BE_SET = "Status must be set";

    private static boolean initLoginHasRun = false;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (!initLoginHasRun) {
            initLogin();
        }
    }

    private void initLogin() {
        loginAsCurator();
        openCreateOrganization();
        selenium.select("curateEntityForm.organization.statusCode", "label=ACTIVE");
        selenium.type("curateEntityForm_organization_name", "Test Organization");

        // add postal address info
        inputAddressInfo(getAddress(), "curateEntityForm", "organization");
        // add contact info
        inputContactInfo("sample@example.com", "703-111-2345", "703-111-1234", "703-111-1234", "http://www.example.com");

        // save the organization
        clickAndWaitSaveButton();
        if (!isLoggedIn()) {
            selenium.type("j_username", "curator");
            selenium.type("j_password", "pass");
            clickAndWait("id=enableEnterSubmit");
            assertTrue(selenium.isElementPresent("link=Logout"));
        }
        clickAndWait("link=Logout");
        initLoginHasRun = true;
    }

    protected void login(String username, String password) {
        selenium.open("/po-web");
        assertTrue(selenium.isTextPresent("Login"));
        assertTrue(selenium.isTextPresent("CONTACT US"));
        clickAndWait("link=Login");
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        clickAndWait("id=enableEnterSubmit");
        assertTrue(selenium.isElementPresent("link=Logout"));
        assertTrue(selenium.isElementPresent("id=EntityInboxOrganization"));
        assertTrue(selenium.isElementPresent("id=SearchOrganization"));
        assertTrue(selenium.isElementPresent("id=CreateOrganization"));
        assertTrue(selenium.isElementPresent("id=EntityInboxPerson"));
        assertTrue(selenium.isElementPresent("id=SearchPerson"));
        assertTrue(selenium.isElementPresent("id=CreatePerson"));
    }

    public void loginAsCurator() {
        login("curator", "pass");
    }

    protected boolean isLoggedIn() {
        return selenium.isElementPresent("link=Logout") && !selenium.isElementPresent("link=Login");
    }

    protected void clickAndWaitSaveButton() {
        clickAndWaitButton("save_button");
    }

    protected void clickAndWaitButton(String buttonId) {
        clickAndWait("//a[@id='" + buttonId + "']/span/span");
    }

    protected void clickAnchor(String anchorId) {
        selenium.click("//a[@id='" + anchorId + "']");
    }

    protected void openCreateOrganization() {
        goHome();
        selenium.click("CreateOrganization");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();
    }

    protected void waitForTelecomFormsToLoad() {
        waitForElementById("emailEntry_value", 10);
        waitForElementById("phoneEntry_value", 10);
        waitForElementById("faxEntry_value", 10);
        waitForElementById("ttyEntry_value", 10);
        waitForElementById("urlEntry_value", 10);
    }

    private void goHome() {
        if (!isLoggedIn()) {
            loginAsCurator();
        }
        openAndWait("/po-web/protected/home.action");
    }

    protected void openAndWait(String url) {
        selenium.open(url);
        waitForPageToLoad();
    }

    protected void openCreatePerson() {
        goHome();
        selenium.click("CreatePerson");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();
    }

    protected void openSearchPerson() {
        goHome();
        clickAndWait("SearchPerson");
    }

    protected void openSearchOrganization() {
        goHome();
        clickAndWait("SearchOrganization");
    }

    /**
     * Searches in the given column for the given text
     *
     * @param text - string to search for
     * @param column - table column to search in
     * @return the row number where the text was found. -1 if not found
     */
    protected int getRow(String text, int column) {
        String tblValue = null;
        for (int row = 1;; row++) {
            tblValue = null;
            try {
                tblValue = selenium.getTable("row." + row + "." + column);
            } catch (RuntimeException e) {
                if (tblValue == null) {
                    // there are no rows on the page
                    return -1;
                }
                LOG.info("problem looking for " + text + " at (" + row + "," + column + ") Stopped at : " + tblValue);
                throw e;
            }
            if (text.equalsIgnoreCase(tblValue)) {
                return row;
            }

            if (row % PAGE_SIZE == 0) {
                // Moving to next page
                // this will fail once there are no more pages and the text parameter is not found
                try {
                    clickAndWait("link=Next");
                } catch (Exception e1) {
                    return -1;
                }
                pause(2000);
                row = 0;
            }
        }
    }

    /**
     * Verify the Telecom fields behave properly
     */
    protected void verifyTelecom() {
        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();
    }

    private void verifyEmail() {
        waitForElementById("email-remove-0", 5);
        assertEquals(DEFAULT_EMAIL + " | Remove", selenium.getText("email-entry-0"));

        waitForElementById("emailEntry_value", 5);
        clear("emailEntry_value");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address must be set"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc@example.com");
        selenium.click("email-add");
        waitForElementById("email-remove-1", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("email-entry-1"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "example.com");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address is not a well-formed email address"));

        clear("emailEntry_value");
    }

    private void clear(String locator) {
        selenium.type(locator, "");
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

        clear("phoneEntry_value");
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

        clear("faxEntry_value");
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

        clear("ttyEntry_value");
    }

    private void verifyUrl() {
        waitForElementById("url-remove-0", 5);
        assertEquals(DEFAULT_URL + " | Remove", selenium.getText("url-entry-0"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL must be set"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "http://www.example.com");
        selenium.click("url-add");
        waitForElementById("url-remove-1", 5);
        assertEquals("http://www.example.com | Remove", selenium.getText("url-entry-1"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "example.com");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL is not well formed"));

        clear("urlEntry_value");
    }

    protected void verifyPostalAddress(ENTITYTYPE type) {
        assertEquals("123 abc ave.", selenium.getValue("curateEntityForm_" + type.name()
                + "_postalAddress_streetAddressLine"));
        assertEquals("", selenium.getValue("curateEntityForm_" + type.name() + "_postalAddress_deliveryAddressLine"));
        assertEquals("mycity", selenium.getValue("curateEntityForm_" + type.name()
                + "_postalAddress_cityOrMunicipality"));
        assertEquals("VA", selenium.getValue(type.name() + ".postalAddress.stateOrProvince"));
        assertEquals("12345", selenium.getValue("curateEntityForm_" + type.name() + "_postalAddress_postalCode"));
    }

    protected void createPerson(String status, String prefix, String fName, String mName, String lName, String suffix,
            Address address, String email, String phone, String url, String fax) {
        // wait for ajax page content to load
        waitForElementById("emailEntry_value", 10);
        waitForElementById("phoneEntry_value", 10);
        waitForElementById("urlEntry_value", 10);
        waitForElementById("faxEntry_value", 10);
        // add person info
        selenium.select("curateEntityForm.person.statusCode", "label=" + status);
        selenium.type("curateEntityForm_person_prefix", prefix);
        selenium.type("curateEntityForm_person_firstName", fName);
        selenium.type("curateEntityForm_person_middleName", mName);
        selenium.type("curateEntityForm_person_lastName", lName);
        selenium.type("curateEntityForm_person_suffix", suffix);
        // add postal addresss info
        inputAddressInfo(address, "curateEntityForm", "person");
        // add contact info
        inputContactInfo(email, phone, fax, null, url);
        //save the person
        clickAndWaitSaveButton();
        //verify person created message
        assertTrue("Success message is missing", selenium.isTextPresent("Person was successfully created"));
    }

    protected void createOrganization(String status, String name, Address address, String email, String phone,
            String fax, String tty, String url) {
        openCreateOrganization();
        selenium.select("curateEntityForm.organization.statusCode", "label=" + status);
        selenium.type("curateEntityForm_organization_name", name);

        // add postal address info
        inputAddressInfo(address, "curateEntityForm", "organization");

        // add contact info
        inputContactInfo(email, phone, fax, tty, url);

        // save the organization
        clickAndWaitSaveButton();
        // verify organization created message
        assertTrue("Success message is missing", selenium.isTextPresent("Organization was successfully created"));
    }

    protected void createGenericOrganizationalContact(String status, String title, String type, Address address,
            String email, String phone, String fax, String tty, String url, boolean verify) {
        waitForTelecomFormsToLoad();
        selenium.type("curateRoleForm_role_title", title);
        selenium.select("curateRoleForm.role.status", "label=" + status);
        selenium.select("curateRoleForm.role.type", "label=" + type);

        // add postal address info
        if (address != null) {
            inputAddressInfoPopup(address);
            waitForTelecomFormsToLoad();
        }

        // add contact info
        inputContactInfo(email, phone, fax, tty, url);

        // save the OC
        clickAndWaitSaveButton();
        if (verify) {
            // verify OC created message
            assertTrue("Success message is missing", selenium
                    .isTextPresent("Organizational Contact was successfully created"));
        }
    }

    protected void inputAddressInfo(Address address, String formName, String objectType) {
        selenium.select(formName + "." + objectType + ".postalAddress.country", "label=" + address.getCountry());
        waitForElementById(objectType + ".postalAddress.stateOrProvince", 10);
        selenium.type(formName + "_" + objectType + "_postalAddress_streetAddressLine", address.getStreetAddressLine());
        selenium.type(formName + "_" + objectType + "_postalAddress_deliveryAddressLine", address
                .getDeliveryAddressLine());
        selenium
        .type(formName + "_" + objectType + "_postalAddress_cityOrMunicipality", address.getCityOrMunicipality());
        selenium.select(objectType + ".postalAddress.stateOrProvince", "value=" + address.getStateOrProvince());
        selenium.type(formName + "_" + objectType + "_postalAddress_postalCode", address.getPostalCode());
    }

    protected void inputAddressInfoPopup(Address address) {
        clickAndWaitButton("add_address");
        selenium.selectFrame("popupFrame");
        selenium.select("postalAddressForm.address.country", "label=" + address.getCountry());
        waitForElementById("address.stateOrProvince", 10);
        selenium.type("postalAddressForm_address_streetAddressLine", address.getStreetAddressLine());
        selenium.type("postalAddressForm_address_deliveryAddressLine", address.getDeliveryAddressLine());
        selenium.type("postalAddressForm_address_cityOrMunicipality", address.getCityOrMunicipality());
        selenium.select("address.stateOrProvince", "value=" + address.getStateOrProvince());
        selenium.type("postalAddressForm_address_postalCode", address.getPostalCode());
        clickAndWaitButton("submitPostalAddressForm");
        selenium.selectFrame("relative=up");
    }

    protected void inputContactInfo(String email, String phone, String fax, String tty, String url) {

        inputEmailAndUrl(email, url);

        if (StringUtils.isNotBlank(phone)) {
            selenium.type("phoneEntry_value", phone);
            selenium.click("phone-add");
            waitForElementById("phone-entry-0", 5);
        }
        if (StringUtils.isNotBlank(fax)) {
            selenium.type("faxEntry_value", fax);
            selenium.click("fax-add");
            waitForElementById("fax-entry-0", 5);
        }
        if (StringUtils.isNotBlank(tty)) {
            selenium.type("ttyEntry_value", tty);
            selenium.click("tty-add");
            waitForElementById("tty-entry-0", 5);
        }

    }

    protected void inputContactInfoForUSAndCan(String email, String[] phone, String[] fax, String[] tty, String url) {
        inputEmailAndUrl(email, url);
        inputForTel(phone, "phone");
        inputForTel(fax, "fax");
        inputForTel(tty, "tty");
    }

    private void inputEmailAndUrl(String email, String url) {
        if (StringUtils.isNotBlank(email)) {
            selenium.type("emailEntry_value", email);
            selenium.click("email-add");
            waitForElementById("email-entry-0", 5);
        }

        if (StringUtils.isNotBlank(url)) {
            selenium.type("urlEntry_value", url);
            selenium.click("url-add");
            waitForElementById("url-entry-0", 5);
        }
    }

    protected void inputForTel(String[]tel, String type) {
        if (tel.length >= 3) {
            selenium.type(type + "Entry_part1", tel[0]);
            selenium.type(type + "Entry_part2", tel[1]);
            selenium.type(type + "Entry_part3", tel[2]);
            if (tel.length == 4) {
                selenium.type(type + "Entry_part4", tel[3]);
            }
            selenium.click("//div[@id='us_format_"+type+"']//a[@id='" + type + "-add']");
            waitForElementById(type +"-entry-0", 5);
        }
    }

    protected void inputNonUSFormatTel(String tel, String type) {
        selenium.type(type + "Entry_value", tel);
        selenium.click("//div[@id='no_format_"+type+"']//a[@id='" + type + "-add']");
         waitForElementById(type +"-entry-0", 5);
    }

    public Address getAddress() {
        return new Address("123 Main Street", "40 5th Street", "Ashburn", "VA", "20147", "United States");
    }

    public Address getForeignAddress() {
        return new Address("123 Main Street", "40 5th Street", "Bogata", null, "20147", "Columbia");
    }

    protected String createPerson() {
        String lname = "lastName" + System.currentTimeMillis();
        createPerson("PENDING", "Dr", "Jakson", "L", lname, "III",
                getAddress(), "sample@example.com", "703-111-2345", "http://www.example.com", "703-111-1234");
        return lname;
    }

    protected String createOrganization() {
        String name = "orgName" + System.currentTimeMillis();
        createOrganization("ACTIVE", name, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        return name;
    }

    protected void accessManageClinicalResearchStaffScreen() {
        clickAndWait("link=Manage Clinical Research Staff(s)");
        assertTrue(selenium.isTextPresent("Clinical Research Staff Information"));
    }

    protected void accessManageIdentifiedOrganizationScreen() {
        clickAndWait("link=Manage Identified Organization(s)");
        assertTrue(selenium.isTextPresent("Identified Organization Information"));
    }

    protected void accessManageOrganizationalContactScreen() {
        clickAndWait("link=Manage Organizational Contact(s)");
        assertTrue(selenium.isTextPresent("Organizational Contact Information"));
    }
    
    protected void accessManageIdentifiedPersonScreen() {
        clickAndWait("link=Manage Other Person Identifier(s)");
        assertTrue(selenium.isTextPresent("Other Person Identifier Information"));
    }
    
    protected void accessManageResearchOrganizationScreen() {
        clickAndWait("link=Manage Research Organization(s)");
        assertTrue(selenium.isTextPresent("Research Organization Information"));
    }

    protected void accessManageHealthCareProviderScreen() {
        clickAndWait("link=Manage Health Care Provider(s)");
        assertTrue(selenium.isTextPresent("Health Care Provider Information"));
    }

    protected void selectOrganizationScoper(String orgId, String orgName) {
        clickAndWaitButton("select_scoper");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicateOrganizationForm_criteria_organization_id", orgId);
        /* search for dups */
        selenium.click("//a[@id='submitDuplicateOrganizationForm']/span/span");
        /* wait for results to load */
        waitForElementById("mark_as_dup_" + orgId, 10);
        /* select record to use at duplicate */
        clickAndWaitButton("mark_as_dup_" + orgId);
        selenium.selectFrame("relative=parent");
        assertEquals(orgName + " (" + orgId + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id"));
    }

    /**
     * Use this to add postal addresses using the popup.
     * Verifies values exist somewhere on the page after the popup save button is pressed.
     * Unfortunately, we're unable to control the order the postalAdresses are displayed (backed by a HashSet),
     * so we can only verify the existence of text on the page.
     *
     * *** SPECIFY UNIQUE VALUES ON THE PAGE
     */
    protected void addPostalAddressUsingPopup(String street1, String street2, String city, String stateCode, String zip,
            String countryName, int totalNumberOfAddressesAfterAdd) {
                assertTrue(totalNumberOfAddressesAfterAdd > 0);
                waitForElementById("add_address", 10);
                clickAndWaitButton("add_address");
                selenium.selectFrame("popupFrame");
                selenium.select("postalAddressForm.address.country", "label="+countryName);
                //might need to wait for //div[@id=address.div_stateOrProvince] to reload
                waitForElementById("address.stateOrProvince", 10);

                selenium.type("postalAddressForm_address_streetAddressLine", street1);
                selenium.type("postalAddressForm_address_deliveryAddressLine", street2);
                selenium.type("postalAddressForm_address_cityOrMunicipality", city);
                if (selenium.isElementPresent("css=input[name=address.stateOrProvince]")){
                    selenium.type("address.stateOrProvince", stateCode);

                } else if (selenium.isElementPresent("css=select[name=address.stateOrProvince]")) {
                    selenium.select("address.stateOrProvince", "value="+stateCode);
                } else {
                    fail("Unable to determine the form element.type of address.stateOrProvince");
                }
                selenium.type("postalAddressForm_address_postalCode", zip);
                selenium.click("//a[@id='submitPostalAddressForm']/span/span");
                selenium.selectFrame("relative=parent");
                totalNumberOfAddressesAfterAdd--;
                waitForElementById("postalAddress"+totalNumberOfAddressesAfterAdd, 10);
                selenium.isTextPresent(street1);
                selenium.isTextPresent(street2);
                selenium.isTextPresent(city);
                selenium.isTextPresent(stateCode);
                selenium.isTextPresent(zip);
                selenium.isTextPresent(countryName);

            }

    public enum ENTITYTYPE {
        person, organization;
    }

    protected Ii createRemoteOrg(String orgName) throws EntityValidationException, NamingException, URISyntaxException, CurationException {

        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(TestConvertHelper.convertToEnOn(orgName));
        dto.setPostalAddress(TestConvertHelper.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        dto.getTelecomAddress().getItem().add(url);
        Ii id = RemoteServiceHelper.getOrganizationEntityService().createOrganization(dto);
        dto.setIdentifier(id);
        return id;
    }

    protected void savePersonAsActive(Ii id) {
        selenium.select("curateEntityForm.person.statusCode", "label=ACTIVE");
        clickAndWaitSaveButton();
        assertEquals("PO: Persons and Organizations - Entity Inbox - Person", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='person_id_" + id.getExtension() + "']/span/span"));
    }

    protected void saveOrganizationAsActive(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=ACTIVE");
        clickAndWaitSaveButton();
        assertTrue("Organization was successfully created!", selenium
                .isTextPresent("Organization was successfully created"));
        assertEquals("PO: Persons and Organizations - Entity Inbox - Person", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='organization_id_" + id.getExtension() + "']/span/span"));
    }

}
