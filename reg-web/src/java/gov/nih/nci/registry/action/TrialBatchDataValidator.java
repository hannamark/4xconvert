package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.OrganizationBatchDTO;
import gov.nih.nci.registry.dto.PersonBatchDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.enums.TrialStatusCode;
import gov.nih.nci.registry.enums.TrialStatusReasonCode;
import gov.nih.nci.registry.util.BatchConstants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * 
 * @author Vrushali
 * This class is used to validate the spread sheet data.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity",
    "PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.ExcessiveMethodLength" })
public class TrialBatchDataValidator {
    private static final int IND_FIELD_COUNT = 5;
    private static final Logger LOG = Logger.getLogger(TrialBatchDataValidator.class);
    private static List<String> countryList = null;
    private static final int TRIAL_TITLE_MAX_LENGTH = 4000;
    private static final int AUS_STATE_CODE_LEN = 3;
    private static final int ORG_FIELD = 8;
    private static final String DELEMITOR = ";";

    /**
     * 
     * validate the submit trial form elements.
     * @param batchDto dto
     * @return str
     */
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
        if (PAUtil.isNotEmpty(batchDto.getSubmissionType()) && !batchDto.getSubmissionType().equals("U")) {
            //Lead org Country and state validation
            fieldErr.append(validateLeadOrg(batchDto));
            //Sponsor validation
            fieldErr.append(validateSponsorInfo(batchDto));
            //Check PI Country and state 
            fieldErr.append(validatePI(batchDto));
            if (PAUtil.isNotEmpty(batchDto.getResponsibleParty()) 
                    && !batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")
                    && !batchDto.getResponsibleParty().equalsIgnoreCase("PI")) {
                    fieldErr.append("Please enter valid value for Responsible Party.");
            }
            if (PAUtil.isEmpty(batchDto.getLocalProtocolIdentifier())) {
                fieldErr.append("Lead Organization Trial Identifier is required.\n");
            }
            if (PAUtil.isEmpty(batchDto.getProtcolDocumentFileName())) {
                fieldErr.append("Protocol Document is required. \n");
            }
            if (PAUtil.isEmpty(batchDto.getIrbApprovalDocumentFileName())) {
                fieldErr.append("IRB Approval Document is required. \n");
            }
            if (PAUtil.isEmpty(batchDto.getResponsibleParty())) {
                fieldErr.append("Responsible Party Not Provided.\n");
            }
            if (PAUtil.isEmpty(batchDto.getTitle())) {
                fieldErr.append("Trial Title is required.\n");
            } else if (batchDto.getTitle().length() > TRIAL_TITLE_MAX_LENGTH) {
                fieldErr.append("Trial Title must be 4000 characters max");
            }


        }
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
        //validate grant
        fieldErr.append(validateGrantInfo(batchDto));
        //validate the IND/IDE
        fieldErr.append(validateIndIde(batchDto));
        //validate the ValidValues for other
        fieldErr.append(validateListOfValues(batchDto));
        //validate the Amendment info
        fieldErr.append(validateAmendmentInfo(batchDto));
        //validate the oversight Info
        fieldErr.append(validateOversightInfo(batchDto));
        //validate the update info
        fieldErr.append(validateUpdate(batchDto));
        return fieldErr.toString();
    }
    private Object validatePI(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        PersonBatchDTO piBatchDto = buildLeadPIDto(batchDto);
        fieldErr.append(validate(piBatchDto, "Principal Investigator's "));
        fieldErr.append(validateCountryAndStateInfo(piBatchDto.getCountry(), piBatchDto.getState(),
                "Principal Investigator's"));
        return fieldErr;
    }
    private StringBuffer validateLeadOrg(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        OrganizationBatchDTO leadOrgDto = buildLeadOrgDto(batchDto);
        fieldErr.append(validate(leadOrgDto, "Lead Organization's "));
        fieldErr.append(validateCountryAndStateInfo(leadOrgDto.getCountry(), leadOrgDto.getState(), 
                "Lead Organization's "));
        return fieldErr;
    }
    private StringBuffer validateUpdate(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isNotEmpty(batchDto.getSubmissionType()) 
                && batchDto.getSubmissionType().equalsIgnoreCase("U") 
           && PAUtil.isEmpty(batchDto.getNciTrialIdentifier())) {
            fieldErr.append("NCI Trial Identifier is required. \n");                
        }
        fieldErr.append(validateSponsorContactInfo(batchDto));
        if (PAUtil.isEmpty(batchDto.getNctNumber())) {
            fieldErr.append("NCT Number is required. \n");
        }

        return fieldErr;
    }
    private StringBuffer validateOversightInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isNotEmpty(batchDto.getFdaRegulatoryInformationIndicator())
                && batchDto.getFdaRegulatoryInformationIndicator().equalsIgnoreCase("YES")) {
            if (PAUtil.isEmpty(batchDto.getSection801Indicator())) {
                fieldErr.append("Must be not NULL if FDA Regulatory Information Indicator is 'Yes'.\n");
            } else if (batchDto.getSection801Indicator().equalsIgnoreCase("YES")
                    && PAUtil.isEmpty(batchDto.getDelayedPostingIndicator())) {
                fieldErr.append("Must be not NULL if section 801 Indicator is 'Yes'.\n");
            }
        }            
        return fieldErr;
    }
    private StringBuffer validateGrantInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
      //validate grant 
        List<TrialFundingWebDTO> dtoList = convertToGrantList(batchDto);
        for (TrialFundingWebDTO webDto : dtoList) {
            fieldErr.append(validate(webDto, ""));
            if (!containsReqGrantInfo(webDto)) {
                fieldErr.append("All Grant values are required.\n");
            } 
        }
        return fieldErr;
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
    private StringBuffer validateIndIde(StudyProtocolBatchDTO batchDto) {
        //validate the IND/IDE
        StringBuffer fieldErr = new StringBuffer();
        List<TrialIndIdeDTO> indDtoList = convertIndsToList(batchDto);
        for (TrialIndIdeDTO indDto : indDtoList) {
            if (!isIndIdeContainsAllInfo(indDto)) {
                fieldErr.append("All IND/IDE values are required.\n");
            } else {
                if (PAUtil.isNotEmpty(indDto.getExpandedAccess()) 
                        && indDto.getExpandedAccess().equalsIgnoreCase("True") 
                        && PAUtil.isEmpty(indDto.getExpandedAccessType())) {
                      fieldErr.append("Expanded Access Status value is required.\n");
                }
                if (PAUtil.isNotEmpty(indDto.getHolderType()) 
                        && indDto.getHolderType().equalsIgnoreCase(HolderTypeCode.NIH.getCode())
                        && PAUtil.isEmpty(indDto.getProgramCode())) {
                        fieldErr.append("NIH Institution value is required.\n");
                }
                if (PAUtil.isNotEmpty(indDto.getHolderType()) 
                        && indDto.getHolderType().equalsIgnoreCase(HolderTypeCode.NCI.getCode())
                        && PAUtil.isEmpty(indDto.getProgramCode())) {
                        fieldErr.append("NCI Division/Program Code value is required.\n");
                }
            }
            //Validate List of values
            if (PAUtil.isNotEmpty(indDto.getIndIde()) && null == IndldeTypeCode.getByCode(indDto.getIndIde())) {
                fieldErr.append("Please enter valid value for IND/IDE.\n");
            }
            if (PAUtil.isNotEmpty(indDto.getHolderType()) && null == HolderTypeCode.getByCode(indDto.getHolderType())) {
                fieldErr.append("Please enter valid value for IND/IDE Holder Type.\n");
            }
            if (PAUtil.isNotEmpty(indDto.getGrantor()) && null == GrantorCode.getByCode(indDto.getGrantor())) {
                fieldErr.append("Please enter valid value for IND/IDE Grantor.\n");
            }
            if (PAUtil.isNotEmpty(indDto.getIndIde()) && indDto.getIndIde().equals(IndldeTypeCode.IDE.getCode())
                && !indDto.getGrantor().equals(GrantorCode.CDRH.getCode())) {
                fieldErr.append("IDE Grantor can have only CDRH value.\n");
            }
            if (PAUtil.isNotEmpty(indDto.getExpandedAccessType()) 
                    && null == ExpandedAccessStatusCode.getByCode(indDto.getExpandedAccessType())) {
                fieldErr.append("Please enter valid value for IND/IDE Expanded Access Status.\n");
            }
            //validate NIH Institution values
            if (PAUtil.isNotEmpty(indDto.getHolderType()) 
                    && indDto.getHolderType().equalsIgnoreCase(HolderTypeCode.NIH.getCode())
                    && PAUtil.isNotEmpty(indDto.getProgramCode()) 
                    && null == NihInstituteCode.getByCode(indDto.getProgramCode())) {
                    fieldErr.append("Please enter valid value for IND/IDE NIH Institution.\n");
            }
            //validate NCI Division values
            if (PAUtil.isNotEmpty(indDto.getHolderType()) 
                    && indDto.getHolderType().equalsIgnoreCase(HolderTypeCode.NCI.getCode())
                && PAUtil.isNotEmpty(indDto.getProgramCode()) 
                && null == NciDivisionProgramCode.getByCode(indDto.getProgramCode())) {
                    fieldErr.append("Please enter valid value for IND/IDE NCI Division /Program.\n");
            }
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
                fieldErr.append("Participating Site Document - File type is not allowed. \n");                
        }        
        if (PAUtil.isNotEmpty(batchDto.getInformedConsentDocumentFileName())
                && !RegistryUtil.isValidFileType(batchDto.getInformedConsentDocumentFileName())) {
                fieldErr.append("Informed Consent Document - File type is not allowed. \n");                
        }        
        if (PAUtil.isNotEmpty(batchDto.getOtherTrialRelDocumentFileName())
                && !RegistryUtil.isValidFileType(batchDto.getOtherTrialRelDocumentFileName())) {
               fieldErr.append("Other Trial Related Document - File type is not allowed. \n");                
        }
        //for amendments
        if (PAUtil.isNotEmpty(batchDto.getProtocolHighlightDocFileName())
                && !RegistryUtil.isValidFileType(batchDto.getProtocolHighlightDocFileName())) {
                fieldErr.append("Protocol Highlight Document - File type is not allowed. \n");                
        }        
        if (PAUtil.isNotEmpty(batchDto.getChangeRequestDocFileName())
                && !RegistryUtil.isValidFileType(batchDto.getChangeRequestDocFileName())) {
               fieldErr.append("Change Request Document - File type is not allowed. \n");                
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
        if (null == TrialStatusCode.getByCode(batchDto.getCurrentTrialStatus())) {
            fieldErr.append("Please enter valid value for Current Trial Status");
        }
        if (null != TrialStatusReasonCode.getByCode(batchDto.getCurrentTrialStatus())
                && PAUtil.isEmpty(batchDto.getReasonForStudyStopped())) {
                fieldErr.append("Why Study Stopped is required.");
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
        fieldErr.append(validate(dto, "Sponsor Organization's "));
        fieldErr.append(validateCountryAndStateInfo(dto.getCountry(), dto.getState(), "Sponsor Organization's"));
        fieldErr.append(validateSponsorContactInfo(batchDto)); 
        if (PAUtil.isEmpty(batchDto.getResponsibleParty())) {
                fieldErr.append("Sponsor Contact Type is required.\n");
        }
        return fieldErr;
    }
    @SuppressWarnings({"PMD.CollapsibleIfStatements" })
    private StringBuffer validateSponsorContactInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isNotEmpty(batchDto.getResponsibleParty()) 
                && batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
            //check Sponsor contact info is provided or not
            //find if its Generic/Personal
            if (PAUtil.isNotEmpty(batchDto.getSponsorContactType())) {
                if (batchDto.getSponsorContactType().equalsIgnoreCase("Personal")) {
                    PersonBatchDTO sponsorContact = buildSponsorContact(batchDto);
                    fieldErr.append(validate(sponsorContact, "Sponsor Contact's "));
                    fieldErr.append(validateCountryAndStateInfo(sponsorContact.getCountry(), sponsorContact.getState(),
                        "Sponsor Contact's "));
                } 
                if (batchDto.getSponsorContactType().equalsIgnoreCase("Generic")) {
                  if (PAUtil.isEmpty(batchDto.getResponsibleGenericContactName())) {
                      fieldErr.append("Title is required when Sponsor Contact Type is Generic .\n");      
                  }
                  if (PAUtil.isEmpty(batchDto.getSponsorContactEmail())) {
                      fieldErr.append("Sponsor Contact Email is required.\n");      
                  } else if (!PAUtil.isValidEmail(batchDto.getSponsorContactEmail())) {
                      fieldErr.append("Sponsor Contact Email is not well formatted.\n");
                  }
                  if (PAUtil.isEmpty(batchDto.getSponsorContactPhone())) {
                      fieldErr.append("Sponsor Contact Phone is required.\n");      
                  }
                }
            }
        }
        return fieldErr;
    }
    /**
     * validate the submit trial dates.
     */

    /**
     * 
     * @param fundmc
     * @param srNumber
     * @param icdCode
     * @return
     */
    private boolean containsReqGrantInfo(TrialFundingWebDTO grandDto) {
            String fundmc = grandDto.getFundingMechanismCode();
            String srNumber = grandDto.getSerialNumber();
            String icdCode = grandDto.getNihInstitutionCode();
            String divCode = grandDto.getNciDivisionProgramCode();
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
    private boolean isIndIdeContainsAllInfo(TrialIndIdeDTO dto) {
        int nullCount = 0;
        if (PAUtil.isEmpty(dto.getIndIde())) {
            nullCount += 1;
        }
       if (PAUtil.isEmpty(dto.getNumber())) {
           nullCount += 1;
       }
       if (PAUtil.isEmpty(dto.getGrantor())) {
           nullCount += 1;
       }
       if (PAUtil.isEmpty(dto.getHolderType())) {
           nullCount += 1;
       }
       if (PAUtil.isEmpty(dto.getExpandedAccess())) {
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
    private StringBuffer validateAmendmentInfo(StudyProtocolBatchDTO batchDto) {
        StringBuffer fieldErr = new StringBuffer();
        if (PAUtil.isNotEmpty(batchDto.getSubmissionType()) 
                &&  batchDto.getSubmissionType().equalsIgnoreCase("A")) {
            if (PAUtil.isEmpty(batchDto.getNciTrialIdentifier())) {
                fieldErr.append("NCI Trial Identifier is required. \n");                
            }
            if (PAUtil.isEmpty(batchDto.getAmendmentDate())) {
                fieldErr.append("Amendment Date is required. \n");                
            } else {
                Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
                if (currentTimeStamp.before(PAUtil.dateStringToTimestamp(batchDto.getAmendmentDate()))) {
                    fieldErr.append("Amendment Date cannot be in the future.\n");                
                }
            }
            if (PAUtil.isEmpty(batchDto.getChangeRequestDocFileName())) {
                   fieldErr.append("Change Request Document is required. \n");                
            }
            if (batchDto.getCurrentTrialStatus().equalsIgnoreCase("In Review")) {
                fieldErr.append("To Amend Submission of pre-IRB approved study replace " 
                  + " current trial status 'In-Review' with 'Approved'");
            }
            if (PAUtil.isEmpty(batchDto.getNctNumber())) {
                fieldErr.append("NCT Number is required. \n");
            }

        }
        return fieldErr;
    }
    /**
     * 
     * @param dto d
     * @return s
     */
    private boolean isMultipleIndIde(StudyProtocolBatchDTO dto) {
        boolean isMultiple = false;
        if (!isIndIdeEmpty(dto)) {
            if (dto.getIndType().indexOf(DELEMITOR) == -1
                && dto.getIndNumber().indexOf(DELEMITOR) == -1
                && dto.getIndGrantor().indexOf(DELEMITOR) == -1
                && dto.getIndHolderType().indexOf(DELEMITOR) == -1
                && dto.getIndHasExpandedAccess().indexOf(DELEMITOR) == -1) {
                isMultiple = false;
            } else {
                isMultiple = true;
            }
        }
        return isMultiple;
    }
    /**
     * 
     * @param dto d
     * @return list
     */
    public List<TrialIndIdeDTO> convertIndsToList(StudyProtocolBatchDTO dto) {
        List<TrialIndIdeDTO> indIdeList = new ArrayList<TrialIndIdeDTO>();
            if (isMultipleIndIde(dto)) {
                Map <Integer, String> indTypeMap = covertToMap(dto.getIndType());
                Map <Integer, String> indNumbereMap = covertToMap(dto.getIndNumber());
                Map <Integer, String> indGrantorMap = covertToMap(dto.getIndGrantor());
                Map <Integer, String> indHolderTypeMap = covertToMap(dto.getIndHolderType());
                Map <Integer, String> indNIHInstitutionMap = covertToMap(dto.getIndNIHInstitution());
                Map <Integer, String> indNCIDivisionMap = covertToMap(dto.getIndNCIDivision());
                Map <Integer, String> indHasExpandedAccessMap = covertToMap(dto.getIndHasExpandedAccess());
                Map <Integer, String> indHasExpandedAccessStatusMap = covertToMap(dto.getIndExpandedAccessStatus());
                //get the map with highest size 
                int maxSize = indTypeMap.size();
                if (maxSize < indNumbereMap.size()) {
                    maxSize = indNumbereMap.size();
                }
                if (maxSize < indGrantorMap.size()) {
                    maxSize = indGrantorMap.size();
                }
                if (maxSize < indHolderTypeMap.size()) {
                    maxSize = indHolderTypeMap.size();
                }
                if (maxSize < indNIHInstitutionMap.size()) {
                    maxSize = indNIHInstitutionMap.size();
                }
                if (maxSize < indNCIDivisionMap.size()) {
                    maxSize = indNCIDivisionMap.size();
                }
                if (maxSize < indHasExpandedAccessMap.size()) {
                    maxSize = indHasExpandedAccessMap.size();
                }
                if (maxSize < indHasExpandedAccessStatusMap.size()) {
                    maxSize = indHasExpandedAccessStatusMap.size();
                }
                for (int i = 0; i < maxSize; i++) {
                    TrialIndIdeDTO indldeDTO = new TrialIndIdeDTO();
                    if (PAUtil.isNotEmpty(indTypeMap.get(i))) {
                        indldeDTO.setIndIde(indTypeMap.get(i).trim());
                    } else {
                        indldeDTO.setIndIde("");
                    }
                    if (PAUtil.isNotEmpty(indNumbereMap.get(i))) {
                        indldeDTO.setNumber(indNumbereMap.get(i).trim());
                    } else {
                        indldeDTO.setNumber("");
                    }
                    if (PAUtil.isNotEmpty(indGrantorMap.get(i))) {
                        indldeDTO.setGrantor(indGrantorMap.get(i).trim());
                    } else {
                        indldeDTO.setGrantor("");
                    }
                    if (PAUtil.isNotEmpty(indHolderTypeMap.get(i))) {
                        indldeDTO.setHolderType(indHolderTypeMap.get(i).trim());
                    } else {
                        indldeDTO.setHolderType("");
                    }
                    String naString = "NA";
                    if (indldeDTO.getHolderType() != null) { 
                        if (indldeDTO.getHolderType().equalsIgnoreCase("NIH") 
                                && PAUtil.isNotEmpty(indNIHInstitutionMap.get(i))) {
                            indldeDTO.setProgramCode(indNIHInstitutionMap.get(i).trim());
                        }
                        if (indldeDTO.getHolderType().equalsIgnoreCase("NCI")
                                && PAUtil.isNotEmpty(indNCIDivisionMap.get(i))) {
                            indldeDTO.setProgramCode(indNCIDivisionMap.get(i).trim());
                        }
                    }
                    if (PAUtil.isNotEmpty(indHasExpandedAccessMap.get(i))) {
                        indldeDTO.setExpandedAccess(indHasExpandedAccessMap.get(i).trim());
                    } else {
                        indldeDTO.setExpandedAccess("");
                    }
                    if (PAUtil.isNotEmpty(indHasExpandedAccessStatusMap.get(i)) 
                            && !indHasExpandedAccessStatusMap.get(i).trim().equalsIgnoreCase(naString)) {
                        indldeDTO.setExpandedAccessType(indHasExpandedAccessStatusMap.get(i).trim());
                    } else {
                        indldeDTO.setExpandedAccessType("");
                    }
                    indIdeList.add(indldeDTO);
                }
            } else {
                if (!isIndIdeEmpty(dto)) {
                    TrialIndIdeDTO indldeDTO = new TrialIndIdeDTO();
                    indldeDTO.setIndIde(dto.getIndType().trim());
                    indldeDTO.setNumber(dto.getIndNumber().trim());
                    indldeDTO.setGrantor(dto.getIndGrantor().trim());
                    indldeDTO.setHolderType(dto.getIndHolderType().trim());
                    if (dto.getIndHolderType().equalsIgnoreCase("NIH")) {
                        indldeDTO.setProgramCode(dto.getIndNIHInstitution().trim());
                    }
                    if (dto.getIndHolderType().equalsIgnoreCase("NCI")) {
                        indldeDTO.setProgramCode(dto.getIndNCIDivision().trim());
                    }
                    indldeDTO.setExpandedAccess(dto.getIndHasExpandedAccess().trim());
                    if (PAUtil.isNotEmpty(dto.getIndExpandedAccessStatus())) {
                        indldeDTO.setExpandedAccessType(dto.getIndExpandedAccessStatus().trim());
                    } else {
                        indldeDTO.setExpandedAccessType("");
                    }
                    indIdeList.add(indldeDTO);
                }
            }
        return indIdeList;
    }
    private boolean isIndIdeEmpty(StudyProtocolBatchDTO dto) {
        boolean retValue = true;
        if (PAUtil.isNotEmpty(dto.getIndType()) && PAUtil.isNotEmpty(dto.getIndNumber())
                && PAUtil.isNotEmpty(dto.getIndGrantor()) && PAUtil.isNotEmpty(dto.getIndHolderType())
                && PAUtil.isNotEmpty(dto.getIndHasExpandedAccess())) {
            retValue = false;
        }
        return retValue;
    }
    private Map<Integer, String> covertToMap(String commaSeparated) {
        Map <Integer, String> map = new HashMap<Integer, String>();
        Integer key = 0;
        if (commaSeparated == null) {
            map.put(key++ , "");
            return map;
        }
        StringTokenizer tokenizer = new StringTokenizer(commaSeparated, DELEMITOR);
        if (commaSeparated.startsWith(DELEMITOR)) {
            map.put(key++ , "");
        }
        while (tokenizer.hasMoreTokens()) {
            map.put(key++ , tokenizer.nextToken());
        }
        if (commaSeparated.endsWith(DELEMITOR)) {
            map.put(key++ , "");
        }
        return map;
    }
    /**
     * 
     * @param dto batch
     * @return dto
     */
    public List<TrialFundingWebDTO> convertToGrantList(StudyProtocolBatchDTO dto) {
        List<TrialFundingWebDTO> grantList = new ArrayList<TrialFundingWebDTO>();
        if (isMultipleGrant(dto)) {
            Map <Integer, String> fundingMechanismCodeMap = covertToMap(dto.getNihGrantFundingMechanism());
            Map <Integer, String> grantNCIDivisionCodeMap = covertToMap(dto.getNihGrantNCIDivisionCode());
            Map <Integer, String> grantInstituteCodeMap = covertToMap(dto.getNihGrantInstituteCode());
            Map <Integer, String> grantSrNumberMap = covertToMap(dto.getNihGrantSrNumber());
            int maxSize = fundingMechanismCodeMap.size();
            if (maxSize < grantNCIDivisionCodeMap.size()) {
                maxSize = grantNCIDivisionCodeMap.size();
            }
            if (maxSize < grantInstituteCodeMap.size()) {
                maxSize = grantInstituteCodeMap.size();
            }
            if (maxSize < grantSrNumberMap.size()) {
                maxSize = grantSrNumberMap.size();
            }
            for (int i = 0; i < maxSize; i++) {
                TrialFundingWebDTO fundingDTO = new TrialFundingWebDTO();
                if (PAUtil.isNotEmpty(fundingMechanismCodeMap.get(i))) {
                    fundingDTO.setFundingMechanismCode(fundingMechanismCodeMap.get(i).trim());
                } else {
                    fundingDTO.setFundingMechanismCode("");
                }
                if (PAUtil.isNotEmpty(grantNCIDivisionCodeMap.get(i))) {
                    fundingDTO.setNciDivisionProgramCode(grantNCIDivisionCodeMap.get(i).trim());
                } else {
                    fundingDTO.setNciDivisionProgramCode("");
                }
                if (PAUtil.isNotEmpty(grantInstituteCodeMap.get(i))) {
                    fundingDTO.setNihInstitutionCode(grantInstituteCodeMap.get(i).trim());
                } else {
                    fundingDTO.setNihInstitutionCode("");
                }
                if (PAUtil.isNotEmpty(grantSrNumberMap.get(i))) {
                    fundingDTO.setSerialNumber(grantSrNumberMap.get(i).trim());
                } else {
                    fundingDTO.setSerialNumber("");
                }
                grantList.add(fundingDTO);
            }
        } else {
            if (!isGrantEmpty(dto)) {
                TrialFundingWebDTO fundingDTO = new TrialFundingWebDTO();
                fundingDTO.setFundingMechanismCode(dto.getNihGrantFundingMechanism());
                fundingDTO.setNciDivisionProgramCode(dto.getNihGrantNCIDivisionCode());
                fundingDTO.setNihInstitutionCode(dto.getNihGrantInstituteCode());
                fundingDTO.setSerialNumber(dto.getNihGrantSrNumber());
                grantList.add(fundingDTO);
            }
        }
        return grantList;
    }
    private boolean isMultipleGrant(StudyProtocolBatchDTO dto) {
        boolean isMultiple = false;
        //if grant is Not empty then check if it has multiple values or not
        if (!isGrantEmpty(dto)) {
            if (dto.getNihGrantFundingMechanism().indexOf(DELEMITOR) == -1
                    && dto.getNihGrantInstituteCode().indexOf(DELEMITOR) == -1
                    && dto.getNihGrantSrNumber().indexOf(DELEMITOR) == -1
                    && dto.getNihGrantNCIDivisionCode().indexOf(DELEMITOR) == -1) {
                        isMultiple = false;
                    } else {
                        isMultiple = true;
                    }
        }
        return isMultiple;
    }
    private boolean isGrantEmpty(StudyProtocolBatchDTO dto) {
        boolean retVal = true;
        if (PAUtil.isNotEmpty(dto.getNihGrantFundingMechanism()) 
                && PAUtil.isNotEmpty(dto.getNihGrantNCIDivisionCode()) 
                && PAUtil.isNotEmpty(dto.getNihGrantInstituteCode()) 
                && PAUtil.isNotEmpty(dto.getNihGrantSrNumber())) {
            retVal = false;
        }
         return retVal;   
    }
}
