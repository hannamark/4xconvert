package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.test.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyCoordinatingCenterTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    /**
     * 
     */
    @Test
    public void createStudyCoordinatingCenter() {
        Session session  = TestSchema.getSession();

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);

        Serializable spid = sp.getId();
        assertNotNull(spid);
        
        Organization o = OrganizationTest.OrganizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        
        StudyCoordinatingCenter create = createStudyCoordinatingCenterObj(sp , o);
        
        TestSchema.addUpdObject(create);
        StudyCoordinatingCenter saved = (StudyCoordinatingCenter) session.load(StudyCoordinatingCenter.class, create.getId());
        
        assertEquals("Organization does match  " , create.getOrganization().getName(), saved.getOrganization().getName());
        assertEquals("Study Protocol Does not match  " , create.getStudyProtocol().getId(), saved.getStudyProtocol().getId());
        
        
    }
    
    public static StudyCoordinatingCenter createStudyCoordinatingCenterObj(StudyProtocol sp , Organization o ) {
        StudyCoordinatingCenter create = new StudyCoordinatingCenter();
        create.setOrganization(o);
        create.setStudyProtocol(sp);
        return create ;
        
    }
    

}
