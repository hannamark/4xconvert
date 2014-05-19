/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudySiteAccrualStatusSortCriterion;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TrialUpdatesRecorder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudySiteAccrualStatusBeanLocal extends AbstractBaseSearchBean<StudySiteAccrualStatus>
    implements StudySiteAccrualStatusServiceLocal {

    private static String errMsgMethodNotImplemented = "Method not yet implemented.";
    
    @EJB
//    @IgnoreDependency
    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessServiceLocal;

    /**
     * @param ii index
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    @Override
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = "Existing StudySiteAccrualStatus objects cannot be modified.  Append new object instead.";
            throw new PAException(errMsg);
        }
        StudySiteAccrualStatusDTO resultDto = null;
        Session session = PaHibernateUtil.getCurrentSession();
        StudySiteAccrualStatusDTO current = getCurrentStudySiteAccrualStatusByStudySite(dto.getStudySiteIi());
        RecruitmentStatusCode oldCode = null;
        Timestamp oldDate = null;
        if (current != null) {
            oldCode = RecruitmentStatusCode.getByCode(current.getStatusCode().getCode());
            oldDate = TsConverter.convertToTimestamp(current.getStatusDate());
        }

        RecruitmentStatusCode newCode = RecruitmentStatusCode.getByCode(dto.getStatusCode().getCode());
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());

        validateNewStatus(newCode, newDate);
        if (!newCode.equals(oldCode) || !newDate.equals(oldDate)) {
            final StudySiteAccrualStatusConverter converter = new StudySiteAccrualStatusConverter();
            StudySiteAccrualStatus bo = converter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            resultDto = converter.convertFromDomainToDto(bo);
            TrialUpdatesRecorder
                    .recordUpdate(TrialUpdatesRecorder.RECRUITMENT_STATUS_DATE_UPDATED);
        }
        return resultDto;
    }

    private void validateNewStatus(RecruitmentStatusCode newCode, Timestamp newDate) throws PAException {
        if (newCode == null) {
            throw new PAException("Study site accrual status must be set.");
        }
        if (newDate == null) {
            throw new PAException("Study site accrual status date must be set.");
        }
    }

    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    @Override
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(Ii studySiteIi) throws PAException {
        if (ISOUtil.isIiNull(studySiteIi)) {
            throw new PAException("Cannot call getStudySiteAccrualStatusByStudySite with a null identifier.");
        }

        StudySiteAccrualStatus criteria = new StudySiteAccrualStatus();
        StudySite ss = new StudySite();
        ss.setId(IiConverter.convertToLong(studySiteIi));
        criteria.setStudySite(ss);

        PageSortParams<StudySiteAccrualStatus> params =
            new PageSortParams<StudySiteAccrualStatus>(PAConstants.MAX_SEARCH_RESULTS, 0,
                    StudySiteAccrualStatusSortCriterion.STUDY_SITE_ACCRUAL_ID, false);
        List<StudySiteAccrualStatus> results =
            search(new AnnotatedBeanSearchCriteria<StudySiteAccrualStatus>(criteria), params);

        List<StudySiteAccrualStatusDTO> returnList = new ArrayList<StudySiteAccrualStatusDTO>();
        for (StudySiteAccrualStatus bo : results) {
            returnList.add(new StudySiteAccrualStatusConverter().convertFromDomainToDto(bo));
        }
        return returnList;
    }

    /**
     * @param studySiteIi Primary key assigned to a StudyProtocl.
     * @return StudySiteAccrualStatusDTO Current status.
     * @throws PAException Exception.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(Ii studySiteIi) throws PAException {
        List<StudySiteAccrualStatusDTO> ssasList = getStudySiteAccrualStatusByStudySite(studySiteIi);
        StudySiteAccrualStatusDTO result = null;
        if (!ssasList.isEmpty()) {
            result = ssasList.get(ssasList.size() - 1);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, StudySiteAccrualStatus> getCurrentStudySiteAccrualStatus(Long[] ids) throws PAException {
        Map<Long, StudySiteAccrualStatus> result = new HashMap<Long, StudySiteAccrualStatus>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                    "from StudySiteAccrualStatus ssas join fetch ssas.studySite ss "
                            + " where ss.id in (:ids) order by ssas.id");
            qry.setParameterList("ids", ids);
            @SuppressWarnings("unchecked")
            List<StudySiteAccrualStatus> queryList = qry.list();
            for (StudySiteAccrualStatus row : queryList) {
                result.put(row.getStudySite().getId(), row);
            }
        } catch (HibernateException e) {
            throw new PAException(e);
        }
        return result;
    }

    /**
     * @return the studySiteAccrualAccessServiceLocal
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessServiceLocal() {
        return studySiteAccrualAccessServiceLocal;
    }

    /**
     * @param studySiteAccrualAccessServiceLocal the studySiteAccrualAccessServiceLocal to set
     */
    public void setStudySiteAccrualAccessServiceLocal(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessServiceLocal) {
        this.studySiteAccrualAccessServiceLocal = studySiteAccrualAccessServiceLocal;
    }
}
