package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyOverallStatusTest  {

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
    public void createStudyOverallStatusTest() {
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyOverallStatus create = createStudyOverallStatusobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);

        StudyOverallStatus saved = new StudyOverallStatus();
        saved = (StudyOverallStatus) session.load(StudyOverallStatus.class, id);
        assertEquals("Study Status code does not match " , create.getStatusCode() , 
                saved.getStatusCode());
        assertEquals("Study Status date does not match " , create.getStatusDate() , 
                saved.getStatusDate());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
            
    }
    
    /**
     * 
     * @param sp StudyProtocol
     * @return StudyOverallStatus
     */
    public static StudyOverallStatus createStudyOverallStatusobj(StudyProtocol sp) {
        StudyOverallStatus create = new StudyOverallStatus();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setStatusCode(StudyStatusCode.ACTIVE);
        create.setStatusDate(now);
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;

    }    
    

}
