package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;

import org.iso._21090.II;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public final class IITransformer implements Transformer<II, Ii> {

    /**
     * Public singleton.
     */
    public static final IITransformer INSTANCE = new IITransformer();

    private IITransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public II toXml(Ii input) {
        if (input == null) {
            return null;
        }
        II d = new II();
        copyToXml(input, d);
        return d;
    }

    private static void copyToXml(Ii source, II target) {
        target.setDisplayable(source.getDisplayable());
        target.setExtension(source.getExtension());
        target.setIdentifierName(source.getIdentifierName());
        target.setRoot(source.getRoot());
        if (source.getReliability() != null) {
            target.setReliability(org.iso._21090.IdentifierReliability.valueOf(source.getReliability().name()));
        }
        if (source.getScope() != null) {
            target.setScope(org.iso._21090.IdentifierScope.valueOf(source.getScope().name()));
        }
        target.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(source.getNullFlavor()));
    }

    /**
     * {@inheritDoc}
     */
    public Ii toDto(II input) {
        if (input == null) {
            return null;
        }
        Ii d = new Ii();
        copyToDto(input, d);
        return d;
    }

    private static void copyToDto(II source, Ii target) {
        target.setDisplayable(source.isDisplayable());
        target.setExtension(source.getExtension());
        target.setIdentifierName(source.getIdentifierName());
        target.setRoot(source.getRoot());
        if (source.getReliability() != null) {
            target.setReliability(IdentifierReliability.valueOf(source.getReliability().name()));
        }
        if (source.getScope() != null) {
            target.setScope(IdentifierScope.valueOf(source.getScope().name()));
        }
        target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
    }
}
