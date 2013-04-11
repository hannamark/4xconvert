DROP TABLE IF EXISTS STG_DW_BIOMARKER_USE;
CREATE TABLE STG_DW_BIOMARKER_USE (
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);