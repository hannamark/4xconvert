DROP TABLE IF EXISTS stg_dw_program_code_master_list; 
CREATE TABLE stg_dw_program_code_master_list (
	family_po_id INTEGER ,
	org_family CHARACTER VARYING(200),
	program_code CHARACTER VARYING(4000),
	program_name CHARACTER VARYING(4000),
	program_code_status CHARACTER VARYING(200)
);
