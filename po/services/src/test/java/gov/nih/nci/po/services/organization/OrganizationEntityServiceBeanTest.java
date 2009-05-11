package gov.nih.nci.po.services.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAdl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.ISOUtils;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.IdConverter.OrgIdConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceBean;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;

/**
 *
 * @author gax
 */
public class OrganizationEntityServiceBeanTest extends OrganizationServiceBeanTest {

    private OrganizationEntityServiceRemote remote;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getOrganizationEntityServiceBeanAsRemote();
    }

    /**
     * test get org behavior.
     * @throws EntityValidationException
     * @throws NullifiedEntityException
     */
    @Test
    public void getOrganization() throws EntityValidationException, NullifiedEntityException {
        long id = super.createOrganization();
        Organization org = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        OrganizationDTO result = remote.getOrganization(new OrgIdConverter().convertToIi(id));
        assertEquals(org.getId(), IiConverter.convertToLong(result.getIdentifier()));
        assertEquals(org.getName(), ISOUtils.EN.convertToString(result.getName()));
    }

    /**
     * test create organization behavior.
     */
    @Test
    public void createOrg() throws EntityValidationException, URISyntaxException {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(StringConverter.convertToEnOn("some name"));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(), getDefaultCountry().getName()));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        Tel t = new Tel();
        String phone = "+1-201-555-0123;extension=4756";
        t.setValue(new URI("tel", phone, null));
        telco.getItem().add(t);
        dto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://example.com"));
        dto.getTelecomAddress().getItem().add(url);

        Ii id = remote.createOrganization(dto);
        assertNotNull(id);
        assertNotNull(id.getExtension());
        Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, Long.parseLong(id.getExtension()));
        assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
        assertEquals(1, o.getPhone().size());
        assertEquals(phone, o.getPhone().get(0).getValue());
        assertEquals("another.email@example.com", o.getEmail().get(0).getValue());
        assertEquals("http://example.com", o.getUrl().get(0).getValue());
    }

    @Test
    public void createMinimalOrg() throws Exception {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setName(StringConverter.convertToEnOn("some name"));
            dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(), getDefaultCountry().getName()));
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            dto.setTelecomAddress(telco);

            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:another.email@example.com"));
            dto.getTelecomAddress().getItem().add(email);

            TelUrl url = new TelUrl();
            url.setValue(new URI("http://example.com"));
            dto.getTelecomAddress().getItem().add(url);

            Ii id = remote.createOrganization(dto);
            assertNotNull(id);
            assertNotNull(id.getExtension());
            Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, IiConverter.convertToLong(id));
            assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
            assertEquals("another.email@example.com", o.getEmail().get(0).getValue());
            assertEquals("http://example.com", o.getUrl().get(0).getValue());
        } catch (EntityValidationException e) {
            fail(e.getErrorMessages());
        }
    }

    @Test
    public void validate() {
        OrganizationDTO dto = new OrganizationDTO();
        Map<String, String[]> errors = remote.validate(dto);
        assertEquals(3, errors.size()) ;
        assertTrue(errors.containsKey("name"));
        assertTrue(errors.containsKey("postalAddress"));
        assertTrue(errors.containsKey("email"));
    }

    private Organization createOrg(String name, String addr1, String addr2,
            String city, String state, String zip, Country country, String[] emails, String[] phones, String[] faxes,
            String[] ttys, String[] urls) {
        Organization org = new Organization();
        org.setName(name);
        Address a = new Address(addr1, city, state, zip, getDefaultCountry());
        a.setDeliveryAddressLine(addr2);
        org.setPostalAddress(a);
        for (String email : emails) {
            org.getEmail().add(new Email(email));
        }
        for (String phone : phones) {
            org.getPhone().add(new PhoneNumber(phone));
        }
        for (String fax : faxes) {
            org.getFax().add(new PhoneNumber(fax));
        }
        for (String tty : ttys) {
            org.getTty().add(new PhoneNumber(tty));
        }
        for (String url : urls) {
            org.getUrl().add(new URL(url));
        }

        org.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(org);
        PoHibernateUtil.getCurrentSession().flush();
        return org;
    }

    @Test
    public void quickSearch() throws Exception {
        Organization o1 = createOrg("oRg1", "1 HaPPy StreEt", "aPt 1", "HaPPyville",
                "Happyland", "11111", getDefaultCountry(),
                new String[] { "admin@org1.com", "sAlEs@org1.com" },
                new String[] { "111-222-3333", "444-555-6666" },
                new String[] { "999-888-7777", "666-777-8888" },
                new String[] { "123-456-7890", "012-345-6789" },
                new String[] { "http://www.org1.com", "http://www.orG1.NET" });

        Organization o2 = createOrg("oRg2", "2 HaPPy StreEt", "aPt 2", "HaPPyburb",
                "Happycomonwealth", "11112", getDefaultCountry(),
                new String[] { "admin@org2.com", "sAlEs@org2.com" },
                new String[] { "111-222-4444", "444-555-7777" },
                new String[] { "999-888-6666", "666-777-9999" },
                new String[] { "123-456-7890", "012-345-6789" },
                new String[] { "http://www.org2.com", "http://www.orG2.NET" });

        Organization o3 = createOrg("oRg3", "3 HaPPy StreEt", "aPt 3", "HaPPytown",
                "Happystate", "11113", getDefaultCountry(),
                new String[] { "admin@org3.com", "sAlEs@org3.com" },
                new String[] { "111-222-5555", "444-555-8888" },
                new String[] { "999-888-5555", "666-777-0000" },
                new String[] { "123-456-7890", "012-345-6789" },
                new String[] { "http://www.org3.com", "http://www.orG3.NET" });


        try {
            remote.search(null);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        try {
            remote.search(new OrganizationDTO());
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // search by name
        OrganizationDTO o1dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(o1.getId()));
        OrganizationDTO sc = new OrganizationDTO();
        sc.setName(o1dto.getName());
        sc.getName().getPart().get(0).setValue("OrG");
        List<OrganizationDTO> results = remote.search(sc);
        assertEquals(3, results.size());

        sc.getName().getPart().get(0).setValue("org1");
        results = remote.search(sc);
        assertEquals(1, results.size());

        sc.getName().getPart().get(0).setValue("noresults");
        results = remote.search(sc);
        assertEquals(0, results.size());

        sc.setName(null);

    }

    @Test
    public void getById() throws Exception {

        Organization org = new Organization();
        org.setName("name");
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry());
        org.setPostalAddress(a);
        org.getEmail().add(new Email("foo@example.com"));
        org.getEmail().add(new Email("bar@example.com"));
        org.getFax().add(new PhoneNumber("201-555-0123"));
        org.getPhone().add(new PhoneNumber("+1-201-555-0123;extension=4756"));
        org.getUrl().add(new URL("http://bla"));
        org.setStatusCode(EntityStatus.ACTIVE);
        Long id = (Long) PoHibernateUtil.getCurrentSession().save(org);
        PoHibernateUtil.getCurrentSession().flush();

        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));

        assertEquals(id.toString(), dto.getIdentifier().getExtension());
        assertEquals(org.getName(), dto.getName().getPart().get(0).getValue());
        assertEquals(org.getPostalAddress().getCityOrMunicipality(), getAddressPart(dto.getPostalAddress(), AddressPartType.CTY).getValue());
        assertEquals(org.getPostalAddress().getStateOrProvince(), getAddressPart(dto.getPostalAddress(), AddressPartType.STA).getValue());
        assertEquals(org.getPostalAddress().getPostalCode(), getAddressPart(dto.getPostalAddress(), AddressPartType.ZIP).getValue());
        assertEquals(getDefaultCountry().getAlpha3(), getAddressPart(dto.getPostalAddress(), AddressPartType.CNT).getCode());
        assertEquals(5, dto.getTelecomAddress().getItem().size());
        for (Tel t : dto.getTelecomAddress().getItem()) {
            if (t.getValue().toString().equals(TelEmail.SCHEME_MAILTO + ":foo@example.com")) {
                continue;
            }
            if (t.getValue().toString().equals(TelEmail.SCHEME_MAILTO + ":bar@example.com")) {
                continue;
            }
            if (t.getValue().toString().equals(TelPhone.SCHEME_X_TEXT_FAX + ":201-555-0123")) {
                continue;
            }
            if (t.getValue().toString().equals(TelPhone.SCHEME_TEL+":+1-201-555-0123;extension=4756")) {
                continue;
            }
            if (t.getValue().toString().equals(TelUrl.SCHEME_HTTP+"://bla")) {
                continue;
            }
            fail();
        }
    }

    public static Adxp getAddressPart(Ad ad, AddressPartType addressPartType) {
        for(Adxp a : ad.getPart()) {
            if (a.getType() == addressPartType) {
                return a;
            }
        }
        return null;
    }

    @Test
    public void updateOrganization() throws EntityValidationException, URISyntaxException, NullifiedEntityException {
        long id = super.createOrganization();
        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));
        assertEquals(EntityStatus.PENDING, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        dto.setName(StringConverter.convertToEnOn("newName"));
        Adxp adl = new AdxpAdl();
        adl.setValue("additional ADL");
        dto.getPostalAddress().getPart().add(adl);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().add(email);
        remote.updateOrganization(dto);
        @SuppressWarnings("unchecked")
        List<OrganizationCR> l = PoHibernateUtil.getCurrentSession().createCriteria(OrganizationCR.class).list();
        assertEquals(1, l.size());
        OrganizationCR cr = l.get(0);
        assertEquals("newName", cr.getName());
        assertEquals("additional ADL", cr.getPostalAddress().getDeliveryAddressLine());
        assertEquals("another.email@example.com", cr.getEmail().get(1).getValue());
        assertEquals(EntityStatus.PENDING, cr.getStatusCode());
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateOrganizationChangeCtatus() throws EntityValidationException, NullifiedEntityException {
        long id = super.createOrganization();
        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));
        assertEquals(EntityStatus.PENDING, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        dto.setStatusCode(StatusCodeConverter.convertToCd(EntityStatus.INACTIVE));
        remote.updateOrganization(dto);
    }

    @Test
    public void updateOrganizationStatus() throws EntityValidationException {
        long id = super.createOrganization();
        Ii ii = ISOUtils.ID_ORG.convertToIi(id);
        Cd newStatus = StatusCodeConverter.convertToCd(EntityStatus.INACTIVE);
        remote.updateOrganizationStatus(ii, newStatus);
        @SuppressWarnings("unchecked")
        List<OrganizationCR> l = PoHibernateUtil.getCurrentSession().createCriteria(OrganizationCR.class).list();
        assertEquals(1, l.size());
        OrganizationCR cr = l.get(0);
        assertEquals(cr.getStatusCode(), EntityStatus.INACTIVE);
    }
    public static EnOn convertToEnOn(String value) {
        EnOn iso = new EnOn();
        if (value == null) {
            iso.setNullFlavor(NullFlavor.NI);
        } else {
            Enxp e = new Enxp(null);
            e.setValue(value);
            iso.getPart().add(e);
        }
        return iso;
    }

    @Test
    public void search() throws TooManyResultsException {
        createOrg("oRg1", "1 HaPPy StreEt", "aPt 1", "HaPPyville",
                "Happyland", "11111", getDefaultCountry(),
                new String[] { "admin@org1.com", "sAlEs@org1.com" },
                new String[] { "111-222-3333", "444-555-6666" },
                new String[] { "999-888-7777", "666-777-8888" },
                new String[] { "123-456-7890", "012-345-6789" },
                new String[] { "http://www.org1.com", "http://www.orG1.NET" });

        createOrg("oRg2", "2 HaPPy StreEt", "aPt 2", "HaPPyburb",
                "Happycomonwealth", "11112", getDefaultCountry(),
                new String[] { "admin@org2.com", "sAlEs@org2.com" },
                new String[] { "111-222-4444", "444-555-7777" },
                new String[] { "999-888-6666", "666-777-9999" },
                new String[] { "123-456-7890", "012-345-6789" },
                new String[] { "http://www.org2.com", "http://www.orG2.NET" });

        createOrg("oRg3", "3 HaPPy StreEt", "aPt 3", "HaPPytown",
                "Happystate", "11113", getDefaultCountry(),
                new String[] { "admin@org3.com", "sAlEs@org3.com" },
                new String[] { "111-222-5555", "444-555-8888" },
                new String[] { "999-888-5555", "666-777-0000" },
                new String[] { "123-456-7890", "012-345-6789" },
                new String[] { "http://www.org3.com", "http://www.orG3.NET" });
        
        OrganizationDTO sc = new OrganizationDTO();
        sc.setName(convertToEnOn("OrG"));
        
        LimitOffset page = new LimitOffset(1,0);
        List<OrganizationDTO> results;
        
        results = remote.search(sc, page);
        assertEquals(0, page.getOffset());
        assertEquals("Expected to find 1 entry", 1, results.size());
        
        page.next();
        results = remote.search(sc, page);
        assertEquals(1, page.getOffset());
        assertEquals("Expected to find 1 entry", 1, results.size());
        
        page.next();
        results = remote.search(sc, page);
        assertEquals(2, page.getOffset());
        assertEquals("Expected to find 1 entry", 1, results.size());
        
        
        page.next();
        results = remote.search(sc, page);
        assertEquals(3, page.getOffset());
        assertEquals("Expected to find 0 entry", 0, results.size());
        
        
        page.previous();
        results = remote.search(sc, page);
        assertEquals("Expected to find 1 entry", 1, results.size());
        assertEquals(2, page.getOffset());
        
        page.previous();
        results = remote.search(sc, page);
        assertEquals("Expected to find 1 entry", 1, results.size());
        assertEquals(1, page.getOffset());
        
        page.previous();
        results = remote.search(sc, page);
        assertEquals(0, page.getOffset());
        assertEquals("Expected to find 1 entry", 1, results.size());
        
        
        //First page is always returned even when offset is < 0
        page.previous();
        results = remote.search(sc, page);
        assertEquals(-1, page.getOffset());
        assertEquals("Expected to find 1 entry", 1, results.size());
        
        page.previous();
        results = remote.search(sc, page);
        assertEquals(-2, page.getOffset());
        assertEquals("Expected to find 0 entry", 1, results.size());

    }
    
    /**
     * Numerous test cases are combined to speed up the test (only perform the insert once)
     * @throws TooManyResultsException
     */
    @Test 
    public void verifySearchThrowsTooManyResultsException() throws TooManyResultsException {
        int max = 7;
        OrganizationEntityServiceBean.setMaxResultsReturnedLimit(max-2);
        
        for (int i = 0; i < max; i++) {
            createOrg("oRg" + i, "" + i + " HaPPy StreEt", "aPt " + i, "HaPPyville",
                    "Happyland", "11111", getDefaultCountry(),
                    new String[] { "admin@org" + i + ".com", "sAlEs@org" + i + ".com" },
                    new String[] { "111-222-3333", "444-555-6666" },
                    new String[] { "999-888-7777", "666-777-8888" },
                    new String[] { "123-456-7890", "012-345-6789" },
                    new String[] { "http://www.org" + i + ".com", "http://www.orG" + i + ".NET" });
        }
        OrganizationDTO sc = new OrganizationDTO();
        sc.setName(convertToEnOn("OrG"));
        
        try {
            remote.search(sc, new LimitOffset(max, 0));
            fail();
        } catch (TooManyResultsException e) {
        }
        
        try {
            remote.search(sc, new LimitOffset(max-1, 0));
            fail();
        } catch (TooManyResultsException e) {
        }
        
        List<OrganizationDTO> results;
        results = remote.search(sc, new LimitOffset(max-2, 0));
        assertEquals(max-2, results.size());
        results = remote.search(sc, new LimitOffset(max-3, 0));
        assertEquals(max-3, results.size());
    }
    
}
