DROP TABLE IF EXISTS STG_DW_STUDY_DISEASE;
CREATE TABLE STG_DW_STUDY_DISEASE ( 
    CT_GOV_XML_INDICATOR character varying(3),
    DATE_LAST_CREATED timestamp,
    DATE_LAST_UPDATED timestamp,
    DISEASE_CODE character varying(200),
    DISEASE_PREFERRED_NAME character varying(200),
    DISEASE_MENU_DISPLAY_NAME character varying(200),
    INTERNAL_SYSTEM_ID INTEGER,
    LEAD_DISEASE_INDICATOR character varying(3),
    NCI_ID character varying(255),
    NCI_THESAURUS_CONCEPT_ID character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);

