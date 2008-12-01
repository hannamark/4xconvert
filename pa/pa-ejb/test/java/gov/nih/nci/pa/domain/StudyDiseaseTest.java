package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class StudyDiseaseTest {
    Session sess;
    StudyProtocol sp;
    
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        sess = TestSchema.getSession();
        sp = (StudyProtocol) sess.get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void getTest() {
        List<StudyDisease> sdList = sp.getStudyDiseases();
        assertTrue(sdList.size() > 0);
        
        Long sdId = sdList.get(0).getId();
        Boolean lead = sdList.get(0).getLeadDiseaseIndicator();
        sess.clear();
        
        StudyDisease sd = (StudyDisease) sess.get(StudyDisease.class, sdId);
        assertEquals(lead, sd.getLeadDiseaseIndicator());
    }
    
    @Test
    public void deleteTest() {
        List<StudyDisease> sdList = sp.getStudyDiseases();
        assertTrue(sdList.size() > 0);
        
        for (StudyDisease sd : sdList) {
            sess.delete(sd);
        }
        sess.flush();
        sess.refresh(sp);
        sdList = sp.getStudyDiseases();
        assertEquals(0, sdList.size());
    }
    
    @Test
    public void mergeTest() {
        int oldSize = sp.getStudyDiseases().size();
        Disease disease = (Disease) sess.get(Disease.class, TestSchema.diseaseIds.get(1));
        sess.merge(createStudyDiseaseObj(sp, disease));
        sess.flush();
        sess.refresh(sp);
        assertEquals(oldSize + 1, sp.getStudyDiseases().size());
    }
    
    public static StudyDisease createStudyDiseaseObj(StudyProtocol studyProtocol, Disease disease) {
        StudyDisease create = new StudyDisease();
        create.setStudyProtocol(studyProtocol);
        create.setDisease(disease);
        create.setLeadDiseaseIndicator(false);
        create.setUserLastCreated("userLastCreated");
        create.setDateLastCreated(new Timestamp(new Date().getTime()));
        create.setUserLastUpdated("userLastUpdated");
        create.setDateLastUpdated(new Timestamp(new Date().getTime()));
        return create;
    }
}
