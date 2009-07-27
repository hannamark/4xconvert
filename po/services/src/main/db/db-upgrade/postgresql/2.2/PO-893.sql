alter table organizationalcontact alter column person_id drop not null;
alter table organizationalcontactcr alter column person_id drop not null;

alter table organizationalcontact add column title varchar(255);
alter table organizationalcontactcr add column title varchar(255);
