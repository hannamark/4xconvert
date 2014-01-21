package gov.nih.nci.pa.action;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Monish
 *
 */
public class CTGovImportLogAction extends ActionSupport implements
Preparable {

    private static final String DETAILS = "details";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CTGovImportLogAction.class);
    
    private HttpServletRequest request;
    private boolean searchPerformed;
    
    private List<CTGovImportLog> allCtGovImportLogs = new ArrayList<CTGovImportLog>();
    private List<CTGovImportLog> nctCtGovImportLogs = new ArrayList<CTGovImportLog>();
    private String nciIdentifier;
    private String nctIdentifier;
    private String officialTitle;
    private String action;
    private String importStatus;
    private String userCreated;
    private String logsOnOrAfter;
    private String logsOnOrBefore;
    private String nctId;
    
    private CTGovSyncServiceLocal ctGovSyncService;

    @Override
    public void prepare() {
        request = ServletActionContext.getRequest();
        ctGovSyncService = PaRegistry.getCTGovSyncService();
    }
    
    /**
     * @return res
     * 
     */
    @Override
    public String execute() {
        return SUCCESS;
    }    
    
    /**
     * @return string
     */
    public String query() {
        if (hasActionErrors()) {
            return ERROR;
        }
        try {      
            final Date onOrAfter = StringUtils.isNotBlank(getLogsOnOrAfter()) ? PAUtil
                    .dateStringToDateTime(getLogsOnOrAfter()) : new Date(0);
            final Date onOrBefore = StringUtils.isNotBlank(getLogsOnOrBefore()) ? PAUtil
                    .endOfDay(PAUtil.dateStringToDateTime(getLogsOnOrBefore()))
                    : PAUtil.endOfDay(new Date());
            validateTimeLine(onOrAfter, onOrBefore);
            //get all the log entries sorted by date
            List<CTGovImportLog> importLogs = ctGovSyncService.getLogEntries(getNciIdentifier(), getNctIdentifier(), 
                    getOfficialTitle(), getAction(), getImportStatus(), getUserCreated(), onOrAfter, onOrBefore);
            //get the latest entry for each trial.
            Set<String> uniqueTrials = new HashSet<String>();
            for (CTGovImportLog importLog : importLogs) {
                if (!uniqueTrials.contains(importLog.getNctID())) {
                    getAllCtGovImportLogs().add(importLog);
                    uniqueTrials.add(importLog.getNctID());
                }
            }            
            setSearchPerformed(true);
            return SUCCESS;
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
            LOG.error(e, e);
        }
        return ERROR;
    }
    
    /**
     * Displays pop up page showing the history of CT.Gov import log entries
     * for a specified NCT identifier. 
     * @return list of CT.Gov import log entries for a specified NCT identifier. 
     */
    public String showDetailspopup() {
        try {
            //get the all the log entries for the specified NCT ID.
            setNctCtGovImportLogs(ctGovSyncService.getLogEntries(null, getNctId(), null, null, null, null, null, null));
            return DETAILS;
        } catch (PAException pae) {
            request.setAttribute(Constants.FAILURE_MESSAGE, pae.getLocalizedMessage());
            LOG.error(pae, pae);
        }
        return ERROR;
    }
    
    /**
     * Validates start and end date
     * @param onOrAfter start date
     * @param onOrBefore end date
     * @throws PAException PAException
     */
    private void validateTimeLine(Date onOrAfter, Date onOrBefore) throws PAException {
        if (onOrAfter != null && onOrBefore != null
                && onOrAfter.after(onOrBefore)) {
            throw new PAException(
                    "Dates are inconsistent and will never produce results. "
                            + "Please correct");
        }
    }

    /**
     * @return the logsOnOrAfter
     */
    public String getLogsOnOrAfter() {
        return logsOnOrAfter;
    }

    /**
     * @param logsOnOrAfter the logsOnOrAfter to set
     */
    public void setLogsOnOrAfter(String logsOnOrAfter) {
        this.logsOnOrAfter = logsOnOrAfter;
    }

    /**
     * @return the logsOnOrBefore
     */
    public String getLogsOnOrBefore() {
        return logsOnOrBefore;
    }

    /**
     * @param logsOnOrBefore the logsOnOrBefore to set
     */
    public void setLogsOnOrBefore(String logsOnOrBefore) {
        this.logsOnOrBefore = logsOnOrBefore;
    }

    /**
     * @param searchPerformed the searchPerformed to set
     */
    public void setSearchPerformed(boolean searchPerformed) {
        this.searchPerformed = searchPerformed;
    }

    /**
     * @return the searchPerformed
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * @return the allCtGovImportLogs
     */
    public List<CTGovImportLog> getAllCtGovImportLogs() {
        return allCtGovImportLogs;
    }

    /**
     * @param allCtGovImportLogs the allCtGovImportLogs to set
     */
    public void setAllCtGovImportLogs(List<CTGovImportLog> allCtGovImportLogs) {
        this.allCtGovImportLogs = allCtGovImportLogs;
    }

    /**
     * @param ctGovSyncService the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }

    /**
     * @return NCI identifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    /**
     * @param nciIdentifier NCI identifier to set
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    /**
     * @return NCT identifier
     */
    public String getNctIdentifier() {
        return nctIdentifier;
    }

    /**
     * @param nctIdentifier NCT identifier to set
     */
    public void setNctIdentifier(String nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }

    /**
     * @return trial official title
     */
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * @param officialTitle trial official title to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * @return action performed
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action action performed to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return import status
     */
    public String getImportStatus() {
        return importStatus;
    }

    /**
     * @param importStatus import status to set
     */
    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    /**
     * @return user created
     */
    public String getUserCreated() {
        return userCreated;
    }

    /**
     * @param userCreated user created to set
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * @return nctId
     */
    public String getNctId() {
        return nctId;
    }

    /**
     * @param nctId nctId to set
     */
    public void setNctId(String nctId) {
        this.nctId = nctId;
    }

    /**
     * @return nctCtGovImportLogs
     */
    public List<CTGovImportLog> getNctCtGovImportLogs() {
        return nctCtGovImportLogs;
    }

    /**
     * @param nctCtGovImportLogs nctCtGovImportLogs to set
     */
    public void setNctCtGovImportLogs(List<CTGovImportLog> nctCtGovImportLogs) {
        this.nctCtGovImportLogs = nctCtGovImportLogs;
    }    
}