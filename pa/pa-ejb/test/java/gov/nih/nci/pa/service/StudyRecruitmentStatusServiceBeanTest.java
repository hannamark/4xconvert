package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyRecruitmentStatusServiceBeanTest {
  
  private StudyRecruitmentStatusServiceRemote remoteEjb = new StudyRecruitmentStatusServiceBean();
  
  @Before
  public void setUp() throws Exception {
    TestSchema.reset1();
  }  

  @Test
  public void getTest() throws Exception {          
    StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
    TestSchema.addUpdObject(sp);
    assertNotNull(sp.getId());
    
    StudyRecruitmentStatus bo = new StudyRecruitmentStatus(); 
    bo.setStudyProtocol(sp);
    bo.setStatusCode(StudyRecruitmentStatusCode.RECRUITING_ACTIVE);
    bo.setStatusDate(new java.sql.Timestamp((new java.util.Date()).getTime()));
    TestSchema.addUpdObject(bo);
    assertNotNull(bo.getId());
    
    List<StudyRecruitmentStatusDTO> statusList =remoteEjb.getByStudyProtocol(IiConverter.convertToIi(sp.getId()));
    assertEquals(1, statusList.size());
  }


}
