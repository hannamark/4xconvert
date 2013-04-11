DROP TABLE IF EXISTS STG_DW_BIOMARKER_PURPOSE;
CREATE TABLE STG_DW_BIOMARKER_PURPOSE (
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);