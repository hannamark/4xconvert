package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.ResponsibilityCode;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */

public class StudyCoordinatingCenterRoleTest extends CommonTest {
    
    /**
     * 
     */
//    @Test
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
        
        StudyCoordinatingCenter scc = new StudyCoordinatingCenter();
        scc.setOrganization(o);
        scc.setStudyProtocol(sp);
        Serializable sccid = session.save(scc);
        
        StudyCoordinatingCenterRole create = new StudyCoordinatingCenterRole();
        create.setStudyCoordinatingCenter(scc);
        create.setResponsibilityCode(ResponsibilityCode.REGISTRATION_MANAGEMENT);
        
        Serializable id = session.save(create);
        
        
        StudyCoordinatingCenterRole saved = (StudyCoordinatingCenterRole) 
                    session.load(StudyCoordinatingCenterRole.class, id);
        assertEquals("Responsibility code does not match  " , 
                create.getResponsibilityCode(), saved.getResponsibilityCode());
        assertEquals("Study Coordinating center does not match  " , 
                create.getStudyCoordinatingCenter(), saved.getStudyCoordinatingCenter());
        
    }
    /**
     * 
     */
    //@Test
    public void leadOrganization() {

        /**
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
        
        StudyCoordinatingCenter scc = new StudyCoordinatingCenter();
        scc.setOrganization(o);
        scc.setStudyProtocol(sp);
        Serializable sccid = session.save(scc);
        
        StudyCoordinatingCenterRole create = new StudyCoordinatingCenterRole();
        create.setStudyCoordinatingCenter(scc);
        create.setResponsibilityCode(ResponsibilityCode.PROTOCOL_MANAGEMENT);
        
        Serializable id = session.save(create);
        
        
        StudyCoordinatingCenterRole saved = (StudyCoordinatingCenterRole) 
                    session.load(StudyCoordinatingCenterRole.class, id);
        assertEquals("Responsibility code does not match  " , 
                create.getResponsibilityCode(), saved.getResponsibilityCode());
        assertEquals("Study Coordinating center does not match  " , 
                create.getStudyCoordinatingCenter(), saved.getStudyCoordinatingCenter());
        
        
        //Select distinct organization from StudyCoordinatingCenter
         
         
        StringBuffer hql = new StringBuffer();
        hql.append(" Select organization from StudyCoordinatingCenter as scc  "
                    
                    + " join scc.studyCoordinatingCenterRoles as sos  "
                   + " where  sos.responsibilityCode = '" + ResponsibilityCode.PROTOCOL_MANAGEMENT + "'");
                   
        
        */
        String hql = "from StudyCoordinatingCenter as sc ";
         hql = hql + " inner join sc.studyCoordinatingCenterRoles as sccr ";
         hql = hql + " where sccr.responsibilityCode = 'PROTOCOL_MANAGEMENT'";
         hql = hql + " group by sc.organization.id";
        List organizations = session.createQuery(hql).list();

        System.out.println(" organizations  " + organizations.size());
    }

    
}
