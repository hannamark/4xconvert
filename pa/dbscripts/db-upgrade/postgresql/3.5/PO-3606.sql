update STUDY_OVERALL_STATUS set STATUS_CODE = 'WITHDRAWN' where STATUS_CODE = 'DISAPPROVED';
update STUDY_PROTOCOL_STAGE set TRIAL_STATUS_CODE = 'WITHDRAWN' where TRIAL_STATUS_CODE = 'DISAPPROVED';