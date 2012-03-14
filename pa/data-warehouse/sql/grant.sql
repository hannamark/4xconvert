DROP TABLE IF EXISTS STG_DW_STUDY_GRANT;
CREATE TABLE STG_DW_STUDY_GRANT (
    ACTIVE_INDICATOR character varying(3),
    FUNDING_MECHANISM_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIVISION_OR_PROGRAM character varying(200),
    NCI_ID character varying(255),
    NIH_INSTITUTION_CODE character varying(200),
    SERIAL_NUMBER character varying(6),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

