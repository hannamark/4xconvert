package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.iso.convert.StudyRegulatoryAuthorityConverter;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    
    /**
     *
     * @param studyProtocolIi ii
     * @return StudyRegulatoryAuthorityDTO as the dto
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyRegulatoryAuthorityDTO getByStudyProtocol(Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocol Identifer should not be null ");
            throw new PAException(" studyProtocol Identifer should not be null ");
        }
        LOG.info("Entering getStudyRegulatoryAuthority");
        Session session = null;
        StudyRegulatoryAuthority sra = null;

        List<StudyRegulatoryAuthority> queryList = new ArrayList<StudyRegulatoryAuthority>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = " select sra " + " from StudyRegulatoryAuthority sra " + " join sra.studyProtocol sp "
                    + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi);
            LOG.info(" query StudyRegulatoryAuthority = " + hql);
            query = session.createQuery(hql);
            queryList = query.list();
            if (queryList.isEmpty()) {
                return null;
            }
            sra = queryList.get(0);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving StudyRegulatoryAuthority for id = " 
                    + studyProtocolIi.getExtension(),
                    hbe);
            throw new PAException(" Hibernate exception while retrieving StudyRegulatoryAuthority for id = "
                    + studyProtocolIi.getExtension(), hbe);
        }
        StudyRegulatoryAuthorityDTO sraDTO = StudyRegulatoryAuthorityConverter.convertFromDomainToDTO(sra);

        LOG.info("Leaving getStudyRegulatoryAuthority");
        return sraDTO;

    }

    /**
     *
     * @param sraDTO as parameter
     * @return StudyRegulatoryAuthorityDTO as the return object
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyRegulatoryAuthorityDTO create(StudyRegulatoryAuthorityDTO sraDTO) throws PAException {
        if (sraDTO == null) {
            LOG.error(" StudyRegulatoryAuthorityDTO should not be null ");
            throw new PAException(" StudyRegulatoryAuthorityDTO should not be null ");
        }
        LOG.debug("Entering createStudyRegulatoryAuthority ");
        Session session = null;
        StudyRegulatoryAuthority sra = StudyRegulatoryAuthorityConverter.convertFromDTOToDomain(sraDTO);
        //
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sra.setDateLastCreated(now);
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(sraDTO.getStudyProtocolIdentifier()));
        sra.setStudyProtocol(studyProtocol);
        //
        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setId(IiConverter.convertToLong(sraDTO.getRegulatoryAuthorityIdentifier()));
        sra.setRegulatoryAuthority(ra);
        if (ejbContext != null) {
            sra.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(sra);
            session.flush();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while createStudyResourcing " , hbe);
            throw new PAException(" Hibernate exception while createStudyResourcing " , hbe);
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
    public StudyRegulatoryAuthorityDTO update(StudyRegulatoryAuthorityDTO sraDTO) throws PAException {
        StudyRegulatoryAuthorityDTO sraDTO1 = null;
        if (sraDTO == null) {
            LOG.error(" StudyRegulatoryAuthorityDTO should not be null ");
            throw new PAException(" StudyRegulatoryAuthorityDTO should not be null ");

        }
        Timestamp now = new Timestamp((new Date()).getTime());
        Session session = null;
        List<StudyRegulatoryAuthority> queryList = new ArrayList<StudyRegulatoryAuthority>();
        try {
            session = HibernateUtil.getCurrentSession();
            
            Query query = null;
            String hql = " select sra " + " from StudyRegulatoryAuthority sra " + " join sra.studyProtocol sp "
            + " where sp.id = " + IiConverter.convertToLong(sraDTO.getStudyProtocolIdentifier());
            LOG.info(" query StudyRegulatoryAuthority = " + hql);
                query = session.createQuery(hql);
                queryList = query.list();
                StudyRegulatoryAuthority sra = queryList.get(0);
            
                
                
              RegulatoryAuthority ra = new RegulatoryAuthority();
              ra.setId(IiConverter.convertToLong(sraDTO.getRegulatoryAuthorityIdentifier()));
              sra.setRegulatoryAuthority(ra);
              sra.setDateLastUpdated(now);
              if (ejbContext != null) {
                  sra.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
              }
            
              session.update(sra);
              session.flush();
              sraDTO1 =  StudyRegulatoryAuthorityConverter.convertFromDomainToDTO(sra);
            
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating StudyProtocol for id = "
                    + sraDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating StudyProtocol for id = "
                      + sraDTO.getIdentifier().getExtension() , hbe);
        }
        return sraDTO1;
    }


}
