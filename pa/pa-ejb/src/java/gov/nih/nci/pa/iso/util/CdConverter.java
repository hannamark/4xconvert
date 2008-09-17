package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.StudyStatusCode;


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
    
    /**
     * converts a String to a Cd.
     * @param code code
     * @return Cd
     */
    public static Cd convertStringToCd(String code) {
        Cd cd = new Cd();
        St st = new St();
        if (code == null) {
            cd.setNullFlavor(NullFlavor.NI);
        } else {
            // set display name
            st.setValue(code);
            cd.setDisplayName(st);
            // set code 
            cd.setCode(code);
        }
        return cd;
    }
    
    /**
     * converts a Cd to String.
     * @param cd code
     * @return String
     */
    public static String convertCdToString(Cd cd) {
        if (cd == null) {
            return null;
        } else if (cd.getNullFlavor().equals(NullFlavor.NI)) {
            return null;
        } else {
            // set display name
            return cd.getCode();
        }
    }    
    /**
     * converts a T to an enum.
     * @param cd iso Cd class
     * @return StudyStatusCode
     */
    public static StudyStatusCode convertToStudyStatusCode(Cd cd) {
        StudyStatusCode result = null;
        if (cd != null) {
            result = StudyStatusCode.getByCode(cd.getCode());
        }
        return result;
    }

    /**
     * converts a Cd to an enum.
     * @param cd iso Cd class
     * @return ActualAnticipatedTypeCode
     */
    public static ActualAnticipatedTypeCode convertToActualAnticipatedTypeCode(Cd cd) {
        ActualAnticipatedTypeCode result = null;
        if (cd != null) {
            result = ActualAnticipatedTypeCode.getByCode(cd.getCode());
        }
        return result;
    }
}
