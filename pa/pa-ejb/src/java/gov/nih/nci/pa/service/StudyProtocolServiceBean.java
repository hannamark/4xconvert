package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;

import gov.nih.nci.pa.service.impl.StudyProtocolServiceImpl;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 08/13/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class StudyProtocolServiceBean  implements StudyProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceBean.class);
  
    /**
     * gets a list StudyProtocl by criteria.
     * @param studyProtocolQueryCriteria studyProtocolQueryCriteria
     * @return pdtos
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> 
                getStudyProtocolByCriteria(StudyProtocolQueryCriteria studyProtocolQueryCriteria) throws PAException {
            LOG.debug("Entering getStudyProtocolByCriteria ");
        StudyProtocolServiceImpl pImpl = new StudyProtocolServiceImpl();
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        pdtos = pImpl.getStudyProtocolByCriteria(studyProtocolQueryCriteria);
        LOG.debug("Leaving getStudyProtocolByCriteria ");
        return pdtos;
    }
    
    /**
     * 
     * @param studyProtocolId studyProtocolId
     * @return StudyProtocolQueryDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) 
    throws PAException {
        LOG.debug("Entering getTrialSummaryByStudyProtocolId ");
        StudyProtocolQueryDTO trialSummary = (new StudyProtocolServiceImpl()).
            getTrialSummaryByStudyProtocolId(studyProtocolId);
        LOG.debug("Leaving getTrialSummaryByStudyProtocolId ");
        return trialSummary;
    }
    
    /**
     * 
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudyProtocol");
        Session session = null;
        StudyProtocol studyProtocol = null;
        try {
            session = HibernateUtil.getCurrentSession();
            studyProtocol = (StudyProtocol) 
                session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving StudyProtocol for id = " + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving " 
                    + "StudyProtocol for id = " + ii.getExtension() , hbe);
        }
        StudyProtocolDTO studyProtocolDTO =  
            StudyProtocolConverter.convertFromDomainToDTO(studyProtocol);

        LOG.info("Leaving getStudyProtocol");
        return studyProtocolDTO;
        
    }
    


    /**
     * 
     * @param studyProtocolDTO studyProtocolDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException {
        if (studyProtocolDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");
            
        }
        StudyProtocolDTO  spDTO = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();

            StudyProtocol studyProtocol = StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO);
            StudyProtocol sp = (StudyProtocol) session.load(StudyProtocol.class, studyProtocol.getId());
            if (studyProtocol.equals(sp)) {
                sp = (StudyProtocol) session.merge(studyProtocol);
            } else {
                sp = studyProtocol;
            }
            session.update(sp);
            session.flush();

            spDTO =  StudyProtocolConverter.convertFromDomainToDTO(sp);  
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating StudyProtocol for id = " 
                    + studyProtocolDTO.getIi().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating StudyProtocol for id = " 
                    + studyProtocolDTO.getIi().getExtension() , hbe);
        }

        return spDTO;
        
    }
    

}
