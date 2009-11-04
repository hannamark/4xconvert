-- the initial verison of PO-1292.sql will fail in production, so this script will undo what the broken version did to prepare for the fixed version to run
delete from CSM_USER_GROUP where group_id = (select group_id from csm_group where group_name = 'gridClient');
delete from csm_user where login_name='/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=coppagridtest';
delete from csm_group where group_name='gridClient';
