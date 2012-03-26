import groovy.sql.Sql

def sql = """select 
			 add.streetaddressline,
			 add.deliveryaddressline,
			 add.postalcode,
			 add.cityormunicipality,
			 country.name as country_name,
			 ctepid.assigned_identifier_extension as ctep_id,
			 org.comments,
			 org.id,
			 org.status,
			 org.statusdate,
			 add.stateorprovince,
			 org.name as orgname,
			 e.value as email,
			 fax.value as faxnumber,	 
			 phone.value as phone,
			 tty.value as tty,
			 url.value as url,
			 fam.name as fam_name,
			 f_o_r.functionaltype,
			 crcount
			 from Organization org
			 left outer join address add on add.id = org.postal_address_id
			 left outer join identifiedorganization ctepid on ctepid.player_id = org.id and ctepid.assigned_identifier_root = '2.16.840.1.113883.3.26.6.2'
			 left outer join country on country.id = add.country_id
			 left outer join organization_email o_e on o_e.organization_id = org.id
			 left outer join email e on e.id = o_e.email_id
			 left outer join organization_fax o_f on o_f.organization_id = org.id
			 left outer join phonenumber fax on fax.id = o_f.fax_id
			 left outer join organization_phone o_ph on o_ph.organization_id = org.id
			 left outer join phonenumber phone on phone.id = o_ph.phone_id			 
			 left outer join organization_tty o_tty on o_tty.organization_id = org.id
			 left outer join phonenumber tty on tty.id = o_tty.tty_id
			 left outer join organization_url o_url on o_url.organization_id = org.id
			 left outer join url url on url.id = o_url.url_id
			 left outer join familyorganizationrelationship f_o_r on f_o_r.organization_id = org.id
			 left outer join family fam on fam.id = f_o_r.family_id
			 left outer join (select count(*) as crcount, target from organizationcr group by target) AS orgcr
				on orgcr.target = org.id
			"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def orgs = destinationConnection.dataSet("STG_DW_ORGANIZATION")

sourceConnection.eachRow(sql) { row ->
    orgs.add(
    	address_line_1: row.streetaddressline,
    	address_line_2: row.deliveryaddressline,
    	postal_code: row.postalcode,
    	city: row.cityormunicipality,
    	country: row.country_name,
    	ctep_id: row.ctep_id,
    	curator_comment: row.comments,
        name: orgname,
 		po_id: row.id,
 		status: row.status,
 		status_date: row.statusdate,
 		state_or_province: row.stateorprovince,
 		email: row.email,
 	 	fax: row.faxnumber,
 	 	phone: row.phone,
 	 	tty: row.tty,
 	 	internal_id: row.id,
 	 	FAMILY: row.fam_name,
 	 	ORG_TO_FAMILY_RELATIONSHIP: row.functionaltype,
 	 	CHANGE_REQUEST_COUNT: row.crcount
	)
}

destinationConnection.execute("UPDATE STG_DW_ORGANIZATION set CHANGE_REQUEST_COUNT = 0 where CHANGE_REQUEST_COUNT is null")



