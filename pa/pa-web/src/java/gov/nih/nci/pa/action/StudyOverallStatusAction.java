/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the protocol status.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyFields" })
public class StudyOverallStatusAction extends ActionSupport implements
        Preparable {
    private static final long serialVersionUID = 1L;
    private static String actualString = "Actual";
    private static String anticipatedString = "Anticipated";

    private Map<String, String>  dateTypeList;
    private StudyProtocolServiceRemote spService;
    private StudyOverallStatusServiceRemote sosService;

    private Ii spIdIi;
    private String currentTrialStatus;
    private String statusDate;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;

    private String ivCurrentTrialStatus;
//    private String ivStatusDate;
//    private String ivStartDate;
//    private String ivCompletionDate;
//    private String ivStartDateType;
//    private String ivCompletionDateType;
    
    /** 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException") 
    public void prepare() throws Exception {
        dateTypeList = new HashMap<String, String>();
        dateTypeList.put(actualString, actualString);
        dateTypeList.put(anticipatedString, anticipatedString);
        
        spService = PaRegistry.getStudyProtocolService();
        sosService = PaRegistry.getStudyOverallStatusService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
        
        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return Action result.
     * @throws Exception exception.
     */
    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException") 
    public String execute() throws Exception {
        loadForm();
        storeInitialValues();
        return SUCCESS;
    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException") 
    private void loadForm() throws Exception {
        StudyProtocolDTO spDto = spService.getStudyProtocol(spIdIi);
        StudyOverallStatusDTO sosDto = sosService.getCurrentStudyOverallStatusByStudyProtocol(spIdIi); 

        Timestamp tsTemp;
        if (spDto != null) {
            tsTemp = TsConverter.convertToTimestamp(spDto.getStartDate());
            if (tsTemp != null) {
                setStartDate(tsTemp.toString());
            } else {
                setStartDate(null);
            }
            
            tsTemp = TsConverter.convertToTimestamp(spDto.getPrimaryCompletionDate());
            if (tsTemp != null) {
                setCompletionDate(tsTemp.toString());
            } else {
                setCompletionDate(null);
            }
            setStartDateType(spDto.getStartDateTypeCode().getCode());
            setCompletionDateType(spDto.getPrimaryCompletionDateTypeCode().getCode());
        } else {
            setStartDate(null);
            setCompletionDate(null);
            setStartDateType(null);
            setCompletionDateType(null);
        }

        if (sosDto != null) {
            setCurrentTrialStatus(sosDto.getStatusCode().getCode());
            tsTemp = TsConverter.convertToTimestamp(sosDto.getStatusDate());
            if (tsTemp != null) {
                setStatusDate(tsTemp.toString());
            } else {
                setStatusDate(null);
            }
        } else {
            setCurrentTrialStatus(null);
            setStatusDate(null);
        }
    }
    
    private void storeInitialValues() {
        ivCurrentTrialStatus = currentTrialStatus;
//        ivStatusDate = statusDate;
//        ivStartDate = startDate;
//        ivCompletionDate = completionDate;
//        ivStartDateType = startDateType;
//        ivCompletionDateType = completionDateType;
    }

    /**  
     * @return res
     */
    public String update()  {
        clearErrorsAndMessages();
        enforceBusinessRules();
        if (hasActionErrors()) {
            return Action.SUCCESS;
        }
        
        // add code here to 
        return Action.SUCCESS;
    }
 
    @SuppressWarnings("PMD.NPathComplexity")
    private void enforceBusinessRules() {
        // check for status transition errors
        StudyStatusCode oldCode = StudyStatusCode.getByCode(ivCurrentTrialStatus);
        StudyStatusCode newCode = StudyStatusCode.getByCode(currentTrialStatus);
        if ((oldCode != null) && (newCode != null) && (!oldCode.canTransitionTo(newCode))) {
            addActionError(getText("Illegal transition.  Status can not be changed from " 
                    + oldCode.getCode() + " to " + newCode.getCode() + "."));
        }
        
        // check all fields are not null unless status is withdrawn
        if (newCode ==  null) {
            addActionError("Current trial status must be set.");
        }
        if (statusDate == null) {
            addActionError("Current trial status date must be set.");
        }
        if (!StudyStatusCode.WITHDRAWN.equals(newCode)) {
            if (startDate == null) {
                addActionError("Trial start date must be set.");
            }
            if (startDateType == null) {
                addActionError("Trial start date type must be set.");
            }
            if (completionDate == null) {
                addActionError("Primary completion date must be set.");
            }
            if (completionDateType == null) {
                addActionError("Primary completion date type must be set.");
            }
        }
        
        // enforce date rules
        Timestamp now = new Timestamp(new Date().getTime());
        if (actualString.equals(startDateType)
            && (now.before(PAUtil.dateStringToTimestamp(startDate)))) {
                addActionError("Actual start dates must be past or current.");
        }
        if (actualString.equals(completionDateType) 
            && (now.before(PAUtil.dateStringToTimestamp(completionDate)))) {
                addActionError("Actual completion dates must be past or current.");
         }
    }
    
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private boolean studyOverallStatusChanged() {
        return true;
    }
    
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private boolean studyProtocolChanged() {
        return true;
    }

    /**
     * @return the startDateType
     */
    public String getStartDateType() {
        return startDateType;
    }

    /**
     * @param startDateType the startDateType to set
     */
    public void setStartDateType(String startDateType) {
        this.startDateType = startDateType;
    }

    /**
     * @return the completionDateType
     */
    public String getCompletionDateType() {
        return completionDateType;
    }

    /**
     * @param completionDateType the completionDateType to set
     */
    public void setCompletionDateType(String completionDateType) {
        this.completionDateType = completionDateType;
    }

    /**
     * @return Trial completion date.
     */
    public Map getPrimaryCompletionDate() {
        return dateTypeList;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = PAUtil.normalizeDateString(startDate);
    }

    /**
     * @return the completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = PAUtil.normalizeDateString(completionDate);
    }

    /**
     * @return the dateTypeList
     */
    public Map<String, String> getDateTypeList() {
        return dateTypeList;
    }

    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = PAUtil.normalizeDateString(statusDate);
    }

    /**
     * @return the currentTrialStatus
     */
    public String getCurrentTrialStatus() {
        return currentTrialStatus;
    }

    /**
     * @param currentTrialStatus the currentTrialStatus to set
     */
    public void setCurrentTrialStatus(String currentTrialStatus) {
        this.currentTrialStatus = currentTrialStatus;
    }
}
