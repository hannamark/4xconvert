DELETE FROM study_protocol
;
DELETE FROM organization
;
DELETE FROM person
;
INSERT INTO study_protocol (id, identifier,official_title , study_protocol_type )  values ( 1, 'NCI-2008-00001','Interventional-NCI-2008-00001','InterventionalStudyProtocol' );
INSERT INTO study_protocol (id, identifier,official_title , study_protocol_type )  values ( 2, 'NCI-2008-00002','Observation-NCI-2008-00001','ObservationalStudyProtocol' );



--- insert into document workflow status

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (1,'ACCEPTED', NULL, '2008-01-11', 1, current_timestamp, 'abstractor' ) 
;

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (2,'ACCEPTED', NULL, '2008-02-21', 2, current_timestamp, 'abstractor' ) 
;



-- insert into study_overall_status 

insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (1, 'IN_REVIEW', '2008-03-11', 1, current_timestamp, 'abstractor' ) 
;
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (2, 'APPROVED', '2007-07-21', 2, current_timestamp, 'abstractor' ) 
;


--- organization insert
insert into organization (id, name, identifier, date_last_updated, user_last_updated ,status_code) 
values (1, 'Oraganization A', '1', current_timestamp, 'po' ,'ACTIVE') 
;

insert into organization (id, name, identifier, date_last_updated, user_last_updated ,status_code) 
values (2, 'Oraganization B', '2', current_timestamp, 'po' ,'ACTIVE') 
;

insert 
into organization (id, name, identifier, date_last_updated, user_last_updated  ,status_code) 
values (3, 'Oraganization C', '3', current_timestamp, 'po' ,'ACTIVE') 
;

insert into organization (id, name, identifier, date_last_updated, user_last_updated  ,status_code) 
values (4, 'Oraganization D', '4', current_timestamp, 'po' ,'ACTIVE') 
;
insert into organization (id, name, identifier, date_last_updated, user_last_updated  ,status_code) 
values (5, 'Oraganization E', '5', current_timestamp, 'po' ,'ACTIVE') 
;

---- healthcare_facility

insert into healthcare_facility (id, identifier, organization_id, date_last_updated, user_last_updated ,status_code) 
values (1, 'PO001', 1, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (id, identifier, organization_id, date_last_updated, user_last_updated,status_code) 
values (2, 'PO002', 2, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (id, identifier, organization_id, date_last_updated, user_last_updated ,status_code) 
values (3, 'PO003', 3, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (id, identifier, organization_id, date_last_updated, user_last_updated ,status_code) 
values (4, 'PO004', 3, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (id, identifier, organization_id, date_last_updated, user_last_updated ,status_code) 
values (5, 'PO005', 2, current_timestamp, 'po' ,'ACTIVE') 
;
insert into healthcare_facility (id, identifier, organization_id, date_last_updated, user_last_updated ,status_code) 
values (6, 'PO006', 1, current_timestamp, 'po' ,'ACTIVE') 
;



insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (1,'LEAD_ORAGANIZATION','CALGB-88612-A443',1, 1, 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;
 
insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id , status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (2,'LEAD_ORAGANIZATION','CCG-09032-A443',2 ,2,'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;



------ person -----------------

insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (1, '1', 'John','D', 'Doe', current_timestamp, 'po' ,'ACTIVE')  
;
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (2, '2', 'Mark','f', 'Smith', current_timestamp, 'po' ,'ACTIVE')  
;
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (3, '3', 'Mary','Ann', 'Jones', current_timestamp, 'po' ,'ACTIVE') 
;
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (4, '4', 'Jack',null, 'Nick', current_timestamp, 'po' ,'ACTIVE')  
;
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (5, '5', 'Samuel','k', 'Smith', current_timestamp, 'po' ,'ACTIVE')  
;

------- heacltcare provider ------

insert into healthcare_provider (id, person_id, organization_id, identifier , date_last_updated, user_last_updated, status_code) 
 values (1, 1,  1, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (id, person_id,  organization_id, identifier , date_last_updated, user_last_updated, status_code) 
 values (2, 2,  2, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (id, person_id,  organization_id, identifier , date_last_updated, user_last_updated, status_code) 
 values (3, 3,  3, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (id, person_id,  organization_id, identifier , date_last_updated, user_last_updated, status_code) 
 values (4, 4,  1, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (id, person_id,  organization_id, identifier , date_last_updated, user_last_updated , status_code)
 values (5, 1,  2, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (id, person_id,  organization_id, identifier , date_last_updated, user_last_updated, status_code) 
 values (6, 2,  3, 'po', current_timestamp, 'po' ,'ACTIVE')  
;



insert into CLINICAL_RESEARCH_STAFF (id, identifier, person_id, organization_id, date_last_updated, user_last_updated ,status_code) 
values (1, 'PO001', 1, 1, current_timestamp, 'po' ,'ACTIVE') 
;

--------------- study contact -----------

insert into study_contact (id,role_code,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ,CLINICAL_RESEARCH_STAFF_id) 
values(1, 'STUDY_PRINCIPAL_INVESTIGATOR','true','2432','terra cot','herdon','de','11111',null,1,1,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ,1) 
;
 
insert into study_contact (id,role_code,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ,CLINICAL_RESEARCH_STAFF_id)  
values(2, 'STUDY_PRINCIPAL_INVESTIGATOR','true','2432','terra cot','herdon','de','11111',null,2,2,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ,1) 
;
