/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class DiseaseActionTest extends AbstractPaActionTest{

    
    DiseaseAction diseaseAction;
    DiseaseWebDTO dto;
    
    @Before
    public void setUp() throws PAException {
        diseaseAction = new DiseaseAction();
        dto = new DiseaseWebDTO();
        diseaseAction.setDisease(dto);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        diseaseAction.prepare();
        
        
    }
    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#edit()}.
     */
    @Test(expected=PAException.class)
    public void testEdit() throws PAException {
        diseaseAction.setSelectedRowIdentifier(null);
        String result = diseaseAction.edit();
        assertEquals("edit", result);
        
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#add()}.
     */
    @Test
    public void testAdd() throws PAException {
        diseaseAction.add();
        assertTrue(diseaseAction.hasActionErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#update()}.
     */
    @Test
    public void testUpdate() throws PAException{
        diseaseAction.update();
        assertTrue(diseaseAction.hasActionErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#delete()}.
     */
    @Test(expected=PAException.class)
    public void testDelete()  throws PAException{
        diseaseAction.setSelectedRowIdentifier(null);
        diseaseAction.delete();
    }

}
