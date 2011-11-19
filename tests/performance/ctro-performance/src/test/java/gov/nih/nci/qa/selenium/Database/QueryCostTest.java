package gov.nih.nci.qa.selenium.Database;

import gov.nih.nci.qa.hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

public class QueryCostTest {
	private static final String FILENAME = "config.properties";
	private static final String NCI_II_ROOT = "2.16.840.1.113883.3.26.4.3";
	private static final Long SP_ID = (long) 74623374;
	private static final String LEAD_ORG_ROLE = "Lead Organization";
	private static final String STUDY_CONTACT_ROLE_CODE = "Study Principal Investigator";

	@Test
	public void getQueryCosts() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = generateReportingSql();

		Query query = session.createSQLQuery(queryString);
		query.setLong("spId", SP_ID);
		query.setString("leadOrgRole", LEAD_ORG_ROLE);
		query.setString("piRole", STUDY_CONTACT_ROLE_CODE);
		query.setString("NCI_II_ROOT", NCI_II_ROOT);
		System.out.println("query [" + query.getQueryString() + "].");

		List results = query.list();

		System.out.println("results =" + results.size());
		HibernateUtil.getSessionFactory().close();
	}

	private String getStudyProtocolSql() {
		return "select identifier from study_protocol;";
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

}
