INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'reg.web.admin.showReportsMenu','true');
INSERT INTO PA_PROPERTIES VALUES ((select max(identifier) + 1 from pa_properties),'regweb.jasper.allow.allssl','true');
DELETE FROM PA_PROPERTIES WHERE NAME='reg.web.admin.allowCenters';