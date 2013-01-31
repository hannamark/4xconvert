DROP TABLE IF EXISTS stg_dw_data_table_4;

CREATE TABLE stg_dw_data_table_4
(
  grant_number character varying(6),
  clinical_research_cat character varying(255),
  study_source character varying(255),
  specific_funding_source character varying(255),
  is_multisite character(1),
  site character varying(255),
  nci_id character varying(255),
  nct_id character varying(200),
  ctep_id character varying(200),
  lead_org_id character varying(200),
  pi_last_name character varying(200),
  pi_first_name character varying(200),
  prog_code character varying(100),
  open_date date,
  close_date date,
  phase character varying(255),
  primary_purpose character varying(255),
  official_title character varying(4000),
  entire_study bigint,
  sp_id integer,
  lead_org_po_id integer,
  is_industrial boolean,
  PRIMARY KEY (nci_id)
);
