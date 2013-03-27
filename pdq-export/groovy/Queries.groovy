class Queries {
    
    public static def otherIdsSQL = """
        select extension from study_otheridentifiers where study_protocol_id = ? and root <> '2.16.840.1.113883.3.26.4.3'
    """
    
    public static def partSitesSQL = """
        select org.name,
            hcf.assigned_identifier as hcf_poid,
            org.assigned_identifier as org_poid,
            prim_crs.assigned_identifier as prim_crs_id,
            prim_ssc.telephone as prim_phone,
            prim_ssc.email as prim_email,
            inv_crs.assigned_identifier as inv_crs_id,
            inv_ssc.telephone as inv_phone,
            inv_ssc.email as inv_email,
            CASE 
                WHEN ssas.status_code = 'APPROVED' then 'Approved'
                WHEN ssas.status_code = 'IN_REVIEW' then 'In Review'
                WHEN ssas.status_code = 'ACTIVE' then 'Active'
                WHEN ssas.status_code = 'ENROLLING_BY_INVITATION' then 'Enrolling by Invitation'
                WHEN ssas.status_code = 'CLOSED_TO_ACCRUAL' then 'Closed to Accrual'
                WHEN ssas.status_code = 'CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Closed to Accrual and Intervention'
                WHEN ssas.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL' then 'Temporarily Closed to Accrual'
                WHEN ssas.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Temporarily Closed to Accrual and Intervention'
                WHEN ssas.status_code = 'WITHDRAWN' then 'Withdrawn'
                WHEN ssas.status_code = 'ADMINISTRATIVELY_COMPLETE' then 'Administratively Complete'
                WHEN ssas.status_code = 'COMPLETED' then 'Completed'
            END as status
        FROM Study_Site ss
        join healthcare_facility hcf on hcf.identifier = ss.healthcare_facility_identifier
        join organization org on org.identifier = hcf.organization_identifier
        left outer join study_site_accrual_status ssas on ssas.study_site_identifier = ss.identifier and ssas.identifier = (select max(identifier) from study_site_accrual_status ssas2 where ssas2.study_site_identifier = ss.identifier)
        left outer join study_site_contact prim_ssc on prim_ssc.study_site_identifier = ss.identifier and prim_ssc.role_code = 'PRIMARY_CONTACT'
        left outer join clinical_research_staff prim_crs on prim_crs.identifier = prim_ssc.clinical_research_staff_identifier
        left outer join study_site_contact inv_ssc on inv_ssc.study_site_identifier = ss.identifier and inv_ssc.role_code = 'PRINCIPAL_INVESTIGATOR'
        left outer join clinical_research_staff inv_crs on inv_crs.identifier = inv_ssc.clinical_research_staff_identifier
        where ss.functional_code = 'TREATING_SITE'
        and ss.study_protocol_identifier = ?
    """

    public static def collabsSQL = """
        select org.name,
            ro.assigned_identifier as ro_poid,
            org.assigned_identifier as org_poid,
            ss.functional_code
        from Study_Site ss
            join research_organization ro on ro.identifier = ss.research_organization_identifier
            join organization org on org.identifier = ro.organization_identifier
        where ss.functional_code in ('FUNDING_SOURCE', 'LABORATORY', 'AGENT_SOURCE')
            and ss.study_protocol_identifier = ?
    """

    public static def ownersSQL = """
        select ru.first_name ||  ' ' || ru.last_name as ownerName
            from registry_user ru
        join study_owner own on own.user_id = ru.identifier
                and own.study_id = ?
    """

    public static def roIdSQL = """
        select ro.identifier
        from research_organization ro
        join organization org on org.identifier = ro.organization_identifier
        where org.name = ?
    """
    
    public static def fundingsSQL = """
        select funding_mechanism_code,
            nih_institute_code,
            serial_number,
            nci_division_program_code
        from study_resourcing
            where study_protocol_identifier = ?
    """
    
    public static def conditionsSQL = """
        select 
            d.disease_code,
            d.nt_term_identifier as nci_thesaurus_id,
            d.menu_display_name,
            d.preferred_name
        from study_disease sd
             join pdq_disease d on d.identifier = sd.disease_identifier
        where sd.study_protocol_identifier = ?
    """
    
    public static def armsSQL = """
        select 
            a.identifier as arm_id,
            a.name as arm_name,
            CASE 
                WHEN a.type_code = 'EXPERIMENTAL' then 'Experimental' 
                WHEN a.type_code = 'ACTIVE_COMPARATOR' then 'Active Comparator' 
                WHEN a.type_code = 'PLACEBO_COMPARATOR' then 'Placebo Comparator' 
                WHEN a.type_code = 'SHAM_COMPARATOR' then 'Sham Comparator' 
                WHEN a.type_code = 'NO_INTERVENTION' then 'No intervention' 
                WHEN a.type_code = 'OTHER' then 'Other' 
            END as arm_type,
            a.description_text as arm_desc,
            int.identifier as int_id,
            int.pdq_term_identifier as cdr_id,
            pa.subcategory_code as int_type,
            int.name as int_name,
            pa.text_description as int_desc,
            altName.name as alt_name
        from ARM a
            left outer join arm_intervention a_i on a_i.arm_identifier = a.identifier   
            left outer join planned_activity pa on pa.identifier = a_i.planned_activity_identifier   
            join intervention int on int.identifier = pa.intervention_identifier 
            left outer join intervention_alternate_name altName on altName.intervention_identifier = int.identifier 
        where a.study_protocol_identifier = ?            
    """
    
    public static def primOutcomesSQL = """
        select 
            prim_som.name as prim_som_name,
            CASE WHEN prim_som.safety_indicator then 'Yes'
                ELSE 'No'
            END as prim_som_safety_ind,
            prim_som.timeframe as prim_som_timeframe
        from study_outcome_measure prim_som 
            where prim_som.study_protocol_identifier = ?
            and prim_som.primary_indicator = true
    """
    
    public static def eligsSQL = """
        select 
            CASE WHEN elig.inclusion_indicator THEN 'Inclusion Criteria'
                 ELSE 'Exclusion Criteria'
            END as elig_type,
            CASE WHEN elig.eligible_gender_code = 'MALE' THEN 'Male'
                WHEN elig.eligible_gender_code = 'FEMALE' THEN 'Female'
                WHEN elig.eligible_gender_code = 'BOTH' THEN 'Both'
                ELSE null
            END as gender,
            criterion_name,
            CASE WHEN max_value = '999' THEN 'N/A'
                ELSE max_value || ' ' || max_unit
            END as max_age,            
            CASE WHEN min_value = '0' THEN 'N/A'
            ELSE min_value || ' ' || min_unit
            END as min_age,            
            text_description as elig_data
         from planned_eligibility_criterion elig
            join planned_activity pa on pa.identifier = elig.identifier 
        where pa.study_protocol_identifier = ?
    """
    
}