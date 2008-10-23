DROP SEQUENCE IF EXISTS HIBERNATE_SEQUENCE ;

CREATE SEQUENCE HIBERNATE_SEQUENCE
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 27425
  CACHE 1;

DROP TABLE IF EXISTS STUDY_PARTICIPATION_CONTACT cascade;

DROP TABLE IF EXISTS STUDY_PARTICIPATION_CONTACT_TELECOM_ADDRESS;

DROP TABLE IF EXISTS FUNDING_MECHANISM ;

DROP TABLE IF EXISTS NIH_INSTITUTE ; 
 
DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER_ROLES ;

DROP TABLE IF EXISTS STUDY_RESOURCING ;

DROP TABLE IF EXISTS DOCUMENT ;

DROP TABLE IF EXISTS STRATUM_GROUP;
  
DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER_ROLES ;

DROP TABLE IF EXISTS STUDY_COORDINATING_CENTER  ;

DROP TABLE IF EXISTS STUDY_OVERALL_STATUS ;

DROP TABLE IF EXISTS DOCUMENT_WORKFLOW_STATUS;

DROP TABLE IF EXISTS STUDY_CONTACT_ROLES;

DROP TABLE IF EXISTS STUDY_CONTACT;

DROP TABLE IF EXISTS STUDY_PARTICIPATION cascade;

DROP TABLE IF EXISTS STUDY_REGULATORY_AUTHORITY;

DROP TABLE IF EXISTS REGULATORY_AUTHORITY;

DROP TABLE IF EXISTS COUNTRY;

DROP TABLE IF EXISTS STUDY_PROTOCOL cascade;

DROP TABLE IF EXISTS HEALTHCARE_FACILITY ;

DROP TABLE IF EXISTS ORGANIZATION cascade;

DROP TABLE IF EXISTS HEALTHCARE_PROVIDER;

DROP TABLE IF EXISTS PERSON ;

DROP TABLE IF EXISTS STUDY_SITE_ACCRUAL_STATUS;

DROP TABLE IF EXISTS STUDY_RECRUITMENT_STATUS;

DROP TABLE IF EXISTS RESEARCH_ORGANIZATION;


CREATE TABLE COUNTRY (
    ID  SERIAL NOT NULL,
    ALPHA2 VARCHAR(200),
    ALPHA3 VARCHAR(200),
    NAME VARCHAR(200),
    NUMERIC VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE STUDY_PROTOCOL (
    ID BIGINT NOT NULL  ,
    ACRONYM VARCHAR(200) ,
    ALLOCATION_CODE VARCHAR(200) ,
    ACCR_REPT_METH_CODE VARCHAR(200) ,
    DATA_MONTY_COMTY_APPTN_INDICATOR BOOLEAN , 
    EXPD_ACCESS_INDIDICATOR BOOLEAN ,
    NCI_IDENTIFIER VARCHAR(200) ,
    MONITOR_CODE VARCHAR(200) ,
    OFFICIAL_TITLE VARCHAR(500) ,
    PHASE_CODE VARCHAR(200) ,
    PRIMARY_PURPOSE_CODE VARCHAR(200) ,
    PRI_COMPL_DATE TIMESTAMP,
    PRI_COMPL_DATE_TYPE_CODE VARCHAR(200) ,
    START_DATE  TIMESTAMP,
    START_DATE_TYPE_CODE  VARCHAR(200) ,
    IND_IDE_INDICATOR BOOLEAN , 
    FDA_REGULATED_INDICATOR BOOLEAN , 
    SECTION801_INDICATOR BOOLEAN ,
    DELAYED_POSTING_INDICATOR BOOLEAN ,
    STUDY_PROTOCOL_TYPE  VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID) 
);

CREATE TABLE STUDY_OVERALL_STATUS ( 
    ID SERIAL NOT NULL,
    COMMENT_TEXT VARCHAR(200),
    STATUS_CODE VARCHAR(200),
    STATUS_DATE TIMESTAMP,
    STUDY_STOPPED_REASON_CODE VARCHAR(200),
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;


CREATE TABLE DOCUMENT_WORKFLOW_STATUS ( 
    ID SERIAL NOT NULL  ,
    STATUS_CODE VARCHAR(200)        ,       
    COMMON_TEXT VARCHAR(200)        , 
    STATUS_DATE_RANGE_LOW TIMESTAMP,            
    STUDY_PROTOCOL_ID BIGINT NOT NULL       ,    
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID) 
) ;

CREATE TABLE STUDY_RESOURCING
(
    ID SERIAL NOT NULL,
    TYPE_CODE VARCHAR(200) ,
    SUMM_4_REPT_INDICATOR BOOLEAN,
    ORGANIZATION_ID VARCHAR(200) ,
    RESOURCE_PROVIDER_ID VARCHAR(200) ,
    STUDY_PROTOCOL_ID BIGINT NOT NULL       ,    
    FUNDING_MECHANISM_CODE VARCHAR(200) ,
    FUNDING_TYPE_CODE VARCHAR(200) ,
    NIH_INSTITUTE_CODE VARCHAR(200) ,
    NCI_DIVISION_PROGRAM_CODE VARCHAR(200) ,
    SUFFIX_GRANT_YEAR VARCHAR(200) ,
    SUFFIX_OTHER VARCHAR(200) ,
    SERIAL_NUMBER INTEGER ,
    ACTIVE_INDICATOR BOOLEAN,
    INACTIVE_COMMENT_TEXT VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID) 
) ;

CREATE TABLE DOCUMENT (
    ID SERIAL NOT NULL,
    TYPE_CODE VARCHAR(200) ,
    ACTIVE_INDICATOR BOOLEAN,
    FILE_NAME VARCHAR(200),
    STUDY_PROTOCOL_ID BIGINT NOT NULL,  
    INACTIVE_COMMENT_TEXT VARCHAR(200),  
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)    
);

CREATE TABLE STRATUM_GROUP (
	ID SERIAL NOT NULL,
	DESCRIPTION VARCHAR(200) ,
	GROUP_NUMBER_TEXT VARCHAR(200) ,
	STUDY_PROTOCOL_ID BIGINT NOT NULL, 
	DATE_LAST_UPDATED TIMESTAMP,
	USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID) 
);

CREATE TABLE STUDY_CONTACT (
    ID  SERIAL NOT NULL,
    PRIMARY_INDICATOR BOOLEAN ,
    ADDRESS_LINE VARCHAR(200),
    DELIVERY_ADDRESS_LINE VARCHAR(200),
    CITY  VARCHAR(200),
    STATE VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_ID BIGINT,
    HEALTHCARE_PROVIDER_ID BIGINT NOT NULL ,
    STUDY_PROTOCOL_ID BIGINT NOT NULL ,
    STATUS_CODE VARCHAR(200) ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE STUDY_CONTACT_ROLES (
    ID  SERIAL NOT NULL,
    ROLE_CODE  VARCHAR(200) NOT NULL,
    STUDY_CONTACT_ID BIGINT NOT NULL ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);


CREATE TABLE STUDY_PARTICIPATION (
    ID  SERIAL NOT NULL,
    FUNCTIONAL_CODE VARCHAR(200) ,
    LOCAL_SP_INDENTIFIER  VARCHAR(200) , 
    STUDY_PROTOCOL_ID BIGINT NOT NULL ,
    HEALTHCARE_FACILITY_ID BIGINT,
    RESEARCH_ORGANIZATION_ID BIGINT,
    STATUS_CODE VARCHAR(200) ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)

);

CREATE TABLE STUDY_COORDINATING_CENTER (
    ID  SERIAL NOT NULL,
    STUDY_PROTOCOL_ID BIGINT NOT NULL ,  
    ORGANIZATION_ID BIGINT NOT NULL ,
    TELEPHONE VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;

CREATE TABLE STUDY_COORDINATING_CENTER_ROLES (
    ID  SERIAL NOT NULL,
    RESPONSIBILITY_CODE VARCHAR(200),
    STUDY_COORDINATING_CENTER_ID BIGINT NOT NULL ,  
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;


CREATE TABLE REGULATORY_AUTHORITY ( 
    ID  SERIAL NOT NULL,
    AUTHORITY_NAME VARCHAR(200),
    COUNTRY_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;

CREATE TABLE STUDY_REGULATORY_AUTHORITY(
    ID  SERIAL NOT NULL,
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    REGULATORY_AUTHORITY_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);


CREATE TABLE ORGANIZATION (     
    ID  SERIAL NOT NULL,
    NAME VARCHAR(200),
    IDENTIFIER VARCHAR(200),
    CITY  VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_NAME VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);


CREATE TABLE HEALTHCARE_FACILITY (  
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200),
    ORGANIZATION_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE PERSON (   
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200),
    FIRST_NAME VARCHAR(200),
    MIDDLE_NAME VARCHAR(200),
    LAST_NAME VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE HEALTHCARE_PROVIDER (
    ID  SERIAL NOT NULL,
    PERSON_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID),
    IDENTIFIER VARCHAR(200)
);

CREATE TABLE FUNDING_MECHANISM (
    ID  SERIAL NOT NULL,
    FUNDING_MECHANISM_CODE VARCHAR(200)  NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE NIH_INSTITUTE (
    ID  SERIAL NOT NULL,
    NIH_INSTITUTE_CODE VARCHAR(200)  NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE STUDY_SITE_ACCRUAL_STATUS ( 
    ID SERIAL NOT NULL,
    STATUS_CODE VARCHAR(200),
    STATUS_DATE TIMESTAMP,
    STUDY_PARTICIPATION_ID BIGINT NOT NULL,    
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;

CREATE TABLE STUDY_PARTICIPATION_CONTACT (
    ID SERIAL NOT NULL,
    ADDRESS_LINE VARCHAR(200), 
    DELIVERY_ADDRESS_LINE VARCHAR(200),
    CITY  VARCHAR(200),
    STATE VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_ID BIGINT,
    PRIMARY_INDICATOR BOOLEAN,
    ROLE_CODE VARCHAR(200),
    STUDY_PARTICIPATION_ID BIGINT,
    health_care_provider_id BIGINT,
    STATUS_CODE VARCHAR(200) ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID),
    study_protocol_id BIGINT,
	telephone VARCHAR(200) ,
	email VARCHAR(200)
);

CREATE TABLE STUDY_PARTICIPATION_CONTACT_TELECOM_ADDRESS (
    ID SERIAL NOT NULL,
    TELECOM_ADDRESS VARCHAR(200),
    STUDY_PARTICIPATION_CONTACT_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (ID)
);

CREATE TABLE STUDY_RECRUITMENT_STATUS ( 
    ID SERIAL NOT NULL,
    STATUS_CODE VARCHAR(200),
    STATUS_DATE TIMESTAMP,
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;

CREATE TABLE RESEARCH_ORGANIZATION (  
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200),
    ORGANIZATION_ID BIGINT NOT NULL,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

ALTER TABLE STUDY_REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_SP
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_COUNTRY
FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_RA
FOREIGN KEY (REGULATORY_AUTHORITY_ID) REFERENCES REGULATORY_AUTHORITY (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_OVERALL_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_OVERALL_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE ;

ALTER TABLE DOCUMENT_WORKFLOW_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_DOCUMENT_WORKFLOW_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_STUDY_CONTACT_COUNTRY
FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)
ON DELETE CASCADE;


ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_CONTACT
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT_ROLES ADD CONSTRAINT FK_STUDY_CONTACT_STUDY_CONTACT_ROLES
FOREIGN KEY (STUDY_CONTACT_ID) REFERENCES STUDY_CONTACT (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_HEALTHCARE_FACILITY
FOREIGN KEY (HEALTHCARE_FACILITY_ID) REFERENCES HEALTHCARE_FACILITY (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_RESEARCH_ORGANIZATION
FOREIGN KEY (RESEARCH_ORGANIZATION_ID) REFERENCES RESEARCH_ORGANIZATION (ID)
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

ALTER TABLE HEALTHCARE_FACILITY ADD CONSTRAINT FK_HEALTHCARE_FACILITY_ORGANIZATION
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE;


ALTER TABLE HEALTHCARE_PROVIDER ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_PERSON
FOREIGN KEY (PERSON_ID) REFERENCES PERSON (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_STUDY_CONTACT
FOREIGN KEY (HEALTHCARE_PROVIDER_ID) REFERENCES HEALTHCARE_PROVIDER (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_RESOURCING ADD CONSTRAINT FK_STUDY_RESOURCING_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE DOCUMENT ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STRATUM_GROUP ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_SITE_ACCRUAL_STATUS ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_SITE_ACCRUAL_STATUS
FOREIGN KEY (STUDY_PARTICIPATION_ID) REFERENCES STUDY_PARTICIPATION (ID)
ON DELETE CASCADE ;

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (STUDY_PARTICIPATION_ID) REFERENCES STUDY_PARTICIPATION (ID)
ON DELETE CASCADE ;

ALTER TABLE STUDY_PARTICIPATION_CONTACT_TELECOM_ADDRESS ADD CONSTRAINT FK_STUDY_PARTICIPATION_CONTACT_STUDY_PARTICIPATION_CONTACT_TELECOM_ADDRESS
FOREIGN KEY (STUDY_PARTICIPATION_CONTACT_ID) REFERENCES STUDY_PARTICIPATION_CONTACT (ID)
ON DELETE CASCADE ;

ALTER TABLE STUDY_RECRUITMENT_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_RECRUITMENT_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE ;

ALTER TABLE RESEARCH_ORGANIZATION ADD CONSTRAINT FK_RESEARCH_ORGANIZATION_ORGANIZATION
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE;


