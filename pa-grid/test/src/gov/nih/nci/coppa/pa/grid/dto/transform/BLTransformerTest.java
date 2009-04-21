package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.services.pa.grid.dto.BLTransformer;

import org.iso._21090.BL;
import org.iso._21090.NullFlavor;
import org.junit.Test;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public class BLTransformerTest extends AbstractTransformerTestBase<BLTransformer, BL, Bl>{

    @Override
    public BL makeXmlSimple() {
        BL x = new BL();
        x.setValue(true);
        return x;
    }

    @Override
    public Bl makeDtoSimple() {
        Bl x = new Bl();
        x.setValue(true);
        return x;
    }

    @Override
    public void verifyXmlSimple(BL x) {
        assertEquals(true, x.isValue());
    }

    @Override
    public void verifyDtoSimple(Bl x) {
        assertEquals(true, x.getValue());
    }

    public BL makeXmlNullFlavored() {
        BL x = new BL();
        x.setNullFlavor(NullFlavor.NI);
        return x;
    }

    public void verifyDtoNullFlavored(Bl dto) {
        assertNull(dto.getValue());
        assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
    }

    @Test
    public void testBlNull() throws Exception {
        Bl bl = new Bl();
        bl.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.ASKU);
        BL result = BLTransformer.INSTANCE.toXml(bl);
        assertNotNull(result);
        assertEquals(NullFlavor.ASKU, result.getNullFlavor());
        assertNull(result.isValue());
    }
}