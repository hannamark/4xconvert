DROP TABLE IF EXISTS STG_DW_STUDY_OUTCOME_MEASURE;
CREATE TABLE STG_DW_STUDY_OUTCOME_MEASURE (
    DESCRIPTION character varying(1000),
    name character varying(2000),
    NCI_ID character varying(255),
    timeframe character varying(255),
    primary_indicator boolean,
    safety_indicator boolean,
	date_created timestamp,
	date_updated timestamp,
	username_created character varying (256),
	username_updated character varying (256),
    FIRST_NAME_CREATED character varying(500),
    LAST_NAME_CREATED character varying(500),
    FIRST_NAME_LAST_UPDATED character varying(500),
    LAST_NAME_LAST_UPDATED character varying(500)
    );

