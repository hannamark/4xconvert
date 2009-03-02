package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.St;

import org.iso._21090.ST;

public class STTransformer implements Transformer<ST, St> {

    public static final STTransformer INSTANCE = new STTransformer();

    private STTransformer() {}

    public ST toXml(St input) throws DtoTransformException {
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

    public St toDto(ST input) throws DtoTransformException {
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
