--PO-5935 : ClinicalTrials.gov Import Logs

DROP TABLE IF EXISTS CTGOVIMPORT_LOG;

CREATE TABLE CTGOVIMPORT_LOG
(
	IDENTIFIER SERIAL NOT NULL,
	NCI_ID VARCHAR(20) NOT NULL,
	NCT_ID VARCHAR(20) NOT NULL,
	TRIAL_TITLE VARCHAR(4000),
	ACTION_PERFORMED VARCHAR(20),
	IMPORT_STATUS VARCHAR(200),
	USER_CREATED VARCHAR(200),
	DATE_CREATED TIMESTAMP,
	PRIMARY KEY (IDENTIFIER) 
) ;