DROP TABLE IF EXISTS DW_STUDY CASCADE;
CREATE TABLE DW_STUDY (
    ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR character varying(3),
    ACRONYM  character varying(200),
    MASKING_ALLOCATION_CODE character varying(200),
    AMENDMENT_DATE date,
    AMENDMENT_NUMBER_TEXT character varying(200),
    AMENDMENT_REASON_CODE character varying(200),
    CENTRAL_CONTACT_EMAIL character varying(200),
    CENTRAL_CONTACT_NAME character varying(200),
    CENTRAL_CONTACT_PHONE character varying(200),
    CENTRAL_CONTACT_TYPE character varying(50),
    CHECKED_OUT_BY_FOR_ADMINISTRATIVE character varying(200),
    CHECKED_OUT_BY_FOR_SCIENTIFIC character varying(200),
    CLASSIFICATION_CODE character varying(200),
    COMPLETION_DATE date,
    COMPLETION_DATE_TYPE_CODE character varying(50),
    CREATED_BY character varying(500),
    CT_GOV_XML_REQUIRED_INDICATOR character varying(3),
    CTEP_ID character varying(200),
    CURRENT_MILESTONE character varying(200),
    CURRENT_TRIAL_STATUS character varying(200),
    CURRENT_TRIAL_STATUS_DATE date,
    DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR character varying(3),
    DATE_LAST_CREATED date,
    DATE_LAST_UPDATED date,
    DCP_ID character varying(200),
    DELAYED_POSTING_INDICATOR character varying(3),
    DETAIL_DESCRIPTION character varying(32000),
    DETAIL_DESCRIPTION_PRIMARY character varying(2000),
    DETAIL_DESCRIPTION_SECONDARY character varying(2000),
    DETAIL_DESCRIPTION_TERTIARY character varying(2000),
    ELIGIBLE_GENDER character varying(200),
    ELIGIBLE_MAX_AGE character varying(200),
    ELIGIBLE_MIN_AGE character varying(200),
    EXPANDED_ACCESS_INDICATOR character varying(3),
    FDAREGULATED_INDICATOR character varying(3),
    INTERNAL_SYSTEM_ID INTEGER,
    INTERVENTIONAL_MODEL character varying(255),
    IRB_APPROVAL_NUMBER character varying(200),
    IRB_APPROVAL_STATUS character varying(200),
    IRB_CITY character varying(200),
    IRB_COUNTRY character varying(200),
    IRB_EMAIL character varying(200),
    IRB_NAME character varying(200),
    IRB_ORGANIZATION_AFFILIATION character varying(200),
    IRB_PHONE character varying(200),
    IRB_STATE_OR_PROVINCE character varying(200),
    IRB_STREET_ADDRESS character varying(200),
    IRB_ZIP_CODE character varying(200),
    KEYWORD_TEXT character varying(600),
    LAST_UPDATED_BY character varying(500),
    LEAD_ORG character varying(200),
    LEAD_ORG_ORG_FAMILY character varying(200),
    LEAD_ORG_ID character varying(200),
    MASKING character varying(200),
    MASKING_ROLE_INVESTIGATOR character varying(200),
    MASKING_ROLE_OUTCOME_ASSESSOR character varying(200),
    MASKING_ROLE_SUBJECT character varying(200),
    MASKING_ROLE_CAREGIVER character varying(200),
    MINIMUM_TARGET_ACCRUAL_NUMBER INTEGER,
    NCI_ID character varying(255),
    NCT_ID character varying(200),
    CDR_ID character varying(200),
    NUMBER_OF_ARMS INTEGER,
    OFFICIAL_TITLE character varying(4000),
    OVERSIGHT_AUTHORITY_COUNTRY character varying(200),
    OVERSIGHT_AUTHORITY_ORGANIZATION_NAME character varying(200),
    PHASE character varying(50),
    PHASE_ADDITIONAL_QUALIFIER_CODE character varying(50),
    PHASE_OTHER_TEXT character varying(200),
    PRIMARY_COMPLETION_DATE date,
    PRIMARY_COMPLETION_DATE_TYPE_CODE character varying(50),
    PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE character varying(50),
    PRIMARY_PURPOSE_CODE character varying(50),
    PRIMARY_PURPOSE_OTHER_TEXT character varying(1000),
    PRINCIPAL_INVESTIGATOR character varying(1000),
    PROCESSING_STATUS character varying(200),
    BRIEF_SUMMARY character varying(5000),
    BRIEF_TITLE character varying(300),
    RECORD_VERIFICATION_DATE date,
    REJECTION_REASON character varying(200),
    REPORTING_METHOD_DATA_CODE character varying(50),
    RESP_PARTY_TYPE character varying (50),
    RESPONSIBLE_PARTY_GENERIC_CONTACT character varying(200),
    RESPONSIBLE_PARTY_PERSONAL_CONTACT character varying(200),
    REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR character varying(3),
    SECTION_801_INDICATOR character varying(3),
    SPONSOR character varying(200),
    RESPONSIBLE_PARTY_NAME character varying(1000),
    SPONSOR_ORG_FAMILY character varying(200),
    SPONSOR_RESP_PARTY_EMAIL character varying(200),
    SPONSOR_RESP_PARTY_PHONE character varying(200),
    START_DATE date,
    START_DATE_TYPE_CODE character varying(50),
    SUBMISSION_NUMBER INTEGER,
    SUBMITTER_NAME character varying(501),
    SUBMITTER_ORGANIZATION character varying(200),
    SUBMITTER_ORGANIZATION_FAMILY character varying(200),
    SUMMARY_4_FUNDING_CATEGORY character varying(200),
    SUMMARY_4_FUNDING_SPONSOR character varying(200),
    SUMMARY_4_FUNDING_SPONSOR_FAMILY character varying(200),
    WHY_STUDY_STOPPED character varying(2000),
    CATEGORY character varying (12),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
); 

CREATE INDEX DW_STUDY_ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR_IDX ON DW_STUDY(ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR);
CREATE INDEX DW_STUDY_ACRONYM_IDX on dw_study(acronym);
CREATE INDEX DW_STUDY_MASKING_ALLOCATION_CODE_IDX on dw_study(MASKING_ALLOCATION_CODE);
CREATE INDEX DW_STUDY_AMENDMENT_DATE_IDX on dw_study(AMENDMENT_DATE);
CREATE INDEX DW_STUDY_AMENDMENT_NUMBER_TEXT_IDX on dw_study(AMENDMENT_NUMBER_TEXT);
CREATE INDEX DW_STUDY_AMENDMENT_REASON_CODE_IDX on dw_study(AMENDMENT_REASON_CODE);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_EMAIL_IDX on dw_study(CENTRAL_CONTACT_EMAIL);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_NAME_IDX on dw_study(CENTRAL_CONTACT_NAME);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_PHONE_IDX on dw_study(CENTRAL_CONTACT_PHONE);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_TYPE_IDX on dw_study(CENTRAL_CONTACT_TYPE);
CREATE INDEX DW_STUDY_CHECKED_OUT_BY_FOR_ADMINISTRATIVE_IDX on dw_study(CHECKED_OUT_BY_FOR_ADMINISTRATIVE);
CREATE INDEX DW_STUDY_CHECKED_OUT_BY_FOR_SCIENTIFIC_IDX on dw_study(CHECKED_OUT_BY_FOR_SCIENTIFIC);
CREATE INDEX DW_STUDY_CLASSIFICATION_CODE_IDX on dw_study(CLASSIFICATION_CODE);
CREATE INDEX DW_STUDY_COMPLETION_DATE_IDX on dw_study(COMPLETION_DATE);
CREATE INDEX DW_STUDY_COMPLETION_DATE_TYPE_CODE_IDX on dw_study(COMPLETION_DATE_TYPE_CODE);
CREATE INDEX DW_STUDY_CREATED_BY_IDX on dw_study(CREATED_BY);
CREATE INDEX DW_STUDY_CT_GOV_XML_REQUIRED_INDICATOR_IDX on dw_study(CT_GOV_XML_REQUIRED_INDICATOR);
CREATE INDEX DW_STUDY_CTEP_ID_IDX on dw_study(CTEP_ID);
CREATE INDEX DW_STUDY_CURRENT_MILESTONE_IDX on dw_study(CURRENT_MILESTONE);
CREATE INDEX DW_STUDY_CURRENT_TRIAL_STATUS_IDX on dw_study(CURRENT_TRIAL_STATUS);
CREATE INDEX DW_STUDY_CURRENT_TRIAL_STATUS_DATE_IDX on dw_study(CURRENT_TRIAL_STATUS_DATE);
CREATE INDEX DW_STUDY_DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR_IDX on dw_study(DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR);
CREATE INDEX DW_STUDY_DATE_LAST_CREATED_IDX on dw_study(DATE_LAST_CREATED);
CREATE INDEX DW_STUDY_DATE_LAST_UPDATED_IDX on dw_study(DATE_LAST_UPDATED);
CREATE INDEX DW_STUDY_DCP_ID_IDX on dw_study(DCP_ID);
CREATE INDEX DW_STUDY_DELAYED_POSTING_INDICATOR_IDX on dw_study(DELAYED_POSTING_INDICATOR);
CREATE INDEX DW_STUDY_ELIGIBLE_GENDER_IDX on dw_study(ELIGIBLE_GENDER);
CREATE INDEX DW_STUDY_ELIGIBLE_MAX_AGE_IDX on dw_study(ELIGIBLE_MAX_AGE);
CREATE INDEX DW_STUDY_ELIGIBLE_MIN_AGE_IDX on dw_study(ELIGIBLE_MIN_AGE);
CREATE INDEX DW_STUDY_EXPANDED_ACCESS_INDICATOR_IDX on dw_study(EXPANDED_ACCESS_INDICATOR);
CREATE INDEX DW_STUDY_FDAREGULATED_INDICATOR_IDX on dw_study(FDAREGULATED_INDICATOR);
CREATE INDEX DW_STUDY_INTERVENTIONAL_MODEL_IDX on dw_study(INTERVENTIONAL_MODEL);
CREATE INDEX DW_STUDY_IRB_APPROVAL_NUMBER_IDX on dw_study(IRB_APPROVAL_NUMBER);
CREATE INDEX DW_STUDY_IRB_APPROVAL_STATUS_IDX on dw_study(IRB_APPROVAL_STATUS);
CREATE INDEX DW_STUDY_IRB_CITY_IDX on dw_study(IRB_CITY);
CREATE INDEX DW_STUDY_IRB_COUNTRY_IDX on dw_study(IRB_COUNTRY);
CREATE INDEX DW_STUDY_IRB_EMAIL_IDX on dw_study(IRB_EMAIL);
CREATE INDEX DW_STUDY_IRB_NAME_IDX on dw_study(IRB_NAME);
CREATE INDEX DW_STUDY_IRB_ORGANIZATION_AFFILIATION_IDX on dw_study(IRB_ORGANIZATION_AFFILIATION);
CREATE INDEX DW_STUDY_IRB_PHONE_IDX on dw_study(IRB_PHONE);
CREATE INDEX DW_STUDY_IRB_STATE_OR_PROVINCE_IDX on dw_study(IRB_STATE_OR_PROVINCE);
CREATE INDEX DW_STUDY_IRB_STREET_ADDRESS_IDX on dw_study(IRB_STREET_ADDRESS);
CREATE INDEX DW_STUDY_IRB_ZIP_CODE_IDX on dw_study(IRB_ZIP_CODE);
CREATE INDEX DW_STUDY_KEYWORD_TEXT_IDX on dw_study(KEYWORD_TEXT);
CREATE INDEX DW_STUDY_LAST_UPDATED_BY_IDX on dw_study(LAST_UPDATED_BY);
CREATE INDEX DW_STUDY_LEAD_ORG_IDX on dw_study(LEAD_ORG);
CREATE INDEX DW_STUDY_LEAD_ORG_ORG_FAMILY_IDX on dw_study(LEAD_ORG_ORG_FAMILY);
CREATE INDEX DW_STUDY_LEAD_ORG_ID_IDX on dw_study(LEAD_ORG_ID);
CREATE INDEX DW_STUDY_MASKING_IDX on dw_study(MASKING);
CREATE INDEX DW_STUDY_MASKING_ROLE_INVESTIGATOR_IDX on dw_study(MASKING_ROLE_INVESTIGATOR);
CREATE INDEX DW_STUDY_MASKING_ROLE_OUTCOME_ASSESSOR_IDX on dw_study(MASKING_ROLE_OUTCOME_ASSESSOR);
CREATE INDEX DW_STUDY_MASKING_ROLE_SUBJECT_IDX on dw_study(MASKING_ROLE_SUBJECT);
CREATE INDEX DW_STUDY_MASKING_ROLE_CAREGIVER_IDX on dw_study(MASKING_ROLE_CAREGIVER);
CREATE INDEX DW_STUDY_MINIMUM_TARGET_ACCRUAL_NUMBER_IDX on dw_study(MINIMUM_TARGET_ACCRUAL_NUMBER);
CREATE INDEX DW_STUDY_NCI_ID_IDX on dw_study(NCI_ID);
CREATE INDEX DW_STUDY_NCT_ID_IDX on dw_Study(NCT_ID);
CREATE INDEX DW_STUDY_CDR_ID_IDX on dw_Study(CDR_ID);
CREATE INDEX DW_STUDY_NUMBER_OF_ARMS_IDX on dw_study(NUMBER_OF_ARMS);
CREATE INDEX DW_STUDY_OVERSIGHT_AUTHORITY_COUNTRY_IDX on dw_study(OVERSIGHT_AUTHORITY_COUNTRY);
CREATE INDEX DW_STUDY_OVERSIGHT_AUTHORITY_ORGANIZATION_NAME_IDX on dw_study(OVERSIGHT_AUTHORITY_ORGANIZATION_NAME);
CREATE INDEX DW_STUDY_PHASE_IDX on dw_study(PHASE);
CREATE INDEX DW_STUDY_PHASE_ADDITIONAL_QUALIFIER_CODE_IDX on dw_study(PHASE_ADDITIONAL_QUALIFIER_CODE);
CREATE INDEX DW_STUDY_PHASE_OTHER_TEXT_IDX on dw_study(PHASE_OTHER_TEXT);
CREATE INDEX DW_STUDY_PRIMARY_COMPLETION_DATE_IDX on dw_study(PRIMARY_COMPLETION_DATE);
CREATE INDEX DW_STUDY_PRIMARY_COMPLETION_DATE_TYPE_CODE_IDX on dw_study(PRIMARY_COMPLETION_DATE_TYPE_CODE);
CREATE INDEX DW_STUDY_PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE_IDX on dw_study(PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE);
CREATE INDEX DW_STUDY_PRIMARY_PURPOSE_CODE_IDX on dw_study(PRIMARY_PURPOSE_CODE);
CREATE INDEX DW_STUDY_PRIMARY_PURPOSE_OTHER_TEXT_IDX on dw_study(PRIMARY_PURPOSE_OTHER_TEXT);
CREATE INDEX DW_STUDY_PRINCIPAL_INVESTIGATOR_IDX on dw_study(PRINCIPAL_INVESTIGATOR);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_IDX on dw_study(PROCESSING_STATUS);
CREATE INDEX DW_STUDY_BRIEF_SUMMARY_IDX on dw_study(BRIEF_SUMMARY);
CREATE INDEX DW_STUDY_BRIEF_TITLE_IDX on dw_study(BRIEF_TITLE);
CREATE INDEX DW_STUDY_RECORD_VERIFICATION_DATE_IDX on dw_study(RECORD_VERIFICATION_DATE);
CREATE INDEX DW_STUDY_REJECTION_REASON_IDX on dw_study(REJECTION_REASON);
CREATE INDEX DW_STUDY_REPORTING_METHOD_DATA_CODE_IDX on dw_study(REPORTING_METHOD_DATA_CODE);
CREATE INDEX DW_STUDY_RESP_PARTY_TYPE_IDX on dw_study(RESP_PARTY_TYPE);
CREATE INDEX DW_STUDY_RESPONSIBLE_PARTY_GENERIC_CONTACT_IDX on dw_study(RESPONSIBLE_PARTY_GENERIC_CONTACT);
CREATE INDEX DW_STUDY_RESPONSIBLE_PARTY_PERSONAL_CONTACT_IDX on dw_study(RESPONSIBLE_PARTY_PERSONAL_CONTACT);
CREATE INDEX DW_STUDY_REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR_IDX on dw_study(REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR);
CREATE INDEX DW_STUDY_SECTION_801_INDICATOR_IDX on dw_study(SECTION_801_INDICATOR);
CREATE INDEX DW_STUDY_SPONSOR_IDX on dw_study(SPONSOR);
CREATE INDEX DW_STUDY_RESPONSIBLE_PARTY_NAME_IDX on dw_study(RESPONSIBLE_PARTY_NAME);
CREATE INDEX DW_STUDY_SPONSOR_ORG_FAMILY_IDX on dw_study(SPONSOR_ORG_FAMILY);
CREATE INDEX DW_STUDY_SPONSOR_RESP_PARTY_EMAIL_IDX on dw_study(SPONSOR_RESP_PARTY_EMAIL);
CREATE INDEX DW_STUDY_SPONSOR_RESP_PARTY_PHONE_IDX on dw_study(SPONSOR_RESP_PARTY_PHONE);
CREATE INDEX DW_STUDY_START_DATE_IDX on dw_study(START_DATE);
CREATE INDEX DW_STUDY_START_DATE_TYPE_CODE_IDX on dw_study(START_DATE_TYPE_CODE);
CREATE INDEX DW_STUDY_SUBMISSION_NUMBER_IDX on dw_study(SUBMISSION_NUMBER);
CREATE INDEX DW_STUDY_SUBMITTER_NAME_IDX on dw_study(SUBMITTER_NAME);
CREATE INDEX DW_STUDY_SUBMITTER_ORGANIZATION_IDX on dw_study(SUBMITTER_ORGANIZATION);
CREATE INDEX DW_STUDY_SUBMITTER_ORGANIZATION_FAMILY_IDX on dw_study(SUBMITTER_ORGANIZATION_FAMILY);
CREATE INDEX DW_STUDY_SUMMARY_4_FUNDING_CATEGORY_IDX on dw_study(SUMMARY_4_FUNDING_CATEGORY);
CREATE INDEX DW_STUDY_SUMMARY_4_FUNDING_SPONSOR_IDX on dw_study(SUMMARY_4_FUNDING_SPONSOR);
CREATE INDEX DW_STUDY_SUMMARY_4_FUNDING_SPONSOR_FAMILY_IDX on dw_study(SUMMARY_4_FUNDING_SPONSOR_FAMILY);
CREATE INDEX DW_STUDY_WHY_STUDY_STOPPED_IDX on dw_study(WHY_STUDY_STOPPED);
CREATE INDEX DW_STUDY_CATEGORY on dw_study(category);

--The below fields are too large for indexing. Need to determine if we need them here or not.
--CREATE INDEX DW_STUDY_OFFICIAL_TITLE_IDX on dw_study(OFFICIAL_TITLE);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_IDX on dw_study(DETAIL_DESCRIPTION);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_PRIMARY_IDX on dw_study(DETAIL_DESCRIPTION_PRIMARY);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_SECONDARY_IDX on dw_study(DETAIL_DESCRIPTION_SECONDARY);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_TERTIARY_IDX on dw_study(DETAIL_DESCRIPTION_TERTIARY);