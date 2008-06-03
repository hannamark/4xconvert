package gov.nih.nci.pa.util;

/**
 * Protocol DAO for accessing DAO.
 *
 * @author Naveen Amiruddin
 * @since 05/30/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public class PAUtil {
    /**
     * utility method to determine if the object is not null. 
     * it returns true, if the object is not null , and does not contain any white space
     * @param obj obj
     * @return boolean 
     */
    public static boolean isNotNullOrNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            String s = obj.toString();
            if (s != null && s.trim().length() > 0) {
                return true;
            } 
            return false;
        } else if (obj != null) {
            return true;
        }
        return false;
    }
    
    

}
