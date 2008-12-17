package gov.nih.nci.coppa.test.integration.test;

public class CreateOrganizationTest extends AbstractPoWebTest {
    /**
     * Verifies PO-618, PO-619 via UI
     */
    public void testVerifyDefaultValidationErrors() throws Exception {
        loginAsCurator();

        openCreateOrganization();
        assertEquals("PENDING", selenium.getSelectedValue("curateEntityForm.organization.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateEntityForm.organization.statusCode"));
        assertEquals("United States", selenium.getSelectedLabel("curateEntityForm.organization.postalAddress.country"));
        clickAndWaitSaveButton();
        assertEquals("PENDING", selenium.getSelectedValue("curateEntityForm.organization.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateEntityForm.organization.statusCode"));
        assertEquals("United States", selenium.getSelectedLabel("curateEntityForm.organization.postalAddress.country"));
        verifyDefaultFieldErrorMessages();
        
        selenium.select("curateEntityForm.organization.statusCode", "label=--Select a Status--");
        selenium.select("curateEntityForm.organization.postalAddress.country", "label=--Select a Country--");
        clickAndWaitSaveButton();
        assertEquals("", selenium.getSelectedValue("curateEntityForm.organization.statusCode"));
        assertEquals("--Select a Status--", selenium.getSelectedLabel("curateEntityForm.organization.statusCode"));
        assertEquals("Status must be set", selenium.getText("//div[@id='wwerr_curateEntityForm.organization.statusCode']/div"));
        assertEquals("", selenium.getSelectedValue("curateEntityForm.organization.postalAddress.country"));
        assertEquals("--Select a Country--", selenium.getSelectedLabel("curateEntityForm.organization.postalAddress.country"));
        assertEquals("Country must be set", selenium.getText("//div[@id='wwerr_curateEntityForm.organization.postalAddress.country']/div"));
        verifyDefaultFieldErrorMessages();
    }

    private void verifyDefaultFieldErrorMessages() {
        assertEquals("Organization Name must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_organization_name']/div"));
        assertEquals("Address Line 1 must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_organization_postalAddress_streetAddressLine']/div"));
        assertEquals("City must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_organization_postalAddress_cityOrMunicipality']/div"));
        assertEquals("Organization Name must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_organization_name']/div"));
        assertEquals("Postal Code must be set", selenium.getText("//div[@id='wwerr_curateEntityForm_organization_postalAddress_postalCode']/div"));
        assertTrue(selenium.isTextPresent("At least one Email Address must be set"));
    }
}
