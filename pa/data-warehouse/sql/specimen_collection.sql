DROP TABLE IF EXISTS STG_DW_SPECIMEN_COLLECTION;
CREATE TABLE STG_DW_SPECIMEN_COLLECTION  (
  type_code character varying(200),
  description_text character varying(400),
  cadsr_id integer
);