package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class InterventionalStudyDesignActionTest extends AbstractPaActionTest {
    InterventionalStudyDesignAction action ;
    @Before
    public void prepare(){
        action = new InterventionalStudyDesignAction();
    }
    @Test
    public void testWebDTOProperty(){
        assertNotNull(action.getWebDTO());
        action.setWebDTO(null);
        assertNull(action.getWebDTO());
    }
    @Test
    public void testSubjectProperty(){
     assertNull(action.getSubject());
     action.setSubject("subject");
     assertNotNull(action.getSubject());
     assertFalse(action.isSubjectChecked());
     action.setSubject("Subject");
     assertTrue(action.isSubjectChecked());
    }
    @Test
    public void testInvestigatorProperty(){
        assertNull(action.getInvestigator());
        action.setInvestigator("investigator");
        assertNotNull(action.getInvestigator());
        assertFalse(action.isInvestigatorChecked());
        action.setInvestigator("Investigator");
        assertTrue(action.isInvestigatorChecked());
    }
    @Test
    public void testCaregiverProperty(){
        assertNull(action.getCaregiver());
        action.setCaregiver("caregiver");
        assertNotNull(action.getCaregiver());
        assertFalse(action.isCaregiverChecked());
        action.setCaregiver("Caregiver");
        assertTrue(action.isCaregiverChecked());
    }
    @Test
    public void testOutcomesassessorProperty(){
     assertNull(action.getOutcomesassessor());
     action.setOutcomesassessor("outcomesassessor");
     assertNotNull(action.getOutcomesassessor());
     assertFalse(action.isOutcomesAssessorChecked());
     action.setOutcomesassessor("Outcomes Assessor");
     assertTrue(action.isOutcomesAssessorChecked());
    }
    @Test
    public void testoutcomeListProperty(){
        assertNull(action.getOutcomeList());
        action.setOutcomeList(new ArrayList<ISDesignDetailsWebDTO>());
        assertNotNull(action.getOutcomeList());
    }
    @Test
    public void testIdProperty(){
        assertNull(action.getId());
        action.setId(1L);
        assertNotNull(action.getId());
    }
    @Test
    public void testPageProperty(){
        assertNull(action.getPage());
        action.setPage("page");
        assertNotNull(action.getPage());
    }
    @Test
    public void testDetailsQuery(){
     getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
     assertEquals("details",action.detailsQuery());   
    }
    @Test
    public void testOutcomeinput(){
     assertEquals("outcomeAdd", action.outcomeinput());   
    }
    @Test
    public void testOutcomeQueryWithResult(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        assertEquals("outcome", action.outcomeQuery());   
    }
    @Test
    public void testOutcomeQueryNoResult(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(2L));
        assertEquals("outcome", action.outcomeQuery());
    }
    @Test
    public void testOutcomeedit(){
        action.setId(1L);
        assertEquals("outcomeAdd", action.outcomeedit());
    }
    @Test
    public void testOutcomecreateErr(){
        assertEquals("outcomeAdd",action.outcomecreate());
    }
    @Test
    public void testOutcomecreate() {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        webDTO.setPrimaryIndicator("yes");
        webDTO.setName("Name");
        webDTO.setTimeFrame("designConfigurationCode");
        webDTO.setSafetyIndicator("1");
        action.setWebDTO(webDTO);
        assertEquals("outcome",action.outcomecreate());
    }
    @Test
    public void testUpdateErr() {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        webDTO.setPrimaryIndicator("");
        webDTO.setPrimaryPurposeCode("");
        webDTO.setPhaseCode("");
        webDTO.setDesignConfigurationCode("");
        webDTO.setNumberOfInterventionGroups("1ds");
        webDTO.setBlindingSchemaCode("");
        webDTO.setAllocationCode("");
        webDTO.setMaximumTargetAccrualNumber("maximumTargetAccrualNumber");
        webDTO.setName("Name");
        webDTO.setTimeFrame("designConfigurationCode");
        webDTO.setSafetyIndicator("1");
        action.setWebDTO(webDTO);

        assertEquals("details",action.update());
    }
    @Test
    public void testUpdateErrOther() {
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        webDTO.setPrimaryIndicator("");
        webDTO.setPrimaryPurposeCode("Other");
        webDTO.setPhaseCode("Other");
        webDTO.setDesignConfigurationCode("");
        webDTO.setNumberOfInterventionGroups("");
        webDTO.setBlindingSchemaCode("");
        webDTO.setAllocationCode("");
        webDTO.setMaximumTargetAccrualNumber("");
        webDTO.setName("Name");
        webDTO.setTimeFrame("designConfigurationCode");
        webDTO.setSafetyIndicator("1");
        action.setWebDTO(webDTO);

        assertEquals("details",action.update());
    }
    @Test
    public void testUpdate(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        webDTO.setPrimaryIndicator("yes");
        webDTO.setPrimaryPurposeCode(PrimaryPurposeCode.TREATMENT.getDisplayName());
        webDTO.setPhaseCode(PhaseCode.I.getDisplayName());
        webDTO.setDesignConfigurationCode("designConfigurationCode");
        webDTO.setNumberOfInterventionGroups("1");
        webDTO.setBlindingSchemaCode("blindingSchemaCode");
        webDTO.setAllocationCode("allocationCode");
        webDTO.setMaximumTargetAccrualNumber("1");
        webDTO.setName("Name");
        webDTO.setTimeFrame("designConfigurationCode");
        webDTO.setSafetyIndicator("1");
        action.setWebDTO(webDTO);
        action.setInvestigator("Investigator");
        action.setOutcomesassessor("Outcomes Assessor");
        action.setCaregiver("Caregiver");
        action.setSubject("Subject");
        assertEquals("details",action.update());

    }
    @Test
    public void testUpdateS(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        webDTO.setPrimaryIndicator("yes");
        webDTO.setPrimaryPurposeCode(PrimaryPurposeCode.TREATMENT.getDisplayName());
        webDTO.setPhaseCode(PhaseCode.I.getDisplayName());
        webDTO.setDesignConfigurationCode("designConfigurationCode");
        webDTO.setNumberOfInterventionGroups("1");
        webDTO.setBlindingSchemaCode("Open");
        webDTO.setAllocationCode("allocationCode");
        webDTO.setMaximumTargetAccrualNumber("1");
        webDTO.setName("Name");
        webDTO.setTimeFrame("designConfigurationCode");
        webDTO.setSafetyIndicator("1");
        action.setWebDTO(webDTO);
        action.setInvestigator("FALSE");
        action.setOutcomesassessor("FALSE");
        action.setCaregiver("FALSE");
        action.setSubject("FALSE");
        assertEquals("details",action.update());

    }

    @Test
    public void testOutcomedelete(){
     assertEquals("outcome", action.outcomedelete());   
    }
    @Test
    public void testOutcomeupdateErr(){
       assertEquals("outcomeAdd", action.outcomeupdate()); 
    }
    @Test
    public void testDetailsQueryException(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(9L));
        assertEquals("details",action.detailsQuery());
    }
    @Test
    public void testUpdateException(){
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
        webDTO.setPrimaryIndicator("yes");
        webDTO.setPrimaryPurposeCode(PrimaryPurposeCode.TREATMENT.getDisplayName());
        webDTO.setPhaseCode(PhaseCode.I.getDisplayName());
        webDTO.setPhaseOtherText("ex");
        webDTO.setDesignConfigurationCode("designConfigurationCode");
        webDTO.setNumberOfInterventionGroups("1");
        webDTO.setBlindingSchemaCode("blindingSchemaCode");
        webDTO.setAllocationCode("allocationCode");
        webDTO.setMaximumTargetAccrualNumber("1");
        webDTO.setName("Name");
        webDTO.setTimeFrame("designConfigurationCode");
        webDTO.setSafetyIndicator("1");
        action.setWebDTO(webDTO);
        action.setInvestigator("FALSE");
        action.setOutcomesassessor("FALSE");
        action.setCaregiver("FALSE");
        action.setSubject("FALSE");
        assertEquals("details",action.update());

    }
}
