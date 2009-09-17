DROP TABLE IF EXISTS study_subject_audit;
DROP TABLE IF EXISTS patient_audit;

-- Table: patient_audit 
CREATE TABLE patient_audit (
	audit_id SERIAL NOT NULL,
	audit_type VARCHAR(20),
	audit_user VARCHAR(200),
    patient_identifier VARCHAR(200),
    patient_assigned_identifier VARCHAR(200),
    person_assigned_identifier VARCHAR(200),
    race_code VARCHAR(200),
    sex_code VARCHAR(200),
    ethnic_code VARCHAR(200),
    birth_date TIMESTAMP,
    status_code VARCHAR(200) NOT NULL,
    status_date_range_low TIMESTAMP,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200),
    PRIMARY KEY (audit_id)
)WITH (OIDS=FALSE);

CREATE FUNCTION updatePatient_audit() RETURNS TRIGGER AS $$
DECLARE
currentRecord RECORD;
BEGIN

FOR currentRecord IN SELECT * FROM patient where identifier = NEW.identifier LOOP

insert into patient_audit(audit_type, audit_user, patient_identifier, patient_assigned_identifier,person_assigned_identifier, 
	race_code, sex_code, ethnic_code, birth_date, status_code, status_date_range_low, date_last_created, user_last_create,
    date_last_updated, user_last_updated) 
    VALUES ('Update', currentRecord.user_last_updated,  currentRecord.identifier, currentRecord.assigned_identifier,
    currentRecord.person_assigned_identifier, currentRecord.race_code, currentRecord.sex_code, currentRecord.ethnic_code, 
    currentRecord.birth_date, currentRecord.status_code, currentRecord.status_date_range_low,  currentRecord.date_last_created, 
    currentRecord.user_last_create, currentRecord.date_last_updated,  currentRecord.user_last_updated);
	
RETURN NEW;
END LOOP;
END;
$$LANGUAGE 'plpgsql';
 
CREATE TRIGGER "trgPatient" AFTER UPDATE
ON patient FOR EACH ROW
EXECUTE PROCEDURE updatePatient_audit();


-- Table: study_subject_audit
CREATE TABLE study_subject_audit (
	audit_id SERIAL NOT NULL,
	audit_type VARCHAR(20),
	audit_user VARCHAR(200),
    study_subject_identifier VARCHAR(200),
    patient_identifier BIGINT NOT NULL,
    study_protocol_identifier BIGINT NOT NULL,
    study_site_identifier BIGINT,
    disease_identifier BIGINT,
    payment_method_code VARCHAR(200) NOT NULL ,
    status_code VARCHAR(200),
    status_date_range_low TIMESTAMP,
    status_date_range_high TIMESTAMP,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200) ,
    PRIMARY KEY (audit_id)
)WITH (OIDS=FALSE);

CREATE FUNCTION updateStudy_subject_audit() RETURNS TRIGGER AS $$
DECLARE
currentRecord RECORD;
BEGIN

FOR currentRecord IN SELECT * FROM study_subject where identifier = NEW.identifier LOOP

insert into study_subject_audit(audit_type, audit_user, study_subject_identifier, patient_identifier, study_protocol_identifier,study_site_identifier, 
	disease_identifier, payment_method_code, status_code, status_date_range_low, status_date_range_high, date_last_created, user_last_create,
    date_last_updated, user_last_updated) 
    VALUES ('Update', currentRecord.user_last_updated,  currentRecord.identifier,  currentRecord.patient_identifier,  
    currentRecord.study_protocol_identifier, currentRecord.study_site_identifier, currentRecord.disease_identifier,  currentRecord.payment_method_code, 
    currentRecord.status_code,  currentRecord.status_date_range_low,  currentRecord.status_date_range_high,  currentRecord.date_last_created, 
    currentRecord.user_last_create, currentRecord.date_last_updated,  currentRecord.user_last_updated);
	
RETURN NEW;
END LOOP;
END;
$$LANGUAGE 'plpgsql';
 
CREATE TRIGGER "trgStudy_subject" AFTER UPDATE
ON study_subject FOR EACH ROW
EXECUTE PROCEDURE updateStudy_subject_audit();
