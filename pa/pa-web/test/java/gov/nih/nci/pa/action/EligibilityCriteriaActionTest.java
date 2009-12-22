package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

public class EligibilityCriteriaActionTest extends AbstractPaActionTest {

    EligibilityCriteriaAction eligibilityCriteriaAction;
    StudyProtocolQueryDTO spDTO;  
    ISDesignDetailsWebDTO dto;
    @Before
    public void setUp(){
        eligibilityCriteriaAction = new EligibilityCriteriaAction();
       
        spDTO = new StudyProtocolQueryDTO();
        spDTO.setStudyProtocolType("ObservationalStudyProtocol");
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        getSession().setAttribute(Constants.TRIAL_SUMMARY, spDTO);
        
        dto = new ISDesignDetailsWebDTO();
        eligibilityCriteriaAction.setWebDTO(dto);
    }
    
    @Test
    public void testQuery() {
        String result = eligibilityCriteriaAction.query();
        assertEquals("eligibility", result);
    }

    @Test
    public void testSave() {
        eligibilityCriteriaAction.save();
        assertTrue(eligibilityCriteriaAction.hasFieldErrors());
    }

    @Test
    public void testInput() {
        String result = eligibilityCriteriaAction.input();
        assertEquals("eligibilityAdd", result);
    }

    @Test
    public void testCreate() {
        String result = eligibilityCriteriaAction.create();
        assertEquals("eligibilityAdd", result);
    }

    @Test
    public void testEdit() {
        eligibilityCriteriaAction.setId(1L);
        String result = eligibilityCriteriaAction.edit();
        assertEquals("eligibilityAdd", result);
    }

    @Test
    public void testUpdate() {
        eligibilityCriteriaAction.update();
        assertTrue(eligibilityCriteriaAction.hasFieldErrors());
    }

    @Test
    public void testDelete() {
        eligibilityCriteriaAction.setId(null);
       String result =  eligibilityCriteriaAction.delete();
        assertEquals("eligibility", result);
    }

}
