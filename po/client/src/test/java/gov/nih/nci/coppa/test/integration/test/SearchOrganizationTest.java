package gov.nih.nci.coppa.test.integration.test;

/**
 * SearchOrganizationTest - 
 *   Adds an organization and performs a Name, Contact, Address and Id search for the added 
 *     organization.
 *   Additionally this will test - No rows returned and nothing selected tests
 * @author Bill Mason
 *
 */
public class SearchOrganizationTest extends AbstractPoWebTest {
	private String orgName= "orgName"+Long.toString(System.currentTimeMillis());
	private String poId = null;
	
	public void testSearchOrganization(){
		
		loginAsCurator();
        openCreateOrganization();
        addOrganization();
        openSearchOrganization();
        verifySearchForm();
        searchByName();
        searchByContactInformation();
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
		selenium.type("searchOrganizationForm.criteria.organization.postalAddress.stateOrProvince", "GA");
		selenium.type("searchOrganizationForm_criteria_organization_postalAddress_postalCode", "30345");
		selenium.select("searchOrganizationForm.criteria.organization.postalAddress.country", "label=United States");
		clickAndWaitButton("submitSearchOrganizationForm");
		verify();
    }

	private void searchByContactInformation() {
		selenium.type("searchOrganizationForm_criteria_emailEntry_value", "emailAddress@aool.com");
		selenium.type("searchOrganizationForm_criteria_urlEntry_value", "http://sample.url.org");
		selenium.type("searchOrganizationForm_criteria_phoneEntry_value", "899-090-0987");
		selenium.type("searchOrganizationForm_criteria_faxEntry_value", "123-fax-0908");
		clickAndWaitButton("submitSearchOrganizationForm");
		verify();
	}

	private void searchByName() {
		selenium.type("searchOrganizationForm_criteria_organization_name", orgName);
		clickAndWaitButton("submitSearchOrganizationForm");
    	verify();
    }

	private void verify() {
		int secondColumn = 1;
		int row = getRow(orgName, secondColumn);
		if (row == -1){
			fail("Did not find " + orgName + " in search results");
		}else{
			setPoId(row);
			assertEquals(poId, selenium.getTable("row."+row+".0"));
			assertEquals(orgName, selenium.getTable("row."+row+".1"));
			assertEquals("PENDING", selenium.getTable("row."+row+".2"));
			assertEquals("NONE", selenium.getTable("row."+row+".3"));
			clear();
		}
	}

	private void setPoId(int row) {
		if (poId == null) {
			poId = selenium.getText("//table[@id='row']/tbody/tr[" + row+ "]/td[1]");
			System.out.println("po id = " + selenium.getTable("row."+row+".0"));
		}
	}

	private void verifySearchForm() {
		assertTrue("PoId field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_id"));
		assertTrue("Status code field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_statusCode"));
		assertTrue("Organization name field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_name"));
		assertTrue("Street address field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_streetAddressLine"));
		assertTrue("Delivery address field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_deliveryAddressLine"));
		assertTrue("City field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_cityOrMunicipality"));
		assertTrue("State field is missing",selenium.isElementPresent("searchOrganizationForm.criteria.organization.postalAddress.stateOrProvince"));
		assertTrue("Postal code field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_organization_postalAddress_postalCode"));
		assertTrue("Country field is missing",selenium.isElementPresent("searchOrganizationForm.criteria.organization.postalAddress.country"));
		assertTrue("Email field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_emailEntry_value"));
		assertTrue("URL field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_urlEntry_value"));
		assertTrue("Phone field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_phoneEntry_value"));
		assertTrue("Fax field is missing",selenium.isElementPresent("searchOrganizationForm_criteria_faxEntry_value"));
	}
    
    private void addOrganization(){
		selenium.select("curateEntityForm.organization.statusCode", "label=PENDING");
		selenium.type("curateEntityForm_organization_name", orgName);
		selenium.type("curateEntityForm_organization_postalAddress_streetAddressLine", "400 First Street");
		selenium.type("curateEntityForm_organization_postalAddress_deliveryAddressLine", "160 Delivery Ave.");
		selenium.type("curateEntityForm_organization_postalAddress_cityOrMunicipality", "Atlanta");
		selenium.type("curateEntityForm.organization.postalAddress.stateOrProvince", "GA");
		selenium.type("curateEntityForm_organization_postalAddress_postalCode", "30345");
		selenium.select("curateEntityForm.organization.postalAddress.country", "label=United States");
		selenium.type("emailEntry_value", "emailAddress@aool.com");
		selenium.click("email-add");
		selenium.type("phoneEntry_value", "899-090-0987");
		selenium.click("phone-add");
		selenium.type("faxEntry_value", "123-fax-0908");
		selenium.click("fax-add");
		selenium.type("ttyEntry_value", "tty-number");
		selenium.click("tty-add");
		selenium.type("urlEntry_value", "http://sample.url.org");
		selenium.click("url-add");
		clickAndWaitSaveButton();
		assertTrue("Organization was successfully created!", selenium.isTextPresent("Organization was successfully created"));
	}
    
	private void clear() {
		openSearchOrganization();
 	}
}
