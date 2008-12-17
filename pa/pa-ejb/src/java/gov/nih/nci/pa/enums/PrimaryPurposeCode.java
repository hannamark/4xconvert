package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
 * Code to identify a type of study protocol based upon the intent of the study's activities. 
 *
 * @author Naveen Amiruddin
 * @since 07/22/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public enum PrimaryPurposeCode implements CodedEnum<String> {

    /** Treatment. */
    TREATMENT("Treatment"),
    /** Prevention. */
    PREVENTION("Prevention"),
    /** Diagnostic. */
    DIAGNOSTIC("Diagnostic"),
    /** Early Detection. */
    EARLY_DETECTION("Early Detection"),     
    /** Supportive Care. */
    SUPPORTIVE_CARE("Supportive Care"),
    /** Screening. */
    SCREENING("Screening"),
    /** Epidemiologic. */
    EPIDEMIOLOGIC("Epidemiologic"),
    /** Observational. */
    OBSERVATIONAL("Observational"),
    /** Outcome. */
    OUTCOME("Outcome"),
    /** Ancillary. */
    ANCILLARY("Ancillary"),
    /** Correlative. */
    CORRELATIVE("Correlative"),
    /** Health Services Research. */
    HEALTH_SERVICES_RESEARCH("Health Services Research"),
    /** Basic Science. */
    BASIC_SCIENCE("Basic Science"),
    /** Other. */
    OTHER("Other");

    private String code;
    /**
     *
     * @param code
     */
    private PrimaryPurposeCode(String code) {
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
    public static PrimaryPurposeCode getByCode(String code) {
        return getByClassAndCode(PrimaryPurposeCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        PrimaryPurposeCode[] l = PrimaryPurposeCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}



