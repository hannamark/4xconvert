package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;

import java.util.ArrayList;
import java.util.List;

import org.iso._21090.AD;
import org.iso._21090.ADXP;

/**
 * Transforms Addresses.
 */
public final class ADTransformer implements Transformer<AD, Ad> {

    /**
     * Public singleton.
     */
    public static final ADTransformer INSTANCE = new ADTransformer();

    private ADTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public AD toXml(Ad input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        AD x = new AD();
        copyToXml(input, x);
        return x;
    }

    private static void copyToXml(Ad source, AD target) throws DtoTransformException {
        target.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(source.getNullFlavor()));
        List<Adxp> sourcePart = source.getPart();
        if (sourcePart != null) {
            List<ADXP> targetPart = target.getPart();
            ADXPTransformer.INSTANCE.copyToXml(sourcePart, targetPart);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Ad toDto(AD input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Ad d = new Ad();
        copyToDto(input, d);
        return d;
    }

    private static void copyToDto(AD source, Ad target) throws DtoTransformException {
        target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
        List<ADXP> sourcePart = source.getPart();
        if (sourcePart != null) {
            List<Adxp> targetPart = target.getPart();
            if (targetPart == null) {
                targetPart = new ArrayList<Adxp>(sourcePart.size());
                target.setPart(targetPart);
            }
            ADXPTransformer.INSTANCE.copyToDto(sourcePart, targetPart);
        }
    }
}
