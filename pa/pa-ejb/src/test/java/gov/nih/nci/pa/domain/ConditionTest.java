package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class ConditionTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createConditionTest() {
       Condition create = new Condition();
       
       create.setCode("1111");
       create.setParentCode(null);
       create.setName("Cancer");
       Serializable cid = session.save(create);
       assertNotNull(cid);
       Condition saved = (Condition) session.load(Condition.class, cid);
       assertNotNull(saved);
       assertEquals("Code  does not match " , create.getCode() , saved.getCode());
       assertEquals("Parent code does not match " , create.getParentCode(), saved.getParentCode());
       System.out.println("end");
       
    }    

}
