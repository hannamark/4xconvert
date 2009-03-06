package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;

/**
 * Transforms individual address parts.
 */
final class AddressPartTypeTransformer implements Transformer<org.iso._21090.AddressPartType, AddressPartType> {

    public static final AddressPartTypeTransformer INSTANCE = new AddressPartTypeTransformer();

    private AddressPartTypeTransformer() {
    }

    public org.iso._21090.AddressPartType toXml(AddressPartType input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return org.iso._21090.AddressPartType.fromValue(input.name());
    }

    public AddressPartType toDto(org.iso._21090.AddressPartType input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return AddressPartType.valueOf(input.name());
    }
}
