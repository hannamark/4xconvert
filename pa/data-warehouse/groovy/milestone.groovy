import groovy.sql.Sql
def sql = """SELECT
				sm.milestone_date,
				sm.milestone_code,
				sm.date_last_created, 
				sm.date_last_updated,
                sm.identifier,
                sm.comment_text,
                nci_id.extension,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                     WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                     ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                     WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                     ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater      
                FROM STUDY_MILESTONE sm
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sm.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                left outer join csm_user as creator on sm.user_last_created_id = creator.user_id   
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on sm.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
                join study_protocol sp on sp.identifier = sm.study_protocol_identifier
                   and sp.status_code = 'ACTIVE'
                """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def milestones = destinationConnection.dataSet("STG_DW_STUDY_MILESTONE");

sourceConnection.eachRow(sql) { row ->
    milestones.add(
    		comments: row.comment_text,
    		name: row.milestone_code,
    		date: row.milestone_date,
    		date_created: row.date_last_created,
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier,
            nci_id: row.extension, 
            user_name_created: row.creator,
            user_name_last_updated: row.updater)
            }
            
            
   
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Received Date'    
	where NAME='SUBMISSION_RECEIVED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Acceptance Date'    
	where NAME='SUBMISSION_ACCEPTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Rejection Date'    
	where NAME='SUBMISSION_REJECTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative Processing Start Date'    
	where NAME='ADMINISTRATIVE_PROCESSING_START_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative Processing Completed Date'    
	where NAME='ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Ready for Administrative QC Date'    
	where NAME='ADMINISTRATIVE_READY_FOR_QC'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative QC Start Date'    
	where NAME='ADMINISTRATIVE_QC_START'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative QC Completed Date'    
	where NAME='ADMINISTRATIVE_QC_COMPLETE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific Processing Start Date'    
	where NAME='SCIENTIFIC_PROCESSING_START_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific Processing Completed Date'    
	where NAME='SCIENTIFIC_PROCESSING_COMPLETED_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Ready for Scientific QC Date'    
	where NAME='SCIENTIFIC_READY_FOR_QC'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific QC Start Date'    
	where NAME='SCIENTIFIC_QC_START'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific QC Completed Date'    
	where NAME='SCIENTIFIC_QC_COMPLETE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Ready for Trial Summary Report Date'    
	where NAME='READY_FOR_TSR'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Trial Summary Report Sent Date'    
	where NAME='TRIAL_SUMMARY_SENT'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submitter Trial Summary Report Feedback Date'    
	where NAME='TRIAL_SUMMARY_FEEDBACK'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Initial Abstraction Verified Date'    
	where NAME='INITIAL_ABSTRACTION_VERIFY'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='On-going Abstraction Verified Date'    
	where NAME='ONGOING_ABSTRACTION_VERIFICATION'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Late Rejection Date'    
	where NAME='LATE_REJECTION_DATE'""")
	
	
	            