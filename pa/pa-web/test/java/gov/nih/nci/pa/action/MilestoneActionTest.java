/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import gov.nih.nci.pa.dto.MilestoneWebDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MilestoneActionTest extends AbstractPaActionTest {
    MilestoneAction action ;
    @Before
    public void prepare() throws PAException {
        action = new MilestoneAction();
        action.prepare();
    }
    
    @Test
    public void testMilestoneProperty(){
       assertNull(action.getMilestone());
       action.setMilestone(new MilestoneWebDTO());
       assertNotNull(action.getMilestone());
    }
    @Test
    public void testMilestoneListProperty(){
        assertNull(action.getMilestoneList());
        action.setMilestoneList(new ArrayList<MilestoneWebDTO>());
        assertNotNull(action.getMilestoneList());
    }
    @Test
    public void testLoadEditForm() throws PAException{
        action.loadEditForm();
        assertNotNull(action.getMilestone());
    }
    @Test
    public void testLoadListForm() throws PAException{
        action.spIi = IiConverter.convertToIi("1");
        action.loadListForm();
        assertNotNull(action.getMilestoneList());
    }
    @Test
    public void testAddThrowsEx() throws PAException{
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06/18/2009");
        webDTO.setMilestone(MilestoneCode.QC_START.getDisplayName());
        action.setMilestone(webDTO);
        action.spIi = IiConverter.convertToIi("9");
        action.add();
    }
    @Test
    public void testAdd() throws PAException{
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06/19/2009");
        webDTO.setMilestone(MilestoneCode.QC_START.getDisplayName());
        action.setMilestone(webDTO);
        action.spIi = IiConverter.convertToIi("1");
        action.add();
    }
}
