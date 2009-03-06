package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;

import org.iso._21090.II;

public class IITransformer implements Transformer<II, Ii> {

    public static final IITransformer INSTANCE = new IITransformer();

    private IITransformer() {}

    public II toXml(Ii input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        II d = new II();
        copyToXml(input, d);
        return d;
    }

    private static void copyToXml(Ii source, II target) throws DtoTransformException {
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

    public Ii toDto(II input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Ii d = new Ii();
        copyToDto(input, d);
        return d;
    }

    private static void copyToDto(II source, Ii target) throws DtoTransformException {
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
