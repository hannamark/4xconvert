DROP TABLE IF EXISTS STG_DW_PERSON_AUDIT;

CREATE TABLE STG_DW_PERSON_AUDIT ( 
	CTEP_ID character varying (50),
	NAME character varying (255),
	PO_ID integer,
	DATE timestamp,
	USERNAME character varying (255),
	FIRST_NAME character varying(255),
	LAST_NAME character varying(255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;
