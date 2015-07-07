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
@DiscriminatorValue("DATA")
@ForceDiscriminator
@Table(name = "STUDY_NOTES")
public class StudyDataDiscrepancy extends StudyNotes {
    
    private String discrepancyType;

    
    /**
     * @return discrepancyType
     */
    @Column(name = "discrepancy_type")
    @Length(max = MAX_LENGTH)
    public String getDiscrepancyType() {
        return discrepancyType;
    }

    /**
     * @param discrepancyType discrepancyType
     */
    public void setDiscrepancyType(String discrepancyType) {
        this.discrepancyType = discrepancyType;
    }

}
