DROP TABLE IF EXISTS DW_STUDY CASCADE;
CREATE TABLE DW_STUDY (
    ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR character varying(3),
    ACRONYM  character varying(200),
    MASKING_ALLOCATION_CODE character varying(200),
    AMENDMENT_DATE date,
    AMENDMENT_NUMBER_TEXT character varying(200),
    AMENDMENT_REASON_CODE character varying(200),
    CENTRAL_CONTACT_EMAIL character varying(200),
    CENTRAL_CONTACT_NAME character varying(200),
    CENTRAL_CONTACT_PHONE character varying(200),
    CENTRAL_CONTACT_TYPE character varying(50),
    CHECKED_OUT_BY_FOR_ADMINISTRATIVE character varying(200),
    CHECKED_OUT_BY_FOR_SCIENTIFIC character varying(200),
    CLASSIFICATION_CODE character varying(200),
    COMPLETION_DATE date,
    COMPLETION_DATE_TYPE_CODE character varying(50),
    CREATED_BY character varying(500),
    CT_GOV_XML_REQUIRED_INDICATOR character varying(3),
    CTEP_ID character varying(200),
    CURRENT_MILESTONE character varying(200),
    CURRENT_TRIAL_STATUS character varying(200),
    CURRENT_TRIAL_STATUS_DATE date,
    DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR character varying(3),
    DATE_LAST_CREATED date,
    DATE_LAST_UPDATED date,
    DCP_ID character varying(200),
    DELAYED_POSTING_INDICATOR character varying(3),
    DETAIL_DESCRIPTION character varying(32000),
    DETAIL_DESCRIPTION_PRIMARY character varying(2000),
    DETAIL_DESCRIPTION_SECONDARY character varying(2000),
    DETAIL_DESCRIPTION_TERTIARY character varying(2000),
    ELIGIBLE_GENDER character varying(200),
    ELIGIBLE_MAX_AGE character varying(200),
    ELIGIBLE_MIN_AGE character varying(200),
    EXPANDED_ACCESS_INDICATOR character varying(3),
    FDAREGULATED_INDICATOR character varying(3),
    INTERNAL_SYSTEM_ID INTEGER,
    INTERVENTIONAL_MODEL character varying(255),
    IRB_APPROVAL_NUMBER character varying(200),
    IRB_APPROVAL_STATUS character varying(200),
    IRB_CITY character varying(200),
    IRB_COUNTRY character varying(200),
    IRB_EMAIL character varying(200),
    IRB_NAME character varying(200),
    IRB_ORGANIZATION_AFFILIATION character varying(200),
    IRB_PHONE character varying(200),
    IRB_STATE_OR_PROVINCE character varying(200),
    IRB_STREET_ADDRESS character varying(200),
    IRB_ZIP_CODE character varying(200),
    KEYWORD_TEXT character varying(600),
    LAST_UPDATED_BY character varying(500),
    LEAD_ORG character varying(200),
    LEAD_ORG_ORG_FAMILY character varying(200),
    LEAD_ORG_ID character varying(200),
    MASKING character varying(200),
    MASKING_ROLE_INVESTIGATOR character varying(200),
    MASKING_ROLE_OUTCOME_ASSESSOR character varying(200),
    MASKING_ROLE_SUBJECT character varying(200),
    MASKING_ROLE_CAREGIVER character varying(200),
    MINIMUM_TARGET_ACCRUAL_NUMBER INTEGER,
    NCI_ID character varying(255),
    NCT_ID character varying(200),
    NUMBER_OF_ARMS INTEGER,
    OFFICIAL_TITLE character varying(4000),
    OVERSIGHT_AUTHORITY_COUNTRY character varying(200),
    OVERSIGHT_AUTHORITY_ORGANIZATION_NAME character varying(200),
    PHASE character varying(50),
    PHASE_ADDITIONAL_QUALIFIER_CODE character varying(50),
    PHASE_OTHER_TEXT character varying(200),
    PRIMARY_COMPLETION_DATE date,
    PRIMARY_COMPLETION_DATE_TYPE_CODE character varying(50),
    PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE character varying(50),
    PRIMARY_PURPOSE_CODE character varying(50),
    PRIMARY_PURPOSE_OTHER_TEXT character varying(1000),
    PRINCIPAL_INVESTIGATOR character varying(1000),
    PROCESSING_STATUS character varying(200),
    BRIEF_SUMMARY character varying(5000),
    BRIEF_TITLE character varying(300),
    RECORD_VERIFICATION_DATE date,
    REJECTION_REASON character varying(200),
    REPORTING_METHOD_DATA_CODE character varying(50),
    RESP_PARTY_TYPE character varying (50),
    RESPONSIBLE_PARTY_GENERIC_CONTACT character varying(200),
    RESPONSIBLE_PARTY_PERSONAL_CONTACT character varying(200),
    REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR character varying(3),
    SECTION_801_INDICATOR character varying(3),
    SPONSOR character varying(200),
    RESPONSIBLE_PARTY_NAME character varying(1000),
    SPONSOR_ORG_FAMILY character varying(200),
    SPONSOR_RESP_PARTY_EMAIL character varying(200),
    SPONSOR_RESP_PARTY_PHONE character varying(200),
    START_DATE date,
    START_DATE_TYPE_CODE character varying(50),
    SUBMISSION_NUMBER INTEGER,
    SUBMITTER_NAME character varying(501),
    SUBMITTER_ORGANIZATION character varying(200),
    SUBMITTER_ORGANIZATION_FAMILY character varying(200),
    SUMMARY_4_FUNDING_CATEGORY character varying(200),
    SUMMARY_4_FUNDING_SPONSOR character varying(200),
    SUMMARY_4_FUNDING_SPONSOR_FAMILY character varying(200),
    WHY_STUDY_STOPPED character varying(2000),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
); 

CREATE INDEX DW_STUDY_ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR_IDX ON DW_STUDY(ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR);
CREATE INDEX DW_STUDY_ACRONYM_IDX on dw_study(acronym);
CREATE INDEX DW_STUDY_MASKING_ALLOCATION_CODE_IDX on dw_study(MASKING_ALLOCATION_CODE);
CREATE INDEX DW_STUDY_AMENDMENT_DATE_IDX on dw_study(AMENDMENT_DATE);
CREATE INDEX DW_STUDY_AMENDMENT_NUMBER_TEXT_IDX on dw_study(AMENDMENT_NUMBER_TEXT);
CREATE INDEX DW_STUDY_AMENDMENT_REASON_CODE_IDX on dw_study(AMENDMENT_REASON_CODE);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_EMAIL_IDX on dw_study(CENTRAL_CONTACT_EMAIL);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_NAME_IDX on dw_study(CENTRAL_CONTACT_NAME);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_PHONE_IDX on dw_study(CENTRAL_CONTACT_PHONE);
CREATE INDEX DW_STUDY_CENTRAL_CONTACT_TYPE_IDX on dw_study(CENTRAL_CONTACT_TYPE);
CREATE INDEX DW_STUDY_CHECKED_OUT_BY_FOR_ADMINISTRATIVE_IDX on dw_study(CHECKED_OUT_BY_FOR_ADMINISTRATIVE);
CREATE INDEX DW_STUDY_CHECKED_OUT_BY_FOR_SCIENTIFIC_IDX on dw_study(CHECKED_OUT_BY_FOR_SCIENTIFIC);
CREATE INDEX DW_STUDY_CLASSIFICATION_CODE_IDX on dw_study(CLASSIFICATION_CODE);
CREATE INDEX DW_STUDY_COMPLETION_DATE_IDX on dw_study(COMPLETION_DATE);
CREATE INDEX DW_STUDY_COMPLETION_DATE_TYPE_CODE_IDX on dw_study(COMPLETION_DATE_TYPE_CODE);
CREATE INDEX DW_STUDY_CREATED_BY_IDX on dw_study(CREATED_BY);
CREATE INDEX DW_STUDY_CT_GOV_XML_REQUIRED_INDICATOR_IDX on dw_study(CT_GOV_XML_REQUIRED_INDICATOR);
CREATE INDEX DW_STUDY_CTEP_ID_IDX on dw_study(CTEP_ID);
CREATE INDEX DW_STUDY_CURRENT_MILESTONE_IDX on dw_study(CURRENT_MILESTONE);
CREATE INDEX DW_STUDY_CURRENT_TRIAL_STATUS_IDX on dw_study(CURRENT_TRIAL_STATUS);
CREATE INDEX DW_STUDY_CURRENT_TRIAL_STATUS_DATE_IDX on dw_study(CURRENT_TRIAL_STATUS_DATE);
CREATE INDEX DW_STUDY_DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR_IDX on dw_study(DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR);
CREATE INDEX DW_STUDY_DATE_LAST_CREATED_IDX on dw_study(DATE_LAST_CREATED);
CREATE INDEX DW_STUDY_DATE_LAST_UPDATED_IDX on dw_study(DATE_LAST_UPDATED);
CREATE INDEX DW_STUDY_DCP_ID_IDX on dw_study(DCP_ID);
CREATE INDEX DW_STUDY_DELAYED_POSTING_INDICATOR_IDX on dw_study(DELAYED_POSTING_INDICATOR);
CREATE INDEX DW_STUDY_ELIGIBLE_GENDER_IDX on dw_study(ELIGIBLE_GENDER);
CREATE INDEX DW_STUDY_ELIGIBLE_MAX_AGE_IDX on dw_study(ELIGIBLE_MAX_AGE);
CREATE INDEX DW_STUDY_ELIGIBLE_MIN_AGE_IDX on dw_study(ELIGIBLE_MIN_AGE);
CREATE INDEX DW_STUDY_EXPANDED_ACCESS_INDICATOR_IDX on dw_study(EXPANDED_ACCESS_INDICATOR);
CREATE INDEX DW_STUDY_FDAREGULATED_INDICATOR_IDX on dw_study(FDAREGULATED_INDICATOR);
CREATE INDEX DW_STUDY_INTERVENTIONAL_MODEL_IDX on dw_study(INTERVENTIONAL_MODEL);
CREATE INDEX DW_STUDY_IRB_APPROVAL_NUMBER_IDX on dw_study(IRB_APPROVAL_NUMBER);
CREATE INDEX DW_STUDY_IRB_APPROVAL_STATUS_IDX on dw_study(IRB_APPROVAL_STATUS);
CREATE INDEX DW_STUDY_IRB_CITY_IDX on dw_study(IRB_CITY);
CREATE INDEX DW_STUDY_IRB_COUNTRY_IDX on dw_study(IRB_COUNTRY);
CREATE INDEX DW_STUDY_IRB_EMAIL_IDX on dw_study(IRB_EMAIL);
CREATE INDEX DW_STUDY_IRB_NAME_IDX on dw_study(IRB_NAME);
CREATE INDEX DW_STUDY_IRB_ORGANIZATION_AFFILIATION_IDX on dw_study(IRB_ORGANIZATION_AFFILIATION);
CREATE INDEX DW_STUDY_IRB_PHONE_IDX on dw_study(IRB_PHONE);
CREATE INDEX DW_STUDY_IRB_STATE_OR_PROVINCE_IDX on dw_study(IRB_STATE_OR_PROVINCE);
CREATE INDEX DW_STUDY_IRB_STREET_ADDRESS_IDX on dw_study(IRB_STREET_ADDRESS);
CREATE INDEX DW_STUDY_IRB_ZIP_CODE_IDX on dw_study(IRB_ZIP_CODE);
CREATE INDEX DW_STUDY_KEYWORD_TEXT_IDX on dw_study(KEYWORD_TEXT);
CREATE INDEX DW_STUDY_LAST_UPDATED_BY_IDX on dw_study(LAST_UPDATED_BY);
CREATE INDEX DW_STUDY_LEAD_ORG_IDX on dw_study(LEAD_ORG);
CREATE INDEX DW_STUDY_LEAD_ORG_ORG_FAMILY_IDX on dw_study(LEAD_ORG_ORG_FAMILY);
CREATE INDEX DW_STUDY_LEAD_ORG_ID_IDX on dw_study(LEAD_ORG_ID);
CREATE INDEX DW_STUDY_MASKING_IDX on dw_study(MASKING);
CREATE INDEX DW_STUDY_MASKING_ROLE_INVESTIGATOR_IDX on dw_study(MASKING_ROLE_INVESTIGATOR);
CREATE INDEX DW_STUDY_MASKING_ROLE_OUTCOME_ASSESSOR_IDX on dw_study(MASKING_ROLE_OUTCOME_ASSESSOR);
CREATE INDEX DW_STUDY_MASKING_ROLE_SUBJECT_IDX on dw_study(MASKING_ROLE_SUBJECT);
CREATE INDEX DW_STUDY_MASKING_ROLE_CAREGIVER_IDX on dw_study(MASKING_ROLE_CAREGIVER);
CREATE INDEX DW_STUDY_MINIMUM_TARGET_ACCRUAL_NUMBER_IDX on dw_study(MINIMUM_TARGET_ACCRUAL_NUMBER);
CREATE INDEX DW_STUDY_NCI_ID_IDX on dw_study(NCI_ID);
CREATE INDEX DW_STUDY_NCT_ID_IDX on dw_Study(NCT_ID);
CREATE INDEX DW_STUDY_NUMBER_OF_ARMS_IDX on dw_study(NUMBER_OF_ARMS);
CREATE INDEX DW_STUDY_OVERSIGHT_AUTHORITY_COUNTRY_IDX on dw_study(OVERSIGHT_AUTHORITY_COUNTRY);
CREATE INDEX DW_STUDY_OVERSIGHT_AUTHORITY_ORGANIZATION_NAME_IDX on dw_study(OVERSIGHT_AUTHORITY_ORGANIZATION_NAME);
CREATE INDEX DW_STUDY_PHASE_IDX on dw_study(PHASE);
CREATE INDEX DW_STUDY_PHASE_ADDITIONAL_QUALIFIER_CODE_IDX on dw_study(PHASE_ADDITIONAL_QUALIFIER_CODE);
CREATE INDEX DW_STUDY_PHASE_OTHER_TEXT_IDX on dw_study(PHASE_OTHER_TEXT);
CREATE INDEX DW_STUDY_PRIMARY_COMPLETION_DATE_IDX on dw_study(PRIMARY_COMPLETION_DATE);
CREATE INDEX DW_STUDY_PRIMARY_COMPLETION_DATE_TYPE_CODE_IDX on dw_study(PRIMARY_COMPLETION_DATE_TYPE_CODE);
CREATE INDEX DW_STUDY_PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE_IDX on dw_study(PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE);
CREATE INDEX DW_STUDY_PRIMARY_PURPOSE_CODE_IDX on dw_study(PRIMARY_PURPOSE_CODE);
CREATE INDEX DW_STUDY_PRIMARY_PURPOSE_OTHER_TEXT_IDX on dw_study(PRIMARY_PURPOSE_OTHER_TEXT);
CREATE INDEX DW_STUDY_PRINCIPAL_INVESTIGATOR_IDX on dw_study(PRINCIPAL_INVESTIGATOR);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_IDX on dw_study(PROCESSING_STATUS);
CREATE INDEX DW_STUDY_BRIEF_SUMMARY_IDX on dw_study(BRIEF_SUMMARY);
CREATE INDEX DW_STUDY_BRIEF_TITLE_IDX on dw_study(BRIEF_TITLE);
CREATE INDEX DW_STUDY_RECORD_VERIFICATION_DATE_IDX on dw_study(RECORD_VERIFICATION_DATE);
CREATE INDEX DW_STUDY_REJECTION_REASON_IDX on dw_study(REJECTION_REASON);
CREATE INDEX DW_STUDY_REPORTING_METHOD_DATA_CODE_IDX on dw_study(REPORTING_METHOD_DATA_CODE);
CREATE INDEX DW_STUDY_RESP_PARTY_TYPE_IDX on dw_study(RESP_PARTY_TYPE);
CREATE INDEX DW_STUDY_RESPONSIBLE_PARTY_GENERIC_CONTACT_IDX on dw_study(RESPONSIBLE_PARTY_GENERIC_CONTACT);
CREATE INDEX DW_STUDY_RESPONSIBLE_PARTY_PERSONAL_CONTACT_IDX on dw_study(RESPONSIBLE_PARTY_PERSONAL_CONTACT);
CREATE INDEX DW_STUDY_REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR_IDX on dw_study(REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR);
CREATE INDEX DW_STUDY_SECTION_801_INDICATOR_IDX on dw_study(SECTION_801_INDICATOR);
CREATE INDEX DW_STUDY_SPONSOR_IDX on dw_study(SPONSOR);
CREATE INDEX DW_STUDY_RESPONSIBLE_PARTY_NAME_IDX on dw_study(RESPONSIBLE_PARTY_NAME);
CREATE INDEX DW_STUDY_SPONSOR_ORG_FAMILY_IDX on dw_study(SPONSOR_ORG_FAMILY);
CREATE INDEX DW_STUDY_SPONSOR_RESP_PARTY_EMAIL_IDX on dw_study(SPONSOR_RESP_PARTY_EMAIL);
CREATE INDEX DW_STUDY_SPONSOR_RESP_PARTY_PHONE_IDX on dw_study(SPONSOR_RESP_PARTY_PHONE);
CREATE INDEX DW_STUDY_START_DATE_IDX on dw_study(START_DATE);
CREATE INDEX DW_STUDY_START_DATE_TYPE_CODE_IDX on dw_study(START_DATE_TYPE_CODE);
CREATE INDEX DW_STUDY_SUBMISSION_NUMBER_IDX on dw_study(SUBMISSION_NUMBER);
CREATE INDEX DW_STUDY_SUBMITTER_NAME_IDX on dw_study(SUBMITTER_NAME);
CREATE INDEX DW_STUDY_SUBMITTER_ORGANIZATION_IDX on dw_study(SUBMITTER_ORGANIZATION);
CREATE INDEX DW_STUDY_SUBMITTER_ORGANIZATION_FAMILY_IDX on dw_study(SUBMITTER_ORGANIZATION_FAMILY);
CREATE INDEX DW_STUDY_SUMMARY_4_FUNDING_CATEGORY_IDX on dw_study(SUMMARY_4_FUNDING_CATEGORY);
CREATE INDEX DW_STUDY_SUMMARY_4_FUNDING_SPONSOR_IDX on dw_study(SUMMARY_4_FUNDING_SPONSOR);
CREATE INDEX DW_STUDY_SUMMARY_4_FUNDING_SPONSOR_FAMILY_IDX on dw_study(SUMMARY_4_FUNDING_SPONSOR_FAMILY);
CREATE INDEX DW_STUDY_WHY_STUDY_STOPPED_IDX on dw_study(WHY_STUDY_STOPPED);

--The below fields are too large for indexing. Need to determine if we need them here or not.
--CREATE INDEX DW_STUDY_OFFICIAL_TITLE_IDX on dw_study(OFFICIAL_TITLE);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_IDX on dw_study(DETAIL_DESCRIPTION);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_PRIMARY_IDX on dw_study(DETAIL_DESCRIPTION_PRIMARY);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_SECONDARY_IDX on dw_study(DETAIL_DESCRIPTION_SECONDARY);
--CREATE INDEX DW_STUDY_DETAIL_DESCRIPTION_TERTIARY_IDX on dw_study(DETAIL_DESCRIPTION_TERTIARY);


INSERT INTO DW_STUDY (
    ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR, ACRONYM, MASKING_ALLOCATION_CODE, AMENDMENT_DATE,
    AMENDMENT_NUMBER_TEXT, AMENDMENT_REASON_CODE, CENTRAL_CONTACT_EMAIL, CENTRAL_CONTACT_PHONE,
    CHECKED_OUT_BY_FOR_ADMINISTRATIVE, CHECKED_OUT_BY_FOR_SCIENTIFIC,
    CLASSIFICATION_CODE, COMPLETION_DATE, COMPLETION_DATE_TYPE_CODE, CREATED_BY,
    CT_GOV_XML_REQUIRED_INDICATOR, CURRENT_MILESTONE, CURRENT_TRIAL_STATUS, CURRENT_TRIAL_STATUS_DATE,
    DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR, DATE_LAST_CREATED, DATE_LAST_UPDATED,
    DELAYED_POSTING_INDICATOR, DETAIL_DESCRIPTION, DETAIL_DESCRIPTION_PRIMARY, DETAIL_DESCRIPTION_SECONDARY,
    DETAIL_DESCRIPTION_TERTIARY, EXPANDED_ACCESS_INDICATOR, FDAREGULATED_INDICATOR, INTERNAL_SYSTEM_ID,
    INTERVENTIONAL_MODEL, IRB_APPROVAL_NUMBER, IRB_APPROVAL_STATUS, IRB_CITY,
    IRB_COUNTRY, IRB_NAME, IRB_ORGANIZATION_AFFILIATION, IRB_STATE_OR_PROVINCE,
    IRB_ZIP_CODE, KEYWORD_TEXT, LAST_UPDATED_BY, LEAD_ORG,
    LEAD_ORG_ID, MASKING, MASKING_ROLE_INVESTIGATOR, MASKING_ROLE_OUTCOME_ASSESSOR,
    MASKING_ROLE_SUBJECT, MASKING_ROLE_CAREGIVER, MINIMUM_TARGET_ACCRUAL_NUMBER,
    NUMBER_OF_ARMS, OFFICIAL_TITLE, OVERSIGHT_AUTHORITY_COUNTRY, OVERSIGHT_AUTHORITY_ORGANIZATION_NAME,
    PHASE, PHASE_ADDITIONAL_QUALIFIER_CODE, PHASE_OTHER_TEXT, PRIMARY_COMPLETION_DATE,
    PRIMARY_COMPLETION_DATE_TYPE_CODE, PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE, PRIMARY_PURPOSE_CODE, PRIMARY_PURPOSE_OTHER_TEXT,
    PRINCIPAL_INVESTIGATOR, PROCESSING_STATUS,
    BRIEF_SUMMARY, BRIEF_TITLE, RECORD_VERIFICATION_DATE,
    REJECTION_REASON, REPORTING_METHOD_DATA_CODE, REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR,
    SECTION_801_INDICATOR, SPONSOR, START_DATE, START_DATE_TYPE_CODE, 
    SUBMISSION_NUMBER, SUBMITTER_NAME, SUBMITTER_ORGANIZATION, SUMMARY_4_FUNDING_CATEGORY,
    SUMMARY_4_FUNDING_SPONSOR, WHY_STUDY_STOPPED
) SELECT 
    CASE WHEN sp.accept_healthy_volunteers_indicator THEN 'YES'
         ELSE 'NO'
    END, 
    sp.acronym, sp.allocation_code, sp.amendment_date, 
    sp.amendment_number, sp.amendment_reason_code, 
    central_contact.email, central_contact.telephone,
    admin.user_identifier, scientific.user_identifier, 
    sp.study_classification_code, sp.completion_date, sp.completion_date_type_code, 
    CASE WHEN ru_creator.first_name is null THEN split_part(creator.login_name, 'CN=', 2)
        ELSE ru_creator.first_name || ' ' || ru_creator.last_name
    END,    
    CASE WHEN sp.ctgov_xml_required_indicator THEN 'YES'
         ELSE 'NO'
    END, 
    milestone.milestone_code, current_status.status_code, current_status.status_date,
    CASE WHEN sp.data_monty_comty_apptn_indicator THEN 'YES'
         ELSE 'NO'
    END, 
    sp.date_last_created, sp.date_last_updated, 
    CASE WHEN sp.delayed_posting_indicator THEN 'YES'
         ELSE 'NO'
    END, 
    sp.scientific_description, obj_primary.description, obj_secondary.description, obj_ternary.description,
    CASE WHEN sp.expd_access_indidicator THEN 'YES'
         ELSE 'NO'
    END,
    CASE WHEN sp.fda_regulated_indicator THEN 'YES'
         ELSE 'NO'
    END,
    sp.identifier, sp.design_configuration_code, irb.review_board_approval_number, 
    irb.review_board_approval_status_code, irb_org.city, irb_org.country_name, irb_org.name,
    irb.review_board_organizational_affiliation, irb_org.state, irb_org.postal_code, sp.keyword_text, 
    CASE WHEN ru_updater.first_name is null THEN split_part(updater.login_name, 'CN=', 2)
        ELSE ru_updater.first_name || ' ' || ru_updater.last_name
    END,
    lead_org.name, lead_org_id.local_sp_indentifier, 
    sp.blinding_schema_code, sp.blinding_role_code_investigator, sp.blinding_role_code_outcome, sp.blinding_role_code_subject, 
    sp.blinding_role_code_caregiver, sp.min_target_accrual_num, sp.number_of_intervention_groups,
    sp.official_title, oversight_country.name, oversight.authority_name, sp.phase_code, 
    sp.phase_additional_qualifier_code, sp.phase_other_text, sp.pri_compl_date, sp.pri_compl_date_type_code, 
    sp.primary_purpose_additional_qualifier_code, sp.primary_purpose_code, sp.primary_purpose_other_text, pi.first_name || ' ' || pi.last_name, 
    processing_status.status_code,
    sp.public_description, sp.public_tittle, sp.record_verification_date, rejection.comment_text, 
    sp.accr_rept_meth_code, 
    CASE WHEN sp.review_brd_approval_req_indicator THEN 'YES'
         ELSE 'NO'
    END, 
    CASE WHEN sp.section801_indicator THEN 'YES'
         ELSE 'NO'
    END,
    sponsor.name, sp.start_date, sp.start_date_type_code, sp.submission_number, 
    submitter.first_name || ' ' || submitter.last_name, submitter.affiliate_org, summary4.type_code, summary4_sponsor.name,  
    stopped.comment_text
 
    from STUDY_PROTOCOL sp
    left outer join study_checkout as admin on admin.study_protocol_identifier = sp.identifier and admin.checkout_type = 'ADMINISTRATIVE'
    left outer join study_checkout as scientific on scientific.study_protocol_identifier = sp.identifier and scientific.checkout_type = 'SCIENTIFIC'
    left outer join csm_user as creator on creator.user_id = sp.user_last_created_id
    left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
    left outer join study_milestone as milestone on milestone.study_protocol_identifier = sp.identifier
    and (milestone.identifier is null or milestone.identifier = (select max(identifier) from study_milestone where study_protocol_identifier = sp.identifier))
    left outer join study_overall_status as current_status on current_status.study_protocol_identifier = sp.identifier
        and (current_status.identifier = (select max(identifier) from study_overall_status where study_protocol_identifier = sp.identifier))
    left outer join study_objective as obj_primary on obj_primary.study_protocol_identifier = sp.identifier and obj_primary.type_code = 'PRIMARY' 
        and obj_primary.identifier = (select max(identifier) from study_objective where study_protocol_identifier = sp.identifier and type_code = 'PRIMARY')
    left outer join study_objective as obj_secondary on obj_secondary.study_protocol_identifier = sp.identifier and obj_secondary.type_code = 'SECONDARY'
        and obj_secondary.identifier = (select max(identifier) from study_objective where study_protocol_identifier = sp.identifier and type_code = 'SECONDARY')
    left outer join study_objective as obj_ternary on obj_ternary.study_protocol_identifier = sp.identifier and obj_ternary.type_code = 'TERNARY'
        and obj_ternary.identifier = (select max(identifier) from study_objective where study_protocol_identifier = sp.identifier and type_code = 'TERNARY')
    left outer join study_site as lo on lo.study_protocol_identifier = sp.identifier and lo.functional_code = 'LEAD_ORGANIZATION'
    left outer join research_organization as ro_lead_org on ro_lead_org.identifier = lo.research_organization_identifier
    left outer join organization as lead_org on lead_org.identifier = ro_lead_org.organization_identifier
    left outer join study_regulatory_authority as sra on sra.study_protocol_identifier = sp.identifier
    left outer join regulatory_authority as oversight on oversight.identifier = sra.regulatory_authority_identifier
    left outer join country as oversight_country on oversight_country.identifier = oversight.country_identifier
    left outer join study_contact as sc on  sc.study_protocol_identifier = sp.identifier and sc.role_code = 'STUDY_PRINCIPAL_INVESTIGATOR'
    left outer join clinical_research_staff as crs on crs.identifier = sc.clinical_research_staff_identifier
    left outer join person as pi on pi.identifier = crs.person_identifier
    left outer join document_workflow_status as processing_status on processing_status.study_protocol_identifier = sp.identifier 
        and processing_status.identifier = (select max(identifier) from document_workflow_status where study_protocol_identifier = sp.identifier)
    left outer join study_milestone as rejection on rejection.study_protocol_identifier = sp.identifier and rejection.milestone_code = 'SUBMISSION_REJECTED'
        and rejection.identifier = (select max(identifier) from study_milestone where study_protocol_identifier = sp.identifier)
    left outer join study_site as sponsor_ss on sponsor_ss.study_protocol_identifier = sp.identifier and sponsor_ss.functional_code = 'SPONSOR'
    left outer join research_organization as sponsor_ro on sponsor_ro.identifier = sponsor_ss.research_organization_identifier
    left outer join organization as sponsor on sponsor.identifier = sponsor_ro.organization_identifier
    left outer join registry_user as submitter on submitter.csm_user_id = sp.user_last_created_id
    left outer join study_resourcing as summary4 on summary4.study_protocol_identifier = sp.identifier and summary4.summ_4_rept_indicator is true
        and summary4.identifier = (select max(identifier) from study_resourcing where study_protocol_identifier = sp.identifier and summ_4_rept_indicator is true)
    left outer join organization as summary4_sponsor on summary4_sponsor.identifier = cast(summary4.organization_identifier as INTEGER)
    left outer join study_overall_status as stopped on stopped.study_protocol_identifier = sp.identifier 
        and stopped.status_code in ('ADMINISTRATIVELY_COMPLETE', 'WITHDRAWN', 'TEMPORARILY_CLOSED_TO_ACCRUAL', 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION')
        and stopped.identifier = (select max(identifier) from study_overall_status where study_protocol_identifier = sp.identifier)
    left outer join study_site as irb on irb.study_protocol_identifier = sp.identifier and irb.functional_code = 'STUDY_OVERSIGHT_COMMITTEE'
        and irb.identifier = (select max(identifier) from study_site where functional_code = 'STUDY_OVERSIGHT_COMMITTEE' and study_protocol_identifier = sp.identifier)
    left outer join oversight_committee as oc on oc.identifier = irb.oversight_committee_identifier
    left outer join organization as irb_org on irb_org.identifier = oc.organization_identifier
    left outer join csm_user as updater on updater.user_id = sp.user_last_updated_id
    left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
    left outer join study_contact as central_contact on central_contact.study_protocol_identifier = sp.identifier and central_contact.role_code = 'CENTRAL_CONTACT'
    left outer join study_site as lead_org_id on lead_org_id.study_protocol_identifier = sp.identifier and lead_org_id.functional_code = 'LEAD_ORGANIZATION'
where sp.status_code = 'ACTIVE';
    
UPDATE DW_STUDY SET ELIGIBLE_GENDER = ec.eligible_gender_code from planned_activity pa
    inner join planned_eligibility_criterion ec on ec.identifier = pa.identifier and ec.criterion_name = 'GENDER'
    where pa.category_code = 'ELIGIBILITY_CRITERION' and internal_system_id = pa.study_protocol_identifier;
    
UPDATE DW_STUDY SET ELIGIBLE_MIN_AGE = trunc(ec.min_value) || ' ' || ec.min_unit, ELIGIBLE_MAX_AGE = trunc(ec.max_value) || ' ' || ec.max_unit from planned_activity pa
    inner join planned_eligibility_criterion ec on ec.identifier = pa.identifier and ec.criterion_name = 'AGE'
    where pa.category_code = 'ELIGIBILITY_CRITERION' and internal_system_id = pa.study_protocol_identifier;

UPDATE DW_STUDY SET CENTRAL_CONTACT_TYPE = 'PERSONAL', CENTRAL_CONTACT_NAME = cc.first_name || ' ' || cc.last_name
    from study_contact sc 
        inner join clinical_research_staff crs on crs.identifier = sc.clinical_research_staff_identifier
        inner join person cc on cc.identifier = crs.person_identifier
    where internal_system_id = sc.study_protocol_identifier and sc.role_code = 'CENTRAL_CONTACT';

UPDATE DW_STUDY SET CENTRAL_CONTACT_TYPE = 'GENERIC' from study_contact sc 
    where internal_system_id = sc.study_protocol_identifier and sc.role_code = 'CENTRAL_CONTACT' 
        and sc.organizational_contact_identifier is not null;

UPDATE DW_STUDY SET RESPONSIBLE_PARTY_NAME = resp.name, SPONSOR_RESP_PARTY_EMAIL = sc.email, SPONSOR_RESP_PARTY_PHONE = sc.telephone, 
                    RESP_PARTY_TYPE = 'PI'
    from study_contact sc 
        inner join clinical_research_staff crs on crs.identifier = sc.clinical_research_staff_identifier
        inner join organization resp on resp.identifier = crs.organization_identifier
    where sc.role_code = 'RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR' and internal_system_id = sc.study_protocol_identifier;

UPDATE DW_STUDY SET RESPONSIBLE_PARTY_NAME = org.name, SPONSOR_RESP_PARTY_EMAIL = resp.email, SPONSOR_RESP_PARTY_PHONE = resp.telephone,
                    RESP_PARTY_TYPE = 'SPONSOR', RESPONSIBLE_PARTY_PERSONAL_CONTACT = p.first_name || ' ' || p.last_name
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier
        inner join study_site_contact as resp on resp.study_site_identifier = ss.identifier and resp.role_code = 'RESPONSIBLE_PARTY_SPONSOR_CONTACT'
        inner join organizational_contact as oc on oc.identifier = resp.organizational_contact_identifier
        join person as p on p.identifier = oc.person_identifier
    where ss.functional_code = 'RESPONSIBLE_PARTY_SPONSOR' and internal_system_id = ss.study_protocol_identifier;


UPDATE DW_STUDY SET NCI_ID = oid.extension from study_otheridentifiers as oid 
    inner join study_protocol as sp on sp.identifier = oid.study_protocol_id and sp.status_code = 'ACTIVE'
    where oid.study_protocol_id = internal_system_id and oid.root = '2.16.840.1.113883.3.26.4.3' and oid.extension is not null;
DELETE FROM DW_STUDY where NCI_ID is null;

UPDATE DW_STUDY SET CTEP_ID = ss.local_sp_indentifier 
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'Cancer Therapy Evaluation Program'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER' and ss.study_protocol_identifier = internal_system_id;
    
UPDATE DW_STUDY SET DCP_ID = ss.local_sp_indentifier 
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier 
                and (org.name = 'Division of Cancer Control and Population Sciences' or org.name = 'National Cancer Institute Division of Cancer Prevention')
    where ss.functional_code = 'IDENTIFIER_ASSIGNER' and ss.study_protocol_identifier = internal_system_id;

UPDATE DW_STUDY SET NCT_ID = ss.local_sp_indentifier 
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'ClinicalTrials.gov'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER' and ss.study_protocol_identifier = internal_system_id; 

UPDATE DW_STUDY SET LEAD_ORG_ORG_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = lead_org; 
    
UPDATE DW_STUDY SET SPONSOR_ORG_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = sponsor;
    
UPDATE DW_STUDY SET SUBMITTER_ORGANIZATION_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = submitter_organization;
    
UPDATE DW_STUDY SET SUMMARY_4_FUNDING_SPONSOR_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = summary_4_funding_sponsor;
