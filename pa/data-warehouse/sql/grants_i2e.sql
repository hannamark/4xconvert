DROP TABLE IF EXISTS stg_dw_grants_i2e;

CREATE TABLE stg_dw_grants_i2e
(
  appl_id bigint NOT NULL,
  grant_number character varying(19),
  grant_type_code character(1),
  impac_ii_activity_code character varying(3),
  primary_icd_code character varying(2),
  serial_number integer,
  support_year integer,
  suffix_code character varying(4),
  fy integer,
  budget_start_date timestamp without time zone,
  budget_end_date timestamp without time zone,
  project_title character varying(81),
  project_start_date timestamp without time zone,
  project_end_date timestamp without time zone,
  institution_name character varying(40),
  pi_name_prefix character varying(15),
  pi_first_name character varying(30),
  pi_mi_name character varying(30),
  pi_last_name character varying(30),
  pi_name_suffix character varying(5),
  pi_title character varying(240),
  institution_state character varying(14),
  institution_city character varying(35),
  institution_zip character varying(9),
  institution_country character varying(14),
  institution_email character varying(255),
  PRIMARY KEY (appl_id)
);