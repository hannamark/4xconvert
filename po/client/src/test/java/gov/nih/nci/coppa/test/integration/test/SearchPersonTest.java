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
    	selenium.type("searchPersonForm_criteria_person_postalAddress_streetAddressLine", "jf yfnfufigkmi");
    	clickAndWaitButton("submitSearchOrganizationForm");
    	assertTrue(selenium.isTextPresent("Nothing found to display"));
    	assertTrue(selenium.isTextPresent("No items found"));
	}

	private void searchByPoId() {
    	selenium.type("searchPersonForm_criteria_person_id", poId);
		selenium.select("searchPersonForm_criteria_person_statusCode", "label=PENDING");
    	clickAndWaitButton("submitSearchOrganizationForm");
		verify();
	}

	private void searchByAddress() {
    	selenium.type("searchPersonForm_criteria_person_postalAddress_streetAddressLine", "123 Main Street");
		selenium.type("searchPersonForm_criteria_person_postalAddress_deliveryAddressLine", "40 5th Street");
		selenium.type("searchPersonForm_criteria_person_postalAddress_cityOrMunicipality", "ashburn");
		selenium.type("searchPersonForm.criteria.person.postalAddress.stateOrProvince", "va");
		selenium.type("searchPersonForm_criteria_person_postalAddress_postalCode", "20147");
		selenium.select("searchPersonForm.criteria.person.postalAddress.country", "label=United States");	
    	clickAndWaitButton("submitSearchOrganizationForm");
		verify();
    }

	private void searchByContactInformation() {
    	selenium.type("searchPersonForm_criteria_emailEntry_value", "email@emial.com");
		selenium.type("searchPersonForm_criteria_urlEntry_value", "http://www.site.com");
		selenium.type("searchPersonForm_criteria_phoneEntry_value", "703-234-2345");
		selenium.type("searchPersonForm_criteria_faxEntry_value", "703-909-1234");
    	clickAndWaitButton("submitSearchOrganizationForm");
		verify();
	}

	private void searchByName() {
		selenium.type("searchPersonForm_criteria_person_prefix", "Dr");
		selenium.type("searchPersonForm_criteria_person_firstName", firstName);
		selenium.type("searchPersonForm_criteria_person_middleName", "L");
		selenium.type("searchPersonForm_criteria_person_lastName", lastName);
    	selenium.type("searchPersonForm_criteria_person_suffix", "ii");
    	clickAndWaitButton("submitSearchOrganizationForm");
    	verify();
    }

	private void verify() {
		int thirdColumn = 2;
		int row = getRow(lastName, thirdColumn);
		if (row == -1){
			fail("Did not find " + lastName + " in search results");
		}else{
			setPoId(row);
			assertEquals(poId, selenium.getTable("row."+row+".0"));
			assertEquals(firstName, selenium.getTable("row."+row+".1"));
			assertEquals(lastName, selenium.getTable("row."+row+".2"));
			assertEquals("PENDING", selenium.getTable("row."+row+".3"));
			assertEquals("NONE", selenium.getTable("row."+row+".4"));
			clear();
		}
	}


	private void setPoId(int row) {
		if (poId == null) {
			poId = selenium.getTable("row."+row+".0");
		}
	}

	private void verifySearchForm() {
        assertTrue("Person id is missing", selenium.isElementPresent("searchPersonForm_criteria_person_id"));
        assertTrue("Person status is missing", selenium.isElementPresent("searchPersonForm_criteria_person_statusCode"));
        assertTrue("Person prefix is missing", selenium.isElementPresent("searchPersonForm_criteria_person_prefix"));
        assertTrue("Person first name is missing", selenium.isElementPresent("searchPersonForm_criteria_person_firstName"));
        assertTrue("Person middle name is missing", selenium.isElementPresent("searchPersonForm_criteria_person_middleName"));
        assertTrue("Person last name is missing", selenium.isElementPresent("searchPersonForm_criteria_person_lastName"));
        assertTrue("Person suffix is missing", selenium.isElementPresent("searchPersonForm_criteria_person_suffix"));
        assertTrue("Person email is missing", selenium.isElementPresent("searchPersonForm_criteria_emailEntry_value"));
        assertTrue("Person url entry is missing", selenium.isElementPresent("searchPersonForm_criteria_urlEntry_value"));
        assertTrue("Person phone is missing", selenium.isElementPresent("searchPersonForm_criteria_phoneEntry_value"));
        assertTrue("Person fax is missing", selenium.isElementPresent("searchPersonForm_criteria_faxEntry_value"));
        assertTrue("Person Street address is missing", selenium.isElementPresent("searchPersonForm_criteria_person_postalAddress_streetAddressLine"));
        assertTrue("Person delivery address is missing", selenium.isElementPresent("searchPersonForm_criteria_person_postalAddress_deliveryAddressLine"));
        assertTrue("Person city is missing", selenium.isElementPresent("searchPersonForm_criteria_person_postalAddress_cityOrMunicipality"));
        assertTrue("Person state is missing", selenium.isElementPresent("searchPersonForm.criteria.person.postalAddress.stateOrProvince"));
        assertTrue("Person postal code is missing", selenium.isElementPresent("searchPersonForm_criteria_person_postalAddress_postalCode"));
        assertTrue("Person country is missing", selenium.isElementPresent("searchPersonForm.criteria.person.postalAddress.country"));
	}
    
    private void addPerson(){
    	selenium.select("curateEntityForm.person.statusCode", "label=PENDING");
		selenium.type("curateEntityForm_person_prefix", "Dr");
		selenium.type("curateEntityForm_person_firstName", firstName);
		selenium.type("curateEntityForm_person_middleName", "L");
		selenium.type("curateEntityForm_person_lastName", lastName);
		selenium.type("curateEntityForm_person_suffix", "III");
		selenium.type("curateEntityForm_person_postalAddress_streetAddressLine", "123 Main Street");
		selenium.type("curateEntityForm_person_postalAddress_deliveryAddressLine", "40 5th Street");
		selenium.type("curateEntityForm_person_postalAddress_cityOrMunicipality", "Ashburn");
		selenium.type("curateEntityForm.person.postalAddress.stateOrProvince", "va");
		selenium.type("curateEntityForm_person_postalAddress_postalCode", "20147");
		selenium.select("curateEntityForm.person.postalAddress.country", "label=United States");
		selenium.type("emailEntry_value", "email@emial.com");
		selenium.click("email-add");
		selenium.type("phoneEntry_value", "703-234-2345");
		selenium.click("phone-add");
		selenium.type("urlEntry_value", "http://www.site.com");
		selenium.click("url-add");
		selenium.type("faxEntry_value", "703-909-1234");
		selenium.click("fax-add");
		clickAndWaitSaveButton();
		assertTrue("Person was successfully created!", selenium.isTextPresent("Person was successfully created"));
	}
    
	private void clear() {
		openSearchPerson();
 	}
}
