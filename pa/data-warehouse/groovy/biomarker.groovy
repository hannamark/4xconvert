import groovy.sql.Sql

def sql = """select marker.assay_purpose_code, marker.assay_purpose_other_text, marker.assay_type_code, marker.assay_type_other_text,
            marker.assay_use_code, marker.identifier, marker.name, marker.long_name, nci_id.extension, marker.status_code,
            marker.tissue_collection_method_code, marker.tissue_specimen_type_code
            from planned_activity pa
            inner join study_protocol as sp on sp.identifier = pa.study_protocol_identifier
            inner join planned_marker as marker on marker.identifier = pa.identifier
            inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = pa.study_protocol_identifier
                        and nci_id.root = '2.16.840.1.113883.3.26.4.3'
            where sp.status_code = 'ACTIVE';"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def biomarkers = destinationConnection.dataSet("DW_STUDY_BIOMARKER");
sourceConnection.eachRow(sql) { row ->
    biomarkers.add(assay_purpose: row.assay_purpose_code, assay_purpose_description: row.assay_purpose_other_text, assay_type_code: row.assay_type_code,
            assay_type_description: row.assay_type_other_text, assay_use:row.assay_use_code, internal_system_id: row.identifier,
            name:row.long_name, long_name: row.name, nci_id: row.extension, status_code: row.status_code,
            tissue_collection_method_code: row.tissue_collection_method_code, tissue_specimen_type_code: row.tissue_specimen_type_code)
}