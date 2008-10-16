package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StratumGroupTest {
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
    public void createStratumGroupTest() {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StratumGroup create = createStratumGroupobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);
        
        StratumGroup saved = new StratumGroup();
        saved = (StratumGroup) session.load(StratumGroup.class, id);
        assertEquals("Document type code does not match " , create.getDescription() , 
                saved.getDescription());
        assertEquals("Document FileName does not match " , create.getGroupNumberText() , 
                saved.getGroupNumberText());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    }
    /**
     * 
     * @param sp StudyProtocol
     * @return Document
     */
    public static StratumGroup createStratumGroupobj(StudyProtocol sp) {
        StratumGroup create = new StratumGroup();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setDescription("Description");
        create.setGroupNumberText("Code");
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
