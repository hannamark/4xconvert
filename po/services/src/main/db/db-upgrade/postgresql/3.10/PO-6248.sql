-- drop table family_p30_grant;

CREATE TABLE family_p30_grant
(
  id bigint NOT NULL UNIQUE,
  family_id bigint NOT NULL UNIQUE,
  serialnumber character varying(6) NOT NULL UNIQUE,
  grant_organization_name character varying(64),
  occ_cancer_center_name character varying(64),
  CONSTRAINT family_p30_grant_pkey PRIMARY KEY (id)
);

INSERT INTO family_p30_grant VALUES (1, 16492241, '13330', 'ALBERT EINSTEIN COL OF MED YESHIVA UNIV', 'Albert Einstein Cancer Research Center');
INSERT INTO family_p30_grant VALUES (2, 16490699, '125123', 'BAYLOR COLLEGE OF MEDICINE', 'Dan L. Duncan Cancer Center');
INSERT INTO family_p30_grant VALUES (3, 16472508, '43703', 'CASE WESTERN RESERVE UNIVERSITY', 'Case Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (4, 16473403, '33572', 'CITY OF HOPE/BECKMAN RESEARCH INSTITUTE', 'City of Hope Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (5, 16491645, '13696', 'COLUMBIA UNIVERSITY HEALTH SCIENCES', 'Herbert Irving Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (6, 16473888, '6516', 'DANA-FARBER CANCER INSTITUTE', 'Dana-Farber / Harvard Cancer Center');
INSERT INTO family_p30_grant VALUES (7, 16557222, '23108', 'DARTMOUTH COLLEGE', 'Norris Cotton Cancer Center');
INSERT INTO family_p30_grant VALUES (8, 16490546, '14236', 'DUKE UNIVERSITY', 'DUKE UNIVERSITY');
INSERT INTO family_p30_grant VALUES (9, 16702669, '138292', 'EMORY UNIVERSITY', 'Winship Cancer Institute');
INSERT INTO family_p30_grant VALUES (10, 16492532, '6927', 'FOX CHASE CANCER CENTER', 'Fox Chase Cancer Center');
INSERT INTO family_p30_grant VALUES (11, 16490874, '15704', 'FRED HUTCHINSON CANCER RESEARCH CENTER', 'Fred Hutchinson / University of Washington Cancer Center');
INSERT INTO family_p30_grant VALUES (12, 16493017, '51008', 'GEORGETOWN UNIVERSITY', 'Lombardi Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (13, 16493519, '76292', 'H. LEE MOFFITT CANCER CTR & RES INST', 'H. Lee Moffitt Cancer Center & Research Institute');
INSERT INTO family_p30_grant VALUES (14, 16491707, '82709', 'INDIANA UNIV-PURDUE UNIV AT INDIANAPOLIS', 'Indiana University Melvin & Bren Simon Cancer Center');
INSERT INTO family_p30_grant VALUES (15, 16557400, '6973', 'JOHNS HOPKINS UNIVERSITY', 'Sidney Kimmel Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (16, 16555954, '15083', 'MAYO CLINIC', 'Mayo Clinic Cancer Center');
INSERT INTO family_p30_grant VALUES (17, 16721612, '138313', 'MEDICAL UNIVERSITY OF SOUTH CAROLINA', 'Hollings Cancer Center');
INSERT INTO family_p30_grant VALUES (18, 16493710, '16087', 'NEW YORK UNIVERSITY SCHOOL OF MEDICINE', 'NYU Cancer Institute');
INSERT INTO family_p30_grant VALUES (19, 16493082, '60553', 'NORTHWESTERN UNIVERSITY AT CHICAGO', 'Robert H. Lurie Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (20, 16753007, '16058', 'OHIO STATE UNIVERSITY', 'Comprehensive Cancer Center at OSU');
INSERT INTO family_p30_grant VALUES (21, 16573406, '69533', 'OREGON HEALTH AND SCIENCE UNIVERSITY', 'OHSU Knight Cancer Institute');
INSERT INTO family_p30_grant VALUES (22, 16721656, '16056', 'ROSWELL PARK CANCER INSTITUTE CORP', 'Roswell Park Cancer Institute');
INSERT INTO family_p30_grant VALUES (23, 16557054, '8748', 'SLOAN-KETTERING INSTITUTE FOR CANCER RES', 'Memorial Sloan-Kettering Cancer Center');
INSERT INTO family_p30_grant VALUES (24, 16493741, '21765', 'ST. JUDE CHILDREN''S RESEARCH HOSPITAL', 'St. Jude Children''s Research Hospital');
INSERT INTO family_p30_grant VALUES (25, 16573878, '124435', 'STANFORD UNIVERSITY', 'Stanford University Cancer Center');
INSERT INTO family_p30_grant VALUES (26, 16492904, '56036', 'THOMAS JEFFERSON UNIVERSITY', 'Kimmel Cancer Center');
INSERT INTO family_p30_grant VALUES (27, 16470928, '72720', 'UNIV OF MED/DENT NJ-R W JOHNSON MED SCH', 'The Cancer Institute of New Jersey');
INSERT INTO family_p30_grant VALUES (28, 16574732, '13148', 'UNIVERSITY OF ALABAMA AT BIRMINGHAM', 'UAB Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (29, 16470595, '23074', 'UNIVERSITY OF ARIZONA', 'Arizona Cancer Center');
INSERT INTO family_p30_grant VALUES (30, 16575245, '93373', 'UNIVERSITY OF CALIFORNIA DAVIS', 'University of California Davis Cancer Center');
INSERT INTO family_p30_grant VALUES (31, 16472683, '62203', 'UNIVERSITY OF CALIFORNIA IRVINE', 'Chao Family Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (32, 16491676, '16042', 'UNIVERSITY OF CALIFORNIA LOS ANGELES', 'Jonsson Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (33, 16493449, '23100', 'UNIVERSITY OF CALIFORNIA SAN DIEGO', 'Moores UCSD Cancer Center');
INSERT INTO family_p30_grant VALUES (34, 16638303, '82103', 'UNIVERSITY OF CALIFORNIA SAN FRANCISCO', 'UCSF Helen Diller Family Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (35, 16637877, '14599', 'UNIVERSITY OF CHICAGO', 'University of Chicago Cancer Research Center');
INSERT INTO family_p30_grant VALUES (36, 16701629, '46934', 'UNIVERSITY OF COLORADO DENVER', 'University of Colorado Cancer Center');
INSERT INTO family_p30_grant VALUES (37, 16702192, '71789', 'UNIVERSITY OF HAWAII AT MANOA', 'Cancer Research Center of Hawaii');
INSERT INTO family_p30_grant VALUES (38, 16491545, '86862', 'UNIVERSITY OF IOWA', 'Holden Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (39, 16491049, '134274', 'UNIVERSITY OF MARYLAND BALTIMORE', 'Marlene and Stewart Greenebaum Cancer Center');
INSERT INTO family_p30_grant VALUES (40, 16702844, '46592', 'UNIVERSITY OF MICHIGAN AT ANN ARBOR', 'University of Michigan Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (41, 16493336, '77598', 'UNIVERSITY OF MINNESOTA TWIN CITIES', 'Masonic Cancer Center');
INSERT INTO family_p30_grant VALUES (42, 16638554, '36727', 'UNIVERSITY OF NEBRASKA MEDICAL CENTER', 'UNMC Eppley Cancer Center');
INSERT INTO family_p30_grant VALUES (43, 16721049, '118100', 'UNIVERSITY OF NEW MEXICO', 'UNM Cancer Research & Treatment Center');
INSERT INTO family_p30_grant VALUES (44, 16575427, '16086', 'UNIVERSITY OF NORTH CAROLINA CHAPEL HILL', 'UNC Lineberger Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (45, 16419740, '16520', 'UNIVERSITY OF PENNSYLVANIA', 'Abramson Cancer Center');
INSERT INTO family_p30_grant VALUES (46, 16638667, '47904', 'UNIVERSITY OF PITTSBURGH AT PITTSBURGH', 'University of Pittsburgh Cancer Institute');
INSERT INTO family_p30_grant VALUES (47, 16638128, '14089', 'UNIVERSITY OF SOUTHERN CALIFORNIA', 'USC / Norris Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (48, 16472006, '54174', 'UNIVERSITY OF TEXAS HLTH SCI CTR SAN ANT', 'Cancer Therapy & Research Center');
INSERT INTO family_p30_grant VALUES (49, 16720353, '16672', 'UNIVERSITY OF TEXAS MD ANDERSON CAN CTR', 'MD Anderson Cancer Center');
INSERT INTO family_p30_grant VALUES (50, 16573747, '142543', 'UNIVERSITY OF TEXAS SW MED CTR/DALLAS', 'Harold C. Simmons Cancer Center');
INSERT INTO family_p30_grant VALUES (51, 16491576, '42014', 'UNIVERSITY OF UTAH', 'Huntsman Cancer Institute');
INSERT INTO family_p30_grant VALUES (52, 16167288, '44579', 'UNIVERSITY OF VIRGINIA CHARLOTTESVILLE', 'UVA Cancer Center');
INSERT INTO family_p30_grant VALUES (53, 16720798, '14520', 'UNIVERSITY OF WISCONSIN MADISON', 'UW Paul P. Carbone Comprehensive Cancer Center');
INSERT INTO family_p30_grant VALUES (54, 16702534, '68485', 'VANDERBILT UNIVERSITY', 'Vanderbuilt-Ingram Cancer Center');
INSERT INTO family_p30_grant VALUES (55, 16368217, '16059', 'VIRGINIA COMMONWEALTH UNIVERSITY', 'Massey Cancer Center');
INSERT INTO family_p30_grant VALUES (56, 16157293, '12197', 'WAKE FOREST UNIVERSITY HEALTH SCIENCES', 'WAKE FOREST UNIVERSITY HEALTH SCIENCES');
INSERT INTO family_p30_grant VALUES (57, 16574797, '91842', 'WASHINGTON UNIVERSITY', 'Siteman Cancer Center');
INSERT INTO family_p30_grant VALUES (58, 16492873, '22453', 'WAYNE STATE UNIVERSITY', 'Barbara Ann Karmanos Cancer Institute');
INSERT INTO family_p30_grant VALUES (59, 16575458, '16359', 'YALE UNIVERSITY', 'Yale Cancer Center');

DELETE FROM family_p30_grant WHERE family_id NOT IN (SELECT id FROM family);

ALTER TABLE family_p30_grant ADD CONSTRAINT p30_family_fk FOREIGN KEY (family_id)
      REFERENCES "family" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE RESTRICT;

