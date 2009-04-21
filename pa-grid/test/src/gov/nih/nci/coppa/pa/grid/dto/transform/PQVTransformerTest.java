package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.EdText;
import gov.nih.nci.coppa.iso.Pqv;
import gov.nih.nci.coppa.iso.UncertaintyType;
import gov.nih.nci.coppa.services.pa.grid.dto.PQVTransformer;

import java.math.BigDecimal;
import java.util.Arrays;

import org.iso._21090.ED;
import org.iso._21090.EDText;
import org.iso._21090.NullFlavor;
import org.iso._21090.PQ;
import org.junit.Test;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public class PQVTransformerTest extends AbstractTransformerTestBase<PQVTransformer, PQ, Pqv>{

        public Double VALUE_DOUBLE = new Double(12345);

        @Override
        public PQ makeXmlSimple() {
            PQ x = new PQ();
            x.setValue(VALUE_DOUBLE);
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
            PQ uncert = new PQ();
            uncert.setValue(VALUE_DOUBLE);
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            x.setUncertainty(uncert);
            x.setUncertaintyType(org.iso._21090.UncertaintyType.B);
            return x;
        }

        @Override
        public Pqv makeDtoSimple() {
            Pqv x = new Pqv();
            x.setValue(BigDecimal.valueOf(VALUE_DOUBLE));
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
            Pqv uncert = new Pqv();
            uncert.setValue(BigDecimal.valueOf(VALUE_DOUBLE));
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            x.setUncertainty(uncert);
            x.setUncertaintyType(UncertaintyType.B);
            return x;
        }

        @Override
        public void verifyXmlSimple(PQ x) {
            assertEquals(VALUE_DOUBLE, x.getValue());
            ED ed = new EDTransformerTest().makeXmlSimple();
            assertEquals(ed.getValue(), x.getOriginalText().getValue());
            assertEquals(ed.getNullFlavor(), x.getOriginalText().getNullFlavor());
            assertEquals(ed.getCharset(), x.getOriginalText().getCharset());
            assertTrue(Arrays.equals(ed.getData(), x.getOriginalText().getData()));
            assertEquals(ed.getMediaType(), x.getOriginalText().getMediaType());
            assertEquals(ed.getReference().getValue(), x.getOriginalText().getReference().getValue());
            assertEquals(ed.getXml(), x.getOriginalText().getXml());
            assertEquals(VALUE_DOUBLE, ((PQ) x.getUncertainty()).getValue());
            assertNotNull(x.getUncertainty());
            assertEquals(org.iso._21090.UncertaintyType.B, x.getUncertaintyType());
        }

        @Override
        public void verifyDtoSimple(Pqv x) {
            assertEquals(BigDecimal.valueOf(VALUE_DOUBLE), x.getValue());
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
            Pqv uncert = new Pqv();
            uncert.setValue(BigDecimal.valueOf(VALUE_DOUBLE));
            uncert.setUncertainty(null);
            uncert.setUncertaintyType(null);
            uncert.setPrecision(0);
            assertEquals(uncert, x.getUncertainty());
            assertEquals(UncertaintyType.B, x.getUncertaintyType());
        }

        public PQ makeXmlNullFlavored() {
            PQ x = new PQ();
            x.setNullFlavor(NullFlavor.NI);
            return x;
        }

        public void verifyDtoNullFlavored(Pqv dto) {
            assertNull(dto.getValue());
            assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
        }

        @Test
        public void testPqvNull() throws Exception {
            Pqv ts = new Pqv();
            ts.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.ASKU);
            PQ result = PQVTransformer.INSTANCE.toXml(ts);
            assertNotNull(result);
            assertEquals(NullFlavor.ASKU, result.getNullFlavor());
            assertNull(result.getValue());
        }
    }
