package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.St;

public class STTransformer implements Transformer<org.iso._21090.ST, gov.nih.nci.coppa.iso.St> {

    public St transform(org.iso._21090.ST input) throws DtoTransformException {
        St res = new St();
        res = transform(input, res);

        return res;
    }

    public St transform(org.iso._21090.ST input, St res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        res.setValue(input.getValue());

        return res;
    }

    public org.iso._21090.ST transform(gov.nih.nci.coppa.iso.St input) throws DtoTransformException {
        org.iso._21090.ST res = new org.iso._21090.ST();
        res = transform(input, res);

        return res;
    }

    public org.iso._21090.ST transform(gov.nih.nci.coppa.iso.St input, org.iso._21090.ST res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        res.setValue(input.getValue());

        return res;
    }

}
