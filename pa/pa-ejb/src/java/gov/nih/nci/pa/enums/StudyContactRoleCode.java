package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * The responsibility of the investigator on a particular study..
 *
 * @author Naveen Amiruddin
 * @since 07/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public enum StudyContactRoleCode implements CodedEnum<String> {

    /** Study Principal Investigator. */
    STUDY_PRINCIPAL_INVESTIGATOR("Study Principal Investigator"),
    /** Study sub Investigator. */
    STUDY_SUB_INVESTIGATOR("Study Sub Investigator"),
    /** Coordinating Investigator. */
    COORDINATING_INVESTIGATOR("Coordinating Investigator"),
    /** Study Director. */
    STUDY_DIRECTOR("Study Director"),
    /** Study Chair. */
    STUDY_CHAIR("Study Chair"),
    /** Public Queries. */
    PUBLIC_QUERIES("Public Queries"),
    /** Scientific Queries. */
    SCIENTIFIC_QUERIES("Scientific Queries"),
    /** Scientific Leadership. */
    SCIENTIFIC_LEADERSHIP("Scientific Leadership"),
    /**Contact. */
    STUDY_PRIMARY_CONTACT("Study Primary Contact"),
    /**Central Contact. */
    CENTRAL_CONTACT("Central Contact"),
    /**Submitter. */
    SUBMITTER("Submitter"),
    /**Responsible Party - Study Principal Investigator. */
    RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR("Responsible Party - Study Principal Investigator");
    
    private String code;
    /**
     *
     * @param code
     */
    private StudyContactRoleCode(String code) {
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
     * @return StudyContactRoleCode
     */
    public static StudyContactRoleCode getByCode(String code) {
        return getByClassAndCode(StudyContactRoleCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        StudyContactRoleCode[] l = StudyContactRoleCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
