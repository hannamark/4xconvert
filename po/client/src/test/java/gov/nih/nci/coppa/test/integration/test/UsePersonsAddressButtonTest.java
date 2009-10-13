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
        waitForTelecomFormsToLoad();
        inputContactInfoForUSAndCan("abc@example.com", new String[]{"123","456","7890"}, 
                new String[]{"234","567","8901"}, new String[]{"345","678","9012"}, "http://www.example.com");
        // email, phone, fax, tty, url
        waitForElementById("email-remove-0", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("//div[@id='email-list']/ul/li[@id='email-entry-0']"));
       
        waitForElementById("phone-remove-0", 5);
        assertEquals("123-456-7890 | Remove", selenium.getText("//div[@id='phone-list']//div[@id='us_format_phone']/ul/li[@id='phone-entry-0']"));

        waitForElementById("fax-remove-0", 5);
        assertEquals("234-567-8901 | Remove", selenium.getText("//div[@id='fax-list']//div[@id='us_format_fax']/ul/li[@id='fax-entry-0']"));

        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", selenium.getText("//div[@id='tty-list']//div[@id='us_format_tty']/ul/li[@id='tty-entry-0']"));

        waitForElementById("url-remove-0", 5);
        assertEquals("http://www.example.com | Remove", selenium.getText("//div[@id='url-list']/ul/li[@id='url-entry-0']"));
        clickAndWaitButton("return_to_button");
        clickAndWaitButton("return_to_button");

        selenium.click("link=Manage Health Care Provider(s)");
        waitForPageToLoad();
        clickAndWaitButton("add_button");
      //add postal addresses
        addPostalAddressUsingPopup("456 jik", "suite xyz", "bogota", "n/a", "67890", "Colombia", 1);
        waitForTelecomFormsToLoad();
        inputContactInfo("abc@example.com", "1010 555 6666 7777", "1010 666 777 88888", "1010 777 888 9999", "http://www.example.com");
        // email, phone, fax, tty, url
        waitForElementById("email-remove-0", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("//div[@id='email-list']/ul/li[@id='email-entry-0']"));
       
        waitForElementById("phone-remove-0", 5);
        assertEquals("1010 555 6666 7777 | Remove", selenium.getText("//div[@id='phone-list']//div[@id='no_format_phone']/ul/li[@id='phone-entry-0']"));

        waitForElementById("fax-remove-0", 5);
        assertEquals("1010 666 777 88888 | Remove", selenium.getText("//div[@id='fax-list']//div[@id='no_format_fax']/ul/li[@id='fax-entry-0']"));

        waitForElementById("tty-remove-0", 5);
        assertEquals("1010 777 888 9999 | Remove", selenium.getText("//div[@id='tty-list']//div[@id='no_format_tty']/ul/li[@id='tty-entry-0']"));

        waitForElementById("url-remove-0", 5);
        assertEquals("http://www.example.com | Remove", selenium.getText("//div[@id='url-list']/ul/li[@id='url-entry-0']"));
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
