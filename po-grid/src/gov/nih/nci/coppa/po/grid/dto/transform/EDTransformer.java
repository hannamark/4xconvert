package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.TelUrl;

import org.iso._21090.ED;

/**
 * Transforms strings.
 * @author mshestopalov
 */
public final class EDTransformer implements Transformer<ED, Ed> {

    /**
     * Public singleton.
     */
    public static final EDTransformer INSTANCE = new EDTransformer();

    private EDTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public ED toXml(Ed input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ED x = new ED();
        String v = input.getValue();
        if (v != null) {
            x.setValue(v);
        } else {
            x.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        }
        x.setCharset(input.getCharset());
        if (input.getCompression() != null) {
            x.setCompression(org.iso._21090.Compression.valueOf(input.getCompression().name()));
        }
        x.setMediaType(input.getMediaType());
        x.setXml(input.getXml());
        x.setData(input.getData());
        x.setIntegrityCheck(input.getIntegrityCheck());
        if (input.getIntegrityCheckAlgorithm() != null) {
            x.setIntegrityCheckAlgorithm(
                    org.iso._21090.IntegrityCheckAlgorithm.fromValue(input.getIntegrityCheckAlgorithm().name()));
        }
        x.setReference(TELTransformer.INSTANCE.toXml(input.getReference()));
        x.setThumbnail(this.toXml(input.getThumbnail()));
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public Ed toDto(ED input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Ed d = new Ed();
        String v = input.getValue();
        if (v != null) {
            d.setValue(v);
        } else {
            d.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
        }

        d.setCharset(input.getCharset());
        if (input.getCompression() != null) {
            d.setCompression(gov.nih.nci.coppa.iso.Compression.valueOf(input.getCompression().name()));
        }
        d.setMediaType(input.getMediaType());
        if (input.getXml() != null) {
            d.setXml(input.getXml().toString());
        }
        d.setData(input.getData());

        d.setIntegrityCheck(input.getIntegrityCheck());
        if (input.getIntegrityCheckAlgorithm() != null) {
            d.setIntegrityCheckAlgorithm(
                    gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm.valueOf(input.getIntegrityCheckAlgorithm().value()));
        }
        TelUrl t = (TelUrl) TELTransformer.INSTANCE.toDto(input.getReference());
        d.setReference(t);
        d.setThumbnail(this.toDto(input.getThumbnail()));

        return d;
    }
}
