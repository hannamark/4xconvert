DROP SEQUENCE IF EXISTS HIBERNATE_SEQUENCE 
/

CREATE SEQUENCE HIBERNATE_SEQUENCE
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 27425
  CACHE 1
/
  
DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER_ROLES 
/

DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER  
/

DROP TABLE IF EXISTS STUDY_OVERALL_STATUS 
/
DROP TABLE IF EXISTS DOCUMENT_WORKFLOW_STATUS
/
DROP TABLE IF EXISTS STUDY_CONTACT_ROLES
/
DROP TABLE IF EXISTS STUDY_CONTACT
/

DROP TABLE IF EXISTS STUDY_PARTICIPATION
/

DROP TABLE IF EXISTS STUDY_REGULATORY_AUTHORITY
/

DROP TABLE IF EXISTS REGULATORY_AUTHORITY
/

DROP TABLE IF EXISTS COUNTRY
/

DROP TABLE IF EXISTS STUDY_PROTOCOL 
/

DROP TABLE IF EXISTS ORGANIZATION 
/

CREATE TABLE COUNTRY (
	ID  SERIAL NOT NULL,
	ALPHA2 VARCHAR(200),
	ALPHA3 VARCHAR(200),
	NAME VARCHAR(200),
	NUMERIC VARCHAR(200),
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
)
/

CREATE TABLE STUDY_PROTOCOL (
        ID BIGINT NOT NULL	,
        ACRONYM VARCHAR(200) ,
        ALLOCATION_CODE VARCHAR(200) ,
        ACCR_REPT_METH_CODE VARCHAR(200) ,
	EXPD_ACCESS_INDIDICATOR VARCHAR(200) ,
	NCI_IDENTIFIER VARCHAR(200) ,
        MONITOR_CODE VARCHAR(200) ,
	OFFICIAL_TITLE VARCHAR(500) ,
        PHASE_CODE VARCHAR(200) ,
        PRI_COMPL_DATE TIMESTAMP,
        PRI_COMPL_DATE_TYPE_CODE VARCHAR(200) ,
	START_DATE  TIMESTAMP,
	START_DATE_TYPE_CODE  VARCHAR(200) ,
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
        PRIMARY KEY (ID) 
)
/

CREATE TABLE STUDY_OVERALL_STATUS ( 
	ID SERIAL NOT NULL	,
	STATUS_CODE VARCHAR(200)		,   	
	STATUS_DATE TIMESTAMP,    		
	STUDY_PROTOCOL_ID BIGINT NOT NULL		,    
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
) 
/


CREATE TABLE DOCUMENT_WORKFLOW_STATUS ( 
	ID SERIAL NOT NULL	,
	STATUS_CODE VARCHAR(200)		,   	
        COMMON_TEXT VARCHAR(200)		, 
	STATUS_DATE_RANGE_LOW TIMESTAMP,    		
	STUDY_PROTOCOL_ID BIGINT NOT NULL		,    
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
        PRIMARY KEY (ID) 
) 
/

CREATE TABLE STUDY_CONTACT (
	ID  SERIAL NOT NULL,
	PRIMARY_INDICATOR BOOLEAN ,
	ADDRESS_LINE VARCHAR(200)		, 
	DELIVERY_ADDRESS_LINE VARCHAR(200)		, 
	CITY  VARCHAR(200)		, 
	STATE VARCHAR(200)		, 
	POSTAL_CODE VARCHAR(200)		, 
	COUNTRY_ID BIGINT NOT NULL ,
	--HEALTHCARE_PROVIDER_ID BIGINT NOT NULL ,
	STUDY_PROTOCOL_ID BIGINT NOT NULL ,
        STATUS_CODE VARCHAR(200) ,
        STATUS_DATE_RANGE_LOW  TIMESTAMP,
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
)
/

CREATE TABLE STUDY_CONTACT_ROLES (
	ID  SERIAL NOT NULL,
	ROLE_CODE  VARCHAR(200) NOT NULL,
	STUDY_CONTACT_ID BIGINT NOT NULL ,
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
)
/


CREATE TABLE STUDY_PARTICIPATION (
        ID  SERIAL NOT NULL,
        FUNCTIONAL_CODE VARCHAR(200) ,
        LEAD_ORG_INDICATOR BOOLEAN , 
        LOCAL_SP_INDENTIFIER  VARCHAR(200) , 
        STUDY_PROTOCOL_ID BIGINT NOT NULL ,
        STATUS_CODE VARCHAR(200) ,
        STATUS_DATE_RANGE_LOW  TIMESTAMP,
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)

)
/

CREATE TABLE STUDY_COORDINATING_CENTER (
	ID  SERIAL NOT NULL,
        STUDY_PROTOCOL_ID BIGINT NOT NULL ,  
        ORGANIZATION_ID BIGINT NOT NULL	,
        TELEPHONE VARCHAR(200),
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
        PRIMARY KEY (ID)
) 
/

CREATE TABLE STUDY_COORDINATING_CENTER_ROLES (
	ID  SERIAL NOT NULL,
	RESPONSIBILITY_CODE VARCHAR(200),
        STUDY_COORDINATING_CENTER_ID BIGINT NOT NULL ,  
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
        PRIMARY KEY (ID)
) 
/

CREATE TABLE ORGANIZATION ( 	
	ID  SERIAL NOT NULL,
	NAME VARCHAR(200),
	IDENTIFIER VARCHAR(200),
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
)
/


CREATE TABLE REGULATORY_AUTHORITY ( 
        ID  SERIAL NOT NULL,
	AUTHORITY_NAME VARCHAR(200),
	COUNTRY_ID BIGINT NOT NULL,
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
) 
/

CREATE TABLE STUDY_REGULATORY_AUTHORITY(
	ID  SERIAL NOT NULL,
	STUDY_PROTOCOL_ID BIGINT NOT NULL,
	REGULATORY_AUTHORITY_ID BIGINT NOT NULL,
        DATE_LAST_UPDATED TIMESTAMP,
        USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID)
)
/

ALTER TABLE STUDY_REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_SP
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE
/

ALTER TABLE REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_COUNTRY
FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_RA
FOREIGN KEY (REGULATORY_AUTHORITY_ID) REFERENCES REGULATORY_AUTHORITY (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_OVERALL_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_OVERALL_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE 
/

ALTER TABLE DOCUMENT_WORKFLOW_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_DOCUMENT_WORKFLOW_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_STUDY_CONTACT_COUNTRY
FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)
ON DELETE CASCADE
/


ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_CONTACT
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_CONTACT_ROLES ADD CONSTRAINT FK_STUDY_CONTACT_STUDY_CONTACT_ROLES
FOREIGN KEY (STUDY_CONTACT_ID) REFERENCES STUDY_CONTACT (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE
/


ALTER TABLE STUDY_COORDINATING_CENTER ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_COORDINATING_CENTER
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_COORDINATING_CENTER ADD CONSTRAINT FK_ORGANIZATION_STUDY_COORDINATING_CENTER
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE
/

ALTER TABLE STUDY_COORDINATING_CENTER_ROLES ADD CONSTRAINT FK_STUDY_COORDINATING_CENTER_ROLES_STUDY_COORDINATING_CENTER
FOREIGN KEY (STUDY_COORDINATING_CENTER_ID) REFERENCES STUDY_COORDINATING_CENTER (ID)
ON DELETE CASCADE
/




