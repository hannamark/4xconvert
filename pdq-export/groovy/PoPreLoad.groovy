import groovy.sql.Sql


class PoPreLoad {

PoPreLoad() {
}

Sql getPoSourceConnection() {
    def props = new Properties()
    
    new File("resolved.build.properties").withInputStream {
      stream -> props.load(stream)
    }
    
    def poSourceConnection = Sql.newInstance(props['po.jdbc.url'], props['po.db.username'],
         props['po.db.password'], props['po.jdbc.driver'])
}

Map getOrgsMap() {   
    def orgSQL = """select 
             add.streetaddressline,
             add.deliveryaddressline,
             add.postalcode,
             add.cityormunicipality,
             country.name as country_name,
             ctepid.assigned_identifier_extension as ctep_id,
             org.id as org_poid,
             org.status,
             org.statusdate,
             org.name,
             add.stateorprovince,
             e.value as email,
             fax.value as faxnumber,     
             phone.value as phone,
             tty.value as tty
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
             """
              
             println "preloading orgs..."
                                 
             def result = [:]       
             getPoSourceConnection().eachRow(orgSQL) { row ->
                 result.put(row.org_poid, row.toRowResult())
             }
             return result         
}

Map getHcfsMap() { 
def hcfSQL = """
	select 
		 add.streetaddressline,
		 add.deliveryaddressline,
		 add.postalcode,
		 add.cityormunicipality,
		 country.name as country_name,
		 ctepid.extension as ctep_id,
		 sr.id,
		 sr.status,
		 sr.statusdate,
		 add.stateorprovince,
		 e.value as email,
		 fax.value as faxnumber,	 
		 phone.value as phone
		 from Organization org
		 inner join healthcarefacility SR on SR.player_id = org.id
		 left outer join hcf_otheridentifier ctepid on ctepid.hcf_id = SR.id 
		 	and ctepid.root = '2.16.840.1.113883.3.26.6.2'
		 left outer join address add on add.id = org.postal_address_id
		 left outer join country on country.id = add.country_id
		 left outer join organization_email o_e on o_e.organization_id = org.id
		 left outer join email e on e.id = o_e.email_id
		 left outer join organization_fax o_f on o_f.organization_id = org.id
		 left outer join phonenumber fax on fax.id = o_f.fax_id
		 left outer join organization_phone o_ph on o_ph.organization_id = org.id
		 left outer join phonenumber phone on phone.id = o_ph.phone_id			 
		 left outer join organization_tty o_tty on o_tty.organization_id = org.id
		 left outer join phonenumber tty on tty.id = o_tty.tty_id
		"""
		

	println "preloading hcfs..."
						
	def result = [:]

	getPoSourceConnection().eachRow(hcfSQL) { row ->
    	result.put(row.id, row.toRowResult())
	}

	return result
}

Map getRosMap() { 

    def roSQL = """
        select 
        add.streetaddressline,
        add.deliveryaddressline,
        add.postalcode,
        add.cityormunicipality,
        country.name as country_name,
        ctepid.extension as ctep_id,
        sr.id,
        sr.status,
        sr.statusdate,
        add.stateorprovince,
        e.value as email,
        fax.value as faxnumber,	 
        phone.value as phone,
        org.name as orgName,
        org.id as org_poid
        from Organization org
        inner join researchorganization SR on SR.player_id = org.id
        left outer join ro_otheridentifier ctepid on ctepid.ro_id = SR.id 
        and ctepid.root = '2.16.840.1.113883.3.26.6.2'
        left outer join address add on add.id = org.postal_address_id
        left outer join country on country.id = add.country_id
        left outer join organization_email o_e on o_e.organization_id = org.id
        left outer join email e on e.id = o_e.email_id
        left outer join organization_fax o_f on o_f.organization_id = org.id
        left outer join phonenumber fax on fax.id = o_f.fax_id
        left outer join organization_phone o_ph on o_ph.organization_id = org.id
        left outer join phonenumber phone on phone.id = o_ph.phone_id			 
        left outer join organization_tty o_tty on o_tty.organization_id = org.id
        left outer join phonenumber tty on tty.id = o_tty.tty_id
        """
                  
        println "preloading ros..." 
        def roResult = [:]                              
        getPoSourceConnection().eachRow(roSQL) { row ->
          roResult.put(row.id, row.toRowResult())
        }
        return roResult
    }

Map getCrsMap() { 
    
def crsSQL = """
    select 
    add.streetaddressline,
    add.deliveryaddressline,
    add.postalcode,
    add.cityormunicipality,
    country.name as country_name,
    ctepid.assigned_identifier_extension as ctep_id,
    sr.id,
    sr.status,
    sr.statusdate,
    add.stateorprovince,
    e.value as email,
    fax.value as faxnumber,	 
    phone.value as phone,
    person.firstname,
    person.middlename,
    person.lastname,
    person.id as person_poid
    from Person person
    inner join clinicalresearchstaff SR on SR.person_id = person.id
    left outer join identifiedperson ctepid on ctepid.player_id = person.id 
    and ctepid.assigned_identifier_root = '2.16.840.1.113883.3.26.6.1'
    left outer join address add on add.id = person.postal_address_id
    left outer join country on country.id = add.country_id
    left outer join person_email p_e on p_e.person_id = person.id
    left outer join email e on e.id = p_e.email_id
    left outer join person_fax p_f on p_f.person_id = person.id
    left outer join phonenumber fax on fax.id = p_f.fax_id
    left outer join person_phone p_ph on p_ph.person_id = person.id
    left outer join phonenumber phone on phone.id = p_ph.phone_id			 
    left outer join person_tty p_tty on p_tty.person_id = person.id
    left outer join phonenumber tty on tty.id = p_tty.tty_id
    """
    
    println "preloading crs..." 
    def result = [:]                              
    getPoSourceConnection().eachRow(crsSQL) { row ->
    result.put(row.id, row.toRowResult())
    }
    return result
}
}