package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class StudyCoordinatingCenterTest  {
    
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
    public void createStudyCoordinatingCenter() {
        Session session  = TestSchema.getSession();

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Serializable spid = sp.getId();
        assertNotNull(spid);
        
        Organization o = OrganizationTest.organizationObj();
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        
        StudyCoordinatingCenter create = createStudyCoordinatingCenterObj(sp , o);
        TestSchema.addUpdObject(create);
        StudyCoordinatingCenter saved = (StudyCoordinatingCenter) 
            session.load(StudyCoordinatingCenter.class, create.getId());
        
        assertEquals("Organization does match  " , create.getOrganization().getName(), 
                saved.getOrganization().getName());
        assertEquals("Study Protocol Does not match  " , create.getStudyProtocol().getId(), 
                saved.getStudyProtocol().getId());
        assertEquals("User Id  does not match " , create.getUserLastUpdated(), saved.getUserLastUpdated());
        assertEquals("Date updated does not match " , create.getDateLastUpdated(), saved.getDateLastUpdated());
        
        
    }
    
    /**
     * 
     * @param sp StudyProtocol
     * @param o Organization
     * @return StudyCoordinatingCenter
     */
    public static StudyCoordinatingCenter createStudyCoordinatingCenterObj(StudyProtocol sp , Organization o) {
        StudyCoordinatingCenter create = new StudyCoordinatingCenter();
        create.setUserLastUpdated("abstractor");
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setDateLastUpdated(now);
        create.setOrganization(o);
        create.setStudyProtocol(sp);
        return create;
        
    }
    

}
