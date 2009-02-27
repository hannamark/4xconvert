package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.NullFlavor;

public class NullFlavorTransformer implements Transformer<org.iso._21090.NullFlavor,NullFlavor> {

    public static final NullFlavorTransformer INSTANCE = new NullFlavorTransformer();

    private NullFlavorTransformer() {}

	
    public org.iso._21090.NullFlavor toXml(NullFlavor input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return org.iso._21090.NullFlavor.valueOf(input.name());
    }

    public void copyToXml(NullFlavor source, org.iso._21090.NullFlavor target) throws DtoTransformException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NullFlavor toDto(org.iso._21090.NullFlavor input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return NullFlavor.valueOf(input.value());
    }

    public void copyToDto(org.iso._21090.NullFlavor source, NullFlavor target) throws DtoTransformException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
