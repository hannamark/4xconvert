insert into CSM_REMOTE_GROUP

    select (select group_id from csm_group where group_name = 'Curator'), (select application_id from csm_application where application_name = 'po'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PO:Curator'
    where not exists (
        select * from CSM_REMOTE_GROUP where group_id=(select group_id from csm_group where group_name = 'Curator')
    );

insert into CSM_REMOTE_GROUP

    select (select group_id from csm_group where group_name = 'gridClient'), (select application_id from csm_application where application_name = 'po'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PO:GridClient'
    where not exists (
        select * from CSM_REMOTE_GROUP where group_id=(select group_id from csm_group where group_name = 'gridClient')
    );  