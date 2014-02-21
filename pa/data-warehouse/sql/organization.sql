DROP TABLE IF EXISTS STG_DW_ORGANIZATION;

CREATE TABLE STG_DW_ORGANIZATION ( 
	ADDRESS_LINE_1 character varying (254),
	ADDRESS_LINE_2 character varying (254),
	POSTAL_CODE character varying (30),
	CITY character varying (50),
	STATE_OR_PROVINCE character varying (255),
	COUNTRY character varying (50),
	CTEP_ID character varying (50),
	CURATOR_COMMENT text,
	NAME character varying (255),
	PO_ID integer,
	STATUS character varying (20),
	STATUS_DATE date,
	EMAIL character varying (256),
	FAX character varying (256),
	PHONE character varying (256),
	TTY character varying (50),
	INTERNAL_ID integer,
	CHANGE_REQUEST_COUNT integer,
	FAMILY character varying (400),
	ORG_TO_FAMILY_RELATIONSHIP character varying (30)
	);

ALTER TABLE stg_dw_organization ADD PRIMARY KEY (po_id);