package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
* @author Naveen Amiruddin
* @since 07/22/2007
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/

public enum StudyParticipationContactRoleCode implements CodedEnum<String> {
    
    /** Sdy primary contact 3rd tab. */
    STUDY_PRIMARY_CONTACT("Study Primary Contact"),
    /** Study Principal Investigator. */
    STUDY_PRINCIPAL_INVESTIGATOR("Study Principal Investigator"),
    /** Study sub Investigator. */
    STUDY_SUB_INVESTIGATOR("Study Sub Investigator"),
    /** Coordinating Investigator. */
    COORDINATING_INVESTIGATOR("Coordinating Investigator"),
    /** Responsible party contact . */
    STUDY_RESPONSIBLE_PARTY_CONTACT("Study Responsible Party Contact"),
    /**Submitter. */
    SUBMITTER("Submitter"),
    /**Responsible party - Sponsor contact. */
    RESPONSIBLE_PARTY_SPONSOR_CONTACT("Responsible party - Sponsor contact");    
    private String code;
    /**
     *
     * @param code
     */
    private StudyParticipationContactRoleCode(String code) {
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
     * @return StudyParticipationContactRoleCode
     */
    public static StudyParticipationContactRoleCode getByCode(String code) {
        return getByClassAndCode(StudyParticipationContactRoleCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        StudyParticipationContactRoleCode[] l = StudyParticipationContactRoleCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
    
