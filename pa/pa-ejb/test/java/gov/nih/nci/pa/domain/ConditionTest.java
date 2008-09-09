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
public class ConditionTest {
    
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
    public void createConditionTest() {
       Condition create = createConditionObj();
       Session session = TestSchema.getSession();
       TestSchema.addUpdObject(create);
       Serializable cid = create.getId();
       assertNotNull(cid);
       Condition saved = (Condition) session.load(Condition.class, cid);
       assertNotNull(saved);
       assertEquals("Code  does not match " , create.getCode() , saved.getCode());
       assertEquals("Parent code does not match " , create.getParentCode(), saved.getParentCode());
       
    }    
    
    /**
     * 
     * @return Condition
     */
    public static Condition createConditionObj() {
        Condition create = new Condition();
        create.setCode("1111");
        create.setParentCode(null);
        create.setName("Cancer");
        return create;
        
    }

}
