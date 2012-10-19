/**
* @author Monish
*
*/
public class Queries {
    
    public static def orgsWithCRSQL = """select distinct target from organizationcr where processed = false"""
    
    public static def crIDsSQL = """select id from organizationcr where processed = false and target = """
    
    public static def currentOrgDetailsSQL = """
        select  
            o.id as poid,
            o.name as name,
            a.cityormunicipality as city,
            a.deliveryaddressline as suite,
            a.postalcode as zip,
            a.stateorprovince as state,
            a.streetaddressline as street,
            c.name as country,
            e.value as email
        from 
            organization o,
            address a,
            organization_email oe,
            email e,
            country c 
        where
            o.postal_address_id = a.id and
            o.id = oe.organization_id and
            o.status <> 'NULLIFIED' and
            oe.email_id = e.id and
            a.country_id = c.id and
            o.id =
    """
    
    
    public static def crOrgDetailsSQL = """
        select  
            ocr.id as crid,
            ocr.name as name,
            a.cityormunicipality as city,
            a.deliveryaddressline as suite,
            a.postalcode as zip,
            a.stateorprovince as state,
            a.streetaddressline as street,
            c.name as country,
            e.value as email
        from 
            organizationcr ocr,
            address a,
            org_cr_email ocre,
            email e,
            country c 
        where
            ocr.postal_address_id = a.id and
            ocr.id = ocre.org_cr_id and
            ocre.email_id = e.id and
            a.country_id = c.id and
            ocr.id =
    """
    
    public static def duplicateOrgsSQL = """
        select name, count(name) as numOccurrences
            from Organization where status <> 'NULLIFIED'
        GROUP BY name
        HAVING ( COUNT(name) > 1 )
    """
    
    public static def duplicateOrgIdsSQL = """select id from organization where status <> 'NULLIFIED' and name = ?"""
    
}