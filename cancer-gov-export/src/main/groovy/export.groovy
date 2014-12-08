import groovy.sql.Sql
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import org.apache.commons.lang3.StringUtils

def resolvedProperties = [:]

if (!properties['ant.home']) {
	println 'We are not running from Ant; so accepting properties passed to JVM'
	resolvedProperties << System.getProperties()
} else
	resolvedProperties << properties

def poJdbcUrl = "jdbc:postgresql://${resolvedProperties['database.server']}:${resolvedProperties['database.port']}/${resolvedProperties['database.name']}"
def paJdbcUrl = "jdbc:postgresql://${resolvedProperties['db.server']}:${resolvedProperties['db.port']}/${resolvedProperties['db.name']}"

println "PO DB: ${poJdbcUrl}"
println "PA DB: ${paJdbcUrl}"

def poConn = Sql.newInstance(poJdbcUrl, resolvedProperties['database.user'],
		resolvedProperties['po.db.password'], 'org.postgresql.Driver')
def paConn = Sql.newInstance(paJdbcUrl, , resolvedProperties['db.username'],
		resolvedProperties['pa.db.password'], 'org.postgresql.Driver')

// Cache some PO data...
def preLoader = new PoPreLoad(poConn)
def crsMap = preLoader.getCrsMap()
println "got Crs " + crsMap.size()
def orgsMap = preLoader.getOrgsMap()
println "got Orgs " + orgsMap.size()
def hcfsMap = preLoader.getHcfsMap()
println "got HCFs " + hcfsMap.size()
def rosMap = preLoader.getRosMap()
println "got Ros " + rosMap.size()


def getTrialsSQL = """
    select sp.identifier,
        nci_id.extension as nciId,
        ctepSs.local_sp_indentifier as ctepId,
        dcpSs.local_sp_indentifier as dcpId,
		ccr.local_sp_indentifier as ccrId,
        rv_trial_id_nct.local_sp_indentifier as nctId,
        leadOrgSs.local_sp_indentifier as leadOrgId,        
		leadOrgSs.name as leadOrgName,
		sponsorSs.name as sponsorOrgName,
		submitter.submitter_org_name as source,
        sum4Org.assigned_identifier as sum4OrgId,        
        sp.public_tittle as brief_title,
        sp.public_description as brief_summary,
        sp.scientific_description as detailed_description,
        sp.official_title,
		sp.acronym,		
        respPartyCrs.assigned_identifier as respPartyCrsId,
        respPartySc.email as prim_email,
        respPartySc.telephone as prim_phone,
        respPartySponsorContact.telephone as respPartySponsorPhone,
        respPartySponsorContact.email as respPartySponsorEmail,
        central_contact.email as centralContactEmail,
        central_contact.telephone as centralContactPhone,
        respPartySponsorContact.identifier as respPartySponsorIdentifier,
        ra_country.name || ': ' || ra.authority_name as reg_authority,
		ra_country.identifier as reg_authority_country_id,
        CASE
            WHEN sos.status_code = 'APPROVED' then 'Approved for marketing'
            WHEN sos.status_code = 'IN_REVIEW' then 'Not yet recruiting'
            WHEN sos.status_code = 'ACTIVE' then 'Recruiting'
            WHEN sos.status_code = 'ENROLLING_BY_INVITATION' then 'Enrolling by invitation'
            WHEN sos.status_code = 'CLOSED_TO_ACCRUAL' then 'Active, not recruiting'
            WHEN sos.status_code = 'CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Active, not recruiting'
            WHEN sos.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL' then 'Suspended'
            WHEN sos.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Suspended'
            WHEN sos.status_code = 'WITHDRAWN' then 'Withdrawn'
            WHEN sos.status_code = 'ADMINISTRATIVELY_COMPLETE' then 'Terminated'
            WHEN sos.status_code = 'COMPLETED' then 'Completed'
            WHEN sos.status_code = 'COMPLETE' then 'Completed'
        END as current_trial_status,
		CASE
            WHEN sos.status_code in ('TEMPORARILY_CLOSED_TO_ACCRUAL','TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION',
				'WITHDRAWN','ADMINISTRATIVELY_COMPLETE') THEN sos.comment_text			
        END as why_stopped,
        sos.status_date::date as current_trial_status_date,
        sp.start_date:: date as start_date,
        CASE
            WHEN sp.start_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.start_date_type_code = 'ANTICIPATED' then 'Anticipated'
        END as start_date_type_code,
        sp.completion_date::date as completion_date,
        CASE
            WHEN sp.completion_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.completion_date_type_code = 'ANTICIPATED' then 'Anticipated'
        END as completion_date_type_code,
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
        pp.code as primary_purpose_code,
		CASE
            WHEN sp.phase_code = 'O' then 'Phase 0'           
			WHEN sp.phase_code = 'I' then 'Phase 1'
			WHEN sp.phase_code = 'I_II' then 'Phase 1/Phase 2'
			WHEN sp.phase_code = 'II' then 'Phase 2'
			WHEN sp.phase_code = 'II_III' then 'Phase 2/Phase 3'
			WHEN sp.phase_code = 'III' then 'Phase 3'
			WHEN sp.phase_code = 'IV' then 'Phase 4'
			WHEN sp.phase_code = 'NA' then 'N/A'
        END as phase_code,   
		CASE
            WHEN sp.study_protocol_type = 'NonInterventionalStudyProtocol' then 'Observational'
			WHEN sp.study_protocol_type = 'InterventionalStudyProtocol' AND sp.expd_access_indidicator=true then 'Expanded Access'
            ELSE 'Interventional'
        END as study_type,
        CASE
            WHEN sp.allocation_code = 'RANDOMIZED_CONTROLLED_TRIAL' then 'Randomized'
            WHEN sp.allocation_code = 'NON_RANDOMIZED_TRIAL' then 'Non-randomized'
			WHEN sp.allocation_code = 'NA' then 'N/A'			
        END as allocation_code,
        CASE
            WHEN sp.blinding_schema_code = 'OPEN' then 'Open Label'
            WHEN sp.blinding_schema_code = 'SINGLE_BLIND' then 'Single Blind'
            WHEN sp.blinding_schema_code = 'DOUBLE_BLIND' then 'Double Blind'
        END as blinding_schema_code,
        CASE
            WHEN sp.design_configuration_code = 'SINGLE_GROUP' then 'Single Group Assignment'
            WHEN sp.design_configuration_code = 'PARALLEL' then 'Parallel Assignment'
            WHEN sp.design_configuration_code = 'CROSSOVER' then 'Crossover Assignment'
            WHEN sp.design_configuration_code = 'FACTORIAL' then 'Factorial Assignment'
        END as design_configuration_code,
		CASE
            WHEN sp.study_model_code = 'CASE_ONLY' then 'Case-Only'
			WHEN sp.study_model_code = 'COHORT' then 'Cohort'
			WHEN sp.study_model_code = 'CASE_CONTROL' then 'Case Control'
			WHEN sp.study_model_code = 'FAMILY_BASED' then 'Family-Based'
			WHEN sp.study_model_code = 'OTHER' then 'Other'
			WHEN sp.study_model_code = 'ECOLOGIC_OR_COMMUNITY_STUDIES' then 'Ecologic or Community'
			WHEN sp.study_model_code = 'CASE_CROSSOVER' then 'Case-Crossover'
        END as study_model_code,
		CASE
            WHEN sp.time_perspective_code = 'PROSPECTIVE' then 'Prospective'
			WHEN sp.time_perspective_code = 'RETROSPECTIVE' then 'Retrospective'
			WHEN sp.time_perspective_code = 'CROSS_SECTION' then 'Cross-Sectional'
			WHEN sp.time_perspective_code = 'OTHER' then 'Other'
        END as time_perspective_code,
        sp.number_of_intervention_groups,
		sp.number_of_groups,
        CASE
            WHEN sp.min_target_accrual_num is null then 0
            ELSE sp.min_target_accrual_num
        END as min_target_accrual_num,
        ov_off_crs.assigned_identifier as ovOffCrsId,
		pi.assigned_identifier as pi_po_id,
		pi.first_name as pi_first_name,
		pi.last_name as pi_last_name,
		pi.middle_name as pi_middle_name,

        central_contact_crs.assigned_identifier as centralContactCrsId,
		central_contact_person.assigned_identifier as cc_po_id,
		central_contact_person.first_name as cc_first_name,
		central_contact_person.last_name as cc_last_name,
		central_contact_person.middle_name as cc_middle_name,

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
			WHEN sp.study_classification_code = 'NA' then 'N/A'
        END as classification_code,
		CASE
            WHEN sp.bio_specimen_retention_code = 'SAMPLES_WITH_DNA' then 'Samples With DNA'
            WHEN sp.bio_specimen_retention_code = 'SAMPLES_WITHOUT_DNA' then 'Samples Without DNA'
            WHEN sp.bio_specimen_retention_code = 'NONE_RETAINED' then 'None Retained'
        END as biospec_retention,
		sp.bio_specimen_description as biospec_descr,
		sp.study_population_description as study_pop,
		CASE
            WHEN sp.sampling_method_code = 'NON_PROBABILITY_SAMPLE' then 'Non-Probability Sample'
            WHEN sp.sampling_method_code = 'PROBABILITY_SAMPLE' then 'Probability Sample'            
        END as sampling_method		
     from study_protocol sp
     inner join rv_trial_id_nct on rv_trial_id_nct.study_protocol_identifier = sp.identifier
     join rv_sponsor_organization sponsorSs on sponsorSs.study_protocol_identifier = sp.identifier 
     inner join rv_dwf_current dws on dws.study_protocol_identifier = sp.identifier
        and dws.status_code in ('ABSTRACTED','VERIFICATION_PENDING','ABSTRACTION_VERIFIED_NORESPONSE', 'ABSTRACTION_VERIFIED_RESPONSE')        
     inner join rv_trial_id_nci as nci_id on nci_id.study_protocol_id = sp.identifier       
     left outer join rv_ctep_id ctepSs on ctepSs.study_protocol_identifier = sp.identifier 
     left outer join rv_dcp_id dcpSs on dcpSs.study_protocol_identifier = sp.identifier 
	 left outer join rv_ccr_id ccr on ccr.study_protocol_identifier = sp.identifier
     left outer join rv_lead_organization leadOrgSs on leadOrgSs.study_protocol_identifier = sp.identifier
	 left outer join rv_trial_submitter submitter on submitter.study_protocol_identifier = sp.identifier
     left outer join study_resourcing as summary4 on summary4.study_protocol_identifier = sp.identifier and summary4.summ_4_rept_indicator is true
        and summary4.identifier = (select max(identifier) from study_resourcing where study_protocol_identifier = sp.identifier and summ_4_rept_indicator is true)
     left outer join organization as sum4Org on cast(summary4.organization_identifier as integer) = sum4Org.identifier     
     left outer join study_contact as respPartySc on respPartySc.study_protocol_identifier = sp.identifier and respPartySc.role_code = 'RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR'
     left outer join study_site_contact respPartySponsorContact on
        respPartySponsorContact.study_site_identifier in (select identifier from study_site where study_site.functional_code='RESPONSIBLE_PARTY_SPONSOR' and study_site.study_protocol_identifier=sp.identifier)
        and respPartySponsorContact.role_code = 'RESPONSIBLE_PARTY_SPONSOR_CONTACT'
     left outer join clinical_research_staff respPartyCrs on respPartyCrs.identifier = respPartySc.clinical_research_staff_identifier
     left outer join study_regulatory_authority sra on sra.study_protocol_identifier = sp.identifier
     left outer join regulatory_authority ra on ra.identifier = sra.regulatory_authority_identifier
     left outer join country ra_country on ra_country.identifier = ra.country_identifier
     join study_overall_status sos on sos.study_protocol_identifier = sp.identifier
         and (sos.identifier = (select max(identifier) from study_overall_status where study_protocol_identifier = sp.identifier))
     left outer join study_contact ov_off on ov_off.study_protocol_identifier = sp.identifier and ov_off.role_code = 'STUDY_PRINCIPAL_INVESTIGATOR'
     left outer join clinical_research_staff ov_off_crs on ov_off_crs.identifier = ov_off.clinical_research_staff_identifier
     left outer join person pi on pi.identifier = ov_off_crs.person_identifier
     left outer join study_contact central_contact on central_contact.study_protocol_identifier = sp.identifier and central_contact.role_code = 'CENTRAL_CONTACT'
     left outer join clinical_research_staff central_contact_crs on central_contact_crs.identifier = central_contact.clinical_research_staff_identifier
	 left outer join person central_contact_person on central_contact_person.identifier = central_contact_crs.person_identifier
     left outer join csm_user subm_csm on subm_csm.user_id = sp.user_last_created_id
     left outer join registry_user subm_ru on subm_ru.csm_user_id = subm_csm.user_id
     left outer join primary_purpose pp on sp.primary_purpose_code = pp.name        
     left outer join document_workflow_status as processing_status on processing_status.study_protocol_identifier = sp.identifier
        and processing_status.identifier = (select max(identifier) from document_workflow_status where study_protocol_identifier = sp.identifier)
     where sp.status_code = 'ACTIVE'  and rv_trial_id_nct.local_sp_indentifier is not null
"""

def nbOfTrials = 0

def outputDir = new File("${resolvedProperties['output.dir']}")
println "Output directory: ${outputDir.getCanonicalPath()}"

paConn.eachRow(getTrialsSQL) { spRow ->

	def trialFile = new File(outputDir, "${spRow.nctId.toUpperCase()}.xml")
	def out = new FileOutputStream(trialFile)
	def writer = new OutputStreamWriter( out , "UTF-8")
	writer.write """<?xml version="1.0" encoding="UTF-8"?>\n"""

	def xml = new MarkupBuilder(writer)
	xml.setDoubleQuotes(true)

	def studyProtocolID = spRow.identifier

	xml.clinical_study {
		xml.required_header {
			xml.download_date(new Date().toString())
			xml.link_text("Link to the current ClinicalTrials.gov record.")
			xml.url("http://clinicaltrials.gov/show/${spRow.nctId}")
		}
		xml.id_info {
			xml.org_study_id(spRow.leadOrgId)
			xml.secondary_id (spRow.nciId)
			paConn.eachRow(Queries.otherIdsSQL, [studyProtocolID]) { row ->
				xml.secondary_id (row.extension)
			}
			if (spRow.ctepId != null) {
				xml.secondary_id (spRow.ctepId)
			}
			if (spRow.dcpId != null) {
				xml.secondary_id (spRow.dcpId)
			}
			if (spRow.ccrId != null) {
				xml.secondary_id (spRow.ccrId)
			}
			xml.nct_id(spRow.nctId)
		}
		xml.brief_title(spRow.brief_title)
		if (spRow.acronym)
			xml.acronym(spRow.acronym)
		if (spRow.official_title)
			xml.official_title(spRow.official_title)

		xml.sponsors {
			xml.lead_sponsor {
				if(spRow.sponsorOrgName) {
					xml.agency(spRow.sponsorOrgName.trim())
					xml.agency_class("Other")
				}
			}
			paConn.eachRow(Queries.collabsSQL, [studyProtocolID]) { collabRow ->
				xml.collaborator {
					xml.agency(collabRow.name.trim())
					xml.agency_class("Other")
				}
			}
		} // end sponsors

		xml.source(spRow.source?:"Unknown")
		if (spRow.reg_authority_country_id)
			xml.oversight_info {
				xml.regulatory_authority(spRow.reg_authority)
				xml.has_dmc(spRow.dmc_indicator)
			}

		if (spRow.brief_summary)
			xml.brief_summary {
				xml.textblock(spRow.brief_summary)
			}
		if (spRow.detailed_description)
			xml.detailed_description {
				xml.textblock(spRow.detailed_description)
			}

		xml.overall_status(spRow.current_trial_status)
		if (spRow.why_stopped)
			xml.why_stopped(spRow.why_stopped)
		if (spRow.start_date)
			xml.start_date(type:spRow.start_date_type_code, spRow.start_date.format("MMMM yyyy"))
		if (spRow.completion_date)
			xml.completion_date(type:spRow.completion_date_type_code, spRow.completion_date.format("MMMM yyyy"))
		if (spRow.pri_compl_date)
			xml.primary_completion_date(type:spRow.pri_compl_date_type_code, spRow.pri_compl_date.format("MMMM yyyy"))
		xml.phase(spRow.phase_code)
		xml.study_type(spRow.study_type)
		xml.study_design(buildStudyDesign(spRow))
		paConn.eachRow(Queries.primOutcomesSQL, [studyProtocolID]) { row ->
			xml.primary_outcome {
				xml.measure(row.prim_som_name);
				xml.safety_issue(row.prim_som_safety_ind)
				if (row.prim_som_timeframe)
					xml.time_frame(row.prim_som_timeframe)
				if (row.description)
					xml.description(row.description)
			}
		}
		paConn.eachRow(Queries.secondOutcomesSQL, [studyProtocolID]) { row ->
			xml.secondary_outcome {
				xml.measure(row.prim_som_name);
				xml.safety_issue(row.prim_som_safety_ind)
				if (row.prim_som_timeframe)
					xml.time_frame(row.prim_som_timeframe)
				if (row.description)
					xml.description(row.description)
			}
		}
		paConn.eachRow(Queries.otherOutcomesSQL, [studyProtocolID]) { row ->
			xml.other_outcome {
				xml.measure(row.prim_som_name);
				xml.safety_issue(row.prim_som_safety_ind)
				if (row.prim_som_timeframe)
					xml.time_frame(row.prim_som_timeframe)
				if (row.description)
					xml.description(row.description)
			}
		}
		if (spRow.study_type=='Observational') {
			if (spRow.number_of_groups)
				xml.number_of_groups(spRow.number_of_groups)
		} else {
			if (spRow.number_of_intervention_groups)
				xml.number_of_arms(spRow.number_of_intervention_groups)
		}
		xml.enrollment(type:'Anticipated', spRow.min_target_accrual_num)
		paConn.eachRow(Queries.conditionsSQL, [studyProtocolID]) { row ->
			xml.condition(row.preferred_name)
		}
		// try to be cheap and steal a few cycles by running 1 query instead of 3.
		def allArmsAndInt = []
		paConn.eachRow(Queries.armsSQL, [studyProtocolID]) { row ->
			allArmsAndInt.add(row.toRowResult())
		}
		// get arms out of it.
		def armsList = []
		allArmsAndInt.each {
			def row = it
			if (!armsList.contains(row.arm_id)) {
				xml.arm_group {
					xml.arm_group_label(row.arm_name)
					if (row.arm_type)
						xml.arm_group_type(row.arm_type)
					if (row.arm_desc)
						xml.description(row.arm_desc)
				}
				armsList.add(row.arm_id)
			}
		}

		// interventions.
		def intsList =[]
		allArmsAndInt.each {
			def intRow = it
			if (!intsList.contains(intRow.int_id)) {
				xml.intervention  {
					xml.intervention_type(intRow.int_type)
					xml.intervention_name(intRow.int_name)
					if (intRow.int_desc) {
						xml.description (intRow.int_desc)
					}

					groupNamesList = []
					allArmsAndInt.each {
						def groupRow = it
						if (intRow.int_id == groupRow.int_id && !groupNamesList.contains(groupRow.arm_name)) {
							xml.arm_group_label(groupRow.arm_name)
							groupNamesList.add(groupRow.arm_name)
						}
					}

					// other names
					otherNamesList = []
					allArmsAndInt.each {
						def otherNameRow = it
						if (intRow.int_id == otherNameRow.int_id && !otherNamesList.contains(otherNameRow.alt_name)
						&& otherNameRow.alt_name != null && otherNameRow.alt_name.size() > 0) {
							xml.other_name(otherNameRow.alt_name)
							otherNamesList.add(otherNameRow.alt_name)
						}
					}

				}
				intsList.add(intRow.int_id)
			}
		}
		if (spRow.study_type=='Observational') {
			if (spRow.biospec_retention)
				xml.biospec_retention(spRow.biospec_retention)
			if (spRow.biospec_descr)
				xml.biospec_descr {
					xml.textblock(spRow.biospec_descr)
				}
		}

		// Eligibility
		def gender
		def minAge
		def maxAge
		def inCriteria = new StringBuilder()
		def exCriteria = new StringBuilder()
		def genCriteria = new StringBuilder()

		paConn.eachRow(Queries.eligsSQL, [studyProtocolID]) { row ->
			if (row.gender) {
				gender = row.gender
			} else if (row.criterion_name == 'AGE') {
				minAge = row.min_age
				maxAge = row.max_age
			} else if (row.elig_criteria_text) {
				switch (row.elig_type) {
					case 'Inclusion Criteria':
						inCriteria << buildEligibilityCriterionDescription(row.elig_criteria_text)
						break
					case 'Exclusion Criteria':
						exCriteria << buildEligibilityCriterionDescription(row.elig_criteria_text)
						break
					default:
						genCriteria << buildEligibilityCriterionDescription(row.elig_criteria_text)
				}
			}
		}
		// To satisfy the XSD, we must include eligibility element only if all three are provided.
		if (gender && minAge && maxAge) {
			xml.eligibility {
				if (spRow.study_type=='Observational') {
					if (spRow.study_pop)
						xml.study_pop {
							xml.textblock(spRow.study_pop)
						}
					if (spRow.sampling_method)
						xml.sampling_method(spRow.sampling_method)
				}
				if (inCriteria || exCriteria || genCriteria) {
					def criteria = new StringBuilder("\n")
					if (genCriteria)
						criteria << "Criteria: \n\n" << genCriteria << "\n"
					if (inCriteria)
						criteria << "Inclusion Criteria: \n\n" << inCriteria << "\n"
					if (exCriteria)
						criteria << "Exclusion Criteria: \n\n" << exCriteria << "\n"
					xml.criteria { xml.textblock(criteria) }
				}
				xml.gender(gender)
				xml.minimum_age(minAge)
				xml.maximum_age(maxAge)
				xml.healthy_volunteers(spRow.healthy_volunteer_indicator)
			}
		}

		if(spRow.pi_po_id) {
			xml.overall_official {
				xml.first_name(spRow.pi_first_name)
				if (spRow.pi_middle_name)
					xml.middle_name(spRow.pi_middle_name)
				xml.last_name(spRow.pi_last_name)
				xml.role("Principal Investigator")
				xml.affiliation(spRow.leadOrgName?.trim())
			}
		}

		if (spRow.cc_po_id) {
			xml.overall_contact {
				xml.first_name(spRow.cc_first_name)
				if (spRow.cc_middle_name)
					xml.middle_name(spRow.cc_middle_name)
				xml.last_name(spRow.cc_last_name)
				if (spRow.centralContactPhone)
					xml.phone(spRow.centralContactPhone)
				if (spRow.centralContactEmail)
					xml.email(spRow.centralContactEmail)
			}
		}

		// Participating sites.
		paConn.eachRow(Queries.partSitesSQL, [studyProtocolID]) { row ->
			xml.location {
				xml.facility {
					xml.name(row.name?.trim())
					def orgRow = orgsMap.get(row.org_poid.toLong())
					address(xml, orgRow)
				}
				
				xml.status(row.status)
				
				paConn.eachRow(Queries.primaryContactSQL, [
					row.ss_identifier,
					studyProtocolID
				]) { primconrow ->
					if (primconrow.prim_crs_id != null && crsMap.get(primconrow.prim_crs_id.toLong()) != null) {
						xml.contact {
							def crsRow = crsMap.get(primconrow.prim_crs_id.toLong())
							crsDetail(xml, crsRow)
							addressAndPhoneDetail(xml, crsRow, primconrow, true)
						}
					}
				}

				paConn.eachRow(Queries.investigatorsSQL, [
					row.ss_identifier,
					studyProtocolID
				]) { invsrow ->
					if (invsrow.inv_crs_id != null && crsMap.get(invsrow.inv_crs_id.toLong()) != null) {
						xml.investigator {
							def crsRow = crsMap.get(invsrow.inv_crs_id.toLong())
							crsDetail(xml, crsRow)
							addressAndPhoneDetail(xml, crsRow, null, false)
							xml.role("Principal Investigator")
						}
					}
				}
			}
		}  // end part sites


		////////////////////////////////////////////////////////////////
		xml.resp_party {
			xml.resp_party_person {
				if (spRow.respPartyCrsId != null) {
					def crsRow = crsMap.get(spRow.respPartyCrsId.toLong())
					crsDetail(xml, crsRow)
					addressAndPhoneDetail(xml, crsRow, spRow, true)
				}
			}
			xml.resp_party_organization {
				if (spRow.sponsorRoId != null && spRow.respPartySponsorIdentifier!=null) {
					def roRow = rosMap.get(spRow.sponsorRoId.toLong())
					xml.name(roRow.orgname)
					xml.po_id(roRow.org_poid)
					xml.ctep_id(roRow.ctep_id)
					def sponsorContactInfo = ['prim_phone':spRow.respPartySponsorPhone,'prim_email':spRow.respPartySponsorEmail]
					addressAndPhoneDetail(xml, roRow,
							sponsorContactInfo, true)
				}
			}
		}

		paConn.eachRow(Queries.ownersSQL, [studyProtocolID]) { row ->
			xml.trial_owners {
				xml.name(row.ownerName)
			}
		}

		xml.lead_org {
			if(spRow.leadRoId != null){
				def roRow = rosMap.get(spRow.leadRoId.toLong())
				xml.name(roRow.orgname)
				xml.po_id(roRow.org_poid)
				xml.ctep_id(roRow.ctep_id)
				addressAndPhoneDetail(xml, roRow, null,false)
			}
		}

		xml.nci_specific_information {
			xml.reporting_data_set_method(spRow.accr_rept_meth_code)
			xml.summary_4_funding_category(spRow.summary4_type_code)
			xml.summary_4_funding_sponsor_source {
				if (spRow.sum4OrgId != null) {
					def sum4Org = orgsMap.get(spRow.sum4OrgId.toLong())
					xml.name(sum4Org.name)
					xml.po_id(sum4Org.org_poid)
					addressAndPhoneDetail(xml, sum4Org, null, false)
				}
			}
		}

		xml.is_fda_regulated(spRow.fda_indicator)
		xml.is_section_801(spRow.section801_indicator)
		xml.delayed_posting(spRow.delayed_posting_indicator)











		xml.trial_status {
			xml.current_trial_status(spRow.current_trial_status)
			xml.current_trial_status_date(spRow.current_trial_status_date)
			xml.current_trial_start_date(spRow.start_date)
			xml.current_trial_start_date_type(spRow.start_date_type_code)
			xml.current_trial_completion_date(spRow.pri_compl_date)
			xml.current_trial_completion_date_type(spRow.pri_compl_date_type_code)
		}

		xml.trial_funding {
			paConn.eachRow(Queries.fundingsSQL, [studyProtocolID]) { fundRow ->
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

			}
		}














		xml.verification_date(spRow.verification_date)
	}
	writer.flush();
	writer.close();
	nbOfTrials++

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

void address(MarkupBuilder xml, Object row) {
	xml.address {
		xml.city(row.cityormunicipality?.trim())
		if (row.stateorprovince)
			xml.state(row.stateorprovince?.trim())
		if (row.postalcode)
			xml.zip(row.postalcode?.trim())
		xml.country(row.country_name?.trim())
	}
}

void addressAndPhoneDetail(MarkupBuilder xml, Object row, Object spRow, boolean suppressPhoneAndEmail) {

	xml.address {

		if (StringUtils.equalsIgnoreCase(row.stateorprovince, "UM")) {
			xml.street("")
			xml.city("")
			xml.state("")
			xml.zip("")
			xml.country("")
		} else {
			if (!StringUtils.equalsIgnoreCase(row.streetaddressline, "unknown")) {
				xml.street(row.streetaddressline)
			} else {
				xml.street("");
			}
			if(!StringUtils.equalsIgnoreCase(row.cityormunicipality, "unknown")) {
				xml.city(row.cityormunicipality)
			}else{
				xml.city("");
			}

			xml.state(row.stateorprovince)

			if(StringUtils.equals(row.postalcode, "96960")) {
				xml.zip("")
			}else{
				xml.zip(row.postalcode)
			}

			xml.country(row.country_name)
		}
	}

	//suppressPhoneAndEmail=true implies, use phone, fax & email values from PA only. DO NOT use phone, fax and email values from PO.
	//suppressPhoneAndEmail=false implies, use phone, fax and email values from PA, if not available in PA then use the ones available in PO.

	xml.phone((spRow!=null && spRow.prim_phone!=null?spRow.prim_phone:(suppressPhoneAndEmail?"":row.phone)))

	if (row.faxnumber != null && !suppressPhoneAndEmail) {
		xml.fax(row.faxnumber)
	}

	xml.email(( spRow!=null &&
			spRow.prim_email!=null &&
			!StringUtils.containsIgnoreCase(spRow.prim_email, "unknown") &&
			!Constants.SUPRESS_EMAIL_IDS.contains(spRow.prim_email) ? spRow.prim_email :
			(StringUtils.containsIgnoreCase(row.email, "unknown") ||
			Constants.SUPRESS_EMAIL_IDS.contains(row.email) ||
			suppressPhoneAndEmail ? "" : row.email)))
}

String buildEligibilityCriterionDescription(criterion) {
	return "  - " <<  applyPrsFormattingFixes(criterion) << '\n'
}

/**
 * Apply some formatting fixes to achieve a better display in PRS.
 * @param text
 * @return
 */
String applyPrsFormattingFixes(String text) {
	text = text.replaceAll("(?m)^ \\*", "  *");
	text = text.replaceAll("(?m)^\\*", "  *");
	text = text.replaceAll("(?m)^ \\-\\s", "  * ");
	text = text.replaceAll("(?m)^\\-\\s", "  * ");
	return text;
}


/**
 * Builds study design string that matches the format ClinicalTrials.gov uses.
 * @param spRow
 * @return
 */
String buildStudyDesign(spRow)	{
	def design = new StringBuilder()
	if (spRow.study_type=='Observational') {
		if (spRow.study_model_code)
			design << (design.size()>0?', ':'') << "Observational Model:  ${spRow.study_model_code}"
		if (spRow.time_perspective_code)
			design << (design.size()>0?', ':'') << "Time Perspective:  ${spRow.time_perspective_code}"
	} else {
		if (spRow.allocation_code)
			design << (design.size()>0?', ':'') << "Allocation:  ${spRow.allocation_code}"
		if (spRow.classification_code)
			design << (design.size()>0?', ':'') << "Endpoint Classification:  ${spRow.classification_code}"
		if (spRow.design_configuration_code)
			design << (design.size()>0?', ':'') << "Intervention Model:  ${spRow.design_configuration_code}"
		if (spRow.blinding_schema_code) {
			design << (design.size()>0?', ':'') << "Masking:  ${spRow.blinding_schema_code}"
			if (spRow.blinding_role_code_caregiver || spRow.blinding_role_code_investigator || spRow.blinding_role_code_subject || spRow.blinding_role_code_outcome) {
				design << ' ('
				def maskingRoles = new StringBuilder()
				if (spRow.blinding_role_code_subject)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Subject"
				if (spRow.blinding_role_code_caregiver)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Caregiver"
				if (spRow.blinding_role_code_investigator)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Investigator"
				if (spRow.blinding_role_code_outcome)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Outcomes Assessor"
				design << maskingRoles << ')'
			}
		}

	}

	if (spRow.primary_purpose_code)
		design << (design.size()>0?', ':'') << "Primary Purpose:  ${spRow.primary_purpose_code}"

	return design.size()>0?design:'N/A'
}
println ""
println "************PDQ EXPORT SUMMARY******************"
println ""
println "Exported $nbOfTrials trials"
println ""
println "************************************************"
println ""