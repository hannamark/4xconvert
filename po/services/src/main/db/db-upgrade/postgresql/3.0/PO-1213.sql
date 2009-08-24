ALTER TABLE person ADD SEX varchar(255);
ALTER TABLE person ADD birthDate timestamp;

create table person_ethnicgroup (person_id int8 not null, ETHNIC_GROUP varchar(255) not null, primary key (person_id, ETHNIC_GROUP));

alter table person_ethnicgroup add constraint PER_EG_FK foreign key (person_id) references Person;

create table person_race (person_id int8 not null, RACE varchar(255) not null, primary key (person_id, RACE));

alter table person_race add constraint PER_RACE_FK foreign key (person_id) references Person;

ALTER TABLE personcr ADD SEX varchar(255);
ALTER TABLE personcr ADD birthDate timestamp;

create table personcr_ethnicgroup (per_cr_id int8 not null, ETHNIC_GROUP varchar(255) not null, primary key (per_cr_id, ETHNIC_GROUP));

alter table personcr_ethnicgroup add constraint PER_CR_EG_FK foreign key (per_cr_id) references Personcr;

create table personcr_race (person_id int8 not null, RACE varchar(255) not null, primary key (person_id, RACE));

alter table personcr_race add constraint PER_CR_RACE_FK foreign key (person_id) references Person;
