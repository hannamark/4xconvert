package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.Ii;
public class ManageClinicalResearchStaffWithCRTest extends AbstractPoWebTest {

    private static final String ORG_WITH_APOS = "Org'With'Apos";

    public void testCreateActivePersonWithPendingCRS() throws Exception {
        loginAsCurator();
        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", ORG_WITH_APOS, getAddress(), "sample@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String activeOrgId =  selenium.getText("wwctrl_organization.id");
        activeOrgId.trim();
        assertNotEquals("null", activeOrgId);

        //create a PENDING ORG for later
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        //create the ACTIVE Person
        openCreatePerson();
        createPerson();
        String personIdExt = selenium.getText("wwctrl_person.id");
        Ii personId = new Ii();
        personId.setExtension(personIdExt);
        savePersonAsActive(personId);

        //open Person Curate page
        openAndWait("po-web/protected/person/curate/start.action?person.id=" + personIdExt);

        //Goto Manage CRS Page
        accessManageClinicalResearchStaffScreen();
        //add CRS
        clickAndWaitButton("add_button");
        verifyTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        //ensure the player is ACTIVE
        verifyEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");

        //verify validation messages
        clickAndWaitButton("save_button");
        assertEquals("ACTIVE", selenium.getSelectedLabel("curateRoleForm.role.status"));

        verifyTrue(selenium.isTextPresent("Affiliated Organization ID must be set"));
        verifyTrue(selenium.isTextPresent("Phone number is required for this status."));
        verifyFalse(selenium.isTextPresent("Role status not compatible with associated entity's status."));

        //select a PENDING Scoper
        selectOrganizationScoper(orgId.getExtension(), ORG_WITH_APOS);
        clickAndWaitButton("save_button");
        verifyTrue(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        verifyTrue(selenium.isTextPresent("Phone number is required for this status."));
        verifyFalse(selenium.isTextPresent("Affiliated Organization ID must be set"));

        //select a ACTIVE Scoper
        selectOrganizationScoper(activeOrgId, ORG_WITH_APOS);
        clickAndWaitButton("save_button");
        verifyTrue(selenium.isTextPresent("Phone number is required for this status."));
        verifyFalse(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        verifyFalse(selenium.isTextPresent("Affiliated Organization ID must be set"));

        //add postal addresses
        addPostalAddressUsingPopup("456 jik ", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        addPostalAddressUsingPopup("123 abc", "suite def", "mycity", "AK", "12345", "United Kingdom", 2);

        selenium.selectFrame("relative=parent");
        waitForTelecomFormsToLoad();
        //add Contact Information
        inputContactInfo("abc@example.com", "1234567890", "2345678901", "3456789012", "http://www.example.com");

        //save CRS
        clickAndWaitButton("save_button");
        assertTrue("PO: Persons and Organizations - Manage Clinical Research Staff(s)".equals(selenium.getTitle()));
        verifyTrue(selenium.isTextPresent("Clinical Research Staff was successfully created!"));
    }

    public void createPendingPersonWithPendingCRS() throws Exception {
        loginAsCurator();
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        openCreatePerson();
        createPerson();

        accessManageClinicalResearchStaffScreen();
        clickAndWaitButton("add_button");
        verifyTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        verifyEquals("PENDING", selenium.getText("wwctrl_person.statusCode"));

        clickAndWaitButton("save_button");
        verifyTrue(selenium.isTextPresent("Affiliated Organization ID must be set"));

        selenium.select("curateRoleForm.role.status", "label=ACTIVE");
        clickAndWaitButton("save_button");
        verifyTrue(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        selenium.select("curateRoleForm.role.status", "label=PENDING");

        selectOrganizationScoper(orgId.getExtension(), ORG_WITH_APOS);

        addPostalAddressUsingPopup("456 jik ", "suite xyz", "phoenix", "AZ", "67890", "United States", 1);
        addPostalAddressUsingPopup("123 abc", "suite def", "mycity", "AK", "12345", "United Kingdom", 2);
        selenium.selectFrame("relative=parent");
        waitForTelecomFormsToLoad();
        inputContactInfo("abc@example.com", "1234567890", "2345678901", "3456789012", "http://www.example.com");

        clickAndWaitButton("save_button");
    }

    public void createPersonWithClinicalResearchStaffTest() throws Exception {
        loginAsCurator();
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        openCreatePerson();
        createPerson();
        accessManageClinicalResearchStaffScreen();
        addClinicalResearchStaffAffiliateOrg(orgId.getExtension());
    }

    private void addClinicalResearchStaffAffiliateOrg(String orgId) throws InterruptedException {
        clickAndWaitButton("add_button");
        verifyTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        selectOrganizationScoper(orgId, ORG_WITH_APOS);
    }

}
