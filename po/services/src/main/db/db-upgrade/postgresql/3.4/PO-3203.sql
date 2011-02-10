alter table Family_FamilyOrganizationRelationship drop constraint FKEFFEBACACF0920B;
alter table Family_FamilyOrganizationRelationship drop constraint FKEFFEBACA87B91596;
alter table Family_OrganizationRelationship drop constraint FKBD0DAF86CF0920B;
alter table Family_OrganizationRelationship drop constraint FKBD0DAF86B728D56E;
alter table Organization_FamilyOrganizationRelationship drop constraint FK47D0471BF55FEAEB;
alter table Organization_FamilyOrganizationRelationship drop constraint FK47D0471B87B91596;
drop table Family_FamilyOrganizationRelationship;
drop table Family_OrganizationRelationship;
drop table Organization_FamilyOrganizationRelationship;

alter table Family 
	alter column statusCode set not null,
	alter column startDate set not null,
	alter column startDate type date,
	alter column endDate type date,
	add constraint familyCheckStartDate check (startDate <= current_date),
	add constraint familyCheckEndDate check (endDate <= current_date);

alter table FamilyOrganizationRelationship
    alter column functionaltype set not null,
    alter column family_id set not null,
    alter column organization_id set not null,
    alter column startDate set not null,
    alter column startDate type date,
    alter column endDate type date,
    add constraint familyOrgRelCheckStartDate check (startDate <= current_date),
    add constraint familyOrgRelCheckEndDate check (endDate <= current_date);

alter table OrganizationRelationship
    alter column hierarchicalType set not null,
    alter column family_id set not null,
    alter column organization_id set not null,
    alter column startDate set not null,
    alter column startDate type date,
    alter column endDate type date,
    add constraint orgRelCheckStartDate check (startDate <= current_date),
    add constraint orgRelCheckEndDate check (endDate <= current_date);

alter table OrganizationRelationship add constraint FK4B61A72B7D351360 foreign key (relatedOrganization_id) references Organization;
alter table OrganizationRelationship add constraint FK4B61A72BF55FEAEB foreign key (organization_id) references Organization;
