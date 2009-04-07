delete from pa_properties;
insert into pa_properties(IDENTIFIER,name,value) VALUES(1,'tsr.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Summary Report and ClinicalTrials.gov Registration File');

insert into pa_properties(IDENTIFIER,name,value) VALUES(2,'tsr.body',
'${CurrentDate}

Dear ${SubmitterName}

Your Trial, ${localOrgID}, ${trialTitle}, received by the NCI Clinical Trial Reporting Office (CTRO) on ${receiptDate} and assigned NCI Trial ID ${nciTrialID}, has been processed by the CTRO.

Please review the attached Trial Summary Report (${fileName}), including all data fields, to ensure that they accurately reflect the trial. Please mark any changes that are required in the attached file and return the updated file by e-mail to the CTRO at ncictrp@mail.nih.gov within five (5) business days. CTRO staff will incorporate changes, as appropriate. If there are no changes, please respond to this email confirming that the report accurately reflects your trial. Please include the NCI Trial ID, as above, in all correspondence. If a response is NOT received within five (5) business days, CTRO will conclude that the information contained in this Trial Summary Report is acceptable.
 
We are also including a file ${fileName2} suitable for posting to ClinicalTrials.gov as an attachment. This file reflects the information contained within the Trial Summary Report in a format that meets the specifications of the National Library of Medicine ClinicalTrials.gov database. You may use the file to register your trial with ClinicalTrials.gov. For information on how to submit your trial to ClinicalTrials.gov please refer to the CTRP web site at http://www.cancer.gov/ncictrp. 

If you have questions on this or other CTRP topics, please contact us at ncictrp@mail.nih.gov.

Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(3,'smtp',
'mailfwd.nih.gov');

insert into pa_properties(IDENTIFIER,name,value) VALUES(4,'fromaddress',
'ncictrp@mail.nih.gov');

insert into pa_properties(IDENTIFIER,name,value) VALUES(5,'rejection.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Registration Rejection Notice.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(6,'rejection.body',
'The following trial was submitted for registration to the NCI Clinical Trials Reporting Office (CTRO): 

Lead Organization Trial Identifier ${leadorgid} 

This trial cannot be registered by the CTRO for the following reasons: ${reasoncode}

If you feel there has been an error in rejecting this trial registration, or if you have additional questions on this or other CTRP topics, please contact us at ncictrp@mail.nih.gov 

Thank you for your participation in NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(7,'allowed.uploadfile.types','doc,pdf,xls,wpd,docx,docm,xlsx,xlsm,xlsb');
insert into pa_properties(IDENTIFIER,name,value) VALUES(8,'trial.register.subject',
'NCI Clinical Trials Reporting Program (CTRP) Trial Registration Acknowledgement');
insert into pa_properties(IDENTIFIER,name,value) VALUES(9,'trial.register.body',
'You have successfully registered the following trial with the National Cancer Institute (NCI) Clinical Trials Reporting Program (CTRP):
Lead Organization Trial Identifier ${leadOrgTrialIdentifier}   
 
Your trial has been assigned a unique NCI identification number for tracking that you will need to reference in future correspondence with the Clinical Trials Reporting Office (CTRO): 
NCI Trial Identifier ${nciTrialIdentifier}
 
Your trial will now be processed by the staff of the CTRO. Upon completion, the CTRO will e-mail you a Trial Summary Report listing key data elements that have been abstracted from the trial protocol.  At that time, the CTRO will request that you review the Trial Summary Report for accuracy and respond with any changes that may be required. 
 
If you have questions on this or other CTRP topics, please contact us at ncictrp@mail.nih.gov
 
Thank you for registering your trial with NCI Clinical Trials Reporting Program (CTRP).
');

insert into pa_properties(IDENTIFIER,name,value) VALUES(10,'user.account.subject',
'NCI Clinical Trials Reporting Program (CTRP) User Activation');

insert into pa_properties(IDENTIFIER,name,value) VALUES(11,'user.account.body',
'Thank you for registering with NCI Clinical Trials Reporting Program. Please click on the link below or copy paste the URL in the browser to activate your account.');

insert into pa_properties(IDENTIFIER,name,value) VALUES(12,'trial.batchUpload.subject',
'Clinical Trials Reporting Program (CTRP) - Batch Trial Upload Status');

insert into pa_properties(IDENTIFIER,name,value) VALUES(13,'trial.batchUpload.body',
'Thank you for using the NCI Clinical Trials Reporting Program.
 
Here is a brief summary of the batch trial submission.
Please see attached report for more details.
----------------------------------------------------------
Total Trials Submitted: ${totalCount}
Successfully Registered: ${successCount}
Failed: ${failedCount}');