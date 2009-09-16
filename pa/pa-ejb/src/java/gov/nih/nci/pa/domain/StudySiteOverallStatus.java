/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudySiteStatusCode;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * Maintains history of overall status for the Site.
 * 
 * @author Vrushali
 *
 */
@Entity
@Table(name = "STUDY_SITE_OVERALL_STATUS")
public class StudySiteOverallStatus extends AbstractSiteEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private StudySiteStatusCode statusCode;
    private Timestamp statusDate;

    /**
     * @return the statusCode
     */
    @NotNull
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    public StudySiteStatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(StudySiteStatusCode statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    @Column(name = "STATUS_DATE")
    @NotNull
    public Timestamp getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
    }

}
