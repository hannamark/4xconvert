package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class HealthCareProviderTest {
    
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
    public void createHealthCareProviderTest() {
        
        try {
            Session session = TestSchema.getSession();

            Transaction t = session.beginTransaction();    

            Person p = PersonTest.createPersonObj();
            TestSchema.addUpdObject(p);
            //session.save(p);
            Organization o = OrganizationTest.createOrganizationObj();
            TestSchema.addUpdObject(o);
            //session.save(o);
            
            HealthCareProvider hc = createHealthCareProviderObj(p , o);
            TestSchema.addUpdObject(hc);
            //session.save(hc);
            Serializable id = hc.getId();
            assertNotNull(id);
            HealthCareProvider saved = (HealthCareProvider) session.load(HealthCareProvider.class, id);
            assertEquals("Healcare Provider does not match " , hc.getId(), saved.getId());
            assertEquals("Person  does not match " , hc.getPerson().getId(), saved.getPerson().getId());
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
    public static  HealthCareProvider createHealthCareProviderObj(Person p , Organization o ) {
        HealthCareProvider hc = new HealthCareProvider();
        hc.setOrganization(o);
        hc.setPerson(p);
        hc.setIdentifier("abc");
        hc.setStatusCode(StatusCode.PENDING);
        return hc;
    }

}
