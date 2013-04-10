DROP TABLE IF EXISTS stg_dw_study_association;

CREATE TABLE stg_dw_study_association (
  study_a character varying(255),
  study_b character varying(255),
  identifier_type character varying(32),
  study_protocol_type character varying(32),
  study_subtype_code character varying(64),
  official_title character varying(4000)
);
