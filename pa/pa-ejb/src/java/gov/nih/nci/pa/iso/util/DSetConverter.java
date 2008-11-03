package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * 
 * @author NAmiruddin, HArsha
 * 
 */
@SuppressWarnings("PMD")
public class DSetConverter { 
    /**
     * @param dsetList list of DSets
     * @param type denoting email, telephone, fax, url, etc.,
     * @param list of Tel objects
     * @return dSet collection
     */
    public static DSet<Tel> convertListToDSet(List<String> list, String type, DSet<Tel> dsetList) {
        Set<Tel> telSet = new HashSet<Tel>();
        DSet<Tel> dSet = null;
        if (dsetList == null) {
            dSet = new DSet<Tel>();
        } else {
            dSet = dsetList;
            if (dsetList.getItem() != null && dsetList.getItem().size() > 0) {
                Iterator val = dsetList.getItem().iterator();
                while (val.hasNext()) {
                    telSet.add((Tel) val.next());
                }            
            }
        }
        if (list == null || list.isEmpty()) {
            return dSet;
        }
        if (type.equals("PHONE")) {
            TelPhone t = null;
            for (String phone : list) {
                t = new TelPhone();
                t.setValue(URI.create("tel:" + phone));
                telSet.add(t);
            }            
        } else if (type.equals("EMAIL")) {
            TelEmail t = null;
            for (String email : list) {
                t = new TelEmail();
                t.setValue(URI.create("mailto:" + email));
                telSet.add(t);
            }         
        }
        dSet.setItem(telSet); 
        return dSet;
    }

    /**
     * @param type to be returned
     * @param dSet set of iso phones
     * @return phones
     */
    public static List<String> convertDSetToList(DSet<Tel> dSet, String type) {
        List<String> retList = new ArrayList<String>();
        if (dSet == null || dSet.getItem() == null) {
            return retList;
        }
        if (type.equals("PHONE")) {
            for (Tel t : dSet.getItem()) {
                if (t.getNullFlavor() != null) {
                    continue;
                }
                if (t instanceof TelPhone) {
                    retList.add((t.getValue().getSchemeSpecificPart()));
                }
            }
        }
        if (type.equals("EMAIL")) {
            for (Tel t : dSet.getItem()) {
                if (t.getNullFlavor() != null) {
                    continue;
                }
                if (t instanceof TelEmail) {
                    retList.add((t.getValue().getSchemeSpecificPart()));
                }
            }
        }        
        return retList;
    }
    
    /**
     * 
     * @param cds cds
     * @return dSet
     */
    public static DSet<Cd> convertCdListToDSet(List<Cd> cds) {
        DSet<Cd> dSet = null;
        if (cds == null || cds.isEmpty()) {
            return dSet;
        }
        dSet = new DSet();
        Set<Cd> cdSet = new HashSet<Cd>();
        cdSet.addAll(cds);
        dSet.setItem(cdSet);
        return dSet;
    }
    
    /**
     * 
     * @param dset dset
     * @return cds
     */
    public static List<Cd> convertDsetToCdList(DSet<Cd> dset) {
        List<Cd> cds = null;
        if (dset == null || dset.getItem() == null || dset.getItem().isEmpty()) {
            return cds;
        }
        cds = new ArrayList<Cd>();
        Set<Cd> set = dset.getItem();
        cds.addAll(set);
        return cds;
    }


}
