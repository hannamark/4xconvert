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