package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.NullFlavor;


/**
 * utility method for converting for Bl.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class BlConverter {

    /**
     * 
     * @param bool boolean
     * @return Bl 
     */
    public static Bl convertToBl(Boolean bool) {
        Bl bl = new Bl();
        if (bool == null) {
            bl.setNullFlavor(NullFlavor.NI);
            return bl;
        }
        bl.setValue(bool);
        return bl;
    }
    /**
     * 
     * @param bl bl
     * @return Boolean Boolean
     */
    public static Boolean covertToBoolean(Bl bl) {
        if (bl == null) {
            return null;
        }
        if (bl.getValue() == null) {
            bl.setNullFlavor(NullFlavor.NI);
            // @todo : throw proper exception 
        }
        if (bl.getNullFlavor() != null && bl.getNullFlavor().equals(NullFlavor.NI)) {
            return null;
        }
        return bl.getValue();
    }
    
    /**
     * 
     * @param bl to be converted
     * @return String as return value
     */
    public static String convertToString(Bl bl) {
        if (bl == null) {
            return null;
        }
        if (bl.getValue() == null) {
            bl.setNullFlavor(NullFlavor.NI);
            // @todo : throw proper exception 
        }
        return bl.getValue().toString();
    }
    
//    /**
//     * 
//     * @param bool boolean
//     * @return Bl 
//     */
//    public static String translateCodeForDisplay(Boolean bool) {
//        if (bool) {
//            return "
//        } else {
//            
//        }
//        return "";
//    }
  
}
