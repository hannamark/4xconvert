ALTER TABLE researchorganization ADD COLUMN fundingmechanism_id bigint;
ALTER TABLE researchorganization RENAME COLUMN fundingmechanism TO fundingmechanismembedded;
ALTER TABLE researchorganization ADD CONSTRAINT  research_org_funding_mech_fk  FOREIGN KEY (fundingmechanism_id)  REFERENCES fundingmechanism (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE researchorganizationcr ADD COLUMN fundingmechanism_id bigint;
ALTER TABLE researchorganizationcr RENAME COLUMN fundingmechanism TO fundingmechanismembedded;
ALTER TABLE researchorganizationcr ADD CONSTRAINT  research_org_funding_mech_fk  FOREIGN KEY (fundingmechanism_id)  REFERENCES fundingmechanism (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
