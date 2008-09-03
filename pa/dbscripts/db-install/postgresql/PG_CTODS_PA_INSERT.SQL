DELETE FROM study_protocol
/
INSERT INTO study_protocol 
    (
        id, acronym,allocation_code,accr_rept_meth_code,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, date_last_updated, user_last_updated
    ) 
    VALUES ( 1, 'Acronym', null, null,null,'NCI-2008-0001','CCR','CANCER FOR KIDS ','I', NULL, NULL, NULL, NULL, NULL, NULL )
/

INSERT INTO study_protocol 
    (
        id, acronym,allocation_code,accr_rept_meth_code,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, date_last_updated, user_last_updated
    ) 
    VALUES ( 2, 'Acronym 2', null, null,null,'NCI-2008-0002','CCR','CANCER FOR WOMEN ','I', NULL, NULL, NULL, NULL, NULL, NULL )
/

COMMIT
/


