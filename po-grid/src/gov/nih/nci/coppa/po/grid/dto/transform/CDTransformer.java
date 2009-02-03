package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Cd;

public class CDTransformer implements Transformer<org.iso._21090.CD, gov.nih.nci.coppa.iso.Cd> {

    public Cd transform(org.iso._21090.CD input) throws DtoTransformException {
        Cd res = new Cd();
        res = transform(input, res);

        return res;
    }

    public Cd transform(org.iso._21090.CD input, Cd res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setCode(input.getCode());
        res.setCodeSystem(input.getCodeSystem());
        res.setCodeSystemName(input.getCodeSystemName());
        res.setCodeSystemVersion(input.getCodeSystemVersion());
        res.setDisplayName(new STTransformer().transform(input.getDisplayName()));
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        res.setOriginalText(new EDTEXTTransformer().transform(input.getOriginalText()));

        return res;
    }

    public org.iso._21090.CD transform(gov.nih.nci.coppa.iso.Cd input) throws DtoTransformException {
        org.iso._21090.CD res = new org.iso._21090.CD();
        res = transform(input, res);

        return res;
    }

    public org.iso._21090.CD transform(gov.nih.nci.coppa.iso.Cd input, org.iso._21090.CD res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res.setCode(input.getCode());
        res.setCodeSystem(input.getCodeSystem());
        res.setCodeSystemName(input.getCodeSystemName());
        res.setCodeSystemVersion(input.getCodeSystemVersion());
        res.setDisplayName(new STTransformer().transform(input.getDisplayName()));
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        res.setOriginalText(new EDTEXTTransformer().transform(input.getOriginalText()));

        return res;
    }

}
