ALTER TABLE MAPPING_IDENTIFIER ADD CONSTRAINT FK_STUDY_PROTOCOL_MAPPING_IDENTIFIER
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE ;