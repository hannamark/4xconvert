package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.services.PoIsoConstraintException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;

/**
 *
 * @author gax
 */
public class StConverterTest {

    @Test
    public void testConvert() {
    
        St value = null;
        
        String expResult = null;
        String result = StConverter.convertToString(value);
        assertEquals(expResult, result);

        value = new St();
        value.setNullFlavor(NullFlavor.NI);
        result = StConverter.convertToString(value);
        assertNull(result);

        expResult = "foo";
        value.setNullFlavor(null);
        value.setValue(expResult);
        result = StConverter.convertToString(value);
        assertEquals(expResult, result);
    }

    @Test( expected = IllegalArgumentException.class )
    public void testConvertBad() {
        St value = new St();
        assertNull(value.getNullFlavor());
        assertNull(value.getValue());

        StConverter.convertToString(value);
    }

    public void testFlavorId() {
        St iso = new St();
        iso.setNullFlavor(NullFlavor.NI);
        StConverter.convertToString(iso);
        iso.setFlavorId("flavorId");
        try {
            StConverter.convertToString(iso);    
            fail();
        }catch(PoIsoConstraintException ex) {
            
        }
    }
    
}