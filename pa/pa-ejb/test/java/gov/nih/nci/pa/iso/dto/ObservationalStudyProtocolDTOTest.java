package gov.nih.nci.pa.iso.dto;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import org.junit.Test;

public class ObservationalStudyProtocolDTOTest {
    
    @Test
    public void observationalStudyProtocolDTOTest() {
        ObservationalStudyProtocolDTO ispDTO = createObservationalStudyProtocolDTOObj();
        assertEquals(ispDTO.getStudyModelCode().getCode(),StudyModelCode.CASE_CONTROL.getCode()); 
        assertEquals(ispDTO.getNumberOfGroups().getValue(),Integer.valueOf(4));
        assertEquals(ispDTO.getBiospecimenRetentionCode().getCode(),BiospecimenRetentionCode.RETAINED.getCode());
        assertEquals(ispDTO.getBiospecimenDescription().getValue(),"BiospecimenDescription");
        assertEquals(ispDTO.getTimePerspectiveCode().getCode(),TimePerspectiveCode.PROSPECTIVE.getCode());
    }
    public static ObservationalStudyProtocolDTO createObservationalStudyProtocolDTOObj() {
        ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
        ospDTO.setStudyModelCode(CdConverter.convertStringToCd(StudyModelCode.CASE_CONTROL.getCode()));
        ospDTO.setTimePerspectiveCode(CdConverter.convertStringToCd(TimePerspectiveCode.PROSPECTIVE.getCode()));
        ospDTO.setBiospecimenDescription(StConverter.convertToSt("BiospecimenDescription"));
        ospDTO.setBiospecimenRetentionCode(CdConverter.convertStringToCd(BiospecimenRetentionCode.RETAINED.getCode()));
        ospDTO.setNumberOfGroups(IntConverter.convertToInt(4));
        return ospDTO;
    }

}
