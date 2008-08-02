package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.test.util.TestSchema;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyOverallStatusTest {

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
    //@Test
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
    /**
     * 
     */
    @Test
    public void createStudyOverallStatusSubQueryTest() {
    
        Session session = TestSchema.getSession();

        String hql = "from StudyOverallStatus as sos " 
            + " where id in ( select max(id) from StudyOverallStatus as sos1 " 
              + "                where sos.studyProtocol = sos1.studyProtocol) ";

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyOverallStatus create = createStudyOverallStatusobj(sp);

        TestSchema.addUpdObject(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        
        create = createStudyOverallStatusobj(sp);
        TestSchema.addUpdObject(create);

//        StudyOverallStatus saved = new StudyOverallStatus();
//        saved = (StudyOverallStatus) session.load(StudyOverallStatus.class, id);
        
        List l = session.createQuery(hql).list();
        System.out.println(" size " + l.size());
        
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
        create.setStudyStatusCode(StudyStatusCode.ACTIVE);
        create.setStudyStatusDate(now);
        return create;

    }
    
    

}
