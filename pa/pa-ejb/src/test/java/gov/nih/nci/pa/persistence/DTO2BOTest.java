package gov.nih.nci.pa.persistence;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;

import org.junit.Test;

public class DTO2BOTest {
    
    @Test
    public void convertTest() {
        ProtocolDTO dto = new ProtocolDTO();
        dto.setLongTitleText("Long Title");
        dto.setNciIdentifier("NCI ID");
        
        Protocol bo = DTO2BO.convert(dto);
        assertEquals(dto.getLongTitleText(), bo.getLongTitleText());
        assertEquals(dto.getNciIdentifier(), bo.getNciIdentifier());
    }

}
