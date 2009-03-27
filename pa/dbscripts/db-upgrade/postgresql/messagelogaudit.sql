DROP TABLE messages_log_audit;

CREATE TABLE messages_log_audit
(
  identifier serial NOT NULL,
  study_protocol_identifier bigint,
  message_log_identifier bigint,
  date_last_created timestamp without time zone,
  user_last_created character varying(200),
  date_last_updated timestamp without time zone,
  user_last_updated character varying(200),
  CONSTRAINT messages_log_audit_pkey PRIMARY KEY (identifier),
  CONSTRAINT fk_message_log_identifier_sp FOREIGN KEY (message_log_identifier)
      REFERENCES messages_log (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)