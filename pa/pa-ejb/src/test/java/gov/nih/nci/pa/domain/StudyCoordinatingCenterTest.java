package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;

import java.io.Serializable;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyCoordinatingCenterTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createStudyCoordinatingCenter() {
        StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Breast Cancer..");
        sp.setMonitorCode(MonitorCode.CCR);
        sp.setPhaseCode(PhaseCode.I);
        Serializable spid = session.save(sp);
        assertNotNull(spid);
        
        Organization o = new Organization();
        o.setName("Mayo University");
        o.setNciInstituteCode("P001");
        Serializable oid = session.save(o);
        assertNotNull(oid);
        
        StudyCoordinatingCenter create = new StudyCoordinatingCenter();
        create.setOrganization(o);
        create.setStudyProtocol(sp);
        
        Serializable id = session.save(create);
        StudyCoordinatingCenter saved = (StudyCoordinatingCenter) session.load(StudyCoordinatingCenter.class, id);
        
        assertEquals("Organization does match  " , create.getOrganization(), saved.getOrganization());
        assertEquals("Study Protocol Does not match  " , create.getStudyProtocol(), saved.getStudyProtocol());
        
        System.out.println("end");
        
    }
    

}
