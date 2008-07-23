package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.ResponsibilityCode;
import gov.nih.nci.pa.test.util.TestSchema;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */

public class StudyCoordinatingCenterRoleTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    /**
     * 
     */
    @Test
    public void createStudyCoordinatingCenterRole() {
        Session session  = TestSchema.getSession();
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        
        Organization o = OrganizationTest.OrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        
        StudyCoordinatingCenter scc =  StudyCoordinatingCenterTest.createStudyCoordinatingCenterObj(sp, o);
        TestSchema.addUpdObject(scc);
        
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
    
    public static StudyCoordinatingCenterRole createStudyCoordinatingCenterRoleObj(StudyCoordinatingCenter scc) {
        StudyCoordinatingCenterRole create = new StudyCoordinatingCenterRole();
        create.setStudyCoordinatingCenter(scc);
        create.setResponsibilityCode(ResponsibilityCode.REGISTRATION_MANAGEMENT);
        return create ;
        
    }

    
}
