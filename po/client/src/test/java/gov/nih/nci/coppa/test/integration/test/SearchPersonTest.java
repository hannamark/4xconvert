package gov.nih.nci.coppa.test.integration.test;

/**
 * Adds a Person and performs a Name, Contact, Address and Id search for the added 
 *     organization.
 *   Additionally this will test - No rows returned and nothing selected tests
 * @author Bill Mason
 *
 */
public class SearchPersonTest extends AbstractPoWebTest {
	private String lastName="lastName"+Long.toString(System.currentTimeMillis());
	private String firstName = "Jakson";
	private String poId = null;
	
	public void testSearchPerson(){
		loginAsCurator();
        openCreatePerson();
        addPerson();
        openSearchPerson();
        verifySearchForm();
        searchByName();
        searchByEmail();
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
    	selenium.type("searchPersonForm_criteria_org", "jf yfnfufigkmi");
    	clickAndWaitButton("submitSearchOrganizationForm");
    	assertTrue(selenium.isTextPresent("Nothing found to display"));
    	assertTrue(selenium.isTextPresent("No items found"));
	}

	private void searchByEmail() {
    	selenium.type("searchPersonForm_criteria_email", "email@emial.com");
    	clickAndWaitButton("submitSearchOrganizationForm");
		verify();
	}

	private void searchByName() {
		selenium.type("searchPersonForm_criteria_firstName", firstName);
		selenium.type("searchPersonForm_criteria_lastName", lastName);
    	clickAndWaitButton("submitSearchOrganizationForm");
    	verify();
    }

	private void verify() {
		int thirdColumn = 3;
		int row = getRow(lastName, thirdColumn);
		if (row == -1){
			fail("Did not find " + lastName + " in search results");
		}else{
			setPoId(row);
			assertEquals(poId, selenium.getTable("row."+row+".0"));
			assertEquals(firstName, selenium.getTable("row."+row+".2"));
			assertEquals(lastName, selenium.getTable("row."+row+".3"));
			assertEquals("PENDING", selenium.getTable("row."+row+".8"));
			assertEquals("NONE", selenium.getTable("row."+row+".9"));
			clear();
		}
	}


	private void setPoId(int row) {
		if (poId == null) {
			poId = selenium.getTable("row."+row+".0");
		}
	}

	private void verifySearchForm() {
        assertTrue("Person first name is missing", selenium.isElementPresent("searchPersonForm_criteria_firstName"));
        assertTrue("Person last name is missing", selenium.isElementPresent("searchPersonForm_criteria_lastName"));
        assertTrue("Person email is missing", selenium.isElementPresent("searchPersonForm_criteria_email"));
        assertTrue("Organization Affiliation is missing", selenium.isElementPresent("searchPersonForm_criteria_org"));
        assertTrue("Investigator CTEP Identifier is missing", selenium.isElementPresent("searchPersonForm_criteria_ctepId"));
	}
    
    private void addPerson(){
        waitForElementById("emailEntry_value", 10); //email is in ajax div. wait for it.
    	selenium.select("curateEntityForm.person.statusCode", "label=PENDING");
		selenium.type("curateEntityForm_person_prefix", "Dr");
		selenium.type("curateEntityForm_person_firstName", firstName);
		selenium.type("curateEntityForm_person_middleName", "L");
		selenium.type("curateEntityForm_person_lastName", lastName);
		selenium.type("curateEntityForm_person_suffix", "III");
		selenium.type("curateEntityForm_person_postalAddress_streetAddressLine", "123 Main Street");
		selenium.type("curateEntityForm_person_postalAddress_deliveryAddressLine", "40 5th Street");
        selenium.type("curateEntityForm_person_postalAddress_postalCode", "20147");
        selenium.select("curateEntityForm.person.postalAddress.country", "label=United States");
        selenium.type("curateEntityForm_person_postalAddress_cityOrMunicipality", "Ashburn");
        selenium.select("curateEntityForm.person.postalAddress._selectStateOrProvince", "label=VA(VIRGINIA)");
        
		selenium.type("emailEntry_value", "email@emial.com");
		selenium.click("email-add");
        waitForElementById("email-entry-0", 5);
        
		selenium.type("phoneEntry_value", "703-234-2345");
		selenium.click("phone-add");
        waitForElementById("phone-entry-0", 5);
        
		selenium.type("urlEntry_value", "http://www.site.com");
		selenium.click("url-add");
        waitForElementById("url-entry-0", 5);
        
		selenium.type("faxEntry_value", "703-909-1234");
		selenium.click("fax-add");
        waitForElementById("fax-entry-0", 5);
        
		clickAndWaitSaveButton();
		assertTrue("Person was successfully created!", selenium.isTextPresent("Person was successfully created"));
	}
    
	private void clear() {
		openSearchPerson();
 	}
}
