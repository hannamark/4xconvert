package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class DocumentWorkflowStatusConverterTest {

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
    DocumentWorkflowStatus bo = new DocumentWorkflowStatus();
    bo.setId(123L);
    bo.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
    bo.setCommentText("Common Text");
    bo.setStudyProtocol(sp);

    DocumentWorkflowStatusConverter sg = new DocumentWorkflowStatusConverter();
    DocumentWorkflowStatusDTO dto = sg.convertFromDomainToDto(bo);
    assertDocumentWorkflowStatusConverter(bo, dto);
  }

  private void assertDocumentWorkflowStatusConverter(DocumentWorkflowStatus bo,
      DocumentWorkflowStatusDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getStatusCode().getCode(), dto.getStatusCode().getCode());
    assertEquals(bo.getCommentText(), dto.getCommentText().getValue());
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
    dto.setCommentText(StConverter.convertToSt("Common text"));
    dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));

    DocumentWorkflowStatusConverter sg = new DocumentWorkflowStatusConverter();
    DocumentWorkflowStatus bo = sg.convertFromDtoToDomain(dto);
    assertDocumentWorkflowStatusConverter(bo, dto);
  }
}
