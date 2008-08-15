package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
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
        org.setName(StringConverter.convertToEnOn(name));
        org.setAbbreviatedName(StringConverter.convertToEnOn(abbrv));
        org.setDescription(StringConverter.convertToSt(desc));
		org.setPostalAddress(AddressConverterUtil.create("123 abc ave.", null, "mycity", null, "12345", "USA"));
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
        crit.setName(StringConverter.convertToEnOn("Z Inc."));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

    @Test
    public void findOrgByNameInsensitiveExact() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(StringConverter.convertToEnOn("z inc."));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

    @Test
    public void findOrgByNameSubstring() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(StringConverter.convertToEnOn("A"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(3, results.size());
    }

    @Test
    public void findOrgByNameSubstringInsensitive() throws Exception {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setName(StringConverter.convertToEnOn("a"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(3, results.size());
    }

    @Test
    public void findOrgByDescriptionExact() {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setDescription(StringConverter.convertToSt("Z org"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

    @Test
    public void findOrgByAbbreviatedNameExact() {
        OrganizationDTO crit = new OrganizationDTO();
        crit.setAbbreviatedName(StringConverter.convertToEnOn("Z"));
        List<OrganizationDTO> results = getOrgService().search(crit);
        assertEquals(1, results.size());
    }

}
