package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.CodedEnum;

/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class IsoConverter {
    
    /**
     * converts a enum to a Cd.
     * @param ce enumerated class
     * @return Cd
     */
    public static Cd convertEnumCodeToIsoCd(CodedEnum ce) {
        Cd cd = new Cd();
        St st = new St();
        if (ce == null) {
            cd.setNullFlavor(NullFlavor.NI);
        } else {
            // set display name
            st.setValue(ce.getDisplayName().toString());
            cd.setDisplayName(st);
            // set code 
            cd.setCode(ce.getCode().toString());
        }
        return cd;
    }
    
    /**
     * 
     * @param id id
     * @return Ii ii
     */
    public static Ii convertIdToIsoIi(Long id) {
        Ii ii = new Ii();
        if (ii == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(id.toString());
            //@todo : set others attributes of II;
        }
        return ii;
    }
    
    /**
     * 
     * @param ii ii
     * @return long
     */
    public static Long convertIitoLong(Ii ii) {
        if (ii == null || ii.getNullFlavor() != null) {
            return null;
        }
        return Long.valueOf(ii.getExtension());
    }

}
