CREATE TYPE table_count AS (table_name TEXT, num_rows INTEGER); 

CREATE OR REPLACE FUNCTION count_em_all () RETURNS SETOF table_count  AS '
DECLARE 
    the_count RECORD; 
    t_name RECORD; 
    r table_count%ROWTYPE; 

BEGIN
    FOR t_name IN 
        SELECT 
            c.relname
        FROM
            pg_catalog.pg_class c LEFT JOIN pg_namespace n ON n.oid = c.relnamespace
        WHERE 
            c.relkind = ''r''
            AND n.nspname = ''public'' 
        ORDER BY 1 
        LOOP
            FOR the_count IN EXECUTE ''SELECT COUNT(*) AS "count" FROM '' || t_name.relname 
            LOOP 
            END LOOP; 

            r.table_name := t_name.relname; 
            r.num_rows := the_count.count; 
            RETURN NEXT r; 
        END LOOP; 
        RETURN; 
END;
' LANGUAGE plpgsql; 
