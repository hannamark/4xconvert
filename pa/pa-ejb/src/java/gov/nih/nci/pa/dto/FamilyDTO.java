package gov.nih.nci.pa.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author lalit-sb
 *
 */
public class FamilyDTO implements Serializable {
    
    private static final long serialVersionUID = 235503630658839402L;
    
    private Long id;    

    private Long poId;            

    private Date reportingPeriodEndDate;
    
    private Integer reportingPeriodLength;    
    
    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return poId
     */
    public Long getPoId() {
        return poId;
    }

    /**
     * sets po id
     * @param poId po id
     */
    public void setPoId(Long poId) {
        this.poId = poId;
    }
    
    /**
     * @return reportingEndDate
     */
    public Date getReportingPeriodEndDate() {
        return reportingPeriodEndDate;
    }

    /**
     * sets reporting end date
     * @param reportingPeriodEndDate reporting persiod end date
     */
    public void setReportingPeriodEndDate(Date reportingPeriodEndDate) {
        this.reportingPeriodEndDate = reportingPeriodEndDate;
    }

    /**
     * @return reportingPeriodLength
     */
    public Integer getReportingPeriodLength() {
        return reportingPeriodLength;
    }

    /**
     * sets reporting length
     * @param reportingPeriodLength reporting period length
     */
    public void setReportingPeriodLength(Integer reportingPeriodLength) {
        this.reportingPeriodLength = reportingPeriodLength;
    }    

}
