DROP TABLE IF EXISTS STG_DW_USER;

CREATE TABLE STG_DW_USER (
    AFFILIATED_ORGANIZATION character varying(255),
    CITY character varying(200),
    COUNTRY character varying(200),
    CTRP_ACCESS_PRIVILEGES character varying(2000),
    DATE_LAST_CREATED timestamp without time zone,
    DATE_LAST_UPDATED timestamp without time zone,
    EMAIL character varying(255),
    EMAIL_NOTIFICATION_REQUIRED character varying(16),
    FIRST_NAME character varying (200),
    LAST_NAME character varying (200),
    MIDDLE_INITIAL character varying(1),
    PHONE character varying(200),
    POSTAL_CODE character varying(200),
    PRS_ORGANIZATION character varying(200),
    SITE_ADMIN character varying(64),
    STATE character varying(200),
    STREET_ADDRESS character varying(200),
    USER_NAME character varying(500),
    USER_NAME_LAST_CREATED character varying(500),
    USER_NAME_LAST_UPDATED character varying(500),
    INTERNAL_SYSTEM_ID integer,
	CSM_USER_ID integer NOT NULL,
	LOGIN_NAME character varying(500) NOT NULL,
	NAME character varying (500),
    AFFILIATED_ORGANIZATION_ID bigint,
    USER_LAST_CREATED_ID bigint,
    USER_LAST_UPDATED_ID bigint,
    PRIMARY KEY (CSM_USER_ID)
	)
	;