/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import gov.nih.nci.pa.dto.SubGroupsWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class SubGroupsActionTest extends AbstractPaActionTest{

    
    SubGroupsAction subGroupsAction;
    SubGroupsWebDTO dto;
    
    @Before
    public void setUp(){
        subGroupsAction = new SubGroupsAction();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        dto = new SubGroupsWebDTO();
        dto.setDescription("test");
        dto.setGroupNumberText("gpText");
        subGroupsAction.setSubGroupsWebDTO(dto);
        
    }
    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#query()}.
     */
    @Test
    public void testQuery() {
        assertFalse(subGroupsAction.hasActionErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#create()}.
     */
    @Test
    public void testCreate() {
        subGroupsAction.create();
        assertFalse(subGroupsAction.hasActionErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#edit()}.
     */
    @Test
    public void testEdit() {
       dto.setId("1");
       String result = subGroupsAction.edit(); 
       assertEquals("input",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#update()}.
     */
    @Test
    public void testUpdate() {
        dto.setId("1");
        String result = subGroupsAction.update(); 
        assertFalse(subGroupsAction.hasActionErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.SubGroupsAction#delete()}.
     */
    @Test
    public void testDelete() {
        dto.setId("1");
        String result = subGroupsAction.delete(); 
        assertEquals("success",result);
    }

}
