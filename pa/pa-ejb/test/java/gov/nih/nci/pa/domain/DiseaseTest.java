package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class DiseaseTest {
    Session sess;
    
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
    public void getTest() {
        Disease d = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        assertNotNull(d.getId());
    }
    @Test
    public void deleteTest() {
        Disease d = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        sess.delete(d);
        sess.flush();
        sess.clear();
        d = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(0));
        assertNull(d);
    }
    @Test 
    public void mergeTest() {
        Disease xxx = createDiseaseObj("new disease");
        sess.merge(xxx);
        assertTrue("new disease".equals(xxx.getPreferredName()));
    }
    public static Disease createDiseaseObj(String preferredName) {
        Disease create = new Disease();
        create.setDiseaseCode("diseaseCode");
        create.setMenuDisplayName("menuDisplayName");
        create.setNtTermIdentifier("ntTermIdentifier");
        create.setPreferredName(preferredName);
        create.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        create.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        create.setUserLastCreated("userLastCreated");
        create.setDateLastCreated(new Timestamp(new Date().getTime()));
        create.setUserLastUpdated("userLastUpdated");
        create.setDateLastUpdated(new Timestamp(new Date().getTime()));
        return create;
    }
}
