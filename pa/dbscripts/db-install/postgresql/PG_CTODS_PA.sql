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

DROP TABLE IF EXISTS OVERSIGHT_COMMITTEE;

DROP TABLE IF EXISTS STUDY_DISEASE;

DROP TABLE IF EXISTS DISEASE cascade;

DROP TABLE IF EXISTS DISEASE_ALTERNAME;

DROP TABLE IF EXISTS DISEASE_PARENT;

DROP TABLE IF EXISTS PA_PROPERTIES;

DROP TABLE IF EXISTS MESSAGES_LOG;

CREATE TABLE COUNTRY (
    IDENTIFIER  SERIAL NOT NULL,
    ALPHA2 VARCHAR(200),
    ALPHA3 VARCHAR(200),
    NAME VARCHAR(200),
    NUMERIC VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE STUDY_PROTOCOL (
    IDENTIFIER BIGINT NOT NULL  ,
    ACCR_REPT_METH_CODE VARCHAR(50) ,
    ACRONYM VARCHAR(200) ,
    ACCEPT_HEALTHY_VOLUNTEERS_INDICATOR BOOLEAN ,
    DATA_MONTY_COMTY_APPTN_INDICATOR BOOLEAN , 
    DELAYED_POSTING_INDICATOR BOOLEAN ,
    EXPD_ACCESS_INDIDICATOR BOOLEAN ,
    FDA_REGULATED_INDICATOR BOOLEAN ,
    REVIEW_BRD_APPROVAL_REQ_INDICATOR BOOLEAN ,
    ASSIGNED_IDENTIFIER VARCHAR(50) NOT NULL  ,
    KEYWORD_TEXT VARCHAR(600) ,
    OFFICIAL_TITLE VARCHAR(4000) ,
    MAX_TARGET_ACCRUAL_NUM INTEGER , 
    PHASE_CODE VARCHAR(50) ,
    PHASE_OTHER_TEXT VARCHAR(200) , 
    PRI_COMPL_DATE TIMESTAMP NOT NULL,
    PRI_COMPL_DATE_TYPE_CODE VARCHAR(50) NOT NULL,
    START_DATE  TIMESTAMP NOT NULL,
    START_DATE_TYPE_CODE  VARCHAR(50) NOT NULL,
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
    PRIMARY KEY (IDENTIFIER) 
);


CREATE TABLE STUDY_OVERALL_STATUS ( 
    IDENTIFIER SERIAL NOT NULL,
    COMMENT_TEXT VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE TIMESTAMP NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
) ;

CREATE TABLE DOCUMENT_WORKFLOW_STATUS ( 
    IDENTIFIER SERIAL NOT NULL  ,
    STATUS_CODE VARCHAR(200)        ,       
    COMMENT_TEXT  VARCHAR(200)        , 
    STATUS_DATE_RANGE_LOW TIMESTAMP,            
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL       ,    
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER) 
) ;

CREATE TABLE STUDY_RESOURCING
(
    IDENTIFIER SERIAL NOT NULL,
    TYPE_CODE VARCHAR(200) ,
    SUMM_4_REPT_INDICATOR BOOLEAN,
    ORGANIZATION_IDENTIFIER VARCHAR(200) ,
    RESOURCE_PROVIDER_IDENTIFIER VARCHAR(200) ,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL       ,    
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
    PRIMARY KEY (IDENTIFIER) 
) ;

CREATE TABLE DOCUMENT (
    IDENTIFIER SERIAL NOT NULL,
    TYPE_CODE VARCHAR(200) ,
    ACTIVE_INDICATOR BOOLEAN,
    FILE_NAME VARCHAR(200),
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,  
    INACTIVE_COMMENT_TEXT VARCHAR(200),  
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)    
);

CREATE TABLE STRATUM_GROUP (
    IDENTIFIER SERIAL NOT NULL,
    DESCRIPTION VARCHAR(200) ,
    GROUP_NUMBER_TEXT VARCHAR(200) ,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL, 
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER) 
);

CREATE TABLE STUDY_CONTACT (
    IDENTIFIER  SERIAL NOT NULL,
    ROLE_CODE  VARCHAR(200) NOT NULL,
    PRIMARY_INDICATOR BOOLEAN ,
    ADDRESS_LINE VARCHAR(200),
    DELIVERY_ADDRESS_LINE VARCHAR(200),
    CITY  VARCHAR(200),
    STATE VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_IDENTIFIER BIGINT,
    TELEPHONE VARCHAR(200) ,
    EMAIL VARCHAR(200),
    HEALTHCARE_PROVIDER_IDENTIFIER BIGINT ,
    CLINICAL_RESEARCH_STAFF_IDENTIFIER BIGINT  NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL ,
    STATUS_CODE VARCHAR(200) NOT NULL ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE STUDY_PARTICIPATION (
    IDENTIFIER SERIAL NOT NULL,
    FUNCTIONAL_CODE VARCHAR(200) ,
    LOCAL_SP_INDENTIFIER  VARCHAR(200) ,
    REVIEW_BOARD_APPROVAL_NUMBER VARCHAR(200),
    REVIEW_BOARD_APPROVAL_DATE TIMESTAMP,
    REVIEW_BOARD_APPROVAL_STATUS_CODE VARCHAR(200),
    TARGET_ACCRUAL_NUMBER INTEGER,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL ,
    HEALTHCARE_FACILITY_IDENTIFIER BIGINT,
    RESEARCH_ORGANIZATION_IDENTIFIER BIGINT,
    OVERSIGHT_COMMITTEE_IDENTIFIER BIGINT,
    STATUS_CODE VARCHAR(200) ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE STUDY_PARTICIPATION_CONTACT (
    IDENTIFIER SERIAL NOT NULL,
    ADDRESS_LINE VARCHAR(200), 
    DELIVERY_ADDRESS_LINE VARCHAR(200),
    CITY  VARCHAR(200),
    STATE VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_IDENTIFIER BIGINT,
    PRIMARY_INDICATOR BOOLEAN,
    ROLE_CODE VARCHAR(200),
    STUDY_PARTICIPATION_IDENTIFIER BIGINT,
    HEALTHCARE_PROVIDER_IDENTIFIER BIGINT ,
    CLINICAL_RESEARCH_STAFF_IDENTIFIER BIGINT  ,
    ORGANIZATIONAL_CONTACT_IDENTIFIER BIGINT , 
    STATUS_CODE VARCHAR(200) ,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    STUDY_PROTOCOL_IDENTIFIER BIGINT,
    TELEPHONE VARCHAR(200) ,
    EMAIL VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);


CREATE TABLE STUDY_COORDINATING_CENTER (
    IDENTIFIER SERIAL NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL ,  
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL ,
    TELEPHONE VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
) ;



CREATE TABLE REGULATORY_AUTHORITY ( 
    IDENTIFIER SERIAL NOT NULL,
    AUTHORITY_NAME VARCHAR(200),
    COUNTRY_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
) ;

CREATE TABLE STUDY_REGULATORY_AUTHORITY(
    IDENTIFIER SERIAL NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    REGULATORY_AUTHORITY_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE ORGANIZATION (     
    IDENTIFIER SERIAL NOT NULL,
    NAME VARCHAR(200),
    ASSIGNED_IDENTIFIER VARCHAR(200),
    CITY  VARCHAR(200),
    POSTAL_CODE VARCHAR(200),
    COUNTRY_NAME VARCHAR(200),
    STATE VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE HEALTHCARE_FACILITY (  
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE PERSON (   
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200) ,
    FIRST_NAME VARCHAR(200),
    MIDDLE_NAME VARCHAR(200),
    LAST_NAME VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE HEALTHCARE_PROVIDER (
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200) NOT NULL,
    PERSON_IDENTIFIER BIGINT NOT NULL,
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL, 
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)

);

CREATE TABLE CLINICAL_RESEARCH_STAFF (
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200) NOT NULL,
    PERSON_IDENTIFIER BIGINT NOT NULL,
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)

);

CREATE TABLE ORGANIZATIONAL_CONTACT (
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200) NOT NULL,
    PERSON_IDENTIFIER BIGINT NOT NULL,
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW  TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)

);


CREATE TABLE FUNDING_MECHANISM (
    IDENTIFIER SERIAL NOT NULL,
    FUNDING_MECHANISM_CODE VARCHAR(200)  NOT NULL,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE NIH_INSTITUTE (
    IDENTIFIER SERIAL NOT NULL,
    NIH_INSTITUTE_CODE VARCHAR(200)  NOT NULL,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE STUDY_SITE_ACCRUAL_STATUS ( 
    IDENTIFIER SERIAL NOT NULL,
    STATUS_CODE VARCHAR(200),
    STATUS_DATE TIMESTAMP,
    STUDY_PARTICIPATION_IDENTIFIER BIGINT NOT NULL,    
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
) ;



CREATE TABLE STUDY_RECRUITMENT_STATUS ( 
    IDENTIFIER SERIAL NOT NULL,
    STATUS_CODE VARCHAR(200),
    STATUS_DATE TIMESTAMP,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
) ;

CREATE TABLE RESEARCH_ORGANIZATION (  
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200),
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER),
    STATUS_DATE_RANGE_LOW  TIMESTAMP
);

CREATE TABLE PLANNED_ACTIVITY (  
    IDENTIFIER SERIAL NOT NULL,
    CATEGORY_CODE VARCHAR(200),
    SUBCATEGORY_CODE VARCHAR(200),
    LEAD_PRODUCT_INDICATOR BOOLEAN,
    TEXT_DESCRIPTION VARCHAR(2000),
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    INTERVENTION_IDENTIFIER BIGINT,
    PLANNED_ACTIVITY_TYPE VARCHAR(100),
    INCLUSION_INDICATOR BOOLEAN ,
    CRITERION_NAME VARCHAR(200) ,
    OPERATOR VARCHAR(200) ,
    VALUE NUMERIC,
    UNIT VARCHAR(200),
    ELIGIBLE_GENDER_CODE VARCHAR(200),
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE INTERVENTION (
    IDENTIFIER SERIAL NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    TYPE_CODE VARCHAR(200) NOT NULL,
    DESCRIPTION_TEXT VARCHAR(1000),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW TIMESTAMP NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE INTERVENTION_ALTERNATE_NAME (
    IDENTIFIER SERIAL NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    INTERVENTION_IDENTIFIER BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW TIMESTAMP NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE STUDY_OUTCOME_MEASURE (
    IDENTIFIER SERIAL NOT NULL,
    NAME VARCHAR(200) ,
    TIMEFRAME VARCHAR(200) ,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL, 
    PRIMARY_INDICATOR BOOLEAN,
    SAFETY_INDICATOR BOOLEAN,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER) 
);

CREATE TABLE STUDY_INDLDE (
    IDENTIFIER SERIAL NOT NULL,
    EXPANDED_ACCESS_STATUS_CODE VARCHAR(200) ,
    EXPANDED_ACCESS_INDICATOR BOOLEAN,
    GRANTOR_CODE VARCHAR(200) ,
    NIH_INST_HOLDER_CODE VARCHAR(200),
    NCI_DIV_PROG_HOLDER_CODE VARCHAR(200),
    HOLDER_TYPE_CODE VARCHAR(200),
    INDLDE_NUMBER VARCHAR(200),
    INDLDE_TYPE_CODE VARCHAR(200),
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER) 
);

CREATE TABLE ARM (
    IDENTIFIER SERIAL NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    TYPE_CODE VARCHAR(200),
    DESCRIPTION_TEXT VARCHAR(1000) NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200) ,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200) ,
    PRIMARY KEY (IDENTIFIER) 
);

CREATE TABLE ARM_INTERVENTION (
    IDENTIFIER SERIAL NOT NULL,
    ARM_IDENTIFIER BIGINT NOT NULL,
    PLANNED_ACTIVITY_IDENTIFIER BIGINT NOT NULL,
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE REGISTRY_USER (   
    IDENTIFIER SERIAL NOT NULL,
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
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE OVERSIGHT_COMMITTEE (  
    IDENTIFIER SERIAL NOT NULL,
    ASSIGNED_IDENTIFIER VARCHAR(200),
    ORGANIZATION_IDENTIFIER BIGINT NOT NULL,
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW TIMESTAMP,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE STUDY_DISEASE (
    IDENTIFIER SERIAL NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DISEASE_IDENTIFIER BIGINT NOT NULL,
    LEAD_DISEASE_INDICATOR BOOLEAN,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE DISEASE (
    IDENTIFIER SERIAL NOT NULL,
    DISEASE_CODE VARCHAR(200),
    NT_TERM_IDENTIFIER VARCHAR(200),
    PREFERRED_NAME VARCHAR(200),
    MENU_DISPLAY_NAME VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW TIMESTAMP NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE DISEASE_ALTERNAME (
    IDENTIFIER SERIAL NOT NULL,
    DISEASE_IDENTIFIER BIGINT NOT NULL,
    ALTERNATE_NAME VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW TIMESTAMP NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE DISEASE_PARENT (
    IDENTIFIER SERIAL NOT NULL,
    DISEASE_IDENTIFIER BIGINT NOT NULL,
    PARENT_DISEASE_IDENTIFIER BIGINT NOT NULL,
    PARENT_DISEASE_CODE VARCHAR(200),
    STATUS_CODE VARCHAR(200) NOT NULL,
    STATUS_DATE_RANGE_LOW TIMESTAMP NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE PA_PROPERTIES (
    IDENTIFIER SERIAL NOT NULL,
    NAME VARCHAR(30)  NOT NULL,
    VALUE VARCHAR(2000),
    PRIMARY KEY (IDENTIFIER)
);

CREATE TABLE MESSAGES_LOG (
    IDENTIFIER SERIAL NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,  
    ASSIGNED_IDENTIFIER VARCHAR(200),
    MESSAGE_ACTION VARCHAR(200),
    ENTITY_NAME VARCHAR(200),
    DATE_CREATED TIMESTAMP,    
    PRIMARY KEY (IDENTIFIER)
);

ALTER TABLE STUDY_REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_SP
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_COUNTRY
FOREIGN KEY (COUNTRY_IDENTIFIER) REFERENCES COUNTRY (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_REGULATORY_AUTHORITY ADD CONSTRAINT FK_STUDY_REGULATORY_AUTHORITY_RA
FOREIGN KEY (REGULATORY_AUTHORITY_IDENTIFIER) REFERENCES REGULATORY_AUTHORITY (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_OVERALL_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_OVERALL_STATUS
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE ;

ALTER TABLE DOCUMENT_WORKFLOW_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_DOCUMENT_WORKFLOW_STATUS
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_STUDY_CONTACT_COUNTRY
FOREIGN KEY (COUNTRY_IDENTIFIER) REFERENCES COUNTRY (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_CONTACT
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_STUDY_CONTACT
FOREIGN KEY (CLINICAL_RESEARCH_STAFF_IDENTIFIER) REFERENCES CLINICAL_RESEARCH_STAFF (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_CONTACT ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_STUDY_CONTACT
FOREIGN KEY (HEALTHCARE_PROVIDER_IDENTIFIER) REFERENCES HEALTHCARE_PROVIDER (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_HEALTHCARE_FACILITY
FOREIGN KEY (HEALTHCARE_FACILITY_IDENTIFIER) REFERENCES HEALTHCARE_FACILITY (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_RESEARCH_ORGANIZATION
FOREIGN KEY (RESEARCH_ORGANIZATION_IDENTIFIER) REFERENCES RESEARCH_ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION ADD CONSTRAINT FK_STUDY_PARTICIPATION_OVERSIGHT_COMMITTEE
FOREIGN KEY (OVERSIGHT_COMMITTEE_IDENTIFIER) REFERENCES OVERSIGHT_COMMITTEE (IDENTIFIER)
ON DELETE SET NULL;

ALTER TABLE STUDY_COORDINATING_CENTER ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_COORDINATING_CENTER
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_COORDINATING_CENTER ADD CONSTRAINT FK_ORGANIZATION_STUDY_COORDINATING_CENTER
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE HEALTHCARE_FACILITY ADD CONSTRAINT FK_HEALTHCARE_FACILITY_ORGANIZATION
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE OVERSIGHT_COMMITTEE ADD CONSTRAINT FK_OVERSIGHT_COMMITTEE_ORGANIZATION
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE HEALTHCARE_PROVIDER ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_PERSON
FOREIGN KEY (PERSON_IDENTIFIER) REFERENCES PERSON (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE HEALTHCARE_PROVIDER ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_ORGANIZATION
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE CLINICAL_RESEARCH_STAFF ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_PERSON
FOREIGN KEY (PERSON_IDENTIFIER) REFERENCES PERSON (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE CLINICAL_RESEARCH_STAFF ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_ORGANIZATION
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE ORGANIZATIONAL_CONTACT ADD CONSTRAINT FK_ORGANIZATION_CONTACT_PERSON
FOREIGN KEY (PERSON_IDENTIFIER) REFERENCES PERSON (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE ORGANIZATIONAL_CONTACT ADD CONSTRAINT FK_ORGANIZATION_CONTACT_ORGANIZATION
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_RESOURCING ADD CONSTRAINT FK_STUDY_RESOURCING_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE DOCUMENT ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STRATUM_GROUP ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_SITE_ACCRUAL_STATUS ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_SITE_ACCRUAL_STATUS
FOREIGN KEY (STUDY_PARTICIPATION_IDENTIFIER) REFERENCES STUDY_PARTICIPATION (IDENTIFIER)
ON DELETE CASCADE ;

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_STUDY_PARTICIPATION_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (STUDY_PARTICIPATION_IDENTIFIER) REFERENCES STUDY_PARTICIPATION (IDENTIFIER)
ON DELETE CASCADE ;

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_HEALTHCARE_PROVIDER_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (HEALTHCARE_PROVIDER_IDENTIFIER) REFERENCES HEALTHCARE_PROVIDER (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_CLINICAL_RESEARCH_STAFF_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (CLINICAL_RESEARCH_STAFF_IDENTIFIER) REFERENCES CLINICAL_RESEARCH_STAFF (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_PARTICIPATION_CONTACT ADD CONSTRAINT FK_ORGANIZATIONAL_CONTACT_STUDY_PARTICIPATION_CONTACT
FOREIGN KEY (ORGANIZATIONAL_CONTACT_IDENTIFIER) REFERENCES ORGANIZATIONAL_CONTACT (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_RECRUITMENT_STATUS ADD CONSTRAINT FK_STUDY_PROTOCOL_STUDY_RECRUITMENT_STATUS
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE ;

ALTER TABLE RESEARCH_ORGANIZATION ADD CONSTRAINT FK_RESEARCH_ORGANIZATION_ORGANIZATION
FOREIGN KEY (ORGANIZATION_IDENTIFIER) REFERENCES ORGANIZATION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE PLANNED_ACTIVITY ADD CONSTRAINT FK_PLANNED_ACTIVITY_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE PLANNED_ACTIVITY ADD CONSTRAINT FK_PLANNED_ACTIVITY_INTERVENTION
FOREIGN KEY (INTERVENTION_IDENTIFIER) REFERENCES INTERVENTION (IDENTIFIER)
ON DELETE RESTRICT;

ALTER TABLE INTERVENTION_ALTERNATE_NAME ADD CONSTRAINT FK_INTERVENTION_ALTERNATE_NAME_INTERVENTION
FOREIGN KEY (INTERVENTION_IDENTIFIER) REFERENCES INTERVENTION (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_OUTCOME_MEASURE ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_INDLDE ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE ARM ADD CONSTRAINT FK_ARM_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE ARM_INTERVENTION ADD CONSTRAINT FK_ARM_INTERVENTION_ARM
FOREIGN KEY (ARM_IDENTIFIER) REFERENCES ARM (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE ARM_INTERVENTION ADD CONSTRAINT FK_ARM_INTERVENTION_PLANNED_ACTIVITY
FOREIGN KEY (PLANNED_ACTIVITY_IDENTIFIER) REFERENCES PLANNED_ACTIVITY (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_DISEASE ADD CONSTRAINT FK_STUDY_DISEASE_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_DISEASE ADD CONSTRAINT FK_STUDY_DISEASE_DISEASE
FOREIGN KEY (DISEASE_IDENTIFIER) REFERENCES DISEASE (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE DISEASE_ALTERNAME ADD CONSTRAINT FK_DISEASE_ALTERNAME_DISEASE
FOREIGN KEY (DISEASE_IDENTIFIER) REFERENCES DISEASE (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE DISEASE_PARENT ADD CONSTRAINT FK_DISEASE_PARENT_DISEASE_1
FOREIGN KEY (DISEASE_IDENTIFIER) REFERENCES DISEASE (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE DISEASE_PARENT ADD CONSTRAINT FK_DISEASE_PARENT_DISEASE_2
FOREIGN KEY (PARENT_DISEASE_IDENTIFIER) REFERENCES DISEASE (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE MESSAGES_LOG ADD CONSTRAINT FK_STUDY_PROTOCOL_MESSAGES_LOG
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;