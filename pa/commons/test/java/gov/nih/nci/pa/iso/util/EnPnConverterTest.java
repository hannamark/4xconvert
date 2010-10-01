package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.iso21090.EnPn;

import org.junit.Test;

public class EnPnConverterTest {

    @Test
    public void testConvertToEnPn() {
        EnPn enpn = EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix");
        assertEquals("Testing prefix", "prefix", enpn.getPart().get(0).getValue());
        assertEquals("Testing first name", "firstName",enpn.getPart().get(1).getValue());
        assertEquals("Testing middle name", "middleName",enpn.getPart().get(2).getValue());
        assertEquals("Testing last name", "lastName",enpn.getPart().get(3).getValue());
        assertEquals("Testing suffix", "suffix",enpn.getPart().get(4).getValue());
    }

    @Test
    public void testConvertEnPnToString() {
        EnPn enpn = EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix");
        assertEquals("Testing Blank name.", EnPnConverter.convertEnPnToString(new EnPn()), "");
        assertEquals("Testing full name.", EnPnConverter.convertEnPnToString(enpn),
        "prefix firstName middleName lastName suffix");
    }

}
