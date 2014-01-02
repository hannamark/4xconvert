DROP TABLE IF EXISTS STG_DW_STUDY CASCADE;
CREATE TABLE STG_DW_STUDY (
    ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR character varying(3),
    ACRONYM  character varying(200),
    MASKING_ALLOCATION_CODE character varying(200),
    AMENDMENT_DATE timestamp,
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
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
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
    KEYWORD_TEXT character varying(4000),
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
    PROGRAM_CODE character varying(100),
    PROCESSING_STATUS character varying(200),
    BRIEF_SUMMARY character varying(5000),
    BRIEF_TITLE character varying(300),
    RECORD_VERIFICATION_DATE date,
    REJECTION_REASON character varying(2000),
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
    WHY_STUDY_STOPPED character varying(2000),
    CATEGORY character varying (12),
    COMMENTS varchar(4000),
    PROCESSING_PRIORITY int,
    CTRO_OVERRIDE boolean,
    BIO_SPECIMEN_DESCRIPTION character varying(800),
    BIO_SPECIMEN_RETENTION_CODE character varying(200),
    SAMPLING_METHOD_CODE CHARACTER varying(200),
    STUDY_MODEL_CODE character varying(200),
    STUDY_MODEL_OTHER_TEXT character varying(200),
    STUDY_POPULATION_DESCRIPTION character varying(800),
    TIME_PERSPECTIVE_CODE character varying(200),
    TIME_PERSPECTIVE_OTHER_TEXT character varying(200),
    STUDY_PROTOCOL_TYPE character varying(100),
    STUDY_SUBTYPE_CODE character varying(64),
    CONSORTIA_TRIAL_CATEGORY character varying(50),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
); 

--The below fields are too large for indexing. Need to determine if we need them here or not.
--CREATE INDEX STG_DW_STUDY_OFFICIAL_TITLE_IDX on STG_DW_study(OFFICIAL_TITLE);
--CREATE INDEX STG_DW_STUDY_DETAIL_DESCRIPTION_IDX on STG_DW_study(DETAIL_DESCRIPTION);
--CREATE INDEX STG_DW_STUDY_DETAIL_DESCRIPTION_PRIMARY_IDX on STG_DW_study(DETAIL_DESCRIPTION_PRIMARY);
--CREATE INDEX STG_DW_STUDY_DETAIL_DESCRIPTION_SECONDARY_IDX on STG_DW_study(DETAIL_DESCRIPTION_SECONDARY);
--CREATE INDEX STG_DW_STUDY_DETAIL_DESCRIPTION_TERTIARY_IDX on STG_DW_study(DETAIL_DESCRIPTION_TERTIARY);