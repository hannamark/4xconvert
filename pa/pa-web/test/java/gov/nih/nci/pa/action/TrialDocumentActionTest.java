/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class TrialDocumentActionTest extends AbstractPaActionTest{

    TrialDocumentAction trialDocumentAction;
    TrialDocumentWebDTO trialDocumentWebDTO;
    StudyProtocolQueryDTO spDTO; 
    
    @Before
    public void setUp() throws PAException {
        trialDocumentAction = new TrialDocumentAction();
        trialDocumentWebDTO = new TrialDocumentWebDTO();
        trialDocumentWebDTO.setTypeCode("");
        trialDocumentWebDTO.setFileName("");
        trialDocumentAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
                
        spDTO = new StudyProtocolQueryDTO();
        spDTO.setStudyProtocolType("ObservationalStudyProtocol");
        spDTO.setNciIdentifier("nci");
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        getSession().setAttribute(Constants.TRIAL_SUMMARY, spDTO);
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#query()}.
     */
    @Test
    public void testQuery() {
        String result = trialDocumentAction.query();
        assertEquals("error",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#create()}.
     */
    @Test
    public void testCreate() {
       String result = trialDocumentAction.create();
       assertEquals("input",result);
       assertTrue(trialDocumentAction.hasFieldErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#saveFile()}.
     */
    @Test
    public void testSaveFile() {
        
        String result = trialDocumentAction.saveFile();
        assertEquals("error",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#edit()}.
     */
    @Test
    public void testEdit() {
        String result = trialDocumentAction.edit();
        assertEquals("input",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#update()}.
     */
    @Test
    public void testUpdate() {
        String result = trialDocumentAction.update();
        assertEquals("input",result);
        assertTrue(trialDocumentAction.hasFieldErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.TrialDocumentAction#delete()}.
     */
    @Test
    public void testDelete() {
        String result = trialDocumentAction.delete();
        assertEquals("delete",result);
        assertTrue(trialDocumentAction.hasFieldErrors());
    }

}
