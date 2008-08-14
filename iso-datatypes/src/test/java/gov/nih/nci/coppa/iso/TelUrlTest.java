package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lpower
 */
public class TelUrlTest {

    private TelUrl t;
    private final String FILE = "file:";
    private final String NFS = "nfs:";
    private final String FTP = "ftp:";
    private final String CID = "cid:";
    private final String HTTP = "http:";
    private final String HTTPS = "https:";
    private final String phrase = "this+is+the+way+the+world+ends";

    @Before
    public void init() {
        t = new TelUrl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueAny() {
        t.setValue(URI.create(phrase));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUse() {
        Set<TelecommunicationAddressUse> a = new HashSet<TelecommunicationAddressUse>();
        a.add(TelecommunicationAddressUse.BAD);
        t.setUse(a);
    }

    @Test
    public void testGetUse() {
        assertEquals(t.getUse(), Collections.EMPTY_SET);
    }

    public static void testAllowed(Tel t, String... allowed) {
        for (String scheme : allowed) {
            String u = scheme + "not+with+a+bang,+but+a+whimper";
            t.setValue(URI.create(u));
            assertEquals(u, t.getValue().toString());
        }
    }

    public static void testDisallowed(Tel t, String... disallowed) {
        for (String scheme : disallowed) {
            String u = scheme + "this+is+the+way+the+world+ends";
            URI uri = URI.create(u);
            try{
                t.setValue(uri);
                fail(scheme);
            } catch (IllegalArgumentException i) {
            }
        }
    }

    @Test
    public void testSchemes() {
        testAllowed(t, FILE, NFS, FTP, CID, HTTP, HTTPS);
        testDisallowed(t, "", "mailto:");
    }


}