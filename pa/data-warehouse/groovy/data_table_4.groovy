import groovy.sql.Sql
import  org.apache.commons.lang.StringUtils

def sql = """SELECT sp.identifier spid, soi.extension nci_id, sos_curr.status_code, sp.study_protocol_type, '3.9 Data Element' study_subtype_code,
                    sr.type_code study_source, org.name specific_funding_source,
                    (select count(*) from study_anatomic_site where sp.identifier = study_protocol_identifier) asite_count,
                    anat.code AS asite, pi.last_name, pi.first_name, sp.program_code_text, sp.start_date, sos_close.status_date close_date,
                    sp.phase_code, sp.primary_purpose_code, sp.official_title, sp.min_target_accrual_num, 
                    sp.identifier sp_id, lead_org.assigned_identifier::integer lead_org_po_id, sp.proprietary_trial_indicator
             FROM study_protocol sp
             JOIN study_overall_status sos_curr ON (sp.identifier = sos_curr.study_protocol_identifier 
                         AND sos_curr.identifier IN (SELECT max(sos2.identifier)
                         FROM study_overall_status sos2 WHERE sos_curr.study_protocol_identifier = sos2.study_protocol_identifier))
             JOIN document_workflow_status dwf_curr ON (sp.identifier = dwf_curr.study_protocol_identifier 
                         AND dwf_curr.identifier IN (SELECT max(dwf2.identifier)
                         FROM document_workflow_status dwf2 WHERE dwf_curr.study_protocol_identifier = dwf2.study_protocol_identifier))
             JOIN study_otheridentifiers soi ON (sp.identifier = soi.study_protocol_id)
             JOIN study_resourcing sr ON (sp.identifier = sr.study_protocol_identifier AND sr.summ_4_rept_indicator = TRUE)
             JOIN organization org ON (org.identifier = sr.organization_identifier::integer)
             LEFT JOIN study_anatomic_site sas1 ON (sp.identifier = sas1.study_protocol_identifier 
                         AND sas1.anatomic_sites_identifier IN (SELECT MIN(anatomic_sites_identifier) 
                         FROM study_anatomic_site sas2 WHERE sas2.study_protocol_identifier = sp.identifier))
             LEFT JOIN anatomic_sites anat ON (sas1.anatomic_sites_identifier = anat.identifier)
             LEFT JOIN study_contact AS sc ON (sc.study_protocol_identifier = sp.identifier AND sc.role_code = 'STUDY_PRINCIPAL_INVESTIGATOR')
             LEFT JOIN clinical_research_staff AS crs ON (crs.identifier = sc.clinical_research_staff_identifier)
             LEFT JOIN person AS pi ON (pi.identifier = crs.person_identifier)
             LEFT JOIN study_overall_status sos_active ON (sp.identifier = sos_active.study_protocol_identifier 
                         AND sos_active.identifier IN (SELECT max(sos2.identifier)
                         FROM study_overall_status sos2 WHERE sos_active.study_protocol_identifier = sos2.study_protocol_identifier
                         AND sos2.status_code = 'ACTIVE'))
             LEFT JOIN study_overall_status sos_close ON (sp.identifier = sos_close.study_protocol_identifier 
                         AND sos_close.identifier IN (SELECT max(sos3.identifier)
                         FROM study_overall_status sos3 WHERE sos_close.study_protocol_identifier = sos3.study_protocol_identifier
                         AND sos3.status_code IN ('CLOSED_TO_ACCRUAL','CLOSED_TO_ACCRUAL_AND_INTERVENTION') 
                         AND (sos3.identifier > sos_active.identifier OR sos_active.identifier IS NULL)))
             LEFT JOIN study_site lo on lo.study_protocol_identifier = sp.identifier and lo.functional_code = 'LEAD_ORGANIZATION'
             LEFT JOIN research_organization ro_lead_org on ro_lead_org.identifier = lo.research_organization_identifier
             LEFT JOIN organization lead_org on lead_org.identifier = ro_lead_org.organization_identifier
             WHERE soi.root = '2.16.840.1.113883.3.26.4.3'
               AND sp.status_code = 'ACTIVE'
               AND sos_curr.status_code NOT IN ('WITHDRAWN','ADMINISTRATIVELY_COMPLETE','COMPLETE')
               AND dwf_curr.status_code != 'REJECTED'
         """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def data_4 = destinationConnection.dataSet("stg_dw_data_table_4");

sourceConnection.eachRow(sql) { row ->
    data_4.add(
                nci_id: row.nci_id,
                clinical_research_cat: (row.study_protocol_type == "InterventionalStudyProtocol" ? "INT" 
                                         : (row.study_subtype_code == "ANCILLARY_CORRELATIVE" ? "ANC/COR" : "OBS")),
                study_source: row.study_source,
                specific_funding_source: row.specific_funding_source,
                is_multisite : "N",
                site: row.asite_count > 1 ? "multiple" : row.asite,
                pi_last_name: row.last_name,
                pi_first_name: row.first_name,
                prog_code: row.program_code_text,
                open_date: row.start_date,
                close_date: row.close_date,
                phase: row.phase_code,
                primary_purpose: row.primary_purpose_code,
                official_title: StringUtils.substring(row.official_title, 0, 600),
                entire_study: row.min_target_accrual_num,
                sp_id: row.sp_id,
                lead_org_po_id: row.lead_org_po_id,
                is_industrial: row.proprietary_trial_indicator
            )};

destinationConnection.execute("""UPDATE stg_dw_data_table_4 dt4
                                 SET nct_id = st.nct_id, ctep_id = st.ctep_id, lead_org_id = st.lead_org_id 
                                 FROM stg_dw_study st 
                                   WHERE dt4.nci_id = st.nci_id""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 dt4
                                 SET grant_number = gr.serial_number 
                                 FROM stg_dw_study_grant gr 
                                   WHERE dt4.nci_id = gr.nci_id AND gr.funding_mechanism_code = 'P30'""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'N' WHERE study_source = 'NATIONAL'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'E' WHERE study_source = 'EXTERNALLY_PEER_REVIEWED'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'I' WHERE study_source = 'INSTITUTIONAL'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'D' WHERE study_source = 'INDUSTRIAL'""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET is_multisite = 'Y' WHERE nci_id IN
                                   (SELECT nci_id FROM stg_dw_study_participating_site GROUP BY nci_id HAVING count(*) > 1)""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Tre' WHERE primary_purpose = 'TREATMENT'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Pre' WHERE primary_purpose = 'PREVENTION'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Sup' WHERE primary_purpose = 'SUPPORTIVE_CARE'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Scr' WHERE primary_purpose = 'SCREENING'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Dia' WHERE primary_purpose = 'DIAGNOSTIC'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Hsr' WHERE primary_purpose = 'HEALTH_SERVICES_RESEARCH'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Bas' WHERE primary_purpose = 'BASIC_SCIENCE'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Oth' WHERE primary_purpose = 'OTHER'""");
