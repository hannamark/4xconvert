DROP TABLE IF EXISTS DW_STUDY_AMENDMENT;
ALTER TABLE STG_DW_STUDY_AMENDMENT RENAME TO DW_STUDY_AMENDMENT;

DROP TABLE IF EXISTS DW_STUDY_ANATOMIC_SITE;
ALTER TABLE STG_DW_STUDY_ANATOMIC_SITE RENAME TO DW_STUDY_ANATOMIC_SITE;

DROP TABLE IF EXISTS DW_STUDY_ARM_AND_INTERVENTION;
ALTER TABLE STG_DW_STUDY_ARM_AND_INTERVENTION RENAME TO DW_STUDY_ARM_AND_INTERVENTION;

DROP TABLE IF EXISTS DW_STUDY_BIOMARKER;
ALTER TABLE STG_DW_STUDY_BIOMARKER RENAME TO DW_STUDY_BIOMARKER;

DROP TABLE IF EXISTS DW_STUDY_COLLABORATOR;
ALTER TABLE STG_DW_STUDY_COLLABORATOR RENAME TO DW_STUDY_COLLABORATOR;

DROP TABLE IF EXISTS DW_STUDY_DISEASE;
ALTER TABLE STG_DW_STUDY_DISEASE RENAME TO DW_STUDY_DISEASE;

DROP TABLE IF EXISTS DW_STUDY_ELIGIBILITY_CRITERIA;
ALTER TABLE STG_DW_STUDY_ELIGIBILITY_CRITERIA RENAME TO DW_STUDY_ELIGIBILITY_CRITERIA;

DROP TABLE IF EXISTS DW_FAMILY_ORGANIZATION;
ALTER TABLE STG_DW_FAMILY_ORGANIZATION RENAME TO DW_FAMILY_ORGANIZATION;

DROP TABLE IF EXISTS DW_GENERIC_CONTACT;
ALTER TABLE STG_DW_GENERIC_CONTACT RENAME TO DW_GENERIC_CONTACT;

DROP TABLE IF EXISTS DW_STUDY_GRANT;
ALTER TABLE STG_DW_STUDY_GRANT RENAME TO DW_STUDY_GRANT;

DROP TABLE IF EXISTS DW_GRANTS_I2E;
ALTER TABLE STG_DW_GRANTS_I2E RENAME TO DW_GRANTS_I2E;

DROP TABLE IF EXISTS DW_GRANTS_P30;
ALTER TABLE STG_DW_GRANTS_P30 RENAME TO DW_GRANTS_P30;

DROP TABLE IF EXISTS DW_STUDY_IND_IDE;
ALTER TABLE STG_DW_STUDY_IND_IDE RENAME TO DW_STUDY_IND_IDE;

DROP TABLE IF EXISTS DW_STUDY_MILESTONE;
ALTER TABLE STG_DW_STUDY_MILESTONE RENAME TO DW_STUDY_MILESTONE;

DROP TABLE IF EXISTS DW_STUDY_ON_HOLD_STATUS;
ALTER TABLE STG_DW_STUDY_ON_HOLD_STATUS RENAME TO DW_STUDY_ON_HOLD_STATUS;

INSERT INTO DW_ORGANIZATION_AUDIT
  SELECT * FROM STG_DW_ORGANIZATION_AUDIT;
DROP TABLE IF EXISTS STG_DW_ORGANIZATION_AUDIT;

DROP TABLE IF EXISTS DW_STUDY_OTHER_IDENTIFIER;
ALTER TABLE STG_DW_STUDY_OTHER_IDENTIFIER RENAME TO DW_STUDY_OTHER_IDENTIFIER;

DROP TABLE IF EXISTS DW_STUDY_OUTCOME_MEASURE;
ALTER TABLE STG_DW_STUDY_OUTCOME_MEASURE RENAME TO DW_STUDY_OUTCOME_MEASURE;

DROP TABLE IF EXISTS DW_STUDY_OVERALL_STATUS;
ALTER TABLE STG_DW_STUDY_OVERALL_STATUS RENAME TO DW_STUDY_OVERALL_STATUS;

DROP TABLE IF EXISTS DW_STUDY_PARTICIPATING_SITE CASCADE;
ALTER TABLE STG_DW_STUDY_PARTICIPATING_SITE RENAME TO DW_STUDY_PARTICIPATING_SITE;

DROP TABLE IF EXISTS DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS CASCADE;
ALTER TABLE STG_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS RENAME TO DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS;

INSERT INTO DW_PERSON_AUDIT
  SELECT * FROM STG_DW_PERSON_AUDIT;
DROP TABLE IF EXISTS STG_DW_PERSON_AUDIT;

DROP TABLE IF EXISTS DW_PERSON_ROLE;
ALTER TABLE STG_DW_PERSON_ROLE RENAME TO DW_PERSON_ROLE;

DROP TABLE IF EXISTS DW_PERSON;
ALTER TABLE STG_DW_PERSON RENAME TO DW_PERSON;

DROP TABLE IF EXISTS DW_STUDY_PROCESSING_STATUS;
ALTER TABLE STG_DW_STUDY_PROCESSING_STATUS RENAME TO DW_STUDY_PROCESSING_STATUS;

DROP TABLE IF EXISTS DW_STUDY_RECORD_OWNER;
ALTER TABLE STG_DW_STUDY_RECORD_OWNER RENAME TO DW_STUDY_RECORD_OWNER;

INSERT INTO DW_STUDY_AUDIT
  SELECT * FROM STG_DW_STUDY_AUDIT;
DROP TABLE IF EXISTS STG_DW_STUDY_AUDIT;

DROP TABLE IF EXISTS DW_STUDY;
ALTER TABLE STG_DW_STUDY RENAME TO DW_STUDY;

DROP TABLE IF EXISTS DW_STUDY_SUBGROUP;
ALTER TABLE STG_DW_STUDY_SUBGROUP RENAME TO DW_STUDY_SUBGROUP;

DROP TABLE IF EXISTS DW_ORGANIZATION;
ALTER TABLE STG_DW_ORGANIZATION RENAME TO DW_ORGANIZATION;

DROP TABLE IF EXISTS DW_ORGANIZATION_ROLE;
ALTER TABLE STG_DW_ORGANIZATION_ROLE RENAME TO DW_ORGANIZATION_ROLE;

DROP TABLE IF EXISTS DW_AFFILIATE_ORG;
ALTER TABLE STG_DW_AFFILIATE_ORG RENAME TO DW_AFFILIATE_ORG;

DROP TABLE IF EXISTS DW_USER;
ALTER TABLE STG_DW_USER RENAME TO DW_USER;

DROP TABLE IF EXISTS DW_STUDY_SITE_ACCRUAL_ACCESS;
ALTER TABLE STG_DW_STUDY_SITE_ACCRUAL_ACCESS RENAME TO DW_STUDY_SITE_ACCRUAL_ACCESS;

DROP TABLE IF EXISTS DW_STUDY_ACCRUAL_COUNT;
ALTER TABLE STG_DW_STUDY_ACCRUAL_COUNT RENAME TO DW_STUDY_ACCRUAL_COUNT;

DROP TABLE IF EXISTS DW_STUDY_SITE_ACCRUAL_DETAILS;
ALTER TABLE STG_DW_STUDY_SITE_ACCRUAL_DETAILS RENAME TO DW_STUDY_SITE_ACCRUAL_DETAILS;

DROP TABLE IF EXISTS DW_ACCRUAL_BATCH_SUBMISSION;
ALTER TABLE STG_DW_ACCRUAL_BATCH_SUBMISSION RENAME TO DW_ACCRUAL_BATCH_SUBMISSION;

DROP TABLE IF EXISTS DW_STUDY_FEWER_INDEXES;
CREATE TABLE DW_STUDY_FEWER_INDEXES AS SELECT * FROM DW_STUDY;
COMMENT ON TABLE DW_STUDY_FEWER_INDEXES IS 'This is an exact copy of the dw_study table. It is updated every time the dw_study table is updated. The only difference is that it has fewer indexes to facilitate importing it into MS Access for ad-hoc reporting. MS Access has a limit of 32 indexes per table';

DROP TABLE IF EXISTS DW_DATA_TABLE_4;
ALTER TABLE STG_DW_DATA_TABLE_4 RENAME TO DW_DATA_TABLE_4;

DROP TABLE IF EXISTS DW_STUDY_ASSOCIATION;
ALTER TABLE STG_DW_STUDY_ASSOCIATION RENAME TO DW_STUDY_ASSOCIATION;

DROP TABLE IF EXISTS DW_ASSAY_TYPE;
ALTER TABLE STG_DW_ASSAY_TYPE RENAME TO DW_ASSAY_TYPE;

DROP TABLE IF EXISTS DW_BIOMARKER_PURPOSE;
ALTER TABLE STG_DW_BIOMARKER_PURPOSE RENAME TO DW_BIOMARKER_PURPOSE;

DROP TABLE IF EXISTS DW_BIOMARKER_USE;
ALTER TABLE STG_DW_BIOMARKER_USE RENAME TO DW_BIOMARKER_USE;

DROP TABLE IF EXISTS DW_EVALUATION_TYPE;
ALTER TABLE STG_DW_EVALUATION_TYPE RENAME TO DW_EVALUATION_TYPE;

DROP TABLE IF EXISTS DW_SPECIMEN_COLLECTION;
ALTER TABLE STG_DW_SPECIMEN_COLLECTION RENAME TO DW_SPECIMEN_COLLECTION;

DROP TABLE IF EXISTS DW_SPECIMEN_TYPE;
ALTER TABLE STG_DW_SPECIMEN_TYPE RENAME TO DW_SPECIMEN_TYPE;

DROP TABLE IF EXISTS DW_STUDY_SECONDARY_PURPOSE;
ALTER TABLE STG_DW_STUDY_SECONDARY_PURPOSE RENAME TO DW_STUDY_SECONDARY_PURPOSE;
