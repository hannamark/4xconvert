package gov.nih.nci.po.services.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.Address;
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
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

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
     */
    @Test
    public void getOrganization() throws EntityValidationException {
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
        dto.setAbbreviatedName(StringConverter.convertToEnOn("short"));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3()));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        Tel t = new Tel();
        String phone = "+1-201-555-0123;extension=4756";
        t.setValue(new URI("tel", phone, null));
        telco.getItem().add(t);
        dto.setTelecomAddress(telco);
        Ii id = remote.createOrganization(dto);
        assertNotNull(id);
        assertNotNull(id.getExtension());
        Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, Long.parseLong(id.getExtension()));
        assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
        assertEquals(ISOUtils.EN.convertToString(dto.getAbbreviatedName()), o.getAbbreviatedName());
        assertEquals(1, o.getPhone().size());
        assertEquals(phone, o.getPhone().get(0).getValue());
    }

    @Test
    public void createMinimalOrg() throws Exception {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setName(StringConverter.convertToEnOn("some name"));
            dto.setAbbreviatedName(StringConverter.convertToEnOn("short"));
            dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3()));
            Ii id = remote.createOrganization(dto);
            assertNotNull(id);
            assertNotNull(id.getExtension());
            Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, IiConverter.convertToLong(id));
            assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
            assertEquals(ISOUtils.EN.convertToString(dto.getAbbreviatedName()), o.getAbbreviatedName());
        } catch (EntityValidationException e) {
            fail(e.getErrorMessages());
        }
    }

    @Test
    public void validate() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setAbbreviatedName(StringConverter.convertToEnOn("short"));
        Map<String, String[]> errors = remote.validate(dto);
        assertEquals(2, errors.size()) ;
        assertTrue(errors.containsKey("name"));
        assertTrue(errors.containsKey("postalAddress"));
    }

    @Test
    public void getById() throws Exception {

        Organization org = new Organization();
        org.setName("name");
        org.setAbbreviatedName("abbreviatedName");
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
        assertEquals(org.getAbbreviatedName(), dto.getAbbreviatedName().getPart().get(0).getValue());
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
    public void updateOrganization() throws EntityValidationException, URISyntaxException {
        long id = super.createOrganization();
        OrganizationDTO dto = remote.getOrganization(ISOUtils.ID_ORG.convertToIi(id));
        assertEquals(EntityStatus.PENDING, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        dto.setName(StringConverter.convertToEnOn("newName"));
        Adxp adl = Adxp.createAddressPart(AddressPartType.ADL);
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
        assertEquals("another.email@example.com", cr.getEmail().get(0).getValue());
        assertEquals(EntityStatus.PENDING, cr.getStatusCode());
    }

    @Test(expected=IllegalArgumentException.class)
    public void updateOrganizationChangeCtatus() throws EntityValidationException {
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

}
