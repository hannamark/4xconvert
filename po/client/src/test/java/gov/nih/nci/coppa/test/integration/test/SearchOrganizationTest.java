package gov.nih.nci.coppa.test.integration.test;

/**
 * SearchOrganizationTest -
 *   Adds an organization and performs a Name, Contact, Address and Id search for the added
 *     organization.
 *   Additionally this will test - No rows returned and nothing selected tests
 * @author Bill Mason
 *
 */
public class SearchOrganizationTest extends OrganizationWebTest {


    public void testSearchOrganization(){

        loginAsCurator();
        openCreateOrganization();
        addOrganization();
        openSearchOrganization();
        verifySearchForm();
        searchByName();
        searchByAddress();
        searchByPoId();
        noRowsReturnedTest();
        nothingSelectedTest();

      }
    
    private void nothingSelectedTest() {
        clear();
        clickAndWaitButton("submitSearchOrganizationForm");
        assertTrue("At least one criterion message is missing", selenium.isTextPresent("At least one criterion must be provided"));
        assertTrue("Nothing found message is missing", selenium.isTextPresent("Nothing found to display"));
        assertTrue("No items found message is missing", selenium.isTextPresent("No items found"));
    }

    private void noRowsReturnedTest() {
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine", "jf yfnfufigkmi");
        clickAndWaitButton("submitSearchOrganizationForm");
        assertTrue("Nothing found message is missing", selenium.isTextPresent("Nothing found to display"));
        assertTrue("No items found message is missing", selenium.isTextPresent("No items found"));
    }

    private void searchByPoId() {
        selenium.type("searchOrganizationForm_criteria_organization_id", poId);
        selenium.select("searchOrganizationForm_criteria_organization_statusCode", "label=PENDING");
        clickAndWaitButton("submitSearchOrganizationForm");
        verify();
    }

    private void searchByAddress() {
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine", "400 First Street");
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_deliveryAddressLine", "160 Delivery Ave");
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_cityOrMunicipality", "Atlanta");
        selenium.type("criteria.organization.postalAddress.stateOrProvince", "GA");
        selenium.type("searchOrganizationForm_criteria_organization_postalAddress_postalCode", "30345");
        selenium.select("searchOrganizationForm.criteria.organization.postalAddress.country", "label=United States");
        clickAndWaitButton("submitSearchOrganizationForm");
        verify();
    }


    private void verifySearchForm() {
        assertTrue("PoId field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_id"));
        assertTrue("Status code field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_statusCode"));
        assertTrue("Organization name field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_name"));
        assertTrue("Address 1 field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine"));
        assertTrue("Address 2 field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_deliveryAddressLine"));
        assertTrue("City field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_cityOrMunicipality"));
        assertTrue("State field is missing",selenium.isElementPresent("criteria.organization.postalAddress.stateOrProvince"));
        assertTrue("Postal code field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_postalCode"));
        assertTrue("Country field is missing",selenium.isElementPresent("searchOrganizationForm.criteria.organization.postalAddress.country"));
    }


}
