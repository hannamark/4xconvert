--DROP TABLE TEMP_STUDY_PROTOCOL;

CREATE TABLE TEMP_STUDY_PROTOCOL(
     IDENTIFIER BIGINT NOT NULL,
     NCT_IDENTIFIER CHARACTER VARYING(200),
     CTEP_IDENTIFIER CHARACTER VARYING(200),
     DCP_IDENTIFIER CHARACTER VARYING(200),
     OFFICIAL_TITLE CHARACTER VARYING(4000),
     PHASE_CODE CHARACTER VARYING(50),
     PHASE_OTHER_TEXT CHARACTER VARYING(200),
     PRIMARY_PURPOSE_CODE CHARACTER VARYING(50),
     PRIMARY_PURPOSE_OTHER_TEXT CHARACTER VARYING(1000),
     LOCAL_PROTOCOL_INDENTIFIER CHARACTER VARYING(200) NOT NULL,
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
     CONSTRAINT TEMP_STUDY_PROTOCOL_PKEY PRIMARY KEY (IDENTIFIER));