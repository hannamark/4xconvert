package gov.nih.nci.coppa.po.grid.dto.transform;


import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iso._21090.CD;
import org.iso._21090.DSETCD;
import org.iso._21090.NullFlavor;

/**
 * Transforms sets of CD.
 */
public final class DSETCDTransformer implements Transformer <DSETCD, DSet<Cd>> {
     /**
     * Public transformer instance.
     */
    public static final DSETCDTransformer INSTANCE = new DSETCDTransformer();

    private DSETCDTransformer() {
    }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    public DSETCD toXml(DSet<Cd> input) {
        DSETCD x = new DSETCD();
        if (input != null && input.getItem() != null) {
            Set<Cd> sItem = input.getItem();
            List<CD> tItem = x.getItem();
            for (Cd cd : sItem) {
              CD cur = CDTransformer.INSTANCE.toXml(cd);
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
    /**
     * {@inheritDoc}
     */
    public DSet<Cd> toDto(DSETCD input) {
        if (input == null || input.getNullFlavor() != null) {
             return null;
         }
         DSet<Cd> x = new DSet<Cd>();
         x.setItem(new HashSet<Cd>());
         List<CD> sItem = input.getItem();
         Set<Cd> tItem = x.getItem();
         for (CD cd : sItem) {
             tItem.add(CDTransformer.INSTANCE.toDto(cd));
         }
         return x;
    }



}
