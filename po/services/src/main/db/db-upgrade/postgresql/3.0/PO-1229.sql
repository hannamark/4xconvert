-- update the research organizations
insert into ro_otheridentifier (ro_id, null_flavor, displayable, extension, identifier_name, reliability, root, scope)
select ro.id, io.assigned_identifier_null_flavor, io.assigned_identifier_displayable, io.assigned_identifier_extension, io.assigned_identifier_identifier_name,  io.assigned_identifier_reliability, io.assigned_identifier_root,  io.assigned_identifier_scope
from identifiedorganization io join organization player on io.player_id = player.id
    join organization scoper on io.scoper_id = scoper.id and scoper.name='Cancer Therapy Evaluation Program'
    join researchorganization ro on ro.player_id = io.player_id
where assigned_identifier_root='Cancer Therapy Evaluation Program Organization Identifier'
    and assigned_identifier_identifier_name = 'CTEP ID'
    and io.status != 'NULLIFIED';

-- update the health care facilities
insert into hcf_otheridentifier (hcf_id, null_flavor, displayable, extension, identifier_name, reliability, root, scope)
select hcf.id, io.assigned_identifier_null_flavor, io.assigned_identifier_displayable, io.assigned_identifier_extension, io.assigned_identifier_identifier_name, io.assigned_identifier_reliability, io.assigned_identifier_root, io.assigned_identifier_scope
from identifiedorganization io join organization player on io.player_id = player.id
    join organization scoper on io.scoper_id = scoper.id and scoper.name='Cancer Therapy Evaluation Program'
    join healthcarefacility hcf on hcf.player_id = io.player_id
where assigned_identifier_root='Cancer Therapy Evaluation Program Organization Identifier'
    and assigned_identifier_identifier_name = 'CTEP ID'
    and io.status != 'NULLIFIED';
