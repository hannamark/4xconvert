package gov.nih.nci.pa.iso.dto;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import org.junit.Test;

public class StudyCheckoutDTOTest {
    
    @Test
    public void studyCheckoutDTOTest() {
        StudyCheckoutDTO ispDTO = createStudyCheckoutDTOObj();
        assertEquals(ispDTO.getUserIdentifier().getValue(),"Abstractor");
       assertEquals(ispDTO.getStudyProtocolIdentifier().getExtension(),"1");
       }
    
    public static StudyCheckoutDTO createStudyCheckoutDTOObj() {
        StudyCheckoutDTO ispDTO = new StudyCheckoutDTO();
        ispDTO.setUserIdentifier(StConverter.convertToSt("Abstractor"));
        ispDTO.setStudyProtocolIdentifier(IiConverter.convertToCountryIi(1L));
        return ispDTO;
    }
    
}
