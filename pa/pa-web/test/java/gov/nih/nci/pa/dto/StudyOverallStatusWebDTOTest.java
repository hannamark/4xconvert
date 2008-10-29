/**
 * 
 */
package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import org.junit.Test;
/**
 * @author hreinhart
 *
 */
/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusWebDTOTest {

    @Test
    public void studyOverallStatusWebDTO() {
        Long sId = 1L;
        String sString = "1/1/2001";
        StudyStatusCode sCode = StudyStatusCode.ADMINISTRATIVELY_COMPLETE;
        
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setIdentifier(IiConverter.convertToIi(sId));
        dto.setStatusCode(CdConverter.convertToCd(sCode));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(sString)));
        
        StudyOverallStatusWebDTO webDto = new StudyOverallStatusWebDTO(dto);
        assertEquals(PAUtil.normalizeDateString(sString), webDto.getStatusDate());
        assertEquals(sCode.getDisplayName(), webDto.getStatusCode());
        
    }
}
