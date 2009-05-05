package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.iso._21090.IVLTS;
import org.iso._21090.TS;

/**
 * Tests for timestamp interval transformer.
 */
public class IVLTSTransformerTest extends AbstractTransformerTestBase<IVLTSTransformer, IVLTS, Ivl<Ts>> {

    @Override
    public Ivl<Ts> makeDtoSimple() {
        Ivl<Ts> result = new Ivl<Ts>();
        result.setAny(getTs(1));
        result.setHigh(getTs(2));
        result.setHighClosed(Boolean.TRUE);
        result.setLow(getTs(-1));
        result.setLowClosed(Boolean.FALSE);
        result.setOriginalText(new EDTextTransformerTest().makeDtoSimple());
        result.setWidth(getTs(3));

        return result;
    }

    @Override
    public IVLTS makeXmlSimple() {
        IVLTS result = new IVLTS();
        result.setHigh(getTS(2));
        result.setHighClosed(Boolean.TRUE);
        result.setLow(getTS(-1));
        result.setLowClosed(Boolean.FALSE);
        result.setOriginalText(new EDTextTransformerTest().makeXmlSimple());
        result.setWidth(getTS(3));

        return result;
    }

    @Override
    public void verifyDtoSimple(Ivl<Ts> x) {
        // instead of doing expected.equals(x), we need to account for PO-1054
        Ivl<Ts> expected = makeDtoSimple();
        assertNull(x.getAny()); // Proves PO-1054 is still around
        assertEquals(expected.getHigh(), x.getHigh());
        assertEquals(expected.getHighClosed(), x.getHighClosed());
        assertEquals(expected.getLow(), x.getLow());
        assertEquals(expected.getLowClosed(), x.getLowClosed());
        assertEquals(expected.getOriginalText(), x.getOriginalText());
        assertEquals(expected.getWidth(), x.getWidth());
    }

    @Override
    public void verifyXmlSimple(IVLTS x) {
        IVLTS expected = makeXmlSimple();
        assertEquals(expected.getHigh().getValue(), x.getHigh().getValue());
        assertEquals(expected.isHighClosed(), x.isHighClosed());
        assertEquals(expected.getLow().getValue(), x.getLow().getValue());
        assertEquals(expected.isLowClosed(), x.isLowClosed());
        assertEquals(expected.getOriginalText().getValue(), x.getOriginalText().getValue());
        assertEquals(((TS) expected.getWidth()).getValue(), ((TS) x.getWidth()).getValue());

    }

    private Ts getTs(int offsetFromDefault) {
        Ts result = new TSTransformerTest().makeDtoSimple();
        Calendar c = Calendar.getInstance();
        c.setTime(result.getValue());
        c.add(Calendar.DATE, offsetFromDefault);
        result.setValue(c.getTime());

        return result;
    }

    private TS getTS(int offsetFromDefault) {
        SimpleDateFormat sdf = new SimpleDateFormat(TSTransformer.FORMAT_STRING);
        TS result = new TSTransformerTest().makeXmlSimple();
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(result.getValue()));
        } catch (ParseException e) {
            fail(e.toString());
        }
        c.add(Calendar.DATE, offsetFromDefault);
        result.setValue(sdf.format(c.getTime()));

        return result;
    }

}
