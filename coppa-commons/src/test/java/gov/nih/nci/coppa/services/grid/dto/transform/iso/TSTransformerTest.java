package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.EdText;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.iso.UncertaintyType;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.iso._21090.ED;
import org.iso._21090.EDText;
import org.iso._21090.NullFlavor;
import org.iso._21090.TS;
import org.junit.Test;

/**
 *
 * @author max
 */
public class TSTransformerTest extends AbstractTransformerTestBase<TSTransformer, TS, Ts>{

    public final String VALUE_DATE = "19800928023033.0978-0000";
    public final String OVERFLOW_VALUE_DATE = "20090229000000.0000-0000";

    @Override
    public TS makeXmlSimple() {
        TS x = new TS();
        x.setValue(VALUE_DATE);
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
        uncert.setValue(VALUE_DATE);
        uncert.setUncertainty(null);
        uncert.setUncertaintyType(null);
        x.setUncertainty(uncert);
        x.setUncertaintyType(org.iso._21090.UncertaintyType.B);
        return x;
    }

    @Override
    public Ts makeDtoSimple() {
        SimpleDateFormat sdf = new SimpleDateFormat(TSTransformer.FORMAT_STRING);
        sdf.setLenient(false);
        Ts x = new Ts();
        try {
            x.setValue(sdf.parse(VALUE_DATE));
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
            uncert.setValue(sdf.parse(VALUE_DATE));
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            x.setUncertainty(uncert);
            x.setUncertaintyType(UncertaintyType.B);
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
        return x;
    }

    @Override
    public void verifyXmlSimple(TS x) {
        SimpleDateFormat sdf = new SimpleDateFormat(TSTransformer.FORMAT_STRING);
        sdf.setLenient(false);
        try {
            Date number1 = sdf.parse(VALUE_DATE);
            Date number2 = sdf.parse(x.getValue());
            assertEquals(number1.getTime(), number2.getTime());
            ED ed = new EDTransformerTest().makeXmlSimple();
            assertEquals(ed.getValue(), x.getOriginalText().getValue());
            assertEquals(ed.getNullFlavor(), x.getOriginalText().getNullFlavor());
            assertEquals(ed.getCharset(), x.getOriginalText().getCharset());
            assertTrue(Arrays.equals(ed.getData(), x.getOriginalText().getData()));
            assertEquals(ed.getMediaType(), x.getOriginalText().getMediaType());
            assertEquals(ed.getReference().getValue(), x.getOriginalText().getReference().getValue());
            assertEquals(ed.getXml(), x.getOriginalText().getXml());

            number1 = sdf.parse(VALUE_DATE);
            number2 = sdf.parse(((TS) x.getUncertainty()).getValue());
            assertEquals(number1.getTime(), number2.getTime());


            assertNotNull(x.getUncertainty());
            assertEquals(org.iso._21090.UncertaintyType.B, x.getUncertaintyType());
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }

    @Override
    public void verifyDtoSimple(Ts x) {
        SimpleDateFormat sdf = new SimpleDateFormat(TSTransformer.FORMAT_STRING);
        sdf.setLenient(false);
        try {
            assertEquals(sdf.parse(VALUE_DATE).getTime(), x.getValue().getTime());
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
            uncert.setValue(sdf.parse(VALUE_DATE));
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            assertEquals(uncert, x.getUncertainty());
            assertEquals(UncertaintyType.B, x.getUncertaintyType());
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
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

    @Test(expected = ParseException.class)
    public void testOverflow() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(TSTransformer.FORMAT_STRING);
        sdf.setLenient(false);
        Ts ts = new Ts();
        ts.setValue(sdf.parse(OVERFLOW_VALUE_DATE));

        TS result = TSTransformer.INSTANCE.toXml(ts);
        assertNotNull(result);
    }
}
