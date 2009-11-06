ALTER TABLE organizationalcontact ADD COLUMN orgcontacttype_id bigint;
ALTER TABLE organizationalcontactcr ADD COLUMN orgcontacttype_id bigint;

ALTER TABLE organizationalcontact ADD CONSTRAINT  ORGCNCT_TYPE_ORGCNCT_FK  FOREIGN KEY (orgcontacttype_id)  REFERENCES organizationalcontacttype (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE organizationalcontactcr ADD CONSTRAINT  ORGCNCTCR_TYPE_ORGCNCTCR_FK  FOREIGN KEY (orgcontacttype_id)  REFERENCES organizationalcontacttype (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

