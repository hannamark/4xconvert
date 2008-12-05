package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StatusCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

/**
 * Abstract class for managing functional roles.
 * @author Naveen Amiruddin
 * @since 05/22/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@MappedSuperclass
public class FunctionalRole extends AbstractEntity {
    static final long serialVersionUID = 8492645655L;
    
    private StatusCode statusCode;
    private Timestamp statusDateRangeLow;
    /** . **/
    protected StudyProtocol studyProtocol;
    
    /**
     * 
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)  
    @NotNull  
    public StatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode statusCode
     */
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 
     * @return statusDateRangeLow
     */
    @Column(name = "STATUS_DATE_RANGE_LOW")
    public Timestamp getStatusDateRangeLow() {
        return statusDateRangeLow;
    }
    /**
     * 
     * @param statusDateRangeLow  statusDateRangeLow
     */
    public void setStatusDateRangeLow(Timestamp statusDateRangeLow) {
        this.statusDateRangeLow = statusDateRangeLow;
    }
    
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
