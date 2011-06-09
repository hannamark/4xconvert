/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.dto.MilestoneWebDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.service.MockStudyProtocolService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MilestoneActionTest extends AbstractPaActionTest {
    MilestoneAction action;

    @Before
    public void prepare() throws PAException {
        action = new MilestoneAction();
        action.prepare();
    }

    @Test
    public void testMilestoneProperty() {
        assertNull(action.getMilestone());
        action.setMilestone(new MilestoneWebDTO());
        assertNotNull(action.getMilestone());
    }

    @Test
    public void testMilestoneListProperty() {
        assertNull(action.getMilestoneList());
        action.setMilestoneList(new ArrayList<MilestoneWebDTO>());
        assertNotNull(action.getMilestoneList());
    }

    @Test
    public void testLoadEditForm() throws PAException {
        action.loadEditForm();
        assertNotNull(action.getMilestone());
    }

    @Test
    public void testLoadListForm() throws PAException {
        setUpAmendmentSearch();
        action.loadListForm();
        assertNotNull("No milestone list", action.getMilestoneList());
        assertNotNull("No amendment map", action.getAmendmentMap());
        assertEquals("Wrong size of amendment map", 1, action.getAmendmentMap().size());
    }

    @Test
    public void testAddThrowsEx() throws PAException {
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06/18/2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        action.setSpIi(IiConverter.convertToIi("9"));
        String result = action.add();
        assertEquals("Wrong result from add action", "edit", result);
        assertNotNull(action.getAllowedMilestones());
        assertFalse(action.getAllowedMilestones().isEmpty());
        assertNotNull(action.getMilestone());
    }

    @Test
    public void testAdd() throws PAException {
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06/19/2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpAmendmentSearch();
        String result = action.add();
        assertEquals("Wrong result from add action", "list", result);
    }

    private void setUpAmendmentSearch() {
        action.setStudyProtocolSvc(new MockStudyProtocolService() {
            @Override
            public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException {
                List<StudyProtocolDTO> result = new ArrayList<StudyProtocolDTO>();
                result.add(StudyProtocolConverter.convertFromDomainToDTO(list.get(0)));
                return result;
            }
        });
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
    }
}
