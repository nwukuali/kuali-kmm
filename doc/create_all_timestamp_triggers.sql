DECLARE
    CURSOR tables IS 
        SELECT table_name
            FROM user_tab_columns
            WHERE column_name = 'LAST_UPDATE_TS'
             AND data_type LIKE 'TIMESTAMP%'
             ORDER BY 1;
BEGIN
    FOR rec IN tables LOOP
        EXECUTE IMMEDIATE 'CREATE OR REPLACE TRIGGER '||LOWER( SUBSTR( rec.table_name, 1, 27) )||'_tr BEFORE INSERT OR UPDATE ON '
            ||LOWER( rec.table_name )||' FOR EACH ROW BEGIN :new.last_update_ts := CURRENT_TIMESTAMP(); END;';
    END LOOP;
END;
/
