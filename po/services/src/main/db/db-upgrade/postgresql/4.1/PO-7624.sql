alter table organization add column overridden_by_id int8;
alter table organization add constraint org_overriddenby_user_fk foreign key (overridden_by_id) references csm_user ON DELETE SET NULL;

alter table healthcarefacility add column overridden_by_id int8;
alter table healthcarefacility add constraint orgrole_overriddenby_user_fk foreign key (overridden_by_id) references csm_user ON DELETE SET NULL;

alter table researchorganization add column overridden_by_id int8;
alter table researchorganization add constraint orgrole_overriddenby_user_fk foreign key (overridden_by_id) references csm_user ON DELETE SET NULL;

alter table oversightcommittee add column overridden_by_id int8;
alter table oversightcommittee add constraint orgrole_overriddenby_user_fk foreign key (overridden_by_id) references csm_user ON DELETE SET NULL;

alter table identifiedorganization add column overridden_by_id int8;
alter table identifiedorganization add constraint identifiedentity_overriddenby_user_fk foreign key (overridden_by_id) references csm_user ON DELETE SET NULL;

alter table organizationalcontact add column overridden_by_id int8;
alter table organizationalcontact add constraint perrole_overriddenby_user_fk foreign key (overridden_by_id) references csm_user ON DELETE SET NULL;


