-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('curator', 'PO', 'Curator','BtM2GNbiAxg=');
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES (2, 2);

INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID) VALUES ('Curator', 'Curator group - security role', (select application_id from csm_application where application_name = 'po'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES (2, 1);

-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('subscriber', 'Test', 'Subscriber','BtM2GNbiAxg=');
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'po'), (select user_id from csm_user where login_name = 'subscriber'));

INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID) VALUES ('Subscriber', 'Topic Subscriber group - security role', (select application_id from csm_application where application_name = 'po'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'subscriber'), (select group_id from csm_group where group_name = 'Subscriber'));

-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('publisher', 'Test', 'Publisher','BtM2GNbiAxg=');
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'po'), (select user_id from csm_user where login_name = 'publisher'));

INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID) VALUES ('Publisher', 'Topic Publisher group - security role', (select application_id from csm_application where application_name = 'po'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'publisher'), (select group_id from csm_group where group_name = 'Publisher'));

-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('ejbclient', 'Test', 'EJBClient','BtM2GNbiAxg=');
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'po'), (select user_id from csm_user where login_name = 'ejbclient'));

INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID) VALUES ('client', 'remote client group - security role', (select application_id from csm_application where application_name = 'po'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'ejbclient'), (select group_id from csm_group where group_name = 'client'));

-- password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('firebird-nci', 'Firebird', 'Nci','BtM2GNbiAxg=');
INSERT INTO CSM_USER_PE(PROTECTION_ELEMENT_ID, USER_ID) VALUES ((select protection_element_id from csm_protection_element where protection_element_name = 'po'), (select user_id from csm_user where login_name = 'firebird-nci'));

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'firebird-nci'), (select group_id from csm_group where group_name = 'client'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'firebird-nci'), (select group_id from csm_group where group_name = 'Subscriber'));

-- create a grid user for web authentication against grid. 
INSERT INTO CSM_USER (LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=curator','PO','Curator','');
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=curator'), (select group_id from csm_group where group_name = 'Curator'));
