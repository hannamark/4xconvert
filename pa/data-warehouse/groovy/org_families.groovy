import groovy.sql.Sql

def sql = """select f.name as family_name, o.name as org_name from organization o
                join familyorganizationrelationship fo on fo.organization_id=o.id
                join family f on f.id=fo.family_id where fo.enddate is null and f.statuscode='ACTIVE' order by f.name asc"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def org_families = destinationConnection.dataSet("DW_FAMILY_ORGANIZATION");
sourceConnection.eachRow(sql) { row ->
    org_families.add(family_name: row.family_name, organization_name: row.org_name)
}