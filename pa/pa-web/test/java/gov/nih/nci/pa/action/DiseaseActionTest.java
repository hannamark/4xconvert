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
    @Test
    public void testEdit() throws PAException {
        diseaseAction.setSelectedRowIdentifier("1");
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
    @Test
    public void testAddNoActionErrors() throws PAException {
    	DiseaseWebDTO  webdto = new DiseaseWebDTO ();
    	webdto.setDiseaseIdentifier("1");
    	diseaseAction.setDisease(webdto);
       assertEquals("list",diseaseAction.add());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#update()}.
     */
    @Test
    public void testUpdate() throws PAException{
        diseaseAction.update();
        assertTrue(diseaseAction.hasActionErrors());
    }
    @Test
    public void testUpdateNoActionErrors() throws PAException{
      	DiseaseWebDTO  webdto = new DiseaseWebDTO ();
    	webdto.setDiseaseIdentifier("1");
    	diseaseAction.setDisease(webdto);
    	 assertEquals("list",diseaseAction.update());
        
    } 
    @Test
    public void testdisplay() throws PAException{
    	getRequest().setupAddParameter("diseaseId", "1");    	
        assertEquals("edit",diseaseAction.display());
    } 
    /**
     * Test method for {@link gov.nih.nci.pa.action.DiseaseAction#delete()}.
     */
    @Test
    public void testDelete()  throws PAException{
        diseaseAction.setSelectedRowIdentifier("1");
        assertEquals("list",diseaseAction.delete());
    }

}
