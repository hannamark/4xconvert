
ALTER TABLE healthcarefacility ADD name varchar(160);
ALTER TABLE healthcarefacilitycr ADD name varchar(160);

ALTER TABLE researchorganization ADD name varchar(160);
ALTER TABLE researchorganizationcr ADD name varchar(160);

create index IX922F1570337A8B on HealthCareFacility (name);
create index IXC2BF81DF337A8B on HealthCareFacilityCR (name);

create index IX80A6A68E337A8B on ResearchOrganization (name);
create index IXF197437D337A8B on ResearchOrganizationCR (name);



create table hcf_address (hcf_id int8 not null, address_id int8 not null, primary key (hcf_id, address_id), unique (address_id));
create table hcf_email (hcf_id int8 not null, email_id int8 not null, idx int4 not null, primary key (hcf_id, idx), unique (email_id));
create table hcf_fax (hcf_id int8 not null, fax_id int8 not null, idx int4 not null, primary key (hcf_id, idx), unique (fax_id));
create table hcf_phone (hcf_id int8 not null, phone_id int8 not null, idx int4 not null, primary key (hcf_id, idx), unique (phone_id));
create table hcf_tty (hcf_id int8 not null, tty_id int8 not null, idx int4 not null, primary key (hcf_id, idx), unique (tty_id));
create table hcf_url (hcf_id int8 not null, url_id int8 not null, idx int4 not null, primary key (hcf_id, idx), unique (url_id));
create table hcfcr_address (hcfcr_id int8 not null, address_id int8 not null, primary key (hcfcr_id, address_id), unique (address_id));
create table hcfcr_email (hcfcr_id int8 not null, email_id int8 not null, idx int4 not null, primary key (hcfcr_id, idx), unique (email_id));
create table hcfcr_fax (hcfcr_id int8 not null, fax_id int8 not null, idx int4 not null, primary key (hcfcr_id, idx), unique (fax_id));
create table hcfcr_phone (hcfcr_id int8 not null, phone_id int8 not null, idx int4 not null, primary key (hcfcr_id, idx), unique (phone_id));
create table hcfcr_tty (hcfcr_id int8 not null, tty_id int8 not null, idx int4 not null, primary key (hcfcr_id, idx), unique (tty_id));
create table hcfcr_url (hcfcr_id int8 not null, url_id int8 not null, idx int4 not null, primary key (hcfcr_id, idx), unique (url_id));

create table ro_address (ro_id int8 not null, address_id int8 not null, primary key (ro_id, address_id), unique (address_id));
create table ro_email (ro_id int8 not null, email_id int8 not null, idx int4 not null, primary key (ro_id, idx), unique (email_id));
create table ro_fax (ro_id int8 not null, fax_id int8 not null, idx int4 not null, primary key (ro_id, idx), unique (fax_id));
create table ro_phone (ro_id int8 not null, phone_id int8 not null, idx int4 not null, primary key (ro_id, idx), unique (phone_id));
create table ro_tty (ro_id int8 not null, tty_id int8 not null, idx int4 not null, primary key (ro_id, idx), unique (tty_id));
create table ro_url (ro_id int8 not null, url_id int8 not null, idx int4 not null, primary key (ro_id, idx), unique (url_id));
create table rocr_address (rocr_id int8 not null, address_id int8 not null, primary key (rocr_id, address_id), unique (address_id));
create table rocr_email (rocr_id int8 not null, email_id int8 not null, idx int4 not null, primary key (rocr_id, idx), unique (email_id));
create table rocr_fax (rocr_id int8 not null, fax_id int8 not null, idx int4 not null, primary key (rocr_id, idx), unique (fax_id));
create table rocr_phone (rocr_id int8 not null, phone_id int8 not null, idx int4 not null, primary key (rocr_id, idx), unique (phone_id));
create table rocr_tty (rocr_id int8 not null, tty_id int8 not null, idx int4 not null, primary key (rocr_id, idx), unique (tty_id));
create table rocr_url (rocr_id int8 not null, url_id int8 not null, idx int4 not null, primary key (rocr_id, idx), unique (url_id));

alter table hcf_address add constraint HCF_ADDRESS_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_address add constraint ADDRESS_HCF_FK foreign key (address_id) references Address;
alter table hcf_email add constraint HCF_EMAIL_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_email add constraint EMAIL_HCF_FK foreign key (email_id) references Email;
alter table hcf_fax add constraint HCF_FAX_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_fax add constraint FAX_HCF_FK foreign key (fax_id) references PhoneNumber;
alter table hcf_phone add constraint HCF_PHONE_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_phone add constraint PHONE_HCF_FK foreign key (phone_id) references PhoneNumber;
alter table hcf_tty add constraint HCF_TTY_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_tty add constraint TTY_HCF_FK foreign key (tty_id) references PhoneNumber;
alter table hcf_url add constraint HCF_URL_FK foreign key (hcf_id) references HealthCareFacility;
alter table hcf_url add constraint URL_HCF_FK foreign key (url_id) references URL;
alter table hcfcr_address add constraint ADDRESS_HCFCR_FK foreign key (address_id) references Address;
alter table hcfcr_address add constraint HCFCR_ADDRESS_FK foreign key (hcfcr_id) references HealthCareFacilityCR;
alter table hcfcr_email add constraint EMAIL_HCFCR_FK foreign key (email_id) references Email;
alter table hcfcr_email add constraint HCFCR_EMAIL_FK foreign key (hcfcr_id) references HealthCareFacilityCR;
alter table hcfcr_fax add constraint FAX_HCFCR_FK foreign key (fax_id) references PhoneNumber;
alter table hcfcr_fax add constraint HCFCR_FAX_FK foreign key (hcfcr_id) references HealthCareFacilityCR;
alter table hcfcr_phone add constraint PHONE_HCFCR_FK foreign key (phone_id) references PhoneNumber;
alter table hcfcr_phone add constraint HCFCR_PHONE_FK foreign key (hcfcr_id) references HealthCareFacilityCR;
alter table hcfcr_tty add constraint TTY_HCFCR_FK foreign key (tty_id) references PhoneNumber;
alter table hcfcr_tty add constraint HCFCR_TTY_FK foreign key (hcfcr_id) references HealthCareFacilityCR;
alter table hcfcr_url add constraint URL_HCFCR_FK foreign key (url_id) references URL;
alter table hcfcr_url add constraint HCFCR_URL_FK foreign key (hcfcr_id) references HealthCareFacilityCR;

alter table ro_address add constraint RO_ADDRESS_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_address add constraint ADDRESS_RO_FK foreign key (address_id) references Address;
alter table ro_email add constraint RO_EMAIL_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_email add constraint EMAIL_RO_FK foreign key (email_id) references Email;
alter table ro_fax add constraint FAX_RO_FK foreign key (fax_id) references PhoneNumber;
alter table ro_fax add constraint RO_FAX_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_phone add constraint RO_PHONE_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_phone add constraint PHONE_RO_FK foreign key (phone_id) references PhoneNumber;
alter table ro_tty add constraint RO_TTY_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_tty add constraint TTY_RO_FK foreign key (tty_id) references PhoneNumber;
alter table ro_url add constraint RO_URL_FK foreign key (ro_id) references ResearchOrganization;
alter table ro_url add constraint URL_RO_FK foreign key (url_id) references URL;
alter table rocr_address add constraint ADDRESS_ROCR_FK foreign key (address_id) references Address;
alter table rocr_address add constraint ROCR_ADDRESS_FK foreign key (rocr_id) references ResearchOrganizationCR;
alter table rocr_email add constraint ROCR_EMAIL_FK foreign key (rocr_id) references ResearchOrganizationCR;
alter table rocr_email add constraint EMAIL_ROCR_FK foreign key (email_id) references Email;
alter table rocr_fax add constraint FAX_ROCR_FK foreign key (fax_id) references PhoneNumber;
alter table rocr_fax add constraint ROCR_FAX_FK foreign key (rocr_id) references ResearchOrganizationCR;
alter table rocr_phone add constraint ROCR_PHONE_FK foreign key (rocr_id) references ResearchOrganizationCR;
alter table rocr_phone add constraint PHONE_ROCR_FK foreign key (phone_id) references PhoneNumber;
alter table rocr_tty add constraint ROCR_TTY_FK foreign key (rocr_id) references ResearchOrganizationCR;
alter table rocr_tty add constraint TTY_ROCR_FK foreign key (tty_id) references PhoneNumber;
alter table rocr_url add constraint ROCR_URL_FK foreign key (rocr_id) references ResearchOrganizationCR;
alter table rocr_url add constraint URL_ROCR_FK foreign key (url_id) references URL;

