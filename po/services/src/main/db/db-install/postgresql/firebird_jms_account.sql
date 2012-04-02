INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('firebird', 'Firebird', 'Nci','BtM2GNbiAxg=');
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'firebird'), (select group_id from csm_group where group_name = 'Subscriber'));
