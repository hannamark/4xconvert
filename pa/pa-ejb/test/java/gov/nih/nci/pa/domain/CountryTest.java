package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


public class CountryTest {
    
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
    public void createCountryTest() {
        Session session = TestSchema.getSession();
        Country create = createCountryObj();
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        Country saved = (Country) session.load(Country.class, create.getId());
        assertNotNull(saved.getId());
        assertEquals("Country id does not match " , create.getId(), saved.getId());
        assertEquals("Country Name does not match " , create.getName(), saved.getName());
        
    }
    
    public static Country createCountryObj() {
        Country c1 = new Country();
        c1.setAlpha2("CA");
        c1.setAlpha3("CAM");
        c1.setName("Cayman Islands");
        return c1;
        
    }
    

}
