package gov.nih.nci.coppa.test.integration.test;

public class CreateOrganizationTest extends AbstractPoWebTest {
    /**
     * Verifies PO-618, PO-619 via UI
     */
    public void testVerifyDefaultValidationErrors() throws Exception {
        loginAsCurator();

        openCreateOrganization();
        assertEquals("PENDING", selenium.getSelectedValue("curateOrgForm.organization.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateOrgForm.organization.statusCode"));
        clickAndWaitSaveButton();
        assertEquals("PENDING", selenium.getSelectedValue("curateOrgForm.organization.statusCode"));
        assertEquals("PENDING", selenium.getSelectedLabel("curateOrgForm.organization.statusCode"));
        verifyDefaultFieldErrorMessages();
        
        selenium.select("curateOrgForm.organization.statusCode", "label=--Select a Status--");
        clickAndWaitSaveButton();
        assertEquals("", selenium.getSelectedValue("curateOrgForm.organization.statusCode"));
        assertEquals("--Select a Status--", selenium.getSelectedLabel("curateOrgForm.organization.statusCode"));
        assertEquals("Status must be set", selenium.getText("//div[@id='wwerr_curateOrgForm.organization.statusCode']/div"));
        verifyDefaultFieldErrorMessages();
    }

    private void verifyDefaultFieldErrorMessages() {
        assertEquals("Organization Name must be set", selenium.getText("//div[@id='wwerr_curateOrgForm_organization_name']/div"));
        assertEquals("Street Address must be set", selenium.getText("//div[@id='wwerr_curateOrgForm_organization_postalAddress_streetAddressLine']/div"));
        assertEquals("City must be set", selenium.getText("//div[@id='wwerr_curateOrgForm_organization_postalAddress_cityOrMunicipality']/div"));
        assertEquals("Organization Name must be set", selenium.getText("//div[@id='wwerr_curateOrgForm_organization_name']/div"));
        assertEquals("Postal Code must be set", selenium.getText("//div[@id='wwerr_curateOrgForm_organization_postalAddress_postalCode']/div"));
        assertEquals("Country must be set", selenium.getText("//div[@id='wwerr_curateOrgForm.organization.postalAddress.country']/div"));
        assertTrue(selenium.isTextPresent("At least one Email Address must be set"));
    }
}
