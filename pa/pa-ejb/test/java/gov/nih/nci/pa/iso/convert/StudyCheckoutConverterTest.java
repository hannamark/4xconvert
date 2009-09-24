package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyCheckoutConverterTest {

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
      StudyCheckout bo = new StudyCheckout();
      bo.setId(123L);
      bo.setStudyProtocol(sp);
      bo.setUserIdentifier("Test");
      
      StudyCheckoutDTO dto = (Converters.get(StudyCheckoutConverter.class).convertFromDomainToDto(bo));
      assertStudyCheckoutConverter(bo, dto);
    }

    private void assertStudyCheckoutConverter(StudyCheckout bo,
        StudyCheckoutDTO dto) {
      assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
      assertEquals(bo.getUserIdentifier(), dto.getUserIdentifier().getValue());
      assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    }

    @Test
    public void convertFromDTOToDomain() throws Exception {
      StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
      StudyCheckoutDTO dto = new StudyCheckoutDTO();
      dto.setIdentifier(IiConverter.convertToIi((Long) null));
      dto.setUserIdentifier(StConverter.convertToSt(null));
      dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
      
      StudyCheckoutConverter sg = new StudyCheckoutConverter();
      StudyCheckout bo = sg.convertFromDtoToDomain(dto);
      assertStudyCheckoutConverter(bo, dto);
    }
}
