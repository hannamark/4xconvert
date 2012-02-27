DROP TABLE IF EXISTS DW_STUDY_SUBGROUP;
CREATE TABLE DW_STUDY_SUBGROUP (
    DESCRIPTION character varying(1000),
    GROUP_CODE character varying(256),
    NCI_ID character varying(255)
);

CREATE INDEX DW_STUDY_DESC_IDX on dw_study_subgroup(description);
CREATE INDEX DW_STUDY_GROUP_CODE_IDX on dw_study_subgroup(group_code);
CREATE INDEX DW_STUDY_NCI_IDX on dw_study_subgroup(nci_id);
