package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyOutcomeMeasureServiceBeanTest {

    private StudyOutcomeMeasureServiceRemote remoteEjb = new StudyOutcomeMeasureServiceBean();;
    Ii pid;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void get() throws Exception {
        List<StudyOutcomeMeasureDTO> statusList =
            remoteEjb.getByStudyProtocol(pid);
        assertEquals(IiConverter.convertToLong(pid), 
                IiConverter.convertToLong(statusList.get(0).getIdentifier()));

        StudyOutcomeMeasureDTO dto =
            remoteEjb.get(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
        StudyOutcomeMeasureDTO dto2 = null;

            dto2 = new StudyOutcomeMeasureDTO();
            dto2 = remoteEjb.update(dto);
            assertEquals(IiConverter.convertToLong(dto.getIdentifier())
                    , (IiConverter.convertToLong(dto2.getIdentifier())));

         remoteEjb.delete(dto.getIdentifier());
    }

    @Test
    public void create() throws Exception {
        StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setStudyProtocolIi(pid);
        dto.setName(StConverter.convertToSt("StudyOutcomeMeasure"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        StudyOutcomeMeasureDTO dto2 = null;
        dto2 = new StudyOutcomeMeasureDTO();
        dto2 = remoteEjb.create(dto);
        assertFalse(PAUtil.isIiNull(dto2.getIdentifier()));
        assertEquals(dto.getName().getValue(), dto2.getName().getValue());
    }
    @Test 
    public void iiRootTest() throws Exception {
        List<StudyOutcomeMeasureDTO> dtoList = remoteEjb.getByStudyProtocol(pid);
        assertTrue(dtoList.size() > 0);
        StudyOutcomeMeasureDTO dto = dtoList.get(0);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.STUDY_OUTCOME_MEASURE_ROOT);
        assertTrue(PAUtil.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIi().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
}
