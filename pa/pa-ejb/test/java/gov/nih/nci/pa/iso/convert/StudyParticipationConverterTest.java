package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyParticipationConverterTest {

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
    StudyParticipation bo = new StudyParticipation();
    bo.setId(123L);
    bo.setFunctionalCode(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION);
    bo.setLocalStudyProtocolIdentifier("Ecog1");
    bo.setStudyProtocol(sp);
    StudyParticipationConverter sg = new StudyParticipationConverter();
    StudyParticipationDTO dto = sg.convertFromDomainToDTO(bo);
    assertStudyParticipationConverter(bo, dto);
  }

  private void assertStudyParticipationConverter(StudyParticipation bo,
      StudyParticipationDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    assertEquals(bo.getLocalStudyProtocolIdentifier(),  dto.getLocalStudyProtocolIdentifier().getValue());
    assertEquals(bo.getFunctionalCode().getCode(),  dto.getFunctionalCode().getCode());
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    StudyParticipationDTO dto = new StudyParticipationDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Ecog1"));
    dto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION));
    dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
    dto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
    StudyParticipationConverter sg = new StudyParticipationConverter();
    StudyParticipation bo = sg.convertFromDtoToDomain(dto);
    assertStudyParticipationConverter(bo, dto);
  }
}
