package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.ResponsibilityCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */

public class StudyCoordinatingCenterRoleTest  {
    
    /**
     * 
     * @throws Exception e
     */
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
        
        Organization o = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        
        StudyCoordinatingCenter scc =  StudyCoordinatingCenterTest.createStudyCoordinatingCenterObj(sp, o);
        TestSchema.addUpdObject(scc);
        
        StudyCoordinatingCenterRole create = createStudyCoordinatingCenterRoleObj(scc , 
                ResponsibilityCode.PROTOCOL_MANAGEMENT);
        TestSchema.addUpdObject(create);
        
        Serializable id = create.getId();
        assertNotNull(id);
        
        StudyCoordinatingCenterRole saved = (StudyCoordinatingCenterRole) 
                    session.load(StudyCoordinatingCenterRole.class, id);
        assertEquals("Responsibility code does not match  " , 
                create.getResponsibilityCode(), saved.getResponsibilityCode());
        assertEquals("Study Coordinating center does not match  " , 
                create.getStudyCoordinatingCenter().getId(), 
                saved.getStudyCoordinatingCenter().getId());
        assertEquals("User Id  does not match " , create.getUserLastUpdated(), saved.getUserLastUpdated());
        assertEquals("Date updated does not match " , create.getDateLastUpdated(), saved.getDateLastUpdated());
      
        
    }
    
    /**
     * 
     * @param scc StudyCoordinatingCenterRole
     * @param rrc rrc
     * @return  StudyCoordinatingCenterRole
     */
    public static StudyCoordinatingCenterRole createStudyCoordinatingCenterRoleObj(
                StudyCoordinatingCenter scc , ResponsibilityCode rrc) {
        StudyCoordinatingCenterRole create = new StudyCoordinatingCenterRole();
        create.setStudyCoordinatingCenter(scc);
        create.setResponsibilityCode(rrc);
        create.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setDateLastUpdated(now);

        return create;
        
    }

    
}
