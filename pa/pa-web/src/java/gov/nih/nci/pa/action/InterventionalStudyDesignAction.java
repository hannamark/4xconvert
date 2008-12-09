package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author Kalpana Guthikonda
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", 
    "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class InterventionalStudyDesignAction extends ActionSupport {

    private static final String OUTCOME = "outcome";
    private static final String OUTCOMEADD = "outcomeAdd";
    private static final int MAXIMUM_CHAR = 200;
    private static final int MAXIMUM_CHAR_OUTCOME = 254; 
    private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    private String subject;
    private String investigator;
    private String caregiver;
    private String outcomesassessor;
    private List<ISDesignDetailsWebDTO> outcomeList;
    private Long id = null;
    private String page;

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
            ispFromDatabaseDTO.setMaximumTargetAccrualNumber(
                    IntConverter.convertToInt(webDTO.getMaximumTargetAccrualNumber()));
            ispFromDatabaseDTO.setStudyClassificationCode(
                    CdConverter.convertToCd(StudyClassificationCode.getByCode(webDTO.getStudyClassificationCode())));

            List<Cd> cds = new ArrayList();
            if (caregiver != null) {
                cds.add(CdConverter.convertStringToCd(caregiver));
            }
            if (investigator != null) {
                cds.add(CdConverter.convertStringToCd(investigator));
            }
            if (outcomesassessor != null) {
                cds.add(CdConverter.convertStringToCd(outcomesassessor));
            }
            if (subject != null) {
                cds.add(CdConverter.convertStringToCd(subject));
            }
            ispFromDatabaseDTO.setBlindedRoleCode(DSetConverter.convertCdListToDSet(cds));

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
        
        if (PAUtil.isNotEmpty(webDTO.getPrimaryPurposeOtherText())
            && webDTO.getPrimaryPurposeOtherText().length() > MAXIMUM_CHAR) {
          addFieldError("webDTO.primaryPurposeOtherText",
              getText("error.spType.other.maximumChar"));        
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
    @SuppressWarnings({"PMD.AvoidDeeplyNestedIfStmts" })
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
            if (ispDTO.getBlindedRoleCode() != null) {
                List<Cd> cds =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
                if (cds != null) {
                    for (Cd cd : cds) {
                        if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                            this.caregiver = BlindingRoleCode.CAREGIVER.getCode();
                            continue;
                        }
                        if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                            this.investigator = BlindingRoleCode.INVESTIGATOR.getCode();
                            continue;
                        }
                        if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                            this.outcomesassessor = BlindingRoleCode.OUTCOMES_ASSESSOR.getCode();
                            continue;
                        }
                        if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                            this.subject = BlindingRoleCode.SUBJECT.getCode();
                            continue;
                        }
                    }
                }
            }
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
     * @return result
     */
    public String outcomeQuery()  {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            List<StudyOutcomeMeasureDTO> isoList = PaRegistry.getOutcomeMeasurService().
            getByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {
                outcomeList = new ArrayList<ISDesignDetailsWebDTO>();
                for (StudyOutcomeMeasureDTO dto : isoList) {
                    outcomeList.add(setOutcomeMeasureDTO(dto));
                }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        getText("No OutcomeMeasures exists for the trial."));
            }            

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOME;
    }

    /**
     * @return result
     */
    public String outcomeinput() {
        return OUTCOMEADD;
    }

    /**
     * @return result
     */
    public String outcomecreate() {
        enforceOutcomeBusinessRules();
        if (hasFieldErrors()) {
            return OUTCOMEADD;
        }
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyOutcomeMeasureDTO sgDTO = new StudyOutcomeMeasureDTO();
            sgDTO.setStudyProtocolIi(studyProtocolIi);
            sgDTO.setName(StConverter.convertToSt(webDTO.getName()));
            sgDTO.setPrimaryIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getPrimaryIndicator())));
            sgDTO.setSafetyIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSafetyIndicator())));
            sgDTO.setTimeFrame(StConverter.convertToSt(webDTO.getTimeFrame()));
            PaRegistry.getOutcomeMeasurService().create(sgDTO);
            outcomeQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            return OUTCOME;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return OUTCOMEADD;
        }         
    }
    /**
     * @return result
     */
    public String outcomeedit() {
        try {
            StudyOutcomeMeasureDTO  sgDTO =
                PaRegistry.getOutcomeMeasurService().get(IiConverter.convertToIi(id));
            webDTO = setOutcomeMeasureDTO(sgDTO);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOMEADD;
    }

    /**
     * @return result
     */
    public String outcomeupdate() {
        enforceOutcomeBusinessRules();
        if (hasFieldErrors()) {
            return OUTCOMEADD;
        }
        try {

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyOutcomeMeasureDTO  sgDTO = new StudyOutcomeMeasureDTO();
            sgDTO.setIdentifier(IiConverter.convertToIi(id));
            sgDTO.setStudyProtocolIi(studyProtocolIi);
            sgDTO.setName(StConverter.convertToSt(webDTO.getName()));
            sgDTO.setPrimaryIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getPrimaryIndicator())));
            sgDTO.setSafetyIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSafetyIndicator())));
            sgDTO.setTimeFrame(StConverter.convertToSt(webDTO.getTimeFrame()));
            PaRegistry.getOutcomeMeasurService().update(sgDTO);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            outcomeQuery();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return OUTCOMEADD;
        }
        return OUTCOME;
    }

    private void enforceOutcomeBusinessRules() {
        if (PAUtil.isEmpty(webDTO.getPrimaryIndicator())) {
            addFieldError("webDTO.primaryIndicator",
                    getText("error.outcome.primary"));
        }
        if (PAUtil.isEmpty(webDTO.getName())) {
            addFieldError("webDTO.name",
                    getText("error.outcome.description"));
        }
        if (PAUtil.isNotEmpty(webDTO.getName())
            && webDTO.getName().length() > MAXIMUM_CHAR_OUTCOME) {
          addFieldError("webDTO.name",
              getText("error.outcome.maximumChar"));        
        }
        if (PAUtil.isEmpty(webDTO.getTimeFrame())) {
            addFieldError("webDTO.timeFrame",
                    getText("error.outcome.timeFrame"));
        } 
        if (PAUtil.isNotEmpty(webDTO.getTimeFrame())
            && webDTO.getTimeFrame().length() > MAXIMUM_CHAR_OUTCOME) {
          addFieldError("webDTO.timeFrame",
              getText("error.outcome.maximumChar"));        
        }
        if (PAUtil.isEmpty(webDTO.getSafetyIndicator())) {
            addFieldError("webDTO.safetyIndicator",
                    getText("error.outcome.safety"));
        } 
    }

    /**
     * @return result
     */
    public String outcomedelete()  {

        try {
            PaRegistry.getOutcomeMeasurService().delete(IiConverter.convertToIi(id));
            outcomeQuery();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return OUTCOME;
    }

    private ISDesignDetailsWebDTO setOutcomeMeasureDTO(
            StudyOutcomeMeasureDTO dto) {
        ISDesignDetailsWebDTO webdto = new ISDesignDetailsWebDTO();
        if (dto != null) {
            if (dto.getPrimaryIndicator().getValue() != null) {
                webdto.setPrimaryIndicator(dto.getPrimaryIndicator().getValue().toString());
            }
            if (dto.getName() != null) {
                webdto.setName(dto.getName().getValue());
            }
            if (dto.getTimeFrame() != null) {
                webdto.setTimeFrame(dto.getTimeFrame().getValue());
            }
            if (dto.getSafetyIndicator().getValue() != null) {
                webdto.setSafetyIndicator(dto.getSafetyIndicator().getValue().toString());
            }
            if (dto.getIdentifier() != null) {
                webdto.setId(dto.getIdentifier().getExtension());
            }

        }
        return webdto;
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

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return investigator
     */
    public String getInvestigator() {
        return investigator;
    }

    /**
     * @param investigator investigator
     */
    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    /**
     * @return caregiver
     */
    public String getCaregiver() {
        return caregiver;
    }

    /**
     * @param caregiver caregiver
     */
    public void setCaregiver(String caregiver) {
        this.caregiver = caregiver;
    }

    /**
     * @return outcomesassessor
     */
    public String getOutcomesassessor() {
        return outcomesassessor;
    }

    /**
     * @param outcomesassessor outcomesassessor
     */
    public void setOutcomesassessor(String outcomesassessor) {
        this.outcomesassessor = outcomesassessor;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return outcomeList
     */
    public List<ISDesignDetailsWebDTO> getOutcomeList() {
        return outcomeList;
    }

    /**
     * @param outcomeList outcomeList
     */
    public void setOutcomeList(List<ISDesignDetailsWebDTO> outcomeList) {
        this.outcomeList = outcomeList;
    }

    /**
     * 
     * @return boolean value
     */
    public boolean isCaregiverChecked() {        
        if (BlindingRoleCode.CAREGIVER.getCode().equals(this.caregiver)) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @return boolean value
     */
    public boolean isInvestigatorChecked() { 
        if (BlindingRoleCode.INVESTIGATOR.getCode().equals(this.investigator)) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @return boolean value
     */
    public boolean isOutcomesAssessorChecked() { 
        if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(this.outcomesassessor)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return boolean value
     */
    public boolean isSubjectChecked() { 
        if (BlindingRoleCode.SUBJECT.getCode().equals(this.subject)) {
            return true;
        }
        return false;
    }

}
