package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;

public class AddressPartTypeTransformer implements Transformer<org.iso._21090.AddressPartType, AddressPartType> {

    public static final AddressPartTypeTransformer INSTANCE = new AddressPartTypeTransformer();

    private AddressPartTypeTransformer() {}

    public org.iso._21090.AddressPartType toXml(AddressPartType input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return org.iso._21090.AddressPartType.fromValue(input.name());
    }

    public void copyToXml(AddressPartType source, org.iso._21090.AddressPartType target) throws DtoTransformException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public AddressPartType toDto(org.iso._21090.AddressPartType input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return AddressPartType.valueOf(input.name());
    }

    public void copyToDto(org.iso._21090.AddressPartType source, AddressPartType target) throws DtoTransformException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
