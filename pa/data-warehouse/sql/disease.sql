DROP TABLE IF EXISTS DW_STUDY_DISEASE;
CREATE TABLE DW_STUDY_DISEASE ( 
    CT_GOV_XML_INDICATOR character varying(3),
    DISEASE_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER,
    LEAD_DISEASE_INDICATOR character varying(3),
    NCI_ID character varying(255),
    PRIMARY KEY (INTERNAL_SYSTEM_ID)
);

INSERT INTO DW_STUDY_DISEASE (CT_GOV_XML_INDICATOR, DISEASE_CODE, INTERNAL_SYSTEM_ID, LEAD_DISEASE_INDICATOR, NCI_ID)
    SELECT 
        CASE WHEN sd.ctgovxml_indicator THEN 'YES'
             ELSE 'NO'
        END,
        disease.disease_code, sd.identifier, 
        CASE WHEN sd.lead_disease_indicator THEN 'YES'
             ELSE 'NO'
        END,
        nci_id.extension
    FROM STUDY_DISEASE sd
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sd.study_protocol_identifier 
            and nci_id.root = '2.16.840.1.113883.3.26.4.3'
        left outer join pdq_disease as disease on disease.identifier = sd.disease_identifier
        left outer join study_protocol as sp on sp.identifier = sd.study_protocol_identifier
    where sp.status_code = 'ACTIVE';

