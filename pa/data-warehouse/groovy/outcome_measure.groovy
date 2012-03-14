import groovy.sql.Sql

def sql = """select 
			out.description,
			out.name,
			out.timeframe,
			out.primary_indicator,
			out.safety_indicator,
			out.date_last_created,
			out.date_last_updated,
			CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                     WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                     ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                     WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                     ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater,   
			 nci_id.extension 
			 from study_outcome_measure out
             inner join study_otheridentifiers as nci_id 
             	on nci_id.study_protocol_id = out.study_protocol_identifier  
	           	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                left outer join csm_user as creator on out.user_last_created_id = creator.user_id   
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on out.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'], 
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def outcomes = destinationConnection.dataSet("STG_DW_STUDY_OUTCOME_MEASURE")

sourceConnection.eachRow(sql) { row ->
    outcomes.add(
    	description: row.description,
    	name: row.name,
    	timeframe: row.timeframe,
	    primary_indicator: row.primary_indicator,
	    safety_indicator: row.safety_indicator,
		date_created: row.date_last_Created,
		date_updated: row.date_last_updated,
		username_created: row.creator,
		username_updated: row.updater,
    	nci_id: row.extension)
}