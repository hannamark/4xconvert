package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudySiteOverallStatus;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 * 
 * @author Vrushali
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public  class StudySiteOverallStatusServiceBean  implements
        StudySiteOverallStatusServiceRemote, StudySiteOverallStatusServiceLocal {
    private static final Logger LOG  = Logger.getLogger(StudySiteOverallStatusServiceBean.class);
    /**
     * 
     * @param dto iso
     * @return bo
     * @throws PAException e
     */
    public StudySiteOverallStatusDTO create(
            StudySiteOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = " Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        StudySiteOverallStatusDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            // enforce business rules
           StudySiteOverallStatusDTO oldStatus = getCurrentByStudySite(dto.getStudySiteIdentifier());

           StudyStatusCode oldCode = null;
            Timestamp oldDate = null;

            if (oldStatus != null) {
                oldCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
                oldDate = TsConverter.convertToTimestamp(oldStatus.getStatusDate());
            }
            StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
            Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
            if (newCode == null) {
                throw new PAException("Study status must be set.  ");
            }
            if (newDate == null) {
                throw new PAException("Study status date must be set.  ");
            }
            if ((oldCode != null) && !oldCode.canTransitionTo(newCode)) {
                throw new PAException("Illegal site status transition from " + oldCode.getCode()
                        + " to " + newCode.getCode() + ".  ");
            }
            if ((oldDate != null) && newDate.before(oldDate)) {
                throw new PAException("New current site status date should be bigger/same as old date.  ");
            }

            StudySiteOverallStatus bo = Converters.get(StudySiteOverallStatusConverter.class).
                convertFromDtoToDomain(dto);

            // update
            session.saveOrUpdate(bo);
            resultDto = Converters.get(StudySiteOverallStatusConverter.class).convertFromDomainToDto(bo);
        } catch (HibernateException hbe) {
        throw new PAException(" Hibernate exception in createStudySiteOverallStatus ", hbe);
        }
        return resultDto;
    }
    private StudySiteOverallStatusDTO getCurrentByStudySite(
            Ii studySiteIdentifier) throws PAException {
        List<StudySiteOverallStatusDTO> dtoList = getByStudySite(studySiteIdentifier);
        StudySiteOverallStatusDTO result = null;
        if (!dtoList.isEmpty()) {
            result = dtoList.get(dtoList.size() - 1);
        }
        return result;
    }
    /**
     * @param studySiteIi id of Site
     * @return list StudySiteAccrualStatusDTO
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteOverallStatusDTO> getByStudySite(Ii studySiteIi)
            throws PAException {
        if (PAUtil.isIiNull(studySiteIi)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudySiteOverallStatusByStudySite");

        Session session = null;
        List<StudySiteOverallStatus> queryList = new ArrayList<StudySiteOverallStatus>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select ssos "
                       + " from StudySiteOverallStatus as ssos "
                       + " join ssos.studySite as sp "
                       + " where sp.id = :studySiteId "
                       + " order by ssos.id ";
            LOG.info(" query StudySiteOverallStatus = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studySiteId", IiConverter.convertToLong(studySiteIi));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getStudySiteOverallStatusByStudySite ", hbe);
            throw new PAException(" Hibernate exception in getStudySiteOverallStatusByStudySite ", hbe);
        }
        ArrayList<StudySiteOverallStatusDTO> resultList = new ArrayList<StudySiteOverallStatusDTO>();
        for (StudySiteOverallStatus bo : queryList) {
            resultList.add(new StudySiteOverallStatusConverter().convertFromDomainToDto(bo));
        }

        LOG.info("Leaving getStudySiteOverallStatusByStudySite, returning "
                + resultList.size() + " object(s).");
        return resultList;
    }

}
