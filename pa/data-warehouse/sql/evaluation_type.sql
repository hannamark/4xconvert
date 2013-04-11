DROP TABLE IF EXISTS STG_DW_EVALUATION_TYPE;
CREATE TABLE STG_DW_EVALUATION_TYPE (
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);
