package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class StringConverterTest {

    @Test
    public void testConvertToSt() {
        String value = null;
        StringConverter instance = new StringConverter();
        St result = instance.convertToSt(value);
        assertEquals(NullFlavor.NI, result.getNullFlavor());
       
        
        value = "foo";
        result = instance.convertToSt(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());
        
        
        value = "";
        result = instance.convertToSt(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());
    }
    
    
    @Test
    public void testConvertToEnOn() {
        String value = null;
        StringConverter instance = new StringConverter();
        EnOn result = instance.convertToEnOn(value);
        assertEquals(NullFlavor.NI, result.getNullFlavor());
       
        
        value = "5AM Soluctions, Inc";
        result = instance.convertToEnOn(value);
        assertNull(result.getNullFlavor());
        assertEquals(1, result.getPart().size());
        assertNull(result.getPart().get(0).getType());
        assertEquals(value, result.getPart().get(0).getValue());
        
        
        value = "";
        result = instance.convertToEnOn(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getPart().get(0).getValue());
    }

}