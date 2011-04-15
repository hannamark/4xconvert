package gov.nih.nci.pa.report.service;

import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.AbstractCriteriaDto;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * Abstract class used for all report ejb's.
 *
 * @author Hugh Reinhart
 * @since 06/04/2009
 *
 * @param <CRITERIA> criteria dto
 * @param <RESULT> result dto
 */
public abstract class AbstractReportBean<CRITERIA extends AbstractCriteriaDto, RESULT> implements
        ViewerReport<CRITERIA, RESULT> {

    /** Static spring to suppress conversion warnings. */
    protected static final String UNCHECKED = "unchecked";

    private static final Logger LOG = Logger.getLogger(AbstractReportBean.class);

    /**
     * Default constructor.
     */
    public AbstractReportBean() {
        // nothing to do here
    }

    /**
     * @param query hibernate query
     * @param parameter parameter name
     * @param value value
     */
    protected static void setStParameter(SQLQuery query, String parameter, St value) {
        String sValue = StConverter.convertToString(value);
        if (sValue == null) {
            sValue = "";
        }
        query.setParameter(parameter, sValue);
    }

    /** Store data on lead organization. */
    protected class LeadOrgInfo {
        private String name;
        private String localSpIdentifier;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the localSpIdentifier
         */
        public String getLocalSpIdentifier() {
            return localSpIdentifier;
        }

        /**
         * @param localSpIdentifier the localSpIdentifier to set
         */
        public void setLocalSpIdentifier(String localSpIdentifier) {
            this.localSpIdentifier = localSpIdentifier;
        }
    };

    /**
     * @param studyProtocolIdentifier the studyProtocol primary key
     * @return string representation of lead organization
     * @throws PAException exception
     */
    protected LeadOrgInfo getLeadOrganization(BigInteger studyProtocolIdentifier) throws PAException {
        if (studyProtocolIdentifier == null) {
            return null;
        }
        LeadOrgInfo result = new LeadOrgInfo();
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer("select org.name, spart.local_sp_indentifier "
                    + "from study_site AS spart "
                    + "  join research_organization AS ro ON (spart.research_organization_identifier = ro.identifier) "
                    + "  join organization AS org ON (ro.organization_identifier = org.identifier) "
                    + "where study_protocol_identifier = :SP_ID "
                    + "  and spart.functional_code = :LEAD_ORGANIZATION ");
            query = session.createSQLQuery(sql.toString());
            query.setParameter("SP_ID", studyProtocolIdentifier);
            query.setParameter("LEAD_ORGANIZATION", StudySiteFunctionalCode.LEAD_ORGANIZATION.getName());
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            if (queryList.size() >= 1) {
                result.name = (String) queryList.get(0)[0];
                result.localSpIdentifier = (String) queryList.get(0)[1];
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        LOG.debug("Leaving getLeadOrganization(Long).");
        return result;
    }
}
