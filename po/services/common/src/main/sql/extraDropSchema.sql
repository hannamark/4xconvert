alter table PersonCR drop constraint PERSONCR_REMOTEAPP_FK;
alter table PersonCR drop constraint PERSONCR_PROCESSEDBY_FK;
alter table OrganizationCR drop constraint ORGCR_REMOTEAPP_FK;
alter table OrganizationCR drop constraint ORGCR_PROCESSEDBY_FK;

drop sequence AUDIT_ID_SEQ;