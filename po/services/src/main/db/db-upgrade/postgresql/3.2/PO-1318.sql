update identifiedperson set assigned_identifier_reliability = 'VRF' where assigned_identifier_reliability = 'VER';
update identifiedpersoncr set assigned_identifier_reliability = 'VRF' where assigned_identifier_reliability = 'VER';
update identifiedorganization set assigned_identifier_reliability = 'VRF' where assigned_identifier_reliability = 'VER';
update identifiedorganizationcr set assigned_identifier_reliability = 'VRF' where assigned_identifier_reliability = 'VER';

update crs_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update hcf_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update hcp_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update identifiedorg_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update identifiedperson_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update oco_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update orgcontact_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update patient_otheridentifier set reliability = 'VRF' where reliability = 'VER';
update ro_otheridentifier set reliability = 'VRF' where reliability = 'VER';
