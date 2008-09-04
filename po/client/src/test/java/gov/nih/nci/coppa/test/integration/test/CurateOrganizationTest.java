package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.remoteapi.RemoteApiUtils;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CurateOrganizationTest extends AbstractPoWebTest {
    private static final int DEFAULT_TEXT_COL_LENGTH = 100;

    private final Map<Ii, OrganizationDTO> catalogOrgs = new HashMap<Ii, OrganizationDTO>();
    private OrganizationEntityServiceRemote orgService;

    protected OrganizationEntityServiceRemote getOrgService() {
        return orgService;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        if (orgService == null) {
            orgService = RemoteServiceHelper.getOrganizationEntityService();
        }
    }

    public void testCurateNewOrg() throws Exception {
        // create a new org via remote API.
        String name = DataGeneratorUtil.text(DEFAULT_TEXT_COL_LENGTH, 'Y');
        String abbrv = DataGeneratorUtil.text(DEFAULT_TEXT_COL_LENGTH, 'X');
        String desc = DataGeneratorUtil.text(DEFAULT_TEXT_COL_LENGTH, 'W');
        Ii id = remoteCreateAndCatalog(create(name, abbrv, desc));

        loginAsCurator();

        selenium.open("/po-web/protected/curate/search/listAll.action");
        selenium.click("link=Inbox");
        waitForPageToLoad();
        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        assertEquals(name, selenium.getValue("curateOrgForm_organization_name"));
        assertEquals(abbrv, selenium.getValue("curateOrgForm_organization_abbreviatedName"));
        assertEquals(desc, selenium.getValue("curateOrgForm_organization_description"));

        verifyPostalAddress();

        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();
        
        markAsAccepted(id);
    }

    private void markAsAccepted(Ii id) {
        clickAndWait("//a[@id='mark_as_accepted_button']/span/span");
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }
    
    public void testCurateOrgWithCRs() throws Exception {
        // create a new org via remote API.
        String name = DataGeneratorUtil.text(DEFAULT_TEXT_COL_LENGTH, 'Y');
        String abbrv = DataGeneratorUtil.text(DEFAULT_TEXT_COL_LENGTH, 'X');
        String desc = DataGeneratorUtil.text(DEFAULT_TEXT_COL_LENGTH, 'W');
        Ii id = remoteCreateAndCatalog(create(name, abbrv, desc));
        
        OrganizationDTO orgDTO = getOrgService().getOrganization(id);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        orgDTO.getTelecomAddress().getItem().add(email);
        getOrgService().updateOrganization(orgDTO);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI(TelPhone.SCHEME_TEL + ":123-456-7890"));
        orgDTO.getTelecomAddress().getItem().add(phone);
        getOrgService().updateOrganization(orgDTO);
        TelPhone fax = new TelPhone();
        fax.setValue(new URI(TelPhone.SCHEME_X_TEXT_FAX + ":234-567-8901"));
        orgDTO.getTelecomAddress().getItem().add(fax);
        getOrgService().updateOrganization(orgDTO);
        TelPhone tty = new TelPhone();
        tty.setValue(new URI(TelPhone.SCHEME_X_TEXT_TEL + ":345-678-9012"));
        orgDTO.getTelecomAddress().getItem().add(tty);
        getOrgService().updateOrganization(orgDTO);
        
        loginAsCurator();
        
        selenium.open("/po-web/protected/curate/search/listAll.action");
        selenium.click("link=Inbox");
        waitForPageToLoad();
        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        assertEquals(name, selenium.getValue("curateOrgForm_organization_name"));
        assertEquals(abbrv, selenium.getValue("curateOrgForm_organization_abbreviatedName"));
        assertEquals(desc, selenium.getValue("curateOrgForm_organization_description"));
        
        verifyPostalAddress();
        
        verifyEmail();
        verifyPhone();
        verifyFax();
        verifyTty();
        verifyUrl();
        
    }

    private void verifyPostalAddress() {
        assertEquals("123 abc ave.", selenium.getValue("curateOrgForm_organization_postalAddress_streetAddressLine"));
        assertEquals("", selenium.getValue("curateOrgForm_organization_postalAddress_deliveryAddressLine"));
        assertEquals("mycity", selenium.getValue("curateOrgForm_organization_postalAddress_cityOrMunicipality"));
        assertEquals("", selenium.getValue("curateOrgForm_organization_postalAddress_stateOrProvince"));
        assertEquals("12345", selenium.getValue("curateOrgForm_organization_postalAddress_postalCode"));
    }

    private void verifyEmail() {
        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address must be set"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc@example.com");
        selenium.click("email-add");
        waitForElementById("email-remove-0", 5);
        assertEquals("abc@example.com | Remove", selenium.getText("email-entry-0"));

        waitForElementById("emailEntry_value", 5);
        selenium.type("emailEntry_value", "abc.com");
        selenium.click("email-add");
        waitForElementById("wwerr_emailEntry_value", 5);
        assertTrue(selenium.isTextPresent("Email Address is not a well-formed email address"));
    }

    private void verifyPhone() {
        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone must be set"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "123-456-7890");
        selenium.click("phone-add");
        waitForElementById("phone-remove-0", 5);
        assertEquals("123-456-7890 | Remove", selenium.getText("phone-entry-0"));

        waitForElementById("phoneEntry_value", 5);
        selenium.type("phoneEntry_value", "1234567890123456789012345678901");
        selenium.click("phone-add");
        waitForElementById("wwerr_phoneEntry_value", 5);
        assertTrue(selenium.isTextPresent("Phone length must be between 0 and 30"));
    }

    private void verifyFax() {
        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax must be set"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "234-567-8901");
        selenium.click("fax-add");
        waitForElementById("fax-remove-0", 5);
        assertEquals("234-567-8901 | Remove", selenium.getText("fax-entry-0"));

        waitForElementById("faxEntry_value", 5);
        selenium.type("faxEntry_value", "1234567890123456789012345678901");
        selenium.click("fax-add");
        waitForElementById("wwerr_faxEntry_value", 5);
        assertTrue(selenium.isTextPresent("Fax length must be between 0 and 30"));
    }

    private void verifyTty() {
        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY must be set"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "345-678-9012");
        selenium.click("tty-add");
        waitForElementById("tty-remove-0", 5);
        assertEquals("345-678-9012 | Remove", selenium.getText("tty-entry-0"));

        waitForElementById("ttyEntry_value", 5);
        selenium.type("ttyEntry_value", "1234567890123456789012345678901");
        selenium.click("tty-add");
        waitForElementById("wwerr_ttyEntry_value", 5);
        assertTrue(selenium.isTextPresent("TTY length must be between 0 and 30"));
    }

    private void verifyUrl() {
        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL must be set"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "http://www.example.com");
        selenium.click("url-add");
        waitForElementById("url-remove-0", 5);
        assertEquals("http://www.example.com | Remove | Visit...", selenium.getText("url-entry-0"));

        waitForElementById("urlEntry_value", 5);
        selenium.type("urlEntry_value", "abc.com");
        selenium.click("url-add");
        waitForElementById("wwerr_urlEntry_value", 5);
        assertTrue(selenium.isTextPresent("URL is not well formed"));
    }

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException {
        Ii id = getOrgService().createOrganization(org);
        org.setIdentifier(id);
        catalogOrgs.put(id, org);
        return id;
    }

    private OrganizationDTO create(String name, String abbrv, String desc) {
        OrganizationDTO org = new OrganizationDTO();
        org.setName(RemoteApiUtils.convertToEnOn(name));
        org.setAbbreviatedName(RemoteApiUtils.convertToEnOn(abbrv));
        org.setDescription(RemoteApiUtils.convertToSt(desc));
        org.setPostalAddress(RemoteApiUtils.createAd("123 abc ave.", null, "mycity", null, "12345", "USA"));
        return org;
    }
}
