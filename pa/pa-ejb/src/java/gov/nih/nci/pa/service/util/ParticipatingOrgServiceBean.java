package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
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

import org.apache.commons.collections.CollectionUtils;
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
    @EJB
    private PAHealthCareProviderRemote paHealthCareProviderService;

    private static final String BY_PROTOCOL_HQL =
            "select ss, org.name, org.identifier, sp.id "
                    + "from StudySite ss "
                    + "join ss.studyProtocol sp "
                    + "join ss.healthCareFacility hf "
                    + "join hf.organization org "
                    + "where sp.id = :spId "
                    + "and ss.functionalCode = :functionalCode "
                    + "order by upper(org.name)";

    private static final String BY_STUDY_SITE_HQL =
            "select ss, org.name, org.identifier, sp.id "
                    + "from StudySite ss "
                    + "join ss.studyProtocol sp "
                    + "join ss.healthCareFacility hf "
                    + "join hf.organization org "
                    + "where ss.id = :studySiteId "
                    + "and ss.functionalCode = :functionalCode";

    private static final int STUDY_SITE_IDX = 0;
    private static final int ORG_NAME_IDX = 1;
    private static final int ORG_IDENTIFIER_IDS = 2;
    private static final int PROTOCOL_IDENTIFIER_IDX = 3;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ParticipatingOrgDTO> getTreatingSites(Long studyProtocolId) throws PAException {
        if (studyProtocolId == null) {
            return new ArrayList<ParticipatingOrgDTO>();
        }
        List<ParticipatingOrgDTO> result = new ArrayList<ParticipatingOrgDTO>();
        List<Long> studySiteIds = new ArrayList<Long>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(BY_PROTOCOL_HQL);
            qry.setParameter("spId", studyProtocolId);
            qry.setParameter("functionalCode", StudySiteFunctionalCode.TREATING_SITE);
            processQueryResults(result, studySiteIds, qry);
        } catch (Exception e) {
            throw new PAException(e);
        }
        addAccrualAndPersonData(result, studySiteIds);
        return result;
    }

    @Override
    public ParticipatingOrgDTO getTreatingSite(Long studySiteId)
            throws PAException {
        List<ParticipatingOrgDTO> result = new ArrayList<ParticipatingOrgDTO>();
        List<Long> studySiteIds = new ArrayList<Long>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                    BY_STUDY_SITE_HQL);
            qry.setParameter("studySiteId", studySiteId);
            qry.setParameter("functionalCode",
                    StudySiteFunctionalCode.TREATING_SITE);
            processQueryResults(result, studySiteIds, qry);
        } catch (Exception e) {
            throw new PAException(e);
        }
        addAccrualAndPersonData(result, studySiteIds);
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * @param result
     * @param studySiteIds
     * @throws PAException
     */
    private void addAccrualAndPersonData(List<ParticipatingOrgDTO> result,
            List<Long> studySiteIds) throws PAException {
        if (CollectionUtils.isNotEmpty(studySiteIds)) {
            Long[] ssIdArray = studySiteIds.toArray(new Long[studySiteIds.size()]);
            addAccrualAndPersonData(result, ssIdArray);
        }
    }

    /**
     * @param result
     * @param studySiteIds
     * @param qry
     */
    @SuppressWarnings("unchecked")
    private void processQueryResults(List<ParticipatingOrgDTO> result,
            List<Long> studySiteIds, Query qry) {
        List<Object[]> queryList = qry.list();
        for (Object[] row : queryList) {
            StudySite studySite = (StudySite) row[STUDY_SITE_IDX];
            ParticipatingOrgDTO site = new ParticipatingOrgDTO();
            site.setName((String) row[ORG_NAME_IDX]);
            site.setPoId((String) row[ORG_IDENTIFIER_IDS]);
            site.setStudyProtocolId((Long) row[PROTOCOL_IDENTIFIER_IDX]);
            site.setStudySiteId(studySite.getId());
            site.setStatusCode(studySite.getStatusCode());
            site.setTargetAccrualNumber(studySite.getTargetAccrualNumber());
            site.setProgramCodeText(studySite.getProgramCodeText());
            result.add(site);
            studySiteIds.add(site.getStudySiteId());
        }
    }

    private void addAccrualAndPersonData(List<ParticipatingOrgDTO> poList, Long[] ssIdArray) throws PAException {
        Map<Long, StudySiteAccrualStatus> siteStatuses =
                studySiteAccrualStatusService.getCurrentStudySiteAccrualStatus(ssIdArray);
        Map<Long, List<PaPersonDTO>> pcs =
                paHealthCareProviderService.getPersonsByStudySiteId(ssIdArray,
                        StudySiteContactRoleCode.PRIMARY_CONTACT.getName());
        Map<Long, List<PaPersonDTO>> pis =
                paHealthCareProviderService.getPersonsByStudySiteId(ssIdArray,
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        Map<Long, List<PaPersonDTO>> sis =
                paHealthCareProviderService.getPersonsByStudySiteId(ssIdArray,
                        StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (ParticipatingOrgDTO org : poList) {
            StudySiteAccrualStatus ssas = siteStatuses.get(org.getStudySiteId());
            if (ssas != null) {
                org.setRecruitmentStatus(ssas.getStatusCode());
                org.setRecruitmentStatusDate(ssas.getStatusDate());
            }
            org.setPrimaryContacts(getPeople(pcs.get(org.getStudySiteId())));
            org.setPrincipalInvestigators(getPeople(pis.get(org.getStudySiteId())));
            org.setSubInvestigators(getPeople(sis.get(org.getStudySiteId())));
        }
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param paHealthCareProviderService the paHealthCareProviderService to set
     */
    public void setPaHealthCareProviderService(PAHealthCareProviderRemote paHealthCareProviderService) {
        this.paHealthCareProviderService = paHealthCareProviderService;
    }

    private List<PaPersonDTO> getPeople(List<PaPersonDTO> people) {
        return people == null ? new ArrayList<PaPersonDTO>() : people;
    }
}
