package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;

import org.iso._21090.ENXP;

/**
 * Transforms en parts.
 */
final class ENXPTransformer implements Transformer<ENXP, Enxp> {

    /**
     * Public singleton.
     */
    public static final ENXPTransformer INSTANCE = new ENXPTransformer();

    private ENXPTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public ENXP toXml(Enxp input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ENXP d = new ENXP();
        if (input.getType() != null) {
            d.setType(org.iso._21090.EntityNamePartType.valueOf(input.getType().name()));
        }
        d.setValue(input.getValue());

        // all of these are currently ignored by PO.
        d.setCode(input.getCode());
        d.setCodeSystem(input.getCodeSystem());
        d.setCodeSystemVersion(input.getCodeSystemVersion());
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public Enxp toDto(ENXP input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        if (input.getType() == null) {
            throw new IllegalArgumentException("ENXP.type is required");
        }
        Enxp d = new Enxp(EntityNamePartType.valueOf(input.getType().name()));
        d.setValue(input.getValue());

        // all of these are currently ignored by PO.
        d.setCode(input.getCode());
        d.setCodeSystem(input.getCodeSystem());
        d.setCodeSystemVersion(input.getCodeSystemVersion());
        return d;
    }
}
