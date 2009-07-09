-- ALTER TABLE disease DROP COLUMN deprecated;
ALTER TABLE disease ADD COLUMN deprecated boolean;
ALTER TABLE disease ALTER COLUMN deprecated SET STORAGE PLAIN;

-- ALTER TABLE intervention DROP COLUMN ctgov_type_code;
ALTER TABLE intervention ADD COLUMN ctgov_type_code character varying(200);
ALTER TABLE intervention ALTER COLUMN ctgov_type_code SET STORAGE EXTENDED;

-- ALTER TABLE intervention DROP COLUMN deprecated;
ALTER TABLE intervention ADD COLUMN deprecated boolean;
ALTER TABLE intervention ALTER COLUMN deprecated SET STORAGE PLAIN;

-- ALTER TABLE intervention_alternate_name DROP COLUMN name_type_code;
ALTER TABLE intervention_alternate_name ADD COLUMN name_type_code character varying(200);
ALTER TABLE intervention_alternate_name ALTER COLUMN name_type_code SET STORAGE EXTENDED;

