create table crs_otheridentifier (crs_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table hcf_otheridentifier (hcf_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table hcp_otheridentifier (hcp_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table identifiedorg_otheridentifier (identifiedorg_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table identifiedperson_otheridentifier (identifiedperson_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table oco_otheridentifier (oco_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table orgcontact_otheridentifier (orgcontact_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));
create table ro_otheridentifier (ro_id int8 not null, null_flavor varchar(255), displayable bool, extension varchar(255), identifier_name varchar(255), reliability varchar(255), root varchar(255), scope varchar(255));

alter table crs_otheridentifier add constraint CRS_OI_FK foreign key (crs_id) references ClinicalResearchStaff;
alter table hcf_otheridentifier add constraint HCF_OI_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcp_otheridentifier add constraint HCP_OI_FK foreign key (hcp_id) references HealthCareProvider;
alter table identifiedorg_otheridentifier add constraint IO_OI_FK foreign key (identifiedorg_id) references IdentifiedOrganization;
alter table identifiedperson_otheridentifier add constraint IP_OI_FK foreign key (identifiedperson_id) references IdentifiedPerson;
alter table oco_otheridentifier add constraint OCO_OI_FK foreign key (oco_id) references OversightCommittee;
alter table orgcontact_otheridentifier add constraint ORGCONTACT_OI_FK foreign key (orgcontact_id) references OrganizationalContact;
alter table ro_otheridentifier add constraint RO_OI_FK foreign key (ro_id) references ResearchOrganization;
