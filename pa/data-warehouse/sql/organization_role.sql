DROP TABLE IF EXISTS STG_DW_ORGANIZATION_ROLE;

CREATE TABLE STG_DW_ORGANIZATION_ROLE ( 
	ADDRESS_LINE_1 character varying (254),
	ADDRESS_LINE_2 character varying (254),
	POSTAL_CODE character varying (30),
	CITY character varying (50),
	STATE_OR_PROVINCE character varying (255),
	COUNTRY character varying (50),
	CTEP_ID character varying (50),
	NAME character varying (255),
	ROLE_NAME character varying (30),
	PO_ID integer,
	STATUS character varying (20),
	STATUS_DATE date,
	SUFFIX character varying (10),
	EMAIL character varying (256),
	FAX character varying (256),
	PHONE character varying (256),
	TTY character varying (50),
	INTERNAL_ID integer
	)
;