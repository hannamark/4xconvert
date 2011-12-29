DROP TABLE IF EXISTS DW_STUDY_GRANT;
CREATE TABLE DW_STUDY_GRANT (
    ACTIVE_INDICATOR character varying(3),
    FUNDING_MECHANISM_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIVISION_OR_PROGRAM character varying(200),
    NCI_ID character varying(255),
    NIH_INSTITUTION_CODE character varying(200),
    SERIAL_NUMBER character varying(6),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

CREATE INDEX DW_STUDY_GRANT_ACTIVE_INDICATOR_IDX on dw_study_grant(active_indicator);
CREATE INDEX DW_STUDY_GRANT_FUNDING_MECHANISM_CODE_IDX on dw_study_grant(funding_mechanism_code);
CREATE INDEX DW_STUDY_GRANT_INTERNAL_SYSTEM_ID_IDX on dw_study_grant(internal_system_id);
CREATE INDEX DW_STUDY_GRANT_NCI_DIVISION_OR_PROGRAM_IDX on dw_study_grant(nci_division_or_program);
CREATE INDEX DW_STUDY_GRANT_NCI_ID_IDX on dw_study_grant(NCI_ID);
CREATE INDEX DW_STUDY_GRANT_NIH_INSTITUTION_CODE_IDX on dw_study_grant(nih_institution_code);
CREATE INDEX DW_STUDY_GRANT_SERIAL_NUMBER_IDX on dw_study_grant(serial_number);