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
public class StudyParticipationServiceBean
        extends AbstractStudyPaService<StudyParticipationDTO>  
        implements StudyParticipationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyParticipationServiceBean.class);
    
    @Override
    Logger getLogger() {
        return LOG;
    }
    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO create(
            StudyParticipationDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIi())) {
            serviceError(" Update method should be used to modify existing. ");
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
            serviceError(" Hibernate exception in createStudyParticipation ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii Index of StudyParticipation object
     * @throws PAException PAException
     */
    public void delete(Ii ii)
            throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering deleteStudyParticipation");
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipation bo = (StudyParticipation) session.get(StudyParticipation.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting "
                + "StudyParticipation for pid = " + ii.getExtension(), hbe);
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
    @Override
    public List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
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
                       + "where spro.id = :studyProtocolId " 
                       + " order by spart.id ";
            LOG.info(" query StudyParticipation = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
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
