package gov.nih.nci.registry.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.OrganizationBatchDTO;
import gov.nih.nci.registry.dto.PersonBatchDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.util.BatchConstants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;

/**
 * 
 * @author Vrushali
 * This class is used to validate the spread sheet data.
 */
@SuppressWarnings("PMD")
public class TrialBatchDataValidator {
    private static final int IND_FIELD_COUNT = 5;
    private static Logger log = Logger.getLogger(TrialBatchDataValidator.class);
    private static List<String> countryList = null;
    private final int serialNumMin = 5;
    private final int serialNumMax = 6;

/**
 * 
 * validate the submit trial form elements.
 * @param batchDto dto
 * @return str
 */
    public String validateForm(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isEmpty(batchDto.getTrialType())) {
            fieldErr.append("Trial Type is required.\n");
        } else if (batchDto.getTrialType().equalsIgnoreCase("Observational")) {
            fieldErr.append("Observational Trial not supported.\n");
            return fieldErr.toString();
        }
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
            if (!RegistryUtil.isValidFileType((batchDto.getProtcolDocumentFileName()))) {
                fieldErr.append("Protocol Document - File type is not allowed.\n");                
            }
        }
        if (PAUtil.isEmpty(batchDto.getIrbApprovalDocumentFileName())) {
            fieldErr.append("IRB Approval Document is required. \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getIrbApprovalDocumentFileName())) {
            if (!RegistryUtil.isValidFileType(batchDto.getIrbApprovalDocumentFileName())) {
                fieldErr.append("IRB Approval Document - File type is not allowed. \n");                
            }
        }        
        if (PAUtil.isNotEmpty(batchDto.getParticipatinSiteDocumentFileName())) {
            if (!RegistryUtil.isValidFileType(batchDto.getParticipatinSiteDocumentFileName())) {
                fieldErr.append("Participating Site Document - File type is not allowed \n");                
            }
        }        
        if (PAUtil.isNotEmpty(batchDto.getInformedConsentDocumentFileName())) {
            if (!RegistryUtil.isValidFileType(batchDto.getInformedConsentDocumentFileName())) {
                fieldErr.append("Informed Consent Document - File type is not allowed \n");                
            }
        }        
        if (PAUtil.isNotEmpty(batchDto.getOtherTrialRelDocumentFileName())) {
            if (!RegistryUtil.isValidFileType(batchDto.getOtherTrialRelDocumentFileName())) {
                fieldErr.append("Other Trial Related Document - File type is not allowed \n");                
            }
        }
        //load the country
        if (null == countryList) {
            getCountryList();   
        }
        //Lead org validation
        OrganizationBatchDTO leadOrgDto = buildLeadOrgDto(batchDto);
        fieldErr.append(validateOrgInfo(leadOrgDto, "Lead Organization's "));
        //Sponsor validation
        fieldErr.append(validateSponsorInfo(batchDto));
        //Check PI values
        PersonBatchDTO piBatchDto = buildLeadPIDto(batchDto);
        fieldErr.append(validatePersonInfo(piBatchDto, "Principal Investigator's").toString());
        //Summary 4 Info validation
        //TO-DO will enable later - fieldErr.append(validateSummary4SponsorInfo(batchDto));
        
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
        //validate the IND/IDE
        if (!isIndIdeContainsAllInfo(batchDto)) {
            fieldErr.append("All IND/IDE values are required.\n");
        } else {
            if (PAUtil.isNotEmpty(batchDto.getIndHasExpandedAccess()) 
                    && batchDto.getIndHasExpandedAccess().equalsIgnoreCase("True")
                    && PAUtil.isEmpty(batchDto.getIndExpandedAccessStatus())) {
                fieldErr.append("Expanded Access Status value is required.\n");
            }
            if (PAUtil.isNotEmpty(batchDto.getIndHolderType())
                    && batchDto.getIndHolderType().equalsIgnoreCase("NIH")
                    && PAUtil.isEmpty(batchDto.getIndNIHInstitution())) {
                fieldErr.append("NIH Institution value is required.\n");
            }
            if (PAUtil.isNotEmpty(batchDto.getIndHolderType())
                    && batchDto.getIndHolderType().equalsIgnoreCase("NCI")
                    && PAUtil.isEmpty(batchDto.getIndNCIDivision())) {
                fieldErr.append("NCI Division/Program Code value is required.\n");
            }
        }
        //validate the ValidValues
        fieldErr.append(validateListOfValues(batchDto));
        return fieldErr.toString();
    }
    private StringBuffer validateListOfValues(StudyProtocolBatchDTO batchDto) {
/*        //make sure values are in Title case for Trial Type , Primary Purpose, Current trial status
        //Study Start type, primary completion type
        batchDto.setTrialType(convertToEnumCode(batchDto.getTrialType()));
        batchDto.setPrimaryPurpose(convertToEnumCode(batchDto.getPrimaryPurpose()));
        batchDto.setCurrentTrialStatus(convertToEnumCode(batchDto.getCurrentTrialStatus()));
        batchDto.setStudyStartDateType(convertToEnumCode(batchDto.getStudyStartDateType()));
        batchDto.setPrimaryCompletionDateType(convertToEnumCode(batchDto.getPrimaryCompletionDateType()));
*/        //make sure case is upper for IND/IDE Type  IND/IDE Grantor IND/IDE Holder Type
        //[NIH Grant] NCI Division/Program Code [NIH Grant] Institute Code
        //Country code, State code

        StringBuffer fieldErr = new StringBuffer();
        if (null == StudyStatusCode.getByCode(batchDto.getCurrentTrialStatus())) {
            fieldErr.append("Please enter valid value for Current Trial Status");
        }
        if (PAUtil.isNotEmpty(batchDto.getResponsibleParty())) {
            if (!batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")
                    && !batchDto.getResponsibleParty().equalsIgnoreCase("PI")) {
                fieldErr.append("Please enter valid value for Responsible Party.");
            }
        }
        //check the trial type it shld be either Interventional or Observational
        if (null == StudyTypeCode.getByCode(batchDto.getTrialType())) {
            fieldErr.append("Please enter valid value for Trial Type.");
        }
        //check Primary Purpose
        if (null == PrimaryPurposeCode.getByCode(batchDto.getPrimaryPurpose())) {
            fieldErr.append("Please enter valid value for Primary Purpose.");
        }
        if (null == ActualAnticipatedTypeCode.getByCode(batchDto.getPrimaryCompletionDateType())) {
            fieldErr.append("Please enter valid value for Primary Completion Date Type.");
        }
        if (null == ActualAnticipatedTypeCode.getByCode(batchDto.getStudyStartDateType())) {
            fieldErr.append("Please enter valid value for Study Start Date Type.");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndType()) 
                && null == IndldeTypeCode.getByCode(batchDto.getIndType())) {
            fieldErr.append("Please enter valid value for IND/IDE.");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndHolderType()) 
                && null == HolderTypeCode.getByCode(batchDto.getIndHolderType())) {
            fieldErr.append("Please enter valid value for IND/IDE Holder Type.");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndGrantor()) 
                && null == GrantorCode.getByCode(batchDto.getIndGrantor())) {
            fieldErr.append("Please enter valid value for IND/IDE Grantor.");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndType()) 
                && batchDto.getIndType().equals("IDE")
                && !batchDto.getIndGrantor().equals("CDRH")) {
            fieldErr.append("IDE Grantor can have only CDRH value.");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndExpandedAccessStatus())
                && null == ExpandedAccessStatusCode.getByCode(batchDto.getIndExpandedAccessStatus())) {
            fieldErr.append("Please enter valid value for IND/IDE Expanded Access Status.");
        }
        if (PAUtil.isNotEmpty(batchDto.getNihGrantSrNumber())
                && (batchDto.getNihGrantSrNumber().length() < serialNumMin 
                        || batchDto.getNihGrantSrNumber().length() > serialNumMax)) {
            fieldErr.append("Serial number can be numeric with 5 or 6 digits");
        }
    return fieldErr;
}
    private StringBuffer validateSummary4SponsorInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isNotEmpty(batchDto.getTrialType()) 
                && batchDto.getTrialType().equalsIgnoreCase("Interventional")) {
            OrganizationBatchDTO summ4Sponsor = buildSummary4Sponsor(batchDto);
            fieldErr.append(validateOrgInfo(summ4Sponsor, "Summary 4 Funding Sponsor/Source's "));
        }
    return fieldErr;
}
    private StringBuffer validateSponsorInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        OrganizationBatchDTO dto = buildSponsorOrgDto(batchDto);
        fieldErr.append(validateOrgInfo(dto, "Sponsor Organization's"));
        if (PAUtil.isEmpty(batchDto.getResponsibleParty())) {
            fieldErr.append("Responsible Party Not Provided \n");
        }
        if (PAUtil.isNotEmpty(batchDto.getResponsibleParty()) 
                && batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
            //check Sponsor contact info is provided or not
            PersonBatchDTO sponsorContact = buildSponsorContact(batchDto);
            fieldErr.append(validatePersonInfo(sponsorContact, "Sponsor Contact's"));
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
        if (null == divCode || divCode.trim().length() < 1) {
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
    /**
     * 
     * @param dto dto
     * @return orgDto
     */
    public OrganizationBatchDTO buildLeadOrgDto(StudyProtocolBatchDTO dto) {
        OrganizationBatchDTO orgDto = new OrganizationBatchDTO();
        orgDto.setName(dto.getLeadOrgName());
        orgDto.setOrgCTEPId(dto.getLeadOrgCTEPOrgNo());
        orgDto.setStreetAddress(dto.getLeadOrgStreetAddress());
        orgDto.setCity(dto.getLeadOrgCity());
        orgDto.setState(dto.getLeadOrgState());
        orgDto.setZip(dto.getLeadOrgZip());
        orgDto.setCountry(dto.getLeadOrgCountry());
        orgDto.setEmail(dto.getLeadOrgEmail());
        orgDto.setPhone(dto.getLeadOrgPhone());
        orgDto.setTty(dto.getLeadOrgTTY());
        orgDto.setFax(dto.getLeadOrgFax());
        orgDto.setUrl(dto.getLeadOrgUrl());
        orgDto.setType(dto.getLeadOrgType());
        return orgDto;
    }
    /**
     * 
     * @param dto dto
     * @return dto
     */
        public PersonBatchDTO buildLeadPIDto(StudyProtocolBatchDTO dto) {
        PersonBatchDTO personDto = new PersonBatchDTO();
        personDto.setFirstName(dto.getPiFirstName());
        personDto.setMiddleName(dto.getPiMiddleName());
        personDto.setLastName(dto.getPiLastName());
        personDto.setPersonCTEPId(dto.getPiPersonCTEPPersonNo());
        personDto.setStreetAddress(dto.getPiStreetAddress());
        personDto.setCity(dto.getPiCity());
        personDto.setState(dto.getPiState());
        personDto.setZip(dto.getPiZip());
        personDto.setCountry(dto.getPiCountry());
        personDto.setEmail(dto.getPiEmail());
        personDto.setPhone(dto.getPiPhone());
        personDto.setTty(dto.getPiTTY());
        personDto.setFax(dto.getPiFax());
        personDto.setUrl(dto.getPiUrl());
        return personDto;
    }
    /**
     * 
     * @param dto dto
     * @return SponsorDto
     */
    public OrganizationBatchDTO buildSponsorOrgDto(StudyProtocolBatchDTO dto) {
        OrganizationBatchDTO sponsorDto = new OrganizationBatchDTO();
        sponsorDto.setName(dto.getSponsorOrgName());
        sponsorDto.setOrgCTEPId(dto.getSponsorCTEPOrgNumber());
        sponsorDto.setStreetAddress(dto.getSponsorStreetAddress());
        sponsorDto.setCity(dto.getSponsorCity());
        sponsorDto.setState(dto.getSponsorState());
        sponsorDto.setZip(dto.getSponsorZip());
        sponsorDto.setCountry(dto.getSponsorCountry());
        sponsorDto.setEmail(dto.getSponsorEmail());
        sponsorDto.setPhone(dto.getSponsorPhone());
        sponsorDto.setTty(dto.getSponsorTTY());
        sponsorDto.setFax(dto.getSponsorFax());
        sponsorDto.setUrl(dto.getSponsorURL());
        return sponsorDto;
    }
    /**
     * 
     * @param dto dto
     * @return dto
     */
    public PersonBatchDTO buildSponsorContact(StudyProtocolBatchDTO dto) {
        PersonBatchDTO  sponsorContact = new PersonBatchDTO();
        sponsorContact.setFirstName(dto.getSponsorContactFName());
        sponsorContact.setMiddleName(dto.getSponsorContactMName());
        sponsorContact.setLastName(dto.getSponsorContactLName());
        sponsorContact.setPersonCTEPId(dto.getSponsorContactCTEPPerNo());
        sponsorContact.setStreetAddress(dto.getSponsorContactStreetAddress());
        sponsorContact.setCity(dto.getSponsorContactCity());
        sponsorContact.setState(dto.getSponsorContactState());
        sponsorContact.setZip(dto.getSponsorContactZip());
        sponsorContact.setCountry(dto.getSponsorContactCountry());
        sponsorContact.setEmail(dto.getSponsorContactEmail());
        sponsorContact.setPhone(dto.getSponsorContactPhone());
        sponsorContact.setTty(dto.getSponsorContactTTY());
        sponsorContact.setFax(dto.getSponsorContactFax());
        sponsorContact.setUrl(dto.getSponsorContactUrl());
        return sponsorContact;
    }

    /**
     * 
     * @param dto dto
     * @return ret
     */
    public OrganizationBatchDTO buildSummary4Sponsor(StudyProtocolBatchDTO dto) {
        OrganizationBatchDTO summ4Sponsor = new OrganizationBatchDTO();
        summ4Sponsor.setName(dto.getSumm4OrgName());
        summ4Sponsor.setOrgCTEPId(dto.getSumm4OrgCTEPOrgNo());
        summ4Sponsor.setStreetAddress(dto.getSumm4OrgStreetAddress());
        summ4Sponsor.setCity(dto.getSumm4City());
        summ4Sponsor.setState(dto.getSumm4State());
        summ4Sponsor.setZip(dto.getSumm4Zip());
        summ4Sponsor.setCountry(dto.getSumm4Country());
        summ4Sponsor.setEmail(dto.getSumm4Email());
        summ4Sponsor.setPhone(dto.getSumm4Phone());
        summ4Sponsor.setTty(dto.getSumm4TTY());
        summ4Sponsor.setFax(dto.getSumm4Fax());
        summ4Sponsor.setUrl(dto.getSumm4Url());
        return summ4Sponsor;
    }
    /**
     * 
     * @param dto dto
     * @return result
     */
    public boolean orgDTOIsEmpty(OrganizationBatchDTO dto) {
        int nullCount = 0;
        if (PAUtil.isEmpty(dto.getName())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getStreetAddress())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getCity())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getState())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getZip())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getCountry())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getEmail())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getPhone())) {
            nullCount += 1;
        }
        if (nullCount == 0) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @param batchDto dto
     * @param fieldName msg
     * @return
     */
    private StringBuffer validatePersonInfo(PersonBatchDTO batchDto , String fieldName) {
        StringBuffer fieldErr = new StringBuffer(); 
        if (PAUtil.isEmpty(batchDto.getFirstName())) {
            fieldErr.append(fieldName + " First Name is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getLastName())) {
            fieldErr.append(fieldName + " Last Name is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getStreetAddress())) {
            fieldErr.append(fieldName + " Street Address is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getCity())) {
            fieldErr.append(fieldName + " City is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getCountry())) {
            fieldErr.append(fieldName + " Country is required.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getCountry())) {
            if (batchDto.getCountry().length() != BatchConstants.COUNTRY_SIZE) {
                fieldErr.append(fieldName + " Country Code is not ISO \n");
            }
            if (!isCountryValid(batchDto.getCountry().toUpperCase())) {
                fieldErr.append(fieldName + " Country Code is not Valid.\n");
            }
            if (batchDto.getCountry().equalsIgnoreCase("USA")) {
                if (PAUtil.isEmpty(batchDto.getState())) {
                    fieldErr.append(fieldName + " State is required. \n");
                }
                if (PAUtil.isNotEmpty(batchDto.getState())
                        && batchDto.getState().length() > 2) {
                    fieldErr.append(" State code should be two digit. \n");
                }
            }
        }
        if (PAUtil.isEmpty(batchDto.getZip())) {
            fieldErr.append(fieldName + " Zip is Not Provided \n");
        }
        if (PAUtil.isEmpty(batchDto.getEmail())) {
            fieldErr.append(fieldName + " Email Address is required. \n");
        }
//      check if the contact e-mail address is valid
        if (PAUtil.isNotEmpty(batchDto.getEmail())) {
            if (!RegistryUtil.isValidEmailAddress(batchDto.getEmail())) {
                fieldErr.append(fieldName + " Email Address is invalid \n");               
            }
        }
        if (PAUtil.isEmpty(batchDto.getPhone())) {
            fieldErr.append(fieldName + " Phone is required. \n");
        }

    return fieldErr;
}
    private String validateOrgInfo(OrganizationBatchDTO batchDto, String message) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isEmpty(batchDto.getName())) {
            fieldErr.append(message + " Name is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getStreetAddress())) {
            fieldErr.append(message + " Street Address is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getCity())) {
            fieldErr.append(message + " City is required.\n");
        }
        if (PAUtil.isEmpty(batchDto.getZip())) {
            fieldErr.append(message + " Zip is required. \n");
        }
        if (PAUtil.isEmpty(batchDto.getCountry())) {
            fieldErr.append(message + " Country is required.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getCountry())) {
                if (batchDto.getCountry().length() != BatchConstants.COUNTRY_SIZE) {
                    fieldErr.append(message + " Country Code is not ISO.\n");
                }
                if (!isCountryValid(batchDto.getCountry().toUpperCase())) {
                    fieldErr.append(message + " Country Code is not Valid.\n");
                }
                if (batchDto.getCountry().equalsIgnoreCase("USA")) {
                    if (PAUtil.isEmpty(batchDto.getState())) {
                        fieldErr.append(message + " State is required. \n");
                    }
                }     
        }
        if (PAUtil.isEmpty(batchDto.getEmail())) {
            fieldErr.append(message + " Email is required. \n");
        }
        //check if the contact e-mail address is valid
        if (PAUtil.isNotEmpty(batchDto.getEmail())) {
            if (!RegistryUtil.isValidEmailAddress(batchDto.getEmail())) {
                fieldErr.append(message + " Email Address is invalid \n");               
            }
        }
        return fieldErr.toString();
    }
    private boolean isIndIdeContainsAllInfo(StudyProtocolBatchDTO dto) {
        int nullCount = 0;
        if (PAUtil.isEmpty(dto.getIndType())) {
            nullCount += 1;
        }
       if (PAUtil.isEmpty(dto.getIndNumber())) {
           nullCount += 1;
       }
       if (PAUtil.isEmpty(dto.getIndGrantor())) {
           nullCount += 1;
       }
       if (PAUtil.isEmpty(dto.getIndHolderType())) {
           nullCount += 1;
       }
       if (PAUtil.isEmpty(dto.getIndHasExpandedAccess())) {
           nullCount += 1;
       }
       log.info("null count in ind Ide" + nullCount);
       if (nullCount == 0) {
           return true;
       }
       if (nullCount == IND_FIELD_COUNT) {
           return true;
       }
        return false;
    }
/*    *//**
     * 
     * @param str str
     * @return str
     *//*
   public String convertToEnumCode(String str) {
       str = str.toUpperCase();
       str = str.replaceAll(" ", "_");
    log.error("convertToEnumCode:-" + str);
    return str;
   }
*/   
    /**
     * 
     * @param country
     * @return tr
     */
    private boolean isCountryValid(String country) {
        if (this.countryList.contains(country)) {
            return true;    
        } 
        return false;
    }
    /**
     * 
     *
     */
    private void getCountryList() {
        
        this.countryList = new ArrayList<String>();
        try {
            List listOfCountries = RegistryServiceLocator.getLookUpTableService().getCountries();
            Iterator iter = listOfCountries.iterator();
            while (iter.hasNext()) {
                Country countries = (Country) iter.next();
                this.countryList.add(countries.getAlpha3());
            }
        } catch (PAException e) {
            // TODO Auto-generated catch block
            log.error("error while validaing country.." + e.getMessage());
        }
    }

}
