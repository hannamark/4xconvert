package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;


/**
 * Enumeration  for Recruitment Status codes.
 *
 * @author Hugh Reinhart
 * @since 08/26/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum RecruitmentStatusCode implements CodedEnum<String> {

    /** Not yet recruiting. */
    NOT_YET_RECRUITING("Not yet recruiting"),
    /** Recruiting. */
    RECRUITING("Recruiting"),
    /** Enrolling by invitation. */
    ENROLLING_BY_INVITATION("Enrolling by invitation"),
    /** Active , not recruiting. */
    ACTIVE_NOT_RECRUITING("Active, not recruiting"),
    /** Completed. */
    COMPLETED("Completed"),
    /** Suspended :  recruiting. */
    SUSPENDED_RECRUITING("Suspended"),
    /** Terminated :  recruiting. */
    TERMINATED_RECRUITING("Terminated"),
    /** Withdrawn. */
    WITHDRAWN("Withdrawn");
    
    private String code;
    /**
     *
     * @param code
     */
    private RecruitmentStatusCode(String code) {
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
     * @return RecruitmentStatusCode
     */
    public static RecruitmentStatusCode getByCode(String code) {
        return getByClassAndCode(RecruitmentStatusCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        RecruitmentStatusCode[] l = RecruitmentStatusCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
