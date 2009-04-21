package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Pqv;

import java.math.BigDecimal;

import org.iso._21090.PQ;

/**
 * Transforms strings.
 * @author mshestopalov
 */
public final class PQVTransformer extends QTYTransformer<PQ, Pqv> implements Transformer<PQ, Pqv> {

    /**
     * Public singleton.
     */
    public static final PQVTransformer INSTANCE = new PQVTransformer();

    private PQVTransformer() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PQ newXml() {
        return new PQ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pqv newDto() {
        return new Pqv();
    }

    /**
     * {@inheritDoc}
     */
    public PQ toXml(Pqv input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        PQ x = (PQ) transformBaseXml(input);
        BigDecimal v = input.getValue();
        if (v != null) {
            x.setValue(v.doubleValue());
        } else {
            x.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }
        x.setPrecision(input.getPrecision());


        return x;
    }

    /**
     * {@inheritDoc}
     */
    public Pqv toDto(PQ input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Pqv d = (Pqv) transformBaseDto(input);
        Double v = input.getValue();
        if (v != null) {
            d.setValue(BigDecimal.valueOf(v));
        } else {
            d.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }
        d.setPrecision(input.getPrecision());


        return d;
    }
}
