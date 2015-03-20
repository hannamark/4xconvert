DROP TABLE IF EXISTS STG_DW_STUDY_PARTICIPATING_SITE CASCADE;
CREATE TABLE STG_DW_STUDY_PARTICIPATING_SITE (
    CONTACT_EMAIL character varying(200),
    CONTACT_NAME character varying(600),
    CONTACT_PHONE character varying(200),
    GENERIC_CONTACT character varying(255),
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    ORG_NAME character varying(200),
    ORG_ORG_FAMILY character varying(200),
    RECRUITMENT_STATUS character varying(50),
    RECRUITMENT_STATUS_DATE date,
    TARGET_ACCRUAL integer,
    ORG_PO_ID integer,
    PROGRAM_CODE character varying(200),
    LOCAL_SITE_IDENTIFIER character varying(200),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);

