DROP TABLE IF EXISTS DW_STUDY_AUDIT;

CREATE TABLE DW_STUDY_AUDIT ( 
	NCI_ID character varying (50),
	DATE timestamp,
	USERNAME character varying (255),
	TYPE character varying (30)
	)
	;