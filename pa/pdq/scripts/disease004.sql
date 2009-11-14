INSERT INTO disease (identifier,disease_code,nt_term_identifier,preferred_name,menu_display_name,status_code,status_date_range_low,date_last_created,user_last_created) 
VALUES (1776,'CDR0000587622',null,'systemic scleroderma','systemic scleroderma','ACTIVE',now(),'09/16/2009','CDR587622.xml');

UPDATE DISEASE SET STATUS_CODE = 'ACTIVE' WHERE IDENTIFIER = 1755 AND STATUS_CODE = 'INACTIVE';


