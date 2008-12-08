package gov.nih.nci.coppa.test.integration.test;

public class CreatePersonTest extends AbstractPoWebTest {
    /**
     * Verifies PO-618, PO-619 via UI
     */
    public void testVerifyDefaultValidationErrors() throws Exception {
        loginAsCurator();

        openCreatePerson();
        assertEquals("PENDING", selenium.getSelectedValue("curateEntityForm.person.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateEntityForm.person.statusCode"));
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
