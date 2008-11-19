package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


public class StudySiteAccrualStatusConverterTest {
  private Session sess;

  @Before
  public void setUp() throws Exception {
    TestSchema.reset1();
    TestSchema.primeData();
    sess = TestSchema.getSession();     
  }

  @Test
  public void convertFromDomainToDTO() throws Exception {
    StudyParticipation sp = (StudyParticipation) sess.load(StudyParticipation.class, TestSchema.studyParticipationIds.get(0));
    StudySiteAccrualStatus bo = new StudySiteAccrualStatus();
    bo.setId(123L);
    bo.setStatusCode(RecruitmentStatusCode.ACTIVE_NOT_RECRUITING);
    bo.setStatusDate(new java.sql.Timestamp((new java.util.Date()).getTime()));
    bo.setStudyParticipation(sp);

    StudySiteAccrualStatusConverter sg = new StudySiteAccrualStatusConverter();
    StudySiteAccrualStatusDTO dto = sg.convertFromDomainToDTO(bo);
    assertStudySiteAccrualStatusConverter(bo, dto);
  }

  private void assertStudySiteAccrualStatusConverter(StudySiteAccrualStatus bo,
      StudySiteAccrualStatusDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getStatusCode().getCode(), dto.getStatusCode().getCode());
    assertEquals(bo.getStatusDate(), dto.getStatusDate().getValue());
    assertEquals(bo.getStudyParticipation().getId(), IiConverter.convertToLong(dto.getStudyParticipationIi()));
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyParticipation sp = (StudyParticipation) sess.load(StudyParticipation.class, TestSchema.studyParticipationIds.get(0));
    StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE_NOT_RECRUITING));
    dto.setStatusDate(TsConverter.convertToTs(new java.sql.Timestamp((new java.util.Date()).getTime())));
    dto.setStudyParticipationIi(IiConverter.convertToIi(sp.getId()));

    StudySiteAccrualStatusConverter sg = new StudySiteAccrualStatusConverter();
    StudySiteAccrualStatus bo = sg.convertFromDtoToDomain(dto);
    assertStudySiteAccrualStatusConverter(bo, dto);
  }
}
