DROP TABLE IF EXISTS DW_STUDY_PARTICIPATING_SITE CASCADE;
CREATE TABLE DW_STUDY_PARTICIPATING_SITE (
    CONTACT_EMAIL character varying(200),
    CONTACT_NAME character varying(600),
    GENERIC_CONTACT character varying(255),
    INTERNAL_SYSTEM_ID INTEGER,
    INVESTIGATOR1_NAME character varying(600),
    INVESTIGATOR1_ROLE character varying(200),
    INVESTIGATOR1_STATUS character varying(50),
    INVESTIGATOR2_NAME character varying(600),
    INVESTIGATOR2_ROLE character varying(200),
    INVESTIGATOR2_STATUS character varying(50),
    NCI_ID character varying(255),
    ORG_NAME character varying(200),
    ORG_ORG_FAMILY character varying(200),
    ORG_STATUS character varying(200),
    RECRUITMENT_STATUS character varying(50),
    RECRUITMENT_STATUS_DATE date,
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);

CREATE INDEX DW_STUDY_PARTICIPATING_SITE_CONTACT_EMAIL_IDX on dw_study_participating_site(contact_email);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_CONTACT_NAME_IDX on dw_study_participating_site(contact_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_GENERIC_CONTACT_IDX on dw_study_participating_site(generic_contact);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INTERNAL_SYSTEM_ID_IDX on dw_study_participating_site(internal_system_id);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR1_NAME_IDX on dw_study_participating_site(investigator1_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR1_ROLE_IDX on dw_study_participating_site(investigator1_role);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR1_STATUS_IDX on dw_study_participating_site(investigator1_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR2_NAME_IDX on dw_study_participating_site(investigator2_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR2_ROLE_IDX on dw_study_participating_site(investigator2_role);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR2_STATUS_IDX on dw_study_participating_site(investigator2_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_NCI_ID_IDX on dw_study_participating_site(nci_id);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_NAME_IDX on dw_study_participating_site(org_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_ORG_FAMILY_IDX on dw_study_participating_site(org_org_family);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_STATUS_IDX on dw_study_participating_site(org_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_RECRUITMENT_STATUS_IDX on dw_study_participating_site(recruitment_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_RECRUITMENT_STATUS_DATE_IDX on dw_study_participating_site(recruitment_status_date);