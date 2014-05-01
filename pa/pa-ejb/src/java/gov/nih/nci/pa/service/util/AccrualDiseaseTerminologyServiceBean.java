package gov.nih.nci.pa.service.util;

import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.annotation.security.SecurityDomain;

/**
 * @author Hugh Reinhart
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain("pa")
@RolesAllowed({SUBMITTER_ROLE })
public class AccrualDiseaseTerminologyServiceBean implements
        AccrualDiseaseTerminologyServiceRemote {

    private static final Logger LOG = Logger.getLogger(AccrualDiseaseTerminologyServiceBean.class);

    /** The default disease terminology. */
    public static final String DEFAULT_CODE_SYSTEM = "SDC";

    @Override
    public List<String> getValidCodeSystems() {
        LinkedList<String> result = new LinkedList<String>();
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            String hql = "select distinct d.codeSystem from AccrualDisease d order by d.codeSystem";
            Query query = session.createQuery(hql);
            @SuppressWarnings("unchecked")
            List<String> qList = query.list();
            for (String code : qList) {
                if (DEFAULT_CODE_SYSTEM.equals(code)) {
                    result.addFirst(code);
                } else {
                    result.add(code);
                }
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getCodeSystem(Long studyProtocolId) {
        if (studyProtocolId == null) {
            return null;
        }
        String qryStr = "SELECT accrualDiseaseCodeSystem FROM " + StudyProtocol.class.getSimpleName()
                + " WHERE id = :id";
        Query qry = PaHibernateUtil.getCurrentSession().createQuery(qryStr);
        qry.setParameter("id", studyProtocolId);
        List<String> rList = qry.list();
        return rList.isEmpty() ? null : rList.get(0);
    }

    @Override
    public void updateCodeSystem(Long studyProtocolId, String codeSystem)
            throws PAException {
        if (!canChangeCodeSystem(studyProtocolId)) {
            throw new PAException("The disease code system for this trial cannot be changed.");
        }
        List<String> validCodes = getValidCodeSystems();
        if (!validCodes.contains(codeSystem)) {
            throw new PAException("Invalid disease code system: " + codeSystem);
        }
        String qryStr = "UPDATE " + StudyProtocol.class.getSimpleName()
                + " SET accrualDiseaseCodeSystem = :newCodeSystem"
                + " WHERE id = :id";
        Query qry = PaHibernateUtil.getCurrentSession().createQuery(qryStr);
        qry.setParameter("newCodeSystem", codeSystem);
        qry.setParameter("id", studyProtocolId);
        qry.executeUpdate();
    }

    @Override
    public Boolean canChangeCodeSystem(Long studyProtocolId) {
        if (studyProtocolId == null) {
            return false;
        }
        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySubject.class, "ss");
        criteria.createAlias("studyProtocol", "sp");
        criteria.add(Restrictions.eq("sp.id", studyProtocolId));
        criteria.add(Restrictions.eq("statusCode", FunctionalRoleStatusCode.ACTIVE));
        criteria.add(Restrictions.isNotNull("disease"));
        criteria.setProjection(Projections.rowCount());
        return (0 == (Integer) criteria.uniqueResult());
    }
}
