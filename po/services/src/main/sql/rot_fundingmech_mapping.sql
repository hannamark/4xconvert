--Cooperative Group
insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'CGP'), 
                                                                    (select id from fundingmechanism where code = 'U10'));

--Consortia
insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'CSM'), 
                                                                    (select id from fundingmechanism where code = 'U01'));
insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'CSM'), 
                                                                    (select id from fundingmechanism where code = 'P01'));
--INACTIVE
--insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'CSM'), 
--                                                                    (select id from fundingmechanism where code = 'N01'));

--Cancer Center
insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'CCR'), 
                                                                    (select id from fundingmechanism where code = 'P30'));

--Clinical Center
--NONE

--Network
--Inserts all active funding mechanism codes except for U10, P30 and P50.
insert into rot_fundingmechs (rot_id, fundingmech_id) (select rot.id, fm.id from researchorganizationtype rot, fundingmechanism fm where rot.code = 'NWK' and fm.status = 'ACTIVE' and fm.code not in ('U10', 'P30', 'P50') );


--Research Base
insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'RSB'), 
                                                                    (select id from fundingmechanism where code = 'U10'));

--Drug Company
--NONE


--CCOP
insert into rot_fundingmechs (rot_id, fundingmech_id) values ((select id from researchorganizationtype where code = 'COP'), 
                                                                    (select id from fundingmechanism where code = 'U10'));