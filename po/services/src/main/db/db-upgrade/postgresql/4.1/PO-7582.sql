alter table organization add column created_by_id int8;
alter table organization add constraint org_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table healthcarefacility add column created_by_id int8;
alter table healthcarefacility add constraint orgrole_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table researchorganization add column created_by_id int8;
alter table researchorganization add constraint orgrole_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table oversightcommittee add column created_by_id int8;
alter table oversightcommittee add constraint orgrole_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table identifiedorganization add column created_by_id int8;
alter table identifiedorganization add constraint identifiedentity_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;


alter table person add column created_by_id int8;
alter table person add constraint person_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table clinicalresearchstaff add column created_by_id int8;
alter table clinicalresearchstaff add constraint perrole_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table healthcareprovider add column created_by_id int8;
alter table healthcareprovider add constraint perrole_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table organizationalcontact add column created_by_id int8;
alter table organizationalcontact add constraint perrole_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;

alter table identifiedperson add column created_by_id int8;
alter table identifiedperson add constraint identifiedentity_createdby_user_fk foreign key (created_by_id) references csm_user ON DELETE SET NULL;


