package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.math.BigDecimal;

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
    bo.setValue(new BigDecimal("14"));
    bo.setUnit(UnitsCode.MONTHS.getCode());
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
    assertEquals(bo.getValue(),  dto.getValue().getValue());
    assertEquals(bo.getUnit(),  dto.getValue().getUnit());
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setCriterionName(StConverter.convertToSt("WHC"));
    dto.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setOperator(StConverter.convertToSt(">"));
    Pq pq = new Pq();
    pq.setValue(new BigDecimal("80"));
    pq.setUnit(UnitsCode.YEARS.getCode());
    dto.setValue(pq);
    dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
    PlannedEligibilityCriterionConverter sg = new PlannedEligibilityCriterionConverter();
    PlannedEligibilityCriterion bo = sg.convertFromDTOToDomain(dto);
    assertPlannedEligibilityCriterionConverter(bo, dto);
  }

}
