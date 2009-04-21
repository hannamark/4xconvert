package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.NullFlavor;

import org.iso._21090.BL;


/**
 * Transforms strings.
 */
public final class BLTransformer implements Transformer<BL, Bl> {

    /**
     * Public singleton.
     */
    public static final BLTransformer INSTANCE = new BLTransformer();

    private BLTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public BL toXml(Bl input) {
        if (input == null) {
            return null;
        }
        BL x = new BL();
        Boolean v = input.getValue();
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
    public Bl toDto(BL input) {
        if (input == null) {
            return null;
        }
        Bl d = new Bl();
        Boolean v = input.isValue();
        if (v != null) {
            d.setValue(v);
        } else {
            NullFlavor nf = NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor());
            d.setNullFlavor(nf);
        }
        return d;
    }
}
