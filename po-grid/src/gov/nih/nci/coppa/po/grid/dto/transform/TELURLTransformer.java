package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.TelUrl;

public class TELURLTransformer implements Transformer<org.iso._21090.TELUrl, gov.nih.nci.coppa.iso.TelUrl> {

    public TelUrl transform(org.iso._21090.TELUrl input) throws DtoTransformException {
        TelUrl res = new TelUrl();
        res = transform(input, res);
        return res;
    }

    public TelUrl transform(org.iso._21090.TELUrl input, TelUrl res) throws DtoTransformException {
        if (input == null)
            return null;
        res = (TelUrl) new TELTransformer().transform(input, res);
        return res;
    }

    public org.iso._21090.TELUrl transform(gov.nih.nci.coppa.iso.TelUrl input) throws DtoTransformException {
        org.iso._21090.TELUrl res = new org.iso._21090.TELUrl();
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.TELUrl transform(gov.nih.nci.coppa.iso.TelUrl input, org.iso._21090.TELUrl res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res = (org.iso._21090.TELUrl) new TELTransformer().transform(input, res);
        return res;
    }

}
