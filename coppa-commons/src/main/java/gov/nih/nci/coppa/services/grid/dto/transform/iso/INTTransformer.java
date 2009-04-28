package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.Int;

import org.iso._21090.INT;

/**
 * Transforms strings.
 * @author mshestopalov
 */
public final class INTTransformer extends QTYTransformer<INT, Int> implements Transformer<INT, Int> {

    /**
     * Public singleton.
     */
    public static final INTTransformer INSTANCE = new INTTransformer();

    private INTTransformer() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected INT newXml() {
        return new INT();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Int newDto() {
        return new Int();
    }

    /**
     * {@inheritDoc}
     */
    public INT toXml(Int input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        INT x = (INT) transformBaseXml(input);
        Integer v = input.getValue();
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
    public Int toDto(INT input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Int d = (Int) transformBaseDto(input);
        Integer v = input.getValue();
        if (v != null) {
            d.setValue(v);
        } else {
            d.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }
        return d;
    }
}
