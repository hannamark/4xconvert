DROP TABLE IF EXISTS STG_DW_STUDY_SUBGROUP;
CREATE TABLE STG_DW_STUDY_SUBGROUP (
    DESCRIPTION character varying(1000),
    GROUP_CODE character varying(256),
    NCI_ID character varying(255)
);
