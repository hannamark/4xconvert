package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.NihInstHolderCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyIndldeServiceBeanTest {
    private StudyIndldeServiceRemote remoteEjb = new StudyIndldeServiceBean();;
    Ii pid;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void get() throws Exception {
        List<StudyIndldeDTO> statusList =
            remoteEjb.getByStudyProtocol(pid);
        assertEquals(IiConverter.convertToLong(pid), 
                IiConverter.convertToLong(statusList.get(0).getIdentifier()));

        StudyIndldeDTO dto =
            remoteEjb.get(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
        StudyIndldeDTO dto2 = null;

            dto2 = new StudyIndldeDTO();
            dto2 = remoteEjb.update(dto);
            assertEquals(IiConverter.convertToLong(dto.getIdentifier())
                    , (IiConverter.convertToLong(dto2.getIdentifier())));

         remoteEjb.delete(dto.getIdentifier());
    }

    @Test
    public void create() throws Exception {
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setExpandedAccessStatusCode(CdConverter.convertToCd(ExpandedAccessStatusCode.AVAILABLE));
        dto.setStudyProtocolIi(pid);
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
        dto.setNihInstHolderCode(CdConverter.convertToCd(NihInstHolderCode.NCRR));
        StudyIndldeDTO dto2 = null;
        dto2 = new StudyIndldeDTO();
        dto2 = remoteEjb.create(dto);
        assertFalse(PAUtil.isIiNull(dto2.getIdentifier()));
        assertEquals(dto.getExpandedAccessStatusCode().getCode(), dto2.getExpandedAccessStatusCode().getCode());
    }
    @Test 
    public void iiRootTest() throws Exception {
        List<StudyIndldeDTO> dtoList = remoteEjb.getByStudyProtocol(pid);
        assertTrue(dtoList.size() > 0);
        StudyIndldeDTO dto = dtoList.get(0);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.STUDY_IND_IDE_ROOT);
        assertTrue(PAUtil.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIi().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
}
