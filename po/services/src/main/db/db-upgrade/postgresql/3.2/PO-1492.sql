CREATE OR REPLACE FUNCTION fill_in_unknown_person_email () RETURNS VOID AS '
  DECLARE
     
     -- Declare a variable for the new email ID number.
    email_id INTEGER;
     
     -- Declare a variable to hold rows from the
     -- person table
    row_data person%ROWTYPE;
  
  BEGIN
     
     -- Iterate through the results of a query.
    FOR row_data IN select p.* from person p left join person_email pe on p.id = pe.person_id 
    where pe.email_id is null LOOP

       -- Insert a fake email address
       email_id := nextval(''hibernate_sequence'');
       insert into email values (email_id ,''unknown@example.com'');
       
       -- Insert the person_id/email_id into the person_emnail table.
      insert into person_email values (row_data.id, email_id, ''0'');
    END LOOP;

  END;
' LANGUAGE plpgsql;

select fill_in_unknown_person_email ();

