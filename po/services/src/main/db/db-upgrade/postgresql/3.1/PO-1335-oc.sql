CREATE OR REPLACE FUNCTION migrate_oc_otheridentifier(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_otheridentifier_id INTEGER;
    oc_current_idx INTEGER;
    new_null_flavor TEXT;
    new_displayable BOOLEAN;
    new_extension TEXT;
    new_identifier_name TEXT;
    new_reliability TEXT;
    new_root TEXT;
    new_scope TEXT;
BEGIN
    -- For all currentOCId otheridentifier...
    FOR ocrecord IN SELECT * FROM orgcontact_otheridentifier WHERE orgcontact_id=currentOCId LOOP
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new otheridentifier record, and get the id.
            SELECT INTO
                new_null_flavor, new_displayable, new_extension, new_identifier_name, new_reliability, new_root, new_scope
                null_flavor, displayable, extension, identifier_name, reliability, root, scope
            FROM orgcontact_otheridentifier WHERE orgcontact_id = currentOCId;

            INSERT INTO
                orgcontact_otheridentifier(orgcontact_id, null_flavor, displayable, extension, identifier_name, reliability, root, scope)
                VALUES (oc_new_id, new_null_flavor, new_displayable, new_extension, new_identifier_name, new_reliability, new_root, new_scope);
        END LOOP;
    END LOOP;
    -- Delete the old OC url mapping.
    DELETE FROM orgcontact_otheridentifier WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION migrate_oc_url(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_url_id INTEGER;
    oc_current_idx INTEGER;
    new_url_id INTEGER;
    new_value TEXT;
BEGIN
    -- For all currentOCId url...
    FOR ocrecord IN SELECT * FROM orgcontact_url WHERE orgcontact_id=currentOCId LOOP
        oc_current_url_id := ocrecord.url_id;
        oc_current_idx := ocrecord.idx;
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new url record, and get the id.
            SELECT INTO new_value value FROM url WHERE id = oc_current_url_id;
            new_url_id := nextval(''hibernate_sequence'');
            INSERT INTO url(id, value) VALUES(new_url_id, new_value);
            INSERT INTO orgcontact_url(orgcontact_id, url_id, idx) VALUES (oc_new_id, new_url_id, oc_current_idx);
        END LOOP;
        -- Delete the redundant/old url.
        DELETE FROM orgcontact_url WHERE url_id=oc_current_url_id;
        DELETE FROM url WHERE id=oc_current_url_id;
    END LOOP;
    -- Delete the old OC url mapping.
    DELETE FROM orgcontact_url WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION migrate_oc_tty(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_tty_id INTEGER;
    oc_current_idx INTEGER;
    new_tty_id INTEGER;
    new_value TEXT;
BEGIN
    -- For all currentOCId tty...
    FOR ocrecord IN SELECT * FROM orgcontact_tty WHERE orgcontact_id=currentOCId LOOP
        oc_current_tty_id := ocrecord.tty_id;
        oc_current_idx := ocrecord.idx;
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new tty record, and get the id.
            SELECT INTO new_value value FROM phonenumber WHERE id = oc_current_tty_id;
            new_tty_id := nextval(''hibernate_sequence'');
            INSERT INTO phonenumber(id, value) VALUES(new_tty_id, new_value);
            INSERT INTO orgcontact_tty(orgcontact_id, tty_id, idx) VALUES (oc_new_id, new_tty_id, oc_current_idx);
        END LOOP;
        -- Delete the redundant/old tty.
        DELETE FROM orgcontact_tty WHERE tty_id=oc_current_tty_id;
        DELETE FROM phonenumber WHERE id=oc_current_tty_id;
    END LOOP;
    -- Delete the old OC tty mapping.
    DELETE FROM orgcontact_tty WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION migrate_oc_phone(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_phone_id INTEGER;
    oc_current_idx INTEGER;
    new_phone_id INTEGER;
    new_value TEXT;
BEGIN
    -- For all currentOCId phones...
    FOR ocrecord IN SELECT * FROM orgcontact_phone WHERE orgcontact_id=currentOCId LOOP
        oc_current_phone_id := ocrecord.phone_id;
        oc_current_idx := ocrecord.idx;
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new phone record, and get the id.
            SELECT INTO new_value value FROM phonenumber WHERE id = oc_current_phone_id;
            new_phone_id := nextval(''hibernate_sequence'');
            INSERT INTO phonenumber(id, value) VALUES(new_phone_id, new_value);
            INSERT INTO orgcontact_phone(orgcontact_id, phone_id, idx) VALUES (oc_new_id, new_phone_id, oc_current_idx);
        END LOOP;
        -- Delete the redundant/old phone.
        DELETE FROM orgcontact_phone WHERE phone_id=oc_current_phone_id;
        DELETE FROM phonenumber WHERE id=oc_current_phone_id;
    END LOOP;
    -- Delete the old OC phone mapping.
    DELETE FROM orgcontact_phone WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION migrate_oc_fax(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_fax_id INTEGER;
    oc_current_idx INTEGER;
    new_fax_id INTEGER;
    new_value TEXT;
BEGIN
    -- For all currentOCId fax...
    FOR ocrecord IN SELECT * FROM orgcontact_fax WHERE orgcontact_id=currentOCId LOOP
        oc_current_fax_id := ocrecord.fax_id;
        oc_current_idx := ocrecord.idx;
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new fax record, and get the id.
            SELECT INTO new_value value FROM phonenumber WHERE id = oc_current_fax_id;
            new_fax_id := nextval(''hibernate_sequence'');
            INSERT INTO phonenumber(id, value) VALUES(new_fax_id, new_value);
            INSERT INTO orgcontact_fax(orgcontact_id, fax_id, idx) VALUES (oc_new_id, new_fax_id, oc_current_idx);
        END LOOP;
        -- Delete the redundant/old fax.
        DELETE FROM orgcontact_fax WHERE fax_id=oc_current_fax_id;
        DELETE FROM phonenumber WHERE id=oc_current_fax_id;
    END LOOP;
    -- Delete the old OC fax mapping.
    DELETE FROM orgcontact_fax WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION migrate_oc_email(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_email_id INTEGER;
    oc_current_idx INTEGER;
    new_email_id INTEGER;
    new_value TEXT;
BEGIN
    -- For all currentOCId emails...
    FOR ocrecord IN SELECT * FROM orgcontact_email WHERE orgcontact_id=currentOCId LOOP
        oc_current_email_id := ocrecord.email_id;
        oc_current_idx := ocrecord.idx;
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new email record, and get the id.
            SELECT INTO new_value value FROM email WHERE id = oc_current_email_id;

            new_email_id := nextval(''hibernate_sequence'');
            INSERT INTO email(id, value) VALUES(new_email_id, new_value);
            INSERT INTO orgcontact_email(orgcontact_id, email_id, idx) VALUES (oc_new_id, new_email_id, oc_current_idx);
        END LOOP;
        -- Delete the redundant/old email.
        DELETE FROM orgcontact_email WHERE email_id=oc_current_email_id;
        DELETE FROM email WHERE id=oc_current_email_id;
    END LOOP;
    -- Delete the old OC email mapping.
    DELETE FROM orgcontact_email WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION migrate_oc_address(currentOCId integer, newOCIds integer[] ) RETURNS VOID AS '
DECLARE
    ocrecord RECORD;
    oc_new_id INTEGER;
    oc_current_address_id INTEGER;
    new_address_id INTEGER;
    new_streetaddressline TEXT;
    new_deliveryaddressline TEXT;
    new_cityormunicipality TEXT;
    new_postalcode TEXT;
    new_stateorprovince TEXT;
    new_country_id INTEGER;
BEGIN
    -- For all currentOCId addresses...
    FOR ocrecord IN SELECT address_id FROM orgcontact_address WHERE orgcontact_id=currentOCId LOOP
        oc_current_address_id := ocrecord.address_id;
        -- For all new OC Ids, do...
        FOR i IN array_lower(newOCIds, 1) .. array_upper(newOCIds, 1) LOOP
            oc_new_id := newOCIds[i];
            -- Create a new address record, and get the id.
            SELECT INTO
                new_streetaddressline, new_deliveryaddressline, new_cityormunicipality, new_postalcode, new_stateorprovince, new_country_id
                streetaddressline, deliveryaddressline, cityormunicipality, postalcode, stateorprovince, country_id
            FROM address
            WHERE id = oc_current_address_id;

            new_address_id := nextval(''hibernate_sequence'');
            INSERT INTO address(id, streetaddressline, deliveryaddressline, cityormunicipality, postalcode, stateorprovince, country_id)
            VALUES(new_address_id, new_streetaddressline, new_deliveryaddressline, new_cityormunicipality, new_postalcode, new_stateorprovince, new_country_id);

            INSERT INTO orgcontact_address(orgcontact_id, address_id) VALUES (oc_new_id, new_address_id);
        END LOOP;
        -- Delete the redundant/old address.
        DELETE FROM orgcontact_address WHERE address_id=oc_current_address_id;
        DELETE FROM address WHERE id=oc_current_address_id;
    END LOOP;
    -- Delete the old OC address mapping.
    DELETE FROM orgcontact_address WHERE orgcontact_id=currentOCId;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION migrate_oc_types() RETURNS VOID AS '
DECLARE
    ocrecord1 RECORD;
    ocrecord2 RECORD;

    oc_orgcontacttypes_octypeid INTEGER;
    oc_status TEXT;
    oc_statusdate DATE;
    oc_org_id INTEGER;
    oc_person_id INTEGER;
    oc_duplicate_of INTEGER;
    oc_title TEXT;

    oc_old_id INTEGER;
    oc_new_id INTEGER;
    oc_new_ids INTEGER[];
    counter INTEGER := 0;
BEGIN
    ALTER TABLE orgcontact_types DROP CONSTRAINT orgcnct_type_orgcnct_fk;
    -- Get distinct OC Ids
    FOR ocrecord1 IN SELECT DISTINCT(orgcontact_id) from orgcontact_types LOOP
        oc_old_id := ocrecord1.orgcontact_id;
        -- For each distinct OC Id and Type Id combination, create new OC with the Type.
        FOR ocrecord2 IN SELECT orgcontacttype_id FROM orgcontact_types WHERE orgcontact_id=oc_old_id LOOP
            oc_orgcontacttypes_octypeid := ocrecord2.orgcontacttype_id;

            SELECT INTO
                oc_status, oc_statusdate, oc_org_id, oc_person_id, oc_duplicate_of, oc_title
                status, statusdate, organization_id, person_id, duplicate_of, title
            FROM organizationalcontact
            WHERE id = oc_old_id;

            oc_new_id := nextval(''hibernate_sequence'');
            oc_new_ids[counter] := oc_new_id; -- Add the new Id to the array.
            counter := counter + 1;

            INSERT INTO organizationalcontact(id, status, statusdate, organization_id, person_id, duplicate_of, title, orgcontacttype_id)
            VALUES (oc_new_id, oc_status, oc_statusdate, oc_org_id, oc_person_id, oc_duplicate_of, oc_title, oc_orgcontacttypes_octypeid);
        END LOOP;
            -- Migrate the address info.
            PERFORM migrate_oc_address(oc_old_id, oc_new_ids);
            PERFORM migrate_oc_email(oc_old_id, oc_new_ids);
            PERFORM migrate_oc_fax(oc_old_id, oc_new_ids);
            PERFORM migrate_oc_phone(oc_old_id, oc_new_ids);
            PERFORM migrate_oc_tty(oc_old_id, oc_new_ids);
            PERFORM migrate_oc_url(oc_old_id, oc_new_ids);
            PERFORM migrate_oc_otheridentifier(oc_old_id, oc_new_ids);

            -- Delete the old OC Id
            DELETE FROM organizationalcontact WHERE id=oc_old_id;
    END LOOP;
    -- Drop the table orgcontact_types
    DROP TABLE orgcontact_types;
END;
' LANGUAGE plpgsql;

