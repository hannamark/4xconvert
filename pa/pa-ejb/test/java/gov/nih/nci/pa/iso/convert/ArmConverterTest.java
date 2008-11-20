package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class ArmConverterTest {
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
    Arm bo = new Arm();
    bo.setId(123L);
    bo.setName("Name");
    bo.setDescriptionText("descriptionText");
    bo.setTypeCode(ArmTypeCode.EXPERIMENTAL);
    bo.setStudyProtocol(sp);

    ArmConverter sg = new ArmConverter();
    ArmDTO dto = sg.convertFromDomainToDto(bo);
    assertArmConverter(bo, dto);
  }

  private void assertArmConverter(Arm bo,
      ArmDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getName(), dto.getName().getValue());
    assertEquals(bo.getDescriptionText(), dto.getDescriptionText().getValue());
    assertEquals(bo.getTypeCode().getCode(), dto.getTypeCode().getCode());
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    ArmDTO dto = new ArmDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setName(StConverter.convertToSt("Name"));
    dto.setDescriptionText(StConverter.convertToSt("descriptionText"));
    dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
    dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));

    ArmConverter sg = new ArmConverter();
    Arm bo = sg.convertFromDtoToDomain(dto);
    assertArmConverter(bo, dto);
  }
}
