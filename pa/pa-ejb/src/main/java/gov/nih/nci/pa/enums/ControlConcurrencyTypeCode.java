package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * A code specifying the temporal relationships of the control to the study intervention.
 *
 * @author Naveen Amiruddin
 * @since 07/09/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ControlConcurrencyTypeCode implements CodedEnum<String> {

    /** Concurrent. */
    CONCURRENT("Concurrent"),
    /** Historical. */
    HISTORICAL("Historical"),
    /** Pre / post (Patient Owned Control). */
    PRE_POST_PATIENT_OWNED_CONTROL("Pre / post (Patient Owned Control)");
    
    private String code;
    /**
     * 
     * @param code
     */
    private ControlConcurrencyTypeCode(String code) {
        this.code = code;
        register(this);
    }
    /**
     * @return code code
     */
    public String getCode() {
        return code;
    }

    /**
     *@return String DisplayName 
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * 
     * @return String name
     */
    public String getName() {
        return name();
    }

    /**
     * 
     * @param code code
     * @return biospecimenRetentionCode 
     */
    public static ControlConcurrencyTypeCode getByCode(String code) {
        return getByClassAndCode(ControlConcurrencyTypeCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        ControlConcurrencyTypeCode[] l = ControlConcurrencyTypeCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getDisplayName();
        }
        return a;
    }
    

}

