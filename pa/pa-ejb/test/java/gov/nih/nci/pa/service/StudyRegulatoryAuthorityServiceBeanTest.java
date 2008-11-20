package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.RegulatoryAuthorityTest;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

public class StudyRegulatoryAuthorityServiceBeanTest {

  private StudyRegulatoryAuthorityServiceRemote remoteEjb = new StudyRegulatoryAuthorityServiceBean();;
  Ii pid;
  RegulatoryAuthority ra;

  @Before
  public void setUp() throws Exception {
    TestSchema.reset1();
    TestSchema.primeData();
    pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    Country c = CountryTest.createCountryObj();
    TestSchema.addUpdObject(c);
    assertNotNull(c.getId());
    //
    ra = RegulatoryAuthorityTest.createRegulatoryObj(c);
    TestSchema.addUpdObject(ra);
    assertNotNull(ra.getId()); 
  }

  @Test
  public void get() throws Exception {
    StudyRegulatoryAuthorityDTO dto =
      remoteEjb.getStudyRegulatoryAuthority(pid);
    assertEquals(IiConverter.convertToLong(pid)
        , (IiConverter.convertToLong(dto.getProtocolId())));
    StudyRegulatoryAuthorityDTO dto2 = null;

    dto2 = new StudyRegulatoryAuthorityDTO();
    dto2 = remoteEjb.updateStudyRegulatoryAuthority(dto);
    assertEquals(dto.getRegulatoryAuthorityId().getExtension()
        , dto2.getRegulatoryAuthorityId().getExtension());       
  }

  @Test
  public void create() throws Exception {
    StudyRegulatoryAuthorityDTO dto = new StudyRegulatoryAuthorityDTO();
    dto.setProtocolId(pid);
    dto.setRegulatoryAuthorityId(IiConverter.convertToIi(ra.getId()));
    StudyRegulatoryAuthorityDTO dto2 = null;
    dto2 = new StudyRegulatoryAuthorityDTO();
    dto2 = remoteEjb.createStudyRegulatoryAuthority(dto);
    assertEquals(dto.getProtocolId()
        , pid);
  }
}
