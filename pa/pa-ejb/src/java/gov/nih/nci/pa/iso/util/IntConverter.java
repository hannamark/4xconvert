package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.NullFlavor;


/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class IntConverter {
    
    
    /**
     * convert string to Int.
     * @param str String
     * @return st
     */
    public static Int convertToInt(String str) {
        Int in = new Int();
        if (str == null) {
            in.setNullFlavor(NullFlavor.NI);
            return in;
        }
        in.setValue(Integer.valueOf(str));
        return in;
    }    
    
    /**
     * convert Integer to Int.
     * @param data Integer
     * @return int
     */
    public static Int convertToInt(Integer data) {
        Int in = new Int();
        if (data == null) {
            in.setNullFlavor(NullFlavor.NI);
            return in;
        }
        in.setValue(data);
        return in;
    }    
    
    /**
     * convert Int to Integerr.
     * @param data Integer
     * @return integer
     */
    public static Integer convertToInteger(Int data) {
        if (data == null) {
            return null;
        }
        if (data.getNullFlavor() != null) {
            return null;
        }
        return data.getValue();
    }    
    

}
