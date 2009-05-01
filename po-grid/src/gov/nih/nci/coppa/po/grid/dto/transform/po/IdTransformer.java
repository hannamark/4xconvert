package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.NullFlavorTransformer;

/**
 * Transforms the Id element type (is-a ISO:II).
 * @author smatyas
 *
 */
public final class IdTransformer implements Transformer<Id, Ii> {

    /**
     * Public singleton.
     */
    public static final IdTransformer INSTANCE = new IdTransformer();

    private IdTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Id toXml(Ii input) {
        if (input == null) {
            return null;
        }
        Id d = new Id();
        copyToXml(input, d);
        return d;
    }

    private static void copyToXml(Ii source, Id target) {
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
    public Ii toDto(Id input) {
        if (input == null) {
            return null;
        }
        Ii d = new Ii();
        copyToDto(input, d);
        return d;
    }

    private static void copyToDto(Id source, Ii target) {
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
