package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
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
        Person p = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        Session session  = TestSchema.getSession();
        HealthCareProvider hc = createHealthCareProviderObj(p);
        TestSchema.addUpdObject(hc);
        Serializable id = hc.getId();
        assertNotNull(id);
        HealthCareProvider saved = (HealthCareProvider) session.load(HealthCareProvider.class, id);
        assertEquals("Healcare Provider does not match " , hc.getId(), saved.getId());
        assertEquals("Person  does not match " , hc.getPerson().getId(), saved.getPerson().getId());
        

    }
    
    /**
     * 
     * @param p Person
     * @return HealthCareProvider
     */
    public static  HealthCareProvider createHealthCareProviderObj(Person p) {
        HealthCareProvider hc = new HealthCareProvider();
        hc.setPerson(p);
        return hc;
    }

}
