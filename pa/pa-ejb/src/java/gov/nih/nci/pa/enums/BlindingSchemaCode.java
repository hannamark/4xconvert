package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 *
 * @author Kalpana Guthikonda
 * @since 10/18/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum BlindingSchemaCode implements CodedEnum<String> {

    /** Open. */
    OPEN("Open"),
    /** Single Blind. */
    SINGLE_BLIND("Single Blind"),
    /** Double Blind. */
    DOUBLE_BLIND("Double Blind");    

    private String code;
    /**
     * 
     * @param code
     */
    private BlindingSchemaCode(String code) {
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
     * @return BlindingSchemaCode 
     */
    public static BlindingSchemaCode getByCode(String code) {
        return getByClassAndCode(BlindingSchemaCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        BlindingSchemaCode[] l = BlindingSchemaCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
