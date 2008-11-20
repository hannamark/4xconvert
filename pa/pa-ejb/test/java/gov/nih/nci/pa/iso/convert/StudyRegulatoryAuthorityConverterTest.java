package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.RegulatoryAuthorityTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyRegulatoryAuthorityConverterTest {
  private RegulatoryAuthority ra;
  private Session sess;

  @Before
  public void setUp() throws Exception {
    TestSchema.reset1();
    TestSchema.primeData();
    sess = TestSchema.getSession();  
    Country c = CountryTest.createCountryObj();
    TestSchema.addUpdObject(c);
    assertNotNull(c.getId());
    //
    ra = RegulatoryAuthorityTest.createRegulatoryObj(c);
    TestSchema.addUpdObject(ra);
    assertNotNull(ra.getId()); 
  }

  @Test
  public void convertFromDomainToDTO() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    StudyRegulatoryAuthority bo = new StudyRegulatoryAuthority();
    bo.setId(123L);
    bo.setRegulatoryAuthority(ra);
    bo.setStudyProtocol(sp);

    StudyRegulatoryAuthorityConverter sg = new StudyRegulatoryAuthorityConverter();
    StudyRegulatoryAuthorityDTO dto = sg.convertFromDomainToDTO(bo);
    assertStudyRegulatoryAuthorityConverter(bo, dto);
  }

  private void assertStudyRegulatoryAuthorityConverter(StudyRegulatoryAuthority bo,
      StudyRegulatoryAuthorityDTO dto) {
    assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
    assertEquals(bo.getRegulatoryAuthority().getId(), IiConverter.convertToLong(dto.getRegulatoryAuthorityId()));
    assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getProtocolId()));
  }

  @Test
  public void convertFromDTOToDomain() throws Exception {
    StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
    StudyRegulatoryAuthorityDTO dto = new StudyRegulatoryAuthorityDTO();
    dto.setIdentifier(IiConverter.convertToIi((Long) null));
    dto.setRegulatoryAuthorityId(IiConverter.convertToIi(ra.getId()));
    dto.setProtocolId(IiConverter.convertToIi(sp.getId()));

    StudyRegulatoryAuthorityConverter sg = new StudyRegulatoryAuthorityConverter();
    StudyRegulatoryAuthority bo = sg.convertFromDTOToDomain(dto);
    assertStudyRegulatoryAuthorityConverter(bo, dto);
  }

}
