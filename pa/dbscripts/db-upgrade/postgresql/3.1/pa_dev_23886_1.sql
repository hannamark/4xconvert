DROP TABLE IF EXISTS DOSE_FREQUENCY CASCADE;

CREATE TABLE DOSE_FREQUENCY  (
	IDENTIFIER SERIAL NOT NULL,
	CODE VARCHAR(200) NOT NULL,
	DISPLAY_NAME VARCHAR(1000) NOT NULL,	
	DESCRIPTION VARCHAR(2000),
	CONCEPT_ID VARCHAR(200),
	PUBLIC_ID  VARCHAR(200),
	CODING_SYSTEM  VARCHAR(200),
	CODING_SYSTEM_NAME  VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
 	PRIMARY KEY (IDENTIFIER)
);

ALTER TABLE DOSE_FREQUENCY ADD CONSTRAINT FK_DOSE_FREQUENCY_CODE  UNIQUE (CODE); 

DROP TABLE IF EXISTS DOSE_FORM CASCADE;

CREATE TABLE DOSE_FORM  (
	IDENTIFIER SERIAL NOT NULL,
	CODE  VARCHAR(200) NOT NULL,
	DISPLAY_NAME VARCHAR(1000) NOT NULL,	
	DESCRIPTION VARCHAR(2000),
	CONCEPT_ID VARCHAR(200),
	PUBLIC_ID  VARCHAR(200),
	CODING_SYSTEM  VARCHAR(200),
	CODING_SYSTEM_NAME  VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
 	PRIMARY KEY (IDENTIFIER)
);

ALTER TABLE DOSE_FORM ADD CONSTRAINT FK_DOSE_FORM_CODE  UNIQUE (CODE); 

DROP TABLE IF EXISTS ROUTE_OF_ADMINISTRATION CASCADE;

CREATE TABLE ROUTE_OF_ADMINISTRATION  (
	IDENTIFIER SERIAL NOT NULL,
	CODE  VARCHAR(200) NOT NULL,
	DISPLAY_NAME VARCHAR(1000) NOT NULL,	
	DESCRIPTION VARCHAR(2000),
	CONCEPT_ID VARCHAR(200),
	PUBLIC_ID  VARCHAR(200),
	CODING_SYSTEM  VARCHAR(200),
	CODING_SYSTEM_NAME  VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
 	PRIMARY KEY (IDENTIFIER)
);

ALTER TABLE ROUTE_OF_ADMINISTRATION ADD CONSTRAINT FK_ROUTE_OF_ADMINISTRATION_CODE  UNIQUE (CODE); 

DROP TABLE IF EXISTS UNIT_OF_MEASUREMENT CASCADE;

CREATE TABLE UNIT_OF_MEASUREMENT  (
	IDENTIFIER SERIAL NOT NULL,
	CODE  VARCHAR(200) NOT NULL,
	DISPLAY_NAME VARCHAR(1000) NOT NULL,	
	DESCRIPTION VARCHAR(2000),
	CONCEPT_ID VARCHAR(200),
	PUBLIC_ID  VARCHAR(200),
	CODING_SYSTEM  VARCHAR(200),
	CODING_SYSTEM_NAME  VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
 	PRIMARY KEY (IDENTIFIER)
);

ALTER TABLE UNIT_OF_MEASUREMENT ADD CONSTRAINT FK_UNIT_OF_MEASUREMENT_CODE  UNIQUE (CODE); 
