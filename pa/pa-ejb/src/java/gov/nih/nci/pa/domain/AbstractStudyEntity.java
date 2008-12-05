package gov.nih.nci.pa.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

/**
 * Abstract class which should be extended for PA domain objects which
 * have an attribute studyProtocol for a many to one association with StudyProtocol.
 * 
 * @author Hugh Reinhart
 *
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 *
 */
@MappedSuperclass
public class AbstractStudyEntity extends AbstractEntity {
    private static final long serialVersionUID = 1212567890L;

    /** . */
    protected StudyProtocol studyProtocol;

    /**
     * 
     * @return studyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", updatable = false)
    @NotNull
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
