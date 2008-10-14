package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Study Recruitment Status codes.
 *
 * @author Hugh Reinhart
 * @since 08/26/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum StudyRecruitmentStatusCode implements CodedEnum<String> {

    /** Not yet recruiting. */
    NOT_YET_RECRUITING("Not yet Recruiting"),
    /** Recruiting. */
    RECRUITING_ACTIVE("Recruiting; Active"),
    /** Not recruiting. */
    NOT_RECRUITING("Not Recruiting"),
    /** Suspended. */
    SUSPENDED("Suspended"),
    /** Withdrawn. */
    WITHDRAWN("Withdrawn"),
    /** Completed. */
    COMPLETED("Completed"),
    /** Terminated :  recruiting. */
    TERMINATED("Terminated");
    
    private String code;
    /**
     *
     * @param code
     */
    private StudyRecruitmentStatusCode(String code) {
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
     * @return StudyRecruitmentStatusCode
     */
    public static StudyRecruitmentStatusCode getByCode(String code) {
        return getByClassAndCode(StudyRecruitmentStatusCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        StudyRecruitmentStatusCode[] l = StudyRecruitmentStatusCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
