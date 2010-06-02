insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Abstractor'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Abstractor');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Submitter'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Submitter');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'RegAdmin'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:RegAdmin');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Subscriber'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Subscriber');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Publisher'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Publisher');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Outcomes'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Outcomes');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'client'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:Client');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'gridClient'), (select application_id from csm_application where application_name = 'pa'), 'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper', 'COPPA:PA:GridClient');