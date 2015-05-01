DROP TRIGGER IF EXISTS study_milestone_set_active_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_admin_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_last_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_other_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_milestone_set_scientific_trigger ON study_milestone;
DROP TRIGGER IF EXISTS study_overall_status_set_current_trigger ON study_overall_status;

DROP INDEX IF EXISTS sm_active;
DROP INDEX IF EXISTS sm_admin;
DROP INDEX IF EXISTS sm_last;
DROP INDEX IF EXISTS sm_other;
DROP INDEX IF EXISTS sm_scientific;
DROP INDEX IF EXISTS sos_current;
DROP INDEX IF EXISTS sm_milestone_code;
DROP INDEX IF EXISTS sm_milestone_date;
DROP INDEX IF EXISTS STUDY_MILESTONE_STUDY_PROTOCOL_IDX;


alter table study_milestone drop column if exists active cascade;
alter table study_milestone add column active boolean;

alter table study_milestone drop column if exists admin cascade;
alter table study_milestone add column admin boolean;

alter table study_milestone drop column if exists last cascade;
alter table study_milestone add column last boolean;

alter table study_milestone drop column if exists other cascade;
alter table study_milestone add column other boolean;

alter table study_milestone drop column if exists scientific cascade;
alter table study_milestone add column scientific boolean;

alter table study_overall_status drop column if exists current cascade;
alter table study_overall_status add column current boolean;

-- Active flag.
CREATE OR REPLACE FUNCTION set_active_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set active = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
			update study_milestone sm1 set active = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
			    sm1.identifier = (
			                      SELECT
			                        sm2.identifier
			                      FROM study_milestone sm2
			                      WHERE
			                      (
			                         (OLD.study_protocol_identifier = sm2.study_protocol_identifier)
			                         AND
			                         (
			                            (
			                               sm2.milestone_code
			                            )
			                            ::text <> ALL
			                            (
			                               ARRAY[('SUBMISSION_TERMINATED'::character varying)::text,
			                               ('SUBMISSION_REACTIVATED'::character varying)::text]
			                            )
			                         )
			                      )
			                      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
			                   );   
			RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set active = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set active = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                sm1.identifier = (
                                  SELECT
                                    sm2.identifier
                                  FROM study_milestone sm2
                                  WHERE
                                  (
                                     (NEW.study_protocol_identifier = sm2.study_protocol_identifier)
                                     AND
                                     (
                                        (
                                           sm2.milestone_code
                                        )
                                        ::text <> ALL
                                        (
                                           ARRAY[('SUBMISSION_TERMINATED'::character varying)::text,
                                           ('SUBMISSION_REACTIVATED'::character varying)::text]
                                        )
                                     )
                                  )
                                  ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
                               );    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
    
CREATE OR REPLACE VIEW rv_active_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE sm1.active=true;    
  
  
-- Admin flag                   
CREATE OR REPLACE FUNCTION set_admin_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set admin = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set admin = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
				   (
				      sm1.milestone_date IN
				      (
				         SELECT
				         max(sm2.milestone_date) AS max
				         FROM study_milestone sm2
				         WHERE
				         (
				            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				            AND
				            (
				               (
				                  (
				                     sm2.milestone_code
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
				                     sm2.milestone_code
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
				            )
				         )
				      )
				   )
				   AND
				   (
				      (
				         sm1.milestone_code
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
				);   
            RETURN null;
            
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set admin = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set admin = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                (
					   (
					      sm1.milestone_date IN
					      (
					         SELECT
					         max(sm2.milestone_date) AS max
					         FROM study_milestone sm2
					         WHERE
					         (
					            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
					            AND
					            (
					               (
					                  (
					                     sm2.milestone_code
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
					                     sm2.milestone_code
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
					            )
					         )
					      )
					   )
					   AND
					   (
					      (
					         sm1.milestone_code
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
					);    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
  
    
CREATE OR REPLACE VIEW rv_admin_milestone AS 
	 SELECT	sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date FROM study_milestone sm1	WHERE sm1.admin=true;

	 
-- Last flag.                   
CREATE OR REPLACE FUNCTION set_last_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set last = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set last = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                    (
					   sm1.identifier IN
						   (
						      SELECT
						      sm2.identifier
						      FROM study_milestone sm2
						      WHERE (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
						      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
						   )
						);   
            RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set last = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set last = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                    (
                       sm1.identifier IN
                           (
                              SELECT
                              sm2.identifier
                              FROM study_milestone sm2
                              WHERE (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
                              ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
                           )
                        );    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
  
    
CREATE OR REPLACE VIEW rv_last_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date FROM study_milestone sm1 WHERE sm1.last=true;
 
 
-- Other flag.                   
CREATE OR REPLACE FUNCTION set_other_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set other = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set other = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
				   sm1.identifier IN
				   (
				      SELECT
				      sm2.identifier
				      FROM study_milestone sm2
				      WHERE
				      (
				         (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				         AND
				         (
				            (
				               sm1.milestone_code
				            )
				            ::text <> ALL
				            (
				               ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
				               ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
				               ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
				               ('ADMINISTRATIVE_QC_START'::character varying)::text,
				               ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text,
				               ('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
				               ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
				               ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
				               ('SCIENTIFIC_QC_START'::character varying)::text,
				               ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
				            )
				         )
				      )
				      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
				   )
				);
				
            RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set other = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set other = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                (
                   sm1.identifier IN
                   (
                      SELECT
                      sm2.identifier
                      FROM study_milestone sm2
                      WHERE
                      (
                         (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
                         AND
                         (
                            (
                               sm1.milestone_code
                            )
                            ::text <> ALL
                            (
                               ARRAY[('ADMINISTRATIVE_PROCESSING_START_DATE'::character varying)::text,
                               ('ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'::character varying)::text,
                               ('ADMINISTRATIVE_READY_FOR_QC'::character varying)::text,
                               ('ADMINISTRATIVE_QC_START'::character varying)::text,
                               ('ADMINISTRATIVE_QC_COMPLETE'::character varying)::text,
                               ('SCIENTIFIC_PROCESSING_START_DATE'::character varying)::text,
                               ('SCIENTIFIC_PROCESSING_COMPLETED_DATE'::character varying)::text,
                               ('SCIENTIFIC_READY_FOR_QC'::character varying)::text,
                               ('SCIENTIFIC_QC_START'::character varying)::text,
                               ('SCIENTIFIC_QC_COMPLETE'::character varying)::text]
                            )
                         )
                      )
                      ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1
                   )
                );   
 
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
   
    
CREATE OR REPLACE VIEW rv_other_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date FROM study_milestone sm1 WHERE sm1.other=true;   

 
-- Scientific flag.
CREATE OR REPLACE FUNCTION set_scientific_study_milestone() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_milestone sm1 set scientific = false where sm1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_milestone sm1 set scientific = true WHERE sm1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
				   (
				      sm1.milestone_date IN
				      (
				         SELECT
				         max(sm2.milestone_date) AS max
				         FROM study_milestone sm2
				         WHERE
				         (
				            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				            AND
				            (
				               (
				                  (
				                     sm2.milestone_code
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
				                     sm2.milestone_code
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
				            )
				         )
				      )
				   )
				   AND
				   (
				      (
				         sm1.milestone_code
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
				);   
				
            RETURN null;
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_milestone sm1 set scientific = false where sm1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_milestone sm1 set scientific = true  WHERE sm1.study_protocol_identifier = NEW.study_protocol_identifier AND 
            (
				   (
				      sm1.milestone_date IN
				      (
				         SELECT
				         max(sm2.milestone_date) AS max
				         FROM study_milestone sm2
				         WHERE
				         (
				            (sm1.study_protocol_identifier = sm2.study_protocol_identifier)
				            AND
				            (
				               (
				                  (
				                     sm2.milestone_code
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
				                     sm2.milestone_code
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
				            )
				         )
				      )
				   )
				   AND
				   (
				      (
				         sm1.milestone_code
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
				)
				;    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;
                    
    
CREATE OR REPLACE VIEW rv_scientific_milestone AS 
 SELECT sm1.study_protocol_identifier, sm1.milestone_code, sm1.milestone_date
   FROM study_milestone sm1
  WHERE sm1.scientific=true;    

  
-- SOS Current flag.
CREATE OR REPLACE FUNCTION set_current_study_overall_status() RETURNS TRIGGER AS $t$
    BEGIN       
        IF (TG_OP = 'DELETE') THEN
            update study_overall_status sos1 set current = false where sos1.study_protocol_identifier=OLD.study_protocol_identifier;
            update study_overall_status sos1 set current = true WHERE sos1.study_protocol_identifier=OLD.study_protocol_identifier AND 
                (
                   sos1.identifier =
                   (
                      SELECT
                      sos2.identifier 
                      FROM study_overall_status sos2
                      WHERE
                      (
                         (sos1.study_protocol_identifier = sos2.study_protocol_identifier)
                         AND (sos2.deleted = false)
                      )
                      ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1
                   )
            );  
            RETURN null;
            
        ELSIF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN 
            update study_overall_status sos1 set current = false where sos1.study_protocol_identifier = NEW.study_protocol_identifier;
            update study_overall_status sos1 set current = true  WHERE sos1.study_protocol_identifier = NEW.study_protocol_identifier AND 
                (
                   sos1.identifier =
                   (
                      SELECT
                      sos2.identifier 
                      FROM study_overall_status sos2
                      WHERE
                      (
                         (sos1.study_protocol_identifier = sos2.study_protocol_identifier)
                         AND (sos2.deleted = false)
                      )
                      ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1
                   )
            );    
            RETURN NULL; 
        END IF;
        RETURN NULL; 
    END;
$t$ LANGUAGE plpgsql;

    
CREATE OR REPLACE VIEW rv_sos_current AS 
 SELECT sos1.status_code, sos1.study_protocol_identifier FROM study_overall_status sos1 WHERE sos1.current=true;   

 
-- Create triggers
CREATE TRIGGER study_milestone_set_active_trigger AFTER INSERT OR UPDATE OR DELETE
   ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_active_study_milestone();
    
CREATE TRIGGER study_milestone_set_admin_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_admin_study_milestone();    
    
CREATE TRIGGER study_milestone_set_last_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_last_study_milestone();    
    
CREATE TRIGGER study_milestone_set_other_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_other_study_milestone();
    
CREATE TRIGGER study_milestone_set_scientific_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_milestone FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_scientific_study_milestone();
    
CREATE TRIGGER study_overall_status_set_current_trigger AFTER INSERT OR UPDATE OR DELETE
    ON study_overall_status FOR EACH ROW    
    WHEN (pg_trigger_depth() = 0)
    EXECUTE PROCEDURE set_current_study_overall_status();    

-- Some indexes.    
create index sm_milestone_date on study_milestone (milestone_date);
create index sm_milestone_code on study_milestone (milestone_code);
CREATE INDEX STUDY_MILESTONE_STUDY_PROTOCOL_IDX on study_milestone (STUDY_PROTOCOL_IDENTIFIER);
    
    
-- Invoke the triggers to populate flags.
update study_milestone sm1 set active = false where sm1.identifier in (select max(identifier) from study_milestone group by  study_protocol_identifier);
update study_overall_status sos1 set current = false where sos1.identifier in (select max(identifier) from study_overall_status group by  study_protocol_identifier);
    
-- Final Indexes
create index sm_active on study_milestone (active);
create index sm_admin on study_milestone (admin);  
create index sm_last on study_milestone (last);  
create index sm_other on study_milestone (other);
create index sm_scientific on study_milestone (scientific);
create index sos_current on study_overall_status (current);

-- Finally, the main search results view.
CREATE OR REPLACE VIEW rv_search_results AS 
 SELECT sp.identifier AS study_protocol_identifier, sp.official_title, 
    sp.proprietary_trial_indicator, sp.record_verification_date, 
    sp.date_last_created, sp.submission_number, sp.ctgov_xml_required_indicator, 
    upd.updating, nciid.extension AS nci_number, 
    nctid.local_sp_indentifier AS nct_number, 
    lo.assigned_identifier AS lead_org_poid, lo.name AS lead_org_name, 
    lo.local_sp_indentifier AS lead_org_sp_identifier, 
    dwf.status_code AS current_dwf_status_code, 
    dwf.status_date_range_low AS current_dwf_status_date, 
    sos.status_code AS current_study_overall_status, 
    ams.milestone_code AS current_admin_milestone, 
    sms.milestone_code AS current_scientific_milestone, 
    oms.milestone_code AS current_other_milestone, 
    coa.identifier AS admin_checkout_identifier, 
    coa.user_identifier AS admin_checkout_user, 
    cos.identifier AS scientific_checkout_identifier, 
    cos.user_identifier AS scientific_checkout_user, 
    spi.first_name AS study_pi_first_name, spi.last_name AS study_pi_last_name, 
    usr.login_name AS user_last_created_login, 
    usr.first_name AS user_last_created_first, 
    usr.last_name AS user_last_created_last, dcp.local_sp_indentifier AS dcp_id, 
    ctep.local_sp_indentifier AS ctep_id, sp.amendment_date, 
    sp.date_last_updated, sp.phase_code, sp.primary_purpose_code, sp.start_date, 
    sr.type_code AS summary4fundingsponsor_type, so.name AS sponsor_name, 
    orp.name AS responsible_party_organization_name, 
    pirp.first_name AS responsible_party_pi_first_name, 
    pirp.last_name AS responsible_party_pi_last_name, 
    usr.login_name AS user_last_updated_login, 
    usr.first_name AS user_last_updated_first, 
    usr.last_name AS user_last_updated_last, 
    sp.pri_compl_date AS primary_completion_date, sp.study_protocol_type, 
    sp.study_subtype_code, ts.submitter_org_name, 
    ams.milestone_date AS current_admin_milestone_date, 
    sms.milestone_date AS current_scientific_milestone_date, 
    oms.milestone_date AS current_other_milestone_date, sp.processing_priority, 
    lms.milestone_code AS last_milestone, 
    lms.milestone_date AS last_milestone_date, 
    actms.milestone_code AS active_milestone, 
    actms.milestone_date AS active_milestone_date, 
    coa.csm_first_name AS admin_checkout_csm_fname, 
    coa.csm_last_name AS admin_checkout_csm_lname, 
    coa.reg_first_name AS admin_checkout_reg_fname, 
    coa.reg_last_name AS admin_checkout_reg_lname, 
    cos.csm_first_name AS scientific_checkout_csm_fname, 
    cos.csm_last_name AS scientific_checkout_csm_lname, 
    cos.reg_first_name AS scientific_checkout_reg_fname, 
    cos.reg_last_name AS scientific_checkout_reg_lname, hold.onhold_reason_code, 
    hold.onhold_date, hold.offhold_date, cdr.extension AS cdr_id, 
    sp.amendment_number, coa.checkout_date AS admin_checkout_date, 
    cos.checkout_date AS scientific_checkout_date, sp.comments, 
    hold.onhold_reason_text, false AS planned_marker_existence_indicator, 
    sp.study_source, ccr.local_sp_indentifier AS ccr_id, 
    sp.accrual_disease_code_system, 
    prev_dwf.status_code AS previous_dwf_status_code, 
    ts.submitter_org_id AS submiting_org_id
   FROM study_protocol sp
   LEFT JOIN rv_updating_trials upd ON sp.identifier = upd.study_protocol_identifier
   LEFT JOIN rv_trial_id_nci nciid ON sp.identifier = nciid.study_protocol_id
   LEFT JOIN rv_trial_id_nct nctid ON sp.identifier = nctid.study_protocol_identifier
   LEFT JOIN rv_lead_organization lo ON sp.identifier = lo.study_protocol_identifier
   LEFT JOIN rv_dwf_current dwf ON sp.identifier = dwf.study_protocol_identifier
   LEFT JOIN rv_sos_current sos ON sp.identifier = sos.study_protocol_identifier
   LEFT JOIN rv_admin_milestone ams ON sp.identifier = ams.study_protocol_identifier
   LEFT JOIN rv_scientific_milestone sms ON sp.identifier = sms.study_protocol_identifier
   LEFT JOIN rv_other_milestone oms ON sp.identifier = oms.study_protocol_identifier
   LEFT JOIN rv_last_milestone lms ON sp.identifier = lms.study_protocol_identifier
   LEFT JOIN rv_active_milestone actms ON sp.identifier = actms.study_protocol_identifier
   LEFT JOIN rv_checkout_admin coa ON sp.identifier = coa.study_protocol_identifier
   LEFT JOIN rv_checkout_scientific cos ON sp.identifier = cos.study_protocol_identifier
   LEFT JOIN rv_study_principal_investigator spi ON sp.identifier = spi.study_protocol_identifier
   LEFT JOIN csm_user usr ON sp.user_last_created_id = usr.user_id
   LEFT JOIN rv_dcp_id dcp ON sp.identifier = dcp.study_protocol_identifier
   LEFT JOIN rv_ctep_id ctep ON sp.identifier = ctep.study_protocol_identifier
   LEFT JOIN rv_study_resourcing sr ON sp.identifier = sr.study_protocol_identifier
   LEFT JOIN rv_sponsor_organization so ON sp.identifier = so.study_protocol_identifier
   LEFT JOIN rv_organization_responsible_party orp ON sp.identifier = orp.study_protocol_identifier
   LEFT JOIN rv_principal_investigator_responsible_party pirp ON sp.identifier = pirp.study_protocol_identifier
   LEFT JOIN rv_trial_submitter ts ON sp.identifier = ts.study_protocol_identifier
   LEFT JOIN rv_last_hold hold ON sp.identifier = hold.study_protocol_identifier
   LEFT JOIN rv_trial_id_cdr cdr ON sp.identifier = cdr.study_protocol_id
   LEFT JOIN rv_ccr_id ccr ON sp.identifier = ccr.study_protocol_identifier
   LEFT JOIN rv_dwf_previous prev_dwf ON sp.identifier = prev_dwf.study_protocol_identifier;