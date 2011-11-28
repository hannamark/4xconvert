DROP TABLE IF EXISTS DW_STUDY_IND_IDE;
CREATE TABLE DW_STUDY_IND_IDE (
    DATE_LAST_CREATED date,
    DATE_LAST_UPDATED date,
    EXPANDED_ACCESS_INDICATOR character varying(3),
    EXPANDED_ACCESS_STATUS_CODE character varying(200),
    GRANTOR_CODE character varying(200),
    HOLDER_TYPE_CODE character varying(200),
    IND_IDE_NUMBER character varying(200),
    IND_IDE_TYPE_CODE character varying(200),
    INTERNAL_SYSTEM_ID INTEGER not null,
    NCI_DIV_PROG_HOLDER_CODE character varying(200),
    NCI_ID character varying(255),
    NIH_INSTHOLDER_CODE character varying(200),
    USER_LAST_CREATED character varying(500),
    USER_LAST_UPDATED character varying(500),
    PRIMARY KEY(INTERNAL_SYSTEM_ID)
);

INSERT INTO DW_STUDY_IND_IDE (
    DATE_LAST_CREATED,
    DATE_LAST_UPDATED,
    EXPANDED_ACCESS_INDICATOR,
    EXPANDED_ACCESS_STATUS_CODE,
    GRANTOR_CODE,
    HOLDER_TYPE_CODE,
    IND_IDE_NUMBER,
    IND_IDE_TYPE_CODE,
    INTERNAL_SYSTEM_ID,
    NCI_DIV_PROG_HOLDER_CODE,
    NCI_ID,
    NIH_INSTHOLDER_CODE,
    USER_LAST_CREATED,
    USER_LAST_UPDATED
) select indide.date_last_created, indide.date_last_updated,
    CASE WHEN indide.expanded_access_indicator THEN 'YES'
         ELSE 'NO'
    END,
    indide.expanded_access_status_code, indide.grantor_code, indide.holder_type_code, indide.indlde_number,
    indide.indlde_type_code, indide.identifier, indide.nci_div_prog_holder_code, nci_id.extension, 
    indide.nih_inst_holder_code,
    CASE WHEN ru_creator.first_name is null THEN split_part(creator.login_name, 'CN=', 2)
        ELSE ru_creator.first_name || ' ' || ru_creator.last_name
    END,
    CASE WHEN ru_updater.first_name is null THEN split_part(updater.login_name, 'CN=', 2)
        ELSE ru_updater.first_name || ' ' || ru_updater.last_name
    END
    from STUDY_INDLDE indide 
        inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = indide.study_protocol_identifier
                and nci_id.root = '2.16.840.1.113883.3.26.4.3'
        left outer join csm_user as creator on indide.user_last_created_id = creator.user_id
        left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
        left outer join csm_user as updater on indide.user_last_created_id = updater.user_id
        left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id;