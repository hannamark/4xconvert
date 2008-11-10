package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StratumGroupConverterTest {

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
            StratumGroup bo = new StratumGroup();
            bo.setId(123L);
            bo.setDescription("Description");
            bo.setGroupNumberText("Code");
            bo.setStudyProtocol(sp);
            
            StratumGroupConverter sg = new StratumGroupConverter();
            StratumGroupDTO dto = sg.convertFromDomainToDto(bo);
            assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
            assertEquals(bo.getDescription(), dto.getDescription().getValue());
            assertEquals(bo.getGroupNumberText(), dto.getGroupNumberText().getValue());
            assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
        }

        @Test
        public void convertFromDTOToDomain() throws Exception {
            StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
            StratumGroupDTO dto = new StratumGroupDTO();
            dto.setIdentifier(IiConverter.convertToIi((Long) null));
            dto.setDescription(StConverter.convertToSt("Description"));
            dto.setGroupNumberText(StConverter.convertToSt("Code"));
            dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));

            StratumGroupConverter sg = new StratumGroupConverter();
            StratumGroup bo = sg.convertFromDtoToDomain(dto);
            assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
            assertEquals(bo.getDescription(), dto.getDescription().getValue());
            assertEquals(bo.getGroupNumberText(), dto.getGroupNumberText().getValue());
            assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
        }
}
