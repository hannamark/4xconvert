package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@Entity
@Table(name =  "STRATUM_GROUP")
public class StratumGroup extends AbstractEntity {
    
    private StudyProtocol studyProtocol;
    private String description;
    private String groupNumberText;
    
    /**
     * 
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    
    /**
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    
    /**
     * 
     * @return description
     */
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 
     * @return groupNumberText
     */
    @Column(name = "GROUP_NUMBER_TEXT")
    public String getGroupNumberText() {
        return groupNumberText;
    }
    
    /**
     * @param groupNumberText groupNumberText
     */
    public void setGroupNumberText(String groupNumberText) {
        this.groupNumberText = groupNumberText;
    }

}
