package gov.nih.nci.coppa.iso;

import java.net.URI;
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
    private String phrase = "this+is+the+way+the+world+ends";

    @Before
    public void init() {
        t = new TelPerson();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueAny() {
        t.setValue(URI.create(phrase));
    }

    @Test
    public void testValueTel() {
        String u = TEL + phrase;
        t.setValue(URI.create(u));
        assertEquals(u, t.getValue().toString());
    }

    @Test
    public void testValueXTel() {
        String u = XTEL + phrase;
        t.setValue(URI.create(u));
        assertEquals(u, t.getValue().toString());
    }

    @Test
    public void testValueXFax() {
        String u = XFAX + phrase;
        t.setValue(URI.create(u));
        assertEquals(u, t.getValue().toString());
    }

    @Test
    public void testValueMailto() {
        String u = MAILTO + phrase;
        t.setValue(URI.create(u));
        assertEquals(u, t.getValue().toString());
    }
}