package gov.nih.nci.pa.enums;


import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * The state of a document in relation to a business process workflow.
 *
 * @author Naveen Amiruddin
 * @since 07/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public enum DocumentWorkflowStatusCode implements CodedEnum<String> {

    /** Submitted. */
    SUBMITTED("Submitted"),
    /** Accepted. */
    ACCEPTED("Accepted"),
    /** Rejected. */
    REJECTED("Rejected"),
    /** Abstracted. */
    ABSTRACTED("Abstracted"),
    /** Abstraction Verified. */
    ABSTRACTION_VERIFIED("Abstraction Verified"),
    /** Abstraction not verified. */
    ABSTRACTION_NOT_VERIFIED("Abstraction not verified");

    private String code;
    /**
     *
     * @param code
     */
    private DocumentWorkflowStatusCode(String code) {
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
     * @return DocumentWorkFlowStatusCode
     */
    public static DocumentWorkflowStatusCode getByCode(String code) {
        return getByClassAndCode(DocumentWorkflowStatusCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        DocumentWorkflowStatusCode[] l = DocumentWorkflowStatusCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }


}


