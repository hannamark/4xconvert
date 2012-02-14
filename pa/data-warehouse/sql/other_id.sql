DROP TABLE IF EXISTS DW_STUDY_OTHER_IDENTIFIER;

CREATE TABLE DW_STUDY_OTHER_IDENTIFIER (
	NAME character varying(500),
    NCI_ID character varying(255),
    VALUE character varying(500)
);

CREATE INDEX DW_STUDY_OTHER_IDENTIFIER_IDX on dw_study_other_identifier(name);
CREATE INDEX DW_STUDY_OTHER_NCI_ID_IDX on dw_study_other_identifier(nci_id);
CREATE INDEX DW_STUDY_OTHER_VALUE_IDX on dw_study_other_identifier(value);

