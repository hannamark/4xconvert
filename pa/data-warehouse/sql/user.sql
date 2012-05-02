DROP TABLE IF EXISTS STG_DW_USER;

CREATE TABLE STG_DW_USER ( 
	CSM_USER_ID integer NOT NULL,
	LOGIN_NAME character varying(500) NOT NULL,
	NAME character varying (500),
	FIRST_NAME character varying (500),
	LAST_NAME character varying (500)
	)
	;