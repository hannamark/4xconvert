package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.YesNoCode;

import java.io.Serializable;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyCondtionTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createStudyCondition() {
        StudyProtocol screate = new StudyProtocol();
        screate.setOfficialTitle("Caner for kids ");
        
        Serializable sid =  session.save(screate);
        assertNotNull(sid);

        Condition condition = new Condition();
        condition.setCode("11111");
        Serializable cid =  session.save(condition);
        assertNotNull(cid);
        
        StudyCondition create = new StudyCondition();
        create.setLeadIndicator(YesNoCode.YES);
        create.setStudyProtocol(screate);
        create.setCondition(condition);
        
        Serializable id =  session.save(create);
        assertNotNull(sid);
        System.out.println("end");

        
    }

}
