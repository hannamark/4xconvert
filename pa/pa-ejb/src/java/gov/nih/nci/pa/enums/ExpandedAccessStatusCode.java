package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
public enum ExpandedAccessStatusCode implements CodedEnum<String> {
    /** Available. */
    AVAILABLE("Available"),
    /** No longer available. */
    NO_LONGER_AVAILABLE("No longer available"),
    /** Temporarily not available. */
    TEMPORARILY_NOT_AVAILABLE("Temporarily not available"),    
    /** Approved for marketing. */
    APPROVED_FOR_MARKETING("Approved for marketing");
    
    private String code;
    /**
     * 
     * @param code
     */
    private ExpandedAccessStatusCode(String code) {
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
     * @return ExpandedAccessCode 
     */
    public static ExpandedAccessStatusCode getByCode(String code) {
        return getByClassAndCode(ExpandedAccessStatusCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        ExpandedAccessStatusCode[] l = ExpandedAccessStatusCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
