package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;

public class AddressPartTypeTransformer implements Transformer<org.iso._21090.AddressPartType, AddressPartType> {

    public AddressPartType transform(org.iso._21090.AddressPartType input) throws DtoTransformException {
        if (input == null)
            return null;
        AddressPartType res = AddressPartType.valueOf(input.getValue());
        return res;
    }

    public AddressPartType transform(org.iso._21090.AddressPartType input, AddressPartType res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res = AddressPartType.valueOf(input.getValue());
        return res;
    }

    public org.iso._21090.AddressPartType transform(gov.nih.nci.coppa.iso.AddressPartType input)
            throws DtoTransformException {
        if (input == null)
            return null;
        org.iso._21090.AddressPartType res = org.iso._21090.AddressPartType.fromString(input.name());
        return res;
    }

    public org.iso._21090.AddressPartType transform(gov.nih.nci.coppa.iso.AddressPartType input,
            org.iso._21090.AddressPartType res) throws DtoTransformException {
        if (input == null)
            return null;
        res = org.iso._21090.AddressPartType.fromString(input.name());
        return res;
    }

}
