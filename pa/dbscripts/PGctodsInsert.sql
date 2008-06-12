delete  from protocol;
delete  from investigator;
delete  from protocol_status;
delete  from healthcare_site;
delete  from study_site;
delete  from study_investigator;
delete  from healthcare_site_investigator;
delete  from healthcare_site_prtcpnt;

insert into protocol(id,nci_identifier,amendment_identifier,long_title_text,short_title_text,phase_code, source)Values(1,'2410','23','A Phase I study of Taxol in refractory leukemia in children','Leukemia in Children','I', 'NCICB CTMS(EN)');
insert into protocol(id,nci_identifier,amendment_identifier,long_title_text,short_title_text,phase_code, source)Values(2,'355','33','Phase III Study of Zoladex Adjuvant to Radiotherapy','Zoladex Adjuvant to Radiotherapy','III', 'NCICB CTMS(EN)');
insert into protocol(id,nci_identifier,amendment_identifier,long_title_text,short_title_text,phase_code, source)Values(3,'3421','213','Randomized double-blind trial of G-CSF versus','Randomized double-blind trial of G-CSF versus','II', 'NCICB CTMS(EN)');
insert into protocol(id,nci_identifier,amendment_identifier,long_title_text,short_title_text,phase_code, source)Values(4,'4433','3','A Controlled Phase III Evaluation of 5FU Combined','Evaluation of 5FU Combined','III', 'NCICB CTMS(EN)');
insert into protocol(id,nci_identifier,amendment_identifier,long_title_text,short_title_text,phase_code, source)Values(5,'345554','7','Phase II trial of Lysominal B','Phase II trial of Lysominal B','II', 'NCICB CTMS(EN)');



insert into investigator(id,last_name, first_name, middle_name,phone,telecom_address)Values(1,'Johns','Paul','M', '301-232-3233','johnsp@nci.nih.gov');
insert into investigator(id,last_name, first_name, middle_name,phone,telecom_address)Values(2,'Einstain','Albert','N', '301-323-9999','einstaina@nci.nih.gov');


insert into protocol_status(id,protocol_id,source) Values(1,1,'NCICB CTMS(EN)');
insert into protocol_status(id,protocol_id,source) Values(2,2,'NCICB CTMS(EN)');
insert into protocol_status(id,protocol_id,source) Values(3,3,'NCICB CTMS(EN)');
insert into protocol_status(id,protocol_id,source) Values(4,4,'NCICB CTMS(EN)');
insert into protocol_status(id,protocol_id,source) Values(5,5,'NCICB CTMS(EN)');

insert into healthcare_site(id,name,nci_institute_code,status_date,status_date_orig)Values(1,'21 Century Oncology-Lehigh Acres (FL360)','DC005','11-11-2004','11-11-2004');
insert into healthcare_site(id,name,nci_institute_code,status_date,status_date_orig)Values(2,'A. C. Camargo Hospital (08017)','DC005','11-11-2004','11-11-2004');
insert into healthcare_site(id,name,nci_institute_code,status_date,status_date_orig)Values(3,'Andrew Love Cancer Center (03120)','DC005','11-11-2004','11-11-2004');

insert into study_site(id, healthcare_site_id,protocol_id,start_date,start_date_orig,source) Values(1,1,1,'01-22-2004','01-22-2004','NCICB CTMS(EN)');

insert into study_investigator(id, investigator_id,protocol_id,source) Values(1,1,1,'NCICB CTMS(EN)');
insert into study_investigator(id, investigator_id,protocol_id,source) Values(2,2,2,'NCICB CTMS(EN)');
insert into study_investigator(id, investigator_id,protocol_id,source) Values(3,2,3,'NCICB CTMS(EN)');
insert into study_investigator(id, investigator_id,protocol_id,source) Values(4,1,4,'NCICB CTMS(EN)');

insert into healthcare_site_investigator(id,investigator_id,healthcare_site_id,ctom_insert_date,ctom_update_date)Values(1,1,1,'01-22-2005','01-22-2005');
insert into healthcare_site_investigator(id,investigator_id,healthcare_site_id,ctom_insert_date,ctom_update_date)Values(2,2,1,'01-12-2006','01-12-2006');
insert into healthcare_site_investigator(id,investigator_id,healthcare_site_id,ctom_insert_date,ctom_update_date)Values(3,2,2,'01-02-2007','01-02-2007');
insert into healthcare_site_investigator(id,investigator_id,healthcare_site_id,ctom_insert_date,ctom_update_date)Values(4,1,3,'01-20-2000','01-20-2000');

insert into healthcare_site_prtcpnt(id,healthcare_site_id,participant_id,ctom_insert_date,ctom_update_date)Values(1,1,1,'01-22-2005','01-22-2005');
insert into healthcare_site_prtcpnt(id,healthcare_site_id,participant_id,ctom_insert_date,ctom_update_date)Values(2,3,1,'01-22-2006','01-22-2006');
insert into healthcare_site_prtcpnt(id,healthcare_site_id,participant_id,ctom_insert_date,ctom_update_date)Values(3,2,1,'01-22-2007','01-22-2007');
