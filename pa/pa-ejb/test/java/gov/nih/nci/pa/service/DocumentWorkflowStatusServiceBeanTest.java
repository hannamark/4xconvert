package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DocumentWorkflowStatusServiceBeanTest {

  private DocumentWorkflowStatusServiceRemote remoteEjb = new DocumentWorkflowStatusServiceBean();;
  Ii pid;

  @Before
  public void setUp() throws Exception {
      TestSchema.reset1();
      TestSchema.primeData();
      pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
  }

  @Test
  public void get() throws Exception {
      List<DocumentWorkflowStatusDTO> statusList =
          remoteEjb.getByStudyProtocol(pid);
      assertEquals(1, statusList.size());

      DocumentWorkflowStatusDTO dto =
          remoteEjb.get(statusList.get(0).getIdentifier());
      assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
              , (IiConverter.convertToLong(dto.getIdentifier())));
      DocumentWorkflowStatusDTO dto2 = null;

          dto2 = new DocumentWorkflowStatusDTO();
          dto2 = remoteEjb.update(dto);
          assertEquals(dto.getCommonText().getValue()
                  , dto2.getCommonText().getValue());

       remoteEjb.delete(dto.getIdentifier());
  }

  @Test
  public void create() throws Exception {
    DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
      dto.setStudyProtocolIi(pid);
      dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
      dto.setCommonText(StConverter.convertToSt("Common text"));
      DocumentWorkflowStatusDTO dto2 = null;
      dto2 = new DocumentWorkflowStatusDTO();
      dto2 = remoteEjb.create(dto);
      assertEquals(dto.getStudyProtocolIi()
              , pid);
  }
}
