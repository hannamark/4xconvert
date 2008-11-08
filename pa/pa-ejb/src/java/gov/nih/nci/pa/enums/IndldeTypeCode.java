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
public enum IndldeTypeCode implements CodedEnum<String> {
    /** IND. */
    IND("IND"),
    /**  IDE. */
    IDE("IDE");    

    private String code;
    /**
     * 
     * @param code
     */
    private IndldeTypeCode(String code) {
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
     * @return IndldeTypeCode 
     */
    public static IndldeTypeCode getByCode(String code) {
        return getByClassAndCode(IndldeTypeCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        IndldeTypeCode[] l = IndldeTypeCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
