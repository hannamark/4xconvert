update csm_user set premgrt_login_name=login_name, login_name='ctepecm'  where login_name like '%CN=ctepecm';
delete from csm_remote_group_sync_record;
delete from csm_remote_group;

INSERT INTO csm_group (group_name,group_desc,update_date,application_id) VALUES ('SecurityAdmin', 'Security administrators', now(),
    (select application_id from csm_application where application_name='po'));