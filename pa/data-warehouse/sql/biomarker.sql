DROP TABLE IF EXISTS STG_DW_STUDY_BIOMARKER;
CREATE TABLE STG_DW_STUDY_BIOMARKER (
    ASSAY_PURPOSE character varying(200),
    ASSAY_PURPOSE_DESCRIPTION character varying(200),
    ASSAY_TYPE_CODE character varying(200),
    ASSAY_TYPE_DESCRIPTION character varying(200),
    ASSAY_USE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    LONG_NAME character varying(1000),
    NAME character varying(1000),
    NCI_ID character varying(255),
    STATUS_CODE character varying(200),
    TISSUE_COLLECTION_METHOD_CODE character varying(200),
    TISSUE_SPECIMEN_TYPE_CODE character varying(200),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

