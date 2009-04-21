package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.EdText;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.iso.UncertaintyType;
import gov.nih.nci.coppa.services.pa.grid.dto.TSTransformer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.iso._21090.ED;
import org.iso._21090.EDText;
import org.iso._21090.NullFlavor;
import org.iso._21090.TS;
import org.junit.Test;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public class TSTransformerTest extends AbstractTransformerTestBase<TSTransformer, TS, Ts>{


    public Date VALUE_DATE = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat(TSTransformer.FORMAT_STRING);

    @Override
    public TS makeXmlSimple() {
        TS x = new TS();
        x.setValue(sdf.format(VALUE_DATE));
        EDText edText = new EDText();
        ED ed = new EDTransformerTest().makeXmlSimple();
        edText.setCharset(ed.getCharset());
        edText.setData(ed.getData());
        edText.setMediaType(ed.getMediaType());
        edText.setReference(ed.getReference());
        edText.setXml(ed.getXml());
        edText.setValue(ed.getValue());
        edText.setNullFlavor(ed.getNullFlavor());
        x.setOriginalText(edText);
        TS uncert = new TS();
        uncert.setValue(sdf.format(VALUE_DATE));
        uncert.setUncertainty(null);
        uncert.setUncertaintyType(null);
        x.setUncertainty(uncert);
        x.setUncertaintyType(org.iso._21090.UncertaintyType.B);
        return x;
    }

    @Override
    public Ts makeDtoSimple() {
        Ts x = new Ts();
        x.setValue(VALUE_DATE);
        EdText edText = new EdText();
        Ed ed = new EDTransformerTest().makeDtoSimple();
        edText.setCharset(ed.getCharset());
        edText.setData(ed.getData());
        edText.setMediaType(ed.getMediaType());
        edText.setReference(ed.getReference());
        edText.setXml(ed.getXml());
        edText.setValue(ed.getValue());
        edText.setNullFlavor(ed.getNullFlavor());
        x.setOriginalText(edText);
        Ts uncert = new Ts();
        uncert.setValue(VALUE_DATE);
        uncert.setUncertainty(null);
        uncert.setUncertaintyType(null);
        x.setUncertainty(uncert);
        x.setUncertaintyType(UncertaintyType.B);
        return x;
    }

    @Override
    public void verifyXmlSimple(TS x) {
        assertEquals(sdf.format(VALUE_DATE), x.getValue());
        ED ed = new EDTransformerTest().makeXmlSimple();
        assertEquals(ed.getValue(), x.getOriginalText().getValue());
        assertEquals(ed.getNullFlavor(), x.getOriginalText().getNullFlavor());
        assertEquals(ed.getCharset(), x.getOriginalText().getCharset());
        assertTrue(Arrays.equals(ed.getData(), x.getOriginalText().getData()));
        assertEquals(ed.getMediaType(), x.getOriginalText().getMediaType());
        assertEquals(ed.getReference().getValue(), x.getOriginalText().getReference().getValue());
        assertEquals(ed.getXml(), x.getOriginalText().getXml());

        assertEquals(sdf.format(VALUE_DATE), ((TS) x.getUncertainty()).getValue());
        assertNotNull(x.getUncertainty());
        assertEquals(org.iso._21090.UncertaintyType.B, x.getUncertaintyType());
    }

    @Override
    public void verifyDtoSimple(Ts x) {
        assertEquals(VALUE_DATE, x.getValue());
        EdText edText = new EdText();
        Ed ed = new EDTransformerTest().makeDtoSimple();
        edText.setCharset(ed.getCharset());
        edText.setData(ed.getData());
        edText.setMediaType(ed.getMediaType());
        edText.setReference(ed.getReference());
        edText.setXml(ed.getXml());
        edText.setValue(ed.getValue());
        edText.setNullFlavor(ed.getNullFlavor());
        assertEquals(edText, x.getOriginalText());
        Ts uncert = new Ts();
        uncert.setValue(VALUE_DATE);
        uncert.setUncertainty(null);
        uncert.setUncertaintyType(null);
        assertEquals(uncert, x.getUncertainty());
        assertEquals(UncertaintyType.B, x.getUncertaintyType());
    }

    public TS makeXmlNullFlavored() {
        TS x = new TS();
        x.setNullFlavor(NullFlavor.NI);
        return x;
    }

    public void verifyDtoNullFlavored(Ts dto) {
        assertNull(dto.getValue());
        assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
    }

    @Test
    public void testTsNull() throws Exception {
        Ts ts = new Ts();
        ts.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.ASKU);
        TS result = TSTransformer.INSTANCE.toXml(ts);
        assertNotNull(result);
        assertEquals(NullFlavor.ASKU, result.getNullFlavor());
        assertNull(result.getValue());
    }
}