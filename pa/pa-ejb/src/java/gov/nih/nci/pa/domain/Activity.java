/**
 * 
 */
package gov.nih.nci.pa.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */
@MappedSuperclass
@SuppressWarnings("PMD")
public class Activity extends AbstractEntity {
    private StudyProtocol studyProtocol;
    /**
     * @return the studyProtocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID")
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    /**
     * @param studyProtocol the studyProtocol to set
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
}
