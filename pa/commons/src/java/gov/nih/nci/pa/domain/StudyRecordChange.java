package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.ForceDiscriminator;
import org.hibernate.validator.Length;

/**
 * @author Apratim K
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "study_note_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("RECORD")
@ForceDiscriminator
@Table(name = "STUDY_NOTES")
public class StudyRecordChange extends StudyNotes {
    
    private String changeType;

    /**
     * @return changeType
     */
    @Column(name = "CHANGE_TYPE")
    @Length(max = MAX_LENGTH)
    public String getChangeType() {
        return changeType;
    }

    /**
     * @param changeType changeType
     */
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    
   

}
