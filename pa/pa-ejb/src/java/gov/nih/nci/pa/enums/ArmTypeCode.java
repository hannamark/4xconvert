/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ArmTypeCode implements CodedEnum<String> {
    
     /** Experimental. */
     EXPERIMENTAL("Experimental"),
     /** Active Comparator. */
     ACTIVE_COMPARATOR("Active Comparator"),
     /** Placebo Comparator. */
     PLACEBO_COMPARATOR("Placebo Comparator"),
     /** Sham Comparator. */
     SHAM_COMPARATOR("Sham Comparator"),
     /** No intervention. */
     NO_INTERVENTION("No intervention"),
     /** Other. */
     OTHER("Other");
     
    private String code;
    /**
     * 
     * @param code
     */
    private ArmTypeCode(String code) {
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
     * @return arm type 
     */
    public static ArmTypeCode getByCode(String code) {
        return getByClassAndCode(ArmTypeCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        ArmTypeCode[] l = ArmTypeCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}    
 
