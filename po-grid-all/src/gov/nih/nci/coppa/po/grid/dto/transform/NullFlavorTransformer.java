package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.NullFlavor;

class NullFlavorTransformer implements Transformer<org.iso._21090.NullFlavor,NullFlavor> {

    public static final NullFlavorTransformer INSTANCE = new NullFlavorTransformer();

    private NullFlavorTransformer() {}


    public org.iso._21090.NullFlavor toXml(NullFlavor input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return org.iso._21090.NullFlavor.valueOf(input.name());
    }

    public NullFlavor toDto(org.iso._21090.NullFlavor input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return NullFlavor.valueOf(input.value());
    }
}
