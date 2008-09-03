package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyContactRoleCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The responsibility of the investigator on a particular study.. 
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2008
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
    @ManyToOne
    @JoinColumn(name = "STUDY_CONTACT_ID", nullable = false)
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
     * @return responsibilityCode
     */
    @Column(name = "ROLE_CODE")
    @Enumerated(EnumType.STRING)
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
