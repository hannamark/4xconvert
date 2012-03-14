DROP TABLE IF EXISTS STG_DW_ORGANIZATION_AUDIT;

CREATE TABLE STG_DW_ORGANIZATION_AUDIT ( 
	CTEP_ID character varying (50),
	NAME character varying (255),
	PO_ID integer,
	DATE timestamp,
	USERNAME character varying (255)
	)
	;