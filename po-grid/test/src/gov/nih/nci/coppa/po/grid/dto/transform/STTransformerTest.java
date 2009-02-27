package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.St;
import org.iso._21090.NullFlavor;
import org.iso._21090.ST;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class STTransformerTest extends AbstractTransformerTestBase<STTransformer, ST, St>{

    @Override
    public org.iso._21090.ST makeXmlSimple() {
        org.iso._21090.ST x = new org.iso._21090.ST();
        x.setValue("v");
        return x;
    }

    @Override
    public gov.nih.nci.coppa.iso.St makeDtoSimple() {
        gov.nih.nci.coppa.iso.St x = new gov.nih.nci.coppa.iso.St();
        x.setValue("v");
        return x;
    }

    @Override
    public void verifyXmlSimple(org.iso._21090.ST x) {
        assertEquals("v", x.getValue());
    }

    @Override
    public void verifyDtoSimple(gov.nih.nci.coppa.iso.St x) {
        assertEquals("v", x.getValue());
    }

    public ST makeXmlNullFlavored() {
        ST x = new ST();
        x.setNullFlavor(NullFlavor.NI);
        return x;
    }

    public void verifyDtoNullFlavored(St dto) {
        assertNull(dto.getValue());
        assertEquals(gov.nih.nci.coppa.iso.NullFlavor.NI, dto.getNullFlavor());
    }
}