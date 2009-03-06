package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;

import java.util.List;

import org.iso._21090.ADXP;

/**
 * Transforms the parts of an address.
 */
final class ADXPTransformer implements Transformer<org.iso._21090.ADXP, Adxp> {

    public static final ADXPTransformer INSTANCE = new ADXPTransformer();

    private ADXPTransformer() {
    }

    public ADXP toXml(Adxp input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ADXP x = new ADXP();
        x.setCode(input.getCode());
        x.setValue(input.getValue());
        org.iso._21090.AddressPartType type = AddressPartTypeTransformer.INSTANCE.toXml(input.getType());
        x.setType(type);
        return x;
    }

    public Adxp toDto(ADXP input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        AddressPartType type = AddressPartTypeTransformer.INSTANCE.toDto(input.getType());
        Adxp d = Adxp.createAddressPart(type);
        d.setCode(input.getCode());
        d.setValue(input.getValue());
        return d;
    }

    // PO-852: this isn't an interface method
    public static void copyToDto(List<ADXP> sourcePart, List<Adxp> targetPart) throws DtoTransformException {
        for (ADXP p : sourcePart) {
            targetPart.add(INSTANCE.toDto(p));
        }
    }

    // PO-852: this isn't an interface method
    public static void copyToXml(List<Adxp> sourcePart, List<ADXP> targetPart) throws DtoTransformException {
        for (Adxp p : sourcePart) {
            targetPart.add(INSTANCE.toXml(p));
        }
    }
}
