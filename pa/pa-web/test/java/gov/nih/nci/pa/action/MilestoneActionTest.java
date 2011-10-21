/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.dto.MilestoneWebDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.service.MockStudyProtocolService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Vrushali
 *
 */
public class MilestoneActionTest extends AbstractPaActionTest {
    MilestoneAction action;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Before
    public void prepare() throws PAException {
        action = new MilestoneAction();
        action.prepare();
    }

    @After
    public void tearDown() {
        DateTimeUtils.setCurrentMillisSystem();
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
    public void testLoadListFormWithRejectedStatus() throws PAException {
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
        action.loadListForm();
        assertNotNull("No milestone list", action.getMilestoneList());
        assertNotNull("No amendment map", action.getAmendmentMap());
        assertEquals("Wrong size of amendment map", 0, action.getAmendmentMap().size());
    }

    @Test
    public void testAddThrowsEx() throws PAException {
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06/18/2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        action.setSpIi(IiConverter.convertToStudyProtocolIi(9L));
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
        webDTO.setDate("06-19-2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpAmendmentSearch();
        String result = action.add();
        assertEquals("Wrong result from add action", "list", result);
    }

    @Test
    public void testDateCheckPastDate() throws PAException {
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate("06-19-2009");
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpDateCheckForTodayOnMilestone();
        setUpAmendmentSearch();
        action.add();
        assertEquals("date does not match", action.getActionErrors().iterator().next());
    }

    @Test
    public void testDateCheckCurrentDate() throws PAException {
        Long now = DateTimeUtils.currentTimeMillis();
        DateTimeUtils.setCurrentMillisFixed(now);
        String clientDate = sdf.format(new Timestamp(now));
        MilestoneWebDTO webDTO = new MilestoneWebDTO();
        webDTO.setComment("comment");
        webDTO.setDate(clientDate);
        webDTO.setMilestone(MilestoneCode.ADMINISTRATIVE_QC_START.getDisplayName());
        action.setMilestone(webDTO);
        setUpDateCheckForTodayOnMilestone();
        setUpAmendmentSearch();
        String result = action.add();
        assertEquals("Wrong result from add action", "list", result);
    }

    private void setUpAmendmentSearch() {
        action.setStudyProtocolService(new MockStudyProtocolService() {
            @Override
            public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException {
                List<StudyProtocolDTO> result = new ArrayList<StudyProtocolDTO>();
                result.add(StudyProtocolConverter.convertFromDomainToDTO(list.get(0)));
                return result;
            }
        });
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
    }

    private void setUpDateCheckForTodayOnMilestone() throws PAException {
        StudyMilestoneServicelocal svcMil = mock(StudyMilestoneServicelocal.class);
        action.setStudyMilestoneService(svcMil);
        when(svcMil.create(any(StudyMilestoneDTO.class))).thenAnswer(new Answer<StudyMilestoneDTO>() {
            @Override
            public StudyMilestoneDTO answer(InvocationOnMock invocation) throws Throwable {
                Object args[] = invocation.getArguments();
                StudyMilestoneDTO smDto = (StudyMilestoneDTO) args[0];
                if (!sdf.format(new Date()).equals(sdf.format(smDto.getMilestoneDate().getValue()))) {
                    throw new PAException("date does not match");
                }
                return smDto;
            }
          });
    }
}
