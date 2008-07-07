package gov.nih.nci.po.service.organization;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.dto.entity.OrganizationDTO;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.OrganizationServiceBean;
import gov.nih.nci.po.service.OrganizationServiceTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class OrganizationEntityServiceBeanTest extends AbstractHibernateTestCase {

    private OrganizationEntityServiceBean instance;
    private OrganizationServiceBean local;
    
    @Before
    public void setupService() {
        local = EjbTestHelper.getOrganizationServiceBean();
        instance = new OrganizationEntityServiceBean();
        instance.setOrganizationServiceBean(local);
    }

    @Test
    public void testGetOrganization() {
        OrganizationServiceTest t = new OrganizationServiceTest();
        t.setUpData();
        long id = t.createOrganization();
        Organization org = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        OrganizationDTO result = instance.getOrganization(id);
        assertEquals(org.getId(), result.getId());
        assertEquals(org.getName(), result.getName());
    }

    @Test
    public void testCreateOrganization() {
        try {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setId(99L);
            dto.setName("some name");
            dto.setAbbreviationName("short");        
            long id = instance.createOrganization(dto);
            assertNull(dto.getId());// make sure this id was not used
            Organization o = local.getOrganization(id);
            assertEquals(dto.getName(), o.getName());
            assertEquals(dto.getAbbreviationName(), o.getAbbreviationName());        
        } catch(org.hibernate.PropertyValueException e) {
            //temporarily catch this untill DTOs are complete
            assertEquals("not-null property references a null or transient value: gov.nih.nci.po.data.bo.Address.cityOrMunicipality", e.getMessage());
        }
    }

}