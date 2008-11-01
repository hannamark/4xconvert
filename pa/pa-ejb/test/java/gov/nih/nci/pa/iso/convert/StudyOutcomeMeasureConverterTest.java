package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyOutcomeMeasureConverterTest {
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
        StudyOutcomeMeasure bo = new StudyOutcomeMeasure();
        bo.setId(123L);
        bo.setName("StudyOutcomeMeasure");
        bo.setStudyProtocol(sp);
        bo.setPrimaryIndicator(Boolean.TRUE);

        StudyOutcomeMeasureDTO dto = StudyOutcomeMeasureConverter.convertFromDomainToDTO(bo);
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(bo.getName(), dto.getName().getValue());
        assertEquals(bo.getPrimaryIndicator(), dto.getPrimaryIndicator().getValue());
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }

    @Test
    public void convertFromDTOToDomain() throws Exception {
        StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setName(StConverter.convertToSt("Description"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));

        StudyOutcomeMeasure bo = StudyOutcomeMeasureConverter.convertFromDTOToDomain(dto);
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(bo.getName(), dto.getName().getValue());
        assertEquals(bo.getPrimaryIndicator(), dto.getPrimaryIndicator().getValue());
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }
}
