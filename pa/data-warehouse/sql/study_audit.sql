DROP TABLE IF EXISTS STG_DW_STUDY_AUDIT;

CREATE TABLE STG_DW_STUDY_AUDIT ( 
	NCI_ID character varying (50),
	DATE timestamp,
	USERNAME character varying (255),
	TYPE character varying (30)
	)
	;