--Listing of all orgs used in study/participating sites
CREATE OR REPLACE VIEW dw_summary_4_report as
select org, 
        (select count(responsible_party_name) from dw_study where unique_org.org = responsible_party_name) as responsible, 
        (select count(lead_org) from dw_study where unique_org.org = lead_org) as lead, 
        (select count(sponsor) from dw_study where unique_org.org = sponsor) as sponsor, 
        (select count(submitter_organization) from dw_study where unique_org.org = submitter_organization) as submitter,
        (select count(summary_4_funding_sponsor) from dw_study where unique_org.org = summary_4_funding_sponsor) as sum_4_sponsor, 
        (select count(org_name) from dw_study_participating_site where unique_org.org = org_name) as participant 
from 
( select distinct org from
        (select distinct responsible_party_name as org from dw_study
                union
        select distinct lead_org as org from dw_study
                union
        select distinct sponsor as org from dw_study
                union
        select distinct summary_4_funding_sponsor as org from dw_study
                union
        select distinct org_name as org from dw_study_participating_site
        ) as orgs order by org asc) as unique_org;
        
--Put here instead of user.groovy because organization table not yet populated when user.groovy run
UPDATE DW_USER USR 
  SET AFFILIATED_ORGANIZATION = (SELECT DISTINCT NAME 
                                 FROM DW_ORGANIZATION ORG 
                                 WHERE ORG.INTERNAL_ID = USR.AFFILIATED_ORGANIZATION_ID);

--Put here instead of disease.groovy because of java heap issue
INSERT INTO dw_study_disease
    SELECT sd.ct_gov_xml_indicator, 'TREE', sd.date_last_created, sd.date_last_updated, dp.disease_code,
           dp.preferred_name, dp.menu_display_name, sd.internal_system_id, dp.parent_disease_identifier,
           NULL, sd.nci_id, dp.nt_term_identifier, sd.user_last_created, sd.user_last_updated
      FROM dw_study_disease sd
      JOIN stg_dw_disease_parents dp ON (sd.internal_system_id2 = dp.disease_identifier);
DELETE FROM dw_study_disease WHERE INCLUSION_INDICATOR = 'TREE'
  AND (nci_id, internal_system_id2) IN (SELECT nci_id, internal_system_id2 FROM dw_study_disease WHERE INCLUSION_INDICATOR = 'TRIAL');
DROP TABLE stg_dw_disease_parents;
