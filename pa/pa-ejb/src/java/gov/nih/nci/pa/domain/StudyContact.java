package gov.nih.nci.pa.domain;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;


/**
 * This class is an abstract concept that contains attributes common to all types of study documents.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@MappedSuperclass
@SuppressWarnings("PMD")
public abstract class StudyContact extends AbstractEntity {


    private StudyProtocol studyProtocol;
    private Boolean primaryIndicator;

    /**
     * 
     * @return studyProtocol
     */
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * 
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    
}
