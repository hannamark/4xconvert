package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.iso.convert.StratumGroupConverter;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
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
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class SubGroupsServiceBean implements SubGroupsServiceRemote {

    
    private static final Logger LOG  = Logger.getLogger(DocumentServiceBean.class);
    
    /**
     * @param studyProtocolIi Ii 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StratumGroupDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocolIi should not be null ");
            throw new PAException(" studyProtocolIi should not be null ");
        }
        LOG.info("Entering getDocumentsByStudyProtocol from SubGroupsServiceBean");
        Session session = null;
        List<StratumGroup> queryList = new ArrayList<StratumGroup>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;            
            String hql = " select sg from StratumGroup sg "
                       + " join sg.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi);

           LOG.info(" query getDocumentsByStudyProtocol from SubGroupsServiceBean= " + hql);
            
            query = session.createQuery(hql);
            queryList = query.list();            
            
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getDocumentsByStudyProtocol" , hbe);
            throw new PAException(" Hibernate exception while retrieving getDocumentsByStudyProtocol "  , hbe);
        }
        
        ArrayList<StratumGroupDTO> resultList = new ArrayList<StratumGroupDTO>();
        for (StratumGroup bo : queryList) {
            resultList.add(StratumGroupConverter.convertFromDomainToDTO(bo));
        }
        session.flush();
        LOG.info("Leaving getDocumentsByStudyProtocol from SubGroupsServiceBean");
        return resultList;
    }
    
    /**
     * @param sgDTO DocumentDTO 
     * @return SubGroupsDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StratumGroupDTO create(StratumGroupDTO sgDTO)
    throws PAException {
        if (sgDTO == null) {
            throw new PAException(" sgDTO should not be null ");
        }     
        LOG.debug("Entering create from SubGroupsServiceBean");
        Session session = null;
        StratumGroup sg = StratumGroupConverter.convertFromDTOToDomain(sgDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sg.setDateLastUpdated(now);
        
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(sg);
            session.flush();
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while create" , hbe);
            throw new PAException(" Hibernate exception while create" , hbe);
        }                
        LOG.debug("Leaving create from SubGroupsServiceBean");
        return sgDTO;  
    }
    
    /**
     * @param id Ii 
     * @return SubGroupsDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StratumGroupDTO get(Ii id) throws PAException {
       
        LOG.info("Entering get from SubGroupsServiceBean");
        Session session = null;
        StratumGroupDTO sgDTO = null;
        StratumGroup sg = null;
        List<StratumGroup> queryList = new ArrayList<StratumGroup>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = " select sg from StratumGroup sg "
                       + " where sg.id = " + IiConverter.convertToLong(id);

           LOG.info(" query get = " + hql);
           query = session.createQuery(hql);
           queryList = query.list();            
            
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving get" , hbe);
            throw new PAException(" Hibernate exception while retrieving get"  , hbe);
        }
        
        if (!queryList.isEmpty()) {
            sg = queryList.get(0);
            sgDTO = StratumGroupConverter.convertFromDomainToDTO(sg);            
        }
        session.flush();
        LOG.info("Leaving get from SubGroupsServiceBean");
        return sgDTO;
    }

    /**
     * 
     * @param sgDTO SubGroupsDTO
     * @return SubGroupsDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StratumGroupDTO update(StratumGroupDTO sgDTO) throws PAException {

        if (sgDTO == null) {
            LOG.error(" docDTO should not be null ");
            throw new PAException(" docDTO should not be null ");
        }
        LOG.debug("Entering update");
        Session session = null;        
        StratumGroup sg = null;
        StratumGroupDTO sgRetDTO = null;
        List<StratumGroup> queryList = new ArrayList<StratumGroup>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = " select sg from StratumGroup sg "
                + " where sg.id = " + IiConverter.convertToLong(sgDTO.getIi());
            
            query = session.createQuery(hql);
            queryList = query.list();
            sg = queryList.get(0);
            
            sg.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            sg.setUserLastUpdated(sgDTO.getUserLastUpdated().getValue());
            sg.setDescription(StConverter.convertToString(sgDTO.getDescription()));
            sg.setGroupNumberText(StConverter.convertToString(sgDTO.getGroupNumberText()));
            
            session.update(sg);
            session.flush();
            sgRetDTO = StratumGroupConverter.convertFromDomainToDTO(sg);
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving update" , hbe);
            throw new PAException(" Hibernate exception while retrieving update "  , hbe);
        }    
        LOG.debug("Leaving update from SubGroupsServiceBean");
        return sgRetDTO;
    }

    /**
     * 
     * @param id Ii 
     * @return Boolean
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Boolean delete(Ii id) throws PAException {
          
        LOG.debug("Entering delete from SubGroupsServiceBean");
        Boolean result = false;
        Session session = null;
        StratumGroup sg = null;
        List<StratumGroup> queryList = new ArrayList<StratumGroup>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null; 
            String hql = " select s from StratumGroup s"
                       + " where s.id = " + IiConverter.convertToLong(id);
            
            query = session.createQuery(hql);
            queryList = query.list();
            sg = queryList.get(0);
            
            session.delete(sg);
            session.flush();
            result = true;
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving delete" , hbe);
            throw new PAException(" Hibernate exception while retrieving delete"  , hbe);
        }   
        LOG.debug("Leaving delete from SubGroupsServiceBean ");
        return result;
    }
    
    
}
