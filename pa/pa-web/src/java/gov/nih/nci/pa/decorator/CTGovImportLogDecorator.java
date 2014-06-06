/**
 * 
 */
package gov.nih.nci.pa.decorator;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.StudyInbox;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public final class CTGovImportLogDecorator {

    private final CTGovImportLog log;
    private final Set<CTGovImportLog> logEntriesPerTrial = new HashSet<CTGovImportLog>();

    /**
     * @param log
     *            log
     */
    public CTGovImportLogDecorator(CTGovImportLog log) {
        this.log = log;
        logEntriesPerTrial.add(log);
    }

    /**
     * @param log
     */
    public void addLogEntry(CTGovImportLog log) {
        logEntriesPerTrial.add(log);
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getId()
     */
    public Long getId() {
        return log.getId();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getNciID()
     */
    public String getNciID() {
        return log.getNciID();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getNctID()
     */
    public String getNctID() {
        return log.getNctID();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getTitle()
     */
    public String getTitle() {
        return log.getTitle();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getAction()
     */
    public String getAction() {
        return log.getAction();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getUserCreated()
     */
    public String getUserCreated() {
        return log.getUserCreated();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getDisplayableReviewIndicator()
     */
    public String getDisplayableReviewIndicator() {
        return log.getDisplayableReviewIndicator();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getDateCreated()
     */
    public Date getDateCreated() {
        return log.getDateCreated();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getImportStatus()
     */
    public String getImportStatus() {
        return log.getImportStatus();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getReviewRequired()
     */
    public Boolean getReviewRequired() {
        return log.getReviewRequired();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getAdmin()
     */
    public Boolean getAdmin() {
        return log.getAdmin();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getScientific()
     */
    public Boolean getScientific() {
        return log.getScientific();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getStudyInbox()
     */
    public StudyInbox getStudyInbox() {
        return log.getStudyInbox();
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getAckPending()
     */
    public String getAckPending() {
        boolean admin = false;
        boolean sci = false;

        for (CTGovImportLog log : logEntriesPerTrial) {
            switch (log.getAckPending()) {
            case CTGovImportLog.ADMIN_ACKNOWLEDGMENT:
                admin = true;
                break;
            case CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT:
                sci = true;
                break;
            case CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT:
                admin = true;
                sci = true;
                break;
            default:
                break;
            }
        }

        if (admin && sci) {
            return CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT;
        } else if (admin) {
            return CTGovImportLog.ADMIN_ACKNOWLEDGMENT;
        } else if (sci) {
            return CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT;
        } else {
            return CTGovImportLog.NO_ACKNOWLEDGEMENT;
        }
    }

    /**
     * @return
     * @see gov.nih.nci.pa.domain.CTGovImportLog#getAckPerformed()
     */
    public String getAckPerformed() {
        boolean admin = false;
        boolean sci = false;

        for (CTGovImportLog log : logEntriesPerTrial) {
            switch (log.getAckPerformed()) {
            case CTGovImportLog.ADMIN_ACKNOWLEDGMENT:
                admin = true;
                break;
            case CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT:
                sci = true;
                break;
            case CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT:
                admin = true;
                sci = true;
                break;
            default:
                break;
            }
        }

        if (admin && sci) {
            return CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT;
        } else if (admin) {
            return CTGovImportLog.ADMIN_ACKNOWLEDGMENT;
        } else if (sci) {
            return CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT;
        } else {
            return CTGovImportLog.NO_ACKNOWLEDGEMENT;
        }

    }

}
