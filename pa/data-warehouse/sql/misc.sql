--Listing of all orgs used in study/participating sites
CREATE OR REPLACE VIEW dw_summary_4_report as
select org, 
        (select count(responsible_party_name) from dw_study where unique_org.org = responsible_party_name) as responsible, 
        (select count(lead_org) from dw_study where unique_org.org = lead_org) as lead, 
        (select count(sponsor) from dw_study where unique_org.org = sponsor) as sponsor, 
        (select count(submitter_organization) from dw_study where unique_org.org = submitter_organization) as submitter,
        (select count(sponsor) from dw_summary_4_funding where unique_org.org = sponsor) as sum_4_sponsor, 
        (select count(org_name) from dw_study_participating_site where unique_org.org = org_name) as participant 
from 
( select distinct org from
        (select distinct responsible_party_name as org from dw_study
                union
        select distinct lead_org as org from dw_study
                union
        select distinct sponsor as org from dw_study
                union
        select distinct sponsor as org from dw_summary_4_funding
                union
        select distinct org_name as org from dw_study_participating_site
        ) as orgs order by org asc) as unique_org;
        
--Put here instead of user.groovy because organization table not yet populated when user.groovy run
UPDATE DW_USER USR 
  SET AFFILIATED_ORGANIZATION = (SELECT DISTINCT NAME 
                                 FROM DW_ORGANIZATION ORG 
                                 WHERE ORG.INTERNAL_ID = USR.AFFILIATED_ORGANIZATION_ID);

--Put here instead of disease.groovy because of java heap issue
CREATE TABLE stg_dw_study_disease_tree AS
    SELECT DISTINCT
           CAST('NO' AS character varying(3)) AS ct_gov_xml_indicator,
           CAST('TREE' AS character varying(5)) AS inclusion_indicator, 
           sd.date_last_created, 
           sd.date_last_updated, 
           dp.disease_code,
           dp.preferred_name AS disease_preferred_name, 
           dp.menu_display_name AS disease_menu_display_name, 
           sd.internal_system_id,
           dp.parent_disease_identifier AS internal_system_id2,
           CAST(NULL AS character varying(3)) AS lead_disease_indicator, 
           sd.nci_id, 
           dp.nt_term_identifier AS nci_thesaurus_concept_id, 
           sd.user_last_created, 
           sd.user_last_updated
      FROM dw_study_disease sd
      JOIN stg_dw_disease_parents dp ON (sd.internal_system_id2 = dp.disease_identifier);

ALTER TABLE stg_dw_study_disease_tree ADD PRIMARY KEY (internal_system_id, internal_system_id2);

CREATE TABLE stg_dw_study_disease_tree_keep AS
    SELECT nci_id, MIN(internal_system_id) AS internal_system_id, internal_system_id2
    FROM stg_dw_study_disease_tree
    GROUP BY nci_id, internal_system_id2;

ALTER TABLE stg_dw_study_disease_tree_keep ADD PRIMARY KEY (internal_system_id, internal_system_id2);

INSERT INTO dw_study_disease 
    SELECT * 
    FROM stg_dw_study_disease_tree 
    WHERE (internal_system_id, internal_system_id2) 
          IN (SELECT internal_system_id, internal_system_id2 FROM stg_dw_study_disease_tree_keep);

DELETE FROM dw_study_disease WHERE INCLUSION_INDICATOR = 'TREE'
  AND (nci_id, internal_system_id2) IN (SELECT nci_id, internal_system_id2 FROM dw_study_disease WHERE INCLUSION_INDICATOR = 'TRIAL');

DROP TABLE stg_dw_study_disease_tree_keep;
DROP TABLE stg_dw_study_disease_tree;
DROP TABLE stg_dw_disease_parents;
