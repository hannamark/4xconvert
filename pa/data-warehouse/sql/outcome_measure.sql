DROP TABLE IF EXISTS DW_STUDY_OUTCOME_MEASURE;
CREATE TABLE DW_STUDY_OUTCOME_MEASURE (
    DESCRIPTION character varying(1000),
    name character varying(256),
    NCI_ID character varying(255),
    timeframe character varying(255),
    primary_indicator boolean,
    safety_indicator boolean,
	date_created timestamp,
	date_updated timestamp,
	username_created character varying (256),
	username_updated character varying (256)
    );

CREATE INDEX DW_STUDY_OUTCOME_DESC_IDX on dw_study_outcome_measure(description);
CREATE INDEX DW_STUDY_OUTCOME_NCI_IDX on dw_study_outcome_measure(description);
