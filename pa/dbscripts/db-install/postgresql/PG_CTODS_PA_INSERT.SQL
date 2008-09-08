DELETE FROM study_protocol
/
DELETE FROM organization
/
DELETE FROM person
/
INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 1, 'Acronym 1', 'NA', 'ABBREVIATED', 'true', 'true','NCI-2008-0001','CCR','Monitoring CA 15-3 Antigen in Patients During and After Adjuvant Therapy For Stage II, Node Positive Carcinoma of the Breast
 ','I', 'TREATMENT', current_timestamp, 'ACTUAL', 
    current_timestamp, 'ANTICIPATED', 'true' , 'false', 'true' , 'false' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 2, 'Acronym 2', 'RANDOMIZED_CONTROLLED_TRIAL', 'COMPLETE','false','true', 'NCI-2008-0002','CTEP','A Phase I study of Taxol in refractory leukemia in children
','II', 'TREATMENT','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'true' , 'false', 'true' , 'false' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 3, 'Acronym 3', 'NON_RANDOMIZED_TRIAL', 'AE','true','true','NCI-2008-0003','DEA','Phase III Study of Zoladex Adjuvant to Radiotherapy in Unfavorable Prognosis Carcinoma of the Prostate','III', 'PREVENTION','2008-01-13', 'ANTICIPATED', 
    '2008-02-01', 'ACTUAL', 'true' , 'false', 'true' , 'false' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 4, 'Acronym 4', 'NA', 'COMPLETE','false','true','NCI-2008-0004','DCB','A Phase I trial of Ifosfamide and Taxol in refractory Pelvic Malignancies','I', 'DIAGNOSTIC','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'true' , 'false', 'true' , 'false' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 5, 'Acronym 5', 'NA', 'ABBREVIATED','true','true','NCI-2008-0005','CCR','Randomized double-blind trial of G-CSF versus placebo at the time of chemotherapy-induced Neutropenia ','I', 'DIAGNOSTIC',current_timestamp, 'ACTUAL', 
    current_timestamp, 'ANTICIPATED', 'true' , 'false', 'true' , 'false' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 6, 'Acronym 6', 'RANDOMIZED_CONTROLLED_TRIAL', 'COMPLETE','false','false','NCI-2008-0006','CTEP','Phase III study of priming with granulocyte-macrophage colony stimulating factor ','III', 'SUPPORTIVE_CARE','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'true' , 'false', 'true' , 'false' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 7, 'Acronym 7', 'NON_RANDOMIZED_TRIAL', 'AE','true','false','NCI-2008-0007','DEA','Phase III Comparison of Methotrexate, Vinblastine, Doxorubicin, and Cisplatin (MVAC) vs. Doxorubicin ','III','SUPPORTIVE_CARE', '2008-01-13', 'ANTICIPATED', 
    '2008-02-01', 'ACTUAL', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 8, 'Acronym 8', 'NA', 'COMPLETE','false','false','NCI-2008-0008','DCB','A Clinical Trial to Evaluate the Effect of Dose Intensification of Postoperative Adriamycin/CTX (AC) ','IV', 'SUPPORTIVE_CARE','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 9, 'Acronym 9', 'NA', 'ABBREVIATED','true','false','NCI-2008-0009','CCR','A Pilot Study of Chemotherapy Plus Radiotherapy for Selected Stage IIIB','III', 'SCREENING',current_timestamp, 'ACTUAL', 
    current_timestamp, 'ANTICIPATED', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 10, 'Acronym 10', 'RANDOMIZED_CONTROLLED_TRIAL', 'COMPLETE','false','false','NCI-2008-0010','CTEP','Phase III Trial of Adjuvant Therapy with Tamoxifen Alone or Combined with Fluoxymesterone in Postmenopausal Women','III', 'SUPPORTIVE_CARE','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 11, 'Acronym 11', 'NON_RANDOMIZED_TRIAL', 'AE','true','true','NCI-2008-0011','DEA','Randomized trial of adjuvant immunotherapy with an allogeneic melanoma vaccine for patients ','O', 'SCREENING','2008-01-13', 'ANTICIPATED', 
    '2008-02-01', 'ACTUAL', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 12, 'Acronym 12', 'NA', 'COMPLETE','false','true','NCI-2008-0012','DCB','Phase II Study of Interleukin-4 (IL-4) in Patients with Previously Treated Measurable ','II', 'SCREENING','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/

INSERT INTO study_protocol 
    (
         id, acronym,allocation_code,accr_rept_meth_code, data_monty_comty_apptn_indicator ,expd_access_indidicator,nci_identifier,monitor_code,official_title,phase_code,primary_purpose_code,pri_compl_date,pri_compl_date_type_code,
        start_date, start_date_type_code, ind_ide_indicator ,fda_regulated_indicator,section801_indicator,delayed_posting_indicator, study_protocol_type, date_last_updated, user_last_updated
    )  values ( 13, 'Acronym 13', 'NA', 'COMPLETE','false','true','NCI-2008-0013','DCB','Phase II Study of Interleukin-4 (IL-4) in Patients with Previously Treated Measurable ','II', 'SCREENING','2008-07-13', 'ACTUAL', 
    '2008-08-13', 'ANTICIPATED', 'false' , 'true', 'false' , 'true' , 'InterventionalStudyProtocol' , current_timestamp, 'abstractor' )
/


--- insert into document workflow status

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (1,'ON_HOLD', NULL, '2008-01-11', 1, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (2,'ACCEPTED', NULL, '2008-02-21', 2, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (3,'REJECTED', NULL, '2008-03-13', 3, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (4,'ABSTRACTED', NULL, '2008-04-14', 4, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (5,'ABSTRACTION_VERIFIED', NULL, '2008-05-14', 5, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (6,'ABSTRACTION_NOT_VERIFIED', NULL, '2008-06-11', 6, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (7,'ON_HOLD', NULL, '2008-07-23', 7, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (8,'ACCEPTED', NULL, '2008-08-18', 8, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (9,'ABSTRACTION_VERIFIED', NULL, '2008-09-29', 9, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (10,'REJECTED', NULL, '2008-12-11', 10, current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (11,'REJECTED', NULL, '2008-12-11', 11 , current_timestamp, 'abstractor' ) 
/

insert into document_workflow_status (id,status_code,common_text,status_date_range_low, study_protocol_id, date_last_updated, user_last_updated) 
values (12,'ABSTRACTION_VERIFIED', NULL, '2008-12-11', 11 , current_timestamp, 'abstractor' ) 
/


-- insert into study_overall_status 

insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (1, 'IN_REVIEW', '2008-03-11', 1, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (2, 'APPROVED', '2007-07-21', 2, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (3, 'ACTIVE', '2008-01-21', 3, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (4, 'CLOSED_TO_ACCRUAL', '2008-06-12', 4, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (5, 'CLOSED_TO_ACCRUAL_AND_INTERVENTION', '2008-03-11', 5, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (6, 'IN_REVIEW', '2008-03-27', 6, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (7, 'APPROVED', '2008-12-12', 7, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (8, 'IN_REVIEW', '2008-09-11', 8, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (9, 'CLOSED_TO_ACCRUAL', '2008-04-04', 9, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (10, 'IN_REVIEW', '2008-09-11', 10, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (11, 'CLOSED_TO_ACCRUAL', '2008-04-04', 11, current_timestamp, 'abstractor' ) 
/
insert into study_overall_status (id, status_code, status_date, study_protocol_id, date_last_updated, user_last_updated) 
values (12, 'CLOSED_TO_ACCRUAL', '2008-04-04', 11, current_timestamp, 'abstractor' ) 
/


--- organization insert
insert into organization (id, name, identifier, date_last_updated, user_last_updated ) 
values (1, 'Oraganization A', '1', current_timestamp, 'po' ) 
/

insert into organization (id, name, identifier, date_last_updated, user_last_updated ) 
values (2, 'Oraganization B', '2', current_timestamp, 'po' ) 
/

insert 
into organization (id, name, identifier, date_last_updated, user_last_updated ) 
values (3, 'Oraganization C', '3', current_timestamp, 'po' ) 
/

insert into organization (id, name, identifier, date_last_updated, user_last_updated ) 
values (4, 'Oraganization D', '4', current_timestamp, 'po' ) 
/
insert into organization (id, name, identifier, date_last_updated, user_last_updated ) 
values (5, 'Oraganization E', '5', current_timestamp, 'po' ) 
/

---- healthcare_facility

insert into healthcare_facility (id,organization_id, date_last_updated, user_last_updated) 
values (1,1, current_timestamp, 'po' ) 
/

insert into healthcare_facility (id,organization_id, date_last_updated, user_last_updated) 
values (2,2, current_timestamp, 'po' ) 
/

insert into healthcare_facility (id,organization_id, date_last_updated, user_last_updated) 
values (3,3, current_timestamp, 'po' ) 
/

insert into healthcare_facility (id,organization_id, date_last_updated, user_last_updated) 
values (4,3, current_timestamp, 'po' ) 
/

insert into healthcare_facility (id,organization_id, date_last_updated, user_last_updated) 
values (5,2, current_timestamp, 'po' ) 
/
insert into healthcare_facility (id,organization_id, date_last_updated, user_last_updated) 
values (6,1, current_timestamp, 'po' ) 
/


insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (1,'LEAD_ORAGANIZATION','CALGB-88612-A443',1, 1, 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/
 
insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id , status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (2,'LEAD_ORAGANIZATION','CCG-09032-A443',2 ,2,'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (3,'LEAD_ORAGANIZATION','GOG-92012-A443',3, 3 , 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (4,'LEAD_ORAGANIZATION','ECOG1',4, 4 , 'ACTIVE',  current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (5,'LEAD_ORAGANIZATION','ECOG123',5, 5 , 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/


insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (6,'LEAD_ORAGANIZATION','CALGB-88612-A443',6, 1, 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/
 
insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id , status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (7,'LEAD_ORAGANIZATION','CCG-09032-A443',7 ,2,'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (8,'LEAD_ORAGANIZATION','GOG-92012-A443',8, 3 , 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (9,'LEAD_ORAGANIZATION','ECOG1',9, 4 , 'ACTIVE',  current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (10,'LEAD_ORAGANIZATION','ECOG123',10, 5 , 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (11,'LEAD_ORAGANIZATION','ECOG123',11, 5 , 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/

insert into study_participation 
	   (id, functional_code, local_sp_indentifier, study_protocol_id, healthcare_facility_id ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (12,'LEAD_ORAGANIZATION','ECOG123',12, 5 , 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
/


------ person -----------------

insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated )
values (1, '1', 'John','D', 'Doe', current_timestamp, 'po' ) 
/
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated )
values (2, '2', 'Mark','f', 'Smith', current_timestamp, 'po' ) 
/
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated )
values (3, '3', 'Mary','Ann', 'Jones', current_timestamp, 'po' ) 
/
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated )
values (4, '4', 'Jack',null, 'Nick', current_timestamp, 'po' ) 
/
insert into person (id, identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated )
values (5, '5', 'Samuel','k', 'Smith', current_timestamp, 'po' ) 
/

------- heacltcare provider ------

insert into healthcare_provider (id, person_id, date_last_updated, user_last_updated) 
 values (1, 1,  current_timestamp, 'po' ) 
/
insert into healthcare_provider (id, person_id, date_last_updated, user_last_updated) 
 values (2, 2,  current_timestamp, 'po' ) 
/
insert into healthcare_provider (id, person_id, date_last_updated, user_last_updated) 
 values (3, 3,  current_timestamp, 'po' ) 
/
insert into healthcare_provider (id, person_id, date_last_updated, user_last_updated) 
 values (4, 4,  current_timestamp, 'po' ) 
/
insert into healthcare_provider (id, person_id, date_last_updated, user_last_updated) 
 values (5, 1,  current_timestamp, 'po' ) 
/
insert into healthcare_provider (id, person_id, date_last_updated, user_last_updated) 
 values (6, 2,  current_timestamp, 'po' ) 
/

--------------- study contact -----------

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(1, 'true','2432','terra cot','herdon','de','11111',null,1,1,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/
 
insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(2, 'true','2432','terra cot','herdon','de','11111',null,2,2,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(3, 'true','2432','terra cot','herdon','de','11111',null,3,3,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(4, 'true','2432','terra cot','herdon','de','11111',null,4,4,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(5, 'true','2432','terra cot','herdon','de','11111',null,5,5,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(6, 'true','2432','terra cot','herdon','de','11111',null,1,6,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/
 
insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(7, 'true','2432','terra cot','herdon','de','11111',null,2,7,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(8, 'true','2432','terra cot','herdon','de','11111',null,3,8,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(9, 'true','2432','terra cot','herdon','de','11111',null,4,9,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(10, 'true','2432','terra cot','herdon','de','11111',null,5,10,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(11, 'true','2432','terra cot','herdon','de','11111',null,4,11,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

insert into study_contact (id,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_id, 
	healthcare_provider_id,study_protocol_id, status_code, status_date_range_low, date_last_updated,user_last_updated ) 
values(12, 'true','2432','terra cot','herdon','de','11111',null,5,12,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ) 
/

------------------ study contact rules

insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (1, 'STUDY_PRINCIPAL_INVESTIGATOR', 1, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (2, 'STUDY_DIRECTOR', 1, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (3, 'STUDY_PRINCIPAL_INVESTIGATOR', 2, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (4, 'STUDY_PRINCIPAL_INVESTIGATOR', 3, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (5, 'STUDY_PRINCIPAL_INVESTIGATOR', 4, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (6, 'STUDY_PRINCIPAL_INVESTIGATOR', 5, current_timestamp , 'abstractor' ) 
/

insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (7, 'STUDY_PRINCIPAL_INVESTIGATOR', 6, current_timestamp , 'abstractor' ) 
/
--insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
--values (2, 'STUDY_DIRECTOR', 1, current_timestamp , 'abstractor' ) 
--/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (9, 'STUDY_PRINCIPAL_INVESTIGATOR', 7, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (10, 'STUDY_PRINCIPAL_INVESTIGATOR', 8, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (11, 'STUDY_PRINCIPAL_INVESTIGATOR', 9, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (12, 'STUDY_PRINCIPAL_INVESTIGATOR', 10, current_timestamp , 'abstractor' ) 
/
 
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (13, 'STUDY_PRINCIPAL_INVESTIGATOR', 11, current_timestamp , 'abstractor' ) 
/
insert into study_contact_roles (id, role_code, study_contact_id, date_last_updated, user_last_updated) 
values (14, 'STUDY_PRINCIPAL_INVESTIGATOR', 12, current_timestamp , 'abstractor' ) 
/
  
COMMIT
/    