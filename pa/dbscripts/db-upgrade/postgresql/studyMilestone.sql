DROP TABLE IF EXISTS STUDY_MILESTONE;

CREATE TABLE STUDY_MILESTONE (
    IDENTIFIER SERIAL NOT NULL,
    COMMENT_TEXT VARCHAR(200),
    MILESTONE_CODE VARCHAR(200),
    MILESTONE_DATE TIMESTAMP,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER) 
);

ALTER TABLE STUDY_MILESTONE ADD CONSTRAINT FK_STUDY_MILESTONE_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

