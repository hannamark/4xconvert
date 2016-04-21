DROP TABLE IF EXISTS stg_dw_study_family_program_code; 
CREATE TABLE stg_dw_study_family_program_code (
	NCI_ID  CHARACTER VARYING(200),
	org_family CHARACTER VARYING(200),
    family_po_id integer, 
	program_codes varchar,
	dt4_program_codes varchar
);
