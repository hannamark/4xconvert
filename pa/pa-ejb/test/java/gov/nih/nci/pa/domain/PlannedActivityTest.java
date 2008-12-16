/**
 * 
 */
package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class PlannedActivityTest {
    Session sess;
    
    @Before
    public void setUp() throws Exception {
        sess = TestSchema.getSession();
        TestSchema.reset1();
        TestSchema.primeData();
    }
    @Test
    public void getTest() {
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        assertNotNull(sp);
        List<PlannedActivity> paList = sp.getPlannedActivities();
        assertFalse(paList.isEmpty());
        Long paId = paList.get(0).getId();
        sess.flush();
        PlannedActivity pa = (PlannedActivity) sess.get(PlannedActivity.class, paId);
        assertNotNull(pa);
    }
    @Test
    public void addTest() {
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        assertNotNull(sp);
        int oldCount = sp.getPlannedActivities().size();
        Intervention inv = new Intervention();
        inv.setId(TestSchema.interventionIds.get(0));
        PlannedActivity pa = new PlannedActivity();
        pa.setCategoryCode(ActivityCategoryCode.INTERVENTION);
        pa.setDateLastUpdated(new Date());
        pa.setIntervention(inv);
        pa.setLeadProductIndicator(true);
        pa.setStudyProtocol(sp);
        pa.setSubcategoryCode(ActivitySubcategoryCode.DIETARY_SUPPLEMENT);
        pa.setUserLastUpdated("Joe");
        sess.saveOrUpdate(pa);
        sess.flush();
        sess.clear();
        sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        assertEquals(oldCount + 1, sp.getPlannedActivities().size());
    }
    @Test
    public void updateTest() {
        String tName = "new name";
        Intervention inv = (Intervention) sess.get(Intervention.class, TestSchema.interventionIds.get(0));
        assertFalse(tName.equals(inv.getName()));
        inv.setName("new name");
        sess.saveOrUpdate(inv);
        sess.flush();
        sess.clear();
        Intervention inv2 = (Intervention) sess.get(Intervention.class, TestSchema.interventionIds.get(0));
        assertTrue(tName.equals(inv2.getName()));
    }
    @Test
    public void deleteTest() {
        PlannedActivity pa2 = (PlannedActivity) sess.get(PlannedActivity.class, TestSchema.plannedActivityIds.get(0));
        assertNotNull(pa2);
        for (Long armId : TestSchema.armIds) {
            Arm arm = (Arm) sess.get(Arm.class, armId);
            arm.setInterventions(null);
            sess.update(arm);
        }
        sess.delete(pa2);
        sess.flush();
        PlannedActivity pa3 = (PlannedActivity) sess.get(PlannedActivity.class, TestSchema.plannedActivityIds.get(0));
        assertNull(pa3);
    }
    @Test
    public void armsAssociationTest() {
        Long spId = TestSchema.studyProtocolIds.get(0);
        StudyProtocol sp = (StudyProtocol) sess.get(StudyProtocol.class, spId);
        assertNotNull(sp);
        List<PlannedActivity> paList = sp.getPlannedActivities();
        assertFalse(paList.isEmpty());
        Long paId = paList.get(0).getId();
        sess.flush();
        PlannedActivity pa = (PlannedActivity) sess.get(PlannedActivity.class, paId);
        assertNotNull(pa);
        Collection<Arm> armList = pa.getArms();
        assertTrue(armList.size() > 0);
        ArrayList<Long> spIds = new ArrayList<Long>();
        for (Arm arm : armList) {
            sp = arm.getStudyProtocol();
            spIds.add(sp.getId());
        }
        assertTrue(spIds.contains(spId));
    }
}
