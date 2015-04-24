DROP INDEX IF EXISTS study_site_healthcare_facility_identifier;
create index study_site_healthcare_facility_identifier ON study_site (healthcare_facility_identifier);

DROP INDEX IF EXISTS hcf_organization_identifier;
create index hcf_organization_identifier ON healthcare_facility (organization_identifier);



