import groovy.sql.Sql

def sql = """select cu.user_id as user_id, 
                    cu.login_name as login_name,
                    CASE WHEN NULLIF(ru.first_name, '') is not null THEN ru.first_name || ' ' || ru.last_name
                      WHEN NULLIF(split_part(cu.login_name, 'CN=', 2), '') is null THEN cu.login_name
                      ELSE split_part(cu.login_name, 'CN=', 2) 
                    END as name,
                    ru.first_name as first_name,
                    ru.last_name as last_name
		from csm_user cu
		left outer join registry_user as ru on ru.csm_user_id = cu.user_id 
                """

def sourceConnectionPa = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def users = destinationConnection.dataSet("STG_DW_USER")

sourceConnectionPa.eachRow(sql) { row ->
    users.add(
		CSM_USER_ID: row.user_id,
		LOGIN_NAME: row.login_name,
		NAME: row.name,
		FIRST_NAME: row.first_name,
		LAST_NAME: row.last_name
	)
}