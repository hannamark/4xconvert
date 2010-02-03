package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class StringConverterTest {

    @Test
    public void testConvertToSt() {
        String value = null;
        St result = StringConverter.convertToSt(value);
        assertEquals(NullFlavor.NI, result.getNullFlavor());


        value = "foo";
        result = StringConverter.convertToSt(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getValue());


        value = "";
        result = StringConverter.convertToSt(value);
        assertNotNull(result.getNullFlavor());
        assertNull(value, result.getValue());
    }


    @Test
    public void testConvertToEnOn() {
        String value = null;
        EnOn result = StringConverter.convertToEnOn(value);
        assertEquals(NullFlavor.NI, result.getNullFlavor());

        value = "5AM Soluctions, Inc";
        result = StringConverter.convertToEnOn(value);
        assertNull(result.getNullFlavor());
        assertEquals(1, result.getPart().size());
        assertNull(result.getPart().get(0).getType());
        assertEquals(value, result.getPart().get(0).getValue());


        value = "";
        result = StringConverter.convertToEnOn(value);
        assertNull(result.getNullFlavor());
        assertEquals(value, result.getPart().get(0).getValue());
    }

}