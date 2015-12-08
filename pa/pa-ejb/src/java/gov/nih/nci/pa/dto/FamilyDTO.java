package gov.nih.nci.pa.dto;

import org.apache.commons.lang.time.DateUtils;

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
    
    private String name;    
    
    // reporting length
    private static final int REPORTING_LENGTH = 12;
    
    // identifier
    private static final int RESULTS_IDENTIFIER = 0;
    
    // po identifier
    private static final int RESULTS_PO_ID = 1;
    
    // reporting period
    private static final int RESULTS_PERIOD_END = 2;
    
    // reporting date
    private static final int RESULTS_PERIOD_DATE = 3;
    
    /**
     * sets family dto 
     * @param poId po identifier
     */
    public FamilyDTO(Long poId) {
        super();
        this.poId = poId;        
        this.reportingPeriodEndDate = new Date();
        this.reportingPeriodLength = REPORTING_LENGTH;
    }
    
    /**
     * sets family dto
     * @param id identifier
     * @param poId po identifier
     * @param reportingPeriodEndDate reporting end date
     * @param reportingPeriodLength reporting length
     */
    public FamilyDTO(Long id, Long poId, Date reportingPeriodEndDate, Integer reportingPeriodLength) {
        super();
        this.id = id;
        this.poId = poId;
        this.reportingPeriodEndDate = reportingPeriodEndDate;
        this.reportingPeriodLength = reportingPeriodLength;
    }
    
    /**
     * sets family dto
     * @param poId po identifier
     * @param reportingPeriodEndDate reporting end date
     * @param reportingPeriodLength reporting period length
     */
    public FamilyDTO(Long poId, Date reportingPeriodEndDate, Integer reportingPeriodLength) {
        super();        
        this.poId = poId;
        this.reportingPeriodEndDate = reportingPeriodEndDate;
        this.reportingPeriodLength = reportingPeriodLength;
    }
    
    /**
     * Converts to DTO
     * @param family list of family objects
     * @return familydto familydto 
     */
    public static FamilyDTO convertResultsToDTO(Object[] family) {
        Long identifier = (Long) family[RESULTS_IDENTIFIER];
        Long poId = (Long) family[RESULTS_PO_ID];
        Date repPeriodEnd = (Date) family[RESULTS_PERIOD_END];
        Integer repLength = (Integer) family[RESULTS_PERIOD_DATE];
        return new FamilyDTO(identifier, poId, repPeriodEnd, repLength);
    }

    /**
     * Will return the start date
     * @return the start date
     */
    public Date findStartDate() {
        return DateUtils.addMonths(reportingPeriodEndDate, -1 * reportingPeriodLength);
    }
    
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
    
    /**
     * get name
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name name of dto
     */
    public void setName(String name) {
        this.name = name;
    }

}
