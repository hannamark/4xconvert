package gov.nih.nci.coppa.test.integration.test;

public class OrganizationWebTest extends AbstractPoWebTest {
    protected String orgName = "orgName" + Long.toString(System.currentTimeMillis());
    protected String poId = null;

    private void setPoId(int row) {
        if (poId == null) {
            poId = selenium.getText("//table[@id='row']/tbody/tr[" + row + "]/td[1]");
        }
    }

    protected void verify() {
        verify(true);
    }

    protected void verify(boolean clear) {
        int secondColumn = 1;
        int row = getRow(orgName, secondColumn);
        if (row == -1) {
            fail("Did not find " + orgName + " in search results");
        } else {
            setPoId(row);
            assertEquals(poId, selenium.getTable("row." + row + ".0"));
            assertEquals(orgName, selenium.getTable("row." + row + ".1"));
            assertEquals("PENDING", selenium.getTable("row." + row + ".2"));
            assertEquals("NONE", selenium.getTable("row." + row + ".3"));
            if (clear) {
                clear();
            }
        }
    }

    protected void searchByName(boolean clear) {
        selenium.type("searchOrganizationForm_criteria_organization_name", orgName);
        clickAndWaitButton("submitSearchOrganizationForm");
        verify(clear);
    }

    protected void searchByName() {
        searchByName(true);
    }

    protected void addOrganization() {
        selenium.select("curateEntityForm.organization.statusCode", "label=PENDING");
        selenium.type("curateEntityForm_organization_name", orgName);
        selenium.type("curateEntityForm_organization_postalAddress_streetAddressLine", "400 First Street");
        selenium.type("curateEntityForm_organization_postalAddress_deliveryAddressLine", "160 Delivery Ave.");
        selenium.type("curateEntityForm_organization_postalAddress_postalCode", "30345");
        selenium.select("curateEntityForm.organization.postalAddress.country", "label=United States");
        waitForElementById("organization.postalAddress.stateOrProvince", 10);
        selenium.type("curateEntityForm_organization_postalAddress_cityOrMunicipality", "Atlanta");
        selenium.select("organization.postalAddress.stateOrProvince", "label=GA (GEORGIA)");

        selenium.type("emailEntry_value", "emailAddress@example.com");
        selenium.click("email-add");
        waitForElementById("email-entry-0", 5);

        selenium.type("phoneEntry_value", "899-090-0987");
        selenium.click("phone-add");
        waitForElementById("phone-entry-0", 5);

        selenium.type("faxEntry_value", "123-fax-0908");
        selenium.click("fax-add");
        waitForElementById("fax-entry-0", 5);

        selenium.type("ttyEntry_value", "tty-number");
        selenium.click("tty-add");
        waitForElementById("tty-entry-0", 5);

        selenium.type("urlEntry_value", "http://sample.url.org");
        selenium.click("url-add");
        waitForElementById("url-entry-0", 5);

        clickAndWaitSaveButton();
        assertTrue("Organization was successfully created!", selenium
                .isTextPresent("Organization was successfully created"));
    }

    protected void clear() {
        openSearchOrganization();
    }
}
