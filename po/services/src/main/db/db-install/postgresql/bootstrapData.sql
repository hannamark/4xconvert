INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (1, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);
INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (2, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);
INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (3, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);
INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (4, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);
INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (5, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);
INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (6, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);
INSERT INTO address (id, streetaddressline, cityormunicipality, postalcode, stateorprovince, country_id) VALUES (7, '2115 E Jefferson St', 'Rockville', '20852', 'MD', 126);

INSERT INTO email (id, value) VALUES (1, 'foo@example.com');
INSERT INTO email (id, value) VALUES (2, 'foo@example.com');
INSERT INTO email (id, value) VALUES (3, 'foo@example.com');
INSERT INTO email (id, value) VALUES (4, 'foo@example.com');
INSERT INTO email (id, value) VALUES (5, 'foo@example.com');
INSERT INTO email (id, value) VALUES (6, 'foo@example.com');
INSERT INTO email (id, value) VALUES (7, 'foo@example.com');

INSERT INTO organization (id, name, status, comments, statusdate, duplicate_of, postal_address_id) VALUES (1, 'ClinicalTrials.gov', 'ACTIVE', NULL, now(), NULL, 1);
INSERT INTO organization (id, name, status, comments, statusdate, duplicate_of, postal_address_id) VALUES (2, 'Cancer Therapy Evaluation Program', 'ACTIVE', NULL, now(), NULL, 2);
INSERT INTO organization (id, name, status, comments, statusdate, duplicate_of, postal_address_id) VALUES (3, 'Division of Cancer Control and Population Sciences', 'ACTIVE', NULL, now(), NULL, 3);
INSERT INTO organization (id, name, status, comments, statusdate, duplicate_of, postal_address_id) VALUES (4, 'National Cancer Institute', 'ACTIVE', NULL, now(), NULL, 4);

INSERT INTO organization_email (organization_id, email_id, idx) VALUES (1, 1, 0);
INSERT INTO organization_email (organization_id, email_id, idx) VALUES (2, 2, 0);
INSERT INTO organization_email (organization_id, email_id, idx) VALUES (3, 3, 0);
INSERT INTO organization_email (organization_id, email_id, idx) VALUES (4, 4, 0);

INSERT INTO researchorganization (id, status, statusdate, typecode_id, player_id, duplicate_of) VALUES (1, 'PENDING', now(), 1, 1, NULL);
INSERT INTO researchorganization (id, status, statusdate, typecode_id, player_id, duplicate_of) VALUES (2, 'PENDING', now(), 8, 2, NULL);
INSERT INTO researchorganization (id, status, statusdate, typecode_id, player_id, duplicate_of) VALUES (3, 'PENDING', now(), 3, 3, NULL);
INSERT INTO researchorganization (id, status, statusdate, typecode_id, player_id, duplicate_of) VALUES (4, 'PENDING', now(), 4, 4, NULL);

INSERT INTO identifiedorganization (id, status, statusdate, assigned_identifier_null_flavor, assigned_identifier_displayable, assigned_identifier_extension, assigned_identifier_identifier_name, 
assigned_identifier_reliability, assigned_identifier_root, assigned_identifier_scope, scoper_id, player_id, duplicate_of) 
VALUES (523, 'PENDING', now(), NULL, true, 'CTEP', 'Cancer Therapy Evaluation Program Organization Identifier', 'VRF', '2.16.840.1.113883.3.26.6.2', 'OBJ', 2, 2, NULL);

INSERT INTO person(id, prefix, firstname, lastname, status, statusdate, postal_address_id) VALUES (1, 'Dr.', 'Alyssa', 'Jones', 'ACTIVE', now(), 5);
INSERT INTO person(id, prefix, firstname, lastname, status, statusdate, postal_address_id) VALUES (2, 'Dr.', 'Ben', 'Bova', 'ACTIVE', now(), 6);
INSERT INTO person(id, prefix, firstname, lastname, status, statusdate, postal_address_id) VALUES (3, 'Dr.', 'Bob', 'Ryan', 'ACTIVE', now(), 7);

INSERT INTO person_email (person_id, email_id, idx) VALUES (1, 5, 0);
INSERT INTO person_email (person_id, email_id, idx) VALUES (2, 6, 0);
INSERT INTO person_email (person_id, email_id, idx) VALUES (3, 7, 0);