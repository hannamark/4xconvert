CREATE TABLE comment (
    id int8 not null,    
    value text,    
    create_date timestamp,
    user_id int8,    
    PRIMARY KEY (id)
);

alter table comment add constraint comment_user_id foreign key (user_id) references csm_user ON DELETE CASCADE;

CREATE TABLE organization_comment
(
   organization_id int8 NOT NULL,
   comment_id int8 NOT NULL,
   idx int NOT NULL,
   CONSTRAINT organization_comment_pkey PRIMARY KEY (organization_id,idx)
);

ALTER TABLE organization_comment
    ADD CONSTRAINT comment_org_fk
    FOREIGN KEY (comment_id)
    REFERENCES comment(id) ON DELETE CASCADE;
    
ALTER TABLE organization_comment
    ADD CONSTRAINT organization_id_fk
    FOREIGN KEY (organization_id)
    REFERENCES organization(id) ON DELETE CASCADE;
    
INSERT INTO comment (id, value, create_date, user_id) 
    SELECT nextval('hibernate_sequence'), o.comments, null, null from organization o where o.comments is not null and o.comments <> '';
    
INSERT INTO organization_comment(organization_id, comment_id, idx)
    SELECT DISTINCT o.id, c.id, 0 FROM organization o INNER JOIN comment c ON o.comments=c.value
    AND c.id=(select max(id) from comment c2 where c2.value=c.value);
    
alter table organization drop column comments;



CREATE TABLE oc_address
(
   oc_id bigint NOT NULL,
   address_id bigint NOT NULL,
   CONSTRAINT oc_address_pkey PRIMARY KEY (oc_id,address_id)
)
;

ALTER TABLE oc_address
ADD CONSTRAINT address_oc_fk
FOREIGN KEY (address_id)
REFERENCES address(id)
;

ALTER TABLE oc_address
ADD CONSTRAINT oc_address_fk
FOREIGN KEY (oc_id)
REFERENCES oversightcommittee(id)
;


CREATE TABLE oc_email
(
   oc_id bigint NOT NULL,
   email_id bigint NOT NULL,
   idx int NOT NULL,
   CONSTRAINT oc_email_pkey PRIMARY KEY (oc_id,idx)
)
;
ALTER TABLE oc_email
ADD CONSTRAINT oc_email_fk
FOREIGN KEY (oc_id)
REFERENCES oversightcommittee(id)
;
ALTER TABLE oc_email
ADD CONSTRAINT email_oc_fk
FOREIGN KEY (email_id)
REFERENCES email(id)
;

CREATE TABLE oc_fax
(
   oc_id bigint NOT NULL,
   fax_id bigint NOT NULL,
   idx int NOT NULL,
   CONSTRAINT oc_fax_pkey PRIMARY KEY (oc_id,idx)
)
;
ALTER TABLE oc_fax
ADD CONSTRAINT fax_oc_fk
FOREIGN KEY (fax_id)
REFERENCES phonenumber(id)
;
ALTER TABLE oc_fax
ADD CONSTRAINT oc_fax_fk
FOREIGN KEY (oc_id)
REFERENCES oversightcommittee(id)
;

CREATE TABLE oc_phone
(
   oc_id bigint NOT NULL,
   phone_id bigint NOT NULL,
   idx int NOT NULL,
   CONSTRAINT oc_phone_pkey PRIMARY KEY (oc_id,idx)
)
;
ALTER TABLE oc_phone
ADD CONSTRAINT oc_phone_fk
FOREIGN KEY (oc_id)
REFERENCES oversightcommittee(id)
;
ALTER TABLE oc_phone
ADD CONSTRAINT phone_oc_fk
FOREIGN KEY (phone_id)
REFERENCES phonenumber(id)
;

CREATE TABLE oc_tty
(
   oc_id bigint NOT NULL,
   tty_id bigint NOT NULL,
   idx int NOT NULL,
   CONSTRAINT oc_tty_pkey PRIMARY KEY (oc_id,idx)
)
;
ALTER TABLE oc_tty
ADD CONSTRAINT tty_oc_fk
FOREIGN KEY (tty_id)
REFERENCES phonenumber(id)
;
ALTER TABLE oc_tty
ADD CONSTRAINT oc_tty_fk
FOREIGN KEY (oc_id)
REFERENCES oversightcommittee(id)
;

CREATE TABLE oc_url
(
   oc_id bigint NOT NULL,
   url_id bigint NOT NULL,
   idx int NOT NULL,
   CONSTRAINT oc_url_pkey PRIMARY KEY (oc_id,idx)
)
;
ALTER TABLE oc_url
ADD CONSTRAINT oc_url_fk
FOREIGN KEY (oc_id)
REFERENCES oversightcommittee(id)
;
ALTER TABLE oc_url
ADD CONSTRAINT url_oc_fk
FOREIGN KEY (url_id)
REFERENCES url(id)
;