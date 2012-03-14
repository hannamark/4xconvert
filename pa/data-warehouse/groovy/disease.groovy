import groovy.sql.Sql
def sql = """SELECT
                CASE WHEN sd.ctgovxml_indicator THEN 'YES'
                     ELSE 'NO'
                END as ct_indicator,
                sd.date_last_created, sd.date_last_updated, disease.disease_code, disease.preferred_name, disease.menu_display_name,
                sd.identifier,
                CASE WHEN sd.lead_disease_indicator THEN 'YES'
                     ELSE 'NO'
                END as lead_indicator,
                nci_id.extension, disease.nt_term_identifier,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                    WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                    ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                    WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                    ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater
                FROM STUDY_DISEASE sd
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sd.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                left outer join pdq_disease as disease on disease.identifier = sd.disease_identifier
                left outer join study_protocol as sp on sp.identifier = sd.study_protocol_identifier
                left outer join csm_user as creator on sd.user_last_created_id = creator.user_id
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on sd.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
                where sp.status_code = 'ACTIVE'"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def diseases = destinationConnection.dataSet("STG_DW_STUDY_DISEASE");
sourceConnection.eachRow(sql) { row ->
    diseases.add(ct_gov_xml_indicator: row.ct_indicator, date_last_created: row.date_last_created, date_last_updated: row.date_last_updated,
            disease_code: row.disease_code, disease_preferred_name: row.preferred_name, disease_menu_display_name: row.menu_display_name,
            internal_system_id: row.identifier, lead_disease_indicator: row.lead_indicator, nci_id: row.extension, 
            nci_thesaurus_concept_id: row.nt_term_identifier, user_last_created: row.creator, user_last_updated: row.updater)
}