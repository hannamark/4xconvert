package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class OrganizationTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createOrganization() {
        Organization create = new Organization();
        create.setName("Mayo University");
        create.setNciInstituteCode("P001");
        Serializable id = session.save(create);
        assertNotNull(id);
        Organization saved = (Organization) session.load(Organization.class, id);
        assertEquals("Name does not match " , create.getName(), saved.getName());
        assertEquals("Id does not match " , create.getNciInstituteCode(), saved.getNciInstituteCode());
        System.out.println("end");
    }
}
