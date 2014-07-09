CREATE TEMPORARY TABLE auditlogrecord_temp (LIKE auditlogrecord INCLUDING DEFAULTS);
CREATE TABLE IF NOT EXISTS auditlogrecord_022713 (LIKE auditlogrecord INCLUDING DEFAULTS);
    
    
insert into auditlogrecord_temp (id,type,username,entityname,entityid,createddate,transactionid)
	select id,type,replace(username,'pauser||',''),entityname,entityid,createddate,transactionid from auditlogrecord where type ='INSERT' 
	   and entityname in ('Organization','HealthCareFacility','ResearchOrganization','OversightCommittee','IdentifiedOrganization','Person','ClinicalResearchStaff','HealthCareProvider','OrganizationalContact','IdentifiedPerson');


insert into auditlogrecord_temp (id,type,username,entityname,entityid,createddate,transactionid)
   select id,type,replace(username,'pauser||',''),entityname,entityid,createddate,transactionid from auditlogrecord_022713 where type ='INSERT' 
    and entityname in ('Organization','HealthCareFacility','ResearchOrganization','OversightCommittee','IdentifiedOrganization','Person','ClinicalResearchStaff','HealthCareProvider','OrganizationalContact','IdentifiedPerson');
    
update auditlogrecord_temp set username='/o=cabig/ou=cagrid/ou=loa1/ou=nci/cn=brucheyks' where username like '%brucheyk%';      
update auditlogrecord_temp set username='/o=cabig/ou=cagrid/ou=loa1/ou=nci/cn=mamuadr' where username like '%mamuadr%';
update auditlogrecord_temp set username='/o=cabig/ou=cagrid/ou=loa1/ou=nci/cn=macyba' where username like '%macyb%';

CREATE INDEX auditlog_temp_index01 ON auditlogrecord_temp (entityid, entityname);    
    
UPDATE organization o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='Organization'  LIMIT 1) LIMIT 1) 
        WHERE created_by_id is null;   
        
UPDATE healthcarefacility o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='HealthCareFacility' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         
 
        
UPDATE researchorganization o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='ResearchOrganization' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         
 
UPDATE oversightcommittee o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='OversightCommittee' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         

UPDATE identifiedorganization o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='IdentifiedOrganization' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         
     
UPDATE person o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='Person' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         

    
UPDATE clinicalresearchstaff o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='ClinicalResearchStaff' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         

UPDATE healthcareprovider o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='HealthCareProvider' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;         
   
UPDATE organizationalcontact o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='OrganizationalContact' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;  
       
UPDATE identifiedperson o SET created_by_id = 
    (SELECT user_id FROM CSM_USER WHERE LOWER(LOGIN_NAME) = (SELECT LOWER(username) FROM auditlogrecord_temp WHERE entityid = o.id and entityname='IdentifiedPerson' LIMIT 1 ) LIMIT 1) 
        WHERE created_by_id is null;