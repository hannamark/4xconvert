package gov.nih.nci.registry.action;

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * 
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class TrialValidator {
    private static String actualString = "Actual";
    private static String anticipatedString = "Anticipated";
    private static final Logger LOG = Logger.getLogger(TrialValidator.class);
    /**
     * 
     * @param fileName name
     * @param file file
     * @param errorField err
     * @param errorMsg errmsg
     * @return map
     */
    public Map<String, String> validateDcoument(String fileName, File file,
            String errorField, String errorMsg) {
            Map<String, String> addFieldError = new HashMap<String, String>();
        if (PAUtil.isNotEmpty(errorMsg) && PAUtil.isEmpty(fileName)) {
            addFieldError.put(errorField, getText(errorMsg));
        }
        if (PAUtil.isNotEmpty(fileName)) {
            if (!file.exists()) {
                addFieldError.put(errorField, 
                        getText("error.submit.invalidDocument"));
            }
            if (!RegistryUtil.isValidFileType(fileName)) {
                addFieldError.put(errorField, 
                        getText("error.submit.invalidFileType"));                
            }
        }
        return addFieldError;
    }

    private String getText(String errorMsg) {
        // TODO Auto-generated method stub
        return ResourceBundle.getBundle("ApplicationResources").getString(errorMsg);
    }

    /**
     * 
     * @param trialDto dto
     * @return map
     */
    @SuppressWarnings({"PMD", "PMD.AvoidDeeplyNestedIfStmts" })
    public Map<String, String> validateTrialDTO(TrialDTO trialDto) {
        Map<String, String> addFieldError = new HashMap<String, String>(); 
        InvalidValue[] invalidValues = null;
        ClassValidator<TrialDTO> classValidator = new ClassValidator(trialDto.getClass());
                invalidValues = classValidator.getInvalidValues(trialDto);
                for (int i = 0; i < invalidValues.length; i++) {
                    addFieldError.put("trialDTO." + invalidValues[i].getPropertyName(), 
                            getText(invalidValues[i].getMessage().trim()));
        }
        if (!(trialDto.getResponsiblePartyType().equals("pi"))
             && (PAUtil.isEmpty(trialDto.getResponsiblePersonIdentifier()))) {
            addFieldError.put("ResponsiblePartyNotSelected", 
                    getText("error.submit.sponsorResponsibelParty"));
        }
        //validate Phase and Purpose when Selected value is OTHER
        if (PAUtil.isNotEmpty(trialDto.getPhaseCode()) && (PhaseCode.OTHER.getCode().equals(trialDto.getPhaseCode()) 
                        && PAUtil.isEmpty(trialDto.getPhaseOtherText()))) {
                addFieldError.put("trialDTO.phaseOtherText", 
                        getText("error.submit.otherPhaseText"));
        }
        if (PAUtil.isNotEmpty(trialDto.getPrimaryPurposeCode()) 
                && (PrimaryPurposeCode.OTHER.getCode().equals(trialDto.getPrimaryPurposeCode()) 
                        && PAUtil.isEmpty(trialDto.getPrimaryPurposeOtherText()))) {
                addFieldError.put("trialDTO.primaryPurposeOtherText", 
                        getText("error.submit.otherPurposeText"));
        }
        String err = "error.submit.invalidDate";      // validate date and its format
        if (!RegistryUtil.isValidDate(trialDto.getStatusDate())) {
                    addFieldError.put("trialDTO.statusDate", getText(err));
        }
        if (!RegistryUtil.isValidDate(trialDto.getStartDate())) {
                    addFieldError.put("trialDTO.startDate", getText(err));
        }
        if (!RegistryUtil.isValidDate(trialDto.getCompletionDate())) {
                    addFieldError.put("trialDTO.completionDate", getText(err));
        }
        if (PAUtil.isNotEmpty(trialDto.getStatusCode())) {
            if ((StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(trialDto.getStatusCode())
                 || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode().equals(trialDto.getStatusCode())
                 || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode().
                 equals(trialDto.getStatusCode())
                 || StudyStatusCode.WITHDRAWN.getCode().equals(trialDto.getStatusCode()))
                 && PAUtil.isEmpty(trialDto.getReason())) {
                addFieldError.put("trialDTO.reason", getText("error.submit.trialStatusReason"));
            }
        }
        return addFieldError;
    }
    /**
     *  
     * @param trialDto dto
     * @return t
     * @throws PAException ex
     */
    public boolean isStatusOrDateChanged(TrialDTO trialDto) throws PAException {
        StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(trialDto.getIdentifier()));
        
        Timestamp oldStartTimestamp = TsConverter.convertToTimestamp(spDTO.getStartDate());
        Timestamp oldCompletionTimestamp = TsConverter.convertToTimestamp(spDTO.getPrimaryCompletionDate());
        String oldStartDateType = CdConverter.convertCdToString(spDTO.getStartDateTypeCode());
        String oldCompletionDateType = CdConverter.convertCdToString(spDTO.getPrimaryCompletionDateTypeCode());

        StudyOverallStatusWebDTO  dto = getStatusDTO(trialDto.getIdentifier());
        StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(dto.getStatusCode());
        Timestamp oldStatusDate = PAUtil.dateStringToTimestamp(dto.getStatusDate());
        String oldReason = dto.getReason();
        
        boolean codeChanged = (StudyStatusCode.getByCode(trialDto.getStatusCode()) == null)
                ? (oldStatusCode != null) : !StudyStatusCode.getByCode(trialDto.getStatusCode()).equals(oldStatusCode);
        boolean statusDateChanged = (oldStatusDate == null) 
                ? (PAUtil.dateStringToTimestamp(trialDto.getStatusDate()) != null) 
                : !oldStatusDate.equals(PAUtil.dateStringToTimestamp(trialDto.getStatusDate()));
        boolean reasonChanged = (oldReason == null) ? !(trialDto.getReason().equals(""))
                : !trialDto.getReason().equals(oldReason);
        boolean startDateChanged = (oldStartTimestamp == null) 
                ? (PAUtil.dateStringToTimestamp(trialDto.getStartDate()) != null) 
                : !oldStartTimestamp.equals(PAUtil.dateStringToTimestamp(trialDto.getStartDate()));
        boolean completionDateChanged = (oldCompletionTimestamp == null) 
                ? (PAUtil.dateStringToTimestamp(trialDto.getCompletionDate()) != null) 
                : !oldCompletionTimestamp.equals(PAUtil.dateStringToTimestamp(trialDto.getCompletionDate()));
        boolean startDateTypeChanged =  (oldStartDateType == null) ? (trialDto.getStartDateType() != null) 
                : !oldStartDateType.equals(trialDto.getStartDateType());
        boolean completionDateTypeChanged = (oldCompletionDateType == null)  
                ? (trialDto.getCompletionDateType() != null) 
                        : !oldCompletionDateType.equals(trialDto.getCompletionDateType());
        
        if (!codeChanged && !statusDateChanged && !reasonChanged && !startDateChanged 
                && !completionDateChanged && !startDateTypeChanged && !completionDateTypeChanged) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @param trialDto dto
     * @return b
     * @throws PAException ex
     */
    @SuppressWarnings({"PMD.AvoidDeeplyNestedIfStmts", "PMD.ExcessiveMethodLength" })
    public Collection<String> enforceBusinessRulesForDates(TrialDTO trialDto) throws PAException {
        Collection<String> addActionError = new HashSet<String>();
        StudyStatusCode newCode = StudyStatusCode.getByCode(trialDto.getStatusCode());
        Timestamp newStatusTimestamp = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
        StudyOverallStatusWebDTO  dto = getStatusDTO(trialDto.getIdentifier());
        StudyStatusCode oldStatusCode = StudyStatusCode.getByCode(dto.getStatusCode());
        //boolean codeChanged = (newCode == null) ? (oldStatusCode != null) : !newCode.equals(oldStatusCode);
        if (oldStatusCode != null && !oldStatusCode.canTransitionTo(newCode)) {
            addActionError.add("Illegal study status transition from '" + oldStatusCode.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        if ((trialDto.getStartDateType() != null) && (trialDto.getCompletionDateType() != null)) {
            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.ACTIVE.equals(newCode)) {
                if (!PAUtil.dateStringToTimestamp(trialDto.getStartDate()).equals(newStatusTimestamp)) {
                    addActionError.add("When transitioning from 'Approved' to 'Active' the trial start "
                            + "date must be the same as the status date.");
                }
                if (!trialDto.getStartDateType().equals(actualString)) {
                    addActionError.add("When transitioning from 'Approved' to 'Active' "
                            + "the trial start date must be 'Actual'.");
                }
            }
            if (!StudyStatusCode.APPROVED.equals(newCode) && !StudyStatusCode.WITHDRAWN.equals(newCode)
                    && trialDto.getStartDateType().equals(anticipatedString)) {
                addActionError.add("Trial start date can be 'Anticipated' only if the status is "
                            + "'Approved' or 'Withdrawn'.");
            }
            if (StudyStatusCode.APPROVED.equals(oldStatusCode) && StudyStatusCode.WITHDRAWN.equals(newCode)
                    && trialDto.getStartDateType().equals(actualString)) {
                addActionError.add("Trial Start date type should be ‘Anticipated’ and Trial Start date "
                            + "should be future date if Trial Status is changed from ‘Approved’ to ‘Withdrawn’.  ");
            }
            if (StudyStatusCode.COMPLETE.equals(newCode) || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(newCode)) {
                if (trialDto.getCompletionDateType().equals(anticipatedString)) {
                    addActionError.add("Primary Completion Date cannot be 'Anticipated' when "
                            + "Current Trial Status is '" + newCode.getCode() + "'.");
                }
                if (!newStatusTimestamp.equals(PAUtil.dateStringToTimestamp(trialDto.getCompletionDate()))) {
                    addActionError.add("Current Trial Status Date and Primary Completion Date must be the same when "
                            + "Current Trial Status is '" + newCode.getCode() + "'.");
                }
            } else {
                if (!trialDto.getCompletionDateType().equals(anticipatedString)) {
                    addActionError.add("Trial completion date must be 'Anticipated' when the status is "
                            + "not 'Complete' or 'Administratively Complete'.");
                }
            }
           // Constraint/Rule: 22 If Current Trial Status is ‘Approved’, Trial Start Date must have ‘anticipated’ type. 
           //  Trial Start Date must have ‘actual’ type for any other Current Trial Status value besides ‘Approved’. 
           if (PAUtil.isNotEmpty(trialDto.getStatusCode()) && PAUtil.isNotEmpty(trialDto.getStartDateType())) {
              if (TrialStatusCode.APPROVED.getCode().equals(trialDto.getStatusCode())) {
                  if (!trialDto.getStartDateType().equals(
                                  ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                      addActionError.add(getText("error.submit.invalidStartDateTypeApproved"));
                  }                
                  } else {
                      if (!trialDto.getStartDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                          addActionError.add(getText("error.submit.invalidStartDateTypeOther"));
                      }              
                  }
           }
           // Constraint/Rule: 18 Current Trial Status Date must be current or past.
           if (PAUtil.isNotEmpty(trialDto.getStatusDate())) {
               Timestamp statusDate = PAUtil.dateStringToTimestamp(trialDto.getStatusDate());
               Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
               if (currentTimeStamp.before(statusDate)) {
                   addActionError.add(getText("error.submit.invalidStatusDate"));                
               }
           }        

        }
        return addActionError;
    }

    /**
     * 
     * @param id id
     * @return dto
     * @throws PAException ex
     */
    private StudyOverallStatusWebDTO getStatusDTO(String id) throws PAException {
        StudyOverallStatusWebDTO webDTO = new StudyOverallStatusWebDTO();    
        List<StudyOverallStatusDTO> sosList = RegistryServiceLocator.getStudyOverallStatusService()
        .getCurrentByStudyProtocol(IiConverter.convertToIi(id));
        if (!sosList.isEmpty()) {
            webDTO.setStatusCode(CdConverter.convertCdToString(sosList.get(0).getStatusCode()));
            webDTO.setStatusDate(TsConverter.convertToString(sosList.get(0).getStatusDate()));
            webDTO.setReason(StConverter.convertToString(sosList.get(0).getReasonText()));
        }
        return webDTO;
    }
    /**
     * 
     * @param fileName
     * @param file
     * @param errorField
     * @param errorMsg
     */
    public static void removeSessionAttributes() {
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
        ServletActionContext.getRequest().getSession().removeAttribute("grantList");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadOrg");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadPI");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("Sponsorselected");
        ServletActionContext.getRequest().getSession().removeAttribute("PoResponsibleContact");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSummary4Sponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("trialDTO");
        ServletActionContext.getRequest().getSession().removeAttribute("spidfromviewresults");
    }
    /**
     * 
     * @param tDTO dto
     */
    public static void addSessionAttributes(TrialDTO tDTO) {
        if (tDTO == null) {
            return;
        }
        if (!tDTO.getIndIdeDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    tDTO.getIndIdeDtos());
        }
        if (!tDTO.getFundingDtos().isEmpty()) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    tDTO.getFundingDtos());
        }
        List<DocumentDTO> documentISOList;
        try {
            documentISOList = RegistryServiceLocator.getDocumentService()
            .getDocumentsByStudyProtocol(IiConverter.convertToIi(tDTO.getIdentifier()));
            if (!(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", 
                    IiConverter.convertToIi(tDTO.getIdentifier()));
        } catch (PAException e) {
            LOG.error("exception in edit" + e.getMessage());
        }
    }
}
