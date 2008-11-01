package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyOutcomeMeasureTest {
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
    public void createStudyOutcomeMeasureTest() {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyOutcomeMeasure create = createStudyOutcomeMeasureobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);
        
        StudyOutcomeMeasure saved = new StudyOutcomeMeasure();
        saved = (StudyOutcomeMeasure) session.load(StudyOutcomeMeasure.class, id);
        
        assertEquals("Name does not match " , create.getName() , 
                saved.getName());
        assertEquals("PrimaryIndicator does not match " , create.getPrimaryIndicator() , 
                saved.getPrimaryIndicator());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    }
    /**
     * 
     * @param sp StudyProtocol
     * @return StudyOutcomeMeasure
     */
    public static StudyOutcomeMeasure createStudyOutcomeMeasureobj(StudyProtocol sp) {
        StudyOutcomeMeasure create = new StudyOutcomeMeasure();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setName("StudyOutcomeMeasure");
        create.setStudyProtocol(sp);
        create.setPrimaryIndicator(Boolean.TRUE);
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
