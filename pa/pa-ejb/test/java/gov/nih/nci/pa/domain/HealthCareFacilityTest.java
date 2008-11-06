package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
        try {
        Transaction t = session.beginTransaction();    
        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        
        HealthCareFacility create = createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(id);
        HealthCareFacility saved = (HealthCareFacility) session.load(HealthCareFacility.class, id);
        assertEquals("Healcare Provider does not match " , create.getId(), saved.getId());
        t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    /**
     * 
     * @param p Person
     * @return HealthCareProvider
     */
    public static  HealthCareFacility createHealthCareFacilityObj(Organization o ) {
        HealthCareFacility hc = new HealthCareFacility();
        hc.setOrganization(o);
        hc.setIdentifier("abc");
        hc.setStatusCode(StatusCode.PENDING);
        return hc;
    }
    
}
