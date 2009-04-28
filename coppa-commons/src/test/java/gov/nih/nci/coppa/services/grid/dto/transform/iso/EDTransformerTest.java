package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Compression;
import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm;
import gov.nih.nci.coppa.iso.TelUrl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.iso._21090.ED;
import org.iso._21090.NullFlavor;
import org.iso._21090.TELUrl;
import org.junit.Test;

/**
 *
 * @author max
 */
public class EDTransformerTest extends AbstractTransformerTestBase<EDTransformer, ED, Ed>{

    public static final String CHAR_SET = "charset";
    public static final String CONT_ACT_EXT = "cont_act_ext";
    public static final byte[] DATA_BYTES = {1,0,1,0,1,0,1,0};
    public static final String URL = "http://www.url.com";
    public static final String MEDIA_TYPE = "text/plain";
    public static final String VALUE = "value";
    public static final String XML = "xml";

    @Override
    public ED makeXmlSimple() {
        ED x = new ED();
        x.setCharset(CHAR_SET);
        x.setCompression(org.iso._21090.Compression.DF);
        x.setData(DATA_BYTES);
        x.setIntegrityCheck(DATA_BYTES);
        x.setIntegrityCheckAlgorithm(org.iso._21090.IntegrityCheckAlgorithm.SHA_1);
        x.setMediaType(MEDIA_TYPE);
        TELUrl tel = new TELUrl();
        tel.setValue(URL);
        x.setReference(tel);
        ED thumb = new ED();
        thumb.setNullFlavor(new NullFlavorTransformerTest().makeXmlSimple());
        thumb.setThumbnail(null);
        x.setThumbnail(thumb);

        x.setValue(VALUE);
        x.setXml(XML);

        return x;
    }

    @Override
    public Ed makeDtoSimple() {
        Ed x = new Ed();
        x.setCharset(CHAR_SET);
        x.setCompression(Compression.DF);
        x.setData(DATA_BYTES);
        x.setIntegrityCheck(DATA_BYTES);
        x.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
        x.setMediaType(MEDIA_TYPE);
        TelUrl tel = new TelUrl();
        try {
            URI uri = new URI(URL);
            tel.setValue(uri);
        } catch (URISyntaxException u) {
            throw new RuntimeException(u);
        }
        x.setReference(tel);

        Ed thumb = new Ed();
        thumb.setNullFlavor(new NullFlavorTransformerTest().makeDtoSimple());
        thumb.setThumbnail(null);
        x.setThumbnail(thumb);

        x.setValue(VALUE);
        x.setXml(XML);

        return x;

    }

    @Override
    public void verifyXmlSimple(ED x) {
        assertEquals(CHAR_SET, x.getCharset());
        assertEquals(org.iso._21090.Compression.DF, x.getCompression());
        assertTrue(Arrays.equals(DATA_BYTES, x.getData()));
        assertTrue(Arrays.equals(DATA_BYTES, x.getIntegrityCheck()));
        assertEquals(org.iso._21090.IntegrityCheckAlgorithm.SHA_1, x.getIntegrityCheckAlgorithm());
        assertEquals(MEDIA_TYPE, x.getMediaType());
        assertEquals(URL, x.getReference().getValue());
        assertNull(x.getThumbnail().getThumbnail());
        assertEquals(VALUE, x.getValue());
        assertEquals(XML, x.getXml());
    }

    @Override
    public void verifyDtoSimple(Ed x) {
        assertEquals(CHAR_SET, x.getCharset());
        assertEquals(org.iso._21090.Compression.DF.value(), x.getCompression().name());
        assertTrue(Arrays.equals(DATA_BYTES, x.getData()));
        assertTrue(Arrays.equals(DATA_BYTES, x.getIntegrityCheck()));
        assertEquals(IntegrityCheckAlgorithm.SHA1.name(), x.getIntegrityCheckAlgorithm().name());
        assertEquals(MEDIA_TYPE, x.getMediaType());
        try {
            assertEquals(new URI(URL), x.getReference().getValue());
        } catch (URISyntaxException u) {
            throw new RuntimeException(u);
        }
        assertNull(x.getThumbnail().getThumbnail());
        assertEquals(VALUE, x.getValue());
        assertEquals(XML, x.getXml());
    }

    public ED makeXmlNullFlavored() {
        ED x = new ED();
        x.setNullFlavor(NullFlavor.NI);
        return x;
    }

    public void verifyDtoNullFlavored(Ed dto) {
        assertNull(dto.getValue());
        assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
    }

    @Test
    public void testEdNull() throws Exception {
        Ed ed = new Ed();
        ed.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.ASKU);
        ED result = EDTransformer.INSTANCE.toXml(ed);
        assertNotNull(result);
        assertEquals(NullFlavor.ASKU, result.getNullFlavor());
        assertNull(result.getValue());
    }
}