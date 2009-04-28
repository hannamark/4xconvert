package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.St;

import org.iso._21090.ST;

/**
 * Transforms strings.
 */
public final class STTransformer implements Transformer<ST, St> {

    /**
     * Public singleton.
     */
    public static final STTransformer INSTANCE = new STTransformer();

    private STTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public ST toXml(St input) {
        if (input == null) {
            return null;
        }
        ST x = new ST();
        String v = input.getValue();
        if (v != null) {
            x.setValue(v);
        } else {
            x.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public St toDto(ST input) {
        if (input == null) {
            return null;
        }
        St d = new St();
        String v = input.getValue();
        if (v != null) {
            d.setValue(v);
        } else {
            d.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }
        return d;
    }
}
