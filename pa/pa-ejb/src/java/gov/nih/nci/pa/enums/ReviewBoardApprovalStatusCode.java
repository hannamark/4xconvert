package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Hugh Reinhart
 * @since 11/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ReviewBoardApprovalStatusCode implements CodedEnum<String> {
    /** Submitted, approved. */
    SUBMITTED_APPROVED("Submitted, approved"),
    /** Submitted, exempt. */
    SUBMITTED_EXEMPT("Submitted, exempt"),
    /** Submission not required. */
    SUBMISSION_NOT_REQUIRED("Submission not required");
    
    private String code;
    /**
     *
     * @param code
     */
    private ReviewBoardApprovalStatusCode(String code) {
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
     * @return ReviewBoardApprovalStatusCode
     */
    public static ReviewBoardApprovalStatusCode getByCode(String code) {
        return getByClassAndCode(ReviewBoardApprovalStatusCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        ReviewBoardApprovalStatusCode[] l = ReviewBoardApprovalStatusCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
