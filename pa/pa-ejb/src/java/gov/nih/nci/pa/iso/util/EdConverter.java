package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ed;
import gov.nih.nci.coppa.iso.NullFlavor;

/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class EdConverter {
    
    /**
     * convert byte to Ed.
     * @param data byte array
     * @return ed
     */
    public static Ed convertToEd(byte[] data) {
        Ed ed = new Ed();
        if (data == null) {
            ed.setNullFlavor(NullFlavor.NI);
        } else if (data.length == 0) {
            ed.setNullFlavor(NullFlavor.NI);
        } else {
            ed.setData(data);
        }
        return ed;
    }

    /**
     * convert Ed to byte.
     * @param ed Ed
     * @return byte
     */
    public static byte[] convertToByte(Ed ed) {
        if (ed == null) {
            return null;
        } else if (ed.getNullFlavor() != null && ed.getNullFlavor().equals(NullFlavor.NI)) {
            return null;
        } else {
            return ed.getData();
        }
    }
}
