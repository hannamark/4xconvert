package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.YesNoCode;
import gov.nih.nci.pa.test.util.TestSchema;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyCondtionTest {

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
    public void createStudyCondition() {
        Session session  = TestSchema.getSession();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();;
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        Condition c = ConditionTest.createConditionObj();
        TestSchema.addUpdObject(c);
        assertNotNull(c.getId());
        
        StudyCondition create = createStudyConditionObj(sp , c);
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        
        StudyCondition saved = (StudyCondition) session.load(StudyCondition.class, create.getId());
        assertEquals("Id does not match", create.getId() , saved.getId());
        


        
    }
    
    public static StudyCondition createStudyConditionObj(StudyProtocol sp , Condition c) {
        StudyCondition create = new StudyCondition();
        create.setLeadIndicator(YesNoCode.YES);
        create.setStudyProtocol(sp);
        create.setCondition(c);
        return create;
        
    }

}
