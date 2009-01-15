package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyMilestoneTest {
    Session sess;
    
    @Before
    public void setUp() throws Exception {
        sess = TestSchema.getSession();
        TestSchema.reset1();
        TestSchema.primeData();
    }
    @Test
    public void mergeTest() {
        StudyMilestone m = new StudyMilestone();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(TestSchema.studyParticipationIds.get(0));
        PlannedActivity pa = new PlannedActivity();
        pa.setId(TestSchema.plannedActivityIds.get(0));
        Date now = new Date();
        String user = "Joe";
        
        m.setCommentText("12345");
        m.setMilestoneCode(MilestoneCode.INITIAL_ABSTRACTION_VERIFY);
        m.setMilestoneDate(new Timestamp(now.getTime()));
        m.setDateLastCreated(now);
        m.setDateLastUpdated(now);
        m.setStudyProtocol(sp);
        m.setUserLastCreated(user);
        m.setUserLastUpdated(user);
        m = (StudyMilestone) sess.merge(m);
        sess.flush();
        
        Long idOut = m.getId();
        assertNotNull(idOut);
        sess.clear();
        
        StudyMilestone armOut = (StudyMilestone) sess.get(StudyMilestone.class, idOut);
        StudyProtocol spOut = (StudyProtocol) sess.get(StudyProtocol.class, sp.getId());
        List<StudyMilestone> armsOut = spOut.getStudyMilestones();
        assertTrue(armsOut.contains(armOut));
        assertEquals(user, armOut.getUserLastCreated());
        assertEquals(user, armOut.getUserLastUpdated());
        assertEquals(now, armOut.getDateLastCreated());
        assertEquals(now, armOut.getDateLastUpdated());
    }
    @Test
    public void deleteTest() {
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyParticipationIds.get(0));
        List<StudyMilestone> smList = sp.getStudyMilestones();
        int oldCount = smList.size();
        assertTrue(0 < oldCount);
        sess.delete(smList.get(0));
        sess.flush();
        sess.clear();
        sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyParticipationIds.get(0));
        smList = sp.getStudyMilestones();
        assertEquals(oldCount - 1, smList.size());
    }

}
