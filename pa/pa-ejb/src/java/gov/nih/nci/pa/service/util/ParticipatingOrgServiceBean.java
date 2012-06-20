package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Query;

/**
 * @author Hugh Reinhart
 * @since Jun 18, 2012
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ParticipatingOrgServiceBean implements ParticipatingOrgServiceLocal {

    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;

    private static final String HQL =
            "select ss, org.name, org.identifier "
                    + "from StudySite ss "
                    + "join ss.studyProtocol sp "
                    + "join ss.healthCareFacility hf "
                    + "join hf.organization org "
                    + "where sp.id = :spId "
                    + "and ss.functionalCode = :functionalCode";

    private static final int STUDY_SITE_IDX = 0;
    private static final int ORG_NAME_IDX = 1;
    private static final int ORG_IDENTIFIER_IDS = 2;

    /**
     * {@inheritDoc}
     * @throws PAException 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ParticipatingOrgDTO> getTreatingSites(Long studyProtocolId) throws PAException {
        List<ParticipatingOrgDTO> result = new ArrayList<ParticipatingOrgDTO>();
        List<Long> studySiteIds = new ArrayList<Long>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(HQL);
            qry.setParameter("spId", studyProtocolId);
            qry.setParameter("functionalCode", StudySiteFunctionalCode.TREATING_SITE);
            List<Object[]> queryList = qry.list();
            for (Object[] row : queryList) {
                StudySite studySite = (StudySite) row[STUDY_SITE_IDX];
                ParticipatingOrgDTO site = new ParticipatingOrgDTO();
                site.setName((String) row[ORG_NAME_IDX]);
                site.setPoId((String) row[ORG_IDENTIFIER_IDS]);
                site.setStudySiteId(studySite.getId());
                site.setStatusCode(studySite.getStatusCode());
                site.setTargetAccrualNumber(studySite.getTargetAccrualNumber());
                site.setProgramCodeText(studySite.getProgramCodeText());
                result.add(site);
                studySiteIds.add(site.getStudySiteId());
            }
        } catch (Exception e) {
            throw new PAException(e);
        }
        Map<Long, StudySiteAccrualStatus> siteStatuses =
                studySiteAccrualStatusService.getCurrentStudySiteAccrualStatus((studySiteIds
                        .toArray(new Long[studySiteIds.size()])));
        for (ParticipatingOrgDTO org : result) {
            StudySiteAccrualStatus ssas = siteStatuses.get(org.getStudySiteId());
            if (ssas != null) {
                org.setRecruitmentStatus(ssas.getStatusCode());
                org.setRecruitmentStatusDate(ssas.getStatusDate());
            }
        }
        return result;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }
    
}
