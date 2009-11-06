/**
 * 
 */
package gov.nih.nci.pa.service.internal;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudySiteAccrualStatusBeanLocal implements StudySiteAccrualStatusServiceLocal {
 
 private static final Logger LOG  = Logger.getLogger(StudySiteAccrualStatusBeanLocal.class);
 private static String errMsgMethodNotImplemented = "Method not yet implemented.";

// Standard methods
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



// Custom methods
/**
 * @param studySiteIi id of Site
 * @return list StudySiteAccrualStatusDTO
 * @throws PAException on error
 */
@SuppressWarnings("unchecked")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(Ii studySiteIi)
throws PAException {
    if (PAUtil.isIiNull(studySiteIi)) {
        LOG.error(" Ii should not be null ");
        throw new PAException(" Ii should not be null ");
    }
    LOG.info("Entering getStudySiteAccrualStatusByStudySite");

    Session session = null;
    List<StudySiteAccrualStatus> queryList = new ArrayList<StudySiteAccrualStatus>();
    session = HibernateUtil.getCurrentSession();
    Query query = null;

    // step 1: form the hql
    String hql = "select ssas "
        + "from StudySiteAccrualStatus ssas "
        + "join ssas.studySite sp "
        + "where sp.id = :studySiteId "
        + "order by ssas.id ";
    LOG.info(" query StudySiteAccrualStatus = " + hql);

    // step 2: construct query object
    query = session.createQuery(hql);
    query.setParameter("studySiteId", IiConverter.convertToLong(studySiteIi));

    // step 3: query the result
    queryList = query.list();
    ArrayList<StudySiteAccrualStatusDTO> resultList = new ArrayList<StudySiteAccrualStatusDTO>();
    for (StudySiteAccrualStatus bo : queryList) {
        resultList.add(StudySiteAccrualStatusConverter.convertFromDomainToDTO(bo));
    }

    LOG.info("Leaving getStudySiteAccrualStatusByStudySite, returning "
            + resultList.size() + " object(s).");
    return resultList;
}

/**
 * @param studySiteIi Primary key assigned to a StudyProtocl.
 * @return StudySiteAccrualStatusDTO Current status.
 * @throws PAException Exception.
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS) 
public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(
        Ii studySiteIi) throws PAException {
    List<StudySiteAccrualStatusDTO> ssasList =
            this.getStudySiteAccrualStatusByStudySite(studySiteIi);
    StudySiteAccrualStatusDTO result = null;
    if (!ssasList.isEmpty()) {
        result = ssasList.get(ssasList.size() - 1);
    }
    return result;
}

}
