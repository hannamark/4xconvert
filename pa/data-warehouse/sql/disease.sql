DROP TABLE IF EXISTS DW_STUDY_DISEASE;
CREATE TABLE DW_STUDY_DISEASE ( 
    CT_GOV_XML_INDICATOR character varying(3),
    DATE_LAST_CREATED date,
    DATE_LAST_UPDATED date,
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

INSERT INTO DW_STUDY_DISEASE (CT_GOV_XML_INDICATOR, DATE_LAST_CREATED, DATE_LAST_UPDATED, DISEASE_CODE, 
    DISEASE_PREFERRED_NAME, DISEASE_MENU_DISPLAY_NAME, INTERNAL_SYSTEM_ID, LEAD_DISEASE_INDICATOR, NCI_ID, 
    NCI_THESAURUS_CONCEPT_ID, USER_LAST_CREATED, USER_LAST_UPDATED)
    SELECT 
        CASE WHEN sd.ctgovxml_indicator THEN 'YES'
             ELSE 'NO'
        END,
        sd.date_last_created, sd.date_last_updated, disease.disease_code, disease.preferred_name, disease.menu_display_name, 
        sd.identifier, 
        CASE WHEN sd.lead_disease_indicator THEN 'YES'
             ELSE 'NO'
        END,
        nci_id.extension, disease.nt_term_identifier,
        CASE WHEN ru_creator.first_name is null THEN split_part(creator.login_name, 'CN=', 2)
             ELSE ru_creator.first_name || ' ' || ru_creator.last_name
        END,
        CASE WHEN ru_updater.first_name is null THEN split_part(updater.login_name, 'CN=', 2)
            ELSE ru_updater.first_name || ' ' || ru_updater.last_name
        END
    FROM STUDY_DISEASE sd
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sd.study_protocol_identifier 
            and nci_id.root = '2.16.840.1.113883.3.26.4.3'
        left outer join pdq_disease as disease on disease.identifier = sd.disease_identifier
        left outer join study_protocol as sp on sp.identifier = sd.study_protocol_identifier
        left outer join csm_user as creator on sd.user_last_created_id = creator.user_id
        left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
        left outer join csm_user as updater on sd.user_last_created_id = updater.user_id
        left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
    where sp.status_code = 'ACTIVE';

