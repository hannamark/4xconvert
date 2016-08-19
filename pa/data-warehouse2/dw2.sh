#!/bin/sh


echo 'cleaning up old DW2 dump'
psql -U postgres -h localhost ctrpdw2 <<EOF
DROP TABLE IF EXISTS dw_study;
DROP TABLE IF EXISTS dw_study_arm_and_intervention;
DROP TABLE IF EXISTS dw_study_association;
DROP TABLE IF EXISTS dw_study_collaborator;
DROP TABLE IF EXISTS dw_study_disease;
DROP TABLE IF EXISTS dw_study_eligibility_criteria;
DROP TABLE IF EXISTS dw_study_grant;
DROP TABLE IF EXISTS dw_study_other_identifier;
DROP TABLE IF EXISTS dw_study_outcome_measure;
DROP TABLE IF EXISTS dw_study_participating_site;
DROP TABLE IF EXISTS dw_study_participating_site_investigators;
DROP TABLE IF EXISTS dw_study_secondary_purpose;
DROP TABLE IF EXISTS dw_study_anatomic_site;
DROP TABLE IF EXISTS dw_organization;
DROP TABLE IF EXISTS dw_study_overall_status;
EOF
# psql -U postgres ctrpdw2 < DW2-interm.sql
echo 'Creating DW dump and piping it to DW2 database'
pg_dump -h ncidb-p126.nci.nih.gov \
	-p 5472\
	-U copparead \
	-w \
	--clean \
	--create \
	--no-owner \
	-F p \
	-t dw_study \
	-t dw_study_overall_status \
	-t dw_study_anatomic_site \
	-t dw_organization \
	-t dw_study_arm_and_intervention \
	-t dw_study_association \
	-t dw_study_collaborator \
	-t dw_study_disease \
	-t dw_study_eligibility_criteria \
	-t dw_study_grant \
	-t dw_study_other_identifier \
	-t dw_study_outcome_measure \
	-t dw_study_participating_site \
	-t dw_study_participating_site_investigators \
	-t dw_study_secondary_purpose dw_ctrpn | psql -U postgres  -h localhost ctrpdw2

# echo 'removing interum data set'
# rm DW2-interm.sql

echo 'create clean DW2 data dump'

# limit data set to only active trils with a processing_status of Abstration Verified*
psql -U postgres  -h localhost ctrpdw2 <<EOF
-- DELETE FROM public.dw_study WHERE current_trial_status != 'Active';
-- DELETE FROM public.dw_study WHERE processing_status NOT LIKE 'Abstraction Verified%';
DELETE FROM public.dw_study WHERE processing_status = 'Rejected';
DELETE FROM public.dw_study WHERE nct_id IS NULL;
DELETE FROM public.dw_study WHERE nct_id NOT LIKE 'NCT%';
DELETE FROM public.dw_study
WHERE nci_id NOT IN
(
with reporting_start_date as (select to_date('06/15/2015', 'MM/dd/yyyy')),

                reporting_end_date as (select current_date)

                select distinct(nci_id) from

                (select *, CASE WHEN internal_system_id in

                (

                with results as (

                select row_number() over(partition by nci_id order by status_date desc) as row_num,*

                from dw_study_overall_status

                ) select internal_system_id from results where row_num =1

                )

                THEN now()

                ELSE lead(status_date) OVER(ORDER BY nci_id,status_date )   END  as "lead_date"

                from dw_study_overall_status    ) as dw

                where ((status_date >= (select * from reporting_start_date)

                and  status_date<= (select * from reporting_end_date))

                or ( (select * from reporting_start_date) >= status_date

                and (select * from reporting_start_date)<=lead_date

                )) and status in ('APPROVED','ACTIVE','ENROLLING_BY_INVITATION','TEMPORARILY_CLOSED_TO_ACCRUAL','TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION')

                and nci_id in (select nci_id from dw_study where processing_status <> 'Rejected')

                order by nci_id
);
EOF




echo 'modifying the study table'

# psql -U postgres ctrpdw2 < dwstudy.sql
psql -U postgres  -h localhost ctrpdw2 <<EOF
ALTER TABLE public.dw_organization DROP COLUMN  curator_comment;
ALTER TABLE public.dw_organization DROP COLUMN change_request_count;
ALTER TABLE public.dw_study_participating_site DROP COLUMN  target_accrual;
ALTER TABLE public.dw_study
DROP COLUMN 	amendment_number_text,
DROP COLUMN 	amendment_reason_code,
DROP COLUMN 	checked_out_by_for_administrative,
DROP COLUMN 	checked_out_by_for_scientific,
DROP COLUMN 	created_by,
DROP COLUMN 	ct_gov_xml_required_indicator,
DROP COLUMN 	current_milestone,
DROP COLUMN 	data_monitoring_committee_appointed_indicator,
DROP COLUMN 	date_last_created,
DROP COLUMN 	date_last_updated,
DROP COLUMN 	delayed_posting_indicator,
DROP COLUMN 	detail_description_primary,
DROP COLUMN 	detail_description_secondary,
DROP COLUMN 	detail_description_tertiary,
DROP COLUMN 	expanded_access_indicator,
DROP COLUMN 	fdaregulated_indicator,
DROP COLUMN 	irb_approval_number,
DROP COLUMN 	irb_approval_status,
DROP COLUMN   irb_organization_affiliation,
DROP COLUMN 	irb_city,
DROP COLUMN 	irb_country,
DROP COLUMN 	irb_email,
DROP COLUMN 	irb_name,
DROP COLUMN 	irb_phone,
DROP COLUMN 	irb_state_or_province,
DROP COLUMN 	irb_street_address,
DROP COLUMN 	irb_zip_code,
DROP COLUMN 	last_updated_by,
DROP COLUMN 	cdr_id,
DROP COLUMN 	oversight_authority_country,
DROP COLUMN 	oversight_authority_organization_name,
DROP COLUMN 	rejection_reason,
DROP COLUMN 	reporting_method_data_code,
DROP COLUMN 	resp_party_type,
DROP COLUMN 	responsible_party_generic_contact,
DROP COLUMN 	responsible_party_personal_contact,
DROP COLUMN 	review_board_approval_required_indicator,
DROP COLUMN 	section_801_indicator,
DROP COLUMN 	sponsor,
DROP COLUMN 	responsible_party_name,
DROP COLUMN 	sponsor_org_family,
DROP COLUMN 	sponsor_resp_party_email,
DROP COLUMN 	sponsor_resp_party_phone,
DROP COLUMN 	submission_number,
DROP COLUMN 	submitter_name,
DROP COLUMN 	submitter_organization,
DROP COLUMN 	submitter_organization_family,
DROP COLUMN 	why_study_stopped,
DROP COLUMN 	category,
DROP COLUMN 	comments,
DROP COLUMN 	processing_priority,
DROP COLUMN 	ctro_override,
DROP COLUMN 	consortia_trial_category,
DROP COLUMN 	nci_grant,
DROP COLUMN 	study_source,
DROP COLUMN     processing_status,
DROP COLUMN     processing_status_date;

ALTER TABLE public.dw_study
RENAME COLUMN summary_4_funding_category TO study_source;

ALTER TABLE public.dw_study_eligibility_criteria
DROP COLUMN  cde_public_identifier,
DROP COLUMN  display_order,
DROP COLUMN  cde_version,
DROP COLUMN  eligible_gender_code,
DROP COLUMN  structured_indicator,
DROP COLUMN  criterion_name,
DROP COLUMN	 operator,
DROP COLUMN  unit,
DROP COLUMN  value;

ALTER TABLE public.dw_study_arm_and_intervention
DROP COLUMN date_created_arm,
DROP COLUMN date_updated_arm,
DROP COLUMN date_created_intervention,
DROP COLUMN date_updated_intervention,
DROP COLUMN user_name_created_arm,
DROP COLUMN user_name_updated_arm,
DROP COLUMN user_name_created_intervention,
DROP COLUMN user_name_updated_intervention,
DROP COLUMN first_name_created_arm,
DROP COLUMN last_name_created_arm,
DROP COLUMN first_name_updated_arm,
DROP COLUMN last_name_updated_arm,
DROP COLUMN first_name_created_intervention,
DROP COLUMN last_name_created_intervention,
DROP COLUMN first_name_updated_intervention,
DROP COLUMN last_name_updated_intervention;

ALTER TABLE public.dw_study_collaborator DROP COLUMN status;

ALTER TABLE public.dw_study_disease 
DROP COLUMN date_last_created,
DROP COLUMN date_last_updated,
DROP COLUMN user_last_created,
DROP COLUMN user_last_updated;

ALTER TABLE public.dw_study_grant
DROP COLUMN deleted_by,
DROP COLUMN deletion_date,
DROP COLUMN reason_for_delete,
DROP COLUMN user_last_updated_id;

ALTER TABLE public.dw_study_outcome_measure
DROP COLUMN date_created,
DROP COLUMN date_updated,
DROP COLUMN username_created,
DROP COLUMN username_updated,
DROP COLUMN first_name_created,
DROP COLUMN last_name_created,
DROP COLUMN first_name_last_updated,
DROP COLUMN last_name_last_updated;
	
ALTER TABLE public.dw_study_overall_status
DROP COLUMN user_created,
DROP COLUMN user_last_updated;

EOF

psql -U postgres  -h localhost ctrpdw2 <<EOF
DROP INDEX  IF EXISTS dw_study_accepts_healthy_volunteers_indicator_idx;
DROP INDEX  IF EXISTS dw_study_acronym_idx;
DROP INDEX  IF EXISTS dw_study_amendment_date_idx;
DROP INDEX  IF EXISTS dw_study_central_contact_email_idx;
DROP INDEX  IF EXISTS dw_study_central_contact_name_idx;
DROP INDEX  IF EXISTS dw_study_central_contact_phone_idx;
DROP INDEX  IF EXISTS dw_study_central_contact_type_idx;
DROP INDEX  IF EXISTS dw_study_classification_code_idx;
DROP INDEX  IF EXISTS dw_study_completion_date_idx;
DROP INDEX  IF EXISTS dw_study_completion_date_type_code_idx;
DROP INDEX  IF EXISTS dw_study_ctep_id_idx;
DROP INDEX  IF EXISTS dw_study_current_trial_status_date_idx;
DROP INDEX  IF EXISTS dw_study_current_trial_status_idx;
DROP INDEX  IF EXISTS dw_study_dcp_id_idx;
DROP INDEX  IF EXISTS dw_study_eligible_gender_idx;
DROP INDEX  IF EXISTS dw_study_eligible_max_age_idx;
DROP INDEX  IF EXISTS dw_study_eligible_min_age_idx;
DROP INDEX  IF EXISTS dw_study_interventional_model_idx;
DROP INDEX  IF EXISTS dw_study_keyword_text_idx;
DROP INDEX  IF EXISTS dw_study_lead_org_id_idx;
DROP INDEX  IF EXISTS dw_study_lead_org_idx;
DROP INDEX  IF EXISTS dw_study_lead_org_org_family_idx;
DROP INDEX  IF EXISTS dw_study_masking_allocation_code_idx;
DROP INDEX  IF EXISTS dw_study_masking_idx;
DROP INDEX  IF EXISTS dw_study_masking_role_caregiver_idx;
DROP INDEX  IF EXISTS dw_study_masking_role_investigator_idx;
DROP INDEX  IF EXISTS dw_study_masking_role_outcome_assessor_idx;
DROP INDEX  IF EXISTS dw_study_masking_role_subject_idx;
DROP INDEX  IF EXISTS dw_study_minimum_target_accrual_number_idx;
DROP INDEX  IF EXISTS dw_study_number_of_arms_idx;
DROP INDEX  IF EXISTS dw_study_phase_additional_qualifier_code_idx;
DROP INDEX  IF EXISTS dw_study_phase_idx;
DROP INDEX  IF EXISTS dw_study_phase_other_text_idx;
DROP INDEX  IF EXISTS dw_study_primary_completion_date_idx;
DROP INDEX  IF EXISTS dw_study_primary_completion_date_type_code_idx;
DROP INDEX  IF EXISTS dw_study_primary_purpose_additional_qualifier_code_idx;
DROP INDEX  IF EXISTS dw_study_primary_purpose_code_idx;
DROP INDEX  IF EXISTS dw_study_primary_purpose_other_text_idx;
DROP INDEX  IF EXISTS dw_study_principal_investigator_idx;
DROP INDEX  IF EXISTS dw_study_record_verification_date_idx;
DROP INDEX  IF EXISTS dw_study_start_date_idx;
DROP INDEX  IF EXISTS dw_study_start_date_type_code_idx;
EOF


pg_dump -h localhost \
	-U postgres \
	-c \
	-C \
	-f DW2.sql \
	-F p \
	-O \
	-t dw_study \
	-t dw_study_overall_status \
	-t dw_study_anatomic_site \
	-t dw_organization \
	-t dw_study_arm_and_intervention \
	-t dw_study_association \
	-t dw_study_collaborator \
	-t dw_study_disease \
	-t dw_study_eligibility_criteria \
	-t dw_study_grant \
	-t dw_study_other_identifier \
	-t dw_study_outcome_measure \
	-t dw_study_participating_site \
	-t dw_study_participating_site_investigators \
	-t dw_study_secondary_purpose ctrpdw2 \


echo 'compressing file'
zip DW2.sql.zip DW2.sql
rm DW2.sql

echo 'all done'	  
