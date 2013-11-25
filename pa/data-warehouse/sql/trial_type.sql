DROP TABLE IF EXISTS dw_study_type;
DROP TABLE IF EXISTS dw_study_types;
CREATE TABLE dw_study_type AS
  SELECT 1 AS id, 'NCI Sponsored'::character varying(16) AS group, 'CTEP'::character varying(16) AS type, nci_id
    FROM dw_study
    WHERE sponsor = 'National Cancer Institute'
      AND ctep_id IS NOT NULL
      AND dcp_id IS NULL
  UNION
  SELECT 2, 'NCI Sponsored', 'DCP', nci_id
    FROM dw_study
    WHERE sponsor = 'National Cancer Institute'
      AND dcp_id IS NOT NULL
  UNION
  SELECT 3, 'NCI Managed', 'CTEP', nci_id
    FROM dw_study
    WHERE ctep_id IS NOT NULL
      AND dcp_id IS NULL
  UNION
  SELECT 4, 'NCI Managed', 'DCP', nci_id
    FROM dw_study
    WHERE dcp_id IS NOT NULL
  UNION
  SELECT 5, 'CCR', NULL, nci_id
    FROM dw_study
    WHERE lead_org = 'NCI - Center for Cancer Research'
      AND dcp_id IS NULL
      AND ctep_id IS NULL
  UNION
  SELECT 6, 'Center Trials', 'Center Registered Complete', nci_id
    FROM dw_study
    WHERE  submitter_name NOT IN ('CTRO Staff National Cancer Institute','DCP PIO', 'Sulekha Avasthi')
       AND category = 'Complete'
  UNION
  SELECT 7, 'Center Trials', 'Center Registered Abbreviated', nci_id
    FROM dw_study
    WHERE  submitter_name NOT IN ('CTRO Staff National Cancer Institute','DCP PIO', 'Sulekha Avasthi')
       AND category = 'Abbreviated'
  UNION
  SELECT 8, 'Import from Clinicaltrials.gov', NULL, nci_id
    FROM dw_study
    WHERE SUBSTRING(created_by FROM 1 FOR 13) = 'CT.Gov Import'
  UNION
  SELECT 9, 'Consortia/Other', NULL, nci_id
    FROM dw_study
    WHERE consortia_trial_category IN ('EXTERNALLY_PEER_REVIEWED', 'NATIONAL')
  UNION
  SELECT 10, NULL, NULL, nci_id FROM dw_study;

ALTER TABLE dw_study_type
  ADD CONSTRAINT dw_study_type_pk PRIMARY KEY (id, nci_id);

CREATE INDEX dw_study_type_nci_id_idx
  ON dw_study_type USING btree  (nci_id);

CREATE TABLE dw_study_types AS SELECT DISTINCT id, "group", type FROM dw_study_type;

ALTER TABLE dw_study_types
  ADD CONSTRAINT dw_study_types_pk PRIMARY KEY (id);