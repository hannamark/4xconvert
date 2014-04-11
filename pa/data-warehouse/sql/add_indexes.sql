CREATE INDEX DW_STUDY_AMEND_DATE       on DW_STUDY_AMENDMENT(amendment_date);
CREATE INDEX DW_STUDY_AMEND_NCI_ID     on DW_STUDY_AMENDMENT(nci_id);

CREATE INDEX DW_STUDY_ANATOMIC_SITE_ANATOMIC_SITE_NAME_IDX on DW_study_anatomic_site(anatomic_site_name);
CREATE INDEX DW_STUDY_ANATOMIC_SITE_NCI_ID_IDX on DW_study_anatomic_site(nci_id);

CREATE INDEX DW_STUDY_ARM_CI_ID_IDX on DW_STUDY_ARM_AND_INTERVENTION(NCI_ID);
CREATE INDEX DW_STUDY_ARM_NAME_IDX on DW_STUDY_ARM_AND_INTERVENTION(ARM_NAME);
CREATE INDEX DW_STUDY_INT_NAME_IDX on DW_STUDY_ARM_AND_INTERVENTION(INTERVENTION_NAME);

CREATE INDEX DW_STUDY_BIOMARKER_NCI_ID_IDX on DW_study_biomarker(nci_id);


CREATE INDEX DW_STUDY_COLLABORATOR_ROLE_IDX on DW_STUDY_COLLABORATOR(functional_role);
CREATE INDEX DW_STUDY_COLLABORATOR_STATUS_IDX on DW_STUDY_COLLABORATOR(nci_id);
CREATE INDEX DW_STUDY_COLLABORATOR_NCI_ID_IDX on DW_STUDY_COLLABORATOR(status);

CREATE INDEX DW_STUDY_DISEASE_NCI_ID_IDX on DW_study_disease(nci_id);

CREATE INDEX DW_STUDY_CRITERIA_CDE_ID_IDX on dw_study_eligibility_criteria(cde_public_identifier);
CREATE INDEX DW_STUDY_CRITERIA_ORDER_IDX on dw_study_eligibility_criteria(display_order);
CREATE INDEX DW_STUDY_CRITERIA_CDE_VERSION_IDX on dw_study_eligibility_criteria(cde_version);
CREATE INDEX DW_STUDY_CRITERIA_GENDER_CODE_IDX on dw_study_eligibility_criteria(eligible_gender_code);
CREATE INDEX DW_STUDY_CRITERIA_INCLUSION_IDX on dw_study_eligibility_criteria(inclusion_indicator);
CREATE INDEX DW_STUDY_CRITERIA_STRUCTURED_IDX on dw_study_eligibility_criteria(structured_indicator);
CREATE INDEX DW_STUDY_CRITERIA_ID_IDX on dw_study_eligibility_criteria(internal_system_id);
CREATE INDEX DW_STUDY_CRITERIA_NAME_IDX on dw_study_eligibility_criteria(criterion_name);
CREATE INDEX DW_STUDY_CRITERIA_NCI_ID_IDX on dw_study_eligibility_criteria(nci_id);
CREATE INDEX DW_STUDY_CRITERIA_OPERATOR_IDX on dw_study_eligibility_criteria(operator);
CREATE INDEX DW_STUDY_CRITERIA_UNIT_IDX on dw_study_eligibility_criteria(unit);
CREATE INDEX DW_STUDY_CRITERIA_VALUE_IDX on dw_study_eligibility_criteria(value);

CREATE INDEX DW_STUDY_GRANT_ACTIVE_INDICATOR_IDX on dw_study_grant(active_indicator);
CREATE INDEX DW_STUDY_GRANT_FUNDING_MECHANISM_CODE_IDX on dw_study_grant(funding_mechanism_code);
CREATE INDEX DW_STUDY_GRANT_INTERNAL_SYSTEM_ID_IDX on dw_study_grant(internal_system_id);
CREATE INDEX DW_STUDY_GRANT_NCI_DIVISION_OR_PROGRAM_IDX on dw_study_grant(nci_division_or_program);
CREATE INDEX DW_STUDY_GRANT_NCI_ID_IDX on dw_study_grant(NCI_ID);
CREATE INDEX DW_STUDY_GRANT_NIH_INSTITUTION_CODE_IDX on dw_study_grant(nih_institution_code);
CREATE INDEX DW_STUDY_GRANT_SERIAL_NUMBER_IDX on dw_study_grant(serial_number);

CREATE INDEX DW_GRANTS_I2E_SERIAL_NUM_IDX on dw_grants_i2e(serial_number);

CREATE INDEX DW_STUDY_IND_IDE_DATE_LAST_CREATED_IDX on dw_study_ind_ide(date_last_created);
CREATE INDEX DW_STUDY_IND_IDE_DATE_LAST_UPDATED_IDX on dw_study_ind_ide(date_last_updated);
CREATE INDEX DW_STUDY_IND_IDE_EXPANDED_ACCESS_INDICATOR_IDX on dw_study_ind_ide(expanded_access_indicator);
CREATE INDEX DW_STUDY_IND_IDE_EXPANDED_ACCESS_STATUS_CODE_IDX on dw_study_ind_ide(expanded_access_status_code);
CREATE INDEX DW_STUDY_IND_IDE_GRANTOR_CODE_IDX on dw_study_ind_ide(grantor_code);
CREATE INDEX DW_STUDY_IND_IDE_HOLDER_TYPE_CODE_IDX on dw_study_ind_ide(holder_type_code);
CREATE INDEX DW_STUDY_IND_IDE_IND_IDE_NUMBER_IDX on dw_study_ind_ide(ind_ide_number);
CREATE INDEX DW_STUDY_IND_IDE_IND_IDE_TYPE_CODE_IDX on dw_study_ind_ide(ind_ide_type_code);
CREATE INDEX DW_STUDY_IND_IDE_INTERNAL_SYSTEM_ID_IDX on dw_study_ind_ide(internal_system_id);
CREATE INDEX DW_STUDY_IND_IDE_NCI_DIV_PROG_HOLDER_CODE_IDX on dw_study_ind_ide(nci_div_prog_holder_code);
CREATE INDEX DW_STUDY_IND_IDE_NCI_ID_IDX on dw_study_ind_ide(nci_id);
CREATE INDEX DW_STUDY_IND_IDE_NIH_INSTHOLDER_CODE_IDX on dw_study_ind_ide(nih_instholder_code);
CREATE INDEX DW_STUDY_IND_IDE_USER_LAST_CREATED_IDX on dw_study_ind_ide(user_last_created);
CREATE INDEX DW_STUDY_IND_IDE_USER_LAST_UPDATED_IDX on dw_study_ind_ide(user_last_updated);

CREATE INDEX DW_STUDY_MILESTONE_COMMENTS_IDX on dw_study_milestone(comments);
CREATE INDEX DW_STUDY_MILESTONE_DATE_IDX on dw_study_milestone(date);
CREATE INDEX DW_STUDY_MILESTONE_DATE_CREATED_IDX on dw_study_milestone(date_created);
CREATE INDEX DW_STUDY_MILESTONE_DATE_UPDATED_IDX on dw_study_milestone(date_last_updated);
CREATE INDEX DW_STUDY_MILESTONE_ID_IDX on dw_study_milestone(internal_system_id);
CREATE INDEX DW_STUDY_MILESTONE_NAME_IDX on dw_study_milestone(name);
CREATE INDEX DW_STUDY_MILESTONE_NCI_ID_IDX on dw_study_milestone(nci_id);
CREATE INDEX DW_STUDY_MILESTONE_USER_CREATED_IDX on dw_study_milestone(user_name_created);
CREATE INDEX DW_STUDY_MILESTONE_USER_UPDATED_IDX on dw_study_milestone(user_name_last_updated);
CREATE INDEX DW_STUDY_MILESTONE_FNAME_CREATED_IDX on dw_study_milestone(first_name_created);
CREATE INDEX DW_STUDY_MILESTONE_LNAME_CREATED_IDX on dw_study_milestone(last_name_created);
CREATE INDEX DW_STUDY_MILESTONE_FNAME_UPDATED_IDX on dw_study_milestone(first_name_last_updated);
CREATE INDEX DW_STUDY_MILESTONE_LNAME_UPDATED_IDX on dw_study_milestone(last_name_last_updated);

CREATE INDEX DW_STUDY_ON_HOLD_REASON_DESC_IDX on dw_study_on_hold_status(reason_description);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_IDX on dw_study_on_hold_status(reason);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_ON_HOLD_DATE_IDX on dw_study_on_hold_status(on_hold_date);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_OFF_HOLD_DATE_IDX on dw_study_on_hold_status(off_hold_date);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_DATE_CREATED_IDX on dw_study_on_hold_status(date_created);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_DATE_UPDATED_IDX on dw_study_on_hold_status(date_last_updated);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_ID_IDX on dw_study_on_hold_status(internal_system_id);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_NCI_ID_IDX on dw_study_on_hold_status(nci_id);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_NAME_CREATED_IDX on dw_study_on_hold_status(user_name_created);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_NAME_UPDATED_IDX on dw_study_on_hold_status(user_name_last_updated);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_FNAME_CREATED_IDX on dw_study_on_hold_status(first_name_created);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_LNAME_CREATED_IDX on dw_study_on_hold_status(last_name_created);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_FNAME_UPDATED_IDX on dw_study_on_hold_status(first_name_last_updated);
CREATE INDEX DW_STUDY_ON_HOLD_REASON_LNAME_UPDATED_IDX on dw_study_on_hold_status(last_name_last_updated);

CREATE INDEX DW_STUDY_OTHER_IDENTIFIER_IDX on dw_study_other_identifier(name);
CREATE INDEX DW_STUDY_OTHER_NCI_ID_IDX on dw_study_other_identifier(nci_id);
CREATE INDEX DW_STUDY_OTHER_VALUE_IDX on dw_study_other_identifier(value);

CREATE INDEX DW_STUDY_OUTCOME_DESC_IDX on dw_study_outcome_measure(description);
CREATE INDEX DW_STUDY_OUTCOME_NCI_IDX on dw_study_outcome_measure(description);


CREATE INDEX DW_STUDY_OVERALL_STATUS_STATUS_IDX on DW_STUDY_OVERALL_STATUS(status);
CREATE INDEX DW_STUDY_OVERALL_STATUS_STATUS_DATE_IDX on DW_STUDY_OVERALL_STATUS(status_date);
CREATE INDEX DW_STUDY_OVERALL_STATUS_DATE_CREATED_IDX on DW_STUDY_OVERALL_STATUS(date_created);
CREATE INDEX DW_STUDY_OVERALL_STATUS_DATE_UPDATED_IDX on DW_STUDY_OVERALL_STATUS(date_last_updated);
CREATE INDEX DW_STUDY_OVERALL_STATUS_SYSTEM_CREATED_IDX on DW_STUDY_OVERALL_STATUS using hash(system_created);
CREATE INDEX DW_STUDY_OVERALL_STATUS_WHY_IDX on DW_STUDY_OVERALL_STATUS(why_study_stopped);
CREATE INDEX DW_STUDY_OVERALL_STATUS_INT_ID_IDX on DW_STUDY_OVERALL_STATUS(internal_system_id);
CREATE INDEX DW_STUDY_OVERALL_STATUS_NCI_ID_IDX on DW_STUDY_OVERALL_STATUS(nci_id);
CREATE INDEX DW_STUDY_OVERALL_STATUS_USER_CREATED_IDX on DW_STUDY_OVERALL_STATUS(user_Created);
CREATE INDEX DW_STUDY_OVERALL_STATUS_USER_UPDATED_IDX on DW_STUDY_OVERALL_STATUS(user_last_updated);

CREATE INDEX DW_STUDY_PARTICIPATING_SITE_CONTACT_EMAIL_IDX on dw_study_participating_site(contact_email);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_CONTACT_NAME_IDX on dw_study_participating_site(contact_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_GENERIC_CONTACT_IDX on dw_study_participating_site(generic_contact);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INTERNAL_SYSTEM_ID_IDX on dw_study_participating_site(internal_system_id);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_NCI_ID_IDX on dw_study_participating_site(nci_id);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_NAME_IDX on dw_study_participating_site(org_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_ORG_FAMILY_IDX on dw_study_participating_site(org_org_family);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_RECRUITMENT_STATUS_IDX on dw_study_participating_site(recruitment_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_RECRUITMENT_STATUS_DATE_IDX on dw_study_participating_site(recruitment_status_date);

CREATE INDEX DW_STUDY_PS_INVESTIGATOR_ID on dw_study_participating_site_investigators(INTERNAL_SYSTEM_ID);
CREATE INDEX DW_STUDY_PS_INVESTIGATOR_NCI on dw_study_participating_site_investigators(NCI_ID);
CREATE INDEX DW_STUDY_PS_INVESTIGATOR_FIRST on dw_study_participating_site_investigators(INVESTIGATOR_FIRST_NAME);
CREATE INDEX DW_STUDY_PS_INVESTIGATOR_LAST on dw_study_participating_site_investigators(INVESTIGATOR_LAST_NAME);
CREATE INDEX DW_STUDY_PS_INVESTIGATOR_ROLE on dw_study_participating_site_investigators(INVESTIGATOR_ROLE);
CREATE INDEX DW_STUDY_PS_INVESTIGATOR_ORG on dw_study_participating_site_investigators(PARTICIPATING_SITE_ORG_NAME);

CREATE INDEX DW_STUDY_PROC_STATUS_COMMENTS_IDX on dw_study_PROCESSING_STATUS(comments);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_DATE_IDX on dw_study_PROCESSING_STATUS(date);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_DATE_CREATED_IDX on dw_study_PROCESSING_STATUS(date_created);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_DATE_UPDATED_IDX on dw_study_PROCESSING_STATUS(date_last_updated);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_ID_IDX on dw_study_PROCESSING_STATUS(internal_system_id);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_NCI_ID_IDX on dw_study_PROCESSING_STATUS(nci_id);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_STATUS_IDX on dw_study_PROCESSING_STATUS(status);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_USER_CREATED_IDX on dw_study_PROCESSING_STATUS(user_name_created);
CREATE INDEX DW_STUDY_PROCESSING_STATUS_USER_UPDATED_IDX on dw_study_PROCESSING_STATUS(user_name_last_updated);
CREATE INDEX DW_STUDY_PROCESSING_REASON_FNAME_CREATED_IDX on dw_study_PROCESSING_STATUS(first_name_created);
CREATE INDEX DW_STUDY_PROCESSING_REASON_LNAME_CREATED_IDX on dw_study_PROCESSING_STATUS(last_name_created);
CREATE INDEX DW_STUDY_PROCESSING_REASON_FNAME_UPDATED_IDX on dw_study_PROCESSING_STATUS(first_name_last_updated);
CREATE INDEX DW_STUDY_PROCESSING_REASON_LNAME_UPDATED_IDX on dw_study_PROCESSING_STATUS(last_name_last_updated);

CREATE INDEX DW_STUDY_RECORD_OWNER_CITY_IDX on dw_study_record_owner(address_city);
CREATE INDEX DW_STUDY_RECORD_OWNER_LINE1_IDX on dw_study_record_owner(address_line);
CREATE INDEX DW_STUDY_RECORD_OWNER_STATE_IDX on dw_study_record_owner(address_state);
CREATE INDEX DW_STUDY_RECORD_OWNER_EMAIL_IDX on dw_study_record_owner(email);
CREATE INDEX DW_STUDY_RECORD_OWNER_NAME_IDX on dw_study_record_owner(name);
CREATE INDEX DW_STUDY_RECORD_OWNER_NCI_ID_IDX on dw_study_record_owner(nci_id);
CREATE INDEX DW_STUDY_RECORD_OWNER_PHONE_ID_IDX on dw_study_record_owner(phone_number);

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
CREATE INDEX DW_STUDY_CDR_ID_IDX on dw_Study(CDR_ID);
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
CREATE INDEX DW_STUDY_WHY_STUDY_STOPPED_IDX on dw_study(WHY_STUDY_STOPPED);
CREATE INDEX DW_STUDY_CATEGORY on dw_study(category);

CREATE INDEX DW_SUMMARY_4_FUNDING_NCI_ID on DW_SUMMARY_4_FUNDING(NCI_ID);

CREATE INDEX DW_STUDY_DESC_IDX on dw_study_subgroup(description);
CREATE INDEX DW_STUDY_GROUP_CODE_IDX on dw_study_subgroup(group_code);
CREATE INDEX DW_STUDY_NCI_IDX on dw_study_subgroup(nci_id);

CREATE INDEX DW_STUDY_SITE_ACCRUAL_ACCESS_NCI_IDX on dw_study_site_accrual_access(nci_id);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_ACCESS_ACCRUAL_ADMIN_IDX on dw_study_site_accrual_access(accrual_admin);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_ACCESS_ORG_IDX on dw_study_site_accrual_access(org_name);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_ACCESS_ORG_FAMILY_IDX on dw_study_site_accrual_access(org_org_family);

CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_COUNTRY_IDX on dw_study_site_accrual_details(country);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_DATE_LAST_CREATED_IDX on dw_study_site_accrual_details(date_last_created);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_DATE_LAST_UPDATED_IDX on dw_study_site_accrual_details(date_last_updated);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_ETHNICITY_IDX on dw_study_site_accrual_details(ethnicity);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_GENDER_IDX on dw_study_site_accrual_details(gender);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_ICD9_DISEASE_CODE_IDX on dw_study_site_accrual_details(icd9_disease_code);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_ICD9_DISEASE_TERM_IDX on dw_study_site_accrual_details(icd9_disease_term);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_NCI_ID_IDX on dw_study_site_accrual_details(nci_id);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_ORG_NAME_IDX on dw_study_site_accrual_details(org_name);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_ORG_ORG_FAMILY_IDX on dw_study_site_accrual_details(org_org_family);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_PAYMENT_METHOD_IDX on dw_study_site_accrual_details(payment_method);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_RACE_IDX on dw_study_site_accrual_details(race);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_REGISTRATION_DATE_IDX on dw_study_site_accrual_details(registration_date);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_REGISTRATION_GROUP_IDX on dw_study_site_accrual_details(registration_group);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_SDC_DISEASE_CODE_IDX on dw_study_site_accrual_details(sdc_disease_code);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_SDC_DISEASE_TERM_IDX on dw_study_site_accrual_details(sdc_disease_term);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_SITE_ID_IDX on dw_study_site_accrual_details(site_id);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_SITE_ORG_ID_IDX on dw_study_site_accrual_details(site_org_id);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_STATUS_IDX on dw_study_site_accrual_details(status);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_USER_NAME_LAST_CREATED_IDX on dw_study_site_accrual_details(user_name_last_created);
CREATE INDEX DW_STUDY_SITE_ACCRUAL_DETAIL_USER_NAME_LAST_UPDATED_IDX on dw_study_site_accrual_details(user_name_last_updated);

CREATE INDEX DW_STUDY_ACCRUAL_COUNT_ACCRUAL_COUNT_IDX on dw_study_accrual_count(accrual_count);
CREATE INDEX DW_STUDY_ACCRUAL_COUNT_COUNT_TYPE_IDX on dw_study_accrual_count(count_type);
CREATE INDEX DW_STUDY_ACCRUAL_COUNT_NCI_ID_IDX on dw_study_accrual_count(nci_id);
CREATE INDEX DW_STUDY_ACCRUAL_COUNT_ORG_NAME_IDX on dw_study_accrual_count(org_name);
CREATE INDEX DW_STUDY_ACCRUAL_COUNT_ORG_ORG_FAMILY_IDX on dw_study_accrual_count(org_org_family);

CREATE INDEX DW_STUDY_IND_IDE_EXEMPT_INDICATOR on dw_study_ind_ide(exempt_indicator);
CREATE INDEX DW_STUDY_IND_IDE_EXPANDED_ACCESS_INDICATOR on dw_study_ind_ide(expanded_access_indicator);
CREATE INDEX DW_STUDY_IND_IDE_EXPANDED_ACCESS_STATUS_CODE on dw_study_ind_ide(expanded_access_status_code);
CREATE INDEX DW_STUDY_IND_IDE_GRANTOR_CODE on dw_study_ind_ide(grantor_code);
CREATE INDEX DW_STUDY_IND_IDE_HOLDER_TYPE_CODE on dw_study_ind_ide(holder_type_code);
CREATE INDEX DW_STUDY_IND_IDE_IND_IDE_NUMBER on dw_study_ind_ide(ind_ide_number);
CREATE INDEX DW_STUDY_IND_IDE_IND_IDE_TYPE_CODE on dw_study_ind_ide(ind_ide_type_code);
CREATE INDEX DW_STUDY_IND_IDE_NCI_DIV_PROG_HOLDER_CODE on dw_study_ind_ide(nci_div_prog_holder_code);
CREATE INDEX DW_STUDY_IND_IDE_NCI_ID on dw_study_ind_ide(nci_id);
CREATE INDEX DW_STUDY_IND_IDE_NIH_INSTHOLDER_CODE on dw_study_ind_ide(nih_instholder_code);
CREATE INDEX DW_STUDY_IND_IDE_NIH_INSTHOLDER_NAME on dw_study_ind_ide(nih_instholder_name);

CREATE INDEX DW_USER_AFFILIATED_ORGANIZATION on dw_user(affiliated_organization);
CREATE INDEX DW_USER_CITY on dw_user(city);
CREATE INDEX DW_USER_COUNTRY on dw_user(country);
CREATE INDEX DW_USER_EMAIL_NOTIFICATION_REQUIRED on dw_user(email_notification_required);
CREATE INDEX DW_USER_POSTAL_CODE on dw_user(postal_code);
CREATE INDEX DW_USER_PRS_ORGANIZATION on dw_user(prs_organization);
CREATE INDEX DW_USER_SITE_ADMIN on dw_user(site_admin);
CREATE INDEX DW_USER_STATE on dw_user(state);
CREATE INDEX DW_USER_USER_NAME on dw_user(user_name);

CREATE INDEX DW_ACCRUAL_BATCH_SUBMISSION_BATCH_FILE_IDENTIFIER on dw_accrual_batch_submission(batch_file_identifier);
CREATE INDEX DW_ACCRUAL_BATCH_SUBMISSION_CHANGE_CODE on dw_accrual_batch_submission(change_code);
CREATE INDEX DW_ACCRUAL_BATCH_SUBMISSION_CORRESPONDING_NCI_ID on dw_accrual_batch_submission(corresponding_nci_id);
CREATE INDEX DW_ACCRUAL_BATCH_SUBMISSION_PASSED_VALIDATION on dw_accrual_batch_submission(passed_validation);
CREATE INDEX DW_ACCRUAL_BATCH_SUBMISSION_STUDY_ID_SUBMITTED on dw_accrual_batch_submission(study_id_submitted);

DROP INDEX IF EXISTS DW_ORGANIZATION_AUDIT_IDX;
CREATE INDEX DW_ORGANIZATION_AUDIT_IDX on dw_organization_audit(internal_system_id);
DROP INDEX IF EXISTS DW_PERSON_AUDIT_IDX;
CREATE INDEX DW_PERSON_AUDIT_IDX on dw_person_audit(internal_system_id);

CREATE INDEX DW_STUDYFI_BRIEF_SUMMARY_IDX ON DW_STUDY_FEWER_INDEXES(BRIEF_SUMMARY);
CREATE INDEX DW_STUDYFI_BRIEF_TITLE_IDX ON DW_STUDY_FEWER_INDEXES(BRIEF_TITLE);
CREATE INDEX DW_STUDYFI_CATEGORY ON DW_STUDY_FEWER_INDEXES(CATEGORY);
CREATE INDEX DW_STUDYFI_CDR_ID_IDX ON DW_STUDY_FEWER_INDEXES(CDR_ID);
CREATE INDEX DW_STUDYFI_COMPLETION_DATE_IDX ON DW_STUDY_FEWER_INDEXES(COMPLETION_DATE);
CREATE INDEX DW_STUDYFI_CTEP_ID_IDX ON DW_STUDY_FEWER_INDEXES(CTEP_ID);
CREATE INDEX DW_STUDYFI_CURRENT_MILESTONE_IDX ON DW_STUDY_FEWER_INDEXES(CURRENT_MILESTONE);
CREATE INDEX DW_STUDYFI_CURRENT_TRIAL_STATUS_DATE_IDX ON DW_STUDY_FEWER_INDEXES(CURRENT_TRIAL_STATUS_DATE);
CREATE INDEX DW_STUDYFI_CURRENT_TRIAL_STATUS_IDX ON DW_STUDY_FEWER_INDEXES(CURRENT_TRIAL_STATUS);
CREATE INDEX DW_STUDYFI_DCP_ID_IDX ON DW_STUDY_FEWER_INDEXES(DCP_ID);
CREATE INDEX DW_STUDYFI_INTERNAL_SYSTEM_ID_IDX ON DW_STUDY_FEWER_INDEXES(INTERNAL_SYSTEM_ID);
CREATE INDEX DW_STUDYFI_INTERVENTIONAL_MODEL_IDX ON DW_STUDY_FEWER_INDEXES(INTERVENTIONAL_MODEL);
CREATE INDEX DW_STUDYFI_LEAD_ORG_ID_IDX ON DW_STUDY_FEWER_INDEXES(LEAD_ORG_ID);
CREATE INDEX DW_STUDYFI_LEAD_ORG_IDX ON DW_STUDY_FEWER_INDEXES(LEAD_ORG);
CREATE INDEX DW_STUDYFI_LEAD_ORG_ORG_FAMILY_IDX ON DW_STUDY_FEWER_INDEXES(LEAD_ORG_ORG_FAMILY);
CREATE INDEX DW_STUDYFI_NCI_ID_IDX ON DW_STUDY_FEWER_INDEXES(NCI_ID);
CREATE INDEX DW_STUDYFI_NCT_ID_IDX ON DW_STUDY_FEWER_INDEXES(NCT_ID);
CREATE INDEX DW_STUDYFI_OFFICIAL_TITLE_IDX ON DW_STUDY_FEWER_INDEXES(OFFICIAL_TITLE);
CREATE INDEX DW_STUDYFI_PHASE_IDX ON DW_STUDY_FEWER_INDEXES(PHASE);
CREATE INDEX DW_STUDYFI_PRIMARY_COMPLETION_DATE_IDX ON DW_STUDY_FEWER_INDEXES(PRIMARY_COMPLETION_DATE);
CREATE INDEX DW_STUDYFI_PRINCIPAL_INVESTIGATOR_IDX ON DW_STUDY_FEWER_INDEXES(PRINCIPAL_INVESTIGATOR);
CREATE INDEX DW_STUDYFI_PROCESSING_STATUS_IDX ON DW_STUDY_FEWER_INDEXES(PROCESSING_STATUS);
CREATE INDEX DW_STUDYFI_RESP_PARTY_TYPE_IDX ON DW_STUDY_FEWER_INDEXES(RESP_PARTY_TYPE);
CREATE INDEX DW_STUDYFI_SPONSOR_IDX ON DW_STUDY_FEWER_INDEXES(SPONSOR);
CREATE INDEX DW_STUDYFI_SPONSOR_ORG_FAMILY_IDX ON DW_STUDY_FEWER_INDEXES(SPONSOR_ORG_FAMILY);
CREATE INDEX DW_STUDYFI_START_DATE_IDX ON DW_STUDY_FEWER_INDEXES(START_DATE);
CREATE INDEX DW_STUDYFI_SUBMITTER_NAME_IDX ON DW_STUDY_FEWER_INDEXES(SUBMITTER_NAME);
CREATE INDEX DW_STUDYFI_SUBMITTER_ORGANIZATION_FAMILY_IDX ON DW_STUDY_FEWER_INDEXES(SUBMITTER_ORGANIZATION_FAMILY);
CREATE INDEX DW_STUDYFI_SUBMITTER_ORGANIZATION_IDX ON DW_STUDY_FEWER_INDEXES(SUBMITTER_ORGANIZATION);

CREATE INDEX DW_DATA_4_NCI_ID_IDX ON DW_DATA_TABLE_4(NCI_ID);
CREATE INDEX DW_DATA_4_CR_CAT_IDX ON DW_DATA_TABLE_4(CLINICAL_RESEARCH_CAT);
CREATE INDEX DW_DATA_4_STUDY_SOURCE_IDX ON DW_DATA_TABLE_4(STUDY_SOURCE);
CREATE INDEX DW_DATA_4_SFS_IDX ON DW_DATA_TABLE_4(SPECIFIC_FUNDING_SOURCE);
CREATE INDEX DW_DATA_4_IS_MULTIINSTITUTIONAL_IDX ON DW_DATA_TABLE_4(IS_MULTIINSTITUTIONAL);
CREATE INDEX DW_DATA_4_SITE_IDX ON DW_DATA_TABLE_4(SITE);
CREATE INDEX DW_DATA_4_OPEN_DATE_IDX ON DW_DATA_TABLE_4(OPEN_DATE);
CREATE INDEX DW_DATA_4_CLOSE_DATE_IDX ON DW_DATA_TABLE_4(CLOSE_DATE);
CREATE INDEX DW_DATA_4_PHASE_IDX ON DW_DATA_TABLE_4(PHASE);
CREATE INDEX DW_DATA_4_SUMMARY_LEVEL_ACCRUAL_IDX ON DW_DATA_TABLE_4(SUMMARY_LEVEL_ACCRUAL);

CREATE INDEX DW_STUDY_ASSOC_A ON DW_STUDY_ASSOCIATION(study_a);
CREATE INDEX DW_STUDY_ASSOC_B ON DW_STUDY_ASSOCIATION(study_b);

CREATE INDEX DW_ASSAY_TYPE_CADSR_ID ON DW_ASSAY_TYPE(cadsr_id);

CREATE INDEX DW_BIOMARKER_PURPOSE_CADSR_ID ON DW_BIOMARKER_PURPOSE(cadsr_id);

CREATE INDEX DW_BIOMARKER_USE_CADSR_ID ON DW_BIOMARKER_USE(cadsr_id);

CREATE INDEX DW_EVALUATION_TYPE_CADSR_ID ON DW_EVALUATION_TYPE(cadsr_id);

CREATE INDEX DW_SPECIMEN_COLLECTION_CADSR_ID ON DW_SPECIMEN_COLLECTION(cadsr_id);

CREATE INDEX DW_SPECIMEN_TYPE_CADSR_ID ON DW_SPECIMEN_TYPE(cadsr_id);

CREATE INDEX DW_STUDY_SECONDARY_PURPOSE_NCI_IDX on DW_STUDY_SECONDARY_PURPOSE(nci_id);

CREATE INDEX DW_ACCRUAL_COUNT_AUDIT_SITE_IDX ON DW_STUDY_ACCRUAL_COUNT_AUDIT(study_site_id);
CREATE INDEX DW_ACCRUAL_COUNT_AUDIT_DATE_IDX ON DW_STUDY_ACCRUAL_COUNT_AUDIT(createddate);

CREATE INDEX DW_FAMILY_TRIAL_DATA_FAM_IDX ON DW_FAMILY_TRIAL_DATA(family_name);
CREATE INDEX DW_FAMILY_TRIAL_DATA_NCI_IDX ON DW_FAMILY_TRIAL_DATA(nci_id);

CREATE INDEX DW_PERSON_CTEP_IDX ON DW_PERSON(ctep_id);

CREATE INDEX DW_PERSON_ROLE_PERSON_IDX ON DW_PERSON_ROLE(person_po_id);
CREATE INDEX DW_PERSON_ROLE_ROLE_IDX ON DW_PERSON_ROLE(role_po_id);

CREATE INDEX DW_STUDY_INBOX_NCI_IDX ON DW_STUDY_INBOX(nci_id);
CREATE INDEX DW_STUDY_INBOX_OPEN_DT_IDX ON DW_STUDY_INBOX(open_date);
CREATE INDEX DW_STUDY_INBOX_CLOSE_DT_IDX ON DW_STUDY_INBOX(close_date);

