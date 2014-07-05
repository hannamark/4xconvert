CREATE OR REPLACE FUNCTION populate_createdby3() RETURNS VOID AS $$
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
     
	 -- Step 1: Create a temp table 'auditlogrecord_temp2'
     CREATE TABLE auditlogrecord_temp2
        (
        id bigint NOT NULL,
        username character varying(100) NOT NULL,
        entityname character varying(254) NOT NULL,
        entityid bigint NOT NULL,
        createddate timestamp without time zone NOT NULL,
        transactionid bigint NOT NULL,
        type character varying(255) NOT NULL
        );

    CREATE INDEX auditlog_temp_index ON auditlogrecord_temp2 USING btree (entityid);

    -- Step 2: Load data into 'auditlogrecord_temp2' from 'auditlogrecord' table
    insert into auditlogrecord_temp2 (id,type,username,entityname,entityid,createddate,transactionid)
	select id,type,username,entityname,entityid,createddate,transactionid from auditlogrecord where type ='INSERT' 
	and entityname in ('Organization','HealthCareFacility','ResearchOrganization','OversightCommittee','IdentifiedOrganization','Person','ClinicalResearchStaff','HealthCareProvider','OrganizationalContact','IdentifiedPerson');

    if exists(select * from information_schema.tables where table_name = 'auditlogrecord_022713') then
        insert into auditlogrecord_temp2 (id,type,username,entityname,entityid,createddate,transactionid)
        select id,type,username,entityname,entityid,createddate,transactionid from auditlogrecord_022713 where type ='INSERT' 
        and entityname in ('Organization','HealthCareFacility','ResearchOrganization','OversightCommittee','IdentifiedOrganization','Person','ClinicalResearchStaff','HealthCareProvider','OrganizationalContact','IdentifiedPerson');
    end if;


    -- Step3: update the records to have 'createdBy' set
        
        -- update 'Organization'
        FOR org_id IN SELECT id FROM organization WHERE created_by_id is null LOOP              
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = org_id );
            UPDATE organization SET created_by_id = created_by_user_id WHERE id = org_id;   
        END LOOP; 

        -- update 'HealthCareFacility'
        FOR hcf_id IN SELECT id FROM healthcarefacility WHERE created_by_id is null LOOP                
            SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = hcf_id );
            UPDATE healthcarefacility SET created_by_id = created_by_user_id WHERE id = hcf_id; 
        END LOOP;   

        -- update 'ResearchOrganization'
        FOR ro_id IN SELECT id FROM researchorganization WHERE created_by_id is null LOOP               
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = ro_id );
           UPDATE researchorganization SET created_by_id = created_by_user_id WHERE id = ro_id;    
        END LOOP;   

        -- update 'OversightCommittee'
        FOR over_comm_id IN SELECT id FROM oversightcommittee WHERE created_by_id is null LOOP              
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = over_comm_id );
           UPDATE oversightcommittee SET created_by_id = created_by_user_id WHERE id = over_comm_id;   
        END LOOP;

        -- update 'IdentifiedOrganization'
        FOR io_id IN SELECT id FROM identifiedorganization WHERE created_by_id is null LOOP             
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = io_id );
           UPDATE identifiedorganization SET created_by_id = created_by_user_id WHERE id = io_id;  
        END LOOP; 

        -- update 'Person'
        FOR per_id IN SELECT id FROM person WHERE created_by_id is null LOOP                
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = per_id);
           UPDATE person SET created_by_id = created_by_user_id WHERE id = per_id; 
        END LOOP;

        -- update 'ClinicalResearchStaff'
        FOR crs_id IN SELECT id FROM clinicalresearchstaff WHERE created_by_id is null LOOP             
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = crs_id );
           UPDATE clinicalresearchstaff SET created_by_id = created_by_user_id WHERE id = crs_id;  
        END LOOP;

        -- update 'HealthCareProvider'
        FOR hcp_id IN SELECT id FROM healthcareprovider WHERE created_by_id is null LOOP                
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = hcp_id);
           UPDATE healthcareprovider SET created_by_id = created_by_user_id WHERE id = hcp_id; 
        END LOOP;

        -- update 'OrganizationalContact'
        FOR org_cont_id IN SELECT id FROM organizationalcontact WHERE created_by_id is null LOOP                
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = org_cont_id);
           UPDATE organizationalcontact SET created_by_id = created_by_user_id WHERE id = org_cont_id; 
        END LOOP;

        -- update 'IdentifiedPerson'
        FOR ip_id IN SELECT id FROM identifiedperson WHERE created_by_id is null LOOP               
           SELECT INTO created_by_user_id user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp2 WHERE entityid = ip_id);
           UPDATE identifiedperson SET created_by_id = created_by_user_id WHERE id = ip_id;    
        END LOOP;

        
    -- Step4: Drop the temp table
        drop table auditlogrecord_temp2;
           
    END;
$$ LANGUAGE plpgsql;

select populate_createdby3();

-- drop this functions as this is meant for temporary used only
DROP FUNCTION populate_createdby3();