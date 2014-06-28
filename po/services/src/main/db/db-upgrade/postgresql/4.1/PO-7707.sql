-- Create 'CTEP ECM' user in the database if it not present and if present then update its first name & last name.
CREATE OR REPLACE FUNCTION insert_ctep_user() RETURNS VOID AS $$
 	BEGIN
    	IF NOT EXISTS (SELECT * FROM csm_user WHERE  login_name ='/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=ctepecm') THEN
			INSERT INTO CSM_USER (LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD) VALUES ('/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=ctepecm','CTEP','ECM','');
		ELSE
		    UPDATE CSM_USER SET FIRST_NAME ='CTEP',  LAST_NAME = 'ECM' WHERE login_name ='/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=ctepecm';
     END IF;
  	END;
$$ LANGUAGE plpgsql;

select insert_ctep_user();


-- Update the database tables to populate 'createdBy' for CTEP created Org/roles
CREATE OR REPLACE FUNCTION populate_createdby_ctep() RETURNS VOID AS $$
	DECLARE    		
	    ctep_user_id INTEGER;
	    ctep_org_id INTEGER;
	    hcf_io_record RECORD;
	    ro_io_record RECORD;
	    iden_org_record RECORD;
	    orgcont_io_record RECORD;
	    overcomm_io_record RECORD;	    
	BEGIN
	    SELECT INTO ctep_user_id user_id FROM CSM_USER WHERE LOGIN_NAME = '/O=caBIG/OU=caGrid/OU=Training/OU=National Cancer Institute/CN=ctepecm';
	    SELECT INTO ctep_org_id id FROM organization where name = 'Cancer Therapy Evaluation Program' and status <> 'NULLIFIED';
	    
	    -- update HCF & related Organization
	    FOR hcf_io_record IN SELECT * FROM hcf_otheridentifier WHERE root='2.16.840.1.113883.3.26.6.2' LOOP	        
		UPDATE healthcarefacility SET created_by_id = ctep_user_id WHERE created_by_id is null and id = hcf_io_record.hcf_id;
		UPDATE organization SET created_by_id = ctep_user_id WHERE created_by_id is null and id = (SELECT player_id from healthcarefacility WHERE id = hcf_io_record.hcf_id);
	    END LOOP;
	    
	    -- update RO & related Organization
	    FOR ro_io_record IN SELECT * FROM ro_otheridentifier WHERE root='2.16.840.1.113883.3.26.6.2' LOOP	        
		UPDATE researchorganization SET created_by_id = ctep_user_id WHERE created_by_id is null and id = ro_io_record.ro_id;
		UPDATE organization SET created_by_id = ctep_user_id WHERE created_by_id is null and id = (SELECT player_id from researchorganization WHERE id = ro_io_record.ro_id);
	    END LOOP;
	    
	    -- update Identified Organization & related Organization
	    FOR iden_org_record IN SELECT * FROM identifiedorganization WHERE assigned_identifier_root='2.16.840.1.113883.3.26.6.2' and scoper_id = ctep_org_id LOOP	        
		UPDATE identifiedorganization SET created_by_id = ctep_user_id WHERE created_by_id is null and id = iden_org_record.id;
		UPDATE organization SET created_by_id = ctep_user_id WHERE created_by_id is null and id = (SELECT player_id from identifiedorganization WHERE id = iden_org_record.id);
	    END LOOP;
	    
	END;
$$ LANGUAGE plpgsql;

select populate_createdby_ctep();

-- drop these functions as these are meant for temporary used only
DROP FUNCTION insert_ctep_user();
DROP FUNCTION populate_createdby_ctep();


