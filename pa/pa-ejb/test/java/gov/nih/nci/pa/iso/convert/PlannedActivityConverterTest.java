package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class PlannedActivityConverterTest {


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
    PlannedActivity bo = new PlannedActivity();
    bo.setId(123L);
    bo.setCategoryCode(ActivityCategoryCode.INTERVENTION);
    bo.setLeadProductIndicator(Boolean.TRUE);
    bo.setSubcategoryCode(ActivitySubcategoryCode.DIETARY_SUPPLEMENT);
    bo.setStudyProtocol(sp);
    PlannedActivityConverter sg = new PlannedActivityConverter();
    PlannedActivityDTO dto = sg.convertFromDomainToDto(bo);
    assertPlannedActivityConverter(bo, dto);
  }

  private void assertPlannedActivityConverter(PlannedActivity bo,
      PlannedActivityDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getLeadProductIndicator(),  dto.getLeadProductIndicator().getValue());
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    assertEquals(bo.getSubcategoryCode().getCode(),  dto.getSubcategoryCode().getCode());
    assertEquals(bo.getCategoryCode().getCode(),  dto.getCategoryCode().getCode());
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    PlannedActivityDTO dto = new PlannedActivityDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
    dto.setLeadProductIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.DIETARY_SUPPLEMENT));
    dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
    PlannedActivityConverter sg = new PlannedActivityConverter();
    PlannedActivity bo = sg.convertFromDtoToDomain(dto);
    assertPlannedActivityConverter(bo, dto);
  }


}
