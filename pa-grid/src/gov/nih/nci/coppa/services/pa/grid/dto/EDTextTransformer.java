package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.EdText;

import org.iso._21090.ED;
import org.iso._21090.EDText;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class EDTextTransformer implements Transformer<EDText, EdText> {

    /**
     * Public singleton.
     */
    public static final EDTextTransformer INSTANCE = new EDTextTransformer();

    private EDTextTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public EDText toXml(EdText input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        EDText x = new EDText();
        ED ed = EDTransformer.INSTANCE.toXml(input);
        x.setCharset(ed.getCharset());
        x.setData(ed.getData());
        x.setMediaType(ed.getMediaType());
        x.setReference(ed.getReference());
        x.setXml(ed.getXml());
        x.setValue(ed.getValue());
        x.setNullFlavor(ed.getNullFlavor());
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public EdText toDto(EDText input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        EdText x = new EdText();
        Ed ed = EDTransformer.INSTANCE.toDto(input);
        x.setCharset(ed.getCharset());
        x.setData(ed.getData());
        x.setMediaType(ed.getMediaType());
        x.setReference(ed.getReference());
        x.setXml(ed.getXml());
        x.setValue(ed.getValue());
        x.setNullFlavor(ed.getNullFlavor());
        return x;
    }
}
