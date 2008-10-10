package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class DocumentConverterTest {
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
        Document bo = new Document();
        bo.setId(123L);
        bo.setTypeCode(DocumentTypeCode.Protocol_Document);
        bo.setFileName("Protocol_Document.doc");
        bo.setStudyProtocol(sp);
        
        DocumentDTO dto = DocumentConverter.convertFromDomainToDTO(bo);
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIi()));
        assertEquals(bo.getTypeCode().getCode(), dto.getTypeCode().getCode());
        assertEquals(bo.getFileName(), dto.getFileName().getValue());
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }
    
    @Test
    public void convertFromDTOToDomain() throws Exception {
        StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        DocumentDTO dto = new DocumentDTO();
        dto.setIi(IiConverter.convertToIi((Long) null));
        dto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_Approval_Document));
        dto.setFileName(StConverter.convertToSt("IRB_Approval_Document.doc"));
        dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
        
        Document bo = DocumentConverter.convertFromDTOToDomain(dto);
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIi()));
        assertEquals(bo.getTypeCode().getCode(), dto.getTypeCode().getCode());
        assertEquals(bo.getFileName(), dto.getFileName().getValue());
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }
}
