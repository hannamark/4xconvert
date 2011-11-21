package gov.nih.nci.qa.selenium.Database;

import gov.nih.nci.qa.hibernate.util.HibernateUtil;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Get the costs for particular queries that were in the viewer project. In
 * other words there is hard coded sql that could be optimized further. This
 * class executes some of them and outputs the "explain analyze sql" from
 * postgreSQL to assist in this analysis.
 * 
 * @author kgann
 * 
 */
public class QueryCostTest {
	private static final String NCI_II_ROOT = "2.16.840.1.113883.3.26.4.3";
	private static final String STUDY_PROTOCOL_ROOT = "2.16.840.1.113883.3.26.4.3";
	private static final String LEAD_ORG_ROLE = "Lead Organization";
	private static final String STUDY_CONTACT_ROLE_CODE = "Study Principal Investigator";

	private static Session session;
	private static ArrayList<BigInteger> studyProtocolIdentifiers;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void getCurrentSession() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String identifierQueryString = getStudyProtocolIdentifierSql();
		Query identifierQuery = session.createSQLQuery(identifierQueryString);
		studyProtocolIdentifiers = (ArrayList<BigInteger>) identifierQuery
				.list();
	}

	@AfterClass
	public static void close() {
		HibernateUtil.getSessionFactory().close();
	}

	@Test
	public void generateReportingSqlCost() {
		for (BigInteger identifier : studyProtocolIdentifiers) {
			String sql = generateReportingSql();
			Query query = session.createSQLQuery(sql);
			query.setBigInteger("spId", identifier);
			query.setString("leadOrgRole", LEAD_ORG_ROLE);
			query.setString("piRole", STUDY_CONTACT_ROLE_CODE);
			query.setString("NCI_II_ROOT", NCI_II_ROOT);
			ArrayList<String> results = (ArrayList<String>) query.list();

			// TODO Output this into a CSV with other results.
			System.out.println(results.get(0));
		}
	}

	@Test
	public void getLeadOrganizationCost() {
		for (BigInteger identifier : studyProtocolIdentifiers) {
			String sql = getLeadOrganization();
			Query query = session.createSQLQuery(sql);
			query.setBigInteger("SP_ID", identifier);
			query.setParameter("LEAD_ORGANIZATION", LEAD_ORG_ROLE);
			ArrayList<String> results = (ArrayList<String>) query.list();

			// TODO Output this into a CSV with other results.
			printResults(results);
		}
	}

	@Test
	public void getSummary4Cost() {
		// These are java Timestamps which are converted from ISO TS types.
		Timestamp endDate = makeTimestamp(2011, 11, 21);
		Timestamp startDate = makeTimestamp(2000, 1, 1);

		String sql = generateSummary4Sql();
		Query query = session.createSQLQuery(sql);
		query.setTimestamp("LOW", endDate);
		query.setTimestamp("HIGH", startDate);
		query.setParameter(
				"ORG_NAME0",
				"Fred Hutchinson Cancer Research Center/University of Washington Cancer Consortium");
		// query.setParameter("ORG_NAMEOR", LEAD_ORG_ROLE);
		query.setString("NCI_II_ROOT", NCI_II_ROOT);

		ArrayList<String> results = (ArrayList<String>) query.list();

		// TODO Output this into a CSV with other results.
		printResults(results);
	}

	/**
	 * Just get one identifier. The cost should be the same.
	 * 
	 * @return
	 */
	private static String getStudyProtocolIdentifierSql() {
		return "SELECT study_protocol_identifier FROM study_overall_status ORDER BY study_protocol_identifier LIMIT 1;";
	}

	/**
	 * Generate reporting sql.
	 * 
	 * @return sql.
	 */
	protected String generateReportingSql() {
		return "explain analyze select crs_p.last_name, crs_p.first_name, crs_p.identifier, ro_org.name, ro_org.identifier, "
				+ "ss.LOCAL_SP_INDENTIFIER, sr.type_code, sos.sosSc, sos.sosSd, dws.dwsSc, "
				+ "dws.dwsSd, si.siId, si.siCm, si.siOd, si.siCd, "
				+ "sp.OFFICIAL_TITLE, sp.PHASE_CODE, sp.PRIMARY_PURPOSE_CODE, sp.PROPRIETARY_TRIAL_INDICATOR, "
				+ "sp.RECORD_VERIFICATION_DATE, sp.CTGOV_XML_REQUIRED_INDICATOR, sp.PHASE_ADDITIONAL_QUALIFIER_CODE, "
				+ "sp.DATE_LAST_CREATED, sp.AMENDMENT_NUMBER, sp.AMENDMENT_DATE, sp.SUBMISSION_NUMBER, "
				+ "sp.STUDY_PROTOCOL_TYPE, sOi.extension "
				+ "from study_protocol AS sp left join study_site AS ss ON sp.identifier = ss.study_protocol_identifier "
				+ "left JOIN study_otheridentifiers sOi ON sp.identifier = sOi.study_protocol_id "
				+ "AND sOi.root = :NCI_II_ROOT "
				+ "left JOIN research_organization AS ro ON ss.research_organization_identifier = ro.identifier "
				+ "left JOIN organization AS ro_org ON ro.organization_identifier = ro_org.identifier "
				+ "left JOIN study_contact AS sc ON sc.study_protocol_identifier = ss.study_protocol_identifier "
				+ "and sc.role_code = :piRole "
				+ "left JOIN clinical_research_staff AS crs ON sc.clinical_research_staff_identifier = crs.identifier "
				+ "left JOIN person AS crs_p ON crs.person_identifier = crs_p.identifier "
				+ "left JOIN study_resourcing AS sr ON sr.study_protocol_identifier = ss.study_protocol_identifier and "
				+ "sr.SUMM_4_REPT_INDICATOR = true "
				+ "left join (select status_code as sosSc, status_date as sosSd, study_protocol_identifier as sosSpi from "
				+ "study_overall_status where study_protocol_identifier = :spId order by identifier desc limit 1) AS sos "
				+ "ON sos.sosSpi = ss.study_protocol_identifier "
				+ "left join (select status_code as dwsSc, status_date_range_low as dwsSd, study_protocol_identifier as dwsSpi "
				+ "from document_workflow_status where study_protocol_identifier = :spId order by identifier desc limit 1) "
				+ "AS dws ON dws.dwsSpi = ss.study_protocol_identifier "
				+ "left join (select identifier as siId, comments as siCm, open_date as siOd, close_date as siCd, "
				+ "study_protocol_identifier as siSpi from study_inbox "
				+ "where study_protocol_identifier = :spId order by identifier desc limit 1) AS si "
				+ "ON si.siSpi = ss.study_protocol_identifier "
				+ "where sp.identifier = :spId and ss.functional_code = :leadOrgRole";
	}

	/**
	 * @param studyProtocolIdentifier
	 *            the studyProtocol primary key
	 * @return string representation of lead organization
	 * @throws PAException
	 *             exception
	 */
	protected String getLeadOrganization() {
		StringBuffer sql = new StringBuffer(
				"explain analyze select org.name, spart.local_sp_indentifier "
						+ "from study_site AS spart "
						+ "  join research_organization AS ro ON (spart.research_organization_identifier = ro.identifier) "
						+ "  join organization AS org ON (ro.organization_identifier = org.identifier) "
						+ "where study_protocol_identifier = :SP_ID "
						+ "  and spart.functional_code = :LEAD_ORGANIZATION ");
		return sql.toString();
	}

	protected String generateSummary4Sql() {
		StringBuffer sql = new StringBuffer();
		sql.append("explain analyze SELECT DISTINCT t.sponsor, t.proto_id, t.pi, t.program_code, t.open_date,");
		sql.append(" (SELECT MAX(closed_date) FROM");
		sql.append(" (SELECT CASE WHEN ct.proprietary_trial_indicator THEN ct.accrual_date_range_high");
		sql.append(" ELSE ct.status_date END as closed_date FROM");
		sql.append(" (SELECT isp.proprietary_trial_indicator, iss.accrual_date_range_high, ssos.status_date");
		sql.append(" FROM study_protocol isp ");
		sql.append(" INNER JOIN study_site iss ON isp.identifier = iss.study_protocol_identifier");
		sql.append(" LEFT JOIN study_site_overall_status ssos ON ssos.study_site_identifier = iss.identifier ");
		sql.append(" AND ssos.status_code = 'CLOSED_TO_ACCRUAL'");
		sql.append(" WHERE isp.identifier = t.trial_id) AS ct) AS tct), ");
		sql.append("t.phase, t.type, t.official_title, t.target, ");
		sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
		sql.append(" INNER JOIN study_site AS ss1 ON sb.study_site_identifier = ss1.identifier");
		sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
		sql.append(" INNER JOIN healthcare_facility AS hf");
		sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
		sql.append(" INNER JOIN study_site AS ss2");
		sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
		sql.append(" INNER JOIN research_organization AS ro");
		sql.append(" ON ss2.research_organization_identifier = ro.identifier");
		sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
		sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
		sql.append(" AND hf.organization_identifier =  ro.organization_identifier");
		sql.append(" AND pa.registration_date >= :LOW");
		sql.append(" AND pa.registration_date < :HIGH ) as accrual_center_leadorg_12m,");
		sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
		sql.append(" INNER JOIN study_site as ss1 ON sb.study_site_identifier = ss1.identifier");
		sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
		sql.append(" INNER JOIN healthcare_facility AS hf");
		sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
		sql.append(" INNER JOIN study_site as ss2");
		sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
		sql.append(" INNER JOIN research_organization AS ro");
		sql.append(" ON ss2.research_organization_identifier = ro.identifier");
		sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
		sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
		sql.append(" AND hf.organization_identifier !=  ro.organization_identifier");
		sql.append(" AND pa.registration_date >= :LOW");
		sql.append(" AND pa.registration_date < :HIGH ) as accrual_center_treatorg_12m,");
		sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
		sql.append(" INNER JOIN study_site as ss1 ON sb.study_site_identifier = ss1.identifier");
		sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
		sql.append(" INNER JOIN healthcare_facility as hf");
		sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
		sql.append(" INNER JOIN study_site as ss2");
		sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
		sql.append(" INNER JOIN research_organization as ro");
		sql.append(" ON ss2.research_organization_identifier = ro.identifier");
		sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
		sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
		sql.append(" AND hf.organization_identifier =  ro.organization_identifier");
		sql.append(" AND pa.registration_date <= NOW()) AS accrual_center_leadorg_todate,");
		sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
		sql.append(" INNER JOIN study_site as ss1 ON sb.study_site_identifier = ss1.identifier");
		sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
		sql.append(" INNER JOIN healthcare_facility as hf");
		sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
		sql.append(" INNER JOIN study_site as ss2");
		sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
		sql.append(" INNER JOIN research_organization as ro");
		sql.append(" ON ss2.research_organization_identifier = ro.identifier");
		sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
		sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
		sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
		sql.append(" AND hf.organization_identifier !=  ro.organization_identifier");
		sql.append(" AND pa.registration_date <= NOW()) AS accrual_center_treatorg_todate,");
		sql.append("(SELECT DISTINCT type_code FROM study_resourcing");
		sql.append(" WHERE study_protocol_identifier = trial_id ");
		sql.append(" AND summ_4_rept_indicator = TRUE) AS sort_sub_category,");
		sql.append(" t.trial_id, t.nciidentifier,");
		sql.append("(SELECT o.name FROM study_site ss, research_organization ro, organization o");
		sql.append(" WHERE ss.research_organization_identifier = ro.identifier");
		sql.append(" AND ro.organization_identifier = o.identifier");
		sql.append(" AND ss.functional_code = 'LEAD_ORGANIZATION'");
		sql.append(" AND ss.study_protocol_identifier = t.trial_id ) AS lead_org_name,");
		sql.append("(SELECT ss.local_sp_indentifier");
		sql.append(" FROM study_site ss, research_organization ro, organization o");
		sql.append(" WHERE ss.research_organization_identifier = ro.identifier");
		sql.append(" AND ro.organization_identifier = o.identifier");
		sql.append(" AND ss.functional_code = 'IDENTIFIER_ASSIGNER'");
		sql.append(" AND ss.study_protocol_identifier = t.trial_id");
		sql.append(" AND o.name = 'ClinicalTrials.gov' ) AS nct_identifier,");
		sql.append("(SELECT ss.local_sp_indentifier");
		sql.append(" FROM study_site ss, research_organization ro, organization o");
		sql.append(" WHERE ss.research_organization_identifier = ro.identifier");
		sql.append(" AND ro.organization_identifier = o.identifier");
		sql.append(" AND ss.functional_code = 'IDENTIFIER_ASSIGNER'");
		sql.append(" AND ss.study_protocol_identifier = t.trial_id");
		sql.append(" AND o.name = 'Cancer Therapy Evaluation Program' ) AS ctep_identifier, ");
		sql.append("(SELECT a.atomic_site FROM (SELECT s.study_protocol_identifier, ");
		sql.append(" array_to_string(array_agg(a.display_name),', ') as atomic_site ");
		sql.append(" FROM study_anatomic_site s ");
		sql.append(" INNER JOIN anatomic_sites As a ON s.anatomic_sites_identifier = a.identifier ");
		sql.append(" WHERE s.study_protocol_identifier = t.trial_id ");
		sql.append(" GROUP BY s.study_protocol_identifier) AS a) AS atomic_sites ");
		sql.append("FROM");
		sql.append(" (SELECT DISTINCT soi.extension AS nciidentifier, so.name AS sponsor,");
		sql.append(" sp.identifier AS trial_id, ss.identifier AS study_site_id,");
		sql.append(" ss.local_sp_indentifier AS proto_id, ss.functional_code AS func_code,");
		sql.append(" p.last_name||', '||p.first_name AS pi,");
		sql.append(" sp.program_code_text AS program_code, sp.phase_code AS phase,");
		sql.append(" (CASE WHEN sp.proprietary_trial_indicator THEN (SELECT MIN(accrual_date_range_low)");
		sql.append("  FROM study_site WHERE study_protocol_identifier = sp.identifier)");
		sql.append("  ELSE sp.start_date END) AS open_date,");
		sql.append(" sp.primary_purpose_code AS type, sp.official_title AS official_title,");
		sql.append(" sp.min_target_accrual_num AS target FROM study_protocol sp");
		sql.append(" INNER JOIN study_milestone AS sm ON sp.identifier = sm.study_protocol_identifier");
		sql.append(" INNER JOIN study_site AS ss ON sp.identifier = ss.study_protocol_identifier");
		sql.append(" INNER JOIN study_otheridentifiers soi ON sp.identifier = soi.study_protocol_id");
		sql.append(" AND soi.root = :NCI_II_ROOT");
		sql.append(" INNER JOIN study_resourcing AS sr ON sp.identifier = sr.study_protocol_identifier");
		sql.append(" LEFT JOIN organization AS so");
		sql.append(" ON CAST(sr.organization_identifier AS INTEGER) = so.identifier");
		sql.append(" LEFT JOIN research_organization AS ro");
		sql.append(" ON ss.research_organization_identifier = ro.identifier");
		sql.append(" LEFT JOIN organization AS roo ON ro.organization_identifier = roo.identifier");
		sql.append(" LEFT JOIN healthcare_facility AS hf ON ss.healthcare_facility_identifier = hf.identifier");
		sql.append(" LEFT JOIN organization AS hfo ON hf.organization_identifier = hfo.identifier");
		sql.append(" LEFT JOIN study_contact AS sc ON sc.study_protocol_identifier = sp.identifier");
		sql.append(" LEFT JOIN healthcare_provider AS hp ON sc.healthcare_provider_identifier = hp.identifier");
		sql.append(" LEFT JOIN person AS p ON hp.person_identifier = p.identifier");
		sql.append(" WHERE sm.milestone_code = 'SUBMISSION_ACCEPTED'");
		sql.append(" AND ss.functional_code in ('LEAD_ORGANIZATION','TREATING_SITE')");
		sql.append(" AND sp.status_code = 'ACTIVE'");
		sql.append(generateOrgClause());
		sql.append("WHERE CHAR_LENGTH(TRIM(BOTH ' ' FROM proto_id)) > 0 ");
		sql.append("AND CHAR_LENGTH(TRIM(BOTH ' ' FROM sponsor)) > 0 ");
		sql.append("AND CHAR_LENGTH(TRIM(BOTH ' ' FROM pi)) > 0 ");
		sql.append("ORDER BY sort_sub_category, nciidentifier, pi ");

		return sql.toString();
	}

	private String generateOrgClause() {
		// int size = criteria.getOrgNames().size();
		int size = 1;
		StringBuffer sb = new StringBuffer("AND (");
		for (int i = 0; i < size; i++) {
			final String paramName = ":ORG_NAME" + i;
			sb.append("(roo.name = ");
			sb.append(paramName);
			sb.append(" OR hfo.name = ");
			sb.append(paramName);
			sb.append(')');
			if (i != size - 1) {
				sb.append(" OR ");
			}
		}
		sb.append(")) t ");

		return sb.toString();
	}

	/**
	 * @param year
	 *            year
	 * @param month
	 *            month
	 * @param day
	 *            day
	 * @return Timestamp
	 */
	public static Timestamp makeTimestamp(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	private void printResults(List<String> results) {
		for (String result : results) {
			System.out.println(result);
		}
	}

}
