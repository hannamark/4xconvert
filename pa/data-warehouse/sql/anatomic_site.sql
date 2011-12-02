DROP TABLE IF EXISTS DW_STUDY_ANATOMIC_SITE;
CREATE TABLE DW_STUDY_ANATOMIC_SITE (
    ANATOMIC_SITE_NAME character varying(1000),
    NCI_ID character varying(255)
);

CREATE INDEX DW_STUDY_ANATOMIC_SITE_ANATOMIC_SITE_NAME_IDX on dw_study_anatomic_site(anatomic_site_name);
CREATE INDEX DW_STUDY_ANATOMIC_SITE_NCI_ID_IDX on dw_study_anatomic_site(nci_id);

INSERT INTO DW_STUDY_ANATOMIC_SITE (
    ANATOMIC_SITE_NAME,
    NCI_ID
) select asite.display_name, nci_id.extension
    from study_anatomic_site sas
        inner join study_protocol as sp on sp.identifier = sas.study_protocol_identifier
        inner join anatomic_sites as asite on asite.identifier = sas.anatomic_sites_identifier 
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sas.study_protocol_identifier
                and nci_id.root = '2.16.840.1.113883.3.26.4.3'
    where sp.status_code = 'ACTIVE';
