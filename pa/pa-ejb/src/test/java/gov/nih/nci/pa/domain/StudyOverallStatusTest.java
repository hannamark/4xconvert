package gov.nih.nci.pa.domain;

import java.io.Serializable;

import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyOverallStatusTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createStudyProtocolTest() {
        
        StudyProtocol sp = new StudyProtocol();
        StudyOverallStatus create = new StudyOverallStatus();
        
        try {
            sp.setOfficialTitle("Breast Cancer..");
            sp.setMonitorCode(MonitorCode.CCR);
            sp.setPhaseCode(PhaseCode.I);
            java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
            Serializable spid = session.save(sp);
            StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
            assertNotNull(spid);
            create.setStudyProtocol(spSaved);
            create.setStudyStatusCode(StudyStatusCode.ACTIVE);
            create.setStudyStatusDate(now);
            Serializable id = session.save(create);
            StudyOverallStatus saved = new StudyOverallStatus();
            saved = (StudyOverallStatus) session.load(StudyOverallStatus.class, id);
            assertEquals("Study Status code does not match " , create.getStudyStatusCode() , 
                    saved.getStudyStatusCode());
            assertEquals("Study Status date does not match " , create.getStudyStatusDate() , 
                    saved.getStudyStatusDate());
            System.out.println("end");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

}
