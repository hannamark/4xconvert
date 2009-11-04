INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID) VALUES ('gridClient', 'Grid Service Invocation Group', (select application_id from csm_application where application_name = 'po'));
