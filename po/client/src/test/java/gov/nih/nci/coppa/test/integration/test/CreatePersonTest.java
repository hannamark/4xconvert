package gov.nih.nci.coppa.test.integration.test;

public class CreatePersonTest extends AbstractPoWebTest {

    public void testPerson() {
        loginAsCurator();
        openCreatePerson();
        verifyDefaultValidationErrors();
        createPerson();
    }

    /**
     * Verifies PO-618, PO-619 via UI
     */
    private void verifyDefaultValidationErrors() {

        clickAndWaitSaveButton();
        assertEquals("PENDING", selenium.getSelectedValue("curateEntityForm.person.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateEntityForm.person.statusCode"));
        assertEquals("United States", selenium.getSelectedLabel("curateEntityForm.person.postalAddress.country"));
        verifyDefaultFieldErrorsMessages();

        selenium.select("curateEntityForm.person.statusCode", "label=" + SELECT_A_STATUS);
        selenium.select("curateEntityForm.person.postalAddress.country", "label=" + SELECT_A_COUNTRY);
        clickAndWaitSaveButton();
        assertEquals("", selenium.getSelectedValue("curateEntityForm.person.statusCode"));
        assertEquals(SELECT_A_STATUS, selenium.getSelectedLabel("curateEntityForm.person.statusCode"));
        assertTrue(selenium.isTextPresent(STATUS_MUST_BE_SET));
        assertEquals("", selenium.getSelectedValue("curateEntityForm.person.postalAddress.country"));
        assertEquals(SELECT_A_COUNTRY, selenium.getSelectedLabel("curateEntityForm.person.postalAddress.country"));
        assertEquals(COUNTRY_MUST_BE_SET, selenium
                .getText("//div[@id='wwerr_curateEntityForm.person.postalAddress.country']/div"));

        verifyDefaultFieldErrorsMessages();
    }

    private void verifyDefaultFieldErrorsMessages() {
        assertEquals("First Name must be set", selenium
                .getText("//div[@id='wwerr_curateEntityForm_person_firstName']/div"));
        assertEquals("Last Name must be set", selenium
                .getText("//div[@id='wwerr_curateEntityForm_person_lastName']/div"));
        assertEquals("Address Line 1 must be set", selenium
                .getText("//div[@id='wwerr_curateEntityForm_person_postalAddress_streetAddressLine']/div"));
        assertEquals("City must be set", selenium
                .getText("//div[@id='wwerr_curateEntityForm_person_postalAddress_cityOrMunicipality']/div"));
        assertEquals("Postal Code must be set", selenium
                .getText("//div[@id='wwerr_curateEntityForm_person_postalAddress_postalCode']/div"));
        assertTrue(selenium.isTextPresent("At least one Email Address must be set"));
    }
}
