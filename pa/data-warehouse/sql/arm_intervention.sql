DROP TABLE IF EXISTS STG_DW_STUDY_ARM_AND_INTERVENTION;
CREATE TABLE STG_DW_STUDY_ARM_AND_INTERVENTION ( 
    ARM_DESCRIPTION character varying(1000),
    ARM_NAME character varying(200),
    ARM_TYPE character varying(50),
    DATE_CREATED_ARM timestamp,
    DATE_UPDATED_ARM timestamp,
    DATE_CREATED_INTERVENTION timestamp,
    DATE_UPDATED_INTERVENTION timestamp,
    INTERVENTION_DESCRIPTION character varying(1000),
    INTERVENTION_NAME character varying(200),
    INTERVENTION_OTHER_NAME character varying(2000),
    INTERVENTION_ID integer,
    INTERVENTION_TYPE character varying(200),
    NCI_ID character varying(255),
	USER_NAME_CREATED_ARM character(255),
	USER_NAME_UPDATED_ARM character(255),
	USER_NAME_CREATED_INTERVENTION character(255),
	USER_NAME_UPDATED_INTERVENTION character(255),
    FIRST_NAME_CREATED_ARM character varying(255),
    LAST_NAME_CREATED_ARM character varying(255),
    FIRST_NAME_UPDATED_ARM character varying(255),
    LAST_NAME_UPDATED_ARM character varying(255),
    FIRST_NAME_CREATED_INTERVENTION character varying(255),
    LAST_NAME_CREATED_INTERVENTION character varying(255),
    FIRST_NAME_UPDATED_INTERVENTION character varying(255),
    LAST_NAME_UPDATED_INTERVENTION character varying(255)
    );

