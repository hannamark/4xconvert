/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.iso.convert.StudyParticipationConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StudyParticipationServiceBean implements StudyParticipationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyParticipationServiceBean.class);
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";
    /**
     * @param ii index
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    public StudyParticipationDTO getStudyParticipation(Ii ii)
    throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    public StudyParticipationDTO createStudyParticipation(
            StudyParticipationDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIi())) {
            LOG.error(" Update method should be used to modify existing. ");
            throw new PAException(" Update method should be used to modify existing. ");
        }
        StudyParticipationDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipation bo = StudyParticipationConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyParticipationConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in createStudyParticipation ", hbe);
            throw new PAException(" Hibernate exception in createStudyParticipation ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    public StudyParticipationDTO updateStudyParticipation(
            StudyParticipationDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    /**
     * @param ii Index of StudyParticipation object
     * @throws PAException PAException
     */
    public void deleteStudyParticipation(Ii ii)
            throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering deleteStudyParticipation");
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            Query query = null;
            
            // step 1: form the hql
            String hql = "select spart "
                       + "from StudyParticipation spart "
                       + "where spart.id =  " + IiConverter.convertToString(ii);
            LOG.info(" query StudyParticipation = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
            // step 3: delete
            if (queryList.isEmpty()) {
                LOG.error(" Trying to delete for non-existend Ii. ");
                throw new PAException(" Trying to delete for non-existent Ii. ");
            }
            session.delete(queryList.get(0));
            session.flush();
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while deleting "
                    + "StudyParticipation for pid = " + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while deleting " 
                    + "StudyParticipation for pid = " + ii.getExtension() , hbe);
        }
        
        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : queryList) {
            resultList.add(StudyParticipationConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving deleteStudyParticipation");
    }
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyParticipationDTO   
     * @throws PAException on error 
     */
    public List<StudyParticipationDTO> getStudyParticipationByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudyParticipationByStudyProtocol");
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = "select spart "
                       + "from StudyParticipation spart "
                       + "join spart.studyProtocol spro "
                       + "where spro.id =  " + IiConverter.convertToString(studyProtocolIi)
                       + " order by spart.id ";
            LOG.info(" query StudyParticipation = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving " 
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }
        
        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : queryList) {
            resultList.add(StudyParticipationConverter.convertFromDomainToDTO(sp));
        }

        LOG.info("Leaving etStudyParticipationByStudyProtocol");
        return resultList;
    }
}
