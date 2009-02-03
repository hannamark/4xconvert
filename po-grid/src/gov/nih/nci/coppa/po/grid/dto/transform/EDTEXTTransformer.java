package gov.nih.nci.coppa.po.grid.dto.transform;

public class EDTEXTTransformer implements Transformer<org.iso._21090.EDText, gov.nih.nci.coppa.iso.EdText> {

    public gov.nih.nci.coppa.iso.EdText transform(org.iso._21090.EDText input) throws DtoTransformException {
        gov.nih.nci.coppa.iso.EdText res = new gov.nih.nci.coppa.iso.EdText();
        res = transform(input, res);
        return res;
    }

    public gov.nih.nci.coppa.iso.EdText transform(org.iso._21090.EDText input, gov.nih.nci.coppa.iso.EdText res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res = (gov.nih.nci.coppa.iso.EdText) new EDTransformer().transform(input, res);
        return res;
    }

    public org.iso._21090.EDText transform(gov.nih.nci.coppa.iso.EdText input) throws DtoTransformException {
        org.iso._21090.EDText res = new org.iso._21090.EDText();
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.EDText transform(gov.nih.nci.coppa.iso.EdText input, org.iso._21090.EDText res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res = (org.iso._21090.EDText) new EDTransformer().transform(input, res);
        return res;
    }

}
