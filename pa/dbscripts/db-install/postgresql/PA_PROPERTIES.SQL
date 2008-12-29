delete from pa_properties;
insert into pa_properties(IDENTIFIER,name,value) VALUES(1,'tsr.subject',
'NCI Trial Summary Report AND ClinicalTrials.gov Submission Document');

insert into pa_properties(IDENTIFIER,name,value) VALUES(2,'tsr.body',
'${CurrentDate}

Subject: NCI Trial Summary Report AND ClinicalTrials.gov submission document 

Dear ${SubmitterName}

Your Trial, ${localOrgID}, ${trialTitle}, was received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} and has been assigned NCI Trial ID ${nciTrialID}. Please reference this NCI Trial ID in all future verbal and written communications.  

Please review the attached Trial Summary Report in the ${fileName} file and verify the data fields to ensure that they accurately reflect the trial. If the abstraction is correct or you would like to make changes, please reply to this email TKTK@nci.nih.gov within the next 7 days. Please include the NCI Trial Identifier number in all correspondence. If a response is NOT received within 7 days we will assume that the information contained in this report is acceptable.
 
Please note that the attached XML file ${fileName2} is formatted in accordance with ClinicalTrials.gov data specification and ready for submission to ClinicalTrials.gov for your convenience.   


Thank you for your assistance,

CTROSTAFFNAME
NCI’s Clinical Trial Reporting Office
CTROSTAFFEMAIL
http://www.cancer.gov/ncictrp
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(3,'smtp',
'mailfwd.nih.gov');

insert into pa_properties(IDENTIFIER,name,value) VALUES(4,'fromaddress',
'do.not.reply@mail.nih.gov');

insert into pa_properties(IDENTIFIER,name,value) VALUES(5,'rejection.subject',
'Subject Clinical Trials Reporting Program (CTRP) Protocol Submission Rejection');

insert into pa_properties(IDENTIFIER,name,value) VALUES(6,'rejection.body',
'You have submitted the following protocol to the NCI Clinical Trials Reporting Office (CTRO):

Lead Organization Trial Identifier ${leadorgid} 

Your Submission is rejected by the CTRO Staff for the following reason ${reasoncode}

If you feel there has been an error in your submission validation, please contact the NCI CTRO Office at 301-496-0001 or send an e-mail to TKTK@nci.nih.gov. Otherwise, we will consider this submission as rejected. 

Thank you for participation in the Clinical Trials Reporting Program.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(7,'allowed.uploadfile.types','doc,pdf,xls,wpd,docx,docm,xlsx,xlsm,xlsb');
insert into pa_properties(IDENTIFIER,name,value) VALUES(8,'trial.register.subject',
'NCI''s Clinical Trials Reporting Program (CTRP) Trial Registration Acknowledgment');
insert into pa_properties(IDENTIFIER,name,value) VALUES(9,'trial.register.body',
'You have successfully registered the following trial with the NCI Clinical Trials Reporting Office (CTRO): 
Lead Organization Trial Identifier ${leadOrgTrialIdentifier}   
 
Your trial has been assigned a unique NCI number for tracking that you will need to reference in future correspondence with the CTRO:
NCI Trial Identifier ${nciTrialIdentifier}
 
Your registered trial will now go through a series of CTRO steps that will result in a Trial Summary Report being sent to you which will contain key elements that the CTRO has abstracted from the protocol.  Upon receipt of the Trial Summary Report, we request that you review the elements for accuracy and reply with your assessment of this review. 
 
If you have questions on this or other CTRP topics, please contact NCICB Application Support:
Email: ncicb@pop.nci.nih.gov 
Toll-free: 888-478-4423 Phone: 301-451-4384 
Web: http://ncicb.nci.nih.gov/NCICB/support 
Live telephone support is available Monday to Friday, 8 a.m. to 8 p.m. Eastern Time, excluding federal holidays.

Thank you for registering your trial with NCI''s Clinical Trials Reporting Program (CTRP).');

insert into pa_properties(IDENTIFIER,name,value) VALUES(10,'user.account.subject',
'NCI''s Clinical Trials Reporting Program (CTRP) User Activation');

insert into pa_properties(IDENTIFIER,name,value) VALUES(11,'user.account.body',
'Thank you for registering with NCI''s Clinical Trials Reporting Program. Please click on the link below or copy paste the URL in the browser to activate your account.');