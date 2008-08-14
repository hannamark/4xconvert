package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.services.PoIsoConstraintException;
import org.junit.Test;
import static org.junit.Assert.*;

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

        StConverter instance = new StConverter();
        instance.convertToString(value);
    }

    @Test(expected = PoIsoConstraintException.class)
    public void testFlavorId() {
        St iso = new St();
        iso.setNullFlavor(NullFlavor.NI);
        iso.setFlavorId("flavorId");
        StConverter.convertToString(iso);
    }
    
}