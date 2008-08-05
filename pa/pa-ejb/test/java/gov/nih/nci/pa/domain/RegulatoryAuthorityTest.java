package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        //
        Country c1 = new Country();
        c1.setAlpha2("CA");
        c1.setAlpha3("CAM");
        c1.setName("Cayman Islands");
        TestSchema.addUpdObject(c1);
        //
        RegulatoryAuthority authority = new RegulatoryAuthority();
        authority.setAuthorityName("BWI reg body");
        authority.setCountry(c1);
        TestSchema.addUpdObject(authority);
        //
        RegulatoryAuthority saved = (RegulatoryAuthority) session.load(RegulatoryAuthority.class, authority.getId());
        //
        assertEquals("BWI reg body", saved.getAuthorityName());
        assertEquals("Cayman Islands", saved.getCountry().getName());
    }
}
