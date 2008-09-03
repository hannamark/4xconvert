package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * 
 */
public class RegulatoryAuthorityTest {
    /**
     * 
     * @throws Exception e
     */
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    

    /**
     * Test Method.
     */
    @Test
    public void createRegulatoryAuthority() {            
        Session session  = TestSchema.getSession();
        Country c1 = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c1);
        assertNotNull(c1.getId());

        RegulatoryAuthority create = createRegulatoryObj(c1);
        TestSchema.addUpdObject(create);
        RegulatoryAuthority saved = (RegulatoryAuthority) session.load(RegulatoryAuthority.class, create.getId());
        assertEquals("Authoruty Name does not match", create.getAuthorityName() , saved.getAuthorityName());
        assertEquals("Country Id does not match", create.getCountry().getId() , saved.getCountry().getId());
        
    }
    
    public static RegulatoryAuthority createRegulatoryObj(Country c){
        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("BWI reg body");
        ra.setCountry(c);
        ra.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        ra.setDateLastUpdated(now);
        
        return ra;
        
    }
}
