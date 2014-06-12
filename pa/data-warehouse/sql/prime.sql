/*
Script needs to be run on any tiers which have data warehouse after refresh from production
*/


CREATE TABLE DW_STUDY_AUDIT ( 
	NCI_ID character varying (50),
	DATE timestamp,
	USERNAME character varying (255),
	FIRST_NAME character varying(255),
	LAST_NAME character varying(255),
	TYPE character varying(255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;
CREATE TABLE DW_ORGANIZATION_AUDIT ( 
	CTEP_ID character varying (50),
	NAME character varying (255),
	PO_ID integer,
	DATE timestamp,
	USERNAME character varying (255),
	INTERNAL_SYSTEM_ID INTEGER
	)
	;
CREATE TABLE DW_PERSON_AUDIT ( 
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

