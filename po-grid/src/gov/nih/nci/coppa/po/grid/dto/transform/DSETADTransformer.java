package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.DSet;

import java.util.HashSet;
import java.util.Set;

import org.iso._21090.AD;
import org.iso._21090.DSETAD;

/**
 * Transforms sets of addresses.
 */
public final class DSETADTransformer implements Transformer<DSETAD, DSet<Ad>> {

    /**
     * Public transformer instance.
     */
    public static final DSETADTransformer INSTANCE = new DSETADTransformer();

    private DSETADTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public DSETAD toXml(DSet<Ad> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        DSETAD x = new DSETAD();
        if (input.getItem() != null) {
            for (Ad ad : input.getItem()) {
                x.getItem().add(ADTransformer.INSTANCE.toXml(ad));
            }
        }
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public DSet<Ad> toDto(DSETAD input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        DSet<Ad> x = new DSet<Ad>();
        Set<Ad> items = x.getItem();
        if (items == null) {
            items = new HashSet<Ad>();
            x.setItem(items);
        }
        for (AD ad : input.getItem()) {
            items.add(ADTransformer.INSTANCE.toDto(ad));
        }
        return x;
    }

}
