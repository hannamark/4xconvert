package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Cd;

public class CDTransformer implements Transformer<org.iso._21090.CD, gov.nih.nci.coppa.iso.Cd> {

    public static final CDTransformer INSTANCE = new CDTransformer();

    private CDTransformer() {}


    public Cd toDto(org.iso._21090.CD input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Cd res = new Cd();
        copyToDto(input, res);
        return res;
    }

    public void copyToDto(org.iso._21090.CD source, Cd target) throws DtoTransformException {
        String v = source.getCode();
        if (v != null) {
            target.setCode(v);
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
        }
    }

    public org.iso._21090.CD toXml(gov.nih.nci.coppa.iso.Cd input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        org.iso._21090.CD res = new org.iso._21090.CD();
        copyToXml(input, res);
        return res;
    }

    public void copyToXml(gov.nih.nci.coppa.iso.Cd source, org.iso._21090.CD target)
            throws DtoTransformException {
        String v = source.getCode();
        if (v != null) {
            target.setCode(v);
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(source.getNullFlavor()));
        }
    }
}
