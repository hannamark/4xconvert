package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class OrganizationTest   {
    
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    /**
     * 
     */
    @Test
    public void createOrganization() {
        Organization create = createOrganizationObj();
        TestSchema.addUpdObject(create);
        Session session  = TestSchema.getSession();
        
        Serializable id = create.getId();
        assertNotNull(id);
        Organization saved = (Organization) session.load(Organization.class, id);
        assertEquals("Name does not match " , create.getName(), saved.getName());
        assertEquals("Identifer does not match " , create.getIdentifier(), saved.getIdentifier());
        assertEquals("User Id  does not match " , create.getUserLastUpdated(), saved.getUserLastUpdated());
        assertEquals("Date updated does not match " , create.getDateLastUpdated(), saved.getDateLastUpdated());
    }
    
    /**
     * 
     * @return Organization
     */
    public  static Organization createOrganizationObj() {
        Organization create = new Organization();
        create.setName("Mayo University");
        create.setIdentifier("P001");
        create.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setDateLastUpdated(now);
        
        return create;
        
    }
}
