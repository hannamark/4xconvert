DROP TABLE IF EXISTS DW_STUDY_IND_IDE;
CREATE TABLE DW_STUDY_IND_IDE (
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    EXPANDED_ACCESS_INDICATOR character varying(3),
    EXPANDED_ACCESS_STATUS_CODE character varying(200),
    GRANTOR_CODE character varying(200),
    HOLDER_TYPE_CODE character varying(200),
    IND_IDE_NUMBER character varying(200),
    IND_IDE_TYPE_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIV_PROG_HOLDER_CODE character varying(200),
    NCI_ID character varying(255),
    NIH_INSTHOLDER_CODE character varying(200),
    NIH_INSTHOLDER_NAME character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

CREATE INDEX DW_STUDY_IND_IDE_DATE_LAST_CREATED_IDX on dw_study_ind_ide(date_last_created);
CREATE INDEX DW_STUDY_IND_IDE_DATE_LAST_UPDATED_IDX on dw_study_ind_ide(date_last_updated);
CREATE INDEX DW_STUDY_IND_IDE_EXPANDED_ACCESS_INDICATOR_IDX on dw_study_ind_ide(expanded_access_indicator);
CREATE INDEX DW_STUDY_IND_IDE_EXPANDED_ACCESS_STATUS_CODE_IDX on dw_study_ind_ide(expanded_access_status_code);
CREATE INDEX DW_STUDY_IND_IDE_GRANTOR_CODE_IDX on dw_study_ind_ide(grantor_code);
CREATE INDEX DW_STUDY_IND_IDE_HOLDER_TYPE_CODE_IDX on dw_study_ind_ide(holder_type_code);
CREATE INDEX DW_STUDY_IND_IDE_IND_IDE_NUMBER_IDX on dw_study_ind_ide(ind_ide_number);
CREATE INDEX DW_STUDY_IND_IDE_IND_IDE_TYPE_CODE_IDX on dw_study_ind_ide(ind_ide_type_code);
CREATE INDEX DW_STUDY_IND_IDE_INTERNAL_SYSTEM_ID_IDX on dw_study_ind_ide(internal_system_id);
CREATE INDEX DW_STUDY_IND_IDE_NCI_DIV_PROG_HOLDER_CODE_IDX on dw_study_ind_ide(nci_div_prog_holder_code);
CREATE INDEX DW_STUDY_IND_IDE_NCI_ID_IDX on dw_study_ind_ide(nci_id);
CREATE INDEX DW_STUDY_IND_IDE_NIH_INSTHOLDER_CODE_IDX on dw_study_ind_ide(nih_instholder_code);
CREATE INDEX DW_STUDY_IND_IDE_USER_LAST_CREATED_IDX on dw_study_ind_ide(user_last_created);
CREATE INDEX DW_STUDY_IND_IDE_USER_LAST_UPDATED_IDX on dw_study_ind_ide(user_last_updated);