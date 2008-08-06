package gov.nih.nci.po.service.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.convert.ISOUtils;
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
        OrganizationDTO result = remote.getOrganization(id);
        assertEquals(org.getId(), ISOUtils.II.convertToLong(result.getIdentifier()));
        assertEquals(org.getName(), ISOUtils.EN.convertToString(result.getName()));
    }

    /**
     * test create organization behavior.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCreateOrganization() {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setIdentifier(ISOUtils.ID_ORG.convertToIi(99L));
            dto.setName(ISOUtils.STRING.convertToEnOn("some name"));
            dto.setAbbreviatedName(ISOUtils.STRING.convertToEnOn("short"));
            long id = remote.createOrganization(dto);
            assertNotNull(dto.getIdentifier().getNullFlavor()); // make sure this id was not used
            Organization o = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, id);
            assertEquals(ISOUtils.EN.convertToString(dto.getName()), o.getName());
            assertEquals(ISOUtils.EN.convertToString(dto.getAbbreviatedName()), o.getAbbreviatedName());
        } catch (EntityValidationException e) {
            // as the DTO to BO conversion gets more completem, we should not
            // have to catch this exception.
            Map<String, String[]> errors = e.getErrors();
            assertEquals(1, errors.size()) ;
            assertTrue(errors.containsKey("postalAddress"));
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
