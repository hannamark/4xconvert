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

public class OrganizationTelecomAddressTest extends AbstractOrganizationEntityService {
    private final Map<Ii, OrganizationDTO> testOrgs = new HashMap<Ii, OrganizationDTO>();

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException {
        Ii id = getOrgService().createOrganization(org);
        org.setIdentifier(id);
        testOrgs.put(id, org);
        return id;
    }

    private OrganizationDTO createOrgDTO(String name, Ad postalAddress, DSet<Tel> telecomAddress)
            throws URISyntaxException {
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
    public void loadData() throws Exception {
        if (!testDataLoaded) {
            DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(Arrays
                    .asList(new String[] { "myorgtestI@mygor.com" }), Arrays.asList(new String[] { "+1 342 fax" }),
                    Arrays.asList(new String[] { "+1 603 telephone with ext" }), Arrays
                            .asList(new String[] { "http://testurl.org" }), null);

            String state = "WY";
            remoteCreateAndCatalog(createOrgDTO("WEE Org Inc. I", RemoteApiUtils.createAd("123 abc ave.", null,
                    "mycity", state, "12345", "USA"), telecomAddress));
            DSet<Tel> telecomAddress1 = RemoteApiUtils.convertToDSetTel(Arrays
                    .asList(new String[] { "myorg@mygor.com" }), Arrays.asList(new String[] { "+1 342 453 5655" }),
                    Arrays.asList(new String[] { "+1 603.123.4567 ext 204" }), Arrays
                            .asList(new String[] { "http://testmail.org.com" }), null);
            remoteCreateAndCatalog(createOrgDTO("ZEE Org Inc. I", RemoteApiUtils.createAd("123 abc ave.", null,
                    "mycity", state, "12345", "USA"), telecomAddress1));
            testDataLoaded = true;
        }
    }

    @Test
    public void findByTelcomAddressByPhoneEmailUrl() {
        List<String> tels = Arrays.asList(new String[] { "+1 603.123.4567 ext 204" });
        List<String> urls = Arrays.asList(new String[] { "http://testmail.org.com" });
        List<String> email = Arrays.asList(new String[] { "myorg@mygor.com" });

        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, tels, urls, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByEmailExact() {
        List<String> email = Arrays.asList(new String[] { "myorg@mygor.com" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByEmailInsensitive() {
        List<String> email = Arrays.asList(new String[] { "MYORG@mygor.com" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByEmailContains() {
        List<String> email = Arrays.asList(new String[] { "mygor" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByEmailSubString() {
        List<String> email = Arrays.asList(new String[] { "gor" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findNoTelcomAddressOnlyByEmailSubString() {
        List<String> email = Arrays.asList(new String[] { "gorr" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(0, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByPhoneExact() {
        List<String> tels = Arrays.asList(new String[] { "+1 603.123.4567 ext 204" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, null, tels, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByPhoneContains() {
        List<String> tels = Arrays.asList(new String[] { "+1 603" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, null, tels, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByPhoneSubString() {
        List<String> tels = Arrays.asList(new String[] { "603" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, null, tels, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByFaxExact() {
        List<String> fax = Arrays.asList(new String[] { "+1 342 453 5655" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, fax, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByFaxContains() {
        List<String> fax = Arrays.asList(new String[] { "+1 342" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, fax, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByFaxSubString() {
        List<String> fax = Arrays.asList(new String[] { "342" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, fax, null, null, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByUrlExact() {
        List<String> url = Arrays.asList(new String[] { "http://testmail.org.com" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, null, null, url, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findTelcomAddressOnlyByUrlContains() {
        List<String> url = Arrays.asList(new String[] { "http://test" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(null, null, null, url, null);
        OrganizationDTO p = new OrganizationDTO();
        p.setTelecomAddress(telecomAddress);
        List<OrganizationDTO> results = getOrgService().search(p);
        assertEquals(2, results.size());

    }

    @Test
    public void findTelcomAddressOnlyByUrlSubString() {
        try {
            List<String> url = Arrays.asList(new String[] { "test" });
            RemoteApiUtils.convertToDSetTel(null, null, null, url, null);
            fail();
        } catch (IllegalArgumentException e) {
        }

    }

    @Test
    public void createTelcomAddressOnlyByEmailNotValid() {
        List<String> email = Arrays.asList(new String[] { "test" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, null, null, null);
        try {
            remoteCreateAndCatalog(createOrgDTO("In valid email id", RemoteApiUtils.createAd("123 abc ave.", null,
                    "mycity", "MD", "12345", "USA"), telecomAddress));
            fail();
        } catch (EntityValidationException e) {
            assertEquals("email[0].value=[is not a well-formed email address]", e.getErrorMessages());
        } catch (URISyntaxException e) {
            System.out.println("URI msg" + e.getMessage());
        }
    }

    // JIRA PO-833 -This test case needs to fails bcoz entering junk value in phone -
    @Test
    public void createTelcomAddressOnlyByPhoneNotValid() {
        List<String> phone = Arrays.asList(new String[] { ";?:&type=ds#/^`" });
        List<String> email = Arrays.asList(new String[] { "mailAdd@add.com" });
        DSet<Tel> telecomAddress = RemoteApiUtils.convertToDSetTel(email, null, phone, null, null);
        try {
            remoteCreateAndCatalog(createOrgDTO("In valid phone-23o4n", RemoteApiUtils.createAd("123 abc ave.", null,
                    "mycity", "MD", "12345", "USA"), telecomAddress));
            // fail();
        } catch (EntityValidationException e) {
            System.out.println("54353" + e.getErrorMessages());
        } catch (URISyntaxException e) {
            System.out.println("URI msg" + e.getMessage());
        }
    }
}
