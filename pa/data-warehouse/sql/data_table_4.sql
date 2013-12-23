DROP TABLE IF EXISTS stg_dw_data_table_4;

CREATE TABLE stg_dw_data_table_4
(
  clinical_research_cat character varying(255),
  study_source character varying(255),
  specific_funding_source character varying(255),
  is_multiinstitutional character(1),
  site character varying(255),
  nci_id character varying(255),
  nct_id character varying(200),
  ctep_dcp_id character varying(200),
  lead_org_id character varying(200),
  other_ids character varying(4000),
  pi_last_name character varying(200),
  pi_first_name character varying(200),
  pi_middle_initial character varying(1),
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
  summary_level_accrual boolean,
  consortia_trial_category character varying(50),
  PRIMARY KEY (nci_id)
);
