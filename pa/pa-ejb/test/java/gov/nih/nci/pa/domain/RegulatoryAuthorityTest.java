package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Query;
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
        
//        String hql = " select ra.id , c.id from RegulatoryAuthority as ra  "
//            + " join ra.country as c"
//            + " where ra.id = "+create.getId();       
        List<StudyRegulatoryAuthority> queryList = new ArrayList<StudyRegulatoryAuthority>();
        Query query = null;
        //query = session.createQuery(hql);
        //queryList = query.list();
        
        String className = "Country";
        String id = "1077";
        String hql1 = " select val.name  from " + className + " as val where val.id=" + c1.getId();
        query = session.createQuery(hql1);
        queryList = query.list();
        //System.out.println("-------------------------------****************************8"+queryList.get(0));
            
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
