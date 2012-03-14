import groovy.sql.Sql
def sql = """SELECT
				STG_DWs.status_date_range_low,
				STG_DWs.status_code,
				STG_DWs.date_last_created, 
				STG_DWs.date_last_updated,
                STG_DWs.identifier,
                STG_DWs.comment_text,
                nci_id.extension,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                     WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                     ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                     WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                     ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater      
                FROM DOCUMENT_WORKFLOW_STATUS STG_DWs
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = STG_DWs.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                left outer join csm_user as creator on STG_DWs.user_last_created_id = creator.user_id   
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on STG_DWs.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def milestones = destinationConnection.dataSet("STG_DW_STUDY_PROCESSING_STATUS");

sourceConnection.eachRow(sql) { row ->
    milestones.add(
    		comments: row.comment_text,
    		status: row.status_code,
    		date: row.status_date_range_low,
    		date_created: row.date_last_created,
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier,
            nci_id: row.extension, 
            user_name_created: row.creator,
            user_name_last_updated: row.updater)
            }
            
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Submitted'    
	where STATUS='SUBMITTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Amendment Submitted'    
	where STATUS='AMENDMENT_SUBMITTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Accepted'    
	where STATUS='ACCEPTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Rejected'    
	where STATUS='REJECTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Abstracted'    
	where STATUS='ABSTRACTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Verification Pending'    
	where STATUS='VERIFICATION_PENDING'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Abstraction Verified Response'    
	where STATUS='ABSTRACTION_VERIFIED_RESPONSE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='Abstraction Verified No Response'    
	where STATUS='ABSTRACTION_VERIFIED_NORESPONSE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_PROCESSING_STATUS SET STATUS='On-Hold'    
	where STATUS='ON_HOLD'""")            
            