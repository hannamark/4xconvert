package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class OrganizationEntityServiceSearchTest extends BaseOrganizationEntityServiceTest {
    private final Map<Ii, OrganizationDTO> catalogOrgs = new HashMap<Ii, OrganizationDTO>();

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

    private static boolean testDataLoaded = false;

    @Before
    public void initData() throws Exception {
        if (!testDataLoaded) {
            remoteCreateAndCatalog(create("Z Inc.", "Z", "Z org"));
            remoteCreateAndCatalog(create("A Inc.", "A", "A org"));
            remoteCreateAndCatalog(create("B Inc.", "B", "B org"));
            remoteCreateAndCatalog(create("C Inc.", "C", "C org"));
            remoteCreateAndCatalog(create("AB Inc.", "AB", "AB org"));
            remoteCreateAndCatalog(create("BC Inc.", "BC", "BC org"));
            remoteCreateAndCatalog(create("CA Inc.", "CA", "CA org"));
            testDataLoaded = true;
        }
    }

    @Test
    public void findOrgByNameExact() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(RemoteApiUtils.convertToEnOn("Z Inc."));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

    @Test
    public void findOrgByNameInsensitiveExact() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(RemoteApiUtils.convertToEnOn("z inc."));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

    @Test
    public void findOrgByNameSubstring() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(RemoteApiUtils.convertToEnOn("A"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(2, results.size());
    }

    @Test
    public void findOrgByNameSubstringInsensitive() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(RemoteApiUtils.convertToEnOn("a"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(2, results.size());
    }

    @Test
    public void findOrgByDescriptionExact() {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setDescription(RemoteApiUtils.convertToSt("Z org"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

    @Test
    public void findOrgByAbbreviatedNameExact() {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setAbbreviatedName(RemoteApiUtils.convertToEnOn("Z"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

}
