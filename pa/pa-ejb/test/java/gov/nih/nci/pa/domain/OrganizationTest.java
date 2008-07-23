package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.nih.nci.pa.test.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class OrganizationTest  {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    /**
     * 
     */
    @Test
    public void createOrganization() {
        Organization create = OrganizationObj();
        TestSchema.addUpdObject(create);
        Session session  = TestSchema.getSession();
        
        Serializable id = create.getId();
        assertNotNull(id);
        Organization saved = (Organization) session.load(Organization.class, id);
        assertEquals("Name does not match " , create.getName(), saved.getName());
        assertEquals("Id does not match " , create.getNciInstituteCode(), saved.getNciInstituteCode());
    }
    
    public static Organization OrganizationObj() {
        Organization create = new Organization();
        create.setName("Mayo University");
        create.setNciInstituteCode("P001");
        return create ;
        
    }
}
