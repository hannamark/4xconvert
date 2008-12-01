package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class DiseaseParentTest {
    private static Session sess;
    
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        sess = TestSchema.getSession();
    }
    @Test
    public void getTreeTest() {
        Disease toe = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        Disease heel = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(1));
        Disease foot = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(2));
        Disease leg = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(3));
        
        List<DiseaseParent> tList = toe.getDiseaseParents();
        List<Disease> toeParents = new ArrayList<Disease>();
        for (DiseaseParent t : tList) {
            toeParents.add(t.getParentDisease());
            assertEquals(toe, t.getDisease());
        }
        tList = foot.getDiseaseChildren();
        List<Disease> footChildren = new ArrayList<Disease>();
        for (DiseaseParent t : tList) {
            footChildren.add(t.getDisease());
            assertEquals(foot, t.getParentDisease());
        }
        assertFalse(toeParents.contains(toe));
        assertFalse(toeParents.contains(heel));
        assertTrue(toeParents.contains(foot));
        assertFalse(toeParents.contains(leg));
        
        assertTrue(footChildren.contains(toe));
        assertTrue(footChildren.contains(heel));
        assertFalse(footChildren.contains(foot));
        assertFalse(footChildren.contains(leg));
    }
    
    @Test 
    public void deleteTest() {
        Disease foot = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(2));
        sess.delete(foot);
        sess.flush();
        sess.clear();
        foot = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(2));
        assertNull(foot);
    }
    
    @Test 
    public void mergeTest() {
        Disease foot = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(2));
        assertFalse("new".equals(foot.getNtTermIdentifier()));
        foot.setNtTermIdentifier("new");
        sess.merge(foot);
        sess.flush();
        sess.clear();
        foot = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(2));
        assertTrue("new".equals(foot.getNtTermIdentifier()));
    }
    
    public static DiseaseParent createDiseaseParentObj(Disease disease, Disease parentDisease) {
        DiseaseParent create = new DiseaseParent();
        create.setDisease(disease);
        create.setParentDisease(parentDisease);
        create.setParentDiseaseCode("parentDiseaseCode");
        create.setStatusCode(ActiveInactiveCode.ACTIVE);
        create.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        create.setUserLastCreated("userLastCreated");
        create.setDateLastCreated(new Timestamp(new Date().getTime()));
        create.setUserLastUpdated("userLastUpdated");
        create.setDateLastUpdated(new Timestamp(new Date().getTime()));
        return create;
    }

}
