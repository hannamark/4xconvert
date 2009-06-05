package gov.nih.nci.pa.report.service;

import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
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
public abstract class AbstractReportBean<CRITERIA, RESULT> implements ViewerReport<CRITERIA, RESULT> {

    /** Static spring to suppress conversion warnings. */
    protected static final String UNCHECKED = "unchecked";

    /** Logger. */
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    protected final Logger logger;

    /** Hibernate session. */
    protected Session session;

    /** Default constructor. */
    @SuppressWarnings(UNCHECKED)
    public AbstractReportBean() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<RESULT> resultType = (Class) parameterizedType.getActualTypeArguments()[1];
        logger = Logger.getLogger(resultType);
    }

    /**
     * {@inheritDoc}
     */
    public List<RESULT> get(CRITERIA criteria) throws PAException {
        if (criteria == null) {
            throw new PAException("Report criteria dto must not be null.");
        }
        return null;
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
}
