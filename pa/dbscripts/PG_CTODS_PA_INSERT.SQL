
delete from document ;
delete from document_identification ;
delete from study_protocol ;
delete from study_overall_status ;
delete from study_coordinating_center_roles ;
delete from study_coordinating_center ;
delete from organization ;

insert into document (id,official_title) values (1,'Cancer and Leukemia Group B');
insert into document (id,official_title) values (2,'Childrens Oncology Group');
insert into document (id,official_title) values (3,'Radiation Therapy Oncology Group');
insert into document (id,official_title) values (4,'Gynecologic Oncology Group');
insert into document (id,official_title) values (5,'North Central Cancer Treatment Group');

insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (1,'NCI',true,'NCI Identifier',1) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (3,'NCI',true,'NCI Identifier',2) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (4,'NCI',true,'NCI Identifier',3) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (5,'NCI',true,'NCI Identifier',4) ;
insert into document_identification (id,assigning_authority_code,primary_indicator, identifier, document_id) values (6,'NCI',true,'NCI Identifier',5) ;


insert into study_protocol (id, acronym, monitor_code, phase_code ) values (1,'Cancer and Leukemia Group B','CCR','I');
insert into study_protocol (id, acronym, monitor_code, phase_code ) values (2,'Childrens Oncology Group','CTEP','PHASEO');
insert into study_protocol (id, acronym, monitor_code, phase_code ) values (3,'Radiation Therapy Oncology Group','DCB','PHASEI');
insert into study_protocol (id, acronym, monitor_code, phase_code ) values (4,'Gynecologic Oncology Group','DCB','PHASE2');
insert into study_protocol (id, acronym, monitor_code, phase_code ) values (5,'North Central Cancer Treatment Group','CCR','PHASE3');

insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (1,'IN_REVIEW' , current_timestamp , 1); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (2,'APPROVED' , current_timestamp , 2); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (3,'ACTIVE' , current_timestamp , 3); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (4,'CLOSED_TO_ACCRUAL' , current_timestamp , 4); 
insert into study_overall_status (id, status_code, status_date, study_protocol_id) values (5,'IN_REVIEW' , current_timestamp , 5); 


insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (1,'ACCEPTED' , current_timestamp , 1); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (2,'ACCEPTED' , current_timestamp , 2); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (3,'ACCEPTED' , current_timestamp , 3); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (4,'ACCEPTED' , current_timestamp , 4); 
insert into document_workflow_status (id, status_code, status_date, study_protocol_id) values (5,'ACCEPTED' , current_timestamp , 5); 

insert into organization (id,name) Values(1,'21 Century Oncology-Lehigh Acres (FL360)');
insert into organization (id,name) Values(2,'A. C. Camargo Hospital (08017)');
insert into organization (id,name) Values(3,'Andrew Love Cancer Center (03120)');

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