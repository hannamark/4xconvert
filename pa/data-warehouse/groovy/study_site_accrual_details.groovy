import groovy.sql.Sql
def sql = """
    SELECT 
       co.name AS country,
       ssub.date_last_created AS date_last_created,
       ssub.date_last_created AS date_last_updated,
       '3.8.1 data element' AS deletion_reason,
       pat.ethnic_code AS ethnicity,
       pat.sex_code AS gender,
       icd9.disease_code AS icd9_disease_code,
       icd9.name AS icd9_disease_term,
       ssub.payment_method_code AS payment_method,
       pat.race_code AS race,
       pact.registration_date AS registration_date,
       ssub.registration_group_id AS registration_group,
       sdc.disease_code AS sdc_disease_code,
       sdc.ctep_term AS sdc_disease_term,
       ssub.study_site_identifier AS site_org_id,
       ssub.status_code AS status,
       ssub.assigned_identifier AS study_subject_id,
       ssub.user_last_created_id,
       ssub.user_last_updated_id
    FROM study_subject ssub
    JOIN performed_activity pact ON (ssub.identifier = pact.study_subject_identifier)
    JOIN patient pat ON (ssub.patient_identifier = pat.identifier)
    JOIN country co ON (pat.country_identifier = co.identifier)
    LEFT OUTER JOIN sdc_disease sdc ON (ssub.disease_identifier = sdc.identifier)
    LEFT OUTER JOIN icd9_disease icd9 ON (ssub.icd9disease_identifier = icd9.identifier)
"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def ssad = destinationConnection.dataSet("stg_dw_study_site_accrual_details");

sourceConnection.eachRow(sql) { row ->
    ssad.add(
        country : row.country,
        date_last_created : row.date_last_created,
        date_last_updated : row.date_last_updated,
        deletion_reason : row.deletion_reason,
        ethnicity : row.ethnicity,
        gender : row.gender,
        icd9_disease_code : row.icd9_disease_code,
        icd9_disease_term : row.icd9_disease_term,
        payment_method : row.payment_method,
        race : row.race,
        registration_date : row.registration_date,
        registration_group : row.registration_group,
        sdc_disease_code : row.sdc_disease_code,
        sdc_disease_term : row.sdc_disease_term,
        site_org_id : row.site_org_id,
        status : row.status,
        study_subject_id : row.study_subject_id,
        user_last_created_id : row.user_last_created_id,
        user_last_updated_id : row.user_last_updated_id
    )};
    
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET user_name_last_created = us.name 
                                 FROM stg_dw_user us where ssad.user_last_created_id = us.csm_user_id""");
   
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET user_name_last_updated = us.name 
                                 FROM stg_dw_user us where ssad.user_last_updated_id = us.csm_user_id""");
   
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET nci_id = ps.nci_id, 
                                     org_name = ps.org_name, 
                                     org_org_family = ps.org_org_family
                                 FROM dw_study_participating_site ps where ssad.site_org_id = ps.internal_system_id""");