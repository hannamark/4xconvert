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
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";

    /**
     * @param ii index
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    public StudyOverallStatusDTO getStudyOverallStatus(Ii ii)
            throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    public StudyOverallStatusDTO createStudyOverallStatus(
            StudyOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIi())) {
            String errMsg = " Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        StudyOverallStatusDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            List<StudyOverallStatusDTO> oldStatus = getCurrentStudyOverallStatusByStudyProtocol(
                    dto.getStudyProtocolIi());
            
            StudyStatusCode oldCode = null;
            Timestamp oldDate = null;
            if (!oldStatus.isEmpty()) {
                oldCode = StudyStatusCode.getByCode(oldStatus.get(0).getStatusCode().getCode());
                oldDate = TsConverter.convertToTimestamp(oldStatus.get(0).getStatusDate());
            }
            StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
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
                resultDto = StudyOverallStatusConverter.convertFromDomainToDTO(bo);
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in createStudyOverallStatus ", hbe);
            throw new PAException(" Hibernate exception in createStudyOverallStatus ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    public StudyOverallStatusDTO updateStudyOverallStatus(
            StudyOverallStatusDTO dto) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
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
            String errMsg = " Ii should not be null ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
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
     * @return List Current status StudyOverllStatusDTO.
     * @throws PAException Exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyOverallStatusDTO> getCurrentStudyOverallStatusByStudyProtocol(Ii studyProtocolIi) 
            throws PAException {
        List<StudyOverallStatusDTO> sosList = this.getStudyOverallStatusByStudyProtocol(studyProtocolIi);
        List<StudyOverallStatusDTO> resultList = new ArrayList<StudyOverallStatusDTO>();
        if (!sosList.isEmpty()) {
            resultList.add(sosList.get(sosList.size() - 1));
        }
        return resultList;
    }
}
