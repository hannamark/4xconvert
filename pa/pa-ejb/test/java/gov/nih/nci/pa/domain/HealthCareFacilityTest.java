package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class HealthCareFacilityTest {
    
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
    public void createHealthCareFacilityTest() {
        Session session  = TestSchema.getSession();

        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        
        HealthCareFacility create = createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(id);
        HealthCareFacility saved = (HealthCareFacility) session.load(HealthCareFacility.class, id);
        assertEquals("Healcare Provider does not match " , create.getId(), saved.getId());

    }
    

    /**
     * 
     * @param p Person
     * @return HealthCareProvider
     */
    public static  HealthCareFacility createHealthCareFacilityObj(Organization o ) {
        HealthCareFacility hc = new HealthCareFacility();
        hc.setOrganization(o);
        return hc;
    }
    
}
