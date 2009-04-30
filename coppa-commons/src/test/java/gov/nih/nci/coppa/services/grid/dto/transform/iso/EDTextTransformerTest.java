package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Compression;
import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.EdText;
import gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.iso._21090.ED;
import org.iso._21090.EDText;
import org.iso._21090.NullFlavor;
import org.junit.Test;

/**
 *
 * @author max
 */
public class EDTextTransformerTest extends AbstractTransformerTestBase<EDTextTransformer, EDText, EdText>{

    @Override
    public EDText makeXmlSimple() {
        ED x = new EDTransformerTest().makeXmlSimple();
        EDText edText = new EDText();
        edText.setCharset(x.getCharset());
        edText.setData(x.getData());
        edText.setMediaType(x.getMediaType());
        edText.setReference(x.getReference());
        edText.setXml(x.getXml());
        edText.setValue(x.getValue());
        edText.setNullFlavor(x.getNullFlavor());
        return edText;
    }

    @Override
    public EdText makeDtoSimple() {
        Ed x = new EDTransformerTest().makeDtoSimple();
        EdText edText = new EdText();
        edText.setCharset(x.getCharset());
        edText.setData(x.getData());
        edText.setMediaType(x.getMediaType());
        edText.setReference(x.getReference());
        edText.setXml(x.getXml());
        edText.setValue(x.getValue());
        edText.setNullFlavor(x.getNullFlavor());
        return edText;
    }

    @Override
    public void verifyXmlSimple(EDText x) {
        assertEquals(EDTransformerTest.CHAR_SET, x.getCharset());
        assertTrue(Arrays.equals(EDTransformerTest.DATA_BYTES, x.getData()));
        assertEquals(EDTransformerTest.MEDIA_TYPE, x.getMediaType());
        assertEquals(EDTransformerTest.URL, x.getReference().getValue());
        assertEquals(EDTransformerTest.VALUE, x.getValue());
        assertEquals(EDTransformerTest.XML, x.getXml());
    }

    @Override
    public void verifyDtoSimple(EdText x) {
        assertEquals(EDTransformerTest.CHAR_SET, x.getCharset());
        assertTrue(Arrays.equals(EDTransformerTest.DATA_BYTES, x.getData()));
        assertEquals(EDTransformerTest.MEDIA_TYPE, x.getMediaType());
        try {
            assertEquals(new URI(EDTransformerTest.URL), x.getReference().getValue());
        } catch (URISyntaxException u) {
            throw new RuntimeException(u);
        }
        assertEquals(EDTransformerTest.VALUE, x.getValue());
        assertEquals(EDTransformerTest.XML, x.getXml());
    }

    public EDText makeXmlNullFlavored() {
        EDText x = new EDText();
        x.setNullFlavor(NullFlavor.NI);
        return x;
    }

    public void verifyDtoNullFlavored(EdText dto) {
        assertNull(dto.getValue());
        assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
    }

    @Test
    public void testEdTextNull() throws Exception {
        EdText ed = new EdText();
        ed.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.ASKU);
        EDText result = EDTextTransformer.INSTANCE.toXml(ed);
        assertNotNull(result);
        assertEquals(NullFlavor.ASKU, result.getNullFlavor());
        assertNull(result.getValue());
    }

    @Test(expected = IllegalArgumentException.class )
    public void testCompression() {
        EdText edText = new EdText();
        edText.setCompression(Compression.DF);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testIntegrityCheck() {
        EdText edText = new EdText();
        edText.setIntegrityCheck(new byte[]{0, 1, 0, 1, 0, 1});
    }

    @Test(expected = IllegalArgumentException.class )
    public void testIntegrityCheckAlgorithm() {
        EdText edText = new EdText();
        edText.setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm.SHA1);
    }

    @Test(expected = IllegalArgumentException.class )
    public void testMediaType() {
        EdText edText = new EdText();
        edText.setMediaType("not correct type");
    }

    @Test(expected = IllegalArgumentException.class )
    public void testThumbnail() {
        EdText edText = new EdText();
        edText.setThumbnail(new EDTransformerTest().makeDtoSimple());
    }

}