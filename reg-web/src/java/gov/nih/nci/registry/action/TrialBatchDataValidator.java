package gov.nih.nci.registry.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.StringTokenizer;

import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.util.BatchConstants;
import gov.nih.nci.registry.util.RegistryUtil;



/**
 * 
 * @author Vrushali
 * This class is used to validate the spread sheet data.
 */
@SuppressWarnings("PMD")
public class TrialBatchDataValidator {

/**
 * 
 * validate the submit trial form elements.
 * @param batchDto dto
 * @return str
 */
    public String validateForm(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isEmpty(batchDto.getLocalProtocolIdentifier())) {
            fieldErr.append("Lead Organization Trial Identifier is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getTitle())) {
            fieldErr.append("Trial Title is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getPhase())) {
            fieldErr.append("Trail Phase is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getCurrentTrialStatus())) {
            fieldErr.append("Current Trial Status is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getTrialType())) {
            fieldErr.append("Trial Type is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getPrimaryPurpose())) {
            fieldErr.append("Trial Purpose is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getCurrentTrialStatusDate())) {
            fieldErr.append("Current Trial Status Date is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getStudyStartDate())) {
            fieldErr.append("Study Start Date is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getStudyStartDateType())) {
            fieldErr.append("Study Start Date Type is required.  \n");
        }
        if (PAUtil.isEmpty(batchDto.getPrimaryCompletionDate())) {
            fieldErr.append("Primary Completion Date is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getPrimaryCompletionDateType())) {
            fieldErr.append("Primary Completion Date Type is required.  \n");
        }
        if (PAUtil.isEmpty(batchDto.getProtcolDocumentFileName())) {
            fieldErr.append("Protocol Document is required. \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getProtcolDocumentFileName())) {
            if (!isValidFileType(batchDto.getProtcolDocumentFileName())) {
                fieldErr.append("Protocol Document - File type is not allowed.\n");                
            }
        }
        if (PAUtil.isEmpty(batchDto.getIrbApprovalDocumentFileName())) {
            fieldErr.append("IRB Approval Document is required. \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getIrbApprovalDocumentFileName())) {
            if (!isValidFileType(batchDto.getIrbApprovalDocumentFileName())) {
                fieldErr.append("IRB Approval Document - File type is not allowed. \n");                
            }
        }        
        if (PAUtil.isNotEmpty(batchDto.getParticipatinSiteDocumentFileName())) {
            if (!isValidFileType(batchDto.getParticipatinSiteDocumentFileName())) {
                fieldErr.append("Participating Site Document - File type is not allowed \n");                
            }
        }        
        if (PAUtil.isNotEmpty(batchDto.getInformedConsentDocumentFileName())) {
            if (!isValidFileType(batchDto.getInformedConsentDocumentFileName())) {
                fieldErr.append("Informed Consent Document - File type is not allowed \n");                
            }
        }        
        if (PAUtil.isNotEmpty(batchDto.getOtherTrialRelDocumentFileName())) {
            if (!isValidFileType(batchDto.getOtherTrialRelDocumentFileName())) {
                fieldErr.append("Other Trial Related Document - File type is not allowed \n");                
            }
        }
        //Lead org validation
        if (PAUtil.isEmpty(batchDto.getLeadOrgName())) {
            fieldErr.append("Lead Organization Name is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getLeadOrgStreetAddress())) {
            fieldErr.append("Lead Organization Street Address is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getLeadOrgCity())) {
            fieldErr.append("Lead Organization City is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getLeadOrgState())) {
            fieldErr.append("Lead Organization State is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getLeadOrgZip())) {
            fieldErr.append("Lead Organization Zip is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getLeadOrgCountry())) {
            fieldErr.append("Lead Organization Country is required.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getLeadOrgCountry())) {
                if (batchDto.getLeadOrgCountry().length() != BatchConstants.COUNTRY_SIZE) {
                    fieldErr.append("Lead Organization Country Code is not ISO.\n");
            }
        }
        if (PAUtil.isEmpty(batchDto.getLeadOrgEmail())) {
            fieldErr.append("Lead Organization Email is required. \n");
        }
        //check if the contact e-mail address is valid
        if (PAUtil.isNotEmpty(batchDto.getLeadOrgEmail())) {
            if (!RegistryUtil.isValidEmailAddress(batchDto.getLeadOrgEmail())) {
                fieldErr.append("Lead Organization Email Address is invalid \n");               
            }
        }
        //Sponsor validation
        fieldErr.append(validateSponsorInfo(batchDto));
        //Check PI values
        fieldErr.append(validatePIInfo(batchDto).toString());    
        if (PAUtil.isNotEmpty(batchDto.getPhase())) {
            if (PhaseCode.OTHER.getCode().equals(batchDto.getPhase())
                && PAUtil.isEmpty(batchDto.getPhaseOtherValueSp())) {
                fieldErr.append("Comment for Phase is required.\n");
            }            
        }
        if (PAUtil.isNotEmpty(batchDto.getPhase())) {
            if (PrimaryPurposeCode.OTHER.getCode().equals(batchDto.getPrimaryPurpose())
                && PAUtil.isEmpty(batchDto.getPrimaryPurposeOtherValueSp())) {
                fieldErr.append("comment for Purpose is required.\n");
            }
        }
        // validate trial status and dates
        fieldErr.append(validateTrialDates(batchDto));
        //validate grant 
        if (!containsReqGrantInfo(batchDto.getNihGrantFundingMechanism(), batchDto.getNihGrantSrNumber(), 
                batchDto.getNihGrantInstituteCode(), batchDto.getNihGrantNCIDivisionCode())) {
            fieldErr.append("All Grant values are required.\n");
        }
        return fieldErr.toString();
    }
    private StringBuffer validateSponsorInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isEmpty(batchDto.getSponsorOrgName())) {
            fieldErr.append("Sponsor Organization Name is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getSponsorStreetAddress())) {
            fieldErr.append("Sponsor Street Address is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getSponsorCity())) {
            fieldErr.append("Sponsor City is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getSponsorState())) {
            fieldErr.append("Sponsor State is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getSponsorZip())) {
            fieldErr.append("Sponsor Zip is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getSponsorCountry())) {
            fieldErr.append("Sponsor Country is required. \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getSponsorCountry())) {
                if (batchDto.getSponsorCountry().length() != BatchConstants.COUNTRY_SIZE) {
                    fieldErr.append("Sponsor Country Code is not ISO.\n");
            }
        }
        if (PAUtil.isEmpty(batchDto.getSponsorEmail())) {
            fieldErr.append("Sponsor Email is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getResponsibleParty())) {
            fieldErr.append("Responsible Party Not Provided \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getResponsibleParty()) 
                && batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
            //check Sponsor contact info is provided or not
            if (PAUtil.isEmpty(batchDto.getSponsorContactFName())) {
                fieldErr.append("Sponsor Contact First Name is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactLName())) {
                fieldErr.append("Sponsor Contact Last Name is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactStreetAddress())) {
                fieldErr.append("Sponsor Contact Street Address is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactCity())) {
                fieldErr.append("Sponsor Contact City is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactState())) {
                fieldErr.append("Sponsor Contact State is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactCountry())) {
                fieldErr.append("Sponsor Contact Country is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactZip())) {
                fieldErr.append("Sponsor Contact Zip is Not Provided \n");
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactEmail())) {
                fieldErr.append("Sponsor Contact Email Address is Not Provided \n");
            }
            //check if the contact e-mail address is valid
            if (PAUtil.isNotEmpty(batchDto.getSponsorContactEmail())) {
                if (!RegistryUtil.isValidEmailAddress(batchDto.getSponsorContactEmail())) {
                    fieldErr.append("Sponsor Contact Email Address is invalid \n");               
                }
            }
            if (PAUtil.isEmpty(batchDto.getSponsorContactPhone())) {
                fieldErr.append("Sponsor Contact Phone is Not Provided \n");
            }
        }
    return fieldErr;
}
    private StringBuffer validatePIInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer(); 
        if (PAUtil.isEmpty(batchDto.getPiFirstName())) {
            fieldErr.append("Principal Investigator First Name is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getPiLastName())) {
            fieldErr.append("Principal Investigator  Last Name is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getPiStreetAddress())) {
            fieldErr.append("Principal Investigator Street Address is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getPiCity())) {
            fieldErr.append("Principal Investigator City is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getPiState())) {
            fieldErr.append("Principal Investigator State is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getPiCountry())) {
            fieldErr.append("Principal Investigator Country is Not Provided \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getPiCountry())) {
            if (batchDto.getPiCountry().length() != BatchConstants.COUNTRY_SIZE) {
                fieldErr.append("Principal Investigator Country Code is not ISO \n");
            }
        }
        if (PAUtil.isEmpty(batchDto.getPiZip())) {
            fieldErr.append("Principal Investigator Zip is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getPiEmail())) {
            fieldErr.append("Principal Investigator Email Address is Not Provided \n");
        }
//      check if the contact e-mail address is valid
        if (PAUtil.isNotEmpty(batchDto.getPiEmail())) {
            if (!RegistryUtil.isValidEmailAddress(batchDto.getPiEmail())) {
                fieldErr.append("Principal Investigator Email Address is invalid \n");               
            }
        }
        if (PAUtil.isEmpty(batchDto.getPiPhone())) {
            fieldErr.append("Principal Investigator Phone is Not Provided \n");
        }

    return fieldErr;
}
    /**
     * validate the submit trial dates.
     */
    private String validateTrialDates(StudyProtocolBatchDTO dto) {
        StringBuffer errors = new StringBuffer();
        // Constraint/Rule: 18 Current Trial Status Date must be current or past.
        if (PAUtil.isNotEmpty(dto.getCurrentTrialStatusDate())) {
            Timestamp statusDate = PAUtil.dateStringToTimestamp(dto.getCurrentTrialStatusDate());
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(statusDate)) {
                errors.append("Current Trial Status Date cannot be in the future.\n");                
            }
        }        
        // Constraint/Rule:  21 If Current Trial Status is ‘Active’, Trial Start Date must be the same as 
        // Current Trial Status Date and have ‘actual’ type.
        if (PAUtil.isNotEmpty(dto.getCurrentTrialStatus())
                && PAUtil.isNotEmpty(dto.getCurrentTrialStatusDate())
                        && PAUtil.isNotEmpty(dto.getStudyStartDate())
                         && PAUtil.isNotEmpty(dto.getStudyStartDateType())) {
          if (TrialStatusCode.ACTIVE.getCode().equals(
                          dto.getCurrentTrialStatus())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(dto.getCurrentTrialStatusDate());
              Timestamp trialStartDate = PAUtil.dateStringToTimestamp(dto.getStudyStartDate());
              if (!statusDate.equals(trialStartDate) 
                              || !dto.getStudyStartDateType().equals(
                                  ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  errors
                        .append("If Current Trial Status is Active, Trial Start Date must be Actual ");
                    errors.append(" and same as Current Trial Status Date.\n");
              }                
          }            
        }        
        // Constraint/Rule: 22 If Current Trial Status is ‘Approved’, Trial Start Date must have ‘anticipated’ type. 
        //  Trial Start Date must have ‘actual’ type for any other Current Trial Status value besides ‘Approved’. 
        if (PAUtil.isNotEmpty(dto.getCurrentTrialStatus())
                         && PAUtil.isNotEmpty(dto.getStudyStartDateType())) {
          if (TrialStatusCode.APPROVED.getCode().equals(
                          dto.getCurrentTrialStatus())) {
              if (!dto.getStudyStartDateType().equals(
                              ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  errors.append("If Current Trial Status is Approved, Trial Start Date must be Anticipated.\n");
              }                
          } else {
              if (!dto.getStudyStartDateType().equals(
                       ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  errors.append("Trial Start Date must be Actual for any Current Trial Status besides Approved.\n");
              }              
          }
        }        
        // Constraint/Rule: 23 If Current Trial Status is ‘Completed’, Primary Completion Date must be the 
        // same as Current Trial Status Date and have ‘actual’ type.
        if (PAUtil.isNotEmpty(dto.getCurrentTrialStatus())
                && PAUtil.isNotEmpty(dto.getCurrentTrialStatusDate())
                        && PAUtil.isNotEmpty(dto.getPrimaryCompletionDate())
                         && PAUtil.isNotEmpty(dto.getPrimaryCompletionDateType())) {
          if (TrialStatusCode.COMPLETE.getCode().equals(
                          dto.getCurrentTrialStatus())) {
              Timestamp statusDate = PAUtil.dateStringToTimestamp(dto.getCurrentTrialStatusDate());
              Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(dto.getPrimaryCompletionDate());
              if (!statusDate.equals(trialCompletionDate) 
                      ||  !dto.getPrimaryCompletionDateType().equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  errors.append("If Current Trial Status is Completed, Primary Completion Date must be Actual ");
                  errors.append(" and same as Current Trial Status Date\n");
              }                
          }            
        }        
        // Constraint/Rule: 24 If Current Trial Status is ‘Completed’ or ‘Administratively Completed’, 
        // Primary Completion Date must have ‘actual’ type. Primary Completion Date must have ‘anticipated’ type 
        // for any other Current Trial Status value besides ‘Completed’ or ‘Administratively Completed’. 
        if (PAUtil.isNotEmpty(dto.getCurrentTrialStatus())
                && PAUtil.isNotEmpty(dto.getPrimaryCompletionDateType())) {
          if (TrialStatusCode.COMPLETE.getCode().equals(dto.getCurrentTrialStatus()) 
                  || TrialStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().
                          equals(dto.getCurrentTrialStatus())) {
              if (!dto.getPrimaryCompletionDateType().equals(
                          ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                  errors.append("If Current Trial Status is Complete or Administratively Complete, ");
                  errors.append(" Primary Completion Date must be  Actual.\n");
              }
             
          } else {
              
              if (PAUtil.isNotEmpty(dto.getPrimaryCompletionDateType())
                      && !dto.getPrimaryCompletionDateType().equals(
                              ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                  errors.append("Primary Completion Date  must be Anticipated for any other Current Trial ");
                  errors.append("Status value besides Complete or Administratively Complete.\n");                  
              }
              
          }          
        }        
        // Constraint/Rule:25 Trial Start Date must be same/smaller than Primary Completion Date. 
        if (PAUtil.isNotEmpty(dto.getStudyStartDate())
                        && PAUtil.isNotEmpty(dto.getPrimaryCompletionDate())) {
            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(dto.getStudyStartDate());
            Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(dto.getPrimaryCompletionDate());
            if (trialCompletionDate.before(trialStartDate)) {
                errors.append("Trial Start Date must be same or earlier ");
                errors.append(" than Primary Completion Date.\n");                
            }
        }        
        // Constraint/Rule: 19 Trial Start Date must be current/past if ‘actual’ trial start date type 
        // is selected and must be future if ‘anticipated’ trial start date type is selected.
        if (PAUtil.isNotEmpty(dto.getStudyStartDate())
                         && PAUtil.isNotEmpty(dto.getStudyStartDateType())) {
            if (dto.getStudyStartDateType().equals(
                    ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                Timestamp trialStartDate = PAUtil.dateStringToTimestamp(dto.getStudyStartDate());
                Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.before(trialStartDate)) {
                    errors.append("Actual Trial Start Date must be current or in past. \n");                
                }
                
            } else if (dto.getStudyStartDateType().equals(
                        ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                            Timestamp trialStartDate = PAUtil.dateStringToTimestamp(
                                    dto.getStudyStartDate());
                            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                            if (currentTimeStamp.after(trialStartDate)) {
                                errors.append("Anticipated Start Date must be in future. \n");                
                            }
            }          
        }        
        // Constraint/Rule: 20 Primary Completion Date must be current/past if ‘actual’ 
        // primary completion date type is selected and must be future if ‘anticipated’ 
        // trial primary completion date type is selected.
        if (PAUtil.isNotEmpty(dto.getPrimaryCompletionDate())
                         && PAUtil.isNotEmpty(dto.getPrimaryCompletionDateType())) {
            if (dto.getPrimaryCompletionDateType().equals(
                    ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                        Timestamp completionDate = PAUtil.dateStringToTimestamp(
                                dto.getPrimaryCompletionDate());
                        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                        if (currentTimeStamp.before(completionDate)) {
                            errors.append("Actual Primary Completion Date ");
                            errors.append(" must be current or in past.\n");                
                        }
                
            } else if (dto.getPrimaryCompletionDateType().equals(
                    ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                        Timestamp completionDate = PAUtil.dateStringToTimestamp(
                                dto.getPrimaryCompletionDate());
                        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                        if (currentTimeStamp.after(completionDate)) {
                            errors.append("Anticipated Primary Completion Date must be in future. \n");                
                        }
            }          
        }        
        return errors.toString();
    }

    /**
     * check if the uploaded file type is valid.
     * @param fileName
     * @return
     */
    private boolean isValidFileType(String fileName) {
        boolean isValidFileType = false;
        String allowedUploadFileTypes =  PaEarPropertyReader.getAllowedUploadFileTypes();
        if (allowedUploadFileTypes != null) {
            int pos =  fileName.lastIndexOf(".");
            String uploadedFileType = fileName.substring(pos + 1, fileName.length());
            StringTokenizer st = new StringTokenizer(allowedUploadFileTypes, ",");        
            while (st.hasMoreTokens()) {
                String allowedFileType = st.nextToken();
                if (allowedFileType.equalsIgnoreCase(uploadedFileType)) {
                    isValidFileType = true;
                    break;
                }
            }
        }        
        return isValidFileType;

    }
    /**
     * 
     * @param fundmc
     * @param srNumber
     * @param icdCode
     * @return
     */
    private boolean containsReqGrantInfo(String fundmc, String srNumber,
            String icdCode, String divCode) {
        int nullCount = 0;
        if (null == fundmc || fundmc.trim().length() < 1) {
            nullCount += 1;
        }
        if (null == srNumber || srNumber.trim().length() <  1) {
            nullCount += 1;
        }
        if (null == icdCode || icdCode.trim().length() < 1) {
            nullCount += 1;
        }
        if (nullCount == BatchConstants.GRANT_FIELDS) {
            return true;
        } 
        if (nullCount == 0) {
            return true;
        }
        return false;
        
    }

}
