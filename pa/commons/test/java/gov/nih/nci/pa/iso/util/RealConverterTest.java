package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Real;

import org.junit.Test;

public class RealConverterTest {
    @Test
    public void testConvertToReal() {
        Real r = RealConverter.convertToReal(null);
        assertEquals(NullFlavor.NI, r.getNullFlavor());
        assertNull(r.getValue());

        r = RealConverter.convertToReal(0d);
        assertNull(r.getNullFlavor());
        assertEquals((Double) 0d, r.getValue());

        r = RealConverter.convertToReal(123.456d);
        assertNull(r.getNullFlavor());
        assertEquals((Double) 123.456d, r.getValue());
    }

    @Test
    public void testConvertToDouble() {
        assertNull(RealConverter.convertToDouble(null));
        Real r = new Real();
        assertNull(RealConverter.convertToDouble(r));
        r.setValue(0d);
        assertEquals((Double) 0d, RealConverter.convertToDouble(r));
        r.setValue(432.456d);
        assertEquals((Double) 432.456d, RealConverter.convertToDouble(r));
        r.setNullFlavor(NullFlavor.TRC);
        assertNull(RealConverter.convertToDouble(r));
        r.setValue(null);
        assertNull(RealConverter.convertToDouble(r));
    }
}
