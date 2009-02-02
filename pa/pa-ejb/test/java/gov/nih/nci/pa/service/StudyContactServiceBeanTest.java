package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyContactServiceBeanTest {
  private StudyContactServiceRemote remoteEjb = new StudyContactServiceBean();;
  Ii pid;
  Ii clinicalResearchStaffId;

  @Before
  public void setUp() throws Exception {
    TestSchema.reset1();
    TestSchema.primeData();
    pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    clinicalResearchStaffId = IiConverter.convertToIi(TestSchema.clinicalResearchStaffIds.get(0));
  }

  @Test
  public void get() throws Exception {
    List<StudyContactDTO> statusList =
      remoteEjb.getByStudyProtocol(pid);
    assertEquals(1, statusList.size());
    List<StudyContactDTO> spList2 = remoteEjb.getByStudyProtocol(pid, statusList.get(0));
    assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier()),
        IiConverter.convertToLong(spList2.get(0).getIdentifier()));
    List<StudyContactDTO> spList3 = remoteEjb.getByStudyProtocol(pid, statusList);
    assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier()), 
        IiConverter.convertToLong(spList3.get(0).getIdentifier()));
    
    remoteEjb.delete(statusList.get(0).getIdentifier());
  }

  @Test
  public void create() throws Exception {
    StudyContactDTO dto = new StudyContactDTO();
    dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setStudyProtocolIi(pid);
    dto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
    dto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
    dto.setClinicalResearchStaffIi(clinicalResearchStaffId);
    StudyContactDTO dto2 = null;
    dto2 = new StudyContactDTO();
    dto2 = remoteEjb.create(dto);
    assertEquals(dto.getStudyProtocolIi()
        , pid);
  }
  @Test 
  public void iiRootTest() throws Exception {
      List<StudyContactDTO> statusList = remoteEjb.getByStudyProtocol(pid);
      assertTrue(statusList.size() > 0);
      StudyContactDTO dto = statusList.get(0);
      assertEquals(dto.getStudyProtocolIi().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
  }
}
