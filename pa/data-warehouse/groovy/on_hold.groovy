import groovy.sql.Sql
def sql = """SELECT
				hold.onhold_reason_text,
				hold.onhold_reason_code,
				hold.identifier,
				hold.onhold_date,
				hold.offhold_date,
				hold.date_last_created, 
				hold.date_last_updated,
                nci_id.extension,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                     WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                     ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                     WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                     ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater      
                FROM STUDY_ONHOLD hold
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = hold.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                left outer join csm_user as creator on hold.user_last_created_id = creator.user_id   
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on hold.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def holds = destinationConnection.dataSet("STG_DW_STUDY_ON_HOLD_STATUS");

sourceConnection.eachRow(sql) { row ->
    holds.add(
    		reason_description: row.onhold_reason_text,
    		reason: row.onhold_reason_code,
    		on_hold_date: row.onhold_date,
    		off_hold_date: row.offhold_date,
    		date_created: row.date_last_created,
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier,
            nci_id: row.extension, 
            user_name_created: row.creator,
            user_name_last_updated: row.updater)
            }
            

	            