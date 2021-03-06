DROP TABLE TEMP_STUDY_FUNDING;
DROP TABLE TEMP_STUDY_INDIDE;
DROP TABLE TEMP_STUDY_PROTOCOL;
--DROP TABLE STUDY_PROTOCOL_STAGE;

CREATE TABLE STUDY_PROTOCOL_STAGE(
     IDENTIFIER BIGINT NOT NULL,
     NCT_IDENTIFIER CHARACTER VARYING(200),
     OFFICIAL_TITLE CHARACTER VARYING(4000),
     PHASE_CODE CHARACTER VARYING(50),
     PHASE_OTHER_TEXT CHARACTER VARYING(200),
     PRIMARY_PURPOSE_CODE CHARACTER VARYING(50),
     PRIMARY_PURPOSE_OTHER_TEXT CHARACTER VARYING(1000),
     LEAD_PROTOCOL_INDENTIFIER CHARACTER VARYING(200) NOT NULL,
     LEAD_ORGANIZATION_IDENTIFIER CHARACTER VARYING(50) NOT NULL,
     PI_IDENTIFIER CHARACTER VARYING(50),
     SPONSOR_IDENTIFIER CHARACTER VARYING(50),
     RESPONSIBLE_PARTY_TYPE CHARACTER VARYING(10),
     RESPONSIBLE_IDENTIFIER CHARACTER VARYING(50),
     RESPONSIBLE_CONTACT_PHONE CHARACTER VARYING(200),
     RESPONSIBLE_CONTACT_EMAIL CHARACTER VARYING(200),
     SUMMARY_FOUR_ORG_IDENTIFIER CHARACTER VARYING(50),
     SUMMARY_FOUR_FUNDING_TYPE_CODE CHARACTER VARYING(200),
     PROGRAM_CODE_TEXT CHARACTER VARYING(100),
     TRIAL_STATUS_CODE CHARACTER VARYING(200),
     TRIAL_STATUS_DATE TIMESTAMP WITHOUT TIME ZONE,
     STATUS_REASON CHARACTER VARYING(200),
     PRI_COMPL_DATE TIMESTAMP WITHOUT TIME ZONE,
     PRI_COMPL_DATE_TYPE_CODE CHARACTER VARYING(50),
     START_DATE TIMESTAMP WITHOUT TIME ZONE,
     START_DATE_TYPE_CODE CHARACTER VARYING(50),
     STUDY_PROTOCOL_TYPE CHARACTER VARYING(100),
     FDA_REGULATED_INDICATOR  BOOLEAN,
     SECTION801_INDICATOR BOOLEAN,
     DELAYED_POSTING_INDICATOR  BOOLEAN,
     DATA_MONTY_COMTY_APPTN_INDICATOR BOOLEAN,
     OVERSIGHT_AUTHORITY_COUNTRY_ID CHARACTER VARYING(50),
     OVERSIGHT_AUTHORITY_ORG_ID CHARACTER VARYING(50),
     PROPRIETARY_TRIAL_INDICATOR BOOLEAN,
     DATE_LAST_CREATED TIMESTAMP WITHOUT TIME ZONE,
     USER_LAST_CREATED CHARACTER VARYING(200),
     DATE_LAST_UPDATED TIMESTAMP WITHOUT TIME ZONE,
     USER_LAST_UPDATED CHARACTER VARYING(200),
     NCI_DESIGNATED_CANCER_CENTER_INDICATOR BOOLEAN,
     CTGOV_XML_REQUIRED_INDICATOR BOOLEAN,
     SUBMITTER_ORGANIZATION_IDENTIFIER CHARACTER VARYING(50),
     SITE_PROTOCOL_IDENTIFIER CHARACTER VARYING(50),
     SITE_PRINCIPAL_INVESTOGATOR_IDENTIFIER CHARACTER VARYING(50),
     SITE_TARGET_ACCRUAL BIGINT,
     SITE_SUMMARY_FOUR_ORG_IDENTIFIER CHARACTER VARYING(50),
     SITE_SUMMARY_FOUR_FUNDING_TYPE_CODE CHARACTER VARYING(200),   
     SITE_PROGRAM_CODE_TEXT CHARACTER VARYING(100),
     SITE_RECRUITMENT_STATUS CHARACTER VARYING(50),
     SITE_RECRUITMENT_STATUS_DATE TIMESTAMP WITHOUT TIME ZONE,
     OPENDED_FOR_ACCRUAL_DATE TIMESTAMP WITHOUT TIME ZONE,
     CLOSED_FOR_ACCRUAL_DATE TIMESTAMP WITHOUT TIME ZONE,
     PI_INITIATED_INDICATOR BOOLEAN,
     SITE_NCI_DESIGNATED_CANCER_CENTER_INDICATOR BOOLEAN,
     CONSTRAINT STUDY_PROTOCOL_STAGE_PKEY PRIMARY KEY (IDENTIFIER));
	 
	 --DROP TABLE STUDY_FUNDING_STAGE ;
CREATE TABLE STUDY_FUNDING_STAGE (
    IDENTIFIER SERIAL NOT NULL,
    FUNDING_MECHANISM_CODE CHARACTER VARYING(200),
    NIH_INSTITUTE_CODE CHARACTER VARYING(200),
    NCI_DIVISION_PROGRAM_CODE CHARACTER VARYING(200),
    SERIAL_NUMBER CHARACTER VARYING(6),
    DATE_LAST_CREATED TIMESTAMP WITHOUT TIME ZONE ,
    USER_LAST_CREATED CHARACTER VARYING(200) ,
    USER_LAST_UPDATED CHARACTER VARYING(200),
    DATE_LAST_UPDATED TIMESTAMP WITHOUT TIME ZONE, 
    STUDY_PROTOCOL_STAGE_IDENTIFIER BIGINT NOT NULL,
    CONSTRAINT STUDY_FUNDING_STAGE_PKEY PRIMARY KEY (IDENTIFIER),
    CONSTRAINT FK_STUDY_FUNDING_ST_STUDY_PROTOCOL_ST FOREIGN KEY (STUDY_PROTOCOL_STAGE_IDENTIFIER)
    REFERENCES STUDY_PROTOCOL_STAGE (IDENTIFIER) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE CASCADE);
	
	
	--DROP TABLE STUDY_INDIDE_STAGE ;

CREATE TABLE STUDY_INDIDE_STAGE (
    IDENTIFIER SERIAL NOT NULL,
    EXPANDED_ACCESS_STATUS_CODE CHARACTER VARYING(200),
    EXPANDED_ACCESS_INDICATOR BOOLEAN,
    GRANTOR_CODE CHARACTER VARYING(200),
    NIH_INST_HOLDER_CODE CHARACTER VARYING(200),
    NCI_DIV_PROG_HOLDER_CODE CHARACTER VARYING(200),
    HOLDER_TYPE_CODE CHARACTER VARYING(200),
    INDIDE_NUMBER CHARACTER VARYING(200),
    INDIDE_TYPE_CODE CHARACTER VARYING(200),
    DATE_LAST_CREATED TIMESTAMP WITHOUT TIME ZONE,
    USER_LAST_CREATED CHARACTER VARYING(200),
    DATE_LAST_UPDATED TIMESTAMP WITHOUT TIME ZONE,
    USER_LAST_UPDATED CHARACTER VARYING(200),
    STUDY_PROTOCOL_STAGE_IDENTIFIER BIGINT NOT NULL,
    CONSTRAINT STUDY_INDIDE_STAGE_PKEY PRIMARY KEY(IDENTIFIER),
    CONSTRAINT FK_STUDY_INDIDE_ST_STUDY_PROTOCOL_ST FOREIGN KEY (STUDY_PROTOCOL_STAGE_IDENTIFIER)
    REFERENCES STUDY_PROTOCOL_STAGE (IDENTIFIER) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE CASCADE);