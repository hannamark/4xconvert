package gov.nih.nci.pa.domain;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * The participation of a Regulatory Authority in the governmental oversight of a
 * clinical trial.
 * 
 * For example, the FDA oversees a breast cancer trial using tamoxifen. 
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table (name = "STUDY_REGULATORY_AUTHORITY")
public class StudyRegulatoryAuthority extends AbstractEntity {
        
    private static final long serialVersionUID = 1L;
    private StudyProtocol studyProtocol;
    private RegulatoryAuthority regulatoryAuthority;
    
    /**
     * 
     * @return regulatoryAuthority
     */
    @ManyToOne
    @JoinColumn(name = "REGULATORY_AUTHORITY_ID", updatable = false)
    @NotNull
    public RegulatoryAuthority getRegulatoryAuthority() {
        return regulatoryAuthority;
    }
    /**
     * 
     * @param regulatoryAuthority regulatoryAuthority
     */
    public void setRegulatoryAuthority(RegulatoryAuthority regulatoryAuthority) {
        this.regulatoryAuthority = regulatoryAuthority;
    }
    /**
     * 
     * @return protocol
     */
    @OneToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", updatable = false)
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
