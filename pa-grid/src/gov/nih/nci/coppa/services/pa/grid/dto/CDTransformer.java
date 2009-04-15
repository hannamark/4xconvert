package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.Cd;

import org.iso._21090.CD;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class CDTransformer implements Transformer<CD, Cd> {

    /**
     * Singleton for client consumption.
     */
    public static final CDTransformer INSTANCE = new CDTransformer();

    private CDTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Cd toDto(CD input) {
        if (input == null) {
            return null;
        }
        Cd res = new Cd();
        String v = input.getCode();
        if (v != null) {
            res.setCode(v);
        } else {
            res.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    public CD toXml(Cd input) {
        if (input == null) {
            return null;
        }
        CD res = new CD();
        String v = input.getCode();
        if (v != null) {
            res.setCode(v);
        } else {
            res.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }
        return res;
    }
}
