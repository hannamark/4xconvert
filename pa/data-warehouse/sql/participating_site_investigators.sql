DROP TABLE IF EXISTS STG_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS CASCADE;
CREATE TABLE STG_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS (
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    INVESTIGATOR_FIRST_NAME character varying(200),
    INVESTIGATOR_MIDDLE_NAME character varying(200),
    INVESTIGATOR_LAST_NAME character varying(200),
    INVESTIGATOR_ROLE character varying(200),
    PARTICIPATING_SITE_ORG_NAME character varying(200),
    ORG_PO_ID integer,
    PERSON_PO_ID character varying(200)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS (
RUN_ID TIMESTAMP,
    INTERNAL_SYSTEM_ID INTEGER,
    NCI_ID character varying(255),
    INVESTIGATOR_FIRST_NAME character varying(200),
    INVESTIGATOR_LAST_NAME character varying(200),
    INVESTIGATOR_ROLE character varying(200),
    PARTICIPATING_SITE_ORG_NAME character varying(200),
    ORG_PO_ID integer,
    PERSON_PO_ID character varying(200)
);

create or replace function alter_site_inv() returns void
language plpgsql as $$
begin
IF  NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS
WHERE lower(TABLE_NAME) = lower('HIST_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS') AND lower(COLUMN_NAME) = lower('INVESTIGATOR_MIDDLE_NAME')) 
THEN
alter table HIST_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS add column INVESTIGATOR_MIDDLE_NAME character varying(200);
END IF;
end $$;

select alter_site_inv();
