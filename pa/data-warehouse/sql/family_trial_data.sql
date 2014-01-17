DROP TABLE IF EXISTS stg_dw_family_trial_data; 
CREATE TABLE stg_dw_family_trial_data (
	family_name CHARACTER VARYING(200) NOT NULL,
	nci_id CHARACTER VARYING(255) NOT NULL,
	program_codes CHARACTER VARYING(4000)
);
