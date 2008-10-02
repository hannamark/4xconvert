/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since 09/29/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_PARTICIPATION_CONTACT_TELECOM_ADDRESS")
public class StudyParticipationContactTelecomAddress extends AbstractEntity {
    private StudyParticipationContact studyParticipationContact;
    private String telecomAddress;
    /**
     * @return the studyParticipationContact
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PARTICIPATION_CONTACT_ID")
    @NotNull
    public StudyParticipationContact getStudyParticipationContact() {
        return studyParticipationContact;
    }
    /**
     * @param studyParticipationContact the studyParticipationContact to set
     */
    public void setStudyParticipationContact(
            StudyParticipationContact studyParticipationContact) {
        this.studyParticipationContact = studyParticipationContact;
    }
    /**
     * @return the telecomAddress
     */
    @Column(name = "TELECOM_ADDRESS")
    public String getTelecomAddress() {
        return telecomAddress;
    }
    /**
     * @param telecomAddress the telecomAddress to set
     */
    public void setTelecomAddress(String telecomAddress) {
        this.telecomAddress = telecomAddress;
    }
}
