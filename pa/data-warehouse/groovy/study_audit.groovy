import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def audit = destinationConnection.dataSet("DW_STUDY_AUDIT")

def spSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
        join study_protocol sp on sp.identifier = audit.entityid and audit.entityname = 'STUDY_PROTOCOL'
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
    """

def dwsSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join document_workflow_status dws on dws.identifier = audit.entityid and entityname = 'DOCUMENT_WORKFLOW_STATUS'
        join study_protocol sp on sp.identifier = dws.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
    """

def ssSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join study_site ss on ss.identifier = audit.entityid and entityname = 'STUDY_SITE'
        join study_protocol sp on sp.identifier = ss.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def docSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join document doc on doc.identifier = audit.entityid and entityname = 'DOCUMENT'
        join study_protocol sp on sp.identifier = doc.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def paSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join PLANNED_ACTIVITY pa on pa.identifier = audit.entityid and entityname = 'PLANNED_ACTIVITY'
        join study_protocol sp on sp.identifier = pa.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def holdSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_ONHOLD obj on obj.identifier = audit.entityid and entityname = 'STUDY_ONHOLD'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def indSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_INDLDE obj on obj.identifier = audit.entityid and entityname = 'STUDY_INDLDE'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'

      """

def inboxSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_INBOX obj on obj.identifier = audit.entityid and entityname = 'STUDY_INBOX'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def omSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_OUTCOME_MEASURE obj on obj.identifier = audit.entityid and entityname = 'STUDY_OUTCOME_MEASURE'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def recSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_RECRUITMENT_STATUS obj on obj.identifier = audit.entityid and entityname = 'STUDY_RECRUITMENT_STATUS'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def regSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_REGULATORY_AUTHORITY obj on obj.identifier = audit.entityid and entityname = 'STUDY_REGULATORY_AUTHORITY'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def armSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join ARM obj on obj.identifier = audit.entityid and entityname = 'ARM'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def groupSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STRATUM_GROUP obj on obj.identifier = audit.entityid and entityname = 'STRATUM_GROUP'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def objSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_OBJECTIVE obj on obj.identifier = audit.entityid and entityname = 'STUDY_OBJECTIVE'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'

      """

def conSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_CONTACT obj on obj.identifier = audit.entityid and entityname = 'STUDY_CONTACT'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def osSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_OVERALL_STATUS obj on obj.identifier = audit.entityid and entityname = 'STUDY_OVERALL_STATUS'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def resSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_RESOURCING obj on obj.identifier = audit.entityid and entityname = 'STUDY_RESOURCING'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

def disSql = """
    select 
    	nci_id.extension,
    	audit.createddate,
		audit.username,
		audit.entityname
    from auditlogrecord audit
    	join STUDY_DISEASE obj on obj.identifier = audit.entityid and entityname = 'STUDY_DISEASE'
        join study_protocol sp on sp.identifier = obj.study_protocol_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        	and nci_id.root = '2.16.840.1.113883.3.26.4.3'
      """

println "Protocol"
sourceConnection.eachRow(spSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "DWS"
sourceConnection.eachRow(dwsSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "SS"
sourceConnection.eachRow(ssSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Doc"
sourceConnection.eachRow(docSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "PA"
sourceConnection.eachRow(paSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Hold"
sourceConnection.eachRow(holdSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "IND"
sourceConnection.eachRow(indSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "inbox"
sourceConnection.eachRow(inboxSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "OM"
sourceConnection.eachRow(omSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Rec"
sourceConnection.eachRow(recSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Reg"
sourceConnection.eachRow(regSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Arm"
sourceConnection.eachRow(armSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Group"
sourceConnection.eachRow(groupSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Objective"
sourceConnection.eachRow(objSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Contact"
sourceConnection.eachRow(conSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Overall Status"
sourceConnection.eachRow(osSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Resourcing"
sourceConnection.eachRow(resSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

println "Disease"
sourceConnection.eachRow(disSql) { row ->
        audit.add(
        	nci_id: row.extension,
        	date: row.createddate,
        	username: row.username,
        	type: row.entityname
            )
    }

