ALTER TABLE intervention ADD COLUMN ctgov_type_code character varying(200);
ALTER TABLE intervention ALTER COLUMN ctgov_type_code SET STORAGE EXTENDED;
ALTER TABLE intervention ALTER COLUMN pdq_term_identifier SET NOT NULL;
ALTER TABLE intervention ADD CONSTRAINT intervention_unique UNIQUE (pdq_term_identifier);

ALTER TABLE intervention_alternate_name ADD COLUMN name_type_code character varying(200);
ALTER TABLE intervention_alternate_name ALTER COLUMN name_type_code SET STORAGE EXTENDED;

ALTER TABLE disease ALTER COLUMN disease_code SET NOT NULL;
ALTER TABLE disease ADD CONSTRAINT disease_unique UNIQUE (disease_code);

ALTER TABLE study_disease DROP CONSTRAINT fk_study_disease_disease;
ALTER TABLE study_disease ADD CONSTRAINT fk_study_disease_disease FOREIGN KEY (disease_identifier)
      REFERENCES disease (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE RESTRICT;
