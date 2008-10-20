/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyFields", "PMD.SignatureDeclareThrowsException" })

public class StudyOverallStatusAction extends ActionSupport implements
        Preparable {
    private static final long serialVersionUID = 1L;
    private static final String ACTION_HISTORY = "history";
    private static String actualString = "Actual";
    private static String anticipatedString = "Anticipated";

    private Map<String, String>  dateTypeList;
    private StudyProtocolServiceRemote spService;
    private StudyOverallStatusServiceRemote sosService;

    private Ii spIdIi;
    private String currentTrialStatus;
    private String statusDate;
    private String statusReason;
    private String startDate;
    private String completionDate;
    private String startDateType;
    private String completionDateType;
    private List<StudyOverallStatusWebDTO> overallStatusList;
    
    /** 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
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
    public String execute() throws Exception {
        loadForm();
        return Action.SUCCESS;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        clearErrorsAndMessages();
        
        boolean statusChanged = enforceBusinessRules();
        if (hasActionErrors()) {
            return Action.SUCCESS;
        }
        
        if (statusChanged) {
            insertStudyOverallStatus();
        }
        if (!hasActionErrors()) {
           updateStudyProtocol();
        }
        if (!hasActionErrors()) {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Update succeeded.");
            loadForm();
        }
        return Action.SUCCESS;
    }
    
    /**  
     * @return result
     * @throws Exception exception
     */
    public String history() throws Exception {
        overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        List<StudyOverallStatusDTO> isoList = sosService.getByStudyProtocol(spIdIi);
        for (StudyOverallStatusDTO iso : isoList) {
            overallStatusList.add(new StudyOverallStatusWebDTO(iso));
        }
        return ACTION_HISTORY;
    }
    
    private void insertStudyOverallStatus() {
        if (currentTrialStatus != null) {
            StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
            dto.setIi(IiConverter.convertToIi((Long) null));
            dto.setReasonText(StConverter.convertToSt(this.getStatusReason()));
            dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(currentTrialStatus)));
            dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(statusDate)));
            dto.setStudyProtocolIi(spIdIi);
            
            try {
                sosService.create(dto);            
                // set the current date and status to the session
                StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
                spDTO.setStudyStatusCode(StudyStatusCode.getByCode(currentTrialStatus));
                spDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp(statusDate));
                // set the nee object back to session
                ServletActionContext.getRequest().getSession().setAttribute(
                        Constants.TRIAL_SUMMARY, spDTO);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
    }
    
    private void updateStudyProtocol() {
        StudyProtocolDTO dto;
        try {
            dto = spService.getStudyProtocol(spIdIi);
            dto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(startDate)));
            dto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(completionDate)));
            dto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(startDateType)));
            dto.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(completionDateType)));
            spService.updateStudyProtocol(dto);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }
 
    private void loadForm() throws Exception {
        StudyProtocolDTO spDto = spService.getStudyProtocol(spIdIi);
        StudyOverallStatusDTO sosDto = null;
        List<StudyOverallStatusDTO> sosList = sosService.getCurrentByStudyProtocol(spIdIi);
        if (!sosList.isEmpty()) {
            sosDto = sosList.get(0);
        }

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
            setCurrentTrialStatus((sosDto.getStatusCode() != null)
                    ? sosDto.getStatusCode().getCode() : null);
            tsTemp = TsConverter.convertToTimestamp(sosDto.getStatusDate());
            if (tsTemp != null) {
                setStatusDate(tsTemp.toString());
            } else {
                setStatusDate(null);
            }
            setStatusReason(StConverter.convertToString(sosDto.getReasonText()));
        } else {
            setCurrentTrialStatus(null);
            setStatusDate(null);
            setStatusReason(null);
        }
    }

    /**
     * This method is used to enforce the business rules which are form specific or
     * based on an interaction between services.
     * @return Whether the study status has changed.
     */
    @SuppressWarnings({"PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
    private boolean enforceBusinessRules() throws Exception {
        StudyStatusCode newCode = StudyStatusCode.getByCode(currentTrialStatus);
        
        // enforce date rules related to status transitions
        Timestamp statusTimestamp = PAUtil.dateStringToTimestamp(statusDate);
        Timestamp startTimestamp = PAUtil.dateStringToTimestamp(startDate);
        Timestamp completionTimestamp = PAUtil.dateStringToTimestamp(completionDate);
        StudyStatusCode oldCode = null;
        Timestamp oldDate = null;
        String oldReason = null;
        List<StudyOverallStatusDTO> sosList = sosService.getCurrentByStudyProtocol(spIdIi);
        if (!sosList.isEmpty()) {
            oldCode = StudyStatusCode.getByCode(CdConverter.convertCdToString(sosList.get(0).getStatusCode()));
            oldDate = TsConverter.convertToTimestamp(sosList.get(0).getStatusDate());
            oldReason = StConverter.convertToString(sosList.get(0).getReasonText());
        }
        if (StudyStatusCode.APPROVED.equals(oldCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
            if (startTimestamp.equals(statusTimestamp)) {
                addActionError("When transitioning from 'Approved' to 'Active' the trial start "
                        + "date must be the same as the status date.");
            }
            if (!startDateType.equals(actualString)) {
                addActionError("When transitioning from 'Approved' to 'Active the trial start date must be 'Actual'.");
            }
        }
        if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                && startDateType.equals(anticipatedString)) {
            addActionError("Trial start date can be 'Anticipated' only if the status is "
                        + "'Approved' or 'Withdrawn'.");
        }
        if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
            if (completionDateType.equals(anticipatedString)) {
                addActionError("Trial completion date can not be 'Anticipated' when the status is "
                        + "'Complete' or 'Administratively Complete'.");
            }
            if (!statusTimestamp.equals(completionTimestamp)) {
                addActionError("The trial completion date must be the same as the date when status "
                        + "changed to 'Complete' or 'Administratively Complete'.");
            }
        } else {
            if (!completionDateType.equals(anticipatedString)) {
                addActionError("Trial completion date must be 'Anticipated' when the status is "
                        + "not 'Complete' or 'Administratively Complete'.");
            }
        }
        boolean codeChanged = (newCode == null) ? (oldCode != null) : !newCode.equals(oldCode);
        boolean dateChanged = (oldDate == null) ? (statusTimestamp != null) : !oldDate.equals(statusTimestamp);
        boolean reasonChanged = (oldReason == null) ? (statusReason != null) : !oldReason.equals(statusReason);
        return (codeChanged || dateChanged || reasonChanged);
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
     * @return the statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
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

    /**
     * @return the overallStatusList
     */
    public List<StudyOverallStatusWebDTO> getOverallStatusList() {
        return overallStatusList;
    }

    /**
     * @param overallStatusList the overallStatusList to set
     */
    public void setOverallStatusList(List<StudyOverallStatusWebDTO> overallStatusList) {
        this.overallStatusList = overallStatusList;
    }

}
