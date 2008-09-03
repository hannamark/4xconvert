package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;

/**
 * utility method for converting Ii and Id.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class IiConverter {
    
    /**
     * 
     * @param id id
     * @return Ii ii
     */
    public static Ii convertToIi(Long id) {
        Ii ii = new Ii();
        if (id == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(id.toString());
            //@todo : set others attributes of II;
        }
        return ii;
    }
    
    /**
     * 
     * @param str string
     * @return Ii
     */
    public static Ii convertToIi(String str) {
        Ii ii = new Ii();
        if (str == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(str);
        }
        return ii;
        
    }
    
    /**
     * 
     * @param ii ii
     * @return long
     */
    public static Long convertToLong(Ii ii) {
        if (ii == null || ii.getNullFlavor() != null) {
            return null;
        }
        if (ii.getExtension() == null) {
            // @todo : throw exception
            ii.setNullFlavor(NullFlavor.NI);
        }
        return Long.valueOf(ii.getExtension());
    }
    
    /**
     * 
     * @param ii ii
     * @return String str
     */
    public static String convertToString(Ii ii) {
        if (ii == null || ii.getNullFlavor() != null) {
            return null;
        }
        if (ii.getExtension() == null) {
         // @todo : throw proper exception 
            ii.setNullFlavor(NullFlavor.NI);
        }
        return ii.getExtension();
    }
    

}
