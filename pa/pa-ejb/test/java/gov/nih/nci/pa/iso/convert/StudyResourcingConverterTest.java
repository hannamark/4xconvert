package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyResourcingConverterTest {

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
    StudyResourcing bo = new StudyResourcing();
    bo.setId(123L);
    bo.setOrganizationIdentifier("100");
    bo.setResourceProviderIdentifier("100");
    bo.setStudyProtocol(sp);
    bo.setSummary4ReportedResourceIndicator(Boolean.TRUE);
    bo.setTypeCode(SummaryFourFundingCategoryCode.INDUSTRIAL);
    StudyResourcingConverter sg = new StudyResourcingConverter();
    StudyResourcingDTO dto = sg.convertFromDomainToDTO(bo);
    assertStudyResourcingConverter(bo, dto);
  }

  private void assertStudyResourcingConverter(StudyResourcing bo,
      StudyResourcingDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getOrganizationIdentifier(),  IiConverter.convertToString(dto.getOrganizationIdentifier()));
    assertEquals(bo.getResourceProviderIdentifier(),  IiConverter.convertToString(dto.getResourceProviderIdentifier()));
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    assertEquals(bo.getSummary4ReportedResourceIndicator(),  dto.getSummary4ReportedResourceIndicator().getValue());
    assertEquals(bo.getTypeCode().getCode(),  dto.getTypeCode().getCode());
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    StudyResourcingDTO dto = new StudyResourcingDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setOrganizationIdentifier(IiConverter.convertToIi("100"));
    dto.setResourceProviderIdentifier(IiConverter.convertToIi("100"));
    dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
    dto.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL));
    StudyResourcingConverter sg = new StudyResourcingConverter();
    StudyResourcing bo = sg.convertFromDTOToDomain(dto);
    assertStudyResourcingConverter(bo, dto);
  }


}
