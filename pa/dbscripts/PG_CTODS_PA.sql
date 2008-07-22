DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER_ROLES ;
DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER  ;
DROP TABLE IF EXISTS DOCUMENT_IDENTIFICATION ;
DROP TABLE IF EXISTS STUDY_CONDITIONS ;
DROP TABLE IF EXISTS STUDY_OVERALL_STATUS ;
DROP TABLE IF EXISTS DOCUMENT_WORKFLOW_STATUS ;
DROP TABLE IF EXISTS DOCUMENT ;
DROP TABLE IF EXISTS STUDY_PROTOCOL ;
DROP TABLE IF EXISTS ORGANIZATION ;
DROP TABLE IF EXISTS CONDITIONS ;
DROP TABLE IF EXISTS HIBERNATE_SEQUENCE ;
DROP TABLE IF EXISTS SESSIONENTRY ;

CREATE SEQUENCE HIBERNATE_SEQUENCE
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 27425
  CACHE 1;

CREATE TABLE SESSIONENTRY
(
  id bigint NOT NULL,
  accesstime bigint NOT NULL,
  createdtime bigint NOT NULL,
  refererapp character varying(256) NOT NULL,
  remoteuser character varying(256) NOT NULL,
  returnurl character varying(2048) NOT NULL,
  secret bigint NOT NULL,
  CONSTRAINT sessionentry_pkey PRIMARY KEY (id),
  CONSTRAINT sessionentry_secret_key UNIQUE (secret)
);


CREATE TABLE DOCUMENT (
    ID BIGINT NOT NULL	,
    OFFICIAL_TITLE VARCHAR(500) ,
    PRIMARY KEY (ID)
);

CREATE TABLE DOCUMENT_IDENTIFICATION ( 
	ID BIGINT NOT NULL	,
	ASSIGNING_AUTHORITY_CODE VARCHAR(200) ,
        PRIMARY_INDICATOR BOOLEAN , 
	IDENTIFIER VARCHAR(200) , 
	DOCUMENT_ID BIGINT NOT NULL		,   
	PRIMARY KEY (ID)
) ;

CREATE TABLE STUDY_PROTOCOL (
        ID BIGINT NOT NULL	,
        NCI_ACCESSION_NUMBER VARCHAR(200) ,
        ACRONYM VARCHAR(200) ,
        MONITOR_CODE VARCHAR(200) ,
        PHASE_CODE VARCHAR(200) ,
        BIOSPECIMEN_DESCRIPTION VARCHAR(200) ,
        BIOSPECIMEN_RETENTION_CODE VARCHAR(200) ,
        REPORTING_DATASET_METHOD_CODE VARCHAR(200) ,
        GROUP_NUMBER INTEGER ,
        STUDY_MODEL_CODE VARCHAR(200) ,
        TIME_PRESPECTIVE_CODE VARCHAR(200) ,
        ALLOCATION_CODE VARCHAR(200) ,
        CONTROL_CONCURRENCY_TYPE_CODE  VARCHAR(200) ,
        CONTROL_TYPE_CODE  VARCHAR(200) ,
        DELAYED_POSTING_INDICATOR  BOOLEAN ,
        DESIGN_CONFIGURATION_CODE VARCHAR(200) ,
        FDA_REGULATED_INDICATOR BOOLEAN , 
        INTERVENTION_TYPE_CODE VARCHAR(200) ,
        NUMBER_OF_INTERVENTION_GROUPS INTEGER ,
        TYPE VARCHAR(200)  , 
        SECTION801_INDICATOR BOOLEAN ,
        PRIMARY KEY (ID)
);

CREATE TABLE STUDY_OVERALL_STATUS ( 
	ID SERIAL NOT NULL	,
	STATUS_CODE VARCHAR(200)		,   	
	STATUS_DATE TIMESTAMP,    		
	STUDY_PROTOCOL_ID BIGINT NOT NULL		,    
	PRIMARY KEY (ID)
) 
;

CREATE TABLE DOCUMENT_WORKFLOW_STATUS ( 
	ID SERIAL NOT NULL	,
	STATUS_CODE VARCHAR(200)		,   	
	STATUS_DATE TIMESTAMP,    		
	STUDY_PROTOCOL_ID BIGINT NOT NULL		,    
	PRIMARY KEY (ID)
) 
;


CREATE TABLE ORGANIZATION ( 	
	ID  SERIAL NOT NULL,
	NAME VARCHAR(200),
	NCI_INSTITUTE_CODE VARCHAR(200),
	PRIMARY KEY (ID)
);

-- using conditions becasue condtion is a key word
CREATE TABLE CONDITIONS (
	ID  SERIAL NOT NULL,
	NAME VARCHAR(200),
	CODE VARCHAR(200),
	PARENT_CODE VARCHAR(200) ,
	CREATED_BY VARCHAR(200) ,
	CREATED_DATE DATE ,
	UPDATED_BY VARCHAR(200) ,
	UPDATED_DATE DATE ,
	PRIMARY KEY (ID)
);

CREATE TABLE STUDY_CONDITIONS (
	ID  SERIAL NOT NULL,
	LEAD_INDICATOR VARCHAR(200),
	CODE VARCHAR(200),
	CONDITIONS_ID BIGINT NOT NULL	,    	
	STUDY_PROTOCOL_ID BIGINT NOT NULL		,    	
	PRIMARY KEY (ID)
);


CREATE TABLE STUDY_COORDINATING_CENTER (
	ID  SERIAL NOT NULL,
        STUDY_PROTOCOL_ID BIGINT NOT NULL ,  
        ORGANIZATION_ID BIGINT NOT NULL	,
        TELEPHONE VARCHAR(200),
        PRIMARY KEY (ID)
) ;



CREATE TABLE STUDY_COORDINATING_CENTER_ROLES (
	ID  SERIAL NOT NULL,
	RESPONSIBILITY_CODE VARCHAR(200),
        STUDY_COORDINATING_CENTER_ID BIGINT NOT NULL ,  
        PRIMARY KEY (ID)
) ;


ALTER TABLE DOCUMENT_IDENTIFICATION ADD CONSTRAINT FK_DOCUMENT_IDENTIFICATION
FOREIGN KEY (DOCUMENT_ID) REFERENCES DOCUMENT (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_OVERALL_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_OVERALL_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE DOCUMENT_WORKFLOW_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_DOCUMENT_WORKFLOW_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONDITIONS ADD CONSTRAINT FK_CONDITIONS_STUDY_CONDITIONS 
FOREIGN KEY (CONDITIONS_ID) REFERENCES CONDITIONS (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONDITIONS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_CONDITIONS 
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_COORDINATING_CENTER ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_COORDINATING_CENTER
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_COORDINATING_CENTER ADD CONSTRAINT FK_ORGANIZATION_STUDY_COORDINATING_CENTER
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_COORDINATING_CENTER_ROLES ADD CONSTRAINT FK_STUDY_COORDINATING_CENTER_ROLES_STUDY_COORDINATING_CENTER
FOREIGN KEY (STUDY_COORDINATING_CENTER_ID) REFERENCES STUDY_COORDINATING_CENTER (ID)
ON DELETE CASCADE;
