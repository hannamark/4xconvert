import groovy.sql.Sql

def sql = """SELECT appl_id, grant_number, grant_type_code, impac_ii_activity_code, primary_icd_code,
                    serial_number, support_year, suffix_code, fy, budget_start_date, budget_end_date,
                    project_title, project_start_date, project_end_date, institution_name, pi_name_prefix,
                    pi_first_name, pi_mi_name, pi_last_name, pi_name_suffix, pi_title, institution_state,
                    institution_city, institution_zip, institution_country, institution_email
             FROM ctrp_grants_r_vw 
          """
def i2eConnection = Sql.newInstance('jdbc:oracle:thin:ctrp_ru/XO4DJLXR@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=nci-oraclecm-dev.nci.nih.gov)(PORT=1610))(CONNECT_DATA=(SERVICE_NAME=NDMD.NCI.NIH.GOV)))'
    , 'oracle.jdbc.driver.OracleDriver')

def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def i2eGrants = destinationConnection.dataSet("stg_dw_grants_i2e")

i2eConnection.eachRow(sql) { row ->
    i2eGrants.add( 
        appl_id                 : row.appl_id,
        grant_number            : row.grant_number,
        grant_type_code         : row.grant_type_code,
        impac_ii_activity_code  : row.impac_ii_activity_code,
        primary_icd_code        : row.primary_icd_code,
        serial_number           : row.serial_number,
        support_year            : row.support_year,
        suffix_code             : row.suffix_code,
        fy                      : row.fy,
        budget_start_date       : row.budget_start_date,
        budget_end_date         : row.budget_end_date,
        project_title           : row.project_title,
        project_start_date      : row.project_start_date,
        project_end_date        : row.project_end_date,
        institution_name        : row.institution_name,
        pi_name_prefix          : row.pi_name_prefix,
        pi_first_name           : row.pi_first_name,
        pi_mi_name              : row.pi_mi_name,
        pi_last_name            : row.pi_last_name,
        pi_name_suffix          : row.pi_name_suffix,
        pi_title                : row.pi_title,
        institution_state       : row.institution_state,
        institution_city        : row.institution_city,
        institution_zip         : row.institution_zip,
        institution_country     : row.institution_country,
        institution_email       : row.institution_email
        )
}
