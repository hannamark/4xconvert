package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lpower
 */
public class TelPersonTest {

    private TelPerson t;
    private String TEL = "tel:";
    private String XTEL = "x-text-tel:";
    private String XFAX = "x-text-fax:";
    private String MAILTO = "mailto:";
    private String phrase = "this is the way the world ends";

    @Before
    public void init() {
        t = new TelPerson();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueAny() {
        t.setValue(phrase);
    }

    @Test
    public void testValueTel() {
        t.setValue(TEL + phrase);
        assertEquals(t.getValue(), (TEL + phrase));
    }

    @Test
    public void testValueXTel() {
        t.setValue(XTEL + phrase);
        assertEquals(t.getValue(), (XTEL + phrase));
    }

    @Test
    public void testValueXFax() {
        t.setValue(XFAX + phrase);
        assertEquals(t.getValue(), (XFAX + phrase));
    }

    @Test
    public void testValueMailto() {
        t.setValue(MAILTO + "not with a bang, but a whimper");
        assertEquals(t.getValue(), (MAILTO + "not with a bang, but a whimper"));
    }

}