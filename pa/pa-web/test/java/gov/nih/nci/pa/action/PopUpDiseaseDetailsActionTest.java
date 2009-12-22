/**
 * 
 */
package gov.nih.nci.pa.action;

import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpDiseaseDetailsActionTest extends AbstractPaActionTest{

    private static PopUpDiseaseDetailsAction popUpDiseaseDetailsAction;
    
    @Before
    public void setUp(){
        popUpDiseaseDetailsAction = new PopUpDiseaseDetailsAction();
        
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpDiseaseDetailsAction#execute()}.
     */
    @Test
    public void testExecute() throws Exception{
     getRequest().setupAddParameter("diseaseId", "1");
     assertEquals("success" ,popUpDiseaseDetailsAction.execute());        
    }

}
