DROP TABLE IF EXISTS DW_STUDY_PARTICIPATING_SITE CASCADE;
CREATE TABLE DW_STUDY_PARTICIPATING_SITE (
    CONTACT_EMAIL character varying(200),
    CONTACT_NAME character varying(600),
    GENERIC_CONTACT character varying(200),
    INTERNAL_SYSTEM_ID INTEGER,
    INVESTIGATOR1_NAME character varying(600),
    INVESTIGATOR1_ROLE character varying(200),
    INVESTIGATOR1_STATUS character varying(50),
    INVESTIGATOR2_NAME character varying(600),
    INVESTIGATOR2_ROLE character varying(200),
    INVESTIGATOR2_STATUS character varying(50),
    NCI_ID character varying(255),
    ORG_NAME character varying(200),
    ORG_ORG_FAMILY character varying(200),
    ORG_STATUS character varying(200),
    RECRUITMENT_STATUS character varying(50),
    RECRUITMENT_STATUS_DATE date,
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);

CREATE INDEX DW_STUDY_PARTICIPATING_SITE_CONTACT_EMAIL_IDX on dw_study_participating_site(contact_email);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_CONTACT_NAME_IDX on dw_study_participating_site(contact_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_GENERIC_CONTACT_IDX on dw_study_participating_site(generic_contact);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INTERNAL_SYSTEM_ID_IDX on dw_study_participating_site(internal_system_id);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR1_NAME_IDX on dw_study_participating_site(investigator1_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR1_ROLE_IDX on dw_study_participating_site(investigator1_role);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR1_STATUS_IDX on dw_study_participating_site(investigator1_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR2_NAME_IDX on dw_study_participating_site(investigator2_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR2_ROLE_IDX on dw_study_participating_site(investigator2_role);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_INVESTIGATOR2_STATUS_IDX on dw_study_participating_site(investigator2_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_NCI_ID_IDX on dw_study_participating_site(nci_id);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_NAME_IDX on dw_study_participating_site(org_name);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_ORG_FAMILY_IDX on dw_study_participating_site(org_org_family);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_ORG_STATUS_IDX on dw_study_participating_site(org_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_RECRUITMENT_STATUS_IDX on dw_study_participating_site(recruitment_status);
CREATE INDEX DW_STUDY_PARTICIPATING_SITE_RECRUITMENT_STATUS_DATE_IDX on dw_study_participating_site(recruitment_status_date);

INSERT INTO DW_STUDY_PARTICIPATING_SITE(
    CONTACT_EMAIL, 
    CONTACT_NAME,
    INTERNAL_SYSTEM_ID,
    ORG_NAME,
    ORG_STATUS,
    RECRUITMENT_STATUS,
    RECRUITMENT_STATUS_DATE
) select contact.email, p.first_name || ' ' || p.middle_name || ' ' || p.last_name, ps.identifier, org.name, 
         org.status_code, ssas.status_code, ssas.status_date
    from study_site ps
        left outer join study_protocol as sp on sp.identifier = ps.study_protocol_identifier
        left outer join study_site_contact as contact on contact.study_site_identifier = ps.identifier and contact.role_code = 'PRIMARY_CONTACT'
        left join healthcare_provider as hcp on hcp.identifier = contact.healthcare_provider_identifier
        left join person as p on p.identifier = hcp.person_identifier
        left outer join healthcare_facility as hcf on hcf.identifier = ps.healthcare_facility_identifier
        left outer join organization as org on org.identifier = hcf.organization_identifier
        left outer join study_site_accrual_status as ssas on ssas.study_site_identifier = ps.identifier
            and ssas.identifier = (select max(identifier) from study_site_accrual_status where study_site_identifier = ps.identifier)
    where ps.functional_code = 'TREATING_SITE' and sp.status_code = 'ACTIVE';
    
UPDATE DW_STUDY_PARTICIPATING_SITE SET NCI_ID = oid.extension from study_site as ps
        inner join study_protocol as sp on sp.identifier = ps.study_protocol_identifier and sp.status_code = 'ACTIVE'
        inner join study_otheridentifiers as oid on oid.study_protocol_id = sp.identifier and oid.root = '2.16.840.1.113883.3.26.4.3'
    where ps.functional_code = 'TREATING_SITE' and ps.identifier = internal_system_id;

UPDATE DW_STUDY_PARTICIPATING_SITE SET INVESTIGATOR1_NAME = p.first_name || ' ' || p.last_name, 
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
    
UPDATE DW_STUDY_PARTICIPATING_SITE SET INVESTIGATOR2_NAME = p.first_name || ' ' || p.last_name,
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