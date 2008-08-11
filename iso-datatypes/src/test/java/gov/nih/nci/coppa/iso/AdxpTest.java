package gov.nih.nci.coppa.iso;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class AdxpTest {

    @Test
    public void testCreateAddressPart() {
        for (AddressPartType type: AddressPartType.values()){
            Adxp result = Adxp.createAddressPart(type);
            assertEquals(type, result.getType());
            assertTrue("Adxp"+type.name(), result.getClass().getSimpleName().equalsIgnoreCase("Adxp"+type.name()));
        }
        
        
        Adxp result = Adxp.createAddressPart(null);
        assertEquals(null, result.getType());
    }

}