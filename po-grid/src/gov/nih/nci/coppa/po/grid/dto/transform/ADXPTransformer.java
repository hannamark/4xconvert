package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;

import java.util.List;
import org.iso._21090.ADXP;

public class ADXPTransformer implements Transformer<org.iso._21090.ADXP, Adxp> {

    public static final ADXPTransformer INSTANCE = new ADXPTransformer();

    private ADXPTransformer() {}

    public ADXP toXml(Adxp input) throws DtoTransformException {
        if (input == null)
            return null;
        ADXP x = new ADXP();
        copyToXml(input, x);
        return x;
    }

    public void copyToDto(ADXP source, Adxp target) throws DtoTransformException {
        target.setCode(source.getCode());
        target.setValue(source.getValue());
    }

    public Adxp toDto(ADXP input) throws DtoTransformException {
        if (input == null)
            return null;
        AddressPartType type = AddressPartTypeTransformer.INSTANCE.toDto(input.getType());
        Adxp d = Adxp.createAddressPart(type);
        copyToDto(input, d);
        return d;
    }

    public void copyToXml(Adxp source, ADXP target) throws DtoTransformException {
        target.setCode(source.getCode());
        target.setValue(source.getValue());
        org.iso._21090.AddressPartType type = AddressPartTypeTransformer.INSTANCE.toXml(source.getType());
        target.setType(type);
    }

    public void copyToDto(List<ADXP> sourcePart, List<Adxp> targetPart) throws DtoTransformException {
        for (ADXP p : sourcePart) {
            targetPart.add(toDto(p));
        }
    }

    public void copyToXml(List<Adxp> sourcePart, List<ADXP> targetPart) throws DtoTransformException {
        for (Adxp p : sourcePart) {
            targetPart.add(toXml(p));
        }
    }
}
