package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.iso.convert.StudyRegulatoryAuthorityConverter;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Stateless Enterprise Java Bean (EJB).
 * 
 * @author Harsha
 * @since 08/05/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.AvoidDuplicateLiterals" })
public class StudyRegulatoryAuthorityServiceBean implements StudyRegulatoryAuthorityServiceRemote {
    private static final Logger LOG = Logger.getLogger(StudyRegulatoryAuthorityServiceBean.class);

    /**
     * 
     * @param ii to be retrieved
     * @return StudyRegulatoryAuthorityDTO as the dto
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyRegulatoryAuthorityDTO getStudyRegulatoryAuthority(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudyRegulatoryAuthority");
        Session session = null;
        StudyRegulatoryAuthority sra = null;
        
        List<StudyRegulatoryAuthority> queryList = new ArrayList<StudyRegulatoryAuthority>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = " select sra " + " from StudyRegulatoryAuthority sra " + " join sra.studyProtocol sp "
                    + " where sp.id = " + IiConverter.convertToLong(ii);
            LOG.info(" query StudyRegulatoryAuthority = " + hql);
            query = session.createQuery(hql);
            queryList = query.list();
            if (queryList.isEmpty()) {
                return null;
            }
            sra = queryList.get(0);
            session.flush();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving StudyRegulatoryAuthority for id = " + ii.getExtension(),
                    hbe);
            throw new PAException(" Hibernate exception while retrieving StudyRegulatoryAuthority for id = "
                    + ii.getExtension(), hbe);
        } finally {
            session.flush();
        }
        StudyRegulatoryAuthorityDTO studyProtocolDTO = StudyRegulatoryAuthorityConverter.convertFromDomainToDTO(sra);

        LOG.info("Leaving getStudyRegulatoryAuthority");
        return studyProtocolDTO;

    }
    
    /**
     * 
     * @param sraDTO as parameter
     * @return StudyRegulatoryAuthorityDTO as the return object
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyRegulatoryAuthorityDTO createStudyRegulatoryAuthority(StudyRegulatoryAuthorityDTO sraDTO) 
                                                                                            throws PAException {
        if (sraDTO == null) {
            LOG.error(" StudyRegulatoryAuthorityDTO should not be null ");
            throw new PAException(" StudyRegulatoryAuthorityDTO should not be null ");
        }     
        LOG.debug("Entering createStudyRegulatoryAuthority ");
        Session session = null;        
        StudyRegulatoryAuthority sra = StudyRegulatoryAuthorityConverter.convertFromDTOToDomain(sraDTO);
        //
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sra.setDateLastUpdated(now);   
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(sraDTO.getProtocolId()));
        sra.setStudyProtocol(studyProtocol);
        sra.setUserLastUpdated(StConverter.convertToString(sraDTO.getUserLastUpdated()));
        //
        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setId(IiConverter.convertToLong(sraDTO.getRegulatoryAuthorityId()));
        sra.setRegulatoryAuthority(ra);
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(sra);            
        } catch (HibernateException hbe) {           
            LOG.error(" Hibernate exception while createStudyResourcing " , hbe);
            throw new PAException(" Hibernate exception while createStudyResourcing " , hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving createStudyRegulatoryAuthority ");
        return StudyRegulatoryAuthorityConverter.convertFromDomainToDTO(sra);
    }
    
    /**
     * 
     * @param sraDTO as parameter
     * @return StudyRegulatoryAuthorityDTO as DTO
     * @throws PAException on exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyRegulatoryAuthorityDTO updateStudyRegulatoryAuthority(StudyRegulatoryAuthorityDTO sraDTO) 
                                                    throws PAException {        
        if (sraDTO == null) {
            LOG.error(" StudyRegulatoryAuthorityDTO should not be null ");
            throw new PAException(" StudyRegulatoryAuthorityDTO should not be null ");
            
        }
        Timestamp now = new Timestamp((new Date()).getTime());
        StudyRegulatoryAuthorityDTO  spDTO = null;
        Session session = null;
        List<StudyRegulatoryAuthority> queryList = new ArrayList<StudyRegulatoryAuthority>();
        try {
            session = HibernateUtil.getCurrentSession();
            
            Query query = null;
            String hql = " select sra " + " from StudyRegulatoryAuthority sra " + " join sra.studyProtocol sp "
            + " where sp.id = " + IiConverter.convertToLong(sraDTO.getProtocolId());
            LOG.info(" query StudyRegulatoryAuthority = " + hql);
            query = session.createQuery(hql);
            queryList = query.list();
            StudyRegulatoryAuthority sra = queryList.get(0);

            ///////// because of update of the object is not working, we will delete the record
            //////// and create it
            session.delete(sra);
            
            StudyRegulatoryAuthority createSra = new StudyRegulatoryAuthority();
            StudyProtocol sp = new StudyProtocol();
            sp.setId(IiConverter.convertToLong(sraDTO.getProtocolId()));
            createSra.setStudyProtocol(sp);
            
            RegulatoryAuthority ra = new RegulatoryAuthority();
            ra.setId(IiConverter.convertToLong(sraDTO.getRegulatoryAuthorityId()));
            createSra.setRegulatoryAuthority(ra);
            
            createSra.setDateLastUpdated(now);   
            createSra.setUserLastUpdated(StConverter.convertToString(sraDTO.getUserLastUpdated()));
            createSra.setRegulatoryAuthority(ra);
            createSra.setStudyProtocol(sp);
            session.save(createSra);
            spDTO =  StudyRegulatoryAuthorityConverter.convertFromDomainToDTO(createSra);
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating StudyProtocol for id = " 
                    + sraDTO.getIi().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating StudyProtocol for id = " 
                      + sraDTO.getIi().getExtension() , hbe);
        } finally {
            session.flush();
        }
        return spDTO;        
    }     
    
    
}
