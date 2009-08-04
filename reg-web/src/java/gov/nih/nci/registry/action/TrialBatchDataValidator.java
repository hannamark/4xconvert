package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.OrganizationBatchDTO;
import gov.nih.nci.registry.dto.PersonBatchDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.enums.TrialStatusReasonCode;
import gov.nih.nci.registry.util.BatchConstants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
/**
 * 
 * @author Vrushali
 * This class is used to validate the spread sheet data.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity",
    "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class TrialBatchDataValidator {
    private static final int IND_FIELD_COUNT = 5;
    private static final Logger LOG = Logger.getLogger(TrialBatchDataValidator.class);
    private static List<String> countryList = null;

    private static final int AUS_STATE_CODE_LEN = 3;
    private static final int ORG_FIELD = 8;
    
    /**
     * 
     * validate the submit trial form elements.
     * @param batchDto dto
     * @return str
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    public String validateBatchDTO(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        
        if (batchDto.getTrialType() == null || batchDto.getTrialType().equalsIgnoreCase("Observational")) {
            fieldErr.append("Observational Trial not supported.\n");
            return fieldErr.toString();
        }
        //load the country
        if (null == countryList) {
            getCountryList();   
        }
        //Validate the dto
        fieldErr.append(validate(batchDto, ""));
        //validate Docs
        fieldErr.append(validateBatchDocuments(batchDto));
        //Lead org Country and state validation
        OrganizationBatchDTO leadOrgDto = buildLeadOrgDto(batchDto);
        fieldErr.append(validateCountryAndStateInfo(leadOrgDto.getCountry(), leadOrgDto.getState(), 
                "Lead Organization's "));
        //Sponsor validation
        fieldErr.append(validateSponsorInfo(batchDto));
        //Check PI Country and state 
        PersonBatchDTO piBatchDto = buildLeadPIDto(batchDto);
        fieldErr.append(validateCountryAndStateInfo(piBatchDto.getCountry(), piBatchDto.getState(),
                "Principal Investigator's"));
        //Summary 4 Info validation
        fieldErr.append(validateSummary4SponsorInfo(batchDto));
        
        if (PAUtil.isNotEmpty(batchDto.getPhase()) && PhaseCode.OTHER.getCode().equals(batchDto.getPhase())
                && PAUtil.isEmpty(batchDto.getPhaseOtherValueSp())) {
                fieldErr.append("Comment for Phase is required.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getPrimaryPurpose()) 
                && PrimaryPurposeCode.OTHER.getCode().equals(batchDto.getPrimaryPurpose())
                && PAUtil.isEmpty(batchDto.getPrimaryPurposeOtherValueSp())) {
                fieldErr.append("comment for Purpose is required.\n");
        }
        // validate trial status and dates
        if (PAUtil.isNotEmpty(batchDto.getCurrentTrialStatus())
                && RegistryUtil.isValidDate(batchDto.getCurrentTrialStatusDate())
                && PAUtil.isNotEmpty(batchDto.getPrimaryCompletionDateType())
                && RegistryUtil.isValidDate(batchDto.getPrimaryCompletionDate())
                && PAUtil.isNotEmpty(batchDto.getStudyStartDateType())
                && RegistryUtil.isValidDate(batchDto.getStudyStartDate())) {
            fieldErr.append(validateTrialDates(batchDto));
        }
        //validate grant 
        if (!containsReqGrantInfo(batchDto.getNihGrantFundingMechanism(), batchDto.getNihGrantSrNumber(), 
                batchDto.getNihGrantInstituteCode(), batchDto.getNihGrantNCIDivisionCode())) {
            fieldErr.append("All Grant values are required.\n");
        }
      //validate the IND/IDE
        fieldErr.append(validateIndIde(batchDto));
        //validate the ValidValues for other
        fieldErr.append(validateListOfValues(batchDto));
        return fieldErr.toString();
    }
    /**
     * @param batchDto
     * @param fieldErr
     */
    private StringBuffer  validate(Object obj, String fieldName) {
        InvalidValue[] validationMessages = null;
        StringBuffer fieldErr = new StringBuffer();
        ClassValidator<Object> classValidator = new ClassValidator(obj.getClass());
        validationMessages = classValidator.getInvalidValues(obj);
        for (InvalidValue validationMessage : validationMessages) {
                    String msg = validationMessage.getMessage();
                    msg = msg.replace("(fieldName)", fieldName).trim();
                    fieldErr.append(msg);
        }
        return fieldErr;
    }
    /**
     * @param batchDto
     * @return fieldErr
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private StringBuffer validateIndIde(StudyProtocolBatchDTO batchDto) {
        //validate the IND/IDE
        StringBuffer fieldErr = new StringBuffer();
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
        //Validate List of values
        if (PAUtil.isNotEmpty(batchDto.getIndType()) 
                && null == IndldeTypeCode.getByCode(batchDto.getIndType())) {
            fieldErr.append("Please enter valid value for IND/IDE.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndHolderType()) 
                && null == HolderTypeCode.getByCode(batchDto.getIndHolderType())) {
            fieldErr.append("Please enter valid value for IND/IDE Holder Type.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndGrantor()) 
                && null == GrantorCode.getByCode(batchDto.getIndGrantor())) {
            fieldErr.append("Please enter valid value for IND/IDE Grantor.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndType()) 
                && batchDto.getIndType().equals("IDE")
                && !batchDto.getIndGrantor().equals("CDRH")) {
            fieldErr.append("IDE Grantor can have only CDRH value.\n");
        }
        if (PAUtil.isNotEmpty(batchDto.getIndExpandedAccessStatus())
                && null == ExpandedAccessStatusCode.getByCode(batchDto.getIndExpandedAccessStatus())) {
            fieldErr.append("Please enter valid value for IND/IDE Expanded Access Status.\n");
        }
        //validate NIH Institution values
        if (PAUtil.isNotEmpty(batchDto.getIndNIHInstitution()) 
                && null == NihInstituteCode.getByCode(batchDto.getIndNIHInstitution())) {
            fieldErr.append("Please enter valid value for IND/IDE NIH Institution.\n");
        }
        //validate NCI Division values
        if (PAUtil.isNotEmpty(batchDto.getIndNCIDivision()) 
                && null == NciDivisionProgramCode.getByCode(batchDto.getIndNCIDivision())) {
            fieldErr.append("Please enter valid value for IND/IDE NCI Division /Program.\n");
        }
        return fieldErr;
    }
    /**
     * @param batchDto
     * @param fieldErr
     */
    private StringBuffer validateBatchDocuments(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isNotEmpty(batchDto.getProtcolDocumentFileName())
                && !RegistryUtil.isValidFileType((batchDto.getProtcolDocumentFileName()))) {
                fieldErr.append("Protocol Document - File type is not allowed.\n");                
        }
        if (PAUtil.isNotEmpty(batchDto.getIrbApprovalDocumentFileName())
                && !RegistryUtil.isValidFileType(batchDto.getIrbApprovalDocumentFileName())) {
                fieldErr.append("IRB Approval Document - File type is not allowed. \n");                
        }        
        if (PAUtil.isNotEmpty(batchDto.getParticipatinSiteDocumentFileName())
                && !RegistryUtil.isValidFileType(batchDto.getParticipatinSiteDocumentFileName())) {
                fieldErr.append("Participating Site Document - File type is not allowed \n");                
        }        
        if (PAUtil.isNotEmpty(batchDto.getInformedConsentDocumentFileName())
                && !RegistryUtil.isValidFileType(batchDto.getInformedConsentDocumentFileName())) {
                fieldErr.append("Informed Consent Document - File type is not allowed \n");                
        }        
        if (PAUtil.isNotEmpty(batchDto.getOtherTrialRelDocumentFileName())
                && !RegistryUtil.isValidFileType(batchDto.getOtherTrialRelDocumentFileName())) {
               fieldErr.append("Other Trial Related Document - File type is not allowed \n");                
        }
     return fieldErr;
    }
    /**
     * Validates List of values.
     * @param batchDto
     * @return
     */
    private StringBuffer validateListOfValues(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (null == StudyStatusCode.getByCode(batchDto.getCurrentTrialStatus())) {
            fieldErr.append("Please enter valid value for Current Trial Status");
        }
        if (null != TrialStatusReasonCode.getByCode(batchDto.getCurrentTrialStatus())
                && PAUtil.isEmpty(batchDto.getReasonForStudyStopped())) {
                fieldErr.append("Why Study Stopped is required.");
        }
        if (PAUtil.isNotEmpty(batchDto.getResponsibleParty()) 
                && !batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")
                && !batchDto.getResponsibleParty().equalsIgnoreCase("PI")) {
                fieldErr.append("Please enter valid value for Responsible Party.");
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
        return fieldErr;
    }
    /**
     * 
     * @param batchDto
     * @return
     */
    private StringBuffer validateSummary4SponsorInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
/*        if (PAUtil.isNotEmpty(batchDto.getTrialType()) 
                && batchDto.getTrialType().equalsIgnoreCase("Interventional")) {
*/
        OrganizationBatchDTO summ4Sponsor = buildSummary4Sponsor(batchDto);
        if (!orgDTOIsEmpty(summ4Sponsor)) {
            fieldErr.append(validate(summ4Sponsor, "Summary 4 Funding Sponsor/Source's "));
            fieldErr.append(validateCountryAndStateInfo(summ4Sponsor.getCountry(), summ4Sponsor.getState(), 
                    "Summary 4 Funding Sponsor/Source's "));
        }
        return fieldErr;
    }
    /**
     * 
     * @param batchDto
     * @return
     */
    private StringBuffer validateSponsorInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        OrganizationBatchDTO dto = buildSponsorOrgDto(batchDto);
        fieldErr.append(validateCountryAndStateInfo(dto.getCountry(), dto.getState(), "Sponsor Organization's"));
        if (PAUtil.isNotEmpty(batchDto.getResponsibleParty()) 
                && batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
            //check Sponsor contact info is provided or not
            PersonBatchDTO sponsorContact = buildSponsorContact(batchDto);
            fieldErr.append(validate(sponsorContact, "Sponsor Contact's "));
            fieldErr.append(validateCountryAndStateInfo(sponsorContact.getCountry(), dto.getState(),
                    "Sponsor Contact's "));
        }
        return fieldErr;
    }

    /**
     * validate the submit trial dates.
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private String validateTrialDates(StudyProtocolBatchDTO dto) {
        StringBuffer errors = new StringBuffer();
        

        Timestamp statusDate = PAUtil.dateStringToTimestamp(dto.getCurrentTrialStatusDate());
        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
        Timestamp trialStartDate = PAUtil.dateStringToTimestamp(dto.getStudyStartDate());
        Timestamp trialCompletionDate = PAUtil.dateStringToTimestamp(dto.getPrimaryCompletionDate());      
        // Constraint/Rule: 22 Current Trial Status Date must be current or past.
        if (currentTimeStamp.before(statusDate)) {
                errors.append("Current Trial Status Date cannot be in the future.\n");                
        }
        // Constraint/Rule: 23 Trial Start Date must be current/past if 'actual' trial start date type 
        // is selected and must be future if 'anticipated' trial start date type is selected. 
        if (dto.getStudyStartDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())
                    && currentTimeStamp.before(trialStartDate)) {
            errors.append("Actual Trial Start Date must be current or in past. \n");                
        } else if (dto.getStudyStartDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())
                && currentTimeStamp.after(trialStartDate)) {
            errors.append("Anticipated Start Date must be in future. \n");                
        }          
        //Constraint/Rule:24 Primary Completion Date must be current/past if 'actual' primary completion date type  
        //is selected and must be future if 'anticipated'trial primary completion date type is selected. 
        if (dto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())
                && currentTimeStamp.before(trialCompletionDate)) {
            errors.append("Actual Primary Completion Date must be current or in past.\n");                
        } else if (dto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())
                && currentTimeStamp.after(trialCompletionDate)) {
            errors.append("Anticipated Primary Completion Date must be in future. \n");                
        }          
        // Constraint/Rule: 25 If Current Trial Status is 'Active', Trial Start Date must be the same as 
        //Current Trial Status Date and have 'actual' type. New Rule added-01/15/09 if start date is smaller 
        //than the Current Trial Status Date, replace Current Trial Status date with the actual Start Date.            
        //pa2.0 as part of release removing the "replace Current Trial Status date with the actual Start Date."
        if (TrialStatusCode.ACTIVE.getCode().equals(dto.getCurrentTrialStatus())
                && (trialStartDate.after(statusDate) || !dto.getStudyStartDateType().equals(
                         ActualAnticipatedTypeCode.ACTUAL.getCode()))) {
                errors.append("If Current Trial Status is Active, Trial Start Date must be Actual "
                              + " and same as Current Trial Status Date.\n");
        }
        // Constraint/Rule: 26 If Current Trial Status is 'Approved', Trial Start Date must have 'anticipated' type. 
        //Trial Start Date must have 'actual' type for any other Current Trial Status value besides 'Approved'. 
          if (TrialStatusCode.APPROVED.getCode().equals(dto.getCurrentTrialStatus())
                  || TrialStatusCode.IN_REVIEW.getCode().equals(dto.getCurrentTrialStatus())) {
              if (!dto.getStudyStartDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                errors.append("If Current Trial Status is Approved/In Review, Trial Start Date must be Anticipated.\n");
              } 
          } else if (!dto.getStudyStartDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
            errors.append("Trial Start Date must be Actual for any Current Trial Status besides Approved/In Review.\n");
          }
          // Constraint/Rule: 27 If Current Trial Status is 'Completed', Primary Completion Date must be the 
          // same as Current Trial Status Date and have 'actual' type.
          if (TrialStatusCode.COMPLETE.getCode().equals(dto.getCurrentTrialStatus())
                  && (!dto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode()))) {
                    errors.append("If Current Trial Status is Completed, Primary Completion Date must be Actual ");
          }            
          // Constraint/Rule: 28 If Current Trial Status is 'Completed' or 'Administratively Completed', 
          // Primary Completion Date must have 'actual' type. Primary Completion Date must have 'anticipated' type 
          // for any other Current Trial Status value besides 'Completed' or 'Administratively Completed'.
          if (TrialStatusCode.COMPLETE.getCode().equals(dto.getCurrentTrialStatus()) 
              || TrialStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equals(dto.getCurrentTrialStatus())) { 
              if (!dto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ACTUAL.getCode())) {
                    errors.append("If Current Trial Status is Complete or Administratively Complete, "
                            + " Primary Completion Date must be  Actual.\n");
              }
          } else if (!dto.getPrimaryCompletionDateType().equals(ActualAnticipatedTypeCode.ANTICIPATED.getCode())) {
                        errors.append("Primary Completion Date  must be Anticipated for any other Current Trial"
                            + " Status value besides Complete or Administratively Complete.\n");                  
          }          
          // Constraint/Rule:29 Trial Start Date must be same/smaller than Primary Completion Date. 
          if (trialCompletionDate.before(trialStartDate)) {
             errors.append("Trial Start Date must be same or earlier than Primary Completion Date.\n");                
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
        boolean orgIsEmpty = false;
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
        if (nullCount == ORG_FIELD) {
            orgIsEmpty = true;
        }
        return orgIsEmpty;
    }
    /**
     * 
     * @param countryName countryName
     *  @param stateName stateName
     * @param fieldName msg
     * @return
     */
    private StringBuffer validateCountryAndStateInfo(String  countryName , String stateName, String fieldName) {
        StringBuffer fieldErr = new StringBuffer(); 
        if (PAUtil.isNotEmpty(countryName)) {
            if (!isCountryValid(countryName.toUpperCase(Locale.US))) {
                fieldErr.append(" Country Code is not Valid.\n");
            }
            if ((countryName.equalsIgnoreCase("USA") || countryName.equalsIgnoreCase("CAN")
                    || countryName.equalsIgnoreCase("AUS")) && (PAUtil.isEmpty(stateName))) {
                fieldErr.append(" State/Province is mandatory for US/Canada/Australia. \n");
            }
            if ((countryName.equalsIgnoreCase("USA") || countryName.equalsIgnoreCase("CAN"))
                    && PAUtil.isNotEmpty(stateName) && stateName.length() > 2) {
                fieldErr.append(" 2-letter State/Province Code required for USA/Canada. \n");
            }
            if (countryName.equalsIgnoreCase("AUS") && PAUtil.isNotEmpty(stateName) 
                    && stateName.length() > AUS_STATE_CODE_LEN) {
                fieldErr.append(" 2/3-letter State/Province Code required for Australia. \n");
            }
        }
        if (fieldErr.length() > 0) {
            fieldErr.insert(0, fieldName);
        }
        return fieldErr;
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
       LOG.info("null count in ind Ide" + nullCount);
       if (nullCount == 0) {
           return true;
       }
       if (nullCount == IND_FIELD_COUNT) {
           return true;
       }
        return false;
    }
    /**
     * 
     * @param country
     * @return tr
     */
    private boolean isCountryValid(String country) {
        if (TrialBatchDataValidator.countryList.contains(country)) {
            return true;    
        } 
        return false;
    }
    /**
     */
    private void getCountryList() {
        
        TrialBatchDataValidator.countryList = new ArrayList<String>();
        try {
            List<Country> listOfCountries = RegistryServiceLocator.getLookUpTableService().getCountries();
            Iterator<Country> iter = listOfCountries.iterator();
            while (iter.hasNext()) {
                Country countries = (Country) iter.next();
                TrialBatchDataValidator.countryList.add(countries.getAlpha3());
            }
        } catch (PAException e) {
            LOG.error("error while validaing country.." + e.getMessage());
        }
    }

}
