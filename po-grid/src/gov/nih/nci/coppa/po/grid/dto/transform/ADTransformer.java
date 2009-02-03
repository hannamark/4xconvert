package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Ad;

public class ADTransformer implements Transformer<org.iso._21090.AD, Ad> {

    public gov.nih.nci.coppa.iso.Ad transform(org.iso._21090.AD input) throws DtoTransformException {
        Ad res = new Ad();
        res = transform(input, res);
        return res;
    }

    public Ad transform(org.iso._21090.AD input, Ad res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        if (input.getPart() != null) {
            ADXPTransformer transformer = new ADXPTransformer();
            res.setPart(transformer.transform(input.getPart()));
        }
        return res;
    }

    public org.iso._21090.AD transform(gov.nih.nci.coppa.iso.Ad input) throws DtoTransformException {
        org.iso._21090.AD res = new org.iso._21090.AD();
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.AD transform(gov.nih.nci.coppa.iso.Ad input, org.iso._21090.AD res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        if (input.getPart() != null) {
            ADXPTransformer transformer = new ADXPTransformer();
            res.setPart(transformer.transform(input.getPart()));
        }
        return res;
    }
}
