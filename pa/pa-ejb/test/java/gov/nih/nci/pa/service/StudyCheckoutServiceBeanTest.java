package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyCheckoutServiceBeanTest extends AbstractHibernateTestCase {

    private StudyCheckoutServiceLocal localEjb = new StudyCheckoutServiceBean();
    Ii pid;

    @Before
    public void setUp() throws Exception {
      TestSchema.primeData();
      pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
      CSMUserService.setRegistryUserService(new MockCSMUserService());
    }

    @Test
    public void get() throws Exception {
        List<StudyCheckoutDTO> statusList = localEjb.getByStudyProtocol(pid);
        assertEquals(2, statusList.size());
        StudyCheckoutDTO dto = localEjb.get(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
         assertTrue(dto.getUserIdentifier().getValue().equals("Abstractor"));
      }

    @Test
    public void create() throws Exception {
        StudyCheckoutDTO dtoNew = new StudyCheckoutDTO();
        dtoNew.setCheckOutTypeCode(CdConverter.convertStringToCd(CheckOutType.ADMINISTRATIVE.getCode()));
        dtoNew.setUserIdentifier(StConverter.convertToSt("Checkout"));
        dtoNew.setStudyProtocolIdentifier(pid);
        localEjb.create(dtoNew);
    }

    @Test
    public void update() throws Exception {
        StudyCheckoutDTO dto = localEjb.getByStudyProtocol(pid).get(0);
        dto.setUserIdentifier(StConverter.convertToSt("Checkout User"));
        localEjb.update(dto);
    }

    @Test
    public void delete() throws Exception {
        StudyCheckoutDTO dto = localEjb.getByStudyProtocol(pid).get(0);
        localEjb.delete(dto.getIdentifier());
    }

}
