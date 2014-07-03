package gov.nih.nci.services.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAdl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.ISOUtils;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.IdConverter.OrgIdConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceBean;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author gax
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityServiceProvider.class)
public class OrganizationEntityServiceBeanTest extends OrganizationServiceBeanTest {

    private OrganizationEntityServiceRemote remote;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getOrganizationEntityServiceBeanAsRemote();
   }

    @Before
    public void mockSecurity() {
        UserProvisioningManager userProvisioningManager = mock(UserProvisioningManager.class);
        when(userProvisioningManager.getUser(anyString())).thenAnswer(
                new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        return (User) PoHibernateUtil.getCurrentSession().createCriteria(User.class)
                                .add(Restrictions.eq("loginName", invocation.getArguments()[0])).uniqueResult();
                    }
                }
        );


        mockStatic(SecurityServiceProvider.class);
        try {
            PowerMockito.when(SecurityServiceProvider.getUserProvisioningManager("po")).thenReturn(userProvisioningManager);
        } catch (CSException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getOrganization() throws EntityValidationException, NullifiedEntityException, JMSException {
        long id = super.createOrganization();
        Organization org = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        OrganizationDTO result = remote.getOrganization(new OrgIdConverter().convertToIi(id));
        assertEquals(org.getId(), IiConverter.convertToLong(result.getIdentifier()));
        assertEquals(org.getName(), ISOUtils.EN.convertToString(result.getName()));
   }

    @Test
    public void createOrg() throws EntityValidationException, URISyntaxException, CurationException {
        OrganizationDTO dto = createOrgWithNoPhones();
        
        Tel t = new Tel();
        String phone = "201-555-0123x4756";
        t.setValue(new URI("tel", phone, null));
        dto.getTelecomAddress().getItem().add(t);

        Ii id = remote.createOrganization(dto);
        assertNotNull(id);
        assertNotNull(id.getExtension());
        Organization o =
                (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class,
                        Long.parseLong(id.getExtension()));

        assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
        assertEquals(1, o.getPhone().size());
        assertEquals(phone, o.getPhone().get(0).getValue());
        assertEquals("another.email@example.com", o.getEmail().get(0).getValue());
        assertEquals("http://example.com", o.getUrl().get(0).getValue());
        assertNotNull("CreatedBy not set on creation.", o.getCreatedBy());
        assertEquals(getUser(), o.getCreatedBy());
   }
    
    private OrganizationDTO createOrgWithNoPhones() throws URISyntaxException {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(StringConverter.convertToEnOn("some name"));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine",
                "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(),
                getDefaultCountry().getName()));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);
        
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://example.com"));
        dto.getTelecomAddress().getItem().add(url);
        
        return dto;
    }
    
    @Test
    public void createOrgWithInvalidPhone() throws EntityValidationException, URISyntaxException, CurationException {
        OrganizationDTO dto = createOrgWithNoPhones();
        
        TelPhone t = new TelPhone();
        String phone = "201-555-0123x4756i";
        t.setValue(new URI("tel", phone, null));
        dto.getTelecomAddress().getItem().add(t);
        
        try {
            remote.createOrganization(dto);
        } catch (EntityValidationException eve) {
            assertTrue(eve.getErrors().containsKey("Phone number 201-555-0123x4756i"));
        }
   } 
   
    @Test
    public void createOrgWithInvalidFax() throws EntityValidationException, URISyntaxException, CurationException {
        OrganizationDTO dto = createOrgWithNoPhones();
        
        TelPhone t = new TelPhone();
        String phone = "201-555-0123x4756i";
        t.setValue(new URI("x-text-fax", phone, null));
        dto.getTelecomAddress().getItem().add(t);
        
        try {
            remote.createOrganization(dto);
        } catch (EntityValidationException eve) {
            assertTrue(eve.getErrors().containsKey("Phone number 201-555-0123x4756i"));
        }
   } 
    
   @Test
   public void createOrgWithInvalidTty() throws EntityValidationException, URISyntaxException, CurationException {
        OrganizationDTO dto = createOrgWithNoPhones();
        
        TelPhone t = new TelPhone();
        String phone = "201-555-0123x4756i";
        t.setValue(new URI("x-text-tel", phone, null));
        dto.getTelecomAddress().getItem().add(t);
        
        try {
            remote.createOrganization(dto);
        } catch (EntityValidationException eve) {
            assertTrue(eve.getErrors().containsKey("Phone number 201-555-0123x4756i"));
        }
   } 

    @Test
    public void createMinimalOrg() throws Exception {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setName(StringConverter.convertToEnOn("some name"));
            dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine",
                    "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(),
                    getDefaultCountry().getName()));
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
            Organization o =
                    (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class,
                            IiConverter.convertToLong(id));
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
        assertEquals(2, errors.size());
        assertTrue(errors.containsKey("name"));
        assertTrue(errors.containsKey("postalAddress"));        
   }

    private Organization createOrg(String name, String addr1, String addr2, String city, String state, String zip,
            Country country, String[] emails, String[] phones, String[] faxes, String[] ttys, String[] urls) {
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
        Organization o1 =
                createOrg("oRg1", "1 HaPPy StreEt", "aPt 1", "HaPPyville", "Happyland", "11111", getDefaultCountry(),
                        new String[] {"admin@org1.com", "sAlEs@org1.com"}, new String[] {"111-222-3333",
                                "444-555-6666"}, new String[] {"999-888-7777", "666-777-8888"}, new String[] {
                                "123-456-7890", "012-345-6789"}, new String[] {"http://www.org1.com",
                                "http://www.orG1.NET"});

        Organization o2 =
                createOrg("oRg2", "2 HaPPy StreEt", "aPt 2", "HaPPyburb", "Happycomonwealth", "11112",
                        getDefaultCountry(), new String[] {"admin@org2.com", "sAlEs@org2.com"}, new String[] {
                                "111-222-4444", "444-555-7777"}, new String[] {"999-888-6666", "666-777-9999"},
                        new String[] {"123-456-7890", "012-345-6789"}, new String[] {"http://www.org2.com",
                                "http://www.orG2.NET"});

        Organization o3 =
                createOrg("oRg3", "3 HaPPy StreEt", "aPt 3", "HaPPytown", "Happystate", "11113", getDefaultCountry(),
                        new String[] {"admin@org3.com", "sAlEs@org3.com"}, new String[] {"111-222-5555",
                                "444-555-8888"}, new String[] {"999-888-5555", "666-777-0000"}, new String[] {
                                "123-456-7890", "012-345-6789"}, new String[] {"http://www.org3.com",
                                "http://www.orG3.NET"});

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
        Address a =
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
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
        assertEquals(org.getPostalAddress().getCityOrMunicipality(), getAddressPart(dto.getPostalAddress(),
                AddressPartType.CTY).getValue());
        assertEquals(org.getPostalAddress().getStateOrProvince(), getAddressPart(dto.getPostalAddress(),
                AddressPartType.STA).getValue());
        assertEquals(org.getPostalAddress().getPostalCode(),
                getAddressPart(dto.getPostalAddress(), AddressPartType.ZIP).getValue());
        assertEquals(getDefaultCountry().getAlpha3(), getAddressPart(dto.getPostalAddress(), AddressPartType.CNT)
                .getCode());
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
            if (t.getValue().toString().equals(TelPhone.SCHEME_TEL + ":+1-201-555-0123;extension=4756")) {
                continue;
           }
            if (t.getValue().toString().equals(TelUrl.SCHEME_HTTP + "://bla")) {
                continue;
           }
            fail();
       }
   }

    public static Adxp getAddressPart(Ad ad, AddressPartType addressPartType) {
        for (Adxp a : ad.getPart()) {
            if (a.getType() == addressPartType) {
                return a;
           }
       }
        return null;
   }

    @Test
    public void updateOrganization() throws EntityValidationException, URISyntaxException, NullifiedEntityException,
            JMSException {
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

    @Test(expected = IllegalArgumentException.class)
    public void updateOrganizationChangeCtatus() throws EntityValidationException, NullifiedEntityException,
            JMSException {
        long id = super.createOrganization();
        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));
        assertEquals(EntityStatus.PENDING, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        dto.setStatusCode(StatusCodeConverter.convertToCd(EntityStatus.INACTIVE));
        remote.updateOrganization(dto);
   }

    @Test
    public void updateOrganizationStatus() throws EntityValidationException, JMSException {
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

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNoIdentifier() throws Exception {
        long id = super.createOrganization();
        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));

        dto.setIdentifier(null);
        remote.updateOrganization(dto);
   }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithWrongIdentifier() throws Exception {
        long id = super.createOrganization();
        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));

        Ii wrongId = new Ii();
        wrongId.setRoot(IdConverter.ORG_ROOT);
        wrongId.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        wrongId.setExtension("999");
        dto.setIdentifier(wrongId);
        remote.updateOrganization(dto);
   }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatusWithWrongIdentifier() throws Exception {

        Cd cd = new Cd();
        cd.setCode("suspended"); // maps to SUSPENDED

        Ii wrongId = new Ii();
        wrongId.setRoot(IdConverter.ORG_ROOT);
        wrongId.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        wrongId.setExtension("999");

        remote.updateOrganizationStatus(wrongId, cd);
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
        createOrg("oRg1", "1 HaPPy StreEt", "aPt 1", "HaPPyville", "Happyland", "11111", getDefaultCountry(),
                new String[] {"admin@org1.com", "sAlEs@org1.com"}, new String[] {"111-222-3333", "444-555-6666"},
                new String[] {"999-888-7777", "666-777-8888"}, new String[] {"123-456-7890", "012-345-6789"},
                new String[] {"http://www.org1.com", "http://www.orG1.NET"});

        createOrg("oRg2", "2 HaPPy StreEt", "aPt 2", "HaPPyburb", "Happycomonwealth", "11112", getDefaultCountry(),
                new String[] {"admin@org2.com", "sAlEs@org2.com"}, new String[] {"111-222-4444", "444-555-7777"},
                new String[] {"999-888-6666", "666-777-9999"}, new String[] {"123-456-7890", "012-345-6789"},
                new String[] {"http://www.org2.com", "http://www.orG2.NET"});

        createOrg("oRg3", "3 HaPPy StreEt", "aPt 3", "HaPPytown", "Happystate", "11113", getDefaultCountry(),
                new String[] {"admin@org3.com", "sAlEs@org3.com"}, new String[] {"111-222-5555", "444-555-8888"},
                new String[] {"999-888-5555", "666-777-0000"}, new String[] {"123-456-7890", "012-345-6789"},
                new String[] {"http://www.org3.com", "http://www.orG3.NET"});

        OrganizationDTO sc = new OrganizationDTO();
        sc.setName(convertToEnOn("OrG"));

        LimitOffset page = new LimitOffset(1, 0);
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

        // First page is always returned even when offset is < 0
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
     * Numerous test cases are combined to speed up the test (only perform the insert once).
     *
     * @throws TooManyResultsException
     */
    @Test
    public void verifySearchThrowsTooManyResultsException() throws TooManyResultsException {
        int max = 7;
        OrganizationEntityServiceBean.setMaxResultsReturnedLimit(max - 2);

        for (int i = 0; i < max; i++) {
            createOrg("oRg" + i, "" + i + " HaPPy StreEt", "aPt " + i, "HaPPyville", "Happyland", "11111",
                    getDefaultCountry(), new String[] {"admin@org" + i + ".com", "sAlEs@org" + i + ".com"},
                    new String[] {"111-222-3333", "444-555-6666"}, new String[] {"999-888-7777", "666-777-8888"},
                    new String[] {"123-456-7890", "012-345-6789"}, new String[] {"http://www.org" + i + ".com",
                            "http://www.orG" + i + ".NET"});
       }
        OrganizationDTO sc = new OrganizationDTO();
        sc.setName(convertToEnOn("OrG"));

        try {
            remote.search(sc, new LimitOffset(max, 0));
            fail();
       } catch (TooManyResultsException e) {
       }

        try {
            remote.search(sc, new LimitOffset(max - 1, 0));
            fail();
       } catch (TooManyResultsException e) {
       }

        List<OrganizationDTO> results;
        results = remote.search(sc, new LimitOffset(max - 2, 0));
        assertEquals(max - 2, results.size());
        results = remote.search(sc, new LimitOffset(max - 3, 0));
        assertEquals(max - 3, results.size());
   }

}
