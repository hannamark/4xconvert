--Adding test registry users
insert into csm_user(login_name, first_name, last_name) values ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor-ci', 'Abstractor', 'Selenium');
insert into registry_user(identifier, first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values(nextval('hibernate_sequence'), 'Abstractor', 'User', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'National Cancer Institute Division of Cancer Prevention', (select user_id from csm_user where login_name like '%CN=abstractor-ci'), 3, 'ADMIN', 'abstractor-ci@example.com');
        
insert into csm_user(login_name, first_name, last_name) values ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=submitter-ci', 'Submitter', 'CI');
insert into registry_user(identifier, first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values(nextval('hibernate_sequence'), 'Submitter', 'CI', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'National Cancer Institute Division of Cancer Prevention', (select user_id from csm_user where login_name like '%CN=submitter-ci'), 3, 'MEMBER', 'submitter-ci@example.com');
        
insert into csm_user(login_name, first_name, last_name) values ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=ctrpsubstractor', 'ctrpsubstractor', 'CI');
insert into registry_user(identifier, first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values(nextval('hibernate_sequence'), 'ctrpsubstractor', 'CI', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'National Cancer Institute Division of Cancer Prevention', (select user_id from csm_user where login_name like '%CN=ctrpsubstractor'), 3, 'MEMBER', 'ctrpsubstractor-ci@example.com');        
        
update csm_remote_group set grid_grouper_url='https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper' where grid_grouper_url='@gridgrouper.url@';
update csm_remote_group set grid_grouper_group_name='COPPA:PA:ScientificAbstractor' where grid_grouper_group_name='@gridgrouper.abstractor.group.stem@:ScientificAbstractor';
update csm_remote_group set grid_grouper_group_name='COPPA:PA:AdminAbstractor' where grid_grouper_group_name='@gridgrouper.abstractor.group.stem@:AdminAbstractor';
INSERT INTO csm_remote_group (group_id,application_id,grid_grouper_url,grid_grouper_group_name) VALUES ((select group_id from csm_group where group_name='SuAbstractor'),(select application_id from csm_application where application_name='pa'),'https://grouper.training.cagrid.org:8443/wsrf/services/cagrid/GridGrouper','COPPA:PA:SuAbstractor');

INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor-ci'), (select group_id from csm_group where group_name = 'Abstractor'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor-ci'), (select group_id from csm_group where group_name = 'Submitter'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=submitter-ci'), (select group_id from csm_group where group_name = 'Submitter'));
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = '/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=ctrpsubstractor'), (select group_id from csm_group where group_name = 'SuAbstractor'));

        
