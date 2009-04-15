package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;

import org.iso._21090.ENXP;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
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
