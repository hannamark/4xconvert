package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

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
        String abbrv = DataGeneratorUtil
                .text(DEFAULT_TEXT_COL_LENGTH, 'X');
        String desc = DataGeneratorUtil.text(
                DEFAULT_TEXT_COL_LENGTH, 'W');
        Ii id = remoteCreateAndCatalog(create(name, abbrv, desc));

        loginAsCurator();

        selenium.open("/po-web/protected/curate/search/listAll.action");
        selenium.click("link=Inbox");
        selenium.waitForPageToLoad("30000");
        selenium.click("//a[@id='org_id_" + id.getExtension() + "']/span/span");
        selenium.waitForPageToLoad("30000");
        verifyEquals(name, selenium.getValue("curateOrgForm_organization_name"));
        verifyEquals(abbrv, selenium.getValue("curateOrgForm_organization_abbreviatedName"));
        verifyEquals(desc, selenium.getValue("curateOrgForm_organization_description"));
        verifyEquals("123 abc ave.", selenium
                .getValue("curateOrgForm_organization_postalAddress_streetAddressLine"));
        verifyEquals("", selenium
                .getValue("curateOrgForm_organization_postalAddress_deliveryAddressLine"));
        verifyEquals("mycity", selenium
                .getValue("curateOrgForm_organization_postalAddress_cityOrMunicipality"));
        verifyEquals("", selenium
                .getValue("curateOrgForm_organization_postalAddress_stateOrProvince"));
        verifyEquals("12345", selenium
                .getValue("curateOrgForm_organization_postalAddress_postalCode"));
    }

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException {
        Ii id = getOrgService().createOrganization(org);
        org.setIdentifier(id);
        catalogOrgs.put(id, org);
        return id;
    }

    private OrganizationDTO create(String name, String abbrv, String desc) {
        OrganizationDTO org = new OrganizationDTO();
        org.setName(StringConverter.convertToEnOn(name));
        org.setAbbreviatedName(StringConverter.convertToEnOn(abbrv));
        org.setDescription(StringConverter.convertToSt(desc));
        org.setPostalAddress(AddressConverterUtil.create("123 abc ave.", null, "mycity", null, "12345", "USA"));
        return org;
    }
}
