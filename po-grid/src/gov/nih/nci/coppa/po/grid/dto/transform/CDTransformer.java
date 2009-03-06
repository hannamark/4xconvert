package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Cd;

import org.iso._21090.CD;

/**
 * Transforms coded data elements.
 */
public final class CDTransformer implements Transformer<CD, Cd> {

    /**
     * Singleton for client consumption.
     */
    public static final CDTransformer INSTANCE = new CDTransformer();

    private CDTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Cd toDto(CD input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Cd res = new Cd();
        String v = input.getCode();
        if (v != null) {
            res.setCode(v);
        } else {
            res.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    public CD toXml(Cd input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        CD res = new CD();
        String v = input.getCode();
        if (v != null) {
            res.setCode(v);
        } else {
            res.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }
        return res;
    }
}
