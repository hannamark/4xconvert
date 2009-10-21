package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.remoteapi.RemoteApiUtils;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

public class CurateOrganizationTest extends AbstractPoWebTest {

    private static final String CTEP_ORG_ROOT = "2.16.840.1.113883.3.26.6.2";

    private static final int DEFAULT_TEXT_COL_LENGTH = 160;

    private final Map<Ii, OrganizationDTO> catalogOrgs = new HashMap<Ii, OrganizationDTO>();
    private OrganizationEntityServiceRemote orgService;
    private HealthCareFacilityCorrelationServiceRemote hcfService;
    protected OrganizationEntityServiceRemote getOrgService() {
        return orgService;
    }

    protected HealthCareFacilityCorrelationServiceRemote getHcfService() {
        return hcfService;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        if (orgService == null) {
            orgService = RemoteServiceHelper.getOrganizationEntityService();
        }

        if (hcfService == null) {
            hcfService = RemoteServiceHelper.getHealthCareFacilityCorrelationService();
        }
    }

    public void testCurateNewOrg() throws Exception {
        /* create a new org via remote API. */
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

        loginAsCurator();

        openEntityInboxOrganization();

        // click on item to curate
        clickAndWaitButton("org_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();

        saveAsActive(id);
    }

    public void testCurateOrgWithCRs() throws Exception {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

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

        openEntityInboxOrganization();

        // click on item to curate
        clickAndWaitButton("org_id_" + id.getExtension());
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();

        saveAsActive(id);
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToActive() throws Exception {
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        saveAsActive(id);
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullify() throws Exception {
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getOrgService().getOrganization(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
        }
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullifyWithDuplicateId() throws Exception {
        /* create an org to serve as a duplicate */
        Ii dupId = createNewOrgThenCurateAsActive();
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        /* wait for in-browser js to execute via select's onchange event */
        Thread.sleep(1000);
        selenium.isVisible("//div[@id='duplicateOfDiv']");
        clickAndWaitButton("select_duplicate");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicateOrganizationForm_criteria_organization_id", dupId.getExtension());

        /* search for dups */
        selenium.click("//a[@id='submitDuplicateOrganizationForm']/span/span");
        /* wait for results to load */
        waitForElementById("mark_as_dup_" + dupId.getExtension(), 30);
        /* select record to use at duplicate */
        clickAndWaitButton("mark_as_dup_" + dupId.getExtension());

        selenium.selectFrame("relative=parent");

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getOrgService().getOrganization(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
            assertEquals(dupId.getExtension(), nullifiedEntities.values().iterator().next().getExtension());
        }
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullifyWithNoDuplicateId() throws Exception {
        Ii orgId = createNewOrgWithCtepRoleThenCurateAsActive();
        openAndWait("po-web/protected/organization/curate/start.action?organization.id=" + orgId.getExtension());

        waitForPageToLoad();
        waitForTelecomFormsToLoad();

        // method exits on certain page
        assertEquals("PO: Persons and Organizations - Organization Details", selenium.getTitle());
        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        /* wait for in-browser js to execute via select's onchange event */
        Thread.sleep(1000);
        saveAsNullifiedFail(orgId);
    }


    public void testCurateNewOrgThenCurateAfterRemoteUpdateToInactive() throws Exception {
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();
        saveAsInactive(id);
    }

    private Ii createNewOrgThenCurateAsActive() throws EntityValidationException, URISyntaxException, CurationException {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        openEntityInboxOrganization();

        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();

        saveAsActive(id);
        return id;
    }

    private Ii createNewOrgWithCtepRoleThenCurateAsActive() throws EntityValidationException, URISyntaxException, CurationException {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));
        Ii hcfId = remoteCreateHcfWithCtepId(id);

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        openEntityInboxOrganization();

        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();

        saveAsActive(id);
        return id;
    }

    private Ii curateNewOrgThenCurateAfterRemoteUpdate() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        Ii id = createNewOrgThenCurateAsActive();

        OrganizationDTO proposedState = remoteGetOrganization(id);
        String newCrName = "a realistic name";
        proposedState.setName(RemoteApiUtils.convertToEnOn(newCrName));
        remoteUpdate(proposedState);

        openEntityInboxOrganization();

        // click on item to curate
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();

        // method exits on certain page
        assertEquals("PO: Persons and Organizations - Organization Details - Comparison", selenium.getTitle());

        return id;
    }

    private void openEntityInboxOrganization() {
        selenium.open("/po-web/protected/curate/search/listOrgs.action");
        selenium.click("id=EntityInboxOrganization");
        waitForPageToLoad();
    }

    private void saveAsActive(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=ACTIVE");
        clickAndWaitSaveButton();
        assertEquals("PO: Persons and Organizations - Entity Inbox - Organization", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsInactive(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=INACTIVE");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        selenium.getConfirmation();
        assertEquals("PO: Persons and Organizations - Entity Inbox - Organization", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullified(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        selenium.getConfirmation();
        assertEquals("PO: Persons and Organizations - Entity Inbox - Organization", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullifiedFail(Ii id) throws InterruptedException {
        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();
        selenium.getConfirmation();
        Thread.sleep(1000);
        assertTrue(selenium.isTextPresent("A duplicate Organization must be provided."));
    }

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException, CurationException {
        Ii id = getOrgService().createOrganization(org);
        org.setIdentifier(id);
        catalogOrgs.put(id, org);
        return id;
    }

    private Ii remoteCreateHcfWithCtepId(Ii orgId) throws EntityValidationException, CurationException, URISyntaxException {
        Ii id = this.getHcfService().createCorrelation(createHcfFromCtep(orgId));
        return id;
    }

    private OrganizationDTO create(String name) throws URISyntaxException {
        return create(name, RemoteApiUtils.createAd("123 abc ave.", null, "mycity", "VA", "12345", "USA"));
    }

    private OrganizationDTO create(String name, Ad postalAddress) throws URISyntaxException {
        OrganizationDTO org = new OrganizationDTO();
        org.setName(RemoteApiUtils.convertToEnOn(name));
        org.setPostalAddress(postalAddress);
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        org.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        org.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        org.getTelecomAddress().getItem().add(url);
        return org;
    }

    private HealthCareFacilityDTO createHcfFromCtep(Ii orgId) throws URISyntaxException {
        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setPlayerIdentifier(orgId);
        hcf.setName(RemoteApiUtils.convertToEnOn("ctep role"));
        Cd status = new Cd();
        status.setCode("PENDING");
        hcf.setStatus(status);
        Ii ctep = new Ii();
        ctep.setRoot(CTEP_ORG_ROOT);
        ctep.setExtension("AAA");
        ctep.setIdentifierName("CTEP ID");
        hcf.setIdentifier(new DSet<Ii>());
        hcf.getIdentifier().setItem(new HashSet<Ii>());
        hcf.getIdentifier().getItem().add(ctep);
        DSet<Ad> addys = new DSet<Ad>();
        Set<Ad> set = new ListOrderedSet();
        addys.setItem(set);
        addys.getItem().add(RemoteApiUtils.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
        hcf.setPostalAddress(addys);

        hcf.setTelecomAddress(new DSet<Tel>());
        hcf.getTelecomAddress().setItem(new HashSet<Tel>());

        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-654"));
        hcf.getTelecomAddress().getItem().add(ph1);
        return hcf;
    }

    private OrganizationDTO remoteGetOrganization(Ii id) throws NullifiedEntityException {
        return getOrgService().getOrganization(id);
    }

    private void remoteUpdate(OrganizationDTO proposedState) throws EntityValidationException {
        getOrgService().updateOrganization(proposedState);
    }
}
