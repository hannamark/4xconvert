import groovy.sql.Sql

def poSourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def paSourceConnection =    Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])    
    
def roles = destinationConnection.dataSet("STG_DW_PERSON_ROLE")

def hcpSql = """select person_id from HealthcareProvider where status in ('ACTIVE', 'PENDING')"""
def hcps = [];

poSourceConnection.eachRow(hcpSql) { row ->
        hcps << row.person_id;
}

hcps.each {row ->
	roles.add(paSourceConnection.firstRow("select * from STG_DW_PERSON where po_id=?", [row]))
}

destinationConnection.execute("UPDATE STG_DW_PERSON_ROLE set ROLE_NAME = 'Healthcare Provider' where Role_name is null")

    
def crsSql = """select person_id from ClinicalResearchStaff where status in ('ACTIVE', 'PENDING')"""
def crss = [];

poSourceConnection.eachRow(crsSql) { row ->
        crss << row.person_id;
}

crss.each {row ->
	roles.add(paSourceConnection.firstRow("select * from STG_DW_PERSON where po_id=?", [row]))
}

destinationConnection.execute("UPDATE STG_DW_PERSON_ROLE set ROLE_NAME = 'Clinical Research Staff' where Role_name is null")

    