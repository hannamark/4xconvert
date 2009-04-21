package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.EdText;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.UncertaintyType;
import gov.nih.nci.coppa.services.pa.grid.dto.INTTransformer;

import java.util.Arrays;
import java.util.Date;

import org.iso._21090.ED;
import org.iso._21090.EDText;
import org.iso._21090.INT;
import org.iso._21090.NullFlavor;
import org.junit.Test;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public class INTTransformerTest extends AbstractTransformerTestBase<INTTransformer, INT, Int>{

        public Date VALUE_DATE = new Date();

        @Override
        public INT makeXmlSimple() {
            INT x = new INT();
            x.setValue(VALUE_DATE.getDate());
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
            INT uncert = new INT();
            uncert.setValue(VALUE_DATE.getDate());
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            x.setUncertainty(uncert);
            x.setUncertaintyType(org.iso._21090.UncertaintyType.B);
            return x;
        }

        @Override
        public Int makeDtoSimple() {
            Int x = new Int();
            x.setValue(VALUE_DATE.getDate());
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
            Int uncert = new Int();
            uncert.setValue(VALUE_DATE.getDate());
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            x.setUncertainty(uncert);
            x.setUncertaintyType(UncertaintyType.B);
            return x;
        }

        @Override
        public void verifyXmlSimple(INT x) {
            assertEquals(VALUE_DATE.getDate(), x.getValue().intValue());
            ED ed = new EDTransformerTest().makeXmlSimple();
            assertEquals(ed.getValue(), x.getOriginalText().getValue());
            assertEquals(ed.getNullFlavor(), x.getOriginalText().getNullFlavor());
            assertEquals(ed.getCharset(), x.getOriginalText().getCharset());
            assertTrue(Arrays.equals(ed.getData(), x.getOriginalText().getData()));
            assertEquals(ed.getMediaType(), x.getOriginalText().getMediaType());
            assertEquals(ed.getReference().getValue(), x.getOriginalText().getReference().getValue());
            assertEquals(ed.getXml(), x.getOriginalText().getXml());

            assertEquals(VALUE_DATE.getDate(), ((INT) x.getUncertainty()).getValue().intValue());
            assertNotNull(x.getUncertainty());
            assertEquals(org.iso._21090.UncertaintyType.B, x.getUncertaintyType());
        }

        @Override
        public void verifyDtoSimple(Int x) {
            assertEquals(VALUE_DATE.getDate(), x.getValue().intValue());
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
            Int uncert = new Int();
            uncert.setValue(VALUE_DATE.getDate());
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            assertEquals(uncert, x.getUncertainty());
            assertEquals(UncertaintyType.B, x.getUncertaintyType());
        }

        public INT makeXmlNullFlavored() {
            INT x = new INT();
            x.setNullFlavor(NullFlavor.NI);
            return x;
        }

        public void verifyDtoNullFlavored(Int dto) {
            assertNull(dto.getValue());
            assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
        }

        @Test
        public void testIntNull() throws Exception {
            Int ts = new Int();
            ts.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.ASKU);
            INT result = INTTransformer.INSTANCE.toXml(ts);
            assertNotNull(result);
            assertEquals(NullFlavor.ASKU, result.getNullFlavor());
            assertNull(result.getValue());
        }
    }
