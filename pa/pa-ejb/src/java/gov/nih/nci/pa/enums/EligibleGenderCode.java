package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
*
* @author Kalpana Guthikonda
* @since 11/10/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public enum EligibleGenderCode implements CodedEnum<String> {


    /** Male. */
    MALE("Male"),
    /** Female. */
    FEMALE("Female"),
    /** Both. */
    BOTH("Both");    

    private String code;
    /**
     * 
     * @param code
     */
    private EligibleGenderCode(String code) {
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
     * @return EligibleGenderCode 
     */
    public static EligibleGenderCode getByCode(String code) {
        return getByClassAndCode(EligibleGenderCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        EligibleGenderCode[] l = EligibleGenderCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
