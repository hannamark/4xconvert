package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceSearchTest extends BasePersonEntityServiceTest {
    private final Map<Ii, PersonDTO> catalog = new HashMap<Ii, PersonDTO>();

    private Ii remoteCreateAndCatalog(PersonDTO person) throws EntityValidationException {
        Ii id = getPersonService().createPerson(person);
        person.setIdentifier(id);
        catalog.put(id, person);
        return id;
    }

    private PersonDTO create(String fName, String mName, String lName, String prefix, String suffix, Ad postalAddress) {
        return create(fName, mName, lName, prefix, suffix, postalAddress, null);
    }
    
    private PersonDTO create(String fName, String mName, String lName, String prefix, String suffix, Ad postalAddress, DSet<Tel> telecomAddress) {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(fName, mName, lName, prefix, suffix));
        p.setPostalAddress(postalAddress);
        p.setTelecomAddress(telecomAddress);
        return p;
    }

    private Ad createPostalAddress(String street, String delivery, String city, String state, String zip,
            String countryAlpha3) {
        return RemoteApiUtils.createAd(street, delivery, city, state, zip, countryAlpha3);
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

            remoteCreateAndCatalog(create("Abc", "Mid1", "Klm", "Mr.", "Suf1", createPostalAddress("street",
                    "delivery", "city", "state", "zip", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("Def", "Mid2", "Nop", "Ms.", "Suf2", createPostalAddress("street",
                    "delivery", "city", "state", "zip", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("Ghi", "Mid3", "Qrs", "Mrs.", "Suf3", createPostalAddress("street",
                    "delivery", "city", "state", "zip", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("ADG", "Mid4", "KNQ", "Dr.", "Suf4", createPostalAddress("street",
                    "delivery", "city", "state", "zip", "USA"), telecomAddress));
            remoteCreateAndCatalog(create("beh", "Mid5", "lor", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "zip", "USA"), telecomAddress));

            // for street searches
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("Rst", "delivery",
                    "city", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("Uvw", "delivery",
                    "city", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("Xyz", "delivery",
                    "city", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("Rsu", "delivery",
                    "city", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("stu", "delivery",
                    "city", "state", "zip", "USA")));

            // for delivery searches
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "Abc", "city",
                    "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "Dfg", "city",
                    "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "Ghi", "city",
                    "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "jkl", "city",
                    "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "Abe", "city",
                    "state", "zip", "USA")));

            // for city searches
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "Reston", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "reston", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "New Falls", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "Old Falls", "state", "zip", "USA")));

            // for state searches
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "VA", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "MD", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "md", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "VT", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "vt", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "ALABAMA", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "ALASKA", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "LOUISIANA", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "INDIANA", "zip", "USA")));

            // for zip searches
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "Abc", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "Def", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "Ghi", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "Abe", "USA")));

            // for country searches
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "zip", "USA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "zip", "UGA")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "zip", "UKR")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "zip", "UMI")));
            remoteCreateAndCatalog(create("ZZZ", "ZZZ", "ZZZ", null, null, createPostalAddress("street", "delivery",
                    "city", "state", "zip", "URY")));
            testDataLoaded = true;
        }
    }

    private DSet<Tel> createDSetTel(List<String> email, List<String> fax,
            List<String> phone, List<String> url, List<String> text) {
        return RemoteApiUtils.convertToDSetTel(email, fax, phone, url, text);
    }

    @Test
    public void findByFamilyNameExact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "Klm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByFamilyNameExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "klm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByFamilyNameSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "lm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByFamilyNameInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "LM", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByFamilyNameStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "K", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByFamilyNameInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "k", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenNameExact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("Abc", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenNameExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("abc", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenNameSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("e", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenNameInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("E", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenNameStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("b", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenNameInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("B", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenName2Exact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "Mid1", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenName2ExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "mid1", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenName2Substring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "id", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenName2InsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "ID", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByGivenName2StartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "Mid", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(5, results.size());
    }

    @Test
    public void findByGivenName2InsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "mID", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByPrefixExact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, "Mr.", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByPrefixExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, "mr.", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByPrefixSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, "r", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByPrefixInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, "R", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByPrefixStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, "M", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(3, results.size());
    }

    @Test
    public void findByPrefixInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, "m", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findBySuffixExact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, null, "Suf1"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findBySuffixExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, null, "suf1"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findBySuffixSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, null, "uf"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findBySuffixInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, null, "UF"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findBySuffixStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, null, "Suf"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(4, results.size());
    }

    @Test
    public void findBySuffixInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, null, null, "suf"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStreetAddressLineExact() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress("Rst", null, null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStreetAddressLineExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress("rST", null, null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStreetAddressLineSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress("yz", null, null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStreetAddressLineInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress("YZ", null, null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStreetAddressLineStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress("Rs", null, null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByStreetAddressLineInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress("rS", null, null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByDeliveryAddressLineExact() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, "Abc", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByDeliveryAddressLineExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, "aBC", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByDeliveryAddressLineSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, "fg", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByDeliveryAddressLineInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, "FG", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByDeliveryAddressLineStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, "Ab", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByDeliveryAddressLineInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, "aB", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCityExact() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, "Reston", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByCityExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, "rESTON", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCitySubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, " Fal", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCityInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, " fAL", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCityStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, "Res", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByCityInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, "rES", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStateExact() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "VA", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStateExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "va", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStateSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "IAN", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStateInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "ian", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByStateStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "LOU", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByStateInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, "lou", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByZipExact() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "Abc", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByZipExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "aBC", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByZipSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "b", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByZipInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "B", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByZipStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "Ab", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByZipInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, "aB", null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findByCountryExact() {
        PersonDTO p = new PersonDTO();
        p.setPostalAddress(createPostalAddress(null, null, null, null, null, "UKR"));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByCountryExactInsensitive() {
        PersonDTO p = new PersonDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "ukr"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountrySubstring() {
        PersonDTO p = new PersonDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "GA"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountryInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "ga"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountryStartsWith() {
        PersonDTO p = new PersonDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "UM"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }

    @Test
    public void findByCountryInsensitiveStartsWith() {
        PersonDTO p = new PersonDTO();
        try {
            p.setPostalAddress(createPostalAddress(null, null, null, null, null, "um"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("must be ISO 3166-1 alpha-3 code", e.getMessage());
        }
    }
}
