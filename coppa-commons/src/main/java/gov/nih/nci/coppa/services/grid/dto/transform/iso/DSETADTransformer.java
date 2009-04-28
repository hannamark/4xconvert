package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.DSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iso._21090.AD;
import org.iso._21090.DSETAD;
import org.iso._21090.NullFlavor;

/**
 * Transforms sets of addresses.
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
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
    public DSETAD toXml(DSet<Ad> input) {
        DSETAD x = new DSETAD();
        if (input != null && input.getItem() != null) {
            Set<Ad> sItem = input.getItem();
            List<AD> tItem = x.getItem();
            for (Ad ad : sItem) {
                AD cur = ADTransformer.INSTANCE.toXml(ad);
                 // XSD rule: all elements of set must be non-null
                  if (!(cur == null || cur.getNullFlavor() != null)) {
                      tItem.add(cur);
                  }
            }
        }
        if (x.getItem().isEmpty()) {
            x.setNullFlavor(NullFlavor.NI);
        }
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public DSet<Ad> toDto(DSETAD input) {
        if (input == null || input.getNullFlavor() != null) {
            return null;
        }
        DSet<Ad> x = new DSet<Ad>();
        x.setItem(new HashSet<Ad>());
        List<AD> sItem = input.getItem();
        Set<Ad> tItem = x.getItem();
        for (AD ad : sItem) {
            tItem.add(ADTransformer.INSTANCE.toDto(ad));
        }
        return x;
    }

}
