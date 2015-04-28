DROP INDEX IF EXISTS study_site_healthcare_facility_identifier;
create index study_site_healthcare_facility_identifier ON study_site (healthcare_facility_identifier);

DROP INDEX IF EXISTS hcf_organization_identifier;
create index hcf_organization_identifier ON healthcare_facility (organization_identifier);

DROP INDEX IF EXISTS sm_milestone_code;
create index sm_milestone_code on study_milestone (milestone_code);

DROP INDEX IF EXISTS sm_milestone_date;
create index sm_milestone_date on study_milestone (milestone_date);
   
DROP INDEX IF EXISTS sm_rv_admin_milestone01;
create index sm_rv_admin_milestone01 on study_milestone (study_protocol_identifier, milestone_code) WHERE (
               (
                  (
                     milestone_code
                  )
                  ::text = ANY
                  (
                     ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
                     ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
                     ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
                     ('ADMINISTRATIVE_QC_START'::character varying)::text,
                     ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text]
                  )
               )
               OR
               (
                  (
                     milestone_code
                  )
                  ::text = ANY
                  (
                     ARRAY[('READY_FOR_TSR'::character varying)::text,
                     ('TRIAL_SUMMARY_REPORT'::character varying)::text,
                     ('INITIAL_ABSTRACTION_VERIFY'::character varying)::text,
                     ('TRIAL_SUMMARY_FEEDBACK'::character varying)::text,
                     ('ONGOING_ABSTRACTION_VERIFICATION'::character varying)::text,
                     ('LATE_REJECTION_DATE'::character varying)::text]
                  )
               )
            );
            
DROP INDEX IF EXISTS sm_rv_admin_milestone02;
create index sm_rv_admin_milestone02 on study_milestone (milestone_code) WHERE (
      (
         milestone_code
      )
      ::text = ANY
      (
         ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
         ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
         ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
         ('ADMINISTRATIVE_QC_START'::character varying)::text,
         ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text]
      )
   );
   

     
DROP INDEX IF EXISTS sm_rv_scientific_milestone01;
create index sm_rv_scientific_milestone01 on study_milestone (study_protocol_identifier,milestone_code) WHERE (
               (
                  (
                     milestone_code
                  )
                  ::text = ANY
                  (
                     ARRAY[('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
                     ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
                     ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
                     ('SCIENTIFIC_QC_START'::character varying)::text,
                     ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
                  )
               )
               OR
               (
                  (
                     milestone_code
                  )
                  ::text = ANY
                  (
                     ARRAY[('READY_FOR_TSR'::character varying)::text,
                     ('TRIAL_SUMMARY_REPORT'::character varying)::text,
                     ('INITIAL_ABSTRACTION_VERIFY'::character varying)::text,
                     ('TRIAL_SUMMARY_FEEDBACK'::character varying)::text,
                     ('ONGOING_ABSTRACTION_VERIFICATION'::character varying)::text,
                     ('LATE_REJECTION_DATE'::character varying)::text]
                  )
               )
            );
            
DROP INDEX IF EXISTS sm_rv_scientific_milestone02;
create index sm_rv_scientific_milestone02 on study_milestone (milestone_code) WHERE (
      (
         milestone_code
      )
      ::text = ANY
      (
         ARRAY[('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
         ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
         ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
         ('SCIENTIFIC_QC_START'::character varying)::text,
         ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
      )
   ); 


vacuum analyze study_milestone;





