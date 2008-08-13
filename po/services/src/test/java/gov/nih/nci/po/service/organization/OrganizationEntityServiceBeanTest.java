package gov.nih.nci.po.service.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.ISOUtils;
import gov.nih.nci.po.data.convert.IdConverter.OrgIdConverter;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gax
 */
public class OrganizationEntityServiceBeanTest extends AbstractHibernateTestCase {

    private OrganizationEntityServiceRemote remote;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getOrganizationEntityServiceBean();
    }

    /**
     * test get org behavior.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetOrganization() {
        OrganizationServiceBeanTest t = new OrganizationServiceBeanTest();
        t.setUpData();
        long id = t.createOrganization();
        Organization org = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        OrganizationDTO result = remote.getOrganization(new OrgIdConverter().convertToIi(id));
        assertEquals(org.getId(), ISOUtils.II.convertToLong(result.getIdentifier()));
        assertEquals(org.getName(), ISOUtils.EN.convertToString(result.getName()));
    }

    /**
     * test create organization behavior.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void createOrganization() {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setIdentifier(ISOUtils.ID_ORG.convertToIi(99L));
            dto.setName(ISOUtils.STRING.convertToEnOn("some name"));
            dto.setAbbreviatedName(ISOUtils.STRING.convertToEnOn("short"));
            Ii id = remote.createOrganization(dto);
            assertNotNull(id);
            assertNotNull(id.getExtension());
            assertNotNull(dto.getIdentifier().getNullFlavor()); // make sure this id was not used
            Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, id);
            assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
            assertEquals(ISOUtils.EN.convertToString(dto.getAbbreviatedName()), o.getAbbreviatedName());
        } catch (EntityValidationException e) {
            // as the DTO to BO conversion gets more complete, we should not
            // have to catch this exception.
            Map<String, String[]> errors = e.getErrors();
            assertEquals(1, errors.size()) ;
            assertTrue(errors.containsKey("postalAddress"));
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createMinimalOrg() throws Exception {
        try {
            PoHibernateUtil.getCurrentSession().save(new Country("MyCountry", "123", "MC", "MYC"));

            OrganizationDTO dto = new OrganizationDTO();
            dto.setIdentifier(ISOUtils.ID_ORG.convertToIi(99L));
            dto.setName(ISOUtils.STRING.convertToEnOn("some name"));
            dto.setAbbreviatedName(ISOUtils.STRING.convertToEnOn("short"));
            Address a = new Address();
            a.setStreetAddressLine("123 abc ave.");
            a.setCityOrMunicipality("mycity");
            a.setPostalCode("12345");
            Country c = new Country(null, null, null, "MYC");
            a.setCountry(c);
            dto.setPostalAddress(AddressConverter.convertToAd(a));

            Ii id = remote.createOrganization(dto);
            assertNotNull(id);
            assertNotNull(id.getExtension());
            Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, new OrgIdConverter().convertToLong(id));
            assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
            assertEquals(ISOUtils.EN.convertToString(dto.getAbbreviatedName()), o.getAbbreviatedName());
        } catch (EntityValidationException e) {
            fail(e.getErrorMessages());
        }
    }

    @Test
    public void validate() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setAbbreviatedName(ISOUtils.STRING.convertToEnOn("short"));
        Map<String, String[]> errors = remote.validate(dto);
        assertEquals(2, errors.size()) ;
        assertTrue(errors.containsKey("name"));
        assertTrue(errors.containsKey("postalAddress"));
    }
}
