update identifiedorganization set assigned_identifier_reliability = 'VRF' where assigned_identifier_root = '2.16.840.1.113883.3.26.6.2';

update ro_otheridentifier set reliability = 'VRF' where root = '2.16.840.1.113883.3.26.6.2';

update hcf_otheridentifier set reliability = 'VRF' where root = '2.16.840.1.113883.3.26.6.2';

update identifiedorg_otheridentifier set reliability = 'VRF' where root = '2.16.840.1.113883.3.26.6.2';

update identifiedperson set assigned_identifier_reliability = 'VRF' where assigned_identifier_root = 'Cancer Therapy Evaluation Program Person Identifier';

update hcp_otheridentifier set reliability = 'VRF' where root = 'Cancer Therapy Evaluation Program Person Identifier';

update crs_otheridentifier set reliability = 'VRF' where root = '"Cancer Therapy Evaluation Program Person Identifier"';

update identifiedperson_otheridentifier set reliability = 'VRF' where root = 'Cancer Therapy Evaluation Program Person Identifier';
