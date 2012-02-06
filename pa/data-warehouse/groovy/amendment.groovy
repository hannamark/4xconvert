import groovy.sql.Sql

def sql =
    """SELECT
            sp.amendment_date,
            sp.amendment_number, 
            sp.amendment_reason_code,
            sp.identifier as system_id,
			sp.submission_number,
			submitter.first_name || ' ' || submitter.last_name as submitter
            from STUDY_PROTOCOL sp
            left outer join registry_user as submitter on submitter.csm_user_id = sp.user_last_created_id
            where sp.amendment_date is not null"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
        properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
        properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def amendments = destinationConnection.dataSet("DW_STUDY_AMENDMENT")

sourceConnection.eachRow(sql) { row ->
    destinationConnection.withTransaction {
        try {
            amendments.add(
            amendment_date: row.amendment_date,
            number: row.amendment_number, 
            internal_system_id: row.system_id,
            submission_number: row.submission_number, 
            submitter_name: row.submitter)
        } catch (Exception e) {
            println "Error adding row : " + row + " - " + e.getMessage(); 
        }
    }
}

sourceConnection.eachRow("""select oid.extension as nci_id, oid.study_protocol_id from study_otheridentifiers as oid
    inner join study_protocol as sp on sp.identifier = oid.study_protocol_id and sp.status_code = 'ACTIVE'
    where oid.root = '2.16.840.1.113883.3.26.4.3' and oid.extension is not null""") { row ->
        id = row.study_protocol_id
        nci_id = row.nci_id
        destinationConnection.execute("UPDATE DW_STUDY_AMENDMENT SET NCI_ID = ? where internal_system_id = ?", [nci_id, id])
    }
destinationConnection.execute("DELETE FROM DW_STUDY_AMENDMENT where NCI_ID is null")

