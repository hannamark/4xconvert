package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.InstitutionCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.IsoConverter;
import gov.nih.nci.pa.util.PAUtil;

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
 * @author Naveen Amiruddin
 * @since 09/11/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({  "PMD.ExcessiveMethodLength" , "PMD.AvoidDuplicateLiterals" })
public class StudyResourcingServiceBean implements StudyResourcingServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(StudyResourcingServiceBean.class);
    
    /**
     * @param studyProtocolIi Ii 
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getsummary4ReportedResource(Ii studyProtocolIi) throws PAException {

        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocolIi should not be null ");
            throw new PAException(" studyProtocolIi should not be null ");
        }
        LOG.info("Entering getsummary4ReportedResource");
        StudyResourcingDTO studyResourcingDTO = null;
        Session session = null;
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();
            //@todo : session.load is not working, so using query to retrieve
            // studyProtocol = (StudyProtocol) 
            // session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            //session.flush();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " join sr.studyProtocol sp "
                       + " where sp.id = " + IsoConverter.convertIitoLong(studyProtocolIi)
                       + " and sr.summary4ReportedResourceIndicator =  '" + Boolean.TRUE + "'";

           LOG.info(" query studyResourcing = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
            if (queryList.size() > 1) {
                session.flush();
                LOG.error(" Summary 4 Reported Sourcing should not be more than 1 record ");
                throw new PAException(" Summary 4 Reported Sourcing should not be more than 1 record ");
                
            }
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getsummary4ReportedResource" , hbe);
            throw new PAException(" Hibernate exception while retrieving getsummary4ReportedResource "  , hbe);
        }
        
        if (!queryList.isEmpty()) {
            studyResourcing = queryList.get(0);
            studyResourcingDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);
            
        }
        session.flush();
        LOG.info("Leaving getsummary4ReportedResource");
        return studyResourcingDTO;
    }
    
    /**
     * 
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        LOG.debug("Entering updateStudyResourcing ");
        Session session = null;
        StudyResourcing studyResourcing = null;
        StudyResourcingDTO studyResourcingRetDTO = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();
            //@todo : session.load is not working, so using query to retrieve
            // studyProtocol = (StudyProtocol) 
            // session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            //session.flush();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IsoConverter.convertIitoLong(studyResourcingDTO.getIi());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            studyResourcing = queryList.get(0);
            // set the values from paramter
            studyResourcing.setTypeCode(SummaryFourFundingCategoryCode.getByCode(
                    studyResourcingDTO.getTypeCode().getCode()));
            studyResourcing.setOrganizationIdentifier(IiConverter.convertToString(
                    studyResourcingDTO.getOrganizationIdentifier()));
            studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            studyResourcing.setUserLastUpdated(studyResourcingDTO.getUserLastUpdated().getValue());
            studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                    studyResourcingDTO.getFundingMechanismCode()));
            studyResourcing.setFundingTypeCode(studyResourcingDTO.getFundingTypeCode());
            studyResourcing.setMonitorCode(MonitorCode.getByCode(studyResourcingDTO.getMonitorCode().getCode()));
            studyResourcing.setInstitutionCode(InstitutionCode.getByCode(
                    studyResourcingDTO.getInstitutionCode().getCode()));
            studyResourcing.setSuffixgrantYear(TsConverter.convertToTimestamp(studyResourcingDTO.getSuffixgrantYear()));
            studyResourcing.setSuffixOther(StConverter.convertToString(studyResourcingDTO.getSuffixOther()));
            studyResourcing.setSerialNumber(StConverter.convertToString(studyResourcingDTO.getSerialNumber()));
            session.update(studyResourcing);
            session.flush();
            studyResourcingRetDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);
            
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getsummary4ReportedResource" , hbe);
            throw new PAException(" Hibernate exception while retrieving getsummary4ReportedResource "  , hbe);
        }    
        LOG.debug("Leaving updateStudyResourcing ");
        return studyResourcingRetDTO;
    }
    
    /**
     * 
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }     
        LOG.debug("Entering createStudyResourcing ");
        Session session = null;
        StudyResourcing studyResourcing = StudyResourcingConverter.convertFromDTOToDomain(studyResourcingDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        studyResourcing.setDateLastUpdated(now);
        // create Protocol Obj
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(studyResourcingDTO.getStudyProtocolIi()));
        
       
        
        studyResourcing.setStudyProtocol(studyProtocol);
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(studyResourcing);
            session.flush();
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while createStudyResourcing " , hbe);
            throw new PAException(" Hibernate exception while createStudyResourcing " , hbe);
        }
        LOG.debug("Leaving createStudyResourcing ");
        return StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);

        
    }
    
    /**
     * 
     * @param ii StudyResourcing
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO getStudyResourcing(Ii ii) throws PAException {
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        if (ii == null) {
            LOG.error(" ii should not be null ");
            throw new PAException(" ii should not be null ");
        }            
        return studyProtocolDTO;
    }
    /**
     * @param studyProtocolIi Ii 
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getstudyResource(Ii studyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocolIi should not be null ");
            throw new PAException(" studyProtocolIi should not be null ");
        }
        LOG.info("Entering getstudyResource");
        Session session = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();
            //@todo : session.load is not working, so using query to retrieve
            // studyProtocol = (StudyProtocol) 
            // session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            //session.flush();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " join sr.studyProtocol sp "
                       + " where sp.id = " + IsoConverter.convertIitoLong(studyProtocolIi);

           LOG.info(" query studyResourcing = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
            
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getstudyResource" , hbe);
            throw new PAException(" Hibernate exception while retrieving getstudyResource "  , hbe);
        }
        
        ArrayList<StudyResourcingDTO> resultList = new ArrayList<StudyResourcingDTO>();
        for (StudyResourcing bo : queryList) {
            resultList.add(StudyResourcingConverter.convertFromDomainToDTO(bo));
        }
        session.flush();
        LOG.info("Leaving getstudyResource");
        return resultList;
    }
    /**
     * @param studyResourceIi Ii 
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getStudyResourceByID(Ii studyResourceIi)
            throws PAException {
        if (PAUtil.isIiNull(studyResourceIi)) {
            LOG.error(" studyProtocolIi should not be null ");
            throw new PAException(" studyProtocolIi should not be null ");
        }
        LOG.info("Entering getStudyResourceByID");
        Session session = null;
        StudyResourcingDTO studyResourcingDTO = null;
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();
            //@todo : session.load is not working, so using query to retrieve
            // studyProtocol = (StudyProtocol) 
            // session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            //session.flush();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IsoConverter.convertIitoLong(studyResourceIi);

           LOG.info(" query studyResourcing = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
            
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getStudyResourceByID" , hbe);
            throw new PAException(" Hibernate exception while retrieving getStudyResourceByID "  , hbe);
        }
        
        if (!queryList.isEmpty()) {
            studyResourcing = queryList.get(0);
            studyResourcingDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);
            
        }
        session.flush();
        LOG.info("Leaving getStudyResourceByID");
        return studyResourcingDTO;
    }
}
