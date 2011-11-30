DROP TABLE IF EXISTS DW_STUDY_PARTICIPATING_SITE;
CREATE TABLE DW_STUDY_PARTICIPATING_SITE (
    CONTACT_EMAIL character varying(200),
    CONTACT_FIRST_NAME character varying(200),
    CONTACT_LAST_NAME character varying(200),
    CONTACT_MIDDLE_NAME character varying(200),
    GENERIC_CONTACT character varying(200),
    INTERNAL_SYSTEM_ID INTEGER,
    INVESTIGATOR1_FIRST_NAME character varying(200),
    INVESTIGATOR1_LAST_NAME character varying(200),
    INVESTIGATOR1_ROLE character varying(200),
    INVESTIGATOR1_STATUS character varying(50),
    INVESTIGATOR2_FIRST_NAME character varying(200),
    INVESTIGATOR2_LAST_NAME character varying(200),
    INVESTIGATOR2_ROLE character varying(200),
    INVESTIGATOR2_STATUS character varying(50),
    NCI_ID character varying(255),
    ORG_NAME character varying(200),
    ORG_ORG_FAMILY character varying(200),
    ORG_STATUS character varying(200),
    RECRUITMENT_STATUS character varying(50),
    RECRUITMENT_STATUS_DATE date,
    STATUS character varying(50),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);

INSERT INTO DW_STUDY_PARTICIPATING_SITE(
    CONTACT_EMAIL, 
    CONTACT_FIRST_NAME,
    CONTACT_LAST_NAME,
    CONTACT_MIDDLE_NAME,
    INTERNAL_SYSTEM_ID,
    NCI_ID,
    ORG_NAME,
    ORG_STATUS,
    RECRUITMENT_STATUS,
    RECRUITMENT_STATUS_DATE,
    STATUS
) select contact.email, p.first_name, p.last_name, p.middle_name, ps.identifier, nci_id.extension, org.name, 
         org.status_code, ssas.status_code, ssas.status_date, ps.status_code
    from study_site ps
        left outer join study_protocol as sp on sp.identifier = ps.study_protocol_identifier
        left outer join study_site_contact as contact on contact.study_site_identifier = ps.identifier and contact.role_code = 'PRIMARY_CONTACT'
        left join healthcare_provider as hcp on hcp.identifier = contact.healthcare_provider_identifier
        left join person as p on p.identifier = hcp.person_identifier
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = ps.study_protocol_identifier
                and nci_id.root = '2.16.840.1.113883.3.26.4.3'
        left outer join healthcare_facility as hcf on hcf.identifier = ps.healthcare_facility_identifier
        left outer join organization as org on org.identifier = hcf.organization_identifier
        left outer join study_site_accrual_status as ssas on ssas.study_site_identifier = ps.identifier
            and ssas.identifier = (select max(identifier) from study_site_accrual_status where study_site_identifier = ps.identifier)
    where ps.functional_code = 'TREATING_SITE' and sp.status_code = 'ACTIVE';
    
UPDATE DW_STUDY_PARTICIPATING_SITE SET INVESTIGATOR1_FIRST_NAME = p.first_name, INVESTIGATOR1_LAST_NAME = p.last_name,
                                 INVESTIGATOR1_ROLE = investigator.role_code, INVESTIGATOR1_STATUS = investigator.status_code
    from study_site ps 
        left outer join study_site_contact as investigator on investigator.study_site_identifier = ps.identifier 
            and investigator.identifier = (
        select identifier from study_site_contact where (role_code = 'PRINCIPAL_INVESTIGATOR' or role_code = 'SUB_INVESTIGATOR')
                and study_site_identifier = ps.identifier
        order by identifier asc   
        limit 1 offset 0
    )
    left outer join clinical_research_staff as crs on crs.identifier = investigator.clinical_research_staff_identifier
    left outer join person as p on p.identifier = crs.person_identifier
    where ps.identifier = internal_system_id;
    
UPDATE DW_STUDY_PARTICIPATING_SITE SET INVESTIGATOR2_FIRST_NAME = p.first_name, INVESTIGATOR2_LAST_NAME = p.last_name,
                                 INVESTIGATOR2_ROLE = investigator.role_code, INVESTIGATOR2_STATUS = investigator.status_code
    from study_site ps 
        left outer join study_site_contact as investigator on investigator.study_site_identifier = ps.identifier 
            and investigator.identifier = (
        select identifier from study_site_contact where (role_code = 'PRINCIPAL_INVESTIGATOR' or role_code = 'SUB_INVESTIGATOR')
                and study_site_identifier = ps.identifier
        order by identifier asc   
        limit 1 offset 1
    )
    left outer join clinical_research_staff as crs on crs.identifier = investigator.clinical_research_staff_identifier
    left outer join person as p on p.identifier = crs.person_identifier
    where ps.identifier = internal_system_id;
    
UPDATE DW_STUDY_PARTICIPATING_SITE SET ORG_ORG_FAMILY = fam_org.family_name
    from dw_family_organization fam_org where fam_org.organization_name = org_name;