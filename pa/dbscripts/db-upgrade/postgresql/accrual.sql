DROP TABLE IF EXISTS epoch;
DROP TABLE IF EXISTS study_subject;  -- includes Subject
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS planned_study_subject_milestone;  -- NO LONGER USED, USING InheritanceType.SINGLE_TABLE
DROP TABLE IF EXISTS observation_result;  -- includes PlannedObservationResult and PerformedObservationResult
DROP TABLE IF EXISTS performed_activity;  -- includes PerformedObservation, PerformedSubjectMilestone, and PerformedAdministrativeActivity

-- the following three tables pending further requirements definition
--DROP TABLE IF EXISTS report;
--DROP TABLE IF EXISTS cdus_report;
--DROP TABLE IF EXISTS basic_results_report;


-- Table: epoch
CREATE TABLE epoch
(
    identifier SERIAL NOT NULL,
    study_protocol_identifier bigint NOT NULL,
    "name" character varying(200) NOT NULL,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200) ,
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE epoch ADD CONSTRAINT fk_epoch_study_protocol
FOREIGN KEY (study_protocol_identifier) REFERENCES study_protocol (identifier)
ON DELETE CASCADE;


-- Table: patient
CREATE TABLE patient (
    identifier SERIAL NOT NULL,
    person_identifier BIGINT NOT NULL,
    status_code VARCHAR(200) NOT NULL,
    status_date_range_low TIMESTAMP,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200) ,
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE patient ADD CONSTRAINT fk_patient_person
FOREIGN KEY (person_identifier) REFERENCES person (identifier)
ON DELETE CASCADE;


-- Table: study_subject
CREATE TABLE study_subject (
    identifier SERIAL NOT NULL,
    patient_identifier BIGINT NOT NULL,
    study_protocol_identifier BIGINT NOT NULL,
    arm_identifier BIGINT,
    payment_method_code VARCHAR(200) NOT NULL ,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200) ,
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_patient
FOREIGN KEY (patient_identifier) REFERENCES patient (identifier)
ON DELETE RESTRICT;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_study_protocol
FOREIGN KEY (study_protocol_identifier) REFERENCES study_protocol (identifier)
ON DELETE CASCADE;

ALTER TABLE study_subject ADD CONSTRAINT fk_study_subject_arm
FOREIGN KEY (arm_identifier) REFERENCES arm (identifier)
ON DELETE SET NULL;


-- Table:  performed_activity
CREATE TABLE performed_activity (
    identifier SERIAL NOT NULL,
    category_code VARCHAR(200),
    subcategory_code VARCHAR(200),
    text_description VARCHAR(2000),
    actual_date_range_low TIMESTAMP,
    actual_date_range_high TIMESTAMP,
    informed_consent_date TIMESTAMP,
    reason_not_completed_type_other VARCHAR(200),
    study_protocol_identifier BIGINT NOT NULL,
    performed_activity_type VARCHAR(100),
    date_last_created TIMESTAMP,
    user_last_created VARCHAR(200),
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200),

    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE performed_activity ADD CONSTRAINT fk_performed_activity_study_protocol
FOREIGN KEY (study_protocol_identifier) REFERENCES study_protocol (identifier)
ON DELETE RESTRICT;


-- Table:  observation_result
CREATE TABLE observation_result (
    identifier SERIAL NOT NULL,
    result_code VARCHAR(200),
    result_code_modified_text VARCHAR(200),
    result_date_range_low TIMESTAMP,
    result_date_range_high TIMESTAMP,
    result_indicator BOOLEAN,
    result_text VARCHAR(200),
    type_code VARCHAR(200),
    unit_of_measure_code VARCHAR(200),
    observation_result_type VARCHAR(200),
    planned_activity_identifier BIGINT,
    performed_activity_identifier BIGINT,
    date_last_created TIMESTAMP,
    user_last_create VARCHAR(200) ,
    date_last_updated TIMESTAMP,
    user_last_updated VARCHAR(200) ,
    PRIMARY KEY (identifier)
)WITH (OIDS=FALSE);

ALTER TABLE observation_result ADD CONSTRAINT fk_observation_result_planned_activity
FOREIGN KEY (planned_activity_identifier) REFERENCES planned_activity (identifier)
ON DELETE RESTRICT;

ALTER TABLE observation_result ADD CONSTRAINT fk_observation_result_performed_activity
FOREIGN KEY (performed_activity_identifier) REFERENCES performed_activity (identifier)
ON DELETE RESTRICT;

