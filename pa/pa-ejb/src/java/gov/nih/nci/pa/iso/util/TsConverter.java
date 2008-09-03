package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Ts;

import java.sql.Timestamp;

/**
 * utility method for converting Ts and Timestamp.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class TsConverter {
    
    /**
     * 
     * @param timeStamp timestamp
     * @return Ts 
     */
    public static Ts convertToTs(Timestamp timeStamp) {
        Ts ts = new Ts();
        if (timeStamp == null) {
            ts.setNullFlavor(NullFlavor.NI);
            return ts;
        }
        ts.setValue(timeStamp.toString());
        return ts;
    }
    
    /**
     * @param tsIso iso Ts
     * @return java Timestamp
     */
    public static Timestamp convertToTimestamp(Ts tsIso) {
        if (tsIso == null) {
            return null;
        }
        return Timestamp.valueOf(tsIso.getValue());
    }


}
