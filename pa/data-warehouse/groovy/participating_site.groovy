import groovy.sql.Sql

def sql = """
    select contact.email, p.first_name || ' ' || p.middle_name || ' ' || p.last_name as contact_name, ps.identifier, org.name as org_name,
         org.status_code as org_status, ssas.status_code, ssas.status_date
    from study_site ps
        left outer join study_protocol as sp on sp.identifier = ps.study_protocol_identifier
        left outer join study_site_contact as contact on contact.study_site_identifier = ps.identifier and contact.role_code = 'PRIMARY_CONTACT'
        left join healthcare_provider as hcp on hcp.identifier = contact.healthcare_provider_identifier
        left join person as p on p.identifier = hcp.person_identifier
        left outer join healthcare_facility as hcf on hcf.identifier = ps.healthcare_facility_identifier
        left outer join organization as org on org.identifier = hcf.organization_identifier
        left outer join study_site_accrual_status as ssas on ssas.study_site_identifier = ps.identifier
            and ssas.identifier = (select max(identifier) from study_site_accrual_status where study_site_identifier = ps.identifier)
    where ps.functional_code = 'TREATING_SITE' and sp.status_code = 'ACTIVE'"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def participating_sites = destinationConnection.dataSet("DW_STUDY_PARTICIPATING_SITE")

sourceConnection.eachRow(sql) { row ->
        participating_sites.add(contact_email: row.email, contact_name: row.contact_name, internal_system_id: row.identifier,
            org_name: row.org_name, recruitment_status: row.status_code, recruitment_status_date: row.status_date)
    }

sourceConnection.eachRow("""select oid.extension, ps.identifier from study_site as ps
    inner join study_protocol as sp on sp.identifier = ps.study_protocol_identifier and sp.status_code = 'ACTIVE'
    inner join study_otheridentifiers as oid on oid.study_protocol_id = sp.identifier and oid.root = '2.16.840.1.113883.3.26.4.3'
    where ps.functional_code = 'TREATING_SITE'""") { row ->
        nci_id = row.extension
        id = row.identifier
        destinationConnection.execute("UPDATE DW_STUDY_PARTICIPATING_SITE SET NCI_ID = ? where internal_system_id = ?", [nci_id, id])
    }



sourceConnection.eachRow("""select generic_contact.email, ps.identifier, cast(oc.assigned_identifier as INTEGER)
    from study_site as ps
        left outer join study_site_contact as generic_contact on generic_contact.study_site_identifier = ps.identifier and generic_contact.role_code = 'PRIMARY_CONTACT'
            and generic_contact.organizational_contact_identifier is not null
        inner join organizational_contact as oc on oc.identifier = generic_contact.organizational_contact_identifier and oc.person_identifier is null""") { row ->
            email = row.email
            id = row.identifier
            def titleRow = destinationConnection.firstRow("SELECT gc.title from DW_GENERIC_CONTACT as gc where gc.identifier = ? ", [row.assigned_identifier])
            if (titleRow != null) {
	            title = titleRow.title
	        }
            statement = "UPDATE DW_STUDY_PARTICIPATING_SITE SET CONTACT_EMAIL = ? , GENERIC_CONTACT = ? where internal_system_id = ?"
            destinationConnection.execute(statement, [email, title, id])
        }

destinationConnection.execute("""UPDATE DW_STUDY_PARTICIPATING_SITE SET ORG_ORG_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = org_name""");
        