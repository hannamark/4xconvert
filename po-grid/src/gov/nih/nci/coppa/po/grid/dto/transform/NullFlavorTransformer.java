package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.NullFlavor;

/**
 * Transforms null flavors.
 */
final class NullFlavorTransformer implements Transformer<org.iso._21090.NullFlavor, NullFlavor> {

    /**
     * Public singleton.
     */
    public static final NullFlavorTransformer INSTANCE = new NullFlavorTransformer();

    private NullFlavorTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public org.iso._21090.NullFlavor toXml(NullFlavor input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return org.iso._21090.NullFlavor.valueOf(input.name());
    }

    /**
     * {@inheritDoc}
     */
    public NullFlavor toDto(org.iso._21090.NullFlavor input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return NullFlavor.valueOf(input.value());
    }
}
