package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lpower
 */
public class TelEmailTest {

    private TelPerson t;
    private String TEL = "tel:";
    private String MAILTO = "mailto:";
    private String phrase = "this is the way the world ends";

    @Before
    public void init() {
        t = new TelEmail();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueAny() {
        t.setValue(phrase);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueTel() {
        t.setValue(TEL + phrase);
        assertEquals(t.getValue(), (TEL + phrase));
    }

    @Test
    public void testValueMailto() {
        t.setValue(MAILTO + "not with a bang, but a whimper");
        assertEquals(t.getValue(), (MAILTO + "not with a bang, but a whimper"));
    }

}