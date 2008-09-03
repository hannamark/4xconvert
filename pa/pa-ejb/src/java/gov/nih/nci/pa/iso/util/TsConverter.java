package gov.nih.nci.pa.iso.util;

import java.sql.Timestamp;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Ts;

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
    

}
