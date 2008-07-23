package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Comparator against which the study treatment is evaluated..
 *
 * @author Naveen Amiruddin
 * @since 07/09/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ControlTypeCode implements CodedEnum<String> {

    /** Placebo. */
    PLACEBO("Placebo"),
    /**Active. */
    ACTIVE("Active"),
    /** Historical. */
    HISTORICAL("Historical"),
    /** Uncontrolled. */
    UNCONTROLLED("Uncontrolled"),
    /** Dose Comparison. */
    DOSE_COMPARISON("Dose Comparison");

    
    private String code;
    /**
     * 
     * @param code
     */
    private ControlTypeCode(String code) {
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
    public static ControlTypeCode getByCode(String code) {
        return getByClassAndCode(ControlTypeCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        ControlTypeCode[] l = ControlTypeCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getDisplayName();
        }
        return a;
    }
    

}

