update researchorganization set status='PENDING' where status='ACTIVE' and not exists (select * from ro_otheridentifier ro_oi where ro_oi.ro_id = id and ro_oi.root='2.16.840.1.113883.3.26.6.2');
update healthcarefacility set status='PENDING' where status='ACTIVE' and not exists (select * from hcf_otheridentifier hcf_oi where hcf_oi.hcf_id = id and hcf_oi.root='2.16.840.1.113883.3.26.6.2');
