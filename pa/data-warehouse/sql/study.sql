DROP TABLE IF EXISTS DW_STUDY ;
CREATE TABLE DW_STUDY (
    ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR boolean,
    ACRONYM  character varying(200),
    ALLOCATION_CODE character varying(200),
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
    CT_GOV_XML_REQUIRED_INDICATOR boolean,
    CTEP_ID character varying(200),
    CURRENT_MILESTONE character varying(200),
    CURRENT_TRIAL_STATUS character varying(200),
    CURRENT_TRIAL_STATUS_DATE date,
    DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR boolean,
    DATE_LAST_CREATED date,
    DATE_LAST_UPDATED date,
    DCP_ID character varying(200),
    DELAYED_POSTING_INDICATOR boolean,
    DETAIL_DESCRIPTION character varying(32000),
    DETAIL_DESCRIPTION_PRIMARY character varying(2000),
    DETAIL_DESCRIPTION_SECONDARY character varying(2000),
    DETAIL_DESCRIPTION_TERTIARY character varying(2000),
    ELIGIBLE_GENDER character varying(200),
    ELIGIBLE_MAX_AGE character varying(200000),
    ELIGIBLE_MIN_AGE character varying(200000),
    EXPANDED_ACCESS_INDICATOR boolean,
    FDAREGULATED_INDICATOR boolean,
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
    OVERSIGHT_AUTORITY_ORGNIAZATION_NAME character varying(200),
    PARENT_NCI_ID character varying(255),
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
    PROGRAM_CODE character varying(100),
    PROPRIETARY_TRIAL_INDICATOR boolean,
    PUBLIC_BRIEF_SUMMARY character varying(5000),
    PUBLIC_BRIEF_TITLE character varying(300),
    RECORD_STATUS_CODE character varying(200),
    RECORD_VERIFICATION_DATE date,
    REJECTION_REASON character varying(200),
    REPORTING_METHOD_DATA_CODE character varying(50),
    RESP_PARTY_TYPE character varying (50),
    RESPONSIBLE_PARTY_GENERIC_CONTACT character varying(200),
    RESPONSIBLE_PARTY_PERSONAL_CONTACT character varying(200),
    REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR boolean,
    SAMPLING_METHOD_CODE character varying(200),
    SECTION_801_INDICATOR boolean,
    SPONSOR character varying(255),
    SPONSOR_OR_RESPONSIBLE_PARTY character varying(1000),
    SPONSOR_RESP_PARTY_EMAIL character varying(200),
    SPONSOR_RESP_PARTY_PHONE character varying(200),
    START_DATE date,
    START_DATE_TYPE_CODE character varying(50),
    SUBMISSION_NUMBER INTEGER,
    SUBMITTER_NAME character varying(501),
    SUBMITTER_ORGANIZATION character varying(200),
    SUMMARY_4_FUNDING_CATEGORY character varying(200),
    SUMMARY_4_FUNDING_SPONSOR character varying(200),
    TYPE_STUDY character varying(100),
    WHY_STUDY_STOPPED character varying(2000)
); 


INSERT INTO DW_STUDY (
    ACCEPTS_HEALTHY_VOLUNTEERS_INDICATOR, ACRONYM, ALLOCATION_CODE,AMENDMENT_DATE,
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
    MASKING_ROLE_SUBJECT, MASKING_ROLE_CAREGIVER, MINIMUM_TARGET_ACCRUAL_NUMBER, NCI_ID,
    NUMBER_OF_ARMS, OFFICIAL_TITLE, OVERSIGHT_AUTHORITY_COUNTRY, OVERSIGHT_AUTORITY_ORGNIAZATION_NAME,
    PHASE, PHASE_ADDITIONAL_QUALIFIER_CODE, PHASE_OTHER_TEXT, PRIMARY_COMPLETION_DATE,
    PRIMARY_COMPLETION_DATE_TYPE_CODE, PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE, PRIMARY_PURPOSE_CODE, PRIMARY_PURPOSE_OTHER_TEXT,
    PRINCIPAL_INVESTIGATOR, PROCESSING_STATUS, PROGRAM_CODE, PROPRIETARY_TRIAL_INDICATOR,
    PUBLIC_BRIEF_SUMMARY, PUBLIC_BRIEF_TITLE, RECORD_STATUS_CODE, RECORD_VERIFICATION_DATE,
    REJECTION_REASON, REPORTING_METHOD_DATA_CODE, REVIEW_BOARD_APPROVAL_REQUIRED_INDICATOR, SAMPLING_METHOD_CODE,
    SECTION_801_INDICATOR, SPONSOR, START_DATE, START_DATE_TYPE_CODE, 
    SUBMISSION_NUMBER, SUBMITTER_NAME, SUBMITTER_ORGANIZATION, SUMMARY_4_FUNDING_CATEGORY,
    SUMMARY_4_FUNDING_SPONSOR, TYPE_STUDY, WHY_STUDY_STOPPED
) SELECT sp.accept_healthy_volunteers_indicator, sp.acronym, sp.allocation_code, sp.amendment_date, 
    sp.amendment_number, sp.amendment_reason_code, 
    central_contact.email, central_contact.telephone,
    admin.user_identifier, scientific.user_identifier, 
    sp.study_classification_code, sp.completion_date, sp.completion_date_type_code, creator.login_name, 
    sp.ctgov_xml_required_indicator, milestone.milestone_code, current_status.status_code, current_status.status_date,
    sp.data_monty_comty_apptn_indicator, sp.date_last_created, sp.date_last_updated, sp.delayed_posting_indicator, 
    sp.scientific_description, obj_primary.description, obj_secondary.description, obj_ternary.description,
    sp.expd_access_indidicator, sp.fda_regulated_indicator, sp.identifier, irb.review_board_approval_number, 
    irb.review_board_approval_status_code, irb_org.city, irb_org.country_name, irb_org.name,
    irb.review_board_organizational_affiliation, irb_org.state, irb_org.postal_code, sp.keyword_text, 
    updater.login_name, sp.design_configuration_code, lead_org.name, lead_org_id.local_sp_indentifier, 
    sp.blinding_schema_code, sp.blinding_role_code_investigator, sp.blinding_role_code_outcome, sp.blinding_role_code_subject, 
    sp.blinding_role_code_caregiver, sp.min_target_accrual_num, nci_id.extension, sp.number_of_intervention_groups,
    sp.official_title, oversight.authority_name, oversight_country.name, sp.phase_code, 
    sp.phase_additional_qualifier_code, sp.phase_other_text, sp.pri_compl_date, sp.pri_compl_date_type_code, 
    sp.primary_purpose_additional_qualifier_code, sp.primary_purpose_code, sp.primary_purpose_other_text, pi.first_name || ' ' || pi.last_name, 
    processing_status.status_code, sp.program_code_text, sp.proprietary_trial_indicator, sp.public_description, 
    sp.public_tittle, sp.status_code, sp.record_verification_date, rejection.comment_text, 
    sp.accr_rept_meth_code, sp.review_brd_approval_req_indicator, sp.sampling_method_code, sp.section801_indicator,
    sponsor.name, sp.start_date, sp.start_date_type_code, sp.submission_number, 
    submitter.first_name || ' ' || submitter.last_name, submitter.affiliate_org, summary4.type_code, summary4_sponsor.name, 
    sp.study_protocol_type, stopped.comment_text
 
    from STUDY_PROTOCOL sp
    left outer join study_checkout as admin on admin.study_protocol_identifier = sp.identifier and admin.checkout_type = 'ADMINISTRATIVE'
    left outer join study_checkout as scientific on scientific.study_protocol_identifier = sp.identifier and scientific.checkout_type = 'SCIENTIFIC'
    left outer join csm_user as creator on creator.user_id = sp.user_last_created_id
    left outer join study_milestone as milestone on milestone.study_protocol_identifier = sp.identifier
    and (milestone.identifier is null or milestone.identifier = (select max(identifier) from study_milestone where study_protocol_identifier = sp.identifier))
    left outer join study_overall_status as current_status on current_status.study_protocol_identifier = sp.identifier
        and (current_status.identifier = (select max(identifier) from study_overall_status where study_protocol_identifier = sp.identifier))
    left outer join study_objective as obj_primary on obj_primary.study_protocol_identifier = sp.identifier and obj_primary.type_code = 'PRIMARY' 
        and obj_primary.identifier = (select max(identifier) from study_objective where study_protocol_identifier = sp.identifier and type_code = 'PRIMARY')
    left outer join study_objective as obj_secondary on obj_secondary.study_protocol_identifier = sp.identifier and obj_secondary.type_code = 'SECONDARY'
        and obj_primary.identifier = (select max(identifier) from study_objective where study_protocol_identifier = sp.identifier and type_code = 'SECONDARY')
    left outer join study_objective as obj_ternary on obj_ternary.study_protocol_identifier = sp.identifier and obj_ternary.type_code = 'TERNARY'
        and obj_primary.identifier = (select max(identifier) from study_objective where study_protocol_identifier = sp.identifier and type_code = 'TERNARY')
    left outer join study_site as lo on lo.study_protocol_identifier = sp.identifier and lo.functional_code = 'LEAD_ORGANIZATION'
    left outer join organization as lead_org on lead_org.identifier = lo.research_organization_identifier
    left outer join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier and nci_id.root = '2.16.840.1.113883.3.26.4.3'
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
    left outer join organization as sponsor on sponsor.identifier = sponsor_ss.research_organization_identifier
    left outer join registry_user as submitter on submitter.csm_user_id = sp.user_last_created_id
    left outer join study_resourcing as summary4 on summary4.study_protocol_identifier = sp.identifier and summary4.summ_4_rept_indicator is true
    left outer join organization as summary4_sponsor on summary4_sponsor.identifier = cast(summary4.organization_identifier as INTEGER)
    left outer join study_overall_status as stopped on stopped.study_protocol_identifier = sp.identifier 
        and stopped.status_code in ('ADMINISTRATIVELY_COMPLETE', 'WITHDRAWN', 'TEMPORARILY_CLOSED_TO_ACCRUAL', 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION')
        and stopped.identifier = (select max(identifier) from study_overall_status where study_protocol_identifier = sp.identifier)
    left outer join study_site as irb on irb.study_protocol_identifier = sp.identifier and irb.functional_code = 'STUDY_OVERSIGHT_COMMITTEE'
    left outer join oversight_committee as oc on oc.identifier = irb.oversight_committee_identifier
    left outer join organization as irb_org on irb_org.identifier = oc.organization_identifier
    left outer join csm_user as updater on updater.user_id = sp.user_last_updated_id
    left outer join study_contact as central_contact on central_contact.study_protocol_identifier = sp.identifier and central_contact.role_code = 'CENTRAL_CONTACT'
    left outer join study_site as lead_org_id on lead_org_id.study_protocol_identifier = sp.identifier and lead_org_id.functional_code = 'LEAD_ORGANIZATION';
    

    
UPDATE DW_STUDY SET ELIGIBLE_GENDER = ec.eligible_gender_code from planned_activity pa
    inner join planned_eligibility_criterion ec on ec.identifier = pa.identifier and ec.criterion_name = 'GENDER'
    where pa.category_code = 'ELIGIBILITY_CRITERION' and internal_system_id = pa.study_protocol_identifier;
    
UPDATE DW_STUDY SET ELIGIBLE_MIN_AGE = ec.min_value || ' ' || ec.min_unit, ELIGIBLE_MAX_AGE = ec.max_value || ' ' || ec.max_unit from planned_activity pa
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

UPDATE DW_STUDY SET SPONSOR_OR_RESPONSIBLE_PARTY = resp.name, SPONSOR_RESP_PARTY_EMAIL = sc.email, SPONSOR_RESP_PARTY_PHONE = sc.telephone, 
                    RESP_PARTY_TYPE = 'PI'
    from study_contact sc 
        inner join clinical_research_staff crs on crs.identifier = sc.clinical_research_staff_identifier
        inner join organization resp on resp.identifier = crs.organization_identifier
    where sc.role_code = 'RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR' and internal_system_id = sc.study_protocol_identifier;

UPDATE DW_STUDY SET SPONSOR_OR_RESPONSIBLE_PARTY = org.name, SPONSOR_RESP_PARTY_EMAIL = resp.email, SPONSOR_RESP_PARTY_PHONE = resp.telephone,
                    RESP_PARTY_TYPE = 'SPONSOR', RESPONSIBLE_PARTY_PERSONAL_CONTACT = p.first_name || ' ' || p.last_name
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier
        inner join study_site_contact as resp on resp.study_site_identifier = ss.identifier and resp.role_code = 'RESPONSIBLE_PARTY_SPONSOR_CONTACT'
        inner join organizational_contact as oc on oc.identifier = resp.organizational_contact_identifier
        join person as p on p.identifier = oc.person_identifier
    where ss.functional_code = 'RESPONSIBLE_PARTY_SPONSOR' and internal_system_id = ss.study_protocol_identifier;

UPDATE DW_STUDY SET CTEP_ID = ss.local_sp_indentifier 
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'Cancer Therapy Evaluation Program'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER' and ss.study_protocol_identifier = internal_system_id;
    
UPDATE DW_STUDY SET DCP_ID = ss.local_sp_indentifier 
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'National Cancer Institute Division of Cancer Prevention'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER' and ss.study_protocol_identifier = internal_system_id;

UPDATE DW_STUDY SET NCT_ID = ss.local_sp_indentifier 
    from study_site ss
        inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
        inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'ClinicalTrials.gov'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER' and ss.study_protocol_identifier = internal_system_id;