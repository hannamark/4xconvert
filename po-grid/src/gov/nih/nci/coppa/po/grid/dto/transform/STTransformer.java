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
        copyToXml(input, x);
        return x;
    }

    public void copyToXml(St source, ST target) throws DtoTransformException {
        String v = source.getValue();
        if (v != null) {
            target.setValue(v);
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(source.getNullFlavor()));
        }
    }

    public St toDto(ST input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        St d = new St();
        copyToDto(input, d);
        return d;
    }

    public void copyToDto(ST source, St target) throws DtoTransformException {
        String v = source.getValue();
        if (v != null) {
            target.setValue(v);
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
        }
    }

}
