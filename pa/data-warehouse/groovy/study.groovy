import groovy.sql.Sql

def sql =
    """SELECT
            CASE WHEN sp.accept_healthy_volunteers_indicator THEN 'YES'
                 ELSE 'NO'
            END as healthy_volunteer_indicator,
            sp.acronym, sp.allocation_code, sp.amendment_date,
            sp.amendment_number, sp.amendment_reason_code,
            central_contact.email as central_contact_email, central_contact.telephone as central_contact_phone,
            admin.user_identifier as admin_user, scientific.user_identifier as scientific_user,
            sp.study_classification_code, sp.completion_date, sp.completion_date_type_code,
            CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                 WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                 ELSE split_part(creator.login_name, 'CN=', 2)
            END as creator,
            CASE WHEN sp.ctgov_xml_required_indicator THEN 'YES'
                 ELSE 'NO'
            END as ctgov_indicator,
            milestone.milestone_code, current_status.status_code as current_status, current_status.status_date as current_status_date,
            CASE WHEN sp.data_monty_comty_apptn_indicator THEN 'YES'
                 ELSE 'NO'
            END as data_indicator,
            sp.date_last_created, sp.date_last_updated,
            CASE WHEN sp.delayed_posting_indicator THEN 'YES'
                 ELSE 'NO'
            END as delayed_posting_indicator,
            sp.scientific_description, obj_primary.description as primary, obj_secondary.description as secondary, obj_ternary.description as ternary,
            CASE WHEN sp.expd_access_indidicator THEN 'YES'
                 ELSE 'NO'
            END as expanded_access_indicator,
            CASE WHEN sp.fda_regulated_indicator THEN 'YES'
                 ELSE 'NO'
            END as fda_indicator,
            sp.identifier, sp.design_configuration_code, irb.review_board_approval_number,
            irb.review_board_approval_status_code, irb_org.city, irb_org.country_name, irb_org.name as irb_name,
            irb.review_board_organizational_affiliation, irb_org.state, irb_org.postal_code, sp.keyword_text,
            CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                 WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                 ELSE split_part(updater.login_name, 'CN=', 2)
            END as updater,
            lead_org.name as lead_org_name, lead_org_id.local_sp_indentifier as lead_org_id,
            sp.blinding_schema_code, sp.blinding_role_code_investigator, sp.blinding_role_code_outcome, sp.blinding_role_code_subject,
            sp.blinding_role_code_caregiver, sp.min_target_accrual_num, sp.number_of_intervention_groups,
            sp.official_title, oversight_country.name as oversight_country, oversight.authority_name as oversight_org_name, sp.phase_code,
            sp.phase_additional_qualifier_code, sp.phase_other_text, sp.pri_compl_date, sp.pri_compl_date_type_code,
            sp.primary_purpose_additional_qualifier_code, sp.primary_purpose_code, sp.primary_purpose_other_text, pi.first_name || ' ' || pi.last_name as principal_investigator,
            processing_status.status_code as processing_status,
            sp.public_description, sp.public_tittle, sp.record_verification_date, rejection.comment_text as rejection_reason,
            sp.accr_rept_meth_code,
            CASE WHEN sp.review_brd_approval_req_indicator THEN 'YES'
                 ELSE 'NO'
            END as review_board_indicator,
            CASE WHEN sp.section801_indicator THEN 'YES'
                 ELSE 'NO'
            END as section801_indicator,
            sponsor.name as sponsor, sp.start_date, sp.start_date_type_code, sp.submission_number,
            submitter.first_name || ' ' || submitter.last_name as submitter, submitter.affiliate_org as submitter_org,
            summary4.type_code as summary4_type_code, summary4_sponsor.name as summary4_sponsor, stopped.comment_text as why_stopped
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
        where sp.status_code = 'ACTIVE'"""


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
        properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
        properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def studies = destinationConnection.dataSet("DW_STUDY")
sourceConnection.eachRow(sql) { row ->
    studies.add(accepts_healthy_volunteers_indicator: row.healthy_volunteer_indicator, acronym: row.acronym, masking_allocation_code: row.allocation_code,
        amendment_date: row.amendment_date, amendment_number_text: row.amendment_number, amendment_reason_code: row.amendment_reason_code,
        central_contact_email: row.central_contact_email, central_contact_phone: row.central_contact_phone, checked_out_by_for_administrative: row.admin_user,
        checked_out_by_for_scientific: row.scientific_user, classification_code: row.study_classification_code, completion_date: row.completion_date,
        completion_date_type_code: row.completion_date_type_code, created_by: row.creator, ct_gov_xml_required_indicator: row.ctgov_indicator,
        current_milestone: row.milestone_code, current_trial_status: row.current_status, current_trial_status_date: row.current_status_date,
        data_monitoring_committee_appointed_indicator: row.data_indicator, date_last_created: row.date_last_created, date_last_updated: row.date_last_updated,
        delayed_posting_indicator: row.delayed_posting_indicator, detail_description: row.scientific_description,
        detail_description_primary: row.primary, detail_description_secondary: row.secondary, detail_description_tertiary: row.ternary,
        expanded_access_indicator: row.expanded_access_indicator, fdaregulated_indicator: row.fda_indicator, internal_system_id: row.identifier,
        interventional_model: row.design_configuration_code, irb_approval_number: row.review_board_approval_number, irb_approval_status:row.review_board_approval_status_code,
        irb_city: row.city, irb_country: row.country_name, irb_organization_affiliation: row.review_board_organizational_affiliation,
        irb_state_or_province: row.state, irb_zip_code: row.postal_code, irb_name: row.irb_name, keyword_text: row.keyword_text,
        last_updated_by: row.updater, lead_org: row.lead_org_name, lead_org_id: row.lead_org_id, masking: row.blinding_schema_code,
        masking_role_investigator: row.blinding_role_code_investigator, masking_role_outcome_assessor: row.blinding_role_code_outcome,
        masking_role_subject: row.blinding_role_code_subject, masking_role_caregiver: row.blinding_role_code_caregiver,
        minimum_target_accrual_number: row.min_target_accrual_num, number_of_arms: row.number_of_intervention_groups, official_title: row.official_title,
        oversight_authority_country: row.oversight_country, oversight_authority_organization_name: row.oversight_org_name,
        phase: row.phase_code, phase_additional_qualifier_code: row.phase_additional_qualifier_code, phase_other_text: row.phase_other_text,
        primary_completion_date: row.pri_compl_date, primary_completion_date_type_code: row.pri_compl_date_type_code,
        primary_purpose_additional_qualifier_code: row.primary_purpose_additional_qualifier_code, primary_purpose_code: row.primary_purpose_code,
        primary_purpose_other_text: row.primary_purpose_other_text, principal_investigator: row.principal_investigator,
        processing_status: row.processing_status, brief_summary: row.public_description, brief_title: row.public_tittle,
        record_verification_date: row.record_verification_date, rejection_reason: row.rejection_reason,
        reporting_method_data_code: row.accr_rept_meth_code, review_board_approval_required_indicator: row.review_board_indicator,
        section_801_indicator: row.section801_indicator, sponsor: row.sponsor, start_date: row.start_date, start_date_type_code: row.start_date_type_code,
        submission_number: row.submission_number, submitter_name: row.submitter, submitter_organization: row.submitter_org,
        summary_4_funding_category: row.summary4_type_code, summary_4_funding_sponsor: row.summary4_sponsor, why_study_stopped: row.why_stopped)
    }

sourceConnection.eachRow("""select ec.eligible_gender_code, pa.study_protocol_identifier from planned_activity pa
    inner join planned_eligibility_criterion ec on ec.identifier = pa.identifier and ec.criterion_name = 'GENDER'
    where pa.category_code = 'ELIGIBILITY_CRITERION'""") { row ->
        id = row.study_protocol_identifier
        gender = row.eligible_gender_code
        destinationConnection.execute("UPDATE DW_STUDY SET ELIGIBLE_GENDER = ? where internal_system_id = ? ", [gender, id])
    }

sourceConnection.eachRow("""select trunc(ec.min_value) || ' ' || ec.min_unit as min_age, trunc(ec.max_value) || ' ' || ec.max_unit as max_age,
    pa.study_protocol_identifier from planned_activity pa
    inner join planned_eligibility_criterion ec on ec.identifier = pa.identifier and ec.criterion_name = 'AGE'
    where pa.category_code = 'ELIGIBILITY_CRITERION'""") { row ->
        id = row.study_protocol_identifier
        min_age = row.min_age
        max_age = row.max_age
        destinationConnection.execute("UPDATE DW_STUDY SET ELIGIBLE_MIN_AGE = ?, ELIGIBLE_MAX_AGE = ? where internal_system_id = ? ", [min_age, max_age, id])
    }

sourceConnection.eachRow("""select cc.first_name || ' ' || cc.last_name as central_contact, sc.study_protocol_identifier from study_contact sc
    inner join clinical_research_staff crs on crs.identifier = sc.clinical_research_staff_identifier
    inner join person cc on cc.identifier = crs.person_identifier
    where  sc.role_code = 'CENTRAL_CONTACT' and sc.organizational_contact_identifier is null""") { row ->
        id = row.study_protocol_identifier
        name = row.central_contact
        destinationConnection.execute("UPDATE DW_STUDY SET CENTRAL_CONTACT_TYPE = 'PERSONAL', CENTRAL_CONTACT_NAME = ? where internal_system_id = ? ", [name, id])
    }

sourceConnection.eachRow("""select gc.title, sc.study_protocol_identifier from study_contact sc
    inner join organizational_contact as oc on oc.identifier = sc.organizational_contact_identifier
    inner join dw_generic_contact as gc on gc.identifier = cast(oc.assigned_identifier as INTEGER)
    where sc.role_code = 'CENTRAL_CONTACT' and sc.organizational_contact_identifier is not null""") { row ->
        id = row.study_protocol_identifier
        title = row.title
        destinationConnection.execute("UPDATE DW_STUDY SET CENTRAL_CONTACT_TYPE = 'GENERIC', CENTRAL_CONTACT_NAME = ? where internal_system_id = ? ", [title, id])
    }

sourceConnection.eachRow("""select resp.name, sc.email, sc.telephone, sc.study_protocol_identifier from study_contact sc
    inner join clinical_research_staff crs on crs.identifier = sc.clinical_research_staff_identifier
    inner join organization as resp on resp.identifier = crs.organization_identifier
    where sc.role_code = 'RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR'""") { row ->
        id = row.study_protocol_identifier
        name = row.name
        email = row.email
        phone = row.telephone
        destinationConnection.execute("""UPDATE DW_STUDY SET RESPONSIBLE_PARTY_NAME = ?, SPONSOR_RESP_PARTY_EMAIL = ?,
            SPONSOR_RESP_PARTY_PHONE = ?, RESP_PARTY_TYPE = 'PI' where internal_system_id = ? """, [name, email, phone, id])
    }

sourceConnection.eachRow("""select org.name, resp.email, resp.telephone, p.first_name || ' ' || p.last_name as contact, ss.study_protocol_identifier from study_site ss
    inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
    inner join organization as org on org.identifier = ro.organization_identifier
    inner join study_site_contact as resp on resp.study_site_identifier = ss.identifier and resp.role_code = 'RESPONSIBLE_PARTY_SPONSOR_CONTACT'
    inner join organizational_contact as oc on oc.identifier = resp.organizational_contact_identifier and oc.person_identifier is not null
    join person as p on p.identifier = oc.person_identifier
    where ss.functional_code = 'RESPONSIBLE_PARTY_SPONSOR'""") { row ->
        id = row.study_protocol_identifier
        name = row.name
        email = row.email
        phone = row.telephone
        contact = row.contact
        destinationConnection.execute("""UPDATE DW_STUDY SET RESPONSIBLE_PARTY_NAME = ?, SPONSOR_RESP_PARTY_EMAIL = ?, SPONSOR_RESP_PARTY_PHONE = ?,
                    RESP_PARTY_TYPE = 'SPONSOR', RESPONSIBLE_PARTY_PERSONAL_CONTACT = ?  where internal_system_id = ? """, [name, email, phone, contact, id])
    }

sourceConnection.eachRow("""select org.name, resp.email, resp.telephone, gc.title as contact, ss.study_protocol_identifier from study_site ss
    inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
    inner join organization as org on org.identifier = ro.organization_identifier
    inner join study_site_contact as resp on resp.study_site_identifier = ss.identifier and resp.role_code = 'RESPONSIBLE_PARTY_SPONSOR_CONTACT'
    inner join organizational_contact as oc on oc.identifier = resp.organizational_contact_identifier and oc.person_identifier is null
    left outer join dw_generic_contact as gc on gc.identifier = cast(oc.assigned_identifier as INTEGER)
    where ss.functional_code = 'RESPONSIBLE_PARTY_SPONSOR'""") { row ->
        id = row.study_protocol_identifier
        name = row.name
        email = row.email
        phone = row.telephone
        contact = row.contact
        destinationConnection.execute("""UPDATE DW_STUDY SET RESPONSIBLE_PARTY_NAME = ?, SPONSOR_RESP_PARTY_EMAIL = ?, SPONSOR_RESP_PARTY_PHONE = ?,
                    RESP_PARTY_TYPE = 'SPONSOR', RESPONSIBLE_PARTY_GENERIC_CONTACT = ?  where internal_system_id = ? """, [name, email, phone, contact, id])
    }

sourceConnection.eachRow("""select oid.extension as nci_id, oid.study_protocol_id from study_otheridentifiers as oid
    inner join study_protocol as sp on sp.identifier = oid.study_protocol_id and sp.status_code = 'ACTIVE'
    where oid.root = '2.16.840.1.113883.3.26.4.3' and oid.extension is not null""") { row ->
        id = row.study_protocol_id
        nci_id = row.nci_id
        destinationConnection.execute("UPDATE DW_STUDY SET NCI_ID = ? where internal_system_id = ?", [nci_id, id])
    }
destinationConnection.execute("DELETE FROM DW_STUDY where NCI_ID is null")

sourceConnection.eachRow("""select ss.local_sp_indentifier as ctep_id, ss.study_protocol_identifier from study_site ss
    inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
    inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'Cancer Therapy Evaluation Program'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER'""") { row ->
        id = row.study_protocol_identifier
        ctep_id = row.ctep_id
        destinationConnection.execute("UPDATE DW_STUDY SET CTEP_ID = ? where internal_system_id = ?", [ctep_id, id])
    }

sourceConnection.eachRow("""select ss.local_sp_indentifier as dcp_id, ss.study_protocol_identifier from study_site ss
    inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
    inner join organization as org on org.identifier = ro.organization_identifier
        and (org.name = 'Division of Cancer Control and Population Sciences' or org.name = 'National Cancer Institute Division of Cancer Prevention')
    where ss.functional_code = 'IDENTIFIER_ASSIGNER'""") { row ->
        id = row.study_protocol_identifier
        dcp_id = row.dcp_id
        destinationConnection.execute("UPDATE DW_STUDY SET DCP_ID = ? where internal_system_id = ?", [dcp_id, id])
    }

sourceConnection.eachRow("""select ss.local_sp_indentifier as nct_id, ss.study_protocol_identifier from study_site ss
    inner join research_organization as ro on ro.identifier = ss.research_organization_identifier
    inner join organization as org on org.identifier = ro.organization_identifier and org.name = 'ClinicalTrials.gov'
    where ss.functional_code = 'IDENTIFIER_ASSIGNER'""") { row ->
        id = row.study_protocol_identifier
        nct_id = row.nct_id
        destinationConnection.execute("UPDATE DW_STUDY SET NCT_ID = ? where internal_system_id = ?", [nct_id, id])
    }

destinationConnection.execute("""UPDATE DW_STUDY SET LEAD_ORG_ORG_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = lead_org""")
destinationConnection.execute("""UPDATE DW_STUDY SET SPONSOR_ORG_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = sponsor""")
destinationConnection.execute("""UPDATE DW_STUDY SET SUBMITTER_ORGANIZATION_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = submitter_organization""")
destinationConnection.execute("""UPDATE DW_STUDY SET SUMMARY_4_FUNDING_SPONSOR_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = summary_4_funding_sponsor""")