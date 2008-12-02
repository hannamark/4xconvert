package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudySiteAccrualStatusServiceBeanTest {

  private StudySiteAccrualStatusServiceRemote remoteEjb = new StudySiteAccrualStatusServiceBean();;
  Ii studyParticipationId;
  @Before
  public void setUp() throws Exception {
    TestSchema.reset1();
    TestSchema.primeData();
    studyParticipationId = IiConverter.convertToIi(TestSchema.studyParticipationIds.get(0));
  }

  @Test
  public void get() throws Exception {
    List<StudySiteAccrualStatusDTO> statusList =
      remoteEjb.getStudySiteAccrualStatusByStudyParticipation(studyParticipationId);
    assertEquals(1, statusList.size());

    List<StudySiteAccrualStatusDTO> dto =
      remoteEjb.getCurrentStudySiteAccrualStatusByStudyParticipation(statusList.get(0).getStudyParticipationIi());
    assertEquals(statusList.size(), dto.size());   
    
    StudySiteAccrualStatusDTO dto2 = null;
    try {
        dto2 = new StudySiteAccrualStatusDTO();
        remoteEjb.updateStudySiteAccrualStatus(dto2);
    } catch(PAException e) {
        // expected behavior
    }
  
  try {
      remoteEjb.getStudySiteAccrualStatus(null);
  } catch(PAException e) {
      // expected behavior
  }
}

  @Test
  public void create() throws Exception {
    StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
    dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE_NOT_RECRUITING));
    dto.setStatusDate(TsConverter.convertToTs(new java.sql.Timestamp((new java.util.Date()).getTime())));
    dto.setStudyParticipationIi(studyParticipationId);
    StudySiteAccrualStatusDTO dto2 = null;
    dto2 = new StudySiteAccrualStatusDTO();
    dto2 = remoteEjb.createStudySiteAccrualStatus(dto);
    assertEquals(dto.getStudyParticipationIi()
        , studyParticipationId);
  }
}
