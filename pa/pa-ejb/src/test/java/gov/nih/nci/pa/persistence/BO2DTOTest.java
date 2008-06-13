package gov.nih.nci.pa.persistence;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;

import org.junit.Test;

public class BO2DTOTest {
    
    @Test
    public void convertTest() {
        Protocol bo = new Protocol();
        bo.setLongTitleText("Long Title");
        bo.setNciIdentifier("NCI ID");
        
        ProtocolDTO dto = BO2DTO.convert(bo);
        assertEquals(bo.getLongTitleText(), dto.getLongTitleText());
        assertEquals(bo.getNciIdentifier(), dto.getNciIdentifier());
    }
}
