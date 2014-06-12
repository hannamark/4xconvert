package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.Alias;

import java.util.Comparator;

/**
 * Compares Alias based on alias value.
 * 
 * @author Rohit Gupta
 * 
 */
public class AliasComparator implements Comparator<Alias> {
    /**
     * {@inheritDoc}
     */
    public int compare(Alias o1, Alias o2) {
        String alias1 = o1.getValue().toLowerCase();
        String alias2 = o2.getValue().toLowerCase();

        return alias1.compareTo(alias2);
    }
}
