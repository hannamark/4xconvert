package gov.nih.nci.pa.domain;

import static org.junit.Assert.*;
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
public class StudyRegulatoryAuthorityTest {
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    @Test
    public void createRegulatoryAuthority(){
    	Session session  = TestSchema.getSession();
    	StudyProtocol create = StudyProtocolTest.createStudyProtocolObj();
    	TestSchema.addUpdObject(create);
    	//
    	Country c2 = new Country();
    	c2.setAlpha2("IN");
    	c2.setAlpha3("IND");
    	c2.setName("INDIA");
    	TestSchema.addUpdObject(c2);
    	//
    	RegulatoryAuthority authority0 = new RegulatoryAuthority();
    	authority0.setAuthorityName("AIMMS_IND_456XSD1QA34");
    	authority0.setCountry(c2);
    	TestSchema.addUpdObject(authority0);
    	//
        StudyRegulatoryAuthority studyAuthority = new StudyRegulatoryAuthority();
        studyAuthority.setRegulatoryAuthorityID(authority0.getId());
        studyAuthority.setStudyProtocolID(create.getId());
        TestSchema.addUpdObject(studyAuthority);
        //
    	StudyRegulatoryAuthority saved = (StudyRegulatoryAuthority) session.load(StudyRegulatoryAuthority.class, studyAuthority.getId());    	
    	assertEquals(authority0.getId().longValue(), saved.getRegulatoryAuthorityID());
    	assertEquals(create.getId().longValue(), saved.getRegulatoryAuthorityID());    	
    }

}
