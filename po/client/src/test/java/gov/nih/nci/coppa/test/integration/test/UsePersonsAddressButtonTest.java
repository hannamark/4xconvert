package gov.nih.nci.coppa.test.integration.test;

public class UsePersonsAddressButtonTest extends AbstractPoWebTest {

    /**
     * Verifies PO-719 via UI.
     */
    public void testUsePersonsAddressButtonWorks() {
        openCreatePerson();
        createPerson();

        selenium.click("link=Manage Organizational Contact(s)");
        waitForPageToLoad();
        clickAndWaitButton("add_button");
        addPostalAddress(getAddress());
        clickAndWaitButton("return_to_button");
        clickAndWaitButton("return_to_button");

        selenium.click("link=Manage Health Care Provider(s)");
        waitForPageToLoad();
        clickAndWaitButton("add_button");
        addPostalAddress(getAddress());
        clickAndWaitButton("return_to_button");
        clickAndWaitButton("return_to_button");

        selenium.click("link=Manage Clinical Research Staff(s)");
        waitForPageToLoad();
        clickAndWaitButton("add_button");
        addPostalAddress(getAddress());
        clickAndWaitButton("return_to_button");
        clickAndWaitButton("return_to_button");
    }

    private void addPostalAddress(Address address) {
        waitForElementById("add_address", 5);
        clickAndWaitButton("add_address");

        selenium.selectFrame("popupFrame");
        clickAndWaitButton("copy_parent_postalAddress");
        /* wait for results to load */
        selenium.selectFrame("relative=parent");
        waitForElementById("postalAddress0", 30);

        assertTrue(selenium.isTextPresent("Address 1"));

        assertEquals(address.getCountry(), selenium.getText("wwctrl_address.country1"));
        assertEquals(address.getStreetAddressLine(), selenium.getText("wwctrl_address.streetAddressLine1"));
        assertEquals(address.getDeliveryAddressLine(), selenium.getText("wwctrl_address.deliveryAddressLine1"));
        assertEquals(address.getCityOrMunicipality(), selenium.getText("wwctrl_address.cityOrMunicipality1"));
        assertEquals(address.getStateOrProvince(), selenium.getText("wwctrl_address.stateOrProvince1"));
        assertEquals(address.getPostalCode(), selenium.getText("wwctrl_address.postalCode1"));
    }
}
