DROP TABLE IF EXISTS DW_STUDY_GRANT;
CREATE TABLE DW_STUDY_GRANT (
    ACTIVE_INDICATOR character varying(3),
    FUNDING_MECHANISM_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIVISION_OR_PROGRAM character varying(200),
    NCI_ID character varying(255),
    NIH_INSTITUTION_CODE character varying(200),
    SERIAL_NUMBER character varying(6),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

INSERT INTO DW_STUDY_GRANT (
    ACTIVE_INDICATOR,
    FUNDING_MECHANISM_CODE,
    INTERNAL_SYSTEM_ID,
    NCI_DIVISION_OR_PROGRAM,
    NCI_ID,
    NIH_INSTITUTION_CODE,
    SERIAL_NUMBER
) select 
    CASE WHEN g.active_indicator THEN 'YES'
         ELSE 'NO'
    END,
    g.funding_mechanism_code, g.identifier, g.nci_division_program_code,
         nci_id.extension, g.nih_institute_code, g.serial_number
         from STUDY_RESOURCING g
            inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = g.study_protocol_identifier
                and nci_id.root = '2.16.840.1.113883.3.26.4.3'
         where g.summ_4_rept_indicator is false;
