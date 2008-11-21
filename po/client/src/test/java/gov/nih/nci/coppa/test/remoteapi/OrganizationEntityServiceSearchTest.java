package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class OrganizationEntityServiceSearchTest extends AbstractOrganizationEntityService {
    private final Map<Ii, OrganizationDTO> catalogOrgs = new HashMap<Ii, OrganizationDTO>();

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException {
        Ii id = getOrgService().createOrganization(org);
        org.setIdentifier(id);
        catalogOrgs.put(id, org);
        return id;
    }

    private OrganizationDTO create(String name, Ad postalAddress) throws URISyntaxException {
        return create(name, postalAddress, null);
    }

    private OrganizationDTO create(String name, Ad postalAddress, DSet<Tel> telecomAddress) throws URISyntaxException {
        OrganizationDTO org = new OrganizationDTO();
        org.setName(RemoteApiUtils.convertToEnOn(name));
        org.setPostalAddress(postalAddress);
        if (telecomAddress != null) {
            org.setTelecomAddress(telecomAddress);
        } else {
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            org.setTelecomAddress(telco);
    
            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:default@example.com"));
            org.getTelecomAddress().getItem().add(email);
    
            TelUrl url = new TelUrl();
            url.setValue(new URI("http://default.example.com"));
            org.getTelecomAddress().getItem().add(url);        
        }
        return org;
    }

    private static boolean testDataLoaded = false;

    @Before
    public void initData() throws Exception {
        if (!testDataLoaded) {
            List<String> tels = Arrays.asList(new String[] { "+1 703.123.4567", "+1 571.239.1234", "+1 866.526.6042",
                    "703-123-1234", "703-123-1235" });
            List<String> urls = Arrays.asList(new String[] { "http://www.example.com", "ftp://ftp.example.com",
                    "http://demos.example.com", "https://mail.example.com", "http://gal.example.com" });
            List<String> email = Arrays.asList(new String[] { "jdoe@example.com", "sales@example.com",
                    "john.doe@example.com", "jdoe@example.net", "jdoe@example.org" });

            DSet<Tel> telecomAddress = createDSetTel(email, tels, tels, urls, tels);

            remoteCreateAndCatalog(create("Z Inc.", createPostalAddress("123 abc ave.", null, "mycity",
                    null, "12345", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("A Inc.", createPostalAddress("123 abc ave.", null, "mycity",
                    null, "12345", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("B Inc.", createPostalAddress("123 abc ave.", null, "mycity",
                    null, "12345", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("C Inc.", createPostalAddress("123 abc ave.", null, "mycity",
                    null, "12345", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("AB Inc.", createPostalAddress("123 abc ave.", null,
                    "mycity", null, "12345", "USA"), telecomAddress));

            remoteCreateAndCatalog(create("BC Inc.", createPostalAddress("123 abc ave.", null,
                    "mycity", null, "12345", "USA")));
            remoteCreateAndCatalog(create("CA Inc.", createPostalAddress("123 abc ave.", null,
                    "mycity", null, "12345", "USA")));

            // for street searches
            String state = "WY";
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("Rst", "delivery", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("Uvw", "delivery", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("Xyz", "delivery", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("Rsu", "delivery", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("stu", "delivery", "city", state,
                    "zip", "USA")));

            // for delivery searches
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "Abc", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "Dfg", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "Ghi", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "jkl", "city", state,
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "Abe", "city", state,
                    "zip", "USA")));

            // for city searches
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "Reston",
                    state, "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "reston",
                    state, "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "New Falls",
                    state, "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "Old Falls",
                    state, "zip", "USA")));

            // for state searches
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city", "VA",
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city", "MD",
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city", "VT",
                    "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    "AL", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    "AK", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    "LA", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    "IN", "zip", "USA")));

            // for zip searches
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "Abc", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "Def", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "Ghi", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "Abe", "USA")));

            // for country searches
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "zip", "UGA")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "zip", "UKR")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "zip", "UMI")));
            remoteCreateAndCatalog(create("ZZZ", createPostalAddress("street", "delivery", "city",
                    state, "zip", "URY")));
            testDataLoaded = true;
        }
    }

    private Ad createPostalAddress(String street, String delivery, String city, String state, String zip,
            String countryAlpha3) {
        return RemoteApiUtils.createAd(street, delivery, city, state, zip, countryAlpha3);
    }

    private DSet<Tel> createDSetTel(List<String> email, List<String> fax, List<String> phone, List<String> url,
            List<String> text) {
        return RemoteApiUtils.convertToDSetTel(email, fax, phone, url, text);
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
    public void findByStreetAddressLineExact() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress("Rst", null, null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStreetAddressLineExactInsensitive() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress("rST", null, null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStreetAddressLineSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress("yz", null, null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStreetAddressLineInsensitiveSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress("YZ", null, null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStreetAddressLineStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress("Rs", null, null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByStreetAddressLineInsensitiveStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress("rS", null, null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByDeliveryAddressLineExact() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, "Abc", null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByDeliveryAddressLineExactInsensitive() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, "aBC", null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByDeliveryAddressLineSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, "fg", null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByDeliveryAddressLineInsensitiveSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, "FG", null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByDeliveryAddressLineStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, "Ab", null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByDeliveryAddressLineInsensitiveStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, "aB", null, null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByCityExact() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, "Reston", null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByCityExactInsensitive() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, "rESTON", null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByCitySubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, " Fal", null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCityInsensitiveSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, " fAL", null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCityStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, "Res", null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByCityInsensitiveStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, "rES", null, null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByStateExact() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "VA", null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStateExactInsensitive() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "va", null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStateSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "IAN", null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStateInsensitiveSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "ian", null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStateStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "LA", null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStateInsensitiveStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "la", null, null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByZipExact() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "Abc", null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByZipExactInsensitive() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "aBC", null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByZipSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "b", null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByZipInsensitiveSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "B", null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByZipStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "Ab", null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByZipInsensitiveStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "aB", null));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByCountryExact() {
        OrganizationDTO p = new OrganizationDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, null, "UKR"));
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByCountryExactInsensitive() {
        OrganizationDTO p = new OrganizationDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "ukr"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountrySubstring() {
        OrganizationDTO p = new OrganizationDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "GA"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountryInsensitiveSubstring() {
        OrganizationDTO p = new OrganizationDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "ga"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountryStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "UM"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountryInsensitiveStartsWith() {
        OrganizationDTO p = new OrganizationDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "um"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }
}
