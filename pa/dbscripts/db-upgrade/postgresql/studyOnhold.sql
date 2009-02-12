DROP TABLE IF EXISTS STUDY_ONHOLD;

CREATE TABLE STUDY_ONHOLD (
    IDENTIFIER SERIAL NOT NULL,
    ONHOLD_REASON_TEXT VARCHAR(200),
    ONHOLD_REASON_CODE VARCHAR(200),
    ONHOLD_DATE TIMESTAMP,
    OFFHOLD_DATE TIMESTAMP,
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    DATE_LAST_CREATED TIMESTAMP,
    USER_LAST_CREATED VARCHAR(200),
    DATE_LAST_UPDATED TIMESTAMP,
    USER_LAST_UPDATED VARCHAR(200),
    PRIMARY KEY (IDENTIFIER) 
);

ALTER TABLE STUDY_ONHOLD ADD CONSTRAINT FK_STUDY_ONHOLD_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;