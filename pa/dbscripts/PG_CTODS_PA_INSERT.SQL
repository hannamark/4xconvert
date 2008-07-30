delete from  healthcare_provider ;
delete from  person_identification ;
delete from  person ;
delete from  study_coordinating_center_roles ;
delete from  study_coordinating_center  ;
delete from  document_identification ;
delete from  study_conditions ;
delete from  study_overall_status ;
delete from  document_workflow_status ;
delete from  study_regulatory_authority;
delete from  study_protocol ;
delete from  document ;
delete from  organization ;
delete from  conditions ;



insert into document (id,official_title) values (1,'Cancer and Leukemia Group B');
insert into document (id,official_title) values (2,'Childrens Oncology Group');
insert into document (id,official_title) values (3,'Radiation Therapy Oncology Group');
insert into document (id,official_title) values (4,'Gynecologic Oncology Group');
insert into document (id,official_title) values (5,'North Central Cancer Treatment Group');

insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (1,'NCI',true,'NCI-2008-00001',1) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (3,'NCI',true,'NCI-2008-00002',2) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (4,'NCI',true,'NCI-2008-00003',3) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (5,'NCI',true,'NCI-2008-00004',4) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (6,'NCI',true,'NCI-2008-00005',5) ;
--insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (7,'LEAD_ORGANIZATION_TRIAL_ID',false,'ECOG1',1) ;
--insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (8,'LEAD_ORGANIZATION_TRIAL_ID',false,'ECOG2',2) ;


insert into study_protocol (id, acronym, monitor_code, phase_code , primary_purpose_code) values (1,'Cancer and Leukemia Group B','CCR','I','TREATMENT');
insert into study_protocol (id, acronym, monitor_code, phase_code , primary_purpose_code) values (2,'Childrens Oncology Group','CTEP','PHASEO','TREATMENT');
insert into study_protocol (id, acronym, monitor_code, phase_code , primary_purpose_code) values (3,'Radiation Therapy Oncology Group','DCB','PHASEI','PREVENTION');
insert into study_protocol (id, acronym, monitor_code, phase_code , primary_purpose_code) values (4,'Gynecologic Oncology Group','DCB','PHASE2','PREVENTION');
insert into study_protocol (id, acronym, monitor_code, phase_code , primary_purpose_code) values (5,'North Central Cancer Treatment Group','CCR','PHASE3','DIAGNOSTIC');

insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (1,'IN_REVIEW' , current_timestamp , 1); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (2,'APPROVED' , current_timestamp , 2); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (3,'ACTIVE' , current_timestamp , 3); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (4,'CLOSED_TO_ACCRUAL' , current_timestamp , 4); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (5,'IN_REVIEW' , current_timestamp , 5); 

insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (6,'ACTIVE' , current_timestamp , 2); 

insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (1,'ACCEPTED' , current_timestamp , 1); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (2,'ON_HOLD' , current_timestamp , 2); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (3,'REJECTED' , current_timestamp , 3); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (4,'ACCEPTED' , current_timestamp , 4); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (5,'ABSTRACTED' , current_timestamp , 5); 

insert into organization (id,name) Values(1,'21 Century Oncology-Lehigh Acres (FL360)');
insert into organization (id,name) Values(2,'A. C. Camargo Hospital (08017)');
insert into organization (id,name) Values(3,'Andrew Love Cancer Center (03120)');

insert into person (id,first_name,middle_name,last_name) Values(1,'John','d','Doe');
insert into person (id,first_name,middle_name,last_name) Values(2,'Mark','s','Smith');
insert into person (id,first_name,middle_name,last_name) Values(3,'Mary',null,'John');
insert into person (id,first_name,middle_name,last_name) Values(4,'Jones',null,'Mary');

insert into person_identification (id,assigning_authority_code,primary_indicator, identifier_value, person_id) values (1,'PO',true,'NCI-2008-00001',1) ;
insert into person_identification (id,assigning_authority_code,primary_indicator, identifier_value, person_id) values (2,'PO',true,'NCI-2008-00002',2) ;
insert into person_identification (id,assigning_authority_code,primary_indicator, identifier_value, person_id) values (3,'PO',true,'NCI-2008-00003',3) ;
insert into person_identification (id,assigning_authority_code,primary_indicator, identifier_value, person_id) values (4,'PO',true,'NCI-2008-00004',4) ;

insert into healthcare_provider (id,person_id) values (1,1);
insert into healthcare_provider (id,person_id) values (2,2);
insert into healthcare_provider (id,person_id) values (3,3);
insert into healthcare_provider (id,person_id) values (4,4);

insert into study_contact (id, primary_indicator , healthcare_provider_id , study_protocol_id ) values (1,true,1,1);
insert into study_contact (id, primary_indicator , healthcare_provider_id , study_protocol_id ) values (2,true,2,2);
insert into study_contact (id, primary_indicator , healthcare_provider_id , study_protocol_id ) values (3,true,3,3);
insert into study_contact (id, primary_indicator , healthcare_provider_id , study_protocol_id ) values (4,true,4,4);

insert into study_contact_roles (id, role_code , study_contact_id ) values (1,'STUDY_PRINCIPAL_INVESTIGATOR',1);
insert into study_contact_roles (id, role_code , study_contact_id ) values (2,'STUDY_PRINCIPAL_INVESTIGATOR',2);
insert into study_contact_roles (id, role_code , study_contact_id ) values (3,'STUDY_PRINCIPAL_INVESTIGATOR',3);
insert into study_contact_roles (id, role_code , study_contact_id ) values (4,'STUDY_PRINCIPAL_INVESTIGATOR',4);




insert into study_coordinating_center (id,study_protocol_id, organization_id) values (1,1,1);
insert into study_coordinating_center (id,study_protocol_id, organization_id) values (2,2,2);
insert into study_coordinating_center (id,study_protocol_id, organization_id) values (3,3,3);
insert into study_coordinating_center (id,study_protocol_id, organization_id) values (4,4,2);
insert into study_coordinating_center (id,study_protocol_id, organization_id) values (5,5,1);

insert into study_coordinating_center_roles (id, responsibility_code,study_coordinating_center_id) values (1,'PROTOCOL_MANAGEMENT',1);
insert into study_coordinating_center_roles (id, responsibility_code,study_coordinating_center_id) values (2,'PROTOCOL_MANAGEMENT',2);
insert into study_coordinating_center_roles (id, responsibility_code,study_coordinating_center_id) values (3,'PROTOCOL_MANAGEMENT',3);
insert into study_coordinating_center_roles (id, responsibility_code,study_coordinating_center_id) values (4,'PROTOCOL_MANAGEMENT',4);
insert into study_coordinating_center_roles (id, responsibility_code,study_coordinating_center_id) values (5,'PROTOCOL_MANAGEMENT',5);

insert into conditions (id,code,name,parent_code ) Values (1,'A','Cancer',null );
insert into conditions (id,code,name,parent_code ) Values (2,'B','Breast Cancer','A' );

insert into study_conditions(id,lead_indicator,conditions_id,study_protocol_id) values (1,'YES',1,1);
insert into study_conditions(id,lead_indicator,conditions_id,study_protocol_id) values (2,'NO',2,1);

commit ;