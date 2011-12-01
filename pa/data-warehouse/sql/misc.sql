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