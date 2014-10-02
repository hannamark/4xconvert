DROP TABLE IF EXISTS stg_dw_failed_patient_accruals;

CREATE TABLE stg_dw_failed_patient_accruals
(   
   study_identifier varchar(200),
   study_protocol_identifier bigint,
   assigned_identifier varchar(200),
   race_code varchar(200),
   sex_code varchar(200),
   ethnic_code varchar(200),
   birth_date timestamp,
   country_code varchar(200),
   zip varchar(200),
   payment_method_code varchar(200),
   study_site varchar(200),
   disease_code varchar(200),
   registration_date timestamp,
   registration_group_id varchar(200),
   accrual_count int,
   file_name varchar(1000),
   submission_status varchar(200),
   date_last_created timestamp,
   date_last_updated timestamp,
   user_last_created_id int,
   user_last_updated_id int,
   site_disease_code varchar(200)
);