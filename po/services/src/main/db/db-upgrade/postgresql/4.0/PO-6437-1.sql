INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID) VALUES ('test', 'test', (select application_id from csm_application where application_name = 'po'));

insert into CSM_REMOTE_GROUP

    select (select group_id from csm_group where group_name = 'test'), (select application_id from csm_application where application_name = 'po'), 'https://cagrid-gridgrouper-qa.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PO:GridClient'
    where not exists (
        select * from CSM_REMOTE_GROUP where group_id=(select group_id from csm_group where group_name = 'test')
    );  