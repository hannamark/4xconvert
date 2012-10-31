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
    
    
    public static def dupHcfRoleSQL = """
        select player_id, count(player_id) as numOccurrences
            from healthcarefacility where status <> 'NULLIFIED'
            GROUP BY player_id
            HAVING ( COUNT(player_id) > 1 )
        """
    
    public static def dupHcfRoleDetailSQL = """
            select
            hcf.id as role_id,
            hcf.player_id as org_poid,
            org.name as orgname, 
            hcfo.extension as role_ctepid,
            hcf.name as org_role_name,
            hcf.status as role_status, 
            to_char(hcf.statusdate,'mm-dd-YYYY') as role_statusdate 
            from
            healthcarefacility hcf 
            left outer join hcf_otheridentifier hcfo on hcf.id = hcfo.hcf_id
            left outer join organization org on hcf.player_id = org.id 
            where 
            hcf.status <> 'NULLIFIED' and
            hcf.id = ?
    """
    
    public static def dupRORoleSQL = """
            select player_id, count(player_id) as numOccurrences
                from researchorganization where status <> 'NULLIFIED'
            GROUP BY player_id
            HAVING ( COUNT(player_id) > 1 ) 
            order by player_id
    """
    
    public static def dupRORoleDetailSQL = """
            select
            ro.id as role_id,
            ro.player_id as org_poid,
            org.name as orgname, 
            roo.extension as role_ctepid,
            ro.name as org_role_name,
            ro.status as role_status, 
            to_char(ro.statusdate,'mm-dd-YYYY') as role_statusdate ,
            rot.code || ' (' || rot.description || ')' as ro_type,
            fm.code || ' (' || fm.description || ')' as ro_fundingmechanism
            from
            researchorganization ro 
            left outer join ro_otheridentifier roo on ro.id = roo.ro_id
            left outer join researchorganizationtype rot on ro.typecode_id = rot.id
            left outer join fundingmechanism fm on ro.fundingmechanism_id = fm.id
            left outer join organization org on ro.player_id = org.id 
            where 
            ro.status <> 'NULLIFIED' and
            ro.id = ?
    """
    
    public static def dupOCRoleSQL = """
            select organization_id, count(organization_id) as numOccurrences
                from organizationalcontact where status <> 'NULLIFIED'
            GROUP BY organization_id
            HAVING ( COUNT(organization_id) > 1 ) 
            order by organization_id;
    """
    
    public static def dupOCRoleDetailSQL = """
            select
            oc.id as role_id,
            oc.organization_id as org_poid,
            org.name as orgname, 
            oc.status as role_status, 
            to_char(oc.statusdate,'mm-dd-YYYY') as role_statusdate,
            oc.title as org_role_title,
            oct.code as oct_type
            from
            organizationalcontact oc 
            left outer join organization org on oc.organization_id = org.id 
            left outer join organizationalcontacttype oct on oc.orgcontacttype_id = oct.id
            where 
            oc.status <> 'NULLIFIED' and
            oc.id = ?
    """
    
    public static def dupOCORoleSQL = """
            select player_id, count(player_id) as numOccurrences
            from oversightcommittee where status <> 'NULLIFIED'
            GROUP BY player_id
            HAVING ( COUNT(player_id) > 1 ) 
            order by player_id
    """
    
    public static def dupOCORoleDetailSQL = """
            select
            oco.id as role_id,
            oco.player_id as org_poid,
            org.name as orgname,
            ocoo.extension as role_ctepid,
            oco.status as role_status, 
            to_char(oco.statusdate,'mm-dd-YYYY') as role_statusdate,
            ocot.code as oco_type
            from
            oversightcommittee oco 
            left outer join oco_otheridentifier ocoo on oco.id = ocoo.oco_id
            left outer join oversightcommitteetype ocot on oco.typecode_id = ocot.id
            left outer join organization org on oco.player_id = org.id 
            where 
            oco.status <> 'NULLIFIED' and
            oco.id = ?
    """
}


