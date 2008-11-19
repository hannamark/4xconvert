package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class PlannedEligibilityCriterionConverterTest {

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
    PlannedEligibilityCriterion bo = new PlannedEligibilityCriterion();
    bo.setId(123L);
    bo.setCriterionName("WHC");
    bo.setInclusionIndicator(Boolean.TRUE);
    bo.setOperator(">");
    bo.setStudyProtocol(sp);
    bo.setAgeValue("14");
    bo.setUnit(UnitsCode.MONTHS);
    PlannedEligibilityCriterionConverter sg = new PlannedEligibilityCriterionConverter();
    PlannedEligibilityCriterionDTO dto = sg.convertFromDomainToDTO(bo);
    assertPlannedEligibilityCriterionConverter(bo, dto);
  }

  private void assertPlannedEligibilityCriterionConverter(PlannedEligibilityCriterion bo,
      PlannedEligibilityCriterionDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getCriterionName(),  dto.getCriterionName().getValue());
    assertEquals(bo.getInclusionIndicator(),  dto.getInclusionIndicator().getValue());
    assertEquals(bo.getOperator(),  dto.getOperator().getValue());
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    assertEquals(bo.getAgeValue(),  dto.getAgeValue().getValue());
    assertEquals(bo.getUnit().getCode(),  dto.getUnit().getCode());
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setCriterionName(StConverter.convertToSt("WHC"));
    dto.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setOperator(StConverter.convertToSt(">"));
    dto.setAgeValue(StConverter.convertToSt("80"));
    dto.setUnit(CdConverter.convertToCd(UnitsCode.YEARS));
    dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
    PlannedEligibilityCriterionConverter sg = new PlannedEligibilityCriterionConverter();
    PlannedEligibilityCriterion bo = sg.convertFromDTOToDomain(dto);
    assertPlannedEligibilityCriterionConverter(bo, dto);
  }

}
