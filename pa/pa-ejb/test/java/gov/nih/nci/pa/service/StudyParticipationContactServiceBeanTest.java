package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyParticipationContactServiceBeanTest {

  private StudyParticipationContactServiceRemote remoteEjb = new StudyParticipationContactServiceBean();
  Ii pid;
  Ii studyParticipationId;

  @Before
  public void setUp() throws Exception {
      TestSchema.reset1();
      TestSchema.primeData();
      pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
      studyParticipationId = IiConverter.convertToIi(TestSchema.studyParticipationIds.get(0));
  }

  @Test
  public void get() throws Exception {
      List<StudyParticipationContactDTO> statusList =
          remoteEjb.getByStudyParticipation(studyParticipationId);
      assertEquals(1, statusList.size());

      StudyParticipationContactDTO dto =
          remoteEjb.get(statusList.get(0).getIdentifier());
      assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
              , (IiConverter.convertToLong(dto.getIdentifier())));

       remoteEjb.delete(dto.getIdentifier());
  }

  @Test
  public void create() throws Exception {
    StudyParticipationContactDTO dto = new StudyParticipationContactDTO();
    dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
    dto.setStudyProtocolIi(pid);
    dto.setStudyParticipationIi(studyParticipationId);
    dto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
    dto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
      StudyParticipationContactDTO dto2 = null;
      dto2 = new StudyParticipationContactDTO();
      dto2 = remoteEjb.create(dto);
      assertEquals(dto.getStudyProtocolIi()
              , pid);
  }


}
