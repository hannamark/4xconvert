/**
 *
 */
package gov.nih.nci.registry.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import gov.nih.nci.pa.enums.CodedEnum;

/**
 * @author asharma
 *
 */
public enum BatchStringConstants implements CodedEnum<String> {

  /** */
    UNIQUE_TRIAL_IDENTIFIER("Unique Trial Identifier", "setUniqueTrialId"),
    /** */
     SUBMISSION_TYPE("Submission Type", "setSubmissionType"),
    /** */
     NCI_TRIAL_IDENTIFIER("NCI Trial Identifier", "setNciTrialIdentifier"),
    /** */
     CTGOV_XML_INDICATOR("ClinicalTrials.gov XML Required?", "setCtGovXmlIndicator"),
    /** */
     AMENDMENT_NUMBER("Amendment Number", "setAmendmentNumber"),
    /** */
     AMENDMENT_DATE("Amendment Date", "setAmendmentDate"),
    /** */
     LEAD_ORGANIZATION_TRIAL_IDENTIFIER("Lead Organization Trial Identifier", "setLocalProtocolIdentifier"),
    /** */
     NCT_NUMBER("NCT", "setNctNumber"),
    /** */
     OTHER_TRIAL_IDENTIFIER("Other Trial Identifier", "setOtherTrialIdentifiers"),
    /** */
     TITLE("Title", "setTitle"),
    /** */
     TRIAL_TYPE("Trial Type", "setTrialType"),
    /** */
     PRIMARY_PURPOSE("Primary Purpose", "setPrimaryPurpose"),
    /** */
     PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER("[Primary Purpose] Additional Qualifier",
             "setPrimaryPurposeAdditionalQualifierCode"),
    /** */
     PHASE("Phase", "setPhase"),
    /** */
     PHASE_ADDITIONAL_QUALIFIER("[Phase] Additional Qualifier", "setPhaseAdditionalQualifierCode"),
    /** */
     SPONSOR_ORG_NAME("[Sponsor] Organization Name", "setSponsorOrgName"),
    /** */
     SPONSOR_CETP_ORG_NO("[Sponsor] Organization PO-ID", "setSponsorPOId"),
     /** */
     SPONSOR_STREET_ADDRESS("[Sponsor] Street Address", "setSponsorStreetAddress"),
    /** */
     SPONSOR_CITY("[Sponsor] City", "setSponsorCity"),
    /** */
     SPONSOR_STATE("[Sponsor] State/Province", "setSponsorState"),
    /** */
     SPONSOR_ZIP("[Sponsor] Zip/Postal code", "setSponsorZip"),
    /** */
     SPONSOR_COUNTRY("[Sponsor] Country", "setSponsorCountry"),
    /** */
     SPONSOR_EMAIL("[Sponsor] Email Address", "setSponsorEmail"),
    /** */
     SPONSOR_PHONE("[Sponsor] Phone", "setSponsorPhone"),
    /** */
     SPONSOR_TTY("[Sponsor] TTY", "setSponsorTTY"),
    /** */
     SPONSOR_FAX("[Sponsor] FAX", "setSponsorFax"),
    /** */
     SPONSOR_URL("[Sponsor] URL", "setSponsorURL"),
    /** */
     RESPONSIBLE_PARTY("Responsible Party", "setResponsibleParty"),
    /** */
     SPONSOR_CONTACT_TYPE("Sponsor Contact Type", "setSponsorContactType"),
    /** */
     SPONSOR_CONTACT_TITLE("[Sponsor Contact] Title", "setResponsibleGenericContactName"),
    /** */
     SPONSOR_CONTACT_FIRST_NAME("[Sponsor Contact] First Name", "setSponsorContactFName"),
    /** */
     SPONSOR_CONTACT_MIDDLE_NAME("[Sponsor Contact] Middle Name", "setSponsorContactMName"),
    /** */
     SPONSOR_CONTACT_LAST_NAME("[Sponsor Contact] Last Name", "setSponsorContactLName"),
    /** */
     SPONSOR_CONTACT_PERSON_ID("[Sponsor Contact] Person PO-ID", "setSponsorContactPOId"),
    /** */
     SPONSOR_CONTACT_STREET_ADDRESS("[Sponsor Contact] Street Address", "setSponsorContactStreetAddress"),
    /** */
     SPONSOR_CONTACT_CITY("[Sponsor Contact] City", "setSponsorContactCity"),
    /** */
     SPONSOR_CONTACT_STATE("[Sponsor Contact] State/Province", "setSponsorContactState"),
    /** */
     SPONSOR_CONTACT_ZIP("[Sponsor Contact] Zip/Postal code", "setSponsorContactZip"),
    /** */
     SPONSOR_CONTACT_COUNTRY("[Sponsor Contact] Country", "setSponsorContactCountry"),
    /** */
     SPONSOR_CONTACT_EMAIL_ID("[Sponsor Contact] Email Address", "setSponsorContactEmail"),
    /** */
     SPONSOR_CONTACT_PHONE("[Sponsor Contact] Phone", "setSponsorContactPhone"),
    /** */
     SPONSOR_CONTACT_TTY("[Sponsor Contact] TTY", "setSponsorContactTTY"),
    /** */
     SPONSOR_CONTACT_FAX("[Sponsor Contact] FAX", "setSponsorContactFax"),
    /** */
     SPONSOR_CONTACT_URL("[Sponsor Contact] URL", "setSponsorContactUrl"),
    /** */
     LEAD_ORG_NAME("[Lead Organization] Name", "setLeadOrgName"),
    /** */
     LEAD_ORG_PO_ID("[Lead Organization] Organization PO-ID", "setLeadOrgPOId"),
    /** */
     LEAD_ORG_STREET_ADDRESS("[Lead Organization] Street Address", "setLeadOrgStreetAddress"),
    /** */
     LEAD_ORG_CITY("[Lead Organization] City", "setLeadOrgCity"),
    /** */
     LEAD_ORG_STATE("[Lead Organization] State/Province", "setLeadOrgState"),
    /** */
     LEAD_ORG_ZIP("[Lead Organization] Zip/Postal code", "setLeadOrgZip"),
    /** */
     LEAD_ORG_COUNTRY("[Lead Organization] Country", "setLeadOrgCountry"),
    /** */
     LEAD_ORG_EMAIL("[Lead Organization] Email Address", "setLeadOrgEmail"),
    /** */
     LEAD_ORG_PHONE("[Lead Organization] Phone", "setLeadOrgPhone"),
    /** */
     LEAD_ORG_TTY("[Lead Organization] TTY", "setLeadOrgTTY"),
    /** */
     LEAD_ORG_FAX("[Lead Organization] FAX", "setLeadOrgFax"),
    /** */
     LEAD_ORG_URL("[Lead Organization] URL", "setLeadOrgUrl"),
    /** */
     LEAD_ORG_TYPE("[Lead Organization] Organization Type", "setLeadOrgType"),
    /** */
     PI_FIRST_NAME("[Principal Investigator] First Name", "setPiFirstName"),
    /** */
     PI_MIDDLE_NAME("[Principal Investigator] Middle Name", "setPiMiddleName"),
    /** */
     PI_LAST_NAME("[Principal Investigator] Last Name", "setPiLastName"),
    /** */
     PI_PERSON_PERSON_PO_ID("[Principal Investigator] Person PO-ID", "setPiPOId"),
    /** */
     PI_STREET_ADDRESS("[Principal Investigator] Street Address", "setPiStreetAddress"),
    /** */
     PI_CITY("[Principal Investigator] City", "setPiCity"),
    /** */
     PI_STATE("[Principal Investigator] State/Province", "setPiState"),
    /** */
     PI_ZIP("[Principal Investigator] Zip/Postal code", "setPiZip"),
    /** */
     PI_COUNTRY("[Principal Investigator] Country", "setPiCountry"),
    /** */
     PI_EMAIL("[Principal Investigator] Email Address", "setPiEmail"),
    /** */
     PI_PHONE("[Principal Investigator] Phone", "setPiPhone"),
    /** */
     PI_TTY("[Principal Investigator] TTY", "setPiTTY"),
    /** */
     PI_FAX("[Principal Investigator] FAX", "setPiFax"),
    /** */
     PI_URL("[Principal Investigator] URL", "setPiUrl"),
    /** */
     S4_FUND_CAT("Summary 4 Funding Category", "setSumm4FundingCat"),
    /** */
     S4_FUND_ORG_NAME("[Summary 4 Funding Sponsor/Source] Organization Name", "setSumm4OrgName"),
    /** */
     S4_FUND_ORG_ORG_PO_ID("[Summary 4 Funding Sponsor/Source] Organization PO-ID", "setSumm4OrgPOId"),
    /** */
     S4_FUND_ORG_STREET_ADDRESS("[Summary 4 Funding Sponsor/Source] Street Address", "setSumm4OrgStreetAddress"),
    /** */
     S4_FUND_CITY("[Summary 4 Funding Sponsor/Source] City", "setSumm4City"),
    /** */
     S4_FUND_STATE("[Summary 4 Funding Sponsor/Source] State/Province", "setSumm4State"),
    /** */
     S4_FUND_ZIP("[Summary 4 Funding Sponsor/Source] Zip/Postal code", "setSumm4Zip"),
    /** */
     S4_FUND_COUNTRY("[Summary 4 Funding Sponsor/Source ] Country", "setSumm4Country"),
    /** */
     S4_FUND_EMAIL("[Summary 4 Funding Sponsor/Source ] Email Address", "setSumm4Email"),
    /** */
     S4_FUND_PHONE("[Summary 4 Funding Sponsor/Source ] Phone", "setSumm4Phone"),
    /** */
     S4_FUND_TTY("[Summary 4 Funding Sponsor/Source ] TTY", "setSumm4TTY"),
    /** */
     S4_FUND_FAX("[Summary 4 Funding Sponsor/Source ] FAX", "setSumm4Fax"),
    /** */
     S4_FUND_URL("[Summary 4 Funding Sponsor/Source ] URL", "setSumm4Url"),
    /** */
     S4_PRG_CODE_TEXT("Program Code", "setProgramCodeText"),
    /** */
     NIH_GRANT_FUND_MC("[NIH Grant] Funding Mechanism", "setNihGrantFundingMechanism"),
    /** */
     NIH_GRANT_INSTITUTE_CODE("[NIH Grant] Institute Code", "setNihGrantInstituteCode"),
    /** */
     NIH_GRANT_SR_NO("[NIH Grant] Serial Number", "setNihGrantSrNumber"),
    /** */
     NIH_GRANT_NCI_DIV_CODE("[NIH Grant] NCI Division/Program Code", "setNihGrantNCIDivisionCode"),
    /** */
     CURRENT_TRIAL_STATUS("Current Trial Status", "setCurrentTrialStatus"),
    /** */
     REASON_FOR_STUDY_STOPPED("Why Study Stopped?", "setReasonForStudyStopped"),
    /** */
     CURRENT_TRIAL_STATUS_DATE("Current Trial Status Date", "setCurrentTrialStatusDate"),
    /** */
     STUDY_START_DATE("Study Start Date", "setStudyStartDate"),
    /** */
     STUDY_START_DATE_TYPE("Study Start Date Type", "setStudyStartDateType"),
    /** */
     PRIMARY_COMP_DATE("Primary Completion Date", "setPrimaryCompletionDate"),
    /** */
     PRIMARY_COMP_DATE_TYPE("Primary Completion Date Type", "setPrimaryCompletionDateType"),
    /** */
     IND_TYPE("IND/IDE Type", "setIndType"),
    /** */
     IND_NUMBER("IND/IDE Number", "setIndNumber"),
    /** */
     IND_GRANTOR("IND/IDE Grantor", "setIndGrantor"),
    /** */
     IND_HOLDER_TYPE("IND/IDE Holder Type", "setIndHolderType"),
    /** */
     IND_NIH_INSTITUTION("[IND/IDE] NIH Institution", "setIndNIHInstitution"),
    /** */
     IND_NCI_DIV_CODE("[IND/IDE] NCI Division /Program", "setIndNCIDivision"),
    /** */
     IND_HAS_EXPANDED_ACCESS("[IND/IDE] Has Expanded Access?", "setIndHasExpandedAccess"),
    /** */
     IND_EXPANED_ACCESS_STATUS("[IND/IDE] Expanded Access Status", "setIndExpandedAccessStatus"),
    /** */
     OVERSIGHT_AUTHORITY_COUNTRY("Oversight Authority Country", "setOversightAuthorityCountry"),
    /** */
     OVERSIGHT_AUTHORITY_ORG_NAME("Oversight Authority Organization Name", "setOversightOrgName"),
    /** */
     FDA_REGULATORY_INFORMATION_INDICATOR("FDA Regulatory Information Indicator",
      "setFdaRegulatoryInformationIndicator"),
    /** */
     SECTION_801_INDICATOR("Section 801 Indicator", "setSection801Indicator"),
    /** */
     DELAYED_POSTING_INDICATOR("Delayed Posting Indicator", "setDelayedPostingIndicator"),
    /** */
     DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR("Data Monitoring Committee Appointed Indicator",
         "setDataMonitoringCommitteeAppointedIndicator"),
    /** */
     PROTOCOL_DOC_FILE_NAME("Protocol Document File Name", "setProtcolDocumentFileName"),
    /** */
     IRB_APPROVAL_DOC_FILE_NAME("IRB Approval Document File Name", "setIrbApprovalDocumentFileName"),
    /** */
     PARTICIPATIING_SITE_DOC_FILE_NAME("Participating Sites Document File Name", "setParticipatinSiteDocumentFileName"),
    /** */
     INFORMED_CONSENT_DOC_FILE_NAME("Informed Consent Document File Name", "setInformedConsentDocumentFileName"),
    /** */
     OTHER_TRIAL_DOC_FILE_NAME("Other Trial Related Document File Name", "setOtherTrialRelDocumentFileName"),
    /** */
     CHANGE_MEMO_DOC_FILE_NAME("Change Memo Document Name", "setChangeRequestDocFileName"),
    /** */
    PROTOCOL_HIGHLIGHTED_DOC_FILE_NAME("Protocol Highlight Document Name", "setProtocolHighlightDocFileName");


    private String code;
    private String methodName;

    /**
     * Constructor for BatchStringConstants.
     * @param code
     */

    private BatchStringConstants(String code, String methodName) {
        this.code = code;
        this.methodName = methodName;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    public String getCode() {
        return code;
    }

    /**
     * @return methodName coded value of enum
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     *@return String DisplayName
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }


    /**
     * @param code code
     * @return BatchStringConstants
     */
    public static BatchStringConstants getByCode(String code) {
        return getByClassAndCode(BatchStringConstants.class, code);
    }

    /**
     * construct a array of display names for BatchStringConstantsEnum.
     * @return String[] display names for BatchStringConstants
     */
    public static String[]  getDisplayNames() {
        BatchStringConstants[] batchStringConstants = BatchStringConstants.values();
        String[] codedNames = new String[batchStringConstants.length];
        for (int i = 0; i < batchStringConstants.length; i++) {
            codedNames[i] = batchStringConstants[i].getCode();
        }
        return codedNames;
    }

   /**
    * {@inheritDoc}
    */
   public String getNameByCode(String str) {
       return getByCode(str).name();
   }
}
