package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.CodedEnum;


/**
 * utility method for converting for Cd.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class CdConverter {

    /**
     * converts a enum to a Cd.
     * @param ce enumerated class
     * @return Cd
     */
    public static Cd convertToCd(CodedEnum ce) {
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

}
