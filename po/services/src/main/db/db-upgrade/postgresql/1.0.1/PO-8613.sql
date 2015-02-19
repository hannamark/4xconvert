INSERT INTO CSM_USER (LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('jdoe01','','','BtM2GNbiAxg=');
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'jdoe01'), (select group_id from csm_group where group_name = 'Curator'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'jdoe01'), (select group_id from csm_group where group_name = 'gridClient'));

INSERT INTO CSM_USER (LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('CTRPQATester1','CTRPQATester1','CI','BtM2GNbiAxg=');
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'CTRPQATester1'), (select group_id from csm_group where group_name = 'Curator'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'CTRPQATester1'), (select group_id from csm_group where group_name = 'gridClient'));


INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'curator'), (select group_id from csm_group where group_name = 'Curator'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'curator'), (select group_id from csm_group where group_name = 'gridClient'));



