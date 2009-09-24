package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyCheckoutServiceBeanTest {

    private StudyCheckoutServiceLocal localEjb = new StudyCheckoutServiceBean();
    StudyCheckoutDTO dto = new StudyCheckoutDTO();
    Ii pid;
    
    @Before
    public void setUp() throws Exception {
      TestSchema.reset1();
      TestSchema.primeData();
      pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void get() throws Exception {
        List<StudyCheckoutDTO> statusList = localEjb.getByStudyProtocol(pid);
        assertEquals(1, statusList.size());

        StudyCheckoutDTO dto = localEjb.get(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
         assertTrue(dto.getUserIdentifier().getValue().equals("Abstractor"));
      }

    @Test(expected=PAException.class)
    public void create() throws Exception {
        StudyCheckoutDTO dtoNew = new StudyCheckoutDTO();
        dtoNew.setUserIdentifier(StConverter.convertToSt("Checkout"));
        localEjb.create(dtoNew);
    }
    
    @Test(expected=PAException.class)
    public void update() throws Exception {
        dto.setUserIdentifier(StConverter.convertToSt("Checkout User"));
        localEjb.update(dto);
    }
    
    @Test (expected=PAException.class)
    public void delete() throws Exception {
        localEjb.delete(dto.getIdentifier());
    }

}
