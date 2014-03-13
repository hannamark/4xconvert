--Author   : Anubhav Das
--Date     : 02/17/2014        
--Jira#    : PO-6780 - Support for alternate trial titles 
--Comments : Adds study alternate title table.
DROP TABLE IF EXISTS STUDY_ALTERNATE_TITLE;

CREATE TABLE STUDY_ALTERNATE_TITLE (
    IDENTIFIER SERIAL NOT NULL,
    ALTERNATE_TITLE VARCHAR(4000) NOT NULL,
    CATEGORY VARCHAR(200) NOT NULL,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_CREATED_ID INTEGER,    
    USER_LAST_UPDATED_ID INTEGER,
    PRIMARY KEY (IDENTIFIER)
);

ALTER TABLE STUDY_ALTERNATE_TITLE ADD CONSTRAINT FK_STUDY_ALTERNATE_TITLE_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_ALTERNATE_TITLE ADD CONSTRAINT FK_STUDY_ALTERNATE_TITLE_CREATED_CSM_USER
FOREIGN KEY (USER_LAST_CREATED_ID) REFERENCES CSM_USER (USER_ID) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE STUDY_ALTERNATE_TITLE ADD CONSTRAINT FK_STUDY_ALTERNATE_TITLE_UPDATED_CSM_USER
FOREIGN KEY (USER_LAST_UPDATED_ID) REFERENCES CSM_USER (USER_ID) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE INDEX STUDY_ALTERNATE_TITLE_STUDY_PROTOCOL_IDX ON STUDY_ALTERNATE_TITLE USING btree (STUDY_PROTOCOL_IDENTIFIER);

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'studyAlternateTitleTypes','Spelling/Formatting Correction, Other');