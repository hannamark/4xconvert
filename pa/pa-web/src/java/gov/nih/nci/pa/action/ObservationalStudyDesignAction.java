package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.OSDesignDetailsWebDTO;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
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
 * @since 10/27/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
public class ObservationalStudyDesignAction extends ActionSupport {

    private OSDesignDetailsWebDTO webDTO =  new OSDesignDetailsWebDTO();
    /**  
     * @return res
     */
    public String detailsQuery() {
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
            ospDTO = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(studyProtocolIi);
            webDTO = setDesignDetailsDTO(ospDTO);
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
            ObservationalStudyProtocolDTO ospFromDatabaseDTO = PaRegistry.getStudyProtocolService()
            .getObservationalStudyProtocol(studyProtocolIi);
            ospFromDatabaseDTO.setStudyModelCode(
                    CdConverter.convertToCd(StudyModelCode.getByCode(webDTO.getStudyModelCode()))); 
            ospFromDatabaseDTO.setTimePerspectiveCode(
                    CdConverter.convertToCd(TimePerspectiveCode.getByCode(webDTO.getTimePerspectiveCode()))); 
            ospFromDatabaseDTO.setBiospecimenRetentionCode(
                    CdConverter.convertToCd(BiospecimenRetentionCode.getByCode(webDTO.getBiospecimenRetentionCode()))); 
            ospFromDatabaseDTO.setNumberOfGroups(
                    IntConverter.convertToInt(webDTO.getNumberOfGroups()));
            ospFromDatabaseDTO.setStudyModelOtherText(
                    StConverter.convertToSt(webDTO.getStudyModelOtherText()));
            ospFromDatabaseDTO.setTimePerspectiveOtherText(
                    StConverter.convertToSt(webDTO.getTimePerspectiveOtherText()));  
            ospFromDatabaseDTO.setBiospecimenDescription(
                    StConverter.convertToSt(webDTO.getBiospecimenDescription()));  
            ospFromDatabaseDTO.setMaximumTargetAccrualNumber(
                    IntConverter.convertToInt(webDTO.getMaximumTargetAccrualNumber()));
            ospFromDatabaseDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            PaRegistry.getStudyProtocolService().updateObservationalStudyProtocol(ospFromDatabaseDTO);
            detailsQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "details";
    }

    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(webDTO.getStudyModelCode())) {
            addFieldError("webDTO.studyModelCode",
                    getText("error.studyModelCode"));
        }
        if (webDTO.getStudyModelCode().equalsIgnoreCase("Other") 
                && PAUtil.isEmpty(webDTO.getStudyModelOtherText())) {
            addFieldError("webDTO.studyModelOtherText",
                    getText("error.studyModelOtherText"));
        }

        if (PAUtil.isEmpty(webDTO.getTimePerspectiveCode())) {
            addFieldError("webDTO.timePerspectiveCode",
                    getText("error.timePerspectiveCode"));
        }
        if (webDTO.getTimePerspectiveCode().equalsIgnoreCase("Other") 
                && PAUtil.isEmpty(webDTO.getTimePerspectiveOtherText())) {
            addFieldError("webDTO.timePerspectiveOtherText",
                    getText("error.timePerspectiveOtherText"));
        }
        if (PAUtil.isEmpty(webDTO.getBiospecimenRetentionCode())) {
            addFieldError("webDTO.biospecimenRetentionCode",
                    getText("error.biospecimenRetentionCode"));
        }        
        if (PAUtil.isEmpty(webDTO.getNumberOfGroups())) {
            addFieldError("webDTO.numberOfGroups",
                    getText("error.numberOfGroups"));            
        }
        if (PAUtil.isNotEmpty(webDTO.getNumberOfGroups())) {
            try {
                Integer.valueOf(webDTO.getNumberOfGroups());
            } catch (NumberFormatException e) {
                addFieldError("webDTO.numberOfGroups",
                        getText("error.numeric"));
            }
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
    private OSDesignDetailsWebDTO setDesignDetailsDTO(
            ObservationalStudyProtocolDTO ospDTO) {
        OSDesignDetailsWebDTO dto = new OSDesignDetailsWebDTO();
        if (ospDTO != null) {

            if (ospDTO.getStudyModelCode() != null) {
                dto.setStudyModelCode(ospDTO.getStudyModelCode().getCode());
            }            

            if (ospDTO.getTimePerspectiveCode() != null) {
                dto.setTimePerspectiveCode(ospDTO.getTimePerspectiveCode().getCode());
            }                

            if (ospDTO.getBiospecimenRetentionCode() != null) {
                dto.setBiospecimenRetentionCode(ospDTO.getBiospecimenRetentionCode().getCode());
            }                

            if (ospDTO.getBiospecimenDescription() != null) {
                dto.setBiospecimenDescription(ospDTO.getBiospecimenDescription().getValue());
            }                

            if (ospDTO.getNumberOfGroups().getValue() != null) {
                dto.setNumberOfGroups(ospDTO.getNumberOfGroups().getValue().toString());
            }                

            if (ospDTO.getMaximumTargetAccrualNumber().getValue() != null) {
                dto.setMaximumTargetAccrualNumber(ospDTO.getMaximumTargetAccrualNumber().getValue().toString());
            }               

            if (ospDTO.getStudyModelOtherText() != null) {
                dto.setStudyModelOtherText(ospDTO.getStudyModelOtherText().getValue());
            }                

            if (ospDTO.getTimePerspectiveOtherText() != null) {
                dto.setTimePerspectiveOtherText(ospDTO.getTimePerspectiveOtherText().getValue());
            }                
        }
        return dto;
    }

    /**
     * @return webDTO
     */
    public OSDesignDetailsWebDTO getWebDTO() {
        return webDTO;
    }

    /**
     * @param webDTO OSDesignDetailsWebDTO
     */
    public void setWebDTO(OSDesignDetailsWebDTO webDTO) {
        this.webDTO = webDTO;
    }
}
