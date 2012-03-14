import groovy.sql.Sql

def sql = """select 
			 per.firstname,
			 per.lastname,	
			 per.id,
			 audit.username,
			 audit.createddate,
			 ctepid.assigned_identifier_extension as ctep_id
			 from Auditlogrecord audit
			 join person per on per.id = audit.entityid
			 left outer join identifiedperson ctepid on ctepid.player_id = per.id and ctepid.assigned_identifier_root = 'Cancer Therapy Evaluation Program Person Identifier'
			 where audit.entityname = 'Person'
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def audits = destinationConnection.dataSet("STG_DW_PERSON_AUDIT")

sourceConnection.eachRow(sql) { row ->
    audits.add(
    	ctep_id: row.ctep_id,
    	name: row.firstname + " " + row.lastname,
    	po_id: row.id,
    	date: row.createddate,
    	username: row.username
	)
}