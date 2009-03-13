package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;

import org.iso._21090.ADXP;

/**
 * Transforms the parts of an address.  Should only be used by the ADTransformer class.
 */
final class ADXPTransformer implements Transformer<org.iso._21090.ADXP, Adxp> {

    public static final ADXPTransformer INSTANCE = new ADXPTransformer();

    private ADXPTransformer() {
    }

    public ADXP toXml(Adxp input) {
        // Don't worry about null here - this is a package-protected class and the AD converter
        // has the responsibility to detect null.
        ADXP x = new ADXP();
        x.setCode(input.getCode());
        x.setValue(input.getValue());
        org.iso._21090.AddressPartType type = AddressPartTypeTransformer.INSTANCE.toXml(input.getType());
        x.setType(type);
        return x;
    }

    public Adxp toDto(ADXP input) {
        // Don't worry about null here - this is a package-protected class and the AD converter
        // has the responsibility to detect null.
        AddressPartType type = AddressPartTypeTransformer.INSTANCE.toDto(input.getType());
        Adxp d = Adxp.createAddressPart(type);
        d.setCode(input.getCode());
        d.setValue(input.getValue());
        return d;
    }
}
