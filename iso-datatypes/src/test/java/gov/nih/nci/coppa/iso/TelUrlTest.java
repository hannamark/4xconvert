package gov.nih.nci.coppa.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    private String FILE = "file:";
    private String NFS = "nfs:";
    private String FTP = "ftp:";
    private String CID = "cid:";
    private String HTTP = "http:";
    private String HTTPS = "https:";
    private String phrase = "this is the way the world ends";

    @Before
    public void init() {
        t = new TelUrl();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueAny() {
        t.setValue(phrase);
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

    @Test
    public void testValueFile() {
        t.setValue(FILE + phrase);
        assertEquals(t.getValue(), (FILE + phrase));
    }

    @Test
    public void testValueNfs() {
        t.setValue(NFS + phrase);
        assertEquals(t.getValue(), (NFS + phrase));
    }

    @Test
    public void testValueFtp() {
        t.setValue(FTP + phrase);
        assertEquals(t.getValue(), (FTP + phrase));
    }

    @Test
    public void testValueCid() {
        t.setValue(CID + phrase);
        assertEquals(t.getValue(), (CID + phrase));
    }

    @Test
    public void testValueHttp() {
        t.setValue(HTTP + phrase);
        assertEquals(t.getValue(), (HTTP + phrase));
    }

    @Test
    public void testValueHttps() {
        t.setValue(HTTPS + "not with a bang, but a whimper");
        assertEquals(t.getValue(), (HTTPS + "not with a bang, but a whimper"));
    }

}