package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author Kalpana Guthikonda
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
public class InterventionalStudyDesignAction extends ActionSupport {
    
    private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    
    /**  
     * @return res
     */
    public String detailsQuery() {
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
            ispDTO = PaRegistry.getStudyProtocolService().getInterventionalStudyProtocol(studyProtocolIi);
            webDTO = setDesignDetailsDTO(ispDTO);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return "details";
    }

    /**  
     * @return res
     */
    public String update() {
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return "details";
        }
        try {  
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        InterventionalStudyProtocolDTO ispFromDatabaseDTO = PaRegistry.getStudyProtocolService()
                .getInterventionalStudyProtocol(studyProtocolIi);
        ispFromDatabaseDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(webDTO.getPhaseCode()))); 
        ispFromDatabaseDTO.setPrimaryPurposeCode(
                CdConverter.convertToCd(PrimaryPurposeCode.getByCode(webDTO.getPrimaryPurposeCode()))); 
        ispFromDatabaseDTO.setBlindingSchemaCode(
                CdConverter.convertToCd(BlindingSchemaCode.getByCode(webDTO.getBlindingSchemaCode()))); 
        ispFromDatabaseDTO.setDesignConfigurationCode(
                CdConverter.convertToCd(DesignConfigurationCode.getByCode(webDTO.getDesignConfigurationCode()))); 
        ispFromDatabaseDTO.setNumberOfInterventionGroups(
                IntConverter.convertToInt(webDTO.getNumberOfInterventionGroups()));
        ispFromDatabaseDTO.setAllocationCode(
                CdConverter.convertToCd(AllocationCode.getByCode(webDTO.getAllocationCode()))); 
        ispFromDatabaseDTO.setPrimaryPurposeOtherText(
                StConverter.convertToSt(webDTO.getPrimaryPurposeOtherText()));
        ispFromDatabaseDTO.setPhaseOtherText(
                StConverter.convertToSt(webDTO.getPhaseOtherText()));
        //ispFromDatabaseDTO.setBlindingRoleCode(
               // CdConverter.convertToCd(BlindingRoleCode.getByCode(webDTO.getBlindingRoleCode())));
        ispFromDatabaseDTO.setMaximumTargetAccrualNumber(
                IntConverter.convertToInt(webDTO.getMaximumTargetAccrualNumber()));
        ispFromDatabaseDTO.setStudyClassificationCode(
                CdConverter.convertToCd(StudyClassificationCode.getByCode(webDTO.getStudyClassificationCode())));
        ispFromDatabaseDTO.setUserLastUpdated((StConverter.convertToSt(
                ServletActionContext.getRequest().getRemoteUser())));
        PaRegistry.getStudyProtocolService().updateInterventionalStudyProtocol(ispFromDatabaseDTO);
        detailsQuery();
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }

    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(webDTO.getPrimaryPurposeCode())) {
            addFieldError("webDTO.primaryPurposeCode",
                    getText("error.primary"));
        }
        if (webDTO.getPrimaryPurposeCode().equalsIgnoreCase("Other") 
                && PAUtil.isEmpty(webDTO.getPrimaryPurposeOtherText())) {
                addFieldError("webDTO.primaryPurposeOtherText",
                    getText("error.comment"));
            }
        
        if (PAUtil.isEmpty(webDTO.getPhaseCode())) {
            addFieldError("webDTO.phaseCode",
                    getText("error.phase"));
        }
        if (PAUtil.isEmpty(webDTO.getDesignConfigurationCode())) {
            addFieldError("webDTO.designConfigurationCode",
                    getText("error.intervention"));
        }
        if (PAUtil.isEmpty(webDTO.getNumberOfInterventionGroups())) {
            addFieldError("webDTO.numberOfInterventionGroups",
                    getText("error.arms"));
        }
        if (PAUtil.isNotEmpty(webDTO.getNumberOfInterventionGroups())) {
            try {
                Integer.valueOf(webDTO.getNumberOfInterventionGroups());
            } catch (NumberFormatException e) {
                addFieldError("webDTO.numberOfInterventionGroups",
                        getText("error.numeric"));
            }
        }
        if (PAUtil.isEmpty(webDTO.getBlindingSchemaCode())) {
            addFieldError("webDTO.blindingSchemaCode",
                    getText("error.masking"));
        }
        if (PAUtil.isEmpty(webDTO.getAllocationCode())) {
            addFieldError("webDTO.allocationCode",
                    getText("error.allocation"));
        }
        if (PAUtil.isEmpty(webDTO.getStudyClassificationCode())) {
            addFieldError("webDTO.studyClassificationCode",
                    getText("error.studyclassification"));
        }
        if (PAUtil.isEmpty(webDTO.getMaximumTargetAccrualNumber())) {
            addFieldError("webDTO.maximumTargetAccrualNumber",
                    getText("error.target.enrollment"));
        }
        if (PAUtil.isNotEmpty(webDTO.getMaximumTargetAccrualNumber())) {
            try {
                Integer.valueOf(webDTO.getMaximumTargetAccrualNumber());
            } catch (NumberFormatException e) {
                addFieldError("webDTO.maximumTargetAccrualNumber",
                        getText("error.numeric"));
            }
        }
    }

    /**
     * @param ispDTO InterventionalStudyProtocolDTO
     * @return DesignDetailsWebDTO
     */
    private ISDesignDetailsWebDTO setDesignDetailsDTO(
            InterventionalStudyProtocolDTO ispDTO) {
        ISDesignDetailsWebDTO dto = new ISDesignDetailsWebDTO();
        if (ispDTO != null) {
            if (ispDTO.getPhaseCode() != null) {
                dto.setPhaseCode(ispDTO.getPhaseCode().getCode()); 
            }
            if (ispDTO.getPrimaryPurposeCode() != null) {
                dto.setPrimaryPurposeCode(ispDTO.getPrimaryPurposeCode().getCode());
            }
            if (ispDTO.getBlindingSchemaCode() != null) {
                dto.setBlindingSchemaCode(ispDTO.getBlindingSchemaCode().getCode());
            }
            if (ispDTO.getDesignConfigurationCode() != null) {
                dto.setDesignConfigurationCode(ispDTO.getDesignConfigurationCode().getCode());
            }
            if (ispDTO.getAllocationCode() != null) {
                dto.setAllocationCode(ispDTO.getAllocationCode().getCode());
            }
            if (ispDTO.getNumberOfInterventionGroups().getValue() != null) {
                dto.setNumberOfInterventionGroups(ispDTO.getNumberOfInterventionGroups().getValue().toString());
            }
            if (ispDTO.getPhaseOtherText() != null) {
                dto.setPhaseOtherText(ispDTO.getPhaseOtherText().getValue());
            }
            if (ispDTO.getPrimaryPurposeOtherText() != null) {
                dto.setPrimaryPurposeOtherText(ispDTO.getPrimaryPurposeOtherText().getValue());
            }
            /*if (ispDTO.getBlindingRoleCode() != null) {
                dto.setBlindingRoleCode(ispDTO.getBlindingRoleCode().getCode());
            }*/
            if (ispDTO.getMaximumTargetAccrualNumber().getValue() != null) {
                dto.setMaximumTargetAccrualNumber(
                        ispDTO.getMaximumTargetAccrualNumber().getValue().toString());
            }
            if (ispDTO.getStudyClassificationCode() != null) {
                dto.setStudyClassificationCode(ispDTO.getStudyClassificationCode().getCode());
            }
        }
        return dto;
    }


    /**
     * @return webDTO
     */
    public ISDesignDetailsWebDTO getWebDTO() {
        return webDTO;
    }


    /**
     * @param webDTO webDTO
     */
    public void setWebDTO(ISDesignDetailsWebDTO webDTO) {
        this.webDTO = webDTO;
    }
    
}
