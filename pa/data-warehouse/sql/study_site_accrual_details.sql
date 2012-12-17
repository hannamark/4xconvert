DROP TABLE IF EXISTS stg_dw_study_site_accrual_details;

CREATE TABLE stg_dw_study_site_accrual_details ( 
    country character varying(200),
    date_last_created timestamp without time zone,
    date_last_updated timestamp without time zone,
    deletion_reason character varying(200),
    ethnicity character varying(200),
    gender character varying(200),
    icd9_disease_code character varying(10),
    icd9_disease_term character varying(200),
    nci_id character varying(255),
    org_name character varying(200),
    org_org_family character varying(160),
    payment_method character varying(200),
    race character varying(200),
    registration_date timestamp without time zone,
    registration_group character varying(200),
    sdc_disease_code character varying(10),
    sdc_disease_term character varying(200),
    site_org_id bigint,
    status character varying(200),
    study_subject_id character varying(200),
    user_name_last_created character varying(500),
    user_name_last_updated character varying(500),
    user_last_created_id integer,
    user_last_updated_id integer
);