/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
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
 * @author Hugh Reinhart
 * @since 08/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudyOverallStatusServiceBean implements
        StudyOverallStatusServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyOverallStatusServiceBean.class);

    /**
     * @param ii index
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    public StudyOverallStatusDTO getStudyOverallStatus(Ii ii)
            throws PAException {
        throw new PAException("Method not yet implemented.");
    }

    /**
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    public StudyOverallStatusDTO createStudyOverallStatus(
            StudyOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIi())) {
            LOG.error(" Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ");
            throw new PAException(" Existing StudyOverallStatus objects cannot be modified. "
                                + " Append new object instead. ");
        }
        
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyOverallStatusDTO oldStatus = getCurrentStudyOverallStatusByStudyProtocol(
                    dto.getStudyProtocolIi());
            StudyStatusCode oldCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
            StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
            Timestamp oldDate = TsConverter.convertToTimestamp(oldStatus.getStatusDate());
            Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
            if (newCode == null) {
                throw new PAException(" Study status must be set ");
            }
            if (newDate == null) {
                throw new PAException(" Study status date must be set ");
            }
            if (!oldCode.canTransitionTo(newCode)) {
                throw new PAException(" Illegal study status transition from " + oldCode.getCode()
                        + " to " + newCode.getCode());
            }
            if (!newCode.equals(oldCode) || !newDate.equals(oldDate)) {
                StudyOverallStatus bo = StudyOverallStatusConverter.convertFromDtoToDomain(dto);
                session.saveOrUpdate(bo);
                session.flush();
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in updateStudyOverallStatus ", hbe);
            throw new PAException(" Hibernate exception in updateStudyOverallStatus ", hbe);
        }
        return null;
    }

    /**
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    public StudyOverallStatusDTO updateStudyOverallStatus(
            StudyOverallStatusDTO dto) throws PAException {
        throw new PAException("Method not yet implemented.");
    }

    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return List.
     * @throws PAException Exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyOverallStatusDTO> getStudyOverallStatusByStudyProtocol(
            Ii studyProtocolIi) throws PAException {

        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudyOverallStatusByStudyProtocol");
        
        Session session = null;
        List<StudyOverallStatus> queryList = new ArrayList<StudyOverallStatus>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
        
            // step 1: form the hql
            String hql = "select sos "
                       + "from StudyOverallStatus sos "
                       + "join sos.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by sos.id ";
            LOG.info(" query StudyOverallStatus = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            
            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getStudyProtocolByCriteria ", hbe);
            throw new PAException(" Hibernate exception in getStudyProtocolByCriteria ", hbe);
        }
        ArrayList<StudyOverallStatusDTO> resultList = new ArrayList<StudyOverallStatusDTO>();
        for (StudyOverallStatus bo : queryList) {
            resultList.add(StudyOverallStatusConverter.convertFromDomainToDTO(bo));
        }
        
        LOG.info("Leaving getStudyOverallStatusByStudyProtocol, returning " + resultList.size() + " object(s).");
        return resultList;
    }
    
    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return StudyOverallStatusDTO Current status.
     * @throws PAException Exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyOverallStatusDTO getCurrentStudyOverallStatusByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        List<StudyOverallStatusDTO> sosList = this.getStudyOverallStatusByStudyProtocol(studyProtocolIi);
        if (sosList.isEmpty()) {
            return null;
        }
        return sosList.get(sosList.size() - 1);
    }
}
