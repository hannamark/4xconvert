create table Alias (id int8 not null, value varchar(254) not null, primary key (id));

create index IX3FF41B7C6AC7385 on Alias (value);

create table organization_alias (organization_id int8 not null, alias_id int8 not null, idx int4 not null, primary key (organization_id, idx), unique (alias_id));
alter table organization_alias add constraint ORG_ALIAS_FK foreign key (organization_id) references Organization;
alter table organization_alias add constraint ALIAS_ORG_FK foreign key (alias_id) references Alias;

create table hcf_alias (hcf_id int8 not null, alias_id int8 not null, idx int4 not null, primary key (hcf_id, idx), unique (alias_id));
alter table hcf_alias add constraint HCF_Alias_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_alias add constraint Alias_HCF_FK foreign key (alias_id) references Alias;

create table ro_alias (ro_id int8 not null, alias_id int8 not null, idx int4 not null, primary key (ro_id, idx), unique (alias_id));
alter table ro_alias add constraint RO_Alias_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_alias add constraint Alias_RO_FK foreign key (alias_id) references Alias;

