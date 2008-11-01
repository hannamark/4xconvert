package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.NihInstHolderCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyIndldeConverterTest {

    private Session sess;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        sess = TestSchema.getSession();
    }

    @Test
    public void convertFromDomainToDTO() throws Exception {
        StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudyIndlde bo = new StudyIndlde();
        bo.setId(123L);
        bo.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        bo.setStudyProtocol(sp);
        bo.setExpandedAccessIndicator(Boolean.TRUE);
        bo.setHolderTypeCode(HolderTypeCode.NIH);
        bo.setNihInstHolderCode(NihInstHolderCode.NCRR);

        StudyIndldeDTO dto = StudyIndldeConverter.convertFromDomainToDTO(bo);
        assertStudyIndlde(bo, dto);
    }

    private void assertStudyIndlde(StudyIndlde bo, StudyIndldeDTO dto) {
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(bo.getExpandedAccessStatusCode().getCode() , dto.getExpandedAccessStatusCode().getCode());
        assertEquals(bo.getExpandedAccessIndicator() ,  dto.getExpandedAccessIndicator().getValue());
        assertEquals(bo.getHolderTypeCode().getCode() ,  dto.getHolderTypeCode().getCode());
        assertEquals(bo.getNihInstHolderCode().getCode() ,  dto.getNihInstHolderCode().getCode());
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }

    @Test
    public void convertFromDTOToDomain() throws Exception {
        StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudyIndldeDTO dto = new StudyIndldeDTO();
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setExpandedAccessStatusCode(CdConverter.convertToCd(ExpandedAccessStatusCode.AVAILABLE));
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
        dto.setNihInstHolderCode(CdConverter.convertToCd(NihInstHolderCode.NCRR));
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));

        StudyIndlde bo = StudyIndldeConverter.convertFromDTOToDomain(dto);
        assertStudyIndlde(bo, dto);
    }
}
