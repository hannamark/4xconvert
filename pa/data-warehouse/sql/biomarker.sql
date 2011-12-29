DROP TABLE IF EXISTS DW_STUDY_BIOMARKER;
CREATE TABLE DW_STUDY_BIOMARKER (
    ASSAY_PURPOSE character varying(200),
    ASSAY_PURPOSE_DESCRIPTION character varying(200),
    ASSAY_TYPE_CODE character varying(200),
    ASSAY_TYPE_DESCRIPTION character varying(200),
    ASSAY_USE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    LONG_NAME character varying(1000),
    NAME character varying(200),
    NCI_ID character varying(255),
    STATUS_CODE character varying(200),
    TISSUE_COLLECTION_METHOD_CODE character varying(200),
    TISSUE_SPECIMEN_TYPE_CODE character varying(200),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

CREATE INDEX DW_STUDY_BIOMARKER_ASSAY_PURPOSE_IDX on dw_study_biomarker(assay_purpose);
CREATE INDEX DW_STUDY_BIOMARKER_ASSAY_PURPOSE_DESCRIPTION_IDX on dw_study_biomarker(assay_purpose_description);
CREATE INDEX DW_STUDY_BIOMARKER_ASSAY_TYPE_CODE_IDX on dw_study_biomarker(assay_type_code);
CREATE INDEX DW_STUDY_BIOMARKER_ASSAY_TYPE_DESCRIPTION_IDX on dw_study_biomarker(assay_type_description);
CREATE INDEX DW_STUDY_BIOMARKER_ASSAY_USE_IDX on dw_study_biomarker(assay_use);
CREATE INDEX DW_STUDY_BIOMARKER_INTERNAL_SYSTEM_ID_IDX on dw_study_biomarker(internal_system_id);
CREATE INDEX DW_STUDY_BIOMARKER_LONG_NAME_IDX on dw_study_biomarker(long_name);
CREATE INDEX DW_STUDY_BIOMARKER_NAME_IDX on dw_study_biomarker(name);
CREATE INDEX DW_STUDY_BIOMARKER_NCI_ID_IDX on dw_study_biomarker(nci_id);
CREATE INDEX DW_STUDY_BIOMARKER_STATUS_CODE_IDX on dw_study_biomarker(status_code);
CREATE INDEX DW_STUDY_BIOMARKER_TISSUE_COLLECTION_METHOD_CODE_IDX on dw_study_biomarker(tissue_collection_method_code);
CREATE INDEX DW_STUDY_BIOMARKER_TISSUE_SPECIMEN_TYPE_CODE_IDX on dw_study_biomarker(tissue_specimen_type_code);