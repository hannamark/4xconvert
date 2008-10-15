package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.TelPhone;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author NAmiruddin
 *
 */
public class DSetConverter {

    /**
     * 
     * @param phones list of emails 
     * @return dSet
     */
    public static DSet<TelPhone> convertPhoneListToDSet(List<String> phones) {
        DSet<TelPhone> dSet = new DSet<TelPhone>();
        Set<TelPhone> set = new HashSet<TelPhone>();
        dSet.setItem(set);
        if (phones == null || phones.isEmpty()) {
            return dSet;
        }
        TelPhone t = null;

        for (String phone : phones) {
            t = new TelPhone();
            t.setValue(URI.create("tel:" + phone));
            set.add(t);
        }
            
        return dSet;
    }
    
    /**
     * 
     * @param dSet set of iso phones
     * @return phones
     */
    public static List<String> convertDsetPhoneToList(DSet<TelPhone> dSet) {
        List<String> phones = new ArrayList<String>();
        if (dSet == null ||  dSet.getItem() == null) {
            return phones;
        }
        for (TelPhone t : dSet.getItem()) {
            if (t.getNullFlavor() != null) {
                continue;
            }
            phones.add((t.getValue().getSchemeSpecificPart()));
        }
        return phones;
    }
}
