update organizationalcontact set orgcontacttype_id = (select id from organizationalcontacttype where code='Site') where orgcontacttype_id is null;
update organizationalcontactcr set orgcontacttype_id = (select id from organizationalcontacttype where code='Site') where orgcontacttype_id is null;

ALTER TABLE organizationalcontact ALTER COLUMN orgcontacttype_id SET NOT NULL;
ALTER TABLE organizationalcontactcr ALTER COLUMN orgcontacttype_id SET NOT NULL;
