DROP TABLE IF EXISTS DW_STUDY_BIOMARKER;
CREATE TABLE DW_STUDY_BIOMARKER (
    ASSAY_PURPOSE character varying(200),
    ASSAY_PURPOSE_DESCRIPTION character varying(200),
    ASSAY_TYPE_CODE character varying(200),
    ASSAY_TYPE_DESCRIPTION character varying(200),
    ASSAY_USE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    LONG_NAME character varying(1000),
    NCI_ID character varying(255),
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
CREATE INDEX DW_STUDY_BIOMARKER_NCI_ID_IDX on dw_study_biomarker(nci_id);
CREATE INDEX DW_STUDY_BIOMARKER_TISSUE_COLLECTION_METHOD_CODE_IDX on dw_study_biomarker(tissue_collection_method_code);
CREATE INDEX DW_STUDY_BIOMARKER_TISSUE_SPECIMEN_TYPE_CODE_IDX on dw_study_biomarker(tissue_specimen_type_code);

INSERT INTO DW_STUDY_BIOMARKER (
    ASSAY_PURPOSE,
    ASSAY_PURPOSE_DESCRIPTION,
    ASSAY_TYPE_CODE,
    ASSAY_TYPE_DESCRIPTION,
    ASSAY_USE,
    INTERNAL_SYSTEM_ID,
    LONG_NAME,
    NCI_ID,
    TISSUE_COLLECTION_METHOD_CODE,
    TISSUE_SPECIMEN_TYPE_CODE
) select marker.assay_purpose_code, marker.assay_purpose_other_text, marker.assay_type_code, marker.assay_type_other_text,
         marker.assay_use_code, marker.identifier, marker.long_name, nci_id.extension, marker.tissue_collection_method_code, 
         marker.tissue_specimen_type_code
    from planned_activity pa
        inner join study_protocol as sp on sp.identifier = pa.study_protocol_identifier
        inner join planned_marker as marker on marker.identifier = pa.identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = pa.study_protocol_identifier
                and nci_id.root = '2.16.840.1.113883.3.26.4.3'
    where sp.status_code = 'ACTIVE';