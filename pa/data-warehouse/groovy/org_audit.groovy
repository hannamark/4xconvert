import groovy.sql.Sql

def sql = """select 
			 org.name,
			 org.id,
			 audit.username,
			 audit.createddate,
			 ctepid.assigned_identifier_extension as ctep_id 
			 from Auditlogrecord audit
			 join organization org on org.id = audit.entityid
			 left outer join identifiedorganization ctepid on ctepid.player_id = org.id and ctepid.assigned_identifier_root = '2.16.840.1.113883.3.26.6.2'
			 where audit.entityname = 'Organization'
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def audits = destinationConnection.dataSet("DW_ORGANIZATION_AUDIT")

sourceConnection.eachRow(sql) { row ->
    audits.add(
    	ctep_id: row.ctep_id,
    	name: row.name,
    	po_id: row.id,
    	date: row.createddate,
    	username: row.username
	)
}