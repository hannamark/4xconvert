/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class AbstractionCompletionActionTest extends AbstractPaActionTest{

    AbstractionCompletionAction abstractionCompletionAction;
    @Before
    public void setUp() throws PAException {
        abstractionCompletionAction = new AbstractionCompletionAction();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        
     }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractionCompletionAction#query()}.
     */
    @Test
    public void testQuery() {
        String result = abstractionCompletionAction.query();
        assertEquals("success", result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractionCompletionAction#generateXML()}.
     */
    @Test
    public void testGenerateXML() {
        String result = abstractionCompletionAction.generateXML();
        assertEquals("displayXML", result);
        
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.AbstractionCompletionAction#viewTSR()}.
     */
    @Test
    public void testViewTSR() {
        String result = abstractionCompletionAction.viewTSR();
        assertEquals("success", result);
    
    }

}
