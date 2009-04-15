package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.St;

import org.iso._21090.ST;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class STTransformer implements Transformer<ST, St> {

    /**
     * Public singleton.
     */
    public static final STTransformer INSTANCE = new STTransformer();

    private STTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public ST toXml(St input) {
        if (input == null) {
            return null;
        }
        ST x = new ST();
        String v = input.getValue();
        if (v != null) {
            x.setValue(v);
        } else {
            x.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public St toDto(ST input) {
        if (input == null) {
            return null;
        }
        St d = new St();
        String v = input.getValue();
        if (v != null) {
            d.setValue(v);
        } else {
            d.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }
        return d;
    }
}
