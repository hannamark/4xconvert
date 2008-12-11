package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
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
@SuppressWarnings({  "PMD.ExcessiveMethodLength" , "PMD.AvoidDuplicateLiterals",
  "PMD.CyclomaticComplexity" })
public class StudyResourcingServiceBean implements StudyResourcingServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyResourcingServiceBean.class);

    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    
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

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " join sr.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
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
        final int serialNumMin = 5;
        final int serialNumMax = 6;
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        LOG.debug("Entering updateStudyResourcing ");
        Session session = null;
        StudyResourcing studyResourcing = null;
        StudyResourcingDTO studyResourcingRetDTO = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        
        if (studyResourcingDTO.getSerialNumber() != null 
            && studyResourcingDTO.getSerialNumber().getValue() != null) {
          String snValue = studyResourcingDTO.getSerialNumber().getValue().toString();
          if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
            throw new PAException("Serial number can be numeric with 5 or 6 digits");
          }
        }
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IiConverter.convertToLong(studyResourcingDTO.getIdentifier());
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
            if (ejbContext != null) {
                studyResourcing.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                    studyResourcingDTO.getFundingMechanismCode()));
            studyResourcing.setFundingTypeCode(studyResourcingDTO.getFundingTypeCode().getCode());
            studyResourcing.setNciDivisionProgramCode(MonitorCode.getByCode(
                        studyResourcingDTO.getNciDivisionProgramCode().getCode()));
            studyResourcing.setNihInstituteCode(studyResourcingDTO.getNihInstitutionCode().getCode());
            studyResourcing.setSuffixGrantYear(StConverter.convertToString(studyResourcingDTO.getSuffixGrantYear()));
            studyResourcing.setSuffixOther(StConverter.convertToString(studyResourcingDTO.getSuffixOther()));
            studyResourcing.setSerialNumber(IntConverter.convertToInteger(studyResourcingDTO.getSerialNumber()));
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
        final int serialNumMin = 5;
        final int serialNumMax = 6;
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        LOG.debug("Entering createStudyResourcing ");
        Session session = null;
        
        if (studyResourcingDTO.getSerialNumber() != null) {
          String snValue = studyResourcingDTO.getSerialNumber().getValue().toString();
          if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
            throw new PAException("Serial number can be numeric with 5 or 6 digits");
          }
        }
        StudyResourcing studyResourcing = StudyResourcingConverter.convertFromDTOToDomain(studyResourcingDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        studyResourcing.setDateLastCreated(now);
        if (ejbContext != null) {
            studyResourcing.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
        // create Protocol Obj
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(studyResourcingDTO.getStudyProtocolIi()));


        studyResourcing.setStudyProtocol(studyProtocol);
        studyResourcing.setActiveIndicator(true);
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
     * @param studyProtocolIi Ii
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getstudyResourceByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocolIi should not be null ");
            throw new PAException(" studyProtocolIi should not be null ");
        }
        LOG.info("Entering getstudyResourceByStudyProtocol");
        Session session = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " join sr.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
                       + " and sr.summary4ReportedResourceIndicator =  '" + Boolean.FALSE + "'"
                       + " and sr.activeIndicator =  '" + Boolean.TRUE + "'";

           LOG.info(" query getstudyResourceByStudyProtocol = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();


        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getstudyResourceByStudyProtocol" , hbe);
            throw new PAException(" Hibernate exception while retrieving getstudyResourceByStudyProtocol "  , hbe);
        }

        ArrayList<StudyResourcingDTO> resultList = new ArrayList<StudyResourcingDTO>();
        for (StudyResourcing bo : queryList) {
            resultList.add(StudyResourcingConverter.convertFromDomainToDTO(bo));
        }
        session.flush();
        LOG.info("Leaving getstudyResourceByStudyProtocol");
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

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IiConverter.convertToLong(studyResourceIi);

           LOG.info(" query getStudyResourceByID = " + hql);

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
    /**
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Boolean deleteStudyResourceByID(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        LOG.debug("Entering deleteStudyResourceByID ");
        Boolean result = false;
        Session session = null;
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IiConverter.convertToLong(studyResourcingDTO.getIdentifier());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            studyResourcing = queryList.get(0);
            // set the values from paramter
            studyResourcing.setActiveIndicator(false);
            studyResourcing.setInactiveCommentText(StConverter.convertToString(
                    studyResourcingDTO.getInactiveCommentText()));
            studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            if (ejbContext != null) {
                studyResourcing.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            session.update(studyResourcing);
            session.flush();
            result = true;
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving deleteStudyResourceByID" , hbe);
            throw new PAException(" Hibernate exception while retrieving deleteStudyResourceByID "  , hbe);
        }
        LOG.debug("Leaving deleteStudyResourceByID ");
        return result;
    }
}
