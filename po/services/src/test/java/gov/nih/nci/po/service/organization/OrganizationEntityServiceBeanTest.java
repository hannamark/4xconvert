package gov.nih.nci.po.service.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.convert.ISOUtils;
import gov.nih.nci.po.dto.entity.OrganizationDTO;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;

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
    public void testGetOrganization() {
        OrganizationServiceBeanTest t = new OrganizationServiceBeanTest();
        t.setUpData();
        long id = t.createOrganization();
        Organization org = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        OrganizationDTO result = remote.getOrganization(id);
        assertEquals(org.getId(), result.getId());
        assertEquals(org.getName(), ISOUtils.TO_STRING.convert(result.getName()));
    }

    /**
     * test create organization behavior.
     */
    @Test
    public void testCreateOrganization() {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setId(99L);
            dto.setName(ISOUtils.TO_ST.convert("some name"));
            dto.setAbbreviationName(ISOUtils.TO_ST.convert("short"));
            long id = remote.createOrganization(dto);
            assertNull(dto.getId()); // make sure this id was not used
            Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, id);
            assertEquals(ISOUtils.TO_STRING.convert(dto.getName()), o.getName());
            assertEquals(ISOUtils.TO_STRING.convert(dto.getAbbreviationName()), o.getAbbreviationName());
        } catch (EntityValidationException e) {
            // as the DTO to BO conversion gets more completem, we should not
            // have to catch this exception.
            Map<String, String[]> errors = e.getErrors();
            assertEquals(4, errors.size()) ;
            assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.streetAddressLine"));
            assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.postalCode"));
            assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.cityOrMunicipality"));
            assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.country"));
        }
    }

    @Test
    public void validate() {
        
        OrganizationDTO dto = new OrganizationDTO();
        dto.setAbbreviationName(ISOUtils.TO_ST.convert("short"));
        Map<String, String[]> errors = remote.validate(dto);
        assertEquals(5, errors.size()) ;
        assertTrue(errors.containsKey("name"));
        assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.streetAddressLine"));
        assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.postalCode"));
        assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.cityOrMunicipality"));
        assertTrue(errors.containsKey("primaryContactInfo.mailingAddress.country"));

    }

}
