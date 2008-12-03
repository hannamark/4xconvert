package gov.nih.nci.pa.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

/**
 * Abstract class which should be extended for PA domain objects which
 * have statusCode and statusDateRangeLow attributes.
 * @param <ENUM> The type of the enumerator used to store code.
 * @author Hugh Reinhart
 * @since 11/29/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 *
 */
@MappedSuperclass
public abstract class AbstractEntityWithStatusCode<ENUM> extends AbstractEntity {

    private static final long serialVersionUID = 8383567890L;

    private ENUM statusCode;
    private Timestamp statusDateRangeLow;

    /**
     * 
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    public ENUM getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode statusCode
     */
    public void setStatusCode(ENUM statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 
     * @return statusDateRangeLow
     */
    @Column(name = "STATUS_DATE_RANGE_LOW")
    @NotNull
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
