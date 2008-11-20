DROP SEQUENCE IF EXISTS HIBERNATE_SEQUENCE ;

CREATE SEQUENCE HIBERNATE_SEQUENCE
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 27425
  CACHE 1;
                 
DROP TABLE IF EXISTS ARM_INTERVENTION;


DROP TABLE IF EXISTS organizational_contact cascade ;

DROP TABLE IF EXISTS PLANNED_ACTIVITY;

DROP TABLE IF EXISTS STUDY_PARTICIPATION_CONTACT cascade;

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

DROP TABLE IF EXISTS STUDY_CONTACT cascade;

DROP TABLE IF EXISTS STUDY_PARTICIPATION cascade;

DROP TABLE IF EXISTS STUDY_REGULATORY_AUTHORITY;

DROP TABLE IF EXISTS REGULATORY_AUTHORITY;

DROP TABLE IF EXISTS COUNTRY cascade;

DROP TABLE IF EXISTS STUDY_PROTOCOL cascade;

DROP TABLE IF EXISTS HEALTHCARE_FACILITY ;

DROP TABLE IF EXISTS ORGANIZATION cascade;

DROP TABLE IF EXISTS CLINICAL_RESEARCH_STAFF ;

DROP TABLE IF EXISTS HEALTHCARE_PROVIDER;

DROP TABLE IF EXISTS PERSON cascade ;

DROP TABLE IF EXISTS STUDY_SITE_ACCRUAL_STATUS;

DROP TABLE IF EXISTS STUDY_RECRUITMENT_STATUS;

DROP TABLE IF EXISTS RESEARCH_ORGANIZATION;

DROP TABLE IF EXISTS INTERVENTION cascade;

DROP TABLE IF EXISTS INTERVENTION_ALTERNATE_NAME;

DROP TABLE IF EXISTS STUDY_OUTCOME_MEASURE;

DROP TABLE IF EXISTS STUDY_INDLDE;

DROP TABLE IF EXISTS ARM;

DROP TABLE IF EXISTS REGISTRY_USER;



CREATE TABLE COUNTRY (
    ID  SERIAL NOT NULL,
    ALPHA2 VARCHAR(200),
    ALPHA3 VARCHAR(200),
    NAME VARCHAR(200),
    NUMERIC VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    
    PRIMARY KEY (ID)
);

CREATE TABLE STUDY_PROTOCOL (
    ID BIGINT NOT NULL  ,
    ACCR_REPT_METH_CODE VARCHAR(50) ,
    ACRONYM VARCHAR(200) ,
	ACCEPT_HEALTHY_VOLUNTEERS_INDICATOR BOOLEAN ,
    DATA_MONTY_COMTY_APPTN_INDICATOR BOOLEAN , 
    DELAYED_POSTING_INDICATOR BOOLEAN ,
    EXPD_ACCESS_INDIDICATOR BOOLEAN ,
    FDA_REGULATED_INDICATOR BOOLEAN ,
    IDENTIFIER VARCHAR(50) NOT NULL  ,
    KEYWORD_TEXT VARCHAR(600) ,
    OFFICIAL_TITLE VARCHAR(4000) ,
	MAX_TARGET_ACCRUAL_NUM INTEGER , 
    PHASE_CODE VARCHAR(50) ,
    PHASE_OTHER_TEXT VARCHAR(200) , 
    PRI_COMPL_DATE TIMESTAMP,
    PRI_COMPL_DATE_TYPE_CODE VARCHAR(50) ,
    START_DATE  TIMESTAMP,
    START_DATE_TYPE_CODE  VARCHAR(50) ,
    PRIMARY_PURPOSE_CODE VARCHAR(50) ,
    PRIMARY_PURPOSE_OTHER_TEXT VARCHAR(1000),
    PUBLIC_DESCRIPTION VARCHAR(300) ,
    PUBLIC_TITTLE VARCHAR(300) ,
    SECTION801_INDICATOR BOOLEAN ,
    RECORD_VERIFICATION_DATE TIMESTAMP,
    SCIENTIFIC_DESCRIPTION VARCHAR(12000) ,
    STUDY_PROTOCOL_TYPE VARCHAR(100) ,
    ALLOCATION_CODE VARCHAR(200) ,
    BLINDING_ROLE_CODE_SUBJECT VARCHAR(200) ,
    BLINDING_ROLE_CODE_CAREGIVER VARCHAR(200) ,
    BLINDING_ROLE_CODE_INVESTIGATOR VARCHAR(200) ,
    BLINDING_ROLE_CODE_OUTCOME VARCHAR(200) ,
    BLINDING_SCHEMA_CODE VARCHAR(200) ,
    DESIGN_CONFIGURATION_CODE  VARCHAR(200) ,
    NUMBER_OF_INTERVENTION_GROUPS INTEGER ,
	STUDY_CLASSIFICATION_CODE VARCHAR(200) ,
    BIO_SPECIMEN_DESCRIPTION VARCHAR(800) ,
    BIO_SPECIMEN_RETENTION_CODE VARCHAR(200) ,
    SAMPLING_METHOD_CODE VARCHAR(200) ,
    NUMBER_OF_GROUPS INTEGER , 
    STUDY_MODEL_CODE VARCHAR(200) ,
    STUDY_MODEL_OTHER_TEXT VARCHAR(200) ,
    TIME_PERSPECTIVE_CODE VARCHAR(200) ,
    TIME_PERSPECTIVE_OTHER_TEXT VARCHAR(200) ,
    STUDY_POPULATION_DESCRIPTION VARCHAR(800) ,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)    
);

CREATE TABLE STRATUM_GROUP (
	ID SERIAL NOT NULL,
	DESCRIPTION VARCHAR(200) ,
	GROUP_NUMBER_TEXT VARCHAR(200) ,
	STUDY_PROTOCOL_ID BIGINT NOT NULL, 
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID) 
);

CREATE TABLE STUDY_CONTACT (
    ID  SERIAL NOT NULL,
    ROLE_CODE  VARCHAR(200) NOT NULL,
    PRIMARY_INDICATOR BOOLEAN ,
    ADDRESS_LINE VARCHAR(200),
    DELIVERY_ADDRESS_LINE VARCHAR(200),
    CITY  VARCHAR(200),
    STATE VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_ID BIGINT,
    TELEPHONE VARCHAR(200) ,
    EMAIL VARCHAR(200),
    HEALTHCARE_PROVIDER_ID BIGINT ,
    CLINICAL_RESEARCH_STAFF_ID BIGINT  NOT NULL,
    STUDY_PROTOCOL_ID BIGINT NOT NULL ,
    STATUS_CODE VARCHAR(200) NOT NULL ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

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
    HEALTHCARE_PROVIDER_ID BIGINT ,
    CLINICAL_RESEARCH_STAFF_ID BIGINT  ,
    ORGANIZATIONAL_CONTACT_ID BIGINT , 
    STATUS_CODE VARCHAR(200) ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    STUDY_PROTOCOL_ID BIGINT,
    TELEPHONE VARCHAR(200) ,
    EMAIL VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);


CREATE TABLE STUDY_COORDINATING_CENTER (
    ID  SERIAL NOT NULL,
    STUDY_PROTOCOL_ID BIGINT NOT NULL ,  
    ORGANIZATION_ID BIGINT NOT NULL ,
    TELEPHONE VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;



CREATE TABLE REGULATORY_AUTHORITY ( 
    ID  SERIAL NOT NULL,
    AUTHORITY_NAME VARCHAR(200),
    COUNTRY_ID BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;

CREATE TABLE STUDY_REGULATORY_AUTHORITY(
    ID  SERIAL NOT NULL,
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    REGULATORY_AUTHORITY_ID BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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
    STATE VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE HEALTHCARE_FACILITY (  
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    ORGANIZATION_ID BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE PERSON (   
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200) ,
    FIRST_NAME VARCHAR(200),
    MIDDLE_NAME VARCHAR(200),
    LAST_NAME VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE HEALTHCARE_PROVIDER (
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200) NOT NULL,
    PERSON_ID BIGINT NOT NULL,
	ORGANIZATION_ID BIGINT NOT NULL, 
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)

);

CREATE TABLE CLINICAL_RESEARCH_STAFF (
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200) NOT NULL,
    PERSON_ID BIGINT NOT NULL,
    ORGANIZATION_ID BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)

);

CREATE TABLE ORGANIZATIONAL_CONTACT (
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200) NOT NULL,
    PERSON_ID BIGINT NOT NULL,
    ORGANIZATION_ID BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)

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
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;



CREATE TABLE STUDY_RECRUITMENT_STATUS ( 
    ID SERIAL NOT NULL,
    STATUS_CODE VARCHAR(200),
    STATUS_DATE TIMESTAMP,
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
) ;

CREATE TABLE RESEARCH_ORGANIZATION (  
    ID  SERIAL NOT NULL,
    IDENTIFIER VARCHAR(200),
    ORGANIZATION_ID BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID),
    STATUS_DATE_RANGE_LOW  TIMESTAMP
);

CREATE TABLE PLANNED_ACTIVITY (  
    ID SERIAL NOT NULL,
    CATEGORY_CODE VARCHAR(200),
    SUBCATEGORY_CODE VARCHAR(200),
    LEAD_PRODUCT_INDICATOR BOOLEAN,
	DESCRIPTION_TEXT VARCHAR(2000),
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    INTERVENTION_ID BIGINT,
	PLANNED_ACTIVITY_TYPE VARCHAR(100),
    INCLUSION_INDICATOR BOOLEAN ,
    CRITERION_NAME VARCHAR(200) ,
    OPERATOR VARCHAR(200) ,
    AGEVALUE VARCHAR(200),
    UNIT VARCHAR(200),
    ELIGIBLE_GENDER_CODE VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE INTERVENTION (
    ID SERIAL NOT NULL,
    NAME VARCHAR(200),
    TYPE_CODE VARCHAR(200),
    DESCRIPTION_TEXT VARCHAR(1000),
    STATUS_CODE VARCHAR(200),
    STATUS_DATE_RANGE_LOW TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE INTERVENTION_ALTERNATE_NAME (
    ID SERIAL NOT NULL,
    NAME VARCHAR(200),
    INTERVENTION_ID BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200),
    STATUS_DATE_RANGE_LOW TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID)
);

CREATE TABLE STUDY_OUTCOME_MEASURE (
	ID SERIAL NOT NULL,
	NAME VARCHAR(200) ,
	TIMEFRAME VARCHAR(200) ,
	STUDY_PROTOCOL_ID BIGINT NOT NULL, 
	PRIMARY_INDICATOR BOOLEAN,
	SAFETY_INDICATOR BOOLEAN,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID) 
);

CREATE TABLE STUDY_INDLDE (
	ID SERIAL NOT NULL,
	EXPANDED_ACCESS_STATUS_CODE VARCHAR(200) ,
	EXPANDED_ACCESS_INDICATOR BOOLEAN,
	GRANTOR_CODE VARCHAR(200) ,
	NIH_INST_HOLDER_CODE VARCHAR(200),
	NCI_DIV_PROG_HOLDER_CODE VARCHAR(200),
	HOLDER_TYPE_CODE VARCHAR(200),
	INDLDE_NUMBER VARCHAR(200),
	INDLDE_TYPE_CODE VARCHAR(200),
	STUDY_PROTOCOL_ID BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
	PRIMARY KEY (ID) 
);

CREATE TABLE ARM (
    ID SERIAL NOT NULL,
    STUDY_PROTOCOL_ID BIGINT NOT NULL,
    NAME VARCHAR(200),
    TYPE_CODE VARCHAR(200),
    DESCRIPTION_TEXT VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (ID) 
);

CREATE TABLE ARM_INTERVENTION (
    ID SERIAL NOT NULL,
    ARM_ID BIGINT NOT NULL,
    PLANNED_ACTIVITY_ID BIGINT NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE REGISTRY_USER (   
    ID  SERIAL NOT NULL,
    FIRST_NAME VARCHAR(200),
    MIDDLE_NAME VARCHAR(200),
    LAST_NAME VARCHAR(200),
    ADDRESS_LINE VARCHAR(200), 
    CITY  VARCHAR(200),
    STATE VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
     COUNTRY VARCHAR(200),
    PHONE VARCHAR(200),
    AFFILIATE_ORG VARCHAR(200),
    CSM_USER_ID INTEGER, UNIQUE(CSM_USER_ID),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
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

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_STUDY_CONTACT
FOREIGN KEY (CLINICAL_RESEARCH_STAFF_ID) REFERENCES CLINICAL_RESEARCH_STAFF (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_STUDY_CONTACT
FOREIGN KEY (HEALTHCARE_PROVIDER_ID) REFERENCES HEALTHCARE_PROVIDER (ID)
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


ALTER TABLE HEALTHCARE_FACILITY ADD CONSTRAINT FK_HEALTHCARE_FACILITY_ORGANIZATION
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE;


ALTER TABLE HEALTHCARE_PROVIDER ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_PERSON
FOREIGN KEY (PERSON_ID) REFERENCES PERSON (ID)
ON DELETE CASCADE;

ALTER TABLE CLINICAL_RESEARCH_STAFF ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_PERSON
FOREIGN KEY (PERSON_ID) REFERENCES PERSON (ID)
ON DELETE CASCADE;

ALTER TABLE CLINICAL_RESEARCH_STAFF ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_ORGANIZATION
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE;

ALTER TABLE ORGANIZATIONAL_CONTACT ADD CONSTRAINT FK_ORGANIZATION_CONTACT_PERSON
FOREIGN KEY (PERSON_ID) REFERENCES PERSON (ID)
ON DELETE CASCADE;

ALTER TABLE ORGANIZATIONAL_CONTACT ADD CONSTRAINT FK_ORGANIZATION_CONTACT_ORGANIZATION
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
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

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (HEALTHCARE_PROVIDER_ID) REFERENCES HEALTHCARE_PROVIDER (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (CLINICAL_RESEARCH_STAFF_ID) REFERENCES CLINICAL_RESEARCH_STAFF (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_RECRUITMENT_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_RECRUITMENT_STATUS
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE ;

ALTER TABLE RESEARCH_ORGANIZATION ADD CONSTRAINT FK_RESEARCH_ORGANIZATION_ORGANIZATION
FOREIGN KEY (ORGANIZATION_ID) REFERENCES ORGANIZATION (ID)
ON DELETE CASCADE;

ALTER TABLE PLANNED_ACTIVITY ADD CONSTRAINT FK_PLANNED_ACTIVITY_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE PLANNED_ACTIVITY ADD CONSTRAINT FK_PLANNED_ACTIVITY_INTERVENTION
FOREIGN KEY (INTERVENTION_ID) REFERENCES INTERVENTION (ID)
ON DELETE RESTRICT;

ALTER TABLE INTERVENTION_ALTERNATE_NAME ADD CONSTRAINT FK_INTERVENTION_ALTERNATE_NAME_INTERVENTION
FOREIGN KEY (INTERVENTION_ID) REFERENCES INTERVENTION (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_OUTCOME_MEASURE ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE STUDY_INDLDE ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE ARM ADD CONSTRAINT FK_ARM_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_ID) REFERENCES STUDY_PROTOCOL (ID)
ON DELETE CASCADE;

ALTER TABLE ARM_INTERVENTION ADD CONSTRAINT FK_ARM_INTERVENTION_ARM
FOREIGN KEY (ARM_ID) REFERENCES ARM (ID)
ON DELETE CASCADE;

ALTER TABLE ARM_INTERVENTION ADD CONSTRAINT FK_ARM_INTERVENTION_PLANNED_ACTIVITY
FOREIGN KEY (PLANNED_ACTIVITY_ID) REFERENCES PLANNED_ACTIVITY (ID)
ON DELETE CASCADE;


