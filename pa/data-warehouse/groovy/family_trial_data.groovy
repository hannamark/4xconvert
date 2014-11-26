import groovy.sql.Sql
import org.apache.commons.lang.StringUtils


def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

destinationConnection.execute("""INSERT INTO stg_dw_family_trial_data
                                 SELECT DISTINCT org_org_family, nci_id, NULL
                                 FROM stg_dw_study_participating_site
                                 WHERE org_org_family IS NOT NULL
                                   AND COALESCE(program_code, '') != ''""");

destinationConnection.eachRow("""SELECT DISTINCT org_org_family, nci_id, program_code
                                 FROM stg_dw_study_participating_site
                                 WHERE org_org_family IS NOT NULL
                                   AND COALESCE(program_code, '') != ''
                                   ORDER BY program_code""") { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_family_trial_data
                                     SET program_codes = program_codes || chr(13) || ?
                                     WHERE family_name = ?
                                       AND nci_id = ?
                                       AND program_codes IS NOT NULL
                                  """, [row.program_code, row.org_org_family, row.nci_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_family_trial_data
                                     SET program_codes = ?
                                     WHERE family_name = ?
                                       AND nci_id = ?
                                       AND program_codes IS NULL
                                 """, [row.program_code, row.org_org_family, row.nci_id]);
}
								   
								   
								   destinationConnection.close()
