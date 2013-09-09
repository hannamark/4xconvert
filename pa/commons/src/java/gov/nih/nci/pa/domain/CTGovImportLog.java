package gov.nih.nci.pa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * 
 * @author Monish
 * 
 */
@Entity
@Table(name = "CTGOVIMPORT_LOG")
public class CTGovImportLog implements PersistentObject {

    private static final long serialVersionUID = 2827128893597594641L;
    private Long id;
    private String nciID;
    private String nctID;
    private String title;
    private String action;
    private String userCreated;
    private Date dateCreated;
    private String importStatus;
    private Boolean reviewRequired;

    /**
     * 
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return NCI ID
     */
    @Column(name = "NCI_ID")
    public String getNciID() {
        return nciID;
    }

    /**
     * 
     * @param nciID
     *            set the NCI ID
     */
    public void setNciID(String nciID) {
        this.nciID = nciID;
    }

    /**
     * 
     * @return NCT ID
     */
    @Column(name = "NCT_ID")
    public String getNctID() {
        return nctID;
    }

    /**
     * 
     * @param nctID
     *            NCT ID to set
     */
    public void setNctID(String nctID) {
        this.nctID = nctID;
    }

    /**
     * 
     * @return trial title
     */
    @Column(name = "TRIAL_TITLE")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *            trial title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return action performed (create or update)
     */
    @Column(name = "ACTION_PERFORMED")
    public String getAction() {
        return action;
    }

    /**
     * 
     * @param action
     *            set the action performed.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 
     * @return userCreated
     */
    @Column(name = "USER_CREATED")
    public String getUserCreated() {
        return userCreated;
    }

    /**
     * 
     * @param userCreated
     *            set user who performed the import
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * 
     * @return date created
     */
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * 
     * @param dateCreated
     *            set date created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * 
     * @return import status
     */
    @Column(name = "IMPORT_STATUS")
    public String getImportStatus() {
        return importStatus;
    }

    /**
     * 
     * @param importStatus
     *            set the status of import
     */
    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    /**
     * @return the reviewRequired
     */
    @Column(name = "review_required")
    public Boolean getReviewRequired() {
        return reviewRequired;
    }

    /**
     * @param reviewRequired the reviewRequired to set
     */
    public void setReviewRequired(Boolean reviewRequired) {
        this.reviewRequired = reviewRequired;
    }
}
