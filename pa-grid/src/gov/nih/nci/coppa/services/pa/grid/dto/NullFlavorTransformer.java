package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.NullFlavor;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class NullFlavorTransformer implements Transformer<org.iso._21090.NullFlavor, NullFlavor> {

    /**
     * Public singleton.
     */
    public static final NullFlavorTransformer INSTANCE = new NullFlavorTransformer();

    private NullFlavorTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public org.iso._21090.NullFlavor toXml(NullFlavor input) {
        if (input == null) {
            return null;
        }
        return org.iso._21090.NullFlavor.valueOf(input.name());
    }

    /**
     * {@inheritDoc}
     */
    public NullFlavor toDto(org.iso._21090.NullFlavor input) {
        if (input == null) {
            return null;
        }
        return NullFlavor.valueOf(input.value());
    }
}
