/**
 *
 */
package gov.nih.nci.pa.service;

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
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudySiteAccrualStatusBeanLocal extends AbstractBaseSearchBean<StudySiteAccrualStatus>
    implements StudySiteAccrualStatusServiceLocal {

    private static final Logger LOG = Logger.getLogger(StudySiteAccrualStatusBeanLocal.class);
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";

    /**
     * @param ii index
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = " Existing StudySiteAccrualStatus objects cannot be modified.  Append new object instead. ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        StudySiteAccrualStatusDTO resultDto = null;
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StudySiteAccrualStatusDTO current = getCurrentStudySiteAccrualStatusByStudySite(dto.getStudySiteIi());
        RecruitmentStatusCode oldCode = null;
        Timestamp oldDate = null;
        if (current != null) {
            oldCode = RecruitmentStatusCode.getByCode(current.getStatusCode().getCode());
            oldDate = TsConverter.convertToTimestamp(current.getStatusDate());
        }

        RecruitmentStatusCode newCode = RecruitmentStatusCode.getByCode(dto.getStatusCode().getCode());
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());

        if (newCode == null) {
            throw new PAException(" Study site accrual status must be set ");
        }
        if (newDate == null) {
            throw new PAException(" Study site accrual status date must be set ");
        }
        if (!newCode.equals(oldCode) || !newDate.equals(oldDate)) {
            StudySiteAccrualStatus bo = StudySiteAccrualStatusConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            resultDto = StudySiteAccrualStatusConverter.convertFromDomainToDTO(bo);
        }
        return resultDto;
    }

    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(Ii studySiteIi) throws PAException {
        if (PAUtil.isIiNull(studySiteIi)) {
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
            returnList.add(StudySiteAccrualStatusConverter.convertFromDomainToDTO(bo));
        }
        return returnList;
    }

    /**
     * @param studySiteIi Primary key assigned to a StudyProtocl.
     * @return StudySiteAccrualStatusDTO Current status.
     * @throws PAException Exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(Ii studySiteIi) throws PAException {
        List<StudySiteAccrualStatusDTO> ssasList = this.getStudySiteAccrualStatusByStudySite(studySiteIi);
        StudySiteAccrualStatusDTO result = null;
        if (!ssasList.isEmpty()) {
            result = ssasList.get(ssasList.size() - 1);
        }
        return result;
    }

}
