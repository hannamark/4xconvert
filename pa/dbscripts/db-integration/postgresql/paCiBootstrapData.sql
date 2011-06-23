--Adding test registry users
insert into csm_user(login_name, first_name, last_name) values ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=abstractor-ci', 'Abstractor', 'Selenium');
insert into registry_user(first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values('Abstractor', 'User', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'ClinicalTrials.gov', (select user_id from csm_user where login_name like '%CN=abstractor-ci'), 3, 'ADMIN', 'abstractor-ci@example.com');
        
insert into csm_user(login_name, first_name, last_name) values ('/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=submitter-ci', 'Submitter', 'CI');
insert into registry_user(first_name, last_name, address_line, city, state, postal_code, country, phone , affiliate_org, csm_user_id, affiliated_org_id, affiliated_org_user_type, email_address)
    values('Submitter', 'CI', '2115 E. Jefferson St.', 'North Bethesda', 'Maryland', '20852', 'USA', '123-456-7890', 
        'ClinicalTrials.gov', (select user_id from csm_user where login_name like '%CN=submitter-ci'), 3, 'MEMBER', 'submitter-ci@example.com');
