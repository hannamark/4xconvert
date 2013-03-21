import groovy.sql.Sql

def sql = """select 
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 per.birthdate,
			 add.cityormunicipality,
			 country.name as country_name,
			 ctepid.assigned_identifier_extension as ctep_id,
			 c.value as comments,
			 crs.id,
			 per.prefix,
			 per.status,
			 per.statusdate,
			 per.sex,
			 per.suffix,
			 add.stateorprovince,
			 e.value as email,
			 fax.value as faxnumber,	 
			 phone.value as phone,
			 tty.value as tty,
			 url.value as url,
			 org.name as organization
			 from Person per
			 join clinicalresearchstaff crs on crs.person_id = per.id
			 join organization org on crs.organization_id = org.id
			 join address add on add.id = per.postal_address_id
			 left outer join identifiedperson ctepid on ctepid.player_id = per.id and ctepid.assigned_identifier_root = 'Cancer Therapy Evaluation Program Person Identifier'
			 left outer join country on country.id = add.country_id
			 left outer join person_email p_e on p_e.person_id = per.id
			 left outer join email e on e.id = p_e.email_id
             left outer join person_comment p_c on p_c.person_id = per.id
             left outer join comment c on c.id = p_c.comment_id
			 left outer join person_fax p_f on p_f.person_id = per.id
			 left outer join phonenumber fax on fax.id = p_f.fax_id
			 left outer join person_phone p_ph on p_ph.person_id = per.id
			 left outer join phonenumber phone on phone.id = p_ph.phone_id			 
			 left outer join person_tty p_tty on p_tty.person_id = per.id
			 left outer join phonenumber tty on tty.id = p_tty.tty_id
			 left outer join person_url p_url on p_url.person_id = per.id
			 left outer join url url on url.id = p_url.url_id
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def persons = destinationConnection.dataSet("STG_DW_PERSON_ROLE")

sourceConnection.eachRow(sql) { row ->
    persons.add(
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	birthdate: row.birthdate,
    	city: row.cityormunicipality,
    	country: row.country_name,
    	ctep_id: row.ctep_id,
    	curator_comment: row.comments,
 		po_id: row.id,
 		prefix: row.prefix,
 		status: row.status,
 		status_date: row.statusdate,
 		sex_code: row.sex,
 		state_or_province: row.stateorprovince,
 		suffix: row.suffix,
 		email: row.email,
 	 	fax: row.faxnumber,
 	 	phone: row.phone,
 	 	tty: row.tty,
 	 	role_name: "Clinical Research Staff",
 	 	organization_name: row.organization
	)
}

sql = """select 
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 per.birthdate,
			 add.cityormunicipality,
			 country.name as country_name,
			 ctepid.assigned_identifier_extension as ctep_id,
			 c.value as comments,
			 hcp.id,
			 per.prefix,
			 per.status,
			 per.statusdate,
			 per.sex,
			 per.suffix,
			 add.stateorprovince,
			 e.value as email,
			 fax.value as faxnumber,	 
			 phone.value as phone,
			 tty.value as tty,
			 url.value as url,
			 org.name as organization
			 from Person per
			 join healthcareprovider hcp on hcp.person_id = per.id
			 join organization org on hcp.organization_id = org.id
			 join address add on add.id = per.postal_address_id
			 left outer join identifiedperson ctepid on ctepid.player_id = per.id and ctepid.assigned_identifier_root = 'Cancer Therapy Evaluation Program Person Identifier'
			 left outer join country on country.id = add.country_id
			 left outer join person_email p_e on p_e.person_id = per.id
			 left outer join email e on e.id = p_e.email_id
             left outer join person_comment p_c on p_c.person_id = per.id
             left outer join comment c on c.id = p_c.comment_id
			 left outer join person_fax p_f on p_f.person_id = per.id
			 left outer join phonenumber fax on fax.id = p_f.fax_id
			 left outer join person_phone p_ph on p_ph.person_id = per.id
			 left outer join phonenumber phone on phone.id = p_ph.phone_id			 
			 left outer join person_tty p_tty on p_tty.person_id = per.id
			 left outer join phonenumber tty on tty.id = p_tty.tty_id
			 left outer join person_url p_url on p_url.person_id = per.id
			 left outer join url url on url.id = p_url.url_id
			"""

sourceConnection.eachRow(sql) { row ->
    persons.add(
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	birthdate: row.birthdate,
    	city: row.cityormunicipality,
    	country: row.country_name,
    	ctep_id: row.ctep_id,
    	curator_comment: row.comments,
 		po_id: row.id,
 		prefix: row.prefix,
 		status: row.status,
 		status_date: row.statusdate,
 		sex_code: row.sex,
 		state_or_province: row.stateorprovince,
 		suffix: row.suffix,
 		email: row.email,
 	 	fax: row.faxnumber,
 	 	phone: row.phone,
 	 	tty: row.tty,
 	 	role_name: "Healthcare Provider",
 	 	organization_name: row.organization
	)
}

    