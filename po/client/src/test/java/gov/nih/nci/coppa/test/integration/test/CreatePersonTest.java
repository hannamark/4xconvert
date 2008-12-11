package gov.nih.nci.coppa.test.integration.test;

public class CreatePersonTest extends AbstractPoWebTest {
	
	private String lastName="lastName"+Long.toString(System.currentTimeMillis());
	
	public void testPerson(){
        loginAsCurator();
		openCreatePerson();
		verifyDefaultValidationErrors();
		addPerson();
	}
    /**
     * Verifies PO-618, PO-619 via UI
     */
    private void verifyDefaultValidationErrors(){


        clickAndWaitSaveButton();
        assertEquals("PENDING", selenium.getSelectedValue("curateEntityForm.person.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateEntityForm.person.statusCode"));
        verifyDefaultFieldErrorsMessages();
        
        selenium.select("curateEntityForm.person.statusCode", "label=--Select a Status--");
        clickAndWaitSaveButton();
        assertEquals("", selenium.getSelectedValue("curateEntityForm.person.statusCode"));
        assertEquals("--Select a Status--", selenium.getSelectedLabel("curateEntityForm.person.statusCode"));
        assertEquals("Status must be set", selenium.getText("//div[@id='wwerr_curateEntityForm.person.statusCode']/div"));
        verifyDefaultFieldErrorsMessages();
    }

    private void addPerson(){
    	selenium.select("curateEntityForm.person.statusCode", "label=PENDING");
		selenium.type("curateEntityForm_person_prefix", "Mr");
		selenium.type("curateEntityForm_person_firstName", "Jakson");
		selenium.type("curateEntityForm_person_middleName", "L");
		selenium.type("curateEntityForm_person_lastName", lastName);
		selenium.type("curateEntityForm_person_suffix", "III");
		selenium.type("curateEntityForm_person_postalAddress_streetAddressLine", "123 Main Street");
		selenium.type("curateEntityForm_person_postalAddress_deliveryAddressLine", "40 5th Street");
		selenium.type("curateEntityForm_person_postalAddress_cityOrMunicipality", "Ashburn");
		selenium.type("curateEntityForm.person.postalAddress.stateOrProvince", "va");
		selenium.type("curateEntityForm_person_postalAddress_postalCode", "20147");
		selenium.select("curateEntityForm.person.postalAddress.country", "label=United States");
		selenium.type("emailEntry_value", "sample@emial.com");
		selenium.click("email-add");
		selenium.type("phoneEntry_value", "703-111-2345");
		selenium.click("phone-add");
		selenium.type("urlEntry_value", "http://www.createperson.com");
		selenium.click("url-add");
		selenium.type("faxEntry_value", "703-111-1234");
		selenium.click("fax-add");
		clickAndWaitSaveButton();
		assertTrue("Success message is missing", selenium.isTextPresent("Person was successfully created"));
	}
    
    private void verifyDefaultFieldErrorsMessages() {
        assertEquals("First Name must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_person_firstName']/div"));
        assertEquals("Last Name must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_person_lastName']/div"));
        assertEquals("Street Address must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_person_postalAddress_streetAddressLine']/div"));
        assertEquals("City must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_person_postalAddress_cityOrMunicipality']/div"));
        assertEquals("Postal Code must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_person_postalAddress_postalCode']/div"));
        assertEquals("Country must be set", selenium.getText("//div[@id='wwerr_curateEntityForm.person.postalAddress.country']/div"));
        assertTrue(selenium.isTextPresent("At least one Email Address must be set"));
    }
}
