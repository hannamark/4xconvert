package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;

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
    public ENXP toXml(Enxp input) {
        ENXP d = new ENXP();
        if (input != null && input.getType() != null) {
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
        if (input != null && input.getType() == null) {
            throw new DtoTransformException("ENXP.type is required");
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
