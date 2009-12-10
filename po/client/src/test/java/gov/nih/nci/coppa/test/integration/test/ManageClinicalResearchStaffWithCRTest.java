package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URISyntaxException;

import javax.naming.NamingException;
public class ManageClinicalResearchStaffWithCRTest extends AbstractManageOrgRolesWithCRTest {

    private static final String ORG_WITH_APOS = "Org'With'Apos";
    public String AFFILIATE_ORG_FOR_PERSON_CRS_OLD = "org_for_person_crs_original";
    public String AFFILIATE_ORG_FOR_PERSON_CRS_NEW = "org_for_person_crs_new";

    public void testCreateActivePersonWithPendingCRS() throws Exception {
        loginAsCurator();
        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", ORG_WITH_APOS, getAddress(), "test1@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String activeOrgId =  selenium.getText("wwctrl_organization.id");
        activeOrgId.trim();
        assertNotEquals("null", activeOrgId);
        
        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON_CRS_OLD, getAddress(), "test2@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String affiliateOldOrgId =  selenium.getText("wwctrl_organization.id");
        affiliateOldOrgId.trim();
        assertNotEquals("null", affiliateOldOrgId);
        
        //create an ACTIVE ORG for later
        createOrganization("ACTIVE", AFFILIATE_ORG_FOR_PERSON_CRS_NEW, getAddress(), "test3@example.com", "703-111-2345",
                "703-111-1234", null, "http://www.example.com");
        String affiliateNewOrgId =  selenium.getText("wwctrl_organization.id");
        affiliateNewOrgId.trim();
        assertNotEquals("null", affiliateNewOrgId);

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
        assertTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        //ensure the player is ACTIVE
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));
        selenium.select("curateRoleForm.role.status", "label=ACTIVE");

        //verify validation messages
        clickAndWaitButton("save_button");
        assertEquals("ACTIVE", selenium.getSelectedLabel("curateRoleForm.role.status"));

        assertTrue(selenium.isTextPresent("Affiliated Organization ID must be set"));
        assertTrue(selenium.isTextPresent("Phone number is required for this status."));
        assertFalse(selenium.isTextPresent("Role status not compatible with associated entity's status."));

        //select a PENDING Scoper
        selectOrganizationScoper(orgId.getExtension(), ORG_WITH_APOS);
        clickAndWaitButton("save_button");
        assertTrue(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        assertTrue(selenium.isTextPresent("Phone number is required for this status."));
        assertFalse(selenium.isTextPresent("Affiliated Organization ID must be set"));

        //select a ACTIVE Scoper
        selectOrganizationScoper(affiliateOldOrgId, AFFILIATE_ORG_FOR_PERSON_CRS_OLD);
        clickAndWaitButton("save_button");
        assertTrue(selenium.isTextPresent("Phone number is required for this status."));
        assertFalse(selenium.isTextPresent("Role status not compatible with associated entity's status."));
        assertFalse(selenium.isTextPresent("Affiliated Organization ID must be set"));
        
        //add Contact Information
        waitForTelecomFormsToLoad();
        checkContactInformation();

        addUSPostalAddress();
        waitForTelecomFormsToLoad();
        addContactInformation();
       
        //save CRS
        clickAndWaitButton("save_button");
        assertTrue("PO: Persons and Organizations - Manage Clinical Research Staff(s)".equals(selenium.getTitle()));
        assertTrue(selenium.isTextPresent("Clinical Research Staff was successfully created!"));

        String ocId = selenium.getTable("row.1.0");
        assertNotEquals("null", ocId.trim());

        clickAndWaitButton("return_to_button");
        assertTrue(selenium.isTextPresent("exact:Basic Identifying Information"));
        // save everything
        clickAndWaitButton("save_button");
        
        updateRemoteCrsOrg(ocId, affiliateNewOrgId);
        
        // Goto Manage CRS Page.... should see CR
        openAndWait("/po-web/protected/roles/person/ClinicalResearchStaff/start.action?person=" + personIdExt);
        clickAndWaitButton("edit_clinicalResearchStaff_id_" + ocId.trim());
        assertTrue(selenium.isTextPresent("exact:Edit Clinical Research Staff - Comparison"));
        // status
        assertEquals("ACTIVE", selenium.getText("wwctrl_person.statusCode"));
        
        // old values
        assertEquals(AFFILIATE_ORG_FOR_PERSON_CRS_OLD + " (" + affiliateOldOrgId.trim() + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id").trim());
        
        // copy over new affiliated org and check new value
        selenium.click("copy_curateCrForm_role_scoper");
        waitForElementById("wwctrl_curateRoleForm_role_scoper_id", 5);
        assertEquals(AFFILIATE_ORG_FOR_PERSON_CRS_NEW + " (" + affiliateNewOrgId.trim() + ")", selenium.getText("wwctrl_curateRoleForm_role_scoper_id").trim());
           
        clickAndWaitButton("save_button");
        assertTrue(selenium.isTextPresent("exact:Clinical Research Staff was successfully updated!".trim()));
        
    }
    
    private void checkContactInformation() throws Exception {
        // Check contact information functionality - add/remove, eror messages etc.
        checkPostalAddress();
        checkEmail();
        checkUrl();
        checkPhone();
        checkFax();
        checkTty();
    }
    
    private void updateRemoteCrsOrg(String ext, String affOrgId) throws EntityValidationException, NamingException, URISyntaxException,
    NullifiedEntityException, NullifiedRoleException {
        Ii id = new Ii();
        id.setExtension(ext);
        id.setRoot("2.16.840.1.113883.3.26.4.4.1");
        id.setIdentifierName("NCI clinical research staff identifier");
        ClinicalResearchStaffDTO dto = RemoteServiceHelper.getClinicalResearchStaffCorrelationService().getCorrelation(id);
        Cd roleStatus = new Cd();
        roleStatus.setCode("active");
        dto.setStatus(roleStatus);
        Ii aff = new Ii();
        aff.setExtension(affOrgId);
        aff.setRoot("2.16.840.1.113883.3.26.4.2");
        aff.setIdentifierName("NCI organization entity identifier");
        dto.setScoperIdentifier(aff);
        
        RemoteServiceHelper.getClinicalResearchStaffCorrelationService().updateCorrelation(dto);
    }

    public void createPendingPersonWithPendingCRS() throws Exception {
        loginAsCurator();
        Ii orgId = createRemoteOrg(ORG_WITH_APOS);
        openCreatePerson();
        createPerson();

        accessManageClinicalResearchStaffScreen();
        clickAndWaitButton("add_button");
        assertTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        assertEquals("PENDING", selenium.getText("wwctrl_person.statusCode"));

        clickAndWaitButton("save_button");
        assertTrue(selenium.isTextPresent("Affiliated Organization ID must be set"));

        selenium.select("curateRoleForm.role.status", "label=ACTIVE");
        clickAndWaitButton("save_button");
        assertTrue(selenium.isTextPresent("Role status not compatible with associated entity's status."));
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
        assertTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        selectOrganizationScoper(orgId, ORG_WITH_APOS);
    }

}
