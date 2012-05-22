

import groovy.sql.Sql
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder

def props = new Properties()
new File("resolved.build.properties").withInputStream {
  stream -> props.load(stream)
}

println "Using " + props['pa.jdbc.url'] + " to connect to PA database"
println "Using " + props['po.jdbc.url'] + " to connect to PO database"

def sourceConnection = Sql.newInstance(props['pa.jdbc.url'], props['pa.db.username'], 
    props['pa.db.password'], props['pa.jdbc.driver'])

def preLoader = new PoPreLoad();

def crsMap = preLoader.getCrsMap()
println "got Crs " + crsMap.size() 
def orgsMap = preLoader.getOrgsMap()
println "got Orgs " + orgsMap.size()
def hcfsMap = preLoader.getHcfsMap()
println "got HCFs " + hcfsMap.size()
def rosMap = preLoader.getRosMap()
println "got Ros " + rosMap.size() 



def ctgovRoId = sourceConnection.firstRow(Queries.roIdSQL, ['ClinicalTrials.gov']).identifier
def ctepRoId = sourceConnection.firstRow(Queries.roIdSQL, ['Cancer Therapy Evaluation Program']).identifier
def dcpRoId = sourceConnection.firstRow(Queries.roIdSQL, ['National Cancer Institute Division of Cancer Prevention']).identifier

def collabTrialsSQL = """
    select sp.identifier,
        nci_id.extension as nciId,
        ctepSs.local_sp_indentifier as ctepId,
        dcpSs.local_sp_indentifier as dcpId,
        ctgovSs.local_sp_indentifier as nctId,
        leadOrgSs.local_sp_indentifier as leadOrgId,
        leadOrgRo.assigned_identifier as leadRoId,
        sum4Org.assigned_identifier as sum4OrgId,
        sponsorRo.assigned_identifier as sponsorRoId,
        sp.public_tittle as brief_title,
        sp.public_description as brief_summary,
        sp.scientific_description as detailed_description,
        sp.official_title,
        respPartyCrs.assigned_identifier as respPartyCrsId,
        respPartySc.email as prim_phone,
        respPartySc.telephone as prim_email,
        ra_country.name || ' : ' || ra.authority_name as reg_authority,
        CASE 
            WHEN sos.status_code = 'APPROVED' then 'Approved'
            WHEN sos.status_code = 'IN_REVIEW' then 'In Review'
            WHEN sos.status_code = 'ACTIVE' then 'Active'
            WHEN sos.status_code = 'ENROLLING_BY_INVITATION' then 'Enrolling by Invitation'
            WHEN sos.status_code = 'CLOSED_TO_ACCRUAL' then 'Closed to Accrual'
            WHEN sos.status_code = 'CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Closed to Accrual and Intervention'
            WHEN sos.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL' then 'Temporarily Closed to Accrual'
            WHEN sos.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Temporarily Closed to Accrual and Intervention'
            WHEN sos.status_code = 'WITHDRAWN' then 'Withdrawn'
            WHEN sos.status_code = 'ADMINISTRATIVELY_COMPLETE' then 'Administratively Complete'
            WHEN sos.status_code = 'COMPLETED' then 'Complete'
            WHEN sos.status_code = 'COMPLETE' then 'Complete'
        END as current_trial_status,
        sos.status_date::date as current_trial_status_date,
        sp.start_date:: date as start_date,
        CASE
            WHEN sp.start_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.start_date_type_code = 'ANTICIPATED' then 'Anticipated'            
        END as start_date_type_code,
        sp.pri_compl_date::date as pri_compl_date,
        CASE
            WHEN sp.pri_compl_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.pri_compl_date_type_code = 'ANTICIPATED' then 'Anticipated'  
        END as pri_compl_date_type_code,
        CASE 
            WHEN sp.accr_rept_meth_code = 'ABBREVIATED' then 'Abbreviated'
            WHEN sp.accr_rept_meth_code = 'COMPLETE' then 'Complete'
            WHEN sp.accr_rept_meth_code = 'AE' then 'AE'
        END as accr_rept_meth_code,
        CASE 
            WHEN sp.primary_purpose_code = 'TREATMENT' then 'Treatment'
            WHEN sp.primary_purpose_code = 'PREVENTION' then 'Prevention'
            WHEN sp.primary_purpose_code = 'SCREENING' then 'Screening'
            WHEN sp.primary_purpose_code = 'DIAGNOSTIC' then 'Diagnostic'
            WHEN sp.primary_purpose_code = 'OTHER' then 'Other'
            WHEN sp.primary_purpose_code = 'BASIC_SCIENCE' then 'Basic Science'
            WHEN sp.primary_purpose_code = 'HEALTH_SERVICES_RESEARCH' then 'Health Services Research'
            WHEN sp.primary_purpose_code = 'SUPPORTIVE_CARE' then 'Supportive Care'
        END as primary_purpose_code,
        sp.phase_code,
        CASE 
            WHEN sp.allocation_code = 'RANDOMIZED_CONTROLLED_TRIAL' then 'Randomized'
            WHEN sp.allocation_code = 'NON_RANDOMIZED_TRIAL' then 'Non-randomized'
        END as allocation_code,
        CASE 
            WHEN sp.blinding_schema_code = 'OPEN' then 'Open'
            WHEN sp.blinding_schema_code = 'SINGLE_BLIND' then 'Single Blind'
            WHEN sp.blinding_schema_code = 'DOUBLE_BLIND' then 'Double Blind'    
        END as blinding_schema_code,
        CASE 
            WHEN sp.design_configuration_code = 'SINGLE_GROUP' then 'Single Group Assignment'
            WHEN sp.design_configuration_code = 'PARALLEL' then 'Parallel Assignment'
            WHEN sp.design_configuration_code = 'CROSSOVER' then 'Crossover Assignment'
            WHEN sp.design_configuration_code = 'FACTORIAL' then 'Factorial Assignment'
        END as design_configuration_code,
        sp.number_of_intervention_groups,
        CASE 
            WHEN sp.max_target_accrual_num is null then 0
            ELSE sp.max_target_accrual_num
        END as max_target_accrual_num,
        ov_off_crs.assigned_identifier as ovOffCrsId,
        subm_ru.prs_org_name,
        CASE WHEN sp.proprietary_trial_indicator then 'Abbreviated'
                ELSE 'Complete'
            END as category,
        CASE WHEN sp.fda_regulated_indicator THEN 'Yes'
                ELSE 'No'
            END as fda_indicator,
        CASE WHEN sp.section801_indicator THEN 'Yes'
                 ELSE 'No'
            END as section801_indicator,
        CASE WHEN sp.delayed_posting_indicator THEN 'Yes'
                 ELSE 'No'
            END as delayed_posting_indicator,   
        CASE WHEN sp.data_monty_comty_apptn_indicator THEN 'Yes'
                 ELSE 'No'
            END as dmc_indicator, 
        CASE
            WHEN summary4.type_code = 'EXTERNALLY_PEER_REVIEWED' then 'Externally Peer Reviewed'
            WHEN summary4.type_code = 'INSTITUTIONAL' then 'Institutional'
            WHEN summary4.type_code = 'INDUSTRIAL' then 'Industrial'
            WHEN summary4.type_code = 'NATIONAL' then 'National'
        END as summary4_type_code,
        CASE WHEN sp.accept_healthy_volunteers_indicator THEN 'Yes'
                 ELSE 'No'
            END as healthy_volunteer_indicator,
        processing_status.status_date_range_low::date as verification_date,
        sp.blinding_role_code_subject,
        sp.blinding_role_code_caregiver,
        sp.blinding_role_code_investigator,
        sp.blinding_role_code_outcome,
        CASE
            WHEN sp.study_classification_code = 'EFFICACY' then 'Efficacy Study'
            WHEN sp.study_classification_code = 'SAFETY_OR_EFFICACY' then 'Safety/Efficacy Study'
            WHEN sp.study_classification_code = 'BIO_EQUIVALENCE' then 'Bio-equivalence Study'
            WHEN sp.study_classification_code = 'BIO_AVAILABILITY' then 'Bio-availability Study'
            WHEN sp.study_classification_code = 'PHARMACOKINETICS' then 'Pharmacokinetics Study'
            WHEN sp.study_classification_code = 'PHARMACODYNAMICS' then 'Pharmacodynamics Study'
            WHEN sp.study_classification_code = 'PHARMACOKINETICS_OR_DYNAMICS' then 'Pharmacokinetics/dynamics Study'
            WHEN sp.study_classification_code = 'SAFETY' then 'Safety Study'
        END as classification_code
     from study_protocol sp
     join study_site sponsorSs on sponsorSs.study_protocol_identifier = sp.identifier and sponsorSs.functional_code = 'SPONSOR'
            and sponsorSs.research_organization_identifier in ($ctepRoId, $dcpRoId)
     join document_workflow_status dws on dws.study_protocol_identifier = sp.identifier
        and dws.status_code in ('ABSTRACTION_VERIFIED_NORESPONSE', 'ABSTRACTION_VERIFIED_RESPONSE')
        and dws.identifier=(select max(identifier) from document_workflow_status where document_workflow_status.study_protocol_identifier=sp.identifier)
     inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sp.identifier
        and nci_id.root = '2.16.840.1.113883.3.26.4.3'
     left outer join study_site ctepSs on ctepSs.study_protocol_identifier = sp.identifier and ctepSs.functional_code = 'IDENTIFIER_ASSIGNER'
        and ctepSs.research_organization_identifier = $ctepRoId
     left outer join study_site ctgovSs on ctgovSs.study_protocol_identifier = sp.identifier and ctgovSs.functional_code = 'IDENTIFIER_ASSIGNER'
        and ctgovSs.research_organization_identifier = $ctgovRoId
     left outer join study_site dcpSs on dcpSs.study_protocol_identifier = sp.identifier and dcpSs.functional_code = 'IDENTIFIER_ASSIGNER'
        and dcpSs.research_organization_identifier = $dcpRoId
     left outer join study_site leadOrgSs on leadOrgSs.study_protocol_identifier = sp.identifier and leadOrgSs.functional_code = 'LEAD_ORGANIZATION'
     join research_organization leadOrgRo on leadOrgRo.identifier = leadOrgSs.research_organization_identifier
     left outer join study_resourcing as summary4 on summary4.study_protocol_identifier = sp.identifier and summary4.summ_4_rept_indicator is true
        and summary4.identifier = (select max(identifier) from study_resourcing where study_protocol_identifier = sp.identifier and summ_4_rept_indicator is true)
     left outer join organization as sum4Org on cast(summary4.organization_identifier as integer) = sum4Org.identifier
     join research_organization sponsorRo on sponsorRo.identifier = sponsorSs.research_organization_identifier
     left outer join study_contact as respPartySc on respPartySc.study_protocol_identifier = sp.identifier and respPartySc.role_code = 'RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR'
     left outer join clinical_research_staff respPartyCrs on respPartyCrs.identifier = respPartySc.clinical_research_staff_identifier
     left outer join study_regulatory_authority sra on sra.study_protocol_identifier = sp.identifier
     left outer join regulatory_authority ra on ra.identifier = sra.regulatory_authority_identifier
     left outer join country ra_country on ra_country.identifier = ra.country_identifier
     join study_overall_status sos on sos.study_protocol_identifier = sp.identifier
         and (sos.identifier = (select max(identifier) from study_overall_status where study_protocol_identifier = sp.identifier))
     left outer join study_contact ov_off on ov_off.study_protocol_identifier = sp.identifier and ov_off.role_code = 'STUDY_PRINCIPAL_INVESTIGATOR'
     left outer join clinical_research_staff ov_off_crs on ov_off_crs.identifier = ov_off.clinical_research_staff_identifier
     left outer join csm_user subm_csm on subm_csm.user_id = sp.user_last_created_id
     left outer join registry_user subm_ru on subm_ru.csm_user_id = subm_csm.user_id
     left outer join document_workflow_status as processing_status on processing_status.study_protocol_identifier = sp.identifier
        and processing_status.identifier = (select max(identifier) from document_workflow_status where study_protocol_identifier = sp.identifier)
     where sp.status_code = 'ACTIVE'
"""

def nbOfTrials = 0

sourceConnection.eachRow(collabTrialsSQL) { spRow ->
    nbOfTrials++
    
    
	def out = new FileOutputStream("temp/" + spRow.nciId +  ".xml")
	def writer = new OutputStreamWriter( out , "UTF-8")
    writer.write """<?xml version="1.0" encoding="UTF-8"?>\n"""
	
    def xml = new MarkupBuilder(writer)
    xml.setDoubleQuotes(true)
    
	def studyProtocolID = spRow.identifier

	xml.clinical_study {
		xml.id_info {
			xml.org_study_id(spRow.leadOrgId)
			xml.secondary_id {
				xml.id(spRow.nciId)
				xml.id_type("Registry Identifier")
	            xml.id_domain("CTRP (Clinical Trial Reporting Program)")
			}
            sourceConnection.eachRow(Queries.otherIdsSQL, [studyProtocolID]) { row ->
                xml.secondary_id {
                    xml.id (row.extension)
                }
            }
            if (spRow.ctepId != null) {
    			xml.secondary_id {
    				xml.id(spRow.ctepId)
    				xml.id_type("ctep-id")
    	            xml.id_domain("CTEP")
    			}
            }
            if (spRow.nctId != null) {
    			xml.secondary_id {
    				xml.id(spRow.nctId)
    				xml.id_type("nct-id")
    	            xml.id_domain("NCT")
    			}
            }
            sourceConnection.eachRow(Queries.fundingsSQL, [studyProtocolID]) { row ->
                 if (row.funding_mechanism_code != null) {
                     xml.secondary_id {
                         xml.id(row.funding_mechanism_code + row.nih_institute_code + row.serial_number)
                         xml.id_type("NIH Grant Number")
                     }   
                 }
            }
            xml.org_name(spRow.prs_org_name == null || spRow.prs_org_name.size() == 0?
                "replace with PRS Organization Name you log in with":
                spRow.prs_org_name)
		}
		
		sourceConnection.eachRow(Queries.ownersSQL, [studyProtocolID]) { row ->
			xml.trial_owners {
				xml.name(row.ownerName)
			}
		}
		
		xml.lead_org {
            def roRow = rosMap.get(spRow.leadRoId.toLong())
            xml.name(roRow.orgname)
            xml.po_id(roRow.org_poid)
            xml.ctep_id(roRow.ctep_id)
            addressAndPhoneDetail(xml, roRow, null,true)
		
		}
        
        xml.nci_specific_information {
            xml.reporting_data_set_method(spRow.accr_rept_meth_code)
            xml.summary_4_funding_category(spRow.summary4_type_code)
            xml.summary_4_funding_sponsor_source {
                if (spRow.sum4OrgId != null) {
                    def sum4Org = orgsMap.get(spRow.sum4OrgId.toLong())
                    xml.name(sum4Org.name)
                    xml.po_id(sum4Org.org_poid)
                    addressAndPhoneDetail(xml, sum4Org, null, true)
                }
            }
        }

        xml.is_fda_regulated(spRow.fda_indicator)
        xml.is_section_801(spRow.section801_indicator)
        xml.delayed_posting(spRow.delayed_posting_indicator)
        xml.brief_title(spRow.brief_title)
        xml.official_title(spRow.official_title)

        xml.sponsors {
            xml.lead_sponsor {
                def roRow = rosMap.get(spRow.sponsorRoId.toLong())
                xml.name(changeSponsorNameIfNeeded(roRow.orgname))
                xml.po_id(roRow.org_poid)
                xml.ctep_id(roRow.ctep_id)
                addressAndPhoneDetail(xml, roRow, null, true)
            }
            xml.resp_party {
                xml.resp_party_person {
                    if (spRow.respPartyCrsId != null) {
                        def crsRow = crsMap.get(spRow.respPartyCrsId.toLong())
                        crsDetail(xml, crsRow)
                        addressAndPhoneDetail(xml, crsRow, spRow, true)
                    }
                }
                xml.resp_party_organization {
                    if (spRow.leadRoId != null) {
                        def roRow = rosMap.get(spRow.leadRoId.toLong())
                        xml.name(roRow.orgname)
                        xml.po_id(roRow.org_poid)
                        xml.ctep_id(roRow.ctep_id)
                        addressAndPhoneDetail(xml, roRow, null, true)
                    }
                }
            }
            sourceConnection.eachRow(Queries.collabsSQL, [studyProtocolID]) { collabRow ->
                xml.collaborator {
                    def roRow = rosMap.get(collabRow.ro_poid.toLong())
                    xml.name(collabRow.name)
                    xml.po_id(collabRow.org_poid)
                    xml.ctep_id(roRow.ctep_id)
                    addressAndPhoneDetail(xml, roRow, null, true)
                }                   
            }
        } // end sponsors
        
        xml.oversight_info {
            xml.regulatory_authority(spRow.reg_authority)
            xml.has_dmc(spRow.dmc_indicator)
        }
            
        xml.brief_summary {
            xml.textblock(spRow.brief_summary)
        }
        
        xml.detailed_description {
            xml.textblock(spRow.detailed_description)
        }
        
        xml.trial_status {
            xml.current_trial_status(spRow.current_trial_status)
            xml.current_trial_status_date(spRow.current_trial_status_date)
            xml.current_trial_start_date(spRow.start_date)
            xml.current_trial_start_date_type(spRow.start_date_type_code)
            xml.current_trial_completion_date(spRow.pri_compl_date)
            xml.current_trial_completion_date_type(spRow.pri_compl_date_type_code)
        }
        
        xml.trial_funding {
           sourceConnection.eachRow(Queries.fundingsSQL, [studyProtocolID]) { fundRow ->
               if (fundRow.funding_mechanism_code != null) {
                   xml.funding_info {
                       xml.funding_code(fundRow.funding_mechanism_code)
                       xml.funding_nih_inst_code(fundRow.nih_institute_code)
                       xml.funding_serial_number(fundRow.serial_number)
                       xml.funding_nci_div_program(fundRow.nci_division_program_code)
                   }   
               }
           }
        }
        
        xml.study_design {
            xml.study_type("Interventional")
            xml.interventional_design {
                xml.interventional_subtype(spRow.primary_purpose_code)
                xml.phase(spRow.phase_code)
                xml.allocation(spRow.allocation_code)
                xml.masking(spRow.blinding_schema_code)
                if (spRow.blinding_role_code_caregiver != null)
                    xml.masked_caregiver("Yes")
                if (spRow.blinding_role_code_investigator != null)
                    xml.masked_investigator("Yes")
                if (spRow.blinding_role_code_subject != null)
                    xml.masked_subject("Yes")
                if (spRow.blinding_role_code_outcome != null)
                    xml.masked_outcome("Yes")
                xml.assignment(spRow.design_configuration_code)
                xml.endpoint(spRow.classification_code)
                xml.number_of_arms(spRow.number_of_intervention_groups)
            }
        }
        
        sourceConnection.eachRow(Queries.primOutcomesSQL, [studyProtocolID]) { row ->
            xml.primary_outcome {
                xml.outcome_measure(row.prim_som_name);
                xml.outcome_safety_issue(row.prim_som_safety_ind)
                xml.outcome_time_frame(row.prim_som_timeframe)
            }
        }
        
        xml.disease_conditions {
            sourceConnection.eachRow(Queries.conditionsSQL, [studyProtocolID]) { row ->
                xml.condition_info {
                    xml.preferred_name(row.preferred_name)
                    xml.disease_code(row.disease_code)
                    if (row.nci_thesaurus_id != null)
                        xml.nci_thesaurus_id(row.nci_thesaurus_id)
                    xml.menu_display_name(row.menu_display_name)
                }
            }
        }

        xml.enrollment(spRow.max_target_accrual_num)
        xml.enrollment_type("anticipated")     
        
        // try to be cheap and steal a few cycles by running 1 query instead of 3.
        def allArmsAndInt = []
        sourceConnection.eachRow(Queries.armsSQL, [studyProtocolID]) { row ->
             allArmsAndInt.add(row.toRowResult())   
        }
                
        // get arms out of it. 
        def armsList = []                        
        allArmsAndInt.each {
            def row = it
            if (!armsList.contains(row.arm_id)) {
                xml.arm_group {
                    xml.arm_group_label(row.arm_name)
                    xml.arm_type(row.arm_type)
                    xml.arm_group_description {
                        xml.textblock(row.arm_desc)
                    }
                }
                armsList.add(row.arm_id)
            }
        }
        
        def intsList =[]
        allArmsAndInt.each {
            def intRow = it
            if (!intsList.contains(intRow.int_id)) {
                xml.intervention("cdr-id": intRow.cdr_id) {
                    xml.intervention_type(intRow.int_type)
                    xml.intervention_name(intRow.int_name)
                    if (intRow.int_desc != null) {
                        xml.intervention_description {
                            xml.textblock(intRow.int_desc)
                        }
                    }
                    // other names
                    otherNamesList = []
                    allArmsAndInt.each {
                        def otherNameRow = it
                        if (intRow.int_id == otherNameRow.int_id && !otherNamesList.contains(otherNameRow.alt_name) 
                            && otherNameRow.alt_name != null && otherNameRow.alt_name.size() > 0) {
                            xml.intervention_other_name(otherNameRow.alt_name)
                            otherNamesList.add(otherNameRow.alt_name)
                        }
                    }
                    
                    groupNamesList = []
                    allArmsAndInt.each {
                        def groupRow = it
                        if (intRow.int_id == groupRow.int_id && !groupNamesList.contains(groupRow.arm_name)) {
                            xml.arm_group_label(groupRow.arm_name)
                            groupNamesList.add(groupRow.arm_name)
                        }
                    }
                }
                intsList.add(intRow.int_id)
            }
        }
        
        xml.eligibility {
            def gender
            def minAge
            def maxAge
            sourceConnection.eachRow(Queries.eligsSQL, [studyProtocolID]) { row ->
                if (row.gender != null) {
                    gender = row.gender
                } else if (row.criterion_name == 'AGE') {
                    minAge = row.min_age
                    maxAge = row.max_age
                } else if (row.elig_data != null) {
                xml.criteria {
                        xml.criterion {
                            xml.type(row.elig_type)
                            xml.data("  - " + row.elig_data)
                        }
                    }
                }
            }
            xml.healthy_volunteers(spRow.healthy_volunteer_indicator)
            if (gender != null) {
                xml.gender(gender)
            }
            xml.minimum_age(minAge)
            xml.maximum_age(maxAge)
        }
        
        xml.overall_official {
            def crsRow = crsMap.get(spRow.ovOffCrsId.toLong())
            crsDetail(xml, crsRow)
            addressAndPhoneDetail(xml, crsRow, null, true) 
            xml.role("Principal Investigator")
            xml.affiliation {
                def roRow = rosMap.get(spRow.leadRoId.toLong())
                xml.name(roRow.orgname)
                xml.po_id(roRow.org_poid)
                xml.ctep_id(roRow.ctep_id)
                addressAndPhoneDetail(xml, roRow, null, true)
            }
        }
    
		sourceConnection.eachRow(Queries.partSitesSQL, [studyProtocolID]) { row ->
			xml.location {
				xml.facility {
					def hcfRow = hcfsMap.get(row.hcf_poid.toLong())
                    xml.name(row.name)
                    xml.po_id(row.org_poid)
                    xml.ctep_id(hcfRow.ctep_id)
                    addressAndPhoneDetail(xml, hcfRow, null, false)
				}
                xml.status(row.status)
                if (row.prim_crs_id != null && crsMap.get(row.prim_crs_id.toLong()) != null) {
                    xml.contact {
                        def crsRow = crsMap.get(row.prim_crs_id.toLong())
                        crsDetail(xml, crsRow)
                        addressAndPhoneDetail(xml, crsRow, row, true)
                    }
                }
                if (row.inv_crs_id != null && crsMap.get(row.inv_crs_id.toLong()) != null) {
                     xml.investigator {
                         def crsRow = crsMap.get(row.inv_crs_id.toLong())
                         crsDetail(xml, crsRow)
                         addressAndPhoneDetail(xml, crsRow, null, true)
                         xml.role("Principal Investigator")
                     }   
                }
			}
		}  // end part sites
        xml.verification_date(spRow.verification_date)      
	}
    writer.flush();
    writer.close();
}

void crsDetail(MarkupBuilder xml, Object crsRow) {
    xml.first_name(crsRow.firstname)
    if (crsRow.middlename != null)
        xml.middle_initial(crsRow.middlename.substring(0, 1))
    xml.last_name(crsRow.lastname)
    xml.po_id(crsRow.person_poid)
    if (crsRow.ctep_id != null)
        xml.ctep_id(crsRow.ctep_id)
}

void addressAndPhoneDetail(MarkupBuilder xml, Object row, Object spRow, boolean suppressPhoneAndEmail) {  
    xml.address {
        xml.street(row.streetaddressline)
        xml.city(row.cityormunicipality)
        xml.state(row.stateorprovince)
        xml.zip(row.postalcode)
        xml.country(row.country_name)
    }
    
    xml.phone((spRow!=null && spRow.prim_phone!=null?spRow.prim_phone:(suppressPhoneAndEmail?"":row.phone)))
    
    if (row.faxnumber != null) {
        xml.fax(suppressPhoneAndEmail?"":row.faxnumber)
    }
    
    xml.email((spRow!=null && spRow.prim_email!=null?spRow.prim_email:(suppressPhoneAndEmail?"":row.email)))
}

String changeSponsorNameIfNeeded(orgName) {
    return (orgName==Constants.CTEP_ORG_NAME || orgName==Constants.DCP_ORG_NAME)?Constants.NCI_ORG_NAME:orgName 
}

println "Generated $nbOfTrials files"
