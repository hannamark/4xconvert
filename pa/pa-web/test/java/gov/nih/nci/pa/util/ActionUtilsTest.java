package gov.nih.nci.pa.util;

import static org.junit.Assert.*;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActionUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testSortTrialsByNciId() {
        List<StudyProtocolQueryDTO> records = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO dto1 = new StudyProtocolQueryDTO();
        dto1.setNciIdentifier("2");
        
        StudyProtocolQueryDTO dto2 = new StudyProtocolQueryDTO();
        dto2.setNciIdentifier("1");
        
        records.add(dto2);
        records.add(dto1);
        
        List<StudyProtocolQueryDTO> sortedRecords = ActionUtils.sortTrialsByNciId(records);
        assertEquals(records, sortedRecords);
        assertEquals(dto1, records.get(0));
        assertEquals(dto2, records.get(1));
        
        records = new ArrayList<StudyProtocolQueryDTO>();
        sortedRecords = ActionUtils.sortTrialsByNciId(records);
        assertTrue(records==sortedRecords);
        assertTrue(records.isEmpty());
        
        assertNull(ActionUtils.sortTrialsByNciId(null));
        
    }

}
