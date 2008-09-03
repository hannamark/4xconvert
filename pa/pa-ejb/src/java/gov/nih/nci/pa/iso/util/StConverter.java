package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;

/**
 * utility method for converting for Cd.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StConverter {
    
    /**
     * convert string to st.
     * @param str String
     * @return st
     */
    public static St convertToSt(String str) {
        St st = new St();
        if (str == null) {
            st.setNullFlavor(NullFlavor.NI);
            return st;
        }
        st.setValue(str);
        return st;
    }
    
    /**
     * 
     * @param st String
     * @return String 
     */
    public static String convertToString(St st) {
        String str = null;
        if (st == null) {
            return str;
        }
        if (st.getNullFlavor() != null) {
            return str;
        }
        if (st.getValue() == null) {
            // @todo : throw pa exception
            st.setNullFlavor(NullFlavor.NI);
        }
        return st.getValue();
         
    }

}
