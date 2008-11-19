package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyContactConverterTest {

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
    StudyContact bo = new StudyContact();
    bo.setId(123L);
    bo.setPrimaryIndicator(Boolean.TRUE);    
    bo.setStudyProtocol(sp);
    bo.setRoleCode(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);

    StudyContactConverter sg = new StudyContactConverter();
    StudyContactDTO dto = sg.convertFromDomainToDTO(bo);
    assertStudyContactConverter(bo, dto);
  }

  private void assertStudyContactConverter(StudyContact bo,
      StudyContactDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getPrimaryIndicator(), dto.getPrimaryIndicator().getValue());
    assertEquals(bo.getRoleCode().getCode(), dto.getRoleCode().getCode());
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    StudyContactDTO dto = new StudyContactDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setPrimaryIndicator(BlConverter.convertToBl((Boolean) null));
    dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
    dto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
    dto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
    StudyContactConverter sg = new StudyContactConverter();
    StudyContact bo = sg.convertFromDtoToDomain(dto);
    assertStudyContactConverter(bo, dto);
  }

}
