DROP TABLE IF EXISTS STG_DW_PERSON_ROLE;

CREATE TABLE STG_DW_PERSON_ROLE (
	ROLE_NAME character varying (50),
	ADDRESS_LINE_1 character varying (254),
	ADDRESS_LINE_2 character varying (254),
	POSTAL_CODE character varying (30),
	BIRTHDATE date,
	CITY character varying (50),
	COUNTRY character varying (50),
	CTEP_ID character varying (50),
	CURATOR_COMMENT text,
	NAME character varying (255),
	PO_ID integer,
	PREFIX character varying (10),
	STATUS character varying (20),
	STATUS_DATE date,
	SEX_CODE character varying (20),
	STATE_OR_PROVINCE character varying (255),
	SUFFIX character varying (10),
	EMAIL character varying (256),
	FAX character varying (256),
	PHONE character varying (256),
	TTY character varying (50)
	)
	;