package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StatusCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

/**
 * base class for all Structural role.
 * @author NAmiruddin
 *
 */
@MappedSuperclass
public class StructuralRole extends AbstractEntity {
    
    private StatusCode statusCode;
    private Timestamp statusDateRangeLow;

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

}
