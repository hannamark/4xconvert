package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyContactRoleCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * . 
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_CONTACT_ROLES")
public class StudyContactRole extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private StudyContact studyContact;
    private StudyContactRoleCode studyContactRoleCode;

    /**
     * 
     * @return studyContact
     */
    public StudyContact getStudyContact() {
        return studyContact;
    }
    /**
     * 
     * @param studyContact studyContact
     */
    public void setStudyContact(StudyContact studyContact) {
        this.studyContact = studyContact;
    }
    /**
     * 
     * @return studyContactRoleCode
     */
    public StudyContactRoleCode getStudyContactRoleCode() {
        return studyContactRoleCode;
    }
    /**
     * 
     * @param studyContactRoleCode studyContactRoleCode
     */
    public void setStudyContactRoleCode(StudyContactRoleCode studyContactRoleCode) {
        this.studyContactRoleCode = studyContactRoleCode;
    }
}
