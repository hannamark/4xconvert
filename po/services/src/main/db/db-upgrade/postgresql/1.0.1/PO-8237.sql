drop table if exists ctepremotecallsrecord;

CREATE TABLE ctepremotecallsrecord
(   
   id bigint PRIMARY KEY NOT NULL,
   operation varchar(64),
   arguments varchar(1024),
   result varchar(8192),
   created_date timestamp
);