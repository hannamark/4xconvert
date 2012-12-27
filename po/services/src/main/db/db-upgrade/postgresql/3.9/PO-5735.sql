CREATE TABLE person_comment
(
   person_id int8 NOT NULL,
   comment_id int8 NOT NULL,
   idx int NOT NULL,
   CONSTRAINT person_comment_pkey PRIMARY KEY (person_id,idx)
);

ALTER TABLE person_comment
    ADD CONSTRAINT comment_org_fk
    FOREIGN KEY (comment_id)
    REFERENCES comment(id) ON DELETE CASCADE;
    
ALTER TABLE person_comment
    ADD CONSTRAINT person_id_fk
    FOREIGN KEY (person_id)
    REFERENCES person(id) ON DELETE CASCADE;
    
INSERT INTO comment (id, value, create_date, user_id) 
    SELECT nextval('hibernate_sequence'), p.comments, null, null from person p where p.comments is not null and p.comments <> '';
    
INSERT INTO person_comment(person_id, comment_id, idx)
    SELECT DISTINCT o.id, c.id, 0 FROM person o INNER JOIN comment c ON o.comments=c.value
    AND c.id=(select max(id) from comment c2 where c2.value=c.value);
    
alter table person drop column comments;