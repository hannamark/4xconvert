DELETE FROM pa_properties where name='studysite.statuschange.ctro.email.subject';
DELETE FROM pa_properties where name='studysite.statuschange.ctro.email.body';
/*
 * 1 - <nci_id>
 */
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'studysite.statuschange.ctro.email.subject',
'Unable to notify participating site of industrial trial closure for trial %1s');
/*
 * 1 - <trial_title>
 * 2 - <nci_id>
 * 3 - <lead_org>
 * 4 - <participating_site_org_name>
 * 5 - <current_date>
 * 
 * 6 - <old_site_status>
 * 7 - <new_site_status>
 */
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'studysite.statuschange.ctro.email.body',
'Title:  %1s
NCI Trial ID:   %2s
Lead Organization   %3s
Participating Site:     %4s
Date:   %5s

The CTRP system was unable to find an email address to use for informing the participating site listed above of the automatic status change that resulted from closing this trial in ClinicalTrials.gov.
Old Site Status:    %6s
New Site Status:    %7s

Thank you,
CTRP - this is an automatically generated message. Please do not reply. 
');