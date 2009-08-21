package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.AbstractTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;

import java.util.Set;

import org.iso._21090.DSETII;
import org.iso._21090.II;

/**
 * For conformance to legacy api it transforms II to DSet&lt;Ii&gt;. 
 */
public final class LegacyIITransformer extends AbstractTransformer<II, DSet<Ii>> implements
        Transformer<II, DSet<Ii>> {

    /**
     * Public singleton.
     */
    public static final LegacyIITransformer INSTANCE = new LegacyIITransformer();

    private LegacyIITransformer() {
    }

    private static final String BASE_ROOT = "2.16.840.1.113883.3.26.4";

    /**
     * {@inheritDoc}
     */
    public II[] createXmlArray(int size) throws DtoTransformException {
        return new II[size];
    }

    /**
     * {@inheritDoc}
     */
    public DSet<Ii> toDto(II input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        DSETII dsetii = new DSETII();
        dsetii.getItem().add(input);
        return DSETIITransformer.INSTANCE.toDto(dsetii);
    }

    /**
     * {@inheritDoc}
     */
    public II toXml(DSet<Ii> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Set<Ii> iis = input.getItem();
        for (Ii ii : iis) {
            // Since PO only assigns one ID, our identifier will be the only ISS with our root
            if (IdentifierReliability.ISS == ii.getReliability() && ii.getRoot().startsWith(BASE_ROOT)) {
                return IITransformer.INSTANCE.toXml(ii);
            }
        }
        return IITransformer.INSTANCE.toXml(null);
    }

}
