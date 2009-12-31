package gov.nih.nci.coppa.test.integration.test;

/**
 * Verifies PO-1440. Tests for US/Canada and non-US/non-Canada Phone/Fax/TTY formatting.
 */
public class ContactFormattingTest extends AbstractPoWebTest {

    public void testCreateOrganizationContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCreateMode(ENTITYTYPE.organization);
    }

    public void testCreatePersonContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCreateMode(ENTITYTYPE.person);
    }

    public void testCurateOrganizationContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCurateMode(ENTITYTYPE.organization);
    }

    public void testCuratePersonContactFormatting() throws Exception {
        loginAsCurator();
        checkUsOrCanadaFormatsInCurateMode(ENTITYTYPE.person);
    }

    private void checkUsOrCanadaFormatsInCreateMode(ENTITYTYPE entityType) throws InterruptedException {
        if (entityType.equals(ENTITYTYPE.organization)) {
            openCreateOrganization();
        } else {
            openCreatePerson();
        }

        String countryLocatorId = "curateEntityForm." + entityType.name() + ".postalAddress.country";
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", countryLocatorId);
        checkPhoneFaxTtyFormats("fax", countryLocatorId);
        checkPhoneFaxTtyFormats("tty", countryLocatorId);
    }

    private void checkUsOrCanadaFormatsInCurateMode(ENTITYTYPE entityType) throws InterruptedException {
        if (entityType.equals(ENTITYTYPE.organization)) {
            createOrganization("ACTIVE", "ORGANIZATION - PENDING", getAddress(), "sample@example.com", "703-111-2345",
                    "703-111-1234", "703-111-1234", "http://www.example.com");
        } else {
            openCreatePerson();
            createPerson("ACTIVE", "po-1440", "po-1440", "1440", "po-1440", "III", getAddress(), "sample@example.com",
                    "703-111-2345", "http://www.example.com", "703-111-1234");
        }

        String entityId = selenium.getText("wwctrl_" + entityType.name() + ".id");
        assertNotEquals("null", entityId);

        String urlAccessPersonOrOrgScreen = "po-web/protected/" + entityType.name() + "/curate/start.action?" + entityType.name() + ".id=" + entityId;
        openAndWait(urlAccessPersonOrOrgScreen);

        String countryLocatorId = "curateEntityForm." + entityType.name() + ".postalAddress.country";
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", countryLocatorId);
        checkPhoneFaxTtyFormats("fax", countryLocatorId);
        checkPhoneFaxTtyFormats("tty", countryLocatorId);

        // Check the formatting on org/person roles.
        if (entityType.equals(ENTITYTYPE.organization)) {
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Research Organization(s)", "Research Organization Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Health Care Facility", "Health Care Facility Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Organizational Contact(s)", "Organizational Contact Information");
        } else {
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Clinical Research Staff(s)", "Clinical Research Staff Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Health Care Provider(s)", "Health Care Provider Information");
            checkPersonOrgRoleContactFormatting(entityType, entityId, "link=Manage Organizational Contact(s)", "Organizational Contact Information");
        }
    }

    private void checkPersonOrgRoleContactFormatting(ENTITYTYPE entityType, String entityId, String roleLinkText, String roleTitleText) throws InterruptedException {
        openAndWait("po-web/protected/" + entityType.name() + "/curate/start.action?" + entityType.name() + ".id=" + entityId);
        clickAndWait(roleLinkText);
        assertTrue(selenium.isTextPresent(roleTitleText));

        clickAndWait("add_button");
        waitForTelecomFormsToLoad();

        // By default, US/Canada formatting is off.
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", false);
        checkPhoneFaxTtyFormats("fax", false);
        checkPhoneFaxTtyFormats("tty", false);

        // add us address. should display us/canada format.
        addPostalAddressUsingPopup("Address One", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        selenium.selectFrame("relative=parent");
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", true);
        checkPhoneFaxTtyFormats("fax", true);
        checkPhoneFaxTtyFormats("tty", true);

        // add non-us/canada address. should display non-US formatted phone/fax/tty.
        addPostalAddressUsingPopup("Non US Address", "suite xyz", "City One", "State One", "67890", "Thailand", 1);
        selenium.selectFrame("relative=parent");
        Thread.sleep(100);
        checkPhoneFaxTtyFormats("phone", false);
        checkPhoneFaxTtyFormats("fax", false);
        checkPhoneFaxTtyFormats("tty", false);
    }

    private void checkPhoneFaxTtyFormats(String type, boolean usOrCanadaFormatExpected) throws InterruptedException {
        String usFormatDivId = "id=us_format_" + type;
        String noFormatDivId = "id=no_format_" + type;

        if (usOrCanadaFormatExpected) {
            checkDivVisibility(usFormatDivId, noFormatDivId);
        } else {
            checkDivVisibility(noFormatDivId, usFormatDivId);
        }
    }

    private void checkPhoneFaxTtyFormats(String type, String countryLocatorId) throws InterruptedException {
        String usFormatDivId = "id=us_format_" + type;
        String noFormatDivId = "id=no_format_" + type;

        selectCountry(countryLocatorId, "label=United States");
        checkDivVisibility(usFormatDivId, noFormatDivId);

        selectCountry(countryLocatorId, "label=Barbados");
        checkDivVisibility(noFormatDivId, usFormatDivId);

        selectCountry(countryLocatorId, "label=Canada");
        checkDivVisibility(usFormatDivId, noFormatDivId);

        selectCountry(countryLocatorId, "label=Taiwan");
        checkDivVisibility(noFormatDivId, usFormatDivId);
    }

    private void selectCountry(String countryLocatorId, String countryLabelText) throws InterruptedException {
        selenium.select(countryLocatorId, countryLabelText);
        Thread.sleep(100);
    }

    private void checkDivVisibility(String visibleDivId, String invisibleDivId) throws InterruptedException {
        Thread.sleep(100);
        assertTrue(selenium.isVisible(visibleDivId));
        assertFalse(selenium.isVisible(invisibleDivId));
    }
}
