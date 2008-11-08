package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class ArmTest {
    Session sess;
    
    @Before
    public void setUp() throws Exception {
        sess = TestSchema.getSession();
        TestSchema.reset1();
        TestSchema.primeData();
    }
    @Test
    public void saveTest() {
        Arm a = new Arm();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(TestSchema.studyParticipationIds.get(0));
        PlannedActivity pa = new PlannedActivity();
        pa.setId(TestSchema.plannedActivityIds.get(0));
        Date now = new Date();
        String user = "Joe";
        
        a.setName("name");
        a.setTypeCode(ArmTypeCode.EXPERIMENTAL);
        a.setDescriptionText("description text");
        a.setDateLastCreated(now);
        a.setDateLastUpdated(now);
        a.setStudyProtocol(sp);
        a.setUserLastCreated(user);
        a.setUserLastUpdated(user);
        a.getInterventions().add(pa);
        sess.save(a);
        sess.flush();
        Long idOut = a.getId();
        assertNotNull(idOut);
        sess.clear();
        
        Arm armOut = (Arm) sess.get(Arm.class, idOut);
        StudyProtocol spOut = (StudyProtocol) sess.get(StudyProtocol.class, sp.getId());
        List<Arm> armsOut = spOut.getArms();
        assertTrue(armsOut.contains(armOut));
        assertEquals(1, armOut.getInterventions().size());
        assertEquals(user, armOut.getUserLastCreated());
        assertEquals(user, armOut.getUserLastUpdated());
        assertEquals(now, armOut.getDateLastCreated());
        assertEquals(now, armOut.getDateLastUpdated());
    }
    @Test
    public void mergeTest() {
        Long armId = TestSchema.armIds.get(0);
        Arm arm = (Arm) sess.get(Arm.class, armId);
        String oldName = arm.getName();
        String newUser = "new user";
        assertNotNull(oldName);
        assertFalse(newUser.equals(arm.getUserLastUpdated()));
        sess.clear();

        arm.setUserLastUpdated(newUser);
        sess.merge(arm);
        sess.flush();
        sess.clear();
        
        Arm armOut = (Arm) sess.get(Arm.class, armId);
        assertTrue(oldName.equals(armOut.getName()));
        assertTrue(newUser.equals(armOut.getUserLastUpdated()));
    }
    @Test
    public void deleteTest() {
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyParticipationIds.get(0));
        List<Arm> armList = sp.getArms();
        int armCount = armList.size();
        assertTrue(0 < armCount);
        sess.delete(armList.get(0));
        sess.flush();
        sess.clear();
        sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyParticipationIds.get(0));
        armList = sp.getArms();
        assertEquals(armCount - 1, armList.size());
    }

}
