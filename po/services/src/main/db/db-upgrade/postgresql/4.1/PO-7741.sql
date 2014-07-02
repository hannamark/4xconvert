CREATE OR REPLACE FUNCTION populate_createdby() RETURNS VOID AS $$
    DECLARE    
        created_by_user_id INTEGER;             
        org_id INTEGER; 
        hcf_id INTEGER;
        ro_id INTEGER;
        over_comm_id INTEGER;
        io_id INTEGER;
        per_id INTEGER;
        crs_id INTEGER;
        hcp_id INTEGER;
        org_cont_id INTEGER;
        ip_id INTEGER;      
                   
    BEGIN               
        -- Step 1: Read data from 'auditlogrecord' table
            
        -- update 'Organization'
        FOR org_id IN SELECT id FROM organization WHERE created_by_id is null LOOP              
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = org_id AND entityname ='Organization' AND type ='INSERT');
            UPDATE organization SET created_by_id = created_by_user_id WHERE id = org_id;   
        END LOOP; 

        -- update 'HealthCareFacility'
        FOR hcf_id IN SELECT id FROM healthcarefacility WHERE created_by_id is null LOOP                
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = hcf_id AND entityname ='HealthCareFacility' AND type ='INSERT');
            UPDATE healthcarefacility SET created_by_id = created_by_user_id WHERE id = hcf_id; 
        END LOOP;   

        -- update 'ResearchOrganization'
        FOR ro_id IN SELECT id FROM researchorganization WHERE created_by_id is null LOOP               
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = ro_id AND entityname ='ResearchOrganization' AND type ='INSERT');
           UPDATE researchorganization SET created_by_id = created_by_user_id WHERE id = ro_id;    
        END LOOP;   

        -- update 'OversightCommittee'
        FOR over_comm_id IN SELECT id FROM oversightcommittee WHERE created_by_id is null LOOP              
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = over_comm_id AND entityname ='OversightCommittee' AND type ='INSERT');
           UPDATE oversightcommittee SET created_by_id = created_by_user_id WHERE id = over_comm_id;   
        END LOOP;

        -- update 'IdentifiedOrganization'
        FOR io_id IN SELECT id FROM identifiedorganization WHERE created_by_id is null LOOP             
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = io_id AND entityname ='IdentifiedOrganization' AND type ='INSERT');
           UPDATE identifiedorganization SET created_by_id = created_by_user_id WHERE id = io_id;  
        END LOOP; 

        -- update 'Person'
        FOR per_id IN SELECT id FROM person WHERE created_by_id is null LOOP                
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = per_id AND entityname ='Person' AND type ='INSERT');
           UPDATE person SET created_by_id = created_by_user_id WHERE id = per_id; 
        END LOOP;

        -- update 'ClinicalResearchStaff'
        FOR crs_id IN SELECT id FROM clinicalresearchstaff WHERE created_by_id is null LOOP             
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = crs_id AND entityname ='ClinicalResearchStaff' AND type ='INSERT');
           UPDATE clinicalresearchstaff SET created_by_id = created_by_user_id WHERE id = crs_id;  
        END LOOP;

        -- update 'HealthCareProvider'
        FOR hcp_id IN SELECT id FROM healthcareprovider WHERE created_by_id is null LOOP                
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = hcp_id AND entityname ='HealthCareProvider' AND type ='INSERT');
           UPDATE healthcareprovider SET created_by_id = created_by_user_id WHERE id = hcp_id; 
        END LOOP;

        -- update 'OrganizationalContact'
        FOR org_cont_id IN SELECT id FROM organizationalcontact WHERE created_by_id is null LOOP                
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = org_cont_id AND entityname ='OrganizationalContact' AND type ='INSERT');
           UPDATE organizationalcontact SET created_by_id = created_by_user_id WHERE id = org_cont_id; 
        END LOOP;

        -- update 'IdentifiedPerson'
        FOR ip_id IN SELECT id FROM identifiedperson WHERE created_by_id is null LOOP               
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord WHERE entityid = ip_id AND entityname ='IdentifiedPerson' AND type ='INSERT');
           UPDATE identifiedperson SET created_by_id = created_by_user_id WHERE id = ip_id;    
        END LOOP;


        -- Step 2: Read data from 'auditlogrecord_022713' table     
        if exists(select * from information_schema.tables where table_name = 'auditlogrecord_022713') then

        -- update 'Organization'
        FOR org_id IN SELECT id FROM organization WHERE created_by_id is null LOOP              
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = org_id AND entityname ='Organization' AND type ='INSERT');
            UPDATE organization SET created_by_id = created_by_user_id WHERE id = org_id;   
            END LOOP;

        -- update 'HealthCareFacility'
        FOR hcf_id IN SELECT id FROM healthcarefacility WHERE created_by_id is null LOOP                
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = hcf_id AND entityname ='HealthCareFacility' AND type ='INSERT');
            UPDATE healthcarefacility SET created_by_id = created_by_user_id WHERE id = hcf_id; 
        END LOOP;

        -- update 'ResearchOrganization'
        FOR ro_id IN SELECT id FROM researchorganization WHERE created_by_id is null LOOP               
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = ro_id AND entityname ='ResearchOrganization' AND type ='INSERT');
            UPDATE researchorganization SET created_by_id = created_by_user_id WHERE id = ro_id;    
        END LOOP;

        -- update 'OversightCommittee'
        FOR over_comm_id IN SELECT id FROM oversightcommittee WHERE created_by_id is null LOOP              
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = over_comm_id AND entityname ='OversightCommittee' AND type ='INSERT');
            UPDATE oversightcommittee SET created_by_id = created_by_user_id WHERE id = over_comm_id;   
        END LOOP;

        -- update 'IdentifiedOrganization'
        FOR io_id IN SELECT id FROM identifiedorganization WHERE created_by_id is null LOOP             
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = io_id AND entityname ='IdentifiedOrganization' AND type ='INSERT');
            UPDATE identifiedorganization SET created_by_id = created_by_user_id WHERE id = io_id;   
        END LOOP; 

        -- update 'Person'
        FOR per_id IN SELECT id FROM person WHERE created_by_id is null LOOP                
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = per_id AND entityname ='Person' AND type ='INSERT');
            UPDATE person SET created_by_id = created_by_user_id WHERE id = per_id;  
        END LOOP;

        -- update 'ClinicalResearchStaff'
        FOR crs_id IN SELECT id FROM clinicalresearchstaff WHERE created_by_id is null LOOP             
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = crs_id AND entityname ='ClinicalResearchStaff' AND type ='INSERT');
            UPDATE clinicalresearchstaff SET created_by_id = created_by_user_id WHERE id = crs_id;   
        END LOOP;

        -- update 'HealthCareProvider'
        FOR hcp_id IN SELECT id FROM healthcareprovider WHERE created_by_id is null LOOP                
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = hcp_id AND entityname ='HealthCareProvider' AND type ='INSERT');
            UPDATE healthcareprovider SET created_by_id = created_by_user_id WHERE id = hcp_id;  
        END LOOP;

        -- update 'OrganizationalContact'
        FOR org_cont_id IN SELECT id FROM organizationalcontact WHERE created_by_id is null LOOP                
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = org_cont_id AND entityname ='OrganizationalContact' AND type ='INSERT');         
            UPDATE organizationalcontact SET created_by_id = created_by_user_id WHERE id = org_cont_id;  
        END LOOP;

        -- update 'IdentifiedPerson'
        FOR ip_id IN SELECT id FROM identifiedperson WHERE created_by_id is null LOOP               
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_022713 WHERE entityid = ip_id AND entityname ='IdentifiedPerson' AND type ='INSERT');          
            UPDATE identifiedperson SET created_by_id = created_by_user_id WHERE id = ip_id; 
        END LOOP;
        
        end if;
           
    END;
$$ LANGUAGE plpgsql;

select populate_createdby();

-- drop this functions as this is meant for temporary used only
DROP FUNCTION populate_createdby();