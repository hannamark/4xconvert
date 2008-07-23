package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
 * The specific responsibility assumed by an organization for a given study.
 *
 * @author Naveen Amiruddin
 * @since 07/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public enum ResponsibilityCode implements CodedEnum<String> {

    /** Protocol Management. */
    PROTOCOL_MANAGEMENT("Protocol Management"),
    /** Registration Management. */
    REGISTRATION_MANAGEMENT("Registration Management"),
    /** Statistical Management. */
    STATISTICAL_MANAGEMENT("Statistical Management"),
    /** Data Management. */
    DATA_MANAGEMENT("Data Management");

    private String code;
    /**
     *
     * @param code
     */
    private ResponsibilityCode(String code) {
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
     * @return ResponsibilityCode
     */
    public static ResponsibilityCode getByCode(String code) {
        return getByClassAndCode(ResponsibilityCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        ResponsibilityCode[] l = ResponsibilityCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getDisplayName();
        }
        return a;
    }
}



