package gov.nih.nci.pa.domain;

import java.io.Serializable;

import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyOverallStatusTest {

    
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
        
        try {

            TestSchema.addUpdObject(sp);
            Serializable spid = sp.getId();
            StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
            assertNotNull(spid);

            TestSchema.addUpdObject(create);
            Serializable id = create.getId();

            StudyOverallStatus saved = new StudyOverallStatus();
            saved = (StudyOverallStatus) session.load(StudyOverallStatus.class, id);
            assertEquals("Study Status code does not match " , create.getStudyStatusCode() , 
                    saved.getStudyStatusCode());
            assertEquals("Study Status date does not match " , create.getStudyStatusDate() , 
                    saved.getStudyStatusDate());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public StudyOverallStatus createStudyOverallStatusobj(StudyProtocol sp) {
        StudyOverallStatus create = new StudyOverallStatus();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setStudyStatusCode(StudyStatusCode.ACTIVE);
        create.setStudyStatusDate(now);
        return create ;

    }
    
    

}
