package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author lpower
 */
public class EdTextTest {

    private EdText t;
    private String phrase = "this is the way the world ends";

    @Before
    public void init() {
        t = new EdText();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCompression() {
        t.setCompression(Compression.BZ);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntegrityCheck() {
        byte[] a = phrase.getBytes();
        t.setData(a);
        t.setIntegrityCheck(a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntegrityCheckAlgorithm() {
        t.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThumbnail() {
        t.setThumbnail(new Ed());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMediaTypeSetter() {
        t.setMediaType(phrase);
    }

    @Test
    public void testCharset() {
        t.setCharset(phrase);
        assertEquals(t.getCharset(), phrase);
    }

    @Test
    public void testData() {
        byte[] a = phrase.getBytes();
        t.setData(a);
        assertEquals(t.getData(), a);
    }

    @Test
    public void testDescription() {
        St st = new St();
        st.setValue(phrase);
        t.setDescription(st);
        assertEquals(t.getDescription(), st);
    }
    
    @Test
    public void testMediaType() {
        assertEquals(t.getMediaType(), "text/plain");
    }
    
    @Test
    public void testReference() {
        TelUrl a = new TelUrl();
        a.setValue("http:"+phrase);
        t.setReference(a);
        assertEquals(t.getReference(), a);
    }
    
    @Test
    public void testValue() {
        t.setValue(phrase);
        assertEquals(t.getValue(), phrase);
    }
    
    @Test
    public void testXml() {
        t.setXml(phrase);
        assertEquals(t.getXml(), phrase);
    }
    

}