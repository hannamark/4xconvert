package gov.nih.nci.coppa.po.grid.dto.transform;


import gov.nih.nci.coppa.iso.EntityNamePartType;

import gov.nih.nci.coppa.iso.Enxp;
import org.iso._21090.ENXP;

public class ENXPTransformer implements Transformer<ENXP, Enxp> {

    public static final ENXPTransformer INSTANCE = new ENXPTransformer();

    private ENXPTransformer() {}
	 
    public ENXP toXml(Enxp input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ENXP d = new ENXP();
        copyToXml(input, d);
        return d;
    }

    public void copyToXml(Enxp source, ENXP target) throws DtoTransformException {
        if (source.getType() != null) {
            target.setType(org.iso._21090.EntityNamePartType.valueOf(source.getType().name()));
        }
        target.setValue(source.getValue());
        
        // all of these are currently ignored by PO.
        target.setCode(source.getCode());
        target.setCodeSystem(source.getCodeSystem());
        target.setCodeSystemVersion(source.getCodeSystemVersion());
    }

    public Enxp toDto(ENXP input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        if (input.getType() == null) {
            throw new IllegalArgumentException("ENXP.type is required");
        }
        Enxp d = new Enxp(EntityNamePartType.valueOf(input.getType().name()));
        copyToDto(input, d);
        return d;
    }

    public void copyToDto(ENXP source, Enxp target) throws DtoTransformException {
        target.setValue(source.getValue());
        
        // all of these are currently ignored by PO.
        target.setCode(source.getCode());
        target.setCodeSystem(source.getCodeSystem());
        target.setCodeSystemVersion(source.getCodeSystemVersion());
    }
}
