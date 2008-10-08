package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.convert.DocumentConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
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
 * @since 10/1/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class DocumentServiceBean implements DocumentServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(DocumentServiceBean.class);
    
    /**
     * @param studyProtocolIi Ii 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocolIi should not be null ");
            throw new PAException(" studyProtocolIi should not be null ");
        }
        LOG.info("Entering getDocumentsByStudyProtocol");
        Session session = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select doc "
                       + " from Document doc "
                       + " join doc.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
                       + " and doc.activeIndicator =  '" + Boolean.TRUE + "'";

           LOG.info(" query getDocumentsByStudyProtocol = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
            
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getDocumentsByStudyProtocol" , hbe);
            throw new PAException(" Hibernate exception while retrieving getDocumentsByStudyProtocol "  , hbe);
        }
        
        ArrayList<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
        for (Document bo : queryList) {
            resultList.add(DocumentConverter.convertFromDomainToDTO(bo));
        }
        session.flush();
        LOG.info("Leaving getDocumentsByStudyProtocol");
        return resultList;
    }
    
    /**
     * @param docDTO DocumentDTO 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO createTrialDocument(DocumentDTO docDTO)
    throws PAException {
        if (docDTO == null) {
            throw new PAException(" docDTO should not be null ");
        }     
        LOG.debug("Entering createTrialDocument ");
        Session session = null;
        Document doc = DocumentConverter.convertFromDTOToDomain(docDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        doc.setDateLastUpdated(now);
        // create Protocol Obj
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(docDTO.getStudyProtocolIi()));       

        doc.setStudyProtocol(studyProtocol);
        doc.setActiveIndicator(true);
        try {
            session = HibernateUtil.getCurrentSession();

            session.save(doc);
            session.flush();
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while createTrialDocument " , hbe);
            throw new PAException(" Hibernate exception while createTrialDocument " , hbe);
        }                

        LOG.debug("Leaving createStudyResourcing ");
        return DocumentConverter.convertFromDomainToDTO(doc);  
    }
    
    /**
     * @param id Ii 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO getTrialDocumentById(Ii id) throws PAException {
       
        LOG.info("Entering getTrialDocumentById");
        Session session = null;
        DocumentDTO docDTO = null;
        Document doc = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select doc "
                       + " from Document doc "
                       + " where doc.id = " + IiConverter.convertToLong(id);

           LOG.info(" query getTrialDocumentById = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
            
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getTrialDocumentById" , hbe);
            throw new PAException(" Hibernate exception while retrieving getTrialDocumentById "  , hbe);
        }
        
        if (!queryList.isEmpty()) {
            doc = queryList.get(0);
            docDTO = DocumentConverter.convertFromDomainToDTO(doc);
            
        }
        session.flush();
        LOG.info("Leaving getTrialDocumentById");
        return docDTO;
    }

    /**
     * 
     * @param docDTO DocumentDTO
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO updateTrialDocument(DocumentDTO docDTO) throws PAException {
        
        if (docDTO == null) {
            LOG.error(" docDTO should not be null ");
            throw new PAException(" docDTO should not be null ");
        }
        LOG.debug("Entering updateTrialDocument ");
        Session session = null;
        Document doc = null;
        DocumentDTO docRetDTO = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select doc "
                       + " from Document doc "
                       + " where doc.id = " + IiConverter.convertToLong(docDTO.getIi());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            doc = queryList.get(0);
            // set the values from paramter
            doc.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            doc.setUserLastUpdated(docDTO.getUserLastUpdated().getValue());  
            doc.setActiveIndicator(false);
            session.update(doc);
            session.flush();
            docRetDTO = DocumentConverter.convertFromDomainToDTO(doc);
            
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving updateTrialDocument" , hbe);
            throw new PAException(" Hibernate exception while retrieving updateTrialDocument "  , hbe);
        }    
        LOG.debug("Leaving updateTrialDocument ");
        return docRetDTO;
    }

    /**
     * 
     * @param docDTO DocumentDTO
     * @return Boolean
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Boolean deleteTrialDocumentByID(DocumentDTO docDTO) throws PAException {
          
        LOG.debug("Entering deleteTrialDocumentByID ");
        Boolean result = false;
        Session session = null;
        Document doc = null;
        List<Document> queryList = new ArrayList<Document>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = " select d"
                       + " from Document d"
                       + " where d.id = " + IiConverter.convertToLong(docDTO.getIi());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            doc = queryList.get(0);
            // set the values from paramter
            doc.setActiveIndicator(false);
            doc.setInactiveCommentText(StConverter.convertToString(
                    docDTO.getInactiveCommentText()));
            doc.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            doc.setUserLastUpdated(docDTO.getUserLastUpdated().getValue());
            session.update(doc);
            session.flush();
            result = true;
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving deleteTrialDocumentByID" , hbe);
            throw new PAException(" Hibernate exception while retrieving deleteTrialDocumentByID "  , hbe);
        }   
        LOG.debug("Leaving deleteTrialDocumentByID ");
        return result;
    } 
    /**
     * @param docDTO DocumentDTO 
     * @return Boolean
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Boolean checkTypeCodes(DocumentDTO docDTO) throws PAException {
        Boolean result = false;
        List<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
        resultList = getDocumentsByStudyProtocol(docDTO.getStudyProtocolIi());
        if (!(resultList.isEmpty())) {
        for (DocumentDTO check : resultList) {
            if (check.getTypeCode().getCode().equals("Protocol Document") 
                    || check.getTypeCode().getCode().equals("IRB Approval Document")) {
                if (check.getTypeCode().getCode().equals(docDTO.getTypeCode().getCode())) {
                    result = false;  
                    break;
                } else {
                result = true;
                }
            }
         }
        } else {
            result = true;
        }
        return result;
    }
}
