package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;

/**
 * Transforms individual address parts.
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
