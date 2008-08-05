package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * 
 */
public class CountryTest {
	
    /**"
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    @Test
    public void getCountryTest(){
    	Session session = TestSchema.getSession();
    	Country country = new Country();
    	country.setAlpha2("GM");
    	country.setAlpha3("BMW");
    	country.setName("Munich");
    	TestSchema.addUpdObject(country);
    	Country c = (Country) session.load(Country.class, country.getId());
        assertEquals("Munich" , c.getName());
        assertEquals("GM", c.getAlpha2());
        assertEquals("BMW", c.getAlpha3());
    }

}
