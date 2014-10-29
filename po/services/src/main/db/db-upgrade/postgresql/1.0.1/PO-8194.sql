drop table if exists jms_messages;

CREATE TABLE jms_messages
(   
   id bigint PRIMARY KEY NOT NULL,
   msg varchar(4096),
   created_date timestamp
);