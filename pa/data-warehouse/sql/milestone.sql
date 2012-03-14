DROP TABLE IF EXISTS STG_DW_STUDY_MILESTONE;

CREATE TABLE STG_DW_STUDY_MILESTONE (
    COMMENTS character varying(200),
    DATE timestamp,
    DATE_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    INTERNAL_SYSTEM_ID INTEGER not null,
	NAME character varying (50),
    NCI_ID character varying(255),
    USER_NAME_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500)
);

