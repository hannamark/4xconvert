package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;

/**
 * Transforms null flavors.
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
