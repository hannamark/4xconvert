import groovy.sql.Sql

def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

destinationConnection.execute("""update stg_dw_grants_p30 p30
                                 set family_name = fo.family_name
                                 from stg_dw_family_organization fo WHERE (p30.family_id = fo.family_id) 
                              """);