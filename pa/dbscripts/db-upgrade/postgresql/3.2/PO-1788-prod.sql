insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Abstractor'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:Abstractor');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Submitter'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:Submitter');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'RegAdmin'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:RegAdmin');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Subscriber'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:Subscriber');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Publisher'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:Publisher');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'Outcomes'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:Outcomes');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'client'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:Client');
insert into CSM_REMOTE_GROUP (GROUP_ID, APPLICATION_ID, GRID_GROUPER_URL, GRID_GROUPER_GROUP_NAME) values ((select group_id from csm_group where group_name = 'gridClient'), (select application_id from csm_application where application_name = 'pa'), 'https://cagrid-gridgrouper-prod.nci.nih.gov:8443/wsrf/services/cagrid/GridGrouper', 'Organization:CBIIT:CTRP-COPPA:PA:GridClient');