package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.Ts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.iso._21090.TS;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class TSTransformer extends QTYTransformer<TS, Ts> implements Transformer<TS, Ts> {

    /**
     * Public singleton.
     */
    public static final TSTransformer INSTANCE = new TSTransformer();
    /**
     * Format of iso data type value.
     */
    public static final String FORMAT_STRING = "yyyyMMddHHmmss.SSSSZ";

    private TSTransformer() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TS newXml() {
        return new TS();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Ts newDto() {
        return new Ts();
    }

    /**
     * {@inheritDoc}
     */
    public TS toXml(Ts input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        SimpleDateFormat sDf = new SimpleDateFormat(FORMAT_STRING);
        sDf.setLenient(false);
        TS x = (TS) transformBaseXml(input);

        Date v = input.getValue();
        if (v != null) {
            x.setValue(sDf.format(v));
        } else {
            x.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }

        return x;
    }

    /**
     * {@inheritDoc}
     */
    public Ts toDto(TS input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Ts d = (Ts) transformBaseDto(input);
        SimpleDateFormat sDf = new SimpleDateFormat(FORMAT_STRING);
        sDf.setLenient(false);
        String v = input.getValue();
        if (v != null) {
            try {
                d.setValue(sDf.parse(v));
            } catch (ParseException pe) {
                throw new DtoTransformException(pe);
            }
        } else {
            d.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }

        return d;
    }
}
