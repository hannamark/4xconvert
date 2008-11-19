package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyParticipationContactConverterTest {


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
    StudyParticipation spa = (StudyParticipation) sess.load(StudyParticipation.class, TestSchema.studyParticipationIds.get(0));
    StudyParticipationContact bo = new StudyParticipationContact();
    bo.setId(123L);
    bo.setPrimaryIndicator(Boolean.TRUE);    
    bo.setRoleCode(StudyParticipationContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
    bo.setStudyParticipation(spa);
    bo.setStudyProtocol(sp);
    StudyParticipationContactConverter sg = new StudyParticipationContactConverter();
    StudyParticipationContactDTO dto = sg.convertFromDomainToDTO(bo);
    assertStudyParticipationContactConverter(bo, dto);
  }

  private void assertStudyParticipationContactConverter(StudyParticipationContact bo,
      StudyParticipationContactDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getPrimaryIndicator(), dto.getPrimaryIndicator().getValue());
    assertEquals(bo.getRoleCode().getCode(), dto.getRoleCode().getCode());
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    assertEquals(bo.getStudyParticipation().getId(), IiConverter.convertToLong(dto.getStudyParticipationIi()));
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    StudyParticipation spa = (StudyParticipation) sess.load(StudyParticipation.class, TestSchema.studyParticipationIds.get(0));
    StudyParticipationContactDTO dto = new StudyParticipationContactDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
    dto.setStudyParticipationIi(IiConverter.convertToIi(spa.getId()));
    dto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
    dto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
    StudyParticipationContactConverter sg = new StudyParticipationContactConverter();
    StudyParticipationContact bo = sg.convertFromDtoToDomain(dto);
    assertStudyParticipationContactConverter(bo, dto);
  }

}
