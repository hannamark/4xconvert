import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def orgs = destinationConnection.dataSet("STG_DW_ORGANIZATION_ROLE")

def sql = """select 
                         org.name,
                         org.id as organization_po_id,
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 add.cityormunicipality,
			 country.name as country_name,
			 ctepid.extension as ctep_id,
			 sr.id as role_po_id,
			 sr.status,
			 sr.statusdate,
			 add.stateorprovince,
			 e.value as email,
			 fax.value as faxnumber,	 
			 phone.value as phone,
			 tty.value as tty,
			 url.value as url
			 from Organization org
			 inner join healthcarefacility SR on SR.player_id = org.id
			 left outer join hcf_otheridentifier ctepid on ctepid.hcf_id = SR.id 
			 	and ctepid.root = '2.16.840.1.113883.3.26.6.2'
			 left outer join hcf_address SR_add on SR_add.hcf_id = SR.id
			 left outer join address add on add.id = SR_add.address_id
			 left outer join country on country.id = add.country_id
			 left outer join hcf_email sr_e on sr_e.hcf_id = sr.id
			 left outer join email e on e.id = sr_e.email_id
			 left outer join hcf_fax sr_f on sr_f.hcf_id = sr.id
			 left outer join phonenumber fax on fax.id = sr_f.fax_id
			 left outer join hcf_phone sr_ph on sr_ph.hcf_id = sr.id
			 left outer join phonenumber phone on phone.id = sr_ph.phone_id			 
			 left outer join hcf_tty sr_tty on sr_tty.hcf_id = sr.id
			 left outer join phonenumber tty on tty.id = sr_tty.tty_id
			 left outer join hcf_url sr_url on sr_url.hcf_id = sr.id
			 left outer join url url on url.id = sr_url.url_id
			"""

sourceConnection.eachRow(sql) { row ->
    orgs.add(
        name: row.name,
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	city: row.cityormunicipality,
    	country: row.country_name,
    	ctep_id: row.ctep_id,
        organization_po_id: row.organization_po_id,
        role_po_id: row.role_po_id,
 		status: row.status,
 		status_date: row.statusdate,
 		state_or_province: row.stateorprovince,
 		email: row.email,
 	 	fax: row.faxnumber,
 	 	phone: row.phone,
 	 	tty: row.tty,
 	 	ROLE_NAME: "Healthcare Facility"
	)
}

sql = """select 
                         org.name,
                         org.id as organization_po_id,
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 add.cityormunicipality,
			 country.name as country_name,
			 ctepid.extension as ctep_id,
			 sr.id as role_po_id,
			 sr.status,
			 sr.statusdate,
			 add.stateorprovince,
			 e.value as email,
			 fax.value as faxnumber,	 
			 phone.value as phone,
			 tty.value as tty,
			 url.value as url
			 from Organization org
			 inner join researchorganization SR on SR.player_id = org.id
			 left outer join ro_otheridentifier ctepid on ctepid.ro_id = SR.id 
			 	and ctepid.root = '2.16.840.1.113883.3.26.6.2'
			 join ro_address SR_add on SR_add.ro_id = SR.id
			 join address add on add.id = SR_add.address_id
			 left outer join country on country.id = add.country_id
			 left outer join ro_email sr_e on sr_e.ro_id = sr.id
			 left outer join email e on e.id = sr_e.email_id
			 left outer join ro_fax sr_f on sr_f.ro_id = sr.id
			 left outer join phonenumber fax on fax.id = sr_f.fax_id
			 left outer join ro_phone sr_ph on sr_ph.ro_id = sr.id
			 left outer join phonenumber phone on phone.id = sr_ph.phone_id			 
			 left outer join ro_tty sr_tty on sr_tty.ro_id = sr.id
			 left outer join phonenumber tty on tty.id = sr_tty.tty_id
			 left outer join ro_url sr_url on sr_url.ro_id = sr.id
			 left outer join url url on url.id = sr_url.url_id
			"""

sourceConnection.eachRow(sql) { row ->
    orgs.add(
        name: row.name,
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	city: row.cityormunicipality,
    	country: row.country_name,
    	ctep_id: row.ctep_id,
        organization_po_id: row.organization_po_id,
        role_po_id: row.role_po_id,
 		status: row.status,
 		status_date: row.statusdate,
 		state_or_province: row.stateorprovince,
 		email: row.email,
 	 	fax: row.faxnumber,
 	 	phone: row.phone,
 	 	tty: row.tty,
 	 	ROLE_NAME: "Research Organization"
	)
}
