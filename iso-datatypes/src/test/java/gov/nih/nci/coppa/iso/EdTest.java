package gov.nih.nci.coppa.iso;

import java.net.URI;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mshestopalov
 */
public class EdTest {

    private Ed t;
    private String phrase = "this+is+the=way+the+world+ends";

    @Before
    public void init() {
        t = new Ed();
    }

    @Test
    public void testEquality() {
        t.setCharset(phrase);
        t.setCompression(Compression.BZ);
        t.setData(phrase.getBytes());
        St st = new St();
        st.setValue(phrase);
        t.setDescription(st);
        t.setIntegrityCheck(phrase.getBytes());
        t.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
        t.setMediaType("text/plain");
        t.setNullFlavor(NullFlavor.NA);
        TelUrl a = new TelUrl();
        a.setValue(URI.create("http:"+phrase));
        t.setReference(a);
        t.setThumbnail(new Ed());
        t.setValue(phrase);
        t.setXml(phrase);
        assertTrue(t.equals(t));
        assertFalse(t.equals(null));

        Ed t2 = new Ed();
        t2.setCharset(phrase);
        t2.setCompression(Compression.BZ);
        t2.setData(phrase.getBytes());
        t2.setDescription(st);
        t2.setIntegrityCheck(phrase.getBytes());
        t2.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
        t2.setMediaType("text/plain");
        t2.setNullFlavor(NullFlavor.NA);
        t2.setReference(a);
        t2.setThumbnail(new Ed());
        t2.setValue(phrase);
        t2.setXml(phrase);

        assertTrue(t.equals(t2));

        St st2 = new St();
        st2.setValue(phrase+"something extra");
        t2.setDescription(st2);

        assertFalse(t.equals(t2));
    }

    @Test
    public void testHashCode() {
        t.setCharset(phrase);
        t.setCompression(Compression.BZ);
        t.setData(phrase.getBytes());
        St st = new St();
        st.setValue(phrase);
        t.setDescription(st);
        t.setIntegrityCheck(phrase.getBytes());
        t.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
        t.setMediaType("text/plain");
        t.setNullFlavor(NullFlavor.NA);
        TelUrl a = new TelUrl();
        a.setValue(URI.create("http:"+phrase));
        t.setReference(a);
        t.setThumbnail(new Ed());
        t.setValue(phrase);
        t.setXml(phrase);
        assertTrue(t.equals(t));
        assertFalse(t.equals(null));

        Ed t2 = new Ed();
        t2.setCharset(phrase);
        t2.setCompression(Compression.BZ);
        t2.setData(phrase.getBytes());
        t2.setDescription(st);
        t2.setIntegrityCheck(phrase.getBytes());
        t2.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
        t2.setMediaType("text/plain");
        t2.setNullFlavor(NullFlavor.NA);
        t2.setReference(a);
        t2.setThumbnail(new Ed());
        t2.setValue(phrase);
        t2.setXml(phrase);

        assertEquals(t.hashCode(), t2.hashCode());
        St st2 = new St();
        st2.setValue(phrase+"something extra");
        t2.setDescription(st2);
        assertFalse(t.hashCode() == t2.hashCode());
    }

    @Test
    public void testCloneable() throws CloneNotSupportedException {
        t.setCharset(phrase);
        t.setCompression(Compression.BZ);
        t.setData(phrase.getBytes());
        St st = new St();
        st.setValue(phrase);
        t.setDescription(st);
        t.setIntegrityCheck(phrase.getBytes());
        t.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
        t.setMediaType("text/plain");
        t.setNullFlavor(NullFlavor.NA);
        TelUrl a = new TelUrl();
        a.setValue(URI.create("http:"+phrase));
        t.setReference(a);
        t.setThumbnail(new Ed());
        t.setValue(phrase);
        t.setXml(phrase);
        assertTrue(t.equals(t));
        assertFalse(t.equals(null));

        Ed t2 = (Ed) t.clone();

        assertTrue(t != t2);
        assertTrue(t.equals(t2));
        assertEquals(t.hashCode(), t2.hashCode());
    }


}