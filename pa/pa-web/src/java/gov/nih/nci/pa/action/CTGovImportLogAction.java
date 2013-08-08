package gov.nih.nci.pa.action;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CTGovImportLogAction.class);
    
    private HttpServletRequest request;
    private boolean searchPerformed;
    
    private List<CTGovImportLog> ctGovImportLogs = new ArrayList<CTGovImportLog>();
    private String logsOnOrAfter;
    private String logsOnOrBefore;
    
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
            final Date onOrAfter = StringUtils.isNotBlank(logsOnOrAfter) ? PAUtil
                    .dateStringToDateTime(logsOnOrAfter) : new Date(0);
            final Date onOrBefore = StringUtils.isNotBlank(logsOnOrBefore) ? PAUtil
                    .endOfDay(PAUtil.dateStringToDateTime(logsOnOrBefore))
                    : PAUtil.endOfDay(new Date());
            validateTimeLine(onOrAfter, onOrBefore);
            ctGovImportLogs = ctGovSyncService.getLogEntries(
                    onOrAfter, onOrBefore);
            searchPerformed = true;
            return SUCCESS;
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
            LOG.error(e, e);
        }
        return ERROR;
    }
    
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
     * @return the ctGovImportLogs
     */
    public List<CTGovImportLog> getCtGovImportLogs() {
        return ctGovImportLogs;
    }

    /**
     * @param ctGovImportLogs the ctGovImportLogs to set
     */
    public void setCtGovImportLogs(List<CTGovImportLog> ctGovImportLogs) {
        this.ctGovImportLogs = ctGovImportLogs;
    }

    /**
     * @param ctGovSyncService the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }
}