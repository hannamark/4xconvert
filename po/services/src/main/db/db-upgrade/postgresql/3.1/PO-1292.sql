INSERT INTO csm_group (group_id, group_name, group_desc, update_date, application_id) VALUES (5, 'gridClient', 'Grid Service Invocation Group', now(), 2);

--adding /O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=coppagridtest as a grid client test user account  
INSERT INTO csm_user (user_id, login_name, first_name, last_name, organization, department, title, phone_number, password, email_id, start_date, end_date, update_date) values (7,'/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=coppagridtest','Test','GridClientUser','','','','','','',null,null,now());
INSERT INTO csm_user_group (user_group_id, user_id, group_id) Values (7, 7, 5);
