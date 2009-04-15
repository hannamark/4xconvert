package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.AddressPartType;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class AddressPartTypeTransformer implements Transformer<org.iso._21090.AddressPartType, AddressPartType> {
    /**
     * Public singleton.
     */

    public static final AddressPartTypeTransformer INSTANCE = new AddressPartTypeTransformer();

    private AddressPartTypeTransformer() {
    }
    /**
     * {@inheritDoc}
     */

    public org.iso._21090.AddressPartType toXml(AddressPartType input) {
        if (input == null) {
            return null;
        }
        return org.iso._21090.AddressPartType.fromValue(input.name());
    }
    /**
     * {@inheritDoc}
     */

    public AddressPartType toDto(org.iso._21090.AddressPartType input) {
        if (input == null) {
            return null;
        }
        return AddressPartType.valueOf(input.name());
    }
}
