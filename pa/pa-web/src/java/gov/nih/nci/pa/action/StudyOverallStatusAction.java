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
        enforceBusinessRules();
        if (hasActionErrors()) {
            return Action.SUCCESS;
        }
        
        insertStudyOverallStatus();
        if (!hasActionErrors()) {
           updateStudyProtocol();
        }
        if (!hasActionErrors()) {
            //addActionError("Update succeeded.");
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Update succeeded.");
        }
        loadForm();
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
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setIi(IiConverter.convertToIi((Long) null));
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
        } else {
            setCurrentTrialStatus(null);
            setStatusDate(null);
        }
    }

    /**
     * This method is used to enforce the business rules which are form specific or
     * based on an interaction between services.
     */
    private void enforceBusinessRules() {
        // check all fields are not null unless status is withdrawn
        StudyStatusCode newCode = StudyStatusCode.getByCode(currentTrialStatus);
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
