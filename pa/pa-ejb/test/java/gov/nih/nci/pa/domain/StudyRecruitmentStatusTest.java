/**
 * 
 */
package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyRecruitmentStatusTest {
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
    public void createStudyRecruitmentStatusTest() {
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyRecruitmentStatus create = createStudyRecruitmentStatusobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);

        StudyRecruitmentStatus saved = new StudyRecruitmentStatus();
        saved = (StudyRecruitmentStatus) session.load(StudyRecruitmentStatus.class, id);
        assertEquals("Status code does not match " , create.getStatusCode() , 
                saved.getStatusCode());
        assertEquals("Status date does not match " , create.getStatusDate() , 
                saved.getStatusDate());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
            
    }
    
    /**
     * 
     * @param sp StudyProtocol
     * @return StudyRecruitmentStatus
     */
    public static StudyRecruitmentStatus createStudyRecruitmentStatusobj(StudyProtocol sp) {
        StudyRecruitmentStatus create = new StudyRecruitmentStatus();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setStatusCode(StudyRecruitmentStatusCode.RECRUITING_ACTIVE);
        create.setStatusDate(now);
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }    
}
