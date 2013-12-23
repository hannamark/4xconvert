DROP TABLE IF EXISTS stg_dw_grants_p30;

CREATE TABLE stg_dw_grants_p30
(
  family_id bigint NOT NULL,
  family_name character varying(200),
  serial_number character varying(6) NOT NULL,
  PRIMARY KEY (family_id)
);
