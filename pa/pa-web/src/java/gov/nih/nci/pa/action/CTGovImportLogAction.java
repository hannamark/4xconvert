package gov.nih.nci.pa.action;

import gov.nih.nci.pa.decorator.CTGovImportLogDecorator;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@SuppressWarnings("PMD.TooManyFields")
public class CTGovImportLogAction extends ActionSupport implements
Preparable {

    private static final String DETAILS = "details";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CTGovImportLogAction.class);
    
    private HttpServletRequest request;
    private boolean searchPerformed;
    
    private List<CTGovImportLogDecorator> allCtGovImportLogs = new ArrayList<CTGovImportLogDecorator>();
    private List<CTGovImportLog> nctCtGovImportLogs = new ArrayList<CTGovImportLog>();
    private CTGovSyncServiceLocal ctGovSyncService;
    private String nctId;
    private String logsOnOrAfter;
    private String logsOnOrBefore;    
    private CTGovImportLogSearchCriteria searchCriteria = new CTGovImportLogSearchCriteria();

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
            Date onOrAfter = StringUtils.isNotBlank(getLogsOnOrAfter()) ? PAUtil
                    .dateStringToDateTime(getLogsOnOrAfter()) : new Date(0);
            Date onOrBefore = StringUtils.isNotBlank(getLogsOnOrBefore()) ? PAUtil
                    .endOfDay(PAUtil.dateStringToDateTime(getLogsOnOrBefore()))
                    : PAUtil.endOfDay(new Date());
                    
            validateTimeLine(onOrAfter, onOrBefore);
            //get all the log entries sorted by date
            searchCriteria.setOnOrAfter(onOrAfter);
            searchCriteria.setOnOrBefore(onOrBefore);
            List<CTGovImportLog> importLogs = ctGovSyncService.getLogEntries(searchCriteria);
            
            processAndDecorate(importLogs);    
            
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
     * @param importLogs
     */
    private void processAndDecorate(List<CTGovImportLog> importLogs) {

        Map<String, CTGovImportLogDecorator> map = new HashMap<String, CTGovImportLogDecorator>();

        for (CTGovImportLog importLog : importLogs) {
            CTGovImportLogDecorator decor = map.get(importLog.getNctID());
            if (decor == null) {
                decor = new CTGovImportLogDecorator(importLog);
                map.put(importLog.getNctID(), decor);
                getAllCtGovImportLogs().add(decor);
            } else {
                decor.addLogEntry(importLog);
            }

        }
    }
    
    /**
     * Displays pop up page showing the history of CT.Gov import log entries
     * for a specified NCT identifier. 
     * @return list of CT.Gov import log entries for a specified NCT identifier. 
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public String showDetailspopup() {
        try {
            //get the all the log entries for the specified NCT ID.
            searchCriteria.setNctIdentifier(getNctId());
            List<CTGovImportLog> logEntries = ctGovSyncService.getLogEntries(searchCriteria);
            for (CTGovImportLog existingEntry : logEntries) {
                StudyInbox si = existingEntry.getStudyInbox();
                //if there is a performed admin/scientific acknowledgment 
                //then the log information needs to be split to show  
                //two entries : first entry to show close date and acknowledged user
                //and second entry to show pending information.
                if (existingEntry.getAckPerformed().equals(CTGovImportLog.ADMIN_ACKNOWLEDGMENT)) {
                    CTGovImportLog newEntry = new CTGovImportLog();
                    newEntry.setDateCreated(si.getAdminCloseDate());
                    newEntry.setUserCreated(CsmUserUtil.getDisplayUsername(
                            si.getAdminAcknowledgedUser()));
                    newEntry.setAckPerformed(CTGovImportLog.ADMIN_ACKNOWLEDGMENT);
                    existingEntry.setStudyInbox(null);
                    existingEntry.setAckPending(CTGovImportLog.ADMIN_ACKNOWLEDGMENT);
                    existingEntry.setAckPerformed("");
                    nctCtGovImportLogs.add(newEntry);
                } else if (existingEntry.getAckPerformed().equals(CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT)) {
                    CTGovImportLog newEntry = new CTGovImportLog();
                    newEntry.setDateCreated(si.getScientificCloseDate());
                    newEntry.setUserCreated(CsmUserUtil.getDisplayUsername(
                            si.getScientificAcknowledgedUser()));
                    newEntry.setAckPerformed(CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT);
                    existingEntry.setStudyInbox(null);
                    existingEntry.setAckPending(CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT);
                    existingEntry.setAckPerformed("");
                    nctCtGovImportLogs.add(newEntry);
                } else if (existingEntry.getAckPerformed().equals(
                        CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT)) {
                    CTGovImportLog newEntry1 = new CTGovImportLog();
                    newEntry1.setDateCreated(si.getAdminCloseDate());
                    newEntry1.setUserCreated(CsmUserUtil.getDisplayUsername(
                            si.getAdminAcknowledgedUser()));
                    newEntry1.setAckPerformed(CTGovImportLog.ADMIN_ACKNOWLEDGMENT);
                    CTGovImportLog newEntry2 = new CTGovImportLog();
                    newEntry2.setDateCreated(si.getScientificCloseDate());
                    newEntry2.setUserCreated(CsmUserUtil.getDisplayUsername(
                            si.getScientificAcknowledgedUser()));
                    newEntry2.setAckPerformed(CTGovImportLog.SCIENTIFIC_ACKNOWLEDGEMENT);
                    //in case of admin and sci ack compare the close dates to
                    //display the entries in date order.
                    Timestamp adminCloseDate = si.getAdminCloseDate();
                    Timestamp sciCloseDate = si.getScientificCloseDate();
                    int moreRecent = adminCloseDate.compareTo(sciCloseDate);
                    if (moreRecent >= 0) {
                        nctCtGovImportLogs.add(newEntry1);
                        nctCtGovImportLogs.add(newEntry2);
                    } else {
                        nctCtGovImportLogs.add(newEntry2);
                        nctCtGovImportLogs.add(newEntry1);
                    }
                    existingEntry.setStudyInbox(null);
                    existingEntry.setAckPending(CTGovImportLog.ADMIN_AND_SCIENTIFIC_ACKNOWLEDGEMENT);
                    existingEntry.setAckPerformed("");
                }
                nctCtGovImportLogs.add(existingEntry);
            }
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
    public List<CTGovImportLogDecorator> getAllCtGovImportLogs() {
        return allCtGovImportLogs;
    }

    /**
     * @param allCtGovImportLogs the allCtGovImportLogs to set
     */
    public void setAllCtGovImportLogs(List<CTGovImportLogDecorator> allCtGovImportLogs) {
        this.allCtGovImportLogs = allCtGovImportLogs;
    }

    /**
     * @param ctGovSyncService the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
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
     * @return search criteria
     */
    public CTGovImportLogSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * @param searchCriteria search criteria to set
     */
    public void setSearchCriteria(CTGovImportLogSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}