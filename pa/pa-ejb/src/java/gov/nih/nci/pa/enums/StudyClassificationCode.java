package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
*
* @author Kalpana Guthikonda
* @since 10/21/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public enum StudyClassificationCode implements CodedEnum<String> {

    /** Safety. */
    SAFETY("Safety"),
    /** Efficacy. */
    EFFICACY("Efficacy"),
    /** Safety/Efficacy. */
    SAFETY_OR_EFFICACY("Safety/Efficacy"),
    /** Bio-availability. */
    BIO_AVAILABILITY("Bio-availability"),
    /** Bio-equivalence. */
    BIO_EQUIVALENCE("Bio-equivalence"),
    /** Pharmacokinetics. */
    PHARMACOKINETICS("Pharmacokinetics"),
    /** Pharmacodynamics. */
    PHARMACODYNAMICS("Pharmacodynamics"),
    /** Pharmacokinetics/dynamics. */
    PHARMACOKINETICS_OR_DYNAMICS("Pharmacokinetics/dynamics"),
    /*** Not Applicable . */
    NA("NA");    

    private String code;
    /**
     * 
     * @param code
     */
    private StudyClassificationCode(String code) {
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
     * @return StudyClassificationCode 
     */
    public static StudyClassificationCode getByCode(String code) {
        return getByClassAndCode(StudyClassificationCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        StudyClassificationCode[] l = StudyClassificationCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
