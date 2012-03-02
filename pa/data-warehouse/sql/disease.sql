DROP TABLE IF EXISTS DW_STUDY_DISEASE;
CREATE TABLE DW_STUDY_DISEASE ( 
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

CREATE INDEX DW_STUDY_DISEASE_CT_GOV_XML_INDICATOR_IDX on dw_study_disease(ct_gov_xml_indicator);
CREATE INDEX DW_STUDY_DISEASE_DATE_LAST_CREATED_IDX on dw_study_disease(date_last_created);
CREATE INDEX DW_STUDY_DISEASE_DATE_LAST_UPDATED_IDX on dw_study_disease(date_last_updated);
CREATE INDEX DW_STUDY_DISEASE_DISEASE_CODE_IDX on dw_study_disease(disease_code);
CREATE INDEX DW_STUDY_DISEASE_DISEASE_PREFERRED_NAME_IDX on dw_study_disease(disease_preferred_name);
CREATE INDEX DW_STUDY_DISEASE_DISEASE_MENU_DISPLAY_NAME_IDX on dw_study_disease(disease_menu_display_name);
CREATE INDEX DW_STUDY_DISEASE_INTERNAL_SYSTEM_ID_IDX on dw_study_disease(internal_system_id);
CREATE INDEX DW_STUDY_DISEASE_LEAD_DISEASE_INDICATOR_IDX on dw_study_disease(lead_disease_indicator);
CREATE INDEX DW_STUDY_DISEASE_NCI_ID_IDX on dw_study_disease(nci_id);
CREATE INDEX DW_STUDY_DISEASE_NCI_THESAURUS_CONCEPT_ID_IDX on dw_study_disease(nci_thesaurus_concept_id);
CREATE INDEX DW_STUDY_DISEASE_USER_LAST_CREATED_IDX on dw_study_disease(user_last_created);
CREATE INDEX DW_STUDY_DISEASE_USER_LAST_UPDATED_IDX on dw_study_disease(user_last_updated);