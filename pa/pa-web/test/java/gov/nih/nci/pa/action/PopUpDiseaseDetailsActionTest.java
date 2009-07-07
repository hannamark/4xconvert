/**
 * 
 */
package gov.nih.nci.pa.action;

import org.junit.Before;
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
    @Test(expected=Exception.class)
    public void testExecute() throws Exception{
            popUpDiseaseDetailsAction.execute();
        
        
    }

}
