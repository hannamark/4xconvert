DROP TABLE IF EXISTS DW_GENERIC_CONTACT;
CREATE TABLE DW_GENERIC_CONTACT (
    IDENTIFIER INTEGER,
    TITLE character varying(255),
    PRIMARY KEY (IDENTIFIER)
);