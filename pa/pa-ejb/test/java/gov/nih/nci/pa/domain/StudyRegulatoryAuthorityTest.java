package gov.nih.nci.pa.domain;

import static org.junit.Assert.*;
import gov.nih.nci.pa.util.TestSchema;

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
public class StudyRegulatoryAuthorityTest  {
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    /**
     * test method.
     */
    @Test
    public void createRegulatoryAuthority() {
        Session session  = TestSchema.getSession();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        //
        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);
        assertNotNull(c.getId());
        //
        RegulatoryAuthority ra = RegulatoryAuthorityTest.createRegulatoryObj(c);
        TestSchema.addUpdObject(ra);
        assertNotNull(ra.getId());

        StudyRegulatoryAuthority create = createStudyRegulatoryAuthorityObj(ra , sp);
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        StudyRegulatoryAuthority saved = 
            (StudyRegulatoryAuthority) session.load(StudyRegulatoryAuthority.class , create.getId());
        //
        assertEquals(" Id does not match " , create.getId(), saved.getId());
        assertEquals(" Study Protocol Id does not match " , create.getStudyProtocol().getId() , 
                    saved.getStudyProtocol().getId());
        assertEquals(" Regulatory Authority Id does not match " , create.getRegulatoryAuthority().getId() , 
                saved.getRegulatoryAuthority().getId());

    }
    
    public static StudyRegulatoryAuthority
        createStudyRegulatoryAuthorityObj(RegulatoryAuthority ra , StudyProtocol sp) {
        StudyRegulatoryAuthority sra = new StudyRegulatoryAuthority();
        sra.setRegulatoryAuthority(ra);
        sra.setStudyProtocol(sp);
        sra.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sra.setDateLastUpdated(now);
        return sra;
    }

}
